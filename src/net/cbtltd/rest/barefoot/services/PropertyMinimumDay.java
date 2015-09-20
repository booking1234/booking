
package net.cbtltd.rest.barefoot.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PropertyMinimumDay complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PropertyMinimumDay">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PropertyID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MininumDayRules" type="{http://www.barefoot.com/Services/}ArrayOfMinimumDayRule" minOccurs="0"/>
 *         &lt;element name="TurnoverDay" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PropertyMinimumDay", propOrder = {
    "propertyID",
    "mininumDayRules",
    "turnoverDay"
})
public class PropertyMinimumDay {

    @XmlElement(name = "PropertyID")
    protected int propertyID;
    @XmlElement(name = "MininumDayRules")
    protected ArrayOfMinimumDayRule mininumDayRules;
    @XmlElement(name = "TurnoverDay")
    protected String turnoverDay;

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
     * Gets the value of the mininumDayRules property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMinimumDayRule }
     *     
     */
    public ArrayOfMinimumDayRule getMininumDayRules() {
        return mininumDayRules;
    }

    /**
     * Sets the value of the mininumDayRules property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMinimumDayRule }
     *     
     */
    public void setMininumDayRules(ArrayOfMinimumDayRule value) {
        this.mininumDayRules = value;
    }

    /**
     * Gets the value of the turnoverDay property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTurnoverDay() {
        return turnoverDay;
    }

    /**
     * Sets the value of the turnoverDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTurnoverDay(String value) {
        this.turnoverDay = value;
    }

}
