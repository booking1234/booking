
package net.cbtltd.rest.homeaway.feedparser.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Location-related data for a Listing.
 * 
 * <p>Java class for Location complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Location">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="address" type="{}Address" minOccurs="0"/>
 *         &lt;element name="allowTravelersToZoom" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="defaultZoomLevel" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="description" type="{}Note" minOccurs="0"/>
 *         &lt;element name="enableMap" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="geoCode" type="{}GeoCode" minOccurs="0"/>
 *         &lt;element name="maxZoomLevel" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="nearestPlaces" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="nearestPlace" type="{}NearestPlace" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="otherActivities" type="{}Note" minOccurs="0"/>
 *         &lt;element name="showExactLocation" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Location", propOrder = {
    "address",
    "allowTravelersToZoom",
    "defaultZoomLevel",
    "description",
    "enableMap",
    "geoCode",
    "maxZoomLevel",
    "nearestPlaces",
    "otherActivities",
    "showExactLocation"
})
public class Location {

    protected Address address;
    protected boolean allowTravelersToZoom;
    protected Integer defaultZoomLevel;
    protected Note description;
    protected boolean enableMap;
    protected GeoCode geoCode;
    protected Integer maxZoomLevel;
    protected Location.NearestPlaces nearestPlaces;
    protected Note otherActivities;
    protected boolean showExactLocation;

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setAddress(Address value) {
        this.address = value;
    }

    /**
     * Gets the value of the allowTravelersToZoom property.
     * 
     */
    public boolean isAllowTravelersToZoom() {
        return allowTravelersToZoom;
    }

    /**
     * Sets the value of the allowTravelersToZoom property.
     * 
     */
    public void setAllowTravelersToZoom(boolean value) {
        this.allowTravelersToZoom = value;
    }

    /**
     * Gets the value of the defaultZoomLevel property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDefaultZoomLevel() {
        return defaultZoomLevel;
    }

    /**
     * Sets the value of the defaultZoomLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDefaultZoomLevel(Integer value) {
        this.defaultZoomLevel = value;
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
     * Gets the value of the enableMap property.
     * 
     */
    public boolean isEnableMap() {
        return enableMap;
    }

    /**
     * Sets the value of the enableMap property.
     * 
     */
    public void setEnableMap(boolean value) {
        this.enableMap = value;
    }

    /**
     * Gets the value of the geoCode property.
     * 
     * @return
     *     possible object is
     *     {@link GeoCode }
     *     
     */
    public GeoCode getGeoCode() {
        return geoCode;
    }

    /**
     * Sets the value of the geoCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeoCode }
     *     
     */
    public void setGeoCode(GeoCode value) {
        this.geoCode = value;
    }

    /**
     * Gets the value of the maxZoomLevel property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxZoomLevel() {
        return maxZoomLevel;
    }

    /**
     * Sets the value of the maxZoomLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxZoomLevel(Integer value) {
        this.maxZoomLevel = value;
    }

    /**
     * Gets the value of the nearestPlaces property.
     * 
     * @return
     *     possible object is
     *     {@link Location.NearestPlaces }
     *     
     */
    public Location.NearestPlaces getNearestPlaces() {
        return nearestPlaces;
    }

    /**
     * Sets the value of the nearestPlaces property.
     * 
     * @param value
     *     allowed object is
     *     {@link Location.NearestPlaces }
     *     
     */
    public void setNearestPlaces(Location.NearestPlaces value) {
        this.nearestPlaces = value;
    }

    /**
     * Gets the value of the otherActivities property.
     * 
     * @return
     *     possible object is
     *     {@link Note }
     *     
     */
    public Note getOtherActivities() {
        return otherActivities;
    }

    /**
     * Sets the value of the otherActivities property.
     * 
     * @param value
     *     allowed object is
     *     {@link Note }
     *     
     */
    public void setOtherActivities(Note value) {
        this.otherActivities = value;
    }

    /**
     * Gets the value of the showExactLocation property.
     * 
     */
    public boolean isShowExactLocation() {
        return showExactLocation;
    }

    /**
     * Sets the value of the showExactLocation property.
     * 
     */
    public void setShowExactLocation(boolean value) {
        this.showExactLocation = value;
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
     *         &lt;element name="nearestPlace" type="{}NearestPlace" maxOccurs="unbounded" minOccurs="0"/>
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
        "nearestPlace"
    })
    public static class NearestPlaces {

        protected List<NearestPlace> nearestPlace;

        /**
         * Gets the value of the nearestPlace property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the nearestPlace property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getNearestPlace().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link NearestPlace }
         * 
         * 
         */
        public List<NearestPlace> getNearestPlace() {
            if (nearestPlace == null) {
                nearestPlace = new ArrayList<NearestPlace>();
            }
            return this.nearestPlace;
        }

    }

}
