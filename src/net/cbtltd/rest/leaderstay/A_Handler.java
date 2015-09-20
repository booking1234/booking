/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	3.00
 */
package net.cbtltd.rest.leaderstay;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.rest.response.QuoteDetail;
import net.cbtltd.rest.rtr.RTRWSAPISoap;
import net.cbtltd.server.MonitorService;
import net.cbtltd.server.PartnerHandler;
import net.cbtltd.server.RelationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Downloaded;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.api.HasPrice;
import net.cbtltd.shared.api.HasUrls;
import net.cbtltd.shared.api.IsPartner;
import net.cbtltd.shared.finance.gateway.CreditCard;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class A_Handler extends PartnerHandler implements IsPartner {
	private static final Logger LOG = Logger.getLogger(A_Handler.class.getName());
	private static final QName SERVICE_NAME = new QName("", "");
	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
	private static HashMap<String,String> AMENITIES = null;
	private static HashMap<String,String> LOCATIONS = null;
	private static HashMap<String,String> TYPES = null;

	/**
	 * Instantiates a new partner handler.
	 *
	 * @param sqlSession the current SQL session.
	 * @param partner the partner.
	 */
	public A_Handler (Partner partner) {super(partner);}

	/**
	 * Gets the port to which to connect.
	 *
	 * @return the port.
	 */
	public static final RTRWSAPISoap getPort() {
		throw new ServiceException(Error.service_absent, "Leaderstay getPort()");
	}

	/**
	 * Returns collisions with the reservation.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation for collisions
	 * @return list of collisions
	 */
	public boolean isAvailable(SqlSession sqlSession, Reservation reservation) {
		Date timestamp = new Date();
		String message = "Leaderstay isAvailable " + reservation.getId();
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, message); //TODO:
		}
		catch (Throwable x) {x.getMessage();} 
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
		String message = "createReservation " + reservation.getId();
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, "Leaderstay createReservation()");
		}
		catch (Throwable x) {x.getMessage();} 
		MonitorService.monitor(message, timestamp);
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
			throw new ServiceException(Error.service_absent, "Leaderstay readReservation()");
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
			throw new ServiceException(Error.service_absent, "Leaderstay updateReservation()");
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
		String message = "cancelReservation " + reservation.getAltid();
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, "Leaderstay cancelReservation()");
		}
		catch (Throwable x) {x.getMessage();} 
		MonitorService.monitor(message, timestamp);
	}

	/**
	 * Transfer accommodation alerts.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readAlerts() {
		Date timestamp = new Date();
		String message = "Leaderstay readAlerts ";
		LOG.debug(message);
		throw new ServiceException(Error.service_absent, message); //TODO:
	}

	/**
	 * Transfer accommodation prices.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readPrices() {
		Date timestamp = new Date();
		String message = "Leaderstay readPrices ";
		LOG.debug(message);
		throw new ServiceException(Error.service_absent, message); //TODO:
	}

	/**
	 * Transfer property locations.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readLocations() {
		Date timestamp = new Date();
		String message = "Leaderstay readLocations ";
		LOG.debug(message);
		throw new ServiceException(Error.service_absent, message); //TODO:
	}

	/**
	 * Transfer accommodation products.
	 *
	 * @param sqlSession the current SQL session.
	 */
	public synchronized void readProducts() {
		Date timestamp = new Date();
		String message = "Leaderstay readProducts ";
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, message); //TODO:
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
		String message = "Leaderstay readSchedule ";
		LOG.debug(message);
		try {
			throw new ServiceException(Error.service_absent, message); //TODO:
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
		Date timestamp = new Date();
		String message = "Leaderstay readSpecials";
		LOG.debug(message);
		throw new ServiceException(Error.service_absent, message); //TODO:
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> createReservationAndPayment(SqlSession sqlSession, Reservation reservation, CreditCard creditCard) {
		throw new ServiceException(Error.service_absent, "Leaderstay createReservationAndPayment()");
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
		throw new ServiceException(Error.service_absent, "Leaderstay inquireReservation()");
	}
}
