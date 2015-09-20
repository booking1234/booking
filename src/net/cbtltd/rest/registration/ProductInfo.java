package net.cbtltd.rest.registration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.gson.annotations.SerializedName;

@XmlRootElement(name = "product")
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class ProductInfo {

	private String id;
	private String description;
	private String bathrooms;
	private String location;
	private String country;
	private String address;
	// Agent discount 
	private String commission;
	@SerializedName("cleaning_fee")
	private String cleaningFee;
	@SerializedName("deposit")
	private String securityDeposit;
	private Boolean selected;
	
	public ProductInfo() {
		super();
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String bedrooms;

	public String getBedrooms() {
		return bedrooms;
	}

	public void setBedrooms(String bedrooms) {
		this.bedrooms = bedrooms;
	}

	public String getBathrooms() {
		return bathrooms;
	}

	public void setBathrooms(String bathrooms) {
		this.bathrooms = bathrooms;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	@XmlElement(name = "cleaning_fee")
	public String getCleaningFee() {
		return cleaningFee;
	}

	public void setCleaningFee(String cleaningFee) {
		this.cleaningFee = cleaningFee;
	}

	@XmlElement(name = "deposit")
	public String getSecurityDeposit() {
		return securityDeposit;
	}

	public void setSecurityDeposit(String securityDeposit) {
		this.securityDeposit = securityDeposit;
	}
	
	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

}