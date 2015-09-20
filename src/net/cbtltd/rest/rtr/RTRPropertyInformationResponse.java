
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
 *         &lt;element name="RTRPropertyInformationResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "rtrPropertyInformationResult"
})
@XmlRootElement(name = "RTRPropertyInformationResponse")
public class RTRPropertyInformationResponse {

    @XmlElement(name = "RTRPropertyInformationResult")
    protected String rtrPropertyInformationResult;

    /**
     * Gets the value of the rtrPropertyInformationResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRTRPropertyInformationResult() {
        return rtrPropertyInformationResult;
    }

    /**
     * Sets the value of the rtrPropertyInformationResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRTRPropertyInformationResult(String value) {
        this.rtrPropertyInformationResult = value;
    }

}
