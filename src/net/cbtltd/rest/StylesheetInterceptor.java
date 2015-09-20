/**
 * @author	abookingnet
 * @see		License at http://razorpms.com/razor/License.html
 * @version	2.00
 */
package net.cbtltd.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.stream.XMLStreamException;

import net.cbtltd.shared.License;
import net.cbtltd.shared.api.HasXsl;

import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.apache.cxf.jaxrs.provider.ProviderFactory;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.log4j.Logger;
import org.codehaus.jettison.AbstractXMLStreamWriter;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;

/** 
 * The Class StylesheetInterceptor is to intercept outgoing XML messages and insert a reference to the XSL style sheet by which it can be rendered to HTML. 
 */

public class StylesheetInterceptor extends AbstractPhaseInterceptor<Message> {

	private static final Logger LOG = Logger.getLogger(StylesheetInterceptor.class.getName());
	private static final String XML_HEADERS = "com.sun.xml.bind.xmlHeaders";

	/** Instantiates a new style sheet interceptor. */
	
	public StylesheetInterceptor() {
		super(Phase.MARSHAL);
	}

	/* (non-Javadoc)
	 * @see org.apache.cxf.interceptor.Interceptor#handleMessage(org.apache.cxf.message.Message)
	 */
	public void handleMessage(Message message) {
		try {
			if (message != null) {

				List<Object>params = message.getContent(List.class); //parameter instances
				if (params.get(0) instanceof HasXsl) {

					OperationResourceInfo ori = message.getExchange().get(OperationResourceInfo.class);
					JAXBElementProvider provider = (JAXBElementProvider) ProviderFactory
							.getInstance(message)
							.createMessageBodyWriter(
									ori.getMethodToInvoke().getReturnType(),
									ori.getMethodToInvoke().getGenericReturnType(), 
									ori.getMethodToInvoke().getAnnotations(),
									MediaType.APPLICATION_XML_TYPE, 
									message);
					Map<String, Object>properties = new HashMap<String, Object>();

					HasXsl hasXsl = (HasXsl)params.get(0);
					//System.out.println("Stylesheet params " + hasXsl.getClass() + " " + hasXsl.getXsl());
					if (hasXsl == null || hasXsl.getXsl().equalsIgnoreCase(Constants.NO_XSL)){properties.put(XML_HEADERS, "<!-- no stylesheet -->");}
					else {properties.put(XML_HEADERS, "<?xml-stylesheet type='application/xml' href='" + hasXsl.getXsl() + "'?>");}
					provider.setMarshallerProperties(properties);
				}
			}
		}
		catch(Throwable x){
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
	}

	/**
	 * Xml json.
	 *
	 * @see http://jettison.codehaus.org/User's+Guide
	 * @see http://cxf.apache.org/docs/service-routing.html
	 * @see http://permalink.gmane.org/gmane.comp.apache.cxf.user/14983
	 * @see http://illegalargumentexception.blogspot.com/2009/05/java-rough-guide-to-character-encoding.html#javaencoding_streams
	 * @param message the message
	 * @throws XMLStreamException the xML stream exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void xml2json(Message message) {
		
		try {
	        OutputStream os = message.getContent(OutputStream.class);
	        System.out.println("xml2json os " + os.toString());
	        StringWriter writer = new StringWriter();
	
	        // Mapped convention
	        MappedNamespaceConvention con = new MappedNamespaceConvention();
	        AbstractXMLStreamWriter w = new MappedXMLStreamWriter(con, writer);
	
	        w.flush();
	        System.out.println("w " + w.toString());
	        w.close();
	        writer.close();
	        System.out.println("writer " + writer.toString());
		} 
		catch(Throwable x) {x.printStackTrace();}
		
	}
	
	/* (non-Javadoc)
	 * @see org.apache.cxf.phase.AbstractPhaseInterceptor#handleFault(org.apache.cxf.message.Message)
	 */
	public void handleFault(Message message) {
		LOG.error("Stylesheet Interceptor error " + message.toString());
		throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
	}
}

