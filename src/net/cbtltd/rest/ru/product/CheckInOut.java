package net.cbtltd.rest.ru.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "CheckInFrom",
    "CheckInTo",
    "CheckOutUntil",
    "Place",
    "LateArrivalFees",
    "EarlyDepartureFees"
})
@XmlRootElement(name = "CheckInOut")
public class CheckInOut {
	

	@XmlElement(required = true)
	protected String CheckInFrom;
	@XmlElement(required = true)
	protected String CheckInTo;
	@XmlElement(required = true)
	protected String CheckOutUntil;
	@XmlElement(required = true)
	protected String Place;
	protected LateArrivalFees LateArrivalFees;
	protected EarlyDepartureFees EarlyDepartureFees;
	
	/**
	 * @return the accepted check in time – from (HH:MM format, 24h format)
	 */
	public String getCheckInFrom() {
		return CheckInFrom;
	}
	
	/**
	 * set checkInFrom
	 */
	public void setCheckInFrom(String checkInFrom) {
		CheckInFrom = checkInFrom;
	}
	
	/**
	 * @return the accepted check in time – to (HH:MM format, 24h format)
	 */
	public String getCheckInTo() {
		return CheckInTo;
	}
	
	/**
	 * set checkInTo
	 */
	public void setCheckInTo(String checkInTo) {
		CheckInTo = checkInTo;
	}
	
	/**
	 * @return the The latest check out time. (HH:MM format, 24h format)
	 */
	public String getCheckOutUntil() {
		return CheckOutUntil;
	}
	
	/**
	 * set checkOutUntil
	 */
	public void setCheckOutUntil(String checkOutUntil) {
		CheckOutUntil = checkOutUntil;
	}
	
	/**
	 * @return the location where the check in takes place
	 */
	public String getPlace() {
		return Place;
	}
	
	/**
	 * set place
	 */
	public void setPlace(String place) {
		Place = place;
	}
	
	/**
	 * @return the collection of fees for a late arrival by arrival time (separable ranges)
	 */
	public LateArrivalFees getLateArrivalFees() {
		return LateArrivalFees;
	}
	
	/**
	 * set lateArrivalFees
	 */
	public void setLateArrivalFees(LateArrivalFees lateArrivalFees) {
		LateArrivalFees = lateArrivalFees;
	}
	
	/**
	 * @return the collection of fees for a early departure by arrival time (separable ranges)
	 */
	public EarlyDepartureFees getEarlyDepartureFees() {
		return EarlyDepartureFees;
	}
	
	/**
	 * set earlyDepartureFees
	 */
	public void setEarlyDepartureFees(EarlyDepartureFees earlyDepartureFees) {
		EarlyDepartureFees = earlyDepartureFees;
	}

}
