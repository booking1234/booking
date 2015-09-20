
package net.cbtltd.rest.leisurelink;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2014-08-26T16:35:41.320-07:00
 * Generated source version: 2.7.12
 * 
 */
public final class RGWServiceSoap_RGWServiceSoap_Client {

    private static final QName SERVICE_NAME = new QName("http://LeisureLink.com/RetailWebServices/", "RGWService");

    private RGWServiceSoap_RGWServiceSoap_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = RGWService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        RGWService ss = new RGWService(wsdlURL, SERVICE_NAME);
        RGWServiceSoap port = ss.getRGWServiceSoap();  
        
        {
        System.out.println("Invoking otaRequest...");
        String _otaRequest_otaRequest = "<OTA_HotelSearchRQ EchoToken=\" \" Version=\"1.001\" xmlns=\"http://www.opentravel.org/OTA/2003/05\"><POS><Source><RequestorID Type=\"13\" ID=\"99\" MessagePassword=\"LL$99test0814\" /></Source></POS><Criteria><Criterion><Address><CountryName Code=\"US\" /></Address></Criterion></Criteria></OTA_HotelSearchRQ>";
        java.lang.String _otaRequest__return = port.otaRequest(_otaRequest_otaRequest);
        System.out.println("otaRequest.result=" + _otaRequest__return);


        }

        System.exit(0);
    }

}
