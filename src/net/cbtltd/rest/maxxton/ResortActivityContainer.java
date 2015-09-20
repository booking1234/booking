
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResortActivityContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResortActivityContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResortActivities" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ResortActivityItem" type="{http://service.newyse.ws.services.newyse.maxxton/}ResortActivity" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "ResortActivityContainer", propOrder = {
    "resortActivities"
})
public class ResortActivityContainer {

    @XmlElement(name = "ResortActivities")
    protected ResortActivityContainer.ResortActivities resortActivities;

    /**
     * Gets the value of the resortActivities property.
     * 
     * @return
     *     possible object is
     *     {@link ResortActivityContainer.ResortActivities }
     *     
     */
    public ResortActivityContainer.ResortActivities getResortActivities() {
        return resortActivities;
    }

    /**
     * Sets the value of the resortActivities property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortActivityContainer.ResortActivities }
     *     
     */
    public void setResortActivities(ResortActivityContainer.ResortActivities value) {
        this.resortActivities = value;
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
     *         &lt;element name="ResortActivityItem" type="{http://service.newyse.ws.services.newyse.maxxton/}ResortActivity" maxOccurs="unbounded" minOccurs="0"/>
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
        "resortActivityItem"
    })
    public static class ResortActivities {

        @XmlElement(name = "ResortActivityItem")
        protected List<ResortActivity> resortActivityItem;

        /**
         * Gets the value of the resortActivityItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the resortActivityItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getResortActivityItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ResortActivity }
         * 
         * 
         */
        public List<ResortActivity> getResortActivityItem() {
            if (resortActivityItem == null) {
                resortActivityItem = new ArrayList<ResortActivity>();
            }
            return this.resortActivityItem;
        }

    }

}
