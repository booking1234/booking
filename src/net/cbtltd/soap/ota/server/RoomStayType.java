/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */

package net.cbtltd.soap.ota.server;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Details on the Room Stay including Guest Counts, Time Span of this Room Stay, pointers to Res Guests, guest Memberships, Comments and Special Requests pertaining to this particular Room Stay and finally finacial information related to the Room Stay, including Guarantee, Deposit and Payment and Cancellation Penalties.
 * 
 * <p>Java class for RoomStayType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RoomStayType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RoomTypes" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="RoomType" type="{http://www.opentravel.org/OTA/2003/05}RoomTypeType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="RatePlans" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="RatePlan" type="{http://www.opentravel.org/OTA/2003/05}RatePlanType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="RoomRates" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="RoomRate" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://www.opentravel.org/OTA/2003/05}RoomRateType">
 *                           &lt;sequence>
 *                             &lt;element name="AdvanceBookingRestriction" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
 *                                     &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DOW_PatternGroup"/>
 *                                     &lt;attribute name="MinAdvanceBookingOffset" type="{http://www.w3.org/2001/XMLSchema}duration" />
 *                                     &lt;attribute name="MaxAdvanceBookingOffset" type="{http://www.w3.org/2001/XMLSchema}duration" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="Restrictions" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Restriction" maxOccurs="unbounded">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="DOW_Restrictions" type="{http://www.opentravel.org/OTA/2003/05}DOW_RestrictionsType"/>
 *                                               &lt;/sequence>
 *                                               &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}EffectiveExpireOptionalDateGroup"/>
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
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="MoreRatesExistInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="GuestCounts" type="{http://www.opentravel.org/OTA/2003/05}GuestCountType" minOccurs="0"/>
 *         &lt;element name="TimeSpan" type="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanType" minOccurs="0"/>
 *         &lt;element name="Guarantee" type="{http://www.opentravel.org/OTA/2003/05}GuaranteeType" maxOccurs="5" minOccurs="0"/>
 *         &lt;element name="DepositPayments" type="{http://www.opentravel.org/OTA/2003/05}RequiredPaymentsType" minOccurs="0"/>
 *         &lt;element name="CancelPenalties" type="{http://www.opentravel.org/OTA/2003/05}CancelPenaltiesType" minOccurs="0"/>
 *         &lt;element name="Discount" type="{http://www.opentravel.org/OTA/2003/05}DiscountType" minOccurs="0"/>
 *         &lt;element name="Total" type="{http://www.opentravel.org/OTA/2003/05}TotalType" minOccurs="0"/>
 *         &lt;element name="BasicPropertyInfo" type="{http://www.opentravel.org/OTA/2003/05}BasicPropertyInfoType" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentravel.org/OTA/2003/05}TPA_Extensions" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}PromotionCodeGroup"/>
 *       &lt;attribute name="MarketCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
 *       &lt;attribute name="SourceOfBusiness" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
 *       &lt;attribute name="DiscountCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
 *       &lt;attribute name="RoomStayStatus" type="{http://www.opentravel.org/OTA/2003/05}HotelResStatusType" />
 *       &lt;attribute name="WarningRPH" type="{http://www.opentravel.org/OTA/2003/05}ListOfRPH" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoomStayType", propOrder = {
    "roomTypes",
    "ratePlans",
    "roomRates",
    "guestCounts",
    "timeSpan",
    "guarantee",
    "depositPayments",
    "cancelPenalties",
    "discount",
    "total",
    "basicPropertyInfo",
    "tpaExtensions"
})
@XmlSeeAlso({
    net.cbtltd.soap.ota.server.OTAHotelAvailRS.RoomStays.RoomStay.class,
    net.cbtltd.soap.ota.server.RoomStaysType.RoomStay.class
})
public class RoomStayType {

