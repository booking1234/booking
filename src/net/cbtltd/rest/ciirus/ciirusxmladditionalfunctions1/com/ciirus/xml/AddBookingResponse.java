
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
 *         &lt;element name="AddBookingResult" type="{http://xml.ciirus.com/}sAddBookingResult"/>
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
    "addBookingResult"
})
@XmlRootElement(name = "AddBookingResponse")
public class AddBookingResponse {

    @XmlElement(name = "AddBookingResult", required = true)
    protected SAddBookingResult addBookingResult;

    /**
     * Gets the value of the addBookingResult property.
     * 
     * @return
     *     possible object is
     *     {@link SAddBookingResult }
     *     
     */
    public SAddBookingResult getAddBookingResult() {
        return addBookingResult;
    }

    /**
     * Sets the value of the addBookingResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SAddBookingResult }
     *     
     */
    public void setAddBookingResult(SAddBookingResult value) {
        this.addBookingResult = value;
    }

}
