package net.cbtltd.json.finance;

import net.cbtltd.json.JSONResponse;

public class FinanceWidgetItem implements JSONResponse {
	
	private String name;
	private String owner;
	private String account;
	private String type;
	private String bankname;
	private String branchname;
	private String branchnumber;
	private String accountnumber;
	private String ibanswift;
	private String contactperson;
	private String phonenumber;
	private String currency;
	private String currencyname;
	private String notes;
	private String message;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public String getBranchnumber() {
		return branchnumber;
	}

	public void setBranchnumber(String branchnumber) {
		this.branchnumber = branchnumber;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public String getIbanswift() {
		return ibanswift;
	}

	public void setIbanswift(String ibanswift) {
		this.ibanswift = ibanswift;
	}

	public String getContactperson() {
		return contactperson;
	}

	public void setContactperson(String contactperson) {
		this.contactperson = contactperson;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyname() {
		return currencyname;
	}

	public void setCurrencyname(String currencyname) {
		this.currencyname = currencyname;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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
		builder.append("FinanceWidgetItem [name=");
		builder.append(name);
		builder.append(", owner=");
		builder.append(owner);
		builder.append(", account=");
		builder.append(account);
		builder.append(", type=");
		builder.append(type);
		builder.append(", bankname=");
		builder.append(bankname);
		builder.append(", branchname=");
		builder.append(branchname);
		builder.append(", branchnumber=");
		builder.append(branchnumber);
		builder.append(", accountnumber=");
		builder.append(accountnumber);
		builder.append(", ibanswift=");
		builder.append(ibanswift);
		builder.append(", contactperson=");
		builder.append(contactperson);
		builder.append(", phonenumber=");
		builder.append(phonenumber);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", currencyname=");
		builder.append(currencyname);
		builder.append(", notes=");
		builder.append(notes);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}

}
