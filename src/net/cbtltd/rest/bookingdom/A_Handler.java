package net.cbtltd.rest.bookingdom;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.rest.response.QuoteDetail;
import net.cbtltd.server.PartnerHandler;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;

public class A_Handler extends PartnerHandler implements IsPartner {
	private static final Logger LOG = Logger.getLogger(A_Handler.class.getName());
	private static final DateFormat DF = new SimpleDateFormat("ddMMyyyy");
	private static String PAYMENT_INFO_FOR_QUOTES = "Included in the price";

	public A_Handler(Partner partner) {
		super(partner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		if (reservation.notActive()) {
			throw new ServiceException(Error.reservation_state,
					reservation.getId() + " state " + reservation.getState());
		}
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		if (product == null) { throw new ServiceException(Error.product_id, reservation.getProductid()); }
		if (reservation.noAgentid()) { throw new ServiceException(Error.reservation_agentid); }
		Party agent = sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());
		if (agent == null) { throw new ServiceException(Error.party_id, reservation.getAgentid()); }
		if (reservation.noCustomerid()) { reservation.setCustomerid(Party.NO_ACTOR); }
		Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
		if (customer == null) { throw new ServiceException(Error.reservation_customerid, reservation.getCustomerid()); }
		
		String fromDate = DF.format(reservation.getFromdate());
		String toDate = DF.format(reservation.getTodate());
		return BookingDomService.isPropertyAvailable(product.getAltid(), fromDate, toDate);
	}

	@Override
	public void createReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.not_implemented, "this service is not supported by this PMS");
		
	}

	@Override
	public ReservationPrice readPrice(SqlSession sqlSession,
			Reservation reservation, String productAltId, String currency) {
		ReservationPrice result = new ReservationPrice();
		
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		if (product == null) { throw new ServiceException(Error.product_id, reservation.getProductid()); }
		BookingDomGuestComposition guestComposition = new BookingDomGuestComposition();
		guestComposition.setAdults(reservation.getAdult() == null ? 0 : reservation.getAdult());
		guestComposition.setChildren(reservation.getChild() == null ? 0 : reservation.getChild());
		guestComposition.setInfants(reservation.getInfant() == null ? 0 : reservation.getInfant());
		String fromDate = DF.format(reservation.getFromdate());
		String toDate = DF.format(reservation.getTodate());
		BookingDomPriceResult priceResult = BookingDomService.computePrice(product.getAltid(), fromDate, toDate, guestComposition);
		
		ArrayList<QuoteDetail> quoteDetails = new ArrayList<QuoteDetail>();
		
		// TODO: add the components of the quote details
		
		result.setCurrency("USD"); // TODO: change
		result.setQuoteDetails(quoteDetails);
		result.setTotal(Double.valueOf(priceResult.getCalculatedPrice())); // TODO: review this after seeing a real response from BookingDOM api
		return result;
	}

	@Override
	public Map<String, String> createReservationAndPayment(
			SqlSession sqlSession, Reservation reservation,
			CreditCard creditCard) {
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		if (product == null) { throw new ServiceException(Error.product_id, reservation.getProductid()); }
		BookingDomBookingRequest rq = new BookingDomBookingRequest();
		BookingDomGuestComposition guestComposition = new BookingDomGuestComposition();
		guestComposition.setAdults(reservation.getAdult() == null ? 0 : reservation.getAdult());
		guestComposition.setChildren(reservation.getChild() == null ? 0 : reservation.getChild());
		guestComposition.setInfants(reservation.getInfant() == null ? 0 : reservation.getInfant());
		String fromDate = DF.format(reservation.getFromdate());
		String toDate = DF.format(reservation.getTodate());
		
		rq.setGuestsComposition(guestComposition);
//		rq.setFromDate(fromDate);
//		rq.setToDate(toDate);
//		rq.setFirstName(reservation.getCustomername());
//		rq.set
		
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
		throw new ServiceException(Error.not_implemented, "this service is not supported by this PMS");		
	}

	@Override
	public void updateReservation(SqlSession sqlSession, Reservation reservation) {
	}

	@Override
	public void cancelReservation(SqlSession sqlSession, Reservation reservation) {
	}

	@Override
	public void readAlerts() {
		throw new ServiceException(Error.not_implemented, "this service is not supported by this PMS");
	}

	@Override
	public void readPrices() {
	}

	@Override
	public void readProducts() {
	}

	@Override
	public void readSchedule() {
	}

	@Override
	public void readSpecials() {
		throw new ServiceException(Error.not_implemented, "this service is not supported by this PMS");
	}

	@Override
	public void readDescriptions() {
		throw new ServiceException(Error.not_implemented, "this service is not supported by this PMS");
	}

	@Override
	public void readImages() {
	}

	@Override
	public void readAdditionCosts() {
		throw new ServiceException(Error.not_implemented, "this service is not supported by this PMS");
		
	}

}
