package net.cbtltd.rest.reservation.cancel;

import net.cbtltd.rest.reservation.CreateReservationContent;

/**
 * Interface responsible for verification and local reservation functionality.
 * 
 * @author rmelnyk
 *
 */
public interface Verifiable {
	void verify(CreateReservationContent content) throws Exception;
}
