package net.cbtltd.rest.response;

import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.server.ServiceException;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Currency;

@XmlRootElement(name="quote_detail")
public class QuoteDetail {

	private String amount;
	private String currency;
	private String description;
	private String paymentInfo;
	private String text;
	private boolean included;
    
	public QuoteDetail() {
		super();
	}
	
	public QuoteDetail(String amount, String currency, String description, String paymentInfo, String text, boolean included) {
		super();
		if ((currency == null || currency.isEmpty()) && Currency.isConvertible(currency)) {throw new ServiceException(Error.currency_code);}
		setAmount(amount);
		setCurrency(currency);
		setDescription(description);
		setPaymentInfo(paymentInfo);
		setIncluded(included);
		setText(text);
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(String paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isIncluded() {
		return included;
	}

	public void setIncluded(boolean included) {
		this.included = included;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QuoteDetail [amount=");
		builder.append(amount);
		builder.append(", curency=");
		builder.append(currency);
		builder.append(", description=");
		builder.append(description);
		builder.append(", paymentInfo=");
		builder.append(paymentInfo);
		builder.append(", text=");
		builder.append(text);
		builder.append(", included=");
		builder.append(included);
		builder.append("]");
		return builder.toString();
	}
}
