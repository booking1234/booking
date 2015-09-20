
package net.cbtltd.rest.homeaway.feedparser.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListingRatePeriods complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListingRatePeriods">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="listingExternalId">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="unitExternalId">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element ref="{}ratePeriods"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListingRatePeriods", propOrder = {
    "listingExternalId",
    "unitExternalId",
    "ratePeriods"
})
public class ListingRatePeriods {

    @XmlElement(required = true)
    protected String listingExternalId;
    @XmlElement(required = true)
    protected String unitExternalId;
    @XmlElement(required = true)
    protected RatePeriods ratePeriods;

    /**
     * Gets the value of the listingExternalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getListingExternalId() {
        return listingExternalId;
    }

    /**
     * Sets the value of the listingExternalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setListingExternalId(String value) {
        this.listingExternalId = value;
    }

    /**
     * Gets the value of the unitExternalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitExternalId() {
        return unitExternalId;
    }

    /**
     * Sets the value of the unitExternalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitExternalId(String value) {
        this.unitExternalId = value;
    }

    /**
     * Gets the value of the ratePeriods property.
     * 
     * @return
     *     possible object is
     *     {@link RatePeriods }
     *     
     */
    public RatePeriods getRatePeriods() {
        return ratePeriods;
    }

    /**
     * Sets the value of the ratePeriods property.
     * 
     * @param value
     *     allowed object is
     *     {@link RatePeriods }
     *     
     */
    public void setRatePeriods(RatePeriods value) {
        this.ratePeriods = value;
    }

}
