package net.cbtltd.server.integration;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.cbtltd.rest.GoogleLocationLimitException;
import net.cbtltd.rest.GoogleLocationProcessor;
import net.cbtltd.server.MonitorService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.CountryCodeMapper;
import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.LocationMapper;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.CountryCode;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.partner.PartnerRead;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.allen_sauer.gwt.log.client.Log;

public class PartnerService implements IsService {
	private static final Logger LOG = Logger.getLogger(PartnerService.class
			.getName());
	private static PartnerService service;
	private static Map<String, String> countryNames;

	/**
	 * Gets the single instance of PartyService to manage Party instances.
	 * 
	 * @see net.cbtltd.shared.Party
	 * 
	 * @return single instance of PartyService
	 */
	public static synchronized PartnerService getInstance() {
		if (service == null) {
			service = new PartnerService();
		}
		return service;
	}

	/**
	 * Executes the PartnerRead action.
	 * 
	 * @param sqlSession
	 *            the current SQL session
	 * @param action
	 *            the action to be executed.
	 * @return the response.
	 */
	public final Partner fetchPartnerServiceById(SqlSession sqlSession,
			PartnerRead action) {
		Partner alert = null;
		try {
			alert = sqlSession.getMapper(PartnerMapper.class).read(
					action.getId());
		} catch (Throwable x) {
			sqlSession.rollback();
			MonitorService.log(x);
		}
		return alert;
	}

	/**
	 * @return map of country and country code 
	 */
	public static Map<String, String> getCountryNames() {
		if (countryNames == null) {
			countryNames = new HashMap<String, String>();
			SqlSession sqlSession = null;
			try {
				sqlSession = RazorServer.openSession();

				CountryCodeMapper mapper = sqlSession
						.getMapper(CountryCodeMapper.class);
				for (CountryCode code : mapper.getCountryCodes()) {
					if (!countryNames.containsKey(code.getCountryCode()))
						countryNames.put(code.getCountryCode(),
								code.getCountryString());
				}
			} catch (Exception e) {
				Log.error("DB Exception fetching country codes ", e);
			} finally {
				if (sqlSession != null)
					sqlSession.close();
			}
		}
		return countryNames;
	}

	/**
	 * to get the country name for the country
	 * @param countryCode
	 * @return country name
	 */
	public static String getCountryNameFromCode(String countryCode) {

		if (countryCode != null) {
			countryCode = countryCode.toUpperCase();
		} else {
			return null;
		}
		if (getCountryNames().containsKey(countryCode)) {
			return getCountryNames().get(countryCode);
		}
		return countryCode;
	}

	/**
	 * to create /persis the product
	 * @param sqlSession
	 * @param product
	 * @return product
	 */
	public static Product createProduct(SqlSession sqlSession, Product product) {
		product.setState(Product.CREATED);
		sqlSession.getMapper(ProductMapper.class).create(product);
		return product;
	}

	/**
	 * to update product
	 * @param sqlSession
	 * @param product
	 */
	public synchronized static void updateProduct(SqlSession sqlSession,
			Product product) {
		Product exist = sqlSession.getMapper(ProductMapper.class).altread(
				new NameId(product.getAltpartyid(), product.getAltid()));
		if (exist != null) {
			sqlSession.getMapper(ProductMapper.class).update(product);
			// replace the replace call..replace call was throwing lock
			// exception
			LOG.debug("updateProduct  " + product.getAltpartyid() + ", "
					+ product.getAltid() + ", " + product.getId());
		}
	}

	/**
	 * Gets the product or null it if it does not exist.
	 * 
	 * @param sqlSession
	 *            the current SQL session.
	 * @param name
	 * @return the product
	 */
	public static Product getProductWithoutInsert(SqlSession sqlSession,
			NameId name) {
		Product product = sqlSession.getMapper(ProductMapper.class).nameRead(
				name);
		return product;
	}
	/**
	 * Gets the location from its name, region and country and creates it if it does not exist.
	 *
	 * @param sqlSession the current SQL session.
	 * @param name the name of the location
	 * @param region the region (state, province, county) preferably using its ISO subdivision code
	 * @param country the ISO country code
	 * @return the location object
	 */
	public static final Location getLocation(SqlSession sqlSession, String name, String region, String country) {
		if (name == null || country == null) {return null;}
		Location location = new Location();
		String[] args = name.split("\\(");
		location.setName(args[0]);
		if (country.equals("US")){location.setRegion(region);}
		location.setCountry(country);
		location.setState(Location.CREATED);
		Location exists = sqlSession.getMapper(LocationMapper.class).exists(location);
		//
		if (exists != null) {
			location = exists;
		}
		else {
			 location = GoogleLocationProcessor.getGoogleLocation(sqlSession, name, region, country);
		}
		LOG.debug("getLocation  " + location.getId());
		return location;
	}

	/**
	 * @param sqlSession
	 * @param postalCode
	 * @param country
	 * @param latitude
	 * @param longitude
	 * @return
	 * @throws GoogleLocationLimitException
	 */
	public static Location getLocation(SqlSession sqlSession,
			String postalCode, String country, Double latitude, Double longitude)
			throws GoogleLocationLimitException {

		return net.cbtltd.server.PartnerService.getLocation(sqlSession,
				postalCode, country, latitude, longitude);
	}

	/**
	 * to get country code of country name
	 * @param strCountry
	 * @return country code
	 */
	public static String getCountryCode(String strCountry) {
		return net.cbtltd.server.PartnerService.getCountryCode(strCountry);
	}

	/**
	 * to get region code from region name
	 * @param strState
	 * @return region code
	 */
	public static String getRegionCode(String strState) {
		return net.cbtltd.server.PartnerService.getRegionCode(strState);
	}

	/**
	 * to create the min stay 
	 * @param sqlSession
	 * @param intMinNights
	 * @param product
	 * @param time
	 * @param time2
	 * @param date
	 */
	public static void createMinStay(SqlSession sqlSession, int intMinNights,
			Product product, Date time, Date time2, Date date) {
		net.cbtltd.server.PartnerService.createMinStay(sqlSession,
				intMinNights, product, time, time2, date);
	}

	/** to create schedule
	 * @param sqlSession
	 * @param product
	 * @param bkDate
	 * @param bkDate2
	 * @param date
	 */
	public static void createSchedule(SqlSession sqlSession, Product product,
			Date bkDate, Date bkDate2, Date date) {
		net.cbtltd.server.PartnerService.createSchedule(sqlSession, product,
				bkDate, bkDate2, date);
	}

	/**
	 * to find the change log in minutes
	 * @param sqlSession
	 * @param altpartyid
	 * @return
	 */
	public static int changeLogInMin(SqlSession sqlSession, String altpartyid) {
		return sqlSession.getMapper(ProductMapper.class).changeLogInMin(
				new NameId(altpartyid));
	}

	/**
	 * to get list of reserved dates
	 * @param sqlSession
	 * @param id
	 * @return dates
	 */
	public static List<Date> getReservationList(SqlSession sqlSession, String id) {
		return sqlSession.getMapper(ReservationMapper.class)
				.reservedDateForProperty(id);
	}

	

	/**
	 * to delete reservation entry
	 * @param sqlSession
	 * @param id
	 * @param cdDate
	 */
	public static void deleteReservation(SqlSession sqlSession, String id,
			Date cdDate) {
		sqlSession.getMapper(ReservationMapper.class).deleteDate(id, cdDate);		
	}

}
