package net.cbtltd.rest.ru.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "Authentication",
    "ReservationID"
})
@XmlRootElement(name = "Push_CancelReservation_RQ")
public class CancelReservation {

	@XmlElement(required = true)
	protected Authentication Authentication;
	@XmlElement(required = true)
	protected String ReservationID;
	
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
	 * @return the code that uniquely identifies a reservation in RU system
	 */
	public String getReservationID() {
		return ReservationID;
	}
	
	/**
	 * set the code that uniquely identifies a reservation in RU system
	 */
	public void setReservationID(String reservationID) {
		ReservationID = reservationID;
	}

}
