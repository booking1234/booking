package net.cbtltd.rest.homeaway.feedparser.receiver;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import net.cbtltd.rest.homeaway.feedparser.receiver.FeedParser.Version;

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
	Map<String,FeedParser> feedParsers=new HashMap<String,FeedParser>();
	private FeedParser getParser(String allProductUrl,Version version){
		String key=getKey(allProductUrl,version);
		if(feedParsers.containsKey(key))
		return feedParsers.get(key);
		return null;
	}
	
	public FeedParser  createContentIndexedFeedParser(IFeedReceiver receiver,String allProductUrl) throws JAXBException, MalformedURLException{
		return createContentIndexedFeedParser( receiver,allProductUrl, Version.VERSION3_3);
	}
	
	public FeedParser createContentIndexedFeedParser(IFeedReceiver receiver,String allProductUrl,Version version) throws JAXBException, MalformedURLException{
		FeedParser parser=(FeedParser) getParser(allProductUrl,version);
		if(parser==null) {  
		parser=new ContentIndexParser(allProductUrl,version);
		feedParsers.put(getKey(allProductUrl,parser.getMyVersion()), parser);
		}
		parser.setReceiver(receiver);
		return parser;
	}
	
	public FeedParser  createBatchFeedParser(IFeedReceiver receiver,String allProductUrl) throws JAXBException, MalformedURLException{
		return createBatchFeedParser( receiver,allProductUrl, Version.VERSION3_3);
	}
	
	public FeedParser createBatchFeedParser(IFeedReceiver receiver,String allProductUrl,Version version) throws JAXBException, MalformedURLException{
		FeedParser parser=(FeedParser) getParser(allProductUrl,version);
		if(parser==null) { 
		parser=new BatchFeedParser(allProductUrl,version);
		feedParsers.put(getKey(allProductUrl,parser.getMyVersion()), parser);
		}
		parser.setReceiver(receiver);
		return parser;
	}
	
	public FeedParser  createReservationIndexedFeedParser(IFeedReceiver receiver,String allProductReservationUrl) throws JAXBException, MalformedURLException{
		return createReservationIndexedFeedParser( receiver,allProductReservationUrl, Version.VERSION3_3);
	}
	
	public FeedParser createReservationIndexedFeedParser(IFeedReceiver receiver,String allProductReservationUrl,Version version) throws JAXBException, MalformedURLException{
		FeedParser parser=(FeedParser) getParser(allProductReservationUrl,version);
		if(parser==null) { 
		parser=new ReservationContentIndexParser(allProductReservationUrl,version);
		feedParsers.put(getKey(allProductReservationUrl,parser.getMyVersion()), parser);
		}
		parser.setReceiver(receiver);
		return parser;
	}
	
	public FeedParser  createRateIndexedFeedParser(IFeedReceiver receiver,String allProductRateUrl) throws JAXBException, MalformedURLException{
		return createRateIndexedFeedParser( receiver,allProductRateUrl, Version.VERSION3_3);
	}
	
	public FeedParser createRateIndexedFeedParser(IFeedReceiver receiver,String allProductRateUrl,Version version) throws JAXBException, MalformedURLException{
		FeedParser parser=(FeedParser) getParser(allProductRateUrl,version);
		if(parser==null) { 
		parser=new RateContentIndexParser(allProductRateUrl,version);
		feedParsers.put(getKey(allProductRateUrl,parser.getMyVersion()), parser);
		}
		parser.setReceiver(receiver);
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
