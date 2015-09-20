
package net.cbtltd.rest.homeaway.feedparser.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * A Listing is an advertisement for a vacation rental - it contains the details of the rental property that are presented to travelers. Listings encompass one or more Units. The Listing represents data about the property as a whole - the physical location and attributes of the property as a whole, for example. Each Unit represents a single rentable unit of the property, and describes the particulars of that unit.
 * 
 * 
 * <p>Java class for ListingBatch complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListingBatch">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentVersion" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="advertisers" type="{}AdvertiserListings"/>
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
    "documentVersion","advertisers"
})
@XmlRootElement(name = "listingBatch")
public class ListingBatch {

    @XmlElement(required = true)
    protected String documentVersion;
    @XmlElement(required = true)
    protected AdvertiserListings advertisers;

    /**
     * Gets the value of the documentVersion property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public String getDocumentVersion() {
        return documentVersion;
    }

    /**
     * Sets the value of the documentVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setDocumentVersion(String value) {
        this.documentVersion = value;
    }

    /**
     * Gets the value of the advertisers property.
     * 
     * @return
     *     possible object is
     *     {@link AdvertiserListings }
     *     
     */
    public AdvertiserListings getAdvertisers() {
        return advertisers;
    }

    /**
     * Sets the value of the advertisers property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdvertiserListings }
     *     
     */
    public void setAdvertisers(AdvertiserListings value) {
        this.advertisers = value;
    }

}
