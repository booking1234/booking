
package net.cbtltd.rest.barefoot.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for MinimumDayRule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MinimumDayRule">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NumOfDayInAdvance_From" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="NumOfDayInAdvance_To" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Period_From" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Period_To" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="NumOfMinDay" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MinimumDayRule", propOrder = {
    "numOfDayInAdvanceFrom",
    "numOfDayInAdvanceTo",
    "periodFrom",
    "periodTo",
    "numOfMinDay"
})
public class MinimumDayRule {

    @XmlElement(name = "NumOfDayInAdvance_From")
    protected int numOfDayInAdvanceFrom;
    @XmlElement(name = "NumOfDayInAdvance_To")
    protected int numOfDayInAdvanceTo;
    @XmlElement(name = "Period_From", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar periodFrom;
    @XmlElement(name = "Period_To", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar periodTo;
    @XmlElement(name = "NumOfMinDay")
    protected int numOfMinDay;

    /**
     * Gets the value of the numOfDayInAdvanceFrom property.
     * 
     */
    public int getNumOfDayInAdvanceFrom() {
        return numOfDayInAdvanceFrom;
    }

    /**
     * Sets the value of the numOfDayInAdvanceFrom property.
     * 
     */
    public void setNumOfDayInAdvanceFrom(int value) {
        this.numOfDayInAdvanceFrom = value;
    }

    /**
     * Gets the value of the numOfDayInAdvanceTo property.
     * 
     */
    public int getNumOfDayInAdvanceTo() {
        return numOfDayInAdvanceTo;
    }

    /**
     * Sets the value of the numOfDayInAdvanceTo property.
     * 
     */
    public void setNumOfDayInAdvanceTo(int value) {
        this.numOfDayInAdvanceTo = value;
    }

    /**
     * Gets the value of the periodFrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPeriodFrom() {
        return periodFrom;
    }

    /**
     * Sets the value of the periodFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPeriodFrom(XMLGregorianCalendar value) {
        this.periodFrom = value;
    }

    /**
     * Gets the value of the periodTo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPeriodTo() {
        return periodTo;
    }

    /**
     * Sets the value of the periodTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPeriodTo(XMLGregorianCalendar value) {
        this.periodTo = value;
    }

    /**
     * Gets the value of the numOfMinDay property.
     * 
     */
    public int getNumOfMinDay() {
        return numOfMinDay;
    }

    /**
     * Sets the value of the numOfMinDay property.
     * 
     */
    public void setNumOfMinDay(int value) {
        this.numOfMinDay = value;
    }

}
