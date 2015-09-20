package net.cbtltd.shared;

import java.util.Date;

public class PaymentTransaction {
	private Long id;
	private Date createDate;
	private int serverId;
	private int reservationId;
	private Integer pmsConfirmationId;
	private int paymentMethod;
	private String gatewayTransactionId;
	private Integer gatewayId;
	private Integer fundsHolder;
	private Integer partialIin;
	private String status;
	private String message;
	private Integer partnerId;
	private int supplierId;
	private Date chargeDate;
	private double totalAmount;
	private String currency;
	private double totalCommission;
	private double partnerPayment;
	private double totalBookingpalPayment;
	private double finalAmount;
	private Double creditCardFee;
	private String chargeType;
	private boolean cancelled;
	private boolean netRate;
	
	// net rate commissions
	private double pmsPayment;
	private double pmCommissionValue;
	
	private double additionalCommissionValue;
	
	public static String FULL_CHARGE_TYPE = "Full";
	public static String DEPOSIT_CHARGE_TYPE = "Deposit";
	public static String SECURITY_CHARGE_TYPE = "Security";
	public static String SECURITY_AND_DPOSIT_CHARGE_TYPE = "Security and deposit";
	public static String REFUND_CHARGE_TYPE = "Refund";
	public static String CANCELLED_CHARGE_TYPE = "Cancelled";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public Integer getPmsConfirmationId() {
		return pmsConfirmationId;
	}

	public void setPmsConfirmationId(Integer pmsConfirmationId) {
		this.pmsConfirmationId = pmsConfirmationId;
	}

	public int getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(int paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getGatewayTransactionId() {
		return gatewayTransactionId;
	}

	public void setGatewayTransactionId(String gatewayTransactionId) {
		this.gatewayTransactionId = gatewayTransactionId;
	}

	public Integer getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(Integer gatewayId) {
		this.gatewayId = gatewayId;
	}

	public Integer getFundsHolder() {
		return fundsHolder;
	}

	public void setFundsHolder(Integer fundsHolder) {
		this.fundsHolder = fundsHolder;
	}

	public Integer getPartialIin() {
		return partialIin;
	}

	public void setPartialIin(Integer partialIin) {
		this.partialIin = partialIin;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
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

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getTotalCommission() {
		return totalCommission;
	}

	public void setTotalCommission(double totalCommission) {
		this.totalCommission = totalCommission;
	}

	public double getPartnerPayment() {
		return partnerPayment;
	}

	public void setPartnerPayment(double partnerPayment) {
		this.partnerPayment = partnerPayment;
	}

	public double getTotalBookingpalPayment() {
		return totalBookingpalPayment;
	}

	public void setTotalBookingpalPayment(double totalBookingpalPayment) {
		this.totalBookingpalPayment = totalBookingpalPayment;
	}

	public double getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(double finalAmount) {
		this.finalAmount = finalAmount;
	}

	public Double getCreditCardFee() {
		return creditCardFee;
	}

	public void setCreditCardFee(Double creditCardFee) {
		this.creditCardFee = creditCardFee;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public boolean isNetRate() {
		return netRate;
	}

	public void setNetRate(boolean netRate) {
		this.netRate = netRate;
	}

	public Double getPmsPayment() {
		return pmsPayment;
	}

	public void setPmsPayment(Double pmsPayment) {
		this.pmsPayment = pmsPayment;
	}

	public Double getPmCommissionValue() {
		return pmCommissionValue;
	}

	public void setPmCommissionValue(Double pmCommissionValue) {
		this.pmCommissionValue = pmCommissionValue;
	}

	public double getAdditionalCommissionValue() {
		return additionalCommissionValue;
	}

	public void setAdditionalCommissionValue(double additionalCommissionValue) {
		this.additionalCommissionValue = additionalCommissionValue;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PaymentTransaction [id=");
		builder.append(id);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", serverId=");
		builder.append(serverId);
		builder.append(", reservationId=");
		builder.append(reservationId);
		builder.append(", pmsConfirmationId=");
		builder.append(pmsConfirmationId);
		builder.append(", paymentMethod=");
		builder.append(paymentMethod);
		builder.append(", gatewayTransactionId=");
		builder.append(gatewayTransactionId);
		builder.append(", gatewayId=");
		builder.append(gatewayId);
		builder.append(", fundsHolder=");
		builder.append(fundsHolder);
		builder.append(", partialIin=");
		builder.append(partialIin);
		builder.append(", status=");
		builder.append(status);
		builder.append(", message=");
		builder.append(message);
		builder.append(", partnerId=");
		builder.append(partnerId);
		builder.append(", supplierId=");
		builder.append(supplierId);
		builder.append(", chargeDate=");
		builder.append(chargeDate);
		builder.append(", totalAmount=");
		builder.append(totalAmount);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", totalCommission=");
		builder.append(totalCommission);
		builder.append(", partnerPayment=");
		builder.append(partnerPayment);
		builder.append(", totalBookingpalPayment=");
		builder.append(totalBookingpalPayment);
		builder.append(", finalAmount=");
		builder.append(finalAmount);
		builder.append(", creditCardFee=");
		builder.append(creditCardFee);
		builder.append(", chargeType=");
		builder.append(chargeType);
		builder.append(", netRate=");
		builder.append(netRate);
		builder.append("]");
		return builder.toString();
	}
}
