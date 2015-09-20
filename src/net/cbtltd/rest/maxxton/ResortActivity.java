
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResortActivity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResortActivity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResortActivityId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Language" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HeadText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Text" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Reserve" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ImageURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ImagemanagerId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PriceChild" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PriceValuePoints" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PriceAdult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MinAttendees" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="MaxAttendees" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="MinAge" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="MaxAge" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ActivityCategories" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ActivityCategoryItem" type="{http://service.newyse.ws.services.newyse.maxxton/}ActivityCategory" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Resorts" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ResortItem" type="{http://service.newyse.ws.services.newyse.maxxton/}Resort" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="OpeningTimes" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="OpeningTimeItem" type="{http://service.newyse.ws.services.newyse.maxxton/}OpeningTime" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "ResortActivity", propOrder = {
    "resortActivityId",
    "title",
    "language",
    "headText",
    "text",
    "description",
    "reserve",
    "imageURL",
    "imagemanagerId",
    "priceChild",
    "priceValuePoints",
    "priceAdult",
    "minAttendees",
    "maxAttendees",
    "minAge",
    "maxAge",
    "activityCategories",
    "resorts",
    "openingTimes"
})
public class ResortActivity {

    @XmlElement(name = "ResortActivityId")
    protected Long resortActivityId;
    @XmlElement(name = "Title")
    protected String title;
    @XmlElement(name = "Language")
    protected String language;
    @XmlElementRef(name = "HeadText", type = JAXBElement.class)
    protected JAXBElement<String> headText;
    @XmlElementRef(name = "Text", type = JAXBElement.class)
    protected JAXBElement<String> text;
    @XmlElementRef(name = "Description", type = JAXBElement.class)
    protected JAXBElement<String> description;
    @XmlElementRef(name = "Reserve", type = JAXBElement.class)
    protected JAXBElement<String> reserve;
    @XmlElement(name = "ImageURL")
    protected String imageURL;
    @XmlElement(name = "ImagemanagerId", required = true)
    protected String imagemanagerId;
    @XmlElement(name = "PriceChild")
    protected String priceChild;
    @XmlElement(name = "PriceValuePoints")
    protected String priceValuePoints;
    @XmlElement(name = "PriceAdult")
    protected String priceAdult;
    @XmlElement(name = "MinAttendees")
    protected Integer minAttendees;
    @XmlElement(name = "MaxAttendees")
    protected Integer maxAttendees;
    @XmlElement(name = "MinAge")
    protected Integer minAge;
    @XmlElement(name = "MaxAge")
    protected Integer maxAge;
    @XmlElement(name = "ActivityCategories")
    protected ResortActivity.ActivityCategories activityCategories;
    @XmlElement(name = "Resorts")
    protected ResortActivity.Resorts resorts;
    @XmlElement(name = "OpeningTimes")
    protected ResortActivity.OpeningTimes openingTimes;

    /**
     * Gets the value of the resortActivityId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getResortActivityId() {
        return resortActivityId;
    }

    /**
     * Sets the value of the resortActivityId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setResortActivityId(Long value) {
        this.resortActivityId = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguage(String value) {
        this.language = value;
    }

    /**
     * Gets the value of the headText property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getHeadText() {
        return headText;
    }

    /**
     * Sets the value of the headText property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setHeadText(JAXBElement<String> value) {
        this.headText = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setText(JAXBElement<String> value) {
        this.text = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDescription(JAXBElement<String> value) {
        this.description = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the reserve property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getReserve() {
        return reserve;
    }

    /**
     * Sets the value of the reserve property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setReserve(JAXBElement<String> value) {
        this.reserve = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the imageURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * Sets the value of the imageURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImageURL(String value) {
        this.imageURL = value;
    }

    /**
     * Gets the value of the imagemanagerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImagemanagerId() {
        return imagemanagerId;
    }

    /**
     * Sets the value of the imagemanagerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImagemanagerId(String value) {
        this.imagemanagerId = value;
    }

    /**
     * Gets the value of the priceChild property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriceChild() {
        return priceChild;
    }

    /**
     * Sets the value of the priceChild property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriceChild(String value) {
        this.priceChild = value;
    }

    /**
     * Gets the value of the priceValuePoints property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriceValuePoints() {
        return priceValuePoints;
    }

    /**
     * Sets the value of the priceValuePoints property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriceValuePoints(String value) {
        this.priceValuePoints = value;
    }

    /**
     * Gets the value of the priceAdult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriceAdult() {
        return priceAdult;
    }

    /**
     * Sets the value of the priceAdult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriceAdult(String value) {
        this.priceAdult = value;
    }

    /**
     * Gets the value of the minAttendees property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinAttendees() {
        return minAttendees;
    }

    /**
     * Sets the value of the minAttendees property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinAttendees(Integer value) {
        this.minAttendees = value;
    }

    /**
     * Gets the value of the maxAttendees property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxAttendees() {
        return maxAttendees;
    }

    /**
     * Sets the value of the maxAttendees property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxAttendees(Integer value) {
        this.maxAttendees = value;
    }

    /**
     * Gets the value of the minAge property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMinAge() {
        return minAge;
    }

    /**
     * Sets the value of the minAge property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinAge(Integer value) {
        this.minAge = value;
    }

    /**
     * Gets the value of the maxAge property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxAge() {
        return maxAge;
    }

    /**
     * Sets the value of the maxAge property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxAge(Integer value) {
        this.maxAge = value;
    }

    /**
     * Gets the value of the activityCategories property.
     * 
     * @return
     *     possible object is
     *     {@link ResortActivity.ActivityCategories }
     *     
     */
    public ResortActivity.ActivityCategories getActivityCategories() {
        return activityCategories;
    }

