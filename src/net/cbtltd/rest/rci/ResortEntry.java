
package net.cbtltd.rest.rci;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resort-entry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resort-entry">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resortId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="languageCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="value" type="{}resort-amenities"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resort-entry", propOrder = {
    "resortId",
    "languageCode",
    "value"
})
public class ResortEntry {

    @XmlElement(required = true)
    protected String resortId;
    @XmlElement(required = true)
    protected String languageCode;
    @XmlElement(required = true)
    protected ResortAmenities value;

    /**
     * Gets the value of the resortId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResortId() {
        return resortId;
    }

    /**
     * Sets the value of the resortId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResortId(String value) {
        this.resortId = value;
    }

    /**
     * Gets the value of the languageCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Sets the value of the languageCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguageCode(String value) {
        this.languageCode = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link ResortAmenities }
     *     
     */
    public ResortAmenities getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortAmenities }
     *     
     */
    public void setValue(ResortAmenities value) {
        this.value = value;
    }

}
