package net.cbtltd.shared.account;

import java.util.Date;

public class AccountAction {
	private String accountid;
	private String entityid;
	private String eventid;
	private String organizationid;
	private String state;
	private String currency;
	private Date fromdate;
	private Date todate;
	private Boolean test = false;
	private int numrows = Integer.MAX_VALUE;

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public String getEntityid() {
		return entityid;
	}

	public void setEntityid(String entityid) {
		this.entityid = entityid;
	}

	public String getEventid() {
		return eventid;
	}

	public void setEventid(String eventid) {
		this.eventid = eventid;
	}

	public String getOrganizationid() {
		return organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getFromdate() {
		return fromdate;
	}

	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}

	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}

	public Boolean getTest() {
		return test;
	}

	public void setTest(Boolean test) {
		this.test = test;
	}

	public int getNumrows() {
		return numrows;
	}

	public void setNumrows(int numrows) {
		this.numrows = numrows;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountAction [accountid=");
		builder.append(accountid);
		builder.append(", entityid=");
		builder.append(entityid);
		builder.append(", eventid=");
		builder.append(eventid);
		builder.append(", organizationid=");
		builder.append(organizationid);
		builder.append(", state=");
		builder.append(state);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", fromdate=");
		builder.append(fromdate);
		builder.append(", todate=");
		builder.append(todate);
		builder.append(", test=");
		builder.append(test);
		builder.append(", numrows=");
		builder.append(numrows);
		builder.append("]");
		return builder.toString();
	}

}
