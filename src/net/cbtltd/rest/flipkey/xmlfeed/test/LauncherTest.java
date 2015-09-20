package net.cbtltd.rest.flipkey.xmlfeed.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.cbtltd.rest.Property;
import net.cbtltd.rest.common.utils.report.Cell;
import net.cbtltd.rest.common.utils.report.ComparableField;
import net.cbtltd.rest.common.utils.report.ReportUtil;
import net.cbtltd.rest.common.utils.report.Row;
import net.cbtltd.rest.common.utils.report.Table;
import net.cbtltd.rest.common.utils.report.Worksheet;
import net.cbtltd.rest.flipkey.xmlfeed.A_Handler;
import net.cbtltd.rest.flipkey.xmlfeed.FlipKeyUtils;
import net.cbtltd.rest.flipkey.xmlfeed.Launcher;
import net.cbtltd.rest.flipkey.xmlfeed.PropertyData;
import net.cbtltd.rest.response.ReservationResponse;
import net.cbtltd.server.PartnerService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.price.PriceCreate;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Senthilnathan
 * 
 */
public class LauncherTest {

	Partner partner;
	SqlSession sqlSession;
	static String altpartyid;
	static Launcher launcher;
	Reservation reservation;
	static String localRESTURL;
	private static final Logger LOG = Logger.getLogger(LauncherTest.class
			.getName());
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	// private static final String REPORT_FILE="d:/java/junit1.xml";
	private static final String SANITY_REPORT_FILE = "C:\\Users\\manikandan15785\\PMS\\test\\filekey_sanity_testreport.xml";

	private static final boolean enableReporting = true;
	static List<String> listFileNames = new ArrayList<String>();
	

	@BeforeClass
	public static void setUp() throws Exception {
		// TODO:Need to know about the partnerid/altpar tyid for Flipkey.Check
		// with Chirayu.
		altpartyid = "325490";
		System.out.println("altpartyid " + altpartyid);

		listFileNames.add("property_data_11.xml.gz");

		 launcher = new Launcher(altpartyid, false, listFileNames, 1, 10);
		localRESTURL = "http://localhost:8090/Razor/xml/rest/product/#productID#/detail?pos=5e7e3a77b3714ea2&test=true";
	}

	//@Test
	public void testReadProducts() throws Exception {
		System.out.println("Inside testReadProducts");
		launcher.readProducts();
		//launcher.inquireReservation();
	//	this.validateProductPersitence();

		// this.validateXMLwithRestUrl();

	}

