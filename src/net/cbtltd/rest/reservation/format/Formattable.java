package net.cbtltd.rest.reservation.format;

import net.cbtltd.rest.reservation.CreateReservationContent;

/**
 * Interface responsible for response format functionality.
 * 
 * @author rmelnyk
 *
 */
public interface Formattable {
	void format(CreateReservationContent content);
}
