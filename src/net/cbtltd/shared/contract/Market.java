/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.cbtltd.shared.Contract;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.api.HasState;
import net.cbtltd.shared.api.HasTableService;

public class Market
implements HasTableService, HasState {

	public static final String[] STATES = {Contract.CREATED};

	private String id;
	private Boolean manager;
	private Boolean owner;
	private Double discount;
	private ArrayList<String> locations;
	private Map<String, ArrayList<String>> attributes;
	private String orderby;
	private int startrow;
	private int numrows;
	
	public Service service() {return Service.CONTRACT;}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return Contract.CREATED;
	}

	public void setState(String state) {
		//this.state = state;
	}

	public Boolean getManager() {
		return manager;
	}

	public void setManager(Boolean manager) {
		this.manager = manager;
	}

	public boolean noManager() {
		return manager == null;
	}

	public Boolean getOwner() {
		return owner;
	}

	public void setOwner(Boolean owner) {
		this.owner = owner;
	}

	public boolean noOwner() {
		return owner == null;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public boolean noDiscount() {
		return discount == null || discount < 0.0 || discount > 100.0;
	}

	public ArrayList<String> getLocations() {
		return locations;
	}

	public void setLocations(ArrayList<String> locations) {
		this.locations = locations;
	}

	public boolean noLocations() {
		return locations == null || locations.isEmpty();
	}

	public Map<String, ArrayList<String>> getAttributes() {
		return attributes;
	}

	public ArrayList<String> getAttributes(String key) {
		return attributes == null ? null : attributes.get(key);
	}

	public boolean noAttributes() {
		return attributes == null || attributes.isEmpty();
	}

	public boolean noAttributes(String key) {
		return noAttributes() || getAttributes(key).isEmpty();
	}

	public void setAttributes(Map<String, ArrayList<String>> attributes) {
		this.attributes = attributes;
	}

	public ArrayList<String> getGradings() {
		return getAttributes(Product.GRADING);
	}

	public void setGradings(ArrayList<String> ratings) {
		if (attributes == null) {attributes = new HashMap<String, ArrayList<String>>();}
		attributes.put(Product.GRADING, ratings);
	}

	public boolean noGradings() {
		return noAttributes(Product.GRADING);
	}

	public String getLocationlist() {
		return NameId.getCdlist(locations);
	}
	
	public String getGradinglist() {
		return NameId.getCdlist(getGradings());
	}
	
	public String getOwnermanager(){
		if (owner && !manager) {return (" and ownerid = supplierid ");}
		if (!owner && manager) {return (" and ownerid <> supplierid ");}
		if (!owner && !manager) {return (" and ownerid <> ownerid ");}
		return "";
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public boolean noOrderby() {
		return orderby == null || orderby.isEmpty();
	}
	
	public int getStartrow() {
		return startrow;
	}

	public void setStartrow(int startrow) {
		this.startrow = startrow;
	}

	public int getNumrows() {
		return numrows;
	}

	public void setNumrows(int numrows) {
		this.numrows = numrows;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(" Id  ").append(getId());
		sb.append(" Manager ").append(getManager());
		sb.append(" Owner ").append(getOwner());
		sb.append(" Discount ").append(getDiscount());
		sb.append(" Currency ").append(getOwner());
		sb.append(" OrderBy ").append(getOrderby());
		sb.append(" StartRow ").append(getStartrow());
		sb.append(" NumRows ").append(getNumrows());
		sb.append("\nLocations ").append(getLocations());
		sb.append("\nRatings ").append(getGradings());
		return sb.toString();
	}
}