	public void validateXMLwithRestUrl() {

		A_Handler handler = new A_Handler(partner, false, listFileNames, 1,
				1000);
		
		try {
			sqlSession = RazorServer.openSession();
			List<Worksheet> objects = new ArrayList<Worksheet>();
			for (String fileNameStr : listFileNames) {
				List<Row> rows = new ArrayList<Row>();
				Row fieldNameRow = null;
				Property restProperty = null;
				
				File file = new File(FlipKeyUtils.FLIPKEY_XML_LOCAL_FILE_PATH
						+ fileNameStr + FlipKeyUtils.XML_EXTENSION);
				JAXBContext jaxbContext = JAXBContext
						.newInstance(PropertyData.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext
						.createUnmarshaller();
				PropertyData propertyData = (PropertyData) jaxbUnmarshaller
						.unmarshal(file);
				for (int i = 500; i < 600; i++) {
					net.cbtltd.rest.flipkey.xmlfeed.Property xmlproperty = propertyData
							.getProperty().get(i);
					List<ComparableField> fields = new ArrayList<ComparableField>();
					try {
						Product xmlProduct = new Product();
						Integer minStay = 0;
						ArrayList<String> attributes = new ArrayList<String>();
						List<Date> listBookedDate = new ArrayList<Date>();
						Map<String, String> mapProductDesc = new HashMap<String, String>();
						Map<String, String> mapImages = new HashMap<String, String>();
						List<PriceCreate> listPrice = new ArrayList<PriceCreate>();
						handler.buildProductDetails(xmlproperty, xmlProduct,
								attributes, minStay);
//						handler.buildProductAttributes(xmlproperty, xmlProduct,
//								attributes, sqlSession, mapProductDesc,
//								listBookedDate, listPrice, mapImages);
						
						
						System.out.println("comparing property "
								+ xmlProduct.getAltid() + " : "
								+ xmlProduct.getName());
						xmlProduct
								.setName((xmlProduct.getName().length() > 100) ? xmlProduct
										.getName().substring(0, 99)
										: xmlProduct.getName());

						Product dbproduct = PartnerService
								.getProductWithoutInsert(sqlSession,
										new NameId(xmlProduct.getName(),
												xmlProduct.getAltid()));
						if(dbproduct==null){
							System.out.println("Property is not perisisted in DB");
							continue;
						}
						restProperty = determineRestProperty(dbproduct.getId());
						
						fields.add(new ComparableField("Property id", dbproduct.getId(),restProperty.getId()));
						
						fields.add(new ComparableField("Property Alt id", xmlProduct
								.getAltid(), dbproduct
								.getAltid()));
						
						fields.add(new ComparableField("Name", xmlProduct
								.getName(), restProperty.getName()));
						fields.add(new ComparableField("Webaddress", xmlProduct
								.getWebaddress(), dbproduct.getWebaddress()));
						fields.add(new ComparableField("Currency", xmlProduct
								.getCurrency(), restProperty.getCurrency()));
						fields.add(new ComparableField("Room", xmlProduct
								.getRoom(), restProperty.getRoom()));
						fields.add(new ComparableField("Bathroom", xmlProduct
								.getBathroom(), restProperty.getBathroom()));
						fields.add(new ComparableField("Toilet", xmlProduct
								.getToilet(), restProperty.getToilet()));
						fields.add(new ComparableField("Person", xmlProduct
								.getPerson(), restProperty.getPerson()));
						fields.add(new ComparableField("Child", xmlProduct
								.getChild(), restProperty.getChild()));
						fields.add(new ComparableField("Latitude", xmlProduct
								.getLatitude(), restProperty.getLatitude()));
						fields.add(new ComparableField("Longitude", xmlProduct
								.getLongitude(), restProperty.getLongitude()));
						fields.add(new ComparableField("Physicaladdress",
								xmlProduct.getPhysicaladdress(), restProperty
										.getPhysicaladdress()));
						//here we need to check whether location is null or not so adding restproperty to both the fields
						if(restProperty.getLocationid()==null)
							fields.add(new ComparableField("Location","VALUED","EMPTY"));
						else
							fields.add(new ComparableField("Location",restProperty.getLocationid(), restProperty.getLocationid()));
						
						if(restProperty.getAttributes()!=null && restProperty.getAttributes().attribute!=null && xmlProduct.getAttributemap()!=null){
							
							fields.add(new ComparableField("Attributes Count",
									xmlProduct.getAttributemap().size(), restProperty.getAttributes().attribute.size()));
						}
						

						
						
						if(listPrice!=null && restProperty
								.getPricetable()!=null && restProperty
										.getPricetable().price!=null){
							fields.add(new ComparableField("Price Count",
									listPrice.size(), restProperty
											.getPricetable().price.size()));
//							if (comparePrice(listPrice, restProperty)) {
//								fields.add(new ComparableField("Price values",
//										"All matches", "All matches"));
//							}else
//							{
//								fields.add(new ComparableField("Price values",
//										"some field mismatches", ""));
//							}
						}
						if(listBookedDate!=null && restProperty
								.getReservations()!=null && restProperty
								.getReservations().Reservation!=null){
							System.out.println("@@@@@@Booked Date Size@@@@@@"+ restProperty
										.getReservations().Reservation.size());
						fields.add(new ComparableField("Booked Dates Count",
								listBookedDate.size(), restProperty
										.getReservations().Reservation.size()));
						if (checkBookedDateInReservationList(restProperty,
								listBookedDate)) {
							fields.add(new ComparableField("Booked Date values",
									"All matches", "All matches"));
						}else
						{
							fields.add(new ComparableField("Booked Date values",
									"some field mismatches", ""));
						}
						}
						if(restProperty.getImages()!=null && restProperty.getImages().image!=null){
							fields.add(new ComparableField("Images Count",
									restProperty.getImages().image.size(),restProperty.getImages().image.size()));
						}
						

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
							for (ComparableField field : fields) {
								cellList1.add(new Cell(field
										.getExpectedFieldValue()));
								cellList2.add(new Cell(field
										.getActualFieldValue()));
								cellList3.add(new Cell(field.getResultString(),
										field.getResultString()));
							}
							rows.add(new Row(cellList1));
							rows.add(new Row(cellList2));
							rows.add(new Row(cellList3));
						}
						/*Worksheet object = new Worksheet(fileNameStr);
						Table table = new Table(rows);
						table.setColumnCount("30");
						object.setTable(table);
						objects.add(object);*/
					

					} catch (Exception ex) {
						ex.printStackTrace();
						
					}

				}
				Worksheet object = new Worksheet(fileNameStr);
				Table table = new Table(rows);
				table.setColumnCount("30");
				object.setTable(table);
				objects.add(object);
				
			}
			ReportUtil.generateFile(objects, SANITY_REPORT_FILE);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Check whether the server is down");
		} finally {
			if (sqlSession != null)
				sqlSession.close();
		}

	}

