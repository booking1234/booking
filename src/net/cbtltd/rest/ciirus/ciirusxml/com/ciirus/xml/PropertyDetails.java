
package net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PropertyDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PropertyDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PropertyID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ManagementCompanyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MainImageURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MCPropertyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WebsitePropertyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DescriptionSetID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Community" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Bedrooms" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Sleeps" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Bathrooms" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="XCO" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="YCO" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="Bullet1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Bullet2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Bullet3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Bullet4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Bullet5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CurrencySymbol" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CurrencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HasPool" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="HasSpa" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="GamesRoom" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="PropertyType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PropertyClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HostMCUserID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="QuoteExcludingTax" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="QuoteIncludingTax" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="LessThanMinimumNightsStay" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="MinimumNightsStay" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CommunalPool" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CommunalGym" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ConservationView" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="WaterView" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="GolfView" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="GolfIncluded" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="WiFi" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Internet" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="WiredInternetAccess" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="PetsAllowed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Stroller" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Crib" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="HighChair" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="AirCon" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ElectricFireplace" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="GasFireplace" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="WoodBurningFireplace" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="FreeCalls" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="FreeSolarHeatedPool" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="PacknPlay" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="PrivacyFence" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SouthFacingPool" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="BBQ" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="AirHockeyTable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="BigScreenTV" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="CDPlayer" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="DVDPlayer" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="FoosballTable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Grill" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="HairDryer" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="TVInEveryBedroom" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="VCR" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IndoorJacuzzi" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IndoorHotTub" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="OutdoorHotTub" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="PavedParking" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="PoolAccess" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="RockingChairs" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="VideoGames" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Fishing" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="BedroomConfiguration" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Address1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Address2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="City" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="County" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="State" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Zip" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Country" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Telephone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExtraBed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SofaBed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Twin_SingleBeds" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FullBeds" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="QueenBeds" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="KingBeds" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PropertyDetails", propOrder = {
    "propertyID",
    "managementCompanyName",
    "mainImageURL",
    "mcPropertyName",
    "websitePropertyName",
    "descriptionSetID",
    "community",
    "bedrooms",
    "sleeps",
    "bathrooms",
    "xco",
    "yco",
    "bullet1",
    "bullet2",
    "bullet3",
    "bullet4",
    "bullet5",
    "currencySymbol",
    "currencyCode",
    "hasPool",
    "hasSpa",
    "gamesRoom",
    "propertyType",
    "propertyClass",
    "hostMCUserID",
    "quoteExcludingTax",
    "quoteIncludingTax",
    "lessThanMinimumNightsStay",
    "minimumNightsStay",
    "communalPool",
    "communalGym",
    "conservationView",
    "waterView",
    "golfView",
    "golfIncluded",
    "wiFi",
    "internet",
    "wiredInternetAccess",
    "petsAllowed",
    "stroller",
    "crib",
    "highChair",
    "airCon",
    "electricFireplace",
    "gasFireplace",
    "woodBurningFireplace",
    "freeCalls",
    "freeSolarHeatedPool",
    "packnPlay",
    "privacyFence",
    "southFacingPool",
    "bbq",
    "airHockeyTable",
    "bigScreenTV",
    "cdPlayer",
    "dvdPlayer",
    "foosballTable",
    "grill",
    "hairDryer",
    "tvInEveryBedroom",
    "vcr",
    "indoorJacuzzi",
    "indoorHotTub",
    "outdoorHotTub",
    "pavedParking",
    "poolAccess",
    "rockingChairs",
    "videoGames",
    "fishing",
    "bedroomConfiguration",
    "address1",
    "address2",
    "city",
    "county",
    "state",
    "zip",
    "country",
    "telephone",
    "extraBed",
    "sofaBed",
    "twinSingleBeds",
    "fullBeds",
    "queenBeds",
    "kingBeds"
})
public class PropertyDetails {

