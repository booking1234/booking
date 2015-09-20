package net.cbtltd.shared;

import java.util.Date;

/**
 * 
 * @author Vova Kambur
 *	
 *  This class consists information about the method of receiving payment from BookingPal. 
 * 
 */

public class PaymentMethod {
	
	private Integer id;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private Integer pmID;					// Property Manager ID
	
	private String type;					/* PaymentType: receiving payment from BookingPal
												1 - Mail Checks;
												2 - PayPal;
												3 - ACH/Direct deposit.*/
	
	private String paymentInfo;				/* Consists payment information depends on payment type:
												type=1 - empty;
												type=2 - PayPal account;
												type=3 - consists ACHPaymentInfo object in JSON format.*/
	 										
	private Date entryDate;					/* Date of test payment transaction.  */
	private Double amount;					/* Amount of test payment transaction. */
	private Date verifiedDate;				/* Date of amount verification. */
	
	public PaymentMethod() {
		super();
	}

	public Integer getPmID() {
		return pmID;
	}

	public void setPmID(Integer pmID) {
		this.pmID = pmID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(String paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getVerifiedDate() {
		return verifiedDate;
	}

	public void setVerifiedDate(Date verifiedDate) {
		this.verifiedDate = verifiedDate;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("PaymentMethod [pmID=");
		builder.append(pmID);
		builder.append(", type=");
		builder.append(type);
		builder.append(", paymentInfo=");
		builder.append(paymentInfo);
		builder.append(", entryDate=");
		builder.append(entryDate);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", verifiedDate=");
		builder.append(verifiedDate);
		builder.append("]");
		return builder.toString();
	}
	
}
