package net.cbtltd.json.party;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import net.cbtltd.json.Handler;
import net.cbtltd.json.JSONResponse;
import net.cbtltd.json.JSONResult;
import net.cbtltd.json.JSONService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.TextService;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.TextMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Text;

import org.apache.ibatis.session.SqlSession;
import org.mortbay.log.Log;

/**
 * Handles the request for details of a party.
 */
public class PartyHandler implements Handler {

	public String service() {return JSONRequest.PARTY.name();}

	public JSONResponse execute(HashMap<String, String> parameters) {
		String method = parameters.get("method"); 		// the method to be used
		if (JSONService.Method.EXISTS.name().equalsIgnoreCase(method)) {return exists(parameters);}
		else if (JSONService.Method.SET.name().equalsIgnoreCase(method)) {return set(parameters);}
		else {return get(parameters);}
	}

	/*
	 * Gets company details, or a message if not available.
	 *
	 * @param the parameter map.
	 * @return the company details, or error message.
	 */
	private JSONResponse exists(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 			// the point of sale code.
		String emailaddress = parameters.get("emailaddress"); 	// the email address of the party
		String productid = parameters.get("productid"); 	// the property ID to see if the party is its owner

		if (emailaddress == null || emailaddress.isEmpty() || emailaddress.length() > 100) {throw new ServiceException(Error.party_emailaddress, emailaddress);}

		SqlSession sqlSession = RazorServer.openSession();
		PartyWidgetItem result = new PartyWidgetItem();
		try {
			JSONService.getParty(sqlSession, pos);
			result = sqlSession.getMapper(PartyMapper.class).partywidgetexists(emailaddress);
			if (result == null) {throw new ServiceException(Error.party_id, emailaddress);}
			if (productid == null) {result.setOwner(false);}
			else {
				Product product = sqlSession.getMapper(ProductMapper.class).read(productid);
				result.setOwner(product != null && product.hasOwnerid(result.getId()));
			}
			
		}
		catch (Throwable x) {result.setMessage(x.getMessage());}
		finally {sqlSession.close();}
		return result;
	}
	
	/*
	 * Gets company details, or a message if not available.
	 *
	 * @param the parameter map.
	 * @return the company details, or error message.
	 */
	private JSONResponse get(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 	// the point of sale code.
		String id = parameters.get("id"); 		// the ID of the party

		if (id == null || id.isEmpty() || id.length() > 10) {throw new ServiceException(Error.party_id, id);}

		SqlSession sqlSession = RazorServer.openSession();
		PartyWidgetItem result = new PartyWidgetItem();
		try {
			JSONService.getParty(sqlSession, pos);
			result = sqlSession.getMapper(PartyMapper.class).partywidget(id);
			if (result == null) {throw new ServiceException(Error.party_id, id);}
			Text text = sqlSession.getMapper(TextMapper.class).read(Party.getPublicId(id));
			result.setNotes(text == null ? null : text.getNotes());
			text = sqlSession.getMapper(TextMapper.class).read(Party.getContractId(id));
			result.setTerms(text == null ? null : text.getNotes());
			Double value = sqlSession.getMapper(ProductMapper.class).commission(Party.getPublicId(id));
			result.setCommission(value == null ? 20.0 : value);
			value = sqlSession.getMapper(ProductMapper.class).discount(Party.getPublicId(id));
			result.setDiscount(value == null ? 20.0 : value);
		}
		catch (Throwable x) {result.setMessage(x.getMessage());}
		finally {sqlSession.close();}
		return result;
	}	

	/*
	 * Sets company details, or a message if in error.
	 *
	 * @param the parameter map.
	 * @return the company details, or error message.
	 */
	private JSONResponse set(HashMap<String, String> parameters) {
		String pos = parameters.get("pos"); 						// the point of sale code.
		String organizationid = parameters.get("organizationid"); 	// the ID of the party
		String name = parameters.get("name"); 						// the name of the party
		String emailaddress = parameters.get("emailaddress"); 		// the email address of the party
		String dayphone = parameters.get("dayphone"); 				// the work phone of the party
		String webaddress = parameters.get("webaddress"); 			// the party's web URL
		String commission = parameters.get("commission"); 			// the default manager's commission percentage
		String discount = parameters.get("discount"); 				// the default agent's commission percentage
		String notes = parameters.get("notes"); 					// the description of the party
		String terms = parameters.get("terms"); 					// the terms and conditions

		if (organizationid == null || organizationid.isEmpty() || organizationid.length() > 10) {throw new ServiceException(Error.party_id, organizationid);}
		if (name == null || name.isEmpty() || name.length() > 100) {throw new ServiceException(Error.party_name, name);}
		if (emailaddress == null || emailaddress.isEmpty() || emailaddress.length() > 100) {throw new ServiceException(Error.party_emailaddress, emailaddress);}

		SqlSession sqlSession = RazorServer.openSession();
		JSONResult result = new JSONResult();
		try {
			JSONService.getParty(sqlSession, pos);
			Party party = sqlSession.getMapper(PartyMapper.class).read(organizationid);
			party.setName(name);
			party.setEmailaddress(emailaddress);
			party.setDayphone(dayphone);
			party.setWebaddress(webaddress);
			sqlSession.getMapper(PartyMapper.class).update(party);
			if (notes != null && !notes.isEmpty()) {party.setPublicText(new Text(null, name, Text.Type.HTML, new Date(), notes, Language.EN));}
			if (terms != null && !terms.isEmpty()) {party.setContractText(new Text(null, name, Text.Type.HTML, new Date(), terms, Language.EN));}
			if (party.hasTexts()) {TextService.update(sqlSession, party.getTexts());}
			Product action = new Product();
			action.setOrganizationid(organizationid);
			ArrayList<Product> products = sqlSession.getMapper(ProductMapper.class).list(action);
			for (Product product : products) {
				//TODO: update product commission/discount
				product.setCommission(Double.valueOf(commission));
				product.setDiscount(Double.valueOf(discount));
				sqlSession.getMapper(ProductMapper.class).update(product);				
			}
			//result.setMessage("The data has been successfully updated.");
		}
		catch (Throwable x) {result.setMessage(x.getMessage());}
		finally {sqlSession.close();}
		return result;
	}
	}
