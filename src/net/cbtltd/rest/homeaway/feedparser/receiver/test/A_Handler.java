package net.cbtltd.rest.homeaway.feedparser.receiver.test;

import java.net.MalformedURLException;
import java.util.Map;

import javax.xml.bind.JAXBException;

import net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListingIndex.Advertiser;
import net.cbtltd.rest.homeaway.feedparser.domain.AdvertiserListingIndex;
import net.cbtltd.rest.homeaway.feedparser.domain.Listing;
import net.cbtltd.rest.homeaway.feedparser.domain.RatePeriods;
import net.cbtltd.rest.homeaway.feedparser.receiver.FeedParser;
import net.cbtltd.rest.homeaway.feedparser.receiver.FeedParser.Version;
import net.cbtltd.rest.homeaway.feedparser.receiver.FeedParserFactory;
import net.cbtltd.rest.homeaway.feedparser.receiver.IFeedReceiver;
import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.server.PartnerHandler;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;

import org.apache.ibatis.session.SqlSession;

/**
 * @author nibodha
 * 
 */
public class A_Handler extends PartnerHandler implements IsPartner ,IFeedReceiver{

	public A_Handler(Partner partner) {
		super(partner);
	}

	@Override
	public void readPrices() {
		String allProductRateUrl= "http://www.lodgix.com/homeaway/feed/contentindex/rates";
		Version version=Version.VERSION3_3;
		try {
			FeedParser parser=FeedParserFactory.getFactoryInstance().createRateIndexedFeedParser(this, allProductRateUrl, version);
			parser.load();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	@Override
	public boolean receiveRates(Advertiser advertiser, RatePeriods listing) {
		System.out.println("received feed :" +advertiser.getAssignedId() +" : "+listing.getRatePeriod().size()+" price entries");
		return false;
	}
	FeedParser parser=null;
	
	@Override
	public void readProducts() {
		String allProductUrl= "http://www.lodgix.com/homeaway/feed/contentindex/listings";
		Version version=Version.VERSION3_3;
		try {
			parser=FeedParserFactory.getFactoryInstance().createContentIndexedFeedParser(this, allProductUrl, version);
			parser.load();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	@Override
	public boolean receiveProduct(Advertiser advertiser, Listing listing) {
		System.out.println("received feed :" +advertiser.getAssignedId() +" : "+listing.getExternalId());
		//listing.getUnits().getUnit().get(0).getPropertyType().getCode();
		return false;
	}

	@Override
	public void readSchedule() {
		String allProductReservationUrl= "http://www.lodgix.com/homeaway/feed/contentindex/reservations";
		Version version=Version.VERSION3_3;
		try {
			FeedParser parser=FeedParserFactory.getFactoryInstance().createReservationIndexedFeedParser(this, allProductReservationUrl, version);
			parser.load();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	@Override
	public boolean receiveReservation(AdvertiserListingIndex.Advertiser advertiser,net.cbtltd.rest.homeaway.feedparser.domain.Reservations reservations) {
		System.out.println("received feed :" +advertiser.getAssignedId() +" : "+reservations.getReservation().size()+" reservation entries");
		return false;
	}

	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		return false;
	}

	@Override
	public void createReservation(SqlSession sqlSession, Reservation reservation) {
		
	}

	@Override
	public ReservationPrice readPrice(SqlSession sqlSession,
			Reservation reservation, String productAltId, String currency) {
		return null;
	}

	@Override
	public Map<String, String> createReservationAndPayment(
			SqlSession sqlSession, Reservation reservation,
			CreditCard creditCard) {
		return null;
	}

	@Override
	public void inquireReservation(SqlSession sqlSession,
			Reservation reservation) {
		
	}

	@Override
	public void confirmReservation(SqlSession sqlSession,
			Reservation reservation) {
		
	}

	@Override
	public void readReservation(SqlSession sqlSession, Reservation reservation) {
		
	}

	@Override
	public void updateReservation(SqlSession sqlSession, Reservation reservation) {
		
	}

	@Override
	public void cancelReservation(SqlSession sqlSession, Reservation reservation) {
		
	}

	@Override
	public void readAlerts() {
		
	}

	@Override
	public void readSpecials() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readDescriptions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readImages() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readAdditionCosts() {
		// TODO Auto-generated method stub
		
	}

	

		
}

