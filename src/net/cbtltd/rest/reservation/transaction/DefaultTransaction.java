package net.cbtltd.rest.reservation.transaction;

import net.cbtltd.rest.reservation.CreateReservationContent;
import net.cbtltd.rest.response.ReservationResponse;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.PaymentTransactionMapper;
import net.cbtltd.shared.PaymentTransaction;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class DefaultTransaction implements Transactional {

	protected static final Logger LOG = Logger.getLogger(DefaultTransaction.class);
	
	@Override
	public void transact(CreateReservationContent content) {
		SqlSession sqlSession = RazorServer.openSession();
		PaymentTransaction paymentTransaction = content.getPaymentTransaction();
		ReservationResponse response = content.getReservationResponse();
		try{
			sqlSession = RazorServer.openSession();
			if(paymentTransaction != null) {
				sqlSession.getMapper(PaymentTransactionMapper.class).create(paymentTransaction);
				sqlSession.commit();
			}
		}
		catch(Throwable x) {
			response.setErrorMessage(x.getMessage());
			LOG.error(x.getMessage());
			sqlSession.rollback();
		}
		finally {sqlSession.close();}
	}

}
