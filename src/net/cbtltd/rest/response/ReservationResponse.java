package net.cbtltd.rest.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.rest.error.ApiResponse;

@XmlRootElement(name="reservation_response")
public class ReservationResponse extends ApiResponse {

	public ReservationResponse() {
		super();
	}
		
	private ReservationInfo reservationInfo;
	private String propertyName;
	private String propertyAddress;
	private String propertyManagerName;
	private String propertyManagerPhone;
	private String propertyManagerEmail;
	private Double totalCharge;
	private Double downPayment;
	private Double secondPayment;
	private CancellationItem cancellationItem;
	private String fromDate;
	private String toDate;
	
	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getPropertyAddress() {
		return propertyAddress;
	}

	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	public String getPropertyManagerName() {
		return propertyManagerName;
	}

	public void setPropertyManagerName(String propertyManagerName) {
		this.propertyManagerName = propertyManagerName;
	}

	public String getPropertyManagerPhone() {
		return propertyManagerPhone;
	}

	public void setPropertyManagerPhone(String propertyManagerPhone) {
		this.propertyManagerPhone = propertyManagerPhone;
	}

	public String getPropertyManagerEmail() {
		return propertyManagerEmail;
	}

	public void setPropertyManagerEmail(String propertyManagerEmail) {
		this.propertyManagerEmail = propertyManagerEmail;
	}

	public Double getTotalCharge() {
		return totalCharge;
	}

	public void setTotalCharge(Double totalCharge) {
		this.totalCharge = totalCharge;
	}

	public Double getDownPayment() {
		return downPayment;
	}

	public void setDownPayment(Double downPayment) {
		this.downPayment = downPayment;
	}

	public CancellationItem getCancellationItem() {
		return cancellationItem;
	}

	public void setCancellationItem(CancellationItem cancellationItem) {
		this.cancellationItem = cancellationItem;
	}
	
	@XmlElement(name = "reservation")
	public ReservationInfo getReservationInfo() {
		return reservationInfo;
	}

	public void setReservationInfo(ReservationInfo reservationInfo) {
		this.reservationInfo = reservationInfo;
	}

	public Double getSecondPayment() {
		return secondPayment;
	}

	public void setSecondPayment(Double secondPayment) {
		this.secondPayment = secondPayment;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
}
