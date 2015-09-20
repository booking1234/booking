
package net.cbtltd.rest.barefoot.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Property complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Property">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Addressid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Bathrooms" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Bedrooms" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Deadline" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Extdescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Imagepath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InternetDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Keyboardid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Keywords" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Maxprice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Minprice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Mindays" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Occupancy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PropAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PropAddressNew" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PropertyID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UnitComments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VideoLink" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Amenity" type="{http://www.barefoot.com/Services/}ArrayOfAmenities" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Property", propOrder = {
    "addressid",
    "bathrooms",
    "bedrooms",
    "deadline",
    "description",
    "extdescription",
    "imagepath",
    "internetDescription",
    "keyboardid",
    "keywords",
    "maxprice",
    "minprice",
    "mindays",
    "name",
    "occupancy",
    "propAddress",
    "propAddressNew",
    "propertyID",
    "status",
    "unitComments",
    "videoLink",
    "amenity"
})
public class Property {

    @XmlElement(name = "Addressid")
    protected String addressid;
    @XmlElement(name = "Bathrooms")
    protected String bathrooms;
    @XmlElement(name = "Bedrooms")
    protected String bedrooms;
    @XmlElement(name = "Deadline")
    protected String deadline;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Extdescription")
    protected String extdescription;
    @XmlElement(name = "Imagepath")
    protected String imagepath;
    @XmlElement(name = "InternetDescription")
    protected String internetDescription;
    @XmlElement(name = "Keyboardid")
    protected String keyboardid;
    @XmlElement(name = "Keywords")
    protected String keywords;
    @XmlElement(name = "Maxprice")
    protected String maxprice;
    @XmlElement(name = "Minprice")
    protected String minprice;
    @XmlElement(name = "Mindays")
    protected String mindays;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Occupancy")
    protected String occupancy;
    @XmlElement(name = "PropAddress")
    protected String propAddress;
    @XmlElement(name = "PropAddressNew")
    protected String propAddressNew;
    @XmlElement(name = "PropertyID")
    protected String propertyID;
    @XmlElement(name = "Status")
    protected String status;
    @XmlElement(name = "UnitComments")
    protected String unitComments;
    @XmlElement(name = "VideoLink")
    protected String videoLink;
    @XmlElement(name = "Amenity")
    protected ArrayOfAmenities amenity;

    /**
     * Gets the value of the addressid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressid() {
        return addressid;
    }

    /**
     * Sets the value of the addressid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressid(String value) {
        this.addressid = value;
    }

    /**
     * Gets the value of the bathrooms property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBathrooms() {
        return bathrooms;
    }

    /**
     * Sets the value of the bathrooms property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBathrooms(String value) {
        this.bathrooms = value;
    }

    /**
     * Gets the value of the bedrooms property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBedrooms() {
        return bedrooms;
    }

    /**
     * Sets the value of the bedrooms property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBedrooms(String value) {
        this.bedrooms = value;
    }

    /**
     * Gets the value of the deadline property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeadline() {
        return deadline;
    }

    /**
     * Sets the value of the deadline property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeadline(String value) {
        this.deadline = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the extdescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtdescription() {
        return extdescription;
    }

    /**
     * Sets the value of the extdescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtdescription(String value) {
        this.extdescription = value;
    }

    /**
     * Gets the value of the imagepath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImagepath() {
        return imagepath;
    }

    /**
     * Sets the value of the imagepath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImagepath(String value) {
        this.imagepath = value;
    }

    /**
     * Gets the value of the internetDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternetDescription() {
        return internetDescription;
    }

    /**
     * Sets the value of the internetDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternetDescription(String value) {
        this.internetDescription = value;
    }

    /**
     * Gets the value of the keyboardid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeyboardid() {
        return keyboardid;
    }

    /**
     * Sets the value of the keyboardid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeyboardid(String value) {
        this.keyboardid = value;
    }

    /**
     * Gets the value of the keywords property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * Sets the value of the keywords property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeywords(String value) {
        this.keywords = value;
    }

    /**
     * Gets the value of the maxprice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxprice() {
        return maxprice;
    }

    /**
     * Sets the value of the maxprice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxprice(String value) {
        this.maxprice = value;
    }

    /**
     * Gets the value of the minprice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinprice() {
        return minprice;
    }

    /**
     * Sets the value of the minprice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinprice(String value) {
        this.minprice = value;
    }

    /**
     * Gets the value of the mindays property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMindays() {
        return mindays;
    }

    /**
     * Sets the value of the mindays property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMindays(String value) {
        this.mindays = value;
    }

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
     * Gets the value of the occupancy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOccupancy() {
        return occupancy;
    }

    /**
     * Sets the value of the occupancy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOccupancy(String value) {
        this.occupancy = value;
    }

    /**
     * Gets the value of the propAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPropAddress() {
        return propAddress;
    }

    /**
     * Sets the value of the propAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPropAddress(String value) {
        this.propAddress = value;
    }

    /**
     * Gets the value of the propAddressNew property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPropAddressNew() {
        return propAddressNew;
    }

    /**
     * Sets the value of the propAddressNew property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPropAddressNew(String value) {
        this.propAddressNew = value;
    }

    /**
     * Gets the value of the propertyID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPropertyID() {
        return propertyID;
    }

    /**
     * Sets the value of the propertyID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPropertyID(String value) {
        this.propertyID = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the unitComments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitComments() {
        return unitComments;
    }

    /**
     * Sets the value of the unitComments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitComments(String value) {
        this.unitComments = value;
    }

    /**
     * Gets the value of the videoLink property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVideoLink() {
        return videoLink;
    }

    /**
     * Sets the value of the videoLink property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVideoLink(String value) {
        this.videoLink = value;
    }

    /**
     * Gets the value of the amenity property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAmenities }
     *     
     */
    public ArrayOfAmenities getAmenity() {
        return amenity;
    }

    /**
     * Sets the value of the amenity property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAmenities }
     *     
     */
    public void setAmenity(ArrayOfAmenities value) {
        this.amenity = value;
    }

}
