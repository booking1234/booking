
package net.cbtltd.rest.rci;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for unit-info complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="unit-info">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="roomFacts" type="{}room-factual-info"/>
 *         &lt;element name="amenities" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="amenity" type="{}amenity" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="entertainment" type="{}entertainment-info" minOccurs="0"/>
 *         &lt;element name="kitchen" type="{}unit-kitchen-info" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "unit-info", propOrder = {
    "roomFacts",
    "amenities",
    "entertainment",
    "kitchen"
})
public class UnitInfo {

    @XmlElement(required = true)
    protected RoomFactualInfo roomFacts;
    protected UnitInfo.Amenities amenities;
    protected EntertainmentInfo entertainment;
    protected UnitKitchenInfo kitchen;

    /**
     * Gets the value of the roomFacts property.
     * 
     * @return
     *     possible object is
     *     {@link RoomFactualInfo }
     *     
     */
    public RoomFactualInfo getRoomFacts() {
        return roomFacts;
    }

    /**
     * Sets the value of the roomFacts property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoomFactualInfo }
     *     
     */
    public void setRoomFacts(RoomFactualInfo value) {
        this.roomFacts = value;
    }

    /**
     * Gets the value of the amenities property.
     * 
     * @return
     *     possible object is
     *     {@link UnitInfo.Amenities }
     *     
     */
    public UnitInfo.Amenities getAmenities() {
        return amenities;
    }

    /**
     * Sets the value of the amenities property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitInfo.Amenities }
     *     
     */
    public void setAmenities(UnitInfo.Amenities value) {
        this.amenities = value;
    }

    /**
     * Gets the value of the entertainment property.
     * 
     * @return
     *     possible object is
     *     {@link EntertainmentInfo }
     *     
     */
    public EntertainmentInfo getEntertainment() {
        return entertainment;
    }

    /**
     * Sets the value of the entertainment property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntertainmentInfo }
     *     
     */
    public void setEntertainment(EntertainmentInfo value) {
        this.entertainment = value;
    }

    /**
     * Gets the value of the kitchen property.
     * 
     * @return
     *     possible object is
     *     {@link UnitKitchenInfo }
     *     
     */
    public UnitKitchenInfo getKitchen() {
        return kitchen;
    }

    /**
     * Sets the value of the kitchen property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitKitchenInfo }
     *     
     */
    public void setKitchen(UnitKitchenInfo value) {
        this.kitchen = value;
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
     *         &lt;element name="amenity" type="{}amenity" maxOccurs="unbounded"/>
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
        "amenity"
    })
    public static class Amenities {

        @XmlElement(required = true)
        protected List<Amenity> amenity;

        /**
         * Gets the value of the amenity property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the amenity property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAmenity().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Amenity }
         * 
         * 
         */
        public List<Amenity> getAmenity() {
            if (amenity == null) {
                amenity = new ArrayList<Amenity>();
            }
            return this.amenity;
        }

    }

}
