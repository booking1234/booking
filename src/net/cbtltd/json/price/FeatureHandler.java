package net.cbtltd.json.price;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import net.cbtltd.json.Handler;
import net.cbtltd.json.JSONResponse;
import net.cbtltd.json.JSONService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.WebService;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.price.WidgetPriceTable;

import org.apache.ibatis.session.SqlSession;

/**
 * Handles the request for the priced feature list for the specified product and currency.
 */
public class FeatureHandler implements Handler {

	public String service() {return JSONRequest.FEATURE.name();}

	/**
	 * Gets the priced feature list for the specified product and currency, or a message on failure.
	 *
	 * @param the parameter map.
	 * @return the price list, or error message.
	 */
	public JSONResponse execute(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 			// the point of sale code.
		String model = parameters.get("model"); 		// the type of the model
		String id = parameters.get("id"); 				// the ID of the model
		String date = parameters.get("date");			// the date from which the price list is required.
		String currency = parameters.get("currency");	// the currency of the prices.
		String type = parameters.get("type");			// the type of the features to be included (null = all).
		String start = parameters.get("start");			// the first row in the price list.
		String rows = parameters.get("rows");			// the the number of rows in the price list.

		if (model == null || model.isEmpty()) {throw new ServiceException(Error.model_type, model);}
		if (id == null || id.isEmpty()) {throw new ServiceException(Error.model_id, id);}
		if (date == null || date.isEmpty() || date.length() != 10) {date = JSONService.DF.format(new Date());}
		if (currency == null || currency.isEmpty() || currency.length() != 3) {currency = Currency.Code.USD.name();}
		if (start == null || start.isEmpty()) {start = "0";}
		if (rows == null || rows.isEmpty()) {rows = "1000";}

		SqlSession sqlSession = RazorServer.openSession();
		PriceWidgetItems result = new PriceWidgetItems();
		try {
			JSONService.getParty(sqlSession, pos);
			WidgetPriceTable action = new WidgetPriceTable(model, id, JSONService.DF.parse(date), currency, type, Integer.valueOf(start), Integer.valueOf(rows));
			ArrayList<PriceWidgetItem> items = sqlSession.getMapper(PriceMapper.class).featurewidget(action);
			if (items != null && !items.isEmpty()) {
				String fromcurrency = items.get(0).getCurrency();
				Double exchangerate = 1.0;
				if (!fromcurrency.equalsIgnoreCase(currency)) {exchangerate = WebService.getRate(sqlSession, fromcurrency, currency, new Date());}
				PriceWidgetItem[] array = new PriceWidgetItem[items.size()];
				int index = 0;
				for (PriceWidgetItem item : items) {
//					item.setCost(item.getCost() * exchangerate);
					item.setPrice(item.getPrice() * exchangerate);
					item.setCurrency(currency);
					array[index++] = item;
				}
				result.setItems(array);
			}
		}
		catch (Throwable x) {result.setMessage(x.getMessage());}
		finally {sqlSession.close();}
		return result;
	}	

}
