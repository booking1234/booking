
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccommodationTypeSearchContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccommodationTypeSearchContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AccommodationTypes" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="AccommodationTypeItem" type="{http://service.newyse.ws.services.newyse.maxxton/}AccommodationTypeSearch" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "AccommodationTypeSearchContainer", propOrder = {
    "accommodationTypes"
})
public class AccommodationTypeSearchContainer {

    @XmlElement(name = "AccommodationTypes")
    protected AccommodationTypeSearchContainer.AccommodationTypes accommodationTypes;

    /**
     * Gets the value of the accommodationTypes property.
     * 
     * @return
     *     possible object is
     *     {@link AccommodationTypeSearchContainer.AccommodationTypes }
     *     
     */
    public AccommodationTypeSearchContainer.AccommodationTypes getAccommodationTypes() {
        return accommodationTypes;
    }

    /**
     * Sets the value of the accommodationTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccommodationTypeSearchContainer.AccommodationTypes }
     *     
     */
    public void setAccommodationTypes(AccommodationTypeSearchContainer.AccommodationTypes value) {
        this.accommodationTypes = value;
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
     *         &lt;element name="AccommodationTypeItem" type="{http://service.newyse.ws.services.newyse.maxxton/}AccommodationTypeSearch" maxOccurs="unbounded" minOccurs="0"/>
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
        "accommodationTypeItem"
    })
    public static class AccommodationTypes {

        @XmlElement(name = "AccommodationTypeItem")
        protected List<AccommodationTypeSearch> accommodationTypeItem;

        /**
         * Gets the value of the accommodationTypeItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the accommodationTypeItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAccommodationTypeItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AccommodationTypeSearch }
         * 
         * 
         */
        public List<AccommodationTypeSearch> getAccommodationTypeItem() {
            if (accommodationTypeItem == null) {
                accommodationTypeItem = new ArrayList<AccommodationTypeSearch>();
            }
            return this.accommodationTypeItem;
        }

    }

}
