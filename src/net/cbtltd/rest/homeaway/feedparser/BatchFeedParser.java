package net.cbtltd.rest.homeaway.feedparser;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBException;

import net.cbtltd.rest.common.utils.CommonUtils;
import net.cbtltd.rest.homeaway.feedparser.domain.Listing;
import net.cbtltd.rest.homeaway.feedparser.domain.ListingBatch;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;


class BatchFeedParser extends FeedParser<net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListings.Advertiser,net.cbtltd.rest.homeaway.feedparser.domain.Listing> {
	private String  url;

	public BatchFeedParser(String url,Version myVersion) throws JAXBException, MalformedURLException{
		this.url=url;
		setMyVersion(myVersion);
	}
	@Override
	public Multimap<net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListings.Advertiser,net.cbtltd.rest.homeaway.feedparser.domain.Listing> load() throws MalformedURLException{
		if(url==null) return null;
		Multimap<net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListings.Advertiser,net.cbtltd.rest.homeaway.feedparser.domain.Listing> result=
				LinkedHashMultimap.create();
		ListingBatch content=CommonUtils.unmarshall(new URL(url), ListingBatch.class);
		if (getMyVersion() == Version.VERSION3_3) {
			for (net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListings.Advertiser advertiser : content
					.getAdvertisers().getAdvertiser()) {
				for (Listing listing : advertiser.getListings().getListing()) {
					result.put(advertiser, listing);
				}
			}
			return result;
		}
		return null;
	}
	
	
}
