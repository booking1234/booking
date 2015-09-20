
package net.cbtltd.rest.leaderstay;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for propertiesInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="propertiesInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="villaid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="hash_key" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="is_on_request" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="area" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="street_address" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="property_type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="built" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="renovated" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="size_SQM" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="max_persons" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="floors_no" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="grounds_SQM" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="view" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="change_of_sheets" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="checkin_time" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="checkout_time" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="amenitiesList" type="{urn:leaderstay}checklistT"/>
 *         &lt;element name="attractionsList" type="{urn:leaderstay}checklistT"/>
 *         &lt;element name="bathroom_facilitiesList" type="{urn:leaderstay}bathroom_facilitiesT"/>
 *         &lt;element name="bedList" type="{urn:leaderstay}bedT"/>
 *         &lt;element name="distancesList" type="{urn:leaderstay}distancesT"/>
 *         &lt;element name="internetList" type="{urn:leaderstay}checklistT"/>
 *         &lt;element name="kitchen_amenitiesList" type="{urn:leaderstay}checklistT"/>
 *         &lt;element name="leisure_activitiesList" type="{urn:leaderstay}checklistT"/>
 *         &lt;element name="local_area_activitiesList" type="{urn:leaderstay}checklistT"/>
 *         &lt;element name="location_typeList" type="{urn:leaderstay}checklistT"/>
 *         &lt;element name="nearby_attraction_facilitiesList" type="{urn:leaderstay}checklistT"/>
 *         &lt;element name="outside_amenitiesList" type="{urn:leaderstay}checklistT"/>
 *         &lt;element name="poolList" type="{urn:leaderstay}poolT"/>
 *         &lt;element name="roomsList" type="{urn:leaderstay}roomsT"/>
 *         &lt;element name="roomsacList" type="{urn:leaderstay}checklistT"/>
 *         &lt;element name="stuffList" type="{urn:leaderstay}checklistT"/>
 *         &lt;element name="suitabilityList" type="{urn:leaderstay}roomsT"/>
 *         &lt;element name="imageList" type="{urn:leaderstay}imageT"/>
 *         &lt;element name="extraList" type="{urn:leaderstay}extralistT"/>
 *         &lt;element name="terms" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="instructions" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="googleCoordinates" type="{urn:leaderstay}googleCoordinate"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "propertiesInfo", propOrder = {

})
public class PropertiesInfo {

    @XmlElement(required = true)
    protected String villaid;
    @XmlElement(name = "hash_key", required = true)
    protected String hashKey;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(name = "is_on_request")
    protected int isOnRequest;
    @XmlElement(required = true)
    protected String country;
    @XmlElement(required = true)
    protected String region;
    @XmlElement(required = true)
    protected String area;
    @XmlElement(name = "street_address", required = true)
    protected String streetAddress;
    @XmlElement(name = "property_type", required = true)
    protected String propertyType;
    protected int built;
    protected int renovated;
    @XmlElement(name = "size_SQM")
    protected double sizeSQM;
    @XmlElement(name = "max_persons", required = true)
    protected String maxPersons;
    @XmlElement(name = "floors_no")
    protected int floorsNo;
    @XmlElement(name = "grounds_SQM")
    protected double groundsSQM;
    @XmlElement(required = true)
    protected String view;
    @XmlElement(name = "change_of_sheets", required = true)
    protected String changeOfSheets;
    @XmlElement(name = "checkin_time", required = true)
    protected String checkinTime;
    @XmlElement(name = "checkout_time", required = true)
    protected String checkoutTime;
    @XmlElement(required = true)
    protected ChecklistT amenitiesList;
    @XmlElement(required = true)
    protected ChecklistT attractionsList;
    @XmlElement(name = "bathroom_facilitiesList", required = true)
    protected BathroomFacilitiesT bathroomFacilitiesList;
    @XmlElement(required = true)
    protected BedT bedList;
    @XmlElement(required = true)
    protected DistancesT distancesList;
    @XmlElement(required = true)
    protected ChecklistT internetList;
    @XmlElement(name = "kitchen_amenitiesList", required = true)
    protected ChecklistT kitchenAmenitiesList;
    @XmlElement(name = "leisure_activitiesList", required = true)
    protected ChecklistT leisureActivitiesList;
    @XmlElement(name = "local_area_activitiesList", required = true)
    protected ChecklistT localAreaActivitiesList;
    @XmlElement(name = "location_typeList", required = true)
    protected ChecklistT locationTypeList;
    @XmlElement(name = "nearby_attraction_facilitiesList", required = true)
    protected ChecklistT nearbyAttractionFacilitiesList;
    @XmlElement(name = "outside_amenitiesList", required = true)
    protected ChecklistT outsideAmenitiesList;
    @XmlElement(required = true)
    protected PoolT poolList;
    @XmlElement(required = true)
    protected RoomsT roomsList;
    @XmlElement(required = true)
    protected ChecklistT roomsacList;
    @XmlElement(required = true)
    protected ChecklistT stuffList;
    @XmlElement(required = true)
    protected RoomsT suitabilityList;
    @XmlElement(required = true)
    protected ImageT imageList;
    @XmlElement(required = true)
    protected ExtralistT extraList;
    @XmlElement(required = true)
    protected String terms;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected String instructions;
    @XmlElement(required = true)
    protected GoogleCoordinate googleCoordinates;

