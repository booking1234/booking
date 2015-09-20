/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import java.util.ArrayList;

import net.cbtltd.server.api.CountryMapper;
import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.LocationMapper;
import net.cbtltd.shared.Area;
import net.cbtltd.shared.Data;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Position;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.location.CountryNameId;
import net.cbtltd.shared.location.LocationCreate;
import net.cbtltd.shared.location.LocationDelete;
import net.cbtltd.shared.location.LocationExists;
import net.cbtltd.shared.location.LocationPosition;
import net.cbtltd.shared.location.LocationRead;
import net.cbtltd.shared.location.LocationUpdate;
import net.cbtltd.shared.location.NameIdByCountry;
import net.cbtltd.shared.location.RegionNameId;

import org.apache.ibatis.session.SqlSession;

/** The Class LocationService responds to location requests. */
public class LocationService
implements IsService {

	private static LocationService service;

	/**
	 * Gets the single instance of LocationService to manage Location instances.
	 * @see net.cbtltd.shared.Location
	 *
	 * @return single instance of LocationService.
	 */
	public static synchronized LocationService getInstance() {
		if (service == null){service = new LocationService();}
		return service;
	}

	/**
	 * Executes the LocationCreate action to create a Location instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Location execute(SqlSession sqlSession, LocationCreate action) {
		try {sqlSession.getMapper(LocationMapper.class).create(action);}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the LocationExists action to check if a Location instance exists.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Location execute(SqlSession sqlSession, LocationExists action) {
		Location location = null;
		try {location = sqlSession.getMapper(LocationMapper.class).exists(action);}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return location;
	}

	/**
	 * Executes the LocationRead action to read a Location instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Location execute(SqlSession sqlSession, LocationRead action) {
		Location location = null;
		try {location = sqlSession.getMapper(LocationMapper.class).read(action.getId());}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return location;
	}

	/**
	 * Executes the LocationUpdate action to update a Location instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Location execute(SqlSession sqlSession, LocationUpdate action) {
		try {
			sqlSession.getMapper(LocationMapper.class).update(action);
			MonitorService.update(sqlSession, Data.Origin.CONSOLE, NameId.Type.Location, action);
			}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the LocationDelete action to delete a Location instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Location execute(SqlSession sqlSession, LocationDelete action) {
		try {
			action.setState(Location.FINAL);
			sqlSession.getMapper(LocationMapper.class).update(action);
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the LocationPosition action to read a Position of a Location instance.
	 * @see net.cbtltd.shared.Position
	 * 
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the position
	 */
	public final Position execute(SqlSession sqlSession, LocationPosition action) {
		Position position = null;
		try {position = sqlSession.getMapper(LocationMapper.class).position(action);}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return position;
	}

	/**
	 * Executes the NameIdAction action to read a list of location NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, NameIdAction action) {
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(LocationMapper.class).nameidbyid(action));}
			else {table.setValue(sqlSession.getMapper(LocationMapper.class).nameidbyname(action));}
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the CountryNameId action to read a list of country NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, CountryNameId action) {
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(CountryMapper.class).nameidbyid(action));}
			else {table.setValue(sqlSession.getMapper(CountryMapper.class).nameidbyname(action));}
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the NameIdByCountry action to read a list of country NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, NameIdByCountry action) { //NameByCountry
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(LocationMapper.class).nameidbyid(action));}
			else {table.setValue(sqlSession.getMapper(LocationMapper.class).nameidbycountry(action));}
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the RegionNameId action to read a list of region NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, RegionNameId action) { //Subdivision
		Table<NameId> table = new Table<NameId>();
		try {table.setValue(sqlSession.getMapper(LocationMapper.class).regionnameid(action));}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/*
	 * Gets a list of the NameIds of locations within the specified distance of the latitude and longitude.
	 *
	 * @param sqlSession the current SQL session.
	 * @param latitude the latitude of the centre of the position.
	 * @param longitude the longitude of the centre of the position.
	 * @param distance the maximum distance from  the centre of the position.
	 * @param unit the unit of the distance.
	 * @return the list of location IDs within the specified distance of the latitude and longitude.
	 */
	private ArrayList<NameId> nameidnearby(SqlSession sqlSession, Double latitude, Double longitude, Double distance, String unit) {
		ArrayList<NameId> list = null;
		try {list = sqlSession.getMapper(LocationMapper.class).nameidnearby(new Area(latitude, longitude, distance, unit));}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return list;
	}

	/**
	 * Gets the IDs of locations within the specified distance of the latitude and longitude.
	 *
	 * @param sqlSession the current SQL session.
	 * @param latitude the latitude of the centre of the position.
	 * @param longitude the longitude of the centre of the position.
	 * @param distance the maximum distance from  the centre of the position.
	 * @param unit the unit of the distance.
	 * @return the list of location IDs within the specified distance of the latitude and longitude.
	 */
	public ArrayList<String> nearbyids(SqlSession sqlSession, Double latitude, Double longitude, Double distance, String unit) {
		return NameId.getIdList(nameidnearby(sqlSession, latitude, longitude, distance, unit));
	}

	/**
	 * Gets the IDs of locations within the specified location.
	 *
	 * @param sqlSession the current SQL session.
	 * @param location the specified location.
	 * @param distance the maximum distance from  the centre of the position.
	 * @param unit the unit of the distance.
	 * @return the list of location IDs within the specified distance of the location.
	 */
	public ArrayList<String> nearbyids(SqlSession sqlSession, Location location, Double distance, String unit) {
		return nearbyids(sqlSession, location.getLatitude(), location.getLongitude(), distance, unit);
	}
	
	public static String getLocationIdByRegionAndCountry(SqlSession sqlSession, String country, String region) {
		NameIdAction action = new NameIdAction();
		action.setType(country);
		action.setName(region);
		action.setNumrows(1);
		ArrayList<NameId> locations = sqlSession.getMapper(LocationMapper.class).nameidbycountryregion(action);
		return locations.get(0).getId();
	}
}
