
package net.cbtltd.rest.leaderstay;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for amendparameters complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="amendparameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="old_code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="old_LScode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="firstname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="lastname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="telnumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="countryofresidence" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="checkindate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="checkintime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="checkouttime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numberofnights" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="persons" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flightno" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="airport" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="asksuitability" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="notes" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
@XmlType(name = "amendparameters", propOrder = {

})
public class Amendparameters {

    @XmlElement(required = true)
    protected String id;
    @XmlElement(name = "old_code", required = true)
    protected String oldCode;
    @XmlElement(name = "old_LScode", required = true)
    protected String oldLScode;
    @XmlElement(required = true)
    protected String code;
    @XmlElement(required = true)
    protected String firstname;
    @XmlElement(required = true)
    protected String lastname;
    @XmlElement(required = true)
    protected String email;
    @XmlElement(required = true)
    protected String telnumber;
    @XmlElement(required = true)
    protected String countryofresidence;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar checkindate;
    @XmlElement(required = true)
    protected String checkintime;
    @XmlElement(required = true)
    protected String checkouttime;
    protected int numberofnights;
    @XmlElement(required = true)
    protected String persons;
    @XmlElement(required = true)
    protected String flightno;
    @XmlElement(required = true)
    protected String airport;
    @XmlElement(required = true)
    protected String asksuitability;
    @XmlElement(required = true)
    protected String notes;
    protected double price;
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
     * Gets the value of the oldCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldCode() {
        return oldCode;
    }

    /**
     * Sets the value of the oldCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldCode(String value) {
        this.oldCode = value;
    }

    /**
     * Gets the value of the oldLScode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldLScode() {
        return oldLScode;
    }

    /**
     * Sets the value of the oldLScode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldLScode(String value) {
        this.oldLScode = value;
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
     * Gets the value of the firstname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the value of the firstname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstname(String value) {
        this.firstname = value;
    }

    /**
     * Gets the value of the lastname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the value of the lastname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastname(String value) {
        this.lastname = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the telnumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelnumber() {
        return telnumber;
    }

    /**
     * Sets the value of the telnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelnumber(String value) {
        this.telnumber = value;
    }

    /**
     * Gets the value of the countryofresidence property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryofresidence() {
        return countryofresidence;
    }

    /**
     * Sets the value of the countryofresidence property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryofresidence(String value) {
        this.countryofresidence = value;
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
     * Gets the value of the checkintime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckintime() {
        return checkintime;
    }

    /**
     * Sets the value of the checkintime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckintime(String value) {
        this.checkintime = value;
    }

    /**
     * Gets the value of the checkouttime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckouttime() {
        return checkouttime;
    }

    /**
     * Sets the value of the checkouttime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckouttime(String value) {
        this.checkouttime = value;
    }

    /**
     * Gets the value of the numberofnights property.
     * 
     */
    public int getNumberofnights() {
        return numberofnights;
    }

    /**
     * Sets the value of the numberofnights property.
     * 
     */
    public void setNumberofnights(int value) {
        this.numberofnights = value;
    }

    /**
     * Gets the value of the persons property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersons() {
        return persons;
    }

    /**
     * Sets the value of the persons property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersons(String value) {
        this.persons = value;
    }

    /**
     * Gets the value of the flightno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlightno() {
        return flightno;
    }

    /**
     * Sets the value of the flightno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlightno(String value) {
        this.flightno = value;
    }

    /**
     * Gets the value of the airport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAirport() {
        return airport;
    }

    /**
     * Sets the value of the airport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAirport(String value) {
        this.airport = value;
    }

    /**
     * Gets the value of the asksuitability property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsksuitability() {
        return asksuitability;
    }

    /**
     * Sets the value of the asksuitability property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsksuitability(String value) {
        this.asksuitability = value;
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
     * Gets the value of the price property.
     * 
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     */
    public void setPrice(double value) {
        this.price = value;
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
