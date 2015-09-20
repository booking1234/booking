package net.cbtltd.shared;

import java.util.Date;

public class MinStay {
	
	private int id;
	private String supplierid;
	private String productid;
	private Date fromdate;
	private Date todate;
	private Integer value;
	private Date version;
	
	public MinStay() {
		super();
	}
	
	public MinStay(String supplierid, String productid, Date fromdate, Date todate, Integer value) {
		super();
		this.supplierid = supplierid;
		this.productid = productid;
		this.fromdate = fromdate;
		this.todate = todate;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
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

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MinStay [id=");
		builder.append(id);
		builder.append(", supplierid=");
		builder.append(supplierid);
		builder.append(", productid=");
		builder.append(productid);
		builder.append(", fromdate=");
		builder.append(fromdate);
		builder.append(", todate=");
		builder.append(todate);
		builder.append(", value=");
		builder.append(value);
		builder.append(", version=");
		builder.append(version);
		builder.append("]");
		return builder.toString();
	}

}
