package net.cbtltd.rest.homeaway.resco.datafeed.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
import net.cbtltd.rest.homeaway.resco.datafeed.Launcher;
import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.rest.response.ReservationResponse;
import net.cbtltd.server.RazorServer;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;

import org.apache.http.client.ClientProtocolException;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;

import com.instantsoftware.secureweblinkplusapi.ClsPropIndex;
import com.instantsoftware.secureweblinkplusapi.ClsProperty;

public class LauncherTest {
	
	private static final boolean enableReporting = true;
	
	static String localRESTURL;

	static String altpartyid;
	static Launcher launcher;
	SqlSession sqlSession;
	Partner partner;
	private static final Logger LOG = Logger.getLogger(LauncherTest.class
			.getName());
	private static final String SANITY_REPORT_FILE = "C:\\Users\\manikandan15785\\PMS\\homeaway\\sanity_testreport.xml";

	@BeforeClass
	public static void setUp() throws Exception {
		altpartyid = "325768"; 
		System.out.println("altpartyid " + altpartyid +" encrypt : "+Model.encrypt(altpartyid));
		launcher = new Launcher(altpartyid);
		localRESTURL = "http://localhost:8080/Razor/xml/rest/product/#productID#/fulldetail?pos="+Model.encrypt(altpartyid)+"&test=true";
		localRESTURL = "https://uat.mybookingpal.com/xml/rest/product/#productID#/fulldetail?pos="+Model.encrypt(altpartyid)+"&test=true";
	}
	
	//@Test
	public void readspecial(){
		launcher.getHandler().readSpecials();
	}

	@Test
	public void getReservationDetails() {
		Reservation reservation = new Reservation();
		reservation.setZip("234234");
		reservation.setAdult(1);
		reservation.setChild(1);
		reservation.setCheckin("2015-01-26T14:00:00");
		reservation.setCheckout("2015-01-29T14:00:00");
		reservation.setEmailaddress("xyz@tripvillas.com");
		reservation.setFirstname("xyz");
		reservation.setFamilyname("xyz");
		reservation.setPhoneNumber("+41 22 7321234");
		reservation.setMessage("Hey, I want to book your Property");
		//reservation.setAltid("ATL 65");
		reservation.setAddrress("address");
		reservation.setCity("LV");
		reservation.setState("US");
		reservation.setCountry("US");
		reservation.setCardnumber("4111111111111111");
		reservation.setCardcode("3234");
		reservation.setCost(235.0);
		reservation.setCardholder("mani");
		reservation.setCardmonth("01");
		reservation.setCardyear("2018");
		reservation.setCardType("VISA");
		reservation.setProductname("Atlantica III #65");
		reservation.setProductid("382960");
		System.out.println(new DateTime());
		System.out.println(new DateTime().getMillis());
		//return reservation;
		launcher.createReservation(reservation);
		//Assert.assertNotNull(reservation.getReservationid());
		System.out.println("Booking Id : "+reservation.getReservationid());
		System.out.println(new DateTime());
		System.out.println(new DateTime().getMillis());
	}
	
