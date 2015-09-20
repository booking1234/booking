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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;sequence>
 *           &lt;element name="Success" type="{http://www.opentravel.org/OTA/2003/05}SuccessType"/>
 *           &lt;element name="Warnings" type="{http://www.opentravel.org/OTA/2003/05}WarningsType" minOccurs="0"/>
 *           &lt;element name="ReservationsList" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;choice>
 *                     &lt;element name="AirReservation" maxOccurs="unbounded" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="FlightSegment" type="{http://www.opentravel.org/OTA/2003/05}FlightSegmentType" minOccurs="0"/>
 *                               &lt;element name="TravelerName" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType" maxOccurs="5" minOccurs="0"/>
 *                               &lt;element ref="{http://www.opentravel.org/OTA/2003/05}TPA_Extensions" minOccurs="0"/>
 *                             &lt;/sequence>
 *                             &lt;attribute name="BookingReferenceID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                             &lt;attribute name="DateBooked" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *                             &lt;attribute name="ItineraryName" type="{http://www.opentravel.org/OTA/2003/05}StringLength0to64" />
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="HotelReservation" type="{http://www.opentravel.org/OTA/2003/05}HotelReservationType" maxOccurs="unbounded" minOccurs="0"/>
 *                     &lt;element name="PackageReservation" maxOccurs="unbounded" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="UniqueID" type="{http://www.opentravel.org/OTA/2003/05}UniqueID_Type" minOccurs="0"/>
 *                               &lt;element name="Name" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType" minOccurs="0"/>
 *                               &lt;element name="ArrivalLocation" type="{http://www.opentravel.org/OTA/2003/05}LocationType" minOccurs="0"/>
 *                               &lt;element name="DepartureLocation" type="{http://www.opentravel.org/OTA/2003/05}LocationType" minOccurs="0"/>
 *                             &lt;/sequence>
 *                             &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
 *                             &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}QuantityGroup"/>
 *                             &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}InventoryStatusGroup"/>
 *                             &lt;attribute name="TravelCode" type="{http://www.opentravel.org/OTA/2003/05}PkgTravelCode" />
 *                             &lt;attribute name="TourCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to8" />
 *                             &lt;attribute name="PackageID" type="{http://www.opentravel.org/OTA/2003/05}PackageID_RefType" />
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="GolfReservation" maxOccurs="unbounded" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="Membership" minOccurs="0">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}CustomerLoyaltyGroup"/>
 *                                     &lt;/restriction>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                               &lt;element name="Name" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType"/>
 *                             &lt;/sequence>
 *                             &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}ID_Group"/>
 *                             &lt;attribute name="RoundID" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *                             &lt;attribute name="PlayDateTime" use="required" type="{http://www.opentravel.org/OTA/2003/05}DateOrDateTimeType" />
 *                             &lt;attribute name="PackageID" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to8" />
 *                             &lt;attribute name="RequestorResID" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to8" />
 *                             &lt;attribute name="ResponderResConfID" use="required" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to8" />
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="VehicleReservation" maxOccurs="unbounded" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="VehResSummary" type="{http://www.opentravel.org/OTA/2003/05}VehicleReservationSummaryType" maxOccurs="99"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="GlobalReservation" maxOccurs="unbounded" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="UniqueID" type="{http://www.opentravel.org/OTA/2003/05}UniqueID_Type"/>
 *                               &lt;element name="TravelerName" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType" minOccurs="0"/>
 *                             &lt;/sequence>
 *                             &lt;attribute name="ItineraryName" type="{http://www.opentravel.org/OTA/2003/05}StringLength0to64" />
 *                             &lt;attribute name="Start" type="{http://www.opentravel.org/OTA/2003/05}DateOrDateTimeType" />
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="CruiseReservation" type="{http://www.opentravel.org/OTA/2003/05}CruiseReservationType" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;/choice>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element name="Errors" type="{http://www.opentravel.org/OTA/2003/05}ErrorsType"/>
 *         &lt;/sequence>
 *       &lt;/choice>
 *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}OTA_PayloadStdAttributes"/>
 *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}ResponseGroup"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "success",
    "warnings",
    "reservationsList",
    "errors"
})
@XmlRootElement(name = "OTA_ResRetrieveRS")
public class OTAResRetrieveRS {

