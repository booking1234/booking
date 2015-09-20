package net.cbtltd.rest.bookingcom.availability.test;

import net.cbtltd.rest.bookingcom.availability.AvailabilityUtils;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Junit test class to test AvailabilityUtils
 * @author nibodha
 *
 */
public class AvailabilityUtilsTest {

	public static final String BOOKINGCOM_USERNAME = "bp.bookingcom.username";
	public static final String BOOKINGCOM_PWD = "bp.bookingcom.pwd";

	private static final Logger LOG = Logger
			.getLogger(AvailabilityUtilsTest.class.getName());

	@BeforeClass
	public static void setUp() {

	}

	@Test
	public void testBookingComReservationAPI() throws Exception {
		AvailabilityUtils availabilityUtils = new AvailabilityUtils();
		//STANDARD_RATE(145473),NON_REFUNDABLE_RATE(300917),SPECIAL_RATE(2349410);
		availabilityUtils.setLoadSelectedProducts(false);
		availabilityUtils.getListInputProducts().add("121");
//		availabilityUtils.setRateType(145473);
		availabilityUtils.setRateType(0);
		availabilityUtils.setRoomsToSell(1);
		/*availabilityUtils.setFromPriceId(54616093);
		availabilityUtils.setToPriceId(54616093);
		availabilityUtils.setMinimunStay(1);
		availabilityUtils.setMaximumStay(20);
		availabilityUtils.setClosedOnArrival(0);
		availabilityUtils.setClosedOnDeparture(0);*/
		availabilityUtils.fetchAndUpdateAvailabilityToBookingCom();

	}

	@AfterClass
	public static void tearDown() {

	}
}
