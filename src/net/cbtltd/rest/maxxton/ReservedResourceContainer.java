
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReservedResourceContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReservedResourceContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="reservedResourceList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ReservedResource" type="{http://service.newyse.ws.services.newyse.maxxton/}WSReservedResource" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "ReservedResourceContainer", propOrder = {
    "reservedResourceList"
})
public class ReservedResourceContainer {

    protected ReservedResourceContainer.ReservedResourceList reservedResourceList;

    /**
     * Gets the value of the reservedResourceList property.
     * 
     * @return
     *     possible object is
     *     {@link ReservedResourceContainer.ReservedResourceList }
     *     
     */
    public ReservedResourceContainer.ReservedResourceList getReservedResourceList() {
        return reservedResourceList;
    }

    /**
     * Sets the value of the reservedResourceList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReservedResourceContainer.ReservedResourceList }
     *     
     */
    public void setReservedResourceList(ReservedResourceContainer.ReservedResourceList value) {
        this.reservedResourceList = value;
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
     *         &lt;element name="ReservedResource" type="{http://service.newyse.ws.services.newyse.maxxton/}WSReservedResource" maxOccurs="unbounded" minOccurs="0"/>
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
        "reservedResource"
    })
    public static class ReservedResourceList {

        @XmlElement(name = "ReservedResource")
        protected List<WSReservedResource> reservedResource;

        /**
         * Gets the value of the reservedResource property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the reservedResource property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getReservedResource().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link WSReservedResource }
         * 
         * 
         */
        public List<WSReservedResource> getReservedResource() {
            if (reservedResource == null) {
                reservedResource = new ArrayList<WSReservedResource>();
            }
            return this.reservedResource;
        }

    }

}