    @XmlElement(name = "RoomTypes")
    protected RoomStayType.RoomTypes roomTypes;
    @XmlElement(name = "RatePlans")
    protected RoomStayType.RatePlans ratePlans;
    @XmlElement(name = "RoomRates")
    protected RoomStayType.RoomRates roomRates;
    @XmlElement(name = "GuestCounts")
    protected GuestCountType guestCounts;
    @XmlElement(name = "TimeSpan")
    protected DateTimeSpanType timeSpan;
    @XmlElement(name = "Guarantee")
    protected List<GuaranteeType> guarantee;
    @XmlElement(name = "DepositPayments")
    protected RequiredPaymentsType depositPayments;
    @XmlElement(name = "CancelPenalties")
    protected CancelPenaltiesType cancelPenalties;
    @XmlElement(name = "Discount")
    protected DiscountType discount;
    @XmlElement(name = "Total")
    protected TotalType total;
    @XmlElement(name = "BasicPropertyInfo")
    protected BasicPropertyInfoType basicPropertyInfo;
    @XmlElement(name = "TPA_Extensions")
    protected TPAExtensionsType tpaExtensions;
    @XmlAttribute(name = "MarketCode")
    protected String marketCode;
    @XmlAttribute(name = "SourceOfBusiness")
    protected String sourceOfBusiness;
    @XmlAttribute(name = "DiscountCode")
    protected String discountCode;
    @XmlAttribute(name = "RoomStayStatus")
    protected String roomStayStatus;
    @XmlAttribute(name = "WarningRPH")
    protected List<String> warningRPH;
    @XmlAttribute(name = "PromotionCode")
    protected String promotionCode;
    @XmlAttribute(name = "PromotionVendorCode")
    protected List<String> promotionVendorCode;

    /**
     * Gets the value of the roomTypes property.
     * 
     * @return
     *     possible object is
     *     {@link RoomStayType.RoomTypes }
     *     
     */
    public RoomStayType.RoomTypes getRoomTypes() {
        return roomTypes;
    }

    /**
     * Sets the value of the roomTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoomStayType.RoomTypes }
     *     
     */
    public void setRoomTypes(RoomStayType.RoomTypes value) {
        this.roomTypes = value;
    }

    /**
     * Gets the value of the ratePlans property.
     * 
     * @return
     *     possible object is
     *     {@link RoomStayType.RatePlans }
     *     
     */
    public RoomStayType.RatePlans getRatePlans() {
        return ratePlans;
    }

    /**
     * Sets the value of the ratePlans property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoomStayType.RatePlans }
     *     
     */
    public void setRatePlans(RoomStayType.RatePlans value) {
        this.ratePlans = value;
    }

    /**
     * Gets the value of the roomRates property.
     * 
     * @return
     *     possible object is
     *     {@link RoomStayType.RoomRates }
     *     
     */
    public RoomStayType.RoomRates getRoomRates() {
        return roomRates;
    }

    /**
     * Sets the value of the roomRates property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoomStayType.RoomRates }
     *     
     */
    public void setRoomRates(RoomStayType.RoomRates value) {
        this.roomRates = value;
    }

    /**
     * Gets the value of the guestCounts property.
     * 
     * @return
     *     possible object is
     *     {@link GuestCountType }
     *     
     */
    public GuestCountType getGuestCounts() {
        return guestCounts;
    }

    /**
     * Sets the value of the guestCounts property.
     * 
     * @param value
     *     allowed object is
     *     {@link GuestCountType }
     *     
     */
    public void setGuestCounts(GuestCountType value) {
        this.guestCounts = value;
    }

    /**
     * Gets the value of the timeSpan property.
     * 
     * @return
     *     possible object is
     *     {@link DateTimeSpanType }
     *     
     */
    public DateTimeSpanType getTimeSpan() {
        return timeSpan;
    }

    /**
     * Sets the value of the timeSpan property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTimeSpanType }
     *     
     */
    public void setTimeSpan(DateTimeSpanType value) {
        this.timeSpan = value;
    }

    /**
     * Gets the value of the guarantee property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the guarantee property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGuarantee().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GuaranteeType }
     * 
     * 
     */
    public List<GuaranteeType> getGuarantee() {
        if (guarantee == null) {
            guarantee = new ArrayList<GuaranteeType>();
        }
        return this.guarantee;
    }

    /**
     * Gets the value of the depositPayments property.
     * 
     * @return
     *     possible object is
     *     {@link RequiredPaymentsType }
     *     
     */
    public RequiredPaymentsType getDepositPayments() {
        return depositPayments;
    }

