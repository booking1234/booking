/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.reservation;

import java.util.ArrayList;

import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.task.Contact;

public class Brochure extends Contact {
	
	public static final String INITIAL = "Initial";
	public static final String FINAL = "Final";
	public static final String CREATED = "Created";
	public static final String SENT = "Sent";
	public static final String[] STATES = {INITIAL, CREATED, SENT, FINAL};

	private String customerid;
	private String currency;
	private Boolean priceunit; //true = night false = stay

	private ArrayList<AvailableItem> availableitems;
	private ArrayList<NameId> collisions;

	public Service service() {return Service.RESERVATION;}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Boolean getPriceunit() {
		return priceunit;
	}

	public void setPriceunit(Boolean priceunit) {
		this.priceunit = priceunit;
	}

	public ArrayList<AvailableItem> getAvailableitems() {
		return availableitems;
	}

	public void setAvailableitems(ArrayList<AvailableItem> availableitems) {
		this.availableitems = availableitems;
	}

	public ArrayList<NameId> getCollisions() {
		return collisions;
	}

	public void setCollisions(ArrayList<NameId> collisions) {
		this.collisions = collisions;
	}

	public void addCollisions(ArrayList<NameId> collisions) {
		if (this.collisions == null) {this.collisions = new ArrayList<NameId>();}
		this.collisions.addAll(collisions);
	}

	public boolean noCollisions() {
		return collisions == null || collisions.isEmpty();
	}

	public boolean hasCollisions() {
		return !noCollisions();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Brochure [customerid=");
		builder.append(customerid);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", priceunit=");
		builder.append(priceunit);
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
		builder.append("\navailableitems=");
		builder.append(availableitems);
		builder.append("\ncollisions=");
		builder.append(collisions);
		builder.append("]");
		return builder.toString();
	}
}
