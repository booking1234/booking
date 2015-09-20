
package net.cbtltd.rest.rci;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resort-dining-info complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resort-dining-info">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="proximity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="distanceInKms" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="distanceInMiles" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="cafeOrCoffeeShop" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="snackBar" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="barbequeArea" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="breakFastAvailable" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="roomServiceAvailable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="eventCatering" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="banquetFacilities" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resort-dining-info", propOrder = {
    "proximity",
    "distanceInKms",
    "distanceInMiles",
    "type",
    "cafeOrCoffeeShop",
    "snackBar",
    "barbequeArea",
    "breakFastAvailable",
    "roomServiceAvailable",
    "eventCatering",
    "banquetFacilities"
})
public class ResortDiningInfo {

    protected String proximity;
    protected Double distanceInKms;
    protected Double distanceInMiles;
    protected List<String> type;
    protected String cafeOrCoffeeShop;
    protected String snackBar;
    protected String barbequeArea;
    protected List<String> breakFastAvailable;
    protected String roomServiceAvailable;
    protected String eventCatering;
    protected String banquetFacilities;

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
     * Gets the value of the type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getType() {
        if (type == null) {
            type = new ArrayList<String>();
        }
        return this.type;
    }

    /**
     * Gets the value of the cafeOrCoffeeShop property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCafeOrCoffeeShop() {
        return cafeOrCoffeeShop;
    }

    /**
     * Sets the value of the cafeOrCoffeeShop property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCafeOrCoffeeShop(String value) {
        this.cafeOrCoffeeShop = value;
    }

    /**
     * Gets the value of the snackBar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSnackBar() {
        return snackBar;
    }

    /**
     * Sets the value of the snackBar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSnackBar(String value) {
        this.snackBar = value;
    }

    /**
     * Gets the value of the barbequeArea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBarbequeArea() {
        return barbequeArea;
    }

    /**
     * Sets the value of the barbequeArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBarbequeArea(String value) {
        this.barbequeArea = value;
    }

    /**
     * Gets the value of the breakFastAvailable property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the breakFastAvailable property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBreakFastAvailable().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getBreakFastAvailable() {
        if (breakFastAvailable == null) {
            breakFastAvailable = new ArrayList<String>();
        }
        return this.breakFastAvailable;
    }

    /**
     * Gets the value of the roomServiceAvailable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoomServiceAvailable() {
        return roomServiceAvailable;
    }

    /**
     * Sets the value of the roomServiceAvailable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoomServiceAvailable(String value) {
        this.roomServiceAvailable = value;
    }

    /**
     * Gets the value of the eventCatering property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventCatering() {
        return eventCatering;
    }

    /**
     * Sets the value of the eventCatering property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventCatering(String value) {
        this.eventCatering = value;
    }

    /**
     * Gets the value of the banquetFacilities property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBanquetFacilities() {
        return banquetFacilities;
    }

    /**
     * Sets the value of the banquetFacilities property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBanquetFacilities(String value) {
        this.banquetFacilities = value;
    }

}
