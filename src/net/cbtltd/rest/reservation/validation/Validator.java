package net.cbtltd.rest.reservation.validation;

import java.text.ParseException;

import net.cbtltd.rest.reservation.CreateReservationContent;

/**
 * Interface responsible for request validation functionality.
 * 
 * @author rmelnyk
 *
 */
public interface Validator {
	void validate(CreateReservationContent content) throws ParseException;
}