    @XmlElement(name = "PropertyID")
    protected int propertyID;
    @XmlElement(name = "ManagementCompanyName")
    protected String managementCompanyName;
    @XmlElement(name = "MainImageURL")
    protected String mainImageURL;
    @XmlElement(name = "MCPropertyName")
    protected String mcPropertyName;
    @XmlElement(name = "WebsitePropertyName")
    protected String websitePropertyName;
    @XmlElement(name = "DescriptionSetID")
    protected int descriptionSetID;
    @XmlElement(name = "Community")
    protected String community;
    @XmlElement(name = "Bedrooms")
    protected int bedrooms;
    @XmlElement(name = "Sleeps")
    protected int sleeps;
    @XmlElement(name = "Bathrooms", required = true)
    protected BigDecimal bathrooms;
    @XmlElement(name = "XCO", required = true)
    protected BigDecimal xco;
    @XmlElement(name = "YCO", required = true)
    protected BigDecimal yco;
    @XmlElement(name = "Bullet1")
    protected String bullet1;
    @XmlElement(name = "Bullet2")
    protected String bullet2;
    @XmlElement(name = "Bullet3")
    protected String bullet3;
    @XmlElement(name = "Bullet4")
    protected String bullet4;
    @XmlElement(name = "Bullet5")
    protected String bullet5;
    @XmlElement(name = "CurrencySymbol")
    protected String currencySymbol;
    @XmlElement(name = "CurrencyCode")
    protected String currencyCode;
    @XmlElement(name = "HasPool")
    protected boolean hasPool;
    @XmlElement(name = "HasSpa")
    protected boolean hasSpa;
    @XmlElement(name = "GamesRoom")
    protected boolean gamesRoom;
    @XmlElement(name = "PropertyType")
    protected String propertyType;
    @XmlElement(name = "PropertyClass")
    protected String propertyClass;
    @XmlElement(name = "HostMCUserID")
    protected int hostMCUserID;
    @XmlElement(name = "QuoteExcludingTax", required = true)
    protected BigDecimal quoteExcludingTax;
    @XmlElement(name = "QuoteIncludingTax", required = true)
    protected BigDecimal quoteIncludingTax;
    @XmlElement(name = "LessThanMinimumNightsStay")
    protected boolean lessThanMinimumNightsStay;
    @XmlElement(name = "MinimumNightsStay")
    protected int minimumNightsStay;
    @XmlElement(name = "CommunalPool")
    protected boolean communalPool;
    @XmlElement(name = "CommunalGym")
    protected boolean communalGym;
    @XmlElement(name = "ConservationView")
    protected boolean conservationView;
    @XmlElement(name = "WaterView")
    protected boolean waterView;
    @XmlElement(name = "GolfView")
    protected boolean golfView;
    @XmlElement(name = "GolfIncluded")
    protected boolean golfIncluded;
    @XmlElement(name = "WiFi")
    protected boolean wiFi;
    @XmlElement(name = "Internet")
    protected boolean internet;
    @XmlElement(name = "WiredInternetAccess")
    protected boolean wiredInternetAccess;
    @XmlElement(name = "PetsAllowed")
    protected boolean petsAllowed;
    @XmlElement(name = "Stroller")
    protected boolean stroller;
    @XmlElement(name = "Crib")
    protected boolean crib;
    @XmlElement(name = "HighChair")
    protected boolean highChair;
    @XmlElement(name = "AirCon")
    protected boolean airCon;
    @XmlElement(name = "ElectricFireplace")
    protected boolean electricFireplace;
    @XmlElement(name = "GasFireplace")
    protected boolean gasFireplace;
    @XmlElement(name = "WoodBurningFireplace")
    protected boolean woodBurningFireplace;
    @XmlElement(name = "FreeCalls")
    protected boolean freeCalls;
    @XmlElement(name = "FreeSolarHeatedPool")
    protected boolean freeSolarHeatedPool;
    @XmlElement(name = "PacknPlay")
    protected boolean packnPlay;
    @XmlElement(name = "PrivacyFence")
    protected boolean privacyFence;
    @XmlElement(name = "SouthFacingPool")
    protected boolean southFacingPool;
    @XmlElement(name = "BBQ")
    protected boolean bbq;
    @XmlElement(name = "AirHockeyTable")
    protected boolean airHockeyTable;
    @XmlElement(name = "BigScreenTV")
    protected boolean bigScreenTV;
    @XmlElement(name = "CDPlayer")
    protected boolean cdPlayer;
    @XmlElement(name = "DVDPlayer")
    protected boolean dvdPlayer;
    @XmlElement(name = "FoosballTable")
    protected boolean foosballTable;
    @XmlElement(name = "Grill")
    protected boolean grill;
    @XmlElement(name = "HairDryer")
    protected boolean hairDryer;
    @XmlElement(name = "TVInEveryBedroom")
    protected boolean tvInEveryBedroom;
    @XmlElement(name = "VCR")
    protected boolean vcr;
    @XmlElement(name = "IndoorJacuzzi")
    protected boolean indoorJacuzzi;
    @XmlElement(name = "IndoorHotTub")
    protected boolean indoorHotTub;
    @XmlElement(name = "OutdoorHotTub")
    protected boolean outdoorHotTub;
    @XmlElement(name = "PavedParking")
    protected boolean pavedParking;
    @XmlElement(name = "PoolAccess")
    protected boolean poolAccess;
    @XmlElement(name = "RockingChairs")
    protected boolean rockingChairs;
    @XmlElement(name = "VideoGames")
    protected boolean videoGames;
    @XmlElement(name = "Fishing")
    protected boolean fishing;
    @XmlElement(name = "BedroomConfiguration")
    protected String bedroomConfiguration;
    @XmlElement(name = "Address1")
    protected String address1;
    @XmlElement(name = "Address2")
    protected String address2;
    @XmlElement(name = "City")
    protected String city;
    @XmlElement(name = "County")
    protected String county;
    @XmlElement(name = "State")
    protected String state;
    @XmlElement(name = "Zip")
    protected String zip;
    @XmlElement(name = "Country")
    protected String country;
    @XmlElement(name = "Telephone")
    protected String telephone;
    @XmlElement(name = "ExtraBed")
    protected boolean extraBed;
    @XmlElement(name = "SofaBed")
    protected boolean sofaBed;
    @XmlElement(name = "Twin_SingleBeds")
    protected int twinSingleBeds;
    @XmlElement(name = "FullBeds")
    protected int fullBeds;
    @XmlElement(name = "QueenBeds")
    protected int queenBeds;
    @XmlElement(name = "KingBeds")
    protected int kingBeds;

