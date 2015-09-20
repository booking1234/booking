package net.cbtltd.rest.rent.paymentrequest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RefundPayment")
public class RefundPayment {
	private String username;
	private String password;
	private String propertyCode;
	private String referenceNumber;
	private Integer amount;
	private Integer damageDeposit;
	private String guid;

	@XmlElement(name="username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@XmlElement(name="password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@XmlElement(name="propertyCode")
	public String getPropertyCode() {
		return propertyCode;
	}

	public void setPropertyCode(String propertyCode) {
		this.propertyCode = propertyCode;
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

	@XmlElement(name="damageDeposit")
	public Integer getDamageDeposit() {
		return damageDeposit;
	}

	public void setDamageDeposit(Integer damageDeposit) {
		this.damageDeposit = damageDeposit;
	}

	@XmlElement(name="guid")
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
}