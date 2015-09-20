/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.cbtltd.server.api.DownloadMapper;
import net.cbtltd.server.api.RelationMapper;
import net.cbtltd.shared.Downloaded;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.Relation;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/**
 * The Class RelationService is to manage relations of many different types between entities.
 * Allowed relation types are enumerated in the Relation class. 
 * @see net.cbtltd.shared.Relation
 */
public class RelationService {
	
	private static final Logger LOG = Logger.getLogger(RelationService.class.getName());

	/**
	 * Creates the relations from one entity to many entities.
	 *
	 * @param sqlSession the current SQL session.
	 * @param link the name of the relation.
	 * @param headid the ID of the first (head) entity.
	 * @param lineids the list of the IDs of the second (line) entities.
	 */
	public static final void create(SqlSession sqlSession, String link, String headid, ArrayList<String> lineids) {
		if (lineids != null) {
			for (String lineid : lineids) {
				
				create(sqlSession, new Relation(link, headid, lineid));
			}
		}
	}

	/**
	 * Creates the relations from many entities to one entity.
	 *
	 * @param sqlSession the current SQL session.
	 * @param link the name of the relation.
	 * @param headids the list of the IDs of the first (head) entities.
	 * @param lineid the ID of the second (line) entity.
	 */
	public static final void create(SqlSession sqlSession, String link, ArrayList<String> headids, String lineid) {
		if (headids != null) {
			for (String headid : headids) {create(sqlSession, new Relation(link, headid, lineid));}
		}
	}

	/**
	 * Creates the relations from one entity to another entity.
	 *
	 * @param sqlSession the current SQL session.
	 * @param link the name of the relation.
	 * @param headid the ID of the first (head) entity.
	 * @param lineid the ID of the second (line) entity.
	 */
	public static final void create(SqlSession sqlSession, String link, String headid, String lineid) {
		LOG.debug("create " + link + " " + headid + " " + lineid);
		if (link == null || link.isEmpty() || headid == null || headid.isEmpty() || lineid == null || lineid.isEmpty()) {return;}
		create(sqlSession, new Relation(link, headid, lineid));
	}

	/**
	 * Creates the specified relation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param relation the specified relation.
	 */
	private static final void create(SqlSession sqlSession, Relation relation) {
		if (!exists(sqlSession, relation)) {sqlSession.getMapper(RelationMapper.class).create(relation);}
	}

	/**
	 * Gets the relation with the specified parameters if it exists.
	 *
	 * @param sqlSession the current SQL session.
	 * @param link the name of the relation.
	 * @param headid the ID of the first (head) entity.
	 * @param lineid the ID of the second (line) entity.
	 * @return true if the relation exists
	 */
	public static final boolean exists(SqlSession sqlSession, String link, String headid, String lineid) {
		return exists(sqlSession, new Relation(link, headid, lineid));
	}

	/*
	 * Gets the specified relation if it exists.
	 *
	 * @param sqlSession the current SQL session.
	 * @param relation the relation
	 * @return true if the relation exists
	 */
	private static final boolean exists(SqlSession sqlSession, Relation relation) {
		return sqlSession.getMapper(RelationMapper.class).exists(relation) != null;
	}

	/**
	 * Gets the relation(s) having the specified parameters.
	 *
	 * @param sqlSession the current SQL session.
	 * @param link the name of the relation.
	 * @param headid the ID of the first (head) entity.
	 * @param lineid the ID of the second (line) entity.
	 * @return the array list
	 */
	public static final ArrayList<String> read(SqlSession sqlSession, String link, String headid, String lineid) {
		return read(sqlSession, new Relation(link, headid, lineid));
	}

	/**
	 * Gets the IDs having the specified relation.
	 * If the head ID is set then return the line IDs
	 * else if the line ID is set then return the head IDs
	 * else return null.
	 *
	 * @param sqlSession the current SQL session.
	 * @param relation the specified relation.
	 * @return the array list
	 */
	public static final ArrayList<String> read(SqlSession sqlSession, Relation relation) {
		if (relation.noHeadid()) {return sqlSession.getMapper(RelationMapper.class).headids(relation);}
		else if (relation.noLineid()) {return sqlSession.getMapper(RelationMapper.class).lineids(relation);}
		else {return null;}
	}

	/**
	 * Gets the list of relations for the specified relation parameter.
	 *
	 * @param sqlSession the current SQL session.
	 * @param relation the specified relation.
	 * @return the array list
	 */
	public static final ArrayList<Relation> list(SqlSession sqlSession, Relation relation) {
		return sqlSession.getMapper(RelationMapper.class).list(relation);
	}

