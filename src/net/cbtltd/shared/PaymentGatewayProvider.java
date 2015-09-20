package net.cbtltd.shared;

import java.util.Date;

public class PaymentGatewayProvider {
	private int id;
	private String name;
	private Date createDate;
	private short fee;
	private boolean autopay;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public short getFee() {
		return fee;
	}

	public void setFee(short fee) {
		this.fee = fee;
	}

	public boolean isAutopay() {
		return autopay;
	}

	public void setAutopay(boolean autopay) {
		this.autopay = autopay;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ManagerToGateway [id=");
		builder.append(getId());
		builder.append(", name=");
		builder.append(name);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", fee=");
		builder.append(fee);
		builder.append(", autopay=");
		builder.append(autopay);
		builder.append("]");
		return builder.toString();
	}
}
