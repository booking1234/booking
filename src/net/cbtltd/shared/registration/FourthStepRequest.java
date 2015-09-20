package net.cbtltd.shared.registration;

import javax.xml.bind.annotation.XmlElement;

import net.cbtltd.rest.registration.BillingPoliciesInfo;

import com.google.gson.annotations.SerializedName;

public class FourthStepRequest extends RegistrationRequest {

	public FourthStepRequest() {
		super();
	}

	@XmlElement(name = "billing_policies")
	@SerializedName("billing_policies")
	private BillingPoliciesInfo billingPolicies;

	public BillingPoliciesInfo getBillingPolicies() {
		return billingPolicies;
	}

	public void setBillingPolicies(BillingPoliciesInfo billingPolicies) {
		this.billingPolicies = billingPolicies;
	}

}
