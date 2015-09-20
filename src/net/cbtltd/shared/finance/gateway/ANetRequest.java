package net.cbtltd.shared.finance.gateway;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ANetRequest {
	
	public ANetRequest() {
		super();
	}
	
	@XmlAttribute(name = "x_invoice_num")
	private String invoiceNum;

	@XmlAttribute(name = "x_relay_url")
	private String relayUrl;
	
	@XmlAttribute(name = "x_login")
	private String login;
	
	@XmlAttribute(name = "x_fp_sequence")
	private String sequence;
	
	@XmlAttribute(name = "x_fp_timestamp")
	private String timestamp;
	
	@XmlAttribute(name = "x_fp_hash")
	private String hash;
	
	@XmlAttribute(name = "x_version")
	private String version;
	
	@XmlAttribute(name = "x_method")
	private String method;
	
	@XmlAttribute(name = "x_type")
	private String type;
	
	@XmlAttribute(name = "x_amount")
	private String amount;
	
	@XmlAttribute(name = "x_test_request")
	private String testRequest;
	
	@XmlAttribute(name = "notes")
	private String notes;
	
//	@XmlAttribute(name = "buy_button")
//	private String buyButton;

	public String getInvoiceNum() {
		return invoiceNum;
	}

	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public String getRelayUrl() {
		return relayUrl;
	}

	public void setRelayUrl(String relayUrl) {
		this.relayUrl = relayUrl;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTestRequest() {
		return testRequest;
	}

	public void setTestRequest(String testRequest) {
		this.testRequest = testRequest;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

//	public String getBuyButton() {
//		return buyButton;
//	}
//
//	public void setBuyButton(String buyButton) {
//		this.buyButton = buyButton;
//	}

}
