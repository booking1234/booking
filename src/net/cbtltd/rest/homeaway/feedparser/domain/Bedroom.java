
package net.cbtltd.rest.homeaway.feedparser.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Structured data about a room.
 * 
 * <p>Java class for Bedroom complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Bedroom">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="amenities" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="amenity" type="{}BedroomFeatureValue" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="name" type="{}Note" minOccurs="0"/>
 *         &lt;element name="note" type="{}Note" minOccurs="0"/>
 *         &lt;element name="roomSubType" type="{}BedroomRoomType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Bedroom", propOrder = {
    "amenities",
    "name",
    "note",
    "roomSubType"
})
public class Bedroom {

    protected Bedroom.Amenities amenities;
    protected Note name;
    protected Note note;
    @XmlElement(required = true)
    protected BedroomRoomType roomSubType;

    /**
     * Gets the value of the amenities property.
     * 
     * @return
     *     possible object is
     *     {@link Bedroom.Amenities }
     *     
     */
    public Bedroom.Amenities getAmenities() {
        return amenities;
    }

    /**
     * Sets the value of the amenities property.
     * 
     * @param value
     *     allowed object is
     *     {@link Bedroom.Amenities }
     *     
     */
    public void setAmenities(Bedroom.Amenities value) {
        this.amenities = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link Note }
     *     
     */
    public Note getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link Note }
     *     
     */
    public void setName(Note value) {
        this.name = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * @return
     *     possible object is
     *     {@link Note }
     *     
     */
    public Note getNote() {
        return note;
    }

    /**
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link Note }
     *     
     */
    public void setNote(Note value) {
        this.note = value;
    }

    /**
     * Gets the value of the roomSubType property.
     * 
     * @return
     *     possible object is
     *     {@link BedroomRoomType }
     *     
     */
    public BedroomRoomType getRoomSubType() {
        return roomSubType;
    }

    /**
     * Sets the value of the roomSubType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BedroomRoomType }
     *     
     */
    public void setRoomSubType(BedroomRoomType value) {
        this.roomSubType = value;
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
     *         &lt;element name="amenity" type="{}BedroomFeatureValue" maxOccurs="unbounded" minOccurs="0"/>
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

        protected List<BedroomFeatureValue> amenity;

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
         * {@link BedroomFeatureValue }
         * 
         * 
         */
        public List<BedroomFeatureValue> getAmenity() {
            if (amenity == null) {
                amenity = new ArrayList<BedroomFeatureValue>();
            }
            return this.amenity;
        }

    }

}
