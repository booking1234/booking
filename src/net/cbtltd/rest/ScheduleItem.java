/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest;

import java.util.Date;

import net.cbtltd.shared.License;

public class ScheduleItem {
	private String id;
	private String name;
	private String state;
	private String reservation;
	private Date date;
	
	public ScheduleItem(){}
	
	public ScheduleItem(
			String id, 
			String name, 
			String state,
			String reservation, 
			Date date) {
		this.id = id;
		this.name = name;
		this.state = state;
		this.reservation = reservation;
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean noId() {
		return getId() == null || getId().isEmpty();
	}
	
	public boolean hasId() {
		return !noId();
	}

	public boolean notId(String id) {
		return this.id == null || id == null || !this.id.equals(id);
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean hasState(String state) {
		return this.state != null && this.state.equals(state);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public boolean notSameCell(ScheduleItem item) {
		return item == null || notId(item.getId()) || date.getDay() != item.getDate().getDay();
	}

	//---------------------------------------------------------------------------------
	// Implements Model interface
	//---------------------------------------------------------------------------------
//	public int compareTo(Model model) {
//		return getName().compareTo(model.getName());
//	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("\nScheduleItemId ").append((getId() == null)? "null" : getId());
		sb.append(" Name ").append((getName() == null) ? "null" : getName());
		sb.append(" State ").append((getState() == null) ? "null" : getState());
		sb.append(" Reservation ").append((getReservation() == null) ? "null" : getReservation());
		sb.append(" Date ").append((getDate() == null) ? "null" : getDate());
		return sb.toString();
	}
}
