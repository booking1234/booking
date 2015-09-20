
package net.cbtltd.rest.homeaway.feedparser.domain;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * Fees, deposits, taxes, and other monetary information about a unit.
 * 
 * 
 * <p>Java class for UnitMonetaryInformation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UnitMonetaryInformation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cleaningFee" type="{}Money" minOccurs="0"/>
 *         &lt;element name="contractualBookingDeposit" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="currency" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="damageDeposit" type="{}Money" minOccurs="0"/>
 *         &lt;element name="gratuity" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="nonContractualBookingDeposit" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="rateNotes" type="{}Note" minOccurs="0"/>
 *         &lt;element name="tax" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnitMonetaryInformation", propOrder = {
    "cleaningFee",
    "contractualBookingDeposit",
    "currency",
    "damageDeposit",
    "gratuity",
    "nonContractualBookingDeposit",
    "rateNotes",
    "tax"
})
public class UnitMonetaryInformation {

    protected Money cleaningFee;
    protected BigDecimal contractualBookingDeposit;
    @XmlElement(required = true)
    protected String currency;
    protected Money damageDeposit;
    protected BigDecimal gratuity;
    protected BigDecimal nonContractualBookingDeposit;
    protected Note rateNotes;
    protected BigDecimal tax;

    /**
     * Gets the value of the cleaningFee property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getCleaningFee() {
        return cleaningFee;
    }

    /**
     * Sets the value of the cleaningFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setCleaningFee(Money value) {
        this.cleaningFee = value;
    }

    /**
     * Gets the value of the contractualBookingDeposit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getContractualBookingDeposit() {
        return contractualBookingDeposit;
    }

    /**
     * Sets the value of the contractualBookingDeposit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setContractualBookingDeposit(BigDecimal value) {
        this.contractualBookingDeposit = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the damageDeposit property.
     * 
     * @return
     *     possible object is
     *     {@link Money }
     *     
     */
    public Money getDamageDeposit() {
        return damageDeposit;
    }

    /**
     * Sets the value of the damageDeposit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Money }
     *     
     */
    public void setDamageDeposit(Money value) {
        this.damageDeposit = value;
    }

    /**
     * Gets the value of the gratuity property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getGratuity() {
        return gratuity;
    }

    /**
     * Sets the value of the gratuity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setGratuity(BigDecimal value) {
        this.gratuity = value;
    }

    /**
     * Gets the value of the nonContractualBookingDeposit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNonContractualBookingDeposit() {
        return nonContractualBookingDeposit;
    }

    /**
     * Sets the value of the nonContractualBookingDeposit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNonContractualBookingDeposit(BigDecimal value) {
        this.nonContractualBookingDeposit = value;
    }

    /**
     * Gets the value of the rateNotes property.
     * 
     * @return
     *     possible object is
     *     {@link Note }
     *     
     */
    public Note getRateNotes() {
        return rateNotes;
    }

    /**
     * Sets the value of the rateNotes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Note }
     *     
     */
    public void setRateNotes(Note value) {
        this.rateNotes = value;
    }

    /**
     * Gets the value of the tax property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTax() {
        return tax;
    }

    /**
     * Sets the value of the tax property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTax(BigDecimal value) {
        this.tax = value;
    }

}