    /**
     * Gets the value of the propertyID property.
     * 
     */
    public int getPropertyID() {
        return propertyID;
    }

    /**
     * Sets the value of the propertyID property.
     * 
     */
    public void setPropertyID(int value) {
        this.propertyID = value;
    }

    /**
     * Gets the value of the managementCompanyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManagementCompanyName() {
        return managementCompanyName;
    }

    /**
     * Sets the value of the managementCompanyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManagementCompanyName(String value) {
        this.managementCompanyName = value;
    }

    /**
     * Gets the value of the mainImageURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainImageURL() {
        return mainImageURL;
    }

    /**
     * Sets the value of the mainImageURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainImageURL(String value) {
        this.mainImageURL = value;
    }

    /**
     * Gets the value of the mcPropertyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMCPropertyName() {
        return mcPropertyName;
    }

    /**
     * Sets the value of the mcPropertyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMCPropertyName(String value) {
        this.mcPropertyName = value;
    }

    /**
     * Gets the value of the websitePropertyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebsitePropertyName() {
        return websitePropertyName;
    }

    /**
     * Sets the value of the websitePropertyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebsitePropertyName(String value) {
        this.websitePropertyName = value;
    }

    /**
     * Gets the value of the descriptionSetID property.
     * 
     */
    public int getDescriptionSetID() {
        return descriptionSetID;
    }

    /**
     * Sets the value of the descriptionSetID property.
     * 
     */
    public void setDescriptionSetID(int value) {
        this.descriptionSetID = value;
    }

