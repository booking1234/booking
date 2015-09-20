
package net.cbtltd.rest.rci;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for unit-bedroom-config complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="unit-bedroom-config">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sleepingArrangements" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="sleepingArrangement" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="babyFacilities" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="babyFacility" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
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
@XmlType(name = "unit-bedroom-config", propOrder = {
    "sleepingArrangements",
    "babyFacilities"
})
public class UnitBedroomConfig {

    protected UnitBedroomConfig.SleepingArrangements sleepingArrangements;
    protected UnitBedroomConfig.BabyFacilities babyFacilities;

    /**
     * Gets the value of the sleepingArrangements property.
     * 
     * @return
     *     possible object is
     *     {@link UnitBedroomConfig.SleepingArrangements }
     *     
     */
    public UnitBedroomConfig.SleepingArrangements getSleepingArrangements() {
        return sleepingArrangements;
    }

    /**
     * Sets the value of the sleepingArrangements property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitBedroomConfig.SleepingArrangements }
     *     
     */
    public void setSleepingArrangements(UnitBedroomConfig.SleepingArrangements value) {
        this.sleepingArrangements = value;
    }

    /**
     * Gets the value of the babyFacilities property.
     * 
     * @return
     *     possible object is
     *     {@link UnitBedroomConfig.BabyFacilities }
     *     
     */
    public UnitBedroomConfig.BabyFacilities getBabyFacilities() {
        return babyFacilities;
    }

    /**
     * Sets the value of the babyFacilities property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitBedroomConfig.BabyFacilities }
     *     
     */
    public void setBabyFacilities(UnitBedroomConfig.BabyFacilities value) {
        this.babyFacilities = value;
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
     *         &lt;element name="babyFacility" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
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
        "babyFacility"
    })
    public static class BabyFacilities {

        @XmlElement(required = true)
        protected List<String> babyFacility;

        /**
         * Gets the value of the babyFacility property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the babyFacility property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBabyFacility().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getBabyFacility() {
            if (babyFacility == null) {
                babyFacility = new ArrayList<String>();
            }
            return this.babyFacility;
        }

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
     *         &lt;element name="sleepingArrangement" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
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
        "sleepingArrangement"
    })
    public static class SleepingArrangements {

        @XmlElement(required = true)
        protected List<String> sleepingArrangement;

        /**
         * Gets the value of the sleepingArrangement property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the sleepingArrangement property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSleepingArrangement().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getSleepingArrangement() {
            if (sleepingArrangement == null) {
                sleepingArrangement = new ArrayList<String>();
            }
            return this.sleepingArrangement;
        }

    }

}
