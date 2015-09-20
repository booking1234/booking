package net.cbtltd.shared;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.gson.annotations.SerializedName;

@XmlRootElement(name = "channel_partner")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
public class ChannelPartner {

	public ChannelPartner() {
		super();
	}

	public ChannelPartner(
		Integer partyId,
		String channelName,
		String logoURL,
		String websiteURL,
		String channelType,
		String coverage,
		String contractType,
		String paymentProcess,
		String payouts,
		String damageCoverage,
		String traffic,
		String commission,
		String listingFees,
		String privacyPolicy,
		String termsConditions,
		Boolean selected,
		String phone,
		String email,
		String officeAddress,
		String description) {
		
		super();
		this.partyId = partyId;
		this.channelName = channelName;
		this.logoURL = logoURL;
		this.websiteURL = websiteURL;
		this.channelType = channelType;
		this.coverage = coverage;
		this.contractType = contractType;
		this.paymentProcess = paymentProcess;
		this.payouts = payouts;
		this.damageCoverage = damageCoverage;
		this.traffic = traffic;
		this.commission = commission;
		this.listingFees = listingFees;
		this.privacyPolicy = privacyPolicy;
		this.termsConditions = termsConditions;
		this.selected = selected;
		this.phone = phone;
		this.email = email;
		this.officeAddress = officeAddress;
		this.description = description;
	}

	private Integer id;
	private Integer partyId;

	@SerializedName("channel_name")
	@XmlElement(name = "channel_name")
	private String channelName;

	@SerializedName("logo_url")
	@XmlElement(name = "logo_url")
	private String logoURL;

	@SerializedName("website_url")
	@XmlElement(name = "website_url")
	private String websiteURL;

	@SerializedName("channel_type")
	@XmlElement(name = "channel_type")
	private String channelType;

	private String coverage;

	@SerializedName("contract_type")
	@XmlElement(name = "contract_type")
	private String contractType;

	@SerializedName("payment_process")
	@XmlElement(name = "payment_process")
	private String paymentProcess;

	private String payouts;

	@SerializedName("damage_coverage")
	@XmlElement(name = "damage_coverage")
	private String damageCoverage;

	private String traffic;

	private String commission;

	@SerializedName("listing_fees")
	@XmlElement(name = "listing_fees")
	private String listingFees;

	@SerializedName("privacy_policy")
	@XmlElement(name = "privacy_policy")
	private String privacyPolicy;

	@SerializedName("terms_conditions")
	@XmlElement(name = "terms_conditions")
	private String termsConditions;

	private Boolean selected;

	@SerializedName("phone")
	@XmlElement(name = "phone")
	private String phone;

	@SerializedName("email")
	@XmlElement(name = "email")
	private String email;

	@SerializedName("office_address")
	@XmlElement(name = "office_address")
	private String officeAddress;

	@SerializedName("description")
	@XmlElement(name = "description")
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogoURL() {
		return logoURL;
	}

	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}

	public String getWebsiteURL() {
		return websiteURL;
	}

	public void setWebsiteURL(String websiteURL) {
		this.websiteURL = websiteURL;
	}

	public String getPrivacyPolicy() {
		return privacyPolicy;
	}

	public void setPrivacyPolicy(String privacyPolicy) {
		this.privacyPolicy = privacyPolicy;
	}

	public String getTermsConditions() {
		return termsConditions;
	}

	public void setTermsConditions(String termsConditions) {
		this.termsConditions = termsConditions;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getContractType() {
		return contractType;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getCoverage() {
		return coverage;
	}

	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

	public String getConrtactType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getPaymentProcess() {
		return paymentProcess;
	}

	public void setPaymentProcess(String paymentProcess) {
		this.paymentProcess = paymentProcess;
	}

	public String getPayouts() {
		return payouts;
	}

	public void setPayouts(String payouts) {
		this.payouts = payouts;
	}

	public String getDamageCoverage() {
		return damageCoverage;
	}

	public void setDamageCoverage(String damageCoverage) {
		this.damageCoverage = damageCoverage;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public String getListingFees() {
		return listingFees;
	}

	public void setListingFees(String listingFees) {
		this.listingFees = listingFees;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlTransient
	public Integer getPartyId() {
		return partyId;
	}

	public void setPartyId(Integer partyId) {
		this.partyId = partyId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChannelPartner [id=");
		builder.append(getId());
		builder.append(", channelName=");
		builder.append(channelName);
		builder.append(", logoURL=");
		builder.append(logoURL);
		builder.append(", websiteURL=");
		builder.append(websiteURL);
		builder.append(", channelType=");
		builder.append(channelType);
		builder.append(", coverage=");
		builder.append(coverage);
		builder.append(", contactType=");
		builder.append(contractType);
		builder.append(", paymentProcess=");
		builder.append(paymentProcess);
		builder.append(", payouts=");
		builder.append(payouts);
		builder.append(", damageCoverage=");
		builder.append(damageCoverage);
		builder.append(", traffic=");
		builder.append(traffic);
		builder.append(", commission=");
		builder.append(commission);
		builder.append(", listing_fees=");
		builder.append(listingFees);
		builder.append(", privacyPolicy=");
		builder.append(privacyPolicy);
		builder.append(", termsConditions=");
		builder.append(termsConditions);
		builder.append(", selected=");
		builder.append(selected.toString());
		builder.append("]");
		return builder.toString();
	}

}
