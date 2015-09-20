package net.cbtltd.json.nameid;

import java.util.ArrayList;
import java.util.HashMap;

import net.cbtltd.json.Handler;
import net.cbtltd.json.JSONResponse;
import net.cbtltd.json.JSONService;
import net.cbtltd.json.Parameter;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.CountryMapper;
import net.cbtltd.server.api.CurrencyMapper;
import net.cbtltd.server.api.LanguageMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Party;

import org.apache.ibatis.session.SqlSession;

/**
 * Handles the request for name ID pairs.
 */
public class NameIdHandler implements Handler {

	public String service() {return JSONRequest.NAMEID.name();}

	/**
	 * Gets the list of the model name ID pairs of an organization.
	 * @param the parameter map.
	 * @return the list of the model name ID pairs.
	 */
	public JSONResponse execute(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 		// the point of sale code.
		String model = parameters.get("model"); 	// the model type.
		String id = parameters.get("id"); 			// the model ID (list).

		if (model == null || model.isEmpty() || model.length() > 20) {throw new ServiceException(Error.model_type, model);}
		if (id == null || id.isEmpty()) {throw new ServiceException(Error.model_id, id);}

		SqlSession sqlSession = RazorServer.openSession();
		NameIdWidgetItems result = new NameIdWidgetItems();
		try {
			Party organization = JSONService.getParty(sqlSession, pos);
			Parameter action = new Parameter();
//			action.setOrganizationid(organization.getId());
			action.setModel(model);
			action.setId(id);
			ArrayList<NameIdWidgetItem> items = new ArrayList<NameIdWidgetItem>();
			if (action.hasModel(NameId.Type.Country.name())) {items = sqlSession.getMapper(CountryMapper.class).nameidwidget(action);}
			else if (action.hasModel(NameId.Type.Currency.name())) {items = sqlSession.getMapper(CurrencyMapper.class).nameidwidget(action);}
			else if (action.hasModel(NameId.Type.Language.name())) {items = sqlSession.getMapper(LanguageMapper.class).nameidwidget(action);}
			else if (action.hasModel(NameId.Type.Party.name())) {items = sqlSession.getMapper(PartyMapper.class).nameidwidget(action);}
			else if (action.hasModel(NameId.Type.Product.name())) {items = sqlSession.getMapper(ProductMapper.class).nameidwidget(action);}
			if (items == null || items.isEmpty()) {throw new ServiceException(Error.nameid_data);}
			NameIdWidgetItem[] array = new NameIdWidgetItem[items.size()];
			items.toArray(array);
			result.setItems(array);
		}
		catch (Throwable x) {result.setMessage(x.getMessage());}
		finally {sqlSession.close();}
		return result;
	}	

}
