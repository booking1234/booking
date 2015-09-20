package net.cbtltd.rest.reservation.price;

import java.text.ParseException;

import net.cbtltd.rest.reservation.CreateReservationContent;

/**
 * Interface responsible for getting price for reservation functionality.
 * 
 * @author rmelnyk
 *
 */
public interface Pricable {
	void price(CreateReservationContent content) throws ParseException;
}
