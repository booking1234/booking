/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

/** The Class Contract carries contract data between client and server. */
public class Contract
extends Process {

	public static final String INITIAL = "Initial";
	public static final String FINAL = "Final";
	public static final String REQUESTED = "Requested";
	public static final String CREATED = "Created";
	public static final String SUSPENDED = "Suspended";
	public static final String[] STATES = {INITIAL, CREATED, SUSPENDED, FINAL};

	public enum Type{Purchase, Reservation, Sale};

	public static final String NONE = "None";
	public static final String INVOICE = "Invoice";
	public static final String INVOICE30 = "Invoice+30Days";
	public static final String STATEMENT = "Statement";
	public static final String STATEMENT30 = "Statement+30Days";
	public static final String STATEMENT60 = "Statement+60Days";
	public static final String STATEMENT90 = "Statement+90Days";
	public static final String[] TERMS = {NONE, INVOICE, INVOICE30, STATEMENT, STATEMENT30, STATEMENT60, STATEMENT90};

	public static final String PARTY = "party.name";
	public static final String NAME = "contract.name";
	public static final String PROCESS = "contract.process";
	public static final String STATE = "contract.state";
	public static final String TYPE = "contract.type";
	public static final String TARGET = "target";
	public static final String CREDITLIMIT = "creditlimit";
	public static final String CREDITTERM = "creditterm";
	public static final String CURRENCY = "contract.currency";
	public static final String DISCOUNT = "discount";
	public static final String DATE = "contract.date";
	public static final String DONEDATE = "contract.duedate";
	public static final String DUEDATE = "contract.donedate";
	public static final String NOTES = "contract.notes";

	protected String partyid;
	protected String partyname;
	protected String currency;
	protected String creditterm;
	protected Double creditlimit;
	protected Double target;
	protected Integer discount;

	public Contract() {}

	public Contract(String name, String organizationid, String partyid) {
		super();
		this.name = name;
		super.setOrganizationid(organizationid);
		this.partyid = partyid;
	}

	public Service service() {return Service.CONTRACT;}

	public Double getDiscountFactor() {
		return (100.0 - getDiscount()) / 100.0;
	}

	public Double getDiscounted(Double value) {
		if (value == null) {return null;}
		else {return value * getDiscountFactor();}
	}

	public String getPartyid() {
		return partyid;
	}

	public void setPartyid(String partyid) {
		this.partyid = partyid;
	}

	public boolean noPartyid() {
		return partyid == null || partyid.isEmpty() || partyid.equalsIgnoreCase(Model.ZERO);
	}

	public String getPartyname() {
		return partyname;
	}

	public void setPartyname(String partyname) {
		this.partyname = partyname;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCreditterm() {
		return creditterm;
	}

	public void setCreditterm(String creditterm) {
		this.creditterm = creditterm;
	}

	public Double getCreditlimit() {
		return creditlimit;
	}

	public void setCreditlimit(Double creditlimit) {
		this.creditlimit = creditlimit;
	}

	public Double getTarget() {
		return target;
	}

	public void setTarget(Double target) {
		this.target = target;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Contract [partyid=");
		sb.append(partyid);
		sb.append(", partyname=");
		sb.append(partyname);
		sb.append(", currency=");
		sb.append(currency);
		sb.append(", creditterm=");
		sb.append(creditterm);
		sb.append(", creditlimit=");
		sb.append(creditlimit);
		sb.append(", target=");
		sb.append(target);
		sb.append(", discount=");
		sb.append(discount);
		sb.append(", parentid=");
		sb.append(parentid);
		sb.append(", actorid=");
		sb.append(actorid);
		sb.append(", locationid=");
		sb.append(locationid);
		sb.append(", parentname=");
		sb.append(parentname);
		sb.append(", actorname=");
		sb.append(actorname);
		sb.append(", process=");
		sb.append(process);
		sb.append(", activity=");
		sb.append(activity);
		sb.append(", notes=");
		sb.append(notes);
		sb.append(", duedate=");
		sb.append(duedate);
		sb.append(", date=");
		sb.append(date);
		sb.append(", donedate=");
		sb.append(donedate);
		sb.append(", organizationid=");
		sb.append(organizationid);
		sb.append(", status=");
		sb.append(status);
		sb.append(", state=");
		sb.append(state);
		sb.append(", values=");
		sb.append(values);
		sb.append(", attributes=");
		sb.append(attributemap);
		sb.append(", texts=");
		sb.append(texts);
		sb.append(", name=");
		sb.append(name);
		sb.append(", id=");
		sb.append(id);
		sb.append(", service()=");
		sb.append(service());
		sb.append("]");
		return sb.toString();
	}
}