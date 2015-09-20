
package net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml;

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
 *         &lt;element name="GetPropertiesResult" type="{http://xml.ciirus.com/}ArrayOfPropertyDetails" minOccurs="0"/>
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
    "getPropertiesResult"
})
@XmlRootElement(name = "GetPropertiesResponse")
public class GetPropertiesResponse {

    @XmlElement(name = "GetPropertiesResult")
    protected ArrayOfPropertyDetails getPropertiesResult;

    /**
     * Gets the value of the getPropertiesResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPropertyDetails }
     *     
     */
    public ArrayOfPropertyDetails getGetPropertiesResult() {
        return getPropertiesResult;
    }

    /**
     * Sets the value of the getPropertiesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPropertyDetails }
     *     
     */
    public void setGetPropertiesResult(ArrayOfPropertyDetails value) {
        this.getPropertiesResult = value;
    }

}
