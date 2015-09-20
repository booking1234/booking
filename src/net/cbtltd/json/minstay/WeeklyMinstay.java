package net.cbtltd.json.minstay;

import java.util.Date;

import net.cbtltd.shared.MinStay;

public class WeeklyMinstay extends MinStay {
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
