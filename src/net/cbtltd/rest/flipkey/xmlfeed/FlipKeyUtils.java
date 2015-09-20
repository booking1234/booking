package net.cbtltd.rest.flipkey.xmlfeed;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import net.cbtltd.rest.common.utils.CommonUtils;
import net.cbtltd.rest.flipkey.inquiry.Inquiry;
import net.cbtltd.server.PartnerService;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Price;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import sun.misc.BASE64Encoder;

import com.mybookingpal.config.RazorConfig;

public class FlipKeyUtils extends CommonUtils{
	
	//get flipkey URL from razorProperties file
	public static final String FLIPKEY_XML_FEED_URL = "bp.flipkey.feed.xml.url";
	public static final String FLIPKEY_XML_FEED_USERNAME = "bp.flipkey.xml.feed.username";
	public static final String FLIPKEY_XML_FEED_PWD = "bp.flipkey.xml.feed.pwd";
	public static final String FLIPKEY_INQUIRY_URL = "bp.flipkey.inquiry.url";
	public static final String FLIPKEY_INQUIRY_USERNAME = "bp.flipkey.inquiry.username";
	public static final String FLIPKEY_INQUIRY_PWD = "bp.flipkey.inquiry.pwd";
	public static final String FLIPKEY_IMAGE_URI = "bp.flipkey.image.uri";	

	private static final Logger LOG = Logger.getLogger(FlipKeyUtils.class.getName());

	// TO DO - put the file on the classpath
	public static String FLIPKEY_XML_LOCAL_FILE_PATH;
	public static final String XML_EXTENSION = ".xml";
	private static Map<String, String> ATTRIBUTES = null;

