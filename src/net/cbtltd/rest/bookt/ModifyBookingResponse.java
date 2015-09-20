
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
 *         &lt;element name="ModifyBookingResult" type="{http://connect.bookt.com/Schemas/Booking.xsd}Booking" minOccurs="0"/>
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
    "modifyBookingResult"
})
@XmlRootElement(name = "ModifyBookingResponse")
public class ModifyBookingResponse {

    @XmlElementRef(name = "ModifyBookingResult", namespace = "https://connect.bookt.com/connect", type = JAXBElement.class)
    protected JAXBElement<Booking> modifyBookingResult;

    /**
     * Gets the value of the modifyBookingResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Booking }{@code >}
     *     
     */
    public JAXBElement<Booking> getModifyBookingResult() {
        return modifyBookingResult;
    }

    /**
     * Sets the value of the modifyBookingResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Booking }{@code >}
     *     
     */
    public void setModifyBookingResult(JAXBElement<Booking> value) {
        this.modifyBookingResult = ((JAXBElement<Booking> ) value);
    }

}
