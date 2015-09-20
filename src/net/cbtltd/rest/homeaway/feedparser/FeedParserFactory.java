package net.cbtltd.rest.homeaway.feedparser;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import net.cbtltd.rest.homeaway.feedparser.FeedParser.Version;
import net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListings.Advertiser;
import net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListingIndex;
import net.cbtltd.rest.homeaway.feedparser.domain.Listing;
import net.cbtltd.rest.homeaway.feedparser.domain.RatePeriods;
import net.cbtltd.rest.homeaway.feedparser.domain.Reservations;

public class FeedParserFactory {
	private FeedParserFactory(){}
	static FeedParserFactory factory;
	public static FeedParserFactory getFactoryInstance(){
		if(factory==null){
			synchronized(FeedParserFactory.class)  {
				if(factory==null){
					factory=new FeedParserFactory();
				}
			}
		}
		return factory;
	}
	Map<String,FeedParser<?,?>> feedParsers=new HashMap<String,FeedParser<?,?>>();
	private FeedParser<?,?> getParser(String allProductUrl,Version version){
		String key=getKey(allProductUrl,version);
		if(feedParsers.containsKey(key))
		return feedParsers.get(key);
		return null;
	}
	
	public FeedParser<AdvertiserListingIndex.Advertiser,net.cbtltd.rest.homeaway.feedparser.domain.Listing>  createContentIndexedFeedParser(String allProductUrl) throws JAXBException, MalformedURLException{
		return createContentIndexedFeedParser( allProductUrl, Version.VERSION3_3);
	}
	
	public FeedParser<AdvertiserListingIndex.Advertiser,net.cbtltd.rest.homeaway.feedparser.domain.Listing> createContentIndexedFeedParser(String allProductUrl,Version version) throws JAXBException, MalformedURLException{
		@SuppressWarnings("unchecked")
		FeedParser<AdvertiserListingIndex.Advertiser,net.cbtltd.rest.homeaway.feedparser.domain.Listing> parser=(FeedParser<net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListingIndex.Advertiser, Listing>) getParser(allProductUrl,version);
		if(parser!=null) {  return parser; }
		parser=new ContentIndexParser(allProductUrl,version);
		feedParsers.put(getKey(allProductUrl,parser.getMyVersion()), parser);
		return parser;
	}
	
	public FeedParser<net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListings.Advertiser,net.cbtltd.rest.homeaway.feedparser.domain.Listing>  createBatchFeedParser(String allProductUrl) throws JAXBException, MalformedURLException{
		return createBatchFeedParser( allProductUrl, Version.VERSION3_3);
	}
	
	public FeedParser<net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListings.Advertiser,net.cbtltd.rest.homeaway.feedparser.domain.Listing> createBatchFeedParser(String allProductUrl,Version version) throws JAXBException, MalformedURLException{
		@SuppressWarnings("unchecked")
		FeedParser<net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListings.Advertiser,net.cbtltd.rest.homeaway.feedparser.domain.Listing> parser=(FeedParser<Advertiser, Listing>) getParser(allProductUrl,version);
		if(parser!=null) { return parser; }
		parser=new BatchFeedParser(allProductUrl,version);
		feedParsers.put(getKey(allProductUrl,parser.getMyVersion()), parser);
		return parser;
	}
	
	public FeedParser<net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListingIndex.Advertiser, Reservations>  createReservationIndexedFeedParser(String allProductReservationUrl) throws JAXBException, MalformedURLException{
		return createReservationIndexedFeedParser( allProductReservationUrl, Version.VERSION3_3);
	}
	
	public FeedParser<net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListingIndex.Advertiser, Reservations> createReservationIndexedFeedParser(String allProductReservationUrl,Version version) throws JAXBException, MalformedURLException{
		@SuppressWarnings("unchecked")
		FeedParser<net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListingIndex.Advertiser, Reservations> parser=(FeedParser<net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListingIndex.Advertiser, Reservations>) getParser(allProductReservationUrl,version);
		if(parser!=null) {  return parser; }
		parser=new ReservationContentIndexParser(allProductReservationUrl,version);
		feedParsers.put(getKey(allProductReservationUrl,parser.getMyVersion()), parser);
		return parser;
	}
	
	public FeedParser<net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListingIndex.Advertiser, RatePeriods>  createRateIndexedFeedParser(String allProductReservationUrl) throws JAXBException, MalformedURLException{
		return createRateIndexedFeedParser( allProductReservationUrl, Version.VERSION3_3);
	}
	
	public FeedParser<net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListingIndex.Advertiser, RatePeriods> createRateIndexedFeedParser(String allProductReservationUrl,Version version) throws JAXBException, MalformedURLException{
		@SuppressWarnings("unchecked")
		FeedParser<net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListingIndex.Advertiser, RatePeriods> parser=(FeedParser<net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListingIndex.Advertiser, RatePeriods>) getParser(allProductReservationUrl,version);
		if(parser!=null) {  return parser; }
		parser=new RateContentIndexParser(allProductReservationUrl,version);
		feedParsers.put(getKey(allProductReservationUrl,parser.getMyVersion()), parser);
		return parser;
	}
	
	private String getKey(String allProductUrl,Version version){
		return version.name()+(allProductUrl!=null?allProductUrl:"");
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
