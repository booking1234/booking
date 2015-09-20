
package net.cbtltd.rest.maxxton;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DebitCardCustomerInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DebitCardCustomerInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CustomerInfoItem" type="{http://service.newyse.ws.services.newyse.maxxton/}CustomerInfo"/>
 *         &lt;element name="CustomerReservationInfoItem" type="{http://service.newyse.ws.services.newyse.maxxton/}CustomerReservationInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DebitCardCustomerInfo", propOrder = {
    "customerInfoItem",
    "customerReservationInfoItem"
})
public class DebitCardCustomerInfo {

    @XmlElement(name = "CustomerInfoItem", required = true)
    protected CustomerInfo customerInfoItem;
    @XmlElement(name = "CustomerReservationInfoItem", required = true)
    protected CustomerReservationInfo customerReservationInfoItem;

    /**
     * Gets the value of the customerInfoItem property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerInfo }
     *     
     */
    public CustomerInfo getCustomerInfoItem() {
        return customerInfoItem;
    }

    /**
     * Sets the value of the customerInfoItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerInfo }
     *     
     */
    public void setCustomerInfoItem(CustomerInfo value) {
        this.customerInfoItem = value;
    }

    /**
     * Gets the value of the customerReservationInfoItem property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerReservationInfo }
     *     
     */
    public CustomerReservationInfo getCustomerReservationInfoItem() {
        return customerReservationInfoItem;
    }

    /**
     * Sets the value of the customerReservationInfoItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerReservationInfo }
     *     
     */
    public void setCustomerReservationInfoItem(CustomerReservationInfo value) {
        this.customerReservationInfoItem = value;
    }

}
