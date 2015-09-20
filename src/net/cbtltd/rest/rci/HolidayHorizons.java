
package net.cbtltd.rest.rci;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for holiday-horizons complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="holiday-horizons">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="area-attractions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="climate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="furtherInformation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="introduction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="majorAirportsTransportation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resorts" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="specialNotesPrecautions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="checkInCheckout" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "holiday-horizons", propOrder = {
    "areaAttractions",
    "climate",
    "furtherInformation",
    "introduction",
    "majorAirportsTransportation",
    "resorts",
    "specialNotesPrecautions",
    "checkInCheckout"
})
public class HolidayHorizons {

    @XmlElement(name = "area-attractions")
    protected String areaAttractions;
    protected String climate;
    protected String furtherInformation;
    protected String introduction;
    protected String majorAirportsTransportation;
    protected String resorts;
    protected String specialNotesPrecautions;
    protected String checkInCheckout;

    /**
     * Gets the value of the areaAttractions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAreaAttractions() {
        return areaAttractions;
    }

    /**
     * Sets the value of the areaAttractions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAreaAttractions(String value) {
        this.areaAttractions = value;
    }

    /**
     * Gets the value of the climate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClimate() {
        return climate;
    }

    /**
     * Sets the value of the climate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClimate(String value) {
        this.climate = value;
    }

    /**
     * Gets the value of the furtherInformation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFurtherInformation() {
        return furtherInformation;
    }

    /**
     * Sets the value of the furtherInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFurtherInformation(String value) {
        this.furtherInformation = value;
    }

    /**
     * Gets the value of the introduction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * Sets the value of the introduction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntroduction(String value) {
        this.introduction = value;
    }

    /**
     * Gets the value of the majorAirportsTransportation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMajorAirportsTransportation() {
        return majorAirportsTransportation;
    }

    /**
     * Sets the value of the majorAirportsTransportation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMajorAirportsTransportation(String value) {
        this.majorAirportsTransportation = value;
    }

    /**
     * Gets the value of the resorts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResorts() {
        return resorts;
    }

    /**
     * Sets the value of the resorts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResorts(String value) {
        this.resorts = value;
    }

    /**
     * Gets the value of the specialNotesPrecautions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialNotesPrecautions() {
        return specialNotesPrecautions;
    }

    /**
     * Sets the value of the specialNotesPrecautions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialNotesPrecautions(String value) {
        this.specialNotesPrecautions = value;
    }

    /**
     * Gets the value of the checkInCheckout property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckInCheckout() {
        return checkInCheckout;
    }

    /**
     * Sets the value of the checkInCheckout property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckInCheckout(String value) {
        this.checkInCheckout = value;
    }

}
