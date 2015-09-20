
package net.cbtltd.rest.ciirus.ciirusxml.com.ciirus.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sBookingDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sBookingDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ArrivalDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DepartureDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PropertyID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="GuestName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GuestEmailAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GuestTelephone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GuestAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GuestList" type="{http://xml.ciirus.com/}ArrayOfSGuests" minOccurs="0"/>
 *         &lt;element name="PoolHeatRequired" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sBookingDetails", propOrder = {
    "arrivalDate",
    "departureDate",
    "propertyID",
    "guestName",
    "guestEmailAddress",
    "guestTelephone",
    "guestAddress",
    "guestList",
    "poolHeatRequired"
})
public class SBookingDetails {

    @XmlElement(name = "ArrivalDate")
    protected String arrivalDate;
    @XmlElement(name = "DepartureDate")
    protected String departureDate;
    @XmlElement(name = "PropertyID")
    protected int propertyID;
    @XmlElement(name = "GuestName")
    protected String guestName;
    @XmlElement(name = "GuestEmailAddress")
    protected String guestEmailAddress;
    @XmlElement(name = "GuestTelephone")
    protected String guestTelephone;
    @XmlElement(name = "GuestAddress")
    protected String guestAddress;
    @XmlElement(name = "GuestList")
    protected ArrayOfSGuests guestList;
    @XmlElement(name = "PoolHeatRequired")
    protected boolean poolHeatRequired;

    /**
     * Gets the value of the arrivalDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArrivalDate() {
        return arrivalDate;
    }

    /**
     * Sets the value of the arrivalDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArrivalDate(String value) {
        this.arrivalDate = value;
    }

    /**
     * Gets the value of the departureDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartureDate() {
        return departureDate;
    }

    /**
     * Sets the value of the departureDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartureDate(String value) {
        this.departureDate = value;
    }

    /**
     * Gets the value of the propertyID property.
     * 
     */
    public int getPropertyID() {
        return propertyID;
    }

    /**
     * Sets the value of the propertyID property.
     * 
     */
    public void setPropertyID(int value) {
        this.propertyID = value;
    }

    /**
     * Gets the value of the guestName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGuestName() {
        return guestName;
    }

    /**
     * Sets the value of the guestName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGuestName(String value) {
        this.guestName = value;
    }

    /**
     * Gets the value of the guestEmailAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGuestEmailAddress() {
        return guestEmailAddress;
    }

    /**
     * Sets the value of the guestEmailAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGuestEmailAddress(String value) {
        this.guestEmailAddress = value;
    }

    /**
     * Gets the value of the guestTelephone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGuestTelephone() {
        return guestTelephone;
    }

    /**
     * Sets the value of the guestTelephone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGuestTelephone(String value) {
        this.guestTelephone = value;
    }

    /**
     * Gets the value of the guestAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGuestAddress() {
        return guestAddress;
    }

    /**
     * Sets the value of the guestAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGuestAddress(String value) {
        this.guestAddress = value;
    }

    /**
     * Gets the value of the guestList property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSGuests }
     *     
     */
    public ArrayOfSGuests getGuestList() {
        return guestList;
    }

    /**
     * Sets the value of the guestList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSGuests }
     *     
     */
    public void setGuestList(ArrayOfSGuests value) {
        this.guestList = value;
    }

    /**
     * Gets the value of the poolHeatRequired property.
     * 
     */
    public boolean isPoolHeatRequired() {
        return poolHeatRequired;
    }

    /**
     * Sets the value of the poolHeatRequired property.
     * 
     */
    public void setPoolHeatRequired(boolean value) {
        this.poolHeatRequired = value;
    }

}
