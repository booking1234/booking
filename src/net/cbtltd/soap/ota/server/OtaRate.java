/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.soap.ota.server;

import java.util.Date;

/** 
 * The Class OtaRate is used to get price data from the database for OTA messages.
 * @see net.cbtltd.soap.ota.OtaService
 * @see net.cbtltd.server.api.PriceMapper
 */
public class OtaRate {

	private String id;
	private String productid;
	private String currency;
	private Date fromdate;
	private Date todate;
	private Double value;
	private Double minimum;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Double getMinimum() {
		return minimum;
	}

	public void setMinimum(Double minimum) {
		this.minimum = minimum;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OtaRate [id=");
		builder.append(id);
		builder.append(", productid=");
		builder.append(productid);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", fromdate=");
		builder.append(fromdate);
		builder.append(", todate=");
		builder.append(todate);
		builder.append(", value=");
		builder.append(value);
		builder.append(", minimum=");
		builder.append(minimum);
		builder.append("]");
		return builder.toString();
	}
}
