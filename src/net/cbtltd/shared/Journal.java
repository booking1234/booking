/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import net.cbtltd.shared.api.HasEntity;
import net.cbtltd.shared.api.HasItem;
import net.cbtltd.shared.api.HasResponse;

public class Journal implements HasItem, HasEntity, HasResponse {

	public static final String ACCOUNTNAME = "account.name";
	public static final String LOCATIONNAME = "location.name";
	public static final String ORGANIZATIONNAME = "organization.name";
	public static final String UNITPRICE = "journal.unitprice";
	public static final String CURRENCY = "journal.currency";
	public static final String QUANTITY = "journal.quantity";
	public static final String UNIT = "journal.unit";
	public static final String DEBITAMOUNT = "journal.debitamount";
	public static final String CREDITAMOUNT = "journal.creditamount";
	public static final String DESCRIPTION = "journal.description";

	private String id;
	private String eventid;
	private String matchid = Model.ZERO;
	private String organizationid;
	private String locationid;
	private String accountid;
	private String entitytype;
	private String entityid;
	private Double quantity;
	private String unit;
	private Double unitprice;
	private String currency;
	private Double debitamount;
	private Double creditamount;
	private String description;
	private String entityname;
	private String locationname;
	private String accountname;
	private String organizationname;
	private int status = 0;
	
	public Journal() {}
	
	public Journal(
			String eventid, 
			String matchid, 
			String organizationid,
			String organizationname,
			String locationid,
			String accountid, 
			String accountname, 
			String entitytype,
			String entityid, 
			String entityname, 
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
		this.organizationname = organizationname;
		this.locationid = locationid;
		this.accountid = accountid;
		this.accountname = accountname;
		this.entitytype = entitytype;
		this.entityid = entityid;
		this.entityname = entityname;
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

	public boolean hasOrganizationid(String organizationid) {
		return this.organizationid != null && organizationid != null && this.organizationid.equalsIgnoreCase(organizationid);
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

	public String getEntitytype() {
		return entitytype;
	}

	public void setEntitytype(String entitytype) {
		this.entitytype = entitytype;
	}
	
	public boolean hasEntitytype(Model.Type type) {
		return type == null || this.entitytype == null ? false : this.entitytype.equalsIgnoreCase(type.name());
	}
	
	public String getEntityid() {
		return entityid;
	}

	public void setEntityid(String entityid) {
		this.entityid = entityid;
	}

	public boolean hasEntityid(String entityid) {
		return entityid == null || this.entityid == null ? false : this.entityid.equalsIgnoreCase(entityid);
	}
	
	public boolean isGateway() {
		return hasEntitytype(NameId.Type.Finance) && (hasEntityid(Finance.CBT_USD_FINANCE) || hasEntityid(Finance.CBT_ZAR_FINANCE));
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

	public Double getDebitamount() {
		return debitamount == null ? 0.0 : debitamount;
	}

	public void setDebitamount(Double debitamount) {
		this.debitamount = debitamount;
	}

	public void addDebitamount(Double debitamount) {
		this.debitamount = (this.debitamount == null) ? debitamount : this.debitamount + debitamount;
	}

	public Double getCreditamount() {
		return creditamount == null ? 0.0 : creditamount;
	}

	public void setCreditamount(Double creditamount) {
		this.creditamount = creditamount;
	}

	public void addCreditamount(Double creditamount) {
		this.creditamount = (this.creditamount == null) ? creditamount : this.creditamount + creditamount;
	}

	public Double getAmount() {
		return getDebitamount() - getCreditamount();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEntityname() {
		return entityname;
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

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getOrganizationname() {
		return organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean entityError() {
		return false; //TODO: (entitytype != null && entityid == null) || (entitytype == null && entityid != null);
	}

	public boolean isEqualto(Journal action) {
		return 
		((this.organizationid == null && action.organizationid == null) || this.organizationid.equalsIgnoreCase(action.organizationid))
		&& ((this.locationid == null && action.locationid == null ) || this.locationid.equalsIgnoreCase(action.locationid))
		&& ((this.accountid == null && action.accountid == null ) || this.accountid.equalsIgnoreCase(action.accountid))
		&& ((this.entitytype == null && action.entitytype == null ) || this.entitytype.equalsIgnoreCase(action.entitytype))
		&& ((this.entityid == null && action.entityid == null ) || this.entityid.equalsIgnoreCase(action.entityid))
		&& ((this.unit == null && action.unit == null ) || this.unit.equalsIgnoreCase(action.unit))
		&& ((this.currency == null && action.currency == null ) || this.currency.equalsIgnoreCase(action.currency))
		&& !("Account".equalsIgnoreCase(entitytype) && !entityid.equals("0"))
		;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Journal [id=");
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
		builder.append(", entityname=");
		builder.append(entityname);
		builder.append(", locationname=");
		builder.append(locationname);
		builder.append(", accountname=");
		builder.append(accountname);
		builder.append("]");
		return builder.toString();
	}
}
