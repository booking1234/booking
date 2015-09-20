/**
 * 
 */
package net.cbtltd.rest.tripvillas.xmlfeed.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.cbtltd.rest.Attribute;
import net.cbtltd.rest.Property;
import net.cbtltd.rest.common.utils.report.Cell;
import net.cbtltd.rest.common.utils.report.ComparableField;
import net.cbtltd.rest.common.utils.report.ReportUtil;
import net.cbtltd.rest.common.utils.report.Row;
import net.cbtltd.rest.common.utils.report.Table;
import net.cbtltd.rest.common.utils.report.Worksheet;
import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.rest.tripvillas.xmlfeed.A_Handler;
import net.cbtltd.rest.tripvillas.xmlfeed.Launcher;
import net.cbtltd.rest.tripvillas.xmlfeed.TripvillasUtils;
import net.cbtltd.rest.tripvillas.xmlfeed.accommodations.Accommodations;
import net.cbtltd.rest.tripvillas.xmlfeed.accommodations.Accommodations.Accommodation;
import net.cbtltd.rest.tripvillas.xmlfeed.accommodations.Accommodations.Accommodation.Rooms.Room;
import net.cbtltd.rest.tripvillas.xmlfeed.prices.Prices;
import net.cbtltd.rest.tripvillas.xmlfeed.vacancies.Vacancies;
import net.cbtltd.server.PartnerService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.finance.gateway.CreditCardType;

import org.apache.http.client.ClientProtocolException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gwt.thirdparty.guava.common.collect.LinkedListMultimap;
import com.google.gwt.thirdparty.guava.common.collect.Multimap;

/**
 * @author Suresh Kumar S
 * 
 */
public class LauncherTest {
	static String altpartyid;
	static Launcher launcher;
	SqlSession sqlSession;
	Partner partner;

	static String localRESTURL;

	//private static final String SANITY_REPORT_FILE = "C:\\Users\\Suresh Kumar S\\PMS\\tripvillas\\test\\tripvillas_sanity_testreport.xml";
	private static final String SANITY_REPORT_FILE = "C:\\Users\\Suresh Kumar S\\PMS\\tripvillas\\test\\tripvillas_sanity_uat_testreport.xml";

	private static final boolean enableReporting = true;

	private static final Logger LOG = Logger.getLogger(LauncherTest.class
			.getName());

	@BeforeClass
	public static void setUp() throws Exception {
		altpartyid = "325746";
		//altpartyid = "200";
		LOG.info("altpartyid " + altpartyid);
		LOG.info(Model.encrypt(altpartyid));
		launcher = new Launcher(altpartyid, false);
		localRESTURL = "http://localhost:8080/Razor/xml/rest/product/#productID#/fulldetail?pos=910c29e329fff399&test=true";
		localRESTURL = "https://uat.mybookingpal.com/xml/rest/product/#productID#/fulldetail?pos="+Model.encrypt(altpartyid)+"&test=true";
	}
	
	@Test
	public void testPayPalMakePayment() throws Exception {
		Reservation reservation = new Reservation();
		reservation.setId("1");
		reservation.setAddrress("52 N Main ST");
		reservation.setCity("Johnstown");
		//reservation.setCountry("US");
		reservation.setCountry("India");
		reservation.setZip("43210");
		reservation.setState("OH");
		reservation.setTaxrate(0.03);
		reservation.setCost(7.47);
		reservation.setCurrency("USD");

		reservation.setAdult(1);
		reservation.setChild(1);
		reservation.setCheckin("2014-12-26T14:00:00");
		reservation.setCheckout("2015-01-06T14:00:00");
		reservation.setEmailaddress("xyz@tripvillas.com");
		reservation.setFirstname("xyz");
		reservation.setFamilyname("xyz");
		reservation.setPrice(2000.0);
		reservation.setPhoneNumber("9999999999");
		reservation.setMessage("Hey, I want to book your Property");
		reservation.setAltid("10001459");
		reservation.setProductid("381423");

		net.cbtltd.shared.finance.gateway.CreditCard creditCard = new net.cbtltd.shared.finance.gateway.CreditCard();
		creditCard.setNumber("4417119669820331");
		creditCard.setType(CreditCardType.VISA);
		creditCard.setMonth("11");
		creditCard.setYear("2018");
		creditCard.setSecurityCode("874");
		creditCard.setFirstName("Joe");
		creditCard.setLastName("Shopper");
		
		Map<String, String> result = launcher.createReservationAndPayment(RazorServer.openSession(), reservation, creditCard);
		LOG.info(result);
	}

