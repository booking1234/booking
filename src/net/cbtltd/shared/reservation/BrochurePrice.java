package net.cbtltd.shared.reservation;

import java.util.ArrayList;

import net.cbtltd.shared.NameId;
import net.cbtltd.shared.api.HasService;

public class BrochurePrice 
extends AvailableItem
implements HasService {

	private String tocurrency;
	private ArrayList<NameId> collisions;

	public String getTocurrency() {
		return tocurrency;
	}

	public void setTocurrency(String tocurrency) {
		this.tocurrency = tocurrency;
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
	public String toString() { //VIP for change management
		StringBuilder builder = new StringBuilder();
		builder.append("BrochurePrice [tocurrency=");
		builder.append(tocurrency);
		builder.append(", collisions=");
		builder.append(collisions);
		builder.append(super.toString());
		return builder.toString();
	}
}
