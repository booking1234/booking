
package net.cbtltd.rest.barefoot.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Amenities complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Amenities">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AmenityName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AmenityValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Amenities", propOrder = {
    "amenityName",
    "amenityValue"
})
public class Amenities {

    @XmlElement(name = "AmenityName")
    protected String amenityName;
    @XmlElement(name = "AmenityValue")
    protected String amenityValue;

    /**
     * Gets the value of the amenityName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmenityName() {
        return amenityName;
    }

    /**
     * Sets the value of the amenityName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmenityName(String value) {
        this.amenityName = value;
    }

    /**
     * Gets the value of the amenityValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmenityValue() {
        return amenityValue;
    }

    /**
     * Sets the value of the amenityValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmenityValue(String value) {
        this.amenityValue = value;
    }

}
