
package net.cbtltd.rest.homeaway.feedparser.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * A Listing is an advertisement for a vacation rental - it contains the details of the rental property that are presented to travelers. Listings encompass one or more Units. The Listing represents data about the property as a whole - the physical location and attributes of the property as a whole, for example. Each Unit represents a single rentable unit of the property, and describes the particulars of that unit.
 * 
 * 
 * <p>Java class for AdvertiserListings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AdvertiserListings">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="advertiser" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="assignedId">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="listings">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element ref="{}listing" maxOccurs="unbounded"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdvertiserListings", propOrder = {
    "advertiser"
})
public class AdvertiserListings {

    @XmlElement(required = true)
    protected List<AdvertiserListings.Advertiser> advertiser;

    /**
     * Gets the value of the advertiser property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the advertiser property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdvertiser().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdvertiserListings.Advertiser }
     * 
     * 
     */
    public List<AdvertiserListings.Advertiser> getAdvertiser() {
        if (advertiser == null) {
            advertiser = new ArrayList<AdvertiserListings.Advertiser>();
        }
        return this.advertiser;
    }


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
     *         &lt;element name="assignedId">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="listings">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element ref="{}listing" maxOccurs="unbounded"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
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
        "assignedId",
        "listings"
    })
    public static class Advertiser {

        @XmlElement(required = true)
        protected String assignedId;
        @XmlElement(required = true)
        protected AdvertiserListings.Advertiser.Listings listings;

        /**
         * Gets the value of the assignedId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAssignedId() {
            return assignedId;
        }

        /**
         * Sets the value of the assignedId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAssignedId(String value) {
            this.assignedId = value;
        }

        /**
         * Gets the value of the listings property.
         * 
         * @return
         *     possible object is
         *     {@link AdvertiserListings.Advertiser.Listings }
         *     
         */
        public AdvertiserListings.Advertiser.Listings getListings() {
            return listings;
        }

        /**
         * Sets the value of the listings property.
         * 
         * @param value
         *     allowed object is
         *     {@link AdvertiserListings.Advertiser.Listings }
         *     
         */
        public void setListings(AdvertiserListings.Advertiser.Listings value) {
            this.listings = value;
        }


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
         *         &lt;element ref="{}listing" maxOccurs="unbounded"/>
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
            "listing"
        })
        public static class Listings {

            @XmlElement(required = true)
            protected List<Listing> listing;

            /**
             * Gets the value of the listing property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the listing property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getListing().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Listing }
             * 
             * 
             */
            public List<Listing> getListing() {
                if (listing == null) {
                    listing = new ArrayList<Listing>();
                }
                return this.listing;
            }

        }

    }

}
