package net.cbtltd.rest.registration;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.shared.Party;

import com.google.gson.annotations.SerializedName;

@XmlRootElement(name = "billing_policies")
@XmlAccessorType(XmlAccessType.FIELD)
public class BillingPoliciesInfo {

	
	public static final String ONE_PAYMENT_VALUE = "100";
	public static final String ONE_PAYMENT_TYPE = Party.DEPOSITS[0];
	
	public BillingPoliciesInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BillingPoliciesInfo(String numberOfPayments, String firstPaymentAmount, String firstPaymentType, String secondPaymentType,
			String cancelationType, List<CancellationPolice> cancellationPolicies, String damageCoverageType, String damageInsurance,
			String currency) {
		super();
		this.numberOfPayments = numberOfPayments;
		this.firstPaymentAmount = firstPaymentAmount;
		this.firstPaymentType = firstPaymentType;
		this.secondPaymentType = secondPaymentType;
		this.cancelationType = cancelationType;
		this.cancellationPolicies = cancellationPolicies;
		this.damageCoverageType = damageCoverageType;
		this.damageInsurance = damageInsurance;
		this.currency = currency;
	}



	@XmlElement(name = "number_of_payments")
	@SerializedName("number_of_payments")
	private String numberOfPayments; // 1 - single payment; 2 - two payments;

	@XmlElement(name = "first_payment_amount")
	@SerializedName("first_payment_amount")
	private String firstPaymentAmount;	// firsts payment amount;
	
	@XmlElement(name = "first_payment_type")
	@SerializedName("first_payment_type")
	private String firstPaymentType;	// 1 - % of booking, 2 - flat rate.
	
	@XmlElement(name = "second_payment_type")
	@SerializedName("second_payment_type")
	private String secondPaymentType;	/*  0 - on arrival			
											1 - up to 1 day
											2 - up to 3 day
											3 - up to 5 day
											4 - up to 7 day
											5 - up to 10 day
											6 - up to 2 weeks
											7 - up to 1 month
											8 - up to 2 months
											9 - up to 3 months	*/
	@XmlElement(name = "cancelation_type")
	@SerializedName("cancelation_type")
	private String cancelationType;		// This value is set based on whether the PM can cancel the reservation up to the day of the trip or up to the fixed period:
										//  1 - traveler can cancel up to the day of the trip and still receive fill refunds;
										//  2 - traveler can cancel up to the fixed period;
	
	@XmlElement(name = "cancelation_policies")
	@SerializedName("cancelation_policies")
	private List<CancellationPolice> cancellationPolicies;
	
	@XmlElement(name = "check_in_time")
	@SerializedName("check_in_time")
	private String checkInTime;
	
	@XmlElement(name = "check_out_time")
	@SerializedName("check_out_time")
	private String checkOutTime;
	
	@XmlElement(name = "terms_link")
	@SerializedName("terms_link")
	private String termsLink;
	
	@XmlElement(name = "damage_coverage_type")
	@SerializedName("damage_coverage_type")
	private String damageCoverageType;	 // 0 - none, 1 - refundable deposit, 2 - insurance
	
	@XmlElement(name = "damage_insurance")
	@SerializedName("damage_insurance")
	private String damageInsurance;
	
	@XmlElement(name = "currency")
	@SerializedName("currency")
	private String currency;				// see REG_CURRENCY enum

	
	
	public String getCancelationType() {
		return cancelationType;
	}

	public void setCancelationType(String cancelationType) {
		this.cancelationType = cancelationType;
	}

	public String getNumberOfPayments() {
		return numberOfPayments;
	}

	public void setNumberOfPayments(String numberOfPayments) {
		this.numberOfPayments = numberOfPayments;
	}
	
	public String getFirstPaymentAmount() {
		return firstPaymentAmount;
	}

	public void setFirstPaymentAmount(String firstPaymentAmount) {
		this.firstPaymentAmount = firstPaymentAmount;
	}

	public String getFirstPaymentType() {
		return firstPaymentType;
	}

	public void setFirstPaymentType(String firstPaymentType) {
		this.firstPaymentType = firstPaymentType;
	}

	public String getSecondPaymentType() {
		return secondPaymentType;
	}

	public void setSecondPaymentType(String secondPaymentType) {
		this.secondPaymentType = secondPaymentType;
	}

	public List<CancellationPolice> getCancellationPolicies() {
		return cancellationPolicies;
	}

	public void setCancellationPolicies(List<CancellationPolice> cancellationPolicies) {
		this.cancellationPolicies = cancellationPolicies;
	}

	public String getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}

	public String getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public String getTermsLink() {
		return termsLink;
	}

	public void setTermsLink(String termsLink) {
		this.termsLink = termsLink;
	}

	public String getDamageCoverageType() {
		return damageCoverageType;
	}

	public void setDamageCoverageType(String damageCoverageType) {
		this.damageCoverageType = damageCoverageType;
	}

	public String getDamageInsurance() {
		return damageInsurance;
	}

	public void setDamageInsurance(String damageInsurance) {
		this.damageInsurance = damageInsurance;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}


}
