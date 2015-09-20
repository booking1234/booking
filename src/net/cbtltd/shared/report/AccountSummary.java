/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.report;

import java.util.Date;

import net.cbtltd.shared.NameId;
import net.cbtltd.shared.api.HasResponse;

public class AccountSummary implements HasResponse {

	public static final String DATE = "date";
	public static final String PROCESS = "event.process";
	public static final String ACCOUNTNAME = "accountname";
	public static final String ENTITYNAME = "entityname";
	public static final String CREDITAMOUNT = "creditamount";
	public static final String DEBITAMOUNT = "debitamount";

	private Integer year;
	private Integer month;
	private Integer day;
	private Integer count;
	private String groupby;
	private String process;
	private String accountid;
	private String accountname;
	private String entityid;
	private String entityname;
	private Double debitamount;
	private Double creditamount;
	private String currency;
	private int status = 0;

	public String getGroupby() {
		return groupby;
	}

	public void setGroupby(String groupby) {
		this.groupby = groupby;
	}

	public Integer getYear() {
		return year == null ? 1900 : year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month == null ? 0 : month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getDay() {
		return day == null ? 1 : day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Date getDate() {
		return new Date(getYear() - 1900, getMonth(), getDay());
	}
	
	public Integer getCount() {
		return count == null ? 0 : count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getEntityid() {
		return entityid;
	}

	public void setEntityid(String entityid) {
		this.entityid = entityid;
	}

	public String getEntityname() {
		return entityname;
	}

	public String getEntityname(int length) {
		return NameId.trim(entityname, ",", length);
	}

	public void setEntityname(String entityname) {
		this.entityname = entityname;
	}

	public Double getDebitamount() {
		return debitamount == null ? 0.0 : debitamount;
	}

	public void setDebitamount(Double debitamount) {
		this.debitamount = debitamount;
	}

	public Double getCreditamount() {
		return creditamount == null ? 0.0 : creditamount;
	}

	public void setCreditamount(Double creditamount) {
		this.creditamount = creditamount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getAmount() {
		return getDebitamount() - getCreditamount();
	}

	public Double getAverage() {
		return getCount() <= 0 ? 0.0 : getAmount() / getCount();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccountSummary [year=");
		builder.append(year);
		builder.append(", month=");
		builder.append(month);
		builder.append(", day=");
		builder.append(day);
		builder.append(", count=");
		builder.append(count);
		builder.append(", groupby=");
		builder.append(groupby);
		builder.append(", process=");
		builder.append(process);
		builder.append(", accountid=");
		builder.append(accountid);
		builder.append(", accountname=");
		builder.append(accountname);
		builder.append(", entityid=");
		builder.append(entityid);
		builder.append(", entityname=");
		builder.append(entityname);
		builder.append(", debitamount=");
		builder.append(debitamount);
		builder.append(", creditamount=");
		builder.append(creditamount);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
