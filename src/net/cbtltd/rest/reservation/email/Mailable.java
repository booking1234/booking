package net.cbtltd.rest.reservation.email;

import net.cbtltd.rest.reservation.CreateReservationContent;

/**
 * Interface responsible for sending administrator email functionality.
 * 
 * @author rmelnyk
 *
 */
public interface Mailable {
	void email(CreateReservationContent content);
}
