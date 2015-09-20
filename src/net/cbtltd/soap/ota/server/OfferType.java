/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */

package net.cbtltd.soap.ota.server;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.Duration;


/**
 * Defines the type of offer, the conditions under which the offer can be applied and the amounts and/or percentages deducted.
 * 
 * <p>Java class for OfferType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OfferType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OfferRules" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="OfferRule" maxOccurs="99">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="DateRestriction" maxOccurs="5" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
 *                                     &lt;attribute name="RestrictionType">
 *                                       &lt;simpleType>
 *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *                                           &lt;enumeration value="arrival"/>
 *                                           &lt;enumeration value="departure"/>
 *                                           &lt;enumeration value="booking"/>
 *                                           &lt;enumeration value="stay"/>
 *                                         &lt;/restriction>
 *                                       &lt;/simpleType>
 *                                     &lt;/attribute>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="LengthsOfStay" type="{http://www.opentravel.org/OTA/2003/05}LengthsOfStayType" minOccurs="0"/>
 *                             &lt;element name="DOW_Restrictions" type="{http://www.opentravel.org/OTA/2003/05}DOW_RestrictionsType" minOccurs="0"/>
 *                             &lt;element name="Occupancy" maxOccurs="5" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}OccupancyGroup"/>
 *                                     &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}AgeQualifyingGroup"/>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="Inventories" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Inventory" maxOccurs="99">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}InventoryGroup"/>
 *                                               &lt;attribute name="AppliesToIndicator" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="StayOverDate" type="{http://www.opentravel.org/OTA/2003/05}DayOfWeekType" />
 *                           &lt;attribute name="MinTotalOccupancy" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                           &lt;attribute name="MaxTotalOccupancy" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                           &lt;attribute name="MaxContiguousBookings" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                           &lt;attribute name="MaxAdvancedBookingOffset" type="{http://www.w3.org/2001/XMLSchema}duration" />
 *                           &lt;attribute name="MinAdvancedBookingOffset" type="{http://www.w3.org/2001/XMLSchema}duration" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Discount" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}CurrencyAmountGroup"/>
 *                 &lt;attribute name="NightsRequired" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                 &lt;attribute name="NightsDiscounted" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                 &lt;attribute name="DiscountPattern" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to32" />
 *                 &lt;attribute name="Percent" type="{http://www.opentravel.org/OTA/2003/05}Percentage" />
 *                 &lt;attribute name="ChargeUnitCode" type="{http://www.opentravel.org/OTA/2003/05}OTA_CodeType" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="FreeUpgrade" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="UpgradeFrom" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}InventoryGroup"/>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="UpgradeTo" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}InventoryGroup"/>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="UpgradeBookingCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="OfferDescription" type="{http://www.opentravel.org/OTA/2003/05}ParagraphType" minOccurs="0"/>
 *         &lt;element name="CompatibleOffers" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="CompatibleOffer" maxOccurs="99">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="OfferRPH" type="{http://www.opentravel.org/OTA/2003/05}ListOfRPH" />
 *                           &lt;attribute name="IncompatibleOfferIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Inventories" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Inventory" maxOccurs="99">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}InventoryGroup"/>
 *                           &lt;attribute name="AppliesToIndicator" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Guests" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Guest" maxOccurs="9">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}AgeQualifyingGroup"/>
 *                           &lt;attribute name="MinCount" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                           &lt;attribute name="MaxCount" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                           &lt;attribute name="FirstQualifyingPosition" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                           &lt;attribute name="LastQualifyingPosition" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
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
 *       &lt;attribute name="OfferCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
 *       &lt;attribute name="RPH" type="{http://www.opentravel.org/OTA/2003/05}RPH_Type" />
 *       &lt;attribute name="ApplicationOrder" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OfferType", propOrder = {
    "offerRules",
    "discount",
    "freeUpgrade",
    "offerDescription",
    "compatibleOffers",
    "inventories",
    "guests"
})
@XmlSeeAlso({
    net.cbtltd.soap.ota.server.HotelRatePlanType.Offers.Offer.class
})
public class OfferType {

    @XmlElement(name = "OfferRules")
    protected OfferType.OfferRules offerRules;
    @XmlElement(name = "Discount")
    protected OfferType.Discount discount;
    @XmlElement(name = "FreeUpgrade")
    protected OfferType.FreeUpgrade freeUpgrade;
    @XmlElement(name = "OfferDescription")
    protected ParagraphType offerDescription;
    @XmlElement(name = "CompatibleOffers")
    protected OfferType.CompatibleOffers compatibleOffers;
    @XmlElement(name = "Inventories")
    protected OfferType.Inventories inventories;
    @XmlElement(name = "Guests")
    protected OfferType.Guests guests;
    @XmlAttribute(name = "OfferCode")
    protected String offerCode;
    @XmlAttribute(name = "RPH")
    protected String rph;
    @XmlAttribute(name = "ApplicationOrder")
    protected BigInteger applicationOrder;

    /**
     * Gets the value of the offerRules property.
     * 
     * @return
     *     possible object is
     *     {@link OfferType.OfferRules }
     *     
     */
    public OfferType.OfferRules getOfferRules() {
        return offerRules;
    }

    /**
     * Sets the value of the offerRules property.
     * 
     * @param value
     *     allowed object is
     *     {@link OfferType.OfferRules }
     *     
     */
    public void setOfferRules(OfferType.OfferRules value) {
        this.offerRules = value;
    }

    /**
     * Gets the value of the discount property.
     * 
     * @return
     *     possible object is
     *     {@link OfferType.Discount }
     *     
     */
    public OfferType.Discount getDiscount() {
        return discount;
    }

    /**
     * Sets the value of the discount property.
     * 
     * @param value
     *     allowed object is
     *     {@link OfferType.Discount }
     *     
     */
    public void setDiscount(OfferType.Discount value) {
        this.discount = value;
    }

    /**
     * Gets the value of the freeUpgrade property.
     * 
     * @return
     *     possible object is
     *     {@link OfferType.FreeUpgrade }
     *     
     */
    public OfferType.FreeUpgrade getFreeUpgrade() {
        return freeUpgrade;
    }

    /**
     * Sets the value of the freeUpgrade property.
     * 
     * @param value
     *     allowed object is
     *     {@link OfferType.FreeUpgrade }
     *     
     */
    public void setFreeUpgrade(OfferType.FreeUpgrade value) {
        this.freeUpgrade = value;
    }

    /**
     * Gets the value of the offerDescription property.
     * 
     * @return
     *     possible object is
     *     {@link ParagraphType }
     *     
     */
    public ParagraphType getOfferDescription() {
        return offerDescription;
    }

    /**
     * Sets the value of the offerDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParagraphType }
     *     
     */
    public void setOfferDescription(ParagraphType value) {
        this.offerDescription = value;
    }

    /**
     * Gets the value of the compatibleOffers property.
     * 
     * @return
     *     possible object is
     *     {@link OfferType.CompatibleOffers }
     *     
     */
    public OfferType.CompatibleOffers getCompatibleOffers() {
        return compatibleOffers;
    }

    /**
     * Sets the value of the compatibleOffers property.
     * 
     * @param value
     *     allowed object is
     *     {@link OfferType.CompatibleOffers }
     *     
     */
    public void setCompatibleOffers(OfferType.CompatibleOffers value) {
        this.compatibleOffers = value;
    }

    /**
     * Gets the value of the inventories property.
     * 
     * @return
     *     possible object is
     *     {@link OfferType.Inventories }
     *     
     */
    public OfferType.Inventories getInventories() {
        return inventories;
    }

    /**
     * Sets the value of the inventories property.
     * 
     * @param value
     *     allowed object is
     *     {@link OfferType.Inventories }
     *     
     */
    public void setInventories(OfferType.Inventories value) {
        this.inventories = value;
    }

    /**
     * Gets the value of the guests property.
     * 
     * @return
     *     possible object is
     *     {@link OfferType.Guests }
     *     
     */
    public OfferType.Guests getGuests() {
        return guests;
    }

    /**
     * Sets the value of the guests property.
     * 
     * @param value
     *     allowed object is
     *     {@link OfferType.Guests }
     *     
     */
    public void setGuests(OfferType.Guests value) {
        this.guests = value;
    }

    /**
     * Gets the value of the offerCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfferCode() {
        return offerCode;
    }

    /**
     * Sets the value of the offerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfferCode(String value) {
        this.offerCode = value;
    }

    /**
     * Gets the value of the rph property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRPH() {
        return rph;
    }

    /**
     * Sets the value of the rph property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRPH(String value) {
        this.rph = value;
    }

    /**
     * Gets the value of the applicationOrder property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getApplicationOrder() {
        return applicationOrder;
    }

    /**
     * Sets the value of the applicationOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setApplicationOrder(BigInteger value) {
        this.applicationOrder = value;
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
     *         &lt;element name="CompatibleOffer" maxOccurs="99">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="OfferRPH" type="{http://www.opentravel.org/OTA/2003/05}ListOfRPH" />
     *                 &lt;attribute name="IncompatibleOfferIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" />
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
        "compatibleOffer"
    })
    public static class CompatibleOffers {

        @XmlElement(name = "CompatibleOffer", required = true)
        protected List<OfferType.CompatibleOffers.CompatibleOffer> compatibleOffer;

        /**
         * Gets the value of the compatibleOffer property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the compatibleOffer property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCompatibleOffer().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OfferType.CompatibleOffers.CompatibleOffer }
         * 
         * 
         */
        public List<OfferType.CompatibleOffers.CompatibleOffer> getCompatibleOffer() {
            if (compatibleOffer == null) {
                compatibleOffer = new ArrayList<OfferType.CompatibleOffers.CompatibleOffer>();
            }
            return this.compatibleOffer;
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
         *       &lt;attribute name="OfferRPH" type="{http://www.opentravel.org/OTA/2003/05}ListOfRPH" />
         *       &lt;attribute name="IncompatibleOfferIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class CompatibleOffer {

            @XmlAttribute(name = "OfferRPH")
            protected List<String> offerRPH;
            @XmlAttribute(name = "IncompatibleOfferIndicator")
            protected Boolean incompatibleOfferIndicator;

            /**
             * Gets the value of the offerRPH property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the offerRPH property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getOfferRPH().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link String }
             * 
             * 
             */
            public List<String> getOfferRPH() {
                if (offerRPH == null) {
                    offerRPH = new ArrayList<String>();
                }
                return this.offerRPH;
            }

            /**
             * Gets the value of the incompatibleOfferIndicator property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isIncompatibleOfferIndicator() {
                return incompatibleOfferIndicator;
            }

            /**
             * Sets the value of the incompatibleOfferIndicator property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setIncompatibleOfferIndicator(Boolean value) {
                this.incompatibleOfferIndicator = value;
            }

        }

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
     *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}CurrencyAmountGroup"/>
     *       &lt;attribute name="NightsRequired" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *       &lt;attribute name="NightsDiscounted" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *       &lt;attribute name="DiscountPattern" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to32" />
     *       &lt;attribute name="Percent" type="{http://www.opentravel.org/OTA/2003/05}Percentage" />
     *       &lt;attribute name="ChargeUnitCode" type="{http://www.opentravel.org/OTA/2003/05}OTA_CodeType" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Discount {

        @XmlAttribute(name = "NightsRequired")
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger nightsRequired;
        @XmlAttribute(name = "NightsDiscounted")
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger nightsDiscounted;
        @XmlAttribute(name = "DiscountPattern")
        protected String discountPattern;
        @XmlAttribute(name = "Percent")
        protected BigDecimal percent;
        @XmlAttribute(name = "ChargeUnitCode")
        protected String chargeUnitCode;
        @XmlAttribute(name = "Amount")
        protected BigDecimal amount;
        @XmlAttribute(name = "CurrencyCode")
        protected String currencyCode;
        @XmlAttribute(name = "DecimalPlaces")
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger decimalPlaces;

        /**
         * Gets the value of the nightsRequired property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getNightsRequired() {
            return nightsRequired;
        }

        /**
         * Sets the value of the nightsRequired property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setNightsRequired(BigInteger value) {
            this.nightsRequired = value;
        }

        /**
         * Gets the value of the nightsDiscounted property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getNightsDiscounted() {
            return nightsDiscounted;
        }

        /**
         * Sets the value of the nightsDiscounted property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setNightsDiscounted(BigInteger value) {
            this.nightsDiscounted = value;
        }

        /**
         * Gets the value of the discountPattern property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDiscountPattern() {
            return discountPattern;
        }

        /**
         * Sets the value of the discountPattern property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDiscountPattern(String value) {
            this.discountPattern = value;
        }

        /**
         * Gets the value of the percent property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getPercent() {
            return percent;
        }

        /**
         * Sets the value of the percent property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setPercent(BigDecimal value) {
            this.percent = value;
        }

        /**
         * Gets the value of the chargeUnitCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getChargeUnitCode() {
            return chargeUnitCode;
        }

        /**
         * Sets the value of the chargeUnitCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setChargeUnitCode(String value) {
            this.chargeUnitCode = value;
        }

        /**
         * Gets the value of the amount property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getAmount() {
            return amount;
        }

        /**
         * Sets the value of the amount property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setAmount(BigDecimal value) {
            this.amount = value;
        }

        /**
         * Gets the value of the currencyCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCurrencyCode() {
            return currencyCode;
        }

        /**
         * Sets the value of the currencyCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCurrencyCode(String value) {
            this.currencyCode = value;
        }

        /**
         * Gets the value of the decimalPlaces property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getDecimalPlaces() {
            return decimalPlaces;
        }

        /**
         * Sets the value of the decimalPlaces property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setDecimalPlaces(BigInteger value) {
            this.decimalPlaces = value;
        }

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
     *         &lt;element name="UpgradeFrom" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}InventoryGroup"/>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="UpgradeTo" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}InventoryGroup"/>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="UpgradeBookingCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "upgradeFrom",
        "upgradeTo"
    })
    public static class FreeUpgrade {

        @XmlElement(name = "UpgradeFrom")
        protected OfferType.FreeUpgrade.UpgradeFrom upgradeFrom;
        @XmlElement(name = "UpgradeTo")
        protected OfferType.FreeUpgrade.UpgradeTo upgradeTo;
        @XmlAttribute(name = "UpgradeBookingCode")
        protected String upgradeBookingCode;

        /**
         * Gets the value of the upgradeFrom property.
         * 
         * @return
         *     possible object is
         *     {@link OfferType.FreeUpgrade.UpgradeFrom }
         *     
         */
        public OfferType.FreeUpgrade.UpgradeFrom getUpgradeFrom() {
            return upgradeFrom;
        }

        /**
         * Sets the value of the upgradeFrom property.
         * 
         * @param value
         *     allowed object is
         *     {@link OfferType.FreeUpgrade.UpgradeFrom }
         *     
         */
        public void setUpgradeFrom(OfferType.FreeUpgrade.UpgradeFrom value) {
            this.upgradeFrom = value;
        }

        /**
         * Gets the value of the upgradeTo property.
         * 
         * @return
         *     possible object is
         *     {@link OfferType.FreeUpgrade.UpgradeTo }
         *     
         */
        public OfferType.FreeUpgrade.UpgradeTo getUpgradeTo() {
            return upgradeTo;
        }

        /**
         * Sets the value of the upgradeTo property.
         * 
         * @param value
         *     allowed object is
         *     {@link OfferType.FreeUpgrade.UpgradeTo }
         *     
         */
        public void setUpgradeTo(OfferType.FreeUpgrade.UpgradeTo value) {
            this.upgradeTo = value;
        }

        /**
         * Gets the value of the upgradeBookingCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUpgradeBookingCode() {
            return upgradeBookingCode;
        }

        /**
         * Sets the value of the upgradeBookingCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUpgradeBookingCode(String value) {
            this.upgradeBookingCode = value;
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
         *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}InventoryGroup"/>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class UpgradeFrom {

            @XmlAttribute(name = "InvCodeApplication")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String invCodeApplication;
            @XmlAttribute(name = "InvCode")
            protected String invCode;
            @XmlAttribute(name = "InvType")
            protected String invType;
            @XmlAttribute(name = "InvTypeCode")
            protected String invTypeCode;
            @XmlAttribute(name = "IsRoom")
            protected Boolean isRoom;

            /**
             * Gets the value of the invCodeApplication property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getInvCodeApplication() {
                return invCodeApplication;
            }

            /**
             * Sets the value of the invCodeApplication property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setInvCodeApplication(String value) {
                this.invCodeApplication = value;
            }

            /**
             * Gets the value of the invCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getInvCode() {
                return invCode;
            }

            /**
             * Sets the value of the invCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setInvCode(String value) {
                this.invCode = value;
            }

            /**
             * Gets the value of the invType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getInvType() {
                return invType;
            }

            /**
             * Sets the value of the invType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setInvType(String value) {
                this.invType = value;
            }

            /**
             * Gets the value of the invTypeCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getInvTypeCode() {
                return invTypeCode;
            }

            /**
             * Sets the value of the invTypeCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setInvTypeCode(String value) {
                this.invTypeCode = value;
            }

            /**
             * Gets the value of the isRoom property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isIsRoom() {
                return isRoom;
            }

            /**
             * Sets the value of the isRoom property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setIsRoom(Boolean value) {
                this.isRoom = value;
            }

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
         *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}InventoryGroup"/>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class UpgradeTo {

            @XmlAttribute(name = "InvCodeApplication")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String invCodeApplication;
            @XmlAttribute(name = "InvCode")
            protected String invCode;
            @XmlAttribute(name = "InvType")
            protected String invType;
            @XmlAttribute(name = "InvTypeCode")
            protected String invTypeCode;
            @XmlAttribute(name = "IsRoom")
            protected Boolean isRoom;

            /**
             * Gets the value of the invCodeApplication property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getInvCodeApplication() {
                return invCodeApplication;
            }

            /**
             * Sets the value of the invCodeApplication property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setInvCodeApplication(String value) {
                this.invCodeApplication = value;
            }

            /**
             * Gets the value of the invCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getInvCode() {
                return invCode;
            }

            /**
             * Sets the value of the invCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setInvCode(String value) {
                this.invCode = value;
            }

            /**
             * Gets the value of the invType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getInvType() {
                return invType;
            }

            /**
             * Sets the value of the invType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setInvType(String value) {
                this.invType = value;
            }

            /**
             * Gets the value of the invTypeCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getInvTypeCode() {
                return invTypeCode;
            }

            /**
             * Sets the value of the invTypeCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setInvTypeCode(String value) {
                this.invTypeCode = value;
            }

            /**
             * Gets the value of the isRoom property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isIsRoom() {
                return isRoom;
            }

            /**
             * Sets the value of the isRoom property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setIsRoom(Boolean value) {
                this.isRoom = value;
            }

        }

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
     *         &lt;element name="Guest" maxOccurs="9">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}AgeQualifyingGroup"/>
     *                 &lt;attribute name="MinCount" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *                 &lt;attribute name="MaxCount" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *                 &lt;attribute name="FirstQualifyingPosition" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *                 &lt;attribute name="LastQualifyingPosition" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
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
        "guest"
    })
    public static class Guests {

        @XmlElement(name = "Guest", required = true)
        protected List<OfferType.Guests.Guest> guest;

        /**
         * Gets the value of the guest property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the guest property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getGuest().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OfferType.Guests.Guest }
         * 
         * 
         */
        public List<OfferType.Guests.Guest> getGuest() {
            if (guest == null) {
                guest = new ArrayList<OfferType.Guests.Guest>();
            }
            return this.guest;
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
         *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}AgeQualifyingGroup"/>
         *       &lt;attribute name="MinCount" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
         *       &lt;attribute name="MaxCount" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
         *       &lt;attribute name="FirstQualifyingPosition" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
         *       &lt;attribute name="LastQualifyingPosition" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Guest {

            @XmlAttribute(name = "MinCount")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger minCount;
            @XmlAttribute(name = "MaxCount")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger maxCount;
            @XmlAttribute(name = "FirstQualifyingPosition")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger firstQualifyingPosition;
            @XmlAttribute(name = "LastQualifyingPosition")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger lastQualifyingPosition;
            @XmlAttribute(name = "AgeQualifyingCode")
            protected String ageQualifyingCode;
            @XmlAttribute(name = "MinAge")
            protected Integer minAge;
            @XmlAttribute(name = "MaxAge")
            protected Integer maxAge;
            @XmlAttribute(name = "AgeTimeUnit")
            protected TimeUnitType ageTimeUnit;

            /**
             * Gets the value of the minCount property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getMinCount() {
                return minCount;
            }

            /**
             * Sets the value of the minCount property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setMinCount(BigInteger value) {
                this.minCount = value;
            }

            /**
             * Gets the value of the maxCount property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getMaxCount() {
                return maxCount;
            }

            /**
             * Sets the value of the maxCount property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setMaxCount(BigInteger value) {
                this.maxCount = value;
            }

            /**
             * Gets the value of the firstQualifyingPosition property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getFirstQualifyingPosition() {
                return firstQualifyingPosition;
            }

            /**
             * Sets the value of the firstQualifyingPosition property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setFirstQualifyingPosition(BigInteger value) {
                this.firstQualifyingPosition = value;
            }

            /**
             * Gets the value of the lastQualifyingPosition property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getLastQualifyingPosition() {
                return lastQualifyingPosition;
            }

            /**
             * Sets the value of the lastQualifyingPosition property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setLastQualifyingPosition(BigInteger value) {
                this.lastQualifyingPosition = value;
            }

            /**
             * Gets the value of the ageQualifyingCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAgeQualifyingCode() {
                return ageQualifyingCode;
            }

            /**
             * Sets the value of the ageQualifyingCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAgeQualifyingCode(String value) {
                this.ageQualifyingCode = value;
            }

            /**
             * Gets the value of the minAge property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getMinAge() {
                return minAge;
            }

            /**
             * Sets the value of the minAge property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setMinAge(Integer value) {
                this.minAge = value;
            }

            /**
             * Gets the value of the maxAge property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getMaxAge() {
                return maxAge;
            }

            /**
             * Sets the value of the maxAge property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setMaxAge(Integer value) {
                this.maxAge = value;
            }

            /**
             * Gets the value of the ageTimeUnit property.
             * 
             * @return
             *     possible object is
             *     {@link TimeUnitType }
             *     
             */
            public TimeUnitType getAgeTimeUnit() {
                return ageTimeUnit;
            }

            /**
             * Sets the value of the ageTimeUnit property.
             * 
             * @param value
             *     allowed object is
             *     {@link TimeUnitType }
             *     
             */
            public void setAgeTimeUnit(TimeUnitType value) {
                this.ageTimeUnit = value;
            }

        }

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
     *         &lt;element name="Inventory" maxOccurs="99">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}InventoryGroup"/>
     *                 &lt;attribute name="AppliesToIndicator" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
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
        "inventory"
    })
    public static class Inventories {

        @XmlElement(name = "Inventory", required = true)
        protected List<OfferType.Inventories.Inventory> inventory;

        /**
         * Gets the value of the inventory property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the inventory property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getInventory().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OfferType.Inventories.Inventory }
         * 
         * 
         */
        public List<OfferType.Inventories.Inventory> getInventory() {
            if (inventory == null) {
                inventory = new ArrayList<OfferType.Inventories.Inventory>();
            }
            return this.inventory;
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
         *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}InventoryGroup"/>
         *       &lt;attribute name="AppliesToIndicator" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Inventory {

            @XmlAttribute(name = "AppliesToIndicator", required = true)
            protected boolean appliesToIndicator;
            @XmlAttribute(name = "InvCodeApplication")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String invCodeApplication;
            @XmlAttribute(name = "InvCode")
            protected String invCode;
            @XmlAttribute(name = "InvType")
            protected String invType;
            @XmlAttribute(name = "InvTypeCode")
            protected String invTypeCode;
            @XmlAttribute(name = "IsRoom")
            protected Boolean isRoom;

            /**
             * Gets the value of the appliesToIndicator property.
             * 
             */
            public boolean isAppliesToIndicator() {
                return appliesToIndicator;
            }

            /**
             * Sets the value of the appliesToIndicator property.
             * 
             */
            public void setAppliesToIndicator(boolean value) {
                this.appliesToIndicator = value;
            }

            /**
             * Gets the value of the invCodeApplication property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getInvCodeApplication() {
                return invCodeApplication;
            }

            /**
             * Sets the value of the invCodeApplication property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setInvCodeApplication(String value) {
                this.invCodeApplication = value;
            }

            /**
             * Gets the value of the invCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getInvCode() {
                return invCode;
            }

            /**
             * Sets the value of the invCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setInvCode(String value) {
                this.invCode = value;
            }

            /**
             * Gets the value of the invType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getInvType() {
                return invType;
            }

            /**
             * Sets the value of the invType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setInvType(String value) {
                this.invType = value;
            }

            /**
             * Gets the value of the invTypeCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getInvTypeCode() {
                return invTypeCode;
            }

            /**
             * Sets the value of the invTypeCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setInvTypeCode(String value) {
                this.invTypeCode = value;
            }

            /**
             * Gets the value of the isRoom property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isIsRoom() {
                return isRoom;
            }

            /**
             * Sets the value of the isRoom property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setIsRoom(Boolean value) {
                this.isRoom = value;
            }

        }

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
     *         &lt;element name="OfferRule" maxOccurs="99">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="DateRestriction" maxOccurs="5" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
     *                           &lt;attribute name="RestrictionType">
     *                             &lt;simpleType>
     *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
     *                                 &lt;enumeration value="arrival"/>
     *                                 &lt;enumeration value="departure"/>
     *                                 &lt;enumeration value="booking"/>
     *                                 &lt;enumeration value="stay"/>
     *                               &lt;/restriction>
     *                             &lt;/simpleType>
     *                           &lt;/attribute>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="LengthsOfStay" type="{http://www.opentravel.org/OTA/2003/05}LengthsOfStayType" minOccurs="0"/>
     *                   &lt;element name="DOW_Restrictions" type="{http://www.opentravel.org/OTA/2003/05}DOW_RestrictionsType" minOccurs="0"/>
     *                   &lt;element name="Occupancy" maxOccurs="5" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}OccupancyGroup"/>
     *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}AgeQualifyingGroup"/>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="Inventories" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Inventory" maxOccurs="99">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}InventoryGroup"/>
     *                                     &lt;attribute name="AppliesToIndicator" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="StayOverDate" type="{http://www.opentravel.org/OTA/2003/05}DayOfWeekType" />
     *                 &lt;attribute name="MinTotalOccupancy" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *                 &lt;attribute name="MaxTotalOccupancy" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *                 &lt;attribute name="MaxContiguousBookings" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *                 &lt;attribute name="MaxAdvancedBookingOffset" type="{http://www.w3.org/2001/XMLSchema}duration" />
     *                 &lt;attribute name="MinAdvancedBookingOffset" type="{http://www.w3.org/2001/XMLSchema}duration" />
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
        "offerRule"
    })
    public static class OfferRules {

        @XmlElement(name = "OfferRule", required = true)
        protected List<OfferType.OfferRules.OfferRule> offerRule;

        /**
         * Gets the value of the offerRule property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the offerRule property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOfferRule().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OfferType.OfferRules.OfferRule }
         * 
         * 
         */
        public List<OfferType.OfferRules.OfferRule> getOfferRule() {
            if (offerRule == null) {
                offerRule = new ArrayList<OfferType.OfferRules.OfferRule>();
            }
            return this.offerRule;
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
         *         &lt;element name="DateRestriction" maxOccurs="5" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
         *                 &lt;attribute name="RestrictionType">
         *                   &lt;simpleType>
         *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
         *                       &lt;enumeration value="arrival"/>
         *                       &lt;enumeration value="departure"/>
         *                       &lt;enumeration value="booking"/>
         *                       &lt;enumeration value="stay"/>
         *                     &lt;/restriction>
         *                   &lt;/simpleType>
         *                 &lt;/attribute>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="LengthsOfStay" type="{http://www.opentravel.org/OTA/2003/05}LengthsOfStayType" minOccurs="0"/>
         *         &lt;element name="DOW_Restrictions" type="{http://www.opentravel.org/OTA/2003/05}DOW_RestrictionsType" minOccurs="0"/>
         *         &lt;element name="Occupancy" maxOccurs="5" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}OccupancyGroup"/>
         *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}AgeQualifyingGroup"/>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="Inventories" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Inventory" maxOccurs="99">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}InventoryGroup"/>
         *                           &lt;attribute name="AppliesToIndicator" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
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
         *       &lt;attribute name="StayOverDate" type="{http://www.opentravel.org/OTA/2003/05}DayOfWeekType" />
         *       &lt;attribute name="MinTotalOccupancy" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
         *       &lt;attribute name="MaxTotalOccupancy" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
         *       &lt;attribute name="MaxContiguousBookings" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
         *       &lt;attribute name="MaxAdvancedBookingOffset" type="{http://www.w3.org/2001/XMLSchema}duration" />
         *       &lt;attribute name="MinAdvancedBookingOffset" type="{http://www.w3.org/2001/XMLSchema}duration" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "dateRestriction",
            "lengthsOfStay",
            "dowRestrictions",
            "occupancy",
            "inventories"
        })
        public static class OfferRule {

            @XmlElement(name = "DateRestriction")
            protected List<OfferType.OfferRules.OfferRule.DateRestriction> dateRestriction;
            @XmlElement(name = "LengthsOfStay")
            protected LengthsOfStayType lengthsOfStay;
            @XmlElement(name = "DOW_Restrictions")
            protected DOWRestrictionsType dowRestrictions;
            @XmlElement(name = "Occupancy")
            protected List<OfferType.OfferRules.OfferRule.Occupancy> occupancy;
            @XmlElement(name = "Inventories")
            protected OfferType.OfferRules.OfferRule.Inventories inventories;
            @XmlAttribute(name = "StayOverDate")
            protected DayOfWeekType stayOverDate;
            @XmlAttribute(name = "MinTotalOccupancy")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger minTotalOccupancy;
            @XmlAttribute(name = "MaxTotalOccupancy")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger maxTotalOccupancy;
            @XmlAttribute(name = "MaxContiguousBookings")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger maxContiguousBookings;
            @XmlAttribute(name = "MaxAdvancedBookingOffset")
            protected Duration maxAdvancedBookingOffset;
            @XmlAttribute(name = "MinAdvancedBookingOffset")
            protected Duration minAdvancedBookingOffset;

            /**
             * Gets the value of the dateRestriction property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the dateRestriction property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getDateRestriction().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link OfferType.OfferRules.OfferRule.DateRestriction }
             * 
             * 
             */
            public List<OfferType.OfferRules.OfferRule.DateRestriction> getDateRestriction() {
                if (dateRestriction == null) {
                    dateRestriction = new ArrayList<OfferType.OfferRules.OfferRule.DateRestriction>();
                }
                return this.dateRestriction;
            }

            /**
             * Gets the value of the lengthsOfStay property.
             * 
             * @return
             *     possible object is
             *     {@link LengthsOfStayType }
             *     
             */
            public LengthsOfStayType getLengthsOfStay() {
                return lengthsOfStay;
            }

            /**
             * Sets the value of the lengthsOfStay property.
             * 
             * @param value
             *     allowed object is
             *     {@link LengthsOfStayType }
             *     
             */
            public void setLengthsOfStay(LengthsOfStayType value) {
                this.lengthsOfStay = value;
            }

            /**
             * Gets the value of the dowRestrictions property.
             * 
             * @return
             *     possible object is
             *     {@link DOWRestrictionsType }
             *     
             */
            public DOWRestrictionsType getDOWRestrictions() {
                return dowRestrictions;
            }

            /**
             * Sets the value of the dowRestrictions property.
             * 
             * @param value
             *     allowed object is
             *     {@link DOWRestrictionsType }
             *     
             */
            public void setDOWRestrictions(DOWRestrictionsType value) {
                this.dowRestrictions = value;
            }

            /**
             * Gets the value of the occupancy property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the occupancy property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getOccupancy().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link OfferType.OfferRules.OfferRule.Occupancy }
             * 
             * 
             */
            public List<OfferType.OfferRules.OfferRule.Occupancy> getOccupancy() {
                if (occupancy == null) {
                    occupancy = new ArrayList<OfferType.OfferRules.OfferRule.Occupancy>();
                }
                return this.occupancy;
            }

            /**
             * Gets the value of the inventories property.
             * 
             * @return
             *     possible object is
             *     {@link OfferType.OfferRules.OfferRule.Inventories }
             *     
             */
            public OfferType.OfferRules.OfferRule.Inventories getInventories() {
                return inventories;
            }

            /**
             * Sets the value of the inventories property.
             * 
             * @param value
             *     allowed object is
             *     {@link OfferType.OfferRules.OfferRule.Inventories }
             *     
             */
            public void setInventories(OfferType.OfferRules.OfferRule.Inventories value) {
                this.inventories = value;
            }

            /**
             * Gets the value of the stayOverDate property.
             * 
             * @return
             *     possible object is
             *     {@link DayOfWeekType }
             *     
             */
            public DayOfWeekType getStayOverDate() {
                return stayOverDate;
            }

            /**
             * Sets the value of the stayOverDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link DayOfWeekType }
             *     
             */
            public void setStayOverDate(DayOfWeekType value) {
                this.stayOverDate = value;
            }

            /**
             * Gets the value of the minTotalOccupancy property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getMinTotalOccupancy() {
                return minTotalOccupancy;
            }

            /**
             * Sets the value of the minTotalOccupancy property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setMinTotalOccupancy(BigInteger value) {
                this.minTotalOccupancy = value;
            }

            /**
             * Gets the value of the maxTotalOccupancy property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getMaxTotalOccupancy() {
                return maxTotalOccupancy;
            }

            /**
             * Sets the value of the maxTotalOccupancy property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setMaxTotalOccupancy(BigInteger value) {
                this.maxTotalOccupancy = value;
            }

            /**
             * Gets the value of the maxContiguousBookings property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getMaxContiguousBookings() {
                return maxContiguousBookings;
            }

            /**
             * Sets the value of the maxContiguousBookings property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setMaxContiguousBookings(BigInteger value) {
                this.maxContiguousBookings = value;
            }

            /**
             * Gets the value of the maxAdvancedBookingOffset property.
             * 
             * @return
             *     possible object is
             *     {@link Duration }
             *     
             */
            public Duration getMaxAdvancedBookingOffset() {
                return maxAdvancedBookingOffset;
            }

            /**
             * Sets the value of the maxAdvancedBookingOffset property.
             * 
             * @param value
             *     allowed object is
             *     {@link Duration }
             *     
             */
            public void setMaxAdvancedBookingOffset(Duration value) {
                this.maxAdvancedBookingOffset = value;
            }

            /**
             * Gets the value of the minAdvancedBookingOffset property.
             * 
             * @return
             *     possible object is
             *     {@link Duration }
             *     
             */
            public Duration getMinAdvancedBookingOffset() {
                return minAdvancedBookingOffset;
            }

            /**
             * Sets the value of the minAdvancedBookingOffset property.
             * 
             * @param value
             *     allowed object is
             *     {@link Duration }
             *     
             */
            public void setMinAdvancedBookingOffset(Duration value) {
                this.minAdvancedBookingOffset = value;
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
             *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
             *       &lt;attribute name="RestrictionType">
             *         &lt;simpleType>
             *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
             *             &lt;enumeration value="arrival"/>
             *             &lt;enumeration value="departure"/>
             *             &lt;enumeration value="booking"/>
             *             &lt;enumeration value="stay"/>
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
            @XmlType(name = "")
            public static class DateRestriction {

                @XmlAttribute(name = "RestrictionType")
                @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                protected String restrictionType;
                @XmlAttribute(name = "Start")
                protected String start;
                @XmlAttribute(name = "Duration")
                protected String duration;
                @XmlAttribute(name = "End")
                protected String end;

                /**
                 * Gets the value of the restrictionType property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getRestrictionType() {
                    return restrictionType;
                }

                /**
                 * Sets the value of the restrictionType property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setRestrictionType(String value) {
                    this.restrictionType = value;
                }

                /**
                 * Gets the value of the start property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getStart() {
                    return start;
                }

                /**
                 * Sets the value of the start property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setStart(String value) {
                    this.start = value;
                }

                /**
                 * Gets the value of the duration property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDuration() {
                    return duration;
                }

                /**
                 * Sets the value of the duration property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDuration(String value) {
                    this.duration = value;
                }

                /**
                 * Gets the value of the end property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getEnd() {
                    return end;
                }

                /**
                 * Sets the value of the end property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setEnd(String value) {
                    this.end = value;
                }

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
             *         &lt;element name="Inventory" maxOccurs="99">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}InventoryGroup"/>
             *                 &lt;attribute name="AppliesToIndicator" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
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
                "inventory"
            })
            public static class Inventories {

                @XmlElement(name = "Inventory", required = true)
                protected List<OfferType.OfferRules.OfferRule.Inventories.Inventory> inventory;

                /**
                 * Gets the value of the inventory property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the inventory property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getInventory().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link OfferType.OfferRules.OfferRule.Inventories.Inventory }
                 * 
                 * 
                 */
                public List<OfferType.OfferRules.OfferRule.Inventories.Inventory> getInventory() {
                    if (inventory == null) {
                        inventory = new ArrayList<OfferType.OfferRules.OfferRule.Inventories.Inventory>();
                    }
                    return this.inventory;
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
                 *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}InventoryGroup"/>
                 *       &lt;attribute name="AppliesToIndicator" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "")
                public static class Inventory {

                    @XmlAttribute(name = "AppliesToIndicator", required = true)
                    protected boolean appliesToIndicator;
                    @XmlAttribute(name = "InvCodeApplication")
                    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                    protected String invCodeApplication;
                    @XmlAttribute(name = "InvCode")
                    protected String invCode;
                    @XmlAttribute(name = "InvType")
                    protected String invType;
                    @XmlAttribute(name = "InvTypeCode")
                    protected String invTypeCode;
                    @XmlAttribute(name = "IsRoom")
                    protected Boolean isRoom;

                    /**
                     * Gets the value of the appliesToIndicator property.
                     * 
                     */
                    public boolean isAppliesToIndicator() {
                        return appliesToIndicator;
                    }

                    /**
                     * Sets the value of the appliesToIndicator property.
                     * 
                     */
                    public void setAppliesToIndicator(boolean value) {
                        this.appliesToIndicator = value;
                    }

                    /**
                     * Gets the value of the invCodeApplication property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getInvCodeApplication() {
                        return invCodeApplication;
                    }

                    /**
                     * Sets the value of the invCodeApplication property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setInvCodeApplication(String value) {
                        this.invCodeApplication = value;
                    }

                    /**
                     * Gets the value of the invCode property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getInvCode() {
                        return invCode;
                    }

                    /**
                     * Sets the value of the invCode property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setInvCode(String value) {
                        this.invCode = value;
                    }

                    /**
                     * Gets the value of the invType property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getInvType() {
                        return invType;
                    }

                    /**
                     * Sets the value of the invType property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setInvType(String value) {
                        this.invType = value;
                    }

                    /**
                     * Gets the value of the invTypeCode property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getInvTypeCode() {
                        return invTypeCode;
                    }

                    /**
                     * Sets the value of the invTypeCode property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setInvTypeCode(String value) {
                        this.invTypeCode = value;
                    }

                    /**
                     * Gets the value of the isRoom property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Boolean }
                     *     
                     */
                    public Boolean isIsRoom() {
                        return isRoom;
                    }

                    /**
                     * Sets the value of the isRoom property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Boolean }
                     *     
                     */
                    public void setIsRoom(Boolean value) {
                        this.isRoom = value;
                    }

                }

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
             *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}OccupancyGroup"/>
             *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}AgeQualifyingGroup"/>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Occupancy {

                @XmlAttribute(name = "MinOccupancy")
                protected Integer minOccupancy;
                @XmlAttribute(name = "MaxOccupancy")
                protected Integer maxOccupancy;
                @XmlAttribute(name = "AgeQualifyingCode")
                protected String ageQualifyingCode;
                @XmlAttribute(name = "MinAge")
                protected Integer minAge;
                @XmlAttribute(name = "MaxAge")
                protected Integer maxAge;
                @XmlAttribute(name = "AgeTimeUnit")
                protected TimeUnitType ageTimeUnit;

                /**
                 * Gets the value of the minOccupancy property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Integer }
                 *     
                 */
                public Integer getMinOccupancy() {
                    return minOccupancy;
                }

                /**
                 * Sets the value of the minOccupancy property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Integer }
                 *     
                 */
                public void setMinOccupancy(Integer value) {
                    this.minOccupancy = value;
                }

                /**
                 * Gets the value of the maxOccupancy property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Integer }
                 *     
                 */
                public Integer getMaxOccupancy() {
                    return maxOccupancy;
                }

                /**
                 * Sets the value of the maxOccupancy property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Integer }
                 *     
                 */
                public void setMaxOccupancy(Integer value) {
                    this.maxOccupancy = value;
                }

                /**
                 * Gets the value of the ageQualifyingCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAgeQualifyingCode() {
                    return ageQualifyingCode;
                }

                /**
                 * Sets the value of the ageQualifyingCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAgeQualifyingCode(String value) {
                    this.ageQualifyingCode = value;
                }

                /**
                 * Gets the value of the minAge property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Integer }
                 *     
                 */
                public Integer getMinAge() {
                    return minAge;
                }

                /**
                 * Sets the value of the minAge property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Integer }
                 *     
                 */
                public void setMinAge(Integer value) {
                    this.minAge = value;
                }

                /**
                 * Gets the value of the maxAge property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Integer }
                 *     
                 */
                public Integer getMaxAge() {
                    return maxAge;
                }

                /**
                 * Sets the value of the maxAge property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Integer }
                 *     
                 */
                public void setMaxAge(Integer value) {
                    this.maxAge = value;
                }

                /**
                 * Gets the value of the ageTimeUnit property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link TimeUnitType }
                 *     
                 */
                public TimeUnitType getAgeTimeUnit() {
                    return ageTimeUnit;
                }

                /**
                 * Sets the value of the ageTimeUnit property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link TimeUnitType }
                 *     
                 */
                public void setAgeTimeUnit(TimeUnitType value) {
                    this.ageTimeUnit = value;
                }

            }

        }

    }

}
