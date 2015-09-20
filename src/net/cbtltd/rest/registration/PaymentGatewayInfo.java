package net.cbtltd.rest.registration;

import javax.xml.bind.annotation.XmlElement;


public class PaymentGatewayInfo {

	private String name;
	private String id;
	
	private String accountId;
	private String transactionKey;
	private String additionalInfo; //delimiter: \t 
	
	
	public PaymentGatewayInfo() {
		super();
	}
	
	
	public PaymentGatewayInfo(String name, String id, String accountId, String transactionKey, String additionalInfo) {
		super();
		this.name = name;
		this.id = id;
		this.accountId = accountId;
		this.transactionKey = transactionKey;
		this.additionalInfo = additionalInfo;
	}


	@XmlElement(name = "accountId")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@XmlElement(name = "transactionKey")
	public String getTransactionKey() {
		return transactionKey;
	}

	public void setTransactionKey(String transactionKey) {
		this.transactionKey = transactionKey;
	}

	@XmlElement(name = "additionalInfo")
	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
