/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.Date;

import net.cbtltd.shared.api.HasData;
import net.cbtltd.shared.api.HasResponse;
import net.cbtltd.shared.api.HasService;

public class License implements HasData, HasService, HasResponse {

	public static final Integer DEFAULT_WAIT = 10;
	public static final Integer PARTY_WAIT = 100;
	public static final Integer PRODUCT_WAIT = 100;
	
	public static final String INITIAL = "Initial";
	public static final String FINAL = "Final";
	public static final String CREATED = "Created";
	public static final String SUSPENDED = "Suspended";

	public enum Type {All, Console, JSON_XML, JSON_XML_UP, jQuery, None}
	public static final String[] TYPES = { Type.All.name(), Type.Console.name(), Type.JSON_XML.name(), Type.JSON_XML_UP.name(), Type.jQuery.name(), Type.None.name()};

	public static final String PARTY = "party.name";
	public static final String PRODUCT = "product.name";
	public static final String FROMDATE = "fromdate";
	public static final String TODATE = "todate";
	public static final String STATE = "state";
	public static final String SUBSCRIPTION = "subscription";
	public static final String TRANSACTION = "transaction";
	public static final String DOWNSTREAM = "downstream";
	public static final String UPSTREAM = "upstream";

	private String organizationid;
	private String actorid;
	private String id;
	private String upstreamid;
	private String downstreamid;
	private String downstreamname;
	private String productid;
	private String productname;
	private String state;
	private String type;
	private String notes;
	private Date fromdate;
	private Date todate;
	private Integer wait;
	private Double subscription;
	private Double transaction;
	private Double downstream;
	private Double upstream;
	private int status;

	
	public License() {}

	public License(String upstreamid, String downstreamid, String productid, Type type, Integer wait, Date date) {
		super();
		this.upstreamid = upstreamid;
		this.downstreamid = downstreamid;
		this.productid = productid;
		this.type = (type == null) ? null : type.name();
		this.wait = wait;
		this.fromdate = date;
		this.todate = date;
	}

	public Service service() {return Service.LICENSE;}

	public String getOrganizationid() {
		return organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getActorid() {
		return actorid;
	}

	public void setActorid(String actorid) {
		this.actorid = actorid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUpstreamid() {
		return upstreamid;
	}

	public void setUpstreamid(String upstreamid) {
		this.upstreamid = upstreamid;
	}

	public String getDownstreamid() {
		return downstreamid;
	}

	public void setDownstreamid(String downstreamid) {
		this.downstreamid = downstreamid;
	}

	public String getDownstreamname() {
		return downstreamname;
	}

	public void setDownstreamname(String downstreamname) {
		this.downstreamname = downstreamname;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
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

	public boolean notType(Type type) {
		return this.type == null || type == null || !this.type.equalsIgnoreCase(type.name());
	}
	
	public boolean hasType(Type type) {
		return !notType(type);
	}
	
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNotes(int length) {
		return NameId.trim(notes, length);
	}

	public Date getFromdate() {
		return fromdate;
	}

	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}

	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}

	public Double getTransaction() {
		return transaction;
	}

	public void setTransaction(Double transaction) {
		this.transaction = transaction;
	}

	public Integer getWait() {
		return wait;
	}

	public void setWait(Integer wait) {
		this.wait = wait;
	}

	public Double getSubscription() {
		return subscription;
	}

	public void setSubscription(Double subscription) {
		this.subscription = subscription;
	}

	public Double getDownstream() {
		return downstream;
	}

	public void setDownstream(Double downstream) {
		this.downstream = downstream;
	}

	public Double getUpstream() {
		return upstream;
	}

	public void setUpstream(Double upstream) {
		this.upstream = upstream;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getKey() {
		StringBuilder builder = new StringBuilder();
		builder.append(upstreamid);
		builder.append(",").append(downstreamid);
		builder.append(",").append(productid);
		builder.append(",").append(type);
		builder.append(",").append(fromdate.getDate());
		builder.append(",").append(fromdate.getMonth());
		builder.append(",").append(fromdate.getYear());
		return builder.toString();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("License [organizationid=");
		builder.append(organizationid);
		builder.append(", actorid=");
		builder.append(actorid);
		builder.append(", id=");
		builder.append(id);
		builder.append(", upstreamid=");
		builder.append(upstreamid);
		builder.append(", downstreamid=");
		builder.append(downstreamid);
		builder.append(", downstreamname=");
		builder.append(downstreamname);
		builder.append(", productid=");
		builder.append(productid);
		builder.append(", productname=");
		builder.append(productname);
		builder.append(", state=");
		builder.append(state);
		builder.append(", type=");
		builder.append(type);
		builder.append(", notes=");
		builder.append(notes);
		builder.append(", fromdate=");
		builder.append(fromdate);
		builder.append(", todate=");
		builder.append(todate);
		builder.append(", wait=");
		builder.append(wait);
		builder.append(", subscription=");
		builder.append(subscription);
		builder.append(", transaction=");
		builder.append(transaction);
		builder.append(", downstream=");
		builder.append(downstream);
		builder.append(", upstream=");
		builder.append(upstream);
		builder.append(", status=");
		builder.append(status);
		builder.append(", service()=");
		builder.append(service());
		builder.append("]");
		return builder.toString();
	}
}