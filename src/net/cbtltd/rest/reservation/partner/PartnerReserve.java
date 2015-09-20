package net.cbtltd.rest.reservation.partner;

import net.cbtltd.rest.reservation.CreateReservationContent;

/**
 * Interface responsible for PMS API reservation call functionality.
 * 
 * @author rmelnyk
 *
 */
public interface PartnerReserve {
	void partnerReserve(CreateReservationContent content) throws Exception;
}
