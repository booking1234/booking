/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.json;

public class EnquiryWidgetItem {
	
	private String productid;
	private String productname;
	private String fromdate;
	private String todate;
	private Double quote;
	private Double cost;
	private Double deposit;
	private String currency;
	private String message;
	private Boolean accepted;

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getFromdate() {
		return fromdate;
	}

	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}

	public String getTodate() {
		return todate;
	}

	public void setTodate(String todate) {
		this.todate = todate;
	}

	public Double getQuote() {
		return quote;
	}

	public void setQuote(Double quote) {
		this.quote = quote;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getDeposit() {
		return deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getAccepted() {
		return accepted;
	}

	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EnquiryWidget [productid=");
		builder.append(productid);
		builder.append(", productname=");
		builder.append(productname);
		builder.append(", fromdate=");
		builder.append(fromdate);
		builder.append(", todate=");
		builder.append(todate);
		builder.append(", quote=");
		builder.append(quote);
		builder.append(", cost=");
		builder.append(cost);
		builder.append(", deposit=");
		builder.append(deposit);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", message=");
		builder.append(message);
		builder.append(", accepted=");
		builder.append(accepted);
		builder.append("]");
		return builder.toString();
	}
}
