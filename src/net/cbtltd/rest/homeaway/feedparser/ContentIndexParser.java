package net.cbtltd.rest.homeaway.feedparser;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBException;

import net.cbtltd.rest.common.utils.CommonUtils;
import net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListingIndex;
import net.cbtltd.rest.homeaway.feedparser.domain.Listing;
import net.cbtltd.rest.homeaway.feedparser.domain.ListingContentIndex;
import net.cbtltd.rest.homeaway.feedparser.domain.ListingContentIndexEntry;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
class ContentIndexParser extends FeedParser<AdvertiserListingIndex.Advertiser,net.cbtltd.rest.homeaway.feedparser.domain.Listing> {
	private String url;

	public ContentIndexParser(String url,Version myVersion) throws JAXBException, MalformedURLException{
		this.url=url;
		setMyVersion(myVersion);
	}
	
	@Override
	public Multimap<AdvertiserListingIndex.Advertiser,net.cbtltd.rest.homeaway.feedparser.domain.Listing> load() throws MalformedURLException{
		if(url==null) return null;
		Multimap<AdvertiserListingIndex.Advertiser,net.cbtltd.rest.homeaway.feedparser.domain.Listing> result=
				LinkedHashMultimap.create();
		if (getMyVersion() == Version.VERSION3_3) {
			ListingContentIndex content = CommonUtils.unmarshall(new URL(url),
					ListingContentIndex.class);
			for (AdvertiserListingIndex.Advertiser advertiser : content
					.getAdvertisers().getAdvertiser()) {
				for (ListingContentIndexEntry contentIndex : advertiser
						.getListingContentIndexEntry()) {
					Listing listing = CommonUtils.unmarshall(new URL(
							contentIndex.getListingUrl()), Listing.class);
					if (contentIndex.getListingUrl() != null
							&& !contentIndex.getListingUrl().isEmpty()) {
						result.put(advertiser, listing);
					}
				}
			}
		}
		return result;
	}
	
}
