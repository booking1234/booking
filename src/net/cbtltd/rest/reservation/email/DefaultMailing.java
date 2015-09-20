package net.cbtltd.rest.reservation.email;

import org.apache.ibatis.session.SqlSession;

import net.cbtltd.rest.GatewayHandler;
import net.cbtltd.rest.reservation.CreateReservationContent;
import net.cbtltd.server.EmailService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.shared.PaymentTransaction;

public class DefaultMailing implements Mailable {

	@Override
	public void email(CreateReservationContent content) {
		PaymentTransaction paymentTransaction = content.getPaymentTransaction();
		SqlSession sqlSession = RazorServer.openSession();
		try {
			if(paymentTransaction != null && (paymentTransaction.getStatus() == null || !paymentTransaction.getStatus().equals(GatewayHandler.ACCEPTED))) {
				EmailService.failedPaymentTransactionToAdmin(sqlSession, paymentTransaction);
			}
		} finally {
			sqlSession.close();
		}
	}

}
