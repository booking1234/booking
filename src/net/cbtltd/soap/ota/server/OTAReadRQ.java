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
 *       &lt;sequence>
 *         &lt;element name="POS" type="{http://www.opentravel.org/OTA/2003/05}POS_Type" minOccurs="0"/>
 *         &lt;element name="UniqueID" type="{http://www.opentravel.org/OTA/2003/05}UniqueID_Type" minOccurs="0"/>
 *         &lt;element name="ReadRequests" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="ReadRequest" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="UniqueID" type="{http://www.opentravel.org/OTA/2003/05}UniqueID_Type"/>
 *                             &lt;element name="Verification" type="{http://www.opentravel.org/OTA/2003/05}VerificationType" minOccurs="0"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="HistoryRequestedInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="GlobalReservationReadRequest" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="TravelerName" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType"/>
 *                           &lt;/sequence>
 *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="HotelReadRequest" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="CityName" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" minOccurs="0"/>
 *                             &lt;element name="Airport" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="LocationCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to8" />
 *                                     &lt;attribute name="CodeContext" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to32" default="IATA" />
 *                                     &lt;attribute name="AirportName" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="UserID" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;extension base="{http://www.opentravel.org/OTA/2003/05}UniqueID_Type">
 *                                     &lt;attribute name="PinNumber" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
 *                                   &lt;/extension>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="Verification" type="{http://www.opentravel.org/OTA/2003/05}VerificationType" minOccurs="0"/>
 *                             &lt;element name="SelectionCriteria" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
 *                                     &lt;attribute name="DateType">
 *                                       &lt;simpleType>
 *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *                                           &lt;enumeration value="ArrivalDate"/>
 *                                           &lt;enumeration value="DepartureDate"/>
 *                                           &lt;enumeration value="CreateDate"/>
 *                                           &lt;enumeration value="LastUpdateDate"/>
 *                                         &lt;/restriction>
 *                                       &lt;/simpleType>
 *                                     &lt;/attribute>
 *                                     &lt;attribute name="SelectionType">
 *                                       &lt;simpleType>
 *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *                                           &lt;enumeration value="All"/>
 *                                           &lt;enumeration value="PreviouslyDelivered"/>
 *                                           &lt;enumeration value="Undelivered"/>
 *                                         &lt;/restriction>
 *                                       &lt;/simpleType>
 *                                     &lt;/attribute>
 *                                     &lt;attribute name="GroupCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
 *                                     &lt;attribute name="ResStatus" type="{http://www.opentravel.org/OTA/2003/05}HotelResStatusType" />
 *                                     &lt;attribute name="OriginalDeliveryMethodCode" type="{http://www.opentravel.org/OTA/2003/05}OTA_CodeType" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element ref="{http://www.opentravel.org/OTA/2003/05}TPA_Extensions" minOccurs="0"/>
 *                           &lt;/sequence>
 *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}HotelReferenceGroup"/>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="AirReadRequest" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="POS" type="{http://www.opentravel.org/OTA/2003/05}POS_Type" minOccurs="0"/>
 *                             &lt;element name="Airline" type="{http://www.opentravel.org/OTA/2003/05}CompanyNameType" minOccurs="0"/>
 *                             &lt;element name="FlightNumber" type="{http://www.opentravel.org/OTA/2003/05}FlightNumberType" minOccurs="0"/>
 *                             &lt;element name="DepartureAirport" type="{http://www.opentravel.org/OTA/2003/05}LocationType" minOccurs="0"/>
 *                             &lt;element name="DepartureDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                             &lt;element name="Name" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType" minOccurs="0"/>
 *                             &lt;element name="Telephone" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}TelephoneGroup"/>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="CustLoyalty" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}CustomerLoyaltyGroup"/>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="CreditCardInfo" type="{http://www.opentravel.org/OTA/2003/05}PaymentCardType" minOccurs="0"/>
 *                             &lt;element name="TicketNumber" type="{http://www.opentravel.org/OTA/2003/05}TicketingInfoRS_Type" minOccurs="0"/>
 *                             &lt;element name="QueueInfo" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Queue" maxOccurs="99">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}QueueGroup"/>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                     &lt;attribute name="FirstItemOnlyInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                                     &lt;attribute name="RemoveFromQueueInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                                     &lt;attribute name="FullDataInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                                     &lt;attribute name="StartDate" type="{http://www.opentravel.org/OTA/2003/05}DateOrDateTimeType" />
 *                                     &lt;attribute name="EndDate" type="{http://www.opentravel.org/OTA/2003/05}DateOrDateTimeType" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="Date" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element ref="{http://www.opentravel.org/OTA/2003/05}TPA_Extensions" minOccurs="0"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="SeatNumber" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="IncludeFF_EquivPartnerLev" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                           &lt;attribute name="ReturnFF_Number" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                           &lt;attribute name="ReturnDownlineSeg" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                           &lt;attribute name="InfoToReturn">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *                                 &lt;enumeration value="ListofFF_StatusLevels"/>
 *                                 &lt;enumeration value="NoFF_Status"/>
 *                                 &lt;enumeration value="All"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                           &lt;attribute name="FF_RequestCriteria">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *                                 &lt;enumeration value="ReturnLevelAndAbove"/>
 *                                 &lt;enumeration value="ReturnLevelAndBelow"/>
 *                                 &lt;enumeration value="ReturnOnlySpecifiedLevel"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                           &lt;attribute name="No_SSR_Ind" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="PkgReadRequest" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Name" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType" minOccurs="0"/>
 *                             &lt;element name="ArrivalLocation" type="{http://www.opentravel.org/OTA/2003/05}LocationType" minOccurs="0"/>
 *                             &lt;element name="DepartureLocation" type="{http://www.opentravel.org/OTA/2003/05}LocationType" minOccurs="0"/>
 *                           &lt;/sequence>
 *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
 *                           &lt;attribute name="TravelCode" type="{http://www.opentravel.org/OTA/2003/05}PkgTravelCode" />
 *                           &lt;attribute name="TourCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to8" />
 *                           &lt;attribute name="PackageID" type="{http://www.opentravel.org/OTA/2003/05}PackageID_RefType" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="GolfReadRequest" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Membership" maxOccurs="99" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}CustomerLoyaltyGroup"/>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="Name" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType" minOccurs="0"/>
 *                           &lt;/sequence>
 *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}ID_Group"/>
 *                           &lt;attribute name="RoundID" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *                           &lt;attribute name="PlayDateTime" type="{http://www.opentravel.org/OTA/2003/05}DateOrDateTimeType" />
 *                           &lt;attribute name="PackageID" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to8" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="VehicleReadRequest" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://www.opentravel.org/OTA/2003/05}VehicleRetrieveResRQCoreType">
 *                           &lt;sequence>
 *                             &lt;element name="VehRetResRQInfo" type="{http://www.opentravel.org/OTA/2003/05}VehicleRetrieveResRQAdditionalInfoType"/>
 *                           &lt;/sequence>
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="CruiseReadRequest" maxOccurs="99" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="SelectedSailing" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}SelectedSailingGroup"/>
 *                                     &lt;attribute name="GroupCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="GuestInfo" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType" minOccurs="0"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="HistoryRequestedInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}ResponseGroup"/>
 *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}ReqRespVersion"/>
 *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}OTA_PayloadStdAttributes"/>
 *       &lt;attribute name="ReservationType" type="{http://www.opentravel.org/OTA/2003/05}OTA_CodeType" />
 *       &lt;attribute name="ReturnListIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pos",
    "uniqueID",
    "readRequests"
})
@XmlRootElement(name = "OTA_ReadRQ")
public class OTAReadRQ {

