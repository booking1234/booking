
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
 *         &lt;element name="GetPropertyBookingDateForAllXMLResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getPropertyBookingDateForAllXMLResult"
})
@XmlRootElement(name = "GetPropertyBookingDateForAllXMLResponse")
public class GetPropertyBookingDateForAllXMLResponse {

    @XmlElement(name = "GetPropertyBookingDateForAllXMLResult")
    protected String getPropertyBookingDateForAllXMLResult;

    /**
     * Gets the value of the getPropertyBookingDateForAllXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetPropertyBookingDateForAllXMLResult() {
        return getPropertyBookingDateForAllXMLResult;
    }

    /**
     * Sets the value of the getPropertyBookingDateForAllXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetPropertyBookingDateForAllXMLResult(String value) {
        this.getPropertyBookingDateForAllXMLResult = value;
    }

}
