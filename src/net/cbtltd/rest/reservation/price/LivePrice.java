package net.cbtltd.rest.reservation.price;

import java.text.ParseException;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import net.cbtltd.rest.ReservationRequest;
import net.cbtltd.rest.reservation.CreateReservationContent;
import net.cbtltd.server.PartnerService;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ReservationService;
import net.cbtltd.server.ServiceException;
import net.cbtltd.server.util.CommissionCalculationUtil;
import net.cbtltd.server.util.PriceUtil;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.ManagerToGateway;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.Reservation;

public class LivePrice extends AbstractPrice implements Pricable {

	protected static final Logger LOG = Logger.getLogger(LivePrice.class);
	
	@Override
	public void price(CreateReservationContent content) throws ParseException {
		super.price(content);
		SqlSession sqlSession = RazorServer.openSession();
		Reservation reservation = content.getReservation();
		Product product = content.getProduct();
		PropertyManagerInfo propertyManagerInfo = content.getPropertyManagerInfo();
		String currency = content.getCurrency();
		try {
			LOG.error("PartnerService.readPrice started");
			
			PartnerService.readPrice(sqlSession, reservation, product.getAltid(), currency);
			
			if((reservation.getPrice() == null || reservation.getPrice() <= 0) || (reservation.getQuote() == null || reservation.getQuote() <= 0)) {
				throw new ServiceException(Error.price_missing, "Price was resturned null or 0");
			}
			
			ReservationService.computeLivePrice(sqlSession, reservation, null, currency);
			
			LOG.error("PartnerService.readPrice finished");
			
			super.verifyPrice(reservation);
		} finally {
			sqlSession.close();
		}
	}

}