	/**
	 * Deletes the value with the specified key.
	 *
	 * @param sqlSession the current SQL session.
	 * @param link the name of the relation.
	 * @param headid the ID of the first (head) entity.
	 * @param key the key to be deleted.
	 */
	public static final void deletekey(SqlSession sqlSession, String link, String headid, String key) {
		sqlSession.getMapper(RelationMapper.class).deletekey(new Relation(link, headid, key));
	}

	/**
	 * Deletes the relation with the specified parameters.
	 *
	 * @param sqlSession the current SQL session.
	 * @param link the name of the relation.
	 * @param headid the ID of the first (head) entity.
	 * @param lineid the ID of the second (line) entity.
	 */
	public static final void delete(SqlSession sqlSession, String link, String headid, String lineid) {
		LOG.debug("delete " + link + " " + headid + " " + lineid);
		delete(sqlSession, new Relation(link, headid, lineid));
	}

	/**
	 * Deletes the specified relation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param relation the relation to delete.
	 */
	private static final void delete(SqlSession sqlSession, Relation relation) {
		sqlSession.getMapper(RelationMapper.class).delete(relation);
	}

	/**
	 * Updates the relation with the specified parameters.
	 *
	 * @param sqlSession the current SQL session.
	 * @param link the name of the relation.
	 * @param headid the ID of the first (head) entity.
	 * @param lineid the ID of the second (line) entity.
	 */
	public static final void replace(SqlSession sqlSession, String link, String headid, String lineid) {
		LOG.debug("replace " + link + " " + headid + " " + lineid);
		delete(sqlSession, new Relation(link, headid, lineid));
		create(sqlSession, new Relation(link, headid, lineid));
	}

	/**
	 * Updates the relations with the specified parameters.
	 *
	 * @param sqlSession the current SQL session.
	 * @param link the name of the relation.
	 * @param headid the ID of the first (head) entity.
	 * @param lineids the list of the IDs of the second (line) entities.
	 */
	public static final void replace(SqlSession sqlSession, String link, String headid, ArrayList<String> lineids) {
		LOG.debug("replace " + link + " " + headid + " " + lineids);
		delete(sqlSession, new Relation(link, headid, null));
		create(sqlSession, link, headid, lineids);
	}

	/**
	 * Updates the relations with the specified parameters.
	 *
	 * @param sqlSession the current SQL session.
	 * @param link the name of the relation.
	 * @param headids the list of the IDs of the first (head) entities.
	 * @param lineid the ID of the second (line) entity.
	 */
	public static final void replace(SqlSession sqlSession, String link, ArrayList<String> headids, String lineid) {
		LOG.debug("replace " + link + " " + headids + " " + lineid);
		delete(sqlSession, new Relation(link, null, lineid));
		create(sqlSession, link, headids, lineid);
	}

	/**
	 * Updates the specified relation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param relation the relation
	 */
//	private static final void replace(SqlSession sqlSession, Relation relation) {
//		if (relation == null || relation.isEmpty()) {return;}
//		sqlSession.getMapper(RelationMapper.class).delete(relation);
//		sqlSession.getMapper(RelationMapper.class).create(relation);
//	}

	/**
	 * Gets the relation with the specified parameters and if it does not exist creates it.
	 *
	 * @param sqlSession the current SQL session.
	 * @param link the name of the relation.
	 * @param headid the ID of the first (head) entity.
	 * @param lineid the ID of the second (line) entity.
	 */
//	public static final void write(SqlSession sqlSession, String link, String headid, String lineid) {
////		if (link == null || link.isEmpty() || headid == null || headid.isEmpty() || lineid == null || lineid.isEmpty()) {return;}
//		Relation relation = new Relation(link, headid, lineid);
//		Relation exists = sqlSession.getMapper(RelationMapper.class).exists(relation);
//		if (exists == null) {create(sqlSession, relation);}
//	}

	/**
	 * Creates relations with the specified link, id and key-option(s) map.
	 * This is used to persist the key-option(s) map of entity attributes created by selector and stack field types.
	 * @see net.cbtltd.client.field.SelectorField
	 * @see net.cbtltd.client.field.StackField
	 *
	 * @param sqlSession the current SQL session.
	 * @param link the name of the relation.
	 * @param id the ID of the entity (head) to which the key-option(s) belong.
	 * @param map the key-option(s) map.
	 */
	public static final void create(SqlSession sqlSession, String link, String id, HashMap<String, ArrayList<String>> map) {
		if (map != null) {
			for (String key: map.keySet()) {create (sqlSession, link, id, map.get(key));}
		}
	}

