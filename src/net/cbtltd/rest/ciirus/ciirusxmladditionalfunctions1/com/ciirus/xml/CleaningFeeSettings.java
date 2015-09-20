
package net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CleaningFeeSettings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CleaningFeeSettings">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ChargeCleaningFee" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CleaningFeeAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="OnlyChargeCleaningFeeWhenLessThanDays" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CleaningFeeSettings", propOrder = {
    "chargeCleaningFee",
    "cleaningFeeAmount",
    "onlyChargeCleaningFeeWhenLessThanDays"
})
public class CleaningFeeSettings {

    @XmlElement(name = "ChargeCleaningFee")
    protected boolean chargeCleaningFee;
    @XmlElement(name = "CleaningFeeAmount", required = true)
    protected BigDecimal cleaningFeeAmount;
    @XmlElement(name = "OnlyChargeCleaningFeeWhenLessThanDays")
    protected int onlyChargeCleaningFeeWhenLessThanDays;

    /**
     * Gets the value of the chargeCleaningFee property.
     * 
     */
    public boolean isChargeCleaningFee() {
        return chargeCleaningFee;
    }

    /**
     * Sets the value of the chargeCleaningFee property.
     * 
     */
    public void setChargeCleaningFee(boolean value) {
        this.chargeCleaningFee = value;
    }

    /**
     * Gets the value of the cleaningFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCleaningFeeAmount() {
        return cleaningFeeAmount;
    }

    /**
     * Sets the value of the cleaningFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCleaningFeeAmount(BigDecimal value) {
        this.cleaningFeeAmount = value;
    }

    /**
     * Gets the value of the onlyChargeCleaningFeeWhenLessThanDays property.
     * 
     */
    public int getOnlyChargeCleaningFeeWhenLessThanDays() {
        return onlyChargeCleaningFeeWhenLessThanDays;
    }

    /**
     * Sets the value of the onlyChargeCleaningFeeWhenLessThanDays property.
     * 
     */
    public void setOnlyChargeCleaningFeeWhenLessThanDays(int value) {
        this.onlyChargeCleaningFeeWhenLessThanDays = value;
    }

}