	static {
		
		LOG.info("user.home " + System.getProperty("user.home"));
		FLIPKEY_XML_LOCAL_FILE_PATH = System.getProperty("user.home")
				+ File.separator + "PMS" + File.separator + "flipkey"
				+ File.separator;
		
		if (ATTRIBUTES == null) {
			ATTRIBUTES = new HashMap<String, String>();
			ATTRIBUTES.put("Pet", "RMA140");
			ATTRIBUTES.put("Smoking", "RMA101");
			ATTRIBUTES.put("Bedroom", "RMA33");
			ATTRIBUTES.put("Air conditioning", "RMA02");
			ATTRIBUTES.put("Television", "RMA251");
			ATTRIBUTES.put("Alarm clock", "RMA03");
			ATTRIBUTES.put("News channel", "RMA04");
			ATTRIBUTES.put("AM/FM radio", "RMA05");
			ATTRIBUTES.put("Baby listening device", "RMA06");
			ATTRIBUTES.put("Balcony/Lanai/Terrace", "RMA07");
			ATTRIBUTES.put("Barbeque grills", "RMA08");
			ATTRIBUTES.put("Bath tub with spray jets", "RMA09");
			ATTRIBUTES.put("Bathrobe", "RMA10");
			ATTRIBUTES.put("Bathroom", "RMA11");
			ATTRIBUTES.put("Bathroom telephone", "RMA12");
			ATTRIBUTES.put("Bathtub", "RMA13");
			ATTRIBUTES.put("Bathtub only", "RMA14");
			ATTRIBUTES.put("Bathtub/shower combination", "RMA15");
			ATTRIBUTES.put("Bidet", "RMA16");
			ATTRIBUTES.put("Bottled water", "RMA17");
			ATTRIBUTES.put("Cable television", "RMA18");
			ATTRIBUTES.put("Coffee/Tea maker", "RMA19");
			ATTRIBUTES.put("Color television", "RMA20");
			ATTRIBUTES.put("Computer", "RMA21");
			ATTRIBUTES.put("Connecting rooms", "RMA22");
			ATTRIBUTES.put("Converters/ Voltage adaptors", "RMA23");
			ATTRIBUTES.put("Copier", "RMA24");
			ATTRIBUTES.put("Cordless phone", "RMA25");
			ATTRIBUTES.put("Cribs", "RMA26");
			ATTRIBUTES.put("Data port", "RMA27");
			ATTRIBUTES.put("Desk", "RMA28");
			ATTRIBUTES.put("Desk with lamp", "RMA29");
			ATTRIBUTES.put("Dining guide", "RMA30");
			ATTRIBUTES.put("Direct dial phone number", "RMA31");
			ATTRIBUTES.put("Dishwasher", "RMA32");
			ATTRIBUTES.put("Double beds", "RMA33");
			ATTRIBUTES.put("Dual voltage outlet", "RMA34");
			ATTRIBUTES.put("Electrical current voltage", "RMA35");
			ATTRIBUTES.put("Ergonomic chair", "RMA36");
			ATTRIBUTES.put("Extended phone cord", "RMA37");
			ATTRIBUTES.put("Fax machine", "RMA38");
			ATTRIBUTES.put("Fire alarm", "RMA39");
			ATTRIBUTES.put("Fire alarm with light", "RMA40");
			ATTRIBUTES.put("Fireplace", "RMA41");
			ATTRIBUTES.put("Free toll free calls", "RMA42");
			ATTRIBUTES.put("Free calls", "RMA43");
			ATTRIBUTES.put("Free credit card access calls", "RMA44");
			ATTRIBUTES.put("Free local calls", "RMA45");
			ATTRIBUTES.put("Free movies/video", "RMA46");
			ATTRIBUTES.put("Full kitchen", "RMA47");
			ATTRIBUTES.put("Grab bars in bathroom", "RMA48");
			ATTRIBUTES.put("Grecian tub", "RMA49");
			ATTRIBUTES.put("Hairdryer", "RMA50");
			ATTRIBUTES.put("High speed internet connection", "RMA51");
			ATTRIBUTES.put("Interactive web TV", "RMA52");
			ATTRIBUTES.put("International direct dialing", "RMA53");
			ATTRIBUTES.put("Internet access", "RMA54");
			ATTRIBUTES.put("Iron", "RMA55");
			ATTRIBUTES.put("Ironing board", "RMA56");
			ATTRIBUTES.put("Jacuzzi", "RMA57");
			ATTRIBUTES.put("King bed", "RMA58");
			ATTRIBUTES.put("Kitchen", "RMA59");
			ATTRIBUTES.put("Kitchen supplies", "RMA60");
			ATTRIBUTES.put("Kitchenette", "RMA61");
			ATTRIBUTES.put("Knock light", "RMA62");
			ATTRIBUTES.put("Laptop", "RMA63");
			ATTRIBUTES.put("Large desk", "RMA64");
			ATTRIBUTES.put("Large work area", "RMA65");
			ATTRIBUTES.put("Laundry basket/clothes hamper", "RMA66");
			ATTRIBUTES.put("Loft", "RMA67");
			ATTRIBUTES.put("Microwave", "RMA68");
			ATTRIBUTES.put("Minibar", "RMA69");
			ATTRIBUTES.put("Modem", "RMA70");
			ATTRIBUTES.put("Modem jack", "RMA71");
			ATTRIBUTES.put("Multi-line phone", "RMA72");
			ATTRIBUTES.put("Newspaper", "RMA73");
			ATTRIBUTES.put("Non-smoking", "RMA74");
			ATTRIBUTES.put("Notepads", "RMA75");
			ATTRIBUTES.put("Office supplies", "RMA76");
			ATTRIBUTES.put("Oven", "RMA77");
			ATTRIBUTES.put("Pay per view movies on TV", "RMA78");
			ATTRIBUTES.put("Pens", "RMA79");
			ATTRIBUTES.put("Phone in bathroom", "RMA80");
			ATTRIBUTES.put("Plates and bowls", "RMA81");
			ATTRIBUTES.put("Pots and pans", "RMA82");
			ATTRIBUTES.put("Prayer mats", "RMA83");
			ATTRIBUTES.put("Printer", "RMA84");
			ATTRIBUTES.put("Private bathroom", "RMA85");
			ATTRIBUTES.put("Queen bed", "RMA86");
			ATTRIBUTES.put("Recliner", "RMA87");
			ATTRIBUTES.put("Refrigerator", "RMA88");
			ATTRIBUTES.put("Refrigerator with ice maker", "RMA89");
			ATTRIBUTES.put("Remote control television", "RMA90");
			ATTRIBUTES.put("Rollaway bed", "RMA91");
			ATTRIBUTES.put("Safe", "RMA92");
			ATTRIBUTES.put("Scanner", "RMA93");
			ATTRIBUTES.put("Separate closet", "RMA94");
			ATTRIBUTES.put("Separate modem line available", "RMA95");
			ATTRIBUTES.put("Shoe polisher", "RMA96");
			ATTRIBUTES.put("Shower only", "RMA97");
			ATTRIBUTES.put("Silverware/utensils", "RMA98");
			ATTRIBUTES.put("Sitting area", "RMA99");
			ATTRIBUTES.put("Smoke detectors", "RMA100");
			ATTRIBUTES.put("Smoking", "RMA101");
			ATTRIBUTES.put("Sofa bed", "RMA102");
			ATTRIBUTES.put("Speaker phone", "RMA103");
			ATTRIBUTES.put("Stereo", "RMA104");
			ATTRIBUTES.put("Stove", "RMA105");
			ATTRIBUTES.put("Tape recorder", "RMA106");
			ATTRIBUTES.put("Telephone", "RMA107");
			ATTRIBUTES.put("Telephone for hearing impaired", "RMA108");
			ATTRIBUTES.put("Telephones with message light", "RMA109");
			ATTRIBUTES.put("Toaster oven", "RMA110");
			ATTRIBUTES.put("Trouser/Pant press", "RMA111");
			ATTRIBUTES.put("Turn down service", "RMA112");
			ATTRIBUTES.put("Twin bed", "RMA113");
			ATTRIBUTES.put("Vaulted ceilings", "RMA114");
			ATTRIBUTES.put("VCR movies", "RMA115");
			ATTRIBUTES.put("VCR player", "RMA116");
			ATTRIBUTES.put("Video games", "RMA117");
			ATTRIBUTES.put("Voice mail", "RMA118");
			ATTRIBUTES.put("Wake-up calls", "RMA119");
			ATTRIBUTES.put("Water closet", "RMA120");
			ATTRIBUTES.put("Water purification system", "RMA121");
			ATTRIBUTES.put("Wet bar", "RMA122");
			ATTRIBUTES.put("Wireless internet connection", "RMA123");
			ATTRIBUTES.put("Wireless keyboard", "RMA124");
			ATTRIBUTES.put("Adaptor available for telephone PC use", "RMA125");
			ATTRIBUTES.put("Air conditioning individually controlled in room",
					"RMA126");
			ATTRIBUTES.put("Bathtub & jacuzzi separate", "RMA127");
			ATTRIBUTES.put("Telephone with data ports", "RMA128");
			ATTRIBUTES.put("CD player", "RMA129");
			ATTRIBUTES.put("Complimentary local calls time limit", "RMA130");
			ATTRIBUTES.put("Extra person charge for rollaway use", "RMA131");
			ATTRIBUTES.put("Down/feather pillows", "RMA132");
			ATTRIBUTES.put("Desk with electrical outlet", "RMA133");
			ATTRIBUTES.put("ESPN available", "RMA134");
			ATTRIBUTES.put("Foam pillows", "RMA135");
			ATTRIBUTES.put("HBO available", "RMA136");
			ATTRIBUTES.put("High ceilings", "RMA137");
			ATTRIBUTES.put("Marble bathroom", "RMA138");
			ATTRIBUTES.put("List of movie channels available", "RMA139");
			ATTRIBUTES.put("Pets allowed", "RMA140");
			ATTRIBUTES.put("Oversized bathtub", "RMA141");
			ATTRIBUTES.put("Shower", "RMA142");
			ATTRIBUTES.put("Sink in-room", "RMA143");
			ATTRIBUTES.put("Soundproofed room", "RMA144");
			ATTRIBUTES.put("Storage space", "RMA145");
			ATTRIBUTES.put("Tables and chairs", "RMA146");
			ATTRIBUTES.put("Two-line phone", "RMA147");
			ATTRIBUTES.put("Walk-in closet", "RMA148");
			ATTRIBUTES.put("Washer/dryer", "RMA149");
			ATTRIBUTES.put("Weight scale", "RMA150");
			ATTRIBUTES.put("Welcome gift", "RMA151");
			ATTRIBUTES.put("Spare electrical outlet available at desk",
					"RMA152");
			ATTRIBUTES.put("Non-refundable charge for pets", "RMA153");
			ATTRIBUTES.put("Refundable deposit for pets", "RMA154");
			ATTRIBUTES.put("Separate tub and shower", "RMA155");
			ATTRIBUTES.put("Entrance type to guest room", "RMA156");
			ATTRIBUTES.put("Ceiling fan", "RMA157");
			ATTRIBUTES.put("CNN available", "RMA158");
			ATTRIBUTES.put("Electrical adaptors available", "RMA159");
			ATTRIBUTES.put("Buffet breakfast", "RMA160");
			ATTRIBUTES.put("Accessible room", "RMA161");
			ATTRIBUTES.put("Closets in room", "RMA162");
			ATTRIBUTES.put("DVD player", "RMA163");
			ATTRIBUTES.put("Mini-refrigerator", "RMA164");
			ATTRIBUTES.put("Separate line billing for multi-line phone",
					"RMA165");
			ATTRIBUTES.put("Self-controlled heating/cooling system", "RMA166");
			ATTRIBUTES.put("Toaster", "RMA167");
			ATTRIBUTES.put("Analog data port", "RMA168");
			ATTRIBUTES.put("Collect calls", "RMA169");
			ATTRIBUTES.put("International calls", "RMA170");
			ATTRIBUTES.put("Carrier access", "RMA171");
			ATTRIBUTES.put("Interstate calls", "RMA172");
			ATTRIBUTES.put("Intrastate calls", "RMA173");
			ATTRIBUTES.put("Local calls", "RMA174");
			ATTRIBUTES.put("Long distance calls", "RMA175");
			ATTRIBUTES.put("Operator-assisted calls", "RMA176");
			ATTRIBUTES.put("Credit card access calls", "RMA177");
			ATTRIBUTES.put("Calling card calls", "RMA178");
			ATTRIBUTES.put("Toll free calls", "RMA179");
			ATTRIBUTES.put("Universal AC/DC adaptors", "RMA180");
			ATTRIBUTES.put("Bathtub seat", "RMA181");
			ATTRIBUTES.put("Canopy/poster bed", "RMA182");
			ATTRIBUTES.put("Cups/glassware", "RMA183");
			ATTRIBUTES.put("Entertainment center", "RMA184");
			ATTRIBUTES.put("Family/oversized room", "RMA185");
			ATTRIBUTES.put("Hypoallergenic bed", "RMA186");
			ATTRIBUTES.put("Hypoallergenic pillows", "RMA187");
			ATTRIBUTES.put("Lamp", "RMA188");
			ATTRIBUTES.put("Meal included - breakfast", "RMA189");
			ATTRIBUTES.put("Meal included - continental breakfast", "RMA190");
			ATTRIBUTES.put("Meal included - dinner", "RMA191");
			ATTRIBUTES.put("Meal included - lunch", "RMA192");
			ATTRIBUTES.put("Shared bathroom", "RMA193");
			ATTRIBUTES.put("Telephone TDD/Textphone", "RMA194");
			ATTRIBUTES.put("Water bed", "RMA195");
			ATTRIBUTES.put("Extra adult charge", "RMA196");
			ATTRIBUTES.put("Extra child charge", "RMA197");
			ATTRIBUTES.put("Extra child charge for rollaway use", "RMA198");
			ATTRIBUTES.put("Meal included: full American breakfast", "RMA199");
			ATTRIBUTES.put("Futon", "RMA200");
			ATTRIBUTES.put("Murphy bed", "RMA201");
			ATTRIBUTES.put("Tatami mats", "RMA202");
			ATTRIBUTES.put("Single bed", "RMA203");
			ATTRIBUTES.put("Annex room", "RMA204");
			ATTRIBUTES.put("Free newspaper", "RMA205");
			ATTRIBUTES.put("Honeymoon suites", "RMA206");
			ATTRIBUTES.put("Complimentary high speed internet in room",
					"RMA207");
			ATTRIBUTES.put("Maid service", "RMA208");
			ATTRIBUTES.put("PC hook-up in room", "RMA209");
			ATTRIBUTES.put("Satellite television", "RMA210");
			ATTRIBUTES.put("VIP rooms", "RMA211");
			ATTRIBUTES.put("Cell phone recharger", "RMA212");
			ATTRIBUTES.put("DVR player", "RMA213");
			ATTRIBUTES.put("iPod docking station", "RMA214");
			ATTRIBUTES.put("Media center", "RMA215");
			ATTRIBUTES.put("Plug & play panel", "RMA216");
			ATTRIBUTES.put("Satellite radio", "RMA217");
			ATTRIBUTES.put("Video on demand", "RMA218");
			ATTRIBUTES.put("Exterior corridors", "RMA219");
			ATTRIBUTES.put("Gulf view", "RMA220");
			ATTRIBUTES.put("Accessible room", "RMA221");
			ATTRIBUTES.put("Interior corridors", "RMA222");
			ATTRIBUTES.put("Mountain view", "RMA223");
			ATTRIBUTES.put("Ocean view", "RMA224");
			ATTRIBUTES.put("High speed internet access fee", "RMA225");
			ATTRIBUTES.put("High speed wireless", "RMA226");
			ATTRIBUTES.put("Premium movie channels", "RMA227");
			ATTRIBUTES.put("Slippers", "RMA228");
			ATTRIBUTES.put("First nighters' kit", "RMA229");
			ATTRIBUTES.put("Chair provided with desk", "RMA230");
			ATTRIBUTES.put("Pillow top mattress", "RMA231");
			ATTRIBUTES.put("Feather bed", "RMA232");
			ATTRIBUTES.put("Duvet", "RMA233");
			ATTRIBUTES.put("Luxury linen type", "RMA234");
			ATTRIBUTES.put("International channels", "RMA235");
			ATTRIBUTES.put("Pantry", "RMA236");
			ATTRIBUTES.put("Dish-cleaning supplies", "RMA237");
			ATTRIBUTES.put("Double vanity", "RMA238");
			ATTRIBUTES.put("Lighted makeup mirror", "RMA239");
			ATTRIBUTES.put("Upgraded bathroom amenities", "RMA240");
			ATTRIBUTES.put("VCR player available at front desk", "RMA241");
			ATTRIBUTES.put("Instant hot water", "RMA242");
			ATTRIBUTES.put("Outdoor space", "RMA243");
			ATTRIBUTES.put("Hinoki tub", "RMA244");
			ATTRIBUTES.put("Private pool", "RMA245");
			ATTRIBUTES
					.put("High Definition (HD) Flat Panel Television - 32 inches or greater",
							"RMA246");
			ATTRIBUTES.put("Room windows open", "RMA247");
			ATTRIBUTES.put("Bedding type unknown or unspecified", "RMA248");
			ATTRIBUTES.put("Full bed", "RMA249");
			ATTRIBUTES.put("Round bed", "RMA250");
			ATTRIBUTES.put("TV", "RMA251");
			ATTRIBUTES.put("Child rollaway", "RMA252");
			ATTRIBUTES.put("DVD player available at front desk", "RMA253");
			ATTRIBUTES.put(
					"Video game player:? ??? ??????? ??????? ??????? ???????",
					"RMA254");
			ATTRIBUTES.put("Video game player available at front desk",
					"RMA255");
			ATTRIBUTES.put("Dining room seats", "RMA256");
			ATTRIBUTES.put("Full size mirror", "RMA257");
			ATTRIBUTES.put("Mobile/cellular phones", "RMA258");
			ATTRIBUTES.put("Movies", "RMA259");
			ATTRIBUTES.put("Multiple closets", "RMA260");
			ATTRIBUTES.put("Plates/glassware", "RMA261");
			ATTRIBUTES.put("Safe large enough to accommodate a laptop",
					"RMA262");
			ATTRIBUTES.put("Bed linen thread count", "RMA263");
			ATTRIBUTES.put("Blackout curtain", "RMA264");
			ATTRIBUTES.put("Bluray player", "RMA265");
			ATTRIBUTES.put("Device with mp3", "RMA266");
			ATTRIBUTES.put("No adult channels or adult channel lock", "RMA267");
			ATTRIBUTES.put("Non-allergenic room", "RMA268");
			ATTRIBUTES.put("Pillow type", "RMA269");
			ATTRIBUTES.put("Seating area with sofa/chair", "RMA270");
			ATTRIBUTES.put("Separate toilet area", "RMA271");
			ATTRIBUTES.put("Web enabled", "RMA272");
			ATTRIBUTES.put("Widescreen TV", "RMA273");
			ATTRIBUTES.put("Other data connection", "RMA274");
			ATTRIBUTES.put("Phoneline billed separately", "RMA275");
			ATTRIBUTES.put("Separate tub or shower", "RMA276");
			ATTRIBUTES.put("Video games", "RMA277");
			//Communication Location Type Attributes(CLT)
			ATTRIBUTES.put("Home", "CLT1");
			ATTRIBUTES.put("Business", "CLT2");
			ATTRIBUTES.put("Other", "CLT3");
			ATTRIBUTES.put("Destination", "CLT4");



			//Age Qualifying Code Attributes(AQC)
			ATTRIBUTES.put("Over 21", "AQC1");
			ATTRIBUTES.put("Over 65", "AQC2");
			ATTRIBUTES.put("Under 2", "AQC3");
			ATTRIBUTES.put("Under 12", "AQC4");
			ATTRIBUTES.put("Under 17", "AQC5");
			ATTRIBUTES.put("Under 21", "AQC6");
			ATTRIBUTES.put("Infant", "AQC7");
			ATTRIBUTES.put("Child", "AQC8");
			ATTRIBUTES.put("Teenager", "AQC9");
			ATTRIBUTES.put("Adult", "AQC10");
			ATTRIBUTES.put("Senior", "AQC11");
			ATTRIBUTES.put("Additional occupant with adult", "AQC12");
			ATTRIBUTES.put("Additional occupant without adult", "AQC13");
			ATTRIBUTES.put("Free child", "AQC14");
			ATTRIBUTES.put("Free adult", "AQC15");
			ATTRIBUTES.put("Young driver", "AQC16");
			ATTRIBUTES.put("Younger driver", "AQC17");
			ATTRIBUTES.put("Under 10", "AQC18");
			ATTRIBUTES.put("Junior", "AQC19");



			//Hotel Amenity Code Attributes(HAC)

			ATTRIBUTES.put("Piano", "HAC0");
			ATTRIBUTES.put("24-hour front desk", "HAC1");
			ATTRIBUTES.put("24-hour room service", "HAC2");
			ATTRIBUTES.put("24-hour security", "HAC3");
			ATTRIBUTES.put("Adjoining rooms", "HAC4");
			ATTRIBUTES.put("Air conditioning", "HAC5");
			ATTRIBUTES.put("Airline desk", "HAC6");
			ATTRIBUTES.put("ATM/Cash machine", "HAC7");
			ATTRIBUTES.put("Baby sitting", "HAC8");
			ATTRIBUTES.put("BBQ/Picnic area", "HAC9");
			ATTRIBUTES.put("Bilingual staf", "HAC10");
			ATTRIBUTES.put("Bookstore", "HAC11");
			ATTRIBUTES.put("Boutiques/stores", "HAC12");
			ATTRIBUTES.put("Brailed elevators", "HAC13");
			ATTRIBUTES.put("Business library", "HAC14");
			ATTRIBUTES.put("Car rental desk", "HAC15");
			ATTRIBUTES.put("Casino", "HAC16");
			ATTRIBUTES.put("Check cashing policy", "HAC17");
			ATTRIBUTES.put("Check-in kiosk", "HAC18");
			ATTRIBUTES.put("Cocktail lounge", "HAC19");
			ATTRIBUTES.put("Coffee shop", "HAC20");
			ATTRIBUTES.put("Coin operated laundry", "HAC21");
			ATTRIBUTES.put("Concierge desk", "HAC22");
			ATTRIBUTES.put("Concierge floor", "HAC23");
			ATTRIBUTES.put("Conference facilities", "HAC24");
			ATTRIBUTES.put("Courtyard", "HAC25");
			ATTRIBUTES.put("Currency exchange", "HAC26");
			ATTRIBUTES.put("Desk with electrical outlet", "HAC27");
			ATTRIBUTES.put("Doctor on call", "HAC28");
			ATTRIBUTES.put("Door man", "HAC29");
			ATTRIBUTES.put("Driving range", "HAC30");
			ATTRIBUTES.put("Drugstore/pharmacy", "HAC31");
			ATTRIBUTES.put("Duty free shop", "HAC32");
			ATTRIBUTES.put("Elevators", "HAC33");
			ATTRIBUTES.put("Executive floor", "HAC34");
			ATTRIBUTES.put("Exercise gym", "HAC35");
			ATTRIBUTES.put("Express check-in", "HAC36");
			ATTRIBUTES.put("Express check-out", "HAC37");
			ATTRIBUTES.put("Family plan", "HAC38");
			ATTRIBUTES.put("Florist", "HAC39");
			ATTRIBUTES.put("Folios", "HAC40");
			ATTRIBUTES.put("Free airport shuttle", "HAC41");
			ATTRIBUTES.put("Free parking", "HAC42");
			ATTRIBUTES.put("Free transportation", "HAC43");
			ATTRIBUTES.put("Game room", "HAC44");
			ATTRIBUTES.put("Gift/News stand", "HAC45");
			ATTRIBUTES.put("Hairdresser/barber", "HAC46");
			ATTRIBUTES.put("Accessible facilities", "HAC47");
			ATTRIBUTES.put("Health club", "HAC48");
			ATTRIBUTES.put("Heated pool", "HAC49");
			ATTRIBUTES.put("Housekeeping - daily", "HAC50");
			ATTRIBUTES.put("Housekeeping - weekly", "HAC51");
			ATTRIBUTES.put("Ice machine", "HAC52");
			ATTRIBUTES.put("Indoor parking", "HAC53");
			ATTRIBUTES.put("Indoor pool", "HAC54");
			ATTRIBUTES.put("Jacuzzi", "HAC55");
			ATTRIBUTES.put("Jogging track", "HAC56");
			ATTRIBUTES.put("Kennels", "HAC57");
			ATTRIBUTES.put("Laundry/Valet service", "HAC58");
			ATTRIBUTES.put("Liquor store", "HAC59");
			ATTRIBUTES.put("Live entertainment", "HAC60");
			ATTRIBUTES.put("Massage services", "HAC61");
			ATTRIBUTES.put("Nightclub", "HAC62");
			ATTRIBUTES.put("Off-Site parking", "HAC63");
			ATTRIBUTES.put("On-Site parking", "HAC64");
			ATTRIBUTES.put("Outdoor parking", "HAC65");
			ATTRIBUTES.put("Outdoor pool", "HAC66");
			ATTRIBUTES.put("Package/Parcel services", "HAC67");
			ATTRIBUTES.put("Parking", "HAC68");
			ATTRIBUTES.put("Photocopy center", "HAC69");
			ATTRIBUTES.put("Playground", "HAC70");
			ATTRIBUTES.put("Pool", "HAC71");
			ATTRIBUTES.put("Poolside snack bar", "HAC72");
			ATTRIBUTES.put("Public address system", "HAC73");
			ATTRIBUTES.put("Ramp access", "HAC74");
			ATTRIBUTES.put("Recreational vehicle parking", "HAC75");
			ATTRIBUTES.put("Restaurant", "HAC76");
			ATTRIBUTES.put("Room service", "HAC77");
			ATTRIBUTES.put("Safe deposit box", "HAC78");
			ATTRIBUTES.put("Sauna", "HAC79");
			ATTRIBUTES.put("Security", "HAC80");
			ATTRIBUTES.put("Shoe shine stand", "HAC81");
			ATTRIBUTES.put("Shopping mall", "HAC82");
			ATTRIBUTES.put("Solarium", "HAC83");
			ATTRIBUTES.put("Spa", "HAC84");
			ATTRIBUTES.put("Sports bar", "HAC85");
			ATTRIBUTES.put("Steam bath", "HAC86");
			ATTRIBUTES.put("Storage space", "HAC87");
			ATTRIBUTES.put("Sundry/Convenience store", "HAC88");
			ATTRIBUTES.put("Technical concierge", "HAC89");
			ATTRIBUTES.put("Theatre desk", "HAC90");
			ATTRIBUTES.put("Tour/sightseeing desk", "HAC91");
			ATTRIBUTES.put("Translation services", "HAC92");
			ATTRIBUTES.put("Travel agency", "HAC93");
			ATTRIBUTES.put("Truck parking", "HAC94");
			ATTRIBUTES.put("Valet cleaning", "HAC95");
			ATTRIBUTES.put("Dry cleaning", "HAC96");
			ATTRIBUTES.put("Valet parking", "HAC97");
			ATTRIBUTES.put("Vending machines", "HAC98");
			ATTRIBUTES.put("Video tapes", "HAC99");
			ATTRIBUTES.put("Wakeup service", "HAC100");
			ATTRIBUTES.put("Wheelchair access", "HAC101");
			ATTRIBUTES.put("Whirlpool", "HAC102");
			ATTRIBUTES.put("Multilingual staff", "HAC103");
			ATTRIBUTES.put("Wedding services", "HAC104");
			ATTRIBUTES.put("Banquet facilities", "HAC105");
			ATTRIBUTES.put("Bell staff/porter", "HAC106");
			ATTRIBUTES.put("Beauty shop/salon", "HAC107");
			ATTRIBUTES.put("Complimentary self service laundry", "HAC108");
			ATTRIBUTES.put("Direct dial telephone", "HAC109");
			ATTRIBUTES.put("Female traveler room/floor", "HAC110");
			ATTRIBUTES.put("Pharmacy", "HAC111");
			ATTRIBUTES.put("Stables", "HAC112");
			ATTRIBUTES.put("120 AC", "HAC113");
			ATTRIBUTES.put("120 DC", "HAC114");
			ATTRIBUTES.put("220 AC", "HAC115");
			ATTRIBUTES.put("Accessible parking", "HAC116");
			ATTRIBUTES.put("220 DC", "HAC117");
			ATTRIBUTES.put("Barbeque grills", "HAC118");
			ATTRIBUTES.put("Women's clothing", "HAC119");
			ATTRIBUTES.put("Men's clothing", "HAC120");
			ATTRIBUTES.put("Children's clothing", "HAC121");
			ATTRIBUTES.put("Shops and commercial services", "HAC122");
			ATTRIBUTES.put("Video games", "HAC123");
			ATTRIBUTES.put("Sports bar open for lunch", "HAC124");
			ATTRIBUTES.put("Sports bar open for dinner", "HAC125");
			ATTRIBUTES.put("Room service - full menu", "HAC126");
			ATTRIBUTES.put("Room service - limited menu", "HAC127");
			ATTRIBUTES.put("Room service - limited hours", "HAC128");
			ATTRIBUTES.put("Valet same day dry cleaning", "HAC129");
			ATTRIBUTES.put("Body scrub", "HAC130");
			ATTRIBUTES.put("Body wrap", "HAC131");
			ATTRIBUTES.put("Public area air conditioned", "HAC132");
			ATTRIBUTES.put("Efolio available to company", "HAC133");
			ATTRIBUTES.put("Individual Efolio available", "HAC134");
			ATTRIBUTES.put("Video review billing", "HAC135");
			ATTRIBUTES.put("Butler service", "HAC136");
			ATTRIBUTES.put("Complimentary in-room coffee or tea", "HAC137");
			ATTRIBUTES.put("Complimentary buffet breakfast", "HAC138");
			ATTRIBUTES.put("Complimentary cocktails", "HAC139");
			ATTRIBUTES.put("Complimentary coffee in lobby", "HAC140");
			ATTRIBUTES.put("Complimentary continental breakfast", "HAC141");
			ATTRIBUTES.put("Complimentary full american breakfast", "HAC142");
			ATTRIBUTES.put("Dinner delivery service from local restaurant", "HAC143");
			ATTRIBUTES.put("Complimentary newspaper delivered to room", "HAC144");
			ATTRIBUTES.put("Complimentary newspaper in lobby", "HAC145");
			ATTRIBUTES.put("Complimentary shoeshine", "HAC146");
			ATTRIBUTES.put("Evening reception", "HAC147");
			ATTRIBUTES.put("Front desk", "HAC148");
			ATTRIBUTES.put("Grocery shopping service available", "HAC149");
			ATTRIBUTES.put("Halal food available", "HAC150");
			ATTRIBUTES.put("Kosher food available", "HAC151");
			ATTRIBUTES.put("Limousine service", "HAC152");
			ATTRIBUTES.put("Managers reception", "HAC153");
			ATTRIBUTES.put("Medical Facilities Service", "HAC154");
			ATTRIBUTES.put("Telephone jack adaptor available", "HAC155");
			ATTRIBUTES.put("All-inclusive meal plan", "HAC156");
			ATTRIBUTES.put("Buffet breakfast", "HAC157");
			ATTRIBUTES.put("Communal bar area", "HAC158");
			ATTRIBUTES.put("Continental breakfast", "HAC159");
			ATTRIBUTES.put("Full meal plan", "HAC160");
			ATTRIBUTES.put("Full american breakfast", "HAC161");
			ATTRIBUTES.put("Meal plan available", "HAC162");
			ATTRIBUTES.put("Modified american meal plan", "HAC163");
			ATTRIBUTES.put("Food and beverage outlets", "HAC164");
			ATTRIBUTES.put("Lounges/bars", "HAC165");
			ATTRIBUTES.put("Barber shop", "HAC166");
			ATTRIBUTES.put("Video checkout", "HAC167");
			ATTRIBUTES.put("Onsite laundry", "HAC168");
			ATTRIBUTES.put("24-hour food & beverage kiosk", "HAC169");
			ATTRIBUTES.put("Concierge lounge", "HAC170");
			ATTRIBUTES.put("Parking fee managed by hotel", "HAC171");
			ATTRIBUTES.put("Transportation", "HAC172");
			ATTRIBUTES.put("Breakfast served in restaurant", "HAC173");
			ATTRIBUTES.put("Lunch served in restaurant", "HAC174");
			ATTRIBUTES.put("Dinner served in restaurant", "HAC175");
			ATTRIBUTES.put("Full service housekeeping", "HAC176");
			ATTRIBUTES.put("Limited service housekeeping", "HAC177");
			ATTRIBUTES.put("High speed internet access for laptop in public areas", "HAC178");
			ATTRIBUTES.put("Wireless internet connection in public areas", "HAC179");
			ATTRIBUTES.put("Additional services/amenities/facilities on property", "HAC180");
			ATTRIBUTES.put("Transportation services - local area", "HAC181");
			ATTRIBUTES.put("Transportation services - local office", "HAC182");
			ATTRIBUTES.put("DVD/video rental", "HAC183");
			ATTRIBUTES.put("Parking lot", "HAC184");
			ATTRIBUTES.put("Parking deck", "HAC185");
			ATTRIBUTES.put("Street side parking", "HAC186");
			ATTRIBUTES.put("Cocktail lounge with entertainment", "HAC187");
			ATTRIBUTES.put("Cocktail lounge with light fare", "HAC188");
			ATTRIBUTES.put("Motorcycle parking", "HAC189");
			ATTRIBUTES.put("Phone services", "HAC190");
			ATTRIBUTES.put("Ballroom", "HAC191");
			ATTRIBUTES.put("Bus parking", "HAC192");
			ATTRIBUTES.put("Children's play area", "HAC193");
			ATTRIBUTES.put("Children's nursery", "HAC194");
			ATTRIBUTES.put("Disco", "HAC195");
			ATTRIBUTES.put("Early check-in", "HAC196");
			ATTRIBUTES.put("Locker room", "HAC197");
			ATTRIBUTES.put("Non-smoking rooms (generic)", "HAC198");
			ATTRIBUTES.put("Train access", "HAC199");
			ATTRIBUTES.put("Aerobics instruction", "HAC200");
			ATTRIBUTES.put("Baggage hold", "HAC201");
			ATTRIBUTES.put("Bicycle rentals", "HAC202");
			ATTRIBUTES.put("Dietician", "HAC203");
			ATTRIBUTES.put("Late check-out available", "HAC204");
			ATTRIBUTES.put("Pet-sitting services", "HAC205");
			ATTRIBUTES.put("Prayer mats", "HAC206");
			ATTRIBUTES.put("Sports trainer", "HAC207");
			ATTRIBUTES.put("Turndown service", "HAC208");
			ATTRIBUTES.put("DVDs/videos - children", "HAC209");
			ATTRIBUTES.put("Bank", "HAC210");
			ATTRIBUTES.put("Lobby coffee service", "HAC211");
			ATTRIBUTES.put("Banking services", "HAC212");
			ATTRIBUTES.put("Stairwells", "HAC213");
			ATTRIBUTES.put("Pet amenities available", "HAC214");
			ATTRIBUTES.put("Exhibition/convention floor", "HAC215");
			ATTRIBUTES.put("Long term parking", "HAC216");
			ATTRIBUTES.put("Children not allowed", "HAC217");
			ATTRIBUTES.put("Children welcome", "HAC218");
			ATTRIBUTES.put("Courtesy car", "HAC219");
			ATTRIBUTES.put("Hotel does not provide pornographic films/TV", "HAC220");
			ATTRIBUTES.put("Hotspots", "HAC221");
			ATTRIBUTES.put("Free high speed internet connection", "HAC222");
			ATTRIBUTES.put("Internet services", "HAC223");
			ATTRIBUTES.put("Pets allowed", "HAC224");
			ATTRIBUTES.put("Gourmet highlights", "HAC225");
			ATTRIBUTES.put("Catering services", "HAC226");
			ATTRIBUTES.put("Complimentary breakfast", "HAC227");
			ATTRIBUTES.put("Business center", "HAC228");
			ATTRIBUTES.put("Business services", "HAC229");
			ATTRIBUTES.put("Secured parking", "HAC230");
			ATTRIBUTES.put("Racquetball", "HAC231");
			ATTRIBUTES.put("Snow sports", "HAC232");
			ATTRIBUTES.put("Tennis court", "HAC233");
			ATTRIBUTES.put("Water sports", "HAC234");
			ATTRIBUTES.put("Child programs", "HAC235");
			ATTRIBUTES.put("Golf", "HAC236");
			ATTRIBUTES.put("Horseback riding", "HAC237");
			ATTRIBUTES.put("Oceanfront", "HAC238");
			ATTRIBUTES.put("Beachfront", "HAC239");
			ATTRIBUTES.put("Hair dryer", "HAC240");
			ATTRIBUTES.put("Ironing board", "HAC241");
			ATTRIBUTES.put("Heated guest rooms", "HAC242");
			ATTRIBUTES.put("Toilet", "HAC243");
			ATTRIBUTES.put("Parlor", "HAC244");
			ATTRIBUTES.put("Video game player", "HAC245");
			ATTRIBUTES.put("Thalassotherapy", "HAC246");
			ATTRIBUTES.put("Private dining for groups", "HAC247");
			ATTRIBUTES.put("Hearing impaired services", "HAC248");
			ATTRIBUTES.put("Carryout breakfast", "HAC249");
			ATTRIBUTES.put("Deluxe continental breakfast", "HAC250");
			ATTRIBUTES.put("Hot continental breakfast", "HAC251");
			ATTRIBUTES.put("Hot breakfast", "HAC252");
			ATTRIBUTES.put("Private pool", "HAC253");
			ATTRIBUTES.put("Connecting rooms", "HAC254");
			ATTRIBUTES.put("Data port", "HAC255");
			ATTRIBUTES.put("Exterior corridors", "HAC256");
			ATTRIBUTES.put("Gulf view", "HAC257");
			ATTRIBUTES.put("Accessible rooms", "HAC258");
			ATTRIBUTES.put("High speed internet access", "HAC259");
			ATTRIBUTES.put("Interior corridors", "HAC260");
			ATTRIBUTES.put("High speed wireless", "HAC261");
			ATTRIBUTES.put("Kitchenette", "HAC262");
			ATTRIBUTES.put("Private bath or shower", "HAC263");
			ATTRIBUTES.put("Fire safety compliant", "HAC264");
			ATTRIBUTES.put("Welcome drink", "HAC265");
			ATTRIBUTES.put("Boarding pass print-out available", "HAC266");
			ATTRIBUTES.put("Printing services available", "HAC267");
			ATTRIBUTES.put("All public areas non-smoking", "HAC268");
			ATTRIBUTES.put("Meeting rooms", "HAC269");
			ATTRIBUTES.put("Movies in room", "HAC270");
			ATTRIBUTES.put("Secretarial service", "HAC271");
			ATTRIBUTES.put("Snow skiing", "HAC272");
			ATTRIBUTES.put("Water skiing", "HAC273");
			ATTRIBUTES.put("Fax service", "HAC274");
			ATTRIBUTES.put("Great room", "HAC275");
			ATTRIBUTES.put("Lobby", "HAC276");
			ATTRIBUTES.put("Multiple phone lines billed separately", "HAC277");
			ATTRIBUTES.put("Umbrellas", "HAC278");
			ATTRIBUTES.put("Gas station", "HAC279");
			ATTRIBUTES.put("Grocery store", "HAC280");
			ATTRIBUTES.put("24-hour coffee shop", "HAC281");
			ATTRIBUTES.put("Airport shuttle service", "HAC282");
			ATTRIBUTES.put("Luggage service", "HAC283");
			ATTRIBUTES.put("Piano Bar", "HAC284");
			ATTRIBUTES.put("VIP security", "HAC285");
			ATTRIBUTES.put("Complimentary wireless internet", "HAC286");
			ATTRIBUTES.put("Concierge breakfast", "HAC287");
			ATTRIBUTES.put("Same gender floor", "HAC288");
			ATTRIBUTES.put("Children programs", "HAC289");
			ATTRIBUTES.put("Building meets local, state and country building codes", "HAC290");
			ATTRIBUTES.put("Internet browser On TV", "HAC291");
			ATTRIBUTES.put("Newspaper", "HAC292");
			ATTRIBUTES.put("Parking - controlled access gates to enter parking area", "HAC293");
			ATTRIBUTES.put("Hotel safe deposit box (not room safe box)", "HAC294");
			ATTRIBUTES.put("Storage space available ï¿½ fee", "HAC295");
			ATTRIBUTES.put("Type of entrances to guest rooms", "HAC296");
			ATTRIBUTES.put("Beverage/cocktail", "HAC297");
			ATTRIBUTES.put("Cell phone rental", "HAC298");
			ATTRIBUTES.put("Coffee/tea", "HAC299");
			ATTRIBUTES.put("Early check in guarantee", "HAC300");
			ATTRIBUTES.put("Food and beverage discount", "HAC301");
			ATTRIBUTES.put("Late check out guarantee", "HAC302");
			ATTRIBUTES.put("Room upgrade confirmed", "HAC303");
			ATTRIBUTES.put("Room upgrade on availability", "HAC304");
			ATTRIBUTES.put("Shuttle to local businesses", "HAC305");
			ATTRIBUTES.put("Shuttle to local attractions", "HAC306");
			ATTRIBUTES.put("Social hour", "HAC307");
			ATTRIBUTES.put("Video billing", "HAC308");
			ATTRIBUTES.put("Welcome gift", "HAC309");
			ATTRIBUTES.put("Hypoallergenic rooms", "HAC310");
			ATTRIBUTES.put("Room air filtration", "HAC311");
			ATTRIBUTES.put("Smoke-free property", "HAC312");
			ATTRIBUTES.put("Water purification system in use", "HAC313");
			ATTRIBUTES.put("Poolside service", "HAC314");
			ATTRIBUTES.put("Clothing store", "HAC315");
			ATTRIBUTES.put("Electric car charging stations", "HAC316");
			ATTRIBUTES.put("Office rental", "HAC317");
			ATTRIBUTES.put("Incoming fax", "HAC319");
			ATTRIBUTES.put("Outgoing fax", "HAC320");
			ATTRIBUTES.put("Semi-private space", "HAC321");
			ATTRIBUTES.put("Loading dock", "HAC322");





			// Bed Type Attributes (BED)

			ATTRIBUTES.put("Double", "BED1");
			ATTRIBUTES.put("Futon", "BED2");
			ATTRIBUTES.put("King", "BED3");
			ATTRIBUTES.put("Murphy bed", "BED4");
			ATTRIBUTES.put("Queen", "BED5");
			ATTRIBUTES.put("Sofa bed", "BED6");
			ATTRIBUTES.put("Tatami mats", "BED7");
			ATTRIBUTES.put("Twin", "BED8");
			ATTRIBUTES.put("Single", "BED9");
			ATTRIBUTES.put("Full", "BED10");
			ATTRIBUTES.put("Run of the house", "BED11");
			ATTRIBUTES.put("Dorm bed", "BED12");




			//location Category Codes(LOC)
			ATTRIBUTES.put("Airport", "LOC1");
			ATTRIBUTES.put("Beach","LOC2");
			ATTRIBUTES.put("City","LOC3");
			ATTRIBUTES.put("Downtown","LOC4");
			ATTRIBUTES.put("East","LOC5");
			ATTRIBUTES.put("Expressway","LOC6");
			ATTRIBUTES.put("Lake","LOC7");
			ATTRIBUTES.put("Mountain","LOC8");
			ATTRIBUTES.put("North","LOC9");
			ATTRIBUTES.put("Resort","LOC10");
			ATTRIBUTES.put("Rural","LOC11");
			ATTRIBUTES.put("South","LOC12");
			ATTRIBUTES.put("Suburban","LOC13");
			ATTRIBUTES.put("West","LOC14");
			ATTRIBUTES.put("Beachfront","LOC15");
			ATTRIBUTES.put("Oceanfront","LOC16");
			ATTRIBUTES.put("Gulf","LOC17");
			ATTRIBUTES.put("Business district","LOC18");
			ATTRIBUTES.put("Entertainment district","LOC19");
			ATTRIBUTES.put("Financial district","LOC20");
			ATTRIBUTES.put("Shopping district","LOC21");
			ATTRIBUTES.put("Theatre district","LOC22");
			ATTRIBUTES.put("Countryside","LOC23");
			ATTRIBUTES.put("Bay","LOC24");
			ATTRIBUTES.put("Marina","LOC25");
			ATTRIBUTES.put("Park","LOC26");
			ATTRIBUTES.put("River","LOC27");
			ATTRIBUTES.put("Tourist site","LOC228");
			ATTRIBUTES.put("North suburb","LOC29");
			ATTRIBUTES.put("South suburb","LOC30");
			ATTRIBUTES.put("East suburb","LOC31");
			ATTRIBUTES.put("West suburb","LOC32");
			ATTRIBUTES.put("Waterfront","LOC33");
			ATTRIBUTES.put("Ski resort","LOC34");

			//Property Class Type (PCT)

			ATTRIBUTES.put("All suite", "PCT1");
			ATTRIBUTES.put("All-Inclusive resort", "PCT2");
			ATTRIBUTES.put("Apartment", "PCT3");
			ATTRIBUTES.put("Bed and breakfast", "PCT4");
			ATTRIBUTES.put("Cabin or bungalow", "PCT5");
			ATTRIBUTES.put("Campground", "PCT6");
			ATTRIBUTES.put("Chalet", "PCT7");
			ATTRIBUTES.put("Condominium", "PCT8");
			ATTRIBUTES.put("Conference center", "PCT9");
			ATTRIBUTES.put("Corporate", "PCT10");
			ATTRIBUTES.put("Corporate business transient", "PCT11");
			ATTRIBUTES.put("Cruise", "PCT12");
			ATTRIBUTES.put("Extended stay", "PCT13");
			ATTRIBUTES.put("Ferry", "PCT14");
			ATTRIBUTES.put("Guest farm", "PCT15");
			ATTRIBUTES.put("Guest house limited service", "PCT16");
			ATTRIBUTES.put("Health spa", "PCT17");
			ATTRIBUTES.put("Holiday resort", "PCT18");
			ATTRIBUTES.put("Hostel", "PCT19");
			ATTRIBUTES.put("Hotel", "PCT20");
			ATTRIBUTES.put("Inn", "PCT21");
			ATTRIBUTES.put("Lodge", "PCT22");
			ATTRIBUTES.put("Meeting resort", "PCT23");
			ATTRIBUTES.put("Meeting/Convention", "PCT24");
			ATTRIBUTES.put("Mobile-home", "PCT25");
			ATTRIBUTES.put("Monastery", "PCT26");
			ATTRIBUTES.put("Motel", "PCT27");
			ATTRIBUTES.put("Ranch", "PCT28");
			ATTRIBUTES.put("Residential apartment", "PCT29");
			ATTRIBUTES.put("Resort", "PCT30");
			ATTRIBUTES.put("Sailing ship", "PCT31");
			ATTRIBUTES.put("Self catering accommodation", "PCT32");
			ATTRIBUTES.put("Tent", "PCT33");
			ATTRIBUTES.put("Vacation home", "PCT34");
			ATTRIBUTES.put("Villa", "PCT35");
			ATTRIBUTES.put("Wildlife reserve", "PCT36");
			ATTRIBUTES.put("Castle", "PCT37");
			ATTRIBUTES.put("Convention Network Property", "PCT38");
			ATTRIBUTES.put("Golf", "PCT39");
			ATTRIBUTES.put("Pension", "PCT40");
			ATTRIBUTES.put("Ski", "PCT41");
			ATTRIBUTES.put("Spa", "PCT42");
			ATTRIBUTES.put("Time share", "PCT43");
			ATTRIBUTES.put("Boatel", "PCT44");
			ATTRIBUTES.put("Boutique", "PCT45");
			ATTRIBUTES.put("Efficiency/studio", "PCT46");
			ATTRIBUTES.put("Full service", "PCT47");
			ATTRIBUTES.put("Historical", "PCT48");
			ATTRIBUTES.put("Limited service", "PCT49");
			ATTRIBUTES.put("Recreational vehicle park", "PCT50");
			ATTRIBUTES.put("Charm hotel", "PCT51");
			ATTRIBUTES.put("Manor", "PCT52");
			ATTRIBUTES.put("Vacation rental", "PCT53");
			ATTRIBUTES.put("Economy", "PCT54");
			ATTRIBUTES.put("Midscale", "PCT55");
			ATTRIBUTES.put("Upscale", "PCT56");
			ATTRIBUTES.put("Luxury", "PCT57");
			ATTRIBUTES.put("Union", "PCT58");
			ATTRIBUTES.put("Leisure", "PCT59");
			ATTRIBUTES.put("Wholesale", "PCT60");
			ATTRIBUTES.put("Transient", "PCT61");







			//Pets Policy Code (PET)

			ATTRIBUTES.put("Cats only", "PET1");
			ATTRIBUTES.put("Dogs only", "PET2");
			ATTRIBUTES.put("Large domestic animals", "PET3");
			ATTRIBUTES.put("Midsize domestic animals", "PET4");
			ATTRIBUTES.put("Small domestic animals only", "PET5");
			ATTRIBUTES.put("Working animals only", "PET6");
			ATTRIBUTES.put("All pets", "PET7");
			ATTRIBUTES.put("Small domestic animals", "PET8");
			ATTRIBUTES.put("Working animals", "PET9");
			ATTRIBUTES.put("Domestic pets", "PET10");




			//Physocally Challenged Feature Code(PHY)

			ATTRIBUTES.put("Americans with Disabilities Act (ADA) compliance", "PHY1");
			ATTRIBUTES.put("Audible emergency alarm for sight impaired guest", "PHY2");
			ATTRIBUTES.put("Bathroom vanity in guest rooms for disabled person height", "PHY3");
			ATTRIBUTES.put("Bed types of wheelchair accessible rooms", "PHY4");
			ATTRIBUTES.put("Closet rods in guest rooms for disabled person height", "PHY5");
			ATTRIBUTES.put("Complies with Local/State/Federal laws for disabled", "PHY6");
			ATTRIBUTES.put("Describe in detail how your kitchen is accessible. Include special appliances, lowered cabinets, where dishes are kept, are upper cabinets usable.", "PHY7");
			ATTRIBUTES.put("Does hotel have accessible parking?", "PHY8");
			ATTRIBUTES.put("Door width in inches", "PHY9");
			ATTRIBUTES.put("Elevators have Braille Instructions", "PHY10");
			ATTRIBUTES.put("Emergency exit signs in Braille", "PHY11");
			ATTRIBUTES.put("Emergency info in Braille", "PHY12");
			ATTRIBUTES.put("Flashing door knocker available for disabled person", "PHY13");
			ATTRIBUTES.put("Impairment aids available", "PHY14");
			ATTRIBUTES.put("Light switches in guest rooms for disabled persons height", "PHY15");
			ATTRIBUTES.put("List available room types for disabled persons- 8 fields", "PHY16");
			ATTRIBUTES.put("Lowered deadbolt in guest room for disabled persons height (in feet)", "PHY17");
			ATTRIBUTES.put("Lowered deadbolt in guest room for disabled persons height (in inches)", "PHY18");
			ATTRIBUTES.put("Number of each room type equipped for disabled persons- 8 fields", "PHY19");
			ATTRIBUTES.put("Number of roll-in showers available for disabled person", "PHY20");
			ATTRIBUTES.put("Number of rooms for disabled persons equipped with standard tub", "PHY21");
			ATTRIBUTES.put("Number of rooms with Braille", "PHY22");
			ATTRIBUTES.put("Number of rooms with wheelchair accessible showers", "PHY23");
			ATTRIBUTES.put("Number of wheelchair accessible rooms", "PHY24");
			ATTRIBUTES.put("Other services for persons with disabilities", "PHY25");
			ATTRIBUTES.put("Peephole in guest room for disabled person height (in ft)", "PHY26");
			ATTRIBUTES.put("Peephole in guest room for disabled person height (in inches)", "PHY27");
			ATTRIBUTES.put("Public areas wheelchair accessible for disabled", "PHY28");
			ATTRIBUTES.put("Service animals allowed on property for people with disabilities", "PHY29");
			ATTRIBUTES.put("Thermostat in guest for disabled persons height (in feet)", "PHY30");
			ATTRIBUTES.put("Thermostat in guest for disabled persons height (in inches)", "PHY31");
			ATTRIBUTES.put("Toilet seat in guest rooms for disabled person", "PHY32");
			ATTRIBUTES.put("Vibrating alarm available for disabled persons", "PHY33");
			ATTRIBUTES.put("Visual emergency alarm for hearing impaired guest", "PHY34");
			ATTRIBUTES.put("What room types have wheel-in showers? (free form stringbox)", "PHY35");
			ATTRIBUTES.put("Wheelchairs available", "PHY36");
			ATTRIBUTES.put("Which floors have handicapped rooms", "PHY37");
			ATTRIBUTES.put("Grab bars in bathroom", "PHY38");
			ATTRIBUTES.put("Telephone for hearing impaired", "PHY39");
			ATTRIBUTES.put("Light switches in guest rooms for disabled persons height (feet)", "PHY40");
			ATTRIBUTES.put("Light switches in guest rooms for disabled persons height (inches)", "PHY41");
			ATTRIBUTES.put("Wheelchair accessible elevators", "PHY42");
			ATTRIBUTES.put("Hearing impaired services", "PHY43");
			ATTRIBUTES.put("Bathtub seat", "PHY44");
			ATTRIBUTES.put("Closed caption TV", "PHY45");
			ATTRIBUTES.put("Safety bars in shower", "PHY46");
			ATTRIBUTES.put("Television amplifier", "PHY47");
			ATTRIBUTES.put("Walk-in shower", "PHY48");
			ATTRIBUTES.put("Ramp access", "PHY49");
			ATTRIBUTES.put("Handicapped parking", "PHY50");
			ATTRIBUTES.put("Handicapped van parking", "PHY51");
			ATTRIBUTES.put("Raised toilet seat with grab bars", "PHY52");
			ATTRIBUTES.put("Bathroom doors open outwards", "PHY53");
			ATTRIBUTES.put("Accommodations have bath in bedroom", "PHY54");
			ATTRIBUTES.put("Elevator access to all levels", "PHY55");
			ATTRIBUTES.put("Elevator near disability accessible rooms", "PHY56");
			ATTRIBUTES.put("Emergency cord/button system in bathroom", "PHY57");
			ATTRIBUTES.put("Emergency cord/button system in bedroom", "PHY58");
			ATTRIBUTES.put("Emergency instructions in pictorial form", "PHY59");
			ATTRIBUTES.put("Emergency procedures for disabled guests", "PHY60");
			ATTRIBUTES.put("Facilities for blind people only", "PHY61");
			ATTRIBUTES.put("Facilities for deaf people only", "PHY62");
			ATTRIBUTES.put("Feather free bedding/pillows available", "PHY63");
			ATTRIBUTES.put("Flat terrain between parking and entrance", "PHY64");
			ATTRIBUTES.put("Hearing induction loop system installed", "PHY65");
			ATTRIBUTES.put("Restaurant/bar menus available in Braille", "PHY66");
			ATTRIBUTES.put("Restaurant/bar menus available in 14pt print", "PHY67");
			ATTRIBUTES.put("Roll-in shower area with bathroom seat", "PHY68");
			ATTRIBUTES.put("Service dogs allowed", "PHY69");
			ATTRIBUTES.put("Staff proficient in sign language", "PHY70");
			ATTRIBUTES.put("Staff trained in service to disabled guests", "PHY71");
			ATTRIBUTES.put("Steps/staircases have handrails", "PHY72");
			ATTRIBUTES.put("Steps/staircases have color markings", "PHY73");
			ATTRIBUTES.put("Subtitles/closed captions available on TV", "PHY74");
			ATTRIBUTES.put("Tactile/14pt print signage throughout hotel", "PHY75");
			ATTRIBUTES.put("Vibrating pillows available", "PHY76");
			ATTRIBUTES.put("Height of bathroom basin", "PHY77");
			ATTRIBUTES.put("Height of bathroom toilet seat", "PHY78");
			ATTRIBUTES.put("Height of controls at highest operable part for bath", "PHY79");
			ATTRIBUTES.put("Height of controls at highest operable part for roll-in shower", "PHY80");
			ATTRIBUTES.put("Height of disable guest bed including mattress", "PHY81");
			ATTRIBUTES.put("Height of elevator external buttons", "PHY82");
			ATTRIBUTES.put("Height of elevator internal buttons", "PHY83");
			ATTRIBUTES.put("Height from ground to light switches in bathroom", "PHY84");
			ATTRIBUTES.put("Height of non-slip handrails adjacent to bathroom toilet", "PHY85");
			ATTRIBUTES.put("Height of non-slip handrails adjacent to bath", "PHY86");
			ATTRIBUTES.put("Height of non-slip handrails in shower area", "PHY87");
			ATTRIBUTES.put("Width/diameter of clear floor space in front of bath", "PHY88");
			ATTRIBUTES.put("Length/depth of clear floor space in front of bath", "PHY89");
			ATTRIBUTES.put("Width/diameter of clear floor space in front of guest bathroom toilet", "PHY90");
			ATTRIBUTES.put("Length/depth of clear floor space in front of guest bathroom toilet", "PHY91");
			ATTRIBUTES.put("Width/diameter of clear floor space at main hotel entrance", "PHY92");
			ATTRIBUTES.put("Width/diameter of clear floor space at main restaurant entrance", "PHY93");
			ATTRIBUTES.put("Width/diameter of elevator clear door opening space", "PHY94");
			ATTRIBUTES.put("Width/diameter of maincorridors", "PHY95");
			ATTRIBUTES.put("Width/diameter of wheelchair turning space in bathroom", "PHY96");
			ATTRIBUTES.put("Width/diameter of wheelchair turning space in bedroom", "PHY97");
			ATTRIBUTES.put("Width/diameter of wheelchair turning space in lobby/reception area", "PHY98");
			ATTRIBUTES.put("Width/diameter of clear opening space at bathroom door", "PHY99");
			ATTRIBUTES.put("Width/diameter of clear opening space at bedroom door", "PHY100");
			ATTRIBUTES.put("Height of elevator internal handrails", "PHY101");
			ATTRIBUTES.put("Accessible baths", "PHY102");
			ATTRIBUTES.put("Braille/large print literature", "PHY103");
			ATTRIBUTES.put("Adapted room doors", "PHY104");
			ATTRIBUTES.put("Bedroom wheelchair access", "PHY105");
			ATTRIBUTES.put("Special needs menus", "PHY106");
			ATTRIBUTES.put("Wide entrance", "PHY107");
			ATTRIBUTES.put("Wide corridors", "PHY108");
			ATTRIBUTES.put("Wide restaurant entrance", "PHY109");
			ATTRIBUTES.put("Roll-in shower available", "PHY110");
			ATTRIBUTES.put("Lever handles on guest room doors", "PHY111");
			ATTRIBUTES.put("Lowered night guards on guest room doors", "PHY112");
			ATTRIBUTES.put("Lowered electrical outlets", "PHY113");
			ATTRIBUTES.put("Bathtub grab bars", "PHY114");
			ATTRIBUTES.put("Adjustable height hand-held shower wand", "PHY115");
			ATTRIBUTES.put("TTY/TTD compatible", "PHY116");
			ATTRIBUTES.put("Bathroom doors in inches", "PHY117");
			ATTRIBUTES.put("Wheelchair, passenger has own chair, trains", "PHY118");
			ATTRIBUTES.put("Wheelchair, passenger has own chair, buses", "PHY119");
			ATTRIBUTES.put("Wheelchair, passenger requires station wheelchair", "PHY120");
			ATTRIBUTES.put("Wheelchair lift required", "PHY121");
			ATTRIBUTES.put("Lower level seating required", "PHY122");
			ATTRIBUTES.put("Passenger has oxygen equipment", "PHY123");
			ATTRIBUTES.put("Passenger has meet and assist requirement", "PHY124");




			//Security Feature Code (SEC)
			ATTRIBUTES.put("2nd lock on guest doors", "SEC1");
			ATTRIBUTES.put("Dead bolts on guest room doors", "SEC10");
			ATTRIBUTES.put("Swingbolt lock", "SEC100");
			ATTRIBUTES.put("AAA security standards", "SEC101");
			ATTRIBUTES.put("Connecting doors have deadbolts", "SEC102");
			ATTRIBUTES.put("Emergency call button on phone", "SEC103");
			ATTRIBUTES.put("Audible alarms", "SEC104");
			ATTRIBUTES.put("Distance to nearest police station", "SEC11");
			ATTRIBUTES.put("Doctor on call", "SEC12");
			ATTRIBUTES.put("Electronic room key", "SEC13");
			ATTRIBUTES.put("Elevator auto recall", "SEC14");
			ATTRIBUTES.put("Emergency back-up generators", "SEC15");
			ATTRIBUTES.put("Emergency evacuation plan", "SEC16");
			ATTRIBUTES.put("Emergency exit maps", "SEC17");
			ATTRIBUTES.put("Emergency info in room", "SEC18");
			ATTRIBUTES.put("Emergency lighting", "SEC19");
			ATTRIBUTES.put("Address of nearest police station", "SEC2");
			ATTRIBUTES.put("Fire detectors in guest rooms", "SEC20");
			ATTRIBUTES.put("Fire detectors in hallways", "SEC21");
			ATTRIBUTES.put("Fire detectors in public areas", "SEC22");
			ATTRIBUTES.put("Fire extinguishers", "SEC23");
			ATTRIBUTES.put("Fire resistant guest room doors", "SEC24");
			ATTRIBUTES.put("Fire resistant hall room doors", "SEC25");
			ATTRIBUTES.put("Frequency of evacuation drills", "SEC26");
			ATTRIBUTES.put("Guest room doors self-closing", "SEC27");
			ATTRIBUTES.put("Hard wired smoke detectors", "SEC28");
			ATTRIBUTES.put("If no 24 hour security, what are the hours?", "SEC29");
			ATTRIBUTES.put("Alarms continuously monitored", "SEC3");
			ATTRIBUTES.put("Lighted parking area", "SEC30");
			ATTRIBUTES.put("Lighted walkways", "SEC31");
			ATTRIBUTES.put("Multiple exits on each floor", "SEC32");
			ATTRIBUTES.put("Parking area attendants", "SEC33");
			ATTRIBUTES.put("Patrolled parking area", "SEC34");
			ATTRIBUTES.put("Phone number of nearest police station", "SEC35");
			ATTRIBUTES.put("Property has elevators", "SEC36");
			ATTRIBUTES.put("Public address system", "SEC37");
			ATTRIBUTES.put("Response time (minutes) from fire/police department", "SEC38");
			ATTRIBUTES.put("Restricted public access", "SEC39");
			ATTRIBUTES.put("Audible alarm smoke detectors in guest rooms", "SEC4");
			ATTRIBUTES.put("Room access through exterior corridor", "SEC40");
			ATTRIBUTES.put("Room access through interior corridor", "SEC41");
			ATTRIBUTES.put("Room accessible through balcony sliding glass doors", "SEC42");
			ATTRIBUTES.put("Room windows open", "SEC43");
			ATTRIBUTES.put("Safety chains on guest doors", "SEC44");
			ATTRIBUTES.put("Secondary locks on room windows", "SEC45");
			ATTRIBUTES.put("Secondary locks on sliding glass doors", "SEC46");
			ATTRIBUTES.put("Security 24 hours/day", "SEC47");
			ATTRIBUTES.put("Smoke detector in guest rooms", "SEC48");
			ATTRIBUTES.put("Smoke detector in hallways", "SEC49");
			ATTRIBUTES.put("Auto link to fire station", "SEC5");
			ATTRIBUTES.put("Smoke detector in public areas", "SEC50");
			ATTRIBUTES.put("Some guest rooms have a balcony", "SEC51");
			ATTRIBUTES.put("Sprinklers in guest rooms", "SEC52");
			ATTRIBUTES.put("Sprinklers in hallways", "SEC53");
			ATTRIBUTES.put("Sprinklers in public areas", "SEC54");
			ATTRIBUTES.put("Staff trained in CPR", "SEC55");
			ATTRIBUTES.put("Staff trained in duplicate key issue", "SEC56");
			ATTRIBUTES.put("Staff trained in first aid", "SEC57");
			ATTRIBUTES.put("Uniformed security", "SEC58");
			ATTRIBUTES.put("Ventilated stair wells", "SEC59");
			ATTRIBUTES.put("Automatic fire doors", "SEC6");
			ATTRIBUTES.put("Video cameras at entrance", "SEC60");
			ATTRIBUTES.put("Video cameras in hallways", "SEC61");
			ATTRIBUTES.put("Video cameras in public areas", "SEC62");
			ATTRIBUTES.put("Viewports in guest room doors", "SEC63");
			ATTRIBUTES.put("Visual alarm smoke detectors in guest rooms", "SEC64");
			ATTRIBUTES.put("Well lighted exit signs", "SEC65");
			ATTRIBUTES.put("Which floors have exterior entrances", "SEC66");
			ATTRIBUTES.put("Which floors have interior entrances", "SEC67");
			ATTRIBUTES.put("Balcony accessibly by adjoining rooms", "SEC68");
			ATTRIBUTES.put("Double glazed windows", "SEC69");
			ATTRIBUTES.put("Building meets all current local, state and country building codes", "SEC7");
			ATTRIBUTES.put("Fire extinguishers in hallways", "SEC70");
			ATTRIBUTES.put("Security", "SEC71");
			ATTRIBUTES.put("Audible alarms in hallways", "SEC72");
			ATTRIBUTES.put("Audible alarms in public areas", "SEC73");
			ATTRIBUTES.put("Automated External Defibrillator (AED) on-site", "SEC74");
			ATTRIBUTES.put("Basic medical equipment on-site", "SEC75");
			ATTRIBUTES.put("Camera monitoring parking area 24 hrs", "SEC76");
			ATTRIBUTES.put("Camera recording parking area 24 hrs", "SEC77");
			ATTRIBUTES.put("Controlled access to parking", "SEC78");
			ATTRIBUTES.put("Exterior doors (except lobby entrance) require key access at night", "SEC79");
			ATTRIBUTES.put("Complies with Hotel/Motel Safety Act", "SEC8");
			ATTRIBUTES.put("Staff Red Cross certified in CPR", "SEC80");
			ATTRIBUTES.put("Staff trained in Automated External Defibrillator (AED) usage", "SEC81");
			ATTRIBUTES.put("Video surveillance of parking", "SEC82");
			ATTRIBUTES.put("Visual alarms in hallways", "SEC83");
			ATTRIBUTES.put("Visual alarms in public areas", "SEC84");
			ATTRIBUTES.put("Emergency exits on each floor", "SEC85");
			ATTRIBUTES.put("Video surveillance recorded 24 hrs a day", "SEC86");
			ATTRIBUTES.put("Video surveillance monitored 24 hrs a day", "SEC87");
			ATTRIBUTES.put("Carbon monoxide detector", "SEC88");
			ATTRIBUTES.put("Fire extinguishers in public areas", "SEC89");
			ATTRIBUTES.put("Complies with Local/State/Federal fire laws", "SEC9");
			ATTRIBUTES.put("First aid available", "SEC90");
			ATTRIBUTES.put("Private security available", "SEC91");
			ATTRIBUTES.put("Secured floors", "SEC92");
			ATTRIBUTES.put("U.S. Fire Safetycompliant", "SEC93");
			ATTRIBUTES.put("Evacuation drills", "SEC94");
			ATTRIBUTES.put("Hotel has fire safety measures in place but does not meet a national fire safety standard", "SEC95");
			ATTRIBUTES.put("FEMA approved", "SEC96");
			ATTRIBUTES.put("Hours security", "SEC97");
			ATTRIBUTES.put("Emergency svc response time (minutes)", "SEC98");
			ATTRIBUTES.put("Security escorts available on request", "SEC99");

		}

	}

