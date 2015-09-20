//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.12.05 at 09:44:01 AM IST 
//


package net.cbtltd.rest.nextpax;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "availabilityRequestOrSellRequestOrAssignRequestOrBookRequestOrChangeBookRequestOrSupplyRequestOrPreBookRequestOrRemoveComponentRequestOrComplaintRequestOrSearchRequestOrSearchDetailsRequest",
    "securityInfo"
})
@XmlRootElement(name = "TRequest")
public class TRequest {

    @XmlElements({
        @XmlElement(name = "AvailabilityRequest", type = AvailabilityRequest.class),
        @XmlElement(name = "SellRequest", type = SellRequest.class),
        @XmlElement(name = "AssignRequest", type = AssignRequest.class),
        @XmlElement(name = "BookRequest", type = BookRequest.class),
        @XmlElement(name = "ChangeBookRequest", type = ChangeBookRequest.class),
        @XmlElement(name = "SupplyRequest", type = SupplyRequest.class),
        @XmlElement(name = "PreBookRequest", type = PreBookRequest.class),
        @XmlElement(name = "RemoveComponentRequest", type = RemoveComponentRequest.class),
        @XmlElement(name = "ComplaintRequest", type = ComplaintRequest.class),
        @XmlElement(name = "SearchRequest", type = SearchRequest.class),
        @XmlElement(name = "SearchDetailsRequest", type = SearchDetailsRequest.class)
    })
    protected List<Object> availabilityRequestOrSellRequestOrAssignRequestOrBookRequestOrChangeBookRequestOrSupplyRequestOrPreBookRequestOrRemoveComponentRequestOrComplaintRequestOrSearchRequestOrSearchDetailsRequest;
    @XmlElement(name = "SecurityInfo")
    protected SecurityInfo securityInfo;

    /**
     * Gets the value of the availabilityRequestOrSellRequestOrAssignRequestOrBookRequestOrChangeBookRequestOrSupplyRequestOrPreBookRequestOrRemoveComponentRequestOrComplaintRequestOrSearchRequestOrSearchDetailsRequest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the availabilityRequestOrSellRequestOrAssignRequestOrBookRequestOrChangeBookRequestOrSupplyRequestOrPreBookRequestOrRemoveComponentRequestOrComplaintRequestOrSearchRequestOrSearchDetailsRequest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAvailabilityRequestOrSellRequestOrAssignRequestOrBookRequestOrChangeBookRequestOrSupplyRequestOrPreBookRequestOrRemoveComponentRequestOrComplaintRequestOrSearchRequestOrSearchDetailsRequest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AvailabilityRequest }
     * {@link SellRequest }
     * {@link AssignRequest }
     * {@link BookRequest }
     * {@link ChangeBookRequest }
     * {@link SupplyRequest }
     * {@link PreBookRequest }
     * {@link RemoveComponentRequest }
     * {@link ComplaintRequest }
     * {@link SearchRequest }
     * {@link SearchDetailsRequest }
     * 
     * 
     */
    public List<Object> getAvailabilityRequestOrSellRequestOrAssignRequestOrBookRequestOrChangeBookRequestOrSupplyRequestOrPreBookRequestOrRemoveComponentRequestOrComplaintRequestOrSearchRequestOrSearchDetailsRequest() {
        if (availabilityRequestOrSellRequestOrAssignRequestOrBookRequestOrChangeBookRequestOrSupplyRequestOrPreBookRequestOrRemoveComponentRequestOrComplaintRequestOrSearchRequestOrSearchDetailsRequest == null) {
            availabilityRequestOrSellRequestOrAssignRequestOrBookRequestOrChangeBookRequestOrSupplyRequestOrPreBookRequestOrRemoveComponentRequestOrComplaintRequestOrSearchRequestOrSearchDetailsRequest = new ArrayList<Object>();
        }
        return this.availabilityRequestOrSellRequestOrAssignRequestOrBookRequestOrChangeBookRequestOrSupplyRequestOrPreBookRequestOrRemoveComponentRequestOrComplaintRequestOrSearchRequestOrSearchDetailsRequest;
    }

    /**
     * Gets the value of the securityInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SecurityInfo }
     *     
     */
    public SecurityInfo getSecurityInfo() {
        return securityInfo;
    }

    /**
     * Sets the value of the securityInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecurityInfo }
     *     
     */
    public void setSecurityInfo(SecurityInfo value) {
        this.securityInfo = value;
    }

}