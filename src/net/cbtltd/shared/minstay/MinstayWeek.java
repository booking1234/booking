package net.cbtltd.shared.minstay;

import java.util.Date;

import net.cbtltd.shared.MinStay;

public class MinstayWeek extends MinStay {
	private Date fromDate;
	private Date toDate;
	private String entityId;

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
}
