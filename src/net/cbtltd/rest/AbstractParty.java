/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	3.0.10
 */
package net.cbtltd.rest;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import net.cbtltd.server.LicenseService;
import net.cbtltd.server.MonitorService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.AttributeMapper;
import net.cbtltd.server.api.LocationMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.RelationMapper;
import net.cbtltd.server.api.TextMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.KeyValue;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Text;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/** 
 * The Class AbstractParty implements REST services for parties.
 */
public abstract class AbstractParty {

	private static final Logger LOG = Logger.getLogger(AbstractParty.class.getName());
	private static ArrayList<NameId> keyvalues = null;

	/**
	 * Gets the value name.
	 *
	 * @param sqlSession the current SQL session.
	 * @param id the id
	 * @return the value name
	 */
	private static String getValueName(SqlSession sqlSession, String id) {
		if (keyvalues == null) {keyvalues = sqlSession.getMapper(AttributeMapper.class).razorlist();}
		for (NameId keyvalue : keyvalues) {
			if (keyvalue.hasId(id)) {return keyvalue.getName();}
		}
		return null;
	}

	/**
	 * Check access and throw exception if not allowed.
	 *
	 * @param sqlSession the current SQL session.
	 * @param pos the POS code for access.
	 * @param partyid the party ID
	 * @return the party if successful.
	 * @throws Throwable the throwable
	 */
	private static final Party getParty(
			SqlSession sqlSession,
			String pos
		) throws Throwable {
		
		String partyid = Constants.decryptPos(pos);

		Party party = sqlSession.getMapper(PartyMapper.class).read(partyid);
		if (party == null) {throw new ServiceException(Error.party_id, partyid);}
		if (party.notState(Constants.CREATED)) {throw new ServiceException(Error.party_inactive, partyid);}

		LicenseService.checkAccess(sqlSession, null, partyid, null, License.Type.JSON_XML, License.PARTY_WAIT);
		LicenseService.setAccess(partyid, System.currentTimeMillis());

		return party;
	}

	
	/**
	 * Get POS by ID and ID by POS.
	 *
	 * @param pos to decrypt
	 * @param id to encrypt
	 * @return the value
	 */
	protected String getPosOrId(String pos, String id ){
		
		if (pos != null && !pos.isEmpty() ){
			return Constants.decryptPos(pos);
		}else if (id != null && !id.isEmpty()){
			return Model.encrypt(id);
		}
		
		return "Error: No data to encrypt or decrypt.";
	}
	