    /**
     * Gets the value of the community property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommunity() {
        return community;
    }

    /**
     * Sets the value of the community property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommunity(String value) {
        this.community = value;
    }

    /**
     * Gets the value of the bedrooms property.
     * 
     */
    public int getBedrooms() {
        return bedrooms;
    }

    /**
     * Sets the value of the bedrooms property.
     * 
     */
    public void setBedrooms(int value) {
        this.bedrooms = value;
    }

    /**
     * Gets the value of the sleeps property.
     * 
     */
    public int getSleeps() {
        return sleeps;
    }

    /**
     * Sets the value of the sleeps property.
     * 
     */
    public void setSleeps(int value) {
        this.sleeps = value;
    }

    /**
     * Gets the value of the bathrooms property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBathrooms() {
        return bathrooms;
    }

    /**
     * Sets the value of the bathrooms property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBathrooms(BigDecimal value) {
        this.bathrooms = value;
    }

    /**
     * Gets the value of the xco property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getXCO() {
        return xco;
    }

    /**
     * Sets the value of the xco property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setXCO(BigDecimal value) {
        this.xco = value;
    }

    /**
     * Gets the value of the yco property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getYCO() {
        return yco;
    }

    /**
     * Sets the value of the yco property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setYCO(BigDecimal value) {
        this.yco = value;
    }

    /**
     * Gets the value of the bullet1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBullet1() {
        return bullet1;
    }

    /**
     * Sets the value of the bullet1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBullet1(String value) {
        this.bullet1 = value;
    }

    /**
     * Gets the value of the bullet2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBullet2() {
        return bullet2;
    }

    /**
     * Sets the value of the bullet2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBullet2(String value) {
        this.bullet2 = value;
    }

    /**
     * Gets the value of the bullet3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBullet3() {
        return bullet3;
    }

    /**
     * Sets the value of the bullet3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBullet3(String value) {
        this.bullet3 = value;
    }

    /**
     * Gets the value of the bullet4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBullet4() {
        return bullet4;
    }

    /**
     * Sets the value of the bullet4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBullet4(String value) {
        this.bullet4 = value;
    }

    /**
     * Gets the value of the bullet5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBullet5() {
        return bullet5;
    }

    /**
     * Sets the value of the bullet5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBullet5(String value) {
        this.bullet5 = value;
    }

    /**
     * Gets the value of the currencySymbol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrencySymbol() {
        return currencySymbol;
    }

    /**
     * Sets the value of the currencySymbol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrencySymbol(String value) {
        this.currencySymbol = value;
    }

    /**
     * Gets the value of the currencyCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets the value of the currencyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrencyCode(String value) {
        this.currencyCode = value;
    }

    /**
     * Gets the value of the hasPool property.
     * 
     */
    public boolean isHasPool() {
        return hasPool;
    }

    /**
     * Sets the value of the hasPool property.
     * 
     */
    public void setHasPool(boolean value) {
        this.hasPool = value;
    }

    /**
     * Gets the value of the hasSpa property.
     * 
     */
    public boolean isHasSpa() {
        return hasSpa;
    }

    /**
     * Sets the value of the hasSpa property.
     * 
     */
    public void setHasSpa(boolean value) {
        this.hasSpa = value;
    }

    /**
     * Gets the value of the gamesRoom property.
     * 
     */
    public boolean isGamesRoom() {
        return gamesRoom;
    }

    /**
     * Sets the value of the gamesRoom property.
     * 
     */
    public void setGamesRoom(boolean value) {
        this.gamesRoom = value;
    }

    /**
     * Gets the value of the propertyType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPropertyType() {
        return propertyType;
    }

    /**
     * Sets the value of the propertyType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPropertyType(String value) {
        this.propertyType = value;
    }

    /**
     * Gets the value of the propertyClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPropertyClass() {
        return propertyClass;
    }

    /**
     * Sets the value of the propertyClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPropertyClass(String value) {
        this.propertyClass = value;
    }

    /**
     * Gets the value of the hostMCUserID property.
     * 
     */
    public int getHostMCUserID() {
        return hostMCUserID;
    }