    @XmlElement(name = "Success")
    protected SuccessType success;
    @XmlElement(name = "Warnings")
    protected WarningsType warnings;
    @XmlElement(name = "ReservationsList")
    protected OTAResRetrieveRS.ReservationsList reservationsList;
    @XmlElement(name = "Errors")
    protected ErrorsType errors;
    @XmlAttribute(name = "EchoToken")
    protected String echoToken;
    @XmlAttribute(name = "TimeStamp")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timeStamp;
    @XmlAttribute(name = "Target")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String target;
    @XmlAttribute(name = "Version", required = true)
    protected BigDecimal version;
    @XmlAttribute(name = "TransactionIdentifier")
    protected String transactionIdentifier;
    @XmlAttribute(name = "SequenceNmbr")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger sequenceNmbr;
    @XmlAttribute(name = "TransactionStatusCode")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String transactionStatusCode;
    @XmlAttribute(name = "RetransmissionIndicator")
    protected Boolean retransmissionIndicator;
    @XmlAttribute(name = "AltLangID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "language")
    protected String altLangID;
    @XmlAttribute(name = "PrimaryLangID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "language")
    protected String primaryLangID;
    @XmlAttribute(name = "MoreIndicator")
    protected Boolean moreIndicator;
    @XmlAttribute(name = "MoreDataEchoToken")
    protected String moreDataEchoToken;
    @XmlAttribute(name = "MaxResponses")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger maxResponses;

    /**
     * Gets the value of the success property.
     * 
     * @return
     *     possible object is
     *     {@link SuccessType }
     *     
     */
    public SuccessType getSuccess() {
        return success;
    }

    /**
     * Sets the value of the success property.
     * 
     * @param value
     *     allowed object is
     *     {@link SuccessType }
     *     
     */
    public void setSuccess(SuccessType value) {
        this.success = value;
    }

    /**
     * Gets the value of the warnings property.
     * 
     * @return
     *     possible object is
     *     {@link WarningsType }
     *     
     */
    public WarningsType getWarnings() {
        return warnings;
    }

    /**
     * Sets the value of the warnings property.
     * 
     * @param value
     *     allowed object is
     *     {@link WarningsType }
     *     
     */
    public void setWarnings(WarningsType value) {
        this.warnings = value;
    }

    /**
     * Gets the value of the reservationsList property.
     * 
     * @return
     *     possible object is
     *     {@link OTAResRetrieveRS.ReservationsList }
     *     
     */
    public OTAResRetrieveRS.ReservationsList getReservationsList() {
        return reservationsList;
    }

    /**
     * Sets the value of the reservationsList property.
     * 
     * @param value
     *     allowed object is
     *     {@link OTAResRetrieveRS.ReservationsList }
     *     
     */
    public void setReservationsList(OTAResRetrieveRS.ReservationsList value) {
        this.reservationsList = value;
    }

    /**
     * Gets the value of the errors property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorsType }
     *     
     */
    public ErrorsType getErrors() {
        return errors;
    }

    /**
     * Sets the value of the errors property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorsType }
     *     
     */
    public void setErrors(ErrorsType value) {
        this.errors = value;
    }

    /**
     * Gets the value of the echoToken property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEchoToken() {
        return echoToken;
    }

    /**
     * Sets the value of the echoToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEchoToken(String value) {
        this.echoToken = value;
    }

    /**
     * Gets the value of the timeStamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimeStamp() {
        return timeStamp;
    }

    /**
     * Sets the value of the timeStamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimeStamp(XMLGregorianCalendar value) {
        this.timeStamp = value;
    }

    /**
     * Gets the value of the target property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTarget() {
        if (target == null) {
            return "Production";
        } else {
            return target;
        }
    }

    /**
     * Sets the value of the target property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTarget(String value) {
        this.target = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVersion(BigDecimal value) {
        this.version = value;
    }

    /**
     * Gets the value of the transactionIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionIdentifier() {
        return transactionIdentifier;
    }

    /**
     * Sets the value of the transactionIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionIdentifier(String value) {
        this.transactionIdentifier = value;
    }

    /**
     * Gets the value of the sequenceNmbr property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSequenceNmbr() {
        return sequenceNmbr;
    }

    /**
     * Sets the value of the sequenceNmbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSequenceNmbr(BigInteger value) {
        this.sequenceNmbr = value;
    }

    /**
     * Gets the value of the transactionStatusCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionStatusCode() {
        return transactionStatusCode;
    }

    /**
     * Sets the value of the transactionStatusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionStatusCode(String value) {
        this.transactionStatusCode = value;
    }

    /**
     * Gets the value of the retransmissionIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRetransmissionIndicator() {
        return retransmissionIndicator;
    }

    /**
     * Sets the value of the retransmissionIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRetransmissionIndicator(Boolean value) {
        this.retransmissionIndicator = value;
    }

    /**
     * Gets the value of the altLangID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAltLangID() {
        return altLangID;
    }

    /**
     * Sets the value of the altLangID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAltLangID(String value) {
        this.altLangID = value;
    }

    /**
     * Gets the value of the primaryLangID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryLangID() {
        return primaryLangID;
    }

    /**
     * Sets the value of the primaryLangID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryLangID(String value) {
        this.primaryLangID = value;
    }

    /**
     * Gets the value of the moreIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMoreIndicator() {
        return moreIndicator;
    }

    /**
     * Sets the value of the moreIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMoreIndicator(Boolean value) {
        this.moreIndicator = value;
    }

    /**
     * Gets the value of the moreDataEchoToken property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMoreDataEchoToken() {
        return moreDataEchoToken;
    }

    /**
     * Sets the value of the moreDataEchoToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMoreDataEchoToken(String value) {
        this.moreDataEchoToken = value;
    }

    /**
     * Gets the value of the maxResponses property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxResponses() {
        return maxResponses;
    }

    /**
     * Sets the value of the maxResponses property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxResponses(BigInteger value) {
        this.maxResponses = value;
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
     *       &lt;choice>
     *         &lt;element name="AirReservation" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="FlightSegment" type="{http://www.opentravel.org/OTA/2003/05}FlightSegmentType" minOccurs="0"/>
     *                   &lt;element name="TravelerName" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType" maxOccurs="5" minOccurs="0"/>
     *                   &lt;element ref="{http://www.opentravel.org/OTA/2003/05}TPA_Extensions" minOccurs="0"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="BookingReferenceID" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="DateBooked" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
     *                 &lt;attribute name="ItineraryName" type="{http://www.opentravel.org/OTA/2003/05}StringLength0to64" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="HotelReservation" type="{http://www.opentravel.org/OTA/2003/05}HotelReservationType" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="PackageReservation" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="UniqueID" type="{http://www.opentravel.org/OTA/2003/05}UniqueID_Type" minOccurs="0"/>
     *                   &lt;element name="Name" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType" minOccurs="0"/>
     *                   &lt;element name="ArrivalLocation" type="{http://www.opentravel.org/OTA/2003/05}LocationType" minOccurs="0"/>
     *                   &lt;element name="DepartureLocation" type="{http://www.opentravel.org/OTA/2003/05}LocationType" minOccurs="0"/>
     *                 &lt;/sequence>
     *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
     *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}QuantityGroup"/>
     *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}InventoryStatusGroup"/>
     *                 &lt;attribute name="TravelCode" type="{http://www.opentravel.org/OTA/2003/05}PkgTravelCode" />
     *                 &lt;attribute name="TourCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to8" />
     *                 &lt;attribute name="PackageID" type="{http://www.opentravel.org/OTA/2003/05}PackageID_RefType" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="GolfReservation" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Membership" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}CustomerLoyaltyGroup"/>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="Name" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType"/>
     *                 &lt;/sequence>
     *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}ID_Group"/>
     *                 &lt;attribute name="RoundID" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
     *                 &lt;attribute name="PlayDateTime" use="required" type="{http://www.opentravel.org/OTA/2003/05}DateOrDateTimeType" />
     *                 &lt;attribute name="PackageID" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to8" />
     *                 &lt;attribute name="RequestorResID" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to8" />
     *                 &lt;attribute name="ResponderResConfID" use="required" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to8" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="VehicleReservation" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="VehResSummary" type="{http://www.opentravel.org/OTA/2003/05}VehicleReservationSummaryType" maxOccurs="99"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="GlobalReservation" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="UniqueID" type="{http://www.opentravel.org/OTA/2003/05}UniqueID_Type"/>
     *                   &lt;element name="TravelerName" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType" minOccurs="0"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="ItineraryName" type="{http://www.opentravel.org/OTA/2003/05}StringLength0to64" />
     *                 &lt;attribute name="Start" type="{http://www.opentravel.org/OTA/2003/05}DateOrDateTimeType" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="CruiseReservation" type="{http://www.opentravel.org/OTA/2003/05}CruiseReservationType" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/choice>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "hotelReservation",
        "globalReservation"
    })
    public static class ReservationsList {

//        @XmlElement(name = "AirReservation")
//        protected List<OTAResRetrieveRS.ReservationsList.AirReservation> airReservation;
        @XmlElement(name = "HotelReservation")
        protected List<HotelReservationType> hotelReservation;
//        @XmlElement(name = "PackageReservation")
//        protected List<OTAResRetrieveRS.ReservationsList.PackageReservation> packageReservation;
//        @XmlElement(name = "GolfReservation")
//        protected List<OTAResRetrieveRS.ReservationsList.GolfReservation> golfReservation;
//        @XmlElement(name = "VehicleReservation")
//        protected List<OTAResRetrieveRS.ReservationsList.VehicleReservation> vehicleReservation;
        @XmlElement(name = "GlobalReservation")
        protected List<OTAResRetrieveRS.ReservationsList.GlobalReservation> globalReservation;
//        @XmlElement(name = "CruiseReservation")
//        protected List<CruiseReservationType> cruiseReservation;

        /**
         * Gets the value of the airReservation property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the airReservation property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAirReservation().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OTAResRetrieveRS.ReservationsList.AirReservation }
         * 
         * 
         */
//        public List<OTAResRetrieveRS.ReservationsList.AirReservation> getAirReservation() {
//            if (airReservation == null) {
//                airReservation = new ArrayList<OTAResRetrieveRS.ReservationsList.AirReservation>();
//            }
//            return this.airReservation;
//        }

        /**
         * Gets the value of the hotelReservation property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the hotelReservation property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHotelReservation().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link HotelReservationType }
         * 
         * 
         */
        public List<HotelReservationType> getHotelReservation() {
            if (hotelReservation == null) {
                hotelReservation = new ArrayList<HotelReservationType>();
            }
            return this.hotelReservation;
        }

        /**
         * Gets the value of the packageReservation property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the packageReservation property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPackageReservation().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OTAResRetrieveRS.ReservationsList.PackageReservation }
         * 
         * 
         */
//        public List<OTAResRetrieveRS.ReservationsList.PackageReservation> getPackageReservation() {
//            if (packageReservation == null) {
//                packageReservation = new ArrayList<OTAResRetrieveRS.ReservationsList.PackageReservation>();
//            }
//            return this.packageReservation;
//        }

        /**
         * Gets the value of the golfReservation property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the golfReservation property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getGolfReservation().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OTAResRetrieveRS.ReservationsList.GolfReservation }
         * 
         * 
         */
//        public List<OTAResRetrieveRS.ReservationsList.GolfReservation> getGolfReservation() {
//            if (golfReservation == null) {
//                golfReservation = new ArrayList<OTAResRetrieveRS.ReservationsList.GolfReservation>();
//            }
//            return this.golfReservation;
//        }

        /**
         * Gets the value of the vehicleReservation property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the vehicleReservation property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getVehicleReservation().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OTAResRetrieveRS.ReservationsList.VehicleReservation }
         * 
         * 
         */
//        public List<OTAResRetrieveRS.ReservationsList.VehicleReservation> getVehicleReservation() {
//            if (vehicleReservation == null) {
//                vehicleReservation = new ArrayList<OTAResRetrieveRS.ReservationsList.VehicleReservation>();
//            }
//            return this.vehicleReservation;
//        }

        /**
         * Gets the value of the globalReservation property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the globalReservation property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getGlobalReservation().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OTAResRetrieveRS.ReservationsList.GlobalReservation }
         * 
         * 
         */
        public List<OTAResRetrieveRS.ReservationsList.GlobalReservation> getGlobalReservation() {
            if (globalReservation == null) {
                globalReservation = new ArrayList<OTAResRetrieveRS.ReservationsList.GlobalReservation>();
            }
            return this.globalReservation;
        }

        /**
         * Gets the value of the cruiseReservation property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the cruiseReservation property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCruiseReservation().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CruiseReservationType }
         * 
         * 
         */
//        public List<CruiseReservationType> getCruiseReservation() {
//            if (cruiseReservation == null) {
//                cruiseReservation = new ArrayList<CruiseReservationType>();
//            }
//            return this.cruiseReservation;
//        }


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
         *         &lt;element name="FlightSegment" type="{http://www.opentravel.org/OTA/2003/05}FlightSegmentType" minOccurs="0"/>
         *         &lt;element name="TravelerName" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType" maxOccurs="5" minOccurs="0"/>
         *         &lt;element ref="{http://www.opentravel.org/OTA/2003/05}TPA_Extensions" minOccurs="0"/>
         *       &lt;/sequence>
         *       &lt;attribute name="BookingReferenceID" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="DateBooked" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
         *       &lt;attribute name="ItineraryName" type="{http://www.opentravel.org/OTA/2003/05}StringLength0to64" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
//        @XmlAccessorType(XmlAccessType.FIELD)
//        @XmlType(name = "", propOrder = {
//            "flightSegment",
//            "travelerName",
//            "tpaExtensions"
//        })
//        public static class AirReservation {
//
////            @XmlElement(name = "FlightSegment")
////            protected FlightSegmentType flightSegment;
//            @XmlElement(name = "TravelerName")
//            protected List<PersonNameType> travelerName;
//            @XmlElement(name = "TPA_Extensions")
//            protected TPAExtensionsType tpaExtensions;
//            @XmlAttribute(name = "BookingReferenceID")
//            protected String bookingReferenceID;
//            @XmlAttribute(name = "DateBooked")
//            @XmlSchemaType(name = "dateTime")
//            protected XMLGregorianCalendar dateBooked;
//            @XmlAttribute(name = "ItineraryName")
//            protected String itineraryName;
//
//            /**
//             * Gets the value of the flightSegment property.
//             * 
//             * @return
//             *     possible object is
//             *     {@link FlightSegmentType }
//             *     
//             */
////            public FlightSegmentType getFlightSegment() {
////                return flightSegment;
////            }
//
//            /**
//             * Sets the value of the flightSegment property.
//             * 
//             * @param value
//             *     allowed object is
//             *     {@link FlightSegmentType }
//             *     
//             */
////            public void setFlightSegment(FlightSegmentType value) {
////                this.flightSegment = value;
////            }
//
//            /**
//             * Gets the value of the travelerName property.
//             * 
//             * <p>
//             * This accessor method returns a reference to the live list,
//             * not a snapshot. Therefore any modification you make to the
//             * returned list will be present inside the JAXB object.
//             * This is why there is not a <CODE>set</CODE> method for the travelerName property.
//             * 
//             * <p>
//             * For example, to add a new item, do as follows:
//             * <pre>
//             *    getTravelerName().add(newItem);
//             * </pre>
//             * 
//             * 
//             * <p>
//             * Objects of the following type(s) are allowed in the list
//             * {@link PersonNameType }
//             * 
//             * 
//             */
//            public List<PersonNameType> getTravelerName() {
//                if (travelerName == null) {
//                    travelerName = new ArrayList<PersonNameType>();
//                }
//                return this.travelerName;
//            }
//
//            /**
//             * Gets the value of the tpaExtensions property.
//             * 
//             * @return
//             *     possible object is
//             *     {@link TPAExtensionsType }
//             *     
//             */
//            public TPAExtensionsType getTPAExtensions() {
//                return tpaExtensions;
//            }
//
//            /**
//             * Sets the value of the tpaExtensions property.
//             * 
//             * @param value
//             *     allowed object is
//             *     {@link TPAExtensionsType }
//             *     
//             */
//            public void setTPAExtensions(TPAExtensionsType value) {
//                this.tpaExtensions = value;
//            }
//
//            /**
//             * Gets the value of the bookingReferenceID property.
//             * 
//             * @return
//             *     possible object is
//             *     {@link String }
//             *     
//             */
//            public String getBookingReferenceID() {
//                return bookingReferenceID;
//            }
//
//            /**
//             * Sets the value of the bookingReferenceID property.
//             * 
//             * @param value
//             *     allowed object is
//             *     {@link String }
//             *     
//             */
//            public void setBookingReferenceID(String value) {
//                this.bookingReferenceID = value;
//            }
//
//            /**
//             * Gets the value of the dateBooked property.
//             * 
//             * @return
//             *     possible object is
//             *     {@link XMLGregorianCalendar }
//             *     
//             */
//            public XMLGregorianCalendar getDateBooked() {
//                return dateBooked;
//            }
//
//            /**
//             * Sets the value of the dateBooked property.
//             * 
//             * @param value
//             *     allowed object is
//             *     {@link XMLGregorianCalendar }
//             *     
//             */
//            public void setDateBooked(XMLGregorianCalendar value) {
//                this.dateBooked = value;
//            }
//
//            /**
//             * Gets the value of the itineraryName property.
//             * 
//             * @return
//             *     possible object is
//             *     {@link String }
//             *     
//             */
//            public String getItineraryName() {
//                return itineraryName;
//            }
//
//            /**
//             * Sets the value of the itineraryName property.
//             * 
//             * @param value
//             *     allowed object is
//             *     {@link String }
//             *     
//             */
//            public void setItineraryName(String value) {
//                this.itineraryName = value;
//            }
//
//        }


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
         *         &lt;element name="UniqueID" type="{http://www.opentravel.org/OTA/2003/05}UniqueID_Type"/>
         *         &lt;element name="TravelerName" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType" minOccurs="0"/>
         *       &lt;/sequence>
         *       &lt;attribute name="ItineraryName" type="{http://www.opentravel.org/OTA/2003/05}StringLength0to64" />
         *       &lt;attribute name="Start" type="{http://www.opentravel.org/OTA/2003/05}DateOrDateTimeType" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "uniqueID",
            "travelerName"
        })
        public static class GlobalReservation {

