
package net.cbtltd.rest.leaderstay;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for infoavailabilitylistparameters complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="infoavailabilitylistparameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="country" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="area" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="checkindate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="nightsno" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="personsno" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="pool" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="internet" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="sortby" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="limit_from" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="limit_amount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="price_per_day" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="price_diversion" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="dateformat" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "infoavailabilitylistparameters", propOrder = {

})
public class Infoavailabilitylistparameters {

    @XmlElement(required = true)
    protected String id;
    protected int country;
    protected int region;
    protected int area;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar checkindate;
    protected int nightsno;
    protected int personsno;
    protected int pool;
    protected int internet;
    @XmlElement(required = true)
    protected String sortby;
    @XmlElement(name = "limit_from")
    protected int limitFrom;
    @XmlElement(name = "limit_amount")
    protected int limitAmount;
    @XmlElement(name = "price_per_day")
    protected double pricePerDay;
    @XmlElement(name = "price_diversion")
    protected double priceDiversion;
    @XmlElement(required = true)
    protected String dateformat;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the country property.
     * 
     */
    public int getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     */
    public void setCountry(int value) {
        this.country = value;
    }

    /**
     * Gets the value of the region property.
     * 
     */
    public int getRegion() {
        return region;
    }

    /**
     * Sets the value of the region property.
     * 
     */
    public void setRegion(int value) {
        this.region = value;
    }

    /**
     * Gets the value of the area property.
     * 
     */
    public int getArea() {
        return area;
    }

    /**
     * Sets the value of the area property.
     * 
     */
    public void setArea(int value) {
        this.area = value;
    }

    /**
     * Gets the value of the checkindate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCheckindate() {
        return checkindate;
    }

    /**
     * Sets the value of the checkindate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCheckindate(XMLGregorianCalendar value) {
        this.checkindate = value;
    }

    /**
     * Gets the value of the nightsno property.
     * 
     */
    public int getNightsno() {
        return nightsno;
    }

    /**
     * Sets the value of the nightsno property.
     * 
     */
    public void setNightsno(int value) {
        this.nightsno = value;
    }

    /**
     * Gets the value of the personsno property.
     * 
     */
    public int getPersonsno() {
        return personsno;
    }

    /**
     * Sets the value of the personsno property.
     * 
     */
    public void setPersonsno(int value) {
        this.personsno = value;
    }

    /**
     * Gets the value of the pool property.
     * 
     */
    public int getPool() {
        return pool;
    }

    /**
     * Sets the value of the pool property.
     * 
     */
    public void setPool(int value) {
        this.pool = value;
    }

    /**
     * Gets the value of the internet property.
     * 
     */
    public int getInternet() {
        return internet;
    }

    /**
     * Sets the value of the internet property.
     * 
     */
    public void setInternet(int value) {
        this.internet = value;
    }

    /**
     * Gets the value of the sortby property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSortby() {
        return sortby;
    }

    /**
     * Sets the value of the sortby property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSortby(String value) {
        this.sortby = value;
    }

    /**
     * Gets the value of the limitFrom property.
     * 
     */
    public int getLimitFrom() {
        return limitFrom;
    }

    /**
     * Sets the value of the limitFrom property.
     * 
     */
    public void setLimitFrom(int value) {
        this.limitFrom = value;
    }

    /**
     * Gets the value of the limitAmount property.
     * 
     */
    public int getLimitAmount() {
        return limitAmount;
    }

    /**
     * Sets the value of the limitAmount property.
     * 
     */
    public void setLimitAmount(int value) {
        this.limitAmount = value;
    }

    /**
     * Gets the value of the pricePerDay property.
     * 
     */
    public double getPricePerDay() {
        return pricePerDay;
    }

    /**
     * Sets the value of the pricePerDay property.
     * 
     */
    public void setPricePerDay(double value) {
        this.pricePerDay = value;
    }

    /**
     * Gets the value of the priceDiversion property.
     * 
     */
    public double getPriceDiversion() {
        return priceDiversion;
    }

    /**
     * Sets the value of the priceDiversion property.
     * 
     */
    public void setPriceDiversion(double value) {
        this.priceDiversion = value;
    }

    /**
     * Gets the value of the dateformat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateformat() {
        return dateformat;
    }

    /**
     * Sets the value of the dateformat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateformat(String value) {
        this.dateformat = value;
    }

}
