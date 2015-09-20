package net.cbtltd.rest.rent.paymentresponse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="CreditCardPaymentResponse")
public class CreditCardPaymentResponse {
	private String referenceNumber;
	private Integer amount;
	private Integer convenienceFee;
	private String token;

	public CreditCardPaymentResponse() {
		super();
	}
	
	@XmlElement(name="referenceNumber")
	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	@XmlElement(name="amount")
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	@XmlElement(name="convenienceFee")
	public Integer getConvenienceFee() {
		return convenienceFee;
	}

	public void setConvenienceFee(Integer convenienceFee) {
		this.convenienceFee = convenienceFee;
	}
	
	@XmlElement(name="token")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}

// <referenceNumber>4852700:47</referenceNumber>
// <amount>150000</amount>
// <convenienceFee>9999</convenienceFee>