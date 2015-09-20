/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.rate;

import java.util.Date;

import net.cbtltd.shared.Rate;
import net.cbtltd.shared.api.HasResponse;

public class RateNote extends Rate implements HasResponse {

	private Date date;
	private Double rating;
	private String notes;
	private int status;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RateNote [date=");
		builder.append(date);
		builder.append(", rating=");
		builder.append(rating);
		builder.append(", notes=");
		builder.append(notes);
		builder.append(", status=");
		builder.append(status);
		builder.append(", id=");
		builder.append(id);
		builder.append(", eventid=");
		builder.append(eventid);
		builder.append(", customerid=");
		builder.append(customerid);
		builder.append(", productid=");
		builder.append(productid);
		builder.append(", type=");
		builder.append(type);
		builder.append(", name=");
		builder.append(name);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append("]");
		return builder.toString();
	}

}