    /**
     * Sets the value of the hostMCUserID property.
     * 
     */
    public void setHostMCUserID(int value) {
        this.hostMCUserID = value;
    }

    /**
     * Gets the value of the quoteExcludingTax property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQuoteExcludingTax() {
        return quoteExcludingTax;
    }

    /**
     * Sets the value of the quoteExcludingTax property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQuoteExcludingTax(BigDecimal value) {
        this.quoteExcludingTax = value;
    }

    /**
     * Gets the value of the quoteIncludingTax property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQuoteIncludingTax() {
        return quoteIncludingTax;
    }

    /**
     * Sets the value of the quoteIncludingTax property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQuoteIncludingTax(BigDecimal value) {
        this.quoteIncludingTax = value;
    }

    /**
     * Gets the value of the lessThanMinimumNightsStay property.
     * 
     */
    public boolean isLessThanMinimumNightsStay() {
        return lessThanMinimumNightsStay;
    }

    /**
     * Sets the value of the lessThanMinimumNightsStay property.
     * 
     */
    public void setLessThanMinimumNightsStay(boolean value) {
        this.lessThanMinimumNightsStay = value;
    }

    /**
     * Gets the value of the minimumNightsStay property.
     * 
     */
    public int getMinimumNightsStay() {
        return minimumNightsStay;
    }

    /**
     * Sets the value of the minimumNightsStay property.
     * 
     */
    public void setMinimumNightsStay(int value) {
        this.minimumNightsStay = value;
    }

    /**
     * Gets the value of the communalPool property.
     * 
     */
    public boolean isCommunalPool() {
        return communalPool;
    }

    /**
     * Sets the value of the communalPool property.
     * 
     */
    public void setCommunalPool(boolean value) {
        this.communalPool = value;
    }

    /**
     * Gets the value of the communalGym property.
     * 
     */
    public boolean isCommunalGym() {
        return communalGym;
    }

    /**
     * Sets the value of the communalGym property.
     * 
     */
    public void setCommunalGym(boolean value) {
        this.communalGym = value;
    }

    /**
     * Gets the value of the conservationView property.
     * 
     */
    public boolean isConservationView() {
        return conservationView;
    }

    /**
     * Sets the value of the conservationView property.
     * 
     */
    public void setConservationView(boolean value) {
        this.conservationView = value;
    }

    /**
     * Gets the value of the waterView property.
     * 
     */
    public boolean isWaterView() {
        return waterView;
    }

    /**
     * Sets the value of the waterView property.
     * 
     */
    public void setWaterView(boolean value) {
        this.waterView = value;
    }

    /**
     * Gets the value of the golfView property.
     * 
     */
    public boolean isGolfView() {
        return golfView;
    }

    /**
     * Sets the value of the golfView property.
     * 
     */
    public void setGolfView(boolean value) {
        this.golfView = value;
    }

    /**
     * Gets the value of the golfIncluded property.
     * 
     */
    public boolean isGolfIncluded() {
        return golfIncluded;
    }

    /**
     * Sets the value of the golfIncluded property.
     * 
     */
    public void setGolfIncluded(boolean value) {
        this.golfIncluded = value;
    }

    /**
     * Gets the value of the wiFi property.
     * 
     */
    public boolean isWiFi() {
        return wiFi;
    }

    /**
     * Sets the value of the wiFi property.
     * 
     */
    public void setWiFi(boolean value) {
        this.wiFi = value;
    }

    /**
     * Gets the value of the internet property.
     * 
     */
    public boolean isInternet() {
        return internet;
    }

    /**
     * Sets the value of the internet property.
     * 
     */
    public void setInternet(boolean value) {
        this.internet = value;
    }

    /**
     * Gets the value of the wiredInternetAccess property.
     * 
     */
    public boolean isWiredInternetAccess() {
        return wiredInternetAccess;
    }

