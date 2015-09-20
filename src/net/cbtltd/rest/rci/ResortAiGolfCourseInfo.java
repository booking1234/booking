
package net.cbtltd.rest.rci;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resort-ai-golf-course-info complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resort-ai-golf-course-info">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rating" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="courseRecord" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="designedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="proximity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="distanceInKms" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="distanceInMiles" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="established" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="feeIncluded" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lengthInYards" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="map" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="numberOfHoles" type="{}multivalueType" minOccurs="0"/>
 *         &lt;element name="par" type="{}multivalueType" minOccurs="0"/>
 *         &lt;element name="pricingDiscount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="teeTimes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tournamentsHosted" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="website" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resort-ai-golf-course-info", propOrder = {
    "name",
    "rating",
    "courseRecord",
    "designedBy",
    "proximity",
    "distanceInKms",
    "distanceInMiles",
    "established",
    "feeIncluded",
    "lengthInYards",
    "map",
    "numberOfHoles",
    "par",
    "pricingDiscount",
    "teeTimes",
    "tournamentsHosted",
    "type",
    "website"
})
public class ResortAiGolfCourseInfo {

    protected String name;
    protected String rating;
    protected String courseRecord;
    protected String designedBy;
    protected String proximity;
    protected Double distanceInKms;
    protected Double distanceInMiles;
    protected Integer established;
    protected String feeIncluded;
    protected String lengthInYards;
    @XmlSchemaType(name = "anyURI")
    protected String map;
    protected MultivalueType numberOfHoles;
    protected MultivalueType par;
    protected String pricingDiscount;
    protected String teeTimes;
    protected String tournamentsHosted;
    protected String type;
    @XmlSchemaType(name = "anyURI")
    protected String website;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the rating property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRating() {
        return rating;
    }

    /**
     * Sets the value of the rating property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRating(String value) {
        this.rating = value;
    }

    /**
     * Gets the value of the courseRecord property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCourseRecord() {
        return courseRecord;
    }

    /**
     * Sets the value of the courseRecord property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCourseRecord(String value) {
        this.courseRecord = value;
    }

    /**
     * Gets the value of the designedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesignedBy() {
        return designedBy;
    }

    /**
     * Sets the value of the designedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesignedBy(String value) {
        this.designedBy = value;
    }

    /**
     * Gets the value of the proximity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProximity() {
        return proximity;
    }

    /**
     * Sets the value of the proximity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProximity(String value) {
        this.proximity = value;
    }

    /**
     * Gets the value of the distanceInKms property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDistanceInKms() {
        return distanceInKms;
    }

    /**
     * Sets the value of the distanceInKms property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDistanceInKms(Double value) {
        this.distanceInKms = value;
    }

    /**
     * Gets the value of the distanceInMiles property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDistanceInMiles() {
        return distanceInMiles;
    }

    /**
     * Sets the value of the distanceInMiles property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDistanceInMiles(Double value) {
        this.distanceInMiles = value;
    }

    /**
     * Gets the value of the established property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEstablished() {
        return established;
    }

    /**
     * Sets the value of the established property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEstablished(Integer value) {
        this.established = value;
    }

    /**
     * Gets the value of the feeIncluded property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFeeIncluded() {
        return feeIncluded;
    }

    /**
     * Sets the value of the feeIncluded property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFeeIncluded(String value) {
        this.feeIncluded = value;
    }

    /**
     * Gets the value of the lengthInYards property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLengthInYards() {
        return lengthInYards;
    }

    /**
     * Sets the value of the lengthInYards property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLengthInYards(String value) {
        this.lengthInYards = value;
    }

    /**
     * Gets the value of the map property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMap() {
        return map;
    }

    /**
     * Sets the value of the map property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMap(String value) {
        this.map = value;
    }

    /**
     * Gets the value of the numberOfHoles property.
     * 
     * @return
     *     possible object is
     *     {@link MultivalueType }
     *     
     */
    public MultivalueType getNumberOfHoles() {
        return numberOfHoles;
    }

    /**
     * Sets the value of the numberOfHoles property.
     * 
     * @param value
     *     allowed object is
     *     {@link MultivalueType }
     *     
     */
    public void setNumberOfHoles(MultivalueType value) {
        this.numberOfHoles = value;
    }

    /**
     * Gets the value of the par property.
     * 
     * @return
     *     possible object is
     *     {@link MultivalueType }
     *     
     */
    public MultivalueType getPar() {
        return par;
    }

    /**
     * Sets the value of the par property.
     * 
     * @param value
     *     allowed object is
     *     {@link MultivalueType }
     *     
     */
    public void setPar(MultivalueType value) {
        this.par = value;
    }

    /**
     * Gets the value of the pricingDiscount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPricingDiscount() {
        return pricingDiscount;
    }

    /**
     * Sets the value of the pricingDiscount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPricingDiscount(String value) {
        this.pricingDiscount = value;
    }

    /**
     * Gets the value of the teeTimes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeeTimes() {
        return teeTimes;
    }

    /**
     * Sets the value of the teeTimes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeeTimes(String value) {
        this.teeTimes = value;
    }

    /**
     * Gets the value of the tournamentsHosted property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTournamentsHosted() {
        return tournamentsHosted;
    }

    /**
     * Sets the value of the tournamentsHosted property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTournamentsHosted(String value) {
        this.tournamentsHosted = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the website property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Sets the value of the website property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebsite(String value) {
        this.website = value;
    }

}
