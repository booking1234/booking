//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.12.05 at 09:44:01 AM IST 
//


package net.cbtltd.rest.nextpax;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "bookID",
    "bookEmployeeID",
    "date",
    "time",
    "optionDate",
    "status",
    "bookInfo"
})
@XmlRootElement(name = "BookResponse")
public class BookResponse {

    @XmlElement(name = "BookID", required = true)
    protected String bookID;
    @XmlElement(name = "BookEmployeeID")
    protected String bookEmployeeID;
    @XmlElement(name = "Date", required = true)
    protected Date date;
    @XmlElement(name = "Time", required = true)
    protected Time time;
    @XmlElement(name = "OptionDate")
    protected String optionDate;
    @XmlElement(name = "Status", required = true)
    protected String status;
    @XmlElement(name = "BookInfo")
    protected BookInfo bookInfo;

    /**
     * Gets the value of the bookID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookID() {
        return bookID;
    }

    /**
     * Sets the value of the bookID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookID(String value) {
        this.bookID = value;
    }

    /**
     * Gets the value of the bookEmployeeID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookEmployeeID() {
        return bookEmployeeID;
    }

    /**
     * Sets the value of the bookEmployeeID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookEmployeeID(String value) {
        this.bookEmployeeID = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setDate(Date value) {
        this.date = value;
    }

    /**
     * Gets the value of the time property.
     * 
     * @return
     *     possible object is
     *     {@link Time }
     *     
     */
    public Time getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *     allowed object is
     *     {@link Time }
     *     
     */
    public void setTime(Time value) {
        this.time = value;
    }

    /**
     * Gets the value of the optionDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOptionDate() {
        return optionDate;
    }

    /**
     * Sets the value of the optionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOptionDate(String value) {
        this.optionDate = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the bookInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BookInfo }
     *     
     */
    public BookInfo getBookInfo() {
        return bookInfo;
    }

    /**
     * Sets the value of the bookInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookInfo }
     *     
     */
    public void setBookInfo(BookInfo value) {
        this.bookInfo = value;
    }

}
