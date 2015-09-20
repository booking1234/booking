package net.cbtltd.rest.hotelscombined.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.cbtltd.rest.hotelscombined.availability.AvailabilityUtils;
import net.cbtltd.rest.hotelscombined.rates.RateUtils;
import net.cbtltd.rest.hotelscombined.reservation.ReservationPushAPI;
import net.cbtltd.rest.hotelscombined.reservation.domain.OTAHotelResNotifRQ;
import net.cbtltd.rest.hotelscombined.reservation.domain.OTAHotelResNotifRS;
import net.cbtltd.rest.hotelscombined.roomrates.RoomRateUtils;
import net.cbtltd.server.ChannelProductService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Reservation.State;
import net.cbtltd.shared.Unit;

import org.apache.ibatis.session.SqlSession;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mybookingpal.config.RazorModule;

public class HotelsCombinedTest {
	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	static SqlSession sqlSession=null;
	
	@BeforeClass
	public static void setup(){
		Injector injector = Guice.createInjector(new RazorModule());
		sqlSession = RazorServer.openSession();
	}
//	@Test
	public void testNotifyAvilabilityReservationCreation() throws Exception {
		
		Product product = ChannelProductService.getInstance().readProductFromChannelProductMapper(sqlSession, 420, 
				"2645", "7403");
		
		System.out.println("Product getid "+product.getId());
		
		Reservation reservation =new Reservation();
		reservation.setAltid("12345");
		reservation.setAltpartyid("12345");
		reservation.setProductid(product.getId());
		reservation.setFromdate(DF.parse("2015-09-23"));
		reservation.setTodate(DF.parse("2015-09-25"));
		reservation.setState(State.Reserved.name());
		sqlSession.getMapper(ReservationMapper.class).create(reservation);
		sqlSession.commit();
		System.out.println("Reservation object id " + reservation.getId());
	}
	
//	@Test
	public void testNotifyAvilabilityReservationModification() throws Exception {
		NameId action=new NameId();
		action.setId("12345");
		action.setName("12345");
		
		Reservation reservation=sqlSession.getMapper(ReservationMapper.class).altread(action);
		reservation.setTodate(DF.parse("2015-09-27"));
		reservation.setState(State.Reserved.name());
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		sqlSession.commit();
		System.out.println("Reservation object id " + reservation.getId());
	}
	
	
//	@Test
	public void testNotifyAvilabilityReservationCancellation() throws Exception {
	//State.Cancelled.name()

		NameId action=new NameId();
		action.setId("12345");
		action.setName("12345");
		Reservation reservation=sqlSession.getMapper(ReservationMapper.class).altread(action);
		reservation.setState(State.Cancelled.name());
		
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		sqlSession.commit();
		System.out.println("Reservation object id " + reservation.getId());
	
	}
//	@Test
	public void testGetAvailability(){

		AvailabilityUtils availabilityUtils=new AvailabilityUtils();
		try {
			availabilityUtils.getAvailability("2014-10-08", "2014-10-15", "2645", "3109");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
//	@Test
	public void testNotifyRates() {
		System.out.println("Env "+sqlSession.getConfiguration().getEnvironment().getId());
		try {
		Product product = ChannelProductService.getInstance().readProductFromChannelProductMapper(sqlSession, 420, 
				"2645", "7403");
		System.out.println("The Test product id is "+product.getId());
		Price price = new Price();
		price.setPartyid(product.getSupplierid());
		price.setEntitytype(NameId.Type.Product.name());
		price.setEntityid(product.getId());//productID
		price.setCurrency(product.getCurrency());
		price.setQuantity(1.0);
		price.setUnit(Unit.DAY);
		price.setRule(Price.Rule.AnyCheckIn.name());
		price.setName("Rack Rate");
		price.setValue(85.00);
		price.setMinimum(340.0);
		price.setMinStay(4);
		price.setMaxStay(5);
		price.setState(Price.CREATED);
		price.setType(NameId.Type.Reservation.name());
		price.setDate(DF.parse("2014-11-08"));
		price.setTodate(DF.parse("2014-11-15"));
		price.setAvailable(1);
	Price exists = sqlSession.getMapper(PriceMapper.class).exists(price);
		if (exists == null) {
			System.out.println("Creating Price");
			sqlSession.getMapper(PriceMapper.class).create(price);
			sqlSession.commit();
		}else{
			System.out.println("Price already exists");
		}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
//	@Test
	public void testFetchRates() {
		RateUtils rateUtils=new RateUtils();
		try {
			rateUtils.getRates("2014-10-08", "2014-10-15", "2645", "3109");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
	public void testFetchRoomRates() {
		RoomRateUtils roomRateUtils=new RoomRateUtils();
		try {
			List<String> listHotelCode=new ArrayList<String>();
			listHotelCode.add("");
			roomRateUtils.getRoomRates(listHotelCode);
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReservationPushURI() throws Exception{
		ReservationPushAPI reservationPushAPI=new ReservationPushAPI();
	
	BufferedReader br = null;
	StringBuilder strBuilder=new StringBuilder();
	 
	try {

		String sCurrentLine;

		br = new BufferedReader(new FileReader("F:\\Nibodha\\clients\\BookingPal\\workspace\\docs\\hotelscombined\\test_reservation.xml"));

		while ((sCurrentLine = br.readLine()) != null) {
			strBuilder.append(sCurrentLine);
			//System.out.println(sCurrentLine);
		}

	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		try {
			if (br != null)br.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	JAXBContext jaxbContext = JAXBContext.newInstance(OTAHotelResNotifRQ.class);
	Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();
	StringReader xmlReader = new StringReader(strBuilder.toString());
	System.out.println("test xml for reservation is "+strBuilder.toString());
	//jaxbUnMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	OTAHotelResNotifRQ otaHotelResNotifRQ=(OTAHotelResNotifRQ) jaxbUnMarshaller.unmarshal(xmlReader);
		//OTAHotelResNotifRQ otaHotelResNotifRQ=
	OTAHotelResNotifRS otaHotelResNotifRS=reservationPushAPI.reservationNotification("5", otaHotelResNotifRQ);
	System.out.println("otaHotelResNotifRS.getEchoToken() "+otaHotelResNotifRS.getEchoToken());
	System.out.println("otaHotelResNotifRS.otaHotelResNotifRS.getSuccess() "+otaHotelResNotifRS.getSuccess());
	System.out.println("otaHotelResNotifRS.getErrors() "+otaHotelResNotifRS.getErrors());
	System.out.println("otaHotelResNotifRS.getTimeStamp() "+otaHotelResNotifRS.getTimeStamp());
	}
	
	
	@AfterClass
	public static void tearDown(){
		sqlSession.close();
	}
}
