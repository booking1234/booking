package net.cbtltd.rest.reservation.format;

import java.math.BigDecimal;

import net.cbtltd.rest.reservation.CreateReservationContent;
import net.cbtltd.rest.response.ReservationInfo;
import net.cbtltd.rest.response.ReservationResponse;
import net.cbtltd.server.ServiceException;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Reservation;

public class DefaultFormatter implements Formattable {

	@Override
	public void format(CreateReservationContent content) {
		ReservationResponse response = content.getReservationResponse();
		Reservation reservation = content.getReservation();
		if(reservation == null) {
			throw new ServiceException(Error.parameter_null, "reservation");
		}
		Double price = reservation.getPrice();
		Double cost = reservation.getCost();
		Double quote = reservation.getQuote();
		Double deposit = reservation.getDeposit();
		Double extra = reservation.getExtra();
		
		reservation.setPrice(price == null ? 0. : round(price, 2, BigDecimal.ROUND_HALF_UP));
		reservation.setCost(cost == null ? 0. : round(cost, 2, BigDecimal.ROUND_HALF_UP));
		reservation.setQuote(quote == null ? 0. : round(quote, 2, BigDecimal.ROUND_HALF_UP));
		reservation.setDeposit(deposit == null ? 0. : round(deposit, 2, BigDecimal.ROUND_HALF_UP));
		reservation.setExtra(extra == null ? 0. : round(extra, 2, BigDecimal.ROUND_HALF_UP));
		if(reservation.getQuotedetail() != null) {
			for(Price priceItem : reservation.getQuotedetail()) {
				priceItem.setValue(priceItem.getValue() == null ? 0. : round(priceItem.getValue(), 2, BigDecimal.ROUND_HALF_UP));
			}
		}
		response.setReservationInfo(new ReservationInfo(reservation));
	}
	
	private static Double round(double unrounded, int precision, int roundingMode) {
		BigDecimal bd = new BigDecimal(unrounded);
		BigDecimal rounded = bd.setScale(precision, roundingMode);
		return rounded.doubleValue();
	}

}
