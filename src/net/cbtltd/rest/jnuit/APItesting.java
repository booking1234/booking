package net.cbtltd.rest.jnuit;

import static org.junit.Assert.*;
import net.cbtltd.rest.Constants;
import net.cbtltd.rest.SearchQuotes;
import net.cbtltd.rest.Items;
import net.cbtltd.rest.response.PropertyResponse;
import net.cbtltd.rest.response.QuoteResponse;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.Product;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.allen_sauer.gwt.log.client.Log;

public class APItesting {
	private static ReservationFeedGenerator reservationFeedGenerator = null;
	private static LocationFeedGenerator locationFeedGenerator = null;
	private static ProductFeedGenerator productFeedGenerator = null;
	private static String POS_ID = "834a55a7680c79fe";
	private static String perPage = "5";
	private static String page = "1";
	private static String guests = "2";
	private static String locationid = "21980";
	private static String currency = "USD";
	private static Boolean amenities = true;
	private static Boolean terms = true;
/*
	@Test
	public void testSearchQuotesPastDates(){
		reservationFeedGenerator  = reservationFeedGenerator.getInstance();
					
		//Test dates in the past
		//If statements for more visual errors
		// slow because searching through uat?
		SearchQuotes search = reservationFeedGenerator.generateSearchQuotes(locationid, "2014-05-14", "2014-05-21", guests, POS_ID, currency, terms, amenities, page, perPage, Constants.NO_XSL);
		if (Integer.parseInt(search.getQuotesCount()) > 0) { 
			Log.error("0 Search Quotes should have been returned for dates 2014-05-14 to 2014-05-21");
		}
		assertFalse(Integer.parseInt(search.getQuotesCount()) > 0);
		
		SearchQuotes search2 = reservationFeedGenerator.generateSearchQuotes(locationid, "2014-06-15", "2014-06-20", guests, POS_ID, currency, terms, amenities, page, perPage, Constants.NO_XSL);
		if (Integer.parseInt(search2.getQuotesCount()) > 0) { 
			Log.error("0 Search Quotes should have been returned for dates 2014-06-15 to 2014-06-20"); 
		}
		assertFalse(Integer.parseInt(search2.getQuotesCount()) > 0);
		
		SearchQuotes search3 = reservationFeedGenerator.generateSearchQuotes(locationid, "2014-01-22", "2014-01-28", guests, POS_ID, currency, terms, amenities, page, perPage, Constants.NO_XSL);
		if (Integer.parseInt(search3.getQuotesCount()) > 0) { 
			Log.error("0 Search Quotes should have been returned for dates 2014-01-22 to 2014-01-28"); 
		}
		assertFalse(Integer.parseInt(search3.getQuotesCount()) > 0);
		
		SearchQuotes search4 = reservationFeedGenerator.generateSearchQuotes(locationid, "2013-08-18", "2013-08-23", guests, POS_ID, currency, terms, amenities, page, perPage, Constants.NO_XSL);
		if (Integer.parseInt(search4.getQuotesCount()) > 0) { 
			Log.error("0 Search Quotes should have been returned for dates 2013-08-18 to 2013-08-23"); 
		}
		assertFalse(Integer.parseInt(search4.getQuotesCount()) > 0);
	}
	
	@Test(expected=Throwable.class)
	public void testSearchQuotesNegativeDates(){
		reservationFeedGenerator  = reservationFeedGenerator.getInstance();
				
		//Test to date is before from date
		SearchQuotes search = reservationFeedGenerator.generateSearchQuotes(locationid, "2014-05-14", "2014-05-13", guests, POS_ID, currency, terms, amenities, page, perPage, Constants.NO_XSL);
		if (Integer.parseInt(search.getQuotesCount()) > 0) { 
			Log.error("0 Search Quotes should have been returned for dates 2014-05-14 to 2014-05-13");
		}
		assertFalse(Integer.parseInt(search.getQuotesCount()) > 0);
		
		SearchQuotes search2 = reservationFeedGenerator.generateSearchQuotes(locationid, "2014-06-14", "2014-05-09", guests, POS_ID, currency, terms, amenities, page, perPage, Constants.NO_XSL);
		if (Integer.parseInt(search2.getQuotesCount()) > 0) { 
			Log.error("0 Search Quotes should have been returned for dates 2014-06-14 to 2014-05-09");
		}
		assertFalse(Integer.parseInt(search2.getQuotesCount()) > 0);
	}
	
	@Test
	public void testSearchQuotesFutureDates(){
		reservationFeedGenerator  = reservationFeedGenerator.getInstance();
				
		//Test future dates
		SearchQuotes search = reservationFeedGenerator.generateSearchQuotes(locationid, "2014-08-14", "2014-08-21", guests, POS_ID, currency, terms, amenities, page, perPage, Constants.NO_XSL);
		if (Integer.parseInt(search.getQuotesCount()) == 0) { 
			Log.error("Search Quotes should have been returned for dates 2014-08-14 to 2014-08-21");
		}
		assertTrue(Integer.parseInt(search.getQuotesCount()) > 0);
		
		SearchQuotes search2 = reservationFeedGenerator.generateSearchQuotes(locationid, "2014-08-10", "2015-08-14", guests, POS_ID, currency, terms, amenities, page, perPage, Constants.NO_XSL);
		if (Integer.parseInt(search2.getQuotesCount()) == 0) { 
			Log.error("Search Quotes should have been returned for dates 2014-08-10 to 2014-08-14");
		}
		assertTrue(Integer.parseInt(search2.getQuotesCount()) > 0);
	}
	
	@Test
	public void testSearchQuotesCurrentToFuture(){
		reservationFeedGenerator  = reservationFeedGenerator.getInstance();
				
		//Test from date today and to date in the future
		SearchQuotes search = reservationFeedGenerator.generateSearchQuotes(locationid, "2014-07-02", "2014-07-05", guests, POS_ID, currency, terms, amenities, page, perPage, Constants.NO_XSL);
		if (Integer.parseInt(search.getQuotesCount()) == 0) { 
			Log.error("Search Quotes should have been returned for dates 2014-07-02 to 2014-07-05");
		}
		assertTrue(Integer.parseInt(search.getQuotesCount()) > 0);
	}
	
	*/
	@Test
	public void testRealLocationCheck() {
		locationFeedGenerator = locationFeedGenerator.getInstance();
		
		Items ids = locationFeedGenerator.getNearIDs(88.0, 88.0, 100.0, "906", Constants.NO_XSL);
		assertTrue(ids.item.size() == 0);
		
	}
	
	@Test
	public void testFakeLocationCheck() {
		locationFeedGenerator = locationFeedGenerator.getInstance();
		
		Items ids = locationFeedGenerator.getNearIDs(48.87, 2.38, 100.0, "21980", Constants.NO_XSL);
		assertTrue(ids.item.size() != 0);
	}
	/*
	@Test
	public void testPropertyDetailsWithValidDates() {
		productFeedGenerator = productFeedGenerator.getInstance();
		PropertyResponse property = productFeedGenerator.getDetails("15182", POS_ID, Language.EN, "2014-08-28", "2014-09-04", "USD", true, "", Constants.NO_XSL);
		System.out.println(property.isError() + " " + property.getErrorMessage() + " " + property.getProperty());
		assertTrue(property.getProperty() != null);
	}
	*/
	
	@Test
	public void testGetPrices() {
		reservationFeedGenerator = reservationFeedGenerator.getInstance();
		
		QuoteResponse quote = reservationFeedGenerator.getCorrespondingQuotes(POS_ID, "21980", "2014-08-14", "2014-08-21", "USD", 2, 0);
		System.out.println(quote.getPropertyName() + " " + quote.getPrice());
		
	}
}
