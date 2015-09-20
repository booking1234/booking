/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.ArrayList;

public class Task
extends Process {

	public static final String INITIAL = "Initial";
	public static final String FINAL = "Final";
	public static final String CREATED = "Created";
	public static final String STARTED = "Started";
	public static final String CONTACTED = "Contacted";
	public static final String COMPLETED = "Completed";
	public static final String CANCELLED = "Cancelled";

	public enum Type {Maintenance, Marketing, Service};

	//Service Types
	public static final String HEADER = "0"; //TODO Product ID
	public static final String NONE = "1";
	public static final String ARRIVAL = "2";
	public static final String DEPARTURE = "3";
	public static final String REFRESH = "4";
	public static final String LINEN_CHANGE = "5";
	public static final String[] SERVICE_TYPES = {NONE, ARRIVAL, DEPARTURE, REFRESH, LINEN_CHANGE};

	//Sortable columns
	public static final String ACTORNAME = "party.name"; 
	public static final String NAME = "task.name";
	public static final String PROCESS = "task.process";
	public static final String STATE = "task.state";
	public static final String DUEDATE = "task.duedate";
	public static final String DONEDATE = "task.donedate";
	public static final String NOTES = "task.notes";
	public static final String DATE = "task.date";
	public static final String AMOUNT = "task.price * task.quantity";

	public static final String SUPPLIER = "Supplier"; 
	public static final String EMPLOYEE = "Employee"; 

	private String customerid = Model.ZERO;
	private String productid = Model.ZERO;
	private String resourcetype;
	private String starter;
	private String outcome;
	private Double quantity = 1.0;
	private String unit;
	private Double cost = 0.0;
	private Double price = 0.0;
	private String currency;

	private ArrayList<String> resources;
	private ArrayList<NameId> collisions;

	public Service service() {return Service.TASK;}

//	public String getResetid() {
//		if (hasProcess(Type.Service.name())) {return parentid;}
//		else if (hasProcess(Type.Maintenance.name())) {return parentid;}
//		else {return id;}
//	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public boolean noProductid() {
		return productid == null || productid.isEmpty()
				|| productid.equalsIgnoreCase(Model.ZERO);
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

	public String getStarter() {
		return starter;
	}

	public void setStarter(String starter) {
		this.starter = starter;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public Double getQuantity() {
		return quantity == null ? 0.0 : quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public boolean noQuantity() {
		return quantity == null || quantity == 0.0;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public boolean noUnit() {
		return unit == null || unit.equalsIgnoreCase(BLANK);
	}

	public Double getCost() {
		return cost == null ? 0.0 : cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getPrice() {
		return price == null ? 0.0 : price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public boolean noPrice() {
		return price == null;
	}
	
	public String getCurrency() {
		return currency;
	}

	public Double getAmount() {
		return noQuantity() ? getPrice() : getQuantity() * getPrice();
	}

	public boolean noAmount() {
		return Math.abs(getAmount()) < 0.01;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean noCurrency() {
		return currency == null || currency.equalsIgnoreCase(BLANK);
	}

	
	public String getResourcetype() {
		return resourcetype;
	}

	public void setResourcetype(String resourcetype) {
		this.resourcetype = resourcetype;
	}

	public boolean isResourceEmployee() {
		return resourcetype == null || resourcetype.equalsIgnoreCase(EMPLOYEE);
	}
	
	public void setResourceEmployee(boolean employee) {
		if (employee) {resourcetype = EMPLOYEE;}
		else {resourcetype = SUPPLIER;}
	}
	
	public ArrayList<NameId> getCollisions() {
		return collisions;
	}

	public void setCollisions(ArrayList<NameId> collisions) {
		this.collisions = collisions;
	}

	public boolean noCollisions() {
		return collisions == null || collisions.isEmpty();
	}

	public boolean hasCollisions() {
		return !noCollisions();
	}
	
	public String getResource() {
		return resources == null || resources.isEmpty() ? null : resources.get(0);
	}

	public void setResource(String resource) {
		this.resources = new ArrayList<String>();
		resources.add(resource);
	}

	public ArrayList<String> getResources() {
		return resources;
	}

	public void setResources(ArrayList<String> resources) {
		this.resources = resources;
	}

	public String getResourcelist() {
		return NameId.getCdlist(resources);
	}
	
	//---------------------------------------------------------------------------------
	// Text is specific to a task
	//---------------------------------------------------------------------------------
//	public static final String getPublicId(String id){
//		return NameId.Type.Task.name() + id + Text.Type.Service.name();
//	}
//	public static Text getText(String id, String language) {
//		return new Text(NameId.Type.Task.name() + id + Text.Type.Public.name(), "", Text.TEXT, new Date(), "", language);
//	}
//
//	public Text getText(String language) {
//		return new Text(NameId.Type.Task.name() + id + Text.Type.Public.name(), name, Text.TEXT, new Date(), name, language);
//	}

	public Text getPublicText() {
		return getText(NameId.Type.Task, id, Text.Code.Service);
	}

	public void setPublicText(Text value) {
		setText(NameId.Type.Task, id, Text.Code.Service, value);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Task [customerid=");
		builder.append(customerid);
		builder.append(", productid=");
		builder.append(productid);
		builder.append(", resourcetype=");
		builder.append(resourcetype);
		builder.append(", starter=");
		builder.append(starter);
		builder.append(", outcome=");
		builder.append(outcome);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", unit=");
		builder.append(unit);
		builder.append(", cost=");
		builder.append(cost);
		builder.append(", price=");
		builder.append(price);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", resources=");
		builder.append(resources);
		builder.append(", collisions=");
		builder.append(collisions);
		builder.append(", parentid=");
		builder.append(parentid);
		builder.append(", actorid=");
		builder.append(actorid);
		builder.append(", locationid=");
		builder.append(locationid);
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
		builder.append(", service()=");
		builder.append(service());
		builder.append("]");
		return builder.toString();
	}
}