    /**
     * Sets the value of the wiredInternetAccess property.
     * 
     */
    public void setWiredInternetAccess(boolean value) {
        this.wiredInternetAccess = value;
    }

    /**
     * Gets the value of the petsAllowed property.
     * 
     */
    public boolean isPetsAllowed() {
        return petsAllowed;
    }

    /**
     * Sets the value of the petsAllowed property.
     * 
     */
    public void setPetsAllowed(boolean value) {
        this.petsAllowed = value;
    }

    /**
     * Gets the value of the stroller property.
     * 
     */
    public boolean isStroller() {
        return stroller;
    }

    /**
     * Sets the value of the stroller property.
     * 
     */
    public void setStroller(boolean value) {
        this.stroller = value;
    }

    /**
     * Gets the value of the crib property.
     * 
     */
    public boolean isCrib() {
        return crib;
    }

    /**
     * Sets the value of the crib property.
     * 
     */
    public void setCrib(boolean value) {
        this.crib = value;
    }

    /**
     * Gets the value of the highChair property.
     * 
     */
    public boolean isHighChair() {
        return highChair;
    }

    /**
     * Sets the value of the highChair property.
     * 
     */
    public void setHighChair(boolean value) {
        this.highChair = value;
    }

    /**
     * Gets the value of the airCon property.
     * 
     */
    public boolean isAirCon() {
        return airCon;
    }

    /**
     * Sets the value of the airCon property.
     * 
     */
    public void setAirCon(boolean value) {
        this.airCon = value;
    }

    /**
     * Gets the value of the electricFireplace property.
     * 
     */
    public boolean isElectricFireplace() {
        return electricFireplace;
    }

    /**
     * Sets the value of the electricFireplace property.
     * 
     */
    public void setElectricFireplace(boolean value) {
        this.electricFireplace = value;
    }

    /**
     * Gets the value of the gasFireplace property.
     * 
     */
    public boolean isGasFireplace() {
        return gasFireplace;
    }

    /**
     * Sets the value of the gasFireplace property.
     * 
     */
    public void setGasFireplace(boolean value) {
        this.gasFireplace = value;
    }

    /**
     * Gets the value of the woodBurningFireplace property.
     * 
     */
    public boolean isWoodBurningFireplace() {
        return woodBurningFireplace;
    }

    /**
     * Sets the value of the woodBurningFireplace property.
     * 
     */
    public void setWoodBurningFireplace(boolean value) {
        this.woodBurningFireplace = value;
    }

    /**
     * Gets the value of the freeCalls property.
     * 
     */
    public boolean isFreeCalls() {
        return freeCalls;
    }

    /**
     * Sets the value of the freeCalls property.
     * 
     */
    public void setFreeCalls(boolean value) {
        this.freeCalls = value;
    }

    /**
     * Gets the value of the freeSolarHeatedPool property.
     * 
     */
    public boolean isFreeSolarHeatedPool() {
        return freeSolarHeatedPool;
    }

    /**
     * Sets the value of the freeSolarHeatedPool property.
     * 
     */
    public void setFreeSolarHeatedPool(boolean value) {
        this.freeSolarHeatedPool = value;
    }

    /**
     * Gets the value of the packnPlay property.
     * 
     */
    public boolean isPacknPlay() {
        return packnPlay;
    }

    /**
     * Sets the value of the packnPlay property.
     * 
     */
    public void setPacknPlay(boolean value) {
        this.packnPlay = value;
    }

    /**
     * Gets the value of the privacyFence property.
     * 
     */
    public boolean isPrivacyFence() {
        return privacyFence;
    }

    /**
     * Sets the value of the privacyFence property.
     * 
     */
    public void setPrivacyFence(boolean value) {
        this.privacyFence = value;
    }

    /**
     * Gets the value of the southFacingPool property.
     * 
     */
    public boolean isSouthFacingPool() {
        return southFacingPool;
    }

    /**
     * Sets the value of the southFacingPool property.
     * 
     */
    public void setSouthFacingPool(boolean value) {
        this.southFacingPool = value;
    }