    @XmlElement(name = "POS")
    protected POSType pos;
    @XmlElement(name = "UniqueID")
    protected UniqueIDType uniqueID;
    @XmlElement(name = "ReadRequests")
    protected OTAReadRQ.ReadRequests readRequests;
    @XmlAttribute(name = "ReservationType")
    protected String reservationType;
    @XmlAttribute(name = "ReturnListIndicator")
    protected Boolean returnListIndicator;
    @XmlAttribute(name = "MoreIndicator")
    protected Boolean moreIndicator;
    @XmlAttribute(name = "MoreDataEchoToken")
    protected String moreDataEchoToken;
    @XmlAttribute(name = "MaxResponses")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger maxResponses;
    @XmlAttribute(name = "ReqRespVersion")
    protected String reqRespVersion;
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

    /**
     * Gets the value of the pos property.
     * 
     * @return
     *     possible object is
     *     {@link POSType }
     *     
     */
    public POSType getPOS() {
        return pos;
    }

    /**
     * Sets the value of the pos property.
     * 
     * @param value
     *     allowed object is
     *     {@link POSType }
     *     
     */
    public void setPOS(POSType value) {
        this.pos = value;
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
     * Gets the value of the readRequests property.
     * 
     * @return
     *     possible object is
     *     {@link OTAReadRQ.ReadRequests }
     *     
     */
    public OTAReadRQ.ReadRequests getReadRequests() {
        return readRequests;
    }

    /**
     * Sets the value of the readRequests property.
     * 
     * @param value
     *     allowed object is
     *     {@link OTAReadRQ.ReadRequests }
     *     
     */
    public void setReadRequests(OTAReadRQ.ReadRequests value) {
        this.readRequests = value;
    }

    /**
     * Gets the value of the reservationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReservationType() {
        return reservationType;
    }

    /**
     * Sets the value of the reservationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReservationType(String value) {
        this.reservationType = value;
    }

    /**
     * Gets the value of the returnListIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isReturnListIndicator() {
        return returnListIndicator;
    }

    /**
     * Sets the value of the returnListIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReturnListIndicator(Boolean value) {
        this.returnListIndicator = value;
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
     * Gets the value of the reqRespVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqRespVersion() {
        return reqRespVersion;
    }

    /**
     * Sets the value of the reqRespVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqRespVersion(String value) {
        this.reqRespVersion = value;
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;choice>
     *         &lt;element name="ReadRequest" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="UniqueID" type="{http://www.opentravel.org/OTA/2003/05}UniqueID_Type"/>
     *                   &lt;element name="Verification" type="{http://www.opentravel.org/OTA/2003/05}VerificationType" minOccurs="0"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="HistoryRequestedInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="GlobalReservationReadRequest" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="TravelerName" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType"/>
     *                 &lt;/sequence>
     *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="HotelReadRequest" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="CityName" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" minOccurs="0"/>
     *                   &lt;element name="Airport" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attribute name="LocationCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to8" />
     *                           &lt;attribute name="CodeContext" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to32" default="IATA" />
     *                           &lt;attribute name="AirportName" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="UserID" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;extension base="{http://www.opentravel.org/OTA/2003/05}UniqueID_Type">
     *                           &lt;attribute name="PinNumber" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
     *                         &lt;/extension>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="Verification" type="{http://www.opentravel.org/OTA/2003/05}VerificationType" minOccurs="0"/>
     *                   &lt;element name="SelectionCriteria" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
     *                           &lt;attribute name="DateType">
     *                             &lt;simpleType>
     *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
     *                                 &lt;enumeration value="ArrivalDate"/>
     *                                 &lt;enumeration value="DepartureDate"/>
     *                                 &lt;enumeration value="CreateDate"/>
     *                                 &lt;enumeration value="LastUpdateDate"/>
     *                               &lt;/restriction>
     *                             &lt;/simpleType>
     *                           &lt;/attribute>
     *                           &lt;attribute name="SelectionType">
     *                             &lt;simpleType>
     *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
     *                                 &lt;enumeration value="All"/>
     *                                 &lt;enumeration value="PreviouslyDelivered"/>
     *                                 &lt;enumeration value="Undelivered"/>
     *                               &lt;/restriction>
     *                             &lt;/simpleType>
     *                           &lt;/attribute>
     *                           &lt;attribute name="GroupCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
     *                           &lt;attribute name="ResStatus" type="{http://www.opentravel.org/OTA/2003/05}HotelResStatusType" />
     *                           &lt;attribute name="OriginalDeliveryMethodCode" type="{http://www.opentravel.org/OTA/2003/05}OTA_CodeType" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element ref="{http://www.opentravel.org/OTA/2003/05}TPA_Extensions" minOccurs="0"/>
     *                 &lt;/sequence>
     *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}HotelReferenceGroup"/>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="AirReadRequest" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="POS" type="{http://www.opentravel.org/OTA/2003/05}POS_Type" minOccurs="0"/>
     *                   &lt;element name="Airline" type="{http://www.opentravel.org/OTA/2003/05}CompanyNameType" minOccurs="0"/>
     *                   &lt;element name="FlightNumber" type="{http://www.opentravel.org/OTA/2003/05}FlightNumberType" minOccurs="0"/>
     *                   &lt;element name="DepartureAirport" type="{http://www.opentravel.org/OTA/2003/05}LocationType" minOccurs="0"/>
     *                   &lt;element name="DepartureDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *                   &lt;element name="Name" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType" minOccurs="0"/>
     *                   &lt;element name="Telephone" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}TelephoneGroup"/>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="CustLoyalty" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}CustomerLoyaltyGroup"/>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="CreditCardInfo" type="{http://www.opentravel.org/OTA/2003/05}PaymentCardType" minOccurs="0"/>
     *                   &lt;element name="TicketNumber" type="{http://www.opentravel.org/OTA/2003/05}TicketingInfoRS_Type" minOccurs="0"/>
     *                   &lt;element name="QueueInfo" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Queue" maxOccurs="99">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}QueueGroup"/>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                           &lt;attribute name="FirstItemOnlyInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *                           &lt;attribute name="RemoveFromQueueInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *                           &lt;attribute name="FullDataInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *                           &lt;attribute name="StartDate" type="{http://www.opentravel.org/OTA/2003/05}DateOrDateTimeType" />
     *                           &lt;attribute name="EndDate" type="{http://www.opentravel.org/OTA/2003/05}DateOrDateTimeType" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="Date" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element ref="{http://www.opentravel.org/OTA/2003/05}TPA_Extensions" minOccurs="0"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="SeatNumber" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="IncludeFF_EquivPartnerLev" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *                 &lt;attribute name="ReturnFF_Number" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *                 &lt;attribute name="ReturnDownlineSeg" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *                 &lt;attribute name="InfoToReturn">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
     *                       &lt;enumeration value="ListofFF_StatusLevels"/>
     *                       &lt;enumeration value="NoFF_Status"/>
     *                       &lt;enumeration value="All"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
     *                 &lt;attribute name="FF_RequestCriteria">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
     *                       &lt;enumeration value="ReturnLevelAndAbove"/>
     *                       &lt;enumeration value="ReturnLevelAndBelow"/>
     *                       &lt;enumeration value="ReturnOnlySpecifiedLevel"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
     *                 &lt;attribute name="No_SSR_Ind" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="PkgReadRequest" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Name" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType" minOccurs="0"/>
     *                   &lt;element name="ArrivalLocation" type="{http://www.opentravel.org/OTA/2003/05}LocationType" minOccurs="0"/>
     *                   &lt;element name="DepartureLocation" type="{http://www.opentravel.org/OTA/2003/05}LocationType" minOccurs="0"/>
     *                 &lt;/sequence>
     *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
     *                 &lt;attribute name="TravelCode" type="{http://www.opentravel.org/OTA/2003/05}PkgTravelCode" />
     *                 &lt;attribute name="TourCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to8" />
     *                 &lt;attribute name="PackageID" type="{http://www.opentravel.org/OTA/2003/05}PackageID_RefType" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="GolfReadRequest" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Membership" maxOccurs="99" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}CustomerLoyaltyGroup"/>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="Name" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType" minOccurs="0"/>
     *                 &lt;/sequence>
     *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}ID_Group"/>
     *                 &lt;attribute name="RoundID" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
     *                 &lt;attribute name="PlayDateTime" type="{http://www.opentravel.org/OTA/2003/05}DateOrDateTimeType" />
     *                 &lt;attribute name="PackageID" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to8" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="VehicleReadRequest" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://www.opentravel.org/OTA/2003/05}VehicleRetrieveResRQCoreType">
     *                 &lt;sequence>
     *                   &lt;element name="VehRetResRQInfo" type="{http://www.opentravel.org/OTA/2003/05}VehicleRetrieveResRQAdditionalInfoType"/>
     *                 &lt;/sequence>
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="CruiseReadRequest" maxOccurs="99" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="SelectedSailing" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}SelectedSailingGroup"/>
     *                           &lt;attribute name="GroupCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="GuestInfo" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType" minOccurs="0"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="HistoryRequestedInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
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
        "readRequest",
        "globalReservationReadRequest",
        "hotelReadRequest"
    })
    public static class ReadRequests {

        @XmlElement(name = "ReadRequest")
        protected List<OTAReadRQ.ReadRequests.ReadRequest> readRequest;
        @XmlElement(name = "GlobalReservationReadRequest")
        protected List<OTAReadRQ.ReadRequests.GlobalReservationReadRequest> globalReservationReadRequest;
        @XmlElement(name = "HotelReadRequest")
        protected List<OTAReadRQ.ReadRequests.HotelReadRequest> hotelReadRequest;
//        @XmlElement(name = "AirReadRequest")
//        protected List<OTAReadRQ.ReadRequests.AirReadRequest> airReadRequest;
//        @XmlElement(name = "PkgReadRequest")
//        protected List<OTAReadRQ.ReadRequests.PkgReadRequest> pkgReadRequest;
//        @XmlElement(name = "GolfReadRequest")
//        protected List<OTAReadRQ.ReadRequests.GolfReadRequest> golfReadRequest;
//        @XmlElement(name = "VehicleReadRequest")
//        protected List<OTAReadRQ.ReadRequests.VehicleReadRequest> vehicleReadRequest;
//        @XmlElement(name = "CruiseReadRequest")
//        protected List<OTAReadRQ.ReadRequests.CruiseReadRequest> cruiseReadRequest;

        /**
         * Gets the value of the readRequest property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the readRequest property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getReadRequest().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OTAReadRQ.ReadRequests.ReadRequest }
         * 
         * 
         */
        public List<OTAReadRQ.ReadRequests.ReadRequest> getReadRequest() {
            if (readRequest == null) {
                readRequest = new ArrayList<OTAReadRQ.ReadRequests.ReadRequest>();
            }
            return this.readRequest;
        }

        /**
         * Gets the value of the globalReservationReadRequest property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the globalReservationReadRequest property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getGlobalReservationReadRequest().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OTAReadRQ.ReadRequests.GlobalReservationReadRequest }
         * 
         * 
         */
        public List<OTAReadRQ.ReadRequests.GlobalReservationReadRequest> getGlobalReservationReadRequest() {
            if (globalReservationReadRequest == null) {
                globalReservationReadRequest = new ArrayList<OTAReadRQ.ReadRequests.GlobalReservationReadRequest>();
            }
            return this.globalReservationReadRequest;
        }

        /**
         * Gets the value of the hotelReadRequest property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the hotelReadRequest property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHotelReadRequest().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OTAReadRQ.ReadRequests.HotelReadRequest }
         * 
         * 
         */
        public List<OTAReadRQ.ReadRequests.HotelReadRequest> getHotelReadRequest() {
            if (hotelReadRequest == null) {
                hotelReadRequest = new ArrayList<OTAReadRQ.ReadRequests.HotelReadRequest>();
            }
            return this.hotelReadRequest;
        }

        /**
         * Gets the value of the airReadRequest property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the airReadRequest property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAirReadRequest().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OTAReadRQ.ReadRequests.AirReadRequest }
         * 
         * 
         */
//        public List<OTAReadRQ.ReadRequests.AirReadRequest> getAirReadRequest() {
//            if (airReadRequest == null) {
//                airReadRequest = new ArrayList<OTAReadRQ.ReadRequests.AirReadRequest>();
//            }
//            return this.airReadRequest;
//        }

        /**
         * Gets the value of the pkgReadRequest property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the pkgReadRequest property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPkgReadRequest().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OTAReadRQ.ReadRequests.PkgReadRequest }
         * 
         * 
         */
//        public List<OTAReadRQ.ReadRequests.PkgReadRequest> getPkgReadRequest() {
//            if (pkgReadRequest == null) {
//                pkgReadRequest = new ArrayList<OTAReadRQ.ReadRequests.PkgReadRequest>();
//            }
//            return this.pkgReadRequest;
//        }

        /**
         * Gets the value of the golfReadRequest property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the golfReadRequest property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getGolfReadRequest().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OTAReadRQ.ReadRequests.GolfReadRequest }
         * 
         * 
         */
//        public List<OTAReadRQ.ReadRequests.GolfReadRequest> getGolfReadRequest() {
//            if (golfReadRequest == null) {
//                golfReadRequest = new ArrayList<OTAReadRQ.ReadRequests.GolfReadRequest>();
//            }
//            return this.golfReadRequest;
//        }

        /**
         * Gets the value of the vehicleReadRequest property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the vehicleReadRequest property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getVehicleReadRequest().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OTAReadRQ.ReadRequests.VehicleReadRequest }
         * 
         * 
         */
//        public List<OTAReadRQ.ReadRequests.VehicleReadRequest> getVehicleReadRequest() {
//            if (vehicleReadRequest == null) {
//                vehicleReadRequest = new ArrayList<OTAReadRQ.ReadRequests.VehicleReadRequest>();
//            }
//            return this.vehicleReadRequest;
//        }

        /**
         * Gets the value of the cruiseReadRequest property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the cruiseReadRequest property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCruiseReadRequest().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link OTAReadRQ.ReadRequests.CruiseReadRequest }
         * 
         * 
         */
//        public List<OTAReadRQ.ReadRequests.CruiseReadRequest> getCruiseReadRequest() {
//            if (cruiseReadRequest == null) {
//                cruiseReadRequest = new ArrayList<OTAReadRQ.ReadRequests.CruiseReadRequest>();
//            }
//            return this.cruiseReadRequest;
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
         *         &lt;element name="POS" type="{http://www.opentravel.org/OTA/2003/05}POS_Type" minOccurs="0"/>
         *         &lt;element name="Airline" type="{http://www.opentravel.org/OTA/2003/05}CompanyNameType" minOccurs="0"/>
         *         &lt;element name="FlightNumber" type="{http://www.opentravel.org/OTA/2003/05}FlightNumberType" minOccurs="0"/>
         *         &lt;element name="DepartureAirport" type="{http://www.opentravel.org/OTA/2003/05}LocationType" minOccurs="0"/>
         *         &lt;element name="DepartureDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
         *         &lt;element name="Name" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType" minOccurs="0"/>
         *         &lt;element name="Telephone" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}TelephoneGroup"/>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="CustLoyalty" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}CustomerLoyaltyGroup"/>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="CreditCardInfo" type="{http://www.opentravel.org/OTA/2003/05}PaymentCardType" minOccurs="0"/>
         *         &lt;element name="TicketNumber" type="{http://www.opentravel.org/OTA/2003/05}TicketingInfoRS_Type" minOccurs="0"/>
         *         &lt;element name="QueueInfo" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Queue" maxOccurs="99">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}QueueGroup"/>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *                 &lt;attribute name="FirstItemOnlyInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
         *                 &lt;attribute name="RemoveFromQueueInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
         *                 &lt;attribute name="FullDataInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
         *                 &lt;attribute name="StartDate" type="{http://www.opentravel.org/OTA/2003/05}DateOrDateTimeType" />
         *                 &lt;attribute name="EndDate" type="{http://www.opentravel.org/OTA/2003/05}DateOrDateTimeType" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="Date" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element ref="{http://www.opentravel.org/OTA/2003/05}TPA_Extensions" minOccurs="0"/>
         *       &lt;/sequence>
         *       &lt;attribute name="SeatNumber" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="IncludeFF_EquivPartnerLev" type="{http://www.w3.org/2001/XMLSchema}boolean" />
         *       &lt;attribute name="ReturnFF_Number" type="{http://www.w3.org/2001/XMLSchema}boolean" />
         *       &lt;attribute name="ReturnDownlineSeg" type="{http://www.w3.org/2001/XMLSchema}boolean" />
         *       &lt;attribute name="InfoToReturn">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
         *             &lt;enumeration value="ListofFF_StatusLevels"/>
         *             &lt;enumeration value="NoFF_Status"/>
         *             &lt;enumeration value="All"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *       &lt;attribute name="FF_RequestCriteria">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
         *             &lt;enumeration value="ReturnLevelAndAbove"/>
         *             &lt;enumeration value="ReturnLevelAndBelow"/>
         *             &lt;enumeration value="ReturnOnlySpecifiedLevel"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *       &lt;attribute name="No_SSR_Ind" type="{http://www.w3.org/2001/XMLSchema}boolean" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "pos",
            "airline",
            "flightNumber",
            "departureAirport",
            "departureDate",
            "name",
            "telephone",
            "custLoyalty",
            "creditCardInfo",
            "ticketNumber",
            "queueInfo",
            "date",
            "tpaExtensions"
        })
        public static class AirReadRequest {

            @XmlElement(name = "POS")
            protected POSType pos;
            @XmlElement(name = "Airline")
            protected CompanyNameType airline;
            @XmlElement(name = "FlightNumber")
            protected String flightNumber;
            @XmlElement(name = "DepartureAirport")
            protected LocationType departureAirport;
            @XmlElement(name = "DepartureDate")
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar departureDate;
            @XmlElement(name = "Name")
            protected PersonNameType name;
            @XmlElement(name = "Telephone")
            protected OTAReadRQ.ReadRequests.AirReadRequest.Telephone telephone;
            @XmlElement(name = "CustLoyalty")
            protected OTAReadRQ.ReadRequests.AirReadRequest.CustLoyalty custLoyalty;
            @XmlElement(name = "CreditCardInfo")
            protected PaymentCardType creditCardInfo;
//            @XmlElement(name = "TicketNumber")
//            protected TicketingInfoRSType ticketNumber;
            @XmlElement(name = "QueueInfo")
            protected OTAReadRQ.ReadRequests.AirReadRequest.QueueInfo queueInfo;
            @XmlElement(name = "Date")
            protected OTAReadRQ.ReadRequests.AirReadRequest.Date date;
            @XmlElement(name = "TPA_Extensions")
            protected TPAExtensionsType tpaExtensions;
            @XmlAttribute(name = "SeatNumber")
            protected String seatNumber;
            @XmlAttribute(name = "IncludeFF_EquivPartnerLev")
            protected Boolean includeFFEquivPartnerLev;
            @XmlAttribute(name = "ReturnFF_Number")
            protected Boolean returnFFNumber;
            @XmlAttribute(name = "ReturnDownlineSeg")
            protected Boolean returnDownlineSeg;
            @XmlAttribute(name = "InfoToReturn")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String infoToReturn;
            @XmlAttribute(name = "FF_RequestCriteria")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String ffRequestCriteria;
            @XmlAttribute(name = "No_SSR_Ind")
            protected Boolean noSSRInd;

            /**
             * Gets the value of the pos property.
             * 
             * @return
             *     possible object is
             *     {@link POSType }
             *     
             */
            public POSType getPOS() {
                return pos;
            }

            /**
             * Sets the value of the pos property.
             * 
             * @param value
             *     allowed object is
             *     {@link POSType }
             *     
             */
            public void setPOS(POSType value) {
                this.pos = value;
            }

            /**
             * Gets the value of the airline property.
             * 
             * @return
             *     possible object is
             *     {@link CompanyNameType }
             *     
             */
            public CompanyNameType getAirline() {
                return airline;
            }

            /**
             * Sets the value of the airline property.
             * 
             * @param value
             *     allowed object is
             *     {@link CompanyNameType }
             *     
             */
            public void setAirline(CompanyNameType value) {
                this.airline = value;
            }

            /**
             * Gets the value of the flightNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFlightNumber() {
                return flightNumber;
            }

            /**
             * Sets the value of the flightNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFlightNumber(String value) {
                this.flightNumber = value;
            }

            /**
             * Gets the value of the departureAirport property.
             * 
             * @return
             *     possible object is
             *     {@link LocationType }
             *     
             */
            public LocationType getDepartureAirport() {
                return departureAirport;
            }

            /**
             * Sets the value of the departureAirport property.
             * 
             * @param value
             *     allowed object is
             *     {@link LocationType }
             *     
             */
            public void setDepartureAirport(LocationType value) {
                this.departureAirport = value;
            }

            /**
             * Gets the value of the departureDate property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getDepartureDate() {
                return departureDate;
            }

            /**
             * Sets the value of the departureDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setDepartureDate(XMLGregorianCalendar value) {
                this.departureDate = value;
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
             * Gets the value of the telephone property.
             * 
             * @return
             *     possible object is
             *     {@link OTAReadRQ.ReadRequests.AirReadRequest.Telephone }
             *     
             */
            public OTAReadRQ.ReadRequests.AirReadRequest.Telephone getTelephone() {
                return telephone;
            }

            /**
             * Sets the value of the telephone property.
             * 
             * @param value
             *     allowed object is
             *     {@link OTAReadRQ.ReadRequests.AirReadRequest.Telephone }
             *     
             */
            public void setTelephone(OTAReadRQ.ReadRequests.AirReadRequest.Telephone value) {
                this.telephone = value;
            }

            /**
             * Gets the value of the custLoyalty property.
             * 
             * @return
             *     possible object is
             *     {@link OTAReadRQ.ReadRequests.AirReadRequest.CustLoyalty }
             *     
             */
            public OTAReadRQ.ReadRequests.AirReadRequest.CustLoyalty getCustLoyalty() {
                return custLoyalty;
            }

            /**
             * Sets the value of the custLoyalty property.
             * 
             * @param value
             *     allowed object is
             *     {@link OTAReadRQ.ReadRequests.AirReadRequest.CustLoyalty }
             *     
             */
            public void setCustLoyalty(OTAReadRQ.ReadRequests.AirReadRequest.CustLoyalty value) {
                this.custLoyalty = value;
            }

            /**
             * Gets the value of the creditCardInfo property.
             * 
             * @return
             *     possible object is
             *     {@link PaymentCardType }
             *     
             */
            public PaymentCardType getCreditCardInfo() {
                return creditCardInfo;
            }

            /**
             * Sets the value of the creditCardInfo property.
             * 
             * @param value
             *     allowed object is
             *     {@link PaymentCardType }
             *     
             */
            public void setCreditCardInfo(PaymentCardType value) {
                this.creditCardInfo = value;
            }

            /**
             * Gets the value of the ticketNumber property.
             * 
             * @return
             *     possible object is
             *     {@link TicketingInfoRSType }
             *     
             */
//            public TicketingInfoRSType getTicketNumber() {
//                return ticketNumber;
//            }

            /**
             * Sets the value of the ticketNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link TicketingInfoRSType }
             *     
             */
//            public void setTicketNumber(TicketingInfoRSType value) {
//                this.ticketNumber = value;
//            }

            /**
             * Gets the value of the queueInfo property.
             * 
             * @return
             *     possible object is
             *     {@link OTAReadRQ.ReadRequests.AirReadRequest.QueueInfo }
             *     
             */
            public OTAReadRQ.ReadRequests.AirReadRequest.QueueInfo getQueueInfo() {
                return queueInfo;
            }

            /**
             * Sets the value of the queueInfo property.
             * 
             * @param value
             *     allowed object is
             *     {@link OTAReadRQ.ReadRequests.AirReadRequest.QueueInfo }
             *     
             */
            public void setQueueInfo(OTAReadRQ.ReadRequests.AirReadRequest.QueueInfo value) {
                this.queueInfo = value;
            }

            /**
             * Gets the value of the date property.
             * 
             * @return
             *     possible object is
             *     {@link OTAReadRQ.ReadRequests.AirReadRequest.Date }
             *     
             */
            public OTAReadRQ.ReadRequests.AirReadRequest.Date getDate() {
                return date;
            }

            /**
             * Sets the value of the date property.
             * 
             * @param value
             *     allowed object is
             *     {@link OTAReadRQ.ReadRequests.AirReadRequest.Date }
             *     
             */
            public void setDate(OTAReadRQ.ReadRequests.AirReadRequest.Date value) {
                this.date = value;
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
             * Gets the value of the seatNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSeatNumber() {
                return seatNumber;
            }

            /**
             * Sets the value of the seatNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSeatNumber(String value) {
                this.seatNumber = value;
            }

            /**
             * Gets the value of the includeFFEquivPartnerLev property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isIncludeFFEquivPartnerLev() {
                return includeFFEquivPartnerLev;
            }

            /**
             * Sets the value of the includeFFEquivPartnerLev property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setIncludeFFEquivPartnerLev(Boolean value) {
                this.includeFFEquivPartnerLev = value;
            }

            /**
             * Gets the value of the returnFFNumber property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isReturnFFNumber() {
                return returnFFNumber;
            }

            /**
             * Sets the value of the returnFFNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setReturnFFNumber(Boolean value) {
                this.returnFFNumber = value;
            }

            /**
             * Gets the value of the returnDownlineSeg property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isReturnDownlineSeg() {
                return returnDownlineSeg;
            }

            /**
             * Sets the value of the returnDownlineSeg property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setReturnDownlineSeg(Boolean value) {
                this.returnDownlineSeg = value;
            }

            /**
             * Gets the value of the infoToReturn property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getInfoToReturn() {
                return infoToReturn;
            }

            /**
             * Sets the value of the infoToReturn property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setInfoToReturn(String value) {
                this.infoToReturn = value;
            }

            /**
             * Gets the value of the ffRequestCriteria property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFFRequestCriteria() {
                return ffRequestCriteria;
            }

            /**
             * Sets the value of the ffRequestCriteria property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFFRequestCriteria(String value) {
                this.ffRequestCriteria = value;
            }

            /**
             * Gets the value of the noSSRInd property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isNoSSRInd() {
                return noSSRInd;
            }

            /**
             * Sets the value of the noSSRInd property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setNoSSRInd(Boolean value) {
                this.noSSRInd = value;
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
            public static class CustLoyalty {

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
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Date {

                @XmlAttribute(name = "Start")
                protected String start;
                @XmlAttribute(name = "Duration")
                protected String duration;
                @XmlAttribute(name = "End")
                protected String end;

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
             *         &lt;element name="Queue" maxOccurs="99">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}QueueGroup"/>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *       &lt;attribute name="FirstItemOnlyInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
             *       &lt;attribute name="RemoveFromQueueInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
             *       &lt;attribute name="FullDataInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
             *       &lt;attribute name="StartDate" type="{http://www.opentravel.org/OTA/2003/05}DateOrDateTimeType" />
             *       &lt;attribute name="EndDate" type="{http://www.opentravel.org/OTA/2003/05}DateOrDateTimeType" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "queue"
            })
            public static class QueueInfo {

                @XmlElement(name = "Queue", required = true)
                protected List<OTAReadRQ.ReadRequests.AirReadRequest.QueueInfo.Queue> queue;
                @XmlAttribute(name = "FirstItemOnlyInd")
                protected Boolean firstItemOnlyInd;
                @XmlAttribute(name = "RemoveFromQueueInd")
                protected Boolean removeFromQueueInd;
                @XmlAttribute(name = "FullDataInd")
                protected Boolean fullDataInd;
                @XmlAttribute(name = "StartDate")
                protected String startDate;
                @XmlAttribute(name = "EndDate")
                protected String endDate;

                /**
                 * Gets the value of the queue property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the queue property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getQueue().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link OTAReadRQ.ReadRequests.AirReadRequest.QueueInfo.Queue }
                 * 
                 * 
                 */
                public List<OTAReadRQ.ReadRequests.AirReadRequest.QueueInfo.Queue> getQueue() {
                    if (queue == null) {
                        queue = new ArrayList<OTAReadRQ.ReadRequests.AirReadRequest.QueueInfo.Queue>();
                    }
                    return this.queue;
                }

                /**
                 * Gets the value of the firstItemOnlyInd property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Boolean }
                 *     
                 */
                public Boolean isFirstItemOnlyInd() {
                    return firstItemOnlyInd;
                }

                /**
                 * Sets the value of the firstItemOnlyInd property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Boolean }
                 *     
                 */
                public void setFirstItemOnlyInd(Boolean value) {
                    this.firstItemOnlyInd = value;
                }

                /**
                 * Gets the value of the removeFromQueueInd property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Boolean }
                 *     
                 */
                public Boolean isRemoveFromQueueInd() {
                    return removeFromQueueInd;
                }

                /**
                 * Sets the value of the removeFromQueueInd property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Boolean }
                 *     
                 */
                public void setRemoveFromQueueInd(Boolean value) {
                    this.removeFromQueueInd = value;
                }

                /**
                 * Gets the value of the fullDataInd property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Boolean }
                 *     
                 */
                public Boolean isFullDataInd() {
                    return fullDataInd;
                }

                /**
                 * Sets the value of the fullDataInd property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Boolean }
                 *     
                 */
                public void setFullDataInd(Boolean value) {
                    this.fullDataInd = value;
                }

                /**
                 * Gets the value of the startDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getStartDate() {
                    return startDate;
                }

                /**
                 * Sets the value of the startDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setStartDate(String value) {
                    this.startDate = value;
                }

                /**
                 * Gets the value of the endDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getEndDate() {
                    return endDate;
                }

                /**
                 * Sets the value of the endDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setEndDate(String value) {
                    this.endDate = value;
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
                 *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}QueueGroup"/>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "")
                public static class Queue {

                    @XmlAttribute(name = "PseudoCityCode")
                    protected String pseudoCityCode;
                    @XmlAttribute(name = "QueueNumber")
                    protected String queueNumber;
                    @XmlAttribute(name = "QueueCategory")
                    protected String queueCategory;
                    @XmlAttribute(name = "SystemCode")
                    protected String systemCode;
                    @XmlAttribute(name = "QueueID")
                    protected String queueID;

                    /**
                     * Gets the value of the pseudoCityCode property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPseudoCityCode() {
                        return pseudoCityCode;
                    }

                    /**
                     * Sets the value of the pseudoCityCode property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPseudoCityCode(String value) {
                        this.pseudoCityCode = value;
                    }

                    /**
                     * Gets the value of the queueNumber property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getQueueNumber() {
                        return queueNumber;
                    }

                    /**
                     * Sets the value of the queueNumber property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setQueueNumber(String value) {
                        this.queueNumber = value;
                    }

                    /**
                     * Gets the value of the queueCategory property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getQueueCategory() {
                        return queueCategory;
                    }

                    /**
                     * Sets the value of the queueCategory property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setQueueCategory(String value) {
                        this.queueCategory = value;
                    }

                    /**
                     * Gets the value of the systemCode property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSystemCode() {
                        return systemCode;
                    }

                    /**
                     * Sets the value of the systemCode property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSystemCode(String value) {
                        this.systemCode = value;
                    }

                    /**
                     * Gets the value of the queueID property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getQueueID() {
                        return queueID;
                    }

                    /**
                     * Sets the value of the queueID property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setQueueID(String value) {
                        this.queueID = value;
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
             *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}TelephoneGroup"/>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Telephone {

                @XmlAttribute(name = "FormattedInd")
                protected Boolean formattedInd;
                @XmlAttribute(name = "ShareSynchInd")
                @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                protected String shareSynchInd;
                @XmlAttribute(name = "ShareMarketInd")
                @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                protected String shareMarketInd;
                @XmlAttribute(name = "PhoneLocationType")
                protected String phoneLocationType;
                @XmlAttribute(name = "PhoneTechType")
                protected String phoneTechType;
                @XmlAttribute(name = "PhoneUseType")
                protected String phoneUseType;
                @XmlAttribute(name = "CountryAccessCode")
                protected String countryAccessCode;
                @XmlAttribute(name = "AreaCityCode")
                protected String areaCityCode;
                @XmlAttribute(name = "PhoneNumber", required = true)
                protected String phoneNumber;
                @XmlAttribute(name = "Extension")
                protected String extension;
                @XmlAttribute(name = "PIN")
                protected String pin;
                @XmlAttribute(name = "Remark")
                protected String remark;

                /**
                 * Gets the value of the formattedInd property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Boolean }
                 *     
                 */
                public boolean isFormattedInd() {
                    if (formattedInd == null) {
                        return false;
                    } else {
                        return formattedInd;
                    }
                }

                /**
                 * Sets the value of the formattedInd property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Boolean }
                 *     
                 */
                public void setFormattedInd(Boolean value) {
                    this.formattedInd = value;
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
                 * Gets the value of the phoneLocationType property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPhoneLocationType() {
                    return phoneLocationType;
                }

                /**
                 * Sets the value of the phoneLocationType property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPhoneLocationType(String value) {
                    this.phoneLocationType = value;
                }

                /**
                 * Gets the value of the phoneTechType property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPhoneTechType() {
                    return phoneTechType;
                }

                /**
                 * Sets the value of the phoneTechType property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPhoneTechType(String value) {
                    this.phoneTechType = value;
                }

                /**
                 * Gets the value of the phoneUseType property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPhoneUseType() {
                    return phoneUseType;
                }

                /**
                 * Sets the value of the phoneUseType property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPhoneUseType(String value) {
                    this.phoneUseType = value;
                }

                /**
                 * Gets the value of the countryAccessCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCountryAccessCode() {
                    return countryAccessCode;
                }

                /**
                 * Sets the value of the countryAccessCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCountryAccessCode(String value) {
                    this.countryAccessCode = value;
                }

                /**
                 * Gets the value of the areaCityCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAreaCityCode() {
                    return areaCityCode;
                }

                /**
                 * Sets the value of the areaCityCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAreaCityCode(String value) {
                    this.areaCityCode = value;
                }

                /**
                 * Gets the value of the phoneNumber property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPhoneNumber() {
                    return phoneNumber;
                }

                /**
                 * Sets the value of the phoneNumber property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPhoneNumber(String value) {
                    this.phoneNumber = value;
                }

                /**
                 * Gets the value of the extension property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getExtension() {
                    return extension;
                }

                /**
                 * Sets the value of the extension property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setExtension(String value) {
                    this.extension = value;
                }

                /**
                 * Gets the value of the pin property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPIN() {
                    return pin;
                }

                /**
                 * Sets the value of the pin property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPIN(String value) {
                    this.pin = value;
                }

                /**
                 * Gets the value of the remark property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getRemark() {
                    return remark;
                }

                /**
                 * Sets the value of the remark property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setRemark(String value) {
                    this.remark = value;
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
         *         &lt;element name="SelectedSailing" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}SelectedSailingGroup"/>
         *                 &lt;attribute name="GroupCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="GuestInfo" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType" minOccurs="0"/>
         *       &lt;/sequence>
         *       &lt;attribute name="HistoryRequestedInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "selectedSailing",
            "guestInfo"
        })
        public static class CruiseReadRequest {

            @XmlElement(name = "SelectedSailing")
            protected OTAReadRQ.ReadRequests.CruiseReadRequest.SelectedSailing selectedSailing;
            @XmlElement(name = "GuestInfo")
            protected PersonNameType guestInfo;
            @XmlAttribute(name = "HistoryRequestedInd")
            protected Boolean historyRequestedInd;

            /**
             * Gets the value of the selectedSailing property.
             * 
             * @return
             *     possible object is
             *     {@link OTAReadRQ.ReadRequests.CruiseReadRequest.SelectedSailing }
             *     
             */
            public OTAReadRQ.ReadRequests.CruiseReadRequest.SelectedSailing getSelectedSailing() {
                return selectedSailing;
            }

            /**
             * Sets the value of the selectedSailing property.
             * 
             * @param value
             *     allowed object is
             *     {@link OTAReadRQ.ReadRequests.CruiseReadRequest.SelectedSailing }
             *     
             */
            public void setSelectedSailing(OTAReadRQ.ReadRequests.CruiseReadRequest.SelectedSailing value) {
                this.selectedSailing = value;
            }

            /**
             * Gets the value of the guestInfo property.
             * 
             * @return
             *     possible object is
             *     {@link PersonNameType }
             *     
             */
            public PersonNameType getGuestInfo() {
                return guestInfo;
            }

            /**
             * Sets the value of the guestInfo property.
             * 
             * @param value
             *     allowed object is
             *     {@link PersonNameType }
             *     
             */
            public void setGuestInfo(PersonNameType value) {
                this.guestInfo = value;
            }

            /**
             * Gets the value of the historyRequestedInd property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isHistoryRequestedInd() {
                return historyRequestedInd;
            }

            /**
             * Sets the value of the historyRequestedInd property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setHistoryRequestedInd(Boolean value) {
                this.historyRequestedInd = value;
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
             *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}SelectedSailingGroup"/>
             *       &lt;attribute name="GroupCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class SelectedSailing {

                @XmlAttribute(name = "GroupCode")
                protected String groupCode;
                @XmlAttribute(name = "VoyageID")
                protected String voyageID;
                @XmlAttribute(name = "Status")
                protected String status;
                @XmlAttribute(name = "VendorCode")
                protected String vendorCode;
                @XmlAttribute(name = "VendorName")
                protected String vendorName;
                @XmlAttribute(name = "ShipCode")
                protected String shipCode;
                @XmlAttribute(name = "ShipName")
                protected String shipName;
                @XmlAttribute(name = "VendorCodeContext")
                protected String vendorCodeContext;
                @XmlAttribute(name = "Start")
                protected String start;
                @XmlAttribute(name = "Duration")
                protected String duration;
                @XmlAttribute(name = "End")
                protected String end;

                /**
                 * Gets the value of the groupCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getGroupCode() {
                    return groupCode;
                }

                /**
                 * Sets the value of the groupCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setGroupCode(String value) {
                    this.groupCode = value;
                }

                /**
                 * Gets the value of the voyageID property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getVoyageID() {
                    return voyageID;
                }

                /**
                 * Sets the value of the voyageID property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setVoyageID(String value) {
                    this.voyageID = value;
                }

                /**
                 * Gets the value of the status property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getStatus() {
                    return status;
                }

                /**
                 * Sets the value of the status property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setStatus(String value) {
                    this.status = value;
                }

                /**
                 * Gets the value of the vendorCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getVendorCode() {
                    return vendorCode;
                }

                /**
                 * Sets the value of the vendorCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setVendorCode(String value) {
                    this.vendorCode = value;
                }

                /**
                 * Gets the value of the vendorName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getVendorName() {
                    return vendorName;
                }

                /**
                 * Sets the value of the vendorName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setVendorName(String value) {
                    this.vendorName = value;
                }

                /**
                 * Gets the value of the shipCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getShipCode() {
                    return shipCode;
                }

                /**
                 * Sets the value of the shipCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setShipCode(String value) {
                    this.shipCode = value;
                }

                /**
                 * Gets the value of the shipName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getShipName() {
                    return shipName;
                }

                /**
                 * Sets the value of the shipName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setShipName(String value) {
                    this.shipName = value;
                }

                /**
                 * Gets the value of the vendorCodeContext property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getVendorCodeContext() {
                    return vendorCodeContext;
                }

                /**
                 * Sets the value of the vendorCodeContext property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setVendorCodeContext(String value) {
                    this.vendorCodeContext = value;
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
         *         &lt;element name="TravelerName" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType"/>
         *       &lt;/sequence>
         *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "travelerName"
        })
        public static class GlobalReservationReadRequest {

            @XmlElement(name = "TravelerName", required = true)
            protected PersonNameType travelerName;
            @XmlAttribute(name = "Start")
            protected String start;
            @XmlAttribute(name = "Duration")
            protected String duration;
            @XmlAttribute(name = "End")
            protected String end;

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
         *         &lt;element name="Membership" maxOccurs="99" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}CustomerLoyaltyGroup"/>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="Name" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType" minOccurs="0"/>
         *       &lt;/sequence>
         *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}ID_Group"/>
         *       &lt;attribute name="RoundID" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
         *       &lt;attribute name="PlayDateTime" type="{http://www.opentravel.org/OTA/2003/05}DateOrDateTimeType" />
         *       &lt;attribute name="PackageID" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to8" />
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
        public static class GolfReadRequest {

            @XmlElement(name = "Membership")
            protected List<OTAReadRQ.ReadRequests.GolfReadRequest.Membership> membership;
            @XmlElement(name = "Name")
            protected PersonNameType name;
            @XmlAttribute(name = "RoundID")
            @XmlSchemaType(name = "positiveInteger")
            protected BigInteger roundID;
            @XmlAttribute(name = "PlayDateTime")
            protected String playDateTime;
            @XmlAttribute(name = "PackageID")
            protected String packageID;
            @XmlAttribute(name = "ID", required = true)
            protected String id;

            /**
             * Gets the value of the membership property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the membership property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getMembership().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link OTAReadRQ.ReadRequests.GolfReadRequest.Membership }
             * 
             * 
             */
            public List<OTAReadRQ.ReadRequests.GolfReadRequest.Membership> getMembership() {
                if (membership == null) {
                    membership = new ArrayList<OTAReadRQ.ReadRequests.GolfReadRequest.Membership>();
                }
                return this.membership;
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
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="CityName" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" minOccurs="0"/>
         *         &lt;element name="Airport" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attribute name="LocationCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to8" />
         *                 &lt;attribute name="CodeContext" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to32" default="IATA" />
         *                 &lt;attribute name="AirportName" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="UserID" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{http://www.opentravel.org/OTA/2003/05}UniqueID_Type">
         *                 &lt;attribute name="PinNumber" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
         *               &lt;/extension>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="Verification" type="{http://www.opentravel.org/OTA/2003/05}VerificationType" minOccurs="0"/>
         *         &lt;element name="SelectionCriteria" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
         *                 &lt;attribute name="DateType">
         *                   &lt;simpleType>
         *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
         *                       &lt;enumeration value="ArrivalDate"/>
         *                       &lt;enumeration value="DepartureDate"/>
         *                       &lt;enumeration value="CreateDate"/>
         *                       &lt;enumeration value="LastUpdateDate"/>
         *                     &lt;/restriction>
         *                   &lt;/simpleType>
         *                 &lt;/attribute>
         *                 &lt;attribute name="SelectionType">
         *                   &lt;simpleType>
         *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
         *                       &lt;enumeration value="All"/>
         *                       &lt;enumeration value="PreviouslyDelivered"/>
         *                       &lt;enumeration value="Undelivered"/>
         *                     &lt;/restriction>
         *                   &lt;/simpleType>
         *                 &lt;/attribute>
         *                 &lt;attribute name="GroupCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
         *                 &lt;attribute name="ResStatus" type="{http://www.opentravel.org/OTA/2003/05}HotelResStatusType" />
         *                 &lt;attribute name="OriginalDeliveryMethodCode" type="{http://www.opentravel.org/OTA/2003/05}OTA_CodeType" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element ref="{http://www.opentravel.org/OTA/2003/05}TPA_Extensions" minOccurs="0"/>
         *       &lt;/sequence>
         *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}HotelReferenceGroup"/>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "cityName",
            "airport",
            "userID",
            "verification",
            "selectionCriteria",
            "tpaExtensions"
        })
        public static class HotelReadRequest {

            @XmlElement(name = "CityName")
            protected String cityName;
            @XmlElement(name = "Airport")
            protected OTAReadRQ.ReadRequests.HotelReadRequest.Airport airport;
            @XmlElement(name = "UserID")
            protected OTAReadRQ.ReadRequests.HotelReadRequest.UserID userID;
            @XmlElement(name = "Verification")
            protected VerificationType verification;
            @XmlElement(name = "SelectionCriteria")
            protected OTAReadRQ.ReadRequests.HotelReadRequest.SelectionCriteria selectionCriteria;
            @XmlElement(name = "TPA_Extensions")
            protected TPAExtensionsType tpaExtensions;
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
             * Gets the value of the cityName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCityName() {
                return cityName;
            }

            /**
             * Sets the value of the cityName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCityName(String value) {
                this.cityName = value;
            }

            /**
             * Gets the value of the airport property.
             * 
             * @return
             *     possible object is
             *     {@link OTAReadRQ.ReadRequests.HotelReadRequest.Airport }
             *     
             */
            public OTAReadRQ.ReadRequests.HotelReadRequest.Airport getAirport() {
                return airport;
            }

            /**
             * Sets the value of the airport property.
             * 
             * @param value
             *     allowed object is
             *     {@link OTAReadRQ.ReadRequests.HotelReadRequest.Airport }
             *     
             */
            public void setAirport(OTAReadRQ.ReadRequests.HotelReadRequest.Airport value) {
                this.airport = value;
            }

            /**
             * Gets the value of the userID property.
             * 
             * @return
             *     possible object is
             *     {@link OTAReadRQ.ReadRequests.HotelReadRequest.UserID }
             *     
             */
            public OTAReadRQ.ReadRequests.HotelReadRequest.UserID getUserID() {
                return userID;
            }

            /**
             * Sets the value of the userID property.
             * 
             * @param value
             *     allowed object is
             *     {@link OTAReadRQ.ReadRequests.HotelReadRequest.UserID }
             *     
             */
            public void setUserID(OTAReadRQ.ReadRequests.HotelReadRequest.UserID value) {
                this.userID = value;
            }

            /**
             * Gets the value of the verification property.
             * 
             * @return
             *     possible object is
             *     {@link VerificationType }
             *     
             */
            public VerificationType getVerification() {
                return verification;
            }

            /**
             * Sets the value of the verification property.
             * 
             * @param value
             *     allowed object is
             *     {@link VerificationType }
             *     
             */
            public void setVerification(VerificationType value) {
                this.verification = value;
            }

            /**
             * Gets the value of the selectionCriteria property.
             * 
             * @return
             *     possible object is
             *     {@link OTAReadRQ.ReadRequests.HotelReadRequest.SelectionCriteria }
             *     
             */
            public OTAReadRQ.ReadRequests.HotelReadRequest.SelectionCriteria getSelectionCriteria() {
                return selectionCriteria;
            }

            /**
             * Sets the value of the selectionCriteria property.
             * 
             * @param value
             *     allowed object is
             *     {@link OTAReadRQ.ReadRequests.HotelReadRequest.SelectionCriteria }
             *     
             */
            public void setSelectionCriteria(OTAReadRQ.ReadRequests.HotelReadRequest.SelectionCriteria value) {
                this.selectionCriteria = value;
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


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;attribute name="LocationCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to8" />
             *       &lt;attribute name="CodeContext" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to32" default="IATA" />
             *       &lt;attribute name="AirportName" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Airport {

                @XmlAttribute(name = "LocationCode")
                protected String locationCode;
                @XmlAttribute(name = "CodeContext")
                protected String codeContext;
                @XmlAttribute(name = "AirportName")
                protected String airportName;

                /**
                 * Gets the value of the locationCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLocationCode() {
                    return locationCode;
                }

                /**
                 * Sets the value of the locationCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLocationCode(String value) {
                    this.locationCode = value;
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
                    if (codeContext == null) {
                        return "IATA";
                    } else {
                        return codeContext;
                    }
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
                 * Gets the value of the airportName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAirportName() {
                    return airportName;
                }

                /**
                 * Sets the value of the airportName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAirportName(String value) {
                    this.airportName = value;
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
             *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
             *       &lt;attribute name="DateType">
             *         &lt;simpleType>
             *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
             *             &lt;enumeration value="ArrivalDate"/>
             *             &lt;enumeration value="DepartureDate"/>
             *             &lt;enumeration value="CreateDate"/>
             *             &lt;enumeration value="LastUpdateDate"/>
             *           &lt;/restriction>
             *         &lt;/simpleType>
             *       &lt;/attribute>
             *       &lt;attribute name="SelectionType">
             *         &lt;simpleType>
             *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
             *             &lt;enumeration value="All"/>
             *             &lt;enumeration value="PreviouslyDelivered"/>
             *             &lt;enumeration value="Undelivered"/>
             *           &lt;/restriction>
             *         &lt;/simpleType>
             *       &lt;/attribute>
             *       &lt;attribute name="GroupCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
             *       &lt;attribute name="ResStatus" type="{http://www.opentravel.org/OTA/2003/05}HotelResStatusType" />
             *       &lt;attribute name="OriginalDeliveryMethodCode" type="{http://www.opentravel.org/OTA/2003/05}OTA_CodeType" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class SelectionCriteria {

                @XmlAttribute(name = "DateType")
                @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                protected String dateType;
                @XmlAttribute(name = "SelectionType")
                @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                protected String selectionType;
                @XmlAttribute(name = "GroupCode")
                protected String groupCode;
                @XmlAttribute(name = "ResStatus")
                protected String resStatus;
                @XmlAttribute(name = "OriginalDeliveryMethodCode")
                protected String originalDeliveryMethodCode;
                @XmlAttribute(name = "Start")
                protected String start;
                @XmlAttribute(name = "Duration")
                protected String duration;
                @XmlAttribute(name = "End")
                protected String end;

                /**
                 * Gets the value of the dateType property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDateType() {
                    return dateType;
                }

                /**
                 * Sets the value of the dateType property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDateType(String value) {
                    this.dateType = value;
                }

                /**
                 * Gets the value of the selectionType property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSelectionType() {
                    return selectionType;
                }

                /**
                 * Sets the value of the selectionType property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSelectionType(String value) {
                    this.selectionType = value;
                }

                /**
                 * Gets the value of the groupCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getGroupCode() {
                    return groupCode;
                }

                /**
                 * Sets the value of the groupCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setGroupCode(String value) {
                    this.groupCode = value;
                }

                /**
                 * Gets the value of the resStatus property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getResStatus() {
                    return resStatus;
                }

                /**
                 * Sets the value of the resStatus property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setResStatus(String value) {
                    this.resStatus = value;
                }

                /**
                 * Gets the value of the originalDeliveryMethodCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getOriginalDeliveryMethodCode() {
                    return originalDeliveryMethodCode;
                }

                /**
                 * Sets the value of the originalDeliveryMethodCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setOriginalDeliveryMethodCode(String value) {
                    this.originalDeliveryMethodCode = value;
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
             *     &lt;extension base="{http://www.opentravel.org/OTA/2003/05}UniqueID_Type">
             *       &lt;attribute name="PinNumber" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to16" />
             *     &lt;/extension>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class UserID
                extends UniqueIDType
            {

                @XmlAttribute(name = "PinNumber")
                protected String pinNumber;

                /**
                 * Gets the value of the pinNumber property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPinNumber() {
                    return pinNumber;
                }

                /**
                 * Sets the value of the pinNumber property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPinNumber(String value) {
                    this.pinNumber = value;
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
         *         &lt;element name="Name" type="{http://www.opentravel.org/OTA/2003/05}PersonNameType" minOccurs="0"/>
         *         &lt;element name="ArrivalLocation" type="{http://www.opentravel.org/OTA/2003/05}LocationType" minOccurs="0"/>
         *         &lt;element name="DepartureLocation" type="{http://www.opentravel.org/OTA/2003/05}LocationType" minOccurs="0"/>
         *       &lt;/sequence>
         *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DateTimeSpanGroup"/>
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
            "name",
            "arrivalLocation",
            "departureLocation"
        })
        public static class PkgReadRequest {

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
         *         &lt;element name="UniqueID" type="{http://www.opentravel.org/OTA/2003/05}UniqueID_Type"/>
         *         &lt;element name="Verification" type="{http://www.opentravel.org/OTA/2003/05}VerificationType" minOccurs="0"/>
         *       &lt;/sequence>
         *       &lt;attribute name="HistoryRequestedInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
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
            "verification"
        })
        public static class ReadRequest {

            @XmlElement(name = "UniqueID", required = true)
            protected UniqueIDType uniqueID;
            @XmlElement(name = "Verification")
            protected VerificationType verification;
            @XmlAttribute(name = "HistoryRequestedInd")
            protected Boolean historyRequestedInd;

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
             * Gets the value of the verification property.
             * 
             * @return
             *     possible object is
             *     {@link VerificationType }
             *     
             */
            public VerificationType getVerification() {
                return verification;
            }

            /**
             * Sets the value of the verification property.
             * 
             * @param value
             *     allowed object is
             *     {@link VerificationType }
             *     
             */
            public void setVerification(VerificationType value) {
                this.verification = value;
            }

            /**
             * Gets the value of the historyRequestedInd property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isHistoryRequestedInd() {
                return historyRequestedInd;
            }

            /**
             * Sets the value of the historyRequestedInd property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setHistoryRequestedInd(Boolean value) {
                this.historyRequestedInd = value;
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
         *     &lt;extension base="{http://www.opentravel.org/OTA/2003/05}VehicleRetrieveResRQCoreType">
         *       &lt;sequence>
         *         &lt;element name="VehRetResRQInfo" type="{http://www.opentravel.org/OTA/2003/05}VehicleRetrieveResRQAdditionalInfoType"/>
         *       &lt;/sequence>
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
//        @XmlAccessorType(XmlAccessType.FIELD)
//        @XmlType(name = "", propOrder = {
//            "vehRetResRQInfo"
//        })
//        public static class VehicleReadRequest
//            extends VehicleRetrieveResRQCoreType
//        {
//
//            @XmlElement(name = "VehRetResRQInfo", required = true)
//            protected VehicleRetrieveResRQAdditionalInfoType vehRetResRQInfo;
//
//            /**
//             * Gets the value of the vehRetResRQInfo property.
//             * 
//             * @return
//             *     possible object is
//             *     {@link VehicleRetrieveResRQAdditionalInfoType }
//             *     
//             */
//            public VehicleRetrieveResRQAdditionalInfoType getVehRetResRQInfo() {
//                return vehRetResRQInfo;
//            }
//
//            /**
//             * Sets the value of the vehRetResRQInfo property.
//             * 
//             * @param value
//             *     allowed object is
//             *     {@link VehicleRetrieveResRQAdditionalInfoType }
//             *     
//             */
//            public void setVehRetResRQInfo(VehicleRetrieveResRQAdditionalInfoType value) {
//                this.vehRetResRQInfo = value;
//            }
//
//        }

    }

}
