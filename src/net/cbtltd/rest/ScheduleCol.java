/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.cbtltd.shared.License;


public class ScheduleCol {
	private String state;
	private String reservation;
	private Date date;
	DateFormat df = new SimpleDateFormat("dd");
	DateFormat mf = new SimpleDateFormat("MM");
	DateFormat yf = new SimpleDateFormat("yyyy");
	
	public ScheduleCol() {}
	
	public ScheduleCol(String state, String reservation, Date date) {
		super();
		this.state = state;
		this.reservation = reservation;
		this.date = date;
	}
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getReservation() {
		return reservation;
	}

	public void setReservation(String reservation) {
		this.reservation = reservation;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDay() {
		return df.format(date);
	}

	public void setDay(String day) {
		//this.date = date;
	}

	public String getMonth() {
		return mf.format(date);
	}

	public void setMonth(String month) {
		//this.date = date;
	}

	public String getYear() {
		return yf.format(date);
	}

	public void setYear(String year) {
		//this.date = date;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ScheduleCol [state=");
		builder.append(state);
		builder.append(", reservation=");
		builder.append(reservation);
		builder.append(", date=");
		builder.append(date);
		builder.append(", df=");
		builder.append(df);
		builder.append(", mf=");
		builder.append(mf);
		builder.append(", yf=");
		builder.append(yf);
		builder.append("]");
		return builder.toString();
	}
}
