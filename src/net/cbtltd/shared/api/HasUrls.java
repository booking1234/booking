/**
 * @author	abookingnet
 * @see		License at http://uat.mybookingpal.com/razor/License.html
 * @version	4.0.0
 */
package net.cbtltd.shared.api;

public interface HasUrls {

	/**
	 * Razor Local Development
	boolean LIVE = true;
	String ROOT_DIRECTORY = "C:/htdocs/";
	String TOMCAT = "C:/tomcat";
	 */

	/**
	 * Razor Cloud Live
	 * 
	 * ADD TO JournalMapper.xml license query
	 */
	boolean LIVE = true;
	
	String ROOT_DIRECTORY = "H:/htdocs/";     //Windows server.
	String ROOT_LINUX_DIRECTORY = "/data1/htdocs/";
	
	String TOMCAT = System.getProperty("os.name").contains("Win") ? "C:/tomcat" : "/opt/tomcatRazor";
	String SERVICE = "razor";
	
	//start using BrandAbbrevation. 
	String ABBREVATION_INTERHOME_AG ="INT";
	String ABBREVATION_NEXTPAX_MAIN_ACCOUNT = "NEX";
	String ABBERVATION_DANCENTER_NEXTPAX = "DAN";
	String ABBERVATION_HOGENBOOM_NEXTPAX = "HOG";
	String ABBERVATION_HAPPYHOME_NEXTPAX = "HAP";
	String ABBERVATION_GRANALACANT_NEXTPAX = "GRA";
	String ABBREVATION_HOLIDAYHOME_NEXTPAX = "HOL";
	String ABBREVATION_INTERCHALET_NEXTPAX = "INC";
	String ABBREVATION_INTERHOME_NEXTPAX = "INH";
	String ABBREVATION_NOVASOL_NEXTPAX = "NOV";
	String ABBREVATION_ROOMPOT_NEXTPAX = "ROO";
	String ABBREVATION_TOPICTRAVEL_NEXTPAX ="TOP";
	String ABBREVATION_TUIFERIENHAUS_NEXTPAX ="TUI";
	String ABBREVATION_UPHILLTRAVEL_NEXTPAX = "UPH";
	String ABBREVATION_VACASOL_NEXTPAX = "VAC";
	String ABBREVATION_BELVILLA_NEXTPAX = "BEL";
	String ABBREVATION_BUNGALOWNET_NEXTPAX = "BUN";
	String ABBREVATION_LAKE_TAHOE_ACCOMODATIONS = "LAK";
	String ABBREVATION_MAXXTON = "MEX";
	String ABBREVATION_CIIRUS = "CII";
	String ABBREVATION_AAXSYS = "AAX";
	String ABBREVATION_RENTAL_UNITED = "REN";
	String ABBREVATION_APARTSMENT_APART = "AAP";
	String ABBREVATION_BOOKT_INSTAMANAGER ="BKT";
	String ABBREVATION_RESORT_MANAGEMENT_SYSTEMS = "RMS";
	String ABBREVATION_PATHWAY_GDS_MAIN = "PAG";
	String ABBREVATION_INTERHOME_PATHWAY = "INP";
	String ABBREVATION_WEBCHALET = "WBC";
	String ABBREVATION_STREAMLINE = "STR";
	String ABBREVATION_LODGIX = "LDX";
	String ABBREVATION_LEISURELINK = "LEL";
	String ABBREVATION_BAREFOOT = "BAR";
	String ABBREVATION_HOMEAWAY_ISI = "ISI";
	String ABBREVATION_HOMEAWAY_ESCAPIA = "HES";
	
	String PARTY_MAXXTON_EMAIL = "api@maxxton.com";
	String PARTY_NEXTPAX_EMAIL ="api@nextpax.com";
	String PARTY_LAKETAHOEACCOMODATIONS_EMAIL = "api@laketahoeaccommodations.com";
	String PARTY_AAXSYS_EMAIL = "api@aaxsys.com";
	String PARTY_STREAMLINE_EMAIL = "api@streamline.com";
	String PARTY_RMS_EMAIL = "api@rms.com";
	String PARTY_PATHWAY_EMAIL = "api@pathway.com";
	String PARTY_BOOKT  =  "api@instamanager.com";
	String PARTY_WEBCHALET_EMAIL = "api@webchalet.com";
	String PARTY_LODGIX_EMAIL = "api@lodgix.com";
	String PARTY_LEISURELINK_EMAIL = "api@leisurelink.com";
	String PARTY_BAREFOOT_EMAIL = "api@barefoot.com";
	
	String FLIPKEY_CLIENTS = "5"; //make CSV when needed

	String BERMI_EXCHANGE_RATE_SERVICE = "http://webservices.bermilabs.com/exchange/";
	String OPEN_EXCHANGE_RATE_SERVICE = "http://openexchangerates.org/latest.json?app_id=e4abf9924e014610906a73cb9957bb46";
	String GOOGLE_DIRECTIONS_SERVICE = "http://maps.googleapis.com/maps/api/directions/json?";

	String DB_CONFIG = "net/cbtltd/server/xml/Configuration.xml";
	String ADMIN_EMAIL = "pavel.boiko@gmail.com";
	String CHIRAYU_SHAH_EMAIL = "chirayu@mybookingpal.com";
	String RAY_KARIMI_EMAIL = "ray@mybookingpal.com";
	String MYBOOKINGPAL_PAYPAL_EMAIL = "platfo_1255077030_biz@gmail.com";

	String MAPS_KEY_OLD = "ABQIAAAA30LJM-VtBK-8CmbP2eic4RTf7B8Hz92uOSGpB3j3J64rWpwdFRRtnzeji201m8XeOjxG-9pkasreJg"; //http://razorpms.com

	String GOOGLE_CLIENT_KEY = "AIzaSyDkdwL3blntxliu3Dpx1FGhXzKVwuMKkyA"; //uat.mybookingpal.com maps
	String GOOGLE_SERVER_KEY = "AIzaSyDFLGBq-ms2EIHKPLbVwbJVxFzGCnkBy-c";
	String GOOGLE_API_KEY ="AIzaSyDEfTrFgxRr8N0XY51KDlBB62RJBON3-Qg";//uat.mybookingpal.com translation //bookingpaltranslate@gmail.com | ps: bookingpal
	String FLIPKEY_KEY = "28816157edbf16d560e14a816c9d107c";

	String PUBLIC_DIRECTORY = "";
	String AUDIO_DIRECTORY = "audio/";
	String VIDEO_DIRECTORY = "video/";
	String PICTURES_DIRECTORY = "pictures/";
	/** The Constant REPORT_URL is where the BIRT reports are located. */
	/** The Constant REPORT_DESIGNS_DIRECTORY is where the report design scripts are located. */
	String REPORT_DESIGNS_DIRECTORY = TOMCAT + "/webapps/" + SERVICE + "/WEB-INF/classes/net/cbtltd/designs/";
	/** The Constant REPORTS_DIRECTORY is where the generated reports are located. */
	String REPORTS_DIRECTORY = TOMCAT + "/webapps/" + SERVICE + "/reports/";
	String APPLICATION_DIRECTORY = TOMCAT + "/webapps/" + SERVICE + "/WEB-INF/classes/";
}