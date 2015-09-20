/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client;

import net.cbtltd.shared.api.HasResponse;
import net.cbtltd.shared.api.HasService;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.SerializationException;

/** The Interface RazorService is the client proxy for the method(s) implemented on the server to handle remote GWT RPC calls. */
@RemoteServiceRelativePath("RazorService")
public interface RazorService extends RemoteService {
	
	/**
	 * Sends a request to the server.
	 *
	 * @param <R> the type of the action to be sent.
	 * @param request the request which is sent
	 * @return the response received from the server.
	 * @throws SerializationException the exception thrown if the action cannot be serialized for transmission.
	 */
	<R extends HasResponse> R send(HasService request) throws SerializationException;
}
