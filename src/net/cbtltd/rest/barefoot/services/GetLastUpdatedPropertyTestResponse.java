
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
 *         &lt;element name="GetLastUpdatedPropertyTestResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getLastUpdatedPropertyTestResult"
})
@XmlRootElement(name = "GetLastUpdatedPropertyTestResponse")
public class GetLastUpdatedPropertyTestResponse {

    @XmlElement(name = "GetLastUpdatedPropertyTestResult")
    protected String getLastUpdatedPropertyTestResult;

    /**
     * Gets the value of the getLastUpdatedPropertyTestResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetLastUpdatedPropertyTestResult() {
        return getLastUpdatedPropertyTestResult;
    }

    /**
     * Sets the value of the getLastUpdatedPropertyTestResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetLastUpdatedPropertyTestResult(String value) {
        this.getLastUpdatedPropertyTestResult = value;
    }

}
