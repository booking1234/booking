
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
 *         &lt;element name="GetLastUpdatedPropertyIDsResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getLastUpdatedPropertyIDsResult"
})
@XmlRootElement(name = "GetLastUpdatedPropertyIDsResponse")
public class GetLastUpdatedPropertyIDsResponse {

    @XmlElement(name = "GetLastUpdatedPropertyIDsResult")
    protected String getLastUpdatedPropertyIDsResult;

    /**
     * Gets the value of the getLastUpdatedPropertyIDsResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetLastUpdatedPropertyIDsResult() {
        return getLastUpdatedPropertyIDsResult;
    }

    /**
     * Sets the value of the getLastUpdatedPropertyIDsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetLastUpdatedPropertyIDsResult(String value) {
        this.getLastUpdatedPropertyIDsResult = value;
    }

}
