/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.journal;

import net.cbtltd.shared.Event;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.api.HasItem;

public class EventJournal extends Event<HasItem> {

	public static final String ACCOUNTNAME = "account.name";
	public static final String ENTITYNAME = "entityname";
	public static final String LOCATIONNAME = "location.name";
	public static final String UNITPRICE = "journal.unitprice";
	public static final String CURRENCY = "journal.currency";
	public static final String QUANTITY = "journal.quantity";
	public static final String UNIT = "journal.unit";
	public static final String DEBITAMOUNT = "journal.debitamount";
	public static final String CREDITAMOUNT = "journal.creditamount";
	public static final String DESCRIPTION = "journal.description";

	protected String actionid;
	protected String matchid;
	protected String locationid;
	protected String accountid;
	protected String entitytype;
	protected String entityid;
	protected Double quantity;
	protected String unit;
	protected Double unitprice;
	protected Double debitamount;
	protected Double creditamount;
	protected String currency;
	protected String description;
	protected String accountname;
	protected String entityname;
	protected String locationname;
	protected String groupby;

	public String getActionid() {
		return actionid;
	}

	public void setActionid(String actionid) {
		this.actionid = actionid;
	}

	public String getMatchid() {
		return matchid;
	}

	public void setMatchid(String matchid) {
		this.matchid = matchid;
	}

	public String getLocationid() {
		return locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public boolean hasAccountid(String accountid) {
		return accountid != null && this.accountid != null && this.accountid.equals(accountid);
	}
	
	public String getEntitytype() {
		return entitytype;
	}

	public void setEntitytype(String entitytype) {
		this.entitytype = entitytype;
	}

	public String getEntityid() {
		return entityid;
	}

	public void setEntityid(String entityid) {
		this.entityid = entityid;
	}

	public Double getQuantity() {
		return quantity == null ? 0.0 : quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(Double unitprice) {
		this.unitprice = unitprice;
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

	public Double getAmount() {
		return getDebitamount() - getCreditamount();
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

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
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

	public String getLocationname() {
		return locationname;
	}

	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}

	public String getGroupby() {
		return groupby;
	}

	public void setGroupby(String groupby) {
		this.groupby = groupby;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EventJournal [actionid=");
		builder.append(actionid);
		builder.append(", matchid=");
		builder.append(matchid);
		builder.append(", locationid=");
		builder.append(locationid);
		builder.append(", accountid=");
		builder.append(accountid);
		builder.append(", entitytype=");
		builder.append(entitytype);
		builder.append(", entityid=");
		builder.append(entityid);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", unit=");
		builder.append(unit);
		builder.append(", unitprice=");
		builder.append(unitprice);
		builder.append(", debitamount=");
		builder.append(debitamount);
		builder.append(", creditamount=");
		builder.append(creditamount);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", description=");
		builder.append(description);
		builder.append(", accountname=");
		builder.append(accountname);
		builder.append(", entityname=");
		builder.append(entityname);
		builder.append(", locationname=");
		builder.append(locationname);
		builder.append(", groupby=");
		builder.append(groupby);
		builder.append(", type=");
		builder.append(type);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", items=");
		builder.append(items);
		builder.append(", parentid=");
		builder.append(parentid);
		builder.append(", actorid=");
		builder.append(actorid);
		builder.append(", parentname=");
		builder.append(parentname);
		builder.append(", actorname=");
		builder.append(actorname);
		builder.append(", process=");
		builder.append(process);
		builder.append(", activity=");
		builder.append(activity);
		builder.append(", notes=");
		builder.append(notes);
		builder.append(", duedate=");
		builder.append(duedate);
		builder.append(", date=");
		builder.append(date);
		builder.append(", donedate=");
		builder.append(donedate);
		builder.append(", organizationid=");
		builder.append(organizationid);
		builder.append(", status=");
		builder.append(status);
		builder.append(", state=");
		builder.append(state);
		builder.append(", values=");
		builder.append(values);
		builder.append(", attributes=");
		builder.append(attributemap);
		builder.append(", texts=");
		builder.append(texts);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
}
