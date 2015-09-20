
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
 *         &lt;element name="RTRPropertyAvailabilityResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "rtrPropertyAvailabilityResult"
})
@XmlRootElement(name = "RTRPropertyAvailabilityResponse")
public class RTRPropertyAvailabilityResponse {

    @XmlElement(name = "RTRPropertyAvailabilityResult")
    protected String rtrPropertyAvailabilityResult;

    /**
     * Gets the value of the rtrPropertyAvailabilityResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRTRPropertyAvailabilityResult() {
        return rtrPropertyAvailabilityResult;
    }

    /**
     * Sets the value of the rtrPropertyAvailabilityResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRTRPropertyAvailabilityResult(String value) {
        this.rtrPropertyAvailabilityResult = value;
    }

}
