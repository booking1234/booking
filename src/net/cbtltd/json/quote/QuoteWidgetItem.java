/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.json.quote;

import net.cbtltd.json.JSONResponse;

public class QuoteWidgetItem implements JSONResponse {

	private Double price;
	private Double quote;
	private Double deposit;
	private Boolean available;
	private String currency;
	private String message;

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getQuote() {
		return quote;
	}

	public void setQuote(Double quote) {
		this.quote = quote;
	}

	public Double getDeposit() {
		return deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean hasCurrency(String currency) {
		return this.currency != null && this.currency.equalsIgnoreCase(currency);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QuoteWidgetItem [price=");
		builder.append(price);
		builder.append(", quote=");
		builder.append(quote);
		builder.append(", deposit=");
		builder.append(deposit);
		builder.append(", available=");
		builder.append(available);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
	
}
