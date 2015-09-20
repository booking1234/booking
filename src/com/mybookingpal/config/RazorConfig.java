package com.mybookingpal.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;

public class RazorConfig {
	
	static final String RAZOR_CONFIG_FILE 	= "razor-config.properties";
	static final String STORAGE_CONFIG_FILE = "storage.properties";
	static final String PARTY_IDS_FILE 		= "party-ids.properties";
	static final String MIME_CONFIG_FILE 	= "mime_mappings.xml";
	
	
	private static CompositeConfiguration razorConfig 	= new CompositeConfiguration();
	private static CompositeConfiguration storageConfig = new CompositeConfiguration();
	private static CompositeConfiguration partyIdsConfig = new CompositeConfiguration();
	private static XMLConfiguration mimeConfig			= null;
	
	private static final String ENVIRONMENT 		= "bp.environment";
	private static final String DO_TRANSLATION 		= "bp.translate";
	private static final String IMAGE_URL 	 		= "bp.image.url";
	private static final String S3_IMAGE_URL  		= "bp.s3.image.url";
	private static final String NEXTPAX_REQUEST_URL	= "bp.nextpax_request_url";
	private static final String PMSES_ON_S3 		= "bp.images.s3pmses";
	private static final String BOOKING_ID 			= "bp.booking.id";
	private static final String HOTELSCOMBINED_ID 	= "bp.hotelscombined.id";
	private static final String EDOMIZIL_ID 			= "bp.edomizil.id";
	private static final String HOSTNAME 			= "bp.hostname";
	public static final String BOOKING_POS			= "booking.pos.code";
	private static final String LAKETAHOEACCOMMODATIONS_REQUEST_FTPS_HOSTNAME = "bp.laketahoeaccommodations_reservation_request_ftps_ip";
	private static final String LAKETAHOEACCOMMODATIONS_REQUEST_FTPS_USERNAME = "bp.laketahoeaccommodations_reservation_request_ftps_username";
	private static final String LAKETAHOEACCOMMODATIONS_REQUEST_FTPS_PASSWORD = "bp.laketahoeaccommodations_reservation_request_ftps_password";
	private static final String LAKETAHOEACCOMMODATIONS_IMAGES_LOCATION = "bp.laketahoeaccommodations_images_location";
	
	private static final String PARTY_BAREFOOT_ID = "bp.barefoot";
	private static final String PARTY_BOOKT_ID = "bp.bookt";
	private static final String PARTY_CIIRUS_ID = "bp.ciirus";
	private static final String PARTY_FLIPKEY_ID = "bp.flipkey";
	private static final String PARTY_INTERHOME_ID = "bp.interhome";
	private static final String PARTY_KIGO_ID = "bp.kigo";
	private static final String PARTY_LEADERSTAY_ID = "bp.leaderstay";
	private static final String PARTY_OPENBOOK_ID = "bp.openbook";
	private static final String PARTY_RCI_ID = "bp.rci";
	private static final String PARTY_RTR_ID = "bp.rtr";
	private static final String PARTY_SUMMITCOVE_ID = "bp.summitcove";
	private static final String PARTY_LAKETAHOEACCOMODATIONS_ID = "bp.lta";
	private static final String PARTY_RENTALS_UNITED_ID = "bp.ru";
	private static final String PARTY_MAXXTON_ID = "bp.maxxton";
	private static final String PARTY_HOMEAWAY_ISI_ID = "bp.homeaway.isi";
	private static final String PARTY_HOMEAWAY_ESCAPIA_ID = "bp.homeaway.escapia";
	private static final String FLIPKEY_PARTNER_ID = "bp.flipkey.partner";
	
	// NextPax IDs
	private static final String PARTY_NEXTPAX_GRANALACANT_ID = "bp.np.granalacant";
	private static final String PARTY_NEXTPAX_BELVILLA_ID = "bp.np.belvilla";
	private static final String PARTY_NEXTPAX_BUNGALOW_ID = "bp.np.bungalow";
	private static final String PARTY_NEXTPAX_HOGENBOOM_ID = "bp.np.hogenboom";
	private static final String PARTY_NEXTPAX_HAPPYHOME_ID = "bp.np.happyhome";
	private static final String PARTY_NEXTPAX_HOLIDAY_HOME_ID = "bp.np.holidayhome";
	private static final String PARTY_NEXTPAX_INTER_CHALET_ID = "bp.np.interchalet";
	private static final String PARTY_NEXTPAX_INTERHOME_ID = "bp.np.interhome";
	private static final String PARTY_NEXTPAX_NOVASOL_ID = "bp.np.novasol";
	private static final String PARTY_NEXTPAX_ROOMPOT_ID = "bp.np.roompot";
	private static final String PARTY_NEXTPAX_TOPIC_TRAVEL_ID = "bp.np.topictravel";
	private static final String PARTY_NEXTPAX_TUI_FERIENHAUS_ID = "bp.np.tui";
	private static final String PARTY_NEXTPAX_UPHILL_TRAVEL_ID = "bp.np.uphilltravel";
	private static final String PARTY_NEXTPAX_VACASOL_ID = "bp.np.vacasol";
	private static final String PARTY_NEXTPAX_DAN_CENTER_ID = "bp.np.dancenter";
	
