package net.cbtltd.rest.response;

import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.rest.error.ApiResponse;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.PropertyManagerSupportCC;

@XmlRootElement(name="party")
public class PartyResponse extends ApiResponse {
	private Party additionalPartyInfo;
	private PropertyManagerSupportCC supportCc;
	private String firstName;
	private String lastName;
	private Double amountToCharge;

	public Party getAdditionalPartyInfo() {
		return additionalPartyInfo;
	}

	public void setAdditionalPartyInfo(Party additionalPartyInfo) {
		this.additionalPartyInfo = additionalPartyInfo;
	}

	public String getFirstName() {
		return firstName;
	}

	public PropertyManagerSupportCC getSupportCc() {
		return supportCc;
	}

	public void setSupportCc(PropertyManagerSupportCC supportCc) {
		this.supportCc = supportCc;
	}

	public void setFirstName(String firstName) {
		if(firstName == null) {
			this.firstName = "";
		} else {
			this.firstName = firstName;
		}
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if(lastName == null) {
			this.lastName = "";
		} else {
			this.lastName = lastName;
		}
	}

	public Double getAmountToCharge() {
		return amountToCharge;
	}

	public void setAmountToCharge(Double amountToCharge) {
		this.amountToCharge = amountToCharge;
	}
}