            @XmlElement(name = "UniqueID", required = true)
            protected UniqueIDType uniqueID;
            @XmlElement(name = "TravelerName")
            protected PersonNameType travelerName;
            @XmlAttribute(name = "ItineraryName")
            protected String itineraryName;
            @XmlAttribute(name = "Start")
            protected String start;

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
             * Gets the value of the travelerName property.
             * 
             * @return
             *     possible object is
             *     {@link PersonNameType }
             *     
             */
            public PersonNameType getTravelerName() {
                return travelerName;
            }

            /**
             * Sets the value of the travelerName property.
             * 
             * @param value
             *     allowed object is
             *     {@link PersonNameType }
             *     
             */
            public void setTravelerName(PersonNameType value) {
                this.travelerName = value;
            }

            /**
             * Gets the value of the itineraryName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getItineraryName() {
                return itineraryName;
            }

            /**
             * Sets the value of the itineraryName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setItineraryName(String value) {
                this.itineraryName = value;
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
         *         &lt;element name="Membership" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}CustomerLoyaltyGroup"/>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="Name" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType"/>
         *       &lt;/sequence>
         *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}ID_Group"/>
         *       &lt;attribute name="RoundID" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
         *       &lt;attribute name="PlayDateTime" use="required" type="{http://www.opentravel.org/OTA/2003/05}DateOrDateTimeType" />
         *       &lt;attribute name="PackageID" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to8" />
         *       &lt;attribute name="RequestorResID" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to8" />
         *       &lt;attribute name="ResponderResConfID" use="required" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to8" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "membership",
            "name"
        })
        public static class GolfReservation {

            @XmlElement(name = "Membership")
            protected OTAResRetrieveRS.ReservationsList.GolfReservation.Membership membership;
            @XmlElement(name = "Name", required = true)
            protected PersonNameType name;
            @XmlAttribute(name = "RoundID", required = true)
            @XmlSchemaType(name = "positiveInteger")
            protected BigInteger roundID;
            @XmlAttribute(name = "PlayDateTime", required = true)
            protected String playDateTime;
            @XmlAttribute(name = "PackageID")
            protected String packageID;
            @XmlAttribute(name = "RequestorResID")
            protected String requestorResID;
            @XmlAttribute(name = "ResponderResConfID", required = true)
            protected String responderResConfID;
            @XmlAttribute(name = "ID", required = true)
            protected String id;

            /**
             * Gets the value of the membership property.
             * 
             * @return
             *     possible object is
             *     {@link OTAResRetrieveRS.ReservationsList.GolfReservation.Membership }
             *     
             */
            public OTAResRetrieveRS.ReservationsList.GolfReservation.Membership getMembership() {
                return membership;
            }

