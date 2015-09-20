
package net.cbtltd.rest.rci;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resort-location-info complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resort-location-info">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="distanceToMajorCityInKms" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="distanceToMajorCityInMiles" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="distanceToNearestTownInKms" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="distanceToNearestTownInMiles" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="drivingDirectionsFromNearestAirport" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="elevationInMeters" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="elevationInFeet" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="latitude" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="longitude" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="carSuggestedToFullyEnjoyTheArea" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accessibilityDuringSeasons" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shuttleServiceToAirport" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shuttleServiceToAreaAttractions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parking" type="{}parking-info" minOccurs="0"/>
 *         &lt;element name="publicTransportInfo" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="publicTransportationInfo" type="{}transportation-info" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resort-location-info", propOrder = {
    "distanceToMajorCityInKms",
    "distanceToMajorCityInMiles",
    "distanceToNearestTownInKms",
    "distanceToNearestTownInMiles",
    "drivingDirectionsFromNearestAirport",
    "elevationInMeters",
    "elevationInFeet",
    "latitude",
    "longitude",
    "carSuggestedToFullyEnjoyTheArea",
    "accessibilityDuringSeasons",
    "shuttleServiceToAirport",
    "shuttleServiceToAreaAttractions",
    "parking",
    "publicTransportInfo"
})
public class ResortLocationInfo {

    protected Double distanceToMajorCityInKms;
    protected Double distanceToMajorCityInMiles;
    protected Double distanceToNearestTownInKms;
    protected Double distanceToNearestTownInMiles;
    protected String drivingDirectionsFromNearestAirport;
    protected Double elevationInMeters;
    protected Double elevationInFeet;
    protected String latitude;
    protected String longitude;
    protected String carSuggestedToFullyEnjoyTheArea;
    protected String accessibilityDuringSeasons;
    protected String shuttleServiceToAirport;
    protected String shuttleServiceToAreaAttractions;
    protected ParkingInfo parking;
    protected ResortLocationInfo.PublicTransportInfo publicTransportInfo;

    /**
     * Gets the value of the distanceToMajorCityInKms property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDistanceToMajorCityInKms() {
        return distanceToMajorCityInKms;
    }

    /**
     * Sets the value of the distanceToMajorCityInKms property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDistanceToMajorCityInKms(Double value) {
        this.distanceToMajorCityInKms = value;
    }

    /**
     * Gets the value of the distanceToMajorCityInMiles property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDistanceToMajorCityInMiles() {
        return distanceToMajorCityInMiles;
    }

    /**
     * Sets the value of the distanceToMajorCityInMiles property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDistanceToMajorCityInMiles(Double value) {
        this.distanceToMajorCityInMiles = value;
    }

    /**
     * Gets the value of the distanceToNearestTownInKms property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDistanceToNearestTownInKms() {
        return distanceToNearestTownInKms;
    }

    /**
     * Sets the value of the distanceToNearestTownInKms property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDistanceToNearestTownInKms(Double value) {
        this.distanceToNearestTownInKms = value;
    }

    /**
     * Gets the value of the distanceToNearestTownInMiles property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getDistanceToNearestTownInMiles() {
        return distanceToNearestTownInMiles;
    }

    /**
     * Sets the value of the distanceToNearestTownInMiles property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setDistanceToNearestTownInMiles(Double value) {
        this.distanceToNearestTownInMiles = value;
    }

    /**
     * Gets the value of the drivingDirectionsFromNearestAirport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDrivingDirectionsFromNearestAirport() {
        return drivingDirectionsFromNearestAirport;
    }

    /**
     * Sets the value of the drivingDirectionsFromNearestAirport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDrivingDirectionsFromNearestAirport(String value) {
        this.drivingDirectionsFromNearestAirport = value;
    }

    /**
     * Gets the value of the elevationInMeters property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getElevationInMeters() {
        return elevationInMeters;
    }

    /**
     * Sets the value of the elevationInMeters property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setElevationInMeters(Double value) {
        this.elevationInMeters = value;
    }

    /**
     * Gets the value of the elevationInFeet property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getElevationInFeet() {
        return elevationInFeet;
    }

    /**
     * Sets the value of the elevationInFeet property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setElevationInFeet(Double value) {
        this.elevationInFeet = value;
    }

    /**
     * Gets the value of the latitude property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Sets the value of the latitude property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLatitude(String value) {
        this.latitude = value;
    }

    /**
     * Gets the value of the longitude property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Sets the value of the longitude property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLongitude(String value) {
        this.longitude = value;
    }

    /**
     * Gets the value of the carSuggestedToFullyEnjoyTheArea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarSuggestedToFullyEnjoyTheArea() {
        return carSuggestedToFullyEnjoyTheArea;
    }

    /**
     * Sets the value of the carSuggestedToFullyEnjoyTheArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarSuggestedToFullyEnjoyTheArea(String value) {
        this.carSuggestedToFullyEnjoyTheArea = value;
    }

    /**
     * Gets the value of the accessibilityDuringSeasons property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessibilityDuringSeasons() {
        return accessibilityDuringSeasons;
    }

    /**
     * Sets the value of the accessibilityDuringSeasons property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessibilityDuringSeasons(String value) {
        this.accessibilityDuringSeasons = value;
    }

    /**
     * Gets the value of the shuttleServiceToAirport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShuttleServiceToAirport() {
        return shuttleServiceToAirport;
    }

    /**
     * Sets the value of the shuttleServiceToAirport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShuttleServiceToAirport(String value) {
        this.shuttleServiceToAirport = value;
    }

    /**
     * Gets the value of the shuttleServiceToAreaAttractions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShuttleServiceToAreaAttractions() {
        return shuttleServiceToAreaAttractions;
    }

    /**
     * Sets the value of the shuttleServiceToAreaAttractions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShuttleServiceToAreaAttractions(String value) {
        this.shuttleServiceToAreaAttractions = value;
    }

    /**
     * Gets the value of the parking property.
     * 
     * @return
     *     possible object is
     *     {@link ParkingInfo }
     *     
     */
    public ParkingInfo getParking() {
        return parking;
    }

    /**
     * Sets the value of the parking property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParkingInfo }
     *     
     */
    public void setParking(ParkingInfo value) {
        this.parking = value;
    }

    /**
     * Gets the value of the publicTransportInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ResortLocationInfo.PublicTransportInfo }
     *     
     */
    public ResortLocationInfo.PublicTransportInfo getPublicTransportInfo() {
        return publicTransportInfo;
    }

    /**
     * Sets the value of the publicTransportInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortLocationInfo.PublicTransportInfo }
     *     
     */
    public void setPublicTransportInfo(ResortLocationInfo.PublicTransportInfo value) {
        this.publicTransportInfo = value;
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
     *         &lt;element name="publicTransportationInfo" type="{}transportation-info" maxOccurs="unbounded"/>
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
        "publicTransportationInfo"
    })
    public static class PublicTransportInfo {

        @XmlElement(required = true)
        protected List<TransportationInfo> publicTransportationInfo;

        /**
         * Gets the value of the publicTransportationInfo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the publicTransportationInfo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPublicTransportationInfo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TransportationInfo }
         * 
         * 
         */
        public List<TransportationInfo> getPublicTransportationInfo() {
            if (publicTransportationInfo == null) {
                publicTransportationInfo = new ArrayList<TransportationInfo>();
            }
            return this.publicTransportationInfo;
        }

    }

}
