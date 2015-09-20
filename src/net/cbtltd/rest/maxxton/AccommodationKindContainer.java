
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccommodationKindContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccommodationKindContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AccommodationKinds" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="AccommodationKindItem" type="{http://service.newyse.ws.services.newyse.maxxton/}AccommodationKind" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "AccommodationKindContainer", propOrder = {
    "accommodationKinds"
})
public class AccommodationKindContainer {

    @XmlElement(name = "AccommodationKinds")
    protected AccommodationKindContainer.AccommodationKinds accommodationKinds;

    /**
     * Gets the value of the accommodationKinds property.
     * 
     * @return
     *     possible object is
     *     {@link AccommodationKindContainer.AccommodationKinds }
     *     
     */
    public AccommodationKindContainer.AccommodationKinds getAccommodationKinds() {
        return accommodationKinds;
    }

    /**
     * Sets the value of the accommodationKinds property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccommodationKindContainer.AccommodationKinds }
     *     
     */
    public void setAccommodationKinds(AccommodationKindContainer.AccommodationKinds value) {
        this.accommodationKinds = value;
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
     *         &lt;element name="AccommodationKindItem" type="{http://service.newyse.ws.services.newyse.maxxton/}AccommodationKind" maxOccurs="unbounded" minOccurs="0"/>
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
        "accommodationKindItem"
    })
    public static class AccommodationKinds {

        @XmlElement(name = "AccommodationKindItem")
        protected List<AccommodationKind> accommodationKindItem;

        /**
         * Gets the value of the accommodationKindItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the accommodationKindItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAccommodationKindItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AccommodationKind }
         * 
         * 
         */
        public List<AccommodationKind> getAccommodationKindItem() {
            if (accommodationKindItem == null) {
                accommodationKindItem = new ArrayList<AccommodationKind>();
            }
            return this.accommodationKindItem;
        }

    }

}
