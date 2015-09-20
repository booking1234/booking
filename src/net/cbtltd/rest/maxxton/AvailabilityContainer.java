
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AvailabilityContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AvailabilityContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Availabilities" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="AvailabilityItem" type="{http://service.newyse.ws.services.newyse.maxxton/}Availability" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "AvailabilityContainer", propOrder = {
    "availabilities"
})
public class AvailabilityContainer {

    @XmlElement(name = "Availabilities")
    protected AvailabilityContainer.Availabilities availabilities;

    /**
     * Gets the value of the availabilities property.
     * 
     * @return
     *     possible object is
     *     {@link AvailabilityContainer.Availabilities }
     *     
     */
    public AvailabilityContainer.Availabilities getAvailabilities() {
        return availabilities;
    }

    /**
     * Sets the value of the availabilities property.
     * 
     * @param value
     *     allowed object is
     *     {@link AvailabilityContainer.Availabilities }
     *     
     */
    public void setAvailabilities(AvailabilityContainer.Availabilities value) {
        this.availabilities = value;
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
     *         &lt;element name="AvailabilityItem" type="{http://service.newyse.ws.services.newyse.maxxton/}Availability" maxOccurs="unbounded" minOccurs="0"/>
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
        "availabilityItem"
    })
    public static class Availabilities {

        @XmlElement(name = "AvailabilityItem")
        protected List<Availability> availabilityItem;

        /**
         * Gets the value of the availabilityItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the availabilityItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAvailabilityItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Availability }
         * 
         * 
         */
        public List<Availability> getAvailabilityItem() {
            if (availabilityItem == null) {
                availabilityItem = new ArrayList<Availability>();
            }
            return this.availabilityItem;
        }

    }

}
