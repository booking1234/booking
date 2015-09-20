package net.cbtltd.json.image;

import java.util.ArrayList;
import java.util.HashMap;

import net.cbtltd.json.Handler;
import net.cbtltd.json.JSONResponse;
import net.cbtltd.json.JSONService;
import net.cbtltd.json.Parameter;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.JSONRequest;

import org.apache.ibatis.session.SqlSession;

/**
 * Handles the request for aan array of images of a model.
 */
public class ImageHandler implements Handler {

	public String service() {return JSONRequest.IMAGE.name();}

	/**
	 * Gets an array of images of a model.
	 *
	 * @param the parameter map.
	 * @return the array of images of a property.
	 */
	public JSONResponse execute(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 		// the point of sale code.
		String model = parameters.get("model"); 	// the type of the model
		String id = parameters.get("id"); 			// the ID of the model

		if (model == null || model.isEmpty() || model.length() > 20) {throw new ServiceException(Error.model_type, model);}
		if (id == null || id.isEmpty() || id.length() > 10) {throw new ServiceException(Error.model_id, id);}

		SqlSession sqlSession = RazorServer.openSession();
		ImageWidgetItems result = new ImageWidgetItems();
		try {
			JSONService.getParty(sqlSession, pos);
			Parameter action = new Parameter();
			action.setModel(model);
			action.setId(id);
			ArrayList<ImageWidgetItem> items = sqlSession.getMapper(ProductMapper.class).imagewidget(action);
			if (items == null || items.isEmpty()) {throw new ServiceException(Error.image_data, model + " " + id);}
			ImageWidgetItem[] array = new ImageWidgetItem[items.size()];
			items.toArray(array);
			result.setItems(array);
		}
		catch (Throwable x) {result.setMessage(x.getMessage());}
		finally {sqlSession.close();}
		return result;
	}
}
