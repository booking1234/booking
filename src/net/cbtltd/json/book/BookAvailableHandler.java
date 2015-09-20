package net.cbtltd.json.book;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import net.cbtltd.client.widget.RazorWidget;
import net.cbtltd.json.Handler;
import net.cbtltd.json.JSONResponse;
import net.cbtltd.json.JSONService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ReservationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;

import org.apache.ibatis.session.SqlSession;

/**
 * Handles the deposit amount and currency required to confirm a reservation.
 */
public class BookAvailableHandler implements Handler {

	public String service() {return JSONRequest.BOOK_AVAILABLE.name();}

	/**
	 * Gets the deposit amount and currency required to confirm a reservation, or a message if not available.
	 *
	 * @param the parameter map.
	 * @return the deposit amount and currency, or error message.
	 */
	public JSONResponse execute(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 					// the point of sale code.
		String productid = parameters.get("productid"); 		// the ID of the property
		String fromdate = parameters.get("fromdate");			// the date from which availability is to be shown.
		String todate = parameters.get("todate");				// the date to which availability is to be shown.
		String emailaddress = parameters.get("emailaddress"); 	// the email address of the guest.

		if (pos == null || pos.isEmpty() || Model.decrypt(pos).length() > 10) {throw new ServiceException(Error.pos_invalid, pos);}
		if (productid == null || productid.isEmpty() || productid.length() > 10) {throw new ServiceException(Error.product_id, productid);}
		if (fromdate == null || fromdate.isEmpty() || fromdate.length() != 10) {throw new ServiceException(Error.date_from, fromdate);}
		//		if (emailaddress == null || emailaddress.isEmpty() || !Party.isEmailAddress(emailaddress)) {throw new ServiceException(Error.emailaddress_invalid);}

		SqlSession sqlSession = RazorServer.openSession();
		BookWidgetItem result = new BookWidgetItem();
		try {
			Party agent = JSONService.getParty(sqlSession, pos);
			Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
			Party customer = emailaddress == null ? null : sqlSession.getMapper(PartyMapper.class).readbyemailaddress(emailaddress);
			Reservation reservation = new Reservation();
			reservation.setAgentid(agent.getId());
			reservation.setOrganizationid(product.getSupplierid());
			reservation.setFromdate(JSONService.DF.parse(fromdate));
			reservation.setTodate(JSONService.DF.parse(todate));
			reservation.setDate(new Date());
			reservation.setDuedate(reservation.getDate());
			reservation.setUnit(product.getUnit());
			reservation.setCurrency(product.getCurrency());
			reservation.setProductid(productid);

			boolean available = sqlSession.getMapper(ReservationMapper.class).available(reservation);
			if (!available) {throw new ServiceException(Error.product_not_available, reservation.getProductFromToDate());}

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
		catch (ParseException x) {throw new ServiceException(Error.date_format);}
		catch (NumberFormatException x) {throw new ServiceException(Error.number_format);}
		catch (Throwable x) {
			result.setQuote(0.0);
			result.setAmount(0.0);
			result.setCurrency(Currency.Code.USD.name());
			result.setMessage(x.getMessage());
			result.setState(RazorWidget.State.FAILURE.name());
		}
		finally {sqlSession.close();}
		return result;
	}
}
