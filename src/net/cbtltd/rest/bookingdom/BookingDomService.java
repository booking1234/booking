package net.cbtltd.rest.bookingdom;

import net.cbtltd.rest.laketahoeaccommodations.A_Handler;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;


public class BookingDomService {
	private static final Logger LOG = Logger.getLogger(BookingDomService.class.getName());
	private static final String apiUsername = "tbd";
	private static final String apiPassword = "tbd";
	private static final String apiPath = "http://api.bookingdom.com/";
	private static final String requestJSON = "application/json";
	private static final String requestXML = "application/xml";
	private static final String propertyAvailablePath = "/property/available/";
	private static final String createBookingPath = "/property/createBooking/";
	
	
	public BookingDomService() {}
	
	public static boolean isPropertyAvailable(
			String propertyKey,
			String fromDate,
			String toDate) {
		GetMethod rq = new GetMethod(apiPath + propertyAvailablePath + propertyKey + "/" + fromDate + "/" + toDate + "/");
		rq.addRequestHeader("Accept", requestJSON);
		
		HttpClient httpclient = new HttpClient();
		try {
			int responseCode = httpclient.executeMethod(rq);
			String response = rq.getResponseBodyAsString();
			// parse response
		} catch (Throwable x) {
			LOG.debug(x.getMessage());
		}
		
		return false;
	}

	public static BookingDomPriceResult computePrice(
			String propertyKey,
			String fromDate,
			String toDate,
			BookingDomGuestComposition guestComposition) {
		return null;	
	}
	
	public static BookingDomBookingResponse createBooking(BookingDomBookingRequest rq) {
		PostMethod post = new PostMethod(apiPath + createBookingPath);
		post.addRequestHeader("Accept", requestJSON);
		post.addRequestHeader("apiUsername", apiUsername);
		post.addRequestHeader("apiPassword", apiPassword);
		post.addParameter("adults", String.valueOf(rq.getGuestsComposition().getAdults()));
		post.addParameter("children", String.valueOf(rq.getGuestsComposition().getChildren()));
		post.addParameter("infants", String.valueOf(rq.getGuestsComposition().getInfants()));
		
		// TODO: Add BookingDTO
		HttpClient httpclient = new HttpClient();
		try {
			int responseCode = httpclient.executeMethod(post);
			String response = post.getResponseBodyAsString();
			// parse response
		} catch (Throwable x) {
			LOG.debug(x.getMessage());
		}
		
		return null;
	}

}