            /**
             * Sets the value of the membership property.
             * 
             * @param value
             *     allowed object is
             *     {@link OTAResRetrieveRS.ReservationsList.GolfReservation.Membership }
             *     
             */
            public void setMembership(OTAResRetrieveRS.ReservationsList.GolfReservation.Membership value) {
                this.membership = value;
            }

            /**
             * Gets the value of the name property.
             * 
             * @return
             *     possible object is
             *     {@link PersonNameType }
             *     
             */
            public PersonNameType getName() {
                return name;
            }

            /**
             * Sets the value of the name property.
             * 
             * @param value
             *     allowed object is
             *     {@link PersonNameType }
             *     
             */
            public void setName(PersonNameType value) {
                this.name = value;
            }

            /**
             * Gets the value of the roundID property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getRoundID() {
                return roundID;
            }

            /**
             * Sets the value of the roundID property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setRoundID(BigInteger value) {
                this.roundID = value;
            }

            /**
             * Gets the value of the playDateTime property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPlayDateTime() {
                return playDateTime;
            }

            /**
             * Sets the value of the playDateTime property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPlayDateTime(String value) {
                this.playDateTime = value;
            }

            /**
             * Gets the value of the packageID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPackageID() {
                return packageID;
            }

            /**
             * Sets the value of the packageID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPackageID(String value) {
                this.packageID = value;
            }

            /**
             * Gets the value of the requestorResID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRequestorResID() {
                return requestorResID;
            }

            /**
             * Sets the value of the requestorResID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRequestorResID(String value) {
                this.requestorResID = value;
            }

            /**
             * Gets the value of the responderResConfID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getResponderResConfID() {
                return responderResConfID;
            }

            /**
             * Sets the value of the responderResConfID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setResponderResConfID(String value) {
                this.responderResConfID = value;
            }

            /**
             * Gets the value of the id property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getID() {
                return id;
            }

            /**
             * Sets the value of the id property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setID(String value) {
                this.id = value;
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
             *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}CustomerLoyaltyGroup"/>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Membership {

                @XmlAttribute(name = "ProgramID")
                protected String programID;
                @XmlAttribute(name = "MembershipID")
                protected String membershipID;
                @XmlAttribute(name = "TravelSector")
                protected String travelSector;
                @XmlAttribute(name = "RPH")
                protected String rph;
                @XmlAttribute(name = "VendorCode")
                protected List<String> vendorCode;
                @XmlAttribute(name = "PrimaryLoyaltyIndicator")
                protected Boolean primaryLoyaltyIndicator;
                @XmlAttribute(name = "AllianceLoyaltyLevelName")
                protected String allianceLoyaltyLevelName;
                @XmlAttribute(name = "CustomerType")
                protected String customerType;
                @XmlAttribute(name = "CustomerValue")
                protected String customerValue;
                @XmlAttribute(name = "SingleVendorInd")
                @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                protected String singleVendorInd;
                @XmlAttribute(name = "ShareSynchInd")
                @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                protected String shareSynchInd;
                @XmlAttribute(name = "ShareMarketInd")
                @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                protected String shareMarketInd;
                @XmlAttribute(name = "EffectiveDate")
                @XmlSchemaType(name = "date")
                protected XMLGregorianCalendar effectiveDate;
                @XmlAttribute(name = "ExpireDate")
                @XmlSchemaType(name = "date")
                protected XMLGregorianCalendar expireDate;
                @XmlAttribute(name = "ExpireDateExclusiveIndicator")
                protected Boolean expireDateExclusiveIndicator;
                @XmlAttribute(name = "LoyalLevel")
                protected String loyalLevel;
                @XmlAttribute(name = "LoyalLevelCode")
                protected Integer loyalLevelCode;
                @XmlAttribute(name = "SignupDate")
                @XmlSchemaType(name = "date")
                protected XMLGregorianCalendar signupDate;

                /**
                 * Gets the value of the programID property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getProgramID() {
                    return programID;
                }

                /**
                 * Sets the value of the programID property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setProgramID(String value) {
                    this.programID = value;
                }

                /**
                 * Gets the value of the membershipID property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getMembershipID() {
                    return membershipID;
                }

                /**
                 * Sets the value of the membershipID property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setMembershipID(String value) {
                    this.membershipID = value;
                }

                /**
                 * Gets the value of the travelSector property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTravelSector() {
                    return travelSector;
                }

                /**
                 * Sets the value of the travelSector property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTravelSector(String value) {
                    this.travelSector = value;
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
                 * Gets the value of the vendorCode property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the vendorCode property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getVendorCode().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link String }
                 * 
                 * 
                 */
                public List<String> getVendorCode() {
                    if (vendorCode == null) {
                        vendorCode = new ArrayList<String>();
                    }
                    return this.vendorCode;
                }

