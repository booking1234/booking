package net.cbtltd.rest.reservation.availability;

import net.cbtltd.rest.reservation.CreateReservationContent;

/**
 * Interface responsible for availability functionality.
 * 
 * @author rmelnyk
 *
 */
public interface Available {
	void available(CreateReservationContent content);
}
