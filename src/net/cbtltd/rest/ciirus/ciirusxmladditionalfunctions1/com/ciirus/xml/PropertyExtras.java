
package net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PropertyExtras complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PropertyExtras">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PropertyID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ItemCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ItemDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FlatFee" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="FlatFeeAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="DailyFee" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="DailyFeeAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="PercentageFee" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Percentage" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="Mandatory" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="MinimumCharge" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="ChargeTax1" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ChargeTax2" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ChargeTax3" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PropertyExtras", propOrder = {
    "propertyID",
    "itemCode",
    "itemDescription",
    "flatFee",
    "flatFeeAmount",
    "dailyFee",
    "dailyFeeAmount",
    "percentageFee",
    "percentage",
    "mandatory",
    "minimumCharge",
    "chargeTax1",
    "chargeTax2",
    "chargeTax3"
})
public class PropertyExtras {

    @XmlElement(name = "PropertyID")
    protected int propertyID;
    @XmlElement(name = "ItemCode")
    protected String itemCode;
    @XmlElement(name = "ItemDescription")
    protected String itemDescription;
    @XmlElement(name = "FlatFee")
    protected boolean flatFee;
    @XmlElement(name = "FlatFeeAmount", required = true)
    protected BigDecimal flatFeeAmount;
    @XmlElement(name = "DailyFee")
    protected boolean dailyFee;
    @XmlElement(name = "DailyFeeAmount", required = true)
    protected BigDecimal dailyFeeAmount;
    @XmlElement(name = "PercentageFee")
    protected boolean percentageFee;
    @XmlElement(name = "Percentage", required = true)
    protected BigDecimal percentage;
    @XmlElement(name = "Mandatory")
    protected boolean mandatory;
    @XmlElement(name = "MinimumCharge", required = true)
    protected BigDecimal minimumCharge;
    @XmlElement(name = "ChargeTax1")
    protected boolean chargeTax1;
    @XmlElement(name = "ChargeTax2")
    protected boolean chargeTax2;
    @XmlElement(name = "ChargeTax3")
    protected boolean chargeTax3;

    /**
     * Gets the value of the propertyID property.
     * 
     */
    public int getPropertyID() {
        return propertyID;
    }

    /**
     * Sets the value of the propertyID property.
     * 
     */
    public void setPropertyID(int value) {
        this.propertyID = value;
    }

    /**
     * Gets the value of the itemCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * Sets the value of the itemCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemCode(String value) {
        this.itemCode = value;
    }

    /**
     * Gets the value of the itemDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     * Sets the value of the itemDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemDescription(String value) {
        this.itemDescription = value;
    }

    /**
     * Gets the value of the flatFee property.
     * 
     */
    public boolean isFlatFee() {
        return flatFee;
    }

    /**
     * Sets the value of the flatFee property.
     * 
     */
    public void setFlatFee(boolean value) {
        this.flatFee = value;
    }

    /**
     * Gets the value of the flatFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFlatFeeAmount() {
        return flatFeeAmount;
    }

    /**
     * Sets the value of the flatFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFlatFeeAmount(BigDecimal value) {
        this.flatFeeAmount = value;
    }

    /**
     * Gets the value of the dailyFee property.
     * 
     */
    public boolean isDailyFee() {
        return dailyFee;
    }

    /**
     * Sets the value of the dailyFee property.
     * 
     */
    public void setDailyFee(boolean value) {
        this.dailyFee = value;
    }

    /**
     * Gets the value of the dailyFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDailyFeeAmount() {
        return dailyFeeAmount;
    }

    /**
     * Sets the value of the dailyFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDailyFeeAmount(BigDecimal value) {
        this.dailyFeeAmount = value;
    }

    /**
     * Gets the value of the percentageFee property.
     * 
     */
    public boolean isPercentageFee() {
        return percentageFee;
    }

    /**
     * Sets the value of the percentageFee property.
     * 
     */
    public void setPercentageFee(boolean value) {
        this.percentageFee = value;
    }

    /**
     * Gets the value of the percentage property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPercentage() {
        return percentage;
    }

    /**
     * Sets the value of the percentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPercentage(BigDecimal value) {
        this.percentage = value;
    }

    /**
     * Gets the value of the mandatory property.
     * 
     */
    public boolean isMandatory() {
        return mandatory;
    }

    /**
     * Sets the value of the mandatory property.
     * 
     */
    public void setMandatory(boolean value) {
        this.mandatory = value;
    }

    /**
     * Gets the value of the minimumCharge property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMinimumCharge() {
        return minimumCharge;
    }

    /**
     * Sets the value of the minimumCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMinimumCharge(BigDecimal value) {
        this.minimumCharge = value;
    }

    /**
     * Gets the value of the chargeTax1 property.
     * 
     */
    public boolean isChargeTax1() {
        return chargeTax1;
    }

    /**
     * Sets the value of the chargeTax1 property.
     * 
     */
    public void setChargeTax1(boolean value) {
        this.chargeTax1 = value;
    }

    /**
     * Gets the value of the chargeTax2 property.
     * 
     */
    public boolean isChargeTax2() {
        return chargeTax2;
    }

    /**
     * Sets the value of the chargeTax2 property.
     * 
     */
    public void setChargeTax2(boolean value) {
        this.chargeTax2 = value;
    }

    /**
     * Gets the value of the chargeTax3 property.
     * 
     */
    public boolean isChargeTax3() {
        return chargeTax3;
    }

    /**
     * Sets the value of the chargeTax3 property.
     * 
     */
    public void setChargeTax3(boolean value) {
        this.chargeTax3 = value;
    }

}
