package net.cbtltd.shared.finance.gateway;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="bank_account_info")
@XmlAccessorType(XmlAccessType.FIELD)
public class BankAccountInfo {
	public BankAccountInfo() {
		super();
	}
	
	@XmlElement(name="holder_name")
	private String bankAccountName;
	
	@XmlElement(name="accounting_number")
	private String bankAccountNumber;
	
	@XmlElement(name="bank_check_number")
	private String bankCheckNumber;
	
	@XmlElement(name="account_type")
	private String accountType;
	
	@XmlElement(name="bank")
	private String bankName;
	
	@XmlElement(name="routing_number")
	private String routingNumber;

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getBankCheckNumber() {
		return bankCheckNumber;
	}

	public void setBankCheckNumber(String bankCheckNumber) {
		this.bankCheckNumber = bankCheckNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getRoutingNumber() {
		return routingNumber;
	}

	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
}
