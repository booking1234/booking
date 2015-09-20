package net.cbtltd.rest.ru;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
		"ReservationID",
	    "StayInfos",
	    "CustomerInfo",
	    "Comments",
	    "CreditCard"
	})
@XmlRootElement(name = "Reservation")
public class Reservation {

	@XmlElement
	protected int ReservationID;
	@XmlElement(required = true)
	protected StayInfos StayInfos;
	@XmlElement(required = true)
	protected CustomerInfo CustomerInfo;
	@XmlElement
	protected String Comments;
	@XmlElement
	protected CreditCard CreditCard;
	
	/**
	 * @return the code that uniquely identifies a reservation
	 */
	public int getReservationID() {
		return ReservationID;
	}
	
	/**
	 * set the code that uniquely identifies a reservation (this element is optional, you should use it if you put property on hold previously)
	 */
	public void setReservationID(int reservationID) {
		ReservationID = reservationID;
	}
	
	/**
	 * @return the collection of stay informations
	 */
	public StayInfos getStayInfos() {
		return StayInfos;
	}
	
	/**
	 * set the collection of stay informations
	 */
	public void setStayInfos(StayInfos stayInfos) {
		StayInfos = stayInfos;
	}
	
	/**
	 * @return the informations about the customer
	 */
	public CustomerInfo getCustomerInfo() {
		return CustomerInfo;
	}
	
	/**
	 * set the informations about the customer
	 */
	public void setCustomerInfo(CustomerInfo customerInfo) {
		CustomerInfo = customerInfo;
	}
	
	/**
	 * @return the additional comments
	 */
	public String getComments() {
		return Comments;
	}
	
	/**
	 * set the additional comments
	 */
	public void setComments(String comments) {
		Comments = comments;
	}
	
	/**
	 * @return the Credit Card information
	 */
	public CreditCard getCreditCard() {
		return CreditCard;
	}
	
	/**
	 * set the Credit Card information
	 */
	public void setCreditCard(CreditCard creditCard) {
		CreditCard = creditCard;
	}
	
}
