package net.cbtltd.rest.reservation.payment;

import java.text.ParseException;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import net.cbtltd.rest.reservation.CreateReservationContent;
import net.cbtltd.rest.response.ReservationInfo;
import net.cbtltd.rest.response.ReservationResponse;
import net.cbtltd.server.EmailService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.server.util.PaymentHelper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.PaymentTransaction;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.Reservation;

public class MailPayment extends AbstractPayment implements Payable {

	@Override
	public void pay(CreateReservationContent content) throws Exception {
		SqlSession sqlSession = RazorServer.openSession();
		ReservationResponse response = content.getReservationResponse();
		Reservation reservation = content.getReservation();
		PropertyManagerInfo propertyManagerInfo = content.getPropertyManagerInfo();
		Party channelPartnerParty = content.getChannelPartnerParty();
		Product product = content.getProduct();
		Map<String, String> resultMap = content.getResultMap();
		
		try {
			super.pay(content);
			String chargeType = content.getChargeType();
			setReservationState(reservation, chargeType);
			response.setReservationInfo(new ReservationInfo(reservation));
			sqlSession.getMapper(ReservationMapper.class).update(reservation);
			sqlSession.commit();
			PaymentTransaction paymentTransaction = PaymentHelper.preparePaymentTransaction(sqlSession, reservation, propertyManagerInfo, null, null, null,
					Integer.valueOf(channelPartnerParty.getId()), product, resultMap, false);
			sendEmails(sqlSession, paymentTransaction, chargeType);
			content.setPaymentTransaction(paymentTransaction);
		} finally {
			sqlSession.close();
		}
	}
	
	private static void setReservationState(Reservation reservation, String chargeType) {
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
	
	private static void sendEmails(SqlSession sqlSession, PaymentTransaction paymentTransaction, String chargeType) throws ParseException, InterruptedException {
		EmailService.reservationPaymentToRenter(sqlSession, paymentTransaction, chargeType);
		Thread.sleep(5000);
		EmailService.reservationPaymentToPropertyManager(sqlSession, paymentTransaction, chargeType);
	}

}
