
package net.cbtltd.rest.rci;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resort-ai-tour-info complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resort-ai-tour-info">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="durations" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="restrictions" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="restriction" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="activityTypes" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="additionalSuggestions" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="additionalSuggestion" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="departureInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="departureTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="feeIncluded" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="picturesLink" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="lunchDrinksIncluded" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="notes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="suggestedAges" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="suggestedAge" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="tourName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tourReview" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemsIncluded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="item" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
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
@XmlType(name = "resort-ai-tour-info", propOrder = {
    "durations",
    "restrictions",
    "activityTypes",
    "additionalSuggestions",
    "departureInfo",
    "departureTime",
    "description",
    "feeIncluded",
    "picturesLink",
    "lunchDrinksIncluded",
    "notes",
    "cost",
    "suggestedAges",
    "tourName",
    "tourReview",
    "itemsIncluded"
})
public class ResortAiTourInfo {

    protected ResortAiTourInfo.Durations durations;
    protected ResortAiTourInfo.Restrictions restrictions;
    protected ResortAiTourInfo.ActivityTypes activityTypes;
    protected ResortAiTourInfo.AdditionalSuggestions additionalSuggestions;
    protected String departureInfo;
    protected String departureTime;
    protected String description;
    protected String feeIncluded;
    @XmlSchemaType(name = "anyURI")
    protected String picturesLink;
    protected String lunchDrinksIncluded;
    protected String notes;
    protected String cost;
    protected ResortAiTourInfo.SuggestedAges suggestedAges;
    protected String tourName;
    protected String tourReview;
    protected ResortAiTourInfo.ItemsIncluded itemsIncluded;

    /**
     * Gets the value of the durations property.
     * 
     * @return
     *     possible object is
     *     {@link ResortAiTourInfo.Durations }
     *     
     */
    public ResortAiTourInfo.Durations getDurations() {
        return durations;
    }

    /**
     * Sets the value of the durations property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortAiTourInfo.Durations }
     *     
     */
    public void setDurations(ResortAiTourInfo.Durations value) {
        this.durations = value;
    }

    /**
     * Gets the value of the restrictions property.
     * 
     * @return
     *     possible object is
     *     {@link ResortAiTourInfo.Restrictions }
     *     
     */
    public ResortAiTourInfo.Restrictions getRestrictions() {
        return restrictions;
    }

    /**
     * Sets the value of the restrictions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortAiTourInfo.Restrictions }
     *     
     */
    public void setRestrictions(ResortAiTourInfo.Restrictions value) {
        this.restrictions = value;
    }

    /**
     * Gets the value of the activityTypes property.
     * 
     * @return
     *     possible object is
     *     {@link ResortAiTourInfo.ActivityTypes }
     *     
     */
    public ResortAiTourInfo.ActivityTypes getActivityTypes() {
        return activityTypes;
    }

    /**
     * Sets the value of the activityTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortAiTourInfo.ActivityTypes }
     *     
     */
    public void setActivityTypes(ResortAiTourInfo.ActivityTypes value) {
        this.activityTypes = value;
    }

    /**
     * Gets the value of the additionalSuggestions property.
     * 
     * @return
     *     possible object is
     *     {@link ResortAiTourInfo.AdditionalSuggestions }
     *     
     */
    public ResortAiTourInfo.AdditionalSuggestions getAdditionalSuggestions() {
        return additionalSuggestions;
    }

    /**
     * Sets the value of the additionalSuggestions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortAiTourInfo.AdditionalSuggestions }
     *     
     */
    public void setAdditionalSuggestions(ResortAiTourInfo.AdditionalSuggestions value) {
        this.additionalSuggestions = value;
    }

