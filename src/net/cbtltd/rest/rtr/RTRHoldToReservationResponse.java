
package net.cbtltd.rest.rtr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RTRHoldToReservationResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "rtrHoldToReservationResult"
})
@XmlRootElement(name = "RTRHoldToReservationResponse")
public class RTRHoldToReservationResponse {

    @XmlElement(name = "RTRHoldToReservationResult")
    protected String rtrHoldToReservationResult;

    /**
     * Gets the value of the rtrHoldToReservationResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRTRHoldToReservationResult() {
        return rtrHoldToReservationResult;
    }

    /**
     * Sets the value of the rtrHoldToReservationResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRTRHoldToReservationResult(String value) {
        this.rtrHoldToReservationResult = value;
    }

}
