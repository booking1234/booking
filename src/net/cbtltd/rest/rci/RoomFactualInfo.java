
package net.cbtltd.rest.rci;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for room-factual-info complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="room-factual-info">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bedroom" type="{}bedroom"/>
 *         &lt;element name="maxOccupancy" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="privateOccupancy" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="squareFootage" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="sizeInMeters" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="loft" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bathroom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bathroomDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rollinShower" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bathroomAccessible" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unitConfig" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accessibility" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bedroomBathLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="floorPlan" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="outdoorLivingSpaceInfo" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="outdoorLivingSpace" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="outdoorLivingSpaceWithFurniture" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="floorings" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="flooring" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="coolingSystems" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="coolingSystem" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="housekeeping" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="housekeepingFeeDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="safebox" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nonSmokingRoomsByRequest" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="telephone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="safetyEquipment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "room-factual-info", propOrder = {
    "bedroom",
    "maxOccupancy",
    "privateOccupancy",
    "squareFootage",
    "sizeInMeters",
    "loft",
    "bathroom",
    "bathroomDesc",
    "rollinShower",
    "bathroomAccessible",
    "unitConfig",
    "accessibility",
    "bedroomBathLocation",
    "floorPlan",
    "outdoorLivingSpaceInfo",
    "outdoorLivingSpaceWithFurniture",
    "floorings",
    "coolingSystems",
    "housekeeping",
    "housekeepingFeeDesc",
    "safebox",
    "nonSmokingRoomsByRequest",
    "telephone",
    "safetyEquipment"
})
public class RoomFactualInfo {

    @XmlElement(required = true)
    protected Bedroom bedroom;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer maxOccupancy;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer privateOccupancy;
    @XmlElementRef(name = "squareFootage", type = JAXBElement.class)
    protected JAXBElement<Double> squareFootage;
    @XmlElementRef(name = "sizeInMeters", type = JAXBElement.class)
    protected JAXBElement<Double> sizeInMeters;
    @XmlElementRef(name = "loft", type = JAXBElement.class)
    protected JAXBElement<String> loft;
    @XmlElementRef(name = "bathroom", type = JAXBElement.class)
    protected JAXBElement<String> bathroom;
    protected String bathroomDesc;
    protected String rollinShower;
    protected String bathroomAccessible;
    protected String unitConfig;
    @XmlElementRef(name = "accessibility", type = JAXBElement.class)
    protected JAXBElement<String> accessibility;
    protected String bedroomBathLocation;
    protected String floorPlan;
    protected RoomFactualInfo.OutdoorLivingSpaceInfo outdoorLivingSpaceInfo;
    protected String outdoorLivingSpaceWithFurniture;
    protected RoomFactualInfo.Floorings floorings;
    protected RoomFactualInfo.CoolingSystems coolingSystems;
    protected String housekeeping;
    protected String housekeepingFeeDesc;
    protected String safebox;
    protected String nonSmokingRoomsByRequest;
    protected String telephone;
    protected String safetyEquipment;

    /**
     * Gets the value of the bedroom property.
     * 
     * @return
     *     possible object is
     *     {@link Bedroom }
     *     
     */
    public Bedroom getBedroom() {
        return bedroom;
    }

    /**
     * Sets the value of the bedroom property.
     * 
     * @param value
     *     allowed object is
     *     {@link Bedroom }
     *     
     */
    public void setBedroom(Bedroom value) {
        this.bedroom = value;
    }

    /**
     * Gets the value of the maxOccupancy property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxOccupancy() {
        return maxOccupancy;
    }

    /**
     * Sets the value of the maxOccupancy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxOccupancy(Integer value) {
        this.maxOccupancy = value;
    }

    /**
     * Gets the value of the privateOccupancy property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPrivateOccupancy() {
        return privateOccupancy;
    }

    /**
     * Sets the value of the privateOccupancy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPrivateOccupancy(Integer value) {
        this.privateOccupancy = value;
    }

    /**
     * Gets the value of the squareFootage property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSquareFootage() {
        return squareFootage;
    }

    /**
     * Sets the value of the squareFootage property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSquareFootage(JAXBElement<Double> value) {
        this.squareFootage = ((JAXBElement<Double> ) value);
    }

    /**
     * Gets the value of the sizeInMeters property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getSizeInMeters() {
        return sizeInMeters;
    }

    /**
     * Sets the value of the sizeInMeters property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setSizeInMeters(JAXBElement<Double> value) {
        this.sizeInMeters = ((JAXBElement<Double> ) value);
    }

    /**
     * Gets the value of the loft property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLoft() {
        return loft;
    }

    /**
     * Sets the value of the loft property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLoft(JAXBElement<String> value) {
        this.loft = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the bathroom property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getBathroom() {
        return bathroom;
    }

    /**
     * Sets the value of the bathroom property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setBathroom(JAXBElement<String> value) {
        this.bathroom = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the bathroomDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBathroomDesc() {
        return bathroomDesc;
    }

    /**
     * Sets the value of the bathroomDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBathroomDesc(String value) {
        this.bathroomDesc = value;
    }

    /**
     * Gets the value of the rollinShower property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRollinShower() {
        return rollinShower;
    }

    /**
     * Sets the value of the rollinShower property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRollinShower(String value) {
        this.rollinShower = value;
    }

    /**
     * Gets the value of the bathroomAccessible property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBathroomAccessible() {
        return bathroomAccessible;
    }

    /**
     * Sets the value of the bathroomAccessible property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBathroomAccessible(String value) {
        this.bathroomAccessible = value;
    }

    /**
     * Gets the value of the unitConfig property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitConfig() {
        return unitConfig;
    }

    /**
     * Sets the value of the unitConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitConfig(String value) {
        this.unitConfig = value;
    }

    /**
     * Gets the value of the accessibility property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAccessibility() {
        return accessibility;
    }

    /**
     * Sets the value of the accessibility property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAccessibility(JAXBElement<String> value) {
        this.accessibility = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the bedroomBathLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBedroomBathLocation() {
        return bedroomBathLocation;
    }

    /**
     * Sets the value of the bedroomBathLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBedroomBathLocation(String value) {
        this.bedroomBathLocation = value;
    }

    /**
     * Gets the value of the floorPlan property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFloorPlan() {
        return floorPlan;
    }

    /**
     * Sets the value of the floorPlan property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFloorPlan(String value) {
        this.floorPlan = value;
    }

    /**
     * Gets the value of the outdoorLivingSpaceInfo property.
     * 
     * @return
     *     possible object is
     *     {@link RoomFactualInfo.OutdoorLivingSpaceInfo }
     *     
     */
    public RoomFactualInfo.OutdoorLivingSpaceInfo getOutdoorLivingSpaceInfo() {
        return outdoorLivingSpaceInfo;
    }

