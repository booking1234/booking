package net.cbtltd.rest.reservation.price;

import java.text.ParseException;

import net.cbtltd.rest.reservation.CreateReservationContent;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ReservationService;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.Reservation;

import org.apache.ibatis.session.SqlSession;

public class LocalPrice extends AbstractPrice implements Pricable{

	@Override
	public void price(CreateReservationContent content) throws ParseException {
		super.price(content);
		SqlSession sqlSession = RazorServer.openSession();
		try {
			Reservation reservation = content.getReservation();
			PropertyManagerInfo propertyManagerInfo = content.getPropertyManagerInfo();
			String currency = content.getCurrency();
			ReservationService.computePrice(sqlSession, reservation, currency);
			Double deposit = ReservationService.getDeposit(reservation, propertyManagerInfo);
			reservation.setDeposit(deposit);
			reservation.setCost(reservation.getQuote() * ReservationService.getDiscountfactor(sqlSession, reservation));
			super.verifyPrice(reservation);
		} finally {
			sqlSession.close();
		}
	}
}
