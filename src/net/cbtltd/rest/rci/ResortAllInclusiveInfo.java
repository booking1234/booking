
package net.cbtltd.rest.rci;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resort-all-inclusive-info complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resort-all-inclusive-info">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="golfCourses" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="golfCourse" type="{}resort-ai-golf-course-info" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="resortFees" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="fee" type="{}resort-ai-fee-info" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="feeGeneral" type="{}resort-ai-fee-general" minOccurs="0"/>
 *         &lt;element name="restaurants" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="restaurant" type="{}resort-ai-restaurant-info" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="specialPromotion" type="{}resort-ai-special-promotion-info" minOccurs="0"/>
 *         &lt;element name="tours" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="tour" type="{}resort-ai-tour-info" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="watersportsSection" type="{}resort-ai-watersports-section-info" minOccurs="0"/>
 *         &lt;element name="visitorRules" type="{}resort-ai-vistor-rules-info" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resort-all-inclusive-info", propOrder = {
    "golfCourses",
    "resortFees",
    "feeGeneral",
    "restaurants",
    "specialPromotion",
    "tours",
    "watersportsSection",
    "visitorRules"
})
public class ResortAllInclusiveInfo {

    protected ResortAllInclusiveInfo.GolfCourses golfCourses;
    protected ResortAllInclusiveInfo.ResortFees resortFees;
    protected ResortAiFeeGeneral feeGeneral;
    protected ResortAllInclusiveInfo.Restaurants restaurants;
    protected ResortAiSpecialPromotionInfo specialPromotion;
    protected ResortAllInclusiveInfo.Tours tours;
    protected ResortAiWatersportsSectionInfo watersportsSection;
    protected ResortAiVistorRulesInfo visitorRules;

    /**
     * Gets the value of the golfCourses property.
     * 
     * @return
     *     possible object is
     *     {@link ResortAllInclusiveInfo.GolfCourses }
     *     
     */
    public ResortAllInclusiveInfo.GolfCourses getGolfCourses() {
        return golfCourses;
    }

    /**
     * Sets the value of the golfCourses property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortAllInclusiveInfo.GolfCourses }
     *     
     */
    public void setGolfCourses(ResortAllInclusiveInfo.GolfCourses value) {
        this.golfCourses = value;
    }

    /**
     * Gets the value of the resortFees property.
     * 
     * @return
     *     possible object is
     *     {@link ResortAllInclusiveInfo.ResortFees }
     *     
     */
    public ResortAllInclusiveInfo.ResortFees getResortFees() {
        return resortFees;
    }

    /**
     * Sets the value of the resortFees property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortAllInclusiveInfo.ResortFees }
     *     
     */
    public void setResortFees(ResortAllInclusiveInfo.ResortFees value) {
        this.resortFees = value;
    }

    /**
     * Gets the value of the feeGeneral property.
     * 
     * @return
     *     possible object is
     *     {@link ResortAiFeeGeneral }
     *     
     */
    public ResortAiFeeGeneral getFeeGeneral() {
        return feeGeneral;
    }

    /**
     * Sets the value of the feeGeneral property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortAiFeeGeneral }
     *     
     */
    public void setFeeGeneral(ResortAiFeeGeneral value) {
        this.feeGeneral = value;
    }

    /**
     * Gets the value of the restaurants property.
     * 
     * @return
     *     possible object is
     *     {@link ResortAllInclusiveInfo.Restaurants }
     *     
     */
    public ResortAllInclusiveInfo.Restaurants getRestaurants() {
        return restaurants;
    }

    /**
     * Sets the value of the restaurants property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortAllInclusiveInfo.Restaurants }
     *     
     */
    public void setRestaurants(ResortAllInclusiveInfo.Restaurants value) {
        this.restaurants = value;
    }

    /**
     * Gets the value of the specialPromotion property.
     * 
     * @return
     *     possible object is
     *     {@link ResortAiSpecialPromotionInfo }
     *     
     */
    public ResortAiSpecialPromotionInfo getSpecialPromotion() {
        return specialPromotion;
    }

