
package net.cbtltd.rest.rci;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resort-highlights-info complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resort-highlights-info">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resort-highlights" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="destination-highlights" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unit-highlights" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="legacy-highlights" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resort-highlights-info", propOrder = {
    "resortHighlights",
    "destinationHighlights",
    "unitHighlights",
    "legacyHighlights"
})
public class ResortHighlightsInfo {

    @XmlElement(name = "resort-highlights")
    protected String resortHighlights;
    @XmlElement(name = "destination-highlights")
    protected String destinationHighlights;
    @XmlElement(name = "unit-highlights")
    protected String unitHighlights;
    @XmlElement(name = "legacy-highlights")
    protected String legacyHighlights;

    /**
     * Gets the value of the resortHighlights property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResortHighlights() {
        return resortHighlights;
    }

    /**
     * Sets the value of the resortHighlights property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResortHighlights(String value) {
        this.resortHighlights = value;
    }

    /**
     * Gets the value of the destinationHighlights property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationHighlights() {
        return destinationHighlights;
    }

    /**
     * Sets the value of the destinationHighlights property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationHighlights(String value) {
        this.destinationHighlights = value;
    }

    /**
     * Gets the value of the unitHighlights property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitHighlights() {
        return unitHighlights;
    }

    /**
     * Sets the value of the unitHighlights property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitHighlights(String value) {
        this.unitHighlights = value;
    }

    /**
     * Gets the value of the legacyHighlights property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegacyHighlights() {
        return legacyHighlights;
    }

    /**
     * Sets the value of the legacyHighlights property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegacyHighlights(String value) {
        this.legacyHighlights = value;
    }

}
