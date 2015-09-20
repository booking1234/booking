
package net.cbtltd.rest.rci;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resort-ai-restaurant-info complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resort-ai-restaurant-info">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cuisineType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dressCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="meantimeService" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reservationRequired" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="restaurantAwardDistinctions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="restaurantTypes" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="roomServiceAvailable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sampleMenu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="websiteAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resort-ai-restaurant-info", propOrder = {
    "cuisineType",
    "dressCode",
    "meantimeService",
    "reservationRequired",
    "restaurantAwardDistinctions",
    "name",
    "restaurantTypes",
    "roomServiceAvailable",
    "sampleMenu",
    "websiteAddress"
})
public class ResortAiRestaurantInfo {

    protected String cuisineType;
    protected String dressCode;
    protected String meantimeService;
    protected String reservationRequired;
    protected String restaurantAwardDistinctions;
    protected String name;
    protected ResortAiRestaurantInfo.RestaurantTypes restaurantTypes;
    protected String roomServiceAvailable;
    protected String sampleMenu;
    protected String websiteAddress;

    /**
     * Gets the value of the cuisineType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCuisineType() {
        return cuisineType;
    }

    /**
     * Sets the value of the cuisineType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCuisineType(String value) {
        this.cuisineType = value;
    }

    /**
     * Gets the value of the dressCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDressCode() {
        return dressCode;
    }

    /**
     * Sets the value of the dressCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDressCode(String value) {
        this.dressCode = value;
    }

    /**
     * Gets the value of the meantimeService property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeantimeService() {
        return meantimeService;
    }

    /**
     * Sets the value of the meantimeService property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeantimeService(String value) {
        this.meantimeService = value;
    }

    /**
     * Gets the value of the reservationRequired property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReservationRequired() {
        return reservationRequired;
    }

    /**
     * Sets the value of the reservationRequired property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReservationRequired(String value) {
        this.reservationRequired = value;
    }

    /**
     * Gets the value of the restaurantAwardDistinctions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRestaurantAwardDistinctions() {
        return restaurantAwardDistinctions;
    }

    /**
     * Sets the value of the restaurantAwardDistinctions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRestaurantAwardDistinctions(String value) {
        this.restaurantAwardDistinctions = value;
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
     * Gets the value of the restaurantTypes property.
     * 
     * @return
     *     possible object is
     *     {@link ResortAiRestaurantInfo.RestaurantTypes }
     *     
     */
    public ResortAiRestaurantInfo.RestaurantTypes getRestaurantTypes() {
        return restaurantTypes;
    }

    /**
     * Sets the value of the restaurantTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortAiRestaurantInfo.RestaurantTypes }
     *     
     */
    public void setRestaurantTypes(ResortAiRestaurantInfo.RestaurantTypes value) {
        this.restaurantTypes = value;
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
     * Gets the value of the sampleMenu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSampleMenu() {
        return sampleMenu;
    }

    /**
     * Sets the value of the sampleMenu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSampleMenu(String value) {
        this.sampleMenu = value;
    }

    /**
     * Gets the value of the websiteAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebsiteAddress() {
        return websiteAddress;
    }

    /**
     * Sets the value of the websiteAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebsiteAddress(String value) {
        this.websiteAddress = value;
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
     *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
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
        "type"
    })
    public static class RestaurantTypes {

        protected List<String> type;

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

    }

}
