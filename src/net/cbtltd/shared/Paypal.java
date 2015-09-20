/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import net.cbtltd.shared.api.HasService;


public class Paypal
implements HasService {
	private String id; //trackingId = eventid;
	private String customerid;
	private String supplierid;
	private String name; //folio
	private String state; //paymentExecStatus
	// private String process;
	private String approvalUrl;
	private String feesPayer;
	private String currency;
	private String allowedFundingType; // ECHECK – Electronic check BALANCE –
										// PayPal account balance CREDITCARD –
										// Credit card
	private Double amount;

	public static final String CREATED = "Created";
	public static final String SUCCESS = "Success";
	public static final String FAILURE = "Failure";

	public Service service() {return Service.FINANCE;}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}

	public String getApprovalUrl() {
		return approvalUrl;
	}

	public void setApprovalUrl(String payKey) {
		this.approvalUrl = payKey;
	}

	public boolean noApprovalUrl() {
		return approvalUrl == null || approvalUrl.isEmpty();
	}

	public boolean hasApprovalUrl() {
		return !noApprovalUrl();
	}

	public String getFeesPayer() {
		return feesPayer;
	}

	public void setFeesPayer(String feesPayer) {
		this.feesPayer = feesPayer;
	}

	public String getAllowedFundingType() {
		return allowedFundingType;
	}

	public void setAllowedFundingType(String allowedFundingType) {
		this.allowedFundingType = allowedFundingType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean noCurrency() {
		return currency == null || currency.isEmpty();
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public boolean notValid() {
		return name == null || name.isEmpty() || supplierid == null
				|| supplierid.isEmpty() || currency == null
				|| currency.isEmpty() || amount == null || amount <= 0.0;
	}

	public boolean isValid() {
		return !notValid();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Paypal [id=");
		builder.append(id);
		builder.append(", customerid=");
		builder.append(customerid);
		builder.append(", supplierid=");
		builder.append(supplierid);
		builder.append(", name=");
		builder.append(name);
		builder.append(", state=");
		builder.append(state);
		builder.append(", approvalUrl=");
		builder.append(approvalUrl);
		builder.append(", feesPayer=");
		builder.append(feesPayer);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", allowedFundingType=");
		builder.append(allowedFundingType);
		builder.append(", amount=");
		builder.append(amount);
		builder.append("]");
		return builder.toString();
	}
}
