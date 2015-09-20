
package net.cbtltd.rest.maxxton;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustomerReservationInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerReservationInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResortId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ResortCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ResortName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ReservationNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ReservationId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerReservationInfo", propOrder = {
    "resortId",
    "resortCode",
    "resortName",
    "reservationNumber",
    "reservationId"
})
public class CustomerReservationInfo {

    @XmlElement(name = "ResortId")
    protected long resortId;
    @XmlElement(name = "ResortCode", required = true)
    protected String resortCode;
    @XmlElement(name = "ResortName", required = true)
    protected String resortName;
    @XmlElement(name = "ReservationNumber", required = true)
    protected String reservationNumber;
    @XmlElement(name = "ReservationId")
    protected long reservationId;

    /**
     * Gets the value of the resortId property.
     * 
     */
    public long getResortId() {
        return resortId;
    }

    /**
     * Sets the value of the resortId property.
     * 
     */
    public void setResortId(long value) {
        this.resortId = value;
    }

    /**
     * Gets the value of the resortCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResortCode() {
        return resortCode;
    }

    /**
     * Sets the value of the resortCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResortCode(String value) {
        this.resortCode = value;
    }

    /**
     * Gets the value of the resortName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResortName() {
        return resortName;
    }

    /**
     * Sets the value of the resortName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResortName(String value) {
        this.resortName = value;
    }

    /**
     * Gets the value of the reservationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReservationNumber() {
        return reservationNumber;
    }

    /**
     * Sets the value of the reservationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReservationNumber(String value) {
        this.reservationNumber = value;
    }

    /**
     * Gets the value of the reservationId property.
     * 
     */
    public long getReservationId() {
        return reservationId;
    }

    /**
     * Sets the value of the reservationId property.
     * 
     */
    public void setReservationId(long value) {
        this.reservationId = value;
    }

}