                /**
                 * Gets the value of the primaryLoyaltyIndicator property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Boolean }
                 *     
                 */
                public Boolean isPrimaryLoyaltyIndicator() {
                    return primaryLoyaltyIndicator;
                }

                /**
                 * Sets the value of the primaryLoyaltyIndicator property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Boolean }
                 *     
                 */
                public void setPrimaryLoyaltyIndicator(Boolean value) {
                    this.primaryLoyaltyIndicator = value;
                }

                /**
                 * Gets the value of the allianceLoyaltyLevelName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAllianceLoyaltyLevelName() {
                    return allianceLoyaltyLevelName;
                }

                /**
                 * Sets the value of the allianceLoyaltyLevelName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAllianceLoyaltyLevelName(String value) {
                    this.allianceLoyaltyLevelName = value;
                }

                /**
                 * Gets the value of the customerType property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCustomerType() {
                    return customerType;
                }

                /**
                 * Sets the value of the customerType property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCustomerType(String value) {
                    this.customerType = value;
                }

                /**
                 * Gets the value of the customerValue property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCustomerValue() {
                    return customerValue;
                }

                /**
                 * Sets the value of the customerValue property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCustomerValue(String value) {
                    this.customerValue = value;
                }

                /**
                 * Gets the value of the singleVendorInd property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSingleVendorInd() {
                    return singleVendorInd;
                }

                /**
                 * Sets the value of the singleVendorInd property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSingleVendorInd(String value) {
                    this.singleVendorInd = value;
                }

                /**
                 * Gets the value of the shareSynchInd property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getShareSynchInd() {
                    return shareSynchInd;
                }

                /**
                 * Sets the value of the shareSynchInd property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setShareSynchInd(String value) {
                    this.shareSynchInd = value;
                }

                /**
                 * Gets the value of the shareMarketInd property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getShareMarketInd() {
                    return shareMarketInd;
                }

                /**
                 * Sets the value of the shareMarketInd property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setShareMarketInd(String value) {
                    this.shareMarketInd = value;
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

                /**
                 * Gets the value of the loyalLevel property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLoyalLevel() {
                    return loyalLevel;
                }

                /**
                 * Sets the value of the loyalLevel property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLoyalLevel(String value) {
                    this.loyalLevel = value;
                }

                /**
                 * Gets the value of the loyalLevelCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Integer }
                 *     
                 */
                public Integer getLoyalLevelCode() {
                    return loyalLevelCode;
                }

