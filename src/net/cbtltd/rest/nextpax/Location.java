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
    "countryIDOrAreaIDOrPlaceIDOrAccommodationIDOrCountryOrAreaOrPlace",
    "gpsCode"
})
@XmlRootElement(name = "Location")
public class Location {

    @XmlElements({
        @XmlElement(name = "CountryID", required = true, type = CountryID.class),
        @XmlElement(name = "AreaID", required = true, type = AreaID.class),
        @XmlElement(name = "PlaceID", required = true, type = PlaceID.class),
        @XmlElement(name = "AccommodationID", required = true, type = AccommodationID.class),
        @XmlElement(name = "Country", required = true, type = Country.class),
        @XmlElement(name = "Area", required = true, type = Area.class),
        @XmlElement(name = "Place", required = true, type = Place.class)
    })
    protected List<Object> countryIDOrAreaIDOrPlaceIDOrAccommodationIDOrCountryOrAreaOrPlace;
    @XmlElement(name = "GPSCode")
    protected GPSCode gpsCode;

    /**
     * Gets the value of the countryIDOrAreaIDOrPlaceIDOrAccommodationIDOrCountryOrAreaOrPlace property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the countryIDOrAreaIDOrPlaceIDOrAccommodationIDOrCountryOrAreaOrPlace property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCountryIDOrAreaIDOrPlaceIDOrAccommodationIDOrCountryOrAreaOrPlace().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CountryID }
     * {@link AreaID }
     * {@link PlaceID }
     * {@link AccommodationID }
     * {@link Country }
     * {@link Area }
     * {@link Place }
     * 
     * 
     */
    public List<Object> getCountryIDOrAreaIDOrPlaceIDOrAccommodationIDOrCountryOrAreaOrPlace() {
        if (countryIDOrAreaIDOrPlaceIDOrAccommodationIDOrCountryOrAreaOrPlace == null) {
            countryIDOrAreaIDOrPlaceIDOrAccommodationIDOrCountryOrAreaOrPlace = new ArrayList<Object>();
        }
        return this.countryIDOrAreaIDOrPlaceIDOrAccommodationIDOrCountryOrAreaOrPlace;
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

}