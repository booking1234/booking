
package net.cbtltd.rest.bookt;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
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
 *         &lt;element name="apiKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="booking" type="{http://connect.bookt.com/Schemas/Booking.xsd}Booking" minOccurs="0"/>
 *         &lt;element name="infoOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
    "apiKey",
    "booking",
    "infoOnly"
})
@XmlRootElement(name = "ModifyBooking")
public class ModifyBooking {

    @XmlElementRef(name = "apiKey", namespace = "https://connect.bookt.com/connect", type = JAXBElement.class)
    protected JAXBElement<String> apiKey;
    @XmlElementRef(name = "booking", namespace = "https://connect.bookt.com/connect", type = JAXBElement.class)
    protected JAXBElement<Booking> booking;
    protected Boolean infoOnly;

    /**
     * Gets the value of the apiKey property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getApiKey() {
        return apiKey;
    }

    /**
     * Sets the value of the apiKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setApiKey(JAXBElement<String> value) {
        this.apiKey = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the booking property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Booking }{@code >}
     *     
     */
    public JAXBElement<Booking> getBooking() {
        return booking;
    }

    /**
     * Sets the value of the booking property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Booking }{@code >}
     *     
     */
    public void setBooking(JAXBElement<Booking> value) {
        this.booking = ((JAXBElement<Booking> ) value);
    }

    /**
     * Gets the value of the infoOnly property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isInfoOnly() {
        return infoOnly;
    }

    /**
     * Sets the value of the infoOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInfoOnly(Boolean value) {
        this.infoOnly = value;
    }

}
