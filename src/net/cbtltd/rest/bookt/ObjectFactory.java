
package net.cbtltd.rest.bookt;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.cbtltd.rest.bookt package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AnyURI_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyURI");
    private final static QName _Event_QNAME = new QName("http://connect.bookt.com/Schemas/Event.xsd", "Event");
    private final static QName _ArrayOfdateTime_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "ArrayOfdateTime");
    private final static QName _ArrayOfKeyValueOfdateTimedecimal_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "ArrayOfKeyValueOfdateTimedecimal");
    private final static QName _DateTime_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "dateTime");
    private final static QName _Char_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "char");
    private final static QName _Media_QNAME = new QName("http://connect.bookt.com/Schemas/Media.xsd", "Media");
    private final static QName _Review_QNAME = new QName("http://connect.bookt.com/Schemas/Review.xsd", "Review");
    private final static QName _QName_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "QName");
    private final static QName _ArrayOfFee_QNAME = new QName("http://connect.bookt.com/Schemas/Fee.xsd", "ArrayOfFee");
    private final static QName _UnsignedShort_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedShort");
    private final static QName _CreditCard_QNAME = new QName("http://connect.bookt.com/Schemas/CreditCard.xsd", "CreditCard");
    private final static QName _Float_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "float");
    private final static QName _Long_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "long");
    private final static QName _Rate_QNAME = new QName("http://connect.bookt.com/Schemas/Rate.xsd", "Rate");
    private final static QName _ArrayOfUnit_QNAME = new QName("http://connect.bookt.com/Schemas/Unit.xsd", "ArrayOfUnit");
    private final static QName _Fee_QNAME = new QName("http://connect.bookt.com/Schemas/Fee.xsd", "Fee");
    private final static QName _ArrayOfMedia_QNAME = new QName("http://connect.bookt.com/Schemas/Media.xsd", "ArrayOfMedia");
    private final static QName _Short_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "short");
    private final static QName _Base64Binary_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "base64Binary");
    private final static QName _Byte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "byte");
    private final static QName _Statement_QNAME = new QName("http://connect.bookt.com/Schemas/Statement.xsd", "Statement");
    private final static QName _Boolean_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "boolean");
    private final static QName _Property_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "Property");
    private final static QName _UnsignedByte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedByte");
    private final static QName _AnyType_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyType");
    private final static QName _UnsignedInt_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedInt");
    private final static QName _Int_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "int");
    private final static QName _ArrayOfStatementDetail_QNAME = new QName("http://connect.bookt.com/Schemas/StatementDetail.xsd", "ArrayOfStatementDetail");
    private final static QName _ArrayOfdecimal_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "ArrayOfdecimal");
    private final static QName _ArrayOfKeyValueOfdateTimeint_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "ArrayOfKeyValueOfdateTimeint");
    private final static QName _Decimal_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "decimal");
    private final static QName _ArrayOfstring_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "ArrayOfstring");
    private final static QName _Double_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "double");
    private final static QName _ArrayOfTax_QNAME = new QName("http://connect.bookt.com/Schemas/Tax.xsd", "ArrayOfTax");
    private final static QName _ArrayOfReview_QNAME = new QName("http://connect.bookt.com/Schemas/Review.xsd", "ArrayOfReview");
    private final static QName _StatementDetail_QNAME = new QName("http://connect.bookt.com/Schemas/StatementDetail.xsd", "StatementDetail");
    private final static QName _Guid_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid");
    private final static QName _Duration_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "duration");
    private final static QName _Tax_QNAME = new QName("http://connect.bookt.com/Schemas/Tax.xsd", "Tax");
    private final static QName _ArrayOfint_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "ArrayOfint");
    private final static QName _Unit_QNAME = new QName("http://connect.bookt.com/Schemas/Unit.xsd", "Unit");
    private final static QName _String_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "string");
    private final static QName _ArrayOfRate_QNAME = new QName("http://connect.bookt.com/Schemas/Rate.xsd", "ArrayOfRate");
    private final static QName _UnsignedLong_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedLong");
    private final static QName _Person_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "Person");
    private final static QName _ArrayOfKeyValueOfstringstring_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "ArrayOfKeyValueOfstringstring");
    private final static QName _Booking_QNAME = new QName("http://connect.bookt.com/Schemas/Booking.xsd", "Booking");
    private final static QName _GetPropertyID_QNAME = new QName("https://connect.bookt.com/connect", "ID");
    private final static QName _GetPropertyApiKey_QNAME = new QName("https://connect.bookt.com/connect", "apiKey");
    private final static QName _CreateLeadResponseCreateLeadResult_QNAME = new QName("https://connect.bookt.com/connect", "CreateLeadResult");
    private final static QName _DeleteReviewResponseDeleteReviewResult_QNAME = new QName("https://connect.bookt.com/connect", "DeleteReviewResult");
    private final static QName _EventStatement_QNAME = new QName("http://connect.bookt.com/Schemas/Event.xsd", "Statement");
    private final static QName _EventAltID_QNAME = new QName("http://connect.bookt.com/Schemas/Event.xsd", "AltID");
    private final static QName _EventType_QNAME = new QName("http://connect.bookt.com/Schemas/Event.xsd", "Type");
    private final static QName _EventSubject_QNAME = new QName("http://connect.bookt.com/Schemas/Event.xsd", "Subject");
    private final static QName _EventMessage_QNAME = new QName("http://connect.bookt.com/Schemas/Event.xsd", "Message");
    private final static QName _EventMessageFormat_QNAME = new QName("http://connect.bookt.com/Schemas/Event.xsd", "MessageFormat");
    private final static QName _EventStatus_QNAME = new QName("http://connect.bookt.com/Schemas/Event.xsd", "Status");
    private final static QName _EventLead_QNAME = new QName("http://connect.bookt.com/Schemas/Event.xsd", "Lead");
    private final static QName _EventUnitID_QNAME = new QName("http://connect.bookt.com/Schemas/Event.xsd", "UnitID");
    private final static QName _EventPropertyID_QNAME = new QName("http://connect.bookt.com/Schemas/Event.xsd", "PropertyID");
    private final static QName _PropertyUnits_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "Units");
    private final static QName _PropertyAddress2_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "Address2");
    private final static QName _PropertyAddress1_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "Address1");
    private final static QName _PropertyAmenities_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "Amenities");
    private final static QName _PropertyAltID_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "AltID");
    private final static QName _PropertyFees_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "Fees");
    private final static QName _PropertyTaxes_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "Taxes");
    private final static QName _PropertyNeighborhood_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "Neighborhood");
    private final static QName _PropertyRegion_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "Region");
    private final static QName _PropertyMaxRateCurrency_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "MaxRateCurrency");
    private final static QName _PropertyAdjLivingSpace_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "AdjLivingSpace");
    private final static QName _PropertyType_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "Type");
    private final static QName _PropertyManagedBy_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "ManagedBy");
    private final static QName _PropertyCheckInInstructions_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "CheckInInstructions");
    private final static QName _PropertyMasterID_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "MasterID");
    private final static QName _PropertyDevelopment_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "Development");
    private final static QName _PropertyImages_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "Images");
    private final static QName _PropertyAmenityTypes_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "AmenityTypes");
    private final static QName _PropertyState_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "State");
    private final static QName _PropertyHeadline_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "Headline");
    private final static QName _PropertyTags_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "Tags");
    private final static QName _PropertyStatus_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "Status");
    private final static QName _PropertyMinRateCurrency_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "MinRateCurrency");
    private final static QName _PropertyCounty_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "County");
    private final static QName _PropertyPostalCode_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "PostalCode");
    private final static QName _PropertyMetro_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "Metro");
    private final static QName _PropertyPrimaryImage_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "PrimaryImage");
    private final static QName _PropertyDescription_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "Description");
    private final static QName _PropertyCountry_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "Country");
    private final static QName _PropertyCity_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "City");
    private final static QName _PropertySummary_QNAME = new QName("http://connect.bookt.com/Schemas/Property.xsd", "Summary");
    private final static QName _GetReviewsResponseGetReviewsResult_QNAME = new QName("https://connect.bookt.com/connect", "GetReviewsResult");
    private final static QName _CreateLeadLead_QNAME = new QName("https://connect.bookt.com/connect", "lead");
    private final static QName _GetReviewIDsResponseGetReviewIDsResult_QNAME = new QName("https://connect.bookt.com/connect", "GetReviewIDsResult");
    private final static QName _SetAvailabilityNumAvailabile_QNAME = new QName("https://connect.bookt.com/connect", "numAvailabile");
    private final static QName _SetAvailabilityEffDates_QNAME = new QName("https://connect.bookt.com/connect", "effDates");
    private final static QName _GetReviewResponseGetReviewResult_QNAME = new QName("https://connect.bookt.com/connect", "GetReviewResult");
    private final static QName _GetReviewIDsReviewType_QNAME = new QName("https://connect.bookt.com/connect", "reviewType");
    private final static QName _CancelBookingBookingID_QNAME = new QName("https://connect.bookt.com/connect", "bookingID");
    private final static QName _GetPropertyCategoriesResponseGetPropertyCategoriesResult_QNAME = new QName("https://connect.bookt.com/connect", "GetPropertyCategoriesResult");
    private final static QName _GetPropertyResponseGetPropertyResult_QNAME = new QName("https://connect.bookt.com/connect", "GetPropertyResult");
    private final static QName _GetPropertyIDsByCategoryResponseGetPropertyIDsByCategoryResult_QNAME = new QName("https://connect.bookt.com/connect", "GetPropertyIDsByCategoryResult");
    private final static QName _GetPerRoomRatesRateType_QNAME = new QName("https://connect.bookt.com/connect", "rateType");
    private final static QName _GetPerRoomRatesCurrency_QNAME = new QName("https://connect.bookt.com/connect", "currency");
    private final static QName _GetEventResponseGetEventResult_QNAME = new QName("https://connect.bookt.com/connect", "GetEventResult");
    private final static QName _CreateEventResponseCreateEventResult_QNAME = new QName("https://connect.bookt.com/connect", "CreateEventResult");
    private final static QName _GetLeadResponseGetLeadResult_QNAME = new QName("https://connect.bookt.com/connect", "GetLeadResult");
    private final static QName _GetPropertyIDsByCategoryPropertyCategory_QNAME = new QName("https://connect.bookt.com/connect", "propertyCategory");
    private final static QName _GetEventCategoriesResponseGetEventCategoriesResult_QNAME = new QName("https://connect.bookt.com/connect", "GetEventCategoriesResult");
    private final static QName _SetPerRoomRatesRates_QNAME = new QName("https://connect.bookt.com/connect", "rates");
    private final static QName _FeeType_QNAME = new QName("http://connect.bookt.com/Schemas/Fee.xsd", "Type");
    private final static QName _FeeStatus_QNAME = new QName("http://connect.bookt.com/Schemas/Fee.xsd", "Status");
    private final static QName _FeeNotes_QNAME = new QName("http://connect.bookt.com/Schemas/Fee.xsd", "Notes");
    private final static QName _FeeName_QNAME = new QName("http://connect.bookt.com/Schemas/Fee.xsd", "Name");
    private final static QName _FeeCurrency_QNAME = new QName("http://connect.bookt.com/Schemas/Fee.xsd", "Currency");
    private final static QName _CreateReviewResponseCreateReviewResult_QNAME = new QName("https://connect.bookt.com/connect", "CreateReviewResult");
    private final static QName _CreateEventEventInfo_QNAME = new QName("https://connect.bookt.com/connect", "eventInfo");
    private final static QName _TaxCurrency_QNAME = new QName("http://connect.bookt.com/Schemas/Tax.xsd", "Currency");
    private final static QName _TaxType_QNAME = new QName("http://connect.bookt.com/Schemas/Tax.xsd", "Type");
    private final static QName _TaxStatus_QNAME = new QName("http://connect.bookt.com/Schemas/Tax.xsd", "Status");
    private final static QName _TaxNotes_QNAME = new QName("http://connect.bookt.com/Schemas/Tax.xsd", "Notes");
    private final static QName _TaxName_QNAME = new QName("http://connect.bookt.com/Schemas/Tax.xsd", "Name");
    private final static QName _GetRatesResponseGetRatesResult_QNAME = new QName("https://connect.bookt.com/connect", "GetRatesResult");
    private final static QName _GetBookingResponseGetBookingResult_QNAME = new QName("https://connect.bookt.com/connect", "GetBookingResult");
    private final static QName _GetAvailabilityResponseGetAvailabilityResult_QNAME = new QName("https://connect.bookt.com/connect", "GetAvailabilityResult");
    private final static QName _StatementNotes_QNAME = new QName("http://connect.bookt.com/Schemas/Statement.xsd", "Notes");
    private final static QName _StatementStatus_QNAME = new QName("http://connect.bookt.com/Schemas/Statement.xsd", "Status");
    private final static QName _StatementDescription_QNAME = new QName("http://connect.bookt.com/Schemas/Statement.xsd", "Description");
    private final static QName _StatementType_QNAME = new QName("http://connect.bookt.com/Schemas/Statement.xsd", "Type");
    private final static QName _StatementDetails_QNAME = new QName("http://connect.bookt.com/Schemas/Statement.xsd", "Details");
    private final static QName _StatementCurrency_QNAME = new QName("http://connect.bookt.com/Schemas/Statement.xsd", "Currency");
    private final static QName _CreateReviewReview_QNAME = new QName("https://connect.bookt.com/connect", "review");
    private final static QName _MediaThumbnailURL_QNAME = new QName("http://connect.bookt.com/Schemas/Media.xsd", "ThumbnailURL");
    private final static QName _MediaMediaName_QNAME = new QName("http://connect.bookt.com/Schemas/Media.xsd", "MediaName");
    private final static QName _MediaType_QNAME = new QName("http://connect.bookt.com/Schemas/Media.xsd", "Type");
    private final static QName _MediaStatus_QNAME = new QName("http://connect.bookt.com/Schemas/Media.xsd", "Status");
    private final static QName _MediaMediumURL_QNAME = new QName("http://connect.bookt.com/Schemas/Media.xsd", "MediumURL");
    private final static QName _MediaCaption_QNAME = new QName("http://connect.bookt.com/Schemas/Media.xsd", "Caption");
    private final static QName _MediaOriginalURL_QNAME = new QName("http://connect.bookt.com/Schemas/Media.xsd", "OriginalURL");
    private final static QName _ReviewTitle_QNAME = new QName("http://connect.bookt.com/Schemas/Review.xsd", "Title");
    private final static QName _ReviewReviewedBy_QNAME = new QName("http://connect.bookt.com/Schemas/Review.xsd", "ReviewedBy");
    private final static QName _ReviewResponse_QNAME = new QName("http://connect.bookt.com/Schemas/Review.xsd", "Response");
    private final static QName _ReviewComment_QNAME = new QName("http://connect.bookt.com/Schemas/Review.xsd", "Comment");
    private final static QName _ReviewReviewedEntityID_QNAME = new QName("http://connect.bookt.com/Schemas/Review.xsd", "ReviewedEntityID");
    private final static QName _ReviewAltID_QNAME = new QName("http://connect.bookt.com/Schemas/Review.xsd", "AltID");
    private final static QName _ReviewType_QNAME = new QName("http://connect.bookt.com/Schemas/Review.xsd", "Type");
    private final static QName _ReviewStatus_QNAME = new QName("http://connect.bookt.com/Schemas/Review.xsd", "Status");
    private final static QName _ReviewSource_QNAME = new QName("http://connect.bookt.com/Schemas/Review.xsd", "Source");
    private final static QName _ReviewExternalLink_QNAME = new QName("http://connect.bookt.com/Schemas/Review.xsd", "ExternalLink");
    private final static QName _MakeBookingResponseMakeBookingResult_QNAME = new QName("https://connect.bookt.com/connect", "MakeBookingResult");
    private final static QName _BookingType_QNAME = new QName("http://connect.bookt.com/Schemas/Booking.xsd", "Type");
    private final static QName _BookingStatement_QNAME = new QName("http://connect.bookt.com/Schemas/Booking.xsd", "Statement");
    private final static QName _BookingBookedBy_QNAME = new QName("http://connect.bookt.com/Schemas/Booking.xsd", "BookedBy");
    private final static QName _BookingAltID_QNAME = new QName("http://connect.bookt.com/Schemas/Booking.xsd", "AltID");
    private final static QName _BookingStatus_QNAME = new QName("http://connect.bookt.com/Schemas/Booking.xsd", "Status");
    private final static QName _BookingRenter_QNAME = new QName("http://connect.bookt.com/Schemas/Booking.xsd", "Renter");
    private final static QName _BookingPaymentTerms_QNAME = new QName("http://connect.bookt.com/Schemas/Booking.xsd", "PaymentTerms");
    private final static QName _BookingPublicNotes_QNAME = new QName("http://connect.bookt.com/Schemas/Booking.xsd", "PublicNotes");
    private final static QName _BookingCreditCard_QNAME = new QName("http://connect.bookt.com/Schemas/Booking.xsd", "CreditCard");
    private final static QName _BookingPrivateNotes_QNAME = new QName("http://connect.bookt.com/Schemas/Booking.xsd", "PrivateNotes");
    private final static QName _MakeBookingBooking_QNAME = new QName("https://connect.bookt.com/connect", "booking");
    private final static QName _CancelBookingResponseCancelBookingResult_QNAME = new QName("https://connect.bookt.com/connect", "CancelBookingResult");
    private final static QName _GetBusinessRulesResponseGetBusinessRulesResult_QNAME = new QName("https://connect.bookt.com/connect", "GetBusinessRulesResult");
    private final static QName _CreditCardAddress_QNAME = new QName("http://connect.bookt.com/Schemas/CreditCard.xsd", "Address");
    private final static QName _CreditCardType_QNAME = new QName("http://connect.bookt.com/Schemas/CreditCard.xsd", "Type");
    private final static QName _CreditCardNameOnCard_QNAME = new QName("http://connect.bookt.com/Schemas/CreditCard.xsd", "NameOnCard");
    private final static QName _CreditCardState_QNAME = new QName("http://connect.bookt.com/Schemas/CreditCard.xsd", "State");
    private final static QName _CreditCardPostalCode_QNAME = new QName("http://connect.bookt.com/Schemas/CreditCard.xsd", "PostalCode");
    private final static QName _CreditCardCardNumber_QNAME = new QName("http://connect.bookt.com/Schemas/CreditCard.xsd", "CardNumber");
    private final static QName _CreditCardCity_QNAME = new QName("http://connect.bookt.com/Schemas/CreditCard.xsd", "City");
    private final static QName _CreditCardSecurityCode_QNAME = new QName("http://connect.bookt.com/Schemas/CreditCard.xsd", "SecurityCode");
    private final static QName _GetPerRoomRatesResponseGetPerRoomRatesResult_QNAME = new QName("https://connect.bookt.com/connect", "GetPerRoomRatesResult");
    private final static QName _GetPropertyIDsResponseGetPropertyIDsResult_QNAME = new QName("https://connect.bookt.com/connect", "GetPropertyIDsResult");
    private final static QName _PersonType_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "Type");
    private final static QName _PersonWorkPhone_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "WorkPhone");
    private final static QName _PersonLastName_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "LastName");
    private final static QName _PersonHomePhone_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "HomePhone");
    private final static QName _PersonLeadSource_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "LeadSource");
    private final static QName _PersonAddress2_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "Address2");
    private final static QName _PersonAltID_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "AltID");
    private final static QName _PersonFaxNumber_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "FaxNumber");
    private final static QName _PersonAddress1_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "Address1");
    private final static QName _PersonGreeting_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "Greeting");
    private final static QName _PersonInitial_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "Initial");
    private final static QName _PersonCompany_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "Company");
    private final static QName _PersonPrimaryEmail_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "PrimaryEmail");
    private final static QName _PersonFirstName_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "FirstName");
    private final static QName _PersonSuffix_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "Suffix");
    private final static QName _PersonCountry_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "Country");
    private final static QName _PersonCity_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "City");
    private final static QName _PersonTitle_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "Title");
    private final static QName _PersonWebSite_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "WebSite");
    private final static QName _PersonPrefix_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "Prefix");
    private final static QName _PersonCellPhone_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "CellPhone");
    private final static QName _PersonState_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "State");
    private final static QName _PersonStatus_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "Status");
    private final static QName _PersonTags_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "Tags");
    private final static QName _PersonNotes_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "Notes");
    private final static QName _PersonPostalCode_QNAME = new QName("http://connect.bookt.com/Schemas/Person.xsd", "PostalCode");
    private final static QName _UnitUnitName_QNAME = new QName("http://connect.bookt.com/Schemas/Unit.xsd", "UnitName");
    private final static QName _UnitPropertyUnitID_QNAME = new QName("http://connect.bookt.com/Schemas/Unit.xsd", "PropertyUnitID");
    private final static QName _UnitStatus_QNAME = new QName("http://connect.bookt.com/Schemas/Unit.xsd", "Status");
    private final static QName _RateCurrency_QNAME = new QName("http://connect.bookt.com/Schemas/Rate.xsd", "Currency");
    private final static QName _RateRateType_QNAME = new QName("http://connect.bookt.com/Schemas/Rate.xsd", "RateType");
    private final static QName _ModifyBookingResponseModifyBookingResult_QNAME = new QName("https://connect.bookt.com/connect", "ModifyBookingResult");
    private final static QName _StatementDetailCurrency_QNAME = new QName("http://connect.bookt.com/Schemas/StatementDetail.xsd", "Currency");
    private final static QName _StatementDetailType_QNAME = new QName("http://connect.bookt.com/Schemas/StatementDetail.xsd", "Type");
    private final static QName _StatementDetailNotes_QNAME = new QName("http://connect.bookt.com/Schemas/StatementDetail.xsd", "Notes");
    private final static QName _StatementDetailStatus_QNAME = new QName("http://connect.bookt.com/Schemas/StatementDetail.xsd", "Status");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.cbtltd.rest.bookt
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ArrayOfKeyValueOfdateTimeint }
     * 
     */
    public ArrayOfKeyValueOfdateTimeint createArrayOfKeyValueOfdateTimeint() {
        return new ArrayOfKeyValueOfdateTimeint();
    }

    /**
     * Create an instance of {@link ArrayOfKeyValueOfstringstring }
     * 
     */
    public ArrayOfKeyValueOfstringstring createArrayOfKeyValueOfstringstring() {
        return new ArrayOfKeyValueOfstringstring();
    }

    /**
     * Create an instance of {@link ArrayOfKeyValueOfdateTimedecimal }
     * 
     */
    public ArrayOfKeyValueOfdateTimedecimal createArrayOfKeyValueOfdateTimedecimal() {
        return new ArrayOfKeyValueOfdateTimedecimal();
    }

    /**
     * Create an instance of {@link Person }
     * 
     */
    public Person createPerson() {
        return new Person();
    }

    /**
     * Create an instance of {@link ArrayOfStatementDetail }
     * 
     */
    public ArrayOfStatementDetail createArrayOfStatementDetail() {
        return new ArrayOfStatementDetail();
    }

    /**
     * Create an instance of {@link StatementDetail }
     * 
     */
    public StatementDetail createStatementDetail() {
        return new StatementDetail();
    }

    /**
     * Create an instance of {@link Event }
     * 
     */
    public Event createEvent() {
        return new Event();
    }

    /**
     * Create an instance of {@link CreditCard }
     * 
     */
    public CreditCard createCreditCard() {
        return new CreditCard();
    }

    /**
     * Create an instance of {@link Statement }
     * 
     */
    public Statement createStatement() {
        return new Statement();
    }

    /**
     * Create an instance of {@link GetEvent }
     * 
     */
    public GetEvent createGetEvent() {
        return new GetEvent();
    }

    /**
     * Create an instance of {@link GetPropertyIDsResponse }
     * 
     */
    public GetPropertyIDsResponse createGetPropertyIDsResponse() {
        return new GetPropertyIDsResponse();
    }

    /**
     * Create an instance of {@link ArrayOfint }
     * 
     */
    public ArrayOfint createArrayOfint() {
        return new ArrayOfint();
    }

    /**
     * Create an instance of {@link GetBusinessRules }
     * 
     */
    public GetBusinessRules createGetBusinessRules() {
        return new GetBusinessRules();
    }

    /**
     * Create an instance of {@link SetAvailabilityResponse }
     * 
     */
    public SetAvailabilityResponse createSetAvailabilityResponse() {
        return new SetAvailabilityResponse();
    }

    /**
     * Create an instance of {@link GetLead }
     * 
     */
    public GetLead createGetLead() {
        return new GetLead();
    }

    /**
     * Create an instance of {@link MakeBookingResponse }
     * 
     */
    public MakeBookingResponse createMakeBookingResponse() {
        return new MakeBookingResponse();
    }

    /**
     * Create an instance of {@link Booking }
     * 
     */
    public Booking createBooking() {
        return new Booking();
    }

    /**
     * Create an instance of {@link MakeBooking }
     * 
     */
    public MakeBooking createMakeBooking() {
        return new MakeBooking();
    }

    /**
     * Create an instance of {@link GetRates }
     * 
     */
    public GetRates createGetRates() {
        return new GetRates();
    }

    /**
     * Create an instance of {@link GetPropertyResponse }
     * 
     */
    public GetPropertyResponse createGetPropertyResponse() {
        return new GetPropertyResponse();
    }

    /**
     * Create an instance of {@link Property }
     * 
     */
    public Property createProperty() {
        return new Property();
    }

    /**
     * Create an instance of {@link ModifyBooking }
     * 
     */
    public ModifyBooking createModifyBooking() {
        return new ModifyBooking();
    }

    /**
     * Create an instance of {@link SetPerRoomRates }
     * 
     */
    public SetPerRoomRates createSetPerRoomRates() {
        return new SetPerRoomRates();
    }

    /**
     * Create an instance of {@link ArrayOfRate }
     * 
     */
    public ArrayOfRate createArrayOfRate() {
        return new ArrayOfRate();
    }

    /**
     * Create an instance of {@link CancelBookingResponse }
     * 
     */
    public CancelBookingResponse createCancelBookingResponse() {
        return new CancelBookingResponse();
    }

    /**
     * Create an instance of {@link GetReviewResponse }
     * 
     */
    public GetReviewResponse createGetReviewResponse() {
        return new GetReviewResponse();
    }

    /**
     * Create an instance of {@link Review }
     * 
     */
    public Review createReview() {
        return new Review();
    }

    /**
     * Create an instance of {@link SetAvailability }
     * 
     */
    public SetAvailability createSetAvailability() {
        return new SetAvailability();
    }

    /**
     * Create an instance of {@link ArrayOfdateTime }
     * 
     */
    public ArrayOfdateTime createArrayOfdateTime() {
        return new ArrayOfdateTime();
    }

    /**
     * Create an instance of {@link GetPerRoomRatesResponse }
     * 
     */
    public GetPerRoomRatesResponse createGetPerRoomRatesResponse() {
        return new GetPerRoomRatesResponse();
    }

    /**
     * Create an instance of {@link GetPerRoomRates }
     * 
     */
    public GetPerRoomRates createGetPerRoomRates() {
        return new GetPerRoomRates();
    }

    /**
     * Create an instance of {@link CancelBooking }
     * 
     */
    public CancelBooking createCancelBooking() {
        return new CancelBooking();
    }

    /**
     * Create an instance of {@link CreateEvent }
     * 
     */
    public CreateEvent createCreateEvent() {
        return new CreateEvent();
    }

    /**
     * Create an instance of {@link SetRatesAndAvailability }
     * 
     */
    public SetRatesAndAvailability createSetRatesAndAvailability() {
        return new SetRatesAndAvailability();
    }

    /**
     * Create an instance of {@link ArrayOfdecimal }
     * 
     */
    public ArrayOfdecimal createArrayOfdecimal() {
        return new ArrayOfdecimal();
    }

    /**
     * Create an instance of {@link CreateReviewResponse }
     * 
     */
    public CreateReviewResponse createCreateReviewResponse() {
        return new CreateReviewResponse();
    }

    /**
     * Create an instance of {@link GetEventResponse }
     * 
     */
    public GetEventResponse createGetEventResponse() {
        return new GetEventResponse();
    }

    /**
     * Create an instance of {@link GetLeadResponse }
     * 
     */
    public GetLeadResponse createGetLeadResponse() {
        return new GetLeadResponse();
    }

    /**
     * Create an instance of {@link DeleteReviewResponse }
     * 
     */
    public DeleteReviewResponse createDeleteReviewResponse() {
        return new DeleteReviewResponse();
    }

    /**
     * Create an instance of {@link GetEventCategoriesResponse }
     * 
     */
    public GetEventCategoriesResponse createGetEventCategoriesResponse() {
        return new GetEventCategoriesResponse();
    }

    /**
     * Create an instance of {@link ArrayOfstring }
     * 
     */
    public ArrayOfstring createArrayOfstring() {
        return new ArrayOfstring();
    }

    /**
     * Create an instance of {@link GetReview }
     * 
     */
    public GetReview createGetReview() {
        return new GetReview();
    }

    /**
     * Create an instance of {@link SetRates }
     * 
     */
    public SetRates createSetRates() {
        return new SetRates();
    }

    /**
     * Create an instance of {@link GetPropertyCategoriesResponse }
     * 
     */
    public GetPropertyCategoriesResponse createGetPropertyCategoriesResponse() {
        return new GetPropertyCategoriesResponse();
    }

    /**
     * Create an instance of {@link GetBusinessRulesResponse }
     * 
     */
    public GetBusinessRulesResponse createGetBusinessRulesResponse() {
        return new GetBusinessRulesResponse();
    }

    /**
     * Create an instance of {@link SetPerRoomRatesResponse }
     * 
     */
    public SetPerRoomRatesResponse createSetPerRoomRatesResponse() {
        return new SetPerRoomRatesResponse();
    }

    /**
     * Create an instance of {@link GetPropertyIDsByCategoryResponse }
     * 
     */
    public GetPropertyIDsByCategoryResponse createGetPropertyIDsByCategoryResponse() {
        return new GetPropertyIDsByCategoryResponse();
    }

    /**
     * Create an instance of {@link GetReviews }
     * 
     */
    public GetReviews createGetReviews() {
        return new GetReviews();
    }

    /**
     * Create an instance of {@link CreateLead }
     * 
     */
    public CreateLead createCreateLead() {
        return new CreateLead();
    }

    /**
     * Create an instance of {@link GetEventCategories }
     * 
     */
    public GetEventCategories createGetEventCategories() {
        return new GetEventCategories();
    }

    /**
     * Create an instance of {@link CreateReview }
     * 
     */
    public CreateReview createCreateReview() {
        return new CreateReview();
    }

    /**
     * Create an instance of {@link DeleteReview }
     * 
     */
    public DeleteReview createDeleteReview() {
        return new DeleteReview();
    }

    /**
     * Create an instance of {@link GetReviewIDsResponse }
     * 
     */
    public GetReviewIDsResponse createGetReviewIDsResponse() {
        return new GetReviewIDsResponse();
    }

    /**
     * Create an instance of {@link GetProperty }
     * 
     */
    public GetProperty createGetProperty() {
        return new GetProperty();
    }

    /**
     * Create an instance of {@link GetAvailability }
     * 
     */
    public GetAvailability createGetAvailability() {
        return new GetAvailability();
    }

    /**
     * Create an instance of {@link SetRatesResponse }
     * 
     */
    public SetRatesResponse createSetRatesResponse() {
        return new SetRatesResponse();
    }

    /**
     * Create an instance of {@link CreateEventResponse }
     * 
     */
    public CreateEventResponse createCreateEventResponse() {
        return new CreateEventResponse();
    }

    /**
     * Create an instance of {@link GetAvailabilityResponse }
     * 
     */
    public GetAvailabilityResponse createGetAvailabilityResponse() {
        return new GetAvailabilityResponse();
    }

    /**
     * Create an instance of {@link GetRatesResponse }
     * 
     */
    public GetRatesResponse createGetRatesResponse() {
        return new GetRatesResponse();
    }

    /**
     * Create an instance of {@link GetPropertyIDsByCategory }
     * 
     */
    public GetPropertyIDsByCategory createGetPropertyIDsByCategory() {
        return new GetPropertyIDsByCategory();
    }

    /**
     * Create an instance of {@link GetPropertyCategories }
     * 
     */
    public GetPropertyCategories createGetPropertyCategories() {
        return new GetPropertyCategories();
    }

    /**
     * Create an instance of {@link SetRatesAndAvailabilityResponse }
     * 
     */
    public SetRatesAndAvailabilityResponse createSetRatesAndAvailabilityResponse() {
        return new SetRatesAndAvailabilityResponse();
    }

    /**
     * Create an instance of {@link GetBookingResponse }
     * 
     */
    public GetBookingResponse createGetBookingResponse() {
        return new GetBookingResponse();
    }

    /**
     * Create an instance of {@link GetBooking }
     * 
     */
    public GetBooking createGetBooking() {
        return new GetBooking();
    }

    /**
     * Create an instance of {@link CreateLeadResponse }
     * 
     */
    public CreateLeadResponse createCreateLeadResponse() {
        return new CreateLeadResponse();
    }

    /**
     * Create an instance of {@link GetPropertyIDs }
     * 
     */
    public GetPropertyIDs createGetPropertyIDs() {
        return new GetPropertyIDs();
    }

    /**
     * Create an instance of {@link ModifyBookingResponse }
     * 
     */
    public ModifyBookingResponse createModifyBookingResponse() {
        return new ModifyBookingResponse();
    }

    /**
     * Create an instance of {@link GetReviewIDs }
     * 
     */
    public GetReviewIDs createGetReviewIDs() {
        return new GetReviewIDs();
    }

    /**
     * Create an instance of {@link GetReviewsResponse }
     * 
     */
    public GetReviewsResponse createGetReviewsResponse() {
        return new GetReviewsResponse();
    }

    /**
     * Create an instance of {@link ArrayOfReview }
     * 
     */
    public ArrayOfReview createArrayOfReview() {
        return new ArrayOfReview();
    }

    /**
     * Create an instance of {@link Rate }
     * 
     */
    public Rate createRate() {
        return new Rate();
    }

    /**
     * Create an instance of {@link Media }
     * 
     */
    public Media createMedia() {
        return new Media();
    }

    /**
     * Create an instance of {@link ArrayOfMedia }
     * 
     */
    public ArrayOfMedia createArrayOfMedia() {
        return new ArrayOfMedia();
    }

    /**
     * Create an instance of {@link Fee }
     * 
     */
    public Fee createFee() {
        return new Fee();
    }

    /**
     * Create an instance of {@link ArrayOfFee }
     * 
     */
    public ArrayOfFee createArrayOfFee() {
        return new ArrayOfFee();
    }

    /**
     * Create an instance of {@link ArrayOfUnit }
     * 
     */
    public ArrayOfUnit createArrayOfUnit() {
        return new ArrayOfUnit();
    }

    /**
     * Create an instance of {@link Unit }
     * 
     */
    public Unit createUnit() {
        return new Unit();
    }

    /**
     * Create an instance of {@link ArrayOfTax }
     * 
     */
    public ArrayOfTax createArrayOfTax() {
        return new ArrayOfTax();
    }

    /**
     * Create an instance of {@link Tax }
     * 
     */
    public Tax createTax() {
        return new Tax();
    }

    /**
     * Create an instance of {@link ArrayOfKeyValueOfdateTimeint.KeyValueOfdateTimeint }
     * 
     */
    public ArrayOfKeyValueOfdateTimeint.KeyValueOfdateTimeint createArrayOfKeyValueOfdateTimeintKeyValueOfdateTimeint() {
        return new ArrayOfKeyValueOfdateTimeint.KeyValueOfdateTimeint();
    }

    /**
     * Create an instance of {@link ArrayOfKeyValueOfstringstring.KeyValueOfstringstring }
     * 
     */
    public ArrayOfKeyValueOfstringstring.KeyValueOfstringstring createArrayOfKeyValueOfstringstringKeyValueOfstringstring() {
        return new ArrayOfKeyValueOfstringstring.KeyValueOfstringstring();
    }

    /**
     * Create an instance of {@link ArrayOfKeyValueOfdateTimedecimal.KeyValueOfdateTimedecimal }
     * 
     */
    public ArrayOfKeyValueOfdateTimedecimal.KeyValueOfdateTimedecimal createArrayOfKeyValueOfdateTimedecimalKeyValueOfdateTimedecimal() {
        return new ArrayOfKeyValueOfdateTimedecimal.KeyValueOfdateTimedecimal();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyURI")
    public JAXBElement<String> createAnyURI(String value) {
        return new JAXBElement<String>(_AnyURI_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Event }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Event.xsd", name = "Event")
    public JAXBElement<Event> createEvent(Event value) {
        return new JAXBElement<Event>(_Event_QNAME, Event.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfdateTime }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/Arrays", name = "ArrayOfdateTime")
    public JAXBElement<ArrayOfdateTime> createArrayOfdateTime(ArrayOfdateTime value) {
        return new JAXBElement<ArrayOfdateTime>(_ArrayOfdateTime_QNAME, ArrayOfdateTime.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfKeyValueOfdateTimedecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/Arrays", name = "ArrayOfKeyValueOfdateTimedecimal")
    public JAXBElement<ArrayOfKeyValueOfdateTimedecimal> createArrayOfKeyValueOfdateTimedecimal(ArrayOfKeyValueOfdateTimedecimal value) {
        return new JAXBElement<ArrayOfKeyValueOfdateTimedecimal>(_ArrayOfKeyValueOfdateTimedecimal_QNAME, ArrayOfKeyValueOfdateTimedecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "dateTime")
    public JAXBElement<XMLGregorianCalendar> createDateTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_DateTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "char")
    public JAXBElement<Integer> createChar(Integer value) {
        return new JAXBElement<Integer>(_Char_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Media }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Media.xsd", name = "Media")
    public JAXBElement<Media> createMedia(Media value) {
        return new JAXBElement<Media>(_Media_QNAME, Media.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Review }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Review.xsd", name = "Review")
    public JAXBElement<Review> createReview(Review value) {
        return new JAXBElement<Review>(_Review_QNAME, Review.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "QName")
    public JAXBElement<QName> createQName(QName value) {
        return new JAXBElement<QName>(_QName_QNAME, QName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfFee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Fee.xsd", name = "ArrayOfFee")
    public JAXBElement<ArrayOfFee> createArrayOfFee(ArrayOfFee value) {
        return new JAXBElement<ArrayOfFee>(_ArrayOfFee_QNAME, ArrayOfFee.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedShort")
    public JAXBElement<Integer> createUnsignedShort(Integer value) {
        return new JAXBElement<Integer>(_UnsignedShort_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreditCard }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/CreditCard.xsd", name = "CreditCard")
    public JAXBElement<CreditCard> createCreditCard(CreditCard value) {
        return new JAXBElement<CreditCard>(_CreditCard_QNAME, CreditCard.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "float")
    public JAXBElement<Float> createFloat(Float value) {
        return new JAXBElement<Float>(_Float_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "long")
    public JAXBElement<Long> createLong(Long value) {
        return new JAXBElement<Long>(_Long_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Rate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Rate.xsd", name = "Rate")
    public JAXBElement<Rate> createRate(Rate value) {
        return new JAXBElement<Rate>(_Rate_QNAME, Rate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfUnit }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Unit.xsd", name = "ArrayOfUnit")
    public JAXBElement<ArrayOfUnit> createArrayOfUnit(ArrayOfUnit value) {
        return new JAXBElement<ArrayOfUnit>(_ArrayOfUnit_QNAME, ArrayOfUnit.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Fee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Fee.xsd", name = "Fee")
    public JAXBElement<Fee> createFee(Fee value) {
        return new JAXBElement<Fee>(_Fee_QNAME, Fee.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfMedia }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Media.xsd", name = "ArrayOfMedia")
    public JAXBElement<ArrayOfMedia> createArrayOfMedia(ArrayOfMedia value) {
        return new JAXBElement<ArrayOfMedia>(_ArrayOfMedia_QNAME, ArrayOfMedia.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "short")
    public JAXBElement<Short> createShort(Short value) {
        return new JAXBElement<Short>(_Short_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "base64Binary")
    public JAXBElement<byte[]> createBase64Binary(byte[] value) {
        return new JAXBElement<byte[]>(_Base64Binary_QNAME, byte[].class, null, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "byte")
    public JAXBElement<Byte> createByte(Byte value) {
        return new JAXBElement<Byte>(_Byte_QNAME, Byte.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Statement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Statement.xsd", name = "Statement")
    public JAXBElement<Statement> createStatement(Statement value) {
        return new JAXBElement<Statement>(_Statement_QNAME, Statement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "boolean")
    public JAXBElement<Boolean> createBoolean(Boolean value) {
        return new JAXBElement<Boolean>(_Boolean_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Property }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "Property")
    public JAXBElement<Property> createProperty(Property value) {
        return new JAXBElement<Property>(_Property_QNAME, Property.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedByte")
    public JAXBElement<Short> createUnsignedByte(Short value) {
        return new JAXBElement<Short>(_UnsignedByte_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyType")
    public JAXBElement<Object> createAnyType(Object value) {
        return new JAXBElement<Object>(_AnyType_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedInt")
    public JAXBElement<Long> createUnsignedInt(Long value) {
        return new JAXBElement<Long>(_UnsignedInt_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "int")
    public JAXBElement<Integer> createInt(Integer value) {
        return new JAXBElement<Integer>(_Int_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfStatementDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/StatementDetail.xsd", name = "ArrayOfStatementDetail")
    public JAXBElement<ArrayOfStatementDetail> createArrayOfStatementDetail(ArrayOfStatementDetail value) {
        return new JAXBElement<ArrayOfStatementDetail>(_ArrayOfStatementDetail_QNAME, ArrayOfStatementDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfdecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/Arrays", name = "ArrayOfdecimal")
    public JAXBElement<ArrayOfdecimal> createArrayOfdecimal(ArrayOfdecimal value) {
        return new JAXBElement<ArrayOfdecimal>(_ArrayOfdecimal_QNAME, ArrayOfdecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfKeyValueOfdateTimeint }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/Arrays", name = "ArrayOfKeyValueOfdateTimeint")
    public JAXBElement<ArrayOfKeyValueOfdateTimeint> createArrayOfKeyValueOfdateTimeint(ArrayOfKeyValueOfdateTimeint value) {
        return new JAXBElement<ArrayOfKeyValueOfdateTimeint>(_ArrayOfKeyValueOfdateTimeint_QNAME, ArrayOfKeyValueOfdateTimeint.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "decimal")
    public JAXBElement<BigDecimal> createDecimal(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Decimal_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/Arrays", name = "ArrayOfstring")
    public JAXBElement<ArrayOfstring> createArrayOfstring(ArrayOfstring value) {
        return new JAXBElement<ArrayOfstring>(_ArrayOfstring_QNAME, ArrayOfstring.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "double")
    public JAXBElement<Double> createDouble(Double value) {
        return new JAXBElement<Double>(_Double_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfTax }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Tax.xsd", name = "ArrayOfTax")
    public JAXBElement<ArrayOfTax> createArrayOfTax(ArrayOfTax value) {
        return new JAXBElement<ArrayOfTax>(_ArrayOfTax_QNAME, ArrayOfTax.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfReview }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Review.xsd", name = "ArrayOfReview")
    public JAXBElement<ArrayOfReview> createArrayOfReview(ArrayOfReview value) {
        return new JAXBElement<ArrayOfReview>(_ArrayOfReview_QNAME, ArrayOfReview.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StatementDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/StatementDetail.xsd", name = "StatementDetail")
    public JAXBElement<StatementDetail> createStatementDetail(StatementDetail value) {
        return new JAXBElement<StatementDetail>(_StatementDetail_QNAME, StatementDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "guid")
    public JAXBElement<String> createGuid(String value) {
        return new JAXBElement<String>(_Guid_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "duration")
    public JAXBElement<Duration> createDuration(Duration value) {
        return new JAXBElement<Duration>(_Duration_QNAME, Duration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Tax }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Tax.xsd", name = "Tax")
    public JAXBElement<Tax> createTax(Tax value) {
        return new JAXBElement<Tax>(_Tax_QNAME, Tax.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfint }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/Arrays", name = "ArrayOfint")
    public JAXBElement<ArrayOfint> createArrayOfint(ArrayOfint value) {
        return new JAXBElement<ArrayOfint>(_ArrayOfint_QNAME, ArrayOfint.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Unit }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Unit.xsd", name = "Unit")
    public JAXBElement<Unit> createUnit(Unit value) {
        return new JAXBElement<Unit>(_Unit_QNAME, Unit.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "string")
    public JAXBElement<String> createString(String value) {
        return new JAXBElement<String>(_String_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfRate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Rate.xsd", name = "ArrayOfRate")
    public JAXBElement<ArrayOfRate> createArrayOfRate(ArrayOfRate value) {
        return new JAXBElement<ArrayOfRate>(_ArrayOfRate_QNAME, ArrayOfRate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedLong")
    public JAXBElement<BigInteger> createUnsignedLong(BigInteger value) {
        return new JAXBElement<BigInteger>(_UnsignedLong_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Person }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "Person")
    public JAXBElement<Person> createPerson(Person value) {
        return new JAXBElement<Person>(_Person_QNAME, Person.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfKeyValueOfstringstring }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/Arrays", name = "ArrayOfKeyValueOfstringstring")
    public JAXBElement<ArrayOfKeyValueOfstringstring> createArrayOfKeyValueOfstringstring(ArrayOfKeyValueOfstringstring value) {
        return new JAXBElement<ArrayOfKeyValueOfstringstring>(_ArrayOfKeyValueOfstringstring_QNAME, ArrayOfKeyValueOfstringstring.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Booking }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Booking.xsd", name = "Booking")
    public JAXBElement<Booking> createBooking(Booking value) {
        return new JAXBElement<Booking>(_Booking_QNAME, Booking.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "ID", scope = GetProperty.class)
    public JAXBElement<String> createGetPropertyID(String value) {
        return new JAXBElement<String>(_GetPropertyID_QNAME, String.class, GetProperty.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = GetProperty.class)
    public JAXBElement<String> createGetPropertyApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, GetProperty.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = GetEventCategories.class)
    public JAXBElement<String> createGetEventCategoriesApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, GetEventCategories.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Person }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "CreateLeadResult", scope = CreateLeadResponse.class)
    public JAXBElement<Person> createCreateLeadResponseCreateLeadResult(Person value) {
        return new JAXBElement<Person>(_CreateLeadResponseCreateLeadResult_QNAME, Person.class, CreateLeadResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "DeleteReviewResult", scope = DeleteReviewResponse.class)
    public JAXBElement<String> createDeleteReviewResponseDeleteReviewResult(String value) {
        return new JAXBElement<String>(_DeleteReviewResponseDeleteReviewResult_QNAME, String.class, DeleteReviewResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Statement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Event.xsd", name = "Statement", scope = Event.class)
    public JAXBElement<Statement> createEventStatement(Statement value) {
        return new JAXBElement<Statement>(_EventStatement_QNAME, Statement.class, Event.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Event.xsd", name = "AltID", scope = Event.class)
    public JAXBElement<String> createEventAltID(String value) {
        return new JAXBElement<String>(_EventAltID_QNAME, String.class, Event.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Event.xsd", name = "Type", scope = Event.class)
    public JAXBElement<String> createEventType(String value) {
        return new JAXBElement<String>(_EventType_QNAME, String.class, Event.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Event.xsd", name = "Subject", scope = Event.class)
    public JAXBElement<String> createEventSubject(String value) {
        return new JAXBElement<String>(_EventSubject_QNAME, String.class, Event.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Event.xsd", name = "Message", scope = Event.class)
    public JAXBElement<String> createEventMessage(String value) {
        return new JAXBElement<String>(_EventMessage_QNAME, String.class, Event.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Event.xsd", name = "MessageFormat", scope = Event.class)
    public JAXBElement<String> createEventMessageFormat(String value) {
        return new JAXBElement<String>(_EventMessageFormat_QNAME, String.class, Event.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Event.xsd", name = "Status", scope = Event.class)
    public JAXBElement<String> createEventStatus(String value) {
        return new JAXBElement<String>(_EventStatus_QNAME, String.class, Event.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Person }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Event.xsd", name = "Lead", scope = Event.class)
    public JAXBElement<Person> createEventLead(Person value) {
        return new JAXBElement<Person>(_EventLead_QNAME, Person.class, Event.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Event.xsd", name = "UnitID", scope = Event.class)
    public JAXBElement<String> createEventUnitID(String value) {
        return new JAXBElement<String>(_EventUnitID_QNAME, String.class, Event.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Event.xsd", name = "PropertyID", scope = Event.class)
    public JAXBElement<String> createEventPropertyID(String value) {
        return new JAXBElement<String>(_EventPropertyID_QNAME, String.class, Event.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfUnit }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "Units", scope = Property.class)
    public JAXBElement<ArrayOfUnit> createPropertyUnits(ArrayOfUnit value) {
        return new JAXBElement<ArrayOfUnit>(_PropertyUnits_QNAME, ArrayOfUnit.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "Address2", scope = Property.class)
    public JAXBElement<String> createPropertyAddress2(String value) {
        return new JAXBElement<String>(_PropertyAddress2_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "Address1", scope = Property.class)
    public JAXBElement<String> createPropertyAddress1(String value) {
        return new JAXBElement<String>(_PropertyAddress1_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "Amenities", scope = Property.class)
    public JAXBElement<ArrayOfstring> createPropertyAmenities(ArrayOfstring value) {
        return new JAXBElement<ArrayOfstring>(_PropertyAmenities_QNAME, ArrayOfstring.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "AltID", scope = Property.class)
    public JAXBElement<String> createPropertyAltID(String value) {
        return new JAXBElement<String>(_PropertyAltID_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfFee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "Fees", scope = Property.class)
    public JAXBElement<ArrayOfFee> createPropertyFees(ArrayOfFee value) {
        return new JAXBElement<ArrayOfFee>(_PropertyFees_QNAME, ArrayOfFee.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfTax }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "Taxes", scope = Property.class)
    public JAXBElement<ArrayOfTax> createPropertyTaxes(ArrayOfTax value) {
        return new JAXBElement<ArrayOfTax>(_PropertyTaxes_QNAME, ArrayOfTax.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "Neighborhood", scope = Property.class)
    public JAXBElement<String> createPropertyNeighborhood(String value) {
        return new JAXBElement<String>(_PropertyNeighborhood_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "Region", scope = Property.class)
    public JAXBElement<String> createPropertyRegion(String value) {
        return new JAXBElement<String>(_PropertyRegion_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "MaxRateCurrency", scope = Property.class)
    public JAXBElement<String> createPropertyMaxRateCurrency(String value) {
        return new JAXBElement<String>(_PropertyMaxRateCurrency_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "AdjLivingSpace", scope = Property.class)
    public JAXBElement<String> createPropertyAdjLivingSpace(String value) {
        return new JAXBElement<String>(_PropertyAdjLivingSpace_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "Type", scope = Property.class)
    public JAXBElement<String> createPropertyType(String value) {
        return new JAXBElement<String>(_PropertyType_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "ManagedBy", scope = Property.class)
    public JAXBElement<String> createPropertyManagedBy(String value) {
        return new JAXBElement<String>(_PropertyManagedBy_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "CheckInInstructions", scope = Property.class)
    public JAXBElement<String> createPropertyCheckInInstructions(String value) {
        return new JAXBElement<String>(_PropertyCheckInInstructions_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "MasterID", scope = Property.class)
    public JAXBElement<String> createPropertyMasterID(String value) {
        return new JAXBElement<String>(_PropertyMasterID_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "Development", scope = Property.class)
    public JAXBElement<String> createPropertyDevelopment(String value) {
        return new JAXBElement<String>(_PropertyDevelopment_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfMedia }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "Images", scope = Property.class)
    public JAXBElement<ArrayOfMedia> createPropertyImages(ArrayOfMedia value) {
        return new JAXBElement<ArrayOfMedia>(_PropertyImages_QNAME, ArrayOfMedia.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "AmenityTypes", scope = Property.class)
    public JAXBElement<ArrayOfstring> createPropertyAmenityTypes(ArrayOfstring value) {
        return new JAXBElement<ArrayOfstring>(_PropertyAmenityTypes_QNAME, ArrayOfstring.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "State", scope = Property.class)
    public JAXBElement<String> createPropertyState(String value) {
        return new JAXBElement<String>(_PropertyState_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "Headline", scope = Property.class)
    public JAXBElement<String> createPropertyHeadline(String value) {
        return new JAXBElement<String>(_PropertyHeadline_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "Tags", scope = Property.class)
    public JAXBElement<ArrayOfstring> createPropertyTags(ArrayOfstring value) {
        return new JAXBElement<ArrayOfstring>(_PropertyTags_QNAME, ArrayOfstring.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "Status", scope = Property.class)
    public JAXBElement<String> createPropertyStatus(String value) {
        return new JAXBElement<String>(_PropertyStatus_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "MinRateCurrency", scope = Property.class)
    public JAXBElement<String> createPropertyMinRateCurrency(String value) {
        return new JAXBElement<String>(_PropertyMinRateCurrency_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "County", scope = Property.class)
    public JAXBElement<String> createPropertyCounty(String value) {
        return new JAXBElement<String>(_PropertyCounty_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "PostalCode", scope = Property.class)
    public JAXBElement<String> createPropertyPostalCode(String value) {
        return new JAXBElement<String>(_PropertyPostalCode_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "Metro", scope = Property.class)
    public JAXBElement<String> createPropertyMetro(String value) {
        return new JAXBElement<String>(_PropertyMetro_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Media }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "PrimaryImage", scope = Property.class)
    public JAXBElement<Media> createPropertyPrimaryImage(Media value) {
        return new JAXBElement<Media>(_PropertyPrimaryImage_QNAME, Media.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "Description", scope = Property.class)
    public JAXBElement<String> createPropertyDescription(String value) {
        return new JAXBElement<String>(_PropertyDescription_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "Country", scope = Property.class)
    public JAXBElement<String> createPropertyCountry(String value) {
        return new JAXBElement<String>(_PropertyCountry_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "City", scope = Property.class)
    public JAXBElement<String> createPropertyCity(String value) {
        return new JAXBElement<String>(_PropertyCity_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Property.xsd", name = "Summary", scope = Property.class)
    public JAXBElement<String> createPropertySummary(String value) {
        return new JAXBElement<String>(_PropertySummary_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfReview }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "GetReviewsResult", scope = GetReviewsResponse.class)
    public JAXBElement<ArrayOfReview> createGetReviewsResponseGetReviewsResult(ArrayOfReview value) {
        return new JAXBElement<ArrayOfReview>(_GetReviewsResponseGetReviewsResult_QNAME, ArrayOfReview.class, GetReviewsResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Person }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "lead", scope = CreateLead.class)
    public JAXBElement<Person> createCreateLeadLead(Person value) {
        return new JAXBElement<Person>(_CreateLeadLead_QNAME, Person.class, CreateLead.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = CreateLead.class)
    public JAXBElement<String> createCreateLeadApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, CreateLead.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "GetReviewIDsResult", scope = GetReviewIDsResponse.class)
    public JAXBElement<ArrayOfstring> createGetReviewIDsResponseGetReviewIDsResult(ArrayOfstring value) {
        return new JAXBElement<ArrayOfstring>(_GetReviewIDsResponseGetReviewIDsResult_QNAME, ArrayOfstring.class, GetReviewIDsResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfint }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "numAvailabile", scope = SetAvailability.class)
    public JAXBElement<ArrayOfint> createSetAvailabilityNumAvailabile(ArrayOfint value) {
        return new JAXBElement<ArrayOfint>(_SetAvailabilityNumAvailabile_QNAME, ArrayOfint.class, SetAvailability.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfdateTime }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "effDates", scope = SetAvailability.class)
    public JAXBElement<ArrayOfdateTime> createSetAvailabilityEffDates(ArrayOfdateTime value) {
        return new JAXBElement<ArrayOfdateTime>(_SetAvailabilityEffDates_QNAME, ArrayOfdateTime.class, SetAvailability.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = SetAvailability.class)
    public JAXBElement<String> createSetAvailabilityApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, SetAvailability.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Review }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "GetReviewResult", scope = GetReviewResponse.class)
    public JAXBElement<Review> createGetReviewResponseGetReviewResult(Review value) {
        return new JAXBElement<Review>(_GetReviewResponseGetReviewResult_QNAME, Review.class, GetReviewResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "reviewType", scope = GetReviewIDs.class)
    public JAXBElement<String> createGetReviewIDsReviewType(String value) {
        return new JAXBElement<String>(_GetReviewIDsReviewType_QNAME, String.class, GetReviewIDs.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = GetReviewIDs.class)
    public JAXBElement<String> createGetReviewIDsApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, GetReviewIDs.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "bookingID", scope = CancelBooking.class)
    public JAXBElement<String> createCancelBookingBookingID(String value) {
        return new JAXBElement<String>(_CancelBookingBookingID_QNAME, String.class, CancelBooking.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = CancelBooking.class)
    public JAXBElement<String> createCancelBookingApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, CancelBooking.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "ID", scope = DeleteReview.class)
    public JAXBElement<String> createDeleteReviewID(String value) {
        return new JAXBElement<String>(_GetPropertyID_QNAME, String.class, DeleteReview.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = DeleteReview.class)
    public JAXBElement<String> createDeleteReviewApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, DeleteReview.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "bookingID", scope = GetBooking.class)
    public JAXBElement<String> createGetBookingBookingID(String value) {
        return new JAXBElement<String>(_CancelBookingBookingID_QNAME, String.class, GetBooking.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = GetBooking.class)
    public JAXBElement<String> createGetBookingApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, GetBooking.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = GetPropertyIDs.class)
    public JAXBElement<String> createGetPropertyIDsApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, GetPropertyIDs.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "GetPropertyCategoriesResult", scope = GetPropertyCategoriesResponse.class)
    public JAXBElement<ArrayOfstring> createGetPropertyCategoriesResponseGetPropertyCategoriesResult(ArrayOfstring value) {
        return new JAXBElement<ArrayOfstring>(_GetPropertyCategoriesResponseGetPropertyCategoriesResult_QNAME, ArrayOfstring.class, GetPropertyCategoriesResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Property }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "GetPropertyResult", scope = GetPropertyResponse.class)
    public JAXBElement<Property> createGetPropertyResponseGetPropertyResult(Property value) {
        return new JAXBElement<Property>(_GetPropertyResponseGetPropertyResult_QNAME, Property.class, GetPropertyResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfint }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "GetPropertyIDsByCategoryResult", scope = GetPropertyIDsByCategoryResponse.class)
    public JAXBElement<ArrayOfint> createGetPropertyIDsByCategoryResponseGetPropertyIDsByCategoryResult(ArrayOfint value) {
        return new JAXBElement<ArrayOfint>(_GetPropertyIDsByCategoryResponseGetPropertyIDsByCategoryResult_QNAME, ArrayOfint.class, GetPropertyIDsByCategoryResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "rateType", scope = GetPerRoomRates.class)
    public JAXBElement<String> createGetPerRoomRatesRateType(String value) {
        return new JAXBElement<String>(_GetPerRoomRatesRateType_QNAME, String.class, GetPerRoomRates.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = GetPerRoomRates.class)
    public JAXBElement<String> createGetPerRoomRatesApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, GetPerRoomRates.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "currency", scope = GetPerRoomRates.class)
    public JAXBElement<String> createGetPerRoomRatesCurrency(String value) {
        return new JAXBElement<String>(_GetPerRoomRatesCurrency_QNAME, String.class, GetPerRoomRates.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Event }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "GetEventResult", scope = GetEventResponse.class)
    public JAXBElement<Event> createGetEventResponseGetEventResult(Event value) {
        return new JAXBElement<Event>(_GetEventResponseGetEventResult_QNAME, Event.class, GetEventResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Event }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "CreateEventResult", scope = CreateEventResponse.class)
    public JAXBElement<Event> createCreateEventResponseCreateEventResult(Event value) {
        return new JAXBElement<Event>(_CreateEventResponseCreateEventResult_QNAME, Event.class, CreateEventResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Person }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "GetLeadResult", scope = GetLeadResponse.class)
    public JAXBElement<Person> createGetLeadResponseGetLeadResult(Person value) {
        return new JAXBElement<Person>(_GetLeadResponseGetLeadResult_QNAME, Person.class, GetLeadResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "propertyCategory", scope = GetPropertyIDsByCategory.class)
    public JAXBElement<String> createGetPropertyIDsByCategoryPropertyCategory(String value) {
        return new JAXBElement<String>(_GetPropertyIDsByCategoryPropertyCategory_QNAME, String.class, GetPropertyIDsByCategory.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = GetPropertyIDsByCategory.class)
    public JAXBElement<String> createGetPropertyIDsByCategoryApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, GetPropertyIDsByCategory.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "GetEventCategoriesResult", scope = GetEventCategoriesResponse.class)
    public JAXBElement<ArrayOfstring> createGetEventCategoriesResponseGetEventCategoriesResult(ArrayOfstring value) {
        return new JAXBElement<ArrayOfstring>(_GetEventCategoriesResponseGetEventCategoriesResult_QNAME, ArrayOfstring.class, GetEventCategoriesResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfRate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "rates", scope = SetPerRoomRates.class)
    public JAXBElement<ArrayOfRate> createSetPerRoomRatesRates(ArrayOfRate value) {
        return new JAXBElement<ArrayOfRate>(_SetPerRoomRatesRates_QNAME, ArrayOfRate.class, SetPerRoomRates.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = SetPerRoomRates.class)
    public JAXBElement<String> createSetPerRoomRatesApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, SetPerRoomRates.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Fee.xsd", name = "Type", scope = Fee.class)
    public JAXBElement<String> createFeeType(String value) {
        return new JAXBElement<String>(_FeeType_QNAME, String.class, Fee.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Fee.xsd", name = "Status", scope = Fee.class)
    public JAXBElement<String> createFeeStatus(String value) {
        return new JAXBElement<String>(_FeeStatus_QNAME, String.class, Fee.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Fee.xsd", name = "Notes", scope = Fee.class)
    public JAXBElement<String> createFeeNotes(String value) {
        return new JAXBElement<String>(_FeeNotes_QNAME, String.class, Fee.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Fee.xsd", name = "Name", scope = Fee.class)
    public JAXBElement<String> createFeeName(String value) {
        return new JAXBElement<String>(_FeeName_QNAME, String.class, Fee.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Fee.xsd", name = "Currency", scope = Fee.class)
    public JAXBElement<String> createFeeCurrency(String value) {
        return new JAXBElement<String>(_FeeCurrency_QNAME, String.class, Fee.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Review }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "CreateReviewResult", scope = CreateReviewResponse.class)
    public JAXBElement<Review> createCreateReviewResponseCreateReviewResult(Review value) {
        return new JAXBElement<Review>(_CreateReviewResponseCreateReviewResult_QNAME, Review.class, CreateReviewResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "rateType", scope = GetRates.class)
    public JAXBElement<String> createGetRatesRateType(String value) {
        return new JAXBElement<String>(_GetPerRoomRatesRateType_QNAME, String.class, GetRates.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = GetRates.class)
    public JAXBElement<String> createGetRatesApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, GetRates.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "currency", scope = GetRates.class)
    public JAXBElement<String> createGetRatesCurrency(String value) {
        return new JAXBElement<String>(_GetPerRoomRatesCurrency_QNAME, String.class, GetRates.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Event }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "eventInfo", scope = CreateEvent.class)
    public JAXBElement<Event> createCreateEventEventInfo(Event value) {
        return new JAXBElement<Event>(_CreateEventEventInfo_QNAME, Event.class, CreateEvent.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = CreateEvent.class)
    public JAXBElement<String> createCreateEventApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, CreateEvent.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Tax.xsd", name = "Currency", scope = Tax.class)
    public JAXBElement<String> createTaxCurrency(String value) {
        return new JAXBElement<String>(_TaxCurrency_QNAME, String.class, Tax.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Tax.xsd", name = "Type", scope = Tax.class)
    public JAXBElement<String> createTaxType(String value) {
        return new JAXBElement<String>(_TaxType_QNAME, String.class, Tax.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Tax.xsd", name = "Status", scope = Tax.class)
    public JAXBElement<String> createTaxStatus(String value) {
        return new JAXBElement<String>(_TaxStatus_QNAME, String.class, Tax.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Tax.xsd", name = "Notes", scope = Tax.class)
    public JAXBElement<String> createTaxNotes(String value) {
        return new JAXBElement<String>(_TaxNotes_QNAME, String.class, Tax.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Tax.xsd", name = "Name", scope = Tax.class)
    public JAXBElement<String> createTaxName(String value) {
        return new JAXBElement<String>(_TaxName_QNAME, String.class, Tax.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfKeyValueOfdateTimedecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "GetRatesResult", scope = GetRatesResponse.class)
    public JAXBElement<ArrayOfKeyValueOfdateTimedecimal> createGetRatesResponseGetRatesResult(ArrayOfKeyValueOfdateTimedecimal value) {
        return new JAXBElement<ArrayOfKeyValueOfdateTimedecimal>(_GetRatesResponseGetRatesResult_QNAME, ArrayOfKeyValueOfdateTimedecimal.class, GetRatesResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "ID", scope = GetReview.class)
    public JAXBElement<String> createGetReviewID(String value) {
        return new JAXBElement<String>(_GetPropertyID_QNAME, String.class, GetReview.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = GetReview.class)
    public JAXBElement<String> createGetReviewApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, GetReview.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "ID", scope = GetLead.class)
    public JAXBElement<String> createGetLeadID(String value) {
        return new JAXBElement<String>(_GetPropertyID_QNAME, String.class, GetLead.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = GetLead.class)
    public JAXBElement<String> createGetLeadApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, GetLead.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Booking }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "GetBookingResult", scope = GetBookingResponse.class)
    public JAXBElement<Booking> createGetBookingResponseGetBookingResult(Booking value) {
        return new JAXBElement<Booking>(_GetBookingResponseGetBookingResult_QNAME, Booking.class, GetBookingResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfKeyValueOfdateTimeint }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "GetAvailabilityResult", scope = GetAvailabilityResponse.class)
    public JAXBElement<ArrayOfKeyValueOfdateTimeint> createGetAvailabilityResponseGetAvailabilityResult(ArrayOfKeyValueOfdateTimeint value) {
        return new JAXBElement<ArrayOfKeyValueOfdateTimeint>(_GetAvailabilityResponseGetAvailabilityResult_QNAME, ArrayOfKeyValueOfdateTimeint.class, GetAvailabilityResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Statement.xsd", name = "Notes", scope = Statement.class)
    public JAXBElement<String> createStatementNotes(String value) {
        return new JAXBElement<String>(_StatementNotes_QNAME, String.class, Statement.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Statement.xsd", name = "Status", scope = Statement.class)
    public JAXBElement<String> createStatementStatus(String value) {
        return new JAXBElement<String>(_StatementStatus_QNAME, String.class, Statement.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Statement.xsd", name = "Description", scope = Statement.class)
    public JAXBElement<String> createStatementDescription(String value) {
        return new JAXBElement<String>(_StatementDescription_QNAME, String.class, Statement.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Statement.xsd", name = "Type", scope = Statement.class)
    public JAXBElement<String> createStatementType(String value) {
        return new JAXBElement<String>(_StatementType_QNAME, String.class, Statement.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfStatementDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Statement.xsd", name = "Details", scope = Statement.class)
    public JAXBElement<ArrayOfStatementDetail> createStatementDetails(ArrayOfStatementDetail value) {
        return new JAXBElement<ArrayOfStatementDetail>(_StatementDetails_QNAME, ArrayOfStatementDetail.class, Statement.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Statement.xsd", name = "Currency", scope = Statement.class)
    public JAXBElement<String> createStatementCurrency(String value) {
        return new JAXBElement<String>(_StatementCurrency_QNAME, String.class, Statement.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Review }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "review", scope = CreateReview.class)
    public JAXBElement<Review> createCreateReviewReview(Review value) {
        return new JAXBElement<Review>(_CreateReviewReview_QNAME, Review.class, CreateReview.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = CreateReview.class)
    public JAXBElement<String> createCreateReviewApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, CreateReview.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Media.xsd", name = "ThumbnailURL", scope = Media.class)
    public JAXBElement<String> createMediaThumbnailURL(String value) {
        return new JAXBElement<String>(_MediaThumbnailURL_QNAME, String.class, Media.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Media.xsd", name = "MediaName", scope = Media.class)
    public JAXBElement<String> createMediaMediaName(String value) {
        return new JAXBElement<String>(_MediaMediaName_QNAME, String.class, Media.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Media.xsd", name = "Type", scope = Media.class)
    public JAXBElement<String> createMediaType(String value) {
        return new JAXBElement<String>(_MediaType_QNAME, String.class, Media.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Media.xsd", name = "Status", scope = Media.class)
    public JAXBElement<String> createMediaStatus(String value) {
        return new JAXBElement<String>(_MediaStatus_QNAME, String.class, Media.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Media.xsd", name = "MediumURL", scope = Media.class)
    public JAXBElement<String> createMediaMediumURL(String value) {
        return new JAXBElement<String>(_MediaMediumURL_QNAME, String.class, Media.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Media.xsd", name = "Caption", scope = Media.class)
    public JAXBElement<String> createMediaCaption(String value) {
        return new JAXBElement<String>(_MediaCaption_QNAME, String.class, Media.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Media.xsd", name = "OriginalURL", scope = Media.class)
    public JAXBElement<String> createMediaOriginalURL(String value) {
        return new JAXBElement<String>(_MediaOriginalURL_QNAME, String.class, Media.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfint }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "numAvailabile", scope = SetRatesAndAvailability.class)
    public JAXBElement<ArrayOfint> createSetRatesAndAvailabilityNumAvailabile(ArrayOfint value) {
        return new JAXBElement<ArrayOfint>(_SetAvailabilityNumAvailabile_QNAME, ArrayOfint.class, SetRatesAndAvailability.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "rateType", scope = SetRatesAndAvailability.class)
    public JAXBElement<String> createSetRatesAndAvailabilityRateType(String value) {
        return new JAXBElement<String>(_GetPerRoomRatesRateType_QNAME, String.class, SetRatesAndAvailability.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfdateTime }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "effDates", scope = SetRatesAndAvailability.class)
    public JAXBElement<ArrayOfdateTime> createSetRatesAndAvailabilityEffDates(ArrayOfdateTime value) {
        return new JAXBElement<ArrayOfdateTime>(_SetAvailabilityEffDates_QNAME, ArrayOfdateTime.class, SetRatesAndAvailability.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfdecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "rates", scope = SetRatesAndAvailability.class)
    public JAXBElement<ArrayOfdecimal> createSetRatesAndAvailabilityRates(ArrayOfdecimal value) {
        return new JAXBElement<ArrayOfdecimal>(_SetPerRoomRatesRates_QNAME, ArrayOfdecimal.class, SetRatesAndAvailability.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = SetRatesAndAvailability.class)
    public JAXBElement<String> createSetRatesAndAvailabilityApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, SetRatesAndAvailability.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "currency", scope = SetRatesAndAvailability.class)
    public JAXBElement<String> createSetRatesAndAvailabilityCurrency(String value) {
        return new JAXBElement<String>(_GetPerRoomRatesCurrency_QNAME, String.class, SetRatesAndAvailability.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Review.xsd", name = "Title", scope = Review.class)
    public JAXBElement<String> createReviewTitle(String value) {
        return new JAXBElement<String>(_ReviewTitle_QNAME, String.class, Review.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Person }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Review.xsd", name = "ReviewedBy", scope = Review.class)
    public JAXBElement<Person> createReviewReviewedBy(Person value) {
        return new JAXBElement<Person>(_ReviewReviewedBy_QNAME, Person.class, Review.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Review.xsd", name = "Response", scope = Review.class)
    public JAXBElement<String> createReviewResponse(String value) {
        return new JAXBElement<String>(_ReviewResponse_QNAME, String.class, Review.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Review.xsd", name = "Comment", scope = Review.class)
    public JAXBElement<String> createReviewComment(String value) {
        return new JAXBElement<String>(_ReviewComment_QNAME, String.class, Review.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Review.xsd", name = "ReviewedEntityID", scope = Review.class)
    public JAXBElement<String> createReviewReviewedEntityID(String value) {
        return new JAXBElement<String>(_ReviewReviewedEntityID_QNAME, String.class, Review.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Review.xsd", name = "AltID", scope = Review.class)
    public JAXBElement<String> createReviewAltID(String value) {
        return new JAXBElement<String>(_ReviewAltID_QNAME, String.class, Review.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Review.xsd", name = "Type", scope = Review.class)
    public JAXBElement<String> createReviewType(String value) {
        return new JAXBElement<String>(_ReviewType_QNAME, String.class, Review.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Review.xsd", name = "Status", scope = Review.class)
    public JAXBElement<String> createReviewStatus(String value) {
        return new JAXBElement<String>(_ReviewStatus_QNAME, String.class, Review.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Review.xsd", name = "Source", scope = Review.class)
    public JAXBElement<String> createReviewSource(String value) {
        return new JAXBElement<String>(_ReviewSource_QNAME, String.class, Review.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Review.xsd", name = "ExternalLink", scope = Review.class)
    public JAXBElement<String> createReviewExternalLink(String value) {
        return new JAXBElement<String>(_ReviewExternalLink_QNAME, String.class, Review.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Booking }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "MakeBookingResult", scope = MakeBookingResponse.class)
    public JAXBElement<Booking> createMakeBookingResponseMakeBookingResult(Booking value) {
        return new JAXBElement<Booking>(_MakeBookingResponseMakeBookingResult_QNAME, Booking.class, MakeBookingResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Booking.xsd", name = "Type", scope = Booking.class)
    public JAXBElement<String> createBookingType(String value) {
        return new JAXBElement<String>(_BookingType_QNAME, String.class, Booking.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Statement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Booking.xsd", name = "Statement", scope = Booking.class)
    public JAXBElement<Statement> createBookingStatement(Statement value) {
        return new JAXBElement<Statement>(_BookingStatement_QNAME, Statement.class, Booking.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Person }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Booking.xsd", name = "BookedBy", scope = Booking.class)
    public JAXBElement<Person> createBookingBookedBy(Person value) {
        return new JAXBElement<Person>(_BookingBookedBy_QNAME, Person.class, Booking.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Booking.xsd", name = "AltID", scope = Booking.class)
    public JAXBElement<String> createBookingAltID(String value) {
        return new JAXBElement<String>(_BookingAltID_QNAME, String.class, Booking.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Booking.xsd", name = "Status", scope = Booking.class)
    public JAXBElement<String> createBookingStatus(String value) {
        return new JAXBElement<String>(_BookingStatus_QNAME, String.class, Booking.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Person }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Booking.xsd", name = "Renter", scope = Booking.class)
    public JAXBElement<Person> createBookingRenter(Person value) {
        return new JAXBElement<Person>(_BookingRenter_QNAME, Person.class, Booking.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Booking.xsd", name = "PaymentTerms", scope = Booking.class)
    public JAXBElement<String> createBookingPaymentTerms(String value) {
        return new JAXBElement<String>(_BookingPaymentTerms_QNAME, String.class, Booking.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Booking.xsd", name = "PublicNotes", scope = Booking.class)
    public JAXBElement<String> createBookingPublicNotes(String value) {
        return new JAXBElement<String>(_BookingPublicNotes_QNAME, String.class, Booking.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreditCard }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Booking.xsd", name = "CreditCard", scope = Booking.class)
    public JAXBElement<CreditCard> createBookingCreditCard(CreditCard value) {
        return new JAXBElement<CreditCard>(_BookingCreditCard_QNAME, CreditCard.class, Booking.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Booking.xsd", name = "PrivateNotes", scope = Booking.class)
    public JAXBElement<String> createBookingPrivateNotes(String value) {
        return new JAXBElement<String>(_BookingPrivateNotes_QNAME, String.class, Booking.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = GetPropertyCategories.class)
    public JAXBElement<String> createGetPropertyCategoriesApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, GetPropertyCategories.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Booking }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "booking", scope = MakeBooking.class)
    public JAXBElement<Booking> createMakeBookingBooking(Booking value) {
        return new JAXBElement<Booking>(_MakeBookingBooking_QNAME, Booking.class, MakeBooking.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = MakeBooking.class)
    public JAXBElement<String> createMakeBookingApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, MakeBooking.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "rateType", scope = SetRates.class)
    public JAXBElement<String> createSetRatesRateType(String value) {
        return new JAXBElement<String>(_GetPerRoomRatesRateType_QNAME, String.class, SetRates.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfdateTime }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "effDates", scope = SetRates.class)
    public JAXBElement<ArrayOfdateTime> createSetRatesEffDates(ArrayOfdateTime value) {
        return new JAXBElement<ArrayOfdateTime>(_SetAvailabilityEffDates_QNAME, ArrayOfdateTime.class, SetRates.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfdecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "rates", scope = SetRates.class)
    public JAXBElement<ArrayOfdecimal> createSetRatesRates(ArrayOfdecimal value) {
        return new JAXBElement<ArrayOfdecimal>(_SetPerRoomRatesRates_QNAME, ArrayOfdecimal.class, SetRates.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = SetRates.class)
    public JAXBElement<String> createSetRatesApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, SetRates.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "currency", scope = SetRates.class)
    public JAXBElement<String> createSetRatesCurrency(String value) {
        return new JAXBElement<String>(_GetPerRoomRatesCurrency_QNAME, String.class, SetRates.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Booking }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "CancelBookingResult", scope = CancelBookingResponse.class)
    public JAXBElement<Booking> createCancelBookingResponseCancelBookingResult(Booking value) {
        return new JAXBElement<Booking>(_CancelBookingResponseCancelBookingResult_QNAME, Booking.class, CancelBookingResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfKeyValueOfstringstring }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "GetBusinessRulesResult", scope = GetBusinessRulesResponse.class)
    public JAXBElement<ArrayOfKeyValueOfstringstring> createGetBusinessRulesResponseGetBusinessRulesResult(ArrayOfKeyValueOfstringstring value) {
        return new JAXBElement<ArrayOfKeyValueOfstringstring>(_GetBusinessRulesResponseGetBusinessRulesResult_QNAME, ArrayOfKeyValueOfstringstring.class, GetBusinessRulesResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/CreditCard.xsd", name = "Address", scope = CreditCard.class)
    public JAXBElement<String> createCreditCardAddress(String value) {
        return new JAXBElement<String>(_CreditCardAddress_QNAME, String.class, CreditCard.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/CreditCard.xsd", name = "Type", scope = CreditCard.class)
    public JAXBElement<String> createCreditCardType(String value) {
        return new JAXBElement<String>(_CreditCardType_QNAME, String.class, CreditCard.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/CreditCard.xsd", name = "NameOnCard", scope = CreditCard.class)
    public JAXBElement<String> createCreditCardNameOnCard(String value) {
        return new JAXBElement<String>(_CreditCardNameOnCard_QNAME, String.class, CreditCard.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/CreditCard.xsd", name = "State", scope = CreditCard.class)
    public JAXBElement<String> createCreditCardState(String value) {
        return new JAXBElement<String>(_CreditCardState_QNAME, String.class, CreditCard.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/CreditCard.xsd", name = "PostalCode", scope = CreditCard.class)
    public JAXBElement<String> createCreditCardPostalCode(String value) {
        return new JAXBElement<String>(_CreditCardPostalCode_QNAME, String.class, CreditCard.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/CreditCard.xsd", name = "CardNumber", scope = CreditCard.class)
    public JAXBElement<String> createCreditCardCardNumber(String value) {
        return new JAXBElement<String>(_CreditCardCardNumber_QNAME, String.class, CreditCard.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/CreditCard.xsd", name = "City", scope = CreditCard.class)
    public JAXBElement<String> createCreditCardCity(String value) {
        return new JAXBElement<String>(_CreditCardCity_QNAME, String.class, CreditCard.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/CreditCard.xsd", name = "SecurityCode", scope = CreditCard.class)
    public JAXBElement<String> createCreditCardSecurityCode(String value) {
        return new JAXBElement<String>(_CreditCardSecurityCode_QNAME, String.class, CreditCard.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfKeyValueOfdateTimedecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "GetPerRoomRatesResult", scope = GetPerRoomRatesResponse.class)
    public JAXBElement<ArrayOfKeyValueOfdateTimedecimal> createGetPerRoomRatesResponseGetPerRoomRatesResult(ArrayOfKeyValueOfdateTimedecimal value) {
        return new JAXBElement<ArrayOfKeyValueOfdateTimedecimal>(_GetPerRoomRatesResponseGetPerRoomRatesResult_QNAME, ArrayOfKeyValueOfdateTimedecimal.class, GetPerRoomRatesResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfint }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "GetPropertyIDsResult", scope = GetPropertyIDsResponse.class)
    public JAXBElement<ArrayOfint> createGetPropertyIDsResponseGetPropertyIDsResult(ArrayOfint value) {
        return new JAXBElement<ArrayOfint>(_GetPropertyIDsResponseGetPropertyIDsResult_QNAME, ArrayOfint.class, GetPropertyIDsResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Booking }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "booking", scope = ModifyBooking.class)
    public JAXBElement<Booking> createModifyBookingBooking(Booking value) {
        return new JAXBElement<Booking>(_MakeBookingBooking_QNAME, Booking.class, ModifyBooking.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = ModifyBooking.class)
    public JAXBElement<String> createModifyBookingApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, ModifyBooking.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "Type", scope = Person.class)
    public JAXBElement<String> createPersonType(String value) {
        return new JAXBElement<String>(_PersonType_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "WorkPhone", scope = Person.class)
    public JAXBElement<String> createPersonWorkPhone(String value) {
        return new JAXBElement<String>(_PersonWorkPhone_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "LastName", scope = Person.class)
    public JAXBElement<String> createPersonLastName(String value) {
        return new JAXBElement<String>(_PersonLastName_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "HomePhone", scope = Person.class)
    public JAXBElement<String> createPersonHomePhone(String value) {
        return new JAXBElement<String>(_PersonHomePhone_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "LeadSource", scope = Person.class)
    public JAXBElement<String> createPersonLeadSource(String value) {
        return new JAXBElement<String>(_PersonLeadSource_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "Address2", scope = Person.class)
    public JAXBElement<String> createPersonAddress2(String value) {
        return new JAXBElement<String>(_PersonAddress2_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "AltID", scope = Person.class)
    public JAXBElement<String> createPersonAltID(String value) {
        return new JAXBElement<String>(_PersonAltID_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "FaxNumber", scope = Person.class)
    public JAXBElement<String> createPersonFaxNumber(String value) {
        return new JAXBElement<String>(_PersonFaxNumber_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "Address1", scope = Person.class)
    public JAXBElement<String> createPersonAddress1(String value) {
        return new JAXBElement<String>(_PersonAddress1_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "Greeting", scope = Person.class)
    public JAXBElement<String> createPersonGreeting(String value) {
        return new JAXBElement<String>(_PersonGreeting_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "Initial", scope = Person.class)
    public JAXBElement<String> createPersonInitial(String value) {
        return new JAXBElement<String>(_PersonInitial_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "Company", scope = Person.class)
    public JAXBElement<String> createPersonCompany(String value) {
        return new JAXBElement<String>(_PersonCompany_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "PrimaryEmail", scope = Person.class)
    public JAXBElement<String> createPersonPrimaryEmail(String value) {
        return new JAXBElement<String>(_PersonPrimaryEmail_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "FirstName", scope = Person.class)
    public JAXBElement<String> createPersonFirstName(String value) {
        return new JAXBElement<String>(_PersonFirstName_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "Suffix", scope = Person.class)
    public JAXBElement<String> createPersonSuffix(String value) {
        return new JAXBElement<String>(_PersonSuffix_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "Country", scope = Person.class)
    public JAXBElement<String> createPersonCountry(String value) {
        return new JAXBElement<String>(_PersonCountry_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "City", scope = Person.class)
    public JAXBElement<String> createPersonCity(String value) {
        return new JAXBElement<String>(_PersonCity_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "Title", scope = Person.class)
    public JAXBElement<String> createPersonTitle(String value) {
        return new JAXBElement<String>(_PersonTitle_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "WebSite", scope = Person.class)
    public JAXBElement<String> createPersonWebSite(String value) {
        return new JAXBElement<String>(_PersonWebSite_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "Prefix", scope = Person.class)
    public JAXBElement<String> createPersonPrefix(String value) {
        return new JAXBElement<String>(_PersonPrefix_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "CellPhone", scope = Person.class)
    public JAXBElement<String> createPersonCellPhone(String value) {
        return new JAXBElement<String>(_PersonCellPhone_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "State", scope = Person.class)
    public JAXBElement<String> createPersonState(String value) {
        return new JAXBElement<String>(_PersonState_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "Status", scope = Person.class)
    public JAXBElement<String> createPersonStatus(String value) {
        return new JAXBElement<String>(_PersonStatus_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "Tags", scope = Person.class)
    public JAXBElement<ArrayOfstring> createPersonTags(ArrayOfstring value) {
        return new JAXBElement<ArrayOfstring>(_PersonTags_QNAME, ArrayOfstring.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "Notes", scope = Person.class)
    public JAXBElement<String> createPersonNotes(String value) {
        return new JAXBElement<String>(_PersonNotes_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Person.xsd", name = "PostalCode", scope = Person.class)
    public JAXBElement<String> createPersonPostalCode(String value) {
        return new JAXBElement<String>(_PersonPostalCode_QNAME, String.class, Person.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Unit.xsd", name = "UnitName", scope = Unit.class)
    public JAXBElement<String> createUnitUnitName(String value) {
        return new JAXBElement<String>(_UnitUnitName_QNAME, String.class, Unit.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Unit.xsd", name = "PropertyUnitID", scope = Unit.class)
    public JAXBElement<String> createUnitPropertyUnitID(String value) {
        return new JAXBElement<String>(_UnitPropertyUnitID_QNAME, String.class, Unit.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Unit.xsd", name = "Status", scope = Unit.class)
    public JAXBElement<String> createUnitStatus(String value) {
        return new JAXBElement<String>(_UnitStatus_QNAME, String.class, Unit.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "rateType", scope = GetAvailability.class)
    public JAXBElement<String> createGetAvailabilityRateType(String value) {
        return new JAXBElement<String>(_GetPerRoomRatesRateType_QNAME, String.class, GetAvailability.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = GetAvailability.class)
    public JAXBElement<String> createGetAvailabilityApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, GetAvailability.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Rate.xsd", name = "Currency", scope = Rate.class)
    public JAXBElement<String> createRateCurrency(String value) {
        return new JAXBElement<String>(_RateCurrency_QNAME, String.class, Rate.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/Rate.xsd", name = "RateType", scope = Rate.class)
    public JAXBElement<String> createRateRateType(String value) {
        return new JAXBElement<String>(_RateRateType_QNAME, String.class, Rate.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "reviewType", scope = GetReviews.class)
    public JAXBElement<String> createGetReviewsReviewType(String value) {
        return new JAXBElement<String>(_GetReviewIDsReviewType_QNAME, String.class, GetReviews.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "ID", scope = GetReviews.class)
    public JAXBElement<String> createGetReviewsID(String value) {
        return new JAXBElement<String>(_GetPropertyID_QNAME, String.class, GetReviews.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = GetReviews.class)
    public JAXBElement<String> createGetReviewsApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, GetReviews.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = GetBusinessRules.class)
    public JAXBElement<String> createGetBusinessRulesApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, GetBusinessRules.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "ID", scope = GetEvent.class)
    public JAXBElement<String> createGetEventID(String value) {
        return new JAXBElement<String>(_GetPropertyID_QNAME, String.class, GetEvent.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "apiKey", scope = GetEvent.class)
    public JAXBElement<String> createGetEventApiKey(String value) {
        return new JAXBElement<String>(_GetPropertyApiKey_QNAME, String.class, GetEvent.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Booking }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://connect.bookt.com/connect", name = "ModifyBookingResult", scope = ModifyBookingResponse.class)
    public JAXBElement<Booking> createModifyBookingResponseModifyBookingResult(Booking value) {
        return new JAXBElement<Booking>(_ModifyBookingResponseModifyBookingResult_QNAME, Booking.class, ModifyBookingResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/StatementDetail.xsd", name = "Currency", scope = StatementDetail.class)
    public JAXBElement<String> createStatementDetailCurrency(String value) {
        return new JAXBElement<String>(_StatementDetailCurrency_QNAME, String.class, StatementDetail.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/StatementDetail.xsd", name = "Type", scope = StatementDetail.class)
    public JAXBElement<String> createStatementDetailType(String value) {
        return new JAXBElement<String>(_StatementDetailType_QNAME, String.class, StatementDetail.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/StatementDetail.xsd", name = "Notes", scope = StatementDetail.class)
    public JAXBElement<String> createStatementDetailNotes(String value) {
        return new JAXBElement<String>(_StatementDetailNotes_QNAME, String.class, StatementDetail.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://connect.bookt.com/Schemas/StatementDetail.xsd", name = "Status", scope = StatementDetail.class)
    public JAXBElement<String> createStatementDetailStatus(String value) {
        return new JAXBElement<String>(_StatementDetailStatus_QNAME, String.class, StatementDetail.class, value);
    }

}
