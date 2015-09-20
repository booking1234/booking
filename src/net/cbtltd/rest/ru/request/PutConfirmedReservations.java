package net.cbtltd.rest.ru.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import net.cbtltd.rest.ru.Reservation;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Authentication",
    "Reservation"
})
@XmlRootElement(name = "Push_PutConfirmedReservationMulti_RQ")
public class PutConfirmedReservations {

	@XmlElement(required = true)
	protected Authentication Authentication;
	@XmlElement(required = true)
	protected Reservation Reservation;
	
	/**
	 * @return the authentication details
	 */
	public Authentication getAuthentication() {
		return Authentication;
	}
	
	/**
	 * set the authentication details
	 */
	public void setAuthentication(Authentication authentication) {
		Authentication = authentication;
	}
	
	/**
	 * @return the single reservation
	 */
	public Reservation getReservation() {
		return Reservation;
	}
	
	/**
	 * set the single reservation
	 */
	public void setReservation(Reservation reservation) {
		Reservation = reservation;
	}

}
