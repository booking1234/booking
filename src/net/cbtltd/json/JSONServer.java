/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.json;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.cbtltd.json.available.AvailableWidgetItem;
import net.cbtltd.json.book.BookWidgetItem;
import net.cbtltd.json.calendar.CalendarWidgetItems;
import net.cbtltd.json.email.EmailWidgetItem;
import net.cbtltd.json.image.ImageWidgetItems;
import net.cbtltd.json.nameid.NameIdWidgetItems;
import net.cbtltd.json.pay.PayWidgetItem;
import net.cbtltd.json.price.PriceWidgetItems;
import net.cbtltd.json.quote.QuoteWidgetItem;
import net.cbtltd.json.reservation.ReservationWidgetItem;
import net.cbtltd.json.review.ReviewWidgetItems;
import net.cbtltd.json.text.TextWidgetItem;
import net.cbtltd.server.ReportService;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.Report;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

/**
 * The Class JSONServer is the JSON servlet which returns JSONP for requests with the callback parameter for GWT.
 * GWT JsonpRequestBuilder appends &callback=gwt_IOSucess 
 * The service uses the Gson library to parse and format Java objects.
 * It is perfectly fine (and recommended) to use private fields
 * There is no need to use any annotations to indicate a field is to be included for serialization and deserialization. 
 * All fields in the current class (and from all super classes) are included by default.
 * If a field is marked transient, (by default) it is ignored and not included in the JSON serialization or deserialization.
 * This implementation handles nulls correctly
 *     during serialization, a null field is skipped from the output
 *     during deserialization, a missing entry in JSON results in setting the corresponding field in the object to null
 * If a field is synthetic, it is ignored and not included in JSON serialization or deserialization
 * Fields corresponding to the outer classes in inner classes, anonymous classes, and local classes are ignored and not included in serialization or deserialization
 * 
 * @see <pre>http://json.org/</pre>
 * @see <pre>http://code.google.com/p/google-gson/</pre>
 * @see <pre>http://code.google.com/p/jsonp-java/wiki/UserGuide</pre>
 * @see <pre>http://blog.altosresearch.com/supporting-the-jsonp-callback-protocol-with-jquery-and-java/</pre>
 * @see <pre>http://www.ibm.com/developerworks/library/wa-aj-jsonp1/</pre>
 * @see <pre>http://www.ibm.com/developerworks/web/library/wa-aj-jsonp2/index.html</pre>
 * @see <pre>http://www.ibm.com/developerworks/web/library/wa-ajaxintro11/index.html</pre>
 * @see <pre>http://www.zackgrossbart.com/hackito/jsonp-sop/</pre>
 * @see <pre>https://developer.mozilla.org/En/HTTP_access_control</pre>
 * @see <pre>http://server.hostname.com/dostuff?format=json&jsoncallback=?</pre>
 */