	public  static final String PROD_ENVIRONMENT 	= "production";
	public  static final String UAT_ENVIRONMENT 	= "uat";
	
	private static final String JUNIT_RESTCALLTESTING_INPUTFILE = "bp.junit.restcalltesting.inputfile";
	
	private static final Map<String, String> mimeTypeToExtMap = new HashMap<String, String>();
	private static final Map<String, String> mimeExtToTypeMap = new HashMap<String, String>();
	
	private static final Logger logger = Logger.getLogger(RazorConfig.class);
	private static final String EXPEDIA_CHANNEL_ID 			= "bp.expedia.id";
	public static int getExpediaChannelPartnerId() {
		// TODO Auto-generated method stub
		return razorConfig.getInt(RazorConfig.EXPEDIA_CHANNEL_ID, 600);
	}
	
	
	
	static {
		razorConfig.addConfiguration(new SystemConfiguration());
		storageConfig.addConfiguration(new SystemConfiguration());
		partyIdsConfig.addConfiguration(new SystemConfiguration());
		try {
			razorConfig.addConfiguration(new PropertiesConfiguration(RAZOR_CONFIG_FILE));
			storageConfig.addConfiguration(new PropertiesConfiguration(STORAGE_CONFIG_FILE));
			partyIdsConfig.addConfiguration(new PropertiesConfiguration(PARTY_IDS_FILE));
			mimeConfig = new XMLConfiguration(MIME_CONFIG_FILE);
			
			Object mimeTypes = mimeConfig.getProperty("mime-mappings.mime-mapping.mime-type");
			Object mimeExtensions = mimeConfig.getProperty("mime-mappings.mime-mapping.extension");

			if(mimeTypes instanceof Collection && mimeExtensions instanceof Collection) {
				System.out.println("Number of mimes: " + ((Collection<?>) mimeTypes).size());
				for(int i = 0; i < ((Collection<?>) mimeTypes).size(); i++) {
					mimeTypeToExtMap.put(((List<String>)mimeTypes).get(i), ((List<String>)mimeExtensions).get(i));
					mimeExtToTypeMap.put(((List<String>)mimeExtensions).get(i), ((List<String>)mimeTypes).get(i));
			    }
			}
			
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
	}
	
	public static String getEnvironmentId() {
		return razorConfig.getString(ENVIRONMENT);
	}
	
	public static boolean isProduction() {
		return razorConfig.getString(ENVIRONMENT).equals(PROD_ENVIRONMENT);
	}
	
	public static boolean doTranslation() {
		boolean translate = false;
		translate = isProduction() && razorConfig.getBoolean(DO_TRANSLATION);
		return translate;
	}

	public static String getNextPaxRequestURL() { 
		return razorConfig.getString(NEXTPAX_REQUEST_URL);
	}
	
	/**
	 * Common method to retrieve any configuration properties.
	 * TO DO - We can have these kind of methods to get other types like int, double etc.
	 * @param key
	 * @return
	 */
	public static String getValue(String key){
		return razorConfig.getString(key);
	}
	
	
	public static String getValue(String key,String defaultValue){
		String value=defaultValue;
		if(razorConfig.getString(key)!=null){
			value=razorConfig.getString(key);
		}
	return value;
	}
	

	public static String getImageURL(){
		return razorConfig.getString(RazorConfig.IMAGE_URL);
	}
	
	public static String getS3ImageURL(){
		return razorConfig.getString(RazorConfig.S3_IMAGE_URL);
	}
	
	public static List<Object> getPmsesOnS3List() { 
		return storageConfig.getList(PMSES_ON_S3);
	}
	
	public static boolean doCompressImages(String pmId) { 
		return storageConfig.getList(PMSES_ON_S3).contains(pmId);
	}
	
	public static String getMimeExtension(String mimeType) {
		return mimeTypeToExtMap.get(mimeType);
	}
	
	public static String getMimeType(String mimeExtension) {
		return mimeExtToTypeMap.get(mimeExtension);
	}

	public static int getBookingChannelPartnerId() {
		// TODO Auto-generated method stub
		return razorConfig.getInt(RazorConfig.BOOKING_ID, 276);
	}
	
	public static int getHotelsCombinedChannelPartnerId() {
		// TODO Auto-generated method stub
		return razorConfig.getInt(RazorConfig.HOTELSCOMBINED_ID, 420);
	}

	public static String getLaketahoeaccommodationsRequestFtpsHostname() {
		return razorConfig.getString(RazorConfig.LAKETAHOEACCOMMODATIONS_REQUEST_FTPS_HOSTNAME);
	}

	public static String getLaketahoeaccommodationsRequestFtpsUsername() {
		return razorConfig.getString(RazorConfig.LAKETAHOEACCOMMODATIONS_REQUEST_FTPS_USERNAME);
	}

	public static String getLaketahoeaccommodationsRequestFtpsPassword() {
		return razorConfig.getString(RazorConfig.LAKETAHOEACCOMMODATIONS_REQUEST_FTPS_PASSWORD);
	}
	
	public static String getLaketahoeaccommodationsImagesLocation() {
		return razorConfig.getString(RazorConfig.LAKETAHOEACCOMMODATIONS_IMAGES_LOCATION);
	}
	
	public static String getJUnitRestCallTestingInputFile() {
		return razorConfig.getString(RazorConfig.JUNIT_RESTCALLTESTING_INPUTFILE);
	}
	
	public static String getHostname() {
		return razorConfig.getString(RazorConfig.HOSTNAME);
	}
	
	// Party IDs
	public static String getBarefootId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_BAREFOOT_ID);
	}
	
	public static String getBooktId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_BOOKT_ID);
	}
	
	public static String getCiirusId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_CIIRUS_ID);
	}
	
	public static String getFlipkeyId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_FLIPKEY_ID);
	}
	
	public static String getInterhomeId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_INTERHOME_ID);
	}
	
	public static String getKigoId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_KIGO_ID);
	}
	
	public static String getLeaderstayId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_LEADERSTAY_ID);
	}
	
	public static String getOpenbookId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_OPENBOOK_ID);
	}
	
	public static String getRciId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_RCI_ID);
	}
	
	public static String getRtrId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_RTR_ID);
	}
	
	public static String getSummitcoveId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_SUMMITCOVE_ID);
	}
	
	public static String getLakeTahoeAccomodationsId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_LAKETAHOEACCOMODATIONS_ID);
	}
	
	public static String getRentalsUnitedId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_RENTALS_UNITED_ID);
	}
	
	public static String getMaxxtonId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_MAXXTON_ID);
	}
	
	public static String getFlipkeyPartnerId() {
		return partyIdsConfig.getString(RazorConfig.FLIPKEY_PARTNER_ID);
	}
	
	public static String getNextpaxGranalacantId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_NEXTPAX_GRANALACANT_ID);
	}
	
	public static String getNextpaxBelvillaId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_NEXTPAX_BELVILLA_ID);
	}
	
	public static String getNextpaxBungalowId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_NEXTPAX_BUNGALOW_ID);
	}
	
	public static String getNextpaxHogenBoomId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_NEXTPAX_HOGENBOOM_ID);
	}
	
	public static String getNextpaxHappyHomeId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_NEXTPAX_HAPPYHOME_ID);
	}
	
	public static String getNextpaxHolidayHomeId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_NEXTPAX_HOLIDAY_HOME_ID);
	}
	
	public static String getNextpaxInterChaletId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_NEXTPAX_INTER_CHALET_ID);
	}
	
	public static String getNextpaxInterHomeId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_NEXTPAX_INTERHOME_ID);
	}
	
	public static String getNextpaxNovasolId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_NEXTPAX_NOVASOL_ID);
	}
	
	public static String getNextpaxRoompotId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_NEXTPAX_ROOMPOT_ID);
	}
	
	public static String getNextpaxTopicTravelId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_NEXTPAX_TOPIC_TRAVEL_ID);
	}
	
	public static String getNextpaxTuiId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_NEXTPAX_TUI_FERIENHAUS_ID);
	}
	
	public static String getNextpaxUphillId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_NEXTPAX_UPHILL_TRAVEL_ID);
	}
	
	public static String getNextpaxVacasolId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_NEXTPAX_VACASOL_ID);
	}
	
	public static String getNextpaxDanCenterId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_NEXTPAX_DAN_CENTER_ID);
	}

	public static String getHomeawayISIId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_HOMEAWAY_ISI_ID);
	}

	public static String getHomeawayEscapiaId() {
		return partyIdsConfig.getString(RazorConfig.PARTY_HOMEAWAY_ESCAPIA_ID);
	}
	public static int getEDomizilChannelPartnerId() {
		// TODO Auto-generated method stub
		return razorConfig.getInt(RazorConfig.EDOMIZIL_ID, 400);
	}
}
