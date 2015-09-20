
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ObjectAvailabilityContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ObjectAvailabilityContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ObjectAvailabilities" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ObjectAvailabilityItem" type="{http://service.newyse.ws.services.newyse.maxxton/}ObjectAvailability" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "ObjectAvailabilityContainer", propOrder = {
    "objectAvailabilities"
})
public class ObjectAvailabilityContainer {

    @XmlElement(name = "ObjectAvailabilities")
    protected ObjectAvailabilityContainer.ObjectAvailabilities objectAvailabilities;

    /**
     * Gets the value of the objectAvailabilities property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectAvailabilityContainer.ObjectAvailabilities }
     *     
     */
    public ObjectAvailabilityContainer.ObjectAvailabilities getObjectAvailabilities() {
        return objectAvailabilities;
    }

    /**
     * Sets the value of the objectAvailabilities property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectAvailabilityContainer.ObjectAvailabilities }
     *     
     */
    public void setObjectAvailabilities(ObjectAvailabilityContainer.ObjectAvailabilities value) {
        this.objectAvailabilities = value;
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
     *         &lt;element name="ObjectAvailabilityItem" type="{http://service.newyse.ws.services.newyse.maxxton/}ObjectAvailability" maxOccurs="unbounded" minOccurs="0"/>
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
        "objectAvailabilityItem"
    })
    public static class ObjectAvailabilities {

        @XmlElement(name = "ObjectAvailabilityItem")
        protected List<ObjectAvailability> objectAvailabilityItem;

        /**
         * Gets the value of the objectAvailabilityItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the objectAvailabilityItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getObjectAvailabilityItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ObjectAvailability }
         * 
         * 
         */
        public List<ObjectAvailability> getObjectAvailabilityItem() {
            if (objectAvailabilityItem == null) {
                objectAvailabilityItem = new ArrayList<ObjectAvailability>();
            }
            return this.objectAvailabilityItem;
        }

    }

}
