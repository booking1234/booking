package net.cbtltd.json.calendar;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import net.cbtltd.json.Handler;
import net.cbtltd.json.JSONResponse;
import net.cbtltd.json.JSONService;
import net.cbtltd.json.Parameter;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.JSONRequest;

import org.apache.ibatis.session.SqlSession;

/**
 * Gets an array of dates when a property is not available.
 */
public class CalendarHandler implements Handler {

	public String service() {return JSONRequest.CALENDAR.name();}

	/**
	 * Gets an array of dates when a property is not available.
	 *
	 * @param the parameter map.
	 * @return the array of dates when the property is not available.
	 */
	public JSONResponse execute(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 				// the point of sale code.
		String productid = parameters.get("productid"); 	// the ID of the property
		String date = parameters.get("date");				// the date from which the calendar is to be shown.

		if (productid == null || productid.isEmpty() || productid.length() > 10) {throw new ServiceException(Error.product_id, productid);}
		if (date == null || date.isEmpty() || date.length() != 10) {throw new ServiceException(Error.date_invalid, date);}

		SqlSession sqlSession = RazorServer.openSession();
		CalendarWidgetItems result = new CalendarWidgetItems();
		try {
			JSONService.getParty(sqlSession, pos);
//			Available action = new Available();
//			action.setProductid(productid);
//			action.setFromdate(JSONService.DF.parse(date));
//			Table<AvailableItem> table = ReservationService.available(sqlSession, action);
//			ArrayList<CalendarWidgetItem> items = new ArrayList<CalendarWidgetItem>();
//			for (AvailableItem availableitem : table.getValue()) {
//				CalendarWidgetItem item = new CalendarWidgetItem();
//				item.setDate(availableitem.get);
//				item.setState(availableitem.getState());
//				items.add(item);
//			}
			Parameter action = new Parameter();
			action.setId(productid);
			action.setFromdate(date);
			JSONService.DF.parse(date);
			ArrayList<CalendarWidgetItem> items = sqlSession.getMapper(ReservationMapper.class).calendarwidget(action);
			if (items != null && !items.isEmpty()) {
				CalendarWidgetItem[] array = new CalendarWidgetItem[items.size()];
				items.toArray(array);
				result.setItems(array);
			}
		}
		catch (ParseException x) {throw new ServiceException(Error.date_format);}
		catch (Throwable x) {result.setMessage(x.getMessage());}
		finally {sqlSession.close();}
		return result;
	}
}
