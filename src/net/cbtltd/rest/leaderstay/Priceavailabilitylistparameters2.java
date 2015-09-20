
package net.cbtltd.rest.leaderstay;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for priceavailabilitylistparameters2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="priceavailabilitylistparameters2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="checkindate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="numberofnights" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="persons" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "priceavailabilitylistparameters2", propOrder = {

})
public class Priceavailabilitylistparameters2 {

    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar checkindate;
    protected int numberofnights;
    @XmlElement(required = true)
    protected String persons;
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
