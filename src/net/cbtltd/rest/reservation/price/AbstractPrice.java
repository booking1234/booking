package net.cbtltd.rest.reservation.price;

import java.text.ParseException;

import org.apache.ibatis.session.SqlSession;

import com.mybookingpal.utils.BPThreadLocal;

import net.cbtltd.rest.ReservationRequest;
import net.cbtltd.rest.reservation.CreateReservationContent;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.util.PaymentHelper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.ManagerToGateway;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.Reservation;

public abstract class AbstractPrice implements Pricable {

	@Override
	public void price(CreateReservationContent content) throws ParseException {
		ReservationRequest request = content.getReservationRequest();
		String currency = request.getCurrency();
		PropertyManagerInfo propertyManagerInfo = content.getPropertyManagerInfo();
		Product product = content.getProduct();

		SqlSession sqlSession = RazorServer.openSession();
		try {
			Double amountToCheck = PaymentHelper.roundAmountTwoDecimals(request.getAmount());
			Double amountDifference = PaymentHelper.amountDifference(sqlSession, content.getReservation(), amountToCheck, currency);
			if(amountDifference > 1 && (BPThreadLocal.get() == null || !BPThreadLocal.get())) {
				throw new ServiceException(Error.price_not_match, "passed: " + amountToCheck + currency + ", difference: " + amountDifference);
			}
			
			if(propertyManagerInfo.getFundsHolder() == ManagerToGateway.BOOKINGPAL_HOLDER) {
				currency = PaymentHelper.DEFAULT_BOOKINGPAL_CURRENCY;
			} else {
				currency = product.getCurrency();
			}
			content.setCurrency(currency);
		} finally {
			sqlSession.close();
		}
	}
	

	public void verifyPrice(Reservation reservation) {
		if(reservation.getPrice() == 0.0) {
			throw new ServiceException(Error.price_data); 
		}
	}

}
