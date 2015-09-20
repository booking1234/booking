
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
 *         &lt;element name="GetBookingResult" type="{http://connect.bookt.com/Schemas/Booking.xsd}Booking" minOccurs="0"/>
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
    "getBookingResult"
})
@XmlRootElement(name = "GetBookingResponse")
public class GetBookingResponse {

    @XmlElementRef(name = "GetBookingResult", namespace = "https://connect.bookt.com/connect", type = JAXBElement.class)
    protected JAXBElement<Booking> getBookingResult;

    /**
     * Gets the value of the getBookingResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Booking }{@code >}
     *     
     */
    public JAXBElement<Booking> getGetBookingResult() {
        return getBookingResult;
    }

    /**
     * Sets the value of the getBookingResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Booking }{@code >}
     *     
     */
    public void setGetBookingResult(JAXBElement<Booking> value) {
        this.getBookingResult = ((JAXBElement<Booking> ) value);
    }

}
