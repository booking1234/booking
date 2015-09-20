package net.cbtltd.shared.registration;

import javax.xml.bind.annotation.XmlElement;

import com.google.gson.annotations.SerializedName;

public class SeventhStepRequest extends RegistrationRequest {

	@XmlElement(name = "verification_amount")
	@SerializedName("verification_amount")
	private String amount;

	public SeventhStepRequest() {
		// TODO Auto-generated constructor stub
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
}
