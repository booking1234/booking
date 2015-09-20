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
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * All details pertaining to a specific rate plan. 
 * 
 * <p>Java class for HotelRatePlanType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HotelRatePlanType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DestinationSystemsCode" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="DestinationSystemCode" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.opentravel.org/OTA/2003/05>StringLength1to32">
 *                           &lt;attribute name="ChainRateLevelCrossRef" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
 *                           &lt;attribute name="ChainRateCodeCrossRef" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
 *                           &lt;attribute name="LengthOfStayRuleID" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
 *                           &lt;attribute name="POS_RuleID" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
 *                           &lt;attribute name="RateAccessID" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
 *                           &lt;attribute name="GuaranteeDepositRuleID" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
 *                           &lt;attribute name="CancelRuleID" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="BookingRules" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://www.opentravel.org/OTA/2003/05}BookingRulesType">
 *                 &lt;sequence>
 *                   &lt;element name="InventoryInfo" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}InventoryGroup"/>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Rates" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Rate" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://www.opentravel.org/OTA/2003/05}RateUploadType">
 *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}InventoryGroup"/>
 *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DerivedRatePlanGroup"/>
 *                           &lt;attribute name="Status" type="{http://www.opentravel.org/OTA/2003/05}AvailabilityStatusType" />
 *                           &lt;attribute name="ExtraNightIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Supplements" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Supplement" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="RoomCompanions" maxOccurs="5" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}AgeQualifyingGroup"/>
 *                                     &lt;attribute name="MinCompanions" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                                     &lt;attribute name="MaxCompanions" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="PrerequisiteInventory" maxOccurs="5" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="InvCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
 *                                     &lt;attribute name="InvType" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="Description" type="{http://www.opentravel.org/OTA/2003/05}ParagraphType" maxOccurs="5" minOccurs="0"/>
 *                           &lt;/sequence>
 *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}CurrencyAmountGroup"/>
 *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}AgeQualifyingGroup"/>
 *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
 *                           &lt;attribute name="SupplementType">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *                                 &lt;enumeration value="AdditionalGuest"/>
 *                                 &lt;enumeration value="Board"/>
 *                                 &lt;enumeration value="Extra"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                           &lt;attribute name="ChargeTypeCode" type="{http://www.opentravel.org/OTA/2003/05}OTA_CodeType" />
 *                           &lt;attribute name="InvCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
 *                           &lt;attribute name="InvType" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
 *                           &lt;attribute name="AdditionalGuestNumber" type="{http://www.opentravel.org/OTA/2003/05}Numeric1to99" />
 *                           &lt;attribute name="RPH" type="{http://www.opentravel.org/OTA/2003/05}RPH_Type" />
 *                           &lt;attribute name="Percent" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *                           &lt;attribute name="AddToBasicRateIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                           &lt;attribute name="SingleUseIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                           &lt;attribute name="MandatoryIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Offers" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Offer" maxOccurs="99">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://www.opentravel.org/OTA/2003/05}OfferType">
 *                           &lt;attribute name="SupplementRPH" type="{http://www.opentravel.org/OTA/2003/05}ListOfRPH" />
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="RatePlanShoulders" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="RatePlanShoulder" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="SellableProducts" type="{http://www.opentravel.org/OTA/2003/05}SellableProductsType" minOccurs="0"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="ShoulderRPH" type="{http://www.opentravel.org/OTA/2003/05}RPH_Type" />
 *                           &lt;attribute name="PreShoulderSellLimit" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                           &lt;attribute name="PostShoulderSellLimit" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                           &lt;attribute name="PreShoulderStartDate" type="{http://www.opentravel.org/OTA/2003/05}DateOrDateTimeType" />
 *                           &lt;attribute name="PostShoulderEndDate" type="{http://www.opentravel.org/OTA/2003/05}DateOrDateTimeType" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="SellableProducts" type="{http://www.opentravel.org/OTA/2003/05}SellableProductsType" minOccurs="0"/>
 *         &lt;element name="RatePlanLevelFee" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Fee" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://www.opentravel.org/OTA/2003/05}FeeType">
 *                           &lt;attribute name="MealPlanCode" type="{http://www.opentravel.org/OTA/2003/05}OTA_CodeType" />
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}CodeListGroup"/>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Commission" type="{http://www.opentravel.org/OTA/2003/05}CommissionType" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.opentravel.org/OTA/2003/05}ParagraphType" maxOccurs="5" minOccurs="0"/>
 *         &lt;element name="UniqueID" type="{http://www.opentravel.org/OTA/2003/05}UniqueID_Type" minOccurs="0"/>
 *         &lt;element name="HotelRef" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}HotelReferenceGroup"/>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}CurrencyCodeGroup"/>
 *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}RatePlanGroup"/>
 *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DerivedRatePlanGroup"/>
 *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
 *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}RatePlanCodeTypeGroup"/>
 *       &lt;attribute name="RatePlanNotifType">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="New"/>
 *             &lt;enumeration value="Delta"/>
 *             &lt;enumeration value="Overlay"/>
 *             &lt;enumeration value="Remove"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="RatePlanStatusType">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="Initial"/>
 *             &lt;enumeration value="Active"/>
 *             &lt;enumeration value="Deactivated"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="RatePlanNotifScopeType">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="RatePlanOnly"/>
 *             &lt;enumeration value="RateOnly"/>
 *             &lt;enumeration value="RatePlanAndRate"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="IsCommissionable" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="RateReturn" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="YieldableIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="MarketCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
 *       &lt;attribute name="YieldDeltaAmount" type="{http://www.opentravel.org/OTA/2003/05}Money" />
 *       &lt;attribute name="InventoryAllocatedInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="RestrictedDisplayIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="EarliestStartIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="LatestEndIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="ExtraNightRatePlanCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
 *       &lt;attribute name="ChargeTypeCode" type="{http://www.opentravel.org/OTA/2003/05}OTA_CodeType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HotelRatePlanType", propOrder = {
    "destinationSystemsCode",
    "bookingRules",
    "rates",
    "supplements",
    "offers",
    "ratePlanShoulders",
    "sellableProducts",
    "ratePlanLevelFee",
    "commission",
    "description",
    "uniqueID",
    "hotelRef"
})
public class HotelRatePlanType {

