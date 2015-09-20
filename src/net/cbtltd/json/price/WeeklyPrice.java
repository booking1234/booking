package net.cbtltd.json.price;

import java.util.Date;

import net.cbtltd.shared.Price;

public class WeeklyPrice extends Price {
	private Date start;
	private Date end;
	private Integer week;

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}
}