	/**
	 * Reads a key-option(s) map for the specified parameters.
	 * This is used to read the key-option(s) map of entity attributes used by selector and stack field types.
	 * @see net.cbtltd.client.field.SelectorField
	 * @see net.cbtltd.client.field.StackField
	 *
	 * @param sqlSession the current SQL session.
	 * @param link the name of the relation.
	 * @param id the ID of the entity (head) to which the key-option(s) belong.
	 * @param keys the keys of the values to be persisted.
	 * @return the key-option(s) map.
	 */
	public static final HashMap<String, ArrayList<String>> readMap(SqlSession sqlSession, String link, String id, String[] keys) {
		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		if (keys != null) {
			for (String key : keys) {
				ArrayList<String> values = sqlSession.getMapper(RelationMapper.class).attributes(new Relation(link, id, key));
				map.put(key, values);
			}
		}
		return map;
	}
	
	/**
	 * Reads a attributes map (key/values) for the specified parameters.
	 *
	 * @param sqlSession the current SQL session.
	 * @param link the name of the relation.
	 * @param id the ID of the entity (head) to which the key-option(s) belong.
	 * @return the key-option(s) map.
	 */
	public static final HashMap<String, ArrayList<String>> readAttributesMap(SqlSession sqlSession, String link, String id) {
		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		
		List<String> attributesList = RelationService.read(sqlSession, link, id, null);
		for (String attr : attributesList){
			if (attr != null && !attr.isEmpty()){
				String key = attr.substring(0, 3);
				if (map.containsKey(key)){
					map.get(key).add(attr);
				}else {
					ArrayList<String> values = new ArrayList<String>();
					values.add(attr);
					map.put(key, values);
				}
			}
		}
		return map;
	}

	/**
	 * Updates the key-option(s) map for the specified parameters.
	 * This is used to replace the key-option(s) map of entity attributes used by selector and stack field types.
	 * @see net.cbtltd.client.field.SelectorField
	 * @see net.cbtltd.client.field.StackField
	 *
	 * @param sqlSession the current SQL session.
	 * @param link the name of the relation.
	 * @param id the ID of the entity (head) to which the key-option(s) belong.
	 * @param map the the key-option(s) map.
	 */
	public static final void replace(SqlSession sqlSession, String link, String id, HashMap<String, ArrayList<String>> map) {
		delete(sqlSession, link, id, null);
		create(sqlSession, link, id, map);
	}

	/**
	 * Reads a key-value map with the values of the specified relation.
	 * This is used to read key-value pairs for attributes that are not fields of an entity.
	 *
	 * @param sqlSession the current SQL session.
	 * @param link the name of the relation.
	 * @param id the ID of the entity (head) to which the key-values belong.
	 * @return the key-value map.
	 */
	public static final HashMap<String, String> values(SqlSession sqlSession, String link, String id) {
		ArrayList<String> keyvalues = read(sqlSession, link, id, null);
		HashMap<String, String> values = new HashMap<String, String>();
		if (keyvalues != null) {
			for (String keyvalue : keyvalues) {
				String[] args = keyvalue.split(Model.DELIMITER);
				if (args.length == 2) {values.put(args[0], args[1]);}
			}
		}
		return values;
	}
	
	/**
	 * Deletes Deprecated relations based on lineids array from PMS.
	 * 
	 * @param sqlSession the current SQL session.
	 * @param link the name of the relation.
	 * @param headid the ID of the first (head) entity.
	 * @param lineids the list of the IDs of the second (line) entities.
	 */
	public static final void removeDeprecatedData(SqlSession sqlSession, String link, String headid, ArrayList<String> lineids) {
		ArrayList<String> oldvalues = read(sqlSession, new Relation(link, headid, null));
		boolean exists;
		if (lineids != null && oldvalues != null){
			for (String oldlineid : oldvalues) {
				exists = lineids.contains(oldlineid);
				if(!exists) {
					delete(sqlSession, link, headid, oldlineid);
				}
			}
		}
	}
	
	/**
	 * Reads a value using the values of the specified relation.
	 * This is used to read the value of the specified key.
	 *
	 * @param sqlSession the current SQL session.
	 * @param link the name of the relation.
	 * @param id the ID of the entity (head) to which the key-values belong.
	 * @param key the specified key.
	 * @return the value mapped by the key.
	 */
	public static final String value(SqlSession sqlSession, String link, String id, String key) {
		HashMap<String, String> values = values (sqlSession, link, id);
		return values == null ? null : values.get(key);
	}
	
