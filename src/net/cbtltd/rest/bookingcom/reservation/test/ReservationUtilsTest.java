package net.cbtltd.rest.bookingcom.reservation.test;

import java.io.IOException;

import net.cbtltd.rest.bookingcom.reservation.ReservationPushAPI;
import net.cbtltd.rest.bookingcom.reservation.ReservationUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Junit test cases for test ReservationUtils
 * 
 * @author nibodha
 * 
 */
public class ReservationUtilsTest {
	static String localRESTURL;
	private static final Logger LOG = Logger
			.getLogger(ReservationUtilsTest.class.getName());

	@BeforeClass
	public static void setUp() {
		localRESTURL = "http://localhost:8090/Razor/xml/json/bookingcom/reservation?pos=5e7e3a77b3714ea2&reservationId=847840455&hotelId=13104";
		//localRESTURL="http://localhost:8090/Razor/xml/json/product/121/detail?pos=5e7e3a77b3714ea2&test=true";
	}

	// @Test
	public void testBookingComReservationAPIForOneHotel() throws Exception {
		ReservationUtils reservationUtils = new ReservationUtils();
		reservationUtils.getListConfirmationId().add(989593951);
		// 566196890,518609990
		reservationUtils.setProcessReservationWithPayment(false);
		reservationUtils.setProcessSpecificConfirmationId(true);
		reservationUtils.setLastChange("2014-07-22 10:00:00");
		reservationUtils.fetchAndUpdateReservationFromBookingCom(13104);

	}

	@Test
	public void testBookingComReservationAPIForAllHotels() throws Exception {
		ReservationUtils reservationUtils = new ReservationUtils();
		reservationUtils.fetchAndUpdateAllReservationsFromBookingCom();

	}
	
	@Test
	public void testBookingComReservationAPIBasedonNotification()
			throws Exception {
		
		ReservationUtils reservationUtils = new ReservationUtils();
		//reservationUtils.fetchAndUpdateReservationBasedOnNotification(13104, 847840455);
		//Test the EndPoint by instance
		ReservationPushAPI reservationPushAPI =new ReservationPushAPI();
		reservationPushAPI.reservationNotification("1234", 847840455, 13104);
	}
	
	//@Test
	public void testBookingComReservationPUSHAPI() throws Exception, IOException{

		DefaultHttpClient client = new DefaultHttpClient();
		LOG.info(" localRESTURL " + localRESTURL);
		HttpGet getRequest = new HttpGet(localRESTURL);
		HttpResponse response = client.execute(getRequest);
		int statusCode = response.getStatusLine().getStatusCode();

		LOG.info("Response Code : " + response.getStatusLine().getStatusCode());
	
	}

	

	@AfterClass
	public static void tearDown() {

	}

}
