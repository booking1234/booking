
package net.cbtltd.rest.ciirus.ciirusxmladditionalfunctions1.com.ciirus.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="APIUsername" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="APIPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BookingParameters" type="{http://xml.ciirus.com/}AddBookingParameters"/>
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
    "apiUsername",
    "apiPassword",
    "bookingParameters"
})
@XmlRootElement(name = "AddBooking")
public class AddBooking {

    @XmlElement(name = "APIUsername")
    protected String apiUsername;
    @XmlElement(name = "APIPassword")
    protected String apiPassword;
    @XmlElement(name = "BookingParameters", required = true)
    protected AddBookingParameters bookingParameters;

    /**
     * Gets the value of the apiUsername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPIUsername() {
        return apiUsername;
    }

    /**
     * Sets the value of the apiUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPIUsername(String value) {
        this.apiUsername = value;
    }

    /**
     * Gets the value of the apiPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPIPassword() {
        return apiPassword;
    }

    /**
     * Sets the value of the apiPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPIPassword(String value) {
        this.apiPassword = value;
    }

    /**
     * Gets the value of the bookingParameters property.
     * 
     * @return
     *     possible object is
     *     {@link AddBookingParameters }
     *     
     */
    public AddBookingParameters getBookingParameters() {
        return bookingParameters;
    }

    /**
     * Sets the value of the bookingParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddBookingParameters }
     *     
     */
    public void setBookingParameters(AddBookingParameters value) {
        this.bookingParameters = value;
    }

}
