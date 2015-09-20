
package net.cbtltd.rest.homeaway.feedparser.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * The geocode specifies a set of latitude/longitude coordinates and a precision value.
 * 
 * 
 * <p>Java class for GeoCode complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GeoCode">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="latLng" type="{}LatLng"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeoCode", propOrder = {
    "latLng"
})
public class GeoCode {

    @XmlElement(required = true)
    protected LatLng latLng;

    /**
     * Gets the value of the latLng property.
     * 
     * @return
     *     possible object is
     *     {@link LatLng }
     *     
     */
    public LatLng getLatLng() {
        return latLng;
    }

    /**
     * Sets the value of the latLng property.
     * 
     * @param value
     *     allowed object is
     *     {@link LatLng }
     *     
     */
    public void setLatLng(LatLng value) {
        this.latLng = value;
    }

}