    /**
     * Sets the value of the outdoorLivingSpaceInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoomFactualInfo.OutdoorLivingSpaceInfo }
     *     
     */
    public void setOutdoorLivingSpaceInfo(RoomFactualInfo.OutdoorLivingSpaceInfo value) {
        this.outdoorLivingSpaceInfo = value;
    }

    /**
     * Gets the value of the outdoorLivingSpaceWithFurniture property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutdoorLivingSpaceWithFurniture() {
        return outdoorLivingSpaceWithFurniture;
    }

    /**
     * Sets the value of the outdoorLivingSpaceWithFurniture property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutdoorLivingSpaceWithFurniture(String value) {
        this.outdoorLivingSpaceWithFurniture = value;
    }

    /**
     * Gets the value of the floorings property.
     * 
     * @return
     *     possible object is
     *     {@link RoomFactualInfo.Floorings }
     *     
     */
    public RoomFactualInfo.Floorings getFloorings() {
        return floorings;
    }

    /**
     * Sets the value of the floorings property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoomFactualInfo.Floorings }
     *     
     */
    public void setFloorings(RoomFactualInfo.Floorings value) {
        this.floorings = value;
    }

    /**
     * Gets the value of the coolingSystems property.
     * 
     * @return
     *     possible object is
     *     {@link RoomFactualInfo.CoolingSystems }
     *     
     */
    public RoomFactualInfo.CoolingSystems getCoolingSystems() {
        return coolingSystems;
    }

    /**
     * Sets the value of the coolingSystems property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoomFactualInfo.CoolingSystems }
     *     
     */
    public void setCoolingSystems(RoomFactualInfo.CoolingSystems value) {
        this.coolingSystems = value;
    }

    /**
     * Gets the value of the housekeeping property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHousekeeping() {
        return housekeeping;
    }

    /**
     * Sets the value of the housekeeping property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHousekeeping(String value) {
        this.housekeeping = value;
    }

    /**
     * Gets the value of the housekeepingFeeDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHousekeepingFeeDesc() {
        return housekeepingFeeDesc;
    }

    /**
     * Sets the value of the housekeepingFeeDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHousekeepingFeeDesc(String value) {
        this.housekeepingFeeDesc = value;
    }

    /**
     * Gets the value of the safebox property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSafebox() {
        return safebox;
    }

    /**
     * Sets the value of the safebox property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSafebox(String value) {
        this.safebox = value;
    }

    /**
     * Gets the value of the nonSmokingRoomsByRequest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNonSmokingRoomsByRequest() {
        return nonSmokingRoomsByRequest;
    }

    /**
     * Sets the value of the nonSmokingRoomsByRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNonSmokingRoomsByRequest(String value) {
        this.nonSmokingRoomsByRequest = value;
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
     * Gets the value of the safetyEquipment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSafetyEquipment() {
        return safetyEquipment;
    }

    /**
     * Sets the value of the safetyEquipment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSafetyEquipment(String value) {
        this.safetyEquipment = value;
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
     *         &lt;element name="coolingSystem" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
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
        "coolingSystem"
    })
    public static class CoolingSystems {

        protected List<String> coolingSystem;

        /**
         * Gets the value of the coolingSystem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the coolingSystem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCoolingSystem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getCoolingSystem() {
            if (coolingSystem == null) {
                coolingSystem = new ArrayList<String>();
            }
            return this.coolingSystem;
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
     *         &lt;element name="flooring" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
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
        "flooring"
    })
    public static class Floorings {

        protected List<String> flooring;

        /**
         * Gets the value of the flooring property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the flooring property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFlooring().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getFlooring() {
            if (flooring == null) {
                flooring = new ArrayList<String>();
            }
            return this.flooring;
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
     *         &lt;element name="outdoorLivingSpace" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
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
        "outdoorLivingSpace"
    })
    public static class OutdoorLivingSpaceInfo {

        protected List<String> outdoorLivingSpace;

        /**
         * Gets the value of the outdoorLivingSpace property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the outdoorLivingSpace property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOutdoorLivingSpace().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getOutdoorLivingSpace() {
            if (outdoorLivingSpace == null) {
                outdoorLivingSpace = new ArrayList<String>();
            }
            return this.outdoorLivingSpace;
        }

    }

}
