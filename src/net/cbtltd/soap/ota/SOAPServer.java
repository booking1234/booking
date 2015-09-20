/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.soap.ota;

import javax.xml.ws.Endpoint;

import org.apache.log4j.Logger;
/* jars for CXF 3.4 -PROBLEM CHILD - probably asm-1.5.3.jar v asm-3.3.jar
    cxf-2.4.0.jar
    jsr311-api-1.1.1.jar
    jaxb-impl-2.1.13.jar
    jaxb-api-2.1.jar

    geronimo-annotation_1.0_spec-1.1.1.jar
    geronimo-activation_1.1_spec-1.1.jar
    geronimo-servlet_3.0_spec_1.0.jar
    commons-logging-1.1.1.jar

    geronimo-stax_api_1.0_spec-1.0.1.jar
    woodstox-core-asl-4.0.8.jar
    stax2-api-3.0.1.jar

    wsdl4j-1.6.2.jar (only needed at the client side)
    xmlschema-core-2.0.jar
    neethi-3.0.0.jar
 */
public class SOAPServer {

	private static final Logger LOG = Logger.getLogger(SOAPServer.class.getName());
	public static final String ENDPOINT_ADDRESS = "http://localhost:9000/xml/soap";

	protected SOAPServer() throws Exception {
//		JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
//		factory.setWsdlURL(RazorPortType.WSDL_FILE);
//		factory.setServiceClass(RazorPortType.class);
//		factory.setServiceBean(new RazorPortTypeImpl());
//		factory.setServiceName(new QName(ENDPOINT_ADDRESS, "RazorPortTypeImpl"));
//		factory.setAddress(RazorPortType.ENDPOINT_ADDRESS);
//		factory.getInInterceptors().add(new LoggingInInterceptor());
//		factory.getOutInterceptors().add(new LoggingOutInterceptor());
//		factory.create();
		Endpoint.publish(ENDPOINT_ADDRESS, new RazorPortTypeImpl());
    }

    public static void main(String args[]) throws Exception {
    	try {
        LOG.debug("Starting SOAP Server");
        new SOAPServer();
        LOG.debug("SOAP Server ready...");

        Thread.sleep(2 * 60 * 1000);
        LOG.debug("Server exiting");
		System.exit(0);
		}
		catch (Throwable x) {
			LOG.debug(x.getMessage());
			x.printStackTrace();
		}
    }

}