                /**
                 * Sets the value of the loyalLevelCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Integer }
                 *     
                 */
                public void setLoyalLevelCode(Integer value) {
                    this.loyalLevelCode = value;
                }

                /**
                 * Gets the value of the signupDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public XMLGregorianCalendar getSignupDate() {
                    return signupDate;
                }

                /**
                 * Sets the value of the signupDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public void setSignupDate(XMLGregorianCalendar value) {
                    this.signupDate = value;
                }

            }

        }


        /**
         * The supplier's code to identify the travel arrangement for the package.
         * 
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="UniqueID" type="{http://www.opentravel.org/OTA/2003/05}UniqueID_Type" minOccurs="0"/>
         *         &lt;element name="Name" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType" minOccurs="0"/>
         *         &lt;element name="ArrivalLocation" type="{http://www.opentravel.org/OTA/2003/05}LocationType" minOccurs="0"/>
         *         &lt;element name="DepartureLocation" type="{http://www.opentravel.org/OTA/2003/05}LocationType" minOccurs="0"/>
         *       &lt;/sequence>
         *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
         *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}QuantityGroup"/>
         *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}InventoryStatusGroup"/>
         *       &lt;attribute name="TravelCode" type="{http://www.opentravel.org/OTA/2003/05}PkgTravelCode" />
         *       &lt;attribute name="TourCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to8" />
         *       &lt;attribute name="PackageID" type="{http://www.opentravel.org/OTA/2003/05}PackageID_RefType" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "uniqueID",
            "name",
            "arrivalLocation",
            "departureLocation"
        })
        public static class PackageReservation {

            @XmlElement(name = "UniqueID")
            protected UniqueIDType uniqueID;
            @XmlElement(name = "Name")
            protected PersonNameType name;
            @XmlElement(name = "ArrivalLocation")
            protected LocationType arrivalLocation;
            @XmlElement(name = "DepartureLocation")
            protected LocationType departureLocation;
            @XmlAttribute(name = "TravelCode")
            protected String travelCode;
            @XmlAttribute(name = "TourCode")
            protected String tourCode;
            @XmlAttribute(name = "PackageID")
            protected String packageID;
            @XmlAttribute(name = "Start")
            protected String start;
            @XmlAttribute(name = "Duration")
            protected String duration;
            @XmlAttribute(name = "End")
            protected String end;
            @XmlAttribute(name = "Quantity")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger quantity;
            @XmlAttribute(name = "ReservationStatusCode")
            protected String reservationStatusCode;
            @XmlAttribute(name = "ReservationStatus")
            protected InventoryStatusType reservationStatus;

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
             * Gets the value of the name property.
             * 
             * @return
             *     possible object is
             *     {@link PersonNameType }
             *     
             */
            public PersonNameType getName() {
                return name;
            }

