
package net.cbtltd.rest.leisurelink.ExternalServices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RateAvailabilityUpdateRQ complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RateAvailabilityUpdateRQ">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Authentication" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PropertyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RateAvailabilities" type="{http://tempuri.org/}ArrayOfRateAvailability" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RateAvailabilityUpdateRQ", propOrder = {
    "authentication",
    "propertyCode",
    "rateAvailabilities"
})
public class RateAvailabilityUpdateRQ {

    @XmlElement(name = "Authentication")
    protected String authentication;
    @XmlElement(name = "PropertyCode")
    protected String propertyCode;
    @XmlElement(name = "RateAvailabilities")
    protected ArrayOfRateAvailability rateAvailabilities;

    /**
     * Gets the value of the authentication property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthentication() {
        return authentication;
    }

    /**
     * Sets the value of the authentication property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthentication(String value) {
        this.authentication = value;
    }

    /**
     * Gets the value of the propertyCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPropertyCode() {
        return propertyCode;
    }

    /**
     * Sets the value of the propertyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPropertyCode(String value) {
        this.propertyCode = value;
    }

    /**
     * Gets the value of the rateAvailabilities property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRateAvailability }
     *     
     */
    public ArrayOfRateAvailability getRateAvailabilities() {
        return rateAvailabilities;
    }

    /**
     * Sets the value of the rateAvailabilities property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRateAvailability }
     *     
     */
    public void setRateAvailabilities(ArrayOfRateAvailability value) {
        this.rateAvailabilities = value;
    }

}
