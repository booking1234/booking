
package net.cbtltd.rest.rci;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for resort-check-in-check-out-info complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resort-check-in-check-out-info">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="checkInTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="checkOutTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="arrivalCheckInSpecialInstructions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="locationForCheckIn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="weeksResortCheckInDays" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="day" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="7"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="pointsResortCheckInDays" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="day" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="7"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="expressCheckInCheckout" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="afterHoursInstructions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resort-check-in-check-out-info", propOrder = {
    "checkInTime",
    "checkOutTime",
    "arrivalCheckInSpecialInstructions",
    "locationForCheckIn",
    "weeksResortCheckInDays",
    "pointsResortCheckInDays",
    "expressCheckInCheckout",
    "afterHoursInstructions"
})
public class ResortCheckInCheckOutInfo {

    protected String checkInTime;
    protected String checkOutTime;
    protected String arrivalCheckInSpecialInstructions;
    protected String locationForCheckIn;
    protected ResortCheckInCheckOutInfo.WeeksResortCheckInDays weeksResortCheckInDays;
    protected ResortCheckInCheckOutInfo.PointsResortCheckInDays pointsResortCheckInDays;
    protected String expressCheckInCheckout;
    protected String afterHoursInstructions;

    /**
     * Gets the value of the checkInTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckInTime() {
        return checkInTime;
    }

    /**
     * Sets the value of the checkInTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckInTime(String value) {
        this.checkInTime = value;
    }

    /**
     * Gets the value of the checkOutTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckOutTime() {
        return checkOutTime;
    }

    /**
     * Sets the value of the checkOutTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckOutTime(String value) {
        this.checkOutTime = value;
    }

    /**
     * Gets the value of the arrivalCheckInSpecialInstructions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArrivalCheckInSpecialInstructions() {
        return arrivalCheckInSpecialInstructions;
    }

    /**
     * Sets the value of the arrivalCheckInSpecialInstructions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArrivalCheckInSpecialInstructions(String value) {
        this.arrivalCheckInSpecialInstructions = value;
    }

    /**
     * Gets the value of the locationForCheckIn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocationForCheckIn() {
        return locationForCheckIn;
    }

    /**
     * Sets the value of the locationForCheckIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocationForCheckIn(String value) {
        this.locationForCheckIn = value;
    }

    /**
     * Gets the value of the weeksResortCheckInDays property.
     * 
     * @return
     *     possible object is
     *     {@link ResortCheckInCheckOutInfo.WeeksResortCheckInDays }
     *     
     */
    public ResortCheckInCheckOutInfo.WeeksResortCheckInDays getWeeksResortCheckInDays() {
        return weeksResortCheckInDays;
    }

    /**
     * Sets the value of the weeksResortCheckInDays property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortCheckInCheckOutInfo.WeeksResortCheckInDays }
     *     
     */
    public void setWeeksResortCheckInDays(ResortCheckInCheckOutInfo.WeeksResortCheckInDays value) {
        this.weeksResortCheckInDays = value;
    }

    /**
     * Gets the value of the pointsResortCheckInDays property.
     * 
     * @return
     *     possible object is
     *     {@link ResortCheckInCheckOutInfo.PointsResortCheckInDays }
     *     
     */
    public ResortCheckInCheckOutInfo.PointsResortCheckInDays getPointsResortCheckInDays() {
        return pointsResortCheckInDays;
    }

    /**
     * Sets the value of the pointsResortCheckInDays property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortCheckInCheckOutInfo.PointsResortCheckInDays }
     *     
     */
    public void setPointsResortCheckInDays(ResortCheckInCheckOutInfo.PointsResortCheckInDays value) {
        this.pointsResortCheckInDays = value;
    }

    /**
     * Gets the value of the expressCheckInCheckout property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpressCheckInCheckout() {
        return expressCheckInCheckout;
    }

    /**
     * Sets the value of the expressCheckInCheckout property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpressCheckInCheckout(String value) {
        this.expressCheckInCheckout = value;
    }

    /**
     * Gets the value of the afterHoursInstructions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAfterHoursInstructions() {
        return afterHoursInstructions;
    }

    /**
     * Sets the value of the afterHoursInstructions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAfterHoursInstructions(String value) {
        this.afterHoursInstructions = value;
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
     *         &lt;element name="day" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="7"/>
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
        "day"
    })
    public static class PointsResortCheckInDays {

        @XmlElement(required = true)
        protected List<String> day;

        /**
         * Gets the value of the day property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the day property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDay().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getDay() {
            if (day == null) {
                day = new ArrayList<String>();
            }
            return this.day;
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
     *         &lt;element name="day" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="7"/>
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
        "day"
    })
    public static class WeeksResortCheckInDays {

        @XmlElement(required = true)
        protected List<String> day;

        /**
         * Gets the value of the day property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the day property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDay().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getDay() {
            if (day == null) {
                day = new ArrayList<String>();
            }
            return this.day;
        }

    }

}
