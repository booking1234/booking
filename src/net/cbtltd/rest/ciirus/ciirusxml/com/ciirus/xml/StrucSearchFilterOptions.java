
package net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for strucSearchFilterOptions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="strucSearchFilterOptions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ManagementCompanyID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CommunityID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PropertyID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PropertyType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="HasPool" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="HasSpa" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PrivacyFence" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CommunalGym" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="HasGamesRoom" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IsGasFree" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Sleeps" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Bedrooms" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PropertyClass" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ConservationView" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="WaterView" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="LakeView" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="WiFi" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PetsAllowed" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OnGolfCourse" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SouthFacingPool" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "strucSearchFilterOptions", propOrder = {
    "managementCompanyID",
    "communityID",
    "propertyID",
    "propertyType",
    "hasPool",
    "hasSpa",
    "privacyFence",
    "communalGym",
    "hasGamesRoom",
    "isGasFree",
    "sleeps",
    "bedrooms",
    "propertyClass",
    "conservationView",
    "waterView",
    "lakeView",
    "wiFi",
    "petsAllowed",
    "onGolfCourse",
    "southFacingPool"
})
public class StrucSearchFilterOptions {

    @XmlElement(name = "ManagementCompanyID")
    protected int managementCompanyID;
    @XmlElement(name = "CommunityID")
    protected int communityID;
    @XmlElement(name = "PropertyID")
    protected int propertyID;
    @XmlElement(name = "PropertyType")
    protected int propertyType;
    @XmlElement(name = "HasPool")
    protected int hasPool;
    @XmlElement(name = "HasSpa")
    protected int hasSpa;
    @XmlElement(name = "PrivacyFence")
    protected int privacyFence;
    @XmlElement(name = "CommunalGym")
    protected int communalGym;
    @XmlElement(name = "HasGamesRoom")
    protected int hasGamesRoom;
    @XmlElement(name = "IsGasFree")
    protected boolean isGasFree;
    @XmlElement(name = "Sleeps")
    protected int sleeps;
    @XmlElement(name = "Bedrooms")
    protected int bedrooms;
    @XmlElement(name = "PropertyClass")
    protected int propertyClass;
    @XmlElement(name = "ConservationView")
    protected int conservationView;
    @XmlElement(name = "WaterView")
    protected int waterView;
    @XmlElement(name = "LakeView")
    protected int lakeView;
    @XmlElement(name = "WiFi")
    protected int wiFi;
    @XmlElement(name = "PetsAllowed")
    protected int petsAllowed;
    @XmlElement(name = "OnGolfCourse")
    protected int onGolfCourse;
    @XmlElement(name = "SouthFacingPool")
    protected int southFacingPool;

    /**
     * Gets the value of the managementCompanyID property.
     * 
     */
    public int getManagementCompanyID() {
        return managementCompanyID;
    }

    /**
     * Sets the value of the managementCompanyID property.
     * 
     */
    public void setManagementCompanyID(int value) {
        this.managementCompanyID = value;
    }

    /**
     * Gets the value of the communityID property.
     * 
     */
    public int getCommunityID() {
        return communityID;
    }

    /**
     * Sets the value of the communityID property.
     * 
     */
    public void setCommunityID(int value) {
        this.communityID = value;
    }

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
     * Gets the value of the propertyType property.
     * 
     */
    public int getPropertyType() {
        return propertyType;
    }

    /**
     * Sets the value of the propertyType property.
     * 
     */
    public void setPropertyType(int value) {
        this.propertyType = value;
    }

    /**
     * Gets the value of the hasPool property.
     * 
     */
    public int getHasPool() {
        return hasPool;
    }

    /**
     * Sets the value of the hasPool property.
     * 
     */
    public void setHasPool(int value) {
        this.hasPool = value;
    }

    /**
     * Gets the value of the hasSpa property.
     * 
     */
    public int getHasSpa() {
        return hasSpa;
    }

    /**
     * Sets the value of the hasSpa property.
     * 
     */
    public void setHasSpa(int value) {
        this.hasSpa = value;
    }

    /**
     * Gets the value of the privacyFence property.
     * 
     */
    public int getPrivacyFence() {
        return privacyFence;
    }

    /**
     * Sets the value of the privacyFence property.
     * 
     */
    public void setPrivacyFence(int value) {
        this.privacyFence = value;
    }

    /**
     * Gets the value of the communalGym property.
     * 
     */
    public int getCommunalGym() {
        return communalGym;
    }

    /**
     * Sets the value of the communalGym property.
     * 
     */
    public void setCommunalGym(int value) {
        this.communalGym = value;
    }

    /**
     * Gets the value of the hasGamesRoom property.
     * 
     */
    public int getHasGamesRoom() {
        return hasGamesRoom;
    }

    /**
     * Sets the value of the hasGamesRoom property.
     * 
     */
    public void setHasGamesRoom(int value) {
        this.hasGamesRoom = value;
    }

    /**
     * Gets the value of the isGasFree property.
     * 
     */
    public boolean isIsGasFree() {
        return isGasFree;
    }

    /**
     * Sets the value of the isGasFree property.
     * 
     */
    public void setIsGasFree(boolean value) {
        this.isGasFree = value;
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
     * Gets the value of the propertyClass property.
     * 
     */
    public int getPropertyClass() {
        return propertyClass;
    }

    /**
     * Sets the value of the propertyClass property.
     * 
     */
    public void setPropertyClass(int value) {
        this.propertyClass = value;
    }

    /**
     * Gets the value of the conservationView property.
     * 
     */
    public int getConservationView() {
        return conservationView;
    }

    /**
     * Sets the value of the conservationView property.
     * 
     */
    public void setConservationView(int value) {
        this.conservationView = value;
    }

    /**
     * Gets the value of the waterView property.
     * 
     */
    public int getWaterView() {
        return waterView;
    }

    /**
     * Sets the value of the waterView property.
     * 
     */
    public void setWaterView(int value) {
        this.waterView = value;
    }

    /**
     * Gets the value of the lakeView property.
     * 
     */
    public int getLakeView() {
        return lakeView;
    }

    /**
     * Sets the value of the lakeView property.
     * 
     */
    public void setLakeView(int value) {
        this.lakeView = value;
    }

    /**
     * Gets the value of the wiFi property.
     * 
     */
    public int getWiFi() {
        return wiFi;
    }

    /**
     * Sets the value of the wiFi property.
     * 
     */
    public void setWiFi(int value) {
        this.wiFi = value;
    }

    /**
     * Gets the value of the petsAllowed property.
     * 
     */
    public int getPetsAllowed() {
        return petsAllowed;
    }

    /**
     * Sets the value of the petsAllowed property.
     * 
     */
    public void setPetsAllowed(int value) {
        this.petsAllowed = value;
    }

    /**
     * Gets the value of the onGolfCourse property.
     * 
     */
    public int getOnGolfCourse() {
        return onGolfCourse;
    }

    /**
     * Sets the value of the onGolfCourse property.
     * 
     */
    public void setOnGolfCourse(int value) {
        this.onGolfCourse = value;
    }

    /**
     * Gets the value of the southFacingPool property.
     * 
     */
    public int getSouthFacingPool() {
        return southFacingPool;
    }

    /**
     * Sets the value of the southFacingPool property.
     * 
     */
    public void setSouthFacingPool(int value) {
        this.southFacingPool = value;
    }

}
