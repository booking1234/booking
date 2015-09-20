package net.cbtltd.shared;

import java.text.ParseException;
import java.util.Date;

import net.cbtltd.server.util.PaymentHelper;
/*
 * pending_transaction(id INT NOT NULL auto_increment, entry_date_time DATETIME, booking_id INT, pms_confirmation_id INT, payment_gateway_id INT,
	funds_holder INT, partial_iin INT, first_name VARCHAR, last_name VARCHAR, phone_number VARCHAR, partner_id INT, supplier_id INT, charge_date DATE,
	 charge_amount DOUBLE,
	currency VARCHAR, commission DOUBLE, partner_payment DOUBLE, bookingpal_payment DOUBLE, gateway_transaction_id VARCHAR, status VARCHAR, autopay_type TINYINT(1),
	PRIMARY KEY USING BTREE (id));
 */
public class PendingTransaction {
	
	public PendingTransaction() {
		super();
	}
	
	public PendingTransaction(boolean autopay, Reservation reservation, double bookingpalPayment, double chargeAmount,
			PropertyManagerInfo propertyManagerInfo, double commission, String firstName, String lastName, String gatewayTransactionId, int partialIin,
			int partnerId, double partnerPayment, Integer paymentGatewayId, String phoneNumber, /*String pmsConfirmationId,*/ int status, int supplierId) throws ParseException {
		setAutopay(autopay);
		setBookingId(reservation.getId());
		setBookingpalPayment(bookingpalPayment);
		setChargeAmount(chargeAmount);
		setChargeDate(PaymentHelper.getSecondChargeDate(reservation, propertyManagerInfo));
		setCommission(commission);
		setCurrency(reservation.getCurrency());
		setEntryDateTime(new Date());
		setFirstName(firstName);
		setLastName(lastName);
		setFundsHolder(0); // TODO
		setGatewayTransactionId(gatewayTransactionId);
		setPartialIin(partialIin);
		setPartnerId(partnerId);
		setPartnerPayment(partnerPayment);
		setPaymentGatewayId(paymentGatewayId);
		setPhoneNumber(phoneNumber);
		//setPmsConfirmationId(pmsConfirmationId);
		setStatus(status);
		setSupplierId(supplierId);
	}

	private int id;
	private Date entryDateTime;
	private String bookingId;
	//private String pmsConfirmationId;
	private Integer paymentGatewayId;
	private int fundsHolder;
	private int partialIin;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private int partnerId;
	private int supplierId;
	private Date chargeDate;
	private double chargeAmount;
	private String currency;
	private double commission;
	private double partnerPayment;
	private double bookingpalPayment;
	private String gatewayTransactionId;
	private int status;
	private boolean autopay;
	
	//net rate
	private boolean netRate;
	private double pmCommissionValue;
	private double additionalCommissionValue;
	private double pmsPayment;
	private Double creditCardFee;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getEntryDateTime() {
		return entryDateTime;
	}

