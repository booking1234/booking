
package net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TaxRates complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxRates">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Tax1Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Tax1Percent" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="Tax2Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Tax2Percent" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="Tax3Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Tax3Percent" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxRates", propOrder = {
    "tax1Name",
    "tax1Percent",
    "tax2Name",
    "tax2Percent",
    "tax3Name",
    "tax3Percent"
})
public class TaxRates {

    @XmlElement(name = "Tax1Name")
    protected String tax1Name;
    @XmlElement(name = "Tax1Percent", required = true)
    protected BigDecimal tax1Percent;
    @XmlElement(name = "Tax2Name")
    protected String tax2Name;
    @XmlElement(name = "Tax2Percent", required = true)
    protected BigDecimal tax2Percent;
    @XmlElement(name = "Tax3Name")
    protected String tax3Name;
    @XmlElement(name = "Tax3Percent", required = true)
    protected BigDecimal tax3Percent;

    /**
     * Gets the value of the tax1Name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTax1Name() {
        return tax1Name;
    }

    /**
     * Sets the value of the tax1Name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTax1Name(String value) {
        this.tax1Name = value;
    }

    /**
     * Gets the value of the tax1Percent property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTax1Percent() {
        return tax1Percent;
    }

    /**
     * Sets the value of the tax1Percent property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTax1Percent(BigDecimal value) {
        this.tax1Percent = value;
    }

    /**
     * Gets the value of the tax2Name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTax2Name() {
        return tax2Name;
    }

    /**
     * Sets the value of the tax2Name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTax2Name(String value) {
        this.tax2Name = value;
    }

    /**
     * Gets the value of the tax2Percent property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTax2Percent() {
        return tax2Percent;
    }

    /**
     * Sets the value of the tax2Percent property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTax2Percent(BigDecimal value) {
        this.tax2Percent = value;
    }

    /**
     * Gets the value of the tax3Name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTax3Name() {
        return tax3Name;
    }

    /**
     * Sets the value of the tax3Name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTax3Name(String value) {
        this.tax3Name = value;
    }

    /**
     * Gets the value of the tax3Percent property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTax3Percent() {
        return tax3Percent;
    }

    /**
     * Sets the value of the tax3Percent property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTax3Percent(BigDecimal value) {
        this.tax3Percent = value;
    }

}
