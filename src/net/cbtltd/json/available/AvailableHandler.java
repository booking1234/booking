package net.cbtltd.json.available;

import java.text.ParseException;
import java.util.HashMap;

import net.cbtltd.json.Handler;
import net.cbtltd.json.JSONResponse;
import net.cbtltd.json.JSONService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.Reservation;

import org.apache.ibatis.session.SqlSession;

/**
 * Handles the request for availability of a property for a range of dates.
 */
public class AvailableHandler implements Handler {

	public String service() {return JSONRequest.AVAILABLE.name();}

	/**
	 * Gets the availability of a property for a range of dates.
	 * @param the parameter map.
	 * @return the availability of a property for a range of dates.
	 */
	public JSONResponse execute(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 			// the point of sale code.
		String productid = parameters.get("productid"); // the ID of the property
		String fromdate = parameters.get("fromdate");	// the date from which availability is to be shown.
		String todate = parameters.get("todate");		// the date to which availability is to be shown.

		if (productid == null || productid.isEmpty() || productid.length() > 10) {throw new ServiceException(Error.product_id, productid);}
		if (fromdate == null || fromdate.isEmpty() || fromdate.length() != 10) {throw new ServiceException(Error.date_from, fromdate);}
		if (todate == null || todate.isEmpty() || todate.length() != 10) {throw new ServiceException(Error.date_to, todate);}

		final SqlSession sqlSession = RazorServer.openSession();
		AvailableWidgetItem result = new AvailableWidgetItem();
		try {
			JSONService.getParty(sqlSession, pos);
			Reservation action = new Reservation();
			action.setProductid(productid);
			action.setFromdate(JSONService.DF.parse(fromdate));
			action.setTodate(JSONService.DF.parse(todate));
			result.setAvailable(sqlSession.getMapper(ReservationMapper.class).available(action));
		} 
		catch (ParseException x) {throw new ServiceException(Error.date_format);}
		catch (Throwable x) {result.setMessage(x.getMessage());}
		finally {sqlSession.close();}
		return result;
	}	

}
