/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.flipkey;

import java.util.Date;

public class ScheduleItem {
	private String property_id;
	private String reservation_id;
	private String emailaddress;
	private Date arrivaldate;
	private Date departuredate;
	
	public ScheduleItem(){}
	
	public ScheduleItem(Date arrivaldate, Date departuredate) {
		super();
		this.arrivaldate = arrivaldate;
		this.departuredate = departuredate;
	}
	public String getProperty_id() {
		return property_id;
	}
	public void setProperty_id(String property_id) {
		this.property_id = property_id;
	}
	public String getReservation_id() {
		return reservation_id;
	}
	public void setReservation_id(String reservation_id) {
		this.reservation_id = reservation_id;
	}
	public String getEmailaddress() {
		return emailaddress;
	}
	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}
	public Date getArrivaldate() {
		return arrivaldate;
	}
	public void setArrivaldate(Date arrivaldate) {
		this.arrivaldate = arrivaldate;
	}
	public Date getDeparturedate() {
		return departuredate;
	}
	public void setDeparturedate(Date departuredate) {
		this.departuredate = departuredate;
	}
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("\ngetProperty_id ").append((getProperty_id() == null)? "null" : getProperty_id());
		sb.append(" getArrivaldate ").append((getArrivaldate() == null)? "null" : getArrivaldate());
		sb.append(" getDeparturedate ").append((getDeparturedate() == null)? "null" : getDeparturedate());
		return sb.toString();
	}
}
