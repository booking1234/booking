package net.cbtltd.json.reservation;

import java.util.ArrayList;
import java.util.HashMap;

import net.cbtltd.client.widget.RazorWidget;
import net.cbtltd.json.Handler;
import net.cbtltd.json.JSONResponse;
import net.cbtltd.json.JSONService;
import net.cbtltd.json.Parameter;
import net.cbtltd.json.pay.PayWidgetItem;
import net.cbtltd.rest.paygate.PaygateHandler;
import net.cbtltd.server.MonitorService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ReservationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.EventMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Data;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Event;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.Journal;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Reservation;

import org.apache.ibatis.session.SqlSession;
import org.jdom.Element;

/**
 * Handles the request for details of a reservation.
 */
public class ReservationHandler implements Handler {

	public String service() {return JSONRequest.RESERVATION.name();}

	public JSONResponse execute(HashMap<String, String> parameters) {
		String method = parameters.get("method"); 		// the method to be used
		if (JSONService.Method.LISTBYPARENTID.name().equalsIgnoreCase(method)) {return listbyparentid(parameters);}
		else if (JSONService.Method.PAY.name().equalsIgnoreCase(method)) {return pay(parameters);}
		else if (JSONService.Method.SET.name().equalsIgnoreCase(method)) {return set(parameters);}
		else if (JSONService.Method.OFFLINE.name().equalsIgnoreCase(method)) {return offline(parameters);}
		else {return get(parameters);}
	}

	/**
	 * Gets the details of the specified reservation, or a message on failure.
	 *
	 * @param the parameter map.
	 * @return the details of the specified reservation, or error message.
	 */
	public JSONResponse get(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 			// the point of sale code.
		String id = parameters.get("id"); 				// the number of the reservation

		if (id == null || id.isEmpty() || id.length() > 10) {throw new ServiceException(Error.reservation_id, id);}

		SqlSession sqlSession = RazorServer.openSession();
		ReservationWidgetItem result = new ReservationWidgetItem();
		try {
			Party organization = JSONService.getParty(sqlSession, pos);
			Parameter action = new Parameter();
			action.setOrganizationid(organization.getId());
			action.setId(id);
			result = sqlSession.getMapper(ReservationMapper.class).readwidget(action);
			if (result == null) {throw new ServiceException(Error.reservation_id, id);}
			result.setBalance(sqlSession.getMapper(EventMapper.class).balancebyreservation(result.getId()));
		}
		catch (Throwable x) {result.setMessage(x.getMessage());}
		return result;
	}	

	/**
	 * Gets a list of reservations by parent ID, or a message on failure.
	 *
	 * @param the parameter map.
	 * @return the details of the specified reservation, or error message.
	 */
	public JSONResponse listbyparentid(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 			// the point of sale code.
		String parentid = parameters.get("parentid"); 	// the parent ID of the list

		if (parentid == null || parentid.isEmpty()) {throw new ServiceException(Error.reservation_bad, parentid);}

		SqlSession sqlSession = RazorServer.openSession();
		ReservationWidgetItems result = new ReservationWidgetItems();
		try {
			JSONService.getParty(sqlSession, pos);
			ArrayList<ReservationWidgetItem> items = sqlSession.getMapper(ReservationMapper.class).listbyparentid(parentid);
			if (items == null || items.isEmpty()) {throw new ServiceException(Error.rate_data);}
			ReservationWidgetItem[] array = new ReservationWidgetItem[items.size()];
			items.toArray(array);
			result.setItems(array);
		}
		catch (Throwable x) {result.setMessage(x.getMessage());}
		return result;
	}	

	/**
	 * Updates the details of the requested reservation, or a message on failure.
	 *
	 * @param the parameter map.
	 * @return the details of the specified reservation, or error message.
	 */
	public JSONResponse offline(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 					// the point of sale code.
		String id = parameters.get("id"); 						// the number of the reservation
		String quote = parameters.get("quote"); 				// the quoted price of the reservation
		String cost = parameters.get("cost"); 					// the STO cost of the reservation
		String deposit = parameters.get("deposit"); 			// the deposit % to confirm the reservation
		String termsaccepted = parameters.get("termsaccepted"); // true if the reservation is accepted
		String notes = parameters.get("notes"); 				// the reservation notes

		if (id == null || id.isEmpty() || id.length() > 10) {throw new ServiceException(Error.reservation_id, id);}

		SqlSession sqlSession = RazorServer.openSession();
		ReservationWidgetItem result = new ReservationWidgetItem();
		try {
			Party organization = JSONService.getParty(sqlSession, pos);
			Reservation reservation = new Reservation();
			reservation.setOrganizationid(organization.getId());
			reservation.setId(id);
			reservation = sqlSession.getMapper(ReservationMapper.class).readbyorganization(reservation);
			if (reservation == null || !organization.hasId(reservation.getOrganizationid())) {throw new ServiceException(Error.reservation_bad, id);}
			//if (reservation == null) {throw new ServiceException(Error.reservation_id, id);}
			reservation.setQuote(Double.valueOf(quote));
			reservation.setCost(Double.valueOf(cost));
			reservation.setDeposit(Double.valueOf(deposit));
			reservation.setNotes(notes);
			reservation.setState(Boolean.valueOf(termsaccepted) ? Reservation.State.Confirmed.name() : Reservation.State.Final.name());
			ReservationService.offline(sqlSession, reservation, Boolean.valueOf(termsaccepted));
			result.setOrganizationid(organization.getId());
			result.setId(id);
			result.setState(reservation.getState()); //TODO handle in offline.js
		}
		catch (Throwable x) {result.setMessage(x.getMessage());}
		return result;
	}	

