
package net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml;

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
 *         &lt;element name="GetTaxRatesResult" type="{http://xml.ciirus.com/}TaxRates"/>
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
    "getTaxRatesResult"
})
@XmlRootElement(name = "GetTaxRatesResponse")
public class GetTaxRatesResponse {

    @XmlElement(name = "GetTaxRatesResult", required = true)
    protected TaxRates getTaxRatesResult;

    /**
     * Gets the value of the getTaxRatesResult property.
     * 
     * @return
     *     possible object is
     *     {@link TaxRates }
     *     
     */
    public TaxRates getGetTaxRatesResult() {
        return getTaxRatesResult;
    }

    /**
     * Sets the value of the getTaxRatesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxRates }
     *     
     */
    public void setGetTaxRatesResult(TaxRates value) {
        this.getTaxRatesResult = value;
    }

}
