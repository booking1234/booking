/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	3.0.10
 */
package net.cbtltd.shared.finance;

import java.util.Date;

/**
 * The Class GatewayPayment.
 */
public class GatewayAction {
	private String id; // Razor transaction ID
	private String altid; // gateway transaction ID
	private String altpartyid; // gateway ID
	private String name;
	private String type;
	private String process;
	private String number;
	private String emailaddress;
	private String ipaddress;
	private String postaladdress;
	private String postalcode;
	private String country;
	private String month;
	private String year;
	private String code;
	private String approval; // approval code
	private String notes;
	private Date date;
	private Integer amount; // in cents
	private Double value;
	private String currency;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAltid() {
		return altid;
	}

	public void setAltid(String altid) {
		this.altid = altid;
	}

	public String getAltpartyid() {
		return altpartyid;
	}

	public void setAltpartyid(String altpartyid) {
		this.altpartyid = altpartyid;
	}

	public boolean noAltpartyid() {
		return altpartyid == null || altpartyid.isEmpty();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getPostaladdress() {
		return postaladdress;
	}

	public void setPostaladdress(String postaladdress) {
		this.postaladdress = postaladdress;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GatewayPayment [id=");
		builder.append(id);
		builder.append(", altid=");
		builder.append(altid);
		builder.append(", altpartyid=");
		builder.append(altpartyid);
		builder.append(", name=");
		builder.append(name);
		builder.append(", type=");
		builder.append(type);
		builder.append(", process=");
		builder.append(process);
		builder.append(", number=");
		builder.append(number);
		builder.append(", emailaddress=");
		builder.append(emailaddress);
		builder.append(", ipaddress=");
		builder.append(ipaddress);
		builder.append(", postaladdress=");
		builder.append(postaladdress);
		builder.append(", postalcode=");
		builder.append(postalcode);
		builder.append(", country=");
		builder.append(country);
		builder.append(", month=");
		builder.append(month);
		builder.append(", year=");
		builder.append(year);
		builder.append(", code=");
		builder.append(code);
		builder.append(", approval=");
		builder.append(approval);
		builder.append(", notes=");
		builder.append(notes);
		builder.append(", date=");
		builder.append(date);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", value=");
		builder.append(value);
		builder.append(", currency=");
		builder.append(currency);
		builder.append("]");
		return builder.toString();
	}
}
