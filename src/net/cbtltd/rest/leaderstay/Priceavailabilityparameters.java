
package net.cbtltd.rest.leaderstay;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for priceavailabilityparameters complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="priceavailabilityparameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="propertycode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="checkindate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="checkintime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="checkouttime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numberofnights" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="persons" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "priceavailabilityparameters", propOrder = {

})
public class Priceavailabilityparameters {

    @XmlElement(required = true)
    protected String id;
    protected int propertycode;
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
     * Gets the value of the propertycode property.
     * 
     */
    public int getPropertycode() {
        return propertycode;
    }

    /**
     * Sets the value of the propertycode property.
     * 
     */
    public void setPropertycode(int value) {
        this.propertycode = value;
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
