/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.Date;

public class Audit
extends Model {

	public static final String INITIAL = "Initial";
	public static final String FINAL = "Final";
	public static final String CREATED = "Created";
	public static final String[] STATES = { INITIAL, CREATED, FINAL };

	public static final String DATE = "date";
	public static final String RATING = "rating";
	public static final String NOTES = "notes";

	private String productid;
	private Date date;
	private Integer rating;
	private String notes;

	public Audit() {}

	public Service service() {return Service.AUDIT;}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Audit [id=");
		builder.append(id);
		builder.append(", organizationid=");
		builder.append(organizationid);
		builder.append(", productid=");
		builder.append(productid);
		builder.append(", name=");
		builder.append(name);
		builder.append(", state=");
		builder.append(state);
		builder.append(", date=");
		builder.append(date);
		builder.append(", rating=");
		builder.append(rating);
		builder.append(", notes=");
		builder.append(notes);
		builder.append(", service()=");
		builder.append(service());
		builder.append("]");
		return builder.toString();
	}
}
