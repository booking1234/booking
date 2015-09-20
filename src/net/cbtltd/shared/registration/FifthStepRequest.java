package net.cbtltd.shared.registration;

import javax.xml.bind.annotation.XmlElement;

import net.cbtltd.rest.registration.CreditCardTypes;
import net.cbtltd.rest.registration.PaymentGatewayInfo;

import com.google.gson.annotations.SerializedName;

public class FifthStepRequest extends RegistrationRequest {

	@XmlElement(name = "payment")
	@SerializedName("payment")
	private String payment;
	@XmlElement(name = "gateway")
	@SerializedName("gateway")
	private PaymentGatewayInfo paymentGatewayInfo;
	@XmlElement(name = "credit_card_types")
	@SerializedName("credit_card_types")
	private CreditCardTypes  creditCardTypes;

	public FifthStepRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public PaymentGatewayInfo getPaymentGatewayInfo() {
		return paymentGatewayInfo;
	}

	public void setPaymentGatewayInfo(PaymentGatewayInfo paymentGatewayInfo) {
		this.paymentGatewayInfo = paymentGatewayInfo;
	}

	public CreditCardTypes getCreditCardTypes() {
		return creditCardTypes;
	}

	public void setCreditCardTypes(CreditCardTypes creditCardTypes) {
		this.creditCardTypes = creditCardTypes;
	}

}