    /**
     * Gets the value of the villaid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVillaid() {
        return villaid;
    }

    /**
     * Sets the value of the villaid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVillaid(String value) {
        this.villaid = value;
    }

    /**
     * Gets the value of the hashKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHashKey() {
        return hashKey;
    }

    /**
     * Sets the value of the hashKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHashKey(String value) {
        this.hashKey = value;
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
     * Gets the value of the isOnRequest property.
     * 
     */
    public int getIsOnRequest() {
        return isOnRequest;
    }

    /**
     * Sets the value of the isOnRequest property.
     * 
     */
    public void setIsOnRequest(int value) {
        this.isOnRequest = value;
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
     * Gets the value of the region property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the value of the region property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegion(String value) {
        this.region = value;
    }

    /**
     * Gets the value of the area property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArea() {
        return area;
    }

    /**
     * Sets the value of the area property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArea(String value) {
        this.area = value;
    }

    /**
     * Gets the value of the streetAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * Sets the value of the streetAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetAddress(String value) {
        this.streetAddress = value;
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
     * Gets the value of the built property.
     * 
     */
    public int getBuilt() {
        return built;
    }

    /**
     * Sets the value of the built property.
     * 
     */
    public void setBuilt(int value) {
        this.built = value;
    }

    /**
     * Gets the value of the renovated property.
     * 
     */
    public int getRenovated() {
        return renovated;
    }

    /**
     * Sets the value of the renovated property.
     * 
     */
    public void setRenovated(int value) {
        this.renovated = value;
    }

    /**
     * Gets the value of the sizeSQM property.
     * 
     */
    public double getSizeSQM() {
        return sizeSQM;
    }

    /**
     * Sets the value of the sizeSQM property.
     * 
     */
    public void setSizeSQM(double value) {
        this.sizeSQM = value;
    }

    /**
     * Gets the value of the maxPersons property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxPersons() {
        return maxPersons;
    }

    /**
     * Sets the value of the maxPersons property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxPersons(String value) {
        this.maxPersons = value;
    }

    /**
     * Gets the value of the floorsNo property.
     * 
     */
    public int getFloorsNo() {
        return floorsNo;
    }

    /**
     * Sets the value of the floorsNo property.
     * 
     */
    public void setFloorsNo(int value) {
        this.floorsNo = value;
    }

    /**
     * Gets the value of the groundsSQM property.
     * 
     */
    public double getGroundsSQM() {
        return groundsSQM;
    }

    /**
     * Sets the value of the groundsSQM property.
     * 
     */
    public void setGroundsSQM(double value) {
        this.groundsSQM = value;
    }

    /**
     * Gets the value of the view property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getView() {
        return view;
    }

    /**
     * Sets the value of the view property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setView(String value) {
        this.view = value;
    }

    /**
     * Gets the value of the changeOfSheets property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChangeOfSheets() {
        return changeOfSheets;
    }

    /**
     * Sets the value of the changeOfSheets property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChangeOfSheets(String value) {
        this.changeOfSheets = value;
    }

    /**
     * Gets the value of the checkinTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckinTime() {
        return checkinTime;
    }

    /**
     * Sets the value of the checkinTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckinTime(String value) {
        this.checkinTime = value;
    }

    /**
     * Gets the value of the checkoutTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckoutTime() {
        return checkoutTime;
    }

    /**
     * Sets the value of the checkoutTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckoutTime(String value) {
        this.checkoutTime = value;
    }

    /**
     * Gets the value of the amenitiesList property.
     * 
     * @return
     *     possible object is
     *     {@link ChecklistT }
     *     
     */
    public ChecklistT getAmenitiesList() {
        return amenitiesList;
    }

    /**
     * Sets the value of the amenitiesList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChecklistT }
     *     
     */
    public void setAmenitiesList(ChecklistT value) {
        this.amenitiesList = value;
    }

    /**
     * Gets the value of the attractionsList property.
     * 
     * @return
     *     possible object is
     *     {@link ChecklistT }
     *     
     */
    public ChecklistT getAttractionsList() {
        return attractionsList;
    }

    /**
     * Sets the value of the attractionsList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChecklistT }
     *     
     */
    public void setAttractionsList(ChecklistT value) {
        this.attractionsList = value;
    }

    /**
     * Gets the value of the bathroomFacilitiesList property.
     * 
     * @return
     *     possible object is
     *     {@link BathroomFacilitiesT }
     *     
     */
    public BathroomFacilitiesT getBathroomFacilitiesList() {
        return bathroomFacilitiesList;
    }

    /**
     * Sets the value of the bathroomFacilitiesList property.
     * 
     * @param value
     *     allowed object is
     *     {@link BathroomFacilitiesT }
     *     
     */
    public void setBathroomFacilitiesList(BathroomFacilitiesT value) {
        this.bathroomFacilitiesList = value;
    }

    /**
     * Gets the value of the bedList property.
     * 
     * @return
     *     possible object is
     *     {@link BedT }
     *     
     */
    public BedT getBedList() {
        return bedList;
    }

    /**
     * Sets the value of the bedList property.
     * 
     * @param value
     *     allowed object is
     *     {@link BedT }
     *     
     */
    public void setBedList(BedT value) {
        this.bedList = value;
    }

    /**
     * Gets the value of the distancesList property.
     * 
     * @return
     *     possible object is
     *     {@link DistancesT }
     *     
     */
    public DistancesT getDistancesList() {
        return distancesList;
    }

    /**
     * Sets the value of the distancesList property.
     * 
     * @param value
     *     allowed object is
     *     {@link DistancesT }
     *     
     */
    public void setDistancesList(DistancesT value) {
        this.distancesList = value;
    }

    /**
     * Gets the value of the internetList property.
     * 
     * @return
     *     possible object is
     *     {@link ChecklistT }
     *     
     */
    public ChecklistT getInternetList() {
        return internetList;
    }

    /**
     * Sets the value of the internetList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChecklistT }
     *     
     */
    public void setInternetList(ChecklistT value) {
        this.internetList = value;
    }

    /**
     * Gets the value of the kitchenAmenitiesList property.
     * 
     * @return
     *     possible object is
     *     {@link ChecklistT }
     *     
     */
    public ChecklistT getKitchenAmenitiesList() {
        return kitchenAmenitiesList;
    }

    /**
     * Sets the value of the kitchenAmenitiesList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChecklistT }
     *     
     */
    public void setKitchenAmenitiesList(ChecklistT value) {
        this.kitchenAmenitiesList = value;
    }

    /**
     * Gets the value of the leisureActivitiesList property.
     * 
     * @return
     *     possible object is
     *     {@link ChecklistT }
     *     
     */
    public ChecklistT getLeisureActivitiesList() {
        return leisureActivitiesList;
    }

    /**
     * Sets the value of the leisureActivitiesList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChecklistT }
     *     
     */
    public void setLeisureActivitiesList(ChecklistT value) {
        this.leisureActivitiesList = value;
    }

    /**
     * Gets the value of the localAreaActivitiesList property.
     * 
     * @return
     *     possible object is
     *     {@link ChecklistT }
     *     
     */
    public ChecklistT getLocalAreaActivitiesList() {
        return localAreaActivitiesList;
    }

    /**
     * Sets the value of the localAreaActivitiesList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChecklistT }
     *     
     */
    public void setLocalAreaActivitiesList(ChecklistT value) {
        this.localAreaActivitiesList = value;
    }

    /**
     * Gets the value of the locationTypeList property.
     * 
     * @return
     *     possible object is
     *     {@link ChecklistT }
     *     
     */
    public ChecklistT getLocationTypeList() {
        return locationTypeList;
    }

    /**
     * Sets the value of the locationTypeList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChecklistT }
     *     
     */
    public void setLocationTypeList(ChecklistT value) {
        this.locationTypeList = value;
    }

    /**
     * Gets the value of the nearbyAttractionFacilitiesList property.
     * 
     * @return
     *     possible object is
     *     {@link ChecklistT }
     *     
     */
    public ChecklistT getNearbyAttractionFacilitiesList() {
        return nearbyAttractionFacilitiesList;
    }

    /**
     * Sets the value of the nearbyAttractionFacilitiesList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChecklistT }
     *     
     */
    public void setNearbyAttractionFacilitiesList(ChecklistT value) {
        this.nearbyAttractionFacilitiesList = value;
    }

    /**
     * Gets the value of the outsideAmenitiesList property.
     * 
     * @return
     *     possible object is
     *     {@link ChecklistT }
     *     
     */
    public ChecklistT getOutsideAmenitiesList() {
        return outsideAmenitiesList;
    }

    /**
     * Sets the value of the outsideAmenitiesList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChecklistT }
     *     
     */
    public void setOutsideAmenitiesList(ChecklistT value) {
        this.outsideAmenitiesList = value;
    }

    /**
     * Gets the value of the poolList property.
     * 
     * @return
     *     possible object is
     *     {@link PoolT }
     *     
     */
    public PoolT getPoolList() {
        return poolList;
    }

    /**
     * Sets the value of the poolList property.
     * 
     * @param value
     *     allowed object is
     *     {@link PoolT }
     *     
     */
    public void setPoolList(PoolT value) {
        this.poolList = value;
    }

    /**
     * Gets the value of the roomsList property.
     * 
     * @return
     *     possible object is
     *     {@link RoomsT }
     *     
     */
    public RoomsT getRoomsList() {
        return roomsList;
    }

    /**
     * Sets the value of the roomsList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoomsT }
     *     
     */
    public void setRoomsList(RoomsT value) {
        this.roomsList = value;
    }

    /**
     * Gets the value of the roomsacList property.
     * 
     * @return
     *     possible object is
     *     {@link ChecklistT }
     *     
     */
    public ChecklistT getRoomsacList() {
        return roomsacList;
    }

    /**
     * Sets the value of the roomsacList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChecklistT }
     *     
     */
    public void setRoomsacList(ChecklistT value) {
        this.roomsacList = value;
    }

    /**
     * Gets the value of the stuffList property.
     * 
     * @return
     *     possible object is
     *     {@link ChecklistT }
     *     
     */
    public ChecklistT getStuffList() {
        return stuffList;
    }

    /**
     * Sets the value of the stuffList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChecklistT }
     *     
     */
    public void setStuffList(ChecklistT value) {
        this.stuffList = value;
    }

    /**
     * Gets the value of the suitabilityList property.
     * 
     * @return
     *     possible object is
     *     {@link RoomsT }
     *     
     */
    public RoomsT getSuitabilityList() {
        return suitabilityList;
    }

    /**
     * Sets the value of the suitabilityList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoomsT }
     *     
     */
    public void setSuitabilityList(RoomsT value) {
        this.suitabilityList = value;
    }

    /**
     * Gets the value of the imageList property.
     * 
     * @return
     *     possible object is
     *     {@link ImageT }
     *     
     */
    public ImageT getImageList() {
        return imageList;
    }

    /**
     * Sets the value of the imageList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImageT }
     *     
     */
    public void setImageList(ImageT value) {
        this.imageList = value;
    }

    /**
     * Gets the value of the extraList property.
     * 
     * @return
     *     possible object is
     *     {@link ExtralistT }
     *     
     */
    public ExtralistT getExtraList() {
        return extraList;
    }

    /**
     * Sets the value of the extraList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtralistT }
     *     
     */
    public void setExtraList(ExtralistT value) {
        this.extraList = value;
    }

    /**
     * Gets the value of the terms property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTerms() {
        return terms;
    }

    /**
     * Sets the value of the terms property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTerms(String value) {
        this.terms = value;
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
     * Gets the value of the instructions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * Sets the value of the instructions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstructions(String value) {
        this.instructions = value;
    }

    /**
     * Gets the value of the googleCoordinates property.
     * 
     * @return
     *     possible object is
     *     {@link GoogleCoordinate }
     *     
     */
    public GoogleCoordinate getGoogleCoordinates() {
        return googleCoordinates;
    }

    /**
     * Sets the value of the googleCoordinates property.
     * 
     * @param value
     *     allowed object is
     *     {@link GoogleCoordinate }
     *     
     */
    public void setGoogleCoordinates(GoogleCoordinate value) {
        this.googleCoordinates = value;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PropertiesInfo [villaid=");
		builder.append(villaid);
		builder.append(", hashKey=");
		builder.append(hashKey);
		builder.append(", name=");
		builder.append(name);
		builder.append(", isOnRequest=");
		builder.append(isOnRequest);
		builder.append(", country=");
		builder.append(country);
		builder.append(", region=");
		builder.append(region);
		builder.append(", area=");
		builder.append(area);
		builder.append(", streetAddress=");
		builder.append(streetAddress);
		builder.append(", propertyType=");
		builder.append(propertyType);
		builder.append(", built=");
		builder.append(built);
		builder.append(", renovated=");
		builder.append(renovated);
		builder.append(", sizeSQM=");
		builder.append(sizeSQM);
		builder.append(", maxPersons=");
		builder.append(maxPersons);
		builder.append(", floorsNo=");
		builder.append(floorsNo);
		builder.append(", groundsSQM=");
		builder.append(groundsSQM);
		builder.append(", view=");
		builder.append(view);
		builder.append(", changeOfSheets=");
		builder.append(changeOfSheets);
		builder.append(", checkinTime=");
		builder.append(checkinTime);
		builder.append(", checkoutTime=");
		builder.append(checkoutTime);
		builder.append(", amenitiesList=");
		builder.append(amenitiesList);
		builder.append(", attractionsList=");
		builder.append(attractionsList);
		builder.append(", bathroomFacilitiesList=");
		builder.append(bathroomFacilitiesList);
		builder.append(", bedList=");
		builder.append(bedList);
		builder.append(", distancesList=");
		builder.append(distancesList);
		builder.append(", internetList=");
		builder.append(internetList);
		builder.append(", kitchenAmenitiesList=");
		builder.append(kitchenAmenitiesList);
		builder.append(", leisureActivitiesList=");
		builder.append(leisureActivitiesList);
		builder.append(", localAreaActivitiesList=");
		builder.append(localAreaActivitiesList);
		builder.append(", locationTypeList=");
		builder.append(locationTypeList);
		builder.append(", nearbyAttractionFacilitiesList=");
		builder.append(nearbyAttractionFacilitiesList);
		builder.append(", outsideAmenitiesList=");
		builder.append(outsideAmenitiesList);
		builder.append(", poolList=");
		builder.append(poolList);
		builder.append(", roomsList=");
		builder.append(roomsList);
		builder.append(", roomsacList=");
		builder.append(roomsacList);
		builder.append(", stuffList=");
		builder.append(stuffList);
		builder.append(", suitabilityList=");
		builder.append(suitabilityList);
		builder.append(", imageList=");
		builder.append(imageList);
		builder.append(", extraList=");
		builder.append(extraList);
		builder.append(", terms=");
		builder.append(terms);
		builder.append(", description=");
		builder.append(description);
		builder.append(", instructions=");
		builder.append(instructions);
		builder.append(", googleCoordinates=");
		builder.append(googleCoordinates);
		builder.append("]");
		return builder.toString();
	}

}