    /**
     * Sets the value of the specialPromotion property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortAiSpecialPromotionInfo }
     *     
     */
    public void setSpecialPromotion(ResortAiSpecialPromotionInfo value) {
        this.specialPromotion = value;
    }

    /**
     * Gets the value of the tours property.
     * 
     * @return
     *     possible object is
     *     {@link ResortAllInclusiveInfo.Tours }
     *     
     */
    public ResortAllInclusiveInfo.Tours getTours() {
        return tours;
    }

    /**
     * Sets the value of the tours property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortAllInclusiveInfo.Tours }
     *     
     */
    public void setTours(ResortAllInclusiveInfo.Tours value) {
        this.tours = value;
    }

    /**
     * Gets the value of the watersportsSection property.
     * 
     * @return
     *     possible object is
     *     {@link ResortAiWatersportsSectionInfo }
     *     
     */
    public ResortAiWatersportsSectionInfo getWatersportsSection() {
        return watersportsSection;
    }

    /**
     * Sets the value of the watersportsSection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortAiWatersportsSectionInfo }
     *     
     */
    public void setWatersportsSection(ResortAiWatersportsSectionInfo value) {
        this.watersportsSection = value;
    }

    /**
     * Gets the value of the visitorRules property.
     * 
     * @return
     *     possible object is
     *     {@link ResortAiVistorRulesInfo }
     *     
     */
    public ResortAiVistorRulesInfo getVisitorRules() {
        return visitorRules;
    }

    /**
     * Sets the value of the visitorRules property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortAiVistorRulesInfo }
     *     
     */
    public void setVisitorRules(ResortAiVistorRulesInfo value) {
        this.visitorRules = value;
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
     *         &lt;element name="golfCourse" type="{}resort-ai-golf-course-info" maxOccurs="unbounded"/>
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
        "golfCourse"
    })
    public static class GolfCourses {

        @XmlElement(required = true)
        protected List<ResortAiGolfCourseInfo> golfCourse;

        /**
         * Gets the value of the golfCourse property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the golfCourse property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getGolfCourse().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ResortAiGolfCourseInfo }
         * 
         * 
         */
        public List<ResortAiGolfCourseInfo> getGolfCourse() {
            if (golfCourse == null) {
                golfCourse = new ArrayList<ResortAiGolfCourseInfo>();
            }
            return this.golfCourse;
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
     *         &lt;element name="fee" type="{}resort-ai-fee-info" maxOccurs="unbounded"/>
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
        "fee"
    })
    public static class ResortFees {

        @XmlElement(required = true)
        protected List<ResortAiFeeInfo> fee;

        /**
         * Gets the value of the fee property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the fee property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFee().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ResortAiFeeInfo }
         * 
         * 
         */
        public List<ResortAiFeeInfo> getFee() {
            if (fee == null) {
                fee = new ArrayList<ResortAiFeeInfo>();
            }
            return this.fee;
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
     *         &lt;element name="restaurant" type="{}resort-ai-restaurant-info" maxOccurs="unbounded"/>
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
        "restaurant"
    })
    public static class Restaurants {

        @XmlElement(required = true)
        protected List<ResortAiRestaurantInfo> restaurant;

        /**
         * Gets the value of the restaurant property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the restaurant property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRestaurant().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ResortAiRestaurantInfo }
         * 
         * 
         */
        public List<ResortAiRestaurantInfo> getRestaurant() {
            if (restaurant == null) {
                restaurant = new ArrayList<ResortAiRestaurantInfo>();
            }
            return this.restaurant;
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
     *         &lt;element name="tour" type="{}resort-ai-tour-info" maxOccurs="unbounded"/>
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
        "tour"
    })
    public static class Tours {

        @XmlElement(required = true)
        protected List<ResortAiTourInfo> tour;

        /**
         * Gets the value of the tour property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the tour property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTour().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ResortAiTourInfo }
         * 
         * 
         */
        public List<ResortAiTourInfo> getTour() {
            if (tour == null) {
                tour = new ArrayList<ResortAiTourInfo>();
            }
            return this.tour;
        }

    }

}
