
package net.cbtltd.rest.maxxton;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for TipTrip complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TipTrip">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TipsTripsId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="TipsTripsCategoryId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="AddressmanagerId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ImagemanagerId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="HeadText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Text" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Location" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Price" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PriceChild" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PriceValuePoint" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PriceAdult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Resorts" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://service.newyse.ws.services.newyse.maxxton/}Resort" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "TipTrip", propOrder = {
    "tipsTripsId",
    "tipsTripsCategoryId",
    "addressmanagerId",
    "imagemanagerId",
    "name",
    "headText",
    "text",
    "description",
    "location",
    "price",
    "priceChild",
    "priceValuePoint",
    "priceAdult",
    "startDate",
    "endDate",
    "url",
    "resorts"
})
public class TipTrip {

    @XmlElement(name = "TipsTripsId")
    protected long tipsTripsId;
    @XmlElement(name = "TipsTripsCategoryId")
    protected long tipsTripsCategoryId;
    @XmlElement(name = "AddressmanagerId")
    protected long addressmanagerId;
    @XmlElement(name = "ImagemanagerId")
    protected long imagemanagerId;
    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElementRef(name = "HeadText", type = JAXBElement.class)
    protected JAXBElement<String> headText;
    @XmlElementRef(name = "Text", type = JAXBElement.class)
    protected JAXBElement<String> text;
    @XmlElementRef(name = "Description", type = JAXBElement.class)
    protected JAXBElement<String> description;
    @XmlElementRef(name = "Location", type = JAXBElement.class)
    protected JAXBElement<String> location;
    @XmlElementRef(name = "Price", type = JAXBElement.class)
    protected JAXBElement<String> price;
    @XmlElement(name = "PriceChild")
    protected String priceChild;
    @XmlElement(name = "PriceValuePoint")
    protected String priceValuePoint;
    @XmlElement(name = "PriceAdult")
    protected String priceAdult;
    @XmlElementRef(name = "StartDate", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> startDate;
    @XmlElementRef(name = "EndDate", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> endDate;
    @XmlElementRef(name = "Url", type = JAXBElement.class)
    protected JAXBElement<String> url;
    @XmlElement(name = "Resorts")
    protected TipTrip.Resorts resorts;

    /**
     * Gets the value of the tipsTripsId property.
     * 
     */
    public long getTipsTripsId() {
        return tipsTripsId;
    }

    /**
     * Sets the value of the tipsTripsId property.
     * 
     */
    public void setTipsTripsId(long value) {
        this.tipsTripsId = value;
    }

    /**
     * Gets the value of the tipsTripsCategoryId property.
     * 
     */
    public long getTipsTripsCategoryId() {
        return tipsTripsCategoryId;
    }

    /**
     * Sets the value of the tipsTripsCategoryId property.
     * 
     */
    public void setTipsTripsCategoryId(long value) {
        this.tipsTripsCategoryId = value;
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
     * Gets the value of the imagemanagerId property.
     * 
     */
    public long getImagemanagerId() {
        return imagemanagerId;
    }

    /**
     * Sets the value of the imagemanagerId property.
     * 
     */
    public void setImagemanagerId(long value) {
        this.imagemanagerId = value;
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
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLocation(JAXBElement<String> value) {
        this.location = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPrice(JAXBElement<String> value) {
        this.price = ((JAXBElement<String> ) value);
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
     * Gets the value of the priceValuePoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriceValuePoint() {
        return priceValuePoint;
    }

    /**
     * Sets the value of the priceValuePoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriceValuePoint(String value) {
        this.priceValuePoint = value;
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
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setStartDate(JAXBElement<XMLGregorianCalendar> value) {
        this.startDate = ((JAXBElement<XMLGregorianCalendar> ) value);
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setEndDate(JAXBElement<XMLGregorianCalendar> value) {
        this.endDate = ((JAXBElement<XMLGregorianCalendar> ) value);
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUrl(JAXBElement<String> value) {
        this.url = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the resorts property.
     * 
     * @return
     *     possible object is
     *     {@link TipTrip.Resorts }
     *     
     */
    public TipTrip.Resorts getResorts() {
        return resorts;
    }

    /**
     * Sets the value of the resorts property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipTrip.Resorts }
     *     
     */
    public void setResorts(TipTrip.Resorts value) {
        this.resorts = value;
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
     *         &lt;element ref="{http://service.newyse.ws.services.newyse.maxxton/}Resort" maxOccurs="unbounded" minOccurs="0"/>
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
        "resort"
    })
    public static class Resorts {

        @XmlElement(name = "Resort", namespace = "http://service.newyse.ws.services.newyse.maxxton/")
        protected List<Resort> resort;

        /**
         * Gets the value of the resort property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the resort property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getResort().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Resort }
         * 
         * 
         */
        public List<Resort> getResort() {
            if (resort == null) {
                resort = new ArrayList<Resort>();
            }
            return this.resort;
        }

    }

}
