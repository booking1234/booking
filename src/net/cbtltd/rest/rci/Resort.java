
package net.cbtltd.rest.rci;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resort complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resort">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resortname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contact" type="{}resort-contact-info"/>
 *         &lt;element name="highlights" type="{}resort-highlights-info" minOccurs="0"/>
 *         &lt;element name="strata" type="{}strata" minOccurs="0"/>
 *         &lt;element name="all-inclusive-code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="all-inclusive" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="frontDesk" type="{}resort-front-desk-info" minOccurs="0"/>
 *         &lt;element name="mapOfResort" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="numberOfFloors" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="recentlyRenovated" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="securityFeatures" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="securityFeature" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="adultOnly" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orientation" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="feature" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="petsAllowed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="additionalInfo-helpYouFurther" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fees" type="{}resort-fee-info" minOccurs="0"/>
 *         &lt;element name="headLine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="air-conditioned-public-areas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="electricService" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="voltage" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="holiday-horizons" type="{}holiday-horizons" minOccurs="0"/>
 *         &lt;element name="checkInCheckOutInfo" type="{}resort-check-in-check-out-info"/>
 *         &lt;element name="location" type="{}resort-location-info"/>
 *         &lt;element name="member-reviews-info" type="{}member-reviews-info" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resort", propOrder = {
    "resortname",
    "contact",
    "highlights",
    "strata",
    "allInclusiveCode",
    "allInclusive",
    "frontDesk",
    "mapOfResort",
    "numberOfFloors",
    "recentlyRenovated",
    "securityFeatures",
    "adultOnly",
    "orientation",
    "petsAllowed",
    "additionalInfoHelpYouFurther",
    "fees",
    "headLine",
    "airConditionedPublicAreas",
    "electricService",
    "holidayHorizons",
    "checkInCheckOutInfo",
    "location",
    "memberReviewsInfo"
})
public class Resort {

    @XmlElement(required = true)
    protected String resortname;
    @XmlElement(required = true)
    protected ResortContactInfo contact;
    protected ResortHighlightsInfo highlights;
    protected Strata strata;
    @XmlElement(name = "all-inclusive-code")
    protected String allInclusiveCode;
    @XmlElement(name = "all-inclusive")
    protected String allInclusive;
    protected ResortFrontDeskInfo frontDesk;
    @XmlSchemaType(name = "anyURI")
    protected String mapOfResort;
    protected Integer numberOfFloors;
    protected String recentlyRenovated;
    protected Resort.SecurityFeatures securityFeatures;
    protected String adultOnly;
    protected Resort.Orientation orientation;
    protected String petsAllowed;
    @XmlElement(name = "additionalInfo-helpYouFurther")
    protected String additionalInfoHelpYouFurther;
    protected ResortFeeInfo fees;
    protected String headLine;
    @XmlElement(name = "air-conditioned-public-areas")
    protected String airConditionedPublicAreas;
    protected Resort.ElectricService electricService;
    @XmlElement(name = "holiday-horizons")
    protected HolidayHorizons holidayHorizons;
    @XmlElement(required = true)
    protected ResortCheckInCheckOutInfo checkInCheckOutInfo;
    @XmlElement(required = true)
    protected ResortLocationInfo location;
    @XmlElement(name = "member-reviews-info")
    protected MemberReviewsInfo memberReviewsInfo;

    /**
     * Gets the value of the resortname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResortname() {
        return resortname;
    }

    /**
     * Sets the value of the resortname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResortname(String value) {
        this.resortname = value;
    }

    /**
     * Gets the value of the contact property.
     * 
     * @return
     *     possible object is
     *     {@link ResortContactInfo }
     *     
     */
    public ResortContactInfo getContact() {
        return contact;
    }

    /**
     * Sets the value of the contact property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortContactInfo }
     *     
     */
    public void setContact(ResortContactInfo value) {
        this.contact = value;
    }

    /**
     * Gets the value of the highlights property.
     * 
     * @return
     *     possible object is
     *     {@link ResortHighlightsInfo }
     *     
     */
    public ResortHighlightsInfo getHighlights() {
        return highlights;
    }

