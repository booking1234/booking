
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
 *         &lt;element name="GetOptionalServiceIDsResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getOptionalServiceIDsResult"
})
@XmlRootElement(name = "GetOptionalServiceIDsResponse")
public class GetOptionalServiceIDsResponse {

    @XmlElement(name = "GetOptionalServiceIDsResult")
    protected String getOptionalServiceIDsResult;

    /**
     * Gets the value of the getOptionalServiceIDsResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetOptionalServiceIDsResult() {
        return getOptionalServiceIDsResult;
    }

    /**
     * Sets the value of the getOptionalServiceIDsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetOptionalServiceIDsResult(String value) {
        this.getOptionalServiceIDsResult = value;
    }

}
