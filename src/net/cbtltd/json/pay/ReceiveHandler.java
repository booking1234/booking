package net.cbtltd.json.pay;

import java.util.Date;
import java.util.HashMap;

import net.cbtltd.client.widget.RazorWidget;
import net.cbtltd.json.Handler;
import net.cbtltd.json.JSONResponse;
import net.cbtltd.json.JSONService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.WebService;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.JSONRequest;

import org.apache.ibatis.session.SqlSession;
import org.jdom.Element;

/**
 * Handles the request to pay the specified amount to the licensor's account and return the target currency exchange rate.
 */
public class ReceiveHandler implements Handler {

	public String service() {return JSONRequest.RECEIVE.name();}

	/**
	 * Pays the specified amount to the licensor's account and returns the target currency exchange rate, or a message on failure.
	 *
	 * @param the parameter map.
	 * @return the target currency exchange rate, or error message.
	 */
	public JSONResponse execute(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 			// the point of sale code.
		String organizationid = parameters.get("organizationid");	// the ID of the organization.
		String financeid = parameters.get("financeid");				// the ID of the bank account.
		String emailaddress = parameters.get("emailaddress"); 		// the email address of the payer.
		String eventname = parameters.get("eventname");				// the event reference.
		String amount = parameters.get("amount");					// the amount to be charged to the card.
		String currency = parameters.get("currency");				// the payment currency.
		String tocurrency = parameters.get("tocurrency");			// the card account currency.
		String cardholder = parameters.get("cardholder");			// the card holder name.
		String cardnumber = parameters.get("cardnumber");			// the card number.
		String cardmonth = parameters.get("cardmonth");				// the card expiry month.
		String cardyear = parameters.get("cardyear");				// the card expiry year.
		String cardcode = parameters.get("cardcode");				// the card CCV code.
		String remote_host = parameters.get("remote_host");			// the remote host URL.

		Double value = Double.valueOf(amount);
		if (organizationid == null || organizationid.isEmpty() || organizationid.length() > 10) {throw new ServiceException(Error.organization_id, organizationid);}
		if (financeid == null || financeid.isEmpty() || financeid.length() > 10) {throw new ServiceException(Error.finance_id, financeid);}
		if (eventname == null || eventname.isEmpty() || eventname.length() > 10) {throw new ServiceException(Error.event_id, eventname);}
		if (currency == null || currency.isEmpty() || currency.length() > 3) {throw new ServiceException(Error.currency_code, currency);}
		if (tocurrency == null || tocurrency.isEmpty() || tocurrency.length() > 3) {throw new ServiceException(Error.currency_code, tocurrency);}
		if (value == null || value <= 0.0) {throw new ServiceException(Error.card_amount, String.valueOf(value));}

		SqlSession sqlSession = RazorServer.openSession();
		PayWidgetItem result = new PayWidgetItem();
		try {
			JSONService.getParty(sqlSession, pos);
			Element element = null; //PaygateService.getXML(eventname, cardholder, cardnumber, cardmonth + cardyear, amount, Currency.ZAR, cardcode, emailaddress, request.getRemoteHost());
			result.setMessage(JSONService.getNotes(element));
			if (currency.equalsIgnoreCase(tocurrency)) {result.setAmount(1.0);}
			else {result.setAmount(WebService.getRate(sqlSession, tocurrency, currency, new Date()));}
			result.setCurrency(tocurrency);
			result.setState(RazorWidget.State.SUCCESS.name());
		}
		catch (Throwable x) {
			result.setState(RazorWidget.State.FAILURE.name());
			result.setMessage(x.getMessage());
		}
		return result;
	}	

}