    /**
     * Sets the value of the highlights property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortHighlightsInfo }
     *     
     */
    public void setHighlights(ResortHighlightsInfo value) {
        this.highlights = value;
    }

    /**
     * Gets the value of the strata property.
     * 
     * @return
     *     possible object is
     *     {@link Strata }
     *     
     */
    public Strata getStrata() {
        return strata;
    }

    /**
     * Sets the value of the strata property.
     * 
     * @param value
     *     allowed object is
     *     {@link Strata }
     *     
     */
    public void setStrata(Strata value) {
        this.strata = value;
    }

    /**
     * Gets the value of the allInclusiveCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAllInclusiveCode() {
        return allInclusiveCode;
    }

    /**
     * Sets the value of the allInclusiveCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAllInclusiveCode(String value) {
        this.allInclusiveCode = value;
    }

    /**
     * Gets the value of the allInclusive property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAllInclusive() {
        return allInclusive;
    }

    /**
     * Sets the value of the allInclusive property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAllInclusive(String value) {
        this.allInclusive = value;
    }

    /**
     * Gets the value of the frontDesk property.
     * 
     * @return
     *     possible object is
     *     {@link ResortFrontDeskInfo }
     *     
     */
    public ResortFrontDeskInfo getFrontDesk() {
        return frontDesk;
    }

    /**
     * Sets the value of the frontDesk property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortFrontDeskInfo }
     *     
     */
    public void setFrontDesk(ResortFrontDeskInfo value) {
        this.frontDesk = value;
    }

    /**
     * Gets the value of the mapOfResort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMapOfResort() {
        return mapOfResort;
    }

    /**
     * Sets the value of the mapOfResort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMapOfResort(String value) {
        this.mapOfResort = value;
    }

    /**
     * Gets the value of the numberOfFloors property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumberOfFloors() {
        return numberOfFloors;
    }

    /**
     * Sets the value of the numberOfFloors property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumberOfFloors(Integer value) {
        this.numberOfFloors = value;
    }

    /**
     * Gets the value of the recentlyRenovated property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecentlyRenovated() {
        return recentlyRenovated;
    }

    /**
     * Sets the value of the recentlyRenovated property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecentlyRenovated(String value) {
        this.recentlyRenovated = value;
    }

    /**
     * Gets the value of the securityFeatures property.
     * 
     * @return
     *     possible object is
     *     {@link Resort.SecurityFeatures }
     *     
     */
    public Resort.SecurityFeatures getSecurityFeatures() {
        return securityFeatures;
    }

    /**
     * Sets the value of the securityFeatures property.
     * 
     * @param value
     *     allowed object is
     *     {@link Resort.SecurityFeatures }
     *     
     */
    public void setSecurityFeatures(Resort.SecurityFeatures value) {
        this.securityFeatures = value;
    }

    /**
     * Gets the value of the adultOnly property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdultOnly() {
        return adultOnly;
    }

    /**
     * Sets the value of the adultOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdultOnly(String value) {
        this.adultOnly = value;
    }

    /**
     * Gets the value of the orientation property.
     * 
     * @return
     *     possible object is
     *     {@link Resort.Orientation }
     *     
     */
    public Resort.Orientation getOrientation() {
        return orientation;
    }

    /**
     * Sets the value of the orientation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Resort.Orientation }
     *     
     */
    public void setOrientation(Resort.Orientation value) {
        this.orientation = value;
    }

    /**
     * Gets the value of the petsAllowed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPetsAllowed() {
        return petsAllowed;
    }

    /**
     * Sets the value of the petsAllowed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPetsAllowed(String value) {
        this.petsAllowed = value;
    }

    /**
     * Gets the value of the additionalInfoHelpYouFurther property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalInfoHelpYouFurther() {
        return additionalInfoHelpYouFurther;
    }

    /**
     * Sets the value of the additionalInfoHelpYouFurther property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalInfoHelpYouFurther(String value) {
        this.additionalInfoHelpYouFurther = value;
    }

    /**
     * Gets the value of the fees property.
     * 
     * @return
     *     possible object is
     *     {@link ResortFeeInfo }
     *     
     */
    public ResortFeeInfo getFees() {
        return fees;
    }

