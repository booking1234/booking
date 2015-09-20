package net.cbtltd.rest.reservation.initialization;

import java.text.ParseException;

import net.cbtltd.rest.reservation.CreateReservationContent;

/**
 * Interface responsible for objects initialization functionality.
 * 
 * @author rmelnyk
 *
 */
public interface Initializable {
	void initialize(CreateReservationContent content) throws ParseException;
}