    @XmlElement(name = "DestinationSystemsCode")
    protected HotelRatePlanType.DestinationSystemsCode destinationSystemsCode;
    @XmlElement(name = "BookingRules")
    protected HotelRatePlanType.BookingRules bookingRules;
    @XmlElement(name = "Rates")
    protected HotelRatePlanType.Rates rates;
    @XmlElement(name = "Supplements")
    protected HotelRatePlanType.Supplements supplements;
    @XmlElement(name = "Offers")
    protected HotelRatePlanType.Offers offers;
    @XmlElement(name = "RatePlanShoulders")
    protected HotelRatePlanType.RatePlanShoulders ratePlanShoulders;
    @XmlElement(name = "SellableProducts")
    protected SellableProductsType sellableProducts;
    @XmlElement(name = "RatePlanLevelFee")
    protected HotelRatePlanType.RatePlanLevelFee ratePlanLevelFee;
    @XmlElement(name = "Commission")
    protected CommissionType commission;
    @XmlElement(name = "Description")
    protected List<ParagraphType> description;
    @XmlElement(name = "UniqueID")
    protected UniqueIDType uniqueID;
    @XmlElement(name = "HotelRef")
    protected HotelRatePlanType.HotelRef hotelRef;
    @XmlAttribute(name = "RatePlanNotifType")
    protected String ratePlanNotifType;
    @XmlAttribute(name = "RatePlanStatusType")
    protected String ratePlanStatusType;
    @XmlAttribute(name = "RatePlanNotifScopeType")
    protected String ratePlanNotifScopeType;
    @XmlAttribute(name = "IsCommissionable")
    protected Boolean isCommissionable;
    @XmlAttribute(name = "RateReturn")
    protected Boolean rateReturn;
    @XmlAttribute(name = "YieldableIndicator")
    protected Boolean yieldableIndicator;
    @XmlAttribute(name = "MarketCode")
    protected String marketCode;
    @XmlAttribute(name = "YieldDeltaAmount")
    protected BigDecimal yieldDeltaAmount;
    @XmlAttribute(name = "InventoryAllocatedInd")
    protected Boolean inventoryAllocatedInd;
    @XmlAttribute(name = "RestrictedDisplayIndicator")
    protected Boolean restrictedDisplayIndicator;
    @XmlAttribute(name = "EarliestStartIndicator")
    protected Boolean earliestStartIndicator;
    @XmlAttribute(name = "LatestEndIndicator")
    protected Boolean latestEndIndicator;
    @XmlAttribute(name = "ExtraNightRatePlanCode")
    protected String extraNightRatePlanCode;
    @XmlAttribute(name = "ChargeTypeCode")
    protected String chargeTypeCode;
    @XmlAttribute(name = "CurrencyCode")
    protected String currencyCode;
    @XmlAttribute(name = "DecimalPlaces")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger decimalPlaces;
    @XmlAttribute(name = "RatePlanType")
    protected String ratePlanType;
    @XmlAttribute(name = "RatePlanCode")
    protected String ratePlanCode;
    @XmlAttribute(name = "RatePlanID")
    protected String ratePlanID;
    @XmlAttribute(name = "RatePlanQualifier")
    protected Boolean ratePlanQualifier;
    @XmlAttribute(name = "RatePlanCategory")
    protected String ratePlanCategory;
    @XmlAttribute(name = "PromotionCode")
    protected String promotionCode;
    @XmlAttribute(name = "PromotionVendorCode")
    protected List<String> promotionVendorCode;
    @XmlAttribute(name = "BaseRatePlanCode")
    protected String baseRatePlanCode;
    @XmlAttribute(name = "AdjustedAmount")
    protected BigDecimal adjustedAmount;
    @XmlAttribute(name = "AdjustedPercentage")
    protected BigDecimal adjustedPercentage;
    @XmlAttribute(name = "FloorAmount")
    protected BigDecimal floorAmount;
    @XmlAttribute(name = "CeilingAmount")
    protected BigDecimal ceilingAmount;
    @XmlAttribute(name = "AdjustUpIndicator")
    protected Boolean adjustUpIndicator;
    @XmlAttribute(name = "Start")
    protected String start;
    @XmlAttribute(name = "Duration")
    protected String duration;
    @XmlAttribute(name = "End")
    protected String end;
    @XmlAttribute(name = "RatePlanCodeType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String ratePlanCodeType;

    /**
     * Gets the value of the destinationSystemsCode property.
     * 
     * @return
     *     possible object is
     *     {@link HotelRatePlanType.DestinationSystemsCode }
     *     
     */
    public HotelRatePlanType.DestinationSystemsCode getDestinationSystemsCode() {
        return destinationSystemsCode;
    }

    /**
     * Sets the value of the destinationSystemsCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link HotelRatePlanType.DestinationSystemsCode }
     *     
     */
    public void setDestinationSystemsCode(HotelRatePlanType.DestinationSystemsCode value) {
        this.destinationSystemsCode = value;
    }

    /**
     * Gets the value of the bookingRules property.
     * 
     * @return
     *     possible object is
     *     {@link HotelRatePlanType.BookingRules }
     *     
     */
    public HotelRatePlanType.BookingRules getBookingRules() {
        return bookingRules;
    }

    /**
     * Sets the value of the bookingRules property.
     * 
     * @param value
     *     allowed object is
     *     {@link HotelRatePlanType.BookingRules }
     *     
     */
    public void setBookingRules(HotelRatePlanType.BookingRules value) {
        this.bookingRules = value;
    }

    /**
     * Gets the value of the rates property.
     * 
     * @return
     *     possible object is
     *     {@link HotelRatePlanType.Rates }
     *     
     */
    public HotelRatePlanType.Rates getRates() {
        return rates;
    }

    /**
     * Sets the value of the rates property.
     * 
     * @param value
     *     allowed object is
     *     {@link HotelRatePlanType.Rates }
     *     
     */
    public void setRates(HotelRatePlanType.Rates value) {
        this.rates = value;
    }

    /**
     * Gets the value of the supplements property.
     * 
     * @return
     *     possible object is
     *     {@link HotelRatePlanType.Supplements }
     *     
     */
    public HotelRatePlanType.Supplements getSupplements() {
        return supplements;
    }

    /**
     * Sets the value of the supplements property.
     * 
     * @param value
     *     allowed object is
     *     {@link HotelRatePlanType.Supplements }
     *     
     */
    public void setSupplements(HotelRatePlanType.Supplements value) {
        this.supplements = value;
    }

    /**
     * Gets the value of the offers property.
     * 
     * @return
     *     possible object is
     *     {@link HotelRatePlanType.Offers }
     *     
     */
    public HotelRatePlanType.Offers getOffers() {
        return offers;
    }

    /**
     * Sets the value of the offers property.
     * 
     * @param value
     *     allowed object is
     *     {@link HotelRatePlanType.Offers }
     *     
     */
    public void setOffers(HotelRatePlanType.Offers value) {
        this.offers = value;
    }

    /**
     * Gets the value of the ratePlanShoulders property.
     * 
     * @return
     *     possible object is
     *     {@link HotelRatePlanType.RatePlanShoulders }
     *     
     */
    public HotelRatePlanType.RatePlanShoulders getRatePlanShoulders() {
        return ratePlanShoulders;
    }

    /**
     * Sets the value of the ratePlanShoulders property.
     * 
     * @param value
     *     allowed object is
     *     {@link HotelRatePlanType.RatePlanShoulders }
     *     
     */
    public void setRatePlanShoulders(HotelRatePlanType.RatePlanShoulders value) {
        this.ratePlanShoulders = value;
    }

    /**
     * Gets the value of the sellableProducts property.
     * 
     * @return
     *     possible object is
     *     {@link SellableProductsType }
     *     
     */
    public SellableProductsType getSellableProducts() {
        return sellableProducts;
    }

    /**
     * Sets the value of the sellableProducts property.
     * 
     * @param value
     *     allowed object is
     *     {@link SellableProductsType }
     *     
     */
    public void setSellableProducts(SellableProductsType value) {
        this.sellableProducts = value;
    }

    /**
     * Gets the value of the ratePlanLevelFee property.
     * 
     * @return
     *     possible object is
     *     {@link HotelRatePlanType.RatePlanLevelFee }
     *     
     */
    public HotelRatePlanType.RatePlanLevelFee getRatePlanLevelFee() {
        return ratePlanLevelFee;
    }

    /**
     * Sets the value of the ratePlanLevelFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link HotelRatePlanType.RatePlanLevelFee }
     *     
     */
    public void setRatePlanLevelFee(HotelRatePlanType.RatePlanLevelFee value) {
        this.ratePlanLevelFee = value;
    }

    /**
     * Gets the value of the commission property.
     * 
     * @return
     *     possible object is
     *     {@link CommissionType }
     *     
     */
    public CommissionType getCommission() {
        return commission;
    }

    /**
     * Sets the value of the commission property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommissionType }
     *     
     */
    public void setCommission(CommissionType value) {
        this.commission = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the description property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescription().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParagraphType }
     * 
     * 
     */
    public List<ParagraphType> getDescription() {
        if (description == null) {
            description = new ArrayList<ParagraphType>();
        }
        return this.description;
    }

    /**
     * Gets the value of the uniqueID property.
     * 
     * @return
     *     possible object is
     *     {@link UniqueIDType }
     *     
     */
    public UniqueIDType getUniqueID() {
        return uniqueID;
    }

    /**
     * Sets the value of the uniqueID property.
     * 
     * @param value
     *     allowed object is
     *     {@link UniqueIDType }
     *     
     */
    public void setUniqueID(UniqueIDType value) {
        this.uniqueID = value;
    }

    /**
     * Gets the value of the hotelRef property.
     * 
     * @return
     *     possible object is
     *     {@link HotelRatePlanType.HotelRef }
     *     
     */
    public HotelRatePlanType.HotelRef getHotelRef() {
        return hotelRef;
    }

    /**
     * Sets the value of the hotelRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link HotelRatePlanType.HotelRef }
     *     
     */
    public void setHotelRef(HotelRatePlanType.HotelRef value) {
        this.hotelRef = value;
    }

    /**
     * Gets the value of the ratePlanNotifType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatePlanNotifType() {
        return ratePlanNotifType;
    }

    /**
     * Sets the value of the ratePlanNotifType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatePlanNotifType(String value) {
        this.ratePlanNotifType = value;
    }

    /**
     * Gets the value of the ratePlanStatusType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatePlanStatusType() {
        return ratePlanStatusType;
    }

    /**
     * Sets the value of the ratePlanStatusType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatePlanStatusType(String value) {
        this.ratePlanStatusType = value;
    }

    /**
     * Gets the value of the ratePlanNotifScopeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatePlanNotifScopeType() {
        return ratePlanNotifScopeType;
    }

    /**
     * Sets the value of the ratePlanNotifScopeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatePlanNotifScopeType(String value) {
        this.ratePlanNotifScopeType = value;
    }

    /**
     * Gets the value of the isCommissionable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsCommissionable() {
        return isCommissionable;
    }

    /**
     * Sets the value of the isCommissionable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsCommissionable(Boolean value) {
        this.isCommissionable = value;
    }

    /**
     * Gets the value of the rateReturn property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRateReturn() {
        return rateReturn;
    }

    /**
     * Sets the value of the rateReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRateReturn(Boolean value) {
        this.rateReturn = value;
    }

    /**
     * Gets the value of the yieldableIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isYieldableIndicator() {
        return yieldableIndicator;
    }

    /**
     * Sets the value of the yieldableIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setYieldableIndicator(Boolean value) {
        this.yieldableIndicator = value;
    }

    /**
     * Gets the value of the marketCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarketCode() {
        return marketCode;
    }

    /**
     * Sets the value of the marketCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarketCode(String value) {
        this.marketCode = value;
    }

    /**
     * Gets the value of the yieldDeltaAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getYieldDeltaAmount() {
        return yieldDeltaAmount;
    }

    /**
     * Sets the value of the yieldDeltaAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setYieldDeltaAmount(BigDecimal value) {
        this.yieldDeltaAmount = value;
    }

    /**
     * Gets the value of the inventoryAllocatedInd property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isInventoryAllocatedInd() {
        return inventoryAllocatedInd;
    }

    /**
     * Sets the value of the inventoryAllocatedInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInventoryAllocatedInd(Boolean value) {
        this.inventoryAllocatedInd = value;
    }

    /**
     * Gets the value of the restrictedDisplayIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRestrictedDisplayIndicator() {
        return restrictedDisplayIndicator;
    }

    /**
     * Sets the value of the restrictedDisplayIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRestrictedDisplayIndicator(Boolean value) {
        this.restrictedDisplayIndicator = value;
    }

    /**
     * Gets the value of the earliestStartIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEarliestStartIndicator() {
        return earliestStartIndicator;
    }

    /**
     * Sets the value of the earliestStartIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEarliestStartIndicator(Boolean value) {
        this.earliestStartIndicator = value;
    }

    /**
     * Gets the value of the latestEndIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLatestEndIndicator() {
        return latestEndIndicator;
    }

    /**
     * Sets the value of the latestEndIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLatestEndIndicator(Boolean value) {
        this.latestEndIndicator = value;
    }

    /**
     * Gets the value of the extraNightRatePlanCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtraNightRatePlanCode() {
        return extraNightRatePlanCode;
    }

    /**
     * Sets the value of the extraNightRatePlanCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtraNightRatePlanCode(String value) {
        this.extraNightRatePlanCode = value;
    }

    /**
     * Gets the value of the chargeTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargeTypeCode() {
        return chargeTypeCode;
    }

    /**
     * Sets the value of the chargeTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargeTypeCode(String value) {
        this.chargeTypeCode = value;
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

    /**
     * Gets the value of the ratePlanType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatePlanType() {
        return ratePlanType;
    }

    /**
     * Sets the value of the ratePlanType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatePlanType(String value) {
        this.ratePlanType = value;
    }

    /**
     * Gets the value of the ratePlanCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatePlanCode() {
        return ratePlanCode;
    }

    /**
     * Sets the value of the ratePlanCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatePlanCode(String value) {
        this.ratePlanCode = value;
    }

    /**
     * Gets the value of the ratePlanID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatePlanID() {
        return ratePlanID;
    }

    /**
     * Sets the value of the ratePlanID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatePlanID(String value) {
        this.ratePlanID = value;
    }

    /**
     * Gets the value of the ratePlanQualifier property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRatePlanQualifier() {
        return ratePlanQualifier;
    }

    /**
     * Sets the value of the ratePlanQualifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRatePlanQualifier(Boolean value) {
        this.ratePlanQualifier = value;
    }

    /**
     * Gets the value of the ratePlanCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatePlanCategory() {
        return ratePlanCategory;
    }

    /**
     * Sets the value of the ratePlanCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatePlanCategory(String value) {
        this.ratePlanCategory = value;
    }

    /**
     * Gets the value of the promotionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromotionCode() {
        return promotionCode;
    }

    /**
     * Sets the value of the promotionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromotionCode(String value) {
        this.promotionCode = value;
    }

    /**
     * Gets the value of the promotionVendorCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the promotionVendorCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPromotionVendorCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPromotionVendorCode() {
        if (promotionVendorCode == null) {
            promotionVendorCode = new ArrayList<String>();
        }
        return this.promotionVendorCode;
    }

    /**
     * Gets the value of the baseRatePlanCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBaseRatePlanCode() {
        return baseRatePlanCode;
    }

    /**
     * Sets the value of the baseRatePlanCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaseRatePlanCode(String value) {
        this.baseRatePlanCode = value;
    }

    /**
     * Gets the value of the adjustedAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAdjustedAmount() {
        return adjustedAmount;
    }

    /**
     * Sets the value of the adjustedAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAdjustedAmount(BigDecimal value) {
        this.adjustedAmount = value;
    }

    /**
     * Gets the value of the adjustedPercentage property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAdjustedPercentage() {
        return adjustedPercentage;
    }

    /**
     * Sets the value of the adjustedPercentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAdjustedPercentage(BigDecimal value) {
        this.adjustedPercentage = value;
    }

    /**
     * Gets the value of the floorAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFloorAmount() {
        return floorAmount;
    }

    /**
     * Sets the value of the floorAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFloorAmount(BigDecimal value) {
        this.floorAmount = value;
    }

    /**
     * Gets the value of the ceilingAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCeilingAmount() {
        return ceilingAmount;
    }

    /**
     * Sets the value of the ceilingAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCeilingAmount(BigDecimal value) {
        this.ceilingAmount = value;
    }

    /**
     * Gets the value of the adjustUpIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAdjustUpIndicator() {
        return adjustUpIndicator;
    }

    /**
     * Sets the value of the adjustUpIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAdjustUpIndicator(Boolean value) {
        this.adjustUpIndicator = value;
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

    /**
     * Gets the value of the ratePlanCodeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatePlanCodeType() {
        return ratePlanCodeType;
    }

    /**
     * Sets the value of the ratePlanCodeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatePlanCodeType(String value) {
        this.ratePlanCodeType = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://www.opentravel.org/OTA/2003/05}BookingRulesType">
     *       &lt;sequence>
     *         &lt;element name="InventoryInfo" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}InventoryGroup"/>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "inventoryInfo"
    })
    public static class BookingRules
        extends BookingRulesType
    {

        @XmlElement(name = "InventoryInfo")
        protected HotelRatePlanType.BookingRules.InventoryInfo inventoryInfo;

        /**
         * Gets the value of the inventoryInfo property.
         * 
         * @return
         *     possible object is
         *     {@link HotelRatePlanType.BookingRules.InventoryInfo }
         *     
         */
        public HotelRatePlanType.BookingRules.InventoryInfo getInventoryInfo() {
            return inventoryInfo;
        }

        /**
         * Sets the value of the inventoryInfo property.
         * 
         * @param value
         *     allowed object is
         *     {@link HotelRatePlanType.BookingRules.InventoryInfo }
         *     
         */
        public void setInventoryInfo(HotelRatePlanType.BookingRules.InventoryInfo value) {
            this.inventoryInfo = value;
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
        public static class InventoryInfo {

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
     *         &lt;element name="DestinationSystemCode" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.opentravel.org/OTA/2003/05>StringLength1to32">
     *                 &lt;attribute name="ChainRateLevelCrossRef" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
     *                 &lt;attribute name="ChainRateCodeCrossRef" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
     *                 &lt;attribute name="LengthOfStayRuleID" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
     *                 &lt;attribute name="POS_RuleID" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
     *                 &lt;attribute name="RateAccessID" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
     *                 &lt;attribute name="GuaranteeDepositRuleID" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
     *                 &lt;attribute name="CancelRuleID" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
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
        "destinationSystemCode"
    })
    public static class DestinationSystemsCode {

        @XmlElement(name = "DestinationSystemCode", required = true)
        protected List<HotelRatePlanType.DestinationSystemsCode.DestinationSystemCode> destinationSystemCode;

        /**
         * Gets the value of the destinationSystemCode property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the destinationSystemCode property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDestinationSystemCode().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link HotelRatePlanType.DestinationSystemsCode.DestinationSystemCode }
         * 
         * 
         */
        public List<HotelRatePlanType.DestinationSystemsCode.DestinationSystemCode> getDestinationSystemCode() {
            if (destinationSystemCode == null) {
                destinationSystemCode = new ArrayList<HotelRatePlanType.DestinationSystemsCode.DestinationSystemCode>();
            }
            return this.destinationSystemCode;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.opentravel.org/OTA/2003/05>StringLength1to32">
         *       &lt;attribute name="ChainRateLevelCrossRef" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
         *       &lt;attribute name="ChainRateCodeCrossRef" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
         *       &lt;attribute name="LengthOfStayRuleID" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
         *       &lt;attribute name="POS_RuleID" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
         *       &lt;attribute name="RateAccessID" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
         *       &lt;attribute name="GuaranteeDepositRuleID" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
         *       &lt;attribute name="CancelRuleID" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class DestinationSystemCode {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "ChainRateLevelCrossRef")
            protected String chainRateLevelCrossRef;
            @XmlAttribute(name = "ChainRateCodeCrossRef")
            protected String chainRateCodeCrossRef;
            @XmlAttribute(name = "LengthOfStayRuleID")
            protected String lengthOfStayRuleID;
            @XmlAttribute(name = "POS_RuleID")
            protected String posRuleID;
            @XmlAttribute(name = "RateAccessID")
            protected String rateAccessID;
            @XmlAttribute(name = "GuaranteeDepositRuleID")
            protected String guaranteeDepositRuleID;
            @XmlAttribute(name = "CancelRuleID")
            protected String cancelRuleID;

            /**
             * Used for Character Strings, length 1 to 32.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * Gets the value of the chainRateLevelCrossRef property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getChainRateLevelCrossRef() {
                return chainRateLevelCrossRef;
            }

            /**
             * Sets the value of the chainRateLevelCrossRef property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setChainRateLevelCrossRef(String value) {
                this.chainRateLevelCrossRef = value;
            }

            /**
             * Gets the value of the chainRateCodeCrossRef property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getChainRateCodeCrossRef() {
                return chainRateCodeCrossRef;
            }

            /**
             * Sets the value of the chainRateCodeCrossRef property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setChainRateCodeCrossRef(String value) {
                this.chainRateCodeCrossRef = value;
            }

            /**
             * Gets the value of the lengthOfStayRuleID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLengthOfStayRuleID() {
                return lengthOfStayRuleID;
            }

            /**
             * Sets the value of the lengthOfStayRuleID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLengthOfStayRuleID(String value) {
                this.lengthOfStayRuleID = value;
            }

            /**
             * Gets the value of the posRuleID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPOSRuleID() {
                return posRuleID;
            }

            /**
             * Sets the value of the posRuleID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPOSRuleID(String value) {
                this.posRuleID = value;
            }

            /**
             * Gets the value of the rateAccessID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRateAccessID() {
                return rateAccessID;
            }

            /**
             * Sets the value of the rateAccessID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRateAccessID(String value) {
                this.rateAccessID = value;
            }

            /**
             * Gets the value of the guaranteeDepositRuleID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getGuaranteeDepositRuleID() {
                return guaranteeDepositRuleID;
            }

            /**
             * Sets the value of the guaranteeDepositRuleID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setGuaranteeDepositRuleID(String value) {
                this.guaranteeDepositRuleID = value;
            }

            /**
             * Gets the value of the cancelRuleID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCancelRuleID() {
                return cancelRuleID;
            }

            /**
             * Sets the value of the cancelRuleID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCancelRuleID(String value) {
                this.cancelRuleID = value;
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
     *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}HotelReferenceGroup"/>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class HotelRef {

        @XmlAttribute(name = "ChainCode")
        protected String chainCode;
        @XmlAttribute(name = "BrandCode")
        protected String brandCode;
        @XmlAttribute(name = "HotelCode")
        protected String hotelCode;
        @XmlAttribute(name = "HotelCityCode")
        protected String hotelCityCode;
        @XmlAttribute(name = "HotelName")
        protected String hotelName;
        @XmlAttribute(name = "HotelCodeContext")
        protected String hotelCodeContext;
        @XmlAttribute(name = "ChainName")
        protected String chainName;
        @XmlAttribute(name = "BrandName")
        protected String brandName;
        @XmlAttribute(name = "AreaID")
        protected String areaID;

        /**
         * Gets the value of the chainCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getChainCode() {
            return chainCode;
        }

        /**
         * Sets the value of the chainCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setChainCode(String value) {
            this.chainCode = value;
        }

        /**
         * Gets the value of the brandCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBrandCode() {
            return brandCode;
        }

        /**
         * Sets the value of the brandCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBrandCode(String value) {
            this.brandCode = value;
        }

        /**
         * Gets the value of the hotelCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHotelCode() {
            return hotelCode;
        }

        /**
         * Sets the value of the hotelCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHotelCode(String value) {
            this.hotelCode = value;
        }

        /**
         * Gets the value of the hotelCityCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHotelCityCode() {
            return hotelCityCode;
        }

        /**
         * Sets the value of the hotelCityCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHotelCityCode(String value) {
            this.hotelCityCode = value;
        }

        /**
         * Gets the value of the hotelName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHotelName() {
            return hotelName;
        }

        /**
         * Sets the value of the hotelName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHotelName(String value) {
            this.hotelName = value;
        }

        /**
         * Gets the value of the hotelCodeContext property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHotelCodeContext() {
            return hotelCodeContext;
        }

        /**
         * Sets the value of the hotelCodeContext property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHotelCodeContext(String value) {
            this.hotelCodeContext = value;
        }

        /**
         * Gets the value of the chainName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getChainName() {
            return chainName;
        }

        /**
         * Sets the value of the chainName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setChainName(String value) {
            this.chainName = value;
        }

        /**
         * Gets the value of the brandName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBrandName() {
            return brandName;
        }

        /**
         * Sets the value of the brandName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBrandName(String value) {
            this.brandName = value;
        }

        /**
         * Gets the value of the areaID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAreaID() {
            return areaID;
        }

        /**
         * Sets the value of the areaID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAreaID(String value) {
            this.areaID = value;
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
     *         &lt;element name="Offer" maxOccurs="99">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://www.opentravel.org/OTA/2003/05}OfferType">
     *                 &lt;attribute name="SupplementRPH" type="{http://www.opentravel.org/OTA/2003/05}ListOfRPH" />
     *               &lt;/extension>
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
        "offer"
    })
    public static class Offers {

        @XmlElement(name = "Offer", required = true)
        protected List<HotelRatePlanType.Offers.Offer> offer;

        /**
         * Gets the value of the offer property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the offer property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOffer().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link HotelRatePlanType.Offers.Offer }
         * 
         * 
         */
        public List<HotelRatePlanType.Offers.Offer> getOffer() {
            if (offer == null) {
                offer = new ArrayList<HotelRatePlanType.Offers.Offer>();
            }
            return this.offer;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://www.opentravel.org/OTA/2003/05}OfferType">
         *       &lt;attribute name="SupplementRPH" type="{http://www.opentravel.org/OTA/2003/05}ListOfRPH" />
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Offer
            extends OfferType
        {

            @XmlAttribute(name = "SupplementRPH")
            protected List<String> supplementRPH;

            /**
             * Gets the value of the supplementRPH property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the supplementRPH property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getSupplementRPH().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link String }
             * 
             * 
             */
            public List<String> getSupplementRPH() {
                if (supplementRPH == null) {
                    supplementRPH = new ArrayList<String>();
                }
                return this.supplementRPH;
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
     *         &lt;element name="Fee" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://www.opentravel.org/OTA/2003/05}FeeType">
     *                 &lt;attribute name="MealPlanCode" type="{http://www.opentravel.org/OTA/2003/05}OTA_CodeType" />
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}CodeListGroup"/>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "fee"
    })
    public static class RatePlanLevelFee {

        @XmlElement(name = "Fee", required = true)
        protected List<HotelRatePlanType.RatePlanLevelFee.Fee> fee;
        @XmlAttribute(name = "URI")
        @XmlSchemaType(name = "anyURI")
        protected String uri;
        @XmlAttribute(name = "Quantity")
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger quantity;
        @XmlAttribute(name = "Code")
        protected String code;
        @XmlAttribute(name = "CodeContext")
        protected String codeContext;

        /**
         * Gets the value of the fee property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the fee property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFee().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link HotelRatePlanType.RatePlanLevelFee.Fee }
         * 
         * 
         */
        public List<HotelRatePlanType.RatePlanLevelFee.Fee> getFee() {
            if (fee == null) {
                fee = new ArrayList<HotelRatePlanType.RatePlanLevelFee.Fee>();
            }
            return this.fee;
        }

        /**
         * Gets the value of the uri property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getURI() {
            return uri;
        }

        /**
         * Sets the value of the uri property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setURI(String value) {
            this.uri = value;
        }

        /**
         * Gets the value of the quantity property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getQuantity() {
            return quantity;
        }

        /**
         * Sets the value of the quantity property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setQuantity(BigInteger value) {
            this.quantity = value;
        }

        /**
         * Gets the value of the code property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCode() {
            return code;
        }

        /**
         * Sets the value of the code property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCode(String value) {
            this.code = value;
        }

        /**
         * Gets the value of the codeContext property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodeContext() {
            return codeContext;
        }

        /**
         * Sets the value of the codeContext property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodeContext(String value) {
            this.codeContext = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://www.opentravel.org/OTA/2003/05}FeeType">
         *       &lt;attribute name="MealPlanCode" type="{http://www.opentravel.org/OTA/2003/05}OTA_CodeType" />
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Fee
            extends FeeType
        {

            @XmlAttribute(name = "MealPlanCode")
            protected String mealPlanCode;

            /**
             * Gets the value of the mealPlanCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMealPlanCode() {
                return mealPlanCode;
            }

            /**
             * Sets the value of the mealPlanCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMealPlanCode(String value) {
                this.mealPlanCode = value;
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
     *         &lt;element name="RatePlanShoulder" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="SellableProducts" type="{http://www.opentravel.org/OTA/2003/05}SellableProductsType" minOccurs="0"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="ShoulderRPH" type="{http://www.opentravel.org/OTA/2003/05}RPH_Type" />
     *                 &lt;attribute name="PreShoulderSellLimit" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *                 &lt;attribute name="PostShoulderSellLimit" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *                 &lt;attribute name="PreShoulderStartDate" type="{http://www.opentravel.org/OTA/2003/05}DateOrDateTimeType" />
     *                 &lt;attribute name="PostShoulderEndDate" type="{http://www.opentravel.org/OTA/2003/05}DateOrDateTimeType" />
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
        "ratePlanShoulder"
    })
    public static class RatePlanShoulders {

        @XmlElement(name = "RatePlanShoulder", required = true)
        protected List<HotelRatePlanType.RatePlanShoulders.RatePlanShoulder> ratePlanShoulder;

        /**
         * Gets the value of the ratePlanShoulder property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ratePlanShoulder property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRatePlanShoulder().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link HotelRatePlanType.RatePlanShoulders.RatePlanShoulder }
         * 
         * 
         */
        public List<HotelRatePlanType.RatePlanShoulders.RatePlanShoulder> getRatePlanShoulder() {
            if (ratePlanShoulder == null) {
                ratePlanShoulder = new ArrayList<HotelRatePlanType.RatePlanShoulders.RatePlanShoulder>();
            }
            return this.ratePlanShoulder;
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
         *         &lt;element name="SellableProducts" type="{http://www.opentravel.org/OTA/2003/05}SellableProductsType" minOccurs="0"/>
         *       &lt;/sequence>
         *       &lt;attribute name="ShoulderRPH" type="{http://www.opentravel.org/OTA/2003/05}RPH_Type" />
         *       &lt;attribute name="PreShoulderSellLimit" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
         *       &lt;attribute name="PostShoulderSellLimit" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
         *       &lt;attribute name="PreShoulderStartDate" type="{http://www.opentravel.org/OTA/2003/05}DateOrDateTimeType" />
         *       &lt;attribute name="PostShoulderEndDate" type="{http://www.opentravel.org/OTA/2003/05}DateOrDateTimeType" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "sellableProducts"
        })
        public static class RatePlanShoulder {

            @XmlElement(name = "SellableProducts")
            protected SellableProductsType sellableProducts;
            @XmlAttribute(name = "ShoulderRPH")
            protected String shoulderRPH;
            @XmlAttribute(name = "PreShoulderSellLimit")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger preShoulderSellLimit;
            @XmlAttribute(name = "PostShoulderSellLimit")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger postShoulderSellLimit;
            @XmlAttribute(name = "PreShoulderStartDate")
            protected String preShoulderStartDate;
            @XmlAttribute(name = "PostShoulderEndDate")
            protected String postShoulderEndDate;

            /**
             * Gets the value of the sellableProducts property.
             * 
             * @return
             *     possible object is
             *     {@link SellableProductsType }
             *     
             */
            public SellableProductsType getSellableProducts() {
                return sellableProducts;
            }

            /**
             * Sets the value of the sellableProducts property.
             * 
             * @param value
             *     allowed object is
             *     {@link SellableProductsType }
             *     
             */
            public void setSellableProducts(SellableProductsType value) {
                this.sellableProducts = value;
            }

            /**
             * Gets the value of the shoulderRPH property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getShoulderRPH() {
                return shoulderRPH;
            }

            /**
             * Sets the value of the shoulderRPH property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setShoulderRPH(String value) {
                this.shoulderRPH = value;
            }

            /**
             * Gets the value of the preShoulderSellLimit property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getPreShoulderSellLimit() {
                return preShoulderSellLimit;
            }

            /**
             * Sets the value of the preShoulderSellLimit property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setPreShoulderSellLimit(BigInteger value) {
                this.preShoulderSellLimit = value;
            }

            /**
             * Gets the value of the postShoulderSellLimit property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getPostShoulderSellLimit() {
                return postShoulderSellLimit;
            }

            /**
             * Sets the value of the postShoulderSellLimit property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setPostShoulderSellLimit(BigInteger value) {
                this.postShoulderSellLimit = value;
            }

            /**
             * Gets the value of the preShoulderStartDate property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPreShoulderStartDate() {
                return preShoulderStartDate;
            }

            /**
             * Sets the value of the preShoulderStartDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPreShoulderStartDate(String value) {
                this.preShoulderStartDate = value;
            }

            /**
             * Gets the value of the postShoulderEndDate property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPostShoulderEndDate() {
                return postShoulderEndDate;
            }

            /**
             * Sets the value of the postShoulderEndDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPostShoulderEndDate(String value) {
                this.postShoulderEndDate = value;
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
     *         &lt;element name="Rate" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://www.opentravel.org/OTA/2003/05}RateUploadType">
     *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}InventoryGroup"/>
     *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DerivedRatePlanGroup"/>
     *                 &lt;attribute name="Status" type="{http://www.opentravel.org/OTA/2003/05}AvailabilityStatusType" />
     *                 &lt;attribute name="ExtraNightIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *               &lt;/extension>
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
        "rate"
    })
    public static class Rates {

        @XmlElement(name = "Rate", required = true)
        protected List<HotelRatePlanType.Rates.Rate> rate;

        /**
         * Gets the value of the rate property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the rate property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRate().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link HotelRatePlanType.Rates.Rate }
         * 
         * 
         */
        public List<HotelRatePlanType.Rates.Rate> getRate() {
            if (rate == null) {
                rate = new ArrayList<HotelRatePlanType.Rates.Rate>();
            }
            return this.rate;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://www.opentravel.org/OTA/2003/05}RateUploadType">
         *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}InventoryGroup"/>
         *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DerivedRatePlanGroup"/>
         *       &lt;attribute name="Status" type="{http://www.opentravel.org/OTA/2003/05}AvailabilityStatusType" />
         *       &lt;attribute name="ExtraNightIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" />
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Rate
            extends RateUploadType
        {

            @XmlAttribute(name = "Status")
            protected List<String> status;
            @XmlAttribute(name = "ExtraNightIndicator")
            protected Boolean extraNightIndicator;
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
            @XmlAttribute(name = "BaseRatePlanCode")
            protected String baseRatePlanCode;
            @XmlAttribute(name = "AdjustedAmount")
            protected BigDecimal adjustedAmount;
            @XmlAttribute(name = "AdjustedPercentage")
            protected BigDecimal adjustedPercentage;
            @XmlAttribute(name = "FloorAmount")
            protected BigDecimal floorAmount;
            @XmlAttribute(name = "CeilingAmount")
            protected BigDecimal ceilingAmount;
            @XmlAttribute(name = "AdjustUpIndicator")
            protected Boolean adjustUpIndicator;

            /**
             * Gets the value of the status property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the status property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getStatus().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link String }
             * 
             * 
             */
            public List<String> getStatus() {
                if (status == null) {
                    status = new ArrayList<String>();
                }
                return this.status;
            }

            /**
             * Gets the value of the extraNightIndicator property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isExtraNightIndicator() {
                return extraNightIndicator;
            }

            /**
             * Sets the value of the extraNightIndicator property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setExtraNightIndicator(Boolean value) {
                this.extraNightIndicator = value;
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

            /**
             * Gets the value of the baseRatePlanCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBaseRatePlanCode() {
                return baseRatePlanCode;
            }

            /**
             * Sets the value of the baseRatePlanCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBaseRatePlanCode(String value) {
                this.baseRatePlanCode = value;
            }

            /**
             * Gets the value of the adjustedAmount property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getAdjustedAmount() {
                return adjustedAmount;
            }

            /**
             * Sets the value of the adjustedAmount property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setAdjustedAmount(BigDecimal value) {
                this.adjustedAmount = value;
            }

            /**
             * Gets the value of the adjustedPercentage property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getAdjustedPercentage() {
                return adjustedPercentage;
            }

            /**
             * Sets the value of the adjustedPercentage property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setAdjustedPercentage(BigDecimal value) {
                this.adjustedPercentage = value;
            }

            /**
             * Gets the value of the floorAmount property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getFloorAmount() {
                return floorAmount;
            }

            /**
             * Sets the value of the floorAmount property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setFloorAmount(BigDecimal value) {
                this.floorAmount = value;
            }

            /**
             * Gets the value of the ceilingAmount property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getCeilingAmount() {
                return ceilingAmount;
            }

            /**
             * Sets the value of the ceilingAmount property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setCeilingAmount(BigDecimal value) {
                this.ceilingAmount = value;
            }

            /**
             * Gets the value of the adjustUpIndicator property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isAdjustUpIndicator() {
                return adjustUpIndicator;
            }

            /**
             * Sets the value of the adjustUpIndicator property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setAdjustUpIndicator(Boolean value) {
                this.adjustUpIndicator = value;
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
     *         &lt;element name="Supplement" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="RoomCompanions" maxOccurs="5" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}AgeQualifyingGroup"/>
     *                           &lt;attribute name="MinCompanions" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *                           &lt;attribute name="MaxCompanions" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="PrerequisiteInventory" maxOccurs="5" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attribute name="InvCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
     *                           &lt;attribute name="InvType" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="Description" type="{http://www.opentravel.org/OTA/2003/05}ParagraphType" maxOccurs="5" minOccurs="0"/>
     *                 &lt;/sequence>
     *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}CurrencyAmountGroup"/>
     *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}AgeQualifyingGroup"/>
     *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
     *                 &lt;attribute name="SupplementType">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
     *                       &lt;enumeration value="AdditionalGuest"/>
     *                       &lt;enumeration value="Board"/>
     *                       &lt;enumeration value="Extra"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
     *                 &lt;attribute name="ChargeTypeCode" type="{http://www.opentravel.org/OTA/2003/05}OTA_CodeType" />
     *                 &lt;attribute name="InvCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
     *                 &lt;attribute name="InvType" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
     *                 &lt;attribute name="AdditionalGuestNumber" type="{http://www.opentravel.org/OTA/2003/05}Numeric1to99" />
     *                 &lt;attribute name="RPH" type="{http://www.opentravel.org/OTA/2003/05}RPH_Type" />
     *                 &lt;attribute name="Percent" type="{http://www.w3.org/2001/XMLSchema}decimal" />
     *                 &lt;attribute name="AddToBasicRateIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *                 &lt;attribute name="SingleUseIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *                 &lt;attribute name="MandatoryIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" />
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
        "supplement"
    })
    public static class Supplements {

        @XmlElement(name = "Supplement", required = true)
        protected List<HotelRatePlanType.Supplements.Supplement> supplement;

        /**
         * Gets the value of the supplement property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the supplement property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSupplement().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link HotelRatePlanType.Supplements.Supplement }
         * 
         * 
         */
        public List<HotelRatePlanType.Supplements.Supplement> getSupplement() {
            if (supplement == null) {
                supplement = new ArrayList<HotelRatePlanType.Supplements.Supplement>();
            }
            return this.supplement;
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
         *         &lt;element name="RoomCompanions" maxOccurs="5" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}AgeQualifyingGroup"/>
         *                 &lt;attribute name="MinCompanions" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
         *                 &lt;attribute name="MaxCompanions" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="PrerequisiteInventory" maxOccurs="5" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attribute name="InvCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
         *                 &lt;attribute name="InvType" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="Description" type="{http://www.opentravel.org/OTA/2003/05}ParagraphType" maxOccurs="5" minOccurs="0"/>
         *       &lt;/sequence>
         *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}CurrencyAmountGroup"/>
         *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}AgeQualifyingGroup"/>
         *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
         *       &lt;attribute name="SupplementType">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
         *             &lt;enumeration value="AdditionalGuest"/>
         *             &lt;enumeration value="Board"/>
         *             &lt;enumeration value="Extra"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *       &lt;attribute name="ChargeTypeCode" type="{http://www.opentravel.org/OTA/2003/05}OTA_CodeType" />
         *       &lt;attribute name="InvCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
         *       &lt;attribute name="InvType" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
         *       &lt;attribute name="AdditionalGuestNumber" type="{http://www.opentravel.org/OTA/2003/05}Numeric1to99" />
         *       &lt;attribute name="RPH" type="{http://www.opentravel.org/OTA/2003/05}RPH_Type" />
         *       &lt;attribute name="Percent" type="{http://www.w3.org/2001/XMLSchema}decimal" />
         *       &lt;attribute name="AddToBasicRateIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" />
         *       &lt;attribute name="SingleUseIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" />
         *       &lt;attribute name="MandatoryIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "roomCompanions",
            "prerequisiteInventory",
            "description"
        })
        public static class Supplement {

            @XmlElement(name = "RoomCompanions")
            protected List<HotelRatePlanType.Supplements.Supplement.RoomCompanions> roomCompanions;
            @XmlElement(name = "PrerequisiteInventory")
            protected List<HotelRatePlanType.Supplements.Supplement.PrerequisiteInventory> prerequisiteInventory;
            @XmlElement(name = "Description")
            protected List<ParagraphType> description;
            @XmlAttribute(name = "SupplementType")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String supplementType;
            @XmlAttribute(name = "ChargeTypeCode")
            protected String chargeTypeCode;
            @XmlAttribute(name = "InvCode")
            protected String invCode;
            @XmlAttribute(name = "InvType")
            protected String invType;
            @XmlAttribute(name = "AdditionalGuestNumber")
            protected Integer additionalGuestNumber;
            @XmlAttribute(name = "RPH")
            protected String rph;
            @XmlAttribute(name = "Percent")
            protected BigDecimal percent;
            @XmlAttribute(name = "AddToBasicRateIndicator")
            protected Boolean addToBasicRateIndicator;
            @XmlAttribute(name = "SingleUseIndicator")
            protected Boolean singleUseIndicator;
            @XmlAttribute(name = "MandatoryIndicator")
            protected Boolean mandatoryIndicator;
            @XmlAttribute(name = "Amount")
            protected BigDecimal amount;
            @XmlAttribute(name = "CurrencyCode")
            protected String currencyCode;
            @XmlAttribute(name = "DecimalPlaces")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger decimalPlaces;
            @XmlAttribute(name = "AgeQualifyingCode")
            protected String ageQualifyingCode;
            @XmlAttribute(name = "MinAge")
            protected Integer minAge;
            @XmlAttribute(name = "MaxAge")
            protected Integer maxAge;
            @XmlAttribute(name = "AgeTimeUnit")
            protected TimeUnitType ageTimeUnit;
            @XmlAttribute(name = "Start")
            protected String start;
            @XmlAttribute(name = "Duration")
            protected String duration;
            @XmlAttribute(name = "End")
            protected String end;

            /**
             * Gets the value of the roomCompanions property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the roomCompanions property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getRoomCompanions().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link HotelRatePlanType.Supplements.Supplement.RoomCompanions }
             * 
             * 
             */
            public List<HotelRatePlanType.Supplements.Supplement.RoomCompanions> getRoomCompanions() {
                if (roomCompanions == null) {
                    roomCompanions = new ArrayList<HotelRatePlanType.Supplements.Supplement.RoomCompanions>();
                }
                return this.roomCompanions;
            }

            /**
             * Gets the value of the prerequisiteInventory property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the prerequisiteInventory property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getPrerequisiteInventory().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link HotelRatePlanType.Supplements.Supplement.PrerequisiteInventory }
             * 
             * 
             */
            public List<HotelRatePlanType.Supplements.Supplement.PrerequisiteInventory> getPrerequisiteInventory() {
                if (prerequisiteInventory == null) {
                    prerequisiteInventory = new ArrayList<HotelRatePlanType.Supplements.Supplement.PrerequisiteInventory>();
                }
                return this.prerequisiteInventory;
            }

            /**
             * Gets the value of the description property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the description property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getDescription().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ParagraphType }
             * 
             * 
             */
            public List<ParagraphType> getDescription() {
                if (description == null) {
                    description = new ArrayList<ParagraphType>();
                }
                return this.description;
            }

            /**
             * Gets the value of the supplementType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSupplementType() {
                return supplementType;
            }

            /**
             * Sets the value of the supplementType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSupplementType(String value) {
                this.supplementType = value;
            }

            /**
             * Gets the value of the chargeTypeCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getChargeTypeCode() {
                return chargeTypeCode;
            }

            /**
             * Sets the value of the chargeTypeCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setChargeTypeCode(String value) {
                this.chargeTypeCode = value;
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
             * Gets the value of the additionalGuestNumber property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getAdditionalGuestNumber() {
                return additionalGuestNumber;
            }

            /**
             * Sets the value of the additionalGuestNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setAdditionalGuestNumber(Integer value) {
                this.additionalGuestNumber = value;
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
             * Gets the value of the addToBasicRateIndicator property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isAddToBasicRateIndicator() {
                return addToBasicRateIndicator;
            }

            /**
             * Sets the value of the addToBasicRateIndicator property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setAddToBasicRateIndicator(Boolean value) {
                this.addToBasicRateIndicator = value;
            }

            /**
             * Gets the value of the singleUseIndicator property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isSingleUseIndicator() {
                return singleUseIndicator;
            }

            /**
             * Sets the value of the singleUseIndicator property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setSingleUseIndicator(Boolean value) {
                this.singleUseIndicator = value;
            }

            /**
             * Gets the value of the mandatoryIndicator property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isMandatoryIndicator() {
                return mandatoryIndicator;
            }

            /**
             * Sets the value of the mandatoryIndicator property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setMandatoryIndicator(Boolean value) {
                this.mandatoryIndicator = value;
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


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;attribute name="InvCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
             *       &lt;attribute name="InvType" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class PrerequisiteInventory {

                @XmlAttribute(name = "InvCode")
                protected String invCode;
                @XmlAttribute(name = "InvType")
                protected String invType;

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
             *       &lt;attribute name="MinCompanions" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
             *       &lt;attribute name="MaxCompanions" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class RoomCompanions {

                @XmlAttribute(name = "MinCompanions")
                @XmlSchemaType(name = "nonNegativeInteger")
                protected BigInteger minCompanions;
                @XmlAttribute(name = "MaxCompanions")
                @XmlSchemaType(name = "nonNegativeInteger")
                protected BigInteger maxCompanions;
                @XmlAttribute(name = "AgeQualifyingCode")
                protected String ageQualifyingCode;
                @XmlAttribute(name = "MinAge")
                protected Integer minAge;
                @XmlAttribute(name = "MaxAge")
                protected Integer maxAge;
                @XmlAttribute(name = "AgeTimeUnit")
                protected TimeUnitType ageTimeUnit;

                /**
                 * Gets the value of the minCompanions property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigInteger }
                 *     
                 */
                public BigInteger getMinCompanions() {
                    return minCompanions;
                }

                /**
                 * Sets the value of the minCompanions property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigInteger }
                 *     
                 */
                public void setMinCompanions(BigInteger value) {
                    this.minCompanions = value;
                }

                /**
                 * Gets the value of the maxCompanions property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigInteger }
                 *     
                 */
                public BigInteger getMaxCompanions() {
                    return maxCompanions;
                }

                /**
                 * Sets the value of the maxCompanions property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigInteger }
                 *     
                 */
                public void setMaxCompanions(BigInteger value) {
                    this.maxCompanions = value;
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
