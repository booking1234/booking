package net.cbtltd.rest.reservation.payment;

import net.cbtltd.rest.reservation.CreateReservationContent;
import net.cbtltd.server.util.PaymentHelper;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.Reservation;

public class AbstractPayment implements Payable {

	@Override
	public void pay(CreateReservationContent content) throws Exception {
		PropertyManagerInfo propertyManagerInfo = content.getPropertyManagerInfo();
		Reservation reservation = content.getReservation();
		// Processing charge type
		String chargeType = PaymentHelper.getChargeType(propertyManagerInfo, reservation);
		Double firstPayment = PaymentHelper.getFirstPayment(reservation, propertyManagerInfo);
		Double secondPayment = PaymentHelper.getSecondPayment(reservation, propertyManagerInfo);
		Double amountToCharge = chargeType.equalsIgnoreCase(PaymentHelper.FULL_PAYMENT_METHOD) ? firstPayment + secondPayment : firstPayment;
		
		content.setPaymentGatewayId(-1);
		content.setFirstPayment(firstPayment);
		content.setSecondPayment(secondPayment);
		content.setAmountToCharge(amountToCharge);
		content.setChargeType(chargeType);
	}

}
