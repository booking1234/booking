package net.cbtltd.shared.registration;

import javax.xml.bind.annotation.XmlElement;

import net.cbtltd.rest.registration.ACHPaymentInfo;

import com.google.gson.annotations.SerializedName;

public class SixthStepRequest extends RegistrationRequest {

	@XmlElement(name = "manager_payment_type")
	@SerializedName("manager_payment_type")
	private String managerPaymentType;
	@XmlElement(name = "manager_paypal")
	@SerializedName("manager_paypal")
	private String managerPaypal;
	@XmlElement(name = "manager_ach")
	@SerializedName("manager_ach")
	private ACHPaymentInfo managerACH;

	public String getManagerPaymentType() {
		return managerPaymentType;
	}

	public void setManagerPaymentType(String managerPaymentType) {
		this.managerPaymentType = managerPaymentType;
	}

	public String getManagerPaypal() {
		return managerPaypal;
	}

	public void setManagerPaypal(String managerPaypal) {
		this.managerPaypal = managerPaypal;
	}

	public ACHPaymentInfo getManagerACH() {
		return managerACH;
	}

	public void setManagerACH(ACHPaymentInfo managerACH) {
		this.managerACH = managerACH;
	}

	public SixthStepRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

}
