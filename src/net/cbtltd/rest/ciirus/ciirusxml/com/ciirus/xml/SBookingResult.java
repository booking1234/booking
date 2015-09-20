
package net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sBookingResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sBookingResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BookingPlaced" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ErrorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BookingID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TotalAmountIncludingTax" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sBookingResult", propOrder = {
    "bookingPlaced",
    "errorMessage",
    "bookingID",
    "totalAmountIncludingTax"
})
public class SBookingResult {

    @XmlElement(name = "BookingPlaced")
    protected boolean bookingPlaced;
    @XmlElement(name = "ErrorMessage")
    protected String errorMessage;
    @XmlElement(name = "BookingID")
    protected int bookingID;
    @XmlElement(name = "TotalAmountIncludingTax", required = true)
    protected BigDecimal totalAmountIncludingTax;

    /**
     * Gets the value of the bookingPlaced property.
     * 
     */
    public boolean isBookingPlaced() {
        return bookingPlaced;
    }

    /**
     * Sets the value of the bookingPlaced property.
     * 
     */
    public void setBookingPlaced(boolean value) {
        this.bookingPlaced = value;
    }

    /**
     * Gets the value of the errorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    /**
     * Gets the value of the bookingID property.
     * 
     */
    public int getBookingID() {
        return bookingID;
    }

    /**
     * Sets the value of the bookingID property.
     * 
     */
    public void setBookingID(int value) {
        this.bookingID = value;
    }

    /**
     * Gets the value of the totalAmountIncludingTax property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalAmountIncludingTax() {
        return totalAmountIncludingTax;
    }

    /**
     * Sets the value of the totalAmountIncludingTax property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalAmountIncludingTax(BigDecimal value) {
        this.totalAmountIncludingTax = value;
    }

}
