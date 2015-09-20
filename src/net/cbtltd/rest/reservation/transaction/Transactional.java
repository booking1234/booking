package net.cbtltd.rest.reservation.transaction;

import net.cbtltd.rest.reservation.CreateReservationContent;

/**
 * Interface responsible for creating payment transaction functionality.
 * 
 * @author rmelnyk
 *
 */
public interface Transactional {
	void transact(CreateReservationContent content);
}
