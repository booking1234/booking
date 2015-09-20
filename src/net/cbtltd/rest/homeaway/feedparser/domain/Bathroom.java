
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
 * <p>Java class for Bathroom complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Bathroom">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="amenities" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="amenity" type="{}BathroomFeatureValue" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="name" type="{}Note" minOccurs="0"/>
 *         &lt;element name="note" type="{}Note" minOccurs="0"/>
 *         &lt;element name="roomSubType" type="{}BathroomRoomType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Bathroom", propOrder = {
    "amenities",
    "name",
    "note",
    "roomSubType"
})
public class Bathroom {

    protected Bathroom.Amenities amenities;
    protected Note name;
    protected Note note;
    @XmlElement(required = true)
    protected BathroomRoomType roomSubType;

    /**
     * Gets the value of the amenities property.
     * 
     * @return
     *     possible object is
     *     {@link Bathroom.Amenities }
     *     
     */
    public Bathroom.Amenities getAmenities() {
        return amenities;
    }

    /**
     * Sets the value of the amenities property.
     * 
     * @param value
     *     allowed object is
     *     {@link Bathroom.Amenities }
     *     
     */
    public void setAmenities(Bathroom.Amenities value) {
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
     *     {@link BathroomRoomType }
     *     
     */
    public BathroomRoomType getRoomSubType() {
        return roomSubType;
    }

    /**
     * Sets the value of the roomSubType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BathroomRoomType }
     *     
     */
    public void setRoomSubType(BathroomRoomType value) {
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
     *         &lt;element name="amenity" type="{}BathroomFeatureValue" maxOccurs="unbounded" minOccurs="0"/>
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

        protected List<BathroomFeatureValue> amenity;

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
         * {@link BathroomFeatureValue }
         * 
         * 
         */
        public List<BathroomFeatureValue> getAmenity() {
            if (amenity == null) {
                amenity = new ArrayList<BathroomFeatureValue>();
            }
            return this.amenity;
        }

    }

}
