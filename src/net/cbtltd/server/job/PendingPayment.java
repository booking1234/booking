package net.cbtltd.server.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.server.EmailService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ReservationService;
import net.cbtltd.server.api.ManagerToGatewayMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PaymentGatewayProviderMapper;
import net.cbtltd.server.api.PaymentTransactionMapper;
import net.cbtltd.server.api.PendingTransactionMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.util.PaymentHelper;
import net.cbtltd.server.util.PendingTransactionStatus;
import net.cbtltd.shared.ManagerToGateway;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.PaymentGatewayProvider;
import net.cbtltd.shared.PaymentTransaction;
import net.cbtltd.shared.PendingTransaction;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.finance.gateway.PaymentGatewayHolder;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class PendingPayment {

	private static final Logger LOG = Logger.getLogger(PendingPayment.class.getName());
	
	public static void execute() {
		LOG.debug("PendingPaymentService processing pending transactions");
		SqlSession sqlSession = RazorServer.openSession();
		boolean successfulPayment = false;
		PaymentTransaction paymentTransaction = null;
		List<PendingTransaction> failedPendingTransactions = new ArrayList<PendingTransaction>();
		List<PendingTransaction> pendingTransactions = new ArrayList<PendingTransaction>();
		try {
			pendingTransactions = sqlSession.getMapper(PendingTransactionMapper.class).readActiveTransactionsByDate(new Date());
			LOG.debug(pendingTransactions.size());
			
			for(PendingTransaction pendingTransaction : pendingTransactions) {
				PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(pendingTransaction.getSupplierId());
				Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(pendingTransaction.getBookingId());
				String chargeType = PaymentHelper.getChargeType(propertyManagerInfo, reservation);
				if(PaymentHelper.isApiPaymentMethod(chargeType)) {
					paymentTransaction = processApiPendingPayment(sqlSession, pendingTransaction, failedPendingTransactions);
				} else {
					paymentTransaction = processLocalPendingPayment(sqlSession, pendingTransaction, failedPendingTransactions);					
				}
			}
		} 
		catch (Throwable x) {
			sqlSession.rollback(); 
			LOG.error("Error!", x);
		}
		finally {sqlSession.close();}
		
		if(successfulPayment || pendingTransactions.size() <= 0) {
			return;
		}
		
		try {
			sqlSession = RazorServer.openSession();
			paymentTransaction.setStatus(GatewayHandler.FAILED);
			for (PendingTransaction failedPendingTransaction : failedPendingTransactions) {
				failedPendingTransaction.setStatus(PendingTransactionStatus.Failed.status());
				sqlSession.getMapper(PendingTransactionMapper.class).update(failedPendingTransaction);
			}
			sqlSession.commit();
		} catch (Exception e) {
			sqlSession.rollback();
			LOG.error("Error!", e);
		} finally {
			sqlSession.close();
		}
	}

	private static PaymentTransaction processLocalPendingPayment(SqlSession sqlSession, PendingTransaction pendingTransaction,
			List<PendingTransaction> failedPendingTransactions)	throws InterruptedException {
		PaymentTransaction paymentTransaction;
		PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(pendingTransaction.getSupplierId());
		ManagerToGateway managerToGateway = sqlSession.getMapper(ManagerToGatewayMapper.class).readBySupplierId(pendingTransaction.getSupplierId());
		CreditCard creditCard = new CreditCard();
		creditCard.setNumber(String.valueOf(pendingTransaction.getPartialIin()));
		GatewayHandler gatewayHandler = PaymentHelper.initializeHandler(propertyManagerInfo, managerToGateway, creditCard);
		PaymentGatewayProvider paymentGatewayProvider = sqlSession.getMapper(PaymentGatewayProviderMapper.class).read(managerToGateway.getPaymentGatewayId());
		Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(pendingTransaction.getBookingId());
		Map<String, String> result = null;
		if(paymentGatewayProvider.getName().equals(PaymentGatewayHolder.PAYPAL) || paymentGatewayProvider.getName().equals(PaymentGatewayHolder.DIBS)) {
			Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
			String currency = pendingTransaction.getCurrency();
			Double chargeAmount = pendingTransaction.getChargeAmount();
			EmailService.secondPaymentNotification(customer, currency, chargeAmount);
		} else {
			result = gatewayHandler.createPaymentByProfile(sqlSession, pendingTransaction.getCurrency(), pendingTransaction.getChargeAmount(), Integer.valueOf(pendingTransaction.getBookingId()));
		}
		
		paymentTransaction = PaymentHelper.preparePaymentTransaction(sqlSession, reservation, pendingTransaction);
		
		if(result != null) {
		    paymentTransaction.setStatus(result.get(GatewayHandler.STATE));
			paymentTransaction.setGatewayTransactionId(result.get(GatewayHandler.TRANSACTION_ID));
			paymentTransaction.setMessage(result.get(GatewayHandler.ERROR_MSG));
			
			if (paymentTransaction.getStatus().equals(GatewayHandler.ACCEPTED)){
			    reservation.setState(Reservation.State.FullyPaid.name());
			}
			
			setPendingTransactionStatus(pendingTransaction, result);
			
		} else if(paymentGatewayProvider.getName().equals(PaymentGatewayHolder.PAYPAL) || paymentGatewayProvider.getName().equals(PaymentGatewayHolder.DIBS)) {
			LOG.warn("This pending transaction [" + pendingTransaction.getId() + "] only sent an email! Please improve me! (it should be related to PayPal or DIBS)");
			return paymentTransaction;
		} else {
			failedPendingTransactions.add(pendingTransaction);
		}

		
		ReservationService.createEvent(sqlSession, paymentTransaction, reservation, creditCard);

		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		sqlSession.getMapper(PendingTransactionMapper.class).update(pendingTransaction);
		sqlSession.getMapper(PaymentTransactionMapper.class).create(paymentTransaction);
		sqlSession.commit();
//				successfulPayment = true;
		sendPendingEmails(sqlSession, pendingTransaction, result);
		if(!paymentTransaction.getStatus().equals(GatewayHandler.ACCEPTED)) {
			paymentTransaction.setStatus(GatewayHandler.FAILED);
			sqlSession.getMapper(PaymentTransactionMapper.class).update(paymentTransaction);
			EmailService.failedPaymentTransactionToAdmin(sqlSession, paymentTransaction);
		}
		return paymentTransaction;
	}
	
	private static PaymentTransaction processApiPendingPayment(SqlSession sqlSession, PendingTransaction pendingTransaction,
			List<PendingTransaction> failedPendingTransactions)	throws InterruptedException {
		PaymentTransaction paymentTransaction;
		Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(pendingTransaction.getBookingId());
		Map<String, String> result = new HashMap<String, String>();
		result = initializeResultMap();
		result.put(GatewayHandler.STATE, GatewayHandler.ACCEPTED);
		Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
		paymentTransaction = PaymentHelper.preparePaymentTransaction(sqlSession, reservation, pendingTransaction);
	    paymentTransaction.setStatus(GatewayHandler.ACCEPTED);
	    reservation.setState(Reservation.State.FullyPaid.name());
		pendingTransaction.setStatus(PendingTransactionStatus.Cleared.status());
		ReservationService.createEvent(sqlSession, paymentTransaction, reservation, customer.getFamilyName() + "," + customer.getFirstName());

		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		sqlSession.getMapper(PendingTransactionMapper.class).update(pendingTransaction);
		sqlSession.getMapper(PaymentTransactionMapper.class).create(paymentTransaction);
		sqlSession.commit();
		return paymentTransaction;
	}
	
	private static void setPendingTransactionStatus(PendingTransaction pendingTransaction, Map<String, String> result) {
		if(result.get(GatewayHandler.STATE).equals(GatewayHandler.ACCEPTED)) {
			pendingTransaction.setStatus(PendingTransactionStatus.Cleared.status());
		} else {
			pendingTransaction.setStatus(PendingTransactionStatus.Failed.status());
		}
	}
	
	private static void sendPendingEmails(SqlSession sqlSession, PendingTransaction pendingTransaction, Map<String, String> result) throws InterruptedException {
		EmailService.sendPMPendingPaymentEmail(sqlSession, pendingTransaction, result);
		Thread.sleep(5000);
		EmailService.sendRenterPendingPaymentEmail(sqlSession, pendingTransaction, result);
	}
	
	private static Map<String, String> initializeResultMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(GatewayHandler.TRANSACTION_ID, null);
		map.put(GatewayHandler.ERROR_MSG, null);
		map.put(GatewayHandler.STATE, null);
		
		return map;
	}
}
