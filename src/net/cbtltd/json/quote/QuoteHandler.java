package net.cbtltd.json.quote;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import net.cbtltd.json.Handler;
import net.cbtltd.json.JSONResponse;
import net.cbtltd.json.JSONService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ReservationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.WebService;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Unit;

import org.apache.ibatis.session.SqlSession;

/**
 * Handles the request for the standard and quoted prices for the specified product and currency.
 */
public class QuoteHandler implements Handler {

	public String service() {return JSONRequest.QUOTE.name();}

	/**
	 * Gets the standard and quoted prices for the specified product and currency, or a message on failure.
	 *
	 * @param the parameter map.
	 * @return the standard and quoted prices, or error message.
	 */
	public JSONResponse execute(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 			// the point of sale code.
		String productid = parameters.get("productid"); // the ID of the property
		String fromdate = parameters.get("fromdate");	// the date from which to quote.
		String todate = parameters.get("todate");		// the date to which to quote.
		String currency = parameters.get("currency");	// the currency of the quote.

		if (productid == null || productid.isEmpty() || productid.length() > 10) {throw new ServiceException(Error.product_id, productid);}
		if (fromdate == null || fromdate.isEmpty() || fromdate.length() != 10) {throw new ServiceException(Error.date_from, fromdate);}
		if (todate == null || todate.isEmpty() || todate.length() != 10) {throw new ServiceException(Error.date_to, todate);}

		SqlSession sqlSession = RazorServer.openSession();
		QuoteWidgetItem result = new QuoteWidgetItem();
		try {
			JSONService.getParty(sqlSession, pos);
			Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
			Reservation reservation = new Reservation();
			reservation.setOrganizationid(product.getSupplierid());
			reservation.setProductid(productid);
			reservation.setFromdate(JSONService.DF.parse(fromdate));
			reservation.setUnit(Unit.DAY);
			reservation.setTodate(JSONService.DF.parse(todate));
			ReservationService.computePrice(sqlSession, reservation, null);
			result.setPrice(reservation.getPrice());
			result.setQuote(reservation.getQuote());
			result.setCurrency(reservation.getCurrency());
			JSONService.LOG.debug("getDeposit " + product.getSupplierid() + ", " + fromdate);
			result.setDeposit(ReservationService.getDeposit(sqlSession, reservation)); //product.getSupplierid(), JSONService.DF.parse(fromdate)));
			Double exchangerate = 1.0;
			if (currency != null && !currency.isEmpty() && currency.length() == 3 && !result.hasCurrency(currency)) {
				exchangerate = WebService.getRate(sqlSession, result.getCurrency(), currency, new Date());
				result.setPrice(result.getPrice() * exchangerate);
				result.setQuote(result.getQuote() * exchangerate);
				result.setCurrency(currency);
			}
			reservation.setCollisions(ReservationService.getCollisions(sqlSession, reservation));
			result.setAvailable(reservation.noCollisions());
//			Log.error("JSON quote available " + result.getAvailable() + " " + reservation.noCollisions() + " " + reservation.getCollisions() + " " + reservation);
//			result.setAvailable(sqlSession.getMapper(ReservationMapper.class).available(reservation));
		}
		catch (ParseException x) {throw new ServiceException(Error.date_format);}
		catch (Throwable x) {
			result.setCurrency(Currency.Code.USD.name());
			result.setPrice(0.0);
			result.setQuote(0.0);
			result.setMessage(x.getMessage());
		}
		finally {sqlSession.close();}
		return result;
	}
}
