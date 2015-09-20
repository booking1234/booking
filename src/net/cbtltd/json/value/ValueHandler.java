package net.cbtltd.json.value;

import java.util.ArrayList;
import java.util.HashMap;

import net.cbtltd.json.Handler;
import net.cbtltd.json.JSONResponse;
import net.cbtltd.json.JSONService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.Model;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/**
 * Handles the request for a key value pair.
 */
public class ValueHandler implements Handler {
	public static final Logger LOG = Logger.getLogger(ValueHandler.class.getName());

	public String service() {return JSONRequest.VALUE.name();}

	public JSONResponse execute(HashMap<String, String> parameters) {
		String method = parameters.get("method"); 		// the method to be used
		if (JSONService.Method.SET.name().equalsIgnoreCase(method)) {return set(parameters);}
		else {return get(parameters);}
	}

	/**
	 * Gets an array of key value pairs of a model.
	 *
	 * @param the parameter map.
	 * @return the array of key value pairs of a model.
	 */
	public JSONResponse get(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 		// the point of sale code.
		String model = parameters.get("model"); 	// the type of the model
		String id = parameters.get("id"); 			// the ID of the model

		if (model == null || model.isEmpty() || model.length() > 20) {throw new ServiceException(Error.model_type, model);}
		if (id == null || id.isEmpty() || id.length() > 10) {throw new ServiceException(Error.model_id, id);}

		SqlSession sqlSession = RazorServer.openSession();
		ValueWidgetItems result = new ValueWidgetItems();
		try {
			JSONService.getParty(sqlSession, pos);
			ArrayList<String> items = RelationService.read(sqlSession, model + " Value", id, null);
			if (items != null && !items.isEmpty()) {
				ValueWidgetItem[] array = new ValueWidgetItem[items.size()];
				int i = 0;
				for (String item : items) {array[i++] = new ValueWidgetItem(item);}
				result.setItems(array);
			}
		}
		catch (Throwable x) {result.setMessage(x.getMessage());}
		finally {sqlSession.close();}
		return result;
	}
	
	public JSONResponse set(HashMap<String, String> parameters) {
		LOG.debug(parameters);
		String pos = parameters.get("pos"); 		// the point of sale code.
		String model = parameters.get("model"); 	// the type of the model
		String id = parameters.get("id"); 			// the ID of the model
		String kvs = parameters.get("kvs"); 		// the key value pair string

		SqlSession sqlSession = RazorServer.openSession();
		ValueWidgetItems result = new ValueWidgetItems();
		try {
			JSONService.getParty(sqlSession, pos);
			String[] items = kvs.split(",");
			ValueWidgetItem[] array = new ValueWidgetItem[items.length];
			int i = 0;
			for (String item : items) {
				if (item != null && !item.isEmpty()) {
					RelationService.deletekey(sqlSession, model + " Value", id, item.split(Model.DELIMITER)[0]);
					RelationService.create(sqlSession, model + " Value", id, item);
					array[i] = new ValueWidgetItem(item);
				}
			}
			result.setItems(array);
			sqlSession.commit();
		}		
		catch (Throwable x) {result.setMessage(x.getMessage());}
		finally {sqlSession.close();}
		LOG.debug(result);
		return result;
	}
}