    /**
     * Sets the value of the depositPayments property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequiredPaymentsType }
     *     
     */
    public void setDepositPayments(RequiredPaymentsType value) {
        this.depositPayments = value;
    }

    /**
     * Gets the value of the cancelPenalties property.
     * 
     * @return
     *     possible object is
     *     {@link CancelPenaltiesType }
     *     
     */
    public CancelPenaltiesType getCancelPenalties() {
        return cancelPenalties;
    }

    /**
     * Sets the value of the cancelPenalties property.
     * 
     * @param value
     *     allowed object is
     *     {@link CancelPenaltiesType }
     *     
     */
    public void setCancelPenalties(CancelPenaltiesType value) {
        this.cancelPenalties = value;
    }

    /**
     * Gets the value of the discount property.
     * 
     * @return
     *     possible object is
     *     {@link DiscountType }
     *     
     */
    public DiscountType getDiscount() {
        return discount;
    }

    /**
     * Sets the value of the discount property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiscountType }
     *     
     */
    public void setDiscount(DiscountType value) {
        this.discount = value;
    }

    /**
     * Gets the value of the total property.
     * 
     * @return
     *     possible object is
     *     {@link TotalType }
     *     
     */
    public TotalType getTotal() {
        return total;
    }

    /**
     * Sets the value of the total property.
     * 
     * @param value
     *     allowed object is
     *     {@link TotalType }
     *     
     */
    public void setTotal(TotalType value) {
        this.total = value;
    }

    /**
     * Gets the value of the basicPropertyInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BasicPropertyInfoType }
     *     
     */
    public BasicPropertyInfoType getBasicPropertyInfo() {
        return basicPropertyInfo;
    }

    /**
     * Sets the value of the basicPropertyInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BasicPropertyInfoType }
     *     
     */
    public void setBasicPropertyInfo(BasicPropertyInfoType value) {
        this.basicPropertyInfo = value;
    }

    /**
     * Gets the value of the tpaExtensions property.
     * 
     * @return
     *     possible object is
     *     {@link TPAExtensionsType }
     *     
     */
    public TPAExtensionsType getTPAExtensions() {
        return tpaExtensions;
    }

    /**
     * Sets the value of the tpaExtensions property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPAExtensionsType }
     *     
     */
    public void setTPAExtensions(TPAExtensionsType value) {
        this.tpaExtensions = value;
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
     * Gets the value of the sourceOfBusiness property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceOfBusiness() {
        return sourceOfBusiness;
    }

    /**
     * Sets the value of the sourceOfBusiness property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceOfBusiness(String value) {
        this.sourceOfBusiness = value;
    }

    /**
     * Gets the value of the discountCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscountCode() {
        return discountCode;
    }

    /**
     * Sets the value of the discountCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscountCode(String value) {
        this.discountCode = value;
    }

    /**
     * Gets the value of the roomStayStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoomStayStatus() {
        return roomStayStatus;
    }

    /**
     * Sets the value of the roomStayStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoomStayStatus(String value) {
        this.roomStayStatus = value;
    }

    /**
     * Gets the value of the warningRPH property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the warningRPH property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWarningRPH().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getWarningRPH() {
        if (warningRPH == null) {
            warningRPH = new ArrayList<String>();
        }
        return this.warningRPH;
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="RatePlan" type="{http://www.opentravel.org/OTA/2003/05}RatePlanType" maxOccurs="unbounded"/>
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
        "ratePlan"
    })
    public static class RatePlans {

        @XmlElement(name = "RatePlan", required = true)
        protected List<RatePlanType> ratePlan;

        /**
         * Gets the value of the ratePlan property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ratePlan property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRatePlan().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RatePlanType }
         * 
         * 
         */
        public List<RatePlanType> getRatePlan() {
            if (ratePlan == null) {
                ratePlan = new ArrayList<RatePlanType>();
            }
            return this.ratePlan;
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
     *         &lt;element name="RoomRate" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://www.opentravel.org/OTA/2003/05}RoomRateType">
     *                 &lt;sequence>
     *                   &lt;element name="AdvanceBookingRestriction" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
     *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DOW_PatternGroup"/>
     *                           &lt;attribute name="MinAdvanceBookingOffset" type="{http://www.w3.org/2001/XMLSchema}duration" />
     *                           &lt;attribute name="MaxAdvanceBookingOffset" type="{http://www.w3.org/2001/XMLSchema}duration" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="Restrictions" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Restriction" maxOccurs="unbounded">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="DOW_Restrictions" type="{http://www.opentravel.org/OTA/2003/05}DOW_RestrictionsType"/>
     *                                     &lt;/sequence>
     *                                     &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}EffectiveExpireOptionalDateGroup"/>
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
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="MoreRatesExistInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "roomRate"
    })
    public static class RoomRates {

