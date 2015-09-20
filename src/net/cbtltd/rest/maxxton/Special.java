
package net.cbtltd.rest.maxxton;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Special complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Special">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SpecialPrice" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="SpecialPriceInclusive" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SpecialName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SpecialId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="SpecialPolicy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SpecialCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MinAge" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MaxAge" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Special", propOrder = {
    "specialPrice",
    "specialPriceInclusive",
    "quantity",
    "specialName",
    "specialId",
    "specialPolicy",
    "specialCode",
    "minAge",
    "maxAge"
})
public class Special {

    @XmlElement(name = "SpecialPrice")
    protected double specialPrice;
    @XmlElement(name = "SpecialPriceInclusive")
    protected double specialPriceInclusive;
    @XmlElement(name = "Quantity")
    protected int quantity;
    @XmlElement(name = "SpecialName", required = true)
    protected String specialName;
    @XmlElement(name = "SpecialId")
    protected long specialId;
    @XmlElement(name = "SpecialPolicy", required = true)
    protected String specialPolicy;
    @XmlElement(name = "SpecialCode", required = true)
    protected String specialCode;
    @XmlElement(name = "MinAge")
    protected int minAge;
    @XmlElement(name = "MaxAge")
    protected int maxAge;

    /**
     * Gets the value of the specialPrice property.
     * 
     */
    public double getSpecialPrice() {
        return specialPrice;
    }

    /**
     * Sets the value of the specialPrice property.
     * 
     */
    public void setSpecialPrice(double value) {
        this.specialPrice = value;
    }

    /**
     * Gets the value of the specialPriceInclusive property.
     * 
     */
    public double getSpecialPriceInclusive() {
        return specialPriceInclusive;
    }

    /**
     * Sets the value of the specialPriceInclusive property.
     * 
     */
    public void setSpecialPriceInclusive(double value) {
        this.specialPriceInclusive = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     */
    public void setQuantity(int value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the specialName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialName() {
        return specialName;
    }

    /**
     * Sets the value of the specialName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialName(String value) {
        this.specialName = value;
    }

    /**
     * Gets the value of the specialId property.
     * 
     */
    public long getSpecialId() {
        return specialId;
    }

    /**
     * Sets the value of the specialId property.
     * 
     */
    public void setSpecialId(long value) {
        this.specialId = value;
    }

    /**
     * Gets the value of the specialPolicy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialPolicy() {
        return specialPolicy;
    }

    /**
     * Sets the value of the specialPolicy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialPolicy(String value) {
        this.specialPolicy = value;
    }

    /**
     * Gets the value of the specialCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialCode() {
        return specialCode;
    }

    /**
     * Sets the value of the specialCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialCode(String value) {
        this.specialCode = value;
    }

    /**
     * Gets the value of the minAge property.
     * 
     */
    public int getMinAge() {
        return minAge;
    }

    /**
     * Sets the value of the minAge property.
     * 
     */
    public void setMinAge(int value) {
        this.minAge = value;
    }

    /**
     * Gets the value of the maxAge property.
     * 
     */
    public int getMaxAge() {
        return maxAge;
    }

    /**
     * Sets the value of the maxAge property.
     * 
     */
    public void setMaxAge(int value) {
        this.maxAge = value;
    }

}
