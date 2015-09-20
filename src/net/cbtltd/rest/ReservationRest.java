/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.cbtltd.rest.logging.ServiceTimer;
import net.cbtltd.rest.response.CalendarResponse;
import net.cbtltd.rest.response.CancelReservationResponse;
import net.cbtltd.rest.response.QuoteResponse;
import net.cbtltd.rest.response.ReservationResponse;
import net.cbtltd.rest.response.WeeklyPriceResponse;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.ReservationExtMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Reservation.State;
import net.cbtltd.shared.ReservationExt;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.apache.ibatis.session.SqlSession;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/** 
 * The Class ReservationRest implements REST service for reservations. 
 * 
 * @see http://cxf.apache.org/docs/jax-rs-basics.html
 */

@Path("/reservation")
//@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class ReservationRest extends AbstractReservationExt {
	private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

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
		ServiceTimer.setStopWatchTagMessage("ReservationRest", "getAvailableXML");
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
		ServiceTimer.setStopWatchTagMessage("ReservationRest", "getScheduleXML");
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
		ServiceTimer.setStopWatchTagMessage("ReservationRest", "getProductXML");
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
		ServiceTimer.setStopWatchTagMessage("ReservationRest", "getQuoteXML");
		return getQuote(productid, fromdate, todate, pos, currency, terms, check, xsl);
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
		ServiceTimer.setStopWatchTagMessage("ReservationRest", "getQuotesXML");
		return getQuotes(locationid, fromdate, todate, pos, currency, terms, check, xsl);
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
		ServiceTimer.setStopWatchTagMessage("ReservationRest", "getQuotesNearbyXML");
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
		ServiceTimer.setStopWatchTagMessage("ReservationRest", "getQuotesByPositionXML");
		return getQuotesByPosition(latitude, longitude, distance, unit, fromdate, todate, pos, currency, terms, check, xsl);
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
		ServiceTimer.setStopWatchTagMessage("ReservationRest", "getSpecialXML");
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
		ServiceTimer.setStopWatchTagMessage("ReservationRest", "getSpecialsByLocationXML");
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
		ServiceTimer.setStopWatchTagMessage("ReservationRest", "getSpecialsByPositionXML");
		return getSpecialsByPosition(latitude, longitude, distance, unit, pos, currency, terms, xsl);
	}

	/************************************************************************************************************************
	 * Reservation API new style - use only these URLS
	************************************************************************************************************************/	
	@GET
	@Path("/id/{id}")
	@Descriptions({ 
		@Description(value = "Get the reservation with the specified ID", target = DocTarget.METHOD),
		@Description(value = "Provisional Reservation", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public static synchronized Reservation getReservationById(
			@Description("Reservation ID") @PathParam("id") String id,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Include Terms") @DefaultValue(Constants.NO_TERMS) @QueryParam("terms") Boolean terms,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ReservationRest", "getReservationById");
		return getReservation(true, false, id, pos, terms, xsl);
	}

	@GET
	@Path("/name/{name}")
	@Descriptions({ 
		@Description(value = "Get the reservation with the specified ID", target = DocTarget.METHOD),
		@Description(value = "Provisional Reservation", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public static synchronized Reservation getReservationByName(
			@Description("Reservation Name") @PathParam("name") String name,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Include Terms") @DefaultValue(Constants.NO_TERMS) @QueryParam("terms") Boolean terms,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ReservationRest", "getReservationByName");
		return getReservation(false, false, name, pos, terms, xsl);
	}

	@GET
	@Path("/id/{id}/detail")
	@Descriptions({ 
		@Description(value = "Get the reservation with the specified ID", target = DocTarget.METHOD),
		@Description(value = "Provisional Reservation", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public static synchronized Reservation getReservationDetailById(
			@Description("Reservation ID") @PathParam("id") String id,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Include Terms") @DefaultValue(Constants.NO_TERMS) @QueryParam("terms") Boolean terms,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ReservationRest", "getReservationDetailById");
		return getReservation(true, true, id, pos, terms, xsl);
	}

	@GET
	@Path("/name/{name}/detail")
	@Descriptions({ 
		@Description(value = "Get the reservation with the specified ID", target = DocTarget.METHOD),
		@Description(value = "Provisional Reservation", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public static synchronized Reservation getReservationDetailByName(
			@Description("Reservation Name") @PathParam("name") String name,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Include Terms") @DefaultValue(Constants.NO_TERMS) @QueryParam("terms") Boolean terms,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ReservationRest", "getReservationDetailByName");
		return getReservation(false, true, name, pos, terms, xsl);
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Path("/")
	@Descriptions({ 
		@Description(value = "Create a reservation of a product between the specified dates", target = DocTarget.METHOD),
		@Description(value = "Uploaded Reservation", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public static synchronized Reservation postReservation (
			Reservation rq,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		Reservation persistedReservation=setReservation(rq, pos, xsl);
		//persist the reservation extension here
				SqlSession sqlSession = RazorServer.openSession();
				String reservationId=persistedReservation.getAltid();
			//delete already existing record and persist the new ones
				ReservationExt query=new ReservationExt();
				query.setReservationId(reservationId);
				List<ReservationExt> listTempReservationExt=sqlSession.getMapper(ReservationExtMapper.class).readReservationExt(query);
				if(listTempReservationExt!=null){
					LOG.info("No of records already exists in ext"+listTempReservationExt.size());	
				}
				
				if(listTempReservationExt!=null && listTempReservationExt.size()>0 && !State.Cancelled.name().equalsIgnoreCase(rq.getState())){
					LOG.info("Deleting records with reservation id "+reservationId);
					 sqlSession.getMapper(ReservationExtMapper.class).delete(reservationId);
				}
				List<ReservationExt> listPersistedReservationExt=new ArrayList<ReservationExt>();
				for(ReservationExt ext:rq.getListReservationExt()){
					ext.setReservationId(reservationId);
					ext.setState("Created");
					if("Guest".equalsIgnoreCase(ext.getType())){
						persistedReservation.setGuestName(ext.getName());
					}
					 sqlSession.getMapper(ReservationExtMapper.class).create(ext);
					 listPersistedReservationExt.add(ext);
				}
				persistedReservation.setListReservationExt(listPersistedReservationExt);
				sqlSession.commit();
				sqlSession.close();
			return persistedReservation;
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
	@Consumes(MediaType.APPLICATION_XML)
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
	@Path("/createwithpayment")
	@Descriptions({ 
		@Description(value = "Create the reservation and payment", target = DocTarget.METHOD),
		@Description(value = "Provisional Reservation", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public static synchronized ReservationResponse createReservationAndPayment(Reservation rq,
			@Description(value = "Point of sale code") @QueryParam("pos") String pos) {
		DateTime fromDateTime = new DateTime(rq.getFromdate());
		DateTime toDateTime = new DateTime(rq.getTodate());
		
		Integer cardType=0;
		/*
		 * "0" - MASTER CARD
			"1" - VISA
			"2" - AMERICAN EXPRESS
			"3" - DINERS CLUB
			"4" - DISCOVER
			"5" - JBC
		 */
		if("Master Card".equalsIgnoreCase(rq.getCardType())) {
			cardType=0;
		}else if("Visa".equalsIgnoreCase(rq.getCardType())){
			cardType=1;
		}else if("American Express".equalsIgnoreCase(rq.getCardType())){
			cardType=2;
		}
		Integer cardCode=0;
		try{
			cardCode=Integer.parseInt(rq.getCardcode());
		}catch(NumberFormatException e){
			cardCode=123;
		}
		ReservationResponse reservationResponse= createReservationPayment(pos, rq.getProductid(), dateTimeFormatter.print(fromDateTime), dateTimeFormatter.print(toDateTime), rq.getEmailaddress(), rq.getFamilyname(), rq.getFirstname(), rq.getNotes(), rq.getCardnumber(), rq.getCardmonth(), rq.getCardyear(), 
				rq.getCost()+"", rq.getCurrency(), rq.getPhoneNumber(), cardType,cardCode , rq.getAdult(), rq.getChild(), rq.getAddrress(), rq.getCountry(), "", rq.getCity(), rq.getZip(), 0, 0,0,rq.getAltid());
		LOG.info("reservationResponse "+reservationResponse.SUCCESS);
		
		//if(reservationResponse!=null && reservationResponse.SUCCESS){
		LOG.info("Start to persist reservation extension here size is "+rq.getListReservationExt().size());
		
		//persist the reservation extension here
		SqlSession sqlSession = RazorServer.openSession();
		Reservation reservation = null;
		
		if (reservationResponse.getReservationInfo() != null){
			reservation = sqlSession.getMapper(ReservationMapper.class).read(reservationResponse.getReservationInfo().getId());
		
			List<ReservationExt> listPersistedReservationExt=new ArrayList<ReservationExt>();
			for(ReservationExt ext:rq.getListReservationExt()){
				ext.setReservationId(reservation.getAltid());
				ext.setState("Created");
				if("Guest".equalsIgnoreCase(ext.getType())){
					reservation.setGuestName(ext.getName());
				}
				 sqlSession.getMapper(ReservationExtMapper.class).create(ext);
				 listPersistedReservationExt.add(ext);
			}
			
			LOG.info("Persisted Addons size "+listPersistedReservationExt.size());
			reservation.setListReservationExt(listPersistedReservationExt);
		}
		
		sqlSession.commit();
		sqlSession.close();
	//	}
		return reservationResponse;
		/*return createReservationPayment("5e7e3a77b3714ea2", "121","2014-07-04 ","2014-07-04", "senthil.subash@nibodha.com", "Subash", "Senthil", "Sample Notes", "5346330641608164", "11","2018", 
				"100", "EUR", "9940483172", 0,123 , 2, 0, "Test Addr", "USA", "Cali", "Irvine", "", 11, 11,1980);*/
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
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Path("/cancelunpaidreservation")
	@Descriptions({ 
		@Description(value = "Create a reservation of a product between the specified dates", target = DocTarget.METHOD),
		@Description(value = "Uploaded Reservation", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public static synchronized Reservation cancelUnPaidReservation (
			Reservation rq,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		return cancelUnPaidBookings(rq, pos, xsl);
	}
	
	@GET
	@Path("/info")
	@Descriptions({ 
		@Description(value = "Get a reservation information", target = DocTarget.METHOD),
		@Description(value = "Reservation", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public static synchronized ReservationResponse getReservation(
			@Description(value = "Encrypted reservation ID") @QueryParam("reservationPos") String reservationPos) throws Exception {
		return getCancellationInfo(reservationPos);
	}
	
	// DEPRECATED ==================================================================================================
	//History Geronimo
	@GET
	@Path("/{productid}/{checkin}/{checkout}/{emailaddress}/{familyname}/{firstname}/{notes}/{quote}/{tax}/{currency}")
	@Descriptions({ 
		@Description(value = "Provisionally reserves a product for the specified dates", target = DocTarget.METHOD),
		@Description(value = "Provisional Reservation", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public static synchronized Reservation getProvisionalQuoted(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("From (Arrival) Date") @PathParam("checkin") String checkin,
			@Description("To (Departure) Date") @PathParam("checkout") String checkout,
			@Description("Guest Email Address") @PathParam("emailaddress") String emailaddress,
			@Description("Guest Family Name") @PathParam("familyname") String familyname,
			@Description("Primary Guest Given Name") @DefaultValue("") @PathParam("firstname") String firstname,
			@Description("Notes and Requests") @DefaultValue("") @PathParam("notes") String notes,
			@Description("Quoted Price") @DefaultValue(Model.ZERO) @PathParam("quote") String quote,
			@Description("Tax on Price") @DefaultValue(Model.ZERO) @PathParam("tax") String tax,
			@Description("Price Currency Code") @DefaultValue(Constants.USD) @PathParam("currency") String currency,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ReservationRest", "getProvisionalQuoted");
		Reservation action = new Reservation();
		action.setState(Reservation.State.Provisional.name());
		action.setProductid(productid);
		action.setFromdate(Constants.parseDate(checkin));
		action.setTodate(Constants.parseDate(checkout));
		action.setEmailaddress(emailaddress);
		action.setCustomername(familyname, firstname);
		action.setNotes(notes);
		action.setQuote(quote == null ? null : Double.valueOf(quote));
		action.setCurrency(currency);
		return setReservation(action, pos, xsl);
	}

	@GET
	@Path("/{productid}/{fromdate}/{todate}/{emailaddress}/{familyname}/{firstname}/{notes}")
	@Descriptions({ 
		@Description(value = "Provisionally reserves a product for the specified dates", target = DocTarget.METHOD),
		@Description(value = "Provisional Reservation", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public static synchronized Provisional getProvisionalXML(
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("From (Arrival) Date") @PathParam("fromdate") String fromdate,
			@Description("To (Departure) Date") @PathParam("todate") String todate,
			@Description("Guest Email Address") @PathParam("emailaddress") String emailaddress,
			@Description("Guest Family Name") @PathParam("familyname") String familyname,
			@Description("Primary Guest Given Name") @DefaultValue("") @PathParam("firstname") String firstname,
			@Description("Notes and Requests") @DefaultValue("") @PathParam("notes") String notes,
//			@Description("Quoted Price") @DefaultValue(Model.ZERO) @PathParam("quote") String quote,
//			@Description("Tax on Price") @DefaultValue(Model.ZERO) @PathParam("tax") String tax,
//			@Description("Price Currency Code") @DefaultValue(Constants.USD) @PathParam("currency") String currency,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ReservationRest", "getProvisionalXML");
//TODO:		Reservation action = new Reservation();
//		action.setState(Reservation.State.Provisional.name());
//		action.setProductid(productid);
//		action.setFromdate(Constants.parseDate(checkin));
//		action.setTodate(Constants.parseDate(checkout));
//		action.setEmailaddress(emailaddress);
//		action.setCustomername(familyname, firstname);
//		action.setNotes(notes);
//		action.setQuote(quote == null ? null : Double.valueOf(quote));
//		action.setCurrency(currency);
//		return setReservation(action, pos, xsl);
		return getProvisional(productid, fromdate, todate, emailaddress, familyname, firstname, notes, null, null, null, pos, false, xsl);
	}

	@GET
	@Consumes(MediaType.MEDIA_TYPE_WILDCARD)
	@Path("/upload/{reservationid}/{productid}/{checkin}/{checkout}/{emailaddress}/{familyname}/{firstname}/{price}/{currency}")
	@Descriptions({ 
		@Description(value = "Upload or update a reservation for a product between the specified dates", target = DocTarget.METHOD),
		@Description(value = "Uploaded Reservation", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public static synchronized Download getUploadXML(
			@Description("Reservation ID") @PathParam("reservationid") String reservationid,
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("From (Arrival) Date") @PathParam("checkin") String checkin,
			@Description("To (Departure) Date") @PathParam("checkout") String checkout,
			@Description("Guest Email Address") @DefaultValue(Constants.BLANK) @PathParam("emailaddress") String emailaddress,
			@Description("Guest Family Name") @DefaultValue(Constants.UNALLOCATED) @PathParam("familyname") String familyname,
			@Description("Primary Guest Given Name") @DefaultValue(Constants.BLANK) @PathParam("firstname") String firstname,
			@Description("Quoted Price") @DefaultValue(Model.ZERO) @PathParam("price") String price,
			@Description("Price Currency Code") @DefaultValue(Constants.USD) @PathParam("currency") String currency,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ReservationRest", "getUploadXML");
		return getUpload(reservationid, productid, checkin, checkout, emailaddress, familyname, firstname, price, currency, pos, xsl);
	}

	@GET
	@Path("/update/{id}/{productid}/{checkin}/{checkout}/{quote}/{cost}/{currency}")
	@Descriptions({ 
		@Description(value = "Updates a reservation with the specified parameters", target = DocTarget.METHOD),
		@Description(value = "Provisional Reservation", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})
	public static synchronized Reservation setProvisionalXML(
			@Description("Reservation ID") @PathParam("id") String id,
			@Description("Product ID") @PathParam("productid") String productid,
			@Description("From (Arrival) Date") @PathParam("checkin") String checkin,
			@Description("To (Departure) Date") @PathParam("checkout") String checkout,
			@Description("Quoted Price") @PathParam("quote") Double quote,
			@Description("STO Price") @PathParam("quote") Double cost,
			@Description("Price Currency Code") @DefaultValue(Constants.USD) @PathParam("currency") String currency,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ReservationRest", "setProvisionalXML");
		Reservation action = new Reservation();
		action.setId(id);
		action.setProductid(productid);
		action.setFromdate(Constants.parseDate(checkin));
		action.setTodate(Constants.parseDate(checkout));
		action.setQuote(quote == null ? null : Double.valueOf(quote));
		action.setCurrency(currency);
		return setReservation(action, pos, xsl);
//		return setProvisional(reservationid, productid, checkin, checkout, quote, cost, currency, pos, test, xsl);
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
		ServiceTimer.setStopWatchTagMessage("ReservationRest", "getDownloadXML");
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
		ServiceTimer.setStopWatchTagMessage("ReservationRest", "getDownloadsXML");
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
		ServiceTimer.setStopWatchTagMessage("ReservationRest", "getUploadsXML");
		return getUploads(pos, xsl);
	}

	@GET
	@Consumes(MediaType.MEDIA_TYPE_WILDCARD)
	@Path("/cancel/{reservationid}")
	@Descriptions({ 
		@Description(value = "Cancels a reservation that has not been confirmed", target = DocTarget.METHOD),
		@Description(value = "Cancel Reservation", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public static synchronized Response cancelDownloadXML(
			@Description("Reservation ID") @PathParam("reservationid") String reservationid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional Test Flag") @DefaultValue("false") @QueryParam("test") Boolean test,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ReservationRest", "cancelDownloadXML");
		return cancelDownload(reservationid, pos, test, xsl);
	}

	@GET
	@Path("/{reservationid}/value")
	@Descriptions({ 
		@Description(value = "List of reservation key value pairs", target = DocTarget.METHOD),
		@Description(value = "Reservation Key Values", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public Items getValuesXML(
			@Description("Reservation ID") @PathParam("reservationid") String reservationid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ReservationRest", "getValuesXML");
		return getValues(reservationid, pos, xsl);
	}

	@GET
	@Path("/{reservationid}/keyvalue")
	@Descriptions({ 
		@Description(value = "List of reservation key value pairs", target = DocTarget.METHOD),
		@Description(value = "Reservation Key Values", target = DocTarget.RETURN),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
		@Description(value = "Resource", target = DocTarget.RESOURCE)
	})	
	public KeyValues getKeyvaluesXML(
			@Description("Reservation ID") @PathParam("reservationid") String reservationid,
			@Description("Mandatory POS Code") @DefaultValue(Constants.NO_POS) @QueryParam("pos") String pos,
			@Description("Optional XSLT") @DefaultValue(Constants.NO_XSL) @QueryParam("xsl") String xsl) {
		ServiceTimer.setStopWatchTagMessage("ReservationRest", "getKeyvaluesXML");
		return getKeyvalues(reservationid, pos, xsl);
	}
}