    /**
     * Gets the value of the departureInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartureInfo() {
        return departureInfo;
    }

    /**
     * Sets the value of the departureInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartureInfo(String value) {
        this.departureInfo = value;
    }

    /**
     * Gets the value of the departureTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the value of the departureTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartureTime(String value) {
        this.departureTime = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the feeIncluded property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFeeIncluded() {
        return feeIncluded;
    }

    /**
     * Sets the value of the feeIncluded property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFeeIncluded(String value) {
        this.feeIncluded = value;
    }

    /**
     * Gets the value of the picturesLink property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPicturesLink() {
        return picturesLink;
    }

    /**
     * Sets the value of the picturesLink property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPicturesLink(String value) {
        this.picturesLink = value;
    }

    /**
     * Gets the value of the lunchDrinksIncluded property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLunchDrinksIncluded() {
        return lunchDrinksIncluded;
    }

    /**
     * Sets the value of the lunchDrinksIncluded property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLunchDrinksIncluded(String value) {
        this.lunchDrinksIncluded = value;
    }

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotes(String value) {
        this.notes = value;
    }

    /**
     * Gets the value of the cost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCost() {
        return cost;
    }

    /**
     * Sets the value of the cost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCost(String value) {
        this.cost = value;
    }

    /**
     * Gets the value of the suggestedAges property.
     * 
     * @return
     *     possible object is
     *     {@link ResortAiTourInfo.SuggestedAges }
     *     
     */
    public ResortAiTourInfo.SuggestedAges getSuggestedAges() {
        return suggestedAges;
    }

    /**
     * Sets the value of the suggestedAges property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortAiTourInfo.SuggestedAges }
     *     
     */
    public void setSuggestedAges(ResortAiTourInfo.SuggestedAges value) {
        this.suggestedAges = value;
    }

    /**
     * Gets the value of the tourName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTourName() {
        return tourName;
    }

    /**
     * Sets the value of the tourName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTourName(String value) {
        this.tourName = value;
    }

    /**
     * Gets the value of the tourReview property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTourReview() {
        return tourReview;
    }

    /**
     * Sets the value of the tourReview property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTourReview(String value) {
        this.tourReview = value;
    }

    /**
     * Gets the value of the itemsIncluded property.
     * 
     * @return
     *     possible object is
     *     {@link ResortAiTourInfo.ItemsIncluded }
     *     
     */
    public ResortAiTourInfo.ItemsIncluded getItemsIncluded() {
        return itemsIncluded;
    }

    /**
     * Sets the value of the itemsIncluded property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortAiTourInfo.ItemsIncluded }
     *     
     */
    public void setItemsIncluded(ResortAiTourInfo.ItemsIncluded value) {
        this.itemsIncluded = value;
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
     *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
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
        "type"
    })
    public static class ActivityTypes {

        @XmlElement(required = true)
        protected List<String> type;

        /**
         * Gets the value of the type property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the type property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getType().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getType() {
            if (type == null) {
                type = new ArrayList<String>();
            }
            return this.type;
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
     *         &lt;element name="additionalSuggestion" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
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
        "additionalSuggestion"
    })
    public static class AdditionalSuggestions {

        @XmlElement(required = true)
        protected List<String> additionalSuggestion;

        /**
         * Gets the value of the additionalSuggestion property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the additionalSuggestion property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAdditionalSuggestion().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getAdditionalSuggestion() {
            if (additionalSuggestion == null) {
                additionalSuggestion = new ArrayList<String>();
            }
            return this.additionalSuggestion;
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
     *         &lt;element name="duration" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
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
        "duration"
    })
    public static class Durations {

        @XmlElement(required = true)
        protected List<String> duration;

        /**
         * Gets the value of the duration property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the duration property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDuration().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getDuration() {
            if (duration == null) {
                duration = new ArrayList<String>();
            }
            return this.duration;
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
     *         &lt;element name="item" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
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
        "item"
    })
    public static class ItemsIncluded {

        @XmlElement(required = true)
        protected List<String> item;

        /**
         * Gets the value of the item property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the item property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getItem() {
            if (item == null) {
                item = new ArrayList<String>();
            }
            return this.item;
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
     *         &lt;element name="restriction" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
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
        "restriction"
    })
    public static class Restrictions {

        @XmlElement(required = true)
        protected List<String> restriction;

        /**
         * Gets the value of the restriction property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the restriction property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRestriction().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getRestriction() {
            if (restriction == null) {
                restriction = new ArrayList<String>();
            }
            return this.restriction;
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
     *         &lt;element name="suggestedAge" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
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
        "suggestedAge"
    })
    public static class SuggestedAges {

        @XmlElement(required = true)
        protected List<String> suggestedAge;

        /**
         * Gets the value of the suggestedAge property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the suggestedAge property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSuggestedAge().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getSuggestedAge() {
            if (suggestedAge == null) {
                suggestedAge = new ArrayList<String>();
            }
            return this.suggestedAge;
        }

    }

}
