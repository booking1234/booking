
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package net.cbtltd.rest.leaderstay;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.4.0
 * 2012-08-18T06:04:24.421+02:00
 * Generated source version: 2.4.0
 * 
 */

@javax.jws.WebService(
                      serviceName = "leaderstay",
                      portName = "leaderstayPort",
                      targetNamespace = "urn:leaderstay",
                      wsdlLocation = "file:/C:/leaderstaytesting.wsdl",
                      endpointInterface = "net.cbtltd.rest.leaderstay.LeaderstayPortType")
                      
public class LeaderstayPortTypeImpl implements LeaderstayPortType {

    private static final Logger LOG = Logger.getLogger(LeaderstayPortTypeImpl.class.getName());

    /* (non-Javadoc)
     * @see net.cbtltd.rest.leaderstay.LeaderstayPortType#getInfoAvailabilityList(net.cbtltd.rest.leaderstay.Infoavailabilitylistparameters  parameters )*
     */
    public net.cbtltd.rest.leaderstay.ReturninfoavailabilitylistT getInfoAvailabilityList(Infoavailabilitylistparameters parameters) { 
        LOG.info("Executing operation getInfoAvailabilityList");
        System.out.println(parameters);
        try {
            net.cbtltd.rest.leaderstay.ReturninfoavailabilitylistT _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see net.cbtltd.rest.leaderstay.LeaderstayPortType#cancel(net.cbtltd.rest.leaderstay.Cancelparameters  parameters )*
     */
    public net.cbtltd.rest.leaderstay.Returncancelparameters cancel(Cancelparameters parameters) { 
        LOG.info("Executing operation cancel");
        System.out.println(parameters);
        try {
            net.cbtltd.rest.leaderstay.Returncancelparameters _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see net.cbtltd.rest.leaderstay.LeaderstayPortType#reserve(net.cbtltd.rest.leaderstay.Reserveparameters  parameters )*
     */
    public net.cbtltd.rest.leaderstay.Returnreserveparameters reserve(Reserveparameters parameters) { 
        LOG.info("Executing operation reserve");
        System.out.println(parameters);
        try {
            net.cbtltd.rest.leaderstay.Returnreserveparameters _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see net.cbtltd.rest.leaderstay.LeaderstayPortType#getPriceAvailability(net.cbtltd.rest.leaderstay.Priceavailabilityparameters  parameters )*
     */
    public net.cbtltd.rest.leaderstay.Returnpriceavailability getPriceAvailability(Priceavailabilityparameters parameters) { 
        LOG.info("Executing operation getPriceAvailability");
        System.out.println(parameters);
        try {
            net.cbtltd.rest.leaderstay.Returnpriceavailability _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see net.cbtltd.rest.leaderstay.LeaderstayPortType#getYearAvailability(net.cbtltd.rest.leaderstay.Yearavailabilityparameters  parameters )*
     */
    public net.cbtltd.rest.leaderstay.Returnyearavailability getYearAvailability(Yearavailabilityparameters parameters) { 
        LOG.info("Executing operation getYearAvailability");
        System.out.println(parameters);
        try {
            net.cbtltd.rest.leaderstay.Returnyearavailability _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see net.cbtltd.rest.leaderstay.LeaderstayPortType#getAreaStructure(net.cbtltd.rest.leaderstay.AreaStructureparameters  parameters )*
     */
    public net.cbtltd.rest.leaderstay.AreaStructurelist getAreaStructure(AreaStructureparameters parameters) { 
        LOG.info("Executing operation getAreaStructure");
        System.out.println(parameters);
        try {
            net.cbtltd.rest.leaderstay.AreaStructurelist _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see net.cbtltd.rest.leaderstay.LeaderstayPortType#getPriceAvailabilityList(net.cbtltd.rest.leaderstay.Priceavailabilitylistparameters  parameters )*
     */
    public net.cbtltd.rest.leaderstay.Returnpriceavailabilitylist getPriceAvailabilityList(Priceavailabilitylistparameters parameters) { 
        LOG.info("Executing operation getPriceAvailabilityList");
        System.out.println(parameters);
        try {
            net.cbtltd.rest.leaderstay.Returnpriceavailabilitylist _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see net.cbtltd.rest.leaderstay.LeaderstayPortType#getGlobalHash(net.cbtltd.rest.leaderstay.Hashparameters  parameters )*
     */
    public net.cbtltd.rest.leaderstay.Hashoutput getGlobalHash(Hashparameters parameters) { 
        LOG.info("Executing operation getGlobalHash");
        System.out.println(parameters);
        try {
            net.cbtltd.rest.leaderstay.Hashoutput _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see net.cbtltd.rest.leaderstay.LeaderstayPortType#getPriceAvailabilityList2(net.cbtltd.rest.leaderstay.Priceavailabilitylistparameters2  parameters )*
     */
    public net.cbtltd.rest.leaderstay.Returnpriceavailabilitylist getPriceAvailabilityList2(Priceavailabilitylistparameters2 parameters) { 
        LOG.info("Executing operation getPriceAvailabilityList2");
        System.out.println(parameters);
        try {
            net.cbtltd.rest.leaderstay.Returnpriceavailabilitylist _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see net.cbtltd.rest.leaderstay.LeaderstayPortType#getPriceCatalogue(net.cbtltd.rest.leaderstay.Pricecatalogueparameters  parameters )*
     */
    public net.cbtltd.rest.leaderstay.ReturnpricecatalogueList getPriceCatalogue(Pricecatalogueparameters parameters) { 
        LOG.info("Executing operation getPriceCatalogue");
        System.out.println(parameters);
        try {
            net.cbtltd.rest.leaderstay.ReturnpricecatalogueList _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see net.cbtltd.rest.leaderstay.LeaderstayPortType#amend(net.cbtltd.rest.leaderstay.Amendparameters  parameters )*
     */
    public net.cbtltd.rest.leaderstay.Returnamendparameters amend(Amendparameters parameters) { 
        LOG.info("Executing operation amend");
        System.out.println(parameters);
        try {
            net.cbtltd.rest.leaderstay.Returnamendparameters _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see net.cbtltd.rest.leaderstay.LeaderstayPortType#getAvailableInfo(net.cbtltd.rest.leaderstay.Infoparameters  parameters )*
     */
    public net.cbtltd.rest.leaderstay.ReturnpropertiesInfoList getAvailableInfo(Infoparameters parameters) { 
        LOG.info("Executing operation getAvailableInfo");
        System.out.println(parameters);
        try {
            net.cbtltd.rest.leaderstay.ReturnpropertiesInfoList _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}