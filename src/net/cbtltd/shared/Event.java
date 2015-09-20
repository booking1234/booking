/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.ArrayList;

public class Event <T>
extends Process {
	
	public static final String INITIAL = "Initial";
	public static final String FINAL = "Final";
	public static final String CREATED = "Created";
	public static final String[] STATES = {INITIAL, CREATED, FINAL};
	public static final String[] LIST_STATES = {CREATED};
	/** Sequence is important because of pop up labels so add new enums at end. */
	public enum Type {Journal, Payment, Purchase, PurchaseSale, Receipt, ReservationSale, Sale, Audit, Rate};

	public static final String[] PROCESSES = {Event.Type.Journal.name(), Event.Type.Payment.name(), 
		Event.Type.Purchase.name(), Event.Type.PurchaseSale.name(),
		Event.Type.Receipt.name(), Event.Type.ReservationSale.name(), Event.Type.Sale.name()};

	public static final String ACCOUNTING = "Accounting";
	public static final String PENDING = "Pending";
	public static final String NOMINAL = "Nominal";
	public static final String LOGGING = "Logging";
	public static final String[] EVENT_TYPES = {ACCOUNTING, PENDING, NOMINAL, LOGGING};

	public static final String DONE = "Done";

	public static final String NAME = "event.name";
	public static final String PROCESS = "event.process";
	public static final String STATE = "event.state";
	public static final String DATE = "event.date";
	public static final String TYPE = "event.type";
	
	protected Service service;
	protected String type;
	protected Double amount;
	protected ArrayList<T> items;

	public Event () {service = Service.JOURNAL;}

	public Event (Service service, String process, String state, String id) {
		this.service = service;
		super.setProcess(process);
		this.state = state;
		this.id = id;
	}

	public Service service() {return service;}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean noType() {
		return type == null || type.isEmpty();
	}

	public boolean hasType(String type) {
		return this.type != null && type != null && this.type.equalsIgnoreCase(type);
	}

	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public T getItem(int index) {
		return (items == null && index >= items.size()) ? null : items.get(index);
	}

	public void addItem(T item) {
		if (items == null){items = new ArrayList<T>();}
		items.add(item);
	}

	public void setItem(int index, T item) {
		if (items == null){items = new ArrayList<T>();}
		if (items.size() == 0){items.add(item);}
		else {items.set(index, item);}
	}

	public void clearItems() {
		if (items == null){items = new ArrayList<T>();}
		else {items.clear();}
	}

	public ArrayList<T> getItems() {
		return items;
	}

	public void setItems(ArrayList<T> items) {
		this.items = items;
	}

	public boolean noItems() {
		return items == null || items.isEmpty();
	}

	public boolean hasItems() {
		return !noItems();
	}

//	public String getItemlist() {
//		return Util.modelListToIds(items);
//	}
//		if (items == null || items.size() == 0) {return "'-1'";}
//		StringBuilder sb = new StringBuilder();
//		for (T item : items)
//			sb.append("'" + item.getId() + "',");
//		if (sb.length() > 0) {sb.deleteCharAt(sb.length()-1);}
//		else {sb.append("'-1'");}
//		return sb.toString();
//	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Event [service=");
		builder.append(service);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append(", state=");
		builder.append(state);
		builder.append(", type=");
		builder.append(type);
		builder.append(", amount=");
		builder.append(amount);
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
		builder.append("\nitems=");
		builder.append(items);
		builder.append("\nvalues=");
		builder.append(values);
		builder.append("\nattributes=");
		builder.append(attributemap);
		builder.append("\ntexts=");
		builder.append(texts);
		builder.append("]");
		return builder.toString();
	}
}