	public void setEntryDateTime(Date entryDateTime) {
		this.entryDateTime = entryDateTime;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	/*public String getPmsConfirmationId() {
		return pmsConfirmationId;
	}

	public void setPmsConfirmationId(String pmsConfirmationId) {
		this.pmsConfirmationId = pmsConfirmationId;
	}
*/
	public Integer getPaymentGatewayId() {
		return paymentGatewayId;
	}

	public void setPaymentGatewayId(Integer paymentGatewayId) {
		this.paymentGatewayId = paymentGatewayId;
	}

	public int getFundsHolder() {
		return fundsHolder;
	}

	public void setFundsHolder(int fundsHolder) {
		this.fundsHolder = fundsHolder;
	}

	public int getPartialIin() {
		return partialIin;
	}

	public void setPartialIin(int partialIin) {
		this.partialIin = partialIin;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public Date getChargeDate() {
		return chargeDate;
	}

	public void setChargeDate(Date chargeDate) {
		this.chargeDate = chargeDate;
	}

	public double getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public double getPartnerPayment() {
		return partnerPayment;
	}

	public void setPartnerPayment(double partnerPayment) {
		this.partnerPayment = partnerPayment;
	}

	public double getBookingpalPayment() {
		return bookingpalPayment;
	}

	public void setBookingpalPayment(double bookingpalPayment) {
		this.bookingpalPayment = bookingpalPayment;
	}

	public String getGatewayTransactionId() {
		return gatewayTransactionId;
	}

	public void setGatewayTransactionId(String gatewayTransactionId) {
		this.gatewayTransactionId = gatewayTransactionId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isAutopay() {
		return autopay;
	}

	public void setAutopay(boolean autopay) {
		this.autopay = autopay;
	}
	
	public boolean isNetRate() {
	    return netRate;
	}

	public void setNetRate(boolean netRate) {
	    this.netRate = netRate;
	}

	public double getPmCommissionValue() {
	    return pmCommissionValue;
	}

	public void setPmCommissionValue(double pmCommissionValue) {
	    this.pmCommissionValue = pmCommissionValue;
	}

	public double getAdditionalCommissionValue() {
	    return additionalCommissionValue;
	}

	public void setAdditionalCommissionValue(double additionalCommissionValue) {
	    this.additionalCommissionValue = additionalCommissionValue;
	}

	public double getPmsPayment() {
	    return pmsPayment;
	}

	public void setPmsPayment(double pmsPayment) {
	    this.pmsPayment = pmsPayment;
	}

	public Double getCreditCardFee() {
	    return creditCardFee;
	}

	public void setCreditCardFee(Double creditCardFee) {
	    this.creditCardFee = creditCardFee;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PendingTransaction [id=");
		builder.append(getId());
		builder.append("], [autopayType=");
		builder.append(isAutopay());
		builder.append("], [BookingId=");
		builder.append(getBookingId());
		builder.append("], [BookingpalPayment=");
		builder.append(getBookingpalPayment());
		builder.append("], [ChargeAmount=");
		builder.append(getChargeAmount());
		builder.append("], [ChargeDate=");
		builder.append(getChargeDate());
		builder.append("], [Commission=");
		builder.append(getCommission());
		builder.append("], [Currency=");
		builder.append(getCurrency());
		builder.append("], [EntryDateTime=");
		builder.append(getEntryDateTime());
		builder.append("], [FirstName=");
		builder.append(getFirstName());
		builder.append("], [FundsHolder=");
		builder.append(getFundsHolder());
		builder.append("], [GatewayTransactionId=");
		builder.append(getGatewayTransactionId());
		builder.append("], [LastName=");
		builder.append(getLastName());
		builder.append("], [PartialIin=");
		builder.append(getPartialIin());
		builder.append("], [PartnerId=");
		builder.append(getPartnerId());
		builder.append("], [PartnerPayment=");
		builder.append(getPartnerPayment());
		builder.append("], [PaymentGatewayId=");
		builder.append(getPaymentGatewayId());
		builder.append("], [PhoneNumber=");
		builder.append(getPhoneNumber());
/*		builder.append("], [PmsConfirmationId=");
		builder.append(getPmsConfirmationId());*/
		builder.append("], [Status=");
		builder.append(getStatus());
		builder.append("], [SupplierId=");
		builder.append(getSupplierId());		
		builder.append("], [netRate=");
		builder.append(isNetRate());
		builder.append("], [pmCommissionValue=");
		builder.append(getPmCommissionValue());
		builder.append("], [additionalCommissionValue=");
		builder.append(getAdditionalCommissionValue());
		builder.append("], [pmsPayment=");
		builder.append(getPmsPayment());
		builder.append("], [creditCardFee=");
		builder.append(getCreditCardFee());
		builder.append("]");
		return builder.toString();
	}
	
}
