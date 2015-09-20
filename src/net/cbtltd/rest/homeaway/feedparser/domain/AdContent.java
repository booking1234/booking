
package net.cbtltd.rest.homeaway.feedparser.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Textual content for a Listing.
 * 
 * <p>Java class for AdContent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AdContent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accommodationsSummary" type="{}Note" minOccurs="0"/>
 *         &lt;element name="description" type="{}Note"/>
 *         &lt;element name="headline" type="{}Note"/>
 *         &lt;element name="ownerListingStory" type="{}Note" minOccurs="0"/>
 *         &lt;element name="propertyName" type="{}Note" minOccurs="0"/>
 *         &lt;element name="uniqueBenefits" type="{}Note" minOccurs="0"/>
 *         &lt;element name="whyPurchased" type="{}Note" minOccurs="0"/>
 *         &lt;element name="yearPurchased" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdContent", propOrder = {
    "accommodationsSummary",
    "description",
    "headline",
    "ownerListingStory",
    "propertyName",
    "uniqueBenefits",
    "whyPurchased",
    "yearPurchased"
})
public class AdContent {

    protected Note accommodationsSummary;
    @XmlElement(required = true)
    protected Note description;
    @XmlElement(required = true)
    protected Note headline;
    protected Note ownerListingStory;
    protected Note propertyName;
    protected Note uniqueBenefits;
    protected Note whyPurchased;
    protected String yearPurchased;

    /**
     * Gets the value of the accommodationsSummary property.
     * 
     * @return
     *     possible object is
     *     {@link Note }
     *     
     */
    public Note getAccommodationsSummary() {
        return accommodationsSummary;
    }

    /**
     * Sets the value of the accommodationsSummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link Note }
     *     
     */
    public void setAccommodationsSummary(Note value) {
        this.accommodationsSummary = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link Note }
     *     
     */
    public Note getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link Note }
     *     
     */
    public void setDescription(Note value) {
        this.description = value;
    }

    /**
     * Gets the value of the headline property.
     * 
     * @return
     *     possible object is
     *     {@link Note }
     *     
     */
    public Note getHeadline() {
        return headline;
    }

    /**
     * Sets the value of the headline property.
     * 
     * @param value
     *     allowed object is
     *     {@link Note }
     *     
     */
    public void setHeadline(Note value) {
        this.headline = value;
    }

    /**
     * Gets the value of the ownerListingStory property.
     * 
     * @return
     *     possible object is
     *     {@link Note }
     *     
     */
    public Note getOwnerListingStory() {
        return ownerListingStory;
    }

    /**
     * Sets the value of the ownerListingStory property.
     * 
     * @param value
     *     allowed object is
     *     {@link Note }
     *     
     */
    public void setOwnerListingStory(Note value) {
        this.ownerListingStory = value;
    }

    /**
     * Gets the value of the propertyName property.
     * 
     * @return
     *     possible object is
     *     {@link Note }
     *     
     */
    public Note getPropertyName() {
        return propertyName;
    }

    /**
     * Sets the value of the propertyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link Note }
     *     
     */
    public void setPropertyName(Note value) {
        this.propertyName = value;
    }

    /**
     * Gets the value of the uniqueBenefits property.
     * 
     * @return
     *     possible object is
     *     {@link Note }
     *     
     */
    public Note getUniqueBenefits() {
        return uniqueBenefits;
    }

    /**
     * Sets the value of the uniqueBenefits property.
     * 
     * @param value
     *     allowed object is
     *     {@link Note }
     *     
     */
    public void setUniqueBenefits(Note value) {
        this.uniqueBenefits = value;
    }

    /**
     * Gets the value of the whyPurchased property.
     * 
     * @return
     *     possible object is
     *     {@link Note }
     *     
     */
    public Note getWhyPurchased() {
        return whyPurchased;
    }

    /**
     * Sets the value of the whyPurchased property.
     * 
     * @param value
     *     allowed object is
     *     {@link Note }
     *     
     */
    public void setWhyPurchased(Note value) {
        this.whyPurchased = value;
    }

    /**
     * Gets the value of the yearPurchased property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYearPurchased() {
        return yearPurchased;
    }

    /**
     * Sets the value of the yearPurchased property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYearPurchased(String value) {
        this.yearPurchased = value;
    }

}
