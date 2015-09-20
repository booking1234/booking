
package net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml;

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
 *         &lt;element name="MakeBookingResult" type="{http://xml.ciirus.com/}sBookingResult"/>
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
    "makeBookingResult"
})
@XmlRootElement(name = "MakeBookingResponse")
public class MakeBookingResponse {

    @XmlElement(name = "MakeBookingResult", required = true)
    protected SBookingResult makeBookingResult;

    /**
     * Gets the value of the makeBookingResult property.
     * 
     * @return
     *     possible object is
     *     {@link SBookingResult }
     *     
     */
    public SBookingResult getMakeBookingResult() {
        return makeBookingResult;
    }

    /**
     * Sets the value of the makeBookingResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SBookingResult }
     *     
     */
    public void setMakeBookingResult(SBookingResult value) {
        this.makeBookingResult = value;
    }

}
