package net.cbtltd.rest.registration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CancellationPolice {
	
	public CancellationPolice() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CancellationPolice(String cancelationTime, String cancelationRefund, String cancelationTransactionFee) {
		super();
		this.cancelationTime = cancelationTime;
		this.cancelationRefund = cancelationRefund;
		this.cancelationTransactionFee = cancelationTransactionFee;
	}


	@SerializedName("cancelation_time")
	@XmlElement(name="cancelation_time")
	private String cancelationTime;        /* Days count to arrive. */		
	
	@SerializedName("cancelation_refund")
	@XmlElement(name="cancelation_refund")
	private String cancelationRefund;
	
	
	@SerializedName("cancelation_transaction_fee")
	@XmlElement(name="cancelation_transaction_fee")
	private String cancelationTransactionFee;

	
	public String getCancelationTime() {
		return cancelationTime;
	}

	public void setCancelationTime(String cancelationTime) {
		this.cancelationTime = cancelationTime;
	}

	public String getCancelationRefund() {
		return cancelationRefund;
	}

	public void setCancelationRefund(String cancelationRefund) {
		this.cancelationRefund = cancelationRefund;
	}

	public String getCancelationTransactionFee() {
		return cancelationTransactionFee;
	}

	public void setCancelationTransactionFee(String cancelationTransactionFee) {
		this.cancelationTransactionFee = cancelationTransactionFee;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CancellationPolice [cancelationTime=");
		builder.append(cancelationTime);
		builder.append(", \ncancelationRefund=");
		builder.append(cancelationRefund);
		builder.append(", \ncancelationTransactionFee=");
		builder.append(cancelationTransactionFee);
		return builder.toString();
	}
	
}