	/**
	 * Gets the name ids.
	 *
	 * @param numrows the maximum number of results
	 * @param pos the mandatory point of sale code
	 * @param version the optional version date
	 * @param xsl the optional style sheet
	 * @return the name id pairs
	 */
	protected Items getNameIds(
			String pos,
			Integer rows,
			String version,
			String xsl) {

		Date timestamp = new Date();
		String message = "/party/list?pos=" + pos + "&rows=" + rows + "&version=" + version + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Items result = null;
		try {
			String organizationid = getParty(sqlSession, pos).getId();
			Date date = Constants.parseDate(version);			
			NameIdAction action = new NameIdAction(Service.PARTY);
			action.setOrganizationid(organizationid);
			action.setNumrows(rows);
			action.setState(Constants.CREATED);
			action.setVersion(date);
			result = new Items(NameId.Type.Party.name(), null, null, null, sqlSession.getMapper(PartyMapper.class).nameidbyname(action), xsl);
		} 
		catch (Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new Items(NameId.Type.Party.name(), null, null, x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the types.
	 *
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the types
	 */
	protected Items getTypes(
			String pos,
			String xsl) {

		Date timestamp = new Date();
		String message = "/party/types?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Items result = null;
		try {
			String organizationid = getParty(sqlSession, pos).getId();
			LOG.debug("organizationid " + organizationid);
			NameIdAction action = new NameIdAction(Service.PARTY);
			action.setOrganizationid(organizationid);
			action.setNumrows(1000);
			LOG.debug("action " + action);
			result = new Items(NameId.Type.Party.name(), null, "Types", null, sqlSession.getMapper(PartyMapper.class).type(action), xsl);
			LOG.debug("result " + result);
		}
		catch (Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new Items(NameId.Type.Party.name(), null, "Types", message + " " + x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		result.setXsl(xsl);
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the name ids by type.
	 *
	 * @param type the type
	 * @param rows the numrows
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the name ids by type
	 */
	protected Items getNameIdsByType(
			String type,
			String pos,
			Integer rows,
			String version,
			String xsl) {

		Date timestamp = new Date();
		String message = "/party/list/" + type + "?pos=" + pos + "&rows=" + rows + "&version=" + version + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Items result = null;
		try {
			String organizationid = getParty(sqlSession, pos).getId();
			Date date = Constants.parseDate(version);
			NameIdAction action = new NameIdAction(Service.PARTY);
			action.setNumrows(rows);
			action.setOrganizationid(organizationid);
			action.setState(Constants.CREATED);
			action.setType(type);
			action.setVersion(date);
			Collection<NameId> item = sqlSession.getMapper(PartyMapper.class).nameidbyname(action);
			if (item == null) {throw new ServiceException(Error.type_invalid, type);}
			result = new Items(NameId.Type.Party.name(), null, type, null, item, xsl);
		} 
		catch (Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new Items(NameId.Type.Party.name(), null, type, x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}
	
	/**
	 * Gets organization name ids.
	 *
	 * @param type the type
	 * @param rows the numrows
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the name ids by type
	 */
	protected Items getPMNameIds(
			Integer rows,
			String xsl) {

		Date timestamp = new Date();
		String message = "/party/listorganizations/";
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Items result = null;
		try {
			Collection<NameId> item = sqlSession.getMapper(PartyMapper.class).propertymanagernameid();
			if (item == null) {throw new ServiceException(Error.type_invalid, "Organizations");}
			result = new Items(NameId.Type.Party.name(), null, "Organizations", null, item, xsl);
		} 
		catch (Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new Items(NameId.Type.Party.name(), null, "Organizations", x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the by id.
	 *
	 * @param partyid the partyid
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the by id
	 */
	protected Party getById(
			String partyid,
			String pos,
			String xsl) {

		Date timestamp = new Date();
		String message = "/party/" + partyid + "?xsl=" + xsl + "?pos=" + pos;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Party result = null;
		try {
			String organizationid = getParty(sqlSession, pos).getId();
			result = sqlSession.getMapper(PartyMapper.class).read(partyid);
			if (result == null) {throw new ServiceException(Error.party_id, partyid);}
			if (sqlSession.getMapper(RelationMapper.class).match(new Relation(Relation.ORG_PARTY_, organizationid, partyid)) == null) {result.setEmailaddress(null);}
			if (!result.hasState(Constants.CREATED)) {throw new ServiceException(Error.party_inactive, partyid);}
			if (result.hasLocationid()) {
				net.cbtltd.shared.Location location = sqlSession.getMapper(LocationMapper.class).read(result.getLocationid());
				result.setCity(location.getName());
				result.setRegion(location.getRegion());
				result.setCountry(location.getCountry());
			}
			result.setLanguage(Language.EN);
			result.setXsl(xsl);
			sqlSession.commit();
		}
		catch (Throwable x) {
			sqlSession.rollback();
			result = new Party();
			result.setMessage(message + " " + x.getMessage());
			result.setXsl(xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the attributes.
	 *
	 * @param partyid the partyid
	 * @param pos the pos
	 * @param test the test
	 * @param xsl the xsl
	 * @return the attributes
	 */
	protected Attributes getAttributes(
			String partyid,
			String pos,
			Boolean test,
			String xsl) {

		Date timestamp = new Date();
		String message = "/party/" + partyid + "/attribute" + "?pos=" + pos + "&xsl=" + xsl + "&test=" + test;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Attributes result = null;
		try {
			getParty(sqlSession, pos);
			HashMap<String, ArrayList<String>> attributeMap = RelationService.readMap(sqlSession, Relation.PARTY_ATTRIBUTE, partyid, Attribute.ACCOMMODATION_SEARCH);
			if (attributeMap == null || attributeMap.isEmpty()) {throw new ServiceException(Error.party_attribute, partyid);}
			Collection<Attribute> attributes = new ArrayList<Attribute>();
			for (String key : attributeMap.keySet()){
				ArrayList<String> values = new ArrayList<String>();
				for (String value : attributeMap.get(key)) {values.add(getValueName(sqlSession, value));}
				if (attributeMap.get(key).size() > 0) {attributes.add(new Attribute(getValueName(sqlSession, key), values));}
			}
			result = new Attributes(NameId.Type.Party.name(), partyid, null, attributes, xsl);
			sqlSession.commit();
		}
		catch(Throwable x){
			sqlSession.rollback();
			result = new Attributes(NameId.Type.Party.name(), partyid, message + " " + x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

	/**
	 * Gets the images by id.
	 *
	 * @param partyid the partyid
	 * @param pos the pos
	 * @param test the test
	 * @param xsl the xsl
	 * @return the images by id
	 */
	protected Items getImagesById(
			String partyid,
			String pos,
			Boolean test,
			String xsl) {

		Date timestamp = new Date();
		String message = "/party/" + partyid + "/image?pos=" + pos + "&xsl=" + xsl + "&test=" + test;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Items result = null;
		try {
			getParty(sqlSession, pos);
			result = new Items(NameId.Type.Party.name(), partyid, "Image", null, sqlSession.getMapper(TextMapper.class).imagesbynameid(new NameId(NameId.Type.Party.name(), partyid)), xsl);
			sqlSession.commit();
		} 
		catch(Throwable x){
			sqlSession.rollback();
			if (x != null && x.getMessage() != null && !x.getMessage().startsWith(Error.data_unchanged.name())) {LOG.error(message + "\n" + x.getMessage());}
			result = new Items(NameId.Type.Party.name(), partyid, "Image", message + " " + x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}
	
	/**
	 * Sets the party text by id and name.
	 *
	 * @param partyid the party ID
	 * @param name the name of the text
	 * @param notes the notes value of the text
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the text by id and name
	 */
	protected Text setTextByIdName(
			String partyid,
			String name,
			String notes,
			String pos,
			String xsl) {
		Date timestamp = new Date();
		String message = "/party/" + partyid + "/text/" + name + "?pos=" + pos + "&notes=" + notes +"&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Text result = null;
		try {
			if (Constants.BLANK.equalsIgnoreCase(notes)) {
				Text text = new Text("Party" + partyid + name, Language.EN);
				result = sqlSession.getMapper(TextMapper.class).readbyexample(text);
			}
			else {
//				notes = URLDecoder.decode(notes, "UTF-8");
				Text text = new Text("Party" + partyid + name, name, Text.Type.Text, new Date(), notes, Language.EN);
				Text exists = sqlSession.getMapper(TextMapper.class).readbyexample(text);
				if (exists == null) {sqlSession.getMapper(TextMapper.class).create(text);}
				else {
					text.setVersion(new Date());
					sqlSession.getMapper(TextMapper.class).update(text);
				}
				result = text;
				sqlSession.commit();
			}
		} 
		catch(Throwable x) {
			sqlSession.rollback();
			result = new Text("Party" + partyid + name, name, Text.Type.Text, new Date(), Error.text_id.getMessage(), Language.EN);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}
	
	/**
	 * Gets the party key values.
	 *
	 * @param partyid the party ID
	 * @param pos the point of sale code
	 * @param xsl the style sheet
	 * @return the key values
	 */
	protected Items getValues(
			String partyid,
			String pos,
			String xsl) {

		Date timestamp = new Date();
		String message = "/party/" + partyid + "/values?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		Items result = null;
		try {
			ArrayList<String> keyvalues = RelationService.read(sqlSession, Relation.PARTY_VALUE, partyid, null);
			if (keyvalues == null) {throw new ServiceException(Error.party_value);}
			Collection<NameId> item = new ArrayList<NameId>();
			for (String keyvalue : keyvalues) {
				String[] args = keyvalue.split(Model.DELIMITER);
				if (args.length == 2) {item.add(new NameId(args[0], args[1]));}
			}
			result = new Items(NameId.Type.Party.name(), partyid, "Value", null, item, xsl);
		} 
		catch(Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new Items(NameId.Type.Party.name(), partyid, message + " " + x.getMessage(), null, null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}
	
	/**
	 * Gets the party key values.
	 *
	 * @param partyid the party ID
	 * @param pos the point of sale code
	 * @param xsl the style sheet
	 * @return the key values
	 */
	protected KeyValues getKeyvalues(
			String partyid,
			String pos,
			String xsl) {

		Date timestamp = new Date();
		String message = "/party/" + partyid + "/keyvalues?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		KeyValues result = null;
		try {
			ArrayList<String> keyvalues = RelationService.read(sqlSession, Relation.PARTY_VALUE, partyid, null);
			if (keyvalues == null) {throw new ServiceException(Error.party_value);}
			Collection<KeyValue> item = new ArrayList<KeyValue>();
			for (String keyvalue : keyvalues) {
				String[] args = keyvalue.split(Model.DELIMITER);
				if (args.length == 2) {item.add(new KeyValue(args[0], args[1]));}
			}
			result = new KeyValues(NameId.Type.Party.name(), partyid, "Value", null, item, xsl);
		} 
		catch(Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			result = new KeyValues(NameId.Type.Party.name(), partyid, message + " " + x.getMessage(), null, null, xsl);
		}
		finally {sqlSession.close();}
		LOG.debug(result);
		MonitorService.monitor(message, timestamp);
		return result;
	}

}
