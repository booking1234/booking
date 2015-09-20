package net.cbtltd.rest.jnuit;

import net.cbtltd.rest.AbstractReservation;
import net.cbtltd.rest.SearchQuotes;
import net.cbtltd.rest.response.QuoteResponse;

public class ReservationFeedGenerator extends AbstractReservation {
	private static ReservationFeedGenerator reservationFeedGenerator;
	public ReservationFeedGenerator() {}
	
	public static ReservationFeedGenerator getInstance() {
		if (reservationFeedGenerator == null) {
			reservationFeedGenerator = new ReservationFeedGenerator();
		}
		return reservationFeedGenerator;
	}
	
	public SearchQuotes generateSearchQuotes(
			String locationid,
			String fromdate,
			String todate,
			String guests,
			String pos,
			String currency,
			Boolean terms,
			Boolean amenities,
			String page,
			String perpage,
			String xsl) {
		
		return getSearchQuotes(locationid, fromdate, todate, guests, pos, currency, terms, amenities, page, perpage, xsl);
	}
	
	public QuoteResponse getCorrespondingQuotes(
			String pos, 
			String productid, 
			String fromDateString, 
			String todate, 
			String currency, 
			Integer adults, 
			Integer children) {
		return getQuotes(pos, productid, fromDateString, todate, currency, adults, children);
	}
}
