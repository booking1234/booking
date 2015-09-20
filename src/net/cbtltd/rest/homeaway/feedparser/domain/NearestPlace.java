
package net.cbtltd.rest.homeaway.feedparser.domain;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * Information regarding a place of interest nearby to a vacation rental.
 * 
 * 
 * <p>Java class for NearestPlace complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NearestPlace">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="distance" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="distanceUnit" type="{}DistanceUnit" minOccurs="0"/>
 *         &lt;element name="name" type="{}Note" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="placeType" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="AIRPORT"/>
 *             &lt;enumeration value="BAR"/>
 *             &lt;enumeration value="BEACH"/>
 *             &lt;enumeration value="FERRY"/>
 *             &lt;enumeration value="GOLF"/>
 *             &lt;enumeration value="HIGHWAY"/>
 *             &lt;enumeration value="RESTAURANT"/>
 *             &lt;enumeration value="SKI"/>
 *             &lt;enumeration value="TRAIN"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NearestPlace", propOrder = {
    "distance",
    "distanceUnit",
    "name"
})
public class NearestPlace {

    protected BigDecimal distance;
    protected DistanceUnit distanceUnit;
    protected Note name;
    @XmlAttribute(name = "placeType", required = true)
    protected String placeType;

    /**
     * Gets the value of the distance property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDistance() {
        return distance;
    }

    /**
     * Sets the value of the distance property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDistance(BigDecimal value) {
        this.distance = value;
    }

    /**
     * Gets the value of the distanceUnit property.
     * 
     * @return
     *     possible object is
     *     {@link DistanceUnit }
     *     
     */
    public DistanceUnit getDistanceUnit() {
        return distanceUnit;
    }

    /**
     * Sets the value of the distanceUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link DistanceUnit }
     *     
     */
    public void setDistanceUnit(DistanceUnit value) {
        this.distanceUnit = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link Note }
     *     
     */
    public Note getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link Note }
     *     
     */
    public void setName(Note value) {
        this.name = value;
    }

    /**
     * Gets the value of the placeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlaceType() {
        return placeType;
    }

    /**
     * Sets the value of the placeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlaceType(String value) {
        this.placeType = value;
    }

}
