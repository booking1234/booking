
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FacilityContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FacilityContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Facilities" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="FacilityItem" type="{http://service.newyse.ws.services.newyse.maxxton/}Facility" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "FacilityContainer", propOrder = {
    "facilities"
})
public class FacilityContainer {

    @XmlElement(name = "Facilities")
    protected FacilityContainer.Facilities facilities;

    /**
     * Gets the value of the facilities property.
     * 
     * @return
     *     possible object is
     *     {@link FacilityContainer.Facilities }
     *     
     */
    public FacilityContainer.Facilities getFacilities() {
        return facilities;
    }

    /**
     * Sets the value of the facilities property.
     * 
     * @param value
     *     allowed object is
     *     {@link FacilityContainer.Facilities }
     *     
     */
    public void setFacilities(FacilityContainer.Facilities value) {
        this.facilities = value;
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
     *         &lt;element name="FacilityItem" type="{http://service.newyse.ws.services.newyse.maxxton/}Facility" maxOccurs="unbounded" minOccurs="0"/>
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
        "facilityItem"
    })
    public static class Facilities {

        @XmlElement(name = "FacilityItem")
        protected List<Facility> facilityItem;

        /**
         * Gets the value of the facilityItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the facilityItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFacilityItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Facility }
         * 
         * 
         */
        public List<Facility> getFacilityItem() {
            if (facilityItem == null) {
                facilityItem = new ArrayList<Facility>();
            }
            return this.facilityItem;
        }

    }

}
