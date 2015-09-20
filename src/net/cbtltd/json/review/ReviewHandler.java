package net.cbtltd.json.review;

import java.util.ArrayList;
import java.util.HashMap;

import net.cbtltd.json.Handler;
import net.cbtltd.json.JSONResponse;
import net.cbtltd.json.JSONService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.RateMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Event;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.api.HasTable;
import net.cbtltd.shared.rate.RateNoteTable;

import org.apache.ibatis.session.SqlSession;

/**
 * Handles the request for an array of guest reviews for the specified product.
 */
public class ReviewHandler implements Handler {

	public String service() {return JSONRequest.REVIEW.name();}

	/**
	 * Gets an array of guest reviews for the specified product, or a message on failure.
	 *
	 * @param the parameter map.
	 * @return the array of guest reviews, or error message.
	 */
	public JSONResponse execute(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 			// the point of sale code.
		String productid = parameters.get("productid"); // the ID of the product or property
		String rows = parameters.get("rows");			// the number of reviews

		if (productid == null || productid.isEmpty() || productid.length() > 10) {throw new ServiceException(Error.product_id, productid);}
		if (rows == null || rows.isEmpty() || rows.length() > 10) {rows = String.valueOf(6);} //throw new ServiceException(Error.rows_invalid);}

		SqlSession sqlSession = RazorServer.openSession();
		ReviewWidgetItems result = new ReviewWidgetItems();
		try {
			JSONService.getParty(sqlSession, pos);
			ArrayList<ReviewWidgetItem> items = sqlSession.getMapper(RateMapper.class).widgetreview(new RateNoteTable(productid, Event.DATE + HasTable.ORDER_BY_DESC, 0, Integer.valueOf(rows)));
			if (items == null || items.isEmpty()) {throw new ServiceException(Error.rate_data);}
			ReviewWidgetItem[] array = new ReviewWidgetItem[items.size()];
			items.toArray(array);
			result.setItems(array);
		}
		catch (Throwable x) {result.setMessage(x.getMessage());}
		finally {sqlSession.close();}
		return result;
	}	

}
