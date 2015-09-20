
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
 *         &lt;element name="GetPropertyAllImgsXMLResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getPropertyAllImgsXMLResult"
})
@XmlRootElement(name = "GetPropertyAllImgsXMLResponse")
public class GetPropertyAllImgsXMLResponse {

    @XmlElement(name = "GetPropertyAllImgsXMLResult")
    protected String getPropertyAllImgsXMLResult;

    /**
     * Gets the value of the getPropertyAllImgsXMLResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetPropertyAllImgsXMLResult() {
        return getPropertyAllImgsXMLResult;
    }

    /**
     * Sets the value of the getPropertyAllImgsXMLResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetPropertyAllImgsXMLResult(String value) {
        this.getPropertyAllImgsXMLResult = value;
    }

}
