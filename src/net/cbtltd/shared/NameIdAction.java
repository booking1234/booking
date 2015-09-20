/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.shared.api.HasService;

public class NameIdAction
implements HasService {
	
	protected Service service;
	private boolean suggested;
	private int numrows = Integer.MAX_VALUE;
	private int offsetrows = 0;
	protected String organizationid;
	protected String id;
	protected String name = Model.BLANK;
	protected String state;
	protected String type;
	protected String ids;
	protected String productids;
	protected Date version;
	protected Double rank;

	public NameIdAction() {}

	public NameIdAction(Service service) {
		this.service = service;
	}
	
	public NameIdAction(Service service, String type) {
		this.service = service;
		this.type = type;
	}

	public Service service() {return service;}
	
	public String getOrganizationid() {
		return organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean noType() {
		return type == null || type.isEmpty();
	}

	public boolean hasType() {
		return !noType();
	}

	public String getWildtype() {
		return '%' + type + '%';
	}

	public void setIds(ArrayList<String> ids) {
		this.ids = (ids == null || ids.isEmpty()) ? null : NameId.getCdlist(ids);
	}

	public String getIds() {
		return ids;
	}

	public String getProductids() {
		return productids;
	}

	public void setProductids(ArrayList<String> productids) {
		this.productids = (productids == null || productids.isEmpty()) ? null : NameId.getCdlist(productids);
		
	}

	public boolean isSuggested() {
		return suggested;
	}

	public void setSuggested(boolean suggested) {
		this.suggested = suggested;
	}

	public int getNumrows() {
		return numrows;
	}

	public void setNumrows(int numrows) {
		this.numrows = numrows;
	}
	
	public int getOffsetrows() {
		return offsetrows;
	}

	public void setOffsetrows(int offsetrows) {
		this.offsetrows = offsetrows;
	}


	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}

	public Double getRank() {
		return rank;
	}

	public void setRank(Double rank) {
		this.rank = rank;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NameIdAction [service=");
		builder.append(service);
		builder.append(", suggested=");
		builder.append(suggested);
		builder.append(", numrows=");
		builder.append(numrows);
		builder.append(", offsetrows=");
		builder.append(offsetrows);
		builder.append(", organizationid=");
		builder.append(organizationid);
		builder.append(", id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", state=");
		builder.append(state);
		builder.append(", type=");
		builder.append(type);
		builder.append(", ids=");
		builder.append(ids);
		builder.append(", version=");
		builder.append(version);
		builder.append(", rank=");
		builder.append(rank);
		builder.append(", service()=");
		builder.append(service());
		builder.append("]");
		return builder.toString();
	}
}