        @XmlElement(name = "RoomRate", required = true)
        protected List<RoomStayType.RoomRates.RoomRate> roomRate;
        @XmlAttribute(name = "MoreRatesExistInd")
        protected Boolean moreRatesExistInd;

        /**
         * Gets the value of the roomRate property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the roomRate property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRoomRate().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RoomStayType.RoomRates.RoomRate }
         * 
         * 
         */
        public List<RoomStayType.RoomRates.RoomRate> getRoomRate() {
            if (roomRate == null) {
                roomRate = new ArrayList<RoomStayType.RoomRates.RoomRate>();
            }
            return this.roomRate;
        }

        /**
         * Gets the value of the moreRatesExistInd property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isMoreRatesExistInd() {
            return moreRatesExistInd;
        }

        /**
         * Sets the value of the moreRatesExistInd property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setMoreRatesExistInd(Boolean value) {
            this.moreRatesExistInd = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://www.opentravel.org/OTA/2003/05}RoomRateType">
         *       &lt;sequence>
         *         &lt;element name="AdvanceBookingRestriction" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
         *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DOW_PatternGroup"/>
         *                 &lt;attribute name="MinAdvanceBookingOffset" type="{http://www.w3.org/2001/XMLSchema}duration" />
         *                 &lt;attribute name="MaxAdvanceBookingOffset" type="{http://www.w3.org/2001/XMLSchema}duration" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="Restrictions" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Restriction" maxOccurs="unbounded">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="DOW_Restrictions" type="{http://www.opentravel.org/OTA/2003/05}DOW_RestrictionsType"/>
         *                           &lt;/sequence>
         *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}EffectiveExpireOptionalDateGroup"/>
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
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "advanceBookingRestriction",
            "restrictions"
        })
        public static class RoomRate
            extends RoomRateType
        {

            @XmlElement(name = "AdvanceBookingRestriction")
            protected RoomStayType.RoomRates.RoomRate.AdvanceBookingRestriction advanceBookingRestriction;
            @XmlElement(name = "Restrictions")
            protected RoomStayType.RoomRates.RoomRate.Restrictions restrictions;

            /**
             * Gets the value of the advanceBookingRestriction property.
             * 
             * @return
             *     possible object is
             *     {@link RoomStayType.RoomRates.RoomRate.AdvanceBookingRestriction }
             *     
             */
            public RoomStayType.RoomRates.RoomRate.AdvanceBookingRestriction getAdvanceBookingRestriction() {
                return advanceBookingRestriction;
            }

            /**
             * Sets the value of the advanceBookingRestriction property.
             * 
             * @param value
             *     allowed object is
             *     {@link RoomStayType.RoomRates.RoomRate.AdvanceBookingRestriction }
             *     
             */
            public void setAdvanceBookingRestriction(RoomStayType.RoomRates.RoomRate.AdvanceBookingRestriction value) {
                this.advanceBookingRestriction = value;
            }

            /**
             * Gets the value of the restrictions property.
             * 
             * @return
             *     possible object is
             *     {@link RoomStayType.RoomRates.RoomRate.Restrictions }
             *     
             */
            public RoomStayType.RoomRates.RoomRate.Restrictions getRestrictions() {
                return restrictions;
            }

