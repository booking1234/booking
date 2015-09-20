/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * 
 * @version	3.0.10
 */
package net.cbtltd.rest.leaderstay;

/**
 * The Class Test.
 * 
 * @see http://stackoverflow.com/questions/412772/java-rpc-encoded-wsdls-are-not-supported-in-jaxws-2-0
 * 		http://stackoverflow.com/questions/7284126/best-way-to-consume-rpc-encoded-webservice
 * 		http://www.jroller.com/gmazza/entry/calling_rpc_encoded_web_services
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

/**
 * This class was generated by Apache CXF 2.4.0
 * 2012-08-18T06:04:24.379+02:00
 * Generated source version: 2.4.0
 * 
 */
public final class _Test {

    private static final QName SERVICE_NAME = new QName("urn:leaderstay", "leaderstay");

    private _Test() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = Leaderstay.WSDL_LOCATION;
        if (args.length > 0) { 
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
      
        Leaderstay ss = new Leaderstay(wsdlURL, SERVICE_NAME);
        LeaderstayPortType port = ss.getLeaderstayPort();  
        
        if (false) {
        System.out.println("Invoking getInfoAvailabilityList...");
        Infoavailabilitylistparameters rq = new Infoavailabilitylistparameters();
 //       rq.setArea(value);
        ReturninfoavailabilitylistT _getInfoAvailabilityList__return = port.getInfoAvailabilityList(rq);
        System.out.println("getInfoAvailabilityList.result=" + _getInfoAvailabilityList__return);
        }
        if (false) {
        System.out.println("Invoking cancel...");
        Cancelparameters _cancel_parameters = null;
        Returncancelparameters _cancel__return = port.cancel(_cancel_parameters);
        System.out.println("cancel.result=" + _cancel__return);
        }
        if (false) {
        System.out.println("Invoking reserve...");
        Reserveparameters _reserve_parameters = null;
        Returnreserveparameters _reserve__return = port.reserve(_reserve_parameters);
        System.out.println("reserve.result=" + _reserve__return);
        }
        if (false) {
        System.out.println("Invoking getPriceAvailability...");
        Priceavailabilityparameters _getPriceAvailability_parameters = null;
        Returnpriceavailability _getPriceAvailability__return = port.getPriceAvailability(_getPriceAvailability_parameters);
        System.out.println("getPriceAvailability.result=" + _getPriceAvailability__return);
        }
        if (false) {
        System.out.println("Invoking getYearAvailability...");
        Yearavailabilityparameters _getYearAvailability_parameters = null;
        Returnyearavailability _getYearAvailability__return = port.getYearAvailability(_getYearAvailability_parameters);
        System.out.println("getYearAvailability.result=" + _getYearAvailability__return);
        }
        if (false) {
        System.out.println("Invoking getAreaStructure...");
        AreaStructureparameters _getAreaStructure_parameters = null;
        AreaStructurelist _getAreaStructure__return = port.getAreaStructure(_getAreaStructure_parameters);
        System.out.println("getAreaStructure.result=" + _getAreaStructure__return);
        }
        if (false) {
        System.out.println("Invoking getPriceAvailabilityList...");
        Priceavailabilitylistparameters _getPriceAvailabilityList_parameters = null;
        Returnpriceavailabilitylist _getPriceAvailabilityList__return = port.getPriceAvailabilityList(_getPriceAvailabilityList_parameters);
        System.out.println("getPriceAvailabilityList.result=" + _getPriceAvailabilityList__return);
        }
        if (false) {
        System.out.println("Invoking getGlobalHash...");
        Hashparameters _getGlobalHash_parameters = null;
        Hashoutput _getGlobalHash__return = port.getGlobalHash(_getGlobalHash_parameters);
        System.out.println("getGlobalHash.result=" + _getGlobalHash__return);
        }
        if (false) {
        System.out.println("Invoking getPriceAvailabilityList2...");
        Priceavailabilitylistparameters2 _getPriceAvailabilityList2_parameters = null;
        Returnpriceavailabilitylist _getPriceAvailabilityList2__return = port.getPriceAvailabilityList2(_getPriceAvailabilityList2_parameters);
        System.out.println("getPriceAvailabilityList2.result=" + _getPriceAvailabilityList2__return);
        }
        if (false) {
        System.out.println("Invoking getPriceCatalogue...");
        Pricecatalogueparameters _getPriceCatalogue_parameters = null;
        ReturnpricecatalogueList _getPriceCatalogue__return = port.getPriceCatalogue(_getPriceCatalogue_parameters);
        System.out.println("getPriceCatalogue.result=" + _getPriceCatalogue__return);
        }
        if (false) {
        System.out.println("Invoking amend...");
        Amendparameters _amend_parameters = null;
        Returnamendparameters _amend__return = port.amend(_amend_parameters);
        System.out.println("amend.result=" + _amend__return);
        }
        if (true) {
        System.out.println("Invoking getAvailableInfo...");
        Infoparameters rq = new Infoparameters();
        rq.setId("1");
        rq.setCheck(1);
        ReturnpropertiesInfoList rs = port.getAvailableInfo(rq);
        System.out.println("getAvailableInfo.result=" + rs);
        }

        System.exit(0);
    }

}

