package net.cbtltd.rest.reservation.payment;

import net.cbtltd.rest.reservation.CreateReservationContent;

/**
 * Interface responsible for payment functionality.
 * 
 * @author rmelnyk
 *
 */
public interface Payable {
	void pay(CreateReservationContent content) throws Exception;
}
