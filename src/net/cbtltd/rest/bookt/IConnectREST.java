package net.cbtltd.rest.bookt;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.4.0
 * 2013-01-22T19:00:24.834+02:00
 * Generated source version: 2.4.0
 * 
 */
 
@WebService(targetNamespace = "https://connect.bookt.com/connect", name = "IConnectREST")
@XmlSeeAlso({ObjectFactory.class})
public interface IConnectREST {

    @WebResult(name = "CreateReviewResult", targetNamespace = "https://connect.bookt.com/connect")
    @Action(input = "https://connect.bookt.com/connect/IConnectREST/CreateReview", output = "https://connect.bookt.com/connect/IConnectREST/CreateReviewResponse")
    @RequestWrapper(localName = "CreateReview", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.CreateReview")
    @WebMethod(operationName = "CreateReview", action = "https://connect.bookt.com/connect/IConnectREST/CreateReview")
    @ResponseWrapper(localName = "CreateReviewResponse", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.CreateReviewResponse")
    public net.cbtltd.rest.bookt.Review createReview(
        @WebParam(name = "apiKey", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String apiKey,
        @WebParam(name = "review", targetNamespace = "https://connect.bookt.com/connect")
        net.cbtltd.rest.bookt.Review review
    );

    @WebResult(name = "GetReviewsResult", targetNamespace = "https://connect.bookt.com/connect")
    @Action(input = "https://connect.bookt.com/connect/IConnectREST/GetReviews", output = "https://connect.bookt.com/connect/IConnectREST/GetReviewsResponse")
    @RequestWrapper(localName = "GetReviews", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetReviews")
    @WebMethod(operationName = "GetReviews", action = "https://connect.bookt.com/connect/IConnectREST/GetReviews")
    @ResponseWrapper(localName = "GetReviewsResponse", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetReviewsResponse")
    public net.cbtltd.rest.bookt.ArrayOfReview getReviews(
        @WebParam(name = "apiKey", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String apiKey,
        @WebParam(name = "ID", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String id,
        @WebParam(name = "reviewType", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String reviewType,
        @WebParam(name = "useInternalID", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.Boolean useInternalID
    );

    @WebResult(name = "GetReviewIDsResult", targetNamespace = "https://connect.bookt.com/connect")
    @Action(input = "https://connect.bookt.com/connect/IConnectREST/GetReviewIDs", output = "https://connect.bookt.com/connect/IConnectREST/GetReviewIDsResponse")
    @RequestWrapper(localName = "GetReviewIDs", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetReviewIDs")
    @WebMethod(operationName = "GetReviewIDs", action = "https://connect.bookt.com/connect/IConnectREST/GetReviewIDs")
    @ResponseWrapper(localName = "GetReviewIDsResponse", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetReviewIDsResponse")
    public net.cbtltd.rest.bookt.ArrayOfstring getReviewIDs(
        @WebParam(name = "apiKey", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String apiKey,
        @WebParam(name = "reviewType", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String reviewType,
        @WebParam(name = "lastMod", targetNamespace = "https://connect.bookt.com/connect")
        javax.xml.datatype.XMLGregorianCalendar lastMod,
        @WebParam(name = "useInternalID", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.Boolean useInternalID
    );

    @WebResult(name = "CancelBookingResult", targetNamespace = "https://connect.bookt.com/connect")
    @Action(input = "https://connect.bookt.com/connect/IConnectREST/CancelBooking", output = "https://connect.bookt.com/connect/IConnectREST/CancelBookingResponse")
    @RequestWrapper(localName = "CancelBooking", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.CancelBooking")
    @WebMethod(operationName = "CancelBooking", action = "https://connect.bookt.com/connect/IConnectREST/CancelBooking")
    @ResponseWrapper(localName = "CancelBookingResponse", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.CancelBookingResponse")
    public net.cbtltd.rest.bookt.Booking cancelBooking(
        @WebParam(name = "apiKey", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String apiKey,
        @WebParam(name = "bookingID", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String bookingID,
        @WebParam(name = "useInternalID", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.Boolean useInternalID,
        @WebParam(name = "refundAmount", targetNamespace = "https://connect.bookt.com/connect")
        java.math.BigDecimal refundAmount,
        @WebParam(name = "options", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.Integer options
    );

    @WebResult(name = "GetBusinessRulesResult", targetNamespace = "https://connect.bookt.com/connect")
    @Action(input = "https://connect.bookt.com/connect/IConnectREST/GetBusinessRules", output = "https://connect.bookt.com/connect/IConnectREST/GetBusinessRulesResponse")
    @RequestWrapper(localName = "GetBusinessRules", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetBusinessRules")
    @WebMethod(operationName = "GetBusinessRules", action = "https://connect.bookt.com/connect/IConnectREST/GetBusinessRules")
    @ResponseWrapper(localName = "GetBusinessRulesResponse", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetBusinessRulesResponse")
    public net.cbtltd.rest.bookt.ArrayOfKeyValueOfstringstring getBusinessRules(
        @WebParam(name = "apiKey", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String apiKey
    );

    @Action(input = "https://connect.bookt.com/connect/IConnectREST/SetAvailability", output = "https://connect.bookt.com/connect/IConnectREST/SetAvailabilityResponse")
    @RequestWrapper(localName = "SetAvailability", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.SetAvailability")
    @WebMethod(operationName = "SetAvailability", action = "https://connect.bookt.com/connect/IConnectREST/SetAvailability")
    @ResponseWrapper(localName = "SetAvailabilityResponse", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.SetAvailabilityResponse")
    public void setAvailability(
        @WebParam(name = "apiKey", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String apiKey,
        @WebParam(name = "propertyID", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.Integer propertyID,
        @WebParam(name = "effDates", targetNamespace = "https://connect.bookt.com/connect")
        net.cbtltd.rest.bookt.ArrayOfdateTime effDates,
        @WebParam(name = "numAvailabile", targetNamespace = "https://connect.bookt.com/connect")
        net.cbtltd.rest.bookt.ArrayOfint numAvailabile
    );

    @WebResult(name = "CreateEventResult", targetNamespace = "https://connect.bookt.com/connect")
    @Action(input = "https://connect.bookt.com/connect/IConnectREST/CreateEvent", output = "https://connect.bookt.com/connect/IConnectREST/CreateEventResponse")
    @RequestWrapper(localName = "CreateEvent", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.CreateEvent")
    @WebMethod(operationName = "CreateEvent", action = "https://connect.bookt.com/connect/IConnectREST/CreateEvent")
    @ResponseWrapper(localName = "CreateEventResponse", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.CreateEventResponse")
    public net.cbtltd.rest.bookt.Event createEvent(
        @WebParam(name = "apiKey", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String apiKey,
        @WebParam(name = "eventInfo", targetNamespace = "https://connect.bookt.com/connect")
        net.cbtltd.rest.bookt.Event eventInfo,
        @WebParam(name = "mode", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.Integer mode
    );

    @Action(input = "https://connect.bookt.com/connect/IConnectREST/SetRatesAndAvailability", output = "https://connect.bookt.com/connect/IConnectREST/SetRatesAndAvailabilityResponse")
    @RequestWrapper(localName = "SetRatesAndAvailability", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.SetRatesAndAvailability")
    @WebMethod(operationName = "SetRatesAndAvailability", action = "https://connect.bookt.com/connect/IConnectREST/SetRatesAndAvailability")
    @ResponseWrapper(localName = "SetRatesAndAvailabilityResponse", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.SetRatesAndAvailabilityResponse")
    public void setRatesAndAvailability(
        @WebParam(name = "apiKey", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String apiKey,
        @WebParam(name = "propertyID", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.Integer propertyID,
        @WebParam(name = "rateType", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String rateType,
        @WebParam(name = "los", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.Integer los,
        @WebParam(name = "effDates", targetNamespace = "https://connect.bookt.com/connect")
        net.cbtltd.rest.bookt.ArrayOfdateTime effDates,
        @WebParam(name = "rates", targetNamespace = "https://connect.bookt.com/connect")
        net.cbtltd.rest.bookt.ArrayOfdecimal rates,
        @WebParam(name = "currency", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String currency,
        @WebParam(name = "numAvailabile", targetNamespace = "https://connect.bookt.com/connect")
        net.cbtltd.rest.bookt.ArrayOfint numAvailabile
    );

    @WebResult(name = "GetLeadResult", targetNamespace = "https://connect.bookt.com/connect")
    @Action(input = "https://connect.bookt.com/connect/IConnectREST/GetLead", output = "https://connect.bookt.com/connect/IConnectREST/GetLeadResponse")
    @RequestWrapper(localName = "GetLead", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetLead")
    @WebMethod(operationName = "GetLead", action = "https://connect.bookt.com/connect/IConnectREST/GetLead")
    @ResponseWrapper(localName = "GetLeadResponse", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetLeadResponse")
    public net.cbtltd.rest.bookt.Person getLead(
        @WebParam(name = "apiKey", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String apiKey,
        @WebParam(name = "ID", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String id,
        @WebParam(name = "useInternalID", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.Boolean useInternalID
    );

    @WebResult(name = "GetRatesResult", targetNamespace = "https://connect.bookt.com/connect")
    @Action(input = "https://connect.bookt.com/connect/IConnectREST/GetRates", output = "https://connect.bookt.com/connect/IConnectREST/GetRatesResponse")
    @RequestWrapper(localName = "GetRates", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetRates")
    @WebMethod(operationName = "GetRates", action = "https://connect.bookt.com/connect/IConnectREST/GetRates")
    @ResponseWrapper(localName = "GetRatesResponse", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetRatesResponse")
    public net.cbtltd.rest.bookt.ArrayOfKeyValueOfdateTimedecimal getRates(
        @WebParam(name = "apiKey", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String apiKey,
        @WebParam(name = "propertyID", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.Integer propertyID,
        @WebParam(name = "rateType", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String rateType,
        @WebParam(name = "los", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.Integer los,
        @WebParam(name = "startDate", targetNamespace = "https://connect.bookt.com/connect")
        javax.xml.datatype.XMLGregorianCalendar startDate,
        @WebParam(name = "endDate", targetNamespace = "https://connect.bookt.com/connect")
        javax.xml.datatype.XMLGregorianCalendar endDate,
        @WebParam(name = "currency", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String currency
    );

    @WebResult(name = "GetEventResult", targetNamespace = "https://connect.bookt.com/connect")
    @Action(input = "https://connect.bookt.com/connect/IConnectREST/GetEvent", output = "https://connect.bookt.com/connect/IConnectREST/GetEventResponse")
    @RequestWrapper(localName = "GetEvent", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetEvent")
    @WebMethod(operationName = "GetEvent", action = "https://connect.bookt.com/connect/IConnectREST/GetEvent")
    @ResponseWrapper(localName = "GetEventResponse", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetEventResponse")
    public net.cbtltd.rest.bookt.Event getEvent(
        @WebParam(name = "apiKey", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String apiKey,
        @WebParam(name = "ID", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String id,
        @WebParam(name = "useInternalID", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.Boolean useInternalID
    );

    @WebResult(name = "GetPropertyResult", targetNamespace = "https://connect.bookt.com/connect")
    @Action(input = "https://connect.bookt.com/connect/IConnectREST/GetProperty", output = "https://connect.bookt.com/connect/IConnectREST/GetPropertyResponse")
    @RequestWrapper(localName = "GetProperty", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetProperty")
    @WebMethod(operationName = "GetProperty", action = "https://connect.bookt.com/connect/IConnectREST/GetProperty")
    @ResponseWrapper(localName = "GetPropertyResponse", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetPropertyResponse")
    public net.cbtltd.rest.bookt.Property getProperty(
        @WebParam(name = "apiKey", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String apiKey,
        @WebParam(name = "ID", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String id,
        @WebParam(name = "useInternalID", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String useInternalID
    );

    @WebResult(name = "GetPropertyIDsResult", targetNamespace = "https://connect.bookt.com/connect")
    @Action(input = "https://connect.bookt.com/connect/IConnectREST/GetPropertyIDs", output = "https://connect.bookt.com/connect/IConnectREST/GetPropertyIDsResponse")
    @RequestWrapper(localName = "GetPropertyIDs", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetPropertyIDs")
    @WebMethod(operationName = "GetPropertyIDs", action = "https://connect.bookt.com/connect/IConnectREST/GetPropertyIDs")
    @ResponseWrapper(localName = "GetPropertyIDsResponse", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetPropertyIDsResponse")
    public net.cbtltd.rest.bookt.ArrayOfint getPropertyIDs(
        @WebParam(name = "apiKey", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String apiKey,
        @WebParam(name = "lastMod", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String lastMod
    );

    @WebResult(name = "GetAvailabilityResult", targetNamespace = "https://connect.bookt.com/connect")
    @Action(input = "https://connect.bookt.com/connect/IConnectREST/GetAvailability", output = "https://connect.bookt.com/connect/IConnectREST/GetAvailabilityResponse")
    @RequestWrapper(localName = "GetAvailability", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetAvailability")
    @WebMethod(operationName = "GetAvailability", action = "https://connect.bookt.com/connect/IConnectREST/GetAvailability")
    @ResponseWrapper(localName = "GetAvailabilityResponse", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetAvailabilityResponse")
    public net.cbtltd.rest.bookt.ArrayOfKeyValueOfdateTimeint getAvailability(
        @WebParam(name = "apiKey", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String apiKey,
        @WebParam(name = "propertyID", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.Integer propertyID,
        @WebParam(name = "rateType", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String rateType,
        @WebParam(name = "startDate", targetNamespace = "https://connect.bookt.com/connect")
        javax.xml.datatype.XMLGregorianCalendar startDate,
        @WebParam(name = "endDate", targetNamespace = "https://connect.bookt.com/connect")
        javax.xml.datatype.XMLGregorianCalendar endDate
    );

    @WebResult(name = "GetReviewResult", targetNamespace = "https://connect.bookt.com/connect")
    @Action(input = "https://connect.bookt.com/connect/IConnectREST/GetReview", output = "https://connect.bookt.com/connect/IConnectREST/GetReviewResponse")
    @RequestWrapper(localName = "GetReview", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetReview")
    @WebMethod(operationName = "GetReview", action = "https://connect.bookt.com/connect/IConnectREST/GetReview")
    @ResponseWrapper(localName = "GetReviewResponse", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetReviewResponse")
    public net.cbtltd.rest.bookt.Review getReview(
        @WebParam(name = "apiKey", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String apiKey,
        @WebParam(name = "ID", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String id,
        @WebParam(name = "useInternalID", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.Boolean useInternalID
    );

    @WebResult(name = "CreateLeadResult", targetNamespace = "https://connect.bookt.com/connect")
    @Action(input = "https://connect.bookt.com/connect/IConnectREST/CreateLead", output = "https://connect.bookt.com/connect/IConnectREST/CreateLeadResponse")
    @RequestWrapper(localName = "CreateLead", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.CreateLead")
    @WebMethod(operationName = "CreateLead", action = "https://connect.bookt.com/connect/IConnectREST/CreateLead")
    @ResponseWrapper(localName = "CreateLeadResponse", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.CreateLeadResponse")
    public net.cbtltd.rest.bookt.Person createLead(
        @WebParam(name = "apiKey", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String apiKey,
        @WebParam(name = "lead", targetNamespace = "https://connect.bookt.com/connect")
        net.cbtltd.rest.bookt.Person lead,
        @WebParam(name = "mode", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.Integer mode
    );

    @WebResult(name = "GetBookingResult", targetNamespace = "https://connect.bookt.com/connect")
    @Action(input = "https://connect.bookt.com/connect/IConnectREST/GetBooking", output = "https://connect.bookt.com/connect/IConnectREST/GetBookingResponse")
    @RequestWrapper(localName = "GetBooking", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetBooking")
    @WebMethod(operationName = "GetBooking", action = "https://connect.bookt.com/connect/IConnectREST/GetBooking")
    @ResponseWrapper(localName = "GetBookingResponse", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetBookingResponse")
    public net.cbtltd.rest.bookt.Booking getBooking(
        @WebParam(name = "apiKey", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String apiKey,
        @WebParam(name = "bookingID", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String bookingID,
        @WebParam(name = "useInternalID", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.Boolean useInternalID
    );

    @Action(input = "https://connect.bookt.com/connect/IConnectREST/SetRates", output = "https://connect.bookt.com/connect/IConnectREST/SetRatesResponse")
    @RequestWrapper(localName = "SetRates", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.SetRates")
    @WebMethod(operationName = "SetRates", action = "https://connect.bookt.com/connect/IConnectREST/SetRates")
    @ResponseWrapper(localName = "SetRatesResponse", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.SetRatesResponse")
    public void setRates(
        @WebParam(name = "apiKey", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String apiKey,
        @WebParam(name = "propertyID", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.Integer propertyID,
        @WebParam(name = "rateType", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String rateType,
        @WebParam(name = "los", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.Integer los,
        @WebParam(name = "effDates", targetNamespace = "https://connect.bookt.com/connect")
        net.cbtltd.rest.bookt.ArrayOfdateTime effDates,
        @WebParam(name = "rates", targetNamespace = "https://connect.bookt.com/connect")
        net.cbtltd.rest.bookt.ArrayOfdecimal rates,
        @WebParam(name = "currency", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String currency
    );

    @WebResult(name = "ModifyBookingResult", targetNamespace = "https://connect.bookt.com/connect")
    @Action(input = "https://connect.bookt.com/connect/IConnectREST/ModifyBooking", output = "https://connect.bookt.com/connect/IConnectREST/ModifyBookingResponse")
    @RequestWrapper(localName = "ModifyBooking", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.ModifyBooking")
    @WebMethod(operationName = "ModifyBooking", action = "https://connect.bookt.com/connect/IConnectREST/ModifyBooking")
    @ResponseWrapper(localName = "ModifyBookingResponse", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.ModifyBookingResponse")
    public net.cbtltd.rest.bookt.Booking modifyBooking(
        @WebParam(name = "apiKey", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String apiKey,
        @WebParam(name = "booking", targetNamespace = "https://connect.bookt.com/connect")
        net.cbtltd.rest.bookt.Booking booking,
        @WebParam(name = "infoOnly", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.Boolean infoOnly
    );

    @WebResult(name = "DeleteReviewResult", targetNamespace = "https://connect.bookt.com/connect")
    @Action(input = "https://connect.bookt.com/connect/IConnectREST/DeleteReview", output = "https://connect.bookt.com/connect/IConnectREST/DeleteReviewResponse")
    @RequestWrapper(localName = "DeleteReview", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.DeleteReview")
    @WebMethod(operationName = "DeleteReview", action = "https://connect.bookt.com/connect/IConnectREST/DeleteReview")
    @ResponseWrapper(localName = "DeleteReviewResponse", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.DeleteReviewResponse")
    public java.lang.String deleteReview(
        @WebParam(name = "apiKey", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String apiKey,
        @WebParam(name = "ID", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String id,
        @WebParam(name = "useInternalID", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.Boolean useInternalID
    );

    @WebResult(name = "GetPropertyCategoriesResult", targetNamespace = "https://connect.bookt.com/connect")
    @Action(input = "https://connect.bookt.com/connect/IConnectREST/GetPropertyCategories", output = "https://connect.bookt.com/connect/IConnectREST/GetPropertyCategoriesResponse")
    @RequestWrapper(localName = "GetPropertyCategories", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetPropertyCategories")
    @WebMethod(operationName = "GetPropertyCategories", action = "https://connect.bookt.com/connect/IConnectREST/GetPropertyCategories")
    @ResponseWrapper(localName = "GetPropertyCategoriesResponse", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetPropertyCategoriesResponse")
    public net.cbtltd.rest.bookt.ArrayOfstring getPropertyCategories(
        @WebParam(name = "apiKey", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String apiKey
    );

    @WebResult(name = "GetEventCategoriesResult", targetNamespace = "https://connect.bookt.com/connect")
    @Action(input = "https://connect.bookt.com/connect/IConnectREST/GetEventCategories", output = "https://connect.bookt.com/connect/IConnectREST/GetEventCategoriesResponse")
    @RequestWrapper(localName = "GetEventCategories", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetEventCategories")
    @WebMethod(operationName = "GetEventCategories", action = "https://connect.bookt.com/connect/IConnectREST/GetEventCategories")
    @ResponseWrapper(localName = "GetEventCategoriesResponse", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetEventCategoriesResponse")
    public net.cbtltd.rest.bookt.ArrayOfstring getEventCategories(
        @WebParam(name = "apiKey", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String apiKey
    );

    @WebResult(name = "MakeBookingResult", targetNamespace = "https://connect.bookt.com/connect")
    @Action(input = "https://connect.bookt.com/connect/IConnectREST/MakeBooking", output = "https://connect.bookt.com/connect/IConnectREST/MakeBookingResponse")
    @RequestWrapper(localName = "MakeBooking", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.MakeBooking")
    @WebMethod(operationName = "MakeBooking", action = "https://connect.bookt.com/connect/IConnectREST/MakeBooking")
    @ResponseWrapper(localName = "MakeBookingResponse", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.MakeBookingResponse")
    public net.cbtltd.rest.bookt.Booking makeBooking(
        @WebParam(name = "apiKey", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String apiKey,
        @WebParam(name = "booking", targetNamespace = "https://connect.bookt.com/connect")
        net.cbtltd.rest.bookt.Booking booking,
        @WebParam(name = "infoOnly", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.Boolean infoOnly
    );

    @WebResult(name = "GetPropertyIDsByCategoryResult", targetNamespace = "https://connect.bookt.com/connect")
    @Action(input = "https://connect.bookt.com/connect/IConnectREST/GetPropertyIDsByCategory", output = "https://connect.bookt.com/connect/IConnectREST/GetPropertyIDsByCategoryResponse")
    @RequestWrapper(localName = "GetPropertyIDsByCategory", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetPropertyIDsByCategory")
    @WebMethod(operationName = "GetPropertyIDsByCategory", action = "https://connect.bookt.com/connect/IConnectREST/GetPropertyIDsByCategory")
    @ResponseWrapper(localName = "GetPropertyIDsByCategoryResponse", targetNamespace = "https://connect.bookt.com/connect", className = "net.cbtltd.rest.bookt.GetPropertyIDsByCategoryResponse")
    public net.cbtltd.rest.bookt.ArrayOfint getPropertyIDsByCategory(
        @WebParam(name = "apiKey", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String apiKey,
        @WebParam(name = "propertyCategory", targetNamespace = "https://connect.bookt.com/connect")
        java.lang.String propertyCategory
    );
}