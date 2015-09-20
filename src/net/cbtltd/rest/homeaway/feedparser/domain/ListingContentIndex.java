
package net.cbtltd.rest.homeaway.feedparser.domain;

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
 *         &lt;element name="advertisers" type="{}AdvertiserListingIndex"/>
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
    "advertisers"
})
@XmlRootElement(name = "listingContentIndex")
public class ListingContentIndex {

    @XmlElement(required = true)
    protected AdvertiserListingIndex advertisers;

    /**
     * Gets the value of the advertisers property.
     * 
     * @return
     *     possible object is
     *     {@link AdvertiserListingIndex }
     *     
     */
    public AdvertiserListingIndex getAdvertisers() {
        return advertisers;
    }

    /**
     * Sets the value of the advertisers property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdvertiserListingIndex }
     *     
     */
    public void setAdvertisers(AdvertiserListingIndex value) {
        this.advertisers = value;
    }

}