	/**
	 * Pays for a reservation, or a message on failure.
	 *
	 * @param the parameter map from JSON client:
				pos: pos,
				reservationid: reservationid,
				date: $.datepicker.formatDate("yy-mm-01", new Date()),
				emailaddress: $("#cbt_emailaddress").val(),
				cardholder: $("#cbt_cardholder").val(),
				cardnumber: $("#cbt_cardnumber").val(),
				cardmonth: $("#cbt_cardmonth").val(),
				cardyear: $("#cbt_cardyear").val(),
				cardcode: $("#cbt_cardcode").val(),
				amount: $("#cbt_cardamount").val()
	 * @return error message.
	 */
	public JSONResponse pay(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 						// the point of sale code.
		String reservationid = parameters.get("reservationid");		// the ID of the reservation.
		String date = parameters.get("date"); 						// the date of the payment.
		String emailaddress = parameters.get("emailaddress"); 		// the email address of the payer.
		String cardholder = parameters.get("cardholder");			// the card holder name.
		String cardnumber = parameters.get("cardnumber");			// the card number.
		String cardmonth = parameters.get("cardmonth");				// the card expiry month.
		String cardyear = parameters.get("cardyear");				// the card expiry year.
		String cardcode = parameters.get("cardcode");				// the card CCV code.
		String amount = parameters.get("amount");					// the amount to be charged to the card.
		String remote_host = parameters.get("remote_host");			// the remote host URL.

		Double value = Double.valueOf(amount);
		if (pos == null || pos.isEmpty() || Model.decrypt(pos).length() > 10) {throw new ServiceException(Error.pos_invalid, pos);}
		if (reservationid == null || reservationid.isEmpty() || reservationid.length() > 10) {throw new ServiceException(Error.reservation_id, reservationid);}
		if (emailaddress == null || emailaddress.isEmpty() || !Party.isEmailAddress(emailaddress)) {throw new ServiceException(Error.party_emailaddress, emailaddress);}
		if (cardholder == null || cardholder.isEmpty() || cardholder.length() > 100) {throw new ServiceException(Error.card_holder, cardholder);}
		if (cardnumber == null || cardnumber.isEmpty() || cardnumber.length() > 20) {throw new ServiceException(Error.card_number, cardnumber);}
		if (cardmonth == null || cardmonth.isEmpty() || cardmonth.length() != 2) {throw new ServiceException(Error.card_month, cardmonth);}
		if (cardyear == null || cardyear.isEmpty() || cardyear.length() != 4) {throw new ServiceException(Error.card_year, cardyear);}
		if (cardcode == null || cardcode.isEmpty() || cardcode.length() < 3) {throw new ServiceException(Error.card_code, cardcode);}
		if (value == null || value <= 0.0) {throw new ServiceException(Error.card_amount, amount);}

		SqlSession sqlSession = RazorServer.openSession();
		PayWidgetItem result = new PayWidgetItem();
		try {
			JSONService.getParty(sqlSession, pos);
			Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(reservationid);
//			Party customer = JSONService.getCustomer (sqlSession, emailaddress, familyname, firstname, reservation.getOrganizationid(), agent.getId());
//			reservation.setCustomerid(customer.getId());
//			Finance finance = JSONService.getFinance(sqlSession, customer.getId(), cardholder, cardnumber, cardmonth, cardyear,	cardcode);
//			reservation.setFinanceid(finance.getId());
//			reservation.setNotes(notes);
//			sqlSession.getMapper(ReservationMapper.class).update(reservation);

			Element element = PaygateHandler.getXML(reservation.getName(), cardholder, cardnumber, cardmonth + cardyear, value, reservation.getCurrency(), cardcode, emailaddress, remote_host);

			Event<Journal> event = JSONService.cardReceipt(sqlSession, reservation, element, cardholder, value);
			result.setId(reservation.getOrganizationid());
			result.setName(event.getName());
			result.setState(RazorWidget.State.SUCCESS.name());
			result.setAmount(value);
			result.setCurrency(reservation.getCurrency());
			result.setMessage(JSONService.getNotes(element));
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
		}
		finally {sqlSession.close();}
		return result;
	}
	
	/**
	 * Sets fields of a reservation, or a message on failure.
	 *
	 * @param the parameter map.
			pos: pos,
			reservationid: reservationid, //$("body").data("amount"),
			arrivaltime: $("#cbt_arrival_hour").val() + ":" + $("#cbt_arrival_minute").val() + ":00",
			departuretime: $("#cbt_departure_hour").val() + ":" + $("#cbt_departure_minute").val() + ":00",
			emailaddress: $("#cbt_emailaddress_input").val(),
			servicefrom: $("#cbt_service_hour").val() + ":" + $("#cbt_service_minute").val() + ":00",
			mobilephone: $("#cbt_mobilephone").val(),
			notes: $("#cbt_notes").val(),
			termsaccepted: $("#cbt_termsaccepted").val()
	 * @return the exchange rate, or error message.
	 */
	public JSONResponse set(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 						// the point of sale code.
		String reservationid = parameters.get("reservationid");		// the ID of the reservation.
		String arrivaltime = parameters.get("arrivaltime"); 		// the email address of the payer.
		String departuretime = parameters.get("departuretime"); 	// the departure time.
		String emailaddress = parameters.get("emailaddress"); 		// the guest's emailaddress.
		String servicefrom = parameters.get("servicefrom");			// the preferred service time.
		String mobilephone = parameters.get("mobilephone");			// the guest's mobile phone number.
		String notes = parameters.get("notes"); 					// the reservation notes.
		String termsaccepted = parameters.get("termsaccepted");		// reservation terms accepted

		if (pos == null || pos.isEmpty() || Model.decrypt(pos).length() > 10) {throw new ServiceException(Error.pos_invalid, pos);}
		if (reservationid == null || reservationid.isEmpty() || reservationid.length() > 10) {throw new ServiceException(Error.reservation_id, reservationid);}
//		if (emailaddress == null || emailaddress.isEmpty() || !Party.isEmailAddress(emailaddress)) {throw new ServiceException(Error.party_emailaddress, emailaddress);}

		SqlSession sqlSession = RazorServer.openSession();
		PayWidgetItem result = new PayWidgetItem();
		try {
			Party agent = JSONService.getParty(sqlSession, pos);
			Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(reservationid);
//			Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
			reservation.setArrivaltime(arrivaltime);
			reservation.setDeparturetime(departuretime);
			reservation.setServicefrom(servicefrom);
			reservation.setTermsaccepted(termsaccepted == null ? null : Boolean.valueOf(termsaccepted));
			reservation.setNotes(notes);
			sqlSession.getMapper(ReservationMapper.class).update(reservation);
			MonitorService.update(sqlSession, Data.Origin.JQUERY, NameId.Type.Reservation, reservation);
			sqlSession.commit();
		}
		catch (Throwable x) {
			sqlSession.rollback();
			result.setId(null);
			result.setName(null);
			result.setState(RazorWidget.State.FAILURE.name());
			result.setCurrency(Currency.Code.USD.name());
			result.setMessage(x.getMessage());
		}
		finally {sqlSession.close();}
		return result;
	}
	
}

//private void test() {
//	try {
//		Class<?> c = Class.forName("net.cbtltd.shared.Reservation"); // say Reservation
//		Field[] fieldlist = c.getDeclaredFields();
//		for (int i = 0; i < fieldlist.length; i++) {
//			Field fld = fieldlist[i];
//			fld.getName();
//			fld.getDeclaringClass();
//			fld.getType();
//			int mod = fld.getModifiers();
//			Modifier.toString(mod);
//		}
//		Method[] m = c.getDeclaredMethods(); // can be static
//		for (int i = 0; i < m.length; i++)
//			System.out.println(m[i].toString());
//	} catch(Throwable x) {}
//}
//	private HashMap<String, String> getExtra(String classname, HashMap<String, String> parameters) {
//		HashMap<String, String> extra = new HashMap<String, String>();
//		for (String key : parameters.keySet()) {
//			if (!isField(classname, key)) {extra.put(key, parameters.get(key));}
//		}
//		return extra;
//	}
//	
//	private boolean isField(String classname, String fieldname) {
//		try {
//			Class<?> c = Class.forName(classname); // say "net.cbtltd.shared.Reservation"
//			Field[] fieldlist = c.getDeclaredFields();
//			for (int i = 0; i < fieldlist.length; i++) {
//				Field fld = fieldlist[i];
//				if (fld.getName().equalsIgnoreCase(fieldname)) {return true;}
//			}
//		} catch(Throwable x) {}
//		return false;
//	}
//
//	private ArrayList<String> getKeyvalues(SqlSession sqlSession, String model, String id) {
//	ArrayList<String> keyvalues = RelationService.read(sqlSession, model + " Value", id, null);
//	if (keyvalues != null) {
//		HashMap<String, String> values = new HashMap<String, String>();
//		for (String keyvalue : keyvalues) {
//			String[] args = keyvalue.split(Model.DELIMITER);
//			if (args.length == 2) {values.put(args[0], args[1]);}
//		}
//		Reservation reservation = new Reservation();
//		result.setBeverage(values.get(Reservation.Value.beverage.name()));
//	}
//}
//
//}
