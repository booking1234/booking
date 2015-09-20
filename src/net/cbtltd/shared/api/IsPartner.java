/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.api;

import java.util.Map;

import net.cbtltd.rest.payment.ReservationPrice;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.finance.gateway.CreditCard;

import org.apache.ibatis.session.SqlSession;

/**
 * The Interface IsForeign is implemented by foreign API handlers to import and export data.
 */
public interface IsPartner {
	enum API {Alert, Price, Product, Schedule, Special}
	int TEXT_NOTES_LENGTH = 20000;
	int PRICE_BATCH_SIZE  = 200;
	void start(API service);
	void stop(API service);
	boolean stopped(API service);

	Double getCommission();
	Double getDiscount();
	String getAltpartyid();
	String getApikey();
	String getCurrency();
	String getWebaddress();
//	void createProduct(SqlSession sqlSession, Product product);
	
	// will be called from platform api
	boolean isAvailable(SqlSession sqlSession, Reservation reservation);
	void createReservation(SqlSession sqlSession, Reservation reservation);
	ReservationPrice readPrice(SqlSession sqlSession, Reservation reservation, String productAltId, String currency);

	/**
	 * Create reservation and payment.
	 *
	 * @param sqlSession the current SQL session.
	 * @param reservation the reservation to be created.
	 * @param creditCard credit card for payment processing.
	 * @param product the product to be reserved.
	 * 
	 * @return result map with parsed response from PMS API
	 */
	Map<String, String> createReservationAndPayment(SqlSession sqlSession, Reservation reservation, CreditCard creditCard);
	
	/**
	 * Inquire reservation
	 * 
	 * @param sqlSession current SQL session.
	 * @param reservation reservation to be created.
	 */
	void inquireReservation(SqlSession sqlSession, Reservation reservation);
	void confirmReservation(SqlSession sqlSession, Reservation reservation);
	void readReservation(SqlSession sqlSession, Reservation reservation);
	void updateReservation(SqlSession sqlSession, Reservation reservation);
	void cancelReservation(SqlSession sqlSession, Reservation reservation);
	
	/**
	 * set of methods to retrieve inventory from the supplier
	 */
	void readAlerts();
	void readPrices();
	void readProducts();
	void readSchedule();
	void readSpecials();
	
	void readDescriptions();
	void readImages();
	void readAdditionCosts();
}