    /**
     * Gets the value of the bbq property.
     * 
     */
    public boolean isBBQ() {
        return bbq;
    }

    /**
     * Sets the value of the bbq property.
     * 
     */
    public void setBBQ(boolean value) {
        this.bbq = value;
    }

    /**
     * Gets the value of the airHockeyTable property.
     * 
     */
    public boolean isAirHockeyTable() {
        return airHockeyTable;
    }

    /**
     * Sets the value of the airHockeyTable property.
     * 
     */
    public void setAirHockeyTable(boolean value) {
        this.airHockeyTable = value;
    }

    /**
     * Gets the value of the bigScreenTV property.
     * 
     */
    public boolean isBigScreenTV() {
        return bigScreenTV;
    }

    /**
     * Sets the value of the bigScreenTV property.
     * 
     */
    public void setBigScreenTV(boolean value) {
        this.bigScreenTV = value;
    }

    /**
     * Gets the value of the cdPlayer property.
     * 
     */
    public boolean isCDPlayer() {
        return cdPlayer;
    }

    /**
     * Sets the value of the cdPlayer property.
     * 
     */
    public void setCDPlayer(boolean value) {
        this.cdPlayer = value;
    }

    /**
     * Gets the value of the dvdPlayer property.
     * 
     */
    public boolean isDVDPlayer() {
        return dvdPlayer;
    }

    /**
     * Sets the value of the dvdPlayer property.
     * 
     */
    public void setDVDPlayer(boolean value) {
        this.dvdPlayer = value;
    }

    /**
     * Gets the value of the foosballTable property.
     * 
     */
    public boolean isFoosballTable() {
        return foosballTable;
    }

    /**
     * Sets the value of the foosballTable property.
     * 
     */
    public void setFoosballTable(boolean value) {
        this.foosballTable = value;
    }

    /**
     * Gets the value of the grill property.
     * 
     */
    public boolean isGrill() {
        return grill;
    }

    /**
     * Sets the value of the grill property.
     * 
     */
    public void setGrill(boolean value) {
        this.grill = value;
    }

    /**
     * Gets the value of the hairDryer property.
     * 
     */
    public boolean isHairDryer() {
        return hairDryer;
    }

    /**
     * Sets the value of the hairDryer property.
     * 
     */
    public void setHairDryer(boolean value) {
        this.hairDryer = value;
    }

    /**
     * Gets the value of the tvInEveryBedroom property.
     * 
     */
    public boolean isTVInEveryBedroom() {
        return tvInEveryBedroom;
    }

    /**
     * Sets the value of the tvInEveryBedroom property.
     * 
     */
    public void setTVInEveryBedroom(boolean value) {
        this.tvInEveryBedroom = value;
    }

    /**
     * Gets the value of the vcr property.
     * 
     */
    public boolean isVCR() {
        return vcr;
    }

    /**
     * Sets the value of the vcr property.
     * 
     */
    public void setVCR(boolean value) {
        this.vcr = value;
    }

    /**
     * Gets the value of the indoorJacuzzi property.
     * 
     */
    public boolean isIndoorJacuzzi() {
        return indoorJacuzzi;
    }

    /**
     * Sets the value of the indoorJacuzzi property.
     * 
     */
    public void setIndoorJacuzzi(boolean value) {
        this.indoorJacuzzi = value;
    }

    /**
     * Gets the value of the indoorHotTub property.
     * 
     */
    public boolean isIndoorHotTub() {
        return indoorHotTub;
    }

    /**
     * Sets the value of the indoorHotTub property.
     * 
     */
    public void setIndoorHotTub(boolean value) {
        this.indoorHotTub = value;
    }

    /**
     * Gets the value of the outdoorHotTub property.
     * 
     */
    public boolean isOutdoorHotTub() {
        return outdoorHotTub;
    }

    /**
     * Sets the value of the outdoorHotTub property.
     * 
     */
    public void setOutdoorHotTub(boolean value) {
        this.outdoorHotTub = value;
    }

