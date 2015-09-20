/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.cbtltd.rest.flipkey.supplier.InquiryRequest;
import net.cbtltd.rest.response.AvailabilityResponse;
import net.cbtltd.rest.response.CalendarResponse;
import net.cbtltd.rest.response.CancelReservationResponse;
import net.cbtltd.rest.response.QuoteResponse;
import net.cbtltd.rest.response.ReservationResponse;
import net.cbtltd.rest.response.SearchResponse;
import net.cbtltd.rest.response.WeeklyPriceResponse;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Reservation;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

/** 
 * The Class ReservationJson implements JSON services for reservations. 
 * 
 * @see http://cxf.apache.org/docs/jax-rs-basics.html
 */

@Path("/reservation")
//@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReservationJson extends AbstractReservationExt {

	@GET
	@Path("/available/{productid}/{fromdate}/{todate}")
	@Descriptions({ 
		@Description(value = "Indicates if a product (product) is available for the specified dates", target = DocTarget.METHOD),
		@Description(value = "Availability Indicator", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public static synchronized Available getAvailableXML(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("From (Arrival) Date") @PathParam("fromdate") String fromdate,
			@Description("To (Departure) Date") @PathParam("todate") String todate,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return getAvailable(productid, fromdate, todate, pos, xsl);
	}
	
	@GET
	@Path("/location/{locationid}/{fromdate}/{todate}")
	@Descriptions({ 
		@Description(value = "Lists product availability at one or more locations for the specified dates", target = DocTarget.METHOD),
		@Description(value = "Availability Schedule", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public static synchronized Schedule getScheduleXML(
			@Description("Location ID") @PathParam("locationid") String locationid,
			@Description("From Date") @PathParam("fromdate") String fromdate,
			@Description("To Date") @PathParam("todate") String todate,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return getSchedule(locationid, fromdate, todate, pos, xsl);
	}
	
	@GET
	@Path("/product/{productid}/{fromdate}/{todate}")
	@Descriptions({ 
		@Description(value = "Lists availability of one or more properties for the specified dates", target = DocTarget.METHOD),
		@Description(value = "Availability Schedule", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public static synchronized Schedule getProductXML(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("From Date") @PathParam("fromdate") String fromdate,
			@Description("To Date") @PathParam("todate") String todate,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return getProduct(productid, fromdate, todate, pos, xsl);
	}
	
	@GET
	@Path("/quote/{productid}/{fromdate}/{todate}")
	@Descriptions({ 
		@Description(value = "Quotation for a product for the specified dates", target = DocTarget.METHOD),
		@Description(value = "Price Quotation", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public static synchronized Quote getQuoteXML(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("From Date") @PathParam("fromdate") String fromdate,
			@Description("To Date") @PathParam("todate") String todate,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Currency Code") @DefaultValue(Constants.NO_CURRENCY) @QueryParam("currency") String currency,
			@Description("Include Terms") @DefaultValue(Constants.NO_TERMS) @QueryParam("terms") Boolean terms,
			@Description("Check Availability") @DefaultValue(Constants.CHECK_AVAILABILITY) @QueryParam("check") Boolean check,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return getQuote(productid, fromdate, todate, pos, currency, terms, check, xsl);
	}
	
	@GET
	@Path("/prices")
	@Descriptions({ 
		@Description(value = "Returns weekly prices for a specific product and range of dates", target = DocTarget.METHOD),
		@Description(value = "Weekly prices", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public static synchronized WeeklyPriceResponse getWeeklyPrices(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Product ID") @QueryParam("productid") String productid,
			@Description("From Date") @QueryParam("fromdate") String fromdate,
			@Description("To Date") @QueryParam("todate") String todate,
			@Description("Currency Code") @DefaultValue(Constants.NO_CURRENCY) @QueryParam("currency") String currency
			) {
		return AbstractReservation.getWeeklyPrices(pos, productid, fromdate, todate, currency);
	}
	
	@GET
	@Path("/quotes/{locationid}/{fromdate}/{todate}")
	@Descriptions({ 
		@Description(value = "Quotations for properties at one or more locations for the specified dates", target = DocTarget.METHOD),
		@Description(value = "Price Quotations", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public static synchronized Quotes getQuotesXML(
			@Description("Location ID") @PathParam("locationid") String locationid,
			@Description("From Date") @PathParam("fromdate") String fromdate,
			@Description("To Date") @PathParam("todate") String todate,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Currency Code") @DefaultValue(Constants.NO_CURRENCY) @QueryParam("currency") String currency,
			@Description("Include Terms") @DefaultValue(Constants.NO_TERMS) @QueryParam("terms") Boolean terms,
			@Description("Check Availability") @DefaultValue(Constants.CHECK_AVAILABILITY) @QueryParam("check") Boolean check,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return getQuotes(locationid, fromdate, todate, pos, currency, terms, check, xsl);
	}
	
	@GET
	@Path("/search/{locationid}/{fromdate}/{todate}")
	@Descriptions({ 
		@Description(value = "Quotations with additional info for properties at one or more locations for the specified dates", target = DocTarget.METHOD),
		@Description(value = "Price Quotations", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public static synchronized SearchQuotes getSearchQuotesXML(
			@Description("Location ID") @PathParam("locationid") String locationid,
			@Description("From Date") @PathParam("fromdate") String fromdate,
			@Description("To Date") @PathParam("todate") String todate,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Currency Code") @DefaultValue(Constants.NO_CURRENCY) @QueryParam("currency") String currency,
			@Description("Include Terms") @DefaultValue(Constants.NO_TERMS) @QueryParam("terms") Boolean terms,
			@Description("Include Amenities") @DefaultValue("false") @QueryParam("amenity") Boolean amenity,
			@Description("Guests") @DefaultValue(Constants.ZERO) @QueryParam("guests") String guests,
			@Description("Page number") @DefaultValue("") @QueryParam("page") String page,
			@Description("Quotes per page") @DefaultValue("") @QueryParam("perpage") String perpage,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return getSearchQuotes(locationid, fromdate, todate, guests, pos, currency, terms, amenity, page, perpage, xsl);
	}
	
	@GET
	@Path("/products/{locationid}/{fromdate}/{todate}")
	@Descriptions({ 
		@Description(value = "Products with additional info at one or more locations for the specified dates", target = DocTarget.METHOD),
		@Description(value = "Price Quotations", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public static synchronized SearchResponse getProductsXML(
			@Description("Location ID") @PathParam("locationid") String locationid,
			@Description("From Date") @PathParam("fromdate") String fromdate,
			@Description("To Date") @PathParam("todate") String todate,
			@Description("Product Id") @DefaultValue(Constants.BLANK) @QueryParam("productid") String productid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Currency Code") @DefaultValue(Constants.NO_CURRENCY) @QueryParam("currency") String currency,
			@Description("Include Terms") @DefaultValue(Constants.NO_TERMS) @QueryParam("terms") Boolean terms,
			@Description("Include Amenities") @DefaultValue("false") @QueryParam("amenity") Boolean amenity,
			@Description("Guests") @DefaultValue(Constants.ZERO) @QueryParam("guests") String guests,
			@Description("Exact Match Dates") @DefaultValue("false") @QueryParam("exec_match") Boolean exactmatch,
			@Description("Return only inquire only products") @DefaultValue("false") @QueryParam("display_inquire_only") Boolean displayinquireonly,			
			@Description("Return agent commission") @DefaultValue("true") @QueryParam("commission") Boolean commission,
			@Description("Page number") @DefaultValue("") @QueryParam("page") String page,
			@Description("Quotes per page") @DefaultValue("") @QueryParam("perpage") String perpage,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return getSearchProducts(locationid, fromdate, todate, productid, guests, exactmatch, displayinquireonly, commission, pos, currency, terms, amenity, page, perpage, xsl);
	}
	
	@GET
	@Path("/quotes/{locationid}/{distance}/{unit}/{fromdate}/{todate}")
	@Descriptions({ 
		@Description(value = "Quotations for properties near to a location for the specified dates", target = DocTarget.METHOD),
		@Description(value = "Price Quotations", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public static synchronized Quotes getQuotesNearbyXML(
			@Description("Location ID") @PathParam("locationid") String locationid,
			@Description("Maximum Distance from Location") @DefaultValue("50") @PathParam("distance") Double distance,
			@Description("Distance Unit") @DefaultValue("KMT") @PathParam("unit") String unit,
			@Description("From Date") @PathParam("fromdate") String fromdate,
			@Description("To Date") @PathParam("todate") String todate,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Currency Code") @DefaultValue(Constants.NO_CURRENCY) @QueryParam("currency") String currency,
			@Description("Include Terms") @DefaultValue(Constants.NO_TERMS) @QueryParam("terms") Boolean terms,
			@Description("Check Availability") @DefaultValue(Constants.CHECK_AVAILABILITY) @QueryParam("check") Boolean check,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return getQuotesNearby(locationid, distance, unit, fromdate, todate, pos, currency, terms, check, xsl);
	}
	
	@GET
	@Path("/quotes/{latitude}/{longitude}/{distance}/{unit}/{fromdate}/{todate}")
	@Descriptions({ 
		@Description(value = "Quotations for properties near to a position for the specified dates", target = DocTarget.METHOD),
		@Description(value = "Price Quotations", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public static synchronized Quotes getQuotesByPositionXML(
			@Description("Latitude of Position") @PathParam("latitude") Double latitude,
			@Description("Longitude of Position") @PathParam("longitude") Double longitude,
			@Description("Maximum Distance from Position") @DefaultValue("50") @PathParam("distance") Double distance,
			@Description("Distance Unit") @DefaultValue("KMT") @PathParam("unit") String unit,
			@PathParam("fromdate") String fromdate,
			@PathParam("todate") String todate,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Currency Code") @DefaultValue(Constants.NO_CURRENCY) @QueryParam("currency") String currency,
			@Description("Include Terms") @DefaultValue(Constants.NO_TERMS) @QueryParam("terms") Boolean terms,
			@Description("Check Availability") @DefaultValue(Constants.CHECK_AVAILABILITY) @QueryParam("check") Boolean check,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return getQuotesByPosition(latitude, longitude, distance, unit, fromdate, todate, pos, currency, terms, check, xsl);
	}
	
	@GET
	@Path("/special/{locationid}")
	@Descriptions({ 
		@Description(value = "Special offers for properties at one or more locations", target = DocTarget.METHOD),
		@Description(value = "Special Offer Quotations", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public static synchronized Specials getSpecialXML(
			@Description("Location ID") @PathParam("locationid") String locationid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Currency Code") @DefaultValue(Constants.NO_CURRENCY) @QueryParam("currency") String currency,
			@Description("Include Terms") @DefaultValue(Constants.NO_TERMS) @QueryParam("terms") Boolean terms,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return getSpecial(locationid, pos, currency, terms, xsl);
	}
	
	@GET
	@Path("/special/{locationid}/{distance}/{unit}")
	@Descriptions({ 
		@Description(value = "Special offers for properties near to a location", target = DocTarget.METHOD),
		@Description(value = "Special Offer Quotations", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public static synchronized Specials getSpecialsByLocationXML(
			@Description("Location ID") @PathParam("locationid") String locationid,
			@Description("Maximum Distance from Location") @DefaultValue("50") @PathParam("distance") Double distance,
			@Description("Distance Unit") @DefaultValue("KMT") @PathParam("unit") String unit,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Currency Code") @DefaultValue(Constants.NO_CURRENCY) @QueryParam("currency") String currency,
			@Description("Include Terms") @DefaultValue(Constants.NO_TERMS) @QueryParam("terms") Boolean terms,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return getSpecialsByLocation(locationid, distance, unit, pos, currency, terms, xsl);
	}
	
	@GET
	@Path("/special/{latitude}/{longitude}/{distance}/{unit}")
	@Descriptions({ 
		@Description(value = "Special offers for properties near to a position", target = DocTarget.METHOD),
		@Description(value = "Special Offer Quotations", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public static synchronized Specials getSpecialsByPositionXML(
			@Description("Latitude of Position") @PathParam("latitude") Double latitude,
			@Description("Longitude of Position") @PathParam("longitude") Double longitude,
			@Description("Maximum Distance from Position") @DefaultValue("50") @PathParam("distance") Double distance,
			@Description("Distance Unit") @DefaultValue("KMT") @PathParam("unit") String unit,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Currency Code") @DefaultValue(Constants.NO_CURRENCY) @QueryParam("currency") String currency,
			@Description("Include Terms") @DefaultValue(Constants.NO_TERMS) @QueryParam("terms") Boolean terms,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return getSpecialsByPosition(latitude, longitude, distance, unit, pos, currency, terms, xsl);
	}

	@POST
	@Path("/")
	@Descriptions({ 
		@Description(value = "Create a reservation of a product between the specified dates", target = DocTarget.METHOD),
		@Description(value = "Uploaded Reservation", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public static synchronized Reservation postReservation(
			Reservation action,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return setReservation(action, pos, xsl);
	}
	
	@GET
	@Consumes(MediaType.MEDIA_TYPE_WILDCARD)
	@Path("/download")
	@Descriptions({ 
		@Description(value = "Lists reservations made since the most recent download", target = DocTarget.METHOD),
		@Description(value = "List of New Reservations", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public static synchronized Downloads getDownloadXML(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return getDownload(pos, xsl);
	}
	
	@GET
	@Consumes(MediaType.MEDIA_TYPE_WILDCARD)
	@Path("/downloads")
	@Descriptions({ 
		@Description(value = "Lists reservations, managed by an external PMS, having future departure dates", target = DocTarget.METHOD),
		@Description(value = "Availability Indicator", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public static synchronized Downloads getDownloadsXML(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return getDownloads(pos, xsl);
	}
	
	@GET
	@Consumes(MediaType.MEDIA_TYPE_WILDCARD)
	@Path("/uploads")
	@Descriptions({ 
		@Description(value = "Lists reservations, managed by an external PMS, having future departure dates", target = DocTarget.METHOD),
		@Description(value = "Availability Indicator", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public static synchronized Downloads getUploadsXML(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return getUploads(pos, xsl);
	}

	@GET
	@Path("/inquiry")
	@Descriptions({ 
		@Description(value = "Create an inquired reservation", target = DocTarget.METHOD),
		@Description(value = "Created Reservation", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public static synchronized ReservationResponse createInquiredReservation(
			@Description(value = "Point of sale code") @QueryParam("pos") String pos, 
			@Description(value = "Product ID") @QueryParam("productid") String productId, 
			@Description(value = "From (Arrival) date") @QueryParam("fromdate") String fromDate, 
			@Description(value = "To (Departure) date") @QueryParam("todate") String toDate, 
			@Description(value = "Reservation notes") @QueryParam("notes") String notes,
			@Description(value = "Adults quantity") @QueryParam("adult") Integer adult,
			@Description(value = "Children quantity") @QueryParam("child") Integer child,
			@Description(value = "Renter's e-mail address") @QueryParam("emailaddress") String emailAddress, 
			@Description(value = "Renter's family name") @QueryParam("familyname") String familyName, 
			@Description(value = "Renter's first name") @QueryParam("firstname") String firstName,
			@Description(value = "Renter's telephone number") @QueryParam("telnumber") String phoneNumber) {
		return AbstractReservation.createInquiredReservation(pos, productId, fromDate, toDate, notes, adult, child, emailAddress, familyName, firstName, phoneNumber);
	}
	
	@GET
	@Path("/payment/create")
	@Descriptions({ 
		@Description(value = "Create the reservation and payment", target = DocTarget.METHOD),
		@Description(value = "Provisional Reservation", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public static synchronized ReservationResponse createReservationAndPayment(
			@Description(value = "Point of sale code") @QueryParam("pos") String pos, 
			@Description(value = "Product ID") @QueryParam("productid") String productId, 
			@Description(value = "From (Arrival) date") @QueryParam("fromdate") String fromDate, 
			@Description(value = "To (Departure) date") @QueryParam("todate") String toDate, 
			@Description(value = "Renter e-mail address") @QueryParam("emailaddress") String emailAddress, 
			@Description(value = "Renter family name") @QueryParam("familyname") String familyName, 
			@Description(value = "Renter first name") @QueryParam("firstname") String firstName, 
			@Description(value = "Reservation notes") @QueryParam("notes") String notes, 
			@Description(value = "Renter credit card number") @QueryParam("cardnumber") String cardNumber, 
			@Description(value = "Renter credit card month") @QueryParam("cardmonth") String cardMonth, 
			@Description(value = "Renter credit card expiration year") @QueryParam("cardyear") String cardYear, 
			@Description(value = "Renter credit card verification  code ") @QueryParam("cc_security_code") Integer cvc, 
			@Description(value = "Renter credit card address") @QueryParam("cc_address") String address, 
			@Description(value = "Renter credit card country ") @QueryParam("cc_country") String country, 
			@Description(value = "Renter credit card state ") @QueryParam("cc_state") String state, 
			@Description(value = "Renter credit card city ") @QueryParam("cc_city") String city,
			@Description(value = "Renter credit card zip code ") @QueryParam("cc_zip") String zip,
			@Description(value = "Amount to be payed") @QueryParam("amount") String amount, 
			@Description(value = "Currency of payment") @QueryParam("currency") String currency, 
			@Description(value = "Renter telephone number") @QueryParam("telnumber") String phoneNumber, 
			@Description(value = "0 - Mastercard, 1 - Visa, 2 - American Express") @QueryParam("cardtype") Integer cardType,
			@Description(value = "Renter's birth day") @QueryParam("cc_bdd") Integer birthDay,
			@Description(value = "Renter's birth month") @QueryParam("cc_bdm") Integer birthMonth,
			@Description(value = "Renter's birth year") @QueryParam("cc_bdy") Integer birthYear,
			@Description(value = "Adults quantity") @QueryParam("adult") Integer adult,
			@Description(value = "Children quantity") @QueryParam("child") Integer child) {
		ReservationResponse response = createReservationPayment(pos, productId, fromDate, toDate, emailAddress, familyName, firstName, notes, cardNumber, cardMonth, cardYear, 
				amount, currency, phoneNumber, cardType, cvc, adult, child, address, country, state, city, zip, birthDay, birthMonth, birthYear, null);
		return response;
	}
	
	@POST
	@Path("/payment/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Descriptions({ 
		@Description(value = "Create the reservation and payment", target = DocTarget.METHOD),
		@Description(value = "Provisional Reservation", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public static synchronized ReservationResponse createReservationAndPayment(
			@Description("Reservation to create") ReservationRequest request) {
		ReservationResponse response = createReservationPayment(request.getPos(), request.getProductId(), request.getFromDate(), request.getToDate(), request.getEmailAddress(), request.getFamilyName(), 
				request.getFirstName(), request.getNotes(), request.getCardNumber(), request.getCardMonth(), request.getCardYear(), 
				request.getAmount(), request.getCurrency(), request.getPhoneNumber(), request.getCardType(), request.getCvc(), request.getAdult(), request.getChild(), 
				request.getAddress(), request.getCountry(), request.getState(), request.getCity(), request.getZip(), request.getBirthDay(), request.getBirthMonth(), 
				request.getBirthYear(), null); 
		return response;
				
	}
	
	@GET
	@Path("/cancel")
	@Descriptions({ 
		@Description(value = "Cancel the reservation", target = DocTarget.METHOD),
		@Description(value = "Cancellation response", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public static synchronized CancelReservationResponse cancelReservation(
			@Description(value = "Encrypted reservation ID") @QueryParam("reservationPos") String reservationPos) throws Exception {
		return cancelBooking(reservationPos);
	}
	
	@GET
	@Path("/cancellation")
	@Descriptions({ 
		@Description(value = "Get a cancellation information", target = DocTarget.METHOD),
		@Description(value = "Reservation", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public static synchronized ReservationResponse getCancellation(
			@Description(value = "Encrypted reservation ID") @QueryParam("reservationPos") String reservationPos) throws Exception {
		return getCancellationInfo(reservationPos);
	}
	
	@GET
	@Path("/calendar")
	@Descriptions({ 
		@Description(value = "Get the availability calendar", target = DocTarget.METHOD),
		@Description(value = "Availability calendar", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public static synchronized CalendarResponse getCalendar(
			@Description(value = "Point of sale code") @QueryParam("pos") String pos,
			@Description(value = "Product ID") @QueryParam("productid") String productid,
			@Description(value = "The date from which the calendar is to be shown") @QueryParam("date") String date) {
		return getAvailabilityCalendar(pos, productid, date);
	}
	
	@GET
	@Path("/available_calendar")
	@Descriptions({ 
		@Description(value = "Get the availability calendar", target = DocTarget.METHOD),
		@Description(value = "Availability calendar", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public static synchronized AvailabilityResponse getAvailableCalendar(
			@Description(value = "Point of sale code") @QueryParam("pos") String pos,
			@Description(value = "Product ID") @QueryParam("productid") String productid,
			@Description(value = "The date from which the calendar is to be shown") @QueryParam("fromdate") String fromDate,
			@Description(value = "The date the calendar is to be shown to") @QueryParam("todate") String toDate) {
		return getAvailabilityCalendar(pos, productid, fromDate, toDate);
	}
	
	@GET
	@Path("/quotes")
	@Descriptions({ 
		@Description(value = "Get the quotes", target = DocTarget.METHOD),
		@Description(value = "Quotes", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public static synchronized QuoteResponse getQuote(
			@Description(value = "Point of sale code") @QueryParam("pos") String pos,
			@Description(value = "Product ID") @QueryParam("productid") String productid, 
			@Description(value = "From date") @QueryParam("fromdate") String fromdate, 
			@Description(value = "To date") @QueryParam("todate") String todate, 
			@Description(value = "Currency") @QueryParam("currency") String currency,
			@Description(value = "Currency") @QueryParam("adults") @DefaultValue("2") Integer adults,
			@Description(value = "Currency") @QueryParam("child") @DefaultValue("0") Integer children
			) {
		return getQuotes(pos, productid, fromdate, todate, currency, adults, children);
	}
	
	@POST
	@Path("/inquiry")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Descriptions({
			@Description(value = "Indicates if a product (product) is available for the specified dates", target = DocTarget.METHOD),
			@Description(value = "Availability Indicator", target = DocTarget.RETURN),
			@Description(value = "Request", target = DocTarget.REQUEST),
			@Description(value = "Response", target = DocTarget.RESPONSE),
			@Description(value = "Resource", target = DocTarget.RESOURCE) })
	public static synchronized Available availabilityInquiry(
			InquiryRequest inquiryRequest,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {

		LOG.info("Inside availabilityInquiry method");

		return getAvailable(inquiryRequest.getClient_property_id(),
				inquiryRequest.getArrival(), inquiryRequest.getDeparture(),
				pos, xsl);

	}
	
	@GET
	@Path("/test")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Descriptions({
			@Description(value = "Indicates if a product (product) is available for the specified dates", target = DocTarget.METHOD),
			@Description(value = "Availability Indicator", target = DocTarget.RETURN),
			@Description(value = "Request", target = DocTarget.REQUEST),
			@Description(value = "Response", target = DocTarget.RESPONSE),
			@Description(value = "Resource", target = DocTarget.RESOURCE) })
	public static synchronized String test(
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {

		LOG.info("Inside availabilityInquiry method");

		return "Successfully called";

	}
}
//	@GET
//	@Path("/{state}/{id}/{productid}/{fromdate}/{todate}/{emailaddress}/{familyname}/{firstname}")
//	@Descriptions({ 
//		@Description(value = "Provisionally reserves a product for the specified dates", target = DocTarget.METHOD),
//		@Description(value = "Provisional Reservation", target = DocTarget.RETURN),
//		@Description(value = "Request", target = DocTarget.REQUEST),
//		@Description(value = "Response", target = DocTarget.RESPONSE),
//		@Description(value = "Resource", target = DocTarget.RESOURCE)
//	})
//	public static synchronized Reservation setReservation (
//			@Description("Current State") @DefaultValue("Initial") @PathParam("state") String state,
//			@Description("Reservation ID") @PathParam("id") String id,
//			@Description("Product ID") @PathParam("productid") String productid,
//			@Description("From (Arrival) Date") @PathParam("fromdate") Date fromdate,
//			@Description("To (Departure) Date") @PathParam("todate") Date todate,
//			@Description("Guest Email Address") @PathParam("emailaddress") String emailaddress,
//			@Description("Guest Family Name") @PathParam("familyname") String familyname,
//			@Description("Primary Guest Given Name") @DefaultValue("") @PathParam("firstname") String firstname,
//			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
//			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
//			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
//		Reservation action = new Reservation();
//		action.setState(state);
//		action.setId(id);
//		action.setProductid(productid);
//		action.setFromdate(fromdate);
//		action.setTodate(todate);
//		action.setEmailaddress(emailaddress);
//		action.setCustomername(familyname, firstname);
//		return setReservation(action, pos, xsl);
//	}
//	
//	@GET
//	@Path("/{state}/{id}/{productid}/{fromdate}/{todate}/{emailaddress}/{familyname}/{firstname}/{notes}")
//	@Descriptions({ 
//		@Description(value = "Provisionally reserves a product for the specified dates", target = DocTarget.METHOD),
//		@Description(value = "Provisional Reservation", target = DocTarget.RETURN),
//		@Description(value = "Request", target = DocTarget.REQUEST),
//		@Description(value = "Response", target = DocTarget.RESPONSE),
//		@Description(value = "Resource", target = DocTarget.RESOURCE)
//	})
//	public static synchronized Reservation setReservation (
//			@Description("Current State") @DefaultValue("Initial") @PathParam("state") String state,
//			@Description("Reservation ID") @PathParam("id") String id,
//			@Description("Product ID") @PathParam("productid") String productid,
//			@Description("From (Arrival) Date") @PathParam("fromdate") Date fromdate,
//			@Description("To (Departure) Date") @PathParam("todate") Date todate,
//			@Description("Guest Email Address") @PathParam("emailaddress") String emailaddress,
//			@Description("Guest Family Name") @PathParam("familyname") String familyname,
//			@Description("Primary Guest Given Name") @DefaultValue("") @PathParam("firstname") String firstname,
//			@Description("Notes and Requests") @DefaultValue("") @PathParam("notes") String notes,
//			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
//			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
//			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
//		Reservation action = new Reservation();
//		action.setState(state);
//		action.setId(id);
//		action.setProductid(productid);
//		action.setFromdate(fromdate);
//		action.setTodate(todate);
//		action.setEmailaddress(emailaddress);
//		action.setCustomername(familyname, firstname);
//		action.setNotes(notes);
//		return setReservation(action, pos, xsl);
//	}
//	
//	@GET
//	@Path("/{state}/{id}/{productid}/{fromdate}/{todate}/{emailaddress}/{familyname}/{firstname}/{notes}/{quote}/{currency}")
//	@Descriptions({ 
//		@Description(value = "Provisionally reserves a product for the specified dates", target = DocTarget.METHOD),
//		@Description(value = "Provisional Reservation", target = DocTarget.RETURN),
//		@Description(value = "Request", target = DocTarget.REQUEST),
//		@Description(value = "Response", target = DocTarget.RESPONSE),
//		@Description(value = "Resource", target = DocTarget.RESOURCE)
//	})
//	public static synchronized Reservation setReservation (
//			@Description("Current State") @DefaultValue("Initial") @PathParam("state") String state,
//			@Description("Reservation ID") @PathParam("id") String id,
//			@Description("Product ID") @PathParam("productid") String productid,
//			@Description("From (Arrival) Date") @PathParam("fromdate") Date fromdate,
//			@Description("To (Departure) Date") @PathParam("todate") Date todate,
//			@Description("Guest Email Address") @PathParam("emailaddress") String emailaddress,
//			@Description("Guest Family Name") @PathParam("familyname") String familyname,
//			@Description("Primary Guest Given Name") @DefaultValue("") @PathParam("firstname") String firstname,
//			@Description("Notes and Requests") @DefaultValue("") @PathParam("notes") String notes,
//			@Description("Quoted Price") @DefaultValue(Model.ZERO) @PathParam("quote") Double quote,
//			@Description("Price Currency Code") @DefaultValue(Constants.USD) @PathParam("currency") String currency,
//			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
//			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
//			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
//		Reservation action = new Reservation();
//		action.setState(state);
//		action.setId(id);
//		action.setProductid(productid);
//		action.setFromdate(fromdate);
//		action.setTodate(todate);
//		action.setEmailaddress(emailaddress);
//		action.setCustomername(familyname, firstname);
//		action.setNotes(notes);
//		action.setQuote(quote);
//		action.setCurrency(currency);
//		return setReservation(action, pos, xsl);
//	}
//	
//	@GET
//	@Path("/{state}/{id}/{productid}/{fromdate}/{todate}/{emailaddress}/{familyname}/{firstname}/{notes}/{quote}/{tax}/{currency}")
//	@Descriptions({ 
//		@Description(value = "Provisionally reserves a product for the specified dates", target = DocTarget.METHOD),
//		@Description(value = "Provisional Reservation", target = DocTarget.RETURN),
//		@Description(value = "Request", target = DocTarget.REQUEST),
//		@Description(value = "Response", target = DocTarget.RESPONSE),
//		@Description(value = "Resource", target = DocTarget.RESOURCE)
//	})
//	public static synchronized Reservation setReservation (
//			@Description("Current State") @DefaultValue("Initial") @PathParam("state") String state,
//			@Description("Reservation ID") @PathParam("id") String id,
//			@Description("Product ID") @PathParam("productid") String productid,
//			@Description("From (Arrival) Date") @PathParam("fromdate") Date fromdate,
//			@Description("To (Departure) Date") @PathParam("todate") Date todate,
//			@Description("Guest Email Address") @PathParam("emailaddress") String emailaddress,
//			@Description("Guest Family Name") @PathParam("familyname") String familyname,
//			@Description("Primary Guest Given Name") @DefaultValue("") @PathParam("firstname") String firstname,
//			@Description("Notes and Requests") @DefaultValue("") @PathParam("notes") String notes,
//			@Description("Quoted Price") @DefaultValue(Model.ZERO) @PathParam("quote") Double quote,
//			@Description("Tax on Price") @DefaultValue(Model.ZERO) @PathParam("tax") Double tax,
//			@Description("Price Currency Code") @DefaultValue(Constants.USD) @PathParam("currency") String currency,
//			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
//			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
//			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
//		Reservation action = new Reservation();
//		action.setState(state);
//		action.setId(id);
//		action.setProductid(productid);
//		action.setFromdate(fromdate);
//		action.setTodate(todate);
//		action.setEmailaddress(emailaddress);
//		action.setCustomername(familyname, firstname);
//		action.setNotes(notes);
//		action.setQuote(quote);
//		action.setQuote(tax);
//		action.setCurrency(currency);
//		return setReservation(action, pos, xsl);
//	}
//}