	public static HttpURLConnection getConnection(String flipKeyURL,
			String username, String pwd) {
		String htmlString = "";
		HttpURLConnection connection = null;
		try {
			URL url = new URL(flipKeyURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			// connection.setRequestProperty("Content-Type",
			// "application/html");
			BASE64Encoder enc = new sun.misc.BASE64Encoder();
			String userpassword = username + ":" + pwd;
			String encodedAuthorization = enc.encode(userpassword.getBytes());
			connection.setRequestProperty("Authorization", "Basic "
					+ encodedAuthorization);

			// connection.setRequestProperty("Accept", "application/html");

			connection.connect();

			if (connection.getResponseCode() != 200) {
				throw new RuntimeException("HTTP:"
						+ connection.getResponseCode() + " URL " + url);
			}

		} catch (Throwable x) {
			x.printStackTrace();
			throw new RuntimeException(x.getMessage());

		}
		return connection;
	}

	
	public static final int callFlipKeyInquiryAPI(Inquiry inquiry)
			throws Throwable {

		JAXBContext jaxbContext = JAXBContext.newInstance(Inquiry.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		StringWriter xmlWriter = new StringWriter();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(inquiry, xmlWriter);
		// output pretty printed

		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(RazorConfig.getValue(FLIPKEY_INQUIRY_URL));
		LOG.info("Flipkey Image URI Value : "+RazorConfig.getValue(FLIPKEY_INQUIRY_URL));

		// add header
		Credentials credentials = new UsernamePasswordCredentials(
				RazorConfig.getValue(FLIPKEY_INQUIRY_USERNAME),
				RazorConfig.getValue(FLIPKEY_INQUIRY_PWD));
		LOG.info("Flipkey Inquiry Username & PWD  : "+RazorConfig.getValue(FLIPKEY_INQUIRY_USERNAME)+
				RazorConfig.getValue(FLIPKEY_INQUIRY_PWD));
		client.getCredentialsProvider().setCredentials(AuthScope.ANY,
				credentials);

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		LOG.debug("xmlWriter " + xmlWriter);

		urlParameters
		.add(new BasicNameValuePair("inquiry_xml",xmlWriter.toString()));
		
		post.setEntity(new UrlEncodedFormEntity(urlParameters, Consts.UTF_8.name()));

		HttpResponse response = client.execute(post);
		int statusCode = response.getStatusLine().getStatusCode();

		LOG.info("Response Code : " + response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		LOG.info("Result " + result);
		return statusCode;
	}

	

	public static Map<String, String> getATTRIBUTES() {
		return ATTRIBUTES;
	}
	

	public static List<NameId> getAttributesAsNameId(){
		List<NameId> listAttributes=new ArrayList<NameId>();
		for(Entry entry:ATTRIBUTES.entrySet()){
			NameId nameId=new NameId((String)entry.getKey(),(String)entry.getValue());
			listAttributes.add(nameId);
		}
		return listAttributes;
	}


	public static boolean isProductPersistRequired(Property prop,
			Map<String, List<String>> filter) {
		if(filter==null||filter.isEmpty()) return true;
		
			for (Object obj : prop
					.getPropertyCalendarOrPropertyAddressesOrPropertyAmenities()) {
			if (obj instanceof PropertyAddresses) {
				PropertyAddresses propertyAddr = (PropertyAddresses) obj;
				String countryCode = PartnerService.getCountryCode(propertyAddr.country.trim());
				if(filter.containsKey("country"))
					if(!filter.get("country").contains(countryCode.toLowerCase())) {
						
						LOG.info("##Product "+getPropertyID(prop)+" skipped for the country "+propertyAddr.country+ "/" + countryCode
								+"  >> "+getProprtyName(prop) );
						return false;
					}
			}
		}
		return true;
	}

	public static boolean isProductExportRequired(
			net.cbtltd.shared.Location loc, Map<String,List< String>> filter) {
		if (filter == null || filter.isEmpty())
			return true;
		if (filter.containsKey("country"))
			if (!filter.get("country").contains(loc.getCountry().toLowerCase())) {
				return false;
			}
		return true;
	}
	
	public static String getPropertyID(Property prop){
		for (Object obj : prop
				.getPropertyCalendarOrPropertyAddressesOrPropertyAmenities()) {
			if (obj instanceof PropertyAttributes) {
				PropertyAttributes propertyAttributes = (PropertyAttributes) obj;
				 return propertyAttributes.getPropertyId().toString() ;
			}
		}
		return "";
	}
	private static String getProprtyName(Property prop){
		for (Object jobj : prop.getPropertyDetails()
				.getCurrencyOrNameOrBathroomCount()) {
			if (jobj instanceof JAXBElement) {
				if ("name".equalsIgnoreCase(((JAXBElement) jobj).getName().toString())) {
					return (String) ((JAXBElement) jobj).getValue();
					
				}
			}
		}
		return "";
	}
	
	public static String getKeyFromValue(String key){
		for(Map.Entry<String, String> entry : ATTRIBUTES.entrySet()){
			if(entry.getValue().equalsIgnoreCase(key)){
				return entry.getKey();
			}
		}
		return null;
	}
}
