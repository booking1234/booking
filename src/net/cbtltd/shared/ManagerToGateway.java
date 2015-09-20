package net.cbtltd.shared;

import java.util.Date;

public class ManagerToGateway {
	private int id;
	private Date createDate;
	private int paymentGatewayId;
	private int fundsHolder;
	private int supplierId;
	private String gatewayCode;
	private String gatewayAccountId;
	private String additionalInfo;

	public static int PROPERTY_MANAGER_HOLDER = 0;
	public static int BOOKINGPAL_HOLDER = 1;
	public static int SPLITTED_HOLDER = 2;
	public static int INVOICE = 3; // 'none' holder
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getPaymentGatewayId() {
		return paymentGatewayId;
	}

	public void setPaymentGatewayId(int paymentGatewayId) {
		this.paymentGatewayId = paymentGatewayId;
	}

	public int getFundsHolder() {
		return fundsHolder;
	}

	public void setFundsHolder(int fundsHolder) {
		this.fundsHolder = fundsHolder;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public String getGatewayCode() {
		return gatewayCode;
	}

	public void setGatewayCode(String gatewayCode) {
		this.gatewayCode = gatewayCode;
	}

	public String getGatewayAccountId() {
		return gatewayAccountId;
	}

	public void setGatewayAccountId(String gatewayAccountId) {
		this.gatewayAccountId = gatewayAccountId;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ManagerToGateway [id=");
		builder.append(getId());
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", paymentGatewayId=");
		builder.append(paymentGatewayId);
		builder.append(", fundsHolder=");
		builder.append(fundsHolder);
		builder.append(", supplierId=");
		builder.append(supplierId);
		builder.append(", gatewayCode=");
		builder.append(gatewayCode);
		builder.append(", gatewayAccountId=");
		builder.append(gatewayAccountId);
		builder.append(", additionalInfo=");
		builder.append(additionalInfo);
		builder.append("]");
		return builder.toString();
	}
}