	//@Test
	public void testReadProducts() throws Exception {
		LOG.info("Inside testReadProducts");
		launcher.readProducts();
	}
	
	//@Test
	public void testReadPrices() throws Exception {
		LOG.info("Inside testReadPrices");
		launcher.readPrices();
	}
	
	//@Test
	public void testReadSchedules() throws Exception {
		LOG.info("Inside testReadSchedules");
		launcher.readSchedules();
	}
	
	//@Test
	public void testIsAvailable() throws Exception {
		LOG.info("Inside testIsAvailable");
		boolean isAvail = launcher.isAvailable(RazorServer.openSession(), getReservationDetails());
		LOG.info(isAvail);
	}
	
	//@Test
	public void testInquireReservation() throws Exception {
		LOG.info("Inside testInquireReservation");
		launcher.inquireReservation(RazorServer.openSession(), getReservationDetails());
	}
	
	//@Test
	public void testReadPrice() throws Exception {
		LOG.info("Inside testReadPrice");
		ReservationPrice reservationPrice = launcher.readPrice(RazorServer.openSession(),getReservationDetails(), getReservationDetails().getAltid(), null);
		LOG.info("Total Amount : " + reservationPrice.getTotal() + " - " + "Currency : " + reservationPrice.getCurrency());
	}
	
	//@Test
	public void testCreateReservation() throws Exception {
		LOG.info("Inside testCreateReservation");
		launcher.createReservation(RazorServer.openSession(),getReservationDetails());
	}
	
	//@Test
	public void testXmlWithRest() throws Exception {
		LOG.info("Inside testXmlWithRest");
		validateXMLwithRestUrl();
	}

	//@Test
	public void testReloadNullLocationProducts() throws Exception {
		LOG.info("Inside testReloadNullLocationProducts");
		launcher.reloadNullLocationProducts(altpartyid);
	}

