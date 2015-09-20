package net.cbtltd.server.web;

import java.util.Date;
import java.util.HashMap;

import net.cbtltd.shared.Time;

public class Exchangerates {
	private String disclaimer;
	private String license;
	private Long timestamp;
	private String base;
	private Rates rates;

	public Date getDate() {
		return new Date(timestamp * 1000);
	}

	public boolean isExpired() {
		return getDate().before(Time.addDuration(new Date(), -1.0, Time.DAY)); //refresh daily on demand
	}

	public HashMap<String, Double> getRates() {
		return rates.getRates();
	}
	
	public Double getRate(String currency) {
		return rates.getRate(currency);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Exchangerates [\ndisclaimer=");
		builder.append(disclaimer);
		builder.append("\nlicense=");
		builder.append(license);
		builder.append("\ntimestamp=");
		builder.append(timestamp);
		builder.append("\ndate=");
		builder.append(new Date(timestamp * 1000));
		builder.append("\nbase=");
		builder.append(base);
		builder.append("\nrates=");
		builder.append(rates);
		builder.append("]");
		return builder.toString();
	}
}
