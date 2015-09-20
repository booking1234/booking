
package net.cbtltd.rest.leisurelink.ExternalServices;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Rate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Rate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Currency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PerDay" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="RatePlanCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Blackout" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ClosedToArrival" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="MinStay" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MaxStay" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Rate", propOrder = {
    "currency",
    "perDay",
    "ratePlanCode",
    "blackout",
    "closedToArrival",
    "minStay",
    "maxStay"
})
public class Rate {

    @XmlElement(name = "Currency")
    protected String currency;
    @XmlElement(name = "PerDay", required = true)
    protected BigDecimal perDay;
    @XmlElement(name = "RatePlanCode")
    protected String ratePlanCode;
    @XmlElement(name = "Blackout")
    protected boolean blackout;
    @XmlElement(name = "ClosedToArrival")
    protected boolean closedToArrival;
    @XmlElement(name = "MinStay")
    protected int minStay;
    @XmlElement(name = "MaxStay")
    protected int maxStay;

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
     * Gets the value of the perDay property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPerDay() {
        return perDay;
    }

    /**
     * Sets the value of the perDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPerDay(BigDecimal value) {
        this.perDay = value;
    }

    /**
     * Gets the value of the ratePlanCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatePlanCode() {
        return ratePlanCode;
    }

    /**
     * Sets the value of the ratePlanCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatePlanCode(String value) {
        this.ratePlanCode = value;
    }

    /**
     * Gets the value of the blackout property.
     * 
     */
    public boolean isBlackout() {
        return blackout;
    }

    /**
     * Sets the value of the blackout property.
     * 
     */
    public void setBlackout(boolean value) {
        this.blackout = value;
    }

    /**
     * Gets the value of the closedToArrival property.
     * 
     */
    public boolean isClosedToArrival() {
        return closedToArrival;
    }

    /**
     * Sets the value of the closedToArrival property.
     * 
     */
    public void setClosedToArrival(boolean value) {
        this.closedToArrival = value;
    }

    /**
     * Gets the value of the minStay property.
     * 
     */
    public int getMinStay() {
        return minStay;
    }

    /**
     * Sets the value of the minStay property.
     * 
     */
    public void setMinStay(int value) {
        this.minStay = value;
    }

    /**
     * Gets the value of the maxStay property.
     * 
     */
    public int getMaxStay() {
        return maxStay;
    }

    /**
     * Sets the value of the maxStay property.
     * 
     */
    public void setMaxStay(int value) {
        this.maxStay = value;
    }

}
