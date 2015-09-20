/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.Date;

public class Currencyrate extends Currency {

	public static final String TOCODE = "tocode";
	public static final String DATE = "date";
	public static final String RATE = "rate";

	private String toid;
	private Date date;
	private Double rate;

	public Currencyrate() {}

	public Currencyrate(String id, String toid, Date date) {
		super();
		this.id = id;
		this.toid = toid;
		this.date = date;
	}

	public Currencyrate(String id, String toid, Date date, Double rate) {
		super();
		this.id = id;
		this.toid = toid;
		this.date = date;
		this.rate = rate;
	}

	public String getToid() {
		return toid;
	}

	public void setToid(String toid) {
		this.toid = toid;
	}

	public boolean noToid() {
		return toid == null || toid.isEmpty();
	}

	public boolean hasToid() {
		return !noToid();
	}

	public boolean hasToid(String toid) {
		return this.toid != null && this.toid.equalsIgnoreCase(toid);
	}

	public boolean before() {
		return getId().compareToIgnoreCase(getToid()) <= 0;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean noDate() {
		return date == null;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public boolean is(String id, String toid) {
		return this.id == id && this.toid == toid;
	}

	public String key() {
		return id + toid + Time.getDay(date);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
//		sb.append(super.toString());
		sb.append(" Id ").append(getId());
		sb.append(" ToId ").append(getToid());
		sb.append(" Date ").append(getDate());
		sb.append(" Rate ").append(getRate());
		return sb.toString();
	}
}
