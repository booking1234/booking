/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.reservation;

import java.util.ArrayList;

import net.cbtltd.shared.Alert;
import net.cbtltd.shared.DoubleResponse;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Price;

public class PriceResponse extends DoubleResponse {

	private Double deposit;
	private Double quote;
	private Double extra;
	private Double cost;
	private Double discountfactor;
	private String currency;
	
	private ArrayList<Alert> alerts;
	private ArrayList<NameId> collisions;
	private ArrayList<Price> quotedetail;

	public Double getDeposit() {
		return deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public Double getDeposit(Double amount) {
		return (amount == null || deposit == null) ? 0.0 : amount * deposit / 100.0;
	}

	public Double getQuote() {
		return quote == null ? 0.0 : quote;
	}

	public void setQuote(Double quote) {
		this.quote = quote;
	}

	public boolean noQuote() {
		return quote == null;
	}
	
	public boolean hasQuote() {
		return !noQuote();
	}
	
	public Double getExtra() {
		return extra;
	}

	public void setExtra(Double extra) {
		this.extra = extra;
	}

	public Double getCost() {
		return cost == null ? 0.0 : cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public boolean noCost() {
		return cost == null;
	}
	
	public boolean hasCost() {
		return !noCost();
	}
	
//	public Double getCostquoteratio() {
//		return getQuote() == 0.0 ? 1.0 : getCost() / getQuote();
//	}

	public Double getDiscountfactor() {
		return discountfactor;
	}

	public void setDiscountfactor(Double discountfactor) {
		this.discountfactor = discountfactor;
	}

	public Double getDiscount() {
		return discountfactor == null ? 0.0 : (1.0 - discountfactor) * 100.0;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public ArrayList<Alert> getAlerts() {
		return alerts;
	}

	public void setAlerts(ArrayList<Alert> alerts) {
		this.alerts = alerts;
	}

	public boolean noAlerts() {
		return alerts == null || alerts.isEmpty();
	}

	public boolean hasAlerts() {
		return !noAlerts();
	}

	public ArrayList<NameId> getCollisions() {
		return collisions;
	}

	public void setCollisions(ArrayList<NameId> collisions) {
		this.collisions = collisions;
	}

	public void addCollisions(ArrayList<NameId> collisions) {
		if (this.collisions == null) this.collisions = new ArrayList<NameId>();
		this.collisions.addAll(collisions);
	}

	public boolean noCollisions() {
		return collisions == null || collisions.isEmpty();
	}

	public boolean hasCollisions() {
		return !noCollisions();
	}

	public ArrayList<Price> getQuotedetail() {
		return quotedetail;
	}

	public void setQuotedetail(ArrayList<Price> quotedetail) {
		this.quotedetail = quotedetail;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PriceResponse [deposit=");
		builder.append(deposit);
		builder.append(", quote=");
		builder.append(quote);
		builder.append(", extra=");
		builder.append(extra);
		builder.append(", cost=");
		builder.append(cost);
		builder.append(", discountfactor=");
		builder.append(discountfactor);
		builder.append(", currency=");
		builder.append(currency);
		builder.append("\nalerts=");
		builder.append(alerts);
		builder.append("\ncollisions=");
		builder.append(collisions);
		builder.append("\nquotedetail=");
		builder.append(quotedetail);
		builder.append(", value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}
}
