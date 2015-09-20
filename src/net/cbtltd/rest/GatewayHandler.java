package net.cbtltd.rest;

import java.util.List;
import java.util.Map; 

import net.cbtltd.shared.PaymentTransaction;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.finance.gateway.CustomerPaymentProfile;

import org.apache.ibatis.session.SqlSession;

public interface GatewayHandler {
	String ERROR_MSG = "errorMessage";
	String TRANSACTION_ID = "transactionId";
	String STATE = "state";
	String DECLINED = "declined";
	String ACCEPTED = "accepted";
	String FAILED = "failed";
	String INQUIRED = "inquired";
	
	/**
	 * Charge from credit card correspondent amount.
	 * 
	 * @param sqlSession current SQL session
	 * @param currency currency to charge
	 * @param amount amount to charge
	 * @param reservationId ID of the reservation which will be user for custom setted order ID or in transaction description
	 * 
	 * @return Map with {ERROR_MSG, TRANSACTION_ID, STATE} keys which are responsible for the response from payment provider
	 * @throws Exception 
	 */
	Map<String, String> createPaymentByCreditCard(SqlSession sqlSession, String currency, Double amount, String reservationId) throws Exception;
	
//	/**
//	 * Charge from credit card correspondent amount.
//	 * 
//	 * @param currency currency to charge
//	 * @param amount amount to charge
//	 * @param orderId manually setted ID for this particular order. This is needed for several payment gateways (e.g. DIBS)
//	 * @return Map with {ERROR_MSG, TRANSACTION_ID, STATE} keys which are responsible for the response from payment provider
//	 * @throws Exception
//	 */
//	Map<String, String> createPaymentByCreditCard(String currency, Double amount, String orderId) throws Exception;
	
	/**
	 * Create refund to a customer from a passed transaction.
	 * 
	 * @param paymentTransactions transactions that should be refunded
	 * @param refundAmount amount to be refunded
	 * @return Map with {ERROR_MSG, TRANSACTION_ID, STATE} keys which are responsible for the response from payment provider
	 * @throws Exception
	 */
	Map<String, String> createRefund(List<PaymentTransaction> paymentTransactions, Double refundAmount) throws Exception;
	
	/**
	 * Charge passed amount with already created payment profile.
	 * 
	 * @param sqlSession current SQL session
	 * @param currency currency for payment
	 * @param amount amount to be charged
	 * @param reservationId ID of reservation by which customer payment profile would be got
	 * @return Map with {ERROR_MSG, TRANSACTION_ID, STATE} keys which are responsible for the response from payment provider
	 */
	Map<String, String> createPaymentByProfile(SqlSession sqlSession, String currency, Double amount, Integer reservationId);
	
	/**
	 * Create customer payment profile on the Payment Gateway side for further transactions.
	 * Method should store all information what was got from gateway to the database ({@link CustomerPaymentProfile}) for further
	 * transaction usage.
	 * 
	 * @param sqlSession current SQL session
	 * @param reservationId reservation ID that will be used for created payment profile footprint
	 * @param creditCard profile credit card
	 * @return map with transaction statuses
	 */
	Map<String, String> createCustomerPaymentProfile(SqlSession sqlSession, Integer reservationId, CreditCard creditCard, Integer gatewayId);
	
	/**
	 * This method is used to authorize payment. It is need to check if credit card has necessary amount, proper credit card number etc.
	 * 
	 * @return result map
	 */
	Map<String, String> authorizePayment(SqlSession sqlSession, String currency, Double amount, Integer reservationId);
	
	/**
	 * This method captures (charges) authorized payment.
	 * @return result map
	 */
	Map<String, String> capturePayment(SqlSession sqlSession, String currency, Integer centAmount, Integer reservationId, String transactionId);
}