            /**
             * Sets the value of the name property.
             * 
             * @param value
             *     allowed object is
             *     {@link PersonNameType }
             *     
             */
            public void setName(PersonNameType value) {
                this.name = value;
            }

            /**
             * Gets the value of the arrivalLocation property.
             * 
             * @return
             *     possible object is
             *     {@link LocationType }
             *     
             */
            public LocationType getArrivalLocation() {
                return arrivalLocation;
            }

            /**
             * Sets the value of the arrivalLocation property.
             * 
             * @param value
             *     allowed object is
             *     {@link LocationType }
             *     
             */
            public void setArrivalLocation(LocationType value) {
                this.arrivalLocation = value;
            }

            /**
             * Gets the value of the departureLocation property.
             * 
             * @return
             *     possible object is
             *     {@link LocationType }
             *     
             */
            public LocationType getDepartureLocation() {
                return departureLocation;
            }

            /**
             * Sets the value of the departureLocation property.
             * 
             * @param value
             *     allowed object is
             *     {@link LocationType }
             *     
             */
            public void setDepartureLocation(LocationType value) {
                this.departureLocation = value;
            }

            /**
             * Gets the value of the travelCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTravelCode() {
                return travelCode;
            }

            /**
             * Sets the value of the travelCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTravelCode(String value) {
                this.travelCode = value;
            }

            /**
             * Gets the value of the tourCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTourCode() {
                return tourCode;
            }

            /**
             * Sets the value of the tourCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTourCode(String value) {
                this.tourCode = value;
            }

            /**
             * Gets the value of the packageID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPackageID() {
                return packageID;
            }

            /**
             * Sets the value of the packageID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPackageID(String value) {
                this.packageID = value;
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
             * Gets the value of the reservationStatusCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getReservationStatusCode() {
                return reservationStatusCode;
            }

            /**
             * Sets the value of the reservationStatusCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setReservationStatusCode(String value) {
                this.reservationStatusCode = value;
            }

            /**
             * Gets the value of the reservationStatus property.
             * 
             * @return
             *     possible object is
             *     {@link InventoryStatusType }
             *     
             */
            public InventoryStatusType getReservationStatus() {
                return reservationStatus;
            }

