package net.cbtltd.rest.bookingcom.availability.test;

import java.util.ArrayList;
import java.util.List;

import net.cbtltd.rest.bookingcom.availability.PriceUtils;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Junit test class to test PriceUtils
 * @author nibodha
 *
 */
public class PriceUtilsTest {

	private static final Logger LOG = Logger
			.getLogger(PriceUtilsTest.class.getName());

	@BeforeClass
	public static void setUp() {

	}

	@Test
	public void testPriceUtils() throws Exception {
		PriceUtils priceUtils=new PriceUtils();
		priceUtils.setFromDate("2014-10-01");
		priceUtils.setToDate("2014-10-10");
		List<Integer> daysToBeUpdated=new ArrayList<Integer>();
	//	daysToBeUpdated.add(1);
	//	daysToBeUpdated.add(2);
		daysToBeUpdated.add(3);
	//	daysToBeUpdated.add(4);
	//	daysToBeUpdated.add(5);
	//	daysToBeUpdated.add(6);
	//	daysToBeUpdated.add(7);
		priceUtils.setDayOfTheWeekToBeUpdated(daysToBeUpdated);
		priceUtils.setMinStayToBeUpdated(4);
		priceUtils.setMaxStayToBeUpdated(8);
		priceUtils.setCostToBeUpdated((double) 150);
		priceUtils.setSingleUserCostToBeUpdated((double) 100);
		priceUtils.setEntityId("122");
		priceUtils.setPartyId("5");
		priceUtils.setCurrency("USD");
		LOG.info("Before calling prepare Data Load");
		priceUtils.prepareDataLoad();
		LOG.info("After calling prepare Data Load");
	}
	
		
	@AfterClass
	public static void tearDown() {

	}
}