            /**
             * Sets the value of the restrictions property.
             * 
             * @param value
             *     allowed object is
             *     {@link RoomStayType.RoomRates.RoomRate.Restrictions }
             *     
             */
            public void setRestrictions(RoomStayType.RoomRates.RoomRate.Restrictions value) {
                this.restrictions = value;
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
             *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DOW_PatternGroup"/>
             *       &lt;attribute name="MinAdvanceBookingOffset" type="{http://www.w3.org/2001/XMLSchema}duration" />
             *       &lt;attribute name="MaxAdvanceBookingOffset" type="{http://www.w3.org/2001/XMLSchema}duration" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class AdvanceBookingRestriction {

                @XmlAttribute(name = "MinAdvanceBookingOffset")
                protected Duration minAdvanceBookingOffset;
                @XmlAttribute(name = "MaxAdvanceBookingOffset")
                protected Duration maxAdvanceBookingOffset;
                @XmlAttribute(name = "Start")
                protected String start;
                @XmlAttribute(name = "Duration")
                protected String duration;
                @XmlAttribute(name = "End")
                protected String end;
                @XmlAttribute(name = "Mon")
                protected Boolean mon;
                @XmlAttribute(name = "Tue")
                protected Boolean tue;
                @XmlAttribute(name = "Weds")
                protected Boolean weds;
                @XmlAttribute(name = "Thur")
                protected Boolean thur;
                @XmlAttribute(name = "Fri")
                protected Boolean fri;
                @XmlAttribute(name = "Sat")
                protected Boolean sat;
                @XmlAttribute(name = "Sun")
                protected Boolean sun;

                /**
                 * Gets the value of the minAdvanceBookingOffset property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Duration }
                 *     
                 */
                public Duration getMinAdvanceBookingOffset() {
                    return minAdvanceBookingOffset;
                }

                /**
                 * Sets the value of the minAdvanceBookingOffset property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Duration }
                 *     
                 */
                public void setMinAdvanceBookingOffset(Duration value) {
                    this.minAdvanceBookingOffset = value;
                }

                /**
                 * Gets the value of the maxAdvanceBookingOffset property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Duration }
                 *     
                 */
                public Duration getMaxAdvanceBookingOffset() {
                    return maxAdvanceBookingOffset;
                }

                /**
                 * Sets the value of the maxAdvanceBookingOffset property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Duration }
                 *     
                 */
                public void setMaxAdvanceBookingOffset(Duration value) {
                    this.maxAdvanceBookingOffset = value;
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
                 * Gets the value of the mon property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Boolean }
                 *     
                 */
                public Boolean isMon() {
                    return mon;
                }

                /**
                 * Sets the value of the mon property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Boolean }
                 *     
                 */
                public void setMon(Boolean value) {
                    this.mon = value;
                }

                /**
                 * Gets the value of the tue property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Boolean }
                 *     
                 */
                public Boolean isTue() {
                    return tue;
                }

                /**
                 * Sets the value of the tue property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Boolean }
                 *     
                 */
                public void setTue(Boolean value) {
                    this.tue = value;
                }

                /**
                 * Gets the value of the weds property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Boolean }
                 *     
                 */
                public Boolean isWeds() {
                    return weds;
                }

                /**
                 * Sets the value of the weds property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Boolean }
                 *     
                 */
                public void setWeds(Boolean value) {
                    this.weds = value;
                }

                /**
                 * Gets the value of the thur property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Boolean }
                 *     
                 */
                public Boolean isThur() {
                    return thur;
                }

                /**
                 * Sets the value of the thur property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Boolean }
                 *     
                 */
                public void setThur(Boolean value) {
                    this.thur = value;
                }

                /**
                 * Gets the value of the fri property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Boolean }
                 *     
                 */
                public Boolean isFri() {
                    return fri;
                }

                /**
                 * Sets the value of the fri property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Boolean }
                 *     
                 */
                public void setFri(Boolean value) {
                    this.fri = value;
                }

                /**
                 * Gets the value of the sat property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Boolean }
                 *     
                 */
                public Boolean isSat() {
                    return sat;
                }

                /**
                 * Sets the value of the sat property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Boolean }
                 *     
                 */
                public void setSat(Boolean value) {
                    this.sat = value;
                }

                /**
                 * Gets the value of the sun property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Boolean }
                 *     
                 */
                public Boolean isSun() {
                    return sun;
                }

                /**
                 * Sets the value of the sun property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Boolean }
                 *     
                 */
                public void setSun(Boolean value) {
                    this.sun = value;
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
             *         &lt;element name="Restriction" maxOccurs="unbounded">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="DOW_Restrictions" type="{http://www.opentravel.org/OTA/2003/05}DOW_RestrictionsType"/>
             *                 &lt;/sequence>
             *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}EffectiveExpireOptionalDateGroup"/>
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
                "restriction"
            })
            public static class Restrictions {

