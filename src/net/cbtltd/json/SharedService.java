/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.json;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.client.widget.RazorWidget;
import net.cbtltd.json.available.AvailableWidgetItem;
import net.cbtltd.json.book.BookWidgetItem;
import net.cbtltd.json.calendar.CalendarWidgetItem;
import net.cbtltd.json.calendar.CalendarWidgetItems;
import net.cbtltd.json.email.EmailWidgetItem;
import net.cbtltd.json.image.ImageWidgetItem;
import net.cbtltd.json.image.ImageWidgetItems;
import net.cbtltd.json.nameid.NameIdWidgetItem;
import net.cbtltd.json.nameid.NameIdWidgetItems;
import net.cbtltd.json.pay.PayWidgetItem;
import net.cbtltd.json.price.PriceWidgetItem;
import net.cbtltd.json.price.PriceWidgetItems;
import net.cbtltd.json.quote.QuoteWidgetItem;
import net.cbtltd.json.reservation.ReservationWidgetItem;
import net.cbtltd.json.review.ReviewWidgetItem;
import net.cbtltd.json.review.ReviewWidgetItems;
import net.cbtltd.json.text.TextWidgetItem;
import net.cbtltd.rest.paygate.PaygateHandler;
import net.cbtltd.server.JournalService;
import net.cbtltd.server.MonitorService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.ReservationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.SessionService;
import net.cbtltd.server.WebService;
import net.cbtltd.server.api.EventMapper;
import net.cbtltd.server.api.FinanceMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.server.api.RateMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.api.TextMapper;
import net.cbtltd.shared.Account;
import net.cbtltd.shared.Country;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Data;
import net.cbtltd.shared.Entity;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Event;
import net.cbtltd.shared.Finance;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.Journal;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Serial;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.api.HasItem;
import net.cbtltd.shared.api.HasTable;
import net.cbtltd.shared.price.WidgetPriceTable;
import net.cbtltd.shared.rate.RateNoteTable;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.jdom.Element;

public class SharedService {
	private static final Logger LOG = Logger.getLogger(SharedService.class.getName());
	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd"); //MySQL date format
	private static final String WIDGET_DESCRIPTION = "0";
	private static final String WIDGET_CONTENTS = "1";
	private static final String WIDGET_OPTIONS = "2";
	private static final String WIDGET_CONDITIONS = "3";
	private static final String WIDGET_MANAGER = "4";

