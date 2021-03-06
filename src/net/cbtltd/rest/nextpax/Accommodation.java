//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.12.05 at 09:44:01 AM IST 
//


package net.cbtltd.rest.nextpax;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "accommodationIDOrComponentID",
    "sequence",
    "accommodationType",
    "name",
    "address",
    "zipcode",
    "place",
    "country",
    "gpsCode",
    "telephone",
    "arrivalDate",
    "departureDateOrDuration",
    "unit",
    "arrangement",
    "extras",
    "multiMedia"
})
@XmlRootElement(name = "Accommodation")
public class Accommodation {

    @XmlElements({
        @XmlElement(name = "AccommodationID", required = true, type = AccommodationID.class),
        @XmlElement(name = "ComponentID", required = true, type = ComponentID.class)
    })
    protected List<Object> accommodationIDOrComponentID;
    @XmlElement(name = "Sequence")
    protected Sequence sequence;
    @XmlElement(name = "AccommodationType")
    protected String accommodationType;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Address")
    protected String address;
    @XmlElement(name = "Zipcode")
    protected String zipcode;
    @XmlElement(name = "Place")
    protected Place place;
    @XmlElement(name = "Country")
    protected Country country;
    @XmlElement(name = "GPSCode")
    protected GPSCode gpsCode;
    @XmlElement(name = "Telephone")
    protected String telephone;
    @XmlElement(name = "ArrivalDate", required = true)
    protected String arrivalDate;
    @XmlElements({
        @XmlElement(name = "DepartureDate", required = true, type = DepartureDate.class),
        @XmlElement(name = "Duration", required = true, type = Duration.class)
    })
    protected List<Object> departureDateOrDuration;
    @XmlElement(name = "Unit", required = true)
    protected List<Unit> unit;
    @XmlElement(name = "Arrangement")
    protected List<Arrangement> arrangement;
    @XmlElement(name = "Extras")
    protected List<Extras> extras;
    @XmlElement(name = "MultiMedia")
    protected List<MultiMedia> multiMedia;

    /**
     * Gets the value of the accommodationIDOrComponentID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the accommodationIDOrComponentID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccommodationIDOrComponentID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AccommodationID }
     * {@link ComponentID }
     * 
     * 
     */
    public List<Object> getAccommodationIDOrComponentID() {
        if (accommodationIDOrComponentID == null) {
            accommodationIDOrComponentID = new ArrayList<Object>();
        }
        return this.accommodationIDOrComponentID;
    }

    /**
     * Gets the value of the sequence property.
     * 
     * @return
     *     possible object is
     *     {@link Sequence }
     *     
     */
    public Sequence getSequence() {
        return sequence;
    }

    /**
     * Sets the value of the sequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sequence }
     *     
     */
    public void setSequence(Sequence value) {
        this.sequence = value;
    }

    /**
     * Gets the value of the accommodationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccommodationType() {
        return accommodationType;
    }

    /**
     * Sets the value of the accommodationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccommodationType(String value) {
        this.accommodationType = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress(String value) {
        this.address = value;
    }

    /**
     * Gets the value of the zipcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * Sets the value of the zipcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZipcode(String value) {
        this.zipcode = value;
    }

    /**
     * Gets the value of the place property.
     * 
     * @return
     *     possible object is
     *     {@link Place }
     *     
     */
    public Place getPlace() {
        return place;
    }

    /**
     * Sets the value of the place property.
     * 
     * @param value
     *     allowed object is
     *     {@link Place }
     *     
     */
    public void setPlace(Place value) {
        this.place = value;
    }

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link Country }
     *     
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link Country }
     *     
     */
    public void setCountry(Country value) {
        this.country = value;
    }

    /**
     * Gets the value of the gpsCode property.
     * 
     * @return
     *     possible object is
     *     {@link GPSCode }
     *     
     */
    public GPSCode getGPSCode() {
        return gpsCode;
    }

    /**
     * Sets the value of the gpsCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link GPSCode }
     *     
     */
    public void setGPSCode(GPSCode value) {
        this.gpsCode = value;
    }

    /**
     * Gets the value of the telephone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Sets the value of the telephone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelephone(String value) {
        this.telephone = value;
    }

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
     * Gets the value of the departureDateOrDuration property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the departureDateOrDuration property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDepartureDateOrDuration().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DepartureDate }
     * {@link Duration }
     * 
     * 
     */
    public List<Object> getDepartureDateOrDuration() {
        if (departureDateOrDuration == null) {
            departureDateOrDuration = new ArrayList<Object>();
        }
        return this.departureDateOrDuration;
    }

    /**
     * Gets the value of the unit property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the unit property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUnit().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Unit }
     * 
     * 
     */
    public List<Unit> getUnit() {
        if (unit == null) {
            unit = new ArrayList<Unit>();
        }
        return this.unit;
    }

    /**
     * Gets the value of the arrangement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the arrangement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArrangement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Arrangement }
     * 
     * 
     */
    public List<Arrangement> getArrangement() {
        if (arrangement == null) {
            arrangement = new ArrayList<Arrangement>();
        }
        return this.arrangement;
    }

    /**
     * Gets the value of the extras property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extras property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtras().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Extras }
     * 
     * 
     */
    public List<Extras> getExtras() {
        if (extras == null) {
            extras = new ArrayList<Extras>();
        }
        return this.extras;
    }

    /**
     * Gets the value of the multiMedia property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the multiMedia property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMultiMedia().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MultiMedia }
     * 
     * 
     */
    public List<MultiMedia> getMultiMedia() {
        if (multiMedia == null) {
            multiMedia = new ArrayList<MultiMedia>();
        }
        return this.multiMedia;
    }

}