    /**
     * Sets the value of the activityCategories property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortActivity.ActivityCategories }
     *     
     */
    public void setActivityCategories(ResortActivity.ActivityCategories value) {
        this.activityCategories = value;
    }

    /**
     * Gets the value of the resorts property.
     * 
     * @return
     *     possible object is
     *     {@link ResortActivity.Resorts }
     *     
     */
    public ResortActivity.Resorts getResorts() {
        return resorts;
    }

    /**
     * Sets the value of the resorts property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortActivity.Resorts }
     *     
     */
    public void setResorts(ResortActivity.Resorts value) {
        this.resorts = value;
    }

    /**
     * Gets the value of the openingTimes property.
     * 
     * @return
     *     possible object is
     *     {@link ResortActivity.OpeningTimes }
     *     
     */
    public ResortActivity.OpeningTimes getOpeningTimes() {
        return openingTimes;
    }

    /**
     * Sets the value of the openingTimes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResortActivity.OpeningTimes }
     *     
     */
    public void setOpeningTimes(ResortActivity.OpeningTimes value) {
        this.openingTimes = value;
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
     *         &lt;element name="ActivityCategoryItem" type="{http://service.newyse.ws.services.newyse.maxxton/}ActivityCategory" maxOccurs="unbounded" minOccurs="0"/>
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
        "activityCategoryItem"
    })
    public static class ActivityCategories {

        @XmlElement(name = "ActivityCategoryItem")
        protected List<ActivityCategory> activityCategoryItem;

        /**
         * Gets the value of the activityCategoryItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the activityCategoryItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getActivityCategoryItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ActivityCategory }
         * 
         * 
         */
        public List<ActivityCategory> getActivityCategoryItem() {
            if (activityCategoryItem == null) {
                activityCategoryItem = new ArrayList<ActivityCategory>();
            }
            return this.activityCategoryItem;
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
     *         &lt;element name="OpeningTimeItem" type="{http://service.newyse.ws.services.newyse.maxxton/}OpeningTime" maxOccurs="unbounded" minOccurs="0"/>
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
        "openingTimeItem"
    })
    public static class OpeningTimes {

        @XmlElement(name = "OpeningTimeItem")
        protected List<OpeningTime> openingTimeItem;

        /**
         * Gets the value of the openingTimeItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the openingTimeItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOpeningTimeItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OpeningTime }
         * 
         * 
         */
        public List<OpeningTime> getOpeningTimeItem() {
            if (openingTimeItem == null) {
                openingTimeItem = new ArrayList<OpeningTime>();
            }
            return this.openingTimeItem;
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
     *         &lt;element name="ResortItem" type="{http://service.newyse.ws.services.newyse.maxxton/}Resort" maxOccurs="unbounded" minOccurs="0"/>
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
        "resortItem"
    })
    public static class Resorts {

        @XmlElement(name = "ResortItem")
        protected List<Resort> resortItem;

        /**
         * Gets the value of the resortItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the resortItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getResortItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Resort }
         * 
         * 
         */
        public List<Resort> getResortItem() {
            if (resortItem == null) {
                resortItem = new ArrayList<Resort>();
            }
            return this.resortItem;
        }

    }

}
