package net.cbtltd.json.card;

import java.util.HashMap;

import net.cbtltd.client.widget.RazorWidget;
import net.cbtltd.json.Handler;
import net.cbtltd.json.JSONResponse;
import net.cbtltd.json.JSONService;
import net.cbtltd.rest.paygate.PaygateHandler;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.Reservation;

import org.apache.ibatis.session.SqlSession;
import org.jdom.Element;

/**
 * Handles the request to pay the specified amount and return the target currency exchange rate.
 */
public class CardHandler implements Handler {

	public String service() {return JSONRequest.PAY.name();}

	/**
	 * Pays the specified amount and returns the target currency exchange rate, or a message on failure.
	 *
	 * @param the parameter map.
	 * @return the exchange rate, or error message.
	 */
	public JSONResponse execute(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 						// the point of sale code.
		String model = parameters.get("model");						// model type to be credited with payment.
		String id = parameters.get("id");							// model ID to be credited with payment.
		String emailaddress = parameters.get("emailaddress"); 		// the email address of the card holder.
		String notes = parameters.get("notes");						// the payment notes.
		String cardholder = parameters.get("cardholder");			// the card holder name.
		String cardnumber = parameters.get("cardnumber");			// the card number.
		String cardmonth = parameters.get("cardmonth");				// the card expiry month.
		String cardyear = parameters.get("cardyear");				// the card expiry year.
		String cardcode = parameters.get("cardcode");				// the card CCV code.
		String amount = parameters.get("amount");					// the amount to be charged to the card.
		String remote_host = parameters.get("remote_host");			// the remote host URL.

		if (model == null || model.isEmpty() || model.length() > 15) {throw new ServiceException(Error.model_type, model);}
		if (id == null || id.isEmpty() || id.length() > 10) {throw new ServiceException(Error.model_id, id);}
		if (notes == null || notes.isEmpty()) {throw new ServiceException(Error.event_id, notes);}
		Double value = Double.valueOf(amount);
		if (value == null || value <= 0.0) {throw new ServiceException(Error.card_amount, String.valueOf(value));}

		SqlSession sqlSession = RazorServer.openSession();
		CardWidgetItem result = new CardWidgetItem();
		try {
			JSONService.getParty(sqlSession, pos);
			Element element = PaygateHandler.getXML(notes, cardholder, cardnumber, cardmonth + cardyear, value, Currency.Code.ZAR.name(), cardcode, emailaddress, remote_host);
//			Reservation reservation = new Reservation();
//			reservation.setId(id);
//			reservation.setCustomerid(customerid);
//			reservation.setName(name);
//			reservation.setNotes(notes);
//			JSONService.cardReceipt(sqlSession, reservation, element, cardholder, value);
//			result.setName(name);
			result.setState(RazorWidget.State.SUCCESS.name());
		}
		catch (Throwable x) {
			result.setState(RazorWidget.State.FAILURE.name());
			result.setMessage(x.getMessage());
		}
		return result;
	}	

}
