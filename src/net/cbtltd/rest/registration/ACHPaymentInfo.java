package net.cbtltd.rest.registration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

@XmlRootElement(name = "manager_ach")
@XmlAccessorType(XmlAccessType.FIELD)
public class ACHPaymentInfo {

	@XmlElement(name = "bank")
	@SerializedName("bank")
	private String bank;
	@XmlElement(name = "holder_name")
	@SerializedName("holder_name")
	private String holderName;
	@XmlElement(name = "routing_number")
	@SerializedName("routing_number")
	private String routingNumber;
	@XmlElement(name = "accounting_number")
	@SerializedName("accounting_number")
	private String accountingNumber;
	@XmlElement(name = "account_type")
	@SerializedName("account_type")
	private String accountType;
	@XmlElement(name = "social_or_tax_number")
	@SerializedName("social_or_tax_number")
	private String socialOrTaxNumber;
	@XmlElement(name = "social_or_tax_number")
	@SerializedName("social_or_tax_type")
	private String socialOrTaxType;
	@XmlElement(name = "birthdate")
	@SerializedName("birthdate")
	private String birthdate;

	public ACHPaymentInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public String getRoutingNumber() {
		return routingNumber;
	}

	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}

	public String getAccountingNumber() {
		return accountingNumber;
	}

	public void setAccountingNumber(String accountingNumber) {
		this.accountingNumber = accountingNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getSocialOrTaxNumber() {
		return socialOrTaxNumber;
	}

	public void setSocialOrTaxNumber(String socialOrTaxNumber) {
		this.socialOrTaxNumber = socialOrTaxNumber;
	}

	public String getSocialOrTaxType() {
		return socialOrTaxType;
	}

	public void setSocialOrTaxType(String socialOrTaxType) {
		this.socialOrTaxType = socialOrTaxType;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ACHPaymentInfo [");
		sb.append("bank=");
		sb.append(bank);
		sb.append(", holderName=");
		sb.append(holderName);
		sb.append(", routingNumber=");
		sb.append(routingNumber);
		sb.append(", accountingNumber=");
		sb.append(accountingNumber);
		sb.append(", accountType=");
		sb.append(accountType);
		sb.append(", socialOrTaxNumber=");
		sb.append(socialOrTaxNumber);
		sb.append(", socialOrTaxType=");
		sb.append(socialOrTaxType);
		sb.append(", birthdate=");
		sb.append(birthdate);
		sb.append("]");

		return sb.toString();
	}

}
