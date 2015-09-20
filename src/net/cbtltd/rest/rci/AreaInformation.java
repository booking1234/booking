
package net.cbtltd.rest.rci;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for area-information complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="area-information">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="areaCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="languageCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="areaName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="areaStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="areaAttractions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="climate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="furtherInformation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="overview" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="majorAirportsTransportation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resorts" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="specialNotes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="languages" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "area-information", propOrder = {
    "areaCode",
    "languageCode",
    "areaName",
    "areaStatus",
    "areaAttractions",
    "climate",
    "furtherInformation",
    "overview",
    "majorAirportsTransportation",
    "resorts",
    "specialNotes",
    "languages"
})
public class AreaInformation {

    @XmlElement(required = true)
    protected String areaCode;
    @XmlElement(required = true)
    protected String languageCode;
    @XmlElement(required = true)
    protected String areaName;
    protected String areaStatus;
    protected String areaAttractions;
    protected String climate;
    protected String furtherInformation;
    protected String overview;
    protected String majorAirportsTransportation;
    protected String resorts;
    protected String specialNotes;
    protected String languages;

    /**
     * Gets the value of the areaCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * Sets the value of the areaCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAreaCode(String value) {
        this.areaCode = value;
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
     * Gets the value of the areaName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * Sets the value of the areaName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAreaName(String value) {
        this.areaName = value;
    }

    /**
     * Gets the value of the areaStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAreaStatus() {
        return areaStatus;
    }

    /**
     * Sets the value of the areaStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAreaStatus(String value) {
        this.areaStatus = value;
    }

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
     * Gets the value of the overview property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOverview() {
        return overview;
    }

    /**
     * Sets the value of the overview property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOverview(String value) {
        this.overview = value;
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
     * Gets the value of the specialNotes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialNotes() {
        return specialNotes;
    }

    /**
     * Sets the value of the specialNotes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialNotes(String value) {
        this.specialNotes = value;
    }

    /**
     * Gets the value of the languages property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguages() {
        return languages;
    }

    /**
     * Sets the value of the languages property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguages(String value) {
        this.languages = value;
    }

}
