package net.cbtltd.rest.tripvillas.xmlfeed;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

import net.cbtltd.rest.common.utils.CommonUtils;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.integration.PartnerService;
import net.cbtltd.server.integration.ProductService;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.Product;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class TripvillasUtils extends CommonUtils {

	// constants for reservtion
	public static final String ADULTS_NUM = "adults_num";	
	public static final String KIDS_NUM = "kids_num";		
	public static final String CHECK_IN = "check_in";		
	public static final String CHECK_OUT = "check_out";		
	public static final String ROOM_ID = "room_id";		
	public static final String GUEST_EMAIL = "guest_email";	
	public static final String FIRST_NAME = "first_name";	
	public static final String LAST_NAME = "last_name";		
	public static final String COUNTRY_CODE = "country_code";	
	public static final String MOBILE_NO = "mobile_no";		
	public static final String TOTAL_AMOUNT = "total_amount";	
	public static final String PROPERTY = "property";
	public static final String MOBILE_COUNTRY_CODE = "mobile_country_code";
	public static final String MESSAGE = "message";	

	public static final String OWNERS_GUEST = "Owner's Guest";
	public static final String INDEPENDENT_STAY = "Independent Stay";

	public static final String AUTHORIZATION = "bp.tripvillas.authorization";
	public static final String BREAKAGE_DEPOSIT = "Breakage deposit";

	public static final SimpleDateFormat YYYY_MM_DD_TIME = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");


	// get tripvillas properties from razorProperties file

	public static final String TRIPVILLAS_XML_FEED_SERVER = "bp.tripvillas.feed.xml.server";
	public static final String TRIPVILLAS_XML_FEED_PORT = "bp.tripvillas.xml.feed.port";
	public static final String TRIPVILLAS_XML_FEED_USER = "bp.tripvillas.xml.feed.username";
	public static final String TRIPVILLAS_XML_FEED_PWD = "bp.tripvillas.xml.feed.pwd";
	public static final String PAYPAL_CLIENTID = "bp.paypal.clientid";
	public static final String PAYPAL_SECRET = "bp.paypal.secret";
	public static final String CURRENCY = "EUR";
	public static final String[] TRIPVILLAS_XML_FILES = {
		"accommodation.tar.gz", "vacancy.tar.gz", "price.tar.gz" };

	public static final String getAccomodationXMLFileName(){ return TRIPVILLAS_XML_FILES[0]; }
	public static final String getVacancyXMLFileName(){ return TRIPVILLAS_XML_FILES[1]; }
	public static final String getPriceXMLFileName(){ return TRIPVILLAS_XML_FILES[2]; }

	public static Map<String,String> tripvillasAttributes = attributes;

	private static final Logger LOG = Logger.getLogger(TripvillasUtils.class
			.getName());

	public static String TRIPVILLAS_XML_LOCAL_FILE_PATH;
	public static final String TAR_EXTENSION = ".tar";
	public static final String XML_EXTENSION = ".xml";

	public static final String TRIPVILLAS_AVAILABILITY_URL = "bp.tripvillas.availability.url";
	public static final String TRIPVILLAS_BOOKING_URL = "bp.tripvillas.booking.url";
	public static final String TRIPVILLAS_ENQUIRY_URL = "bp.tripvillas.enquiry.url";
	public static final String TRIPVILLAS_RATES_URL = "bp.tripvillas.rates.url";

	static {
		LOG.info("user.home " + System.getProperty("user.home"));
		TRIPVILLAS_XML_LOCAL_FILE_PATH = System.getProperty("user.home")
				+ File.separator + "PMS" + File.separator + "tripvillas"
				+ File.separator;
		// Sub type of property
		tripvillasAttributes.put("apartment", "PCT3");
		tripvillasAttributes.put("bed and breakfast", "PCT4");
		tripvillasAttributes.put("camping ground", "PCT6");
		tripvillasAttributes.put("farm", "ACC21");
		tripvillasAttributes.put("hostel", "PCT19");
		tripvillasAttributes.put("luxury yacht", "PCT44");
		tripvillasAttributes.put("resort", "PCT30");
		tripvillasAttributes.put("villa", "PCT35");

		// Amenities
		tripvillasAttributes.put("television", "RMA251");
		tripvillasAttributes.put("swimming pool", "RST106");
		tripvillasAttributes.put("gym", "RST35");
		tripvillasAttributes.put("satellite tv", "RMA210");
		tripvillasAttributes.put("broadband", "RMA54");
		tripvillasAttributes.put("wireless internet", "RMA123");
		tripvillasAttributes.put("dvd player", "RMA163");
		tripvillasAttributes.put("hot water", "RMA242");
		tripvillasAttributes.put("shower", "RMA142");
		tripvillasAttributes.put("bath tub", "RMA13");
		tripvillasAttributes.put("housekeeping", "CSC6");
		tripvillasAttributes.put("restaurants that deliver", "HAC143");
		tripvillasAttributes.put("bus stop close by", "ACC8");
		tripvillasAttributes.put("train station close by", "ACC51");
		tripvillasAttributes.put("airport close by", "ACC1");
		tripvillasAttributes.put("shopping close by", "ACC65");
		tripvillasAttributes.put("cinema close by", "ACC50");
		tripvillasAttributes.put("public transportation", "HAC172");
		tripvillasAttributes.put("daily newspaper", "RMA73");
		tripvillasAttributes.put("beach nearby", "ACC5");
		tripvillasAttributes.put("park nearby", "ACC36");
		tripvillasAttributes.put("linen provided", "RMA234");
		tripvillasAttributes.put("microwave", "RMA68");
		tripvillasAttributes.put("airconditioning in bedrooms", "RMA2");
		tripvillasAttributes.put("fridge", "RMA88");

		tripvillasAttributes.put("king bed", "RMA58");
		tripvillasAttributes.put("queen bed", "RMA86");
		tripvillasAttributes.put("double bed", "RMA33");
		tripvillasAttributes.put("single bed", "RMA203");
		tripvillasAttributes.put("bunk bed", "RMA113");
		tripvillasAttributes.put("sofa bed", "RMA102");

		// Activities
		tripvillasAttributes.put("golf", "RST27");
		tripvillasAttributes.put("tennis", "RST94");
		tripvillasAttributes.put("archery", "RST1");
		tripvillasAttributes.put("shooting", "RST85");
		tripvillasAttributes.put("scuba diving", "RST82");
		tripvillasAttributes.put("mountaineering", "RST108");
		tripvillasAttributes.put("whitewater rafting", "RST79");
		tripvillasAttributes.put("trekking / hiking", "RST60");
		tripvillasAttributes.put("wildlife safaris", "RST113");
		tripvillasAttributes.put("bird watching", "RST127");
		tripvillasAttributes.put("nature walks", "RST161");
	}

	public static Map<String, String> getATTRIBUTES() {
		return tripvillasAttributes;
	}


	/**
	 * fetch the null location products from DB and load the location details again
	 * 
	 * @param supplierId
	 */
	public static void reloadNullLocationProducts(String supplierId) {
		SqlSession sqlSession = RazorServer.openSession();
		Location location = null;
		ArrayList<Product> nullLocProducts = ProductService.getInstance().reloadNullLocationProducts(sqlSession, supplierId);
		try{
			if(nullLocProducts != null && !nullLocProducts.isEmpty()){
				LOG.info("Total number of null location products are  " + nullLocProducts.size());
				int count = 0;
				for (Product product : nullLocProducts) {
					try{
						LOG.info("Processing product with id  " + product.getId());
						if (product.getLatitude() != null && product.getLongitude() != null && product.getPhysicaladdress() != null) {
							String[] spaceSplitAddr = product.getPhysicaladdress().split(" ");
							String countryCode = (spaceSplitAddr.length > 1) ? spaceSplitAddr[spaceSplitAddr.length-2].trim() : null;
							LOG.info("Country code : " + countryCode + " Latitude : " + product.getLatitude() + " Longitude : " + product.getLongitude());
							location = PartnerService.getLocation(sqlSession, null, countryCode, product.getLatitude(), product.getLongitude());
							LOG.info("Location Id : " + location.getId());
							if (location != null) {
								LOG.info("Persisting product with id  " + product.getId());
								product.setLocationid(location.getId());
								PartnerService.updateProduct(sqlSession, product);
								count ++;
							}
							sqlSession.commit();
						}
					} catch (Exception e) {
						LOG.error("Error while reloading product ", e);
					}
				}
				LOG.info("Total number of null location products persisted are  " + count);
			}
		}finally {
			if (sqlSession != null)
				sqlSession.close();
		}
	}
}
