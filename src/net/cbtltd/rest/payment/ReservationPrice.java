package net.cbtltd.rest.payment;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

import net.cbtltd.rest.response.QuoteDetail;

@XmlAccessorType(XmlAccessType.FIELD)
public class ReservationPrice {
	private List<QuoteDetail> quoteDetails;

	@XmlTransient
	private Double price;
	private Double total;

	@XmlTransient
	@JsonIgnore
	private String currency;
	
	public ReservationPrice() {
		super();
	}

	public List<QuoteDetail> getQuoteDetails() {
		return quoteDetails;
	}

	public void setQuoteDetails(List<QuoteDetail> quoteDetails) {
		this.quoteDetails = quoteDetails;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