	private Reservation getReservationDetails() {
		Reservation reservation = new Reservation();
		reservation.setAdult(1);
		reservation.setChild(1);
		reservation.setCheckin("2014-10-26T14:00:00");
		reservation.setCheckout("2014-11-06T14:00:00");
		reservation.setEmailaddress("xyz@tripvillas.com");
		reservation.setFirstname("xyz");
		reservation.setFamilyname("xyz");
		reservation.setPrice(2000.0);
		reservation.setPhoneNumber("9999999999");
		reservation.setMessage("Hey, I want to book your Property");
		//reservation.setAltid("10002062");
		reservation.setAltid("14d4cb5af19040a090a4f26a3936f732");
		reservation.setCountry("India");
		//reservation.setProductid("1925");
		reservation.setProductid("1982");
		return reservation;
	}
	Row fieldNameRow = null;
	public void validateXMLwithRestUrl() {
		File accommodationXmlFile = new File(
				TripvillasUtils.TRIPVILLAS_XML_LOCAL_FILE_PATH
						+ TripvillasUtils.getAccomodationXMLFileName()
						+ TripvillasUtils.TAR_EXTENSION
						+ TripvillasUtils.XML_EXTENSION);
		File priceXmlFile = new File(
				TripvillasUtils.TRIPVILLAS_XML_LOCAL_FILE_PATH
						+ TripvillasUtils.getPriceXMLFileName()
						+ TripvillasUtils.TAR_EXTENSION
						+ TripvillasUtils.XML_EXTENSION);
		File vacancyXmlFile = new File(
				TripvillasUtils.TRIPVILLAS_XML_LOCAL_FILE_PATH
						+ TripvillasUtils.getVacancyXMLFileName()
						+ TripvillasUtils.TAR_EXTENSION
						+ TripvillasUtils.XML_EXTENSION);
		A_Handler handler = launcher.getHandler(false);

		try {
			sqlSession = RazorServer.openSession();
			Accommodations accommodations = new Accommodations();
			accommodations = TripvillasUtils.unmarshallFile(accommodations,
					Accommodations.class, accommodationXmlFile);
			Vacancies vacancies = new Vacancies();
			vacancies = TripvillasUtils.unmarshallFile(vacancies,
					Vacancies.class, vacancyXmlFile);
			Prices prices = new Prices();
			prices = TripvillasUtils.unmarshallFile(prices, Prices.class,
					priceXmlFile);

			List<Row> rows = new ArrayList<Row>();
			
			Property restProperty = null;

			try {

				for (Accommodation accommodation : accommodations
						.getAccommodation()) {
					Product xmlProduct = new Product();
					Multimap<String, String> attributes = LinkedListMultimap.create();
					Map<String, String> mapImages = new HashMap<String, String>();
					
					handler.buildProductDetails(xmlProduct, accommodation);
					handler.buildProductAttributes(xmlProduct, attributes, accommodation);
					handler.buildImageData(mapImages, accommodation);
					handler.buildLocationDetails(xmlProduct, sqlSession,
							accommodation);
					Product dbproduct = null;
					
					if(accommodation.getPropertyType() != null && !accommodation.getPropertyType().isEmpty() && accommodation.getPropertyType().equalsIgnoreCase(TripvillasUtils.INDEPENDENT_STAY)){
						LOG.info("comparing property "
								+ xmlProduct.getAltid() + " : "
								+ xmlProduct.getName());
						dbproduct = PartnerService.getProductWithoutInsert(
								sqlSession, new NameId(xmlProduct.getName(), xmlProduct.getAltid()));
						if (dbproduct == null) {
							LOG.info("Property is not perisisted in DB");
							continue;
						}
						if (accommodation.getRooms() != null) {
							int personCount = 0;
							List<Room> rooms = accommodation.getRooms().getRoom();
							for (Room room : rooms) {
								if (room != null) {
									personCount += room.getMaxSleep();
								}
							}
							xmlProduct.setPerson(personCount);
						}
					restProperty = determineRestProperty(dbproduct.getId());
					buildRow(compare(dbproduct, restProperty, xmlProduct, (List<String>)attributes.get(xmlProduct.getAltid()), mapImages, handler, vacancies, String.valueOf(accommodation.getPropertyID())),
							rows);

					
					}else if(accommodation.getPropertyType() != null && !accommodation.getPropertyType().isEmpty() && accommodation.getPropertyType().equalsIgnoreCase(TripvillasUtils.OWNERS_GUEST)){
						if(accommodation.getRooms() != null && accommodation.getRooms().getRoom() != null && !accommodation.getRooms().getRoom().isEmpty()){
							
							for(Room room : accommodation.getRooms().getRoom()){
								xmlProduct.setAltid(room.getRoomID());
								xmlProduct.setPerson(room.getMaxSleep());

								LOG.info("comparing property " + xmlProduct.getAltid() + " : " + xmlProduct.getName());
								dbproduct = PartnerService.getProductWithoutInsert(
										sqlSession, new NameId(xmlProduct.getName(), xmlProduct.getAltid()));
								if (dbproduct == null) {
									LOG.info("Property is not perisisted in DB");
									continue;
								}
								restProperty = determineRestProperty(dbproduct.getId());
								buildRow(compare(dbproduct, restProperty, xmlProduct, (List<String>)attributes.get(room.getRoomID()), mapImages, handler, vacancies, room.getRoomID()),
										rows);

								
							}
						}
					}
					
					
				}
				Worksheet object = new Worksheet("Products");
				Table table = new Table(rows);
				table.setColumnCount("50");
				object.setTable(table);
				ReportUtil.generateFile(object, SANITY_REPORT_FILE);
			} catch (Exception ex) {
				ex.printStackTrace();

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.info("Check whether the server is down");
		} finally {
			if (sqlSession != null)
				sqlSession.close();
		}
	}
	private void buildRow(List<ComparableField> fields,List<Row> rows){
		List<Cell> headingList = new ArrayList<Cell>();
		for (ComparableField field : fields) {

			if (enableReporting && fieldNameRow == null)
				headingList.add(new Cell("heading", field
						.getFieldName()));
		}
		if (enableReporting && fieldNameRow == null) {
			fieldNameRow = new Row(headingList);
			rows.add(fieldNameRow);
		}
		if (enableReporting) {
			List<Cell> cellList1 = new ArrayList<Cell>(), cellList2 = new ArrayList<Cell>(), cellList3 = new ArrayList<Cell>();
			if(fields.isEmpty()) return;
			for (ComparableField field : fields) {
				cellList1.add(new Cell(field
						.getExpectedFieldValue()));
				cellList2
						.add(new Cell(field.getActualFieldValue()));
				cellList3.add(new Cell(field.getResultString(),
						field.getResultString()));
			}
			rows.add(new Row(cellList1));
			rows.add(new Row(cellList2));
			rows.add(new Row(cellList3));
		}
	}
	private List<ComparableField> compare(Product dbproduct, Property restProperty, Product xmlProduct, List<String> attributes, Map<String,String> mapImages, A_Handler handler, Vacancies vacancies, String productId){
		List<ComparableField> fields = new ArrayList<ComparableField>();
		try{
			fields.add(new ComparableField("Property id", dbproduct
					.getId(), restProperty.getId()));

			fields.add(new ComparableField("Property Alt id",
					xmlProduct.getAltid(), dbproduct.getAltid()));

			fields.add(new ComparableField("Property Type",
					xmlProduct.getOptions(), dbproduct.getOptions()));

			fields.add(new ComparableField("Name",
					xmlProduct.getName(), restProperty.getName()));
			fields.add(new ComparableField("Webaddress", xmlProduct
					.getWebaddress(), dbproduct.getWebaddress()));
			fields.add(new ComparableField("Bathroom", xmlProduct
					.getBathroom(), restProperty.getBathroom()));
			fields.add(new ComparableField("Person", xmlProduct
					.getPerson(), restProperty.getPerson()));
			fields.add(new ComparableField("Latitude", xmlProduct
					.getLatitude(), restProperty.getLatitude()));
			fields.add(new ComparableField("Longitude", xmlProduct
					.getLongitude(), restProperty.getLongitude()));
			fields.add(new ComparableField("Physicaladdress",
					xmlProduct.getPhysicaladdress(), new String(restProperty
					.getPhysicaladdress().getBytes(),"UTF-8")));
			// here we need to check whether location is null or not so
			// adding restproperty to both the fields
			if (restProperty.getLocationid() == null)
				fields.add(new ComparableField("Location", "VALUED",
						"EMPTY"));
			else
				fields.add(new ComparableField("Location", restProperty
						.getLocationid(), restProperty.getLocationid()));

			if (restProperty.getAttributes() != null
					&& restProperty.getAttributes().attribute != null && attributes != null) {
				int dbAttrCount = 0;
				for (Attribute attrs : restProperty.getAttributes().attribute) {
					if(attrs.values != null){
						if(attrs.values.value != null){
							dbAttrCount += attrs.values.value.size();
						}
					}
				}

				attributes.removeAll(Collections.singleton(null));
				fields.add(new ComparableField("Attributes Count",
						attributes.size(), dbAttrCount)); // added one for hard coded value of Property Class Type (PCT)
			}

			if (restProperty.getImages() != null
					&& restProperty.getImages().image != null
					&& mapImages != null ) {

				fields.add(new ComparableField("Image Count",
						mapImages.size(),
						restProperty.getImages().image.size()));
			}

			Multimap<String, Date> bookedDatesWithPropertyId = handler
					.getBookedDatesWithPropertyId(vacancies);
			int bookedCount = 0;
			if (bookedDatesWithPropertyId.containsKey(productId)) {
				bookedCount = bookedDatesWithPropertyId.get(productId)
						.size();
			}
			if (restProperty.getReservations() != null
					&& restProperty.getReservations().Reservation != null) {
				fields.add(new ComparableField("Reservation Count", bookedCount,
						restProperty.getReservations().Reservation.size()));
			}else {
				fields.add(new ComparableField("Reservation Count", bookedCount,0));
			}

			Multimap<String, Date> availableDatesWithPropertyId = handler
					.getAvailableDatesWithPropertyId(vacancies);
			int priceCount = 0;
			if (availableDatesWithPropertyId.containsKey(productId)) {
				priceCount = availableDatesWithPropertyId.get(productId)
						.size();
			}
			if (restProperty.getPricetable() != null
					&& restProperty.getPricetable().price != null) {
				fields.add(new ComparableField("Price Count", priceCount,
						restProperty.getPricetable().price.size()));
			}else {
				fields.add(new ComparableField("Price Count", priceCount,0));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return fields;
}
	
	private Property determineRestProperty(String productID)
			throws ClientProtocolException, IOException, JAXBException {

		Property property = null;
		HttpURLConnection connection = null;
		String localURL = localRESTURL.replace("#productID#", productID);
		LOG.info(" localRESTURL " + localURL);
		try {
			URL url = new URL(localURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			// StringBuffer result = new StringBuffer();
			StringWriter xmlWriter = new StringWriter();

			String line = "";
			while ((line = rd.readLine()) != null) {
				xmlWriter.write(line);
			}

			StringReader xmlreader = new StringReader(xmlWriter.toString());
			JAXBContext jaxbContext = JAXBContext.newInstance(Property.class);
			Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();
			property = (Property) jaxbUnMarshaller.unmarshal(xmlreader);

		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (null != connection) {
				connection.disconnect();
			}
		}

		return property;

	}

	/**
	 * Destroy the server
	 */
	@AfterClass
	public static void tearDown() throws IOException {
		LOG.info("********** tearDown method called **********");

	}
}
