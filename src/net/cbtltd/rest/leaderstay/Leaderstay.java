
/*
 * 
 */

package net.cbtltd.rest.leaderstay;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.4.0
 * 2012-08-18T06:04:24.461+02:00
 * Generated source version: 2.4.0
 * 
 */


@WebServiceClient(name = "leaderstay", 
                  wsdlLocation = "file:/C:/wsdl/leaderstay.wsdl",
                  targetNamespace = "urn:leaderstay") 
public class Leaderstay extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("urn:leaderstay", "leaderstay");
    public final static QName LeaderstayPort = new QName("urn:leaderstay", "leaderstayPort");
    static {
        URL url = null;
        try {
            url = new URL("file:/C:/wsdl/leaderstay.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(Leaderstay.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/C:/wsdl/leaderstay.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public Leaderstay(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public Leaderstay(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Leaderstay() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     * 
     * @return
     *     returns LeaderstayPortType
     */
    @WebEndpoint(name = "leaderstayPort")
    public LeaderstayPortType getLeaderstayPort() {
        return super.getPort(LeaderstayPort, LeaderstayPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns LeaderstayPortType
     */
    @WebEndpoint(name = "leaderstayPort")
    public LeaderstayPortType getLeaderstayPort(WebServiceFeature... features) {
        return super.getPort(LeaderstayPort, LeaderstayPortType.class, features);
    }

}