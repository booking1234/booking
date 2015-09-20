package net.cbtltd.json.book;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import net.cbtltd.client.widget.RazorWidget;
import net.cbtltd.json.Handler;
import net.cbtltd.json.JSONResponse;
import net.cbtltd.json.JSONService;
import net.cbtltd.rest.paygate.PaygateHandler;
import net.cbtltd.server.EmailService;
import net.cbtltd.server.MonitorService;
import net.cbtltd.server.PartnerService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ReservationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.SessionService;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Data;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Finance;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Serial;
import net.cbtltd.shared.Time;

import org.apache.ibatis.session.SqlSession;
import org.jdom.Element;

/**
 * Handles the request for availability of a property for a range of dates.
 */
public class BookHandler implements Handler {

	public String service() {return JSONRequest.BOOK.name();}

	/**
	 * Gets the availability of a property for a range of dates.
	 * @param the parameter map.
	 * @return the availability of a property for a range of dates.
	 */
	public JSONResponse execute(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 					// the point of sale code.
		String productid = parameters.get("productid"); 		// the ID of the property
		String fromdate = parameters.get("fromdate");			// the date from which availability is to be shown.
		String todate = parameters.get("todate");				// the date to which availability is to be shown.
		String emailaddress = parameters.get("emailaddress"); 	// the email address of the guest.
		String familyname = parameters.get("familyname");		// the family name of the guest.
		String firstname = parameters.get("firstname");			// the first name of the guest.
		String notes = parameters.get("notes");					// the guest notes or requests.
		String cardholder = parameters.get("cardholder");		// the card holder name.
		String cardnumber = parameters.get("cardnumber");		// the card number.
		String cardmonth = parameters.get("cardmonth");			// the card expiry month.
		String cardyear = parameters.get("cardyear");			// the card expiry year.
		String cardcode = parameters.get("cardcode");			// the card CCV code.
		String amount = parameters.get("amount");				// the amount to be charged to the card.
		String remote_host = parameters.get("remote_host");		// the remote host URL.

		Double value = Double.valueOf(amount);
		if (pos == null || pos.isEmpty() || Model.decrypt(pos).length() > 10) {throw new ServiceException(Error.pos_invalid, pos);}
		if (productid == null || productid.isEmpty() || productid.length() > 10) {throw new ServiceException(Error.product_id, productid);}
		if (emailaddress == null || emailaddress.isEmpty() || !Party.isEmailAddress(emailaddress)) {throw new ServiceException(Error.party_emailaddress, emailaddress);}
		if (familyname == null || familyname.isEmpty() || familyname.length() > 100) {throw new ServiceException(Error.family_name, familyname);}
		if (fromdate == null || fromdate.isEmpty() || fromdate.length() != 10) {throw new ServiceException(Error.date_from, fromdate);}
		if (todate == null || todate.isEmpty() || todate.length() != 10) {throw new ServiceException(Error.date_to, todate);}

		SqlSession sqlSession = RazorServer.openSession();
		BookWidgetItem result = new BookWidgetItem();
		Double deposit = 100.;
		Reservation reservation = new Reservation();
		try {
			Party agent = JSONService.getParty(sqlSession, pos);
			Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
			Party customer = JSONService.getCustomer (sqlSession, emailaddress, familyname, firstname, product.getSupplierid(), "", agent);
			PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(Integer.valueOf(product.getSupplierid()));
			reservation.setActorid(Party.NO_ACTOR);
			reservation.setAgentid(agent.getId());
			reservation.setCustomerid(customer.getId());
			reservation.setOrganizationid(product.getSupplierid());
			reservation.setFromdate(JSONService.DF.parse(fromdate));
			reservation.setTodate(JSONService.DF.parse(todate));
			if (reservation.getDuration(Time.DAY) < 1.0) {throw new ServiceException(Error.date_range, fromdate + " - " + todate);}
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

			if (customer.hasId(product.getOwnerid())) {value = 0.0;} // guest is owner
			else {
				ReservationService.computePrice(sqlSession, reservation, null);
//				deposit = ReservationService.getDeposit(sqlSession, reservation.getSupplierid(), reservation.getFromdate());
				deposit = ReservationService.getDeposit(sqlSession, reservation);
				reservation.setDeposit(deposit);
				reservation.setCost(reservation.getQuote() * ReservationService.getDiscountfactor(sqlSession, reservation));
				if (value == null || value < 0.0) {throw new ServiceException(Error.card_amount, String.valueOf(value));}
			}

			boolean available = sqlSession.getMapper(ReservationMapper.class).available(reservation);
			if (!available) {throw new ServiceException(Error.product_not_available, reservation.getProductFromToDate());}
			reservation.setName(SessionService.pop(sqlSession, reservation.getOrganizationid(), Serial.RESERVATION));
			sqlSession.getMapper(ReservationMapper.class).create(reservation);

			if (value > 0.0) {
				Finance finance = JSONService.getFinance(sqlSession, customer.getId(), cardholder, cardnumber, cardmonth, cardyear,	cardcode);
				reservation.setFinanceid(finance.getId());
				Element element = PaygateHandler.getXML(reservation.getName(), cardholder, cardnumber, cardmonth + cardyear, value, reservation.getCurrency(), cardcode, emailaddress, remote_host);
				JSONService.cardReceipt(sqlSession, reservation, element, cardholder, value);
			}

//TODO: CJM			reservation.setCollisions(sqlSession.getMapper(ReservationMapper.class).collisions(reservation));
			reservation.setCollisions(ReservationService.getCollisions(sqlSession, reservation));
			if (reservation.hasCollisions()) {throw new ServiceException(Error.product_not_available, reservation.getProductFromToDate());}

			sqlSession.getMapper(ReservationMapper.class).update(reservation);

			if (product.hasAltpartyid()) {PartnerService.createReservation(sqlSession, reservation);}

			MonitorService.update(sqlSession, Data.Origin.JQUERY, NameId.Type.Reservation, reservation);
			sqlSession.commit();
			
			if (reservation.isActive() && product.noAltpartyid()) {EmailService.guestReservation(sqlSession, reservation);}

			result.setOrganizationid(reservation.getOrganizationid());
			result.setName(reservation.getName());
			result.setQuote(reservation.getQuote());
			result.setAmount(value);
			result.setCurrency(reservation.getCurrency());
			result.setState(RazorWidget.State.SUCCESS.name());
		}
		catch (ParseException x) {throw new ServiceException(Error.date_format);}
		catch (NumberFormatException x) {throw new ServiceException(Error.number_format);}
		catch (Throwable x) {
			sqlSession.rollback();
			result.setName(null);
			result.setQuote(0.0);
			result.setAmount(0.0);
			result.setCurrency(reservation.noCurrency() ? Currency.Code.USD.name() : reservation.getCurrency());
			result.setMessage(x.getMessage());
			result.setState(RazorWidget.State.FAILURE.name());
		}
		finally {sqlSession.close();}
		return result;
	}
}
