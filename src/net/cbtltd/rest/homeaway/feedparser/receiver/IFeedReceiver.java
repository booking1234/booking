package net.cbtltd.rest.homeaway.feedparser.receiver;

import net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListingIndex;


public interface IFeedReceiver {
	public boolean receiveProduct(AdvertiserListingIndex.Advertiser advertiser,net.cbtltd.rest.homeaway.feedparser.domain.Listing listing);
	public boolean receiveReservation(AdvertiserListingIndex.Advertiser advertiser,net.cbtltd.rest.homeaway.feedparser.domain.Reservations reservations);
	public boolean receiveRates(AdvertiserListingIndex.Advertiser advertiser,net.cbtltd.rest.homeaway.feedparser.domain.RatePeriods rates);
}
