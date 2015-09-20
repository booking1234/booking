
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AccommodationTypeSearch complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccommodationTypeSearch">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResourceId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ShortDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Description2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PropertyManagerId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ImageManagerId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ResortId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ResortCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="AccommodationTypeSearchObjects" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="AccommodationTypeSearchObjectItem" type="{http://service.newyse.ws.services.newyse.maxxton/}AccommodationTypeSearchObject" maxOccurs="unbounded"/>
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
@XmlType(name = "AccommodationTypeSearch", propOrder = {
    "resourceId",
    "code",
    "name",
    "shortDescription",
    "description",
    "description2",
    "propertyManagerId",
    "imageManagerId",
    "resortId",
    "resortCode",
    "startDate",
    "endDate",
    "accommodationTypeSearchObjects"
})
public class AccommodationTypeSearch {

    @XmlElement(name = "ResourceId")
    protected long resourceId;
    @XmlElement(name = "Code", required = true)
    protected String code;
    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "ShortDescription", required = true)
    protected String shortDescription;
    @XmlElement(name = "Description", required = true)
    protected String description;
    @XmlElement(name = "Description2", required = true)
    protected String description2;
    @XmlElement(name = "PropertyManagerId")
    protected long propertyManagerId;
    @XmlElement(name = "ImageManagerId")
    protected long imageManagerId;
    @XmlElement(name = "ResortId")
    protected long resortId;
    @XmlElement(name = "ResortCode", required = true)
    protected String resortCode;
    @XmlElement(name = "StartDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDate;
    @XmlElement(name = "EndDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    @XmlElement(name = "AccommodationTypeSearchObjects")
    protected AccommodationTypeSearch.AccommodationTypeSearchObjects accommodationTypeSearchObjects;

    /**
     * Gets the value of the resourceId property.
     * 
     */
    public long getResourceId() {
        return resourceId;
    }

    /**
     * Sets the value of the resourceId property.
     * 
     */
    public void setResourceId(long value) {
        this.resourceId = value;
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
     * Gets the value of the shortDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Sets the value of the shortDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortDescription(String value) {
        this.shortDescription = value;
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
     * Gets the value of the description2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription2() {
        return description2;
    }

    /**
     * Sets the value of the description2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription2(String value) {
        this.description2 = value;
    }

    /**
     * Gets the value of the propertyManagerId property.
     * 
     */
    public long getPropertyManagerId() {
        return propertyManagerId;
    }

    /**
     * Sets the value of the propertyManagerId property.
     * 
     */
    public void setPropertyManagerId(long value) {
        this.propertyManagerId = value;
    }

    /**
     * Gets the value of the imageManagerId property.
     * 
     */
    public long getImageManagerId() {
        return imageManagerId;
    }

    /**
     * Sets the value of the imageManagerId property.
     * 
     */
    public void setImageManagerId(long value) {
        this.imageManagerId = value;
    }

    /**
     * Gets the value of the resortId property.
     * 
     */
    public long getResortId() {
        return resortId;
    }

    /**
     * Sets the value of the resortId property.
     * 
     */
    public void setResortId(long value) {
        this.resortId = value;
    }

    /**
     * Gets the value of the resortCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResortCode() {
        return resortCode;
    }

    /**
     * Sets the value of the resortCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResortCode(String value) {
        this.resortCode = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the accommodationTypeSearchObjects property.
     * 
     * @return
     *     possible object is
     *     {@link AccommodationTypeSearch.AccommodationTypeSearchObjects }
     *     
     */
    public AccommodationTypeSearch.AccommodationTypeSearchObjects getAccommodationTypeSearchObjects() {
        return accommodationTypeSearchObjects;
    }

    /**
     * Sets the value of the accommodationTypeSearchObjects property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccommodationTypeSearch.AccommodationTypeSearchObjects }
     *     
     */
    public void setAccommodationTypeSearchObjects(AccommodationTypeSearch.AccommodationTypeSearchObjects value) {
        this.accommodationTypeSearchObjects = value;
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
     *         &lt;element name="AccommodationTypeSearchObjectItem" type="{http://service.newyse.ws.services.newyse.maxxton/}AccommodationTypeSearchObject" maxOccurs="unbounded"/>
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
        "accommodationTypeSearchObjectItem"
    })
    public static class AccommodationTypeSearchObjects {

        @XmlElement(name = "AccommodationTypeSearchObjectItem", required = true)
        protected List<AccommodationTypeSearchObject> accommodationTypeSearchObjectItem;

        /**
         * Gets the value of the accommodationTypeSearchObjectItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the accommodationTypeSearchObjectItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAccommodationTypeSearchObjectItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AccommodationTypeSearchObject }
         * 
         * 
         */
        public List<AccommodationTypeSearchObject> getAccommodationTypeSearchObjectItem() {
            if (accommodationTypeSearchObjectItem == null) {
                accommodationTypeSearchObjectItem = new ArrayList<AccommodationTypeSearchObject>();
            }
            return this.accommodationTypeSearchObjectItem;
        }

    }

}
