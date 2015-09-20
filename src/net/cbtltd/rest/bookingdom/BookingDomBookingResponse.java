package net.cbtltd.rest.bookingdom;

import java.util.Date;

public class BookingDomBookingResponse {
	private String status;
	private Date arrivalDate;
	private Date departureDate;
	private String id;
	private String paymentLinkUID;
	private Date checkInTime;
	private String checkInNote;
	private String apiVersion;
	private String methodType;
	private String resultCode;
	private String resultText;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPaymentLinkUID() {
		return paymentLinkUID;
	}
	public void setPaymentLinkUID(String paymentLinkUID) {
		this.paymentLinkUID = paymentLinkUID;
	}
	public Date getCheckInTime() {
		return checkInTime;
	}
	public void setCheckInTime(Date checkInTime) {
		this.checkInTime = checkInTime;
	}
	public String getCheckInNote() {
		return checkInNote;
	}
	public void setCheckInNote(String checkInNote) {
		this.checkInNote = checkInNote;
	}
	public String getApiVersion() {
		return apiVersion;
	}
	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}
	public String getMethodType() {
		return methodType;
	}
	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultText() {
		return resultText;
	}
	public void setResultText(String resultText) {
		this.resultText = resultText;
	}
}
