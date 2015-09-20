/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	3.0.10
 */
package net.cbtltd.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import net.cbtltd.rest.response.LocationsResponse;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.ChannelPartnerMapper;
import net.cbtltd.server.api.LocationMapper;
import net.cbtltd.shared.Area;
import net.cbtltd.shared.ChannelPartner;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/** The Class AbstractLocation implements REST services for locations and positions. */

public abstract class AbstractLocation {

	private static final Logger LOG = Logger.getLogger(AbstractLocation.class.getName());

	/**
	 * Gets the country name ids.
	 *
	 * @param xsl the xsl
	 * @return the country name ids
	 */
	protected Countries getCountryNameIds(String xsl) {
		String message = "/location/countries?xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		try {
			NameIdAction action = new NameIdAction();
			action.setName("");
			action.setNumrows(1000);
			Collection<NameId> item =sqlSession.getMapper(LocationMapper.class).countrynameid(action);
			if (item == null) {throw new ServiceException(Error.country_list, action.getOrganizationid());}
			return new Countries(item, null, xsl);
		} 
		catch(Throwable x) {
			sqlSession.rollback(); 
			LOG.error(message + "\n" + x.getMessage());
			return new Countries(null, x.getMessage(), xsl);
		}
		finally {sqlSession.close();}
	}

	/**
	 * Gets the location name ids by country.
	 *
	 * @param country the country
	 * @param xsl the xsl
	 * @return the location name ids by country
	 */
	protected Items getLocationNameIdsByCountry(
			String country,
			Integer rows,
			String xsl) {

		String message = "/location/list/" + country + "?xsl=" + xsl + "&rows=" + rows;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		try {
			NameIdAction action = new NameIdAction();
			action.setName("");
			action.setType(country);
			action.setNumrows(rows);
			Collection<NameId> item = sqlSession.getMapper(LocationMapper.class).nameidbycountry(action);
			if (item == null) {throw new ServiceException(Error.country_code, action.getType());}
			Items result = new Items(country, null, null, null,item, xsl);
			LOG.debug(result);
			return result;
		} 
		catch (Throwable x) {
			sqlSession.rollback(); 
			LOG.error(message + "\n" + x.getMessage()); 
			return new Items(country, null, null, x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
	}

	/**
	 * Gets the subdivision name ids by country.
	 *
	 * @param country the country
	 * @param xsl the xsl
	 * @return the subdivision name ids by country
	 */
	protected Subdivisions getSubdivisionNameIdsByCountry(
			String country,
			Integer rows,
			String xsl) {

		String message = "/location/subdivisions/" + country + "?xsl=" + xsl + "&rows=" + rows;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		try {
			NameIdAction action = new NameIdAction();
			action.setName("");
			action.setType(country);
			action.setNumrows(rows);
			Collection<NameId> item = sqlSession.getMapper(LocationMapper.class).regionnameid(action);
			if (item == null) {throw new ServiceException(Error.country_code, action.getType());}
			return new Subdivisions(country, item, null, xsl);
		}
		catch (Throwable x) {
			sqlSession.rollback(); 
			LOG.error(message + "\n" + x.getMessage()); 
			return new Subdivisions(country, null, x.getMessage(), xsl);
		}
		finally {sqlSession.close();}
	}

	/**
	 * Gets the location name ids by subdivision.
	 *
	 * @param country the country
	 * @param subdivision the subdivision
	 * @param xsl the xsl
	 * @return the location name ids by subdivision
	 */
	protected Items getLocationNameIdsBySubdivision(
			String country,
			String subdivision,
			Integer rows,
			String xsl) {

		String message = "/location/list/" + country +"/" + subdivision + "?xsl=" + xsl + "&rows=" + rows;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		try {
			NameIdAction action = new NameIdAction();
			action.setType(country);
			action.setName(subdivision);
			action.setNumrows(rows);
			Collection<NameId> item = sqlSession.getMapper(LocationMapper.class).nameidbycountryregion(action);
			if (item == null) {throw new ServiceException(Error.country_region, action.getType() + ", " + action.getName());}
			return new Items(country, subdivision, null, null, item, xsl);
		}
		catch (Throwable x) {
			sqlSession.rollback(); 
			LOG.error(message + "\n" + x.getMessage()); 
			return new Items(country, subdivision, null, x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
	}

	/**
	 * Gets the location units.
	 *
	 * @param xsl the xsl
	 * @return the location units
	 */
	protected Units getLocationUnits(String xsl) {

		String message = "/location/units?xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		try {
			String[] IDS = {"MTR", "KMT", "KTM", "FOT", "YRD", "SMI", "NMI"};
			String[] NAMES = {"metre", "kilometre", "kilometre", "foot", "yard", "statute mile", "nautical mile"};
			List<NameId> units = new ArrayList<NameId>();
			int length = (NAMES.length > IDS.length)? IDS.length: NAMES.length;
			for (int i=0; i<length; i++) {units.add(new NameId(NAMES[i], IDS[i]));}
			return new Units(units, xsl);
		}
		catch (Throwable x) {
			sqlSession.rollback(); 
			LOG.error(message + "\n" + x.getMessage()); 
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).build());
		}
		finally {sqlSession.close();}
	}

	/**
	 * Gets the name ids near to.
	 *
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @param distance the distance
	 * @param unit the unit
	 * @param xsl the xsl
	 * @return the name ids near to
	 */
	protected Items getNameIdsNearTo(
			Double latitude,
			Double longitude,
			Double distance,
			String unit,
			String xsl) {

		String message = "/location/list/" + latitude + "/" + longitude + "/" + distance + "/" + unit + "/?xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		try {
			if (latitude < -90.0 || latitude > 90.0) {throw new ServiceException(Error.position_latitude, "Latitude " + latitude);}
			if (longitude < -180.0 || longitude > 180.0) {throw new ServiceException(Error.position_longitude, "Longitude " + longitude);}
			Area action = new Area(latitude, longitude, distance, unit);
			Collection<NameId> item = sqlSession.getMapper(LocationMapper.class).nameidnearby(action);
			if (item == null) {throw new ServiceException(Error.position_nearby, action.toString());}
			return new Items(null, null, null, null, item, xsl);
		}
		catch (Throwable x) {
			sqlSession.rollback(); 
			LOG.error(message + "\n" + x.getMessage()); 
			return new Items(null, null, null, x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
	}

	/**
	 * Gets the name ids by location.
	 *
	 * @param locationid the locationid
	 * @param distance the distance
	 * @param unit the unit
	 * @param xsl the xsl
	 * @return the name ids by location
	 */
	protected Items getNameIdsByLocation(
			String locationid,
			Double distance,
			String unit,
			String xsl) {

		String message = "/location/list/" + locationid + " " + " " + distance + " " + unit + "/?xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		try {
			Location location = sqlSession.getMapper(LocationMapper.class).restread(locationid);
			Area action = new Area(location.getLatitude(), location.getLongitude(), distance, unit);
			Collection<NameId> item = sqlSession.getMapper(LocationMapper.class).nameidnearby(action);
			if (item == null) {throw new ServiceException(Error.locationid_nearby, action.toString());}
			return new Items(null, null, null, null, item, xsl);
		} 
		catch (Throwable x) {
			sqlSession.rollback(); 
			LOG.error(message + "\n" + x.getMessage()); 
			return new Items(null, null, null, x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
	}

	/**
	 * Gets the by id.
	 *
	 * @param locationid the locationid
	 * @param xsl the xsl
	 * @return the by id
	 */
	protected Location getById(
			String locationid,
			String xsl) {

		String message = "/location/" + locationid + "/?xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		try {
			Location location = sqlSession.getMapper(LocationMapper.class).restread(locationid);
			if (location == null) {throw new ServiceException(Error.location_id, locationid);}			
			location.setXsl(xsl);
			return location;
		} 
		catch (Throwable x) {
			sqlSession.rollback(); 
			LOG.error(message + "\n" + x.getMessage());
			return new Location(x.getMessage());
		}
		finally {sqlSession.close();}
	}
	
	/**
	 * Gets locations with products by name.
	 *
	 * @param name the the first part of location name
	 * @param pos the encrypted channel partner id
	 * @return list of location label,id
	 */
	protected LocationsResponse getLabelIdByName(String name, String pos, Boolean sublocation) {

		String message = "/location/getlocations?term=" + name + "&pos=" + pos;
		LOG.debug(message);
		
		if (StringUtils.isEmpty(name)) {return new LocationsResponse(Error.location_id.getMessage());}
		
		LocationsResponse response = new LocationsResponse();
		ArrayList<LabelId> locationList = new ArrayList<LabelId>();
		SqlSession sqlSession = RazorServer.openSession();
		try {
			String channelPartnerId =  Model.decrypt(pos);
			
			if (StringUtils.isEmpty(channelPartnerId)){
				throw new Exception("Invalid input parameter pos.");
			}
			
			ChannelPartner channelPartner = sqlSession.getMapper(ChannelPartnerMapper.class).readByPartyId(Integer.valueOf(channelPartnerId));
			
			if (channelPartner == null){
				throw new ServiceException(Error.database_cannot_find, " channel partner.");
			}
			
			NameId nameId = new NameId(name, channelPartner.getId().toString());
			List<net.cbtltd.shared.Location> locations = null;
			if (sublocation == null || !sublocation){
				locations = sqlSession.getMapper(LocationMapper.class).getSearchLocations(nameId);
			}else {
				locations = sqlSession.getMapper(LocationMapper.class).getSearchSublocations(nameId);
			}
			
			if (locations == null) {
				return response;
			}
			
			for (net.cbtltd.shared.Location location : locations) {
				String state =  StringUtils.isEmpty(location.getState()) ? "" : location.getState() + ", ";
				String label = location.getName() + ", " + state + location.getCountry();				
				locationList.add(new LabelId(new String(label), new String(location.getId())));
			}		
			
			response.setLocations(locationList);						
			return response;
			
		} catch (Throwable x) {
			LOG.error(message + "\n" + x.getMessage());
			return new LocationsResponse(x.getMessage());
		} finally {
			sqlSession.close();
		}
	}
}
