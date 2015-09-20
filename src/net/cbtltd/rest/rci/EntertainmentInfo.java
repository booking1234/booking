
package net.cbtltd.rest.rci;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for entertainment-info complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="entertainment-info">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="furnishings" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="furnishing" type="{}entertainment-furnishing" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="television" type="{}unit-television-info" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "entertainment-info", propOrder = {
    "furnishings",
    "television"
})
public class EntertainmentInfo {

    protected EntertainmentInfo.Furnishings furnishings;
    protected UnitTelevisionInfo television;

    /**
     * Gets the value of the furnishings property.
     * 
     * @return
     *     possible object is
     *     {@link EntertainmentInfo.Furnishings }
     *     
     */
    public EntertainmentInfo.Furnishings getFurnishings() {
        return furnishings;
    }

    /**
     * Sets the value of the furnishings property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntertainmentInfo.Furnishings }
     *     
     */
    public void setFurnishings(EntertainmentInfo.Furnishings value) {
        this.furnishings = value;
    }

    /**
     * Gets the value of the television property.
     * 
     * @return
     *     possible object is
     *     {@link UnitTelevisionInfo }
     *     
     */
    public UnitTelevisionInfo getTelevision() {
        return television;
    }

    /**
     * Sets the value of the television property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitTelevisionInfo }
     *     
     */
    public void setTelevision(UnitTelevisionInfo value) {
        this.television = value;
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
     *         &lt;element name="furnishing" type="{}entertainment-furnishing" maxOccurs="unbounded"/>
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
        "furnishing"
    })
    public static class Furnishings {

        @XmlElement(required = true)
        protected List<EntertainmentFurnishing> furnishing;

        /**
         * Gets the value of the furnishing property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the furnishing property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFurnishing().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link EntertainmentFurnishing }
         * 
         * 
         */
        public List<EntertainmentFurnishing> getFurnishing() {
            if (furnishing == null) {
                furnishing = new ArrayList<EntertainmentFurnishing>();
            }
            return this.furnishing;
        }

    }

}