public class JSONServer extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(JSONServer.class.getName());
	private static final Gson GSON = new Gson();

	/**
	 * This method handles POST requests from the browser or the load client.
	 *
	 * @param request the HTTP request.
	 * @param response the HTTP response.
	 * @throws ServletException the exception raised by servlet errors.
	 * @throws IOException the exception raised by I/O errors.
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * This method handles GET requests from the browser or the load client.
	 *
	 * @param request the HTTP request.
	 * @param response the HTTP response.
	 * @throws ServletException the exception raised by servlet errors.
	 * @throws IOException the exception raised by I/O errors.
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String type = request.getParameter("type").toUpperCase();
		LOG.debug("JSONServer type " + type);
		String jsonCallbackParam = request.getParameter("callback");
		LOG.debug("JSONServer jsonCallbackParam " + jsonCallbackParam);

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String jsonString = null;

		switch(JSONRequest.valueOf(type.toUpperCase())) {
		case AVAILABLE: jsonString = GSON.toJson(getAvailable(request)); break;
		case BOOK: jsonString = GSON.toJson(getBook(request)); break;
		case CALENDAR: jsonString = GSON.toJson(getCalendar(request)); break;
		case IMAGE: jsonString = GSON.toJson(getImage(request)); break;
		case MAP: jsonString = GSON.toJson(getMap(request)); break;
		case PRICE: jsonString = GSON.toJson(getPrice(request)); break;
		case BOOK_AVAILABLE: jsonString = GSON.toJson(getPriceAvailable(request)); break;
		case PARTY: jsonString = GSON.toJson(getParty(request)); break;
		case PAY: jsonString = GSON.toJson(getPay(request)); break;
		case PRODUCT: jsonString = GSON.toJson(getProduct(request)); break;
		case QUOTE: jsonString = GSON.toJson(getQuote(request)); break;
		case RECEIVE: jsonString = GSON.toJson(getReceive(request)); break;
		case REPORT: jsonString = GSON.toJson(getReport(request)); break;
		case RESERVATION: jsonString = GSON.toJson(getReservation(request)); break;
		case RESERVATION_RS: jsonString = GSON.toJson(getReservationRS(request)); break;
		case REVIEW: jsonString = GSON.toJson(getReview(request)); break;
		case TEXT: jsonString = GSON.toJson(getText(request)); break;
		default: throw new RuntimeException("The type " + type + " is not valid");
		}
		LOG.debug("JSONServer jsonString " + jsonString);
		if (jsonCallbackParam != null) jsonString = jsonCallbackParam + "(" + jsonString + ");"; //for JSONP wrapping
		LOG.debug("JSONServer jsonpString " + jsonString);
		out.write(jsonString);
		out.flush();
		out.close();
	}
	
	/*
	 * Gets the availability of a property for a range of dates.
	 *
	 * @param request the HTTP request.
	 * @return the availability of a property for a range of dates.
	 */
	private static final AvailableWidgetItem getAvailable(HttpServletRequest request) {
		return SharedService.getAvailable(
			request.getParameter("pos"),
			request.getParameter("productid"),
			request.getParameter("fromdate"),
			request.getParameter("todate")
		);
	}

	/*
	 * Gets the reservation number created for a property for a range of dates if it is created.
	 *
	 * @param request the HTTP request.
	 * @return the reservation number.
	 */
	private static final BookWidgetItem getBook(HttpServletRequest request) {
		return SharedService.getBook(
				request.getParameter("pos"),
				request.getParameter("productid"),
				request.getParameter("fromdate"),
				request.getParameter("todate"),
				request.getParameter("emailaddress"),
				request.getParameter("familyname"),
				request.getParameter("firstname"),
				request.getParameter("notes"),
				request.getParameter("cardholder"),
				request.getParameter("cardnumber"),
				request.getParameter("cardmonth"),
				request.getParameter("cardyear"),
				request.getParameter("cardcode"),
				request.getParameter("amount"),
				request.getRemoteHost()
		);
	}
	
	/*
	 * Gets an array of dates when a property is not available.
	 *
	 * @param request the HTTP request.
	 * @return the array of dates when the property is not available.
	 */
	private static final CalendarWidgetItems getCalendar(HttpServletRequest request) {
		return SharedService.getCalendar(
				request.getParameter("pos"),
				request.getParameter("productid"),
				request.getParameter("date")
		);
	}
	
	/*
	 * Gets an array of images of a property.
	 *
	 * @param request the HTTP request.
	 * @return the array of images of a property.
	 */
	private static final ImageWidgetItems getImage(HttpServletRequest request) {
		return SharedService.getImage(
				request.getParameter("pos"),
				request.getParameter("productid")
		);
	}

	/*
	 * Gets the latitude and longitude of the specified product, or a message on failure.
	 *
	 * @param request the HTTP request.
	 * @return the latitude and longitude, or error message.
	 */
	private static final MapWidgetItem getMap(HttpServletRequest request) {
		return SharedService.getMap(
				request.getParameter("pos"),
				request.getParameter("productid")
		);
	}
	
	/*
	 * Gets the deposit amount and currency required to confirm a reservation, or a message if not available.
	 *
	 * @param request the HTTP request.
	 * @return the deposit amount and currency, or error message.
	 */
	private static final BookWidgetItem getPriceAvailable(HttpServletRequest request) {
		return SharedService.getPriceAvailable(
				request.getParameter("pos"),
				request.getParameter("productid"),
				request.getParameter("fromdate"),
				request.getParameter("todate"),
				request.getParameter("emailaddress")
		);
	}
	
	/*
	 * Gets a party's name using its email address, or a message if it does not exist.
	 *
	 * @param request the HTTP request.
	 * @return the party's name, or error message.
	 */
	private static final EmailWidgetItem getParty(HttpServletRequest request) {
		return SharedService.getParty(
				request.getParameter("pos"),
				request.getParameter("emailaddress")
		);
	}
	
	/*
	 * Pays the specified amount and returns the target currency exchange rate, or a message on failure.
	 *
	 * @param request the HTTP request.
	 * @return the exchange rate, or error message.
	 */
	private static final PayWidgetItem getPay(HttpServletRequest request) {
		return SharedService.getPay(
				request.getParameter("pos"),
				request.getParameter("organizationid"),
				request.getParameter("financeid"),
				request.getParameter("eventname"),
				request.getParameter("amount"),
				request.getParameter("currency"),
				request.getParameter("tocurrency"),
				request.getParameter("emailaddress"),
				request.getParameter("cardholder"),
				request.getParameter("cardnumber"),
				request.getParameter("cardmonth"),
				request.getParameter("cardyear"),
				request.getParameter("cardcode"),
				request.getRemoteHost()
		 );
	}
	
	/*
	 * Gets the price list for the specified product and currency , or a message on failure.
	 *
	 * @param request the HTTP request.
	 * @return the price list, or error message.
	 */
	private static final PriceWidgetItems getPrice(HttpServletRequest request) {
		return SharedService.getPrice(
				request.getParameter("pos"),
				request.getParameter("productid"),
				request.getParameter("date"),
				request.getParameter("rows"),
				request.getParameter("currency")
		);
	}
	
	/*
	 * Gets the standard and quoted prices for the specified product and currency , or a message on failure.
	 *
	 * @param request the HTTP request.
	 * @return the standard and quoted prices, or error message.
	 */
	private static final QuoteWidgetItem getQuote(HttpServletRequest request) {
		return SharedService.getQuote(
				request.getParameter("pos"),
				request.getParameter("productid"),
				request.getParameter("fromdate"),
				request.getParameter("todate"),
				request.getParameter("currency")
		);
	}
	
	/*
	 * Gets an array of product name ID pairs, or a message on failure.
	 *
	 * @param request the HTTP request.
	 * @return the array of product name ID pairs, or error message.
	 */
	private static final NameIdWidgetItems getProduct(HttpServletRequest request) {
		return SharedService.getProduct(
				request.getParameter("pos"),
				request.getParameter("productid")
		);
	}
	
	/*
	 * Pays the specified amount to the licensor's account and returns the target currency exchange rate, or a message on failure.
	 *
	 * @param request the HTTP request.
	 * @return the target currency exchange rate, or error message.
	 */
	private static final PayWidgetItem getReceive(HttpServletRequest request) {
		return SharedService.getReceive(
				request.getParameter("pos"),
				request.getParameter("organizationid"),
				request.getParameter("financeid"),
				request.getParameter("eventname"),
				request.getParameter("currency"),
				request.getParameter("tocurrency"),
				request.getParameter("amount"),
				request.getParameter("emailaddress"),
				request.getParameter("cardholder"),
				request.getParameter("cardnumber"),
				request.getParameter("cardmonth"),
				request.getParameter("cardyear"),
				request.getParameter("cardcode")
		);
	}
	
	/*
	 * Gets the details of the specified reservation, or a message on failure.
	 *
	 * @param request the HTTP request.
	 * @return the details of the specified reservation, or error message.
	 */
	private static final Report getReport(HttpServletRequest request) {
		return ReportService.getReport(
				request.getParameter("design"),
				request.getParameter("format"),
				request.getParameter("notes")
		);
	}
	
	/*
	 * Gets the details of the specified reservation, or a message on failure.
	 *
	 * @param request the HTTP request.
	 * @return the details of the specified reservation, or error message.
	 */
	private static final ReservationWidgetItem getReservation(HttpServletRequest request) {
		return SharedService.getReservation(
				request.getParameter("pos"),
				request.getParameter("reservationid")
		);
	}
	
	/*
	 * Gets a payment response of a reservation, or a message on failure.
	 *
	 * @param request the HTTP request.
	 * @return the payment response, or error message.
	 */
	private static final PayWidgetItem getReservationRS(HttpServletRequest request) {
		return SharedService.getReservationRS(
				request.getParameter("pos"),
				request.getParameter("reservationid"),
				request.getParameter("emailaddress"),
				request.getParameter("familyname"),
				request.getParameter("notes"),
				request.getParameter("firstname"),
				request.getParameter("cardholder"),
				request.getParameter("cardnumber"),
				request.getParameter("cardmonth"),
				request.getParameter("cardyear"),
				request.getParameter("cardcode"),
				request.getParameter("amount"),
				request.getRemoteHost()
		);
	}
	
	/*
	 * Gets an array of guest reviews for the specified product, or a message on failure.
	 *
	 * @param request the HTTP request.
	 * @return the array of guest reviews, or error message.
	 */
	private static final ReviewWidgetItems getReview(HttpServletRequest request) {
		return SharedService.getReview(
				request.getParameter("pos"),
				request.getParameter("productid"),
				request.getParameter("rows")
		);
	}
	
	/*
	 * Gets the text of the specified id and language, or a message on failure.
	 *
	 * @param request the HTTP request.
	 * @return the text, or error message.
	 */
	private static final TextWidgetItem getText(HttpServletRequest request) {
		return SharedService.getText(
			request.getParameter("pos"),
			request.getParameter("productid"),
			request.getParameter("language"),
			request.getParameter("text")
		);
	}
}
