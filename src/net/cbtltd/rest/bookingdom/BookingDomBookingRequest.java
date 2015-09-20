package net.cbtltd.rest.bookingdom;

import java.util.Date;

public class BookingDomBookingRequest {
	private int propertyId; // Mandatory
	private BookingDomGuestComposition guestsComposition; // Mandatory
	private String reservationLanguage; // Mandatory
	private Date fromDate; // Mandatory
	private Date toDate; // Mandatory
	private String username; // Customer email, Mandatory
	private String firstName; // Mandatory
	private String lastName; // Mandatory
	private String customerPhoneNumber;
	private String paymentMethod;
	private Double fee;
	private double payoff;
	private double reservationValue; // Mandatory

	public int getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}
	public BookingDomGuestComposition getGuestsComposition() {
		return guestsComposition;
	}
	public void setGuestsComposition(BookingDomGuestComposition guestsComposition) {
		this.guestsComposition = guestsComposition;
	}
	public String getReservationLanguage() {
		return reservationLanguage;
	}
	public void setReservationLanguage(String reservationLanguage) {
		this.reservationLanguage = reservationLanguage;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}
	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public double getPayoff() {
		return payoff;
	}
	public void setPayoff(double payoff) {
		this.payoff = payoff;
	}
	public double getReservationValue() {
		return reservationValue;
	}
	public void setReservationValue(double reservationValue) {
		this.reservationValue = reservationValue;
	}


}
