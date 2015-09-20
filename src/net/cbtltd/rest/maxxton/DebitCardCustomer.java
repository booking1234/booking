
package net.cbtltd.rest.maxxton;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DebitCardCustomer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DebitCardCustomer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://service.newyse.ws.services.newyse.maxxton/}CustomerInfo"/>
 *         &lt;element ref="{http://service.newyse.ws.services.newyse.maxxton/}CustomerReservationInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DebitCardCustomer", propOrder = {
    "customerInfo",
    "customerReservationInfo"
})
public class DebitCardCustomer {

    @XmlElement(name = "CustomerInfo", namespace = "http://service.newyse.ws.services.newyse.maxxton/", required = true)
    protected CustomerInfo customerInfo;
    @XmlElement(name = "CustomerReservationInfo", namespace = "http://service.newyse.ws.services.newyse.maxxton/", required = true)
    protected CustomerReservationInfo customerReservationInfo;

    /**
     * Gets the value of the customerInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerInfo }
     *     
     */
    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    /**
     * Sets the value of the customerInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerInfo }
     *     
     */
    public void setCustomerInfo(CustomerInfo value) {
        this.customerInfo = value;
    }

    /**
     * Gets the value of the customerReservationInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerReservationInfo }
     *     
     */
    public CustomerReservationInfo getCustomerReservationInfo() {
        return customerReservationInfo;
    }

    /**
     * Sets the value of the customerReservationInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerReservationInfo }
     *     
     */
    public void setCustomerReservationInfo(CustomerReservationInfo value) {
        this.customerReservationInfo = value;
    }

}