    /**
     * Sets the value of the fees property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortFeeInfo }
     *     
     */
    public void setFees(ResortFeeInfo value) {
        this.fees = value;
    }

    /**
     * Gets the value of the headLine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHeadLine() {
        return headLine;
    }

    /**
     * Sets the value of the headLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHeadLine(String value) {
        this.headLine = value;
    }

    /**
     * Gets the value of the airConditionedPublicAreas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAirConditionedPublicAreas() {
        return airConditionedPublicAreas;
    }

    /**
     * Sets the value of the airConditionedPublicAreas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAirConditionedPublicAreas(String value) {
        this.airConditionedPublicAreas = value;
    }

    /**
     * Gets the value of the electricService property.
     * 
     * @return
     *     possible object is
     *     {@link Resort.ElectricService }
     *     
     */
    public Resort.ElectricService getElectricService() {
        return electricService;
    }

    /**
     * Sets the value of the electricService property.
     * 
     * @param value
     *     allowed object is
     *     {@link Resort.ElectricService }
     *     
     */
    public void setElectricService(Resort.ElectricService value) {
        this.electricService = value;
    }

    /**
     * Gets the value of the holidayHorizons property.
     * 
     * @return
     *     possible object is
     *     {@link HolidayHorizons }
     *     
     */
    public HolidayHorizons getHolidayHorizons() {
        return holidayHorizons;
    }

    /**
     * Sets the value of the holidayHorizons property.
     * 
     * @param value
     *     allowed object is
     *     {@link HolidayHorizons }
     *     
     */
    public void setHolidayHorizons(HolidayHorizons value) {
        this.holidayHorizons = value;
    }

    /**
     * Gets the value of the checkInCheckOutInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ResortCheckInCheckOutInfo }
     *     
     */
    public ResortCheckInCheckOutInfo getCheckInCheckOutInfo() {
        return checkInCheckOutInfo;
    }

    /**
     * Sets the value of the checkInCheckOutInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortCheckInCheckOutInfo }
     *     
     */
    public void setCheckInCheckOutInfo(ResortCheckInCheckOutInfo value) {
        this.checkInCheckOutInfo = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link ResortLocationInfo }
     *     
     */
    public ResortLocationInfo getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortLocationInfo }
     *     
     */
    public void setLocation(ResortLocationInfo value) {
        this.location = value;
    }

    /**
     * Gets the value of the memberReviewsInfo property.
     * 
     * @return
     *     possible object is
     *     {@link MemberReviewsInfo }
     *     
     */
    public MemberReviewsInfo getMemberReviewsInfo() {
        return memberReviewsInfo;
    }

    /**
     * Sets the value of the memberReviewsInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link MemberReviewsInfo }
     *     
     */
    public void setMemberReviewsInfo(MemberReviewsInfo value) {
        this.memberReviewsInfo = value;
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
     *         &lt;element name="voltage" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
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
        "voltage"
    })
    public static class ElectricService {

        protected List<String> voltage;

        /**
         * Gets the value of the voltage property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the voltage property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getVoltage().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getVoltage() {
            if (voltage == null) {
                voltage = new ArrayList<String>();
            }
            return this.voltage;
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
     *         &lt;element name="feature" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
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
        "feature"
    })
    public static class Orientation {

        protected List<String> feature;

        /**
         * Gets the value of the feature property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the feature property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFeature().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getFeature() {
            if (feature == null) {
                feature = new ArrayList<String>();
            }
            return this.feature;
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
     *         &lt;element name="securityFeature" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
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
        "securityFeature"
    })
    public static class SecurityFeatures {

        @XmlElement(required = true)
        protected List<String> securityFeature;

        /**
         * Gets the value of the securityFeature property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the securityFeature property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSecurityFeature().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getSecurityFeature() {
            if (securityFeature == null) {
                securityFeature = new ArrayList<String>();
            }
            return this.securityFeature;
        }

    }

}
