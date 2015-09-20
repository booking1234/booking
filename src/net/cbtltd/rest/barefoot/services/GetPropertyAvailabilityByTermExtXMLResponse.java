
package net.cbtltd.rest.barefoot.services;

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
 *         &lt;element name="GetPropertyAvailabilityByTermExtXMLResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getPropertyAvailabilityByTermExtXMLResult"
})
@XmlRootElement(name = "GetPropertyAvailabilityByTermExtXMLResponse")
public class GetPropertyAvailabilityByTermExtXMLResponse {

    @XmlElement(name = "GetPropertyAvailabilityByTermExtXMLResult")
    protected String getPropertyAvailabilityByTermExtXMLResult;

    /**
     * Gets the value of the getPropertyAvailabilityByTermExtXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetPropertyAvailabilityByTermExtXMLResult() {
        return getPropertyAvailabilityByTermExtXMLResult;
    }

    /**
     * Sets the value of the getPropertyAvailabilityByTermExtXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetPropertyAvailabilityByTermExtXMLResult(String value) {
        this.getPropertyAvailabilityByTermExtXMLResult = value;
    }

}