	/**
	 * Reads a string value using the specified parameters.
	 * This is used to read the value of the specified key.
	 *
	 * @param sqlSession the current SQL session.
	 * @param link the name of the relation.
	 * @param id the ID of the entity (head) to which the key-values belong.
	 * @param key the specified key.
	 * @return the string value mapped by the key.
	 */
	public static final String string(SqlSession sqlSession, String link, String id, String key) {
//		if (link == null || link.isEmpty() || id == null || id.isEmpty() || key == null || key.isEmpty()) {return null;}
		ArrayList<String> values = read(sqlSession, new Relation(link, id, key));
		return values == null ? null : values.get(0);
	}

	/**
	 * Reads an integer value using the specified parameters.
	 * This is used to read the value of the specified key.
	 *
	 * @param sqlSession the current SQL session.
	 * @param link the name of the relation.
	 * @param id the ID of the entity (head) to which the key-values belong.
	 * @param key the specified key.
	 * @return the integer value mapped by the key.
	 */
	public static final Integer integer(SqlSession sqlSession, String link, String id, String key) {
		String value = string(sqlSession, link, id, key);
		return value == null ? 0 : Integer.valueOf(value);
	}

	/**
	 * Reads a double value using the specified parameters.
	 * This is used to read the value of the specified key.
	 *
	 * @param sqlSession the current SQL session.
	 * @param link the name of the relation.
	 * @param id the ID of the entity (head) to which the key-values belong.
	 * @param key the specified key.
	 * @return the double value mapped by the key.
	 */
	public static final Double number(SqlSession sqlSession, String link, String id, String key) {
		String value = string(sqlSession, link, id, key);
		return value == null ? 0.0 : Double.valueOf(value);
	}
	
	/**
	 * Loads the download/upload relation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param downloaded the enum of the relation.
	 * @param headid the ID of the first (head) entity.
	 * @param lineid the ID of the second (line) entity.
	 */
	public static final void load(SqlSession sqlSession, Downloaded downloaded, String headid, int lineid) {
		load(sqlSession, downloaded, headid, String.valueOf(lineid));
	}

	/**
	 * Loads the download/upload relation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param downloaded the enum of the relation.
	 * @param headid the ID of the first (head) entity.
	 * @param lineid the ID of the second (line) entity.
	 */
	public static final void load(SqlSession sqlSession, Downloaded downloaded, String headid, String lineid) {
		if (!loaded(sqlSession, downloaded, headid, lineid)) {sqlSession.getMapper(DownloadMapper.class).create(new Relation(downloaded, headid, lineid));}
	}

	/**
	 * Checks if the download/upload relation exists.
	 *
	 * @param sqlSession the current SQL session.
	 * @param downloaded the enum of the relation.
	 * @param headid the ID of the first (head) entity.
	 * @param lineid the ID of the second (line) entity.
	 */
	public static final boolean loaded(SqlSession sqlSession, Downloaded downloaded, String headid, String lineid) {
		return sqlSession.getMapper(DownloadMapper.class).exists(new Relation(downloaded, headid, lineid)) != null;
	}

	/**
	 * Unloads the download/upload relation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param downloaded the enum of the relation.
	 * @param headid the ID of the first (head) entity.
	 * @param lineid the ID of the second (line) entity.
	 */
	public static final void unload(SqlSession sqlSession, Downloaded downloaded, String headid, String lineid) {
		sqlSession.getMapper(DownloadMapper.class).delete(new Relation(downloaded, headid, lineid));
	}
	
	/**
	 * Finds the missing element from the specified parameters.
	 *
	 * @param sqlSession the current SQL session.
	 * @param link the name of the relation.
	 * @param headid the ID of the first (head) entity.
	 * @param lineid the ID of the second (line) entity.
	 * @return the null value
	 */
	public static final String find(SqlSession sqlSession, Downloaded downloaded, String headid, String lineid) {
		return sqlSession.getMapper(DownloadMapper.class).find(new Relation(downloaded, headid, lineid));
	}
	
	/**
	 * Gets the list of relations for the specified parameters.
	 *
	 * @param sqlSession the current SQL session.
	 * @param relation the specified relation.
	 * @return the array list
	 */
	public static final ArrayList<Relation> list(SqlSession sqlSession, Downloaded downloaded, String headid, String lineid) {
		return sqlSession.getMapper(RelationMapper.class).list(new Relation(downloaded, headid, lineid));
	}
}
