package net.cbtltd.json.product;

import java.util.HashMap;

import net.cbtltd.json.Handler;
import net.cbtltd.json.JSONResponse;
import net.cbtltd.json.JSONService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.JSONRequest;

import org.apache.ibatis.session.SqlSession;

/**
 * Handles the request for details of a party.
 */
public class ProductHandler implements Handler {

	public String service() {return JSONRequest.PRODUCT.name();}

	/**
	 * Gets company details, or a message if not available.
	 *
	 * @param the parameter map.
	 * @return the company details, or error message.
	 */
	public JSONResponse execute(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 	// the point of sale code.
		String id = parameters.get("id"); 		// the ID of the product

		if (id == null || id.isEmpty() || id.length() > 10) {throw new ServiceException(Error.product_id, id);}

		SqlSession sqlSession = RazorServer.openSession();
		ProductWidgetItem result = new ProductWidgetItem();
		try {
			JSONService.getParty(sqlSession, pos);
			result = sqlSession.getMapper(ProductMapper.class).productwidget(id);
			if (result == null) {throw new ServiceException(Error.product_id, id);}
		}
		catch (Throwable x) {result.setMessage(x.getMessage());}
		finally {sqlSession.close();}
		return result;
	}	

}