	public void testXMLwithRestUrl() {

		A_Handler handler = new A_Handler(partner, false,
				Arrays.asList(new String[] { "property_data_47.xml.gz" }), 100,
				10);
		List<Row> rows = new ArrayList<Row>();
		Row fieldNameRow = null;
		Property restProperty = null;
		List<Worksheet> objects = new ArrayList<Worksheet>();
		try {
			sqlSession = RazorServer.openSession();
			File file = new File(FlipKeyUtils.FLIPKEY_XML_LOCAL_FILE_PATH
					+ "property_data_47.xml.gz" + FlipKeyUtils.XML_EXTENSION);
			JAXBContext jaxbContext = JAXBContext
					.newInstance(PropertyData.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			PropertyData propertyData = (PropertyData) jaxbUnmarshaller
					.unmarshal(file);
			// for (final net.cbtltd.rest.flipkey.xmlfeed.Property property :
			// propertyData.getProperty()) {
			for (int i = 0; i < 2; i++) {
				net.cbtltd.rest.flipkey.xmlfeed.Property xmlproperty = propertyData
						.getProperty().get(i);
				List<ComparableField> fields = new ArrayList<ComparableField>();
				try {
					Product xmlProduct = new Product();
					Integer minStay = 0;
					ArrayList<String> attributes = new ArrayList<String>();
					List<Date> listBookedDate = new ArrayList<Date>();
					Map<String, String> mapProductDesc = new HashMap<String, String>();
					Map<String, String> mapImages = new HashMap<String, String>();
					List<PriceCreate> listPrice = new ArrayList<PriceCreate>();
					//Build the Property from xml for the purpose of comparsion
					handler.buildProductDetails(xmlproperty, xmlProduct,
							attributes, minStay);
//					handler.buildProductAttributes(xmlproperty, xmlProduct,
//							attributes, null, mapProductDesc, listBookedDate,
//							listPrice, mapImages);
					
					System.out.println("comparing property "
							+ xmlProduct.getId() + " : "
							+ xmlProduct.getAltid() + " : "
							+ xmlProduct.getName());
					xmlProduct
							.setName((xmlProduct.getName().length() > 100) ? xmlProduct
									.getName().substring(0, 99) : xmlProduct
									.getName());
					//Build the Property from DB for the purpose of comparison
					Product dbproduct = PartnerService.getProductWithoutInsert(
							sqlSession, new NameId(xmlProduct.getName(),
									xmlProduct.getAltid()));
					restProperty = determineRestProperty(dbproduct.getId());
					fields.add(new ComparableField("Propertyid", dbproduct
							.getId(), dbproduct.getId()));
					fields.add(new ComparableField("Supplierid", xmlProduct
							.getSupplierid(), restProperty.getSupplierid()));

					fields.add(new ComparableField("Altpartyid", xmlProduct
							.getAltpartyid(), restProperty.getAltpartyid()));
					fields.add(new ComparableField("AltSupplierId", xmlProduct
							.getAltSupplierId(), restProperty
							.getAltSupplierId()));
					fields.add(new ComparableField("Name",
							xmlProduct.getName(), restProperty.getName()));
					fields.add(new ComparableField("State", xmlProduct
							.getState(), restProperty.getState()));
					fields.add(new ComparableField("Type",
							xmlProduct.getType(), restProperty.getType()));
					fields.add(new ComparableField("Webaddress", xmlProduct
							.getWebaddress(), restProperty.getWebaddress()));
					fields.add(new ComparableField("Currency", xmlProduct
							.getCurrency(), restProperty.getCurrency()));
					fields.add(new ComparableField("Unit",
							xmlProduct.getUnit(), restProperty.getUnit()));
					fields.add(new ComparableField("Room",
							xmlProduct.getRoom(), restProperty.getRoom()));
					fields.add(new ComparableField("Bathroom", xmlProduct
							.getBathroom(), restProperty.getBathroom()));
					fields.add(new ComparableField("Toilet", xmlProduct
							.getToilet(), restProperty.getToilet()));
					fields.add(new ComparableField("Quantity", xmlProduct
							.getQuantity(), restProperty.getQuantity()));
					fields.add(new ComparableField("Person", xmlProduct
							.getPerson(), restProperty.getPerson()));
					fields.add(new ComparableField("Child", xmlProduct
							.getChild(), restProperty.getChild()));
					fields.add(new ComparableField("Commission", xmlProduct
							.getCommissionValue(), restProperty.getCommissionValue()));
					fields.add(new ComparableField("Latitude", xmlProduct
							.getLatitude(), restProperty.getLatitude()));
					fields.add(new ComparableField("Longitude", xmlProduct
							.getLongitude(), restProperty.getLongitude()));
					fields.add(new ComparableField("Physicaladdress",
							xmlProduct.getPhysicaladdress(), restProperty
									.getPhysicaladdress()));

					List<Cell> headingList = new ArrayList<Cell>();
					for (ComparableField field : fields) {
						// Assert.assertTrue(field.getCompareString(),
						// field.getResult());
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
					Worksheet object = new Worksheet("property_data_47.xml.gz");
					Table table = new Table(rows);
					table.setColumnCount("30");
					object.setTable(table);
					objects.add(object);
					if (comparePrice(listPrice, restProperty)) {
						System.out.println("Price list are all verified");
					}

					if (checkBookedDateInReservationList(restProperty,
							listBookedDate)) {
						System.out.println("BookedDates are all verified");
					}

				} catch (NullPointerException ex) {
					System.out.println("Property is not perisisted in DB");
				}

			}
			// ReportUtil.generateFile( objects, REPORT_FILE );
		} catch (NullPointerException ex) {
			System.out.println("Property is not perisisted in DB");
		} catch (Exception ex) {
			System.out.println("Check whether the server is down");
		} finally {
			if (sqlSession != null)
				sqlSession.close();
		}

	}

	private boolean comparePrice(List<PriceCreate> listPrice,
			Property restProperty) {
		for (PriceCreate xmlPrice : listPrice) {

			boolean alike = false;

			for (Price restPrice : restProperty.getPricetable().price) {
				if (xmlPrice.getDateStr().equalsIgnoreCase(
						restPrice.getDateStr())
						&& xmlPrice
								.getTodate()
								.toString()
								.equalsIgnoreCase(
										restPrice.getTodate().toString())
						&& xmlPrice.getValue() == restPrice.getValue()
						&& xmlPrice.getName().equalsIgnoreCase(
								restPrice.getName())
						&& xmlPrice.getState().equalsIgnoreCase(
								restPrice.getState())
						&& xmlPrice.getAvailable() == restPrice.getAvailable()
						&& xmlPrice.getFactor() == restPrice.getFactor()
						&& xmlPrice.getMinStay() == restPrice.getMinStay()
						&& xmlPrice.getMinimum() == restPrice.getMinimum()
						&& xmlPrice.getRule().equalsIgnoreCase(
								restPrice.getRule())
						&& xmlPrice.getType().equalsIgnoreCase(
								restPrice.getType())
						&& xmlPrice.getUnit().equalsIgnoreCase(
								restPrice.getUnit()))
					alike = true;

			}
			if (!alike) {
				throw new AssertionError(
						"Some of the price values not updated for "
								+ xmlPrice.getEntityid());
			}

		}
		return true;
	}

	private boolean checkBookedDateInReservationList(Property restProperty,
			List<Date> listBookedDate) throws ClientProtocolException,
			IOException {
		List<String> bookedDates = new ArrayList();
		for (Date bookedDt : listBookedDate)
			bookedDates
					.add(new SimpleDateFormat("yyyy-MM-dd").format(bookedDt));
		return checkBookedDateInReservation(restProperty, bookedDates);
	}

	private boolean checkBookedDateInReservation(Property restProperty,
			List<String> bookedDates) throws ClientProtocolException,
			IOException {
		for (net.cbtltd.rest.flipkey.Reservation reservation : restProperty
				.getReservations().Reservation) {
			if (!bookedDates.contains(reservation.ArrivalDate)) {
				throw new AssertionError("Booked Date "
						+ reservation.ArrivalDate
						+ " does not comes in reservation");
			}
		}
		return true;
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
	        BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			//StringBuffer result = new StringBuffer();
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
	        if(null != connection) { connection.disconnect(); }
	    }
		
		
		return property;

	}

	private void validateProductPersitence() throws Exception {
		SqlSession sqlSession = RazorServer.openSession();
		StringBuilder reportBuilder = new StringBuilder();
		List<Product> listProduct = sqlSession.getMapper(ProductMapper.class)
				.altPartyread(new NameId("", "100"));
		LOG.info("Total No of product count is " + listProduct.size());
		reportBuilder.append("Total No of product count is "
				+ listProduct.size());
		reportBuilder.append("\n");
		int cnt = 0;
		for (Product product : listProduct) {
			LOG.info("@@@@Testing Product@@@@" + product.getId());
			reportBuilder.append("@@@@Testing Product@@@@" + product.getId());
			reportBuilder.append("\n");
			DefaultHttpClient client = new DefaultHttpClient();
			localRESTURL = localRESTURL.replace("#productID#", product.getId());
			LOG.info(" localRESTURL " + localRESTURL);
			HttpGet getRequest = new HttpGet(localRESTURL);
			HttpResponse response = client.execute(getRequest);
			int statusCode = response.getStatusLine().getStatusCode();

			LOG.info("Response Code : "
					+ response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			StringWriter xmlWriter = new StringWriter();

			String line = "";
			while ((line = rd.readLine()) != null) {
				xmlWriter.write(line);
			}
			StringReader xmlreader = new StringReader(xmlWriter.toString());
			JAXBContext jaxbContext = JAXBContext.newInstance(Property.class);
			Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();
			Property property = (Property) jaxbUnMarshaller
					.unmarshal(xmlreader);
			LOG.info("property.getId() " + property.getId());

			if (!(property.getBathroom() != null && property.getBathroom() > 0)) {
				throw new AssertionError("Bathroom Count is null or zero");
			}
			if (!(property.getBedroom() != null && property.getBedroom() > 0)) {
				throw new AssertionError("Bedroom Count is null or zero");
			}
			if (!(property.getPerson() != null && property.getPerson() > 0)) {
				throw new AssertionError("Person is null or zero");
			}
			if (!(property.getAttributes() != null
					&& property.getAttributes().attribute != null && property
					.getAttributes().attribute.size() > 0)) {
				throw new AssertionError("Size of Attribute is zero");
			}
			List<String> listImageNames = (List<String>) property.getImages().image;
			if (!(listImageNames != null && listImageNames.size() > 0)) {
				throw new AssertionError("No images Found");
			}
			int priceSize = property.getPricetable().price.size();
			if (!(priceSize > 0)) {
				throw new AssertionError("No Price data Found");
			}

			LOG.info(" Product with product id " + product.getId()
					+ " passed all test cases ");
			cnt++;
			if (cnt == 100) {
				break;
			}

		}

	}

	 @Test
	public void testInquireReservation() throws Exception {
		System.out.println("Inside testInquireReservation");
	//http://localhost:8090/Razor/xml/rest/reservation/inquiry?pos=5e7e3a77b3714ea2&productid=3&fromdate=2014-07-17&todate=2014-07-21&notes=test&adult=2&child=2&emailaddress=sen@mybookingpal1.com&familyname=family&firstname=sen&telnumber=1234567&cc_address=test address&cc_country=IN&cc_city=Chennai&cc_zip=600044&cc_state=TN&cc_bdd=31&cc_bdm=10&cc_bdy=1983

		HttpURLConnection connection = null;
		String localURL = "http://localhost:8090/Razor/xml/rest/reservation/inquiry?pos=5e7e3a77b3714ea2&productid=3&fromdate=2014-08-17&todate=2014-08-21&notes=test&adult=2&child=2&emailaddress=sen@mybookingpal1.com&familyname=family&firstname=sen&telnumber=1234567&cc_address=test%20address&cc_country=IN&cc_city=Chennai&cc_zip=600044&cc_state=TN&cc_bdd=31&cc_bdm=10&cc_bdy=1983";
		LOG.info(" Reservation Inquiry URL " + localURL);
	    try {
	        URL url = new URL(localURL);
	        connection = (HttpURLConnection) url.openConnection();
	        connection.connect();
	        BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			//StringBuffer result = new StringBuffer();
			StringWriter xmlWriter = new StringWriter();

			String line = "";
			while ((line = rd.readLine()) != null) {
				xmlWriter.write(line);
			}
	              
			StringReader xmlreader = new StringReader(xmlWriter.toString());
			JAXBContext jaxbContext = JAXBContext.newInstance(ReservationResponse.class);
			Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();
			ReservationResponse reservationResponse = (ReservationResponse) jaxbUnMarshaller.unmarshal(xmlreader);
		
			if(reservationResponse!=null && reservationResponse.getReservationInfo()!=null){ // &&  "Inquiry".equalsIgnoreCase(reservationResponse.getReservationInfo().getState())){
				LOG.info(" Reservation is persisted inside DB "+ reservationResponse.getReservationInfo().getId());
			}else{
				LOG.info("Error Message "+reservationResponse.getErrorMessage());
			}
				
			
	    } catch (MalformedURLException e1) {
	        e1.printStackTrace();
	    } catch (IOException e1) {
	        e1.printStackTrace();
	    } finally {
	        if(null != connection) { connection.disconnect(); }
	    }
		
		
		

	}

		
	

	/**
	 * Destroy the server
	 */
	@AfterClass
	public static void tearDown() throws IOException {
		System.out.println("@@@ tearDown method called @@@@@");

	}

}
