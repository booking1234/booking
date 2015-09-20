
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
 *         &lt;element name="BookingHasPoolHeatResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "bookingHasPoolHeatResult"
})
@XmlRootElement(name = "BookingHasPoolHeatResponse")
public class BookingHasPoolHeatResponse {

    @XmlElement(name = "BookingHasPoolHeatResult")
    protected boolean bookingHasPoolHeatResult;

    /**
     * Gets the value of the bookingHasPoolHeatResult property.
     * 
     */
    public boolean isBookingHasPoolHeatResult() {
        return bookingHasPoolHeatResult;
    }

    /**
     * Sets the value of the bookingHasPoolHeatResult property.
     * 
     */
    public void setBookingHasPoolHeatResult(boolean value) {
        this.bookingHasPoolHeatResult = value;
    }

}
