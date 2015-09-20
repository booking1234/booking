package net.cbtltd.rest.reservation.cancel;

import org.apache.ibatis.session.SqlSession;

import net.cbtltd.rest.reservation.CreateReservationContent;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.PaymentTransaction;
import net.cbtltd.shared.Reservation;

public class NovasolVerification extends AbstractVerification implements Verifiable {

	@Override
	public void verify(CreateReservationContent content) throws Exception {
		PaymentTransaction paymentTransaction = content.getPaymentTransaction();
		Reservation reservation = content.getReservation();
		SqlSession sqlSession = RazorServer.openSession();
		try {
			if(paymentTransaction == null) {
				super.cancelPendingTransactions(sqlSession, reservation);
				content.setReservationFinished(true);
				throw new ServiceException(Error.pms_reservation_reject);
			} else {
				super.setReservationState(reservation, content.getChargeType());
				sqlSession.commit();
				content.setReservationFinished(true);
			}
		} finally {
			sqlSession.close();
		}
	}

}
