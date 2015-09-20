package net.cbtltd.rest.response;

import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.rest.error.ApiResponse;
import net.cbtltd.shared.Reservation;

@XmlRootElement(name="cancel_reservation")
public class CancelReservationResponse extends ApiResponse {
	
	public CancelReservationResponse() {
		super();
	}
}
