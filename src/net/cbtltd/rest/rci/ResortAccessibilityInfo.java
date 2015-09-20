
package net.cbtltd.rest.rci;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resort-accessibility-info complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resort-accessibility-info">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="features" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="feature" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="elevator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="equipmentForDeaf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="braileOrRaisedSignage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resort-accessibility-info", propOrder = {
    "features",
    "elevator",
    "equipmentForDeaf",
    "braileOrRaisedSignage"
})
public class ResortAccessibilityInfo {

    protected ResortAccessibilityInfo.Features features;
    protected String elevator;
    protected String equipmentForDeaf;
    protected String braileOrRaisedSignage;

    /**
     * Gets the value of the features property.
     * 
     * @return
     *     possible object is
     *     {@link ResortAccessibilityInfo.Features }
     *     
     */
    public ResortAccessibilityInfo.Features getFeatures() {
        return features;
    }

    /**
     * Sets the value of the features property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortAccessibilityInfo.Features }
     *     
     */
    public void setFeatures(ResortAccessibilityInfo.Features value) {
        this.features = value;
    }

    /**
     * Gets the value of the elevator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElevator() {
        return elevator;
    }

    /**
     * Sets the value of the elevator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElevator(String value) {
        this.elevator = value;
    }

    /**
     * Gets the value of the equipmentForDeaf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEquipmentForDeaf() {
        return equipmentForDeaf;
    }

    /**
     * Sets the value of the equipmentForDeaf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEquipmentForDeaf(String value) {
        this.equipmentForDeaf = value;
    }

    /**
     * Gets the value of the braileOrRaisedSignage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBraileOrRaisedSignage() {
        return braileOrRaisedSignage;
    }

    /**
     * Sets the value of the braileOrRaisedSignage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBraileOrRaisedSignage(String value) {
        this.braileOrRaisedSignage = value;
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
     *         &lt;element name="feature" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
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
        "feature"
    })
    public static class Features {

        @XmlElement(required = true)
        protected List<String> feature;

        /**
         * Gets the value of the feature property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the feature property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFeature().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getFeature() {
            if (feature == null) {
                feature = new ArrayList<String>();
            }
            return this.feature;
        }

    }

}
