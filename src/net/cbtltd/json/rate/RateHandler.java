package net.cbtltd.json.rate;

import java.util.ArrayList;
import java.util.HashMap;

import net.cbtltd.json.Handler;
import net.cbtltd.json.JSONResponse;
import net.cbtltd.json.JSONService;
import net.cbtltd.json.Parameter;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.RateMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.Party;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/**
 * Handles the request for availability of a property for a range of dates.
 */
public class RateHandler implements Handler {
	public static final Logger LOG = Logger.getLogger(RateHandler.class.getName());

	public String service() {return JSONRequest.RATE.name();}

	public JSONResponse execute(HashMap<String, String> parameters) {
		String method = parameters.get("method"); 		// the method to be used
		if (JSONService.Method.SET.name().equalsIgnoreCase(method)) {return set(parameters);}
		else {return get(parameters);}
	}

	/**
	 * Gets an array of reservation ratings, or a message on failure.
	 *
	 * @param the parameter map.
	 * @return the reservation ratings, or error message.
	 */
	public JSONResponse get(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 					// the point of sale code.
		String reservationid = parameters.get("reservationid"); // the ID of the reservation
		String type = parameters.get("type"); 					// the type of rating

		LOG.debug(pos + ", " + reservationid + ", " + type);
		if (reservationid == null || reservationid.isEmpty() || reservationid.length() > 10) {throw new ServiceException(Error.reservation_id, reservationid);}
		if (type == null || type.isEmpty() || type.length() > 20) {throw new ServiceException(Error.rate_type, type);}

		SqlSession sqlSession = RazorServer.openSession();
		RateWidgetItems result = new RateWidgetItems();
		try {
			Party organization = JSONService.getParty(sqlSession, pos);
			Parameter action = new Parameter();
			action.setOrganizationid(organization.getId());
			action.setId(reservationid);
			action.setType(type);
			ArrayList<RateWidgetItem> items = sqlSession.getMapper(RateMapper.class).ratewidget(action);
			LOG.debug("\nitems " + items);
			if (items == null || items.isEmpty()) {throw new ServiceException(Error.rate_data);} //TODO create empty
			RateWidgetItem[] array = new RateWidgetItem[items.size()];
			items.toArray(array);
			result.setItems(array);
		}
		catch (Throwable x) {result.setMessage(x.getMessage());}
		finally {sqlSession.close();}
		LOG.debug(result);
		return result;
	}
	
	/**
	 * Sets an array of reservation ratings, or a message on failure.
	 *
	 * @param the parameter map.
	 * @return the reservation ratings, or error message.
	 */
	public JSONResponse set(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 					// the point of sale code.
		String reservationid = parameters.get("reservationid"); // the ID of the reservation
		String type = parameters.get("type"); 					// the type of rating

		LOG.debug(pos + ", " + reservationid + ", " + type);
		if (reservationid == null || reservationid.isEmpty() || reservationid.length() > 10) {throw new ServiceException(Error.reservation_id, reservationid);}
		if (type == null || type.isEmpty() || type.length() > 20) {throw new ServiceException(Error.rate_type, type);}

		SqlSession sqlSession = RazorServer.openSession();
		RateWidgetItems result = new RateWidgetItems();
		try {
			Party organization = JSONService.getParty(sqlSession, pos);
			Parameter action = new Parameter();
			action.setOrganizationid(organization.getId());
			action.setId(reservationid);
			action.setType(type);
			ArrayList<RateWidgetItem> items = sqlSession.getMapper(RateMapper.class).ratewidget(action);
			LOG.debug("\nitems " + items);
			if (items == null || items.isEmpty()) {throw new ServiceException(Error.rate_data);} //TODO create empty
			RateWidgetItem[] array = new RateWidgetItem[items.size()];
			items.toArray(array);
			result.setItems(array);
		}
		catch (Throwable x) {result.setMessage(x.getMessage());}
		finally {sqlSession.close();}
		LOG.debug(result);
		return result;
	}
}
