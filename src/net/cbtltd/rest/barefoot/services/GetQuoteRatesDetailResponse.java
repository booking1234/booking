
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
 *         &lt;element name="GetQuoteRatesDetailResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getQuoteRatesDetailResult"
})
@XmlRootElement(name = "GetQuoteRatesDetailResponse")
public class GetQuoteRatesDetailResponse {

    @XmlElement(name = "GetQuoteRatesDetailResult")
    protected String getQuoteRatesDetailResult;

    /**
     * Gets the value of the getQuoteRatesDetailResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetQuoteRatesDetailResult() {
        return getQuoteRatesDetailResult;
    }

    /**
     * Sets the value of the getQuoteRatesDetailResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetQuoteRatesDetailResult(String value) {
        this.getQuoteRatesDetailResult = value;
    }

}
