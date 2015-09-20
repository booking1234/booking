package net.cbtltd.rest.homeaway.feedparser.receiver;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBException;

import net.cbtltd.rest.common.utils.CommonUtils;
import net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListingIndex;
import net.cbtltd.rest.homeaway.feedparser.domain.ListingContentIndex;
import net.cbtltd.rest.homeaway.feedparser.domain.ListingContentIndexEntry;
class ReservationContentIndexParser extends FeedParser {
	private String url;

	public ReservationContentIndexParser(String url,Version myVersion) throws JAXBException, MalformedURLException{
		this.url=url;
		setMyVersion(myVersion);
	}
	
	@Override
	public void load() throws MalformedURLException{
		if(url==null) return ;
		if (getMyVersion() == Version.VERSION3_3) {
			ListingContentIndex content = CommonUtils.unmarshall(new URL(url),
					ListingContentIndex.class);
			for (AdvertiserListingIndex.Advertiser advertiser : content
					.getAdvertisers().getAdvertiser()) {
				for (ListingContentIndexEntry contentIndex : advertiser
						.getListingContentIndexEntry()) {
					if (contentIndex.getUnitReservationsUrl() != null
							&& !contentIndex.getUnitReservationsUrl().isEmpty()) {
						net.cbtltd.rest.homeaway.feedparser.domain.ListingReservations reservationLst=loadReservation(new URL(contentIndex.getUnitReservationsUrl()));
							if(!getReceiver().receiveReservation(advertiser, reservationLst.getReservations())){
								return;
						}
					}
				}
			}
		}
	}
	
}
