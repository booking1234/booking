package com.mybookingpal.server.api.junit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.ReservationExt;

import org.apache.http.client.ClientProtocolException;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mybookingpal.config.RazorModule;
import com.mybookingpal.server.EmailService;
import com.mybookingpal.wrapper.ReservationWrapper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;


public class EmailServiceTest {
	
	// 1. Configure FreeMarker
    //
    // You should do this ONLY ONCE, when your application starts,
    // then reuse the same Configuration object elsewhere.
    
    Configuration cfg = new Configuration();
    
	@Before
	public void setup(){
		// Where do we load the templates from:
	    cfg.setClassForTemplateLoading(EmailServiceTest.class, "templates");
	    
	    // Some other recommended settings:
	    cfg.setDefaultEncoding("UTF-8");
	    cfg.setLocale(Locale.US);
	    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	    Injector injector = Guice.createInjector(new RazorModule());
	}

	//@Test
	public void testTemplate() {
		// 2. Proccess template(s)
	    //
	    // You will do this for several times in typical applications.
	    
	    // 2.1. Prepare the template input:
	    
	    Map<String, Object> input = new HashMap<String, Object>();
	    
	    input.put("title", "Cancelation email");
	    
	    Price price = new Price();
	    price.setCost(240.00);
	    input.put("price", price);
	    
	    Reservation reservation = new Reservation();
	    reservation.setState(Reservation.State.Cancelled.name());
	    reservation.setId("12345");
	    
	    input.put("reservation", reservation);
	    
	    Party party = new Party();
	    party.setName("Aktay Aydin");
	    party.setEmailaddress("alexaydin@gmail.com");
//	    party.setAddress("251 Avenu of the test test testtest, Los Angeles, CA 90067, USA");
	    input.put("party", party);
	    
	    
	    // 2.2. Get the template

	    Template template;
		try {
			template = cfg.getTemplate("cancellationmail.ftl");
			// 2.3. Generate the output

		    // Write output to the console
		    Writer consoleWriter = new OutputStreamWriter(System.out);
		    template.process(input, consoleWriter);

		    // For the sake of example, also write output into a file:
		    Writer fileWriter = new FileWriter(new File("output.html"));
		    try {
		      template.process(input, fileWriter);
		    } finally {
		      fileWriter.close();
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	    
	}
	
	@Test
	public void testEmailServiceReservation() throws ClientProtocolException, IOException, JAXBException {
				
	//	ReservationWrapper reservationWrapper=buildReservationWrapper();
		ReservationWrapper reservationWrapper=getReservationWrapper();
		EmailService.sendReservationEmail(reservationWrapper);
		
	}
	
	//@Test
	public void testEmailServiceCancellation() {
		//	ReservationWrapper reservationWrapper=buildReservationWrapper();
		ReservationWrapper reservationWrapper=getReservationWrapper();
		EmailService.sendCancellationEmail(reservationWrapper);
	}
	
	//@Test
	public void testBookingEmail(){
		System.out.println("Inside testBookingEmail");
		net.cbtltd.server.EmailService.reservationNotPersisted("Test Response from Booking.com");
	}
	
	private ReservationWrapper getReservationWrapper() {
		SqlSession sqlSession = RazorServer.openSession();
		NameId action = new NameId();
		action.setId("9567631");
		action.setName("325490");
		Reservation reservation = sqlSession.getMapper(ReservationMapper.class)
				.altread(action);
		
		System.out.println("Reservation object id " + reservation.getId());
		sqlSession.close();
				
		ReservationWrapper reservationWrapper = new ReservationWrapper();
		reservationWrapper.buildReservationWrapper(reservation,"1234");
		
		
		return reservationWrapper;
	}
	
	private ReservationWrapper getReservationWrapperRestProperty()
			throws ClientProtocolException, IOException, JAXBException {

		Reservation reservation = null;
		HttpURLConnection connection = null;
		String localURL = "http://localhost:8090/Razor/xml/rest/reservation/id/9567630?pos=5e7e3a77b3714ea2";
		
		
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
			JAXBContext jaxbContext = JAXBContext.newInstance(Reservation.class);
			Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();
			reservation = (Reservation) jaxbUnMarshaller.unmarshal(xmlreader);
			
	    } catch (MalformedURLException e1) {
	        e1.printStackTrace();
	    } catch (IOException e1) {
	        e1.printStackTrace();
	    } finally {
	        if(null != connection) { connection.disconnect(); }
	    }
		
	    System.out.println("reservation getCustomer "+reservation.getCustomer());
	    ReservationWrapper reservationWrapper=new ReservationWrapper();
		  reservationWrapper.setReservation(reservation);
		
		return reservationWrapper;

	}

	
	private ReservationWrapper buildReservationWrapper(){
	    
	    Party customer = new Party();
	    customer.setName("Aktay Aydin");
	    customer.setEmailaddress("alexaydin@gmail.com");
	    customer.setPostaladdress("101 Pacifica,\\n Irvine, \\nCA 92602");
	    customer.setCity("Irvine");
	    customer.setRegion("CA");
	    customer.setPostalcode("92602");
	    customer.setCountry("USA");
	    customer.setPhoneNumber("9940483172");
	    customer.setDayphone("9940483172");
	    
	    Party supplier = new Party();
	    supplier.setName("Supplier Name");
	    supplier.setEmailaddress("supplier@gmail.com");
	    supplier.setPostaladdress("101 Pacifica,\\n Irvine, \\nCA 92602");
	    supplier.setCity("Supplier City");
	    supplier.setRegion("SUP Region");
	    supplier.setPostalcode("92602");
	    supplier.setCountry("SUP COuntry");
	    supplier.setPhoneNumber("9940483172");
	    supplier.setDayphone("9940483172");
	    
	    Product product=new Product();
	    product.setName("Test Hotel");
	    product.setAdult(2);
	    product.setChild(4);
	    product.setPhysicaladdress("101 Pacifica,\\n Irvine, \\nCA 92602");
	    Location location=new Location();
	    location.setName("Test Location");
	    location.setRegion("Test Region");
	    location.setCountry("Test Country");
	    product.setLocation(location);
	    
	    Reservation reservation = new Reservation();
	    
	    ReservationExt ext=new ReservationExt();
	    ext.setName("Test Guest");
	    ext.setType("Guest");
	    ReservationExt ext1=new ReservationExt();
	    ext1.setName("Breakfast");
	    ext1.setType("Addon");
	    List<ReservationExt> listReservationExt=new ArrayList<ReservationExt>();
	    listReservationExt.add(ext);
	    listReservationExt.add(ext1);
	    reservation.setListReservationExt(listReservationExt);
	    reservation.setState(Reservation.State.Cancelled.name());
	    reservation.setId("12345");
	    reservation.setQuantity(4);
	    reservation.setArrivaltime("2014-07-25");
	    reservation.setDeparturetime("2014-07-26");
	    reservation.setAltid("Alt12345");
	    reservation.setCustomer(customer);
	    reservation.setProduct(product);
	    reservation.setSupplier(supplier);
	    	    
	    ReservationWrapper reservationWrapper=new ReservationWrapper();
	    reservationWrapper.setReservation(reservation);
	    return reservationWrapper;
	}

}
