package net.cbtltd.rest.reservation.cancel;

import java.util.ArrayList;
import java.util.List;

import net.cbtltd.rest.reservation.CreateReservationContent;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.PaymentTransaction;
import net.cbtltd.shared.Reservation;

import org.apache.ibatis.session.SqlSession;

public class DefaultVerification extends AbstractVerification implements Verifiable {

	@Override
	public void verify(CreateReservationContent content) throws Exception {
		PaymentTransaction paymentTransaction = content.getPaymentTransaction();
		Reservation reservation = content.getReservation();
		SqlSession sqlSession = RazorServer.openSession();
		
		try {
			if(paymentTransaction != null && reservation.getState() != null && reservation.getState().equals(Reservation.State.Cancelled.name())) {
				List<PaymentTransaction> paymentTransactions = new ArrayList<PaymentTransaction>();
				paymentTransactions.add(paymentTransaction);
				super.refundAmount(sqlSession, paymentTransaction.getSupplierId(), paymentTransactions, content.getFirstPayment());
				super.cancelPendingTransactions(sqlSession, reservation);
				content.setReservationFinished(true);
				throw new ServiceException(Error.pms_reservation_reject);
			} else {
				super.setReservationState(reservation, content.getChargeType());
				sqlSession.getMapper(ReservationMapper.class).update(reservation);
				sqlSession.commit();
				content.setReservationFinished(true);
			}
			finishVerification(sqlSession, content);
		} finally {
			sqlSession.close();
		}
	}
}
