/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import java.util.ArrayList;
import java.util.HashMap;

import net.cbtltd.server.api.AttributeMapper;
import net.cbtltd.server.api.IsService;
import net.cbtltd.shared.Attribute;
import net.cbtltd.shared.AttributeAction;
import net.cbtltd.shared.AttributeMapAction;
import net.cbtltd.shared.MapResponse;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.Value;
import net.cbtltd.shared.value.ValueDelete;
import net.cbtltd.shared.value.ValueUpdate;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/** The Class AttributeService responds to attribute actions. */
public class AttributeService
implements IsService {

	private static final Logger LOG = Logger.getLogger(AttributeService.class.getName());
	private static AttributeService service;

	/**
	 * Gets the single instance of AttributeService to manage Attribute instances.
	 * @see net.cbtltd.shared.Attribute
	 *
	 * @return single instance of AttributeService
	 */
	public static synchronized AttributeService getInstance() {
		if (service == null) {service = new AttributeService();}
		return service;
	}

	/**
	 * Executes the AttributeAction action to create a list of Attribute values.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	protected final Table<NameId> execute(SqlSession sqlSession, AttributeAction action) {
		Table<NameId> table = new Table<NameId>();
		try {table.setValue(sqlSession.getMapper(AttributeMapper.class).valuelist(action));}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the AttributeMapAction action to create an Attribute map.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the map response
	 */
	protected final MapResponse<NameId> execute (SqlSession sqlSession, AttributeMapAction action) {
		MapResponse<NameId> response = new MapResponse<NameId>();
		try {
			HashMap<NameId, ArrayList<NameId>> map = new HashMap<NameId, ArrayList<NameId>>();

			ArrayList<Attribute> values = sqlSession.getMapper(AttributeMapper.class).valuemap(action);
			ArrayList<NameId> mapvalue = new ArrayList<NameId>();
			for (Attribute value : values) {
				if (value.isKey()) {
					NameId mapkey = new NameId(value.getName(), value.getList());
					mapvalue = new ArrayList<NameId>();
					map.put(mapkey, mapvalue);
				}
				else {mapvalue.add(new NameId(value.getName(), value.getList() + value.getId()));}
			}
			LOG.debug("AttributeMapAction " + map);
			response.setValue(map);
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return response;
	}

	/**
	 * Executes the ValueUpdate action to update an Attribute value.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	protected final Value execute(SqlSession sqlSession, ValueUpdate action) {
		LOG.debug("ValueUpdate in " + action);
		try {RelationService.replace(sqlSession, action.getLink(), action.getId(), action.getLineid());}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		LOG.debug("ValueUpdate out " + action);
		return action;
	}

	/**
	 * Executes the ValueDelete action to delete an Attribute value.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	protected final Value execute(SqlSession sqlSession, ValueDelete action) {
		LOG.debug("ValueDelete in " + action);
		try {RelationService.delete(sqlSession, action.getLink(), action.getId(), action.getLineid());}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		LOG.debug("ValueDelete out " + action);
		return action;
	}

	/**
	 * Executes the NameIdAction action to read a list of attribute NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, NameIdAction action) {
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(AttributeMapper.class).nameidbyid(action));}
			else {table.setValue(sqlSession.getMapper(AttributeMapper.class).nameidbyname(action));}
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}
	
	private static HashMap<String,String> AmenitiesMap = null;
	public static final String getOTACodeForAttribute(String attribute) {
		if (AmenitiesMap == null) {
			AmenitiesMap = new  HashMap<String,String> ();
			AmenitiesMap.put("air conditioning","FAC1");
			AmenitiesMap.put("air conditioning","FAC1");
			AmenitiesMap.put("breakfast bar","EVT4");
			AmenitiesMap.put("cable/satellite","RMA210");
			AmenitiesMap.put("charcoal grill","HAC118");
			AmenitiesMap.put("city view","RVT3");
			AmenitiesMap.put("coffeemaker","RMA19");
			AmenitiesMap.put("community pool","FAC8");
			AmenitiesMap.put("dvd","RMA163");
			AmenitiesMap.put("daily housekeeping","CSC6");
			AmenitiesMap.put("deck or balcony","CBF1");
			AmenitiesMap.put("dining and livingroom","CBF71");
			AmenitiesMap.put("double bed","CBF29");
			AmenitiesMap.put("eat-in kitchen","RMA59");
			AmenitiesMap.put("full bed","CBF29");
			AmenitiesMap.put("full sized refridgerator","RMA88");
			AmenitiesMap.put("game room","HAC44");
			AmenitiesMap.put("garage","");//nothing.
			AmenitiesMap.put("gas grill","HAC118");
			AmenitiesMap.put("gourmet kitchen","RMA59");
			AmenitiesMap.put("ground floor","RLT17");
			AmenitiesMap.put("hair dryer","HAC240");
			AmenitiesMap.put("heated pool","REC30");
			AmenitiesMap.put("hot tub","RST104");
			AmenitiesMap.put("internet connection","RMA54");
			AmenitiesMap.put("iron/board","RMA56");
			AmenitiesMap.put("jacuzzi tub","RMA57");
			AmenitiesMap.put("jacuzzi/hottub","RMA57");
			AmenitiesMap.put("king bed","CBF30");
			AmenitiesMap.put("linens provided","");//cannot find anyhting. 
			AmenitiesMap.put("microwave","RMA68");
			AmenitiesMap.put("multiple tv's","RMA90");
			AmenitiesMap.put("nice furniture","");//cannot find match. 
			AmenitiesMap.put("night life","RSN18");
			AmenitiesMap.put("no pets allowed","");
			AmenitiesMap.put("no smoking","RMA74");
			AmenitiesMap.put("non smoking","RMP74");
			AmenitiesMap.put("ocean view","LOC16");
			AmenitiesMap.put("oceanfront","LOC16");
			AmenitiesMap.put("outdoor pool","RST123");
			AmenitiesMap.put("patio furniture","");
			AmenitiesMap.put("queen bed","CBF15");
			AmenitiesMap.put("shuttle","TRP17");
			AmenitiesMap.put("sofa bed","BED6");
			AmenitiesMap.put("sound/cove view","");
			AmenitiesMap.put("stove/oven","RMA77");
			AmenitiesMap.put("suitable for children","HAC218");
			AmenitiesMap.put("tv","RMA90");
			AmenitiesMap.put("telephone","RMA107");
			AmenitiesMap.put("tennis courts","RST94");
			AmenitiesMap.put("walk to beach","ENV4");
			AmenitiesMap.put("washer/dryer","RMA149");
		}
    	String key = attribute.toLowerCase();
		if (AmenitiesMap.get(key) == null) {LOG.error("missing amenity: "+attribute ); return "";}
		return AmenitiesMap.get(key);
	}

}
