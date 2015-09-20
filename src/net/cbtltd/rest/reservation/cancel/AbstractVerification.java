package net.cbtltd.rest.reservation.cancel;

import java.text.ParseException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.rest.reservation.CreateReservationContent;
import net.cbtltd.server.EmailService;
import net.cbtltd.server.ReservationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.ManagerToGatewayMapper;
import net.cbtltd.server.api.PendingTransactionMapper;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.util.PaymentHelper;
import net.cbtltd.server.util.PendingTransactionStatus;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.ManagerToGateway;
import net.cbtltd.shared.PaymentTransaction;
import net.cbtltd.shared.PendingTransaction;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.finance.gateway.CreditCard;

public abstract class AbstractVerification implements Verifiable {
	
	protected static void cancelPendingTransactions(SqlSession sqlSession, Reservation reservation) {
		PendingTransactionMapper mapper = sqlSession.getMapper(PendingTransactionMapper.class);
		PendingTransaction pendingTransaction = mapper.readByReservation(reservation);
		if(pendingTransaction == null) {
			return;
		}
		pendingTransaction.setStatus(PendingTransactionStatus.Cancelled.status());
		mapper.update(pendingTransaction);
		sqlSession.commit();
	}
	
	protected static void refundAmount(SqlSession sqlSession, Integer propertyManagerId, List<PaymentTransaction> paymentTransactions, Double amount) throws Exception {
		if(paymentTransactions == null || paymentTransactions.isEmpty()) {
			throw new ServiceException(Error.database_cannot_find, "payment transaction for refund");
		}
		
		CreditCard creditCard = new CreditCard();
		creditCard.setNumber(String.valueOf(paymentTransactions.get(0).getPartialIin()));
		
		PropertyManagerInfo propertyManagerInfo = sqlSession.getMapper(PropertyManagerInfoMapper.class).readbypmid(propertyManagerId);
		ManagerToGateway managerToGateway = sqlSession.getMapper(ManagerToGatewayMapper.class).readBySupplierId(propertyManagerId);
		GatewayHandler gatewayHandler = PaymentHelper.initializeHandler(propertyManagerInfo, managerToGateway, creditCard);
		
		gatewayHandler.createRefund(paymentTransactions, amount);
	}
	
	protected static void setReservationState(Reservation reservation, String chargeType) {
		if(PaymentHelper.isFullPaymentMethod(chargeType)){
			reservation.setState(Reservation.State.FullyPaid.name());
		} else if(PaymentHelper.isDepositPaymentMethod(chargeType)) {
			reservation.setState(Reservation.State.Confirmed.name());
		} else if(PaymentHelper.MAIL_PAYMENT_METHOD.equals(chargeType)) {
			reservation.setState(Reservation.State.Reserved.name());
		} else {
			throw new ServiceException(Error.payment_method_unsupported);
		}
	}
	
	protected static void sendEmails(SqlSession sqlSession, PaymentTransaction paymentTransaction, String chargeType) throws ParseException, InterruptedException {
		EmailService.reservationPaymentToRenter(sqlSession, paymentTransaction, chargeType);
		Thread.sleep(5000); // test if second email is not sending because of delay absence
		EmailService.reservationPaymentToPropertyManager(sqlSession, paymentTransaction, chargeType);
	}
	
	protected static void finishVerification(SqlSession sqlSession, CreateReservationContent content) throws ParseException, InterruptedException {
		Reservation reservation = content.getReservation();
		PaymentTransaction paymentTransaction = content.getPaymentTransaction();
		ReservationService.createEvent(sqlSession, paymentTransaction, reservation, content.getCreditCard());
		sqlSession.getMapper(ReservationMapper.class).update(reservation);
		// Pending transaction end
		sendEmails(sqlSession, paymentTransaction, content.getChargeType());
	}

}
