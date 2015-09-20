package net.cbtltd.server.job;

import java.util.Date;
import java.util.List;

import net.cbtltd.rest.Constants;
import net.cbtltd.rest.anet.ANetHandler;
import net.cbtltd.rest.paypal.PaypalAccount;
import net.cbtltd.rest.paypal.PaypalHandlerNew;
import net.cbtltd.server.PaymentService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PaymentMethodMapper;
import net.cbtltd.server.api.PaymentRegisterMapper;
import net.cbtltd.server.api.PaymentTransactionMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.PaymentMethod;
import net.cbtltd.shared.PaymentTransaction;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.api.HasUrls;
import net.cbtltd.shared.finance.gateway.BankAccountInfo;
import net.cbtltd.shared.finance.gateway.GatewayInfo;
import net.cbtltd.shared.finance.gateway.PaymentProcessingTypeEnum;
import net.cbtltd.shared.finance.gateway.PaymentRegister;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class PropertyManagerPayment {
private static final Logger LOG = Logger.getLogger(PendingPayment.class.getName());
	
	public static void execute() {
		LOG.debug("PropertyManagerPayment processing payments from BookingPal to property managers");
		SqlSession sqlSession = RazorServer.openSession();
		Date currentDate = new Date();
		try {
			bookingPalFundsHolder(sqlSession, currentDate);
			propertyManagerFundsHolder(sqlSession, currentDate);
			splittedFunds(sqlSession, currentDate);
			noneHolder(sqlSession, currentDate);
		} 
		catch (Throwable x) {sqlSession.rollback(); LOG.error(x.getMessage());}
		finally {sqlSession.close();}
	}
	
	private static void bookingPalFundsHolder(SqlSession sqlSession, Date date) throws Exception {
		NameIdAction action = new NameIdAction();
		action.setId("1"); // funds_holder
		action.setVersion(date);
		// find all transactions within the last day where funds holder is BookingPal
		List<PaymentTransaction> paymentTransactions = sqlSession.getMapper(PaymentTransactionMapper.class).listLast24hours(action);
		for(PaymentTransaction paymentTransaction : paymentTransactions) {
			PaymentProcessingTypeEnum paymentMethod = PaymentProcessingTypeEnum.getByInt(paymentTransaction.getPaymentMethod());
			
			switch(paymentMethod) {
			case Mail : throw new ServiceException(Error.not_implemented, "Mail");
			case ACH : eCheckPayment(sqlSession, paymentTransaction);
			case GATEWAY : gatewayPayment(sqlSession, paymentTransaction);
			// if payment type = ACH
				// charge payment
				// insert new record to payment_transaction table, cleared = false, type = 1-ACH
			// if payment type = PayPal
				// charge payment
				// insert new record to payment_transaction table, type = 2-PayPal transfer
			// if payment type = mail
				// insert new record to payment_transaction table, cleared = false, type = Mail
			}
		}
	}
	
	private static void gatewayPayment(SqlSession sqlSession, PaymentTransaction paymentTransaction) throws Exception {
		double amountToCharge = paymentTransaction.getFinalAmount() - paymentTransaction.getTotalBookingpalPayment();
		Party pm = sqlSession.getMapper(PartyMapper.class).read(String.valueOf(paymentTransaction.getSupplierId()));
		amountToCharge = PaymentService.convertCurrency(sqlSession, paymentTransaction.getCurrency(), pm.getCurrency(), amountToCharge);
		PaypalAccount account = new PaypalAccount();
//		account.set
		PaypalHandlerNew.accountToAccountPayment(pm.getEmailaddress(), HasUrls.MYBOOKINGPAL_PAYPAL_EMAIL, null /* TODO */, amountToCharge, pm.getCurrency());
		
		PaymentRegister paymentRegister = initializePaymentRegister(sqlSession, paymentTransaction, 2, true);
		sqlSession.getMapper(PaymentRegisterMapper.class).create(paymentRegister);
	}
	
	private static void eCheckPayment(SqlSession sqlSession, PaymentTransaction paymentTransaction) {
		double amountToCharge = paymentTransaction.getFinalAmount() - paymentTransaction.getTotalBookingpalPayment();
		Party pm = sqlSession.getMapper(PartyMapper.class).read(String.valueOf(paymentTransaction.getSupplierId()));
		amountToCharge = PaymentService.convertCurrency(sqlSession, paymentTransaction.getCurrency(), pm.getCurrency(), amountToCharge);
		PaymentMethod paymentMethod = sqlSession.getMapper(PaymentMethodMapper.class).read_by_pm(paymentTransaction.getSupplierId());
		BankAccountInfo bankAccountInfo = new BankAccountInfo();
		// set up bank account info parameters
		Integer reservationId = paymentTransaction.getReservationId();
		GatewayInfo gatewayInfo = new GatewayInfo();
		gatewayInfo.setId(Constants.ANET_BOOKINGPAL_ACC_ID);
		gatewayInfo.setToken(Constants.ANET_BOOKINGPAL_TRANSACTION_KEY);
		ANetHandler handler = new ANetHandler(gatewayInfo);
		handler.createBankPaymentProfile(sqlSession, reservationId, bankAccountInfo, Constants.ANET_BOOKINGPAL_ACC_ID, Constants.ANET_BOOKINGPAL_TRANSACTION_KEY);
		handler.createPaymentByProfile(sqlSession, pm.getCurrency(), amountToCharge, reservationId);
	}
	
	private static void propertyManagerFundsHolder(SqlSession sqlSession, Date date) {
		NameIdAction action = new NameIdAction();
		action.setId("0");
		action.setVersion(date);
		// find all transactions within the last day where funds holder is BookingPal
		List<PaymentTransaction> paymentTransactions = sqlSession.getMapper(PaymentTransactionMapper.class).listLast24hours(action);
		
//		Double transactionsSum = 0.;
		for(PaymentTransaction paymentTransaction : paymentTransactions) {
			// Set the Type to 4-Invoice and Set Cleared = False
			PaymentRegister paymentRegister = initializePaymentRegister(sqlSession, paymentTransaction, 4, false);
			sqlSession.getMapper(PaymentRegisterMapper.class).create(paymentRegister);
		}
	}
	
	private static void splittedFunds(SqlSession sqlSession, Date date) {
		NameIdAction action = new NameIdAction();
		action.setId("2");
		action.setVersion(date);
		// find all transactions within the last day where funds holder is BookingPal
		List<PaymentTransaction> paymentTransactions = sqlSession.getMapper(PaymentTransactionMapper.class).listLast24hours(action);
		for(PaymentTransaction paymentTransaction : paymentTransactions) {
			// Set the Type to 5-Auto Distributed and Set Cleared = True
			PaymentRegister paymentRegister = initializePaymentRegister(sqlSession, paymentTransaction, 5, true);
			sqlSession.getMapper(PaymentRegisterMapper.class).create(paymentRegister);
		}
	}
	
	private static void noneHolder(SqlSession sqlSession, Date date) {
		NameIdAction action = new NameIdAction();
		action.setId("3");
		action.setVersion(date);
		// find all transactions within the last day where funds holder is BookingPal
		List<PaymentTransaction> paymentTransactions = sqlSession.getMapper(PaymentTransactionMapper.class).listLast24hours(action);
		for(PaymentTransaction paymentTransaction : paymentTransactions) {
			// Set the Type to 6-Invoice Partner and Set Cleared = False
			PaymentRegister paymentRegister = initializePaymentRegister(sqlSession, paymentTransaction, 6, false);
			sqlSession.getMapper(PaymentRegisterMapper.class).create(paymentRegister);
		}
	}
	
	private static PaymentRegister initializePaymentRegister(SqlSession sqlSession, PaymentTransaction paymentTransaction, Integer type, boolean cleared) {
		Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(String.valueOf(paymentTransaction.getReservationId()));
		Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
		
		PaymentRegister paymentRegister = new PaymentRegister();
		paymentRegister.setCleared(cleared);
		paymentRegister.setEntryDateTime(new Date());
		paymentRegister.setPartnerId(paymentTransaction.getPartnerId());
		paymentRegister.setPaymentTransactionId(paymentTransaction.getId());
		paymentRegister.setPmId(paymentTransaction.getSupplierId());
		paymentRegister.setPropertyId(Integer.valueOf(product.getId()));
		paymentRegister.setReservationId(Integer.valueOf(reservation.getId()));
		paymentRegister.setType(type);
		
		return paymentRegister;
	}
}