    /**
     * Gets the value of the pavedParking property.
     * 
     */
    public boolean isPavedParking() {
        return pavedParking;
    }

    /**
     * Sets the value of the pavedParking property.
     * 
     */
    public void setPavedParking(boolean value) {
        this.pavedParking = value;
    }

    /**
     * Gets the value of the poolAccess property.
     * 
     */
    public boolean isPoolAccess() {
        return poolAccess;
    }

    /**
     * Sets the value of the poolAccess property.
     * 
     */
    public void setPoolAccess(boolean value) {
        this.poolAccess = value;
    }

    /**
     * Gets the value of the rockingChairs property.
     * 
     */
    public boolean isRockingChairs() {
        return rockingChairs;
    }

    /**
     * Sets the value of the rockingChairs property.
     * 
     */
    public void setRockingChairs(boolean value) {
        this.rockingChairs = value;
    }

    /**
     * Gets the value of the videoGames property.
     * 
     */
    public boolean isVideoGames() {
        return videoGames;
    }

    /**
     * Sets the value of the videoGames property.
     * 
     */
    public void setVideoGames(boolean value) {
        this.videoGames = value;
    }

    /**
     * Gets the value of the fishing property.
     * 
     */
    public boolean isFishing() {
        return fishing;
    }

    /**
     * Sets the value of the fishing property.
     * 
     */
    public void setFishing(boolean value) {
        this.fishing = value;
    }

    /**
     * Gets the value of the bedroomConfiguration property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBedroomConfiguration() {
        return bedroomConfiguration;
    }

    /**
     * Sets the value of the bedroomConfiguration property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBedroomConfiguration(String value) {
        this.bedroomConfiguration = value;
    }

    /**
     * Gets the value of the address1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * Sets the value of the address1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress1(String value) {
        this.address1 = value;
    }

    /**
     * Gets the value of the address2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Sets the value of the address2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress2(String value) {
        this.address2 = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the county property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCounty() {
        return county;
    }

    /**
     * Sets the value of the county property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCounty(String value) {
        this.county = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * Gets the value of the zip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets the value of the zip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZip(String value) {
        this.zip = value;
    }

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Gets the value of the telephone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Sets the value of the telephone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelephone(String value) {
        this.telephone = value;
    }

    /**
     * Gets the value of the extraBed property.
     * 
     */
    public boolean isExtraBed() {
        return extraBed;
    }

    /**
     * Sets the value of the extraBed property.
     * 
     */
    public void setExtraBed(boolean value) {
        this.extraBed = value;
    }

    /**
     * Gets the value of the sofaBed property.
     * 
     */
    public boolean isSofaBed() {
        return sofaBed;
    }

    /**
     * Sets the value of the sofaBed property.
     * 
     */
    public void setSofaBed(boolean value) {
        this.sofaBed = value;
    }

    /**
     * Gets the value of the twinSingleBeds property.
     * 
     */
    public int getTwinSingleBeds() {
        return twinSingleBeds;
    }

    /**
     * Sets the value of the twinSingleBeds property.
     * 
     */
    public void setTwinSingleBeds(int value) {
        this.twinSingleBeds = value;
    }

    /**
     * Gets the value of the fullBeds property.
     * 
     */
    public int getFullBeds() {
        return fullBeds;
    }

    /**
     * Sets the value of the fullBeds property.
     * 
     */
    public void setFullBeds(int value) {
        this.fullBeds = value;
    }

    /**
     * Gets the value of the queenBeds property.
     * 
     */
    public int getQueenBeds() {
        return queenBeds;
    }

    /**
     * Sets the value of the queenBeds property.
     * 
     */
    public void setQueenBeds(int value) {
        this.queenBeds = value;
    }

    /**
     * Gets the value of the kingBeds property.
     * 
     */
    public int getKingBeds() {
        return kingBeds;
    }

    /**
     * Sets the value of the kingBeds property.
     * 
     */
    public void setKingBeds(int value) {
        this.kingBeds = value;
    }

}
