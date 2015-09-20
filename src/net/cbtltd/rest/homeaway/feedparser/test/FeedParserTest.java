package net.cbtltd.rest.homeaway.feedparser.test;

import java.net.URL;

import net.cbtltd.rest.homeaway.feedparser.FeedParser;
import net.cbtltd.rest.homeaway.feedparser.FeedParser.Version;
import net.cbtltd.rest.homeaway.feedparser.FeedParserFactory;
import net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListingIndex;
import net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListings.Advertiser;
import net.cbtltd.rest.homeaway.feedparser.domain.Listing;
import net.cbtltd.rest.homeaway.feedparser.domain.RatePeriods;
import net.cbtltd.rest.homeaway.feedparser.domain.Reservations;
import net.cbtltd.server.RazorServer;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.Partner;

import org.apache.ibatis.session.SqlSession;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;



public class FeedParserTest {
	
	
	static String altpartyid;
	SqlSession session;
	Partner partner;
//	private static final Logger LOG = Logger.getLogger(FeedParserTest.class
//			.getName());
	
	
	
	@Before
	public void setUp() throws Exception {
		altpartyid = "201";
		session=RazorServer.openSession();
		System.out.println("altpartyid " + altpartyid +" encrypt : "+Model.encrypt("201"));
	}
	
	//static String allProductUrl= "http://www.lodgix.com/homeaway/feed/contentindex/listings";
	static String allProductUrl= "http://www.lodgix.com/partners/feed/bookingpal/en/contentindex/listings";
	@Test
	public void loadCompleteStaticData() {
		try {
			System.out.println("static load starting @ " + new DateTime());
			FeedParser<AdvertiserListingIndex.Advertiser,net.cbtltd.rest.homeaway.feedparser.domain.Listing> parser = 
					FeedParserFactory.getFactoryInstance()
					.createContentIndexedFeedParser(
							allProductUrl,
							Version.VERSION3_3);
			System.out.println(parser.load());
			System.out.println("static load finished @ "+new DateTime());
			//testloadCompleteStaticData();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	

	static String allProductReservationUrl= "http://www.lodgix.com/homeaway/feed/contentindex/reservations";
	@Test
	public void loadCompleteReservationData() {
		try {
			System.out.println("static load starting @ " + new DateTime());
			FeedParser<net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListingIndex.Advertiser, Reservations> parser = FeedParserFactory.getFactoryInstance()
					.createReservationIndexedFeedParser(
							allProductReservationUrl,
							Version.VERSION3_3);
			System.out.println(parser.load());
			System.out.println("static load finished @ "+new DateTime());
			//testloadCompleteStaticData();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	static String allProductRateUrl= "http://www.lodgix.com/homeaway/feed/contentindex/rates";
	@Test
	public void loadCompleteRateData() {
		try {
			System.out.println("static load starting @ " + new DateTime());
			FeedParser<net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListingIndex.Advertiser, RatePeriods> parser = FeedParserFactory.getFactoryInstance()
					.createRateIndexedFeedParser(
							allProductRateUrl,
							Version.VERSION3_3);
			System.out.println(parser.load());
			System.out.println("static load finished @ "+new DateTime());
			//testloadCompleteStaticData();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	static String productUrl= "http://www.lodgix.com/homeaway/feed/listing/%s";
	static String altid0="399";
	@Test
	public void loadSpecificProductData() {
		try {
			System.out.println("static load starting @ " + new DateTime());
			FeedParser<?,?> parser = FeedParserFactory.getFactoryInstance().createContentIndexedFeedParser(null,Version.VERSION3_3);
			System.out.println(parser.loadProduct(productUrl, altid0));
			System.out.println("static load finished @ "+new DateTime());
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	static String reservationUrl= "http://www.lodgix.com/homeaway/feed/reservations/%s";
	static String altid="399";
	@Test
	public void loadSpecificReservationData() {
		try {
			System.out.println("reservation starting @ " + new DateTime());
			FeedParser<?,?> parser = FeedParserFactory.getFactoryInstance().createContentIndexedFeedParser(null,Version.VERSION3_3);
			System.out.println(parser.loadReservation(new URL(String.format(
					reservationUrl, altid))));
			System.out.println("reservation finished @ "+new DateTime());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	static String ratesUrl= "http://www.lodgix.com/homeaway/feed/rates/%s";
	static String altid2="399";
	@Test
	public void loadSpecificRateData() {
		try {
			System.out.println("static load starting @ " + new DateTime());
			FeedParser<?,?> parser = FeedParserFactory.getFactoryInstance().createContentIndexedFeedParser(null,Version.VERSION3_3);
			System.out.println(parser.loadPrice(ratesUrl, altid2));
			System.out.println("static load finished @ "+new DateTime());
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	static String batchUrl= "http://www.tropicalvillavacations.com/xml/HomeAway/xml.aspx";
	@Test
	public void loadBatchData() {
		try {
			System.out.println("static load starting @ " + new DateTime());
			FeedParser<Advertiser, Listing> parser = FeedParserFactory.getFactoryInstance().createBatchFeedParser( null,Version.VERSION3_3);
			System.out.println(parser.load());
			System.out.println("static load finished @ "+new DateTime());
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	
}
