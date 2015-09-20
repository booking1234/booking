package net.cbtltd.server.integration;

import net.cbtltd.server.MonitorService;
import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.LocationMapper;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.location.LocationRead;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class LocationService implements IsService {

	private static final Logger LOG = Logger.getLogger(LocationService.class
			.getName());
	private static LocationService service;

	/**
	 * Gets the single instance of ProductService to manage Product instances.
	 * 
	 * @see net.cbtltd.shared.Product
	 * 
	 * @return single instance of ProductService.
	 */
	public static synchronized LocationService getInstance() {
		if (service == null) {
			service = new LocationService();
		}
		return service;
	}

	/**
	 * Executes the LocationRead action to read a Location instance.
	 * 
	 * @param sqlSession
	 *            the current SQL session.
	 * @param action
	 *            the action to be executed.
	 * @return the response.
	 */
	public final Location findLocationById(SqlSession sqlSession,
			LocationRead action) {
		Location location = null;
		try {
			location = sqlSession.getMapper(LocationMapper.class).read(
					action.getId());
		} catch (Throwable x) {
			sqlSession.rollback();
			MonitorService.log(x);
		}
		return location;
	}

	/**
	 * to find the geo location
	 * @param locName
	 * @param stateCode
	 * @param countryCode
	 * @param zip
	 * @param lat
	 * @param lng
	 * @param sqlSession
	 * @return location object
	 */
	public String getLocation(String locName, String stateCode,
			String countryCode, String zip, double lat, double lng,
			SqlSession sqlSession) {
		Location location = null;
		try {

			LOG.info("Searching location with state code " + stateCode
					+ " countryCode " + countryCode);
			location = PartnerService.getLocation(sqlSession, zip, countryCode,
					lat, lng);

			if (location != null) {
				LOG.info("Setting location : " + location.getName());
				return location.getId();
			} else {
				LOG.error("Location is null");
			}

		} catch (Throwable t) {
			LOG.error("Error while doing location lookup ", t);
		}
		return null;
	}
	
	public Location read(SqlSession sqlSession, String locationId){
		return sqlSession.getMapper(LocationMapper.class).read(locationId);
	}
}
