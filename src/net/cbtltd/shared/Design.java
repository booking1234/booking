/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.client.form.AccessControl;

public class Design
extends Model {

	public static final String INITIAL = "Initial";
	public static final String FINAL = "Final";
	public static final String CREATED = "Created";
	public static final String SUSPENDED = "Suspended";
	public static final String SETTING_UP = "Setup";
	public static final String[] STATES = {INITIAL, CREATED, SETTING_UP, SUSPENDED, FINAL};

	//Designs
	public static final String ACCOUNT_TRANSACTION = "AccountTransaction";
	public static final String ACCOUNT_SUMMARY = "AccountSummary";
	public static final String LICENSE_SALE = "LicenseSale";
	public static final String LICENSE_STATEMENT = "LicenseStatement";
	public static final String PARTY_STATEMENT = "PartyStatement";
	public static final String RESERVATION_CONFIRMATION = "ReservationConfirmation";
	public static final String RESERVATION_DETAIL = "ReservationDetail";
	public static final String RESERVATION_QUOTATION = "ReservationQuotation";
	public static final String RESERVATION_STATEMENT = "ReservationStatement";
	public static final String RESERVATION_AGENT_STATEMENT = "ReservationAgentStatement";
	public static final String RESERVATION_CUSTOMER_STATEMENT = "ReservationCustomerStatement";

	//Route Field
	public static final String ROUTE = "Route";

	//JournalPopup
	public static final String JOURNAL = "Journal";
	public static final String PAYMENT = "Payment";
	public static final String PURCHASE = "Purchase";
	public static final String RECEIPT = "Receipt";
	public static final String RESERVATION_SALE = "ReservationSale";
	public static final String SALE = "Sale";
	
	private String nameservice;
	private String nametype;
    private String namelabel;
	private String entitytype;
    private String frequency;
    private Date lastdate;
    private String notes;
    private Boolean daterangeenabled;
    private Boolean statesenabled;
    private Boolean typesenabled;
    private Boolean currencyenabled;
    private Boolean accountenabled;
    private Boolean processenabled;
    private ArrayList<String> roles;

	public Service service() {return Service.REPORT;}

	public Service getService() {
		try {return nameservice == null ? null : Service.valueOf(nameservice.toUpperCase());}
		catch (Throwable x) {throw new RuntimeException(nameservice);}
	}

	public boolean noService() {
		return nameservice == null || nameservice.isEmpty();
	}

	public boolean hasService() {
		return !noService();
	}

	public String getNameservice() {
		return nameservice;
	}

	public void setNameservice(String nameservice) {
		this.nameservice = nameservice;
	}

	public String getNametype() {
		return nametype;
	}

	public void setNametype(String nametype) {
		this.nametype = nametype;
	}

	public String getNamelabel() {
		return namelabel;
	}

	public void setNamelabel(String namelabel) {
		this.namelabel = namelabel;
	}

	public boolean noNamelabel() {
		return namelabel == null || namelabel.trim().isEmpty();
	}
	
	public boolean hasNamelabel() {
		return !noNamelabel();
	}
	
	public String getEntitytype() {
		return entitytype;
	}

	public void setEntitytype(String entitytype) {
		this.entitytype = entitytype;
	}

	public boolean noEntitytype() {
		return entitytype == null || entitytype.trim().isEmpty();
	}
	
	public boolean hasEntitytype() {
		return !noEntitytype();
	}
	
	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public Date getLastdate() {
		return lastdate;
	}

	public void setLastdate(Date lastdate) {
		this.lastdate = lastdate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public Boolean getStatesenabled() {
		return statesenabled;
	}

	public void setStatesenabled(Boolean statesenabled) {
		this.statesenabled = statesenabled;
	}

	public Boolean getTypesenabled() {
		return typesenabled;
	}

	public void setTypesenabled(Boolean typesenabled) {
		this.typesenabled = typesenabled;
	}

	public Boolean getDaterangeenabled() {
		return daterangeenabled;
	}

	public void setDaterangeenabled(Boolean daterangeenabled) {
		this.daterangeenabled = daterangeenabled;
	}

	public Boolean getCurrencyenabled() {
		return currencyenabled;
	}

	public void setCurrencyenabled(Boolean currencyenabled) {
		this.currencyenabled = currencyenabled;
	}

	public Boolean getAccountenabled() {
		return accountenabled;
	}

	public void setAccountenabled(Boolean accountenabled) {
		this.accountenabled = accountenabled;
	}

	public Boolean getProcessenabled() {
		return processenabled;
	}

	public void setProcessenabled(Boolean processenabled) {
		this.processenabled = processenabled;
	}

	public ArrayList<String> getRoles() {
		return roles;
	}

	public void setRoles(ArrayList<String> roles) {
		this.roles = roles;
	}

	public void setRole(short role) {
		roles = new ArrayList<String>();
		roles.add(String.valueOf(role));
	}

	public boolean noRoles(){
		return roles == null || roles.size() == 0 || !roles.contains(String.valueOf(AccessControl.SUPERUSER));
	}

    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Design [nameservice=");
		builder.append(nameservice);
		builder.append(", nametype=");
		builder.append(nametype);
		builder.append(", entitytype=");
		builder.append(entitytype);
		builder.append(", frequency=");
		builder.append(frequency);
		builder.append(", lastdate=");
		builder.append(lastdate);
		builder.append(", notes=");
		builder.append(notes);
		builder.append(", namerangeenabled=");
		builder.append(namelabel);
		builder.append(", daterangeenabled=");
		builder.append(daterangeenabled);
		builder.append(", statesenabled=");
		builder.append(statesenabled);
		builder.append(", typesenabled=");
		builder.append(typesenabled);
		builder.append(", currencyenabled=");
		builder.append(currencyenabled);
		builder.append(", accountenabled=");
		builder.append(accountenabled);
		builder.append(", processenabled=");
		builder.append(processenabled);
		builder.append(", roles=");
		builder.append(roles);
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
		builder.append(", service()=");
		builder.append(service());
		builder.append("]");
		return builder.toString();
	}

}