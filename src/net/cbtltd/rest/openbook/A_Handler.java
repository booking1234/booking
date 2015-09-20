package net.cbtltd.rest.openbook;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.rest.response.QuoteDetail;
import net.cbtltd.server.MonitorService;
import net.cbtltd.server.PartnerHandler;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.TextService;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PriceMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.KeyValue;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.soap.ota.server.ObjectFactory;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
/** 
 * Class A_Handler manages the Openbook API
 * 
 * @see http://cxf.apache.org/docs/jax-rs-client-api.html
 */

public class A_Handler extends PartnerHandler implements IsPartner {

	protected static final ObjectFactory OF = new ObjectFactory();
	private static final Logger LOG = Logger.getLogger(A_Handler.class.getName());

	/**
	 * Instantiates a new partner handler.
	 *
	 * @param sqlSession the current SQL session.
	 * @param partner the partner.
	 */
	public A_Handler (Partner partner) {super(partner);}
	
	/**
	 * Returns collisions with the reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation for collisions
	 * @return list of collisions
	 */
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "Openbook isAvailable " + reservation.getId();
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, message); //TODO:
		}
		catch (Throwable x) {LOG.error(message + x.getMessage());} 
		MonitorService.monitor(message, timestamp);
		return true;
	}

	/**
	 * Create reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be created.
	 * @param product the product to be reserved.
	 */
	public void createReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		boolean swapped = false;
		String message = "Openbook createReservation " + reservation;
		LOG.debug(message);
		try {
			reservation = sqlSession.getMapper(ReservationMapper.class).read(reservation.getId());
			if (reservation.noCustomerid()) {throw new ServiceException(Error.party_id, "no customer");}
			Party party = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
			if (party == null) {throw new ServiceException(Error.party_id, reservation.getCustomerid());}
			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
			if (product == null) {throw new ServiceException(Error.product_id, reservation.getProductid());}

			Party actor = reservation.noActorid() ? null : sqlSession.getMapper(PartyMapper.class).read(reservation.getActorid());
			Party agent = reservation.noAgentid() ? null : sqlSession.getMapper(PartyMapper.class).read(reservation.getAgentid());
			Party customer = reservation.noCustomerid() ? null : sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
			Party supplier = product.noSupplierid() ? null : sqlSession.getMapper(PartyMapper.class).read(product.getSupplierid());

			reservation.setProduct(product);
			reservation.setActor(actor);
			reservation.setAgent(agent);
			reservation.setCustomer(customer);
			reservation.setSupplier(supplier);
			ArrayList<net.cbtltd.shared.Price> quotedetail = sqlSession.getMapper(PriceMapper.class).quotedetail(reservation.getId());
			reservation.setQuotedetail(quotedetail);

			ArrayList<String> keyvalues = RelationService.read(sqlSession, Relation.RESERVATION_VALUE, reservation.getId(), null);
			if (keyvalues != null) {
				ArrayList<KeyValue> item = new ArrayList<KeyValue>();
				for (String keyvalue : keyvalues) {
					String[] args = keyvalue.split(Model.DELIMITER);
					if (args.length == 2) {item.add(new KeyValue(args[0], args[1]));}
				}
				reservation.setKeyvalues(item);
			}

			// String URL = "http://www.skywaresystems.net:5100/Login.aspx?PropertyRowID=108&HotelID=1&ActType=OTA";
			// String URL = "http://www.skywaresystems.net:5100/Interface.aspx?PropertyRowID=108&HotelID=1&ActType=OTA";
			String URL = TextService.notes(sqlSession, NameId.Type.Party.name() + product.getSupplierid() + Text.Code.Url.name(), Language.EN);
			if (URL == null) {throw new ServiceException(Error.text_id, NameId.Type.Party.name() + product.getSupplierid() + Text.Code.Url.name());}
			WebClient client = WebClient.create(URL);
			client.path(URL).type(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML);
			reservation = swapNameId(reservation);
			swapped = true;
			client.post(reservation);
//TODO:			Reservation response = client.post(reservation, Reservation.class);
//			if (response == null || response.noAltid()) {throw new ServiceException(Error.reservation_api, "Invalid Response");}
//			else {reservation.setAltid(response.getAltid());}
			LOG.error(message + "\n" + keyvalues + "\n" + reservation);
		}
		catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			reservation.setState(Reservation.State.Cancelled.name());
			if(swapped) {
				reservation = swapNameId(reservation);
				swapped = false;
			}
			sqlSession.getMapper(ReservationMapper.class).update(reservation);
			LOG.error(reservation.getMessage());
		}
		MonitorService.monitor(message, timestamp);
	}

	// This is needed to match Openbook requirements
	private Reservation swapNameId(Reservation reservation) {
		String name = reservation.getName();
		String id = reservation.getId();
		reservation.setName(id);
		reservation.setId(name);
		return reservation;
	}
	
	/**
	 * Read reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be read.
	 */
	public void readReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "readReservation altid " + reservation.getAltid();
		LOG.debug(message);
		try {
			LOG.debug("readReservation " + reservation);
			throw new ServiceException(Error.service_absent, "Openbook readReservation()");
		}
		catch (Throwable x) {x.getMessage();} 
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Update reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be updated.
	 */
	public void updateReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "updateReservation " + reservation.getId();
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, "Openbook updateReservation()");
		}
		catch (Throwable x) {x.getMessage();} 
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Confirm reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be confirmed.
	 */
	public void confirmReservation(SqlSession sqlSession, Reservation reservation) {
		//DO NOTHING
	}

	/**
	 * Cancel reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be cancelled.
	 */
	public void cancelReservation(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "cancelReservation " + reservation.getId();
		LOG.debug(message);
		try {
			reservation = sqlSession.getMapper(ReservationMapper.class).read(reservation.getId());
			reservation.setState(Reservation.State.Cancelled.name());
			String URL = "http://www.skywaresystems.net:5100/Interface.aspx?PropertyRowID=108&HotelID=1&ActType=OTA";
			WebClient client = WebClient.create(URL);
//GET			Reservation result = client.path("bookstore/books").accept("text/xml").get(Reservation.class);
//POST
//			client.path(URL).type("text/xml").accept("text/xml");
			client.path(URL).type(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML);
			client.post(reservation);
		}
		catch (Throwable x) {
			reservation.setMessage(x.getMessage());
			reservation.setState(Reservation.State.Cancelled.name());
			sqlSession.getMapper(ReservationMapper.class).update(reservation);
			LOG.error(reservation.getMessage());
		}
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Transfer accommodation alerts.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readAlerts() {
		throw new ServiceException(Error.service_absent, "Openbook readAlerts()");
	}

	/**
	 * Transfer property locations.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readLocations() {
		throw new ServiceException(Error.service_absent, "Openbook readLocations()");
	}

	/**
	 * Transfer accommodation prices.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readPrices() {
		throw new ServiceException(Error.service_absent, "Openbook readPrices()");
	}

	/**
	 * Transfer accommodation products.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readProducts() {
		Date timestamp = new Date();
		String message = "readProducts ";
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, "Openbook readProducts()");
		}
		catch (Throwable x) {x.getMessage();} 
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Transfer reservation schedule.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readSchedule() {
		Date timestamp = new Date();
		String message = "readSchedule ";
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, "Openbook readSchedule()");
		}
		catch (Throwable x) {x.getMessage();} 
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Transfer accommodation special offers.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readSpecials() {
		throw new ServiceException(Error.service_absent, "Openbook readSpecials()");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> createReservationAndPayment(SqlSession sqlSession, Reservation reservation, CreditCard creditCard) {
		throw new ServiceException(Error.service_absent, "Openbook createReservationAndPayment()");
	}

	@Override
	public ReservationPrice readPrice(SqlSession sqlSession, Reservation reservation, String productAltId, String currency) {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public void inquireReservation(SqlSession sqlSession, Reservation reservation) {
		throw new ServiceException(Error.service_absent, "Openbook inquireReservation()");
	}
}

