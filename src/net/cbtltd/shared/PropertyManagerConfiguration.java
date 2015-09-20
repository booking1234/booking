package net.cbtltd.shared;

public class PropertyManagerConfiguration {
	
	public PropertyManagerConfiguration() {
		super();
	}

	private Integer id;
	private Integer propertyManagerId;
	private Integer validation;
	private Integer initialization;
	private Integer price;
	private Integer availability;
	private Integer payment;
	private Integer partnerReservation;
	private Integer verifyAndCancel;
	private Integer paymentTransaction;
	private Integer format;
	private Integer sendAdminEmail;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getPropertyManagerId() {
		return propertyManagerId;
	}

	public void setPropertyManagerId(Integer propertyManagerId) {
		this.propertyManagerId = propertyManagerId;
	}
	
	public Integer getValidation() {
		return validation;
	}

	public void setValidation(Integer validation) {
		this.validation = validation;
	}

	public Integer getInitialization() {
		return initialization;
	}

	public void setInitialization(Integer initialization) {
		this.initialization = initialization;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getAvailability() {
		return availability;
	}

	public void setAvailability(Integer availability) {
		this.availability = availability;
	}

	public Integer getPayment() {
		return payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
	}

	public Integer getPartnerReservation() {
		return partnerReservation;
	}

	public void setPartnerReservation(Integer partnerReservation) {
		this.partnerReservation = partnerReservation;
	}

	public Integer getVerifyAndCancel() {
		return verifyAndCancel;
	}

	public void setVerifyAndCancel(Integer verifyAndCancel) {
		this.verifyAndCancel = verifyAndCancel;
	}

	public Integer getPaymentTransaction() {
		return paymentTransaction;
	}

	public void setPaymentTransaction(Integer paymentTransaction) {
		this.paymentTransaction = paymentTransaction;
	}

	public Integer getFormat() {
		return format;
	}

	public void setFormat(Integer format) {
		this.format = format;
	}

	public Integer getSendAdminEmail() {
		return sendAdminEmail;
	}

	public void setSendAdminEmail(Integer sendAdminEmail) {
		this.sendAdminEmail = sendAdminEmail;
	}
}