                @XmlElement(name = "Restriction", required = true)
                protected List<RoomStayType.RoomRates.RoomRate.Restrictions.Restriction> restriction;

                /**
                 * Gets the value of the restriction property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the restriction property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getRestriction().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link RoomStayType.RoomRates.RoomRate.Restrictions.Restriction }
                 * 
                 * 
                 */
                public List<RoomStayType.RoomRates.RoomRate.Restrictions.Restriction> getRestriction() {
                    if (restriction == null) {
                        restriction = new ArrayList<RoomStayType.RoomRates.RoomRate.Restrictions.Restriction>();
                    }
                    return this.restriction;
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
                 *         &lt;element name="DOW_Restrictions" type="{http://www.opentravel.org/OTA/2003/05}DOW_RestrictionsType"/>
                 *       &lt;/sequence>
                 *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}EffectiveExpireOptionalDateGroup"/>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "dowRestrictions"
                })
                public static class Restriction {

                    @XmlElement(name = "DOW_Restrictions", required = true)
                    protected DOWRestrictionsType dowRestrictions;
                    @XmlAttribute(name = "EffectiveDate")
                    @XmlSchemaType(name = "date")
                    protected XMLGregorianCalendar effectiveDate;
                    @XmlAttribute(name = "ExpireDate")
                    @XmlSchemaType(name = "date")
                    protected XMLGregorianCalendar expireDate;
                    @XmlAttribute(name = "ExpireDateExclusiveIndicator")
                    protected Boolean expireDateExclusiveIndicator;

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
                     * Gets the value of the effectiveDate property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link XMLGregorianCalendar }
                     *     
                     */
                    public XMLGregorianCalendar getEffectiveDate() {
                        return effectiveDate;
                    }

                    /**
                     * Sets the value of the effectiveDate property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link XMLGregorianCalendar }
                     *     
                     */
                    public void setEffectiveDate(XMLGregorianCalendar value) {
                        this.effectiveDate = value;
                    }

                    /**
                     * Gets the value of the expireDate property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link XMLGregorianCalendar }
                     *     
                     */
                    public XMLGregorianCalendar getExpireDate() {
                        return expireDate;
                    }

                    /**
                     * Sets the value of the expireDate property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link XMLGregorianCalendar }
                     *     
                     */
                    public void setExpireDate(XMLGregorianCalendar value) {
                        this.expireDate = value;
                    }

                    /**
                     * Gets the value of the expireDateExclusiveIndicator property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Boolean }
                     *     
                     */
                    public Boolean isExpireDateExclusiveIndicator() {
                        return expireDateExclusiveIndicator;
                    }

                    /**
                     * Sets the value of the expireDateExclusiveIndicator property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Boolean }
                     *     
                     */
                    public void setExpireDateExclusiveIndicator(Boolean value) {
                        this.expireDateExclusiveIndicator = value;
                    }

                }

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
     *         &lt;element name="RoomType" type="{http://www.opentravel.org/OTA/2003/05}RoomTypeType" maxOccurs="unbounded"/>
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
        "roomType"
    })
    public static class RoomTypes {

        @XmlElement(name = "RoomType", required = true)
        protected List<RoomTypeType> roomType;

        /**
         * Gets the value of the roomType property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the roomType property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRoomType().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RoomTypeType }
         * 
         * 
         */
        public List<RoomTypeType> getRoomType() {
            if (roomType == null) {
                roomType = new ArrayList<RoomTypeType>();
            }
            return this.roomType;
        }

    }

}