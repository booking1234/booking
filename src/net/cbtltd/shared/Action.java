/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.List;

import net.cbtltd.shared.api.HasEntity;
import net.cbtltd.shared.api.HasItem;

public class Action
implements HasItem, HasEntity {


	protected String id;
	protected String eventid;
	protected String matchid = Model.ZERO;
	protected String organizationid;
	protected String locationid;
	protected String accountid;
	protected String entitytype;
	protected String entityid;
	protected Double quantity;
	protected String unit;
	protected Double unitprice;
	protected String currency;
	protected Double debitamount;
	protected Double creditamount;
	protected String description;
	private List<String> steps;

	public Action() {
	}

	public Action(String accountid) {
		this.accountid = accountid;
	}

	public Action(
			String eventid, 
			String matchid, 
			String organizationid,
			String locationid, 
			String accountid, 
			String entitytype,
			String entityid, 
			Double quantity, 
			String unit, 
			Double unitprice, 
			String currency, 
			Double debitamount,
			Double creditamount, 
			String description
	) {
		this.eventid = eventid;
		this.matchid = matchid;
		this.organizationid = organizationid;
		this.locationid = locationid;
		this.accountid = accountid;
		this.entitytype = entitytype;
		this.entityid = entityid;
		this.quantity = quantity;
		this.unit = unit;
		this.unitprice = unitprice;
		this.currency = currency;
		this.creditamount = creditamount;
		this.debitamount = debitamount;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean noId() {
		return id == null || id.isEmpty() || id.equals(Model.ZERO);
	}

	public String getEventid() {
		return eventid;
	}

	public void setEventid(String eventid) {
		this.eventid = eventid;
	}

	public String getMatchid() {
		return matchid;
	}

	public void setMatchid(String matchid) {
		this.matchid = matchid;
	}

	public String getOrganizationid() {
		return organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public boolean noOrganizationid() {
		return organizationid == null || organizationid.isEmpty()
		|| organizationid == Model.ZERO;
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

	public boolean noAccountid() {
		return accountid == null || accountid.isEmpty() || accountid == Model.ZERO;
	}

	public String getEntitytype() {
		return entitytype;
	}

	public void setEntitytype(String entitytype) {
		this.entitytype = entitytype;
	}

	public boolean noEntitytype() {
		return (entitytype == null || entitytype.isEmpty());
	}

	public boolean hasEntitytype() {
		return !noEntitytype();
	}

	public String getEntityid() {
		return entityid;
	}

	public void setEntityid(String entityid) {
		this.entityid = entityid;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public void addQuantity(Double quantity) {
		this.quantity = (this.quantity == null) ? quantity : this.quantity + quantity;
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean noCurrency() {
		return currency == null || currency.isEmpty();
	}

	public Double getDebitamount() {
		return debitamount;
	}

	public void setDebitamount(Double debitamount) {
		this.debitamount = debitamount;
	}

	public void addDebitamount(Double debitamount) {
		this.debitamount = (this.debitamount == null) ? debitamount : this.debitamount + debitamount;
	}

	public Double getCreditamount() {
		return creditamount;
	}

	public void setCreditamount(Double creditamount) {
		this.creditamount = creditamount;
	}

	public void addCreditamount(Double creditamount) {
		this.creditamount = (this.creditamount == null) ? creditamount : this.creditamount + creditamount;
	}

	public boolean noAmount() {
		return (debitamount == null || debitamount <= 0.0)
		&& (creditamount == null || creditamount <= 0.0);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getSteps() {
		return steps;
	}

	public void setSteps(List<String> steps) {
		this.steps = steps;
	}

	public boolean isEqualto(Action action) {
		return 
		this.organizationid.equalsIgnoreCase(organizationid)
		&& this.locationid.equalsIgnoreCase(locationid)
		&& this.accountid.equalsIgnoreCase(accountid)
		&& this.entitytype.equalsIgnoreCase(entitytype)
		&& this.entityid.equalsIgnoreCase(entityid)
		&& this.unit.equalsIgnoreCase(unit)
		&& this.currency.equalsIgnoreCase(currency);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Action [id=");
		builder.append(id);
		builder.append(", eventid=");
		builder.append(eventid);
		builder.append(", matchid=");
		builder.append(matchid);
		builder.append(", organizationid=");
		builder.append(organizationid);
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
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", debitamount=");
		builder.append(debitamount);
		builder.append(", creditamount=");
		builder.append(creditamount);
		builder.append(", description=");
		builder.append(description);
		builder.append(", steps=");
		builder.append(steps);
		builder.append("]");
		return builder.toString();
	}
}