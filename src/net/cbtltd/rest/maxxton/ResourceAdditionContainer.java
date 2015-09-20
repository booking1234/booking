
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResourceAdditionContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResourceAdditionContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResourceAdditions" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ResourceAdditionItem" type="{http://service.newyse.ws.services.newyse.maxxton/}ResourceAddition" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "ResourceAdditionContainer", propOrder = {
    "resourceAdditions"
})
public class ResourceAdditionContainer {

    @XmlElement(name = "ResourceAdditions")
    protected ResourceAdditionContainer.ResourceAdditions resourceAdditions;

    /**
     * Gets the value of the resourceAdditions property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceAdditionContainer.ResourceAdditions }
     *     
     */
    public ResourceAdditionContainer.ResourceAdditions getResourceAdditions() {
        return resourceAdditions;
    }

    /**
     * Sets the value of the resourceAdditions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceAdditionContainer.ResourceAdditions }
     *     
     */
    public void setResourceAdditions(ResourceAdditionContainer.ResourceAdditions value) {
        this.resourceAdditions = value;
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
     *         &lt;element name="ResourceAdditionItem" type="{http://service.newyse.ws.services.newyse.maxxton/}ResourceAddition" maxOccurs="unbounded" minOccurs="0"/>
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
        "resourceAdditionItem"
    })
    public static class ResourceAdditions {

        @XmlElement(name = "ResourceAdditionItem")
        protected List<ResourceAddition> resourceAdditionItem;

        /**
         * Gets the value of the resourceAdditionItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the resourceAdditionItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getResourceAdditionItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ResourceAddition }
         * 
         * 
         */
        public List<ResourceAddition> getResourceAdditionItem() {
            if (resourceAdditionItem == null) {
                resourceAdditionItem = new ArrayList<ResourceAddition>();
            }
            return this.resourceAdditionItem;
        }

    }

}
