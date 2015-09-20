package net.cbtltd.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.cbtltd.server.BCrypt;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.shared.NameId;

import org.apache.cxf.binding.soap.interceptor.SoapHeaderInterceptor;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

/**
 * CXF Interceptor that provides HTTP Basic Authentication validation.
 *
 * @see http://code.google.com/p/fenius/wiki/SecurityConfig
 * @see http://code.google.com/p/fenius/source/browse/trunk/fenius-util/src/main/java/is/glif/fenius/util/BasicAuthAuthorizationInterceptor.java?r=111
 * Based on the concepts outline here: http://chrisdail.com/2008/03/31/apache-cxf-with-http-basic-authentication
 *
 * @author CDail
 */
public class BasicAuthAuthorizationInterceptor extends SoapHeaderInterceptor {

	private static final Logger LOG = Logger.getLogger(BasicAuthAuthorizationInterceptor.class.getName());

	/** 
	 * Map of allowed users to this system with their corresponding passwords. 
	 * Load from database using email:password for employers only.
	 */

	private Map<String, String> users = new HashMap<String, String>();

	public BasicAuthAuthorizationInterceptor() {
		setUsers(null);
	}

	@Required
	public void setUsers(Map<String, String> users) {
		if (users == null) {
			SqlSession sqlSession = RazorServer.openSession();
			try {
				ArrayList<NameId> credentials = sqlSession.getMapper(PartyMapper.class).credentials();
				for (NameId credential : credentials) {this.users.put(credential.getName(), credential.getId());}
				LOG.error("users " + users);
			}
			catch (Throwable x) {x.printStackTrace();}
			finally {sqlSession.close();}
		}
		else {this.users = users;}
	}

	@Override public void handleMessage(Message message) throws Fault {
		try {
			// This is set by CXF
			AuthorizationPolicy policy = message.get(AuthorizationPolicy.class);

			// If the policy is not set, the user did not specify credentials
			// A 401 is sent to the client to indicate that authentication is required
			if (users == null) {LOG.error("No users have been defined");}
			else if (policy == null) {
				LOG.error("User attempted to LOG in with no credentials");
				sendErrorResponse(message, HttpURLConnection.HTTP_UNAUTHORIZED);
			}
			else {
				// Verify the password
				//			String hashedPassword = users.get(policy.getUserName());
				LOG.debug("user count " + users.size());
				boolean passes = false;
				String username = policy.getUserName();
				String password = users.get(username);
				
				LOG.debug("trying " + username + ", " + policy.getPassword() + ", " + password);

				if (password != null) {passes = BCrypt.checkpw(policy.getPassword(), password);}

				LOG.debug("passes " + passes + " " + policy.getUserName() + " " + policy.getPassword());
				//			if (realPassword == null || !realPassword.equals(policy.getPassword())) {
				if (!passes) {
					LOG.error("Invalid username or password for user: " + policy.getUserName() + " " + policy.getPassword());
					sendErrorResponse(message, HttpURLConnection.HTTP_FORBIDDEN);
				}
			}
		}
		catch (Throwable x) {x.printStackTrace();}
	}

	private void sendErrorResponse(Message message, int responseCode) {
		Message outMessage = getOutMessage(message);
		outMessage.put(Message.RESPONSE_CODE, responseCode);

		// Set the response headers
		Map<String, List<String>> responseHeaders =
				(Map<String, List<String>>)message.get(Message.PROTOCOL_HEADERS);
		if (responseHeaders != null) {
			responseHeaders.put("WWW-Authenticate", Arrays.asList(new String[]{"Basic realm=realm"}));
			responseHeaders.put("Content-Length", Arrays.asList(new String[]{"0"}));
		}
		message.getInterceptorChain().abort();
		try {
			getConduit(message).prepare(outMessage);
			close(outMessage);
		}
		catch (IOException e) {LOG.error(e.getMessage(), e);}
	}

	private Message getOutMessage(Message inMessage) {
		Exchange exchange = inMessage.getExchange();
		Message outMessage = exchange.getOutMessage();
		if (outMessage == null) {
			Endpoint endpoint = exchange.get(Endpoint.class);
			outMessage = endpoint.getBinding().createMessage();
			exchange.setOutMessage(outMessage);
		}
		outMessage.putAll(inMessage);
		return outMessage;
	}

	private Conduit getConduit(Message inMessage) throws IOException {
		Exchange exchange = inMessage.getExchange();
		EndpointReferenceType target = exchange.get(EndpointReferenceType.class);
		Conduit conduit =
				exchange.getDestination().getBackChannel(inMessage, null, target);
		exchange.setConduit(conduit);
		return conduit;
	}

	private void close(Message outMessage) throws IOException {
		OutputStream os = outMessage.getContent(OutputStream.class);
		os.flush();
		os.close();
	}
}


