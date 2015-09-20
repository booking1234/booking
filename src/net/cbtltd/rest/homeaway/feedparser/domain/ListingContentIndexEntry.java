
package net.cbtltd.rest.homeaway.feedparser.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ListingContentIndexEntry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListingContentIndexEntry">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="listingExternalId" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="unitExternalId" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="active" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="lastUpdatedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;choice>
 *           &lt;element name="listingUrl" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *           &lt;element name="unitReservationsUrl" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *           &lt;element name="unitRatesUrl" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListingContentIndexEntry", propOrder = {
    "listingExternalId",
    "unitExternalId",
    "active",
    "lastUpdatedDate",
    "listingUrl",
    "unitReservationsUrl",
    "unitRatesUrl"
})
public class ListingContentIndexEntry {

    @XmlElement(required = true)
    protected String listingExternalId;
    protected String unitExternalId;
    @XmlElement(defaultValue = "true")
    protected boolean active;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastUpdatedDate;
    protected String listingUrl;
    @XmlElement(required = false)
    protected String unitReservationsUrl;
    @XmlElement(required = false)
    protected String unitRatesUrl;

    /**
     * Gets the value of the listingExternalId property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
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
     *     {@link Object }
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
     *     {@link Object }
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
     *     {@link Object }
     *     
     */
    public void setUnitExternalId(String value) {
        this.unitExternalId = value;
    }

    /**
     * Gets the value of the active property.
     * 
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the value of the active property.
     * 
     */
    public void setActive(boolean value) {
        this.active = value;
    }

    /**
     * Gets the value of the lastUpdatedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    /**
     * Sets the value of the lastUpdatedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastUpdatedDate(XMLGregorianCalendar value) {
        this.lastUpdatedDate = value;
    }

    /**
     * Gets the value of the listingUrl property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public String getListingUrl() {
        return listingUrl;
    }

    /**
     * Sets the value of the listingUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setListingUrl(String value) {
        this.listingUrl = value;
    }

    /**
     * Gets the value of the unitReservationsUrl property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public String getUnitReservationsUrl() {
        return unitReservationsUrl;
    }

    /**
     * Sets the value of the unitReservationsUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUnitReservationsUrl(String value) {
        this.unitReservationsUrl = value;
    }

    /**
     * Gets the value of the unitRatesUrl property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public String getUnitRatesUrl() {
        return unitRatesUrl;
    }

    /**
     * Sets the value of the unitRatesUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUnitRatesUrl(String value) {
        this.unitRatesUrl = value;
    }

}