	@Test
	public void getQuotes() {
		Reservation reservation = new Reservation();
		reservation.setZip("92604");
		reservation.setAdult(2);
		reservation.setChild(2);
		reservation.setCheckin("2015-06-05T14:00:00");
		reservation.setCheckout("2015-06-12T14:00:00");
		reservation.setEmailaddress("xyz@mybookingpal.com");
		reservation.setFirstname("xyz");
		reservation.setFamilyname("xyz");
		reservation.setPhoneNumber("+41 22 7321234");
		reservation.setMessage("Hey, I want to book your Property");
		//reservation.setAltid("ARB 521");
		reservation.setAddrress("101 Pacifica");
		reservation.setCity("Irvine");
		reservation.setState("CA");
		reservation.setCountry("US");
		reservation.setCardnumber("4111111111111111");
		reservation.setCardcode("3234");
		reservation.setCost(235.0);
		reservation.setCardholder("mani");
		reservation.setCardmonth("01");
		reservation.setCardyear("2018");
		reservation.setCardType("VISA");
		reservation.setProductname("Atlantica III #65");
		reservation.setProductid("382960");
		System.out.println(new DateTime());
		System.out.println(new DateTime().getMillis());
		
		ReservationPrice reservationPrice = launcher.readPrice(reservation, "ARB 521", "USD");
		//Assert.assertNotNull(reservation.getReservationid());
		System.out.println("Booking Id : "+reservation.getReservationid());
		System.out.println("Total Quote: " + reservation.getQuote());
		System.out.println("Total Quote Cost: " + reservation.getCost());
		System.out.println(new DateTime().getMillis());
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
	
	@Test
	public void checkAvailability(){
		Reservation reservation = new Reservation();
		reservation.setZip("234234");
		reservation.setAdult(1);
		reservation.setChild(1);
//		reservation.setCheckin("2014-10-21T14:00:00");
//		reservation.setCheckout("2014-10-22T14:00:00");
		Calendar st=Calendar.getInstance(); st.set(2015, 11, 22);
		reservation.setDate( st.getTime());
		st.set(2015, 11, 25);
		reservation.setTodate(st.getTime());
		reservation.setEmailaddress("xyz@tripvillas.com");
		reservation.setFirstname("xyz");
		reservation.setFamilyname("xyz");
		reservation.setPhoneNumber("+41 22 7321234");
		reservation.setMessage("Hey, I want to book your Property");
		//reservation.setAltid("ATL 856");
		reservation.setAddrress("address");
		reservation.setCity("LV");
		reservation.setState("US");
		reservation.setCountry("US");
		reservation.setCardnumber("4111111111111111");
		reservation.setCardcode("3234");
		reservation.setCost(235.0);
		reservation.setCardholder("mani");
		reservation.setCardmonth("01");
		reservation.setCardyear("2018");
		reservation.setCardType("VISA");
		reservation.setProductname("Atlantica III #856");
		reservation.setProductid("382962");
		System.out.println(launcher.getHandler().isAvailable(RazorServer.openSession(), reservation));
		
	}
	
	//@Test
	public void test_LoadData() {
//		Launcher launcher=new Launcher("90640");
//		launcher.loadPropertyData(true);
		//launcher.inquireReservation();
		
//		String cardno="4111111111111111";
		try {
			launcher.loadPropertyData(true);
			test_LoadedData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		// 1407919255318 : d428da2188d0feae3c41480b7c42272ff9060e3c0e96d5f7e3f7dfafe911f134
		// 1407920749294			   8391da809dd9ca6b46bd1d406cd1e6298334ec421ec1292451c9e2acc2fd10e4	
		
	}
	public void test_LoadedData() throws FileNotFoundException, JAXBException{
		SqlSession sqlSession = RazorServer.openSession();

		List<Row> rows = new ArrayList<Row>();
		launcher.getHandler().setPersistFlag(false);
		List<ClsPropIndex> indexes = launcher.getProxy().getPropertyIndexes(launcher.getHandler().getUserName(),
				launcher.getHandler().getPassword(),launcher.getHandler(). getCoid(), null).getClsPropIndex();
		for (ClsPropIndex index : indexes) {
		List<ClsProperty> property = launcher.getProxy().getPropertyDesc(launcher.getHandler().getUserName(),
				launcher.getHandler().getPassword(), launcher.getHandler().getCoid(), index.getStrId(), null, null, true).getClsProperty();
		ClsProperty prop=property.get(0);
		
		try {
			Product product=launcher.getHandler().buildAndSaveProduct(sqlSession, null, index  );
			Property restproperty = determineRestProperty(product.getId());
			List<ComparableField>  fields=compare(prop,product,restproperty);
			List<Cell> headingList = new ArrayList<Cell>();
			Row fieldNameRow = null;
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
					cellList2
							.add(new Cell(field.getActualFieldValue()));
					cellList3.add(new Cell(field.getResultString(),
							field.getResultString()));
				}
				rows.add(new Row(cellList1));
				rows.add(new Row(cellList2));
				rows.add(new Row(cellList3));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		}
		Worksheet object = new Worksheet("Products");
		Table table = new Table(rows);
		table.setColumnCount("50");
		object.setTable(table);
		ReportUtil.generateFile(object, SANITY_REPORT_FILE);
	}
	
	private List<ComparableField>  compare(ClsProperty clsProperty, Product product,
			Property restProperty) {
		List<ComparableField> fields = new ArrayList<ComparableField>();
		fields.add(new ComparableField("Property id", product
				.getId(), restProperty.getId()));

		fields.add(new ComparableField("Property Alt id",
				product.getAltid(), product.getAltid()));

		fields.add(new ComparableField("Name",
				product.getName(), restProperty.getName()));
		fields.add(new ComparableField("Webaddress", product
				.getWebaddress(), product.getWebaddress()));
		
		fields.add(new ComparableField("Bathroom", product
				.getBathroom(), restProperty.getBathroom()));
		fields.add(new ComparableField("Person", product
				.getPerson(), restProperty.getPerson()));
		fields.add(new ComparableField("Child", product
				.getChild(), restProperty.getChild()));
		fields.add(new ComparableField("Adult", product
				.getAdult(), restProperty.getAdult()));
		fields.add(new ComparableField("Latitude", product
				.getLatitude(), restProperty.getLatitude()));
		fields.add(new ComparableField("Longitude", product
				.getLongitude(), restProperty.getLongitude()));
		fields.add(new ComparableField("Physicaladdress",
				product.getPhysicaladdress(), restProperty
						.getPhysicaladdress()));
		fields.add(new ComparableField("Check in",
				product.getCheckinId(), restProperty
						.getCheckinId()));
		fields.add(new ComparableField("Currency",
				product.getCurrency(), restProperty
						.getCurrency()));
		fields.add(new ComparableField("Rank",
				product.getRank(), restProperty
						.getRank()));
		fields.add(new ComparableField("Rating",
				product.getRating(), restProperty
						.getRating()));

		// here we need to check whether location is null or not so
		// adding restproperty to both the fields
		if (restProperty.getLocationid() == null)
			fields.add(new ComparableField("Location", "VALUED",
					"EMPTY"));
		else
			fields.add(new ComparableField("Location", restProperty
					.getLocationid(), restProperty.getLocationid()));
		java.util.Set<String> attributes=launcher.getHandler().getAttributes(clsProperty);
		if (restProperty.getAttributes() != null
			     && restProperty.getAttributes().attribute != null &&
			    		 attributes != null ) {
			    int dbAttrCount = 0;
			    for (Attribute attrs : restProperty.getAttributes().attribute) {
			     if(attrs.values != null){
			      if(attrs.values.value != null){
			       dbAttrCount += attrs.values.value.size();
			      }
			     }
			    }

			    fields.add(new ComparableField("Attributes Count",
			    		attributes.size(), dbAttrCount+1)); // added one for hard coded value of Property Class Type (PCT)
			   }else{
					fields.add(new ComparableField("Attributes Count",0,0));
				}
		
		if(clsProperty.getArrPicList()!=null && clsProperty.getArrPicList().getClsPictureInfo()!=null && restProperty
				.getImages()!=null &&  restProperty
				.getImages().image!=null){
			fields.add(new ComparableField("Image Count",
					clsProperty.getArrPicList().getClsPictureInfo().size(), restProperty
							.getImages().image.size()));
		}else{
			fields.add(new ComparableField("Image Count",0,0));
		}
		
		List<Price> listPrice=launcher.getHandler().getAllPrice(clsProperty, product);
		if(listPrice!=null && restProperty
				.getPricetable()!=null && restProperty
						.getPricetable().price!=null){
			fields.add(new ComparableField("Price Count",
					listPrice.size(), restProperty
							.getPricetable().price.size()));
		}else{
			fields.add(new ComparableField("Price Count",""+listPrice.size(),"-"));
		}
		java.util.Set<Date> listBookedDate=launcher.getHandler().getReservedDates(clsProperty);
		if(listBookedDate!=null && restProperty
				.getReservations()!=null && restProperty
				.getReservations().Reservation!=null){
		fields.add(new ComparableField("Booked Dates Count",
				restProperty
				.getReservations().Reservation.size(), restProperty
						.getReservations().Reservation.size()));
		}else{
			fields.add(new ComparableField("Booked Dates Count",0,0));
		}
		
		
		return fields;
		
	}

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	List<Date> getDates(Date cal,String pattern){
//		List<Date> dates=new ArrayList<Date>();
		GregorianCalendar itrCal=new GregorianCalendar();
		itrCal.setTime(cal);
		int i=0,l=pattern.length(); String searchPattern=pattern.toLowerCase();
		for(i=0;i<l;i++,itrCal.add(Calendar.DATE, 1)){
			if(searchPattern.charAt(i)=='n'){
				System.out.println(sdf.format(itrCal.getTime()));
			}
		}
		return null;
	}
	 //@Test
		public void testInquireReservation() throws Exception {
			System.out.println("Inside testInquireReservation");
		//http://localhost:8090/Razor/xml/rest/reservation/inquiry?pos=5e7e3a77b3714ea2&productid=3&fromdate=2014-07-17&todate=2014-07-21&notes=test&adult=2&child=2&emailaddress=sen@mybookingpal1.com&familyname=family&firstname=sen&telnumber=1234567&cc_address=test address&cc_country=IN&cc_city=Chennai&cc_zip=600044&cc_state=TN&cc_bdd=31&cc_bdm=10&cc_bdy=1983

			HttpURLConnection connection = null;
			String localURL = "http://localhost:8080/Razor/xml/rest/reservation/inquiry?pos=5e7e3a77b3714ea2&productid=453670&fromdate=2014-08-17&todate=2014-08-21&notes=test&adult=2&child=2&emailaddress=sen@mybookingpal1.com&familyname=family&firstname=sen&telnumber=1234567&cc_address=test%20address&cc_country=IN&cc_city=Chennai&cc_zip=600044&cc_state=TN&cc_bdd=31&cc_bdm=10&cc_bdy=1983";
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
			
				if(reservationResponse!=null && reservationResponse.getReservationInfo()!=null) {// &&  "Inquiry".equalsIgnoreCase(reservationResponse.getReservation().getState())){
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

}