package net.cbtltd.trial.test;

import java.util.Date;
import java.util.HashMap;

/**
 * The Class Exchangerates.
 */
public class Exchangerates {
	
	private String disclaimer;
	private String license;
	private Long timestamp;
	private String base;
	private Rates rates;

	/**
	 * Gets the date from the timestamp.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return new Date(timestamp * 1000);
	}

	/**
	 * Checks if is expired.
	 *
	 * @return true, if is expired
	 */
	public boolean isExpired() {
		long now = System.currentTimeMillis();
		return getDate().before(new Date(now - 24 * 60 * 60 * 1000)); // or Calendar or timestamp arithmetic
	}

	/**
	 * Gets the map of currency codes to exchange rates.
	 *
	 * @return the map
	 */
	public HashMap<String, Double> getRates() {
		return rates.getRates();
	}
	
	/**
	 * Gets the rate.
	 *
	 * @param currency the currency
	 * @return the rate
	 */
	public Double getRate(String currency) {
		return rates.getRate(currency);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
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
		builder.append(getDate());
		builder.append("\nbase=");
		builder.append(base);
		builder.append("\nrates=");
		builder.append(rates);
		builder.append("]");
		return builder.toString();
	}
}
