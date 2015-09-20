/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.cbtltd.shared.api.HasData;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

@XmlRootElement(name = "lease")
@Description(value = "Lease of a property between two dates", target = DocTarget.RESPONSE)
public class Lease
extends Process
implements HasData {

	public enum State {Initial, Created, Leased, Closed, Cancelled, Final};
	public static final String[] STATES = { State.Initial.name(), State.Created.name(), State.Leased.name(), State.Closed.name(), State.Cancelled.name(), State.Final.name() };
	public static final String[] WORKFLOW_STATES = { State.Created.name(), State.Leased.name(), State.Closed.name()};

	//Sortable columns
	public static final String NAME = "lease.name"; 
	public static final String DATE = "lease.date";
	public static final String FROMDATE = "lease.fromdate";
	public static final String TODATE = "lease.todate";
	public static final String ACTORNAME = "lease.actorname";
	public static final String PROCESS = "lease.process";

	private String agentid;
	private String customerid;
	private String productid;
	private String financeid;
	private String market;
	private String outcome;
	private String unit;
	private String currency;
	private Date fromdate;
	private Date todate;
	private Double price;
	private Double deposit;
	private ArrayList<Rule> rules;
	private ArrayList<Task> tasks;
	
	public Lease() {}

	public Lease(String id) {
		this.id = id;
	}

	public Service service() {return Service.LEASE;}

	public boolean isValidState () {return hasState(STATES);}
	
	public boolean notValidState () {return !isValidState();}
	
	public String getLeaseid() {
		return id;
	}

	public String getSupplierid() {
		return organizationid;
	}

	public void setSupplierid(String supplierid) {
		this.organizationid = supplierid;
	}

	public boolean noSupplierid() {
		return organizationid == null || organizationid.isEmpty() || organizationid.equalsIgnoreCase(Model.ZERO);
	}
	
	public String getEntitytype() {
		return NameId.Type.Product.name();
	}
	
	public String getEntityid() {
		return productid;
	}
	
	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public boolean noProductid() {
		return productid == null || productid.isEmpty() || productid.equalsIgnoreCase(Model.ZERO);
	}
	
	public boolean hasProductid() {
		return !noProductid();
	}
	
	public String getFinanceid() {
		return financeid;
	}

	public void setFinanceid(String financeid) {
		this.financeid = financeid;
	}

	@XmlTransient
	public String getProcess() {
		return NameId.Type.Lease.name();
	}

	public void setDate(Date date) {
		super.setDate(date);
	}

	public Date getFromdate() {
		return fromdate;
	}

	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}

	public boolean noFromdate() {
		return fromdate == null;
	}

	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}

	public boolean noTodate() {
		return todate == null;
	}

	public Double getDuration(Time unit) {
		return Time.getDuration(fromdate, todate, unit);
	}

	public boolean noDuration(Double duration, Time unit) {
		return noFromdate() || noTodate() || Time.getDuration(fromdate, todate, unit) < duration;
	}

	public Date getWorkflowDate(String datename) {
		if (DATE.equalsIgnoreCase(datename)) {return date;}
		else if (FROMDATE.equalsIgnoreCase(datename)) {return fromdate;}
		else if (TODATE.equalsIgnoreCase(datename)) {return todate;}
		else {return null;}
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDeposit() {
		return deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public boolean noCustomerid() {
		return customerid == null || customerid.isEmpty()
				|| customerid.equalsIgnoreCase(Model.ZERO);
	}

	public boolean hasCustomerid() {
		return !noCustomerid();
	}

	public String getAgentid() {
		return agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}

	public boolean noAgentid() {
		return agentid == null || agentid.isEmpty()
				|| agentid.equalsIgnoreCase(Model.ZERO);
	}

	public boolean hasAgentid() {
		return !noAgentid();
	}

	public boolean hasAgentid(String agentid) {
		return this.agentid != null && this.agentid.equals(agentid);
	}

	@XmlTransient
	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public boolean noMarket() {
		return market == null || market.isEmpty();
	}

	@XmlTransient
	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public boolean noUnit() {
		return unit == null || unit.isEmpty();
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

	public boolean hasCurrency() {
		return !noCurrency();
	}

	public boolean hasCurrency(String currency) {
		return hasCurrency() && this.currency.equalsIgnoreCase(currency);
	}

	public boolean notCurrency(String currency) {
		return !hasCurrency(currency);
	}

	public ArrayList<Rule> getRules() {
		return rules;
	}

	public void setRules(ArrayList<Rule> rules) {
		this.rules = rules;
	}

	public boolean noRules() {
		return rules == null || rules.isEmpty();
	}

	public boolean hasRules() {
		return !noRules();
	}

	public ArrayList<Task> getTasks() {
		return tasks;
	}

	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}

	//---------------------------------------------------------------------------------
	// Customer Text is specific to an organization
	//---------------------------------------------------------------------------------
	public static Text getCustomerText(String organizationid, String customerid, String language) {
		String textid = organizationid + NameId.Type.Party.name() + customerid;
		return new Text(textid, language);
	}

	public void setCustomerText(Text value){
		setText(organizationid, NameId.Type.Party, customerid, value);
	}

	public String workflowToString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Lease [name=");
		sb.append(name);
		sb.append(", id=");
		sb.append(id);
		sb.append(", name=");
		sb.append(name);
		sb.append(", state=");
		sb.append(state);
		sb.append(", duedate=");
		sb.append(duedate);
		sb.append(", service()=");
		sb.append(service());
		return sb.toString();
	}
	
	public String paramString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Lease [customerid=");
		builder.append(customerid);
		builder.append(", productid=");
		builder.append(productid);
		builder.append(", agentid=");
		builder.append(agentid);
		builder.append(", financeid=");
		builder.append(financeid);
		builder.append(", fromdate=");
		builder.append(fromdate);
		builder.append(", todate=");
		builder.append(todate);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", reverse=");
		builder.append(", notes=");
		builder.append(notes);
		builder.append(", organizationid=");
		builder.append(organizationid);
		builder.append(", state=");
		builder.append(state);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Lease [customerid=");
		builder.append(customerid);
		builder.append(", \nproductid=");
		builder.append(productid);
		builder.append(", \naltpartyid=");
		builder.append(altpartyid);
		builder.append(", \naltid=");
		builder.append(altid);
		builder.append(", \nagentid=");
		builder.append(agentid);
		builder.append(", \nmarket=");
		builder.append(market);
		builder.append(", \noutcome=");
		builder.append(outcome);
		builder.append(", \nfromdate=");
		builder.append(fromdate);
		builder.append(", \ntodate=");
		builder.append(todate);
		builder.append(", \ndeposit=");
		builder.append(deposit);
		builder.append(", \nunit=");
		builder.append(unit);
		builder.append(", \ncurrency=");
		builder.append(currency);
		builder.append(", \ntasks=");
		builder.append(tasks);
		builder.append(", \nparentid=");
		builder.append(parentid);
		builder.append(", \nactorid=");
		builder.append(actorid);
		builder.append(", \nlocationid=");
		builder.append(locationid);
		builder.append(", \nparentname=");
		builder.append(parentname);
		builder.append(", \nactorname=");
		builder.append(actorname);
		builder.append(", \nprocess=");
		builder.append(process);
		builder.append(", \nactivity=");
		builder.append(activity);
		builder.append(", \nnotes=");
		builder.append(notes);
		builder.append(", \nduedate=");
		builder.append(duedate);
		builder.append(", \ndate=");
		builder.append(date);
		builder.append(", \ndonedate=");
		builder.append(donedate);
		builder.append(", \norganizationid=");
		builder.append(organizationid);
		builder.append(", \nstatus=");
		builder.append(status);
		builder.append(", \nstate=");
		builder.append(state);
		builder.append("values=");
		builder.append(values);
		builder.append("attributes=");
		builder.append(attributemap);
		builder.append("texts=");
		builder.append(texts);
		builder.append("images=");
		builder.append(imageurls);
		builder.append("name=");
		builder.append(name);
		builder.append(", \nid=");
		builder.append(id);
		builder.append(", \nservice()=");
		builder.append(service());
		builder.append("]");
		return builder.toString();
	}
}