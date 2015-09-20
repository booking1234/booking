
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
 *         &lt;element name="GetPropertyAvailabilityByDateXMLResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getPropertyAvailabilityByDateXMLResult"
})
@XmlRootElement(name = "GetPropertyAvailabilityByDateXMLResponse")
public class GetPropertyAvailabilityByDateXMLResponse {

    @XmlElement(name = "GetPropertyAvailabilityByDateXMLResult")
    protected String getPropertyAvailabilityByDateXMLResult;

    /**
     * Gets the value of the getPropertyAvailabilityByDateXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetPropertyAvailabilityByDateXMLResult() {
        return getPropertyAvailabilityByDateXMLResult;
    }

    /**
     * Sets the value of the getPropertyAvailabilityByDateXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetPropertyAvailabilityByDateXMLResult(String value) {
        this.getPropertyAvailabilityByDateXMLResult = value;
    }

}
