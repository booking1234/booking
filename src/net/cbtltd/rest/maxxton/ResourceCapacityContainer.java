
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResourceCapacityContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResourceCapacityContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResourceCapacity" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ResourceCapacityItem" type="{http://service.newyse.ws.services.newyse.maxxton/}WSResourceCapacity" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "ResourceCapacityContainer", propOrder = {
    "resourceCapacity"
})
public class ResourceCapacityContainer {

    @XmlElement(name = "ResourceCapacity")
    protected ResourceCapacityContainer.ResourceCapacity resourceCapacity;

    /**
     * Gets the value of the resourceCapacity property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceCapacityContainer.ResourceCapacity }
     *     
     */
    public ResourceCapacityContainer.ResourceCapacity getResourceCapacity() {
        return resourceCapacity;
    }

    /**
     * Sets the value of the resourceCapacity property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceCapacityContainer.ResourceCapacity }
     *     
     */
    public void setResourceCapacity(ResourceCapacityContainer.ResourceCapacity value) {
        this.resourceCapacity = value;
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
     *         &lt;element name="ResourceCapacityItem" type="{http://service.newyse.ws.services.newyse.maxxton/}WSResourceCapacity" maxOccurs="unbounded" minOccurs="0"/>
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
        "resourceCapacityItem"
    })
    public static class ResourceCapacity {

        @XmlElement(name = "ResourceCapacityItem")
        protected List<WSResourceCapacity> resourceCapacityItem;

        /**
         * Gets the value of the resourceCapacityItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the resourceCapacityItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getResourceCapacityItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link WSResourceCapacity }
         * 
         * 
         */
        public List<WSResourceCapacity> getResourceCapacityItem() {
            if (resourceCapacityItem == null) {
                resourceCapacityItem = new ArrayList<WSResourceCapacity>();
            }
            return this.resourceCapacityItem;
        }

    }

}