            /**
             * Sets the value of the reservationStatus property.
             * 
             * @param value
             *     allowed object is
             *     {@link InventoryStatusType }
             *     
             */
            public void setReservationStatus(InventoryStatusType value) {
                this.reservationStatus = value;
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
         *         &lt;element name="VehResSummary" type="{http://www.opentravel.org/OTA/2003/05}VehicleReservationSummaryType" maxOccurs="99"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
//        @XmlAccessorType(XmlAccessType.FIELD)
//        @XmlType(name = "", propOrder = {
//            "vehResSummary"
//        })
//        public static class VehicleReservation {
//
//            @XmlElement(name = "VehResSummary", required = true)
//            protected List<VehicleReservationSummaryType> vehResSummary;
//
//            /**
//             * Gets the value of the vehResSummary property.
//             * 
//             * <p>
//             * This accessor method returns a reference to the live list,
//             * not a snapshot. Therefore any modification you make to the
//             * returned list will be present inside the JAXB object.
//             * This is why there is not a <CODE>set</CODE> method for the vehResSummary property.
//             * 
//             * <p>
//             * For example, to add a new item, do as follows:
//             * <pre>
//             *    getVehResSummary().add(newItem);
//             * </pre>
//             * 
//             * 
//             * <p>
//             * Objects of the following type(s) are allowed in the list
//             * {@link VehicleReservationSummaryType }
//             * 
//             * 
//             */
//            public List<VehicleReservationSummaryType> getVehResSummary() {
//                if (vehResSummary == null) {
//                    vehResSummary = new ArrayList<VehicleReservationSummaryType>();
//                }
//                return this.vehResSummary;
//            }
//
//        }

    }

}
