package net.cbtltd.rest;

import java.util.Map;

import net.cbtltd.rest.response.PartyResponse;
import net.cbtltd.rest.response.PendingPaymentResponse;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.ManagerToGatewayMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.PaymentTransactionMapper;
import net.cbtltd.server.api.PendingTransactionMapper;
import net.cbtltd.server.api.ProductMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.server.api.PropertyManagerSupportCCMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.util.PaymentHelper;
import net.cbtltd.server.util.PendingTransactionStatus;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.ManagerToGateway;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.PaymentGatewayProvider;
import net.cbtltd.shared.PaymentTransaction;
import net.cbtltd.shared.PendingTransaction;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.PropertyManagerSupportCC;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.finance.gateway.CreditCard;
import net.cbtltd.shared.finance.gateway.CreditCardType;
import net.cbtltd.shared.finance.gateway.PaymentGatewayHolder;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public abstract class AbstractPayment {

	private static final Logger LOG = Logger.getLogger(AbstractPayment.class.getName());
	
	protected static PartyResponse pendingPaymentCustomerInfo(String reservationPos) {
		SqlSession sqlSession = null;
		PartyResponse response = new PartyResponse(); 
		try{
			sqlSession = RazorServer.openSession();
	
			if(reservationPos == null) {
				LOG.error("ReservationId parameter is missing in request");
				throw new ServiceException(Error.parameter_absent, "reservationId");
			}
			
			String reservationId = Model.decrypt(reservationPos);
			Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(reservationId);
			if(reservation == null) {
				LOG.error("Cannot find reservation in database with id :[" + reservationId +"]");
				throw new ServiceException(Error.database_cannot_find, "reservation");
			}
			PendingTransaction pendingTransaction = sqlSession.getMapper(PendingTransactionMapper.class).readByReservation(reservation);
			if(pendingTransaction == null) {
				LOG.error("There are no pending transactions related to this reservation!");
				throw new ServiceException(Error.database_cannot_find, " pending transactions related to current reservation");
			}
			Party customer = sqlSession.getMapper(PartyMapper.class).read(reservation.getCustomerid());
			response.setAdditionalPartyInfo(customer);
			if(customer == null) {
				LOG.warn("Cannot find any customer related to this reservation: [" + reservationId + "]");
			}
			Integer supplierId = Integer.valueOf(reservation.getSupplierid());
			PropertyManagerSupportCC propertyManagerSupportCC = initializeSupportCC(sqlSession, supplierId);
			response.setFirstName(customer.getFirstName());
			response.setLastName(customer.getFamilyName());
			response.setSupportCc(propertyManagerSupportCC);
			response.setAmountToCharge(PaymentHelper.getAmountWithTwoDecimals(pendingTransaction.getChargeAmount()));
			if(pendingTransaction.getStatus() != PendingTransactionStatus.Active.status()) {
				LOG.error("Transaction was already charged!");
				throw new ServiceException(Error.transaction_charged);
			}
		} catch(Exception ex) {
			LOG.error("Something went wrong while getting customer info");
			response.setErrorMessage(ex.getMessage());
		} finally {
			sqlSession.close();
		}
		return response;
	}
	
	protected static PendingPaymentResponse chragePendingPayment(String reservationPos, String firstName, String lastName, String cardNumber, String cardExpiryMonth, String cardExpiryYear,
			String amount, String currency,	Integer cardType, Integer cvc) {
		SqlSession sqlSession = null;
		PendingPaymentResponse response = new PendingPaymentResponse();
		PaymentTransaction paymentTransaction = null;
		response.setSuccessful(false);
		try {
			sqlSession = RazorServer.openSession();
			if(reservationPos == null) {
				throw new ServiceException(Error.parameter_absent, "reservationId");
			}
			
			String reservationId = Model.decrypt(reservationPos);
			Reservation reservation = sqlSession.getMapper(ReservationMapper.class).read(reservationId);
			if(reservation == null) {
				throw new ServiceException(Error.database_cannot_find, "reservation");
			}
			
			double roundedAmount = PaymentHelper.roundAmountTwoDecimals(amount);
			PendingTransaction pendingTransaction = sqlSession.getMapper(PendingTransactionMapper.class).readByReservation(reservation);
			double roundedPendingPaymentChargeAmount = PaymentHelper.getAmountWithTwoDecimals(pendingTransaction.getChargeAmount());
			if(roundedPendingPaymentChargeAmount != roundedAmount) {
				throw new ServiceException(Error.price_not_match, "database: " + roundedPendingPaymentChargeAmount + pendingTransaction.getCurrency() + ", UI: " + amount + currency);
			}
			
			int supplierId = Integer.valueOf(reservation.getSupplierid());
			ManagerToGateway managerToGateway = sqlSession.getMapper(ManagerToGatewayMapper.class).readBySupplierId(supplierId);
			PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(supplierId);
			int paymentGatewayId = managerToGateway.getPaymentGatewayId();
			PaymentGatewayProvider paymentGatewayProvider = PaymentGatewayHolder.getPaymentGateway(paymentGatewayId);
			CreditCard creditCard = initializeCreditCard(firstName, lastName, cardNumber, cardExpiryMonth, cardExpiryYear, cardType, cvc);
			GatewayHandler handler = PaymentHelper.initializeHandler(propertyManagerInfo, managerToGateway, creditCard);
			Map<String, String> resultMap = handler.createPaymentByCreditCard(sqlSession, currency, roundedAmount, reservationId);
			Product product = sqlSession.getMapper(ProductMapper.class).read(reservation.getProductid());
			
			paymentTransaction = PaymentHelper.preparePaymentTransaction(sqlSession, reservation, pendingTransaction);
			
			sqlSession.getMapper(PaymentTransactionMapper.class).create(paymentTransaction);
			pendingTransaction.setStatus(PendingTransactionStatus.Cleared.status());
			sqlSession.getMapper(PendingTransactionMapper.class).update(pendingTransaction);
			response.setSuccessful(true);
			sqlSession.commit();
		} catch (Exception e) {
			LOG.error("Something went wrong while charging pending transaction");
			response.setErrorMessage(e.getMessage());
			sqlSession.rollback();
		} finally {
			sqlSession.close();
		}
		return response;
	}
	
	private static CreditCard initializeCreditCard(String firstName, String lastName, String cardNumber, String cardExpiryMonth, String cardExpiryYear,  
			Integer cardType, Integer cvc) {
		CreditCardType creditCardType = CreditCardType.get(cardType);
		CreditCard creditCard = new CreditCard();
		creditCard.setFirstName(firstName);
		creditCard.setLastName(lastName);
		creditCard.setMonth(cardExpiryMonth);
		creditCard.setNumber(cardNumber);
		creditCard.setSecurityCode(cvc.toString());
		creditCard.setType(creditCardType);
		creditCard.setYear(cardExpiryYear);
		
		return creditCard;
	}
	
	private static PropertyManagerSupportCC initializeSupportCC(SqlSession sqlSession, int supplierId) {
		PropertyManagerSupportCC propertyManagerSupportCC = sqlSession.getMapper(PropertyManagerSupportCCMapper.class).readbypartyid(supplierId);
		
		if(propertyManagerSupportCC == null) { 			// set all credit card types to false in case of inability to find the PropertyManagerSupportCC in DB 
			propertyManagerSupportCC = new PropertyManagerSupportCC();
			propertyManagerSupportCC.setNone(false);
			propertyManagerSupportCC.setPartyId(supplierId);
			propertyManagerSupportCC.setSupportAE(false);
			propertyManagerSupportCC.setSupportDINERSCLUB(false);
			propertyManagerSupportCC.setSupportDISCOVER(false);
			propertyManagerSupportCC.setSupportJCB(false);
			propertyManagerSupportCC.setSupportMC(false);
			propertyManagerSupportCC.setSupportVISA(false);
			LOG.error("cannot find propertyManagerSupportCC for PM [" + supplierId + "] in database");
		}
		
		return propertyManagerSupportCC;
	}
}
