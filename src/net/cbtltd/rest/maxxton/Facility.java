
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Facility complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Facility">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FacilityId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="Priority" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Language" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ImageURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressmanagerId" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
@XmlType(name = "Facility", propOrder = {
    "facilityId",
    "priority",
    "code",
    "language",
    "name",
    "description",
    "imageURL",
    "addressmanagerId",
    "resorts",
    "openingTimes"
})
public class Facility {

    @XmlElement(name = "FacilityId")
    protected Long facilityId;
    @XmlElement(name = "Priority")
    protected Long priority;
    @XmlElement(name = "Code")
    protected String code;
    @XmlElement(name = "Language")
    protected String language;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "ImageURL")
    protected String imageURL;
    @XmlElement(name = "AddressmanagerId")
    protected long addressmanagerId;
    @XmlElement(name = "Resorts")
    protected Facility.Resorts resorts;
    @XmlElement(name = "OpeningTimes")
    protected Facility.OpeningTimes openingTimes;

    /**
     * Gets the value of the facilityId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getFacilityId() {
        return facilityId;
    }

    /**
     * Sets the value of the facilityId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setFacilityId(Long value) {
        this.facilityId = value;
    }

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPriority(Long value) {
        this.priority = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
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
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
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
     * Gets the value of the addressmanagerId property.
     * 
     */
    public long getAddressmanagerId() {
        return addressmanagerId;
    }

    /**
     * Sets the value of the addressmanagerId property.
     * 
     */
    public void setAddressmanagerId(long value) {
        this.addressmanagerId = value;
    }

    /**
     * Gets the value of the resorts property.
     * 
     * @return
     *     possible object is
     *     {@link Facility.Resorts }
     *     
     */
    public Facility.Resorts getResorts() {
        return resorts;
    }

    /**
     * Sets the value of the resorts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Facility.Resorts }
     *     
     */
    public void setResorts(Facility.Resorts value) {
        this.resorts = value;
    }

    /**
     * Gets the value of the openingTimes property.
     * 
     * @return
     *     possible object is
     *     {@link Facility.OpeningTimes }
     *     
     */
    public Facility.OpeningTimes getOpeningTimes() {
        return openingTimes;
    }

    /**
     * Sets the value of the openingTimes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Facility.OpeningTimes }
     *     
     */
    public void setOpeningTimes(Facility.OpeningTimes value) {
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
