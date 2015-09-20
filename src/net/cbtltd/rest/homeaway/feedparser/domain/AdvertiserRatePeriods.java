
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
 * <p>Java class for AdvertiserRatePeriods complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AdvertiserRatePeriods">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="advertiser" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="assignedId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="advertiserRatePeriods" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="listingExternalId">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;minLength value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="unitExternalId">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;minLength value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element ref="{}ratePeriods"/>
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
@XmlType(name = "AdvertiserRatePeriods", propOrder = {
    "advertiser"
})
public class AdvertiserRatePeriods {

    @XmlElement(required = true)
    protected List<AdvertiserRatePeriods.Advertiser> advertiser;

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
     * {@link AdvertiserRatePeriods.Advertiser }
     * 
     * 
     */
    public List<AdvertiserRatePeriods.Advertiser> getAdvertiser() {
        if (advertiser == null) {
            advertiser = new ArrayList<AdvertiserRatePeriods.Advertiser>();
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
     *         &lt;element name="assignedId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="advertiserRatePeriods" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="listingExternalId">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;minLength value="1"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="unitExternalId">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;minLength value="1"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element ref="{}ratePeriods"/>
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
        "advertiserRatePeriods"
    })
    public static class Advertiser {

        @XmlElement(required = true)
        protected String assignedId;
        @XmlElement(required = true)
        protected List<AdvertiserRatePeriods.Advertiser.AdvRatePeriods> advertiserRatePeriods;

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
         * Gets the value of the advertiserRatePeriods property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the advertiserRatePeriods property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAdvertiserRatePeriods().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AdvertiserRatePeriods.Advertiser.AdvertiserRatePeriods }
         * 
         * 
         */
        public List<AdvertiserRatePeriods.Advertiser.AdvRatePeriods> getAdvertiserRatePeriods() {
            if (advertiserRatePeriods == null) {
                advertiserRatePeriods = new ArrayList<AdvertiserRatePeriods.Advertiser.AdvRatePeriods>();
            }
            return this.advertiserRatePeriods;
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
        @XmlType(name = "", propOrder = {
            "listingExternalId",
            "unitExternalId",
            "ratePeriods"
        })
        public static class AdvRatePeriods {

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

    }

}
