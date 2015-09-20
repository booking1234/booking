
package net.cbtltd.rest.homeaway.feedparser.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * A Unit is an advertisement for a single unit of a vacation rental property. The Unit describes a single rentable unit of a vacation rental property - details of the property in general, such as location, are contained in the Listing that owns the Unit.
 * 
 * 
 * <p>Java class for UnitWSD complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UnitWSD">
 *   &lt;complexContent>
 *     &lt;extension base="{}Entity">
 *       &lt;sequence>
 *         &lt;element name="externalId">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="active" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="area" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="areaUnit" type="{}AreaUnit" minOccurs="0"/>
 *         &lt;element name="bathroomDetails" type="{}Note" minOccurs="0"/>
 *         &lt;element name="bathrooms">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="bathroom" type="{}Bathroom" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="bedroomDetails" type="{}Note" minOccurs="0"/>
 *         &lt;element name="bedrooms">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="bedroom" type="{}Bedroom" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="description" type="{}Note" minOccurs="0"/>
 *         &lt;element name="diningSeating" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="featuresDescription" type="{}Note" minOccurs="0"/>
 *         &lt;element name="featureValues" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="featureValue" type="{}UnitFeatureValue" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="loungeSeating" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="maxSleep" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="maxSleepInBeds" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="propertyType" type="{}PropertyType"/>
 *         &lt;element name="unitMonetaryInformation" type="{}UnitMonetaryInformation"/>
 *         &lt;element name="unitName" type="{}Note" minOccurs="0"/>
 *         &lt;element ref="{}ratePeriods" minOccurs="0"/>
 *         &lt;element ref="{}reservations" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnitWSD", propOrder = {
    "externalId",
    "active",
    "area",
    "areaUnit",
    "bathroomDetails",
    "bathrooms",
    "bedroomDetails",
    "bedrooms",
    "description",
    "diningSeating",
    "featuresDescription",
    "featureValues",
    "loungeSeating",
    "maxSleep",
    "maxSleepInBeds",
    "propertyType",
    "unitMonetaryInformation",
    "unitName",
    "ratePeriods",
    "reservations"
})
@XmlSeeAlso({
    Unit.class
})
public class UnitWSD
    extends Entity
{

    @XmlElement(required = true)
    protected String externalId;
    protected Boolean active;
    protected Integer area;
    protected AreaUnit areaUnit;
    protected Note bathroomDetails;
    @XmlElement(required = true)
    protected UnitWSD.Bathrooms bathrooms;
    protected Note bedroomDetails;
    @XmlElement(required = true)
    protected UnitWSD.Bedrooms bedrooms;
    protected Note description;
    protected Integer diningSeating;
    protected Note featuresDescription;
    protected UnitWSD.FeatureValues featureValues;
    protected Integer loungeSeating;
    protected int maxSleep;
    protected Integer maxSleepInBeds;
    @XmlElement(required = true)
    protected PropertyType propertyType;
    @XmlElement(required = true)
    protected UnitMonetaryInformation unitMonetaryInformation;
    protected Note unitName;
    protected RatePeriods ratePeriods;
    protected Reservations reservations;

    /**
     * Gets the value of the externalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalId() {
        return externalId;
    }

    /**
     * Sets the value of the externalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalId(String value) {
        this.externalId = value;
    }

    /**
     * Gets the value of the active property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isActive() {
        return active;
    }

    /**
     * Sets the value of the active property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setActive(Boolean value) {
        this.active = value;
    }

    /**
     * Gets the value of the area property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getArea() {
        return area;
    }

    /**
     * Sets the value of the area property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setArea(Integer value) {
        this.area = value;
    }

    /**
     * Gets the value of the areaUnit property.
     * 
     * @return
     *     possible object is
     *     {@link AreaUnit }
     *     
     */
    public AreaUnit getAreaUnit() {
        return areaUnit;
    }

    /**
     * Sets the value of the areaUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link AreaUnit }
     *     
     */
    public void setAreaUnit(AreaUnit value) {
        this.areaUnit = value;
    }

    /**
     * Gets the value of the bathroomDetails property.
     * 
     * @return
     *     possible object is
     *     {@link Note }
     *     
     */
    public Note getBathroomDetails() {
        return bathroomDetails;
    }

    /**
     * Sets the value of the bathroomDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link Note }
     *     
     */
    public void setBathroomDetails(Note value) {
        this.bathroomDetails = value;
    }

    /**
     * Gets the value of the bathrooms property.
     * 
     * @return
     *     possible object is
     *     {@link UnitWSD.Bathrooms }
     *     
     */
    public UnitWSD.Bathrooms getBathrooms() {
        return bathrooms;
    }

    /**
     * Sets the value of the bathrooms property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitWSD.Bathrooms }
     *     
     */
    public void setBathrooms(UnitWSD.Bathrooms value) {
        this.bathrooms = value;
    }

    /**
     * Gets the value of the bedroomDetails property.
     * 
     * @return
     *     possible object is
     *     {@link Note }
     *     
     */
    public Note getBedroomDetails() {
        return bedroomDetails;
    }

    /**
     * Sets the value of the bedroomDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link Note }
     *     
     */
    public void setBedroomDetails(Note value) {
        this.bedroomDetails = value;
    }

    /**
     * Gets the value of the bedrooms property.
     * 
     * @return
     *     possible object is
     *     {@link UnitWSD.Bedrooms }
     *     
     */
    public UnitWSD.Bedrooms getBedrooms() {
        return bedrooms;
    }

    /**
     * Sets the value of the bedrooms property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitWSD.Bedrooms }
     *     
     */
    public void setBedrooms(UnitWSD.Bedrooms value) {
        this.bedrooms = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link Note }
     *     
     */
    public Note getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link Note }
     *     
     */
    public void setDescription(Note value) {
        this.description = value;
    }

    /**
     * Gets the value of the diningSeating property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDiningSeating() {
        return diningSeating;
    }

    /**
     * Sets the value of the diningSeating property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDiningSeating(Integer value) {
        this.diningSeating = value;
    }

    /**
     * Gets the value of the featuresDescription property.
     * 
     * @return
     *     possible object is
     *     {@link Note }
     *     
     */
    public Note getFeaturesDescription() {
        return featuresDescription;
    }

    /**
     * Sets the value of the featuresDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link Note }
     *     
     */
    public void setFeaturesDescription(Note value) {
        this.featuresDescription = value;
    }

    /**
     * Gets the value of the featureValues property.
     * 
     * @return
     *     possible object is
     *     {@link UnitWSD.FeatureValues }
     *     
     */
    public UnitWSD.FeatureValues getFeatureValues() {
        return featureValues;
    }

    /**
     * Sets the value of the featureValues property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitWSD.FeatureValues }
     *     
     */
    public void setFeatureValues(UnitWSD.FeatureValues value) {
        this.featureValues = value;
    }

    /**
     * Gets the value of the loungeSeating property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLoungeSeating() {
        return loungeSeating;
    }

    /**
     * Sets the value of the loungeSeating property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLoungeSeating(Integer value) {
        this.loungeSeating = value;
    }

    /**
     * Gets the value of the maxSleep property.
     * 
     */
    public int getMaxSleep() {
        return maxSleep;
    }

    /**
     * Sets the value of the maxSleep property.
     * 
     */
    public void setMaxSleep(int value) {
        this.maxSleep = value;
    }

    /**
     * Gets the value of the maxSleepInBeds property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxSleepInBeds() {
        return maxSleepInBeds;
    }

    /**
     * Sets the value of the maxSleepInBeds property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxSleepInBeds(Integer value) {
        this.maxSleepInBeds = value;
    }

    /**
     * Gets the value of the propertyType property.
     * 
     * @return
     *     possible object is
     *     {@link PropertyType }
     *     
     */
    public PropertyType getPropertyType() {
        return propertyType;
    }

    /**
     * Sets the value of the propertyType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertyType }
     *     
     */
    public void setPropertyType(PropertyType value) {
        this.propertyType = value;
    }

    /**
     * Gets the value of the unitMonetaryInformation property.
     * 
     * @return
     *     possible object is
     *     {@link UnitMonetaryInformation }
     *     
     */
    public UnitMonetaryInformation getUnitMonetaryInformation() {
        return unitMonetaryInformation;
    }

    /**
     * Sets the value of the unitMonetaryInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitMonetaryInformation }
     *     
     */
    public void setUnitMonetaryInformation(UnitMonetaryInformation value) {
        this.unitMonetaryInformation = value;
    }

    /**
     * Gets the value of the unitName property.
     * 
     * @return
     *     possible object is
     *     {@link Note }
     *     
     */
    public Note getUnitName() {
        return unitName;
    }

    /**
     * Sets the value of the unitName property.
     * 
     * @param value
     *     allowed object is
     *     {@link Note }
     *     
     */
    public void setUnitName(Note value) {
        this.unitName = value;
    }

    /**
     * Gets the value of the ratePeriods property.
     * 
     * @return
     *     possible object is
     *     {@link RatePeriods }
     *     
     */
    public RatePeriods getRatePeriods() {
        return ratePeriods;
    }

    /**
     * Sets the value of the ratePeriods property.
     * 
     * @param value
     *     allowed object is
     *     {@link RatePeriods }
     *     
     */
    public void setRatePeriods(RatePeriods value) {
        this.ratePeriods = value;
    }

    /**
     * Gets the value of the reservations property.
     * 
     * @return
     *     possible object is
     *     {@link Reservations }
     *     
     */
    public Reservations getReservations() {
        return reservations;
    }

    /**
     * Sets the value of the reservations property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reservations }
     *     
     */
    public void setReservations(Reservations value) {
        this.reservations = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="bathroom" type="{}Bathroom" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "bathroom"
    })
    public static class Bathrooms {

        @XmlElement(required = true)
        protected List<Bathroom> bathroom;

        /**
         * Gets the value of the bathroom property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the bathroom property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBathroom().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Bathroom }
         * 
         * 
         */
        public List<Bathroom> getBathroom() {
            if (bathroom == null) {
                bathroom = new ArrayList<Bathroom>();
            }
            return this.bathroom;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="bedroom" type="{}Bedroom" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "bedroom"
    })
    public static class Bedrooms {

        @XmlElement(required = true)
        protected List<Bedroom> bedroom;

        /**
         * Gets the value of the bedroom property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the bedroom property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBedroom().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Bedroom }
         * 
         * 
         */
        public List<Bedroom> getBedroom() {
            if (bedroom == null) {
                bedroom = new ArrayList<Bedroom>();
            }
            return this.bedroom;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="featureValue" type="{}UnitFeatureValue" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "featureValue"
    })
    public static class FeatureValues {

        protected List<UnitFeatureValue> featureValue;

        /**
         * Gets the value of the featureValue property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the featureValue property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFeatureValue().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link UnitFeatureValue }
         * 
         * 
         */
        public List<UnitFeatureValue> getFeatureValue() {
            if (featureValue == null) {
                featureValue = new ArrayList<UnitFeatureValue>();
            }
            return this.featureValue;
        }

    }

}
