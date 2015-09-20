/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.Date;

public class Monitor {

	private String id;
	private String name;
	private Integer count;
    private Double duration;
    private Date date;

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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Monitor [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", count=");
		builder.append(count);
		builder.append(", duration=");
		builder.append(duration);
		builder.append(", date=");
		builder.append(date);
		builder.append("]");
		return builder.toString();
	}
}