	/**
	 * Gets the availability of a property for a range of dates.
	 *
	 * @param pos the point of sale code.
	 * @param productid the ID of the property
	 * @param fromdate the date from which availability is to be shown.
	 * @param todate the date to which availability is to be shown.
	 * @return the availability of a property for a range of dates.
	 */
	public static final AvailableWidgetItem getAvailable(String pos, String productid, String fromdate, String todate) {
		Date timestamp = new Date();
		String message = 
				"?type=" + JSONRequest.AVAILABLE 
				+ "&pos=" + pos 
				+ "&productid=" + productid 
				+ "&fromdate=" + fromdate
				+ "&todate=" + todate
				;
		LOG.debug(message);
		if (productid == null || productid.isEmpty() || productid.length() > 10) {throw new RuntimeException("Invalid productid parameter");}
		if (fromdate == null || fromdate.isEmpty() || fromdate.length() != 10) {throw new RuntimeException("Invalid fromdate parameter");}
		if (todate == null || todate.isEmpty() || todate.length() != 10) {throw new RuntimeException("Invalid todate parameter");}

		final SqlSession sqlSession = RazorServer.openSession();
		AvailableWidgetItem result = new AvailableWidgetItem();
		try {
			getParty(sqlSession, pos);
			Reservation action = new Reservation();
			action.setProductid(productid);
			action.setFromdate(DF.parse(fromdate));
			action.setTodate(DF.parse(todate));
			LOG.debug(action);
			result.setAvailable(sqlSession.getMapper(ReservationMapper.class).available(action));
		} 
		catch (ParseException x) {throw new RuntimeException("Invalid date format");}
		catch (Throwable x) {
			result.setMessage(x.getMessage());
			LOG.error(x.getMessage());	
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Creates a reservation from the specified parameters.
	 *
	 * @param pos the point of sale code.
	 * @param productid the ID of the reserved property.
	 * @param fromdate the date from which to book.
	 * @param todate the date to which to book.
	 * @param emailaddress the email address of the guest.
	 * @param familyname the family name of the guets.
	 * @param firstname the first name of the guest.
	 * @param notes the guest notes or requests.
	 * @param cardholder the card holder name.
	 * @param cardnumber the card number.
	 * @param cardmonth the card month.
	 * @param cardyear the card year.
	 * @param cardcode the card code.
	 * @param amount the amount to be charged to the card.
	 * @param remotehost the remote host URL.
	 * @return the reservation.
	 */
	public static final BookWidgetItem getBook(
			String pos,
			String productid,
			String fromdate,
			String todate,
			String emailaddress,
			String familyname,
			String firstname,
			String notes,
			String cardholder,
			String cardnumber,
			String cardmonth,
			String cardyear,
			String cardcode,
			String amount,
			String remotehost
			) {
		Date timestamp = new Date();

		String message = 
				"?type=" + JSONRequest.BOOK
				+ "&pos=" + pos 
				+ "&productid=" + productid 
				+ "&fromdate=" + fromdate 
				+ "&todate=" + todate 
				+ "&emailaddress=" + emailaddress 
				+ "&familyname=" + familyname 
				+ "&firstname=" + firstname
				+ "&notes=" + notes
				+ "&cardholder=" + cardholder
				+ "&cardnumber=" + cardnumber
				+ "&cardmonth=" + cardmonth
				+ "&cardyear=" + cardyear
				+ "&cardcode=" + cardcode
				+ "&amount=" + amount
				;
		LOG.debug(message);

		Double value = Double.valueOf(amount);
		if (pos == null || pos.isEmpty() || Model.decrypt(pos).length() > 10) {throw new RuntimeException("Invalid pos parameter");}
		if (productid == null || productid.isEmpty() || productid.length() > 10) {throw new RuntimeException("Invalid productid parameter");}
		if (fromdate == null || fromdate.isEmpty() || fromdate.length() != 10) {throw new RuntimeException("Invalid fromdate parameter");}
		if (todate == null || todate.isEmpty() || todate.length() != 10) {throw new RuntimeException("Invalid todate parameter");}
		if (emailaddress == null || emailaddress.isEmpty() || !Party.isEmailAddress(emailaddress)) {throw new RuntimeException("Invalid email address parameter");}
		if (familyname == null || familyname.isEmpty() || familyname.length() > 100) {throw new RuntimeException("Invalid familyname parameter");}
		if (value == null || value < 0.0) {throw new RuntimeException("Invalid amount parameter");}

		SqlSession sqlSession = RazorServer.openSession();
		BookWidgetItem result = new BookWidgetItem();
//		Double price = 0.0;
//		Double quote = 0.0;
		Double deposit = 100.;
		Reservation reservation = new Reservation();
		try {
			Party agent = getParty(sqlSession, pos);
			Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
			Party customer = getCustomer (sqlSession, emailaddress, familyname, firstname, product.getSupplierid(), agent);
			PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(Integer.valueOf(product.getSupplierid()));
			reservation.setActorid(Party.NO_ACTOR);
			reservation.setAgentid(agent.getId());
			reservation.setCustomerid(customer.getId());
			reservation.setOrganizationid(product.getSupplierid());
			reservation.setFromdate(DF.parse(fromdate));
			reservation.setTodate(DF.parse(todate));
			reservation.setDate(new Date());
			reservation.setDuedate(reservation.getDate());
			reservation.setDonedate(null);
			reservation.setArrivaltime(propertyManagerInfo.getCheckInTime().toString());
			reservation.setDeparturetime(propertyManagerInfo.getCheckOutTime().toString());
			reservation.setUnit(product.getUnit());
			reservation.setCurrency(product.getCurrency());
			reservation.setNotes(notes);
			reservation.setState(Reservation.State.Confirmed.name());
			reservation.setProductid(productid);
			boolean available = sqlSession.getMapper(ReservationMapper.class).available(reservation);
			if (!available) {throw new RuntimeException("The property is not available for these dates");}

			if (!customer.hasId(product.getOwnerid())) { // guest is not owner
				ReservationService.computePrice(sqlSession, reservation, null);
				deposit = ReservationService.getDeposit(sqlSession, reservation); //.getSupplierid(), reservation.getFromdate());
			}
			reservation.setDeposit(deposit);
			reservation.setCost(reservation.getQuote() * ReservationService.getDiscountfactor(sqlSession, reservation));
			reservation.setName(SessionService.pop(sqlSession, reservation.getOrganizationid(), Serial.RESERVATION));
			sqlSession.getMapper(ReservationMapper.class).create(reservation);

			if (value > 0.0) {
				Finance finance = getFinance(sqlSession, customer.getId(), cardholder, cardnumber, cardmonth, cardyear,	cardcode);
				reservation.setFinanceid(finance.getId());
				Element element = PaygateHandler.getXML(reservation.getName(), cardholder, cardnumber, cardmonth + cardyear, value, reservation.getCurrency(), cardcode, emailaddress, remotehost);
				String notesFromPaygate = getNotes(element);
				cardReceipt(sqlSession, reservation, notesFromPaygate, cardholder, value);
			}

			sqlSession.getMapper(ReservationMapper.class).update(reservation);

			result.setOrganizationid(reservation.getOrganizationid());
			result.setName(reservation.getName());
			result.setQuote(reservation.getQuote());
			result.setAmount(value);
			result.setCurrency(reservation.getCurrency());
			result.setState(RazorWidget.State.SUCCESS.name());
			MonitorService.update(sqlSession, Data.Origin.JQUERY, NameId.Type.Reservation, reservation);
			sqlSession.commit();
		}
		catch (ParseException x) {throw new RuntimeException("Invalid date format");}
		catch (NumberFormatException x) {throw new RuntimeException("Invalid number format");}
		catch (RuntimeException x) {
			sqlSession.rollback();
			result.setName(null);
			result.setQuote(0.0);
			result.setAmount(0.0);
			result.setCurrency(reservation.noCurrency() ? Currency.Code.USD.name() : reservation.getCurrency());
			result.setMessage(x.getMessage());
			result.setState(RazorWidget.State.FAILURE.name());
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets an array of dates when a property is not available.
	 *
	 * @param pos the point of sale code.
	 * @param productid the ID of the property
	 * @param date the date
	 * @return the array of dates when the property is not available.
	 */
	public static final CalendarWidgetItems getCalendar(String pos, String productid, String date) {
		Date timestamp = new Date();
		String message = 
				"?type=" + JSONRequest.CALENDAR 
				+ "&pos=" + pos 
				+ "&productid=" + productid 
				+ "&date=" + date
		;
		LOG.debug(message);

		if (productid == null || productid.isEmpty() || productid.length() > 10) {throw new RuntimeException("Invalid productid parameter");}
		if (date == null || date.isEmpty() || date.length() != 10) {throw new RuntimeException("Invalid date parameter");}

		SqlSession sqlSession = RazorServer.openSession();
		CalendarWidgetItems result = new CalendarWidgetItems();
		try {
			getParty(sqlSession, pos);
			Parameter action = new Parameter();
			action.setId(productid);
			action.setFromdate(date);
			DF.parse(date);
			ArrayList<CalendarWidgetItem> items = sqlSession.getMapper(ReservationMapper.class).calendarwidget(action);
			LOG.debug("action " + action + "\nitems " + items);
			if (items != null && !items.isEmpty()) {
				CalendarWidgetItem[] array = new CalendarWidgetItem[items.size()];
				items.toArray(array);
				result.setItems(array);
			}
		}
		catch (ParseException x) {throw new RuntimeException("Invalid date format");}
		catch (RuntimeException x) {
			result.setMessage(x.getMessage());
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets an array of images of a property.
	 *
	 * @param pos the point of sale code.
	 * @param productid the ID of the property
	 * @return the array of images of a property.
	 */
	public static final ImageWidgetItems getImage(String pos, String productid) {
		Date timestamp = new Date();
		String message = 
				"?type=" + JSONRequest.CALENDAR 
				+ "&pos=" + pos 
				+ "&productid=" + productid 
		;
		LOG.debug(message);

		if (productid == null || productid.isEmpty() || productid.length() > 10) {throw new RuntimeException("Invalid productid parameter");}

		SqlSession sqlSession = RazorServer.openSession();
		ImageWidgetItems result = new ImageWidgetItems();
		try {
			getParty(sqlSession, pos);
			Parameter action = new Parameter();
			action.setModel(NameId.Type.Product.name());
			action.setId(productid);
			ArrayList<ImageWidgetItem> items = sqlSession.getMapper(ProductMapper.class).imagewidget(action);
			LOG.debug("action " + action + "\nitems " + items);
			if (items != null && !items.isEmpty()) {
				ImageWidgetItem[] array = new ImageWidgetItem[items.size()];
				items.toArray(array);
				result.setItems(array);
			}
		}
		catch (RuntimeException x) {
			result.setMessage(x.getMessage());
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the latitude and longitude of the specified product, or a message on failure.
	 *
	 * @param pos the point of sale code.
	 * @param productid the ID of the property.
	 * @return the latitude and longitude, or error message.
	 */
	public static final MapWidgetItem getMap(String pos, String productid) {
		Date timestamp = new Date();

		String message = 
				"?type=" + JSONRequest.MAP
				+ "&pos=" + pos 
				+ "&productid=" + productid 
				;
		LOG.debug(message);

		if (productid == null || productid.isEmpty() || productid.length() > 10) {throw new RuntimeException("Invalid productid parameter");}

		SqlSession sqlSession = RazorServer.openSession();
		MapWidgetItem result = new MapWidgetItem();
		try {
			Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
			result.setProductid(product.getId());
			result.setLatitude(product.getLatitude());
			result.setLongitude(product.getLongitude());
		}
		catch (Throwable x) {
			result.setProductid(productid);
			result.setLatitude(0.0);
			result.setLongitude(0.0);
			result.setMessage(x.getMessage());
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the deposit amount and currency required to confirm a reservation, or a message if not available.
	 *
	 * @param pos the point of sale code.
	 * @param productid the ID of the property
	 * @param fromdate the date from which to book.
	 * @param todate the date to which to book.
	 * @param emailaddress the email address of the guest.
	 * @return the deposit amount and currency, or error message.
	 * the deposit amount and currency required.
	 */
	public static final BookWidgetItem getPriceAvailable(String pos, String productid, String fromdate, String todate, String emailaddress) {
		Date timestamp = new Date();

		String message = 
				"?type=" + JSONRequest.BOOK_AVAILABLE
				+ "&pos=" + pos 
				+ "&productid=" + productid 
				+ "&fromdate=" + fromdate 
				+ "&todate=" + todate 
				+ "&emailaddress=" + emailaddress 
				;
		LOG.debug(message);

		if (pos == null || pos.isEmpty() || Model.decrypt(pos).length() > 10) {throw new RuntimeException("Invalid pos parameter");}
		if (productid == null || productid.isEmpty() || productid.length() > 10) {throw new RuntimeException("Invalid productid parameter");}
		if (fromdate == null || fromdate.isEmpty() || fromdate.length() != 10) {throw new RuntimeException("Invalid fromdate parameter");}
		//		if (emailaddress == null || emailaddress.isEmpty() || !Party.isEmailAddress(emailaddress)) {throw new RuntimeException("Invalid emailaddress parameter");}

		SqlSession sqlSession = RazorServer.openSession();
		BookWidgetItem result = new BookWidgetItem();
		try {
			Party agent = getParty(sqlSession, pos);
			Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
			Party customer = emailaddress == null ? null : sqlSession.getMapper(PartyMapper.class).readbyemailaddress(emailaddress);
			Reservation reservation = new Reservation();
			reservation.setAgentid(agent.getId());
			reservation.setOrganizationid(product.getSupplierid());
			reservation.setFromdate(DF.parse(fromdate));
			reservation.setTodate(DF.parse(todate));
			reservation.setDate(new Date());
			reservation.setDuedate(reservation.getDate());
			reservation.setUnit(product.getUnit());
			reservation.setCurrency(product.getCurrency());
			reservation.setProductid(productid);

			boolean available = sqlSession.getMapper(ReservationMapper.class).available(reservation);
			if (!available) {throw new RuntimeException("The property is not available for these dates");}

			Double deposit = 100.;
			if (customer == null || !customer.hasId(product.getOwnerid())) { // guest is not owner
				ReservationService.computePrice(sqlSession, reservation, null);
				deposit = ReservationService.getDeposit(sqlSession, reservation); //.getSupplierid(), reservation.getFromdate());
			}
			reservation.setDeposit(deposit);
			reservation.setCost(reservation.getQuote() * ReservationService.getDiscountfactor(sqlSession, reservation));

			result.setQuote(reservation.getQuote());
			result.setAmount(reservation.getDeposit(reservation.getQuote()));
			result.setCurrency(reservation.getCurrency());
			result.setState(RazorWidget.State.SUCCESS.name());
		}
		catch (ParseException x) {throw new RuntimeException("Invalid date format");}
		catch (NumberFormatException x) {throw new RuntimeException("Invalid number format");}
		catch (Throwable x) {
			result.setQuote(0.0);
			result.setAmount(0.0);
			result.setCurrency(Currency.Code.USD.name());
			result.setMessage(x.getMessage());
			result.setState(RazorWidget.State.FAILURE.name());
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets a party's name using its email address, or a message if it does not exist.
	 *
	 * @param pos the point of sale code.
	 * @param emailaddress the email address of the guest.
	 * @return the party's name, or error message.
	 */
	public static final EmailWidgetItem getParty(String pos, String emailaddress) {
		Date timestamp = new Date();

		String message = 
				"?type=" + JSONRequest.PARTY
				+ "&pos=" + pos 
				+ "&emailaddress=" + emailaddress 
				;
		LOG.debug(message);

		if (emailaddress == null || emailaddress.isEmpty() || emailaddress.length() > 100) {throw new RuntimeException("Invalid productid parameter");}

		SqlSession sqlSession = RazorServer.openSession();
		EmailWidgetItem result = new EmailWidgetItem();
		try {
			Party party = sqlSession.getMapper(PartyMapper.class).readbyemailaddress(emailaddress);
			if (party != null) {
				result.setFamilyname(party.getFamilyName());
				result.setFirstname(party.getFirstName());
			}
		}
		catch (Throwable x) {
			result.setMessage(x.getMessage());
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Pays the specified amount and returns the target currency exchange rate, or a message on failure.
	 *
	 * @param pos the point of sale code.
	 * @param organizationid the organizationid
	 * @param financeid the financeid
	 * @param eventname the eventname
	 * @param amount the amount
	 * @param currency the currency
	 * @param tocurrency the tocurrency
	 * @param emailaddress the emailaddress
	 * @param cardholder the cardholder
	 * @param cardnumber the cardnumber
	 * @param cardmonth the cardmonth
	 * @param cardyear the cardyear
	 * @param cardcode the cardcode
	 * @param remotehost the remotehost
	 * @return the exchange rate, or error message.
	 */
	public static final PayWidgetItem getPay(
			String pos,
			String organizationid,
			String financeid,
			String eventname,
			String amount,
			String currency,
			String tocurrency,
			String emailaddress,
			String cardholder,
			String cardnumber,
			String cardmonth,
			String cardyear,
			String cardcode,
			String remotehost) {
		Date timestamp = new Date();

		String message = 
				"?type=" + JSONRequest.PAY
				+ "&pos=" + pos 
				+ "&organizationid=" + organizationid 
				+ "&financeid=" + financeid
				+ "&eventname=" + eventname
				+ "&amount=" + amount 
				+ "&currency=" + currency 
				+ "&tocurrency=" + tocurrency
				+ "&emailaddress=" + emailaddress
				+ "&cardholder=" + cardholder
				+ "&cardnumber=" + cardnumber
				+ "&cardmonth=" + cardmonth
				+ "&cardyear=" + cardyear
				+ "&cardcode=" + cardcode
				;
		System.out.println(message);
		LOG.debug(message);

		if (organizationid == null || organizationid.isEmpty() || organizationid.length() > 10) {throw new RuntimeException("Invalid organizationid parameter");}
		if (financeid == null || financeid.isEmpty() || financeid.length() > 10) {throw new RuntimeException("Invalid financeid parameter");}
		if (eventname == null || eventname.isEmpty() || eventname.length() > 10) {throw new RuntimeException("Invalid event name parameter");}
		if (currency == null || currency.isEmpty() || currency.length() > 3) {throw new RuntimeException("Invalid currency code parameter");}
		if (tocurrency == null || tocurrency.isEmpty() || tocurrency.length() > 3) {throw new RuntimeException("Invalid to currency code parameter");}
		Double value = Double.valueOf(amount);
		if (value == null || value <= 0.0) {throw new RuntimeException("Invalid amount parameter");}

		SqlSession sqlSession = RazorServer.openSession();
		PayWidgetItem result = new PayWidgetItem();
		try {
			Element element = PaygateHandler.getXML(eventname, cardholder, cardnumber, cardmonth + cardyear, value, Currency.Code.ZAR.name(), cardcode, emailaddress, remotehost);
			result.setMessage(getNotes(element));

			if (currency.equalsIgnoreCase(tocurrency)) {result.setAmount(1.0);}
			else {result.setAmount(WebService.getRate(sqlSession, tocurrency, currency, new Date()));}
			result.setCurrency(tocurrency);
			result.setState(RazorWidget.State.SUCCESS.name());
		}
		catch (Throwable x) {
			result.setState(RazorWidget.State.FAILURE.name());
			result.setMessage(x.getMessage());
			LOG.error(x.getMessage());
		}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the price list for the specified product and currency , or a message on failure.
	 *
	 * @param pos the point of sale code.
	 * @param productid the ID of the property.
	 * @param date the date from which the price list is required.
	 * @param rows the number of rows required.
	 * @param currency the currency of the prices.
	 * @return the price list, or error message.
	 */
	public static final PriceWidgetItems getPrice(
			String pos,
			String productid,
			String date,
			String rows,
			String currency) {
		Date timestamp = new Date();

		String message = 
				"?type=" + JSONRequest.PRICE 
				+ "&pos=" + pos 
				+ "&productid=" + productid 
				+ "&date=" + date
				+ "&rows=" + rows
				+ "&currency=" + currency
				;
		LOG.debug(message);

		if (date == null || date.isEmpty() || date.length() != 10) {date = DF.format(new Date());} //throw new RuntimeException("Invalid date parameter");}
		if (rows == null || rows.isEmpty() || rows.length() > 3) {rows = String.valueOf(6);} //throw new RuntimeException("Invalid rows parameter");}
		if (productid == null || productid.isEmpty()) {throw new RuntimeException("Invalid productid parameter");}
		if (currency == null || currency.isEmpty() || currency.length() != 3) {currency = Currency.Code.USD.name();} //throw new RuntimeException("Invalid currency parameter");}

		SqlSession sqlSession = RazorServer.openSession();
		PriceWidgetItems result = new PriceWidgetItems();
		try {
			getParty(sqlSession, pos);

			ArrayList<PriceWidgetItem> items = sqlSession.getMapper(PriceMapper.class).pricewidget(new WidgetPriceTable(NameId.Type.Product.name(), productid, DF.parse(date), currency, Price.DATE, 0, Integer.valueOf(rows)));
			LOG.debug("items " + items);
			if (items != null && !items.isEmpty()) {
				String fromcurrency = items.get(0).getCurrency();
				Double exchangerate = 1.0;
				if (!fromcurrency.equalsIgnoreCase(currency)) {exchangerate = WebService.getRate(sqlSession, fromcurrency, currency, new Date());}
				PriceWidgetItem[] array = new PriceWidgetItem[items.size()];
				int index = 0;
				for (PriceWidgetItem item : items) {
					item.setPrice(item.getPrice() * exchangerate);
					item.setMinimum(item.getMinimum() * exchangerate);
					item.setCurrency(currency);
					array[index++] = item;
				}
				result.setItems(array);
			}
		}
		catch (Throwable x) {
			result.setMessage(x.getMessage());
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets an array of product name ID pairs, or a message on failure.
	 *
	 * @param pos the pos
	 * @param productid the productid
	 * @return the array of product name ID pairs, or error message.
	 */
	public static final NameIdWidgetItems getProduct(
		String pos,
		String productid) {
		Date timestamp = new Date();

		String message = 
				"?type=" + JSONRequest.PRODUCT 
				+ "&pos=" + pos 
				+ "&productid=" + productid
				;
		LOG.debug(message);

		if (productid == null || productid.isEmpty()) {throw new RuntimeException("Invalid productid parameter");}

		SqlSession sqlSession = RazorServer.openSession();
		NameIdWidgetItems result = new NameIdWidgetItems();
		try {
			getParty(sqlSession, pos);
			ArrayList<NameIdWidgetItem> items = sqlSession.getMapper(ProductMapper.class).jsonnameids(productid.split(","));
			if (items == null || items.isEmpty()) {result.setMessage("Invalid or offline productid parameter");}
			else {
				LOG.debug("items " + items);
				NameIdWidgetItem[] array = new NameIdWidgetItem[items.size()];
				items.toArray(array);
				result.setItems(array);
			}
		}
		catch (Throwable x) {
			result.setMessage(x.getMessage());
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the standard and quoted prices for the specified product and currency , or a message on failure.
	 *
	 * @param pos the pos
	 * @param productid the productid
	 * @param fromdate the fromdate
	 * @param todate the todate
	 * @param currency the currency
	 * @return the standard and quoted prices, or error message.
	 */
	public static final QuoteWidgetItem getQuote(
			String pos,
			String productid,
			String fromdate,
			String todate,
			String currency
			){
		Date timestamp = new Date();

		String message = 
				"?type=" + JSONRequest.QUOTE
				+ "&pos=" + pos 
				+ "&productid=" + productid 
				+ "&fromdate=" + fromdate 
				+ "&todate=" + todate
				;
		LOG.debug(message);

		if (productid == null || productid.isEmpty() || productid.length() > 10) {throw new RuntimeException("Invalid productid parameter");}
		if (fromdate == null || fromdate.isEmpty() || fromdate.length() != 10) {throw new RuntimeException("Invalid fromdate parameter");}
		if (todate == null || todate.isEmpty() || todate.length() != 10) {throw new RuntimeException("Invalid todate parameter");}

		SqlSession sqlSession = RazorServer.openSession();
		QuoteWidgetItem result = new QuoteWidgetItem();
		try {
			Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
			Reservation reservation = new Reservation();
			reservation.setOrganizationid(product.getSupplierid());
			reservation.setProductid(productid);
			reservation.setFromdate(DF.parse(fromdate));
			reservation.setUnit(Unit.DAY);
			reservation.setTodate(DF.parse(todate));
			ReservationService.computePrice(sqlSession, reservation, null);
			result.setPrice(reservation.getPrice());
			result.setQuote(reservation.getQuote());
			result.setCurrency(reservation.getCurrency());
			result.setDeposit(ReservationService.getDeposit(sqlSession, reservation)); //product.getSupplierid(), DF.parse(fromdate)));
			Double exchangerate = 1.0;
			if (currency != null && !currency.isEmpty() && currency.length() == 3 && !result.hasCurrency(currency)) {
				exchangerate = WebService.getRate(sqlSession, result.getCurrency(), currency, new Date());
				result.setPrice(result.getPrice() * exchangerate);
				result.setQuote(result.getQuote() * exchangerate);
				result.setCurrency(currency);
			}
			ReservationService.getCollisions(sqlSession, reservation);
			result.setAvailable(reservation.noCollisions());
//			result.setAvailable(sqlSession.getMapper(ReservationMapper.class).available(reservation));
		}
		catch (ParseException x) {throw new RuntimeException("Invalid date format");}
		catch (Throwable x) {
			result.setCurrency(Currency.Code.USD.name());
			result.setPrice(0.0);
			result.setQuote(0.0);
			result.setMessage(x.getMessage());
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Pays the specified amount to the licensor's account and returns the target currency exchange rate, or a message on failure.
	 *
	 * @param pos the pos
	 * @param organizationid the organizationid
	 * @param financeid the financeid
	 * @param eventname the eventname
	 * @param currency the currency
	 * @param tocurrency the tocurrency
	 * @param amount the amount
	 * @param emailaddress the emailaddress
	 * @param cardholder the cardholder
	 * @param cardnumber the cardnumber
	 * @param cardmonth the cardmonth
	 * @param cardyear the cardyear
	 * @param cardcode the cardcode
	 * @return the target currency exchange rate, or error message.
	 */
	public static final PayWidgetItem getReceive(
			String pos,
			String organizationid,
			String financeid,
			String eventname,
			String currency,
			String tocurrency,
			String amount,
			String emailaddress,
			String cardholder,
			String cardnumber,
			String cardmonth,
			String cardyear,
			String cardcode) {
		Date timestamp = new Date();

		String message = 
				"?type=" + JSONRequest.RECEIVE
				+ "&pos=" + pos 
				+ "&organizationid=" + organizationid 
				+ "&financeid=" + financeid
				+ "&eventname=" + eventname
				+ "&amount=" + amount 
				+ "&currency=" + currency 
				+ "&tocurrency=" + tocurrency
				+ "&emailaddress=" + emailaddress
				+ "&cardholder=" + cardholder
				+ "&cardnumber=" + cardnumber
				+ "&cardmonth=" + cardmonth
				+ "&cardyear=" + cardyear
				+ "&cardcode=" + cardcode
				;
		LOG.debug(message);

		Double value = Double.valueOf(amount);
		if (organizationid == null || organizationid.isEmpty() || organizationid.length() > 10) {throw new RuntimeException("Invalid organizationid parameter");}
		if (financeid == null || financeid.isEmpty() || financeid.length() > 10) {throw new RuntimeException("Invalid financeid parameter");}
		if (eventname == null || eventname.isEmpty() || eventname.length() > 10) {throw new RuntimeException("Invalid event name parameter");}
		if (currency == null || currency.isEmpty() || currency.length() > 3) {throw new RuntimeException("Invalid currency code parameter");}
		if (tocurrency == null || tocurrency.isEmpty() || tocurrency.length() > 3) {throw new RuntimeException("Invalid to currency code parameter");}
		if (value == null || value <= 0.0) {throw new RuntimeException("Invalid amount parameter");}

		SqlSession sqlSession = RazorServer.openSession();
		PayWidgetItem result = new PayWidgetItem();
		try {
			Element element = null; //PaygateService.getXML(eventname, cardholder, cardnumber, cardmonth + cardyear, amount, Currency.ZAR, cardcode, emailaddress, request.getRemoteHost());
			result.setMessage(getNotes(element));

			if (currency.equalsIgnoreCase(tocurrency)) {result.setAmount(1.0);}
			else {result.setAmount(WebService.getRate(sqlSession, tocurrency, currency, new Date()));}
			result.setCurrency(tocurrency);
			result.setState(RazorWidget.State.SUCCESS.name());
		}
		catch (Throwable x) {
			result.setState(RazorWidget.State.FAILURE.name());
			result.setMessage(x.getMessage());
			LOG.error(x.getMessage());
		}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the details of the specified reservation, or a message on failure.
	 *
	 * @param pos the pos
	 * @param reservationid the reservationid
	 * @return the details of the specified reservation, or error message.
	 */
	public static final ReservationWidgetItem getReservation(
		String pos,
		String reservationid) {
		Date timestamp = new Date();

		String message = 
				"?type=" + JSONRequest.RESERVATION
				+ "&pos=" + pos 
				+ "&reservationid=" + reservationid 
				;
		LOG.debug(message);

		if (reservationid == null || reservationid.isEmpty() || reservationid.length() > 10) {throw new RuntimeException("Invalid organizationid parameter");}

		SqlSession sqlSession = RazorServer.openSession();
		ReservationWidgetItem result = new ReservationWidgetItem();
		try {
			getParty(sqlSession, pos);
			Parameter action = new Parameter();
			action.setId(reservationid);
			result = sqlSession.getMapper(ReservationMapper.class).readwidget(action);
		}
		catch (Throwable x) {
			result.setMessage(x.getMessage());
			LOG.error(x.getMessage());
		}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets a payment response of a reservation, or a message on failure.
	 *
	 * @param pos the pos
	 * @param reservationid the reservationid
	 * @param emailaddress the emailaddress
	 * @param familyname the familyname
	 * @param notes the notes
	 * @param firstname the firstname
	 * @param cardholder the cardholder
	 * @param cardnumber the cardnumber
	 * @param cardmonth the cardmonth
	 * @param cardyear the cardyear
	 * @param cardcode the cardcode
	 * @param amount the amount
	 * @param remotehost the remotehost
	 * @return the payment response, or error message.
	 */
	public static final PayWidgetItem getReservationRS(
		String pos,
		String reservationid,
		String emailaddress,
		String familyname,
		String notes,
		String firstname,
		String cardholder,
		String cardnumber,
		String cardmonth,
		String cardyear,
		String cardcode,
		String amount,
		String remotehost) {
		Date timestamp = new Date();

		String message = 
				"?type=" + JSONRequest.RESERVATION_RS
				+ "&pos=" + pos 
				+ "&reservationid=" + reservationid 
				+ "&emailaddress=" + emailaddress 
				+ "&familyname=" + familyname 
				+ "&firstname=" + firstname
				+ "&notes=" + notes
				+ "&cardholder=" + cardholder
				+ "&cardnumber=" + cardnumber
				+ "&cardmonth=" + cardmonth
				+ "&cardyear=" + cardyear
				+ "&cardcode=" + cardcode
				+ "&amount=" + amount
		;
		LOG.debug(message);

		Double value = Double.valueOf(amount);
		if (pos == null || pos.isEmpty() || Model.decrypt(pos).length() > 10) {throw new RuntimeException("Invalid pos parameter");}
		if (reservationid == null || reservationid.isEmpty() || reservationid.length() > 10) {throw new RuntimeException("Invalid reservationid parameter");}
		if (emailaddress == null || emailaddress.isEmpty() || !Party.isEmailAddress(emailaddress)) {throw new RuntimeException("Invalid emailaddress parameter");}
		if (familyname == null || familyname.isEmpty() || familyname.length() > 100) {throw new RuntimeException("Invalid familyname parameter");}
		if (value == null || value <= 0.0) {throw new RuntimeException("Invalid amount parameter");}

		SqlSession sqlSession = RazorServer.openSession();
		PayWidgetItem result = new PayWidgetItem();
		try {
			Party agent = getParty(sqlSession, pos);
			Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(reservationid);
			Party customer = getCustomer (sqlSession, emailaddress, familyname, firstname, reservation.getOrganizationid(), agent);
			reservation.setCustomerid(customer.getId());
			Finance finance = getFinance(sqlSession, customer.getId(), cardholder, cardnumber, cardmonth, cardyear,	cardcode);
			reservation.setFinanceid(finance.getId());
			reservation.setNotes(notes);
			sqlSession.getMapper(ReservationMapper.class).update(reservation);

			Element element = PaygateHandler.getXML(reservation.getName(), cardholder, cardnumber, cardmonth + cardyear, value, reservation.getCurrency(), cardcode, emailaddress, remotehost);
			String notesFromPaygate = getNotes(element);
			
			Event<Journal> event = cardReceipt(sqlSession, reservation, notesFromPaygate, cardholder, value);
			result.setId(reservation.getOrganizationid());
			result.setName(event.getName());
			result.setState(RazorWidget.State.SUCCESS.name());
			result.setAmount(value);
			result.setCurrency(reservation.getCurrency());
			result.setMessage(getNotes(element));
			MonitorService.update(sqlSession, Data.Origin.JQUERY, NameId.Type.Reservation, reservation);
			sqlSession.commit();
		}
		catch (Throwable x) {
			sqlSession.rollback();
			result.setId(null);
			result.setName(null);
			result.setState(RazorWidget.State.FAILURE.name());
			result.setAmount(value == null ? 0.0 : value);
			result.setCurrency(Currency.Code.USD.name());
			result.setMessage(x.getMessage());
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets an array of guest reviews for the specified product, or a message on failure.
	 *
	 * @param pos the pos
	 * @param productid the productid
	 * @param rows the rows
	 * @return the array of guest reviews, or error message.
	 */
	public static final ReviewWidgetItems getReview(
		String pos,
		String productid,
		String rows) {
		Date timestamp = new Date();

		String message = 
				"?type=" + JSONRequest.REVIEW 
				+ "&pos=" + pos 
				+ "&productid=" + productid 
				+ "&rows=" + rows
				;
		LOG.debug(message);

		if (productid == null || productid.isEmpty() || productid.length() > 10) {throw new RuntimeException("Invalid productid parameter");}
		if (rows == null || rows.isEmpty() || rows.length() > 10) {rows = String.valueOf(6);} //throw new RuntimeException("Invalid rows parameter");}

		SqlSession sqlSession = RazorServer.openSession();
		ReviewWidgetItems result = new ReviewWidgetItems();
		try {
			getParty(sqlSession, pos);
			ArrayList<ReviewWidgetItem> items = sqlSession.getMapper(RateMapper.class).widgetreview(new RateNoteTable(productid, Event.DATE + HasTable.ORDER_BY_DESC, 0, Integer.valueOf(rows)));
			LOG.debug("getReview productid " + productid + "\nitems " + items);
			if (items != null && !items.isEmpty()) {
				ReviewWidgetItem[] array = new ReviewWidgetItem[items.size()];
				items.toArray(array);
				result.setItems(array);
			}
		}
		catch (Throwable x) {
			result.setMessage(x.getMessage());
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the text of the specified id and language, or a message on failure.
	 *
	 * @param pos the pos
	 * @param productid the productid
	 * @param language the language
	 * @param text the text
	 * @return the text, or error message.
	 */
	public static final TextWidgetItem getText(
		String pos,
		String productid,
		String language,
		String text) {
		Date timestamp = new Date();

		String message = 
				"?type=" + JSONRequest.TEXT
				+ "&pos=" + pos 
				+ "&productid=" + productid 
				+ "&language=" + language 
				+ "&text=" + text 
				;
		LOG.debug(message);

		if (productid == null || productid.isEmpty() || productid.length() > 10) {throw new RuntimeException("Invalid product id parameter");}
		if (language == null || language.isEmpty()) {language = Language.EN;} //throw new RuntimeException("Invalid text language parameter");}
		if (text == null || text.isEmpty() || text.length() > 2) {text = WIDGET_DESCRIPTION;} //throw new RuntimeException("Invalid text type parameter");}

		SqlSession sqlSession = RazorServer.openSession();
		TextWidgetItem result = new TextWidgetItem();
		try {
			String textid = getTextid(sqlSession, text, productid);
			LOG.debug("getText " + productid + " " + textid + " " + language);
			Text record = sqlSession.getMapper(TextMapper.class).readbyexample(new Text(textid, language));
			result.setId(textid);
			result.setLanguage(record.getLanguage());
			result.setMessage(record.getNotes());
		}
		catch (Throwable x) {
			result.setId(productid);
			result.setLanguage(language);
			result.setMessage(x.getMessage());
			LOG.error(x.getMessage());
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the text id for the specified type and product ID.
	 *
	 * @param sqlSession the current SQL session.
	 * @param type the text type.
	 * @param productid the product id.
	 * @return the textid
	 */
	public static final String getTextid(SqlSession sqlSession, String type, String productid) {
//		if (type.equalsIgnoreCase(WIDGET_CONTENTS)) {return Product.getContentsId(productid);}
//		else if (type.equalsIgnoreCase(WIDGET_OPTIONS)) {return Product.getOptionsId(productid);}
//		else if (type.equalsIgnoreCase(WIDGET_CONDITIONS)) {
//			Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
//			return Party.getContractId(product.getSupplierid());
//		}
//		else if (type.equalsIgnoreCase(WIDGET_MANAGER)) {
//			Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
//			return Party.getPublicId(product.getSupplierid());
//		}
//		else {return Product.getPublicId(productid);}
		return null;
	}

	/*
	 * Creates the journal event to record a credit card receipt.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to which the receipt is allocated.
	 * @param element the HTTP element containing the transaction details.
	 * @param cardholder the name of the credit card holder.
	 * @param amount the amount received in the currency of the reservation.
	 * @return the array of guest reviews, or error message.
	 */
	public static final Event<Journal> cardReceipt (
			SqlSession sqlSession, 
			Reservation reservation, 
			String notes, 
			String cardholder, 
			Double amount) {

		//-----------------------------------------------
		// Journal event
		//-----------------------------------------------
		Event<Journal> event = new Event<Journal>();
		event.setActivity(NameId.Type.Reservation.name());
		event.setActorid(Party.NO_ACTOR);
		event.setDate(new Date());
		event.setDuedate(event.getDate());
		event.setLocationid(Model.ZERO);
		event.setNotes(notes);
		event.setOrganizationid(reservation.getOrganizationid());
		event.setParentid(reservation.getId());
		event.setProcess(Event.Type.Receipt.name());
		event.setType(Event.ACCOUNTING);
		event.setState(Event.CREATED);

		Double fee = Event.round(amount * Finance.CBT_ZAR_FINANCE_FEE, 2);
		String description = "Reservation " + reservation.getName() + " paid by " + cardholder;

		//-----------------------------------------------
		// Organization - credit accounts receivable from guest 
		//-----------------------------------------------
		Journal journal = new Journal();
		journal.setAccountid(Account.ACCOUNTS_RECEIVABLE);
		journal.setAccountname(Account.ACCOUNTS_RECEIVABLE_NAME);
		journal.setCreditamount(amount);
		journal.setDebitamount(0.0);
		journal.setCurrency(reservation.getCurrency());
		journal.setDescription(description);
		journal.setEntityid(reservation.noCustomerid() ? reservation.getAgentid() : reservation.getCustomerid());
		journal.setEntitytype(Entity.Type.Party.name());
		journal.setLocationid(Model.ZERO);
		journal.setOrganizationid(reservation.getOrganizationid());
		journal.setQuantity(reservation.getDuration(Time.DAY));
		journal.setUnit(Time.DAY.name());
		journal.setUnitprice(amount - fee / reservation.getDuration(Time.DAY));
		event.addItem(journal);

		//-----------------------------------------------
		// Organization debit accounts receivable from licensor
		//-----------------------------------------------
		journal = new Journal();
		journal.setAccountid(Account.ACCOUNTS_RECEIVABLE);
		journal.setAccountname(Account.ACCOUNTS_RECEIVABLE_NAME);
		journal.setCreditamount(fee);
		journal.setDebitamount(amount);
		journal.setCurrency(reservation.getCurrency());
		journal.setDescription(description);
		journal.setEntityid(Party.CBT_LTD_PARTY);
		journal.setEntitytype(Entity.Type.Party.name());
		journal.setLocationid(Model.ZERO);
		journal.setOrganizationid(reservation.getOrganizationid());
		journal.setQuantity(reservation.getDuration(Time.DAY));
		journal.setUnit(Time.DAY.name());
		journal.setUnitprice(fee / reservation.getDuration(Time.DAY));
		event.addItem(journal);

		//-----------------------------------------------
		// Organization debit licensor fees
		//-----------------------------------------------
		journal = new Journal();
		journal.setAccountid(Account.CARD_MERCHANT_FEES);
		journal.setAccountname(Account.CARD_MERCHANT_FEES_NAME);
		journal.setCreditamount(0.0);
		journal.setDebitamount(fee);
		journal.setCurrency(reservation.getCurrency());
		journal.setDescription(description);
//		journal.setEntityid(Party.CBT_LTD_PARTY);
//		journal.setEntitytype(Entity.Type.Party.name());
		journal.setLocationid(Model.ZERO);
		journal.setOrganizationid(reservation.getOrganizationid());
		journal.setQuantity(reservation.getDuration(Time.DAY));
		journal.setUnit(Time.DAY.name());
		journal.setUnitprice(fee / reservation.getDuration(Time.DAY));
		event.addItem(journal);

		JournalService.journalUpdate(sqlSession, event);
		return event;
	}

	public static void onReservationStateChange(SqlSession sqlSession, Reservation reservation, Product product) {
		if (reservation.noState() 
				|| reservation.hasState(Reservation.State.Initial.name())
//				|| reservation.hadState(reservation.getState())
		) {return;}
		Event<HasItem> event = new Event<HasItem>();
		event.setActivity(NameId.Type.Reservation.name());
		event.setActorid(reservation.getActorid());
		event.setAmount(reservation.getQuote());
		event.setDate(new Date());
		event.setName(reservation.getName());
		event.setNotes("Property " + product.getName() + " from  " + DF.format(reservation.getFromdate()) + " to " + DF.format(reservation.getTodate()));
		event.setOrganizationid(reservation.getOrganizationid());
		event.setParentid(reservation.getId());
		event.setProcess("StateChange");
		event.setState(reservation.getState());
		event.setType(Event.LOGGING);
		sqlSession.getMapper(EventMapper.class).create(event);
	}
	/* Gets the customer party from the specified details.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param emailaddress the email address.
	 * @param familyname the family name.
	 * @param firstname the first name.
	 * @param organizationid the ID of the organization.
	 * @param agentid the ID of the agent.
	 * @return the party instance.
	 */
	private static final Party getCustomer (
			SqlSession sqlSession, 
			String emailaddress, 
			String familyname, 
			String firstname, 
			String organizationid, 
			Party agent) {
		Party party =  sqlSession.getMapper(PartyMapper.class).readbyemailaddress(emailaddress);
		if (party == null) {
			party = new Party();
			party.setEmailaddress(emailaddress);
			party.setName(familyname, firstname);
			party.setState(Party.CREATED);
			party.setCreatorid(agent == null ? organizationid : agent.getId());
			party.setCountry(agent == null ? Country.US : agent.getCountry());
			party.setCurrency(agent == null ? Currency.Code.USD.name() : agent.getCurrency());
			party.setLanguage(agent == null ? Language.EN : agent.getLanguage());
			sqlSession.getMapper(PartyMapper.class).create(party);
		}
		RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Customer.name(), organizationid, party.getId());
		RelationService.create(sqlSession, Relation.ORG_PARTY_ + Party.Type.Customer.name(), agent.getId(), party.getId());
//		RelationService.create(sqlSession, Relation.ORGANIZATION_PARTY, agentid, party.getId());
//		RelationService.create(sqlSession, Relation.ORGANIZATION_PARTY, organizationid, party.getId());
//		RelationService.create(sqlSession, Relation.PARTY_TYPE, party.getId(), Party.Type.Customer.name());
		return party;
	}

	/*
	 * Gets the credit card account from the card details.
	 *
	 * @param sqlSession the current SQL session.
	 * @param ownerid the ID of the card owner.
	 * @param cardholder the name of the credit card holder.
	 * @param cardnumber the credit card number.
	 * @param cardmonth the credit card expiry month.
	 * @param cardyear the credit card expiry year.
	 * @param cardcode the credit card CVV digits.
	 * @return the credit card account instance.
	 */
	private static final Finance getFinance(
			SqlSession sqlSession, 
			String ownerid,	
			String cardholder,
			String cardnumber,
			String cardmonth,
			String cardyear,
			String cardcode) {
		Finance finance = new Finance();
		finance.setOwnerid(ownerid);
		finance.setName(cardholder);
		finance.setAccountnumber(Model.encrypt(cardnumber));
		finance.setMonth(Model.encrypt(cardmonth));
		finance.setYear(Model.encrypt(cardyear));
		finance.setCode(Model.encrypt(cardcode));
		finance.setType(Finance.Type.Card.name());
		finance.setCurrency(Currency.Code.USD.name());
		sqlSession.getMapper(FinanceMapper.class).create(finance);
		return finance;
	}

	/* 
	 * Gets the notes from the specified Paygate response element.
	 * 
	 * @param element the specified Paygate response element.
	 * @return the notes.
	 */
	private static String getNotes(Element element) {
		if (element == null) {return "Test Transaction";}
		StringBuilder sb = new StringBuilder();
		sb.append( "Status: " + element.getAttributeValue( "stat" ) );
		sb.append( " Status Description: " + element.getAttributeValue( "sdesc" ) );
		sb.append( " Result: " + element.getAttributeValue( "res" ) );
		sb.append( " Result Description: " + element.getAttributeValue( "rdesc" ) );
		sb.append( " Transaction ID: " + element.getAttributeValue( "tid" ) );
		sb.append( " Reference: " + element.getAttributeValue( "cref" ) );
		sb.append( " Auth Code: " + element.getAttributeValue( "auth" ) );
		sb.append( " Card Type: " + element.getAttributeValue( "ctype" ) );
		return sb.toString();
	}

	/*
	 * Gets the party having the specified point of sale code.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param pos the specified point of sale code.
	 * @throws RuntimeException the exception raised by run time errors.
	 */
	private static final Party getParty(SqlSession sqlSession, String pos) throws RuntimeException {
		try {
			if (pos == null || pos.isEmpty()){throw new RuntimeException("There is no point of sale (pos) code");}
			String partyid = Model.decrypt(pos);
			Party party = sqlSession.getMapper(PartyMapper.class).read(partyid);
			if (party == null) {throw new ServiceException(Error.party_id, pos);}
//			Relation organization = RelationService.exists(sqlSession, Relation.PARTY_TYPE, partyid, Party.Type.Organization.name());
//			Relation agent = RelationService.exists(sqlSession, Relation.PARTY_TYPE, partyid, Party.Type.Agent.name());
//			if (organization == null && agent == null) {throw new ServiceException(Error.party_type, pos);}
			return party;
		}
		catch (Throwable x) {throw new RuntimeException("The point of sale (pos) code is not valid");}
	}
}
