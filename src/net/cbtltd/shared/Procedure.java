/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.ArrayList;

public class Procedure 
extends Product {
	
	private boolean looped;
	private ArrayList<Bill> bills;

	public ArrayList<Bill> getBills() {
		return bills;
	}

	public boolean isLooped() {
		return looped;
	}

	public void setLooped(boolean looped) {
		this.looped = looped;
	}

	public void setBills(ArrayList<Bill> bills) {
		this.bills = bills;
	}

	public boolean noBills() {
		return bills == null || bills.isEmpty();
	}

	public boolean hasBills() {
		return !noBills();
	}

	public void addBill(Bill bill) {
		if (bills == null) {bills = new ArrayList<Bill>();}
		bills.add(bill);
	}

	public Bill getBill(int index) {
		return bills == null ? new Bill() : bills.get(index);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Procedure [looped=");
		builder.append(looped);
		builder.append(", bills=");
		builder.append(bills);
		builder.append(", entitytype=");
		builder.append(entitytype);
		builder.append(", locationid=");
		builder.append(locationid);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", unit=");
		builder.append(unit);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", altitude=");
		builder.append(altitude);
		builder.append(", images=");
		builder.append(imageurls);
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