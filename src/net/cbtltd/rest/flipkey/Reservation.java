/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.flipkey;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.xml.bind.annotation.XmlAttribute;

public class Reservation {
	@XmlAttribute (name = "property_id")
	public String Property_id;
	@XmlAttribute (name = "reservation_id")
	public String Reservation_id;
	public String EmailAddress;
	public String ArrivalDate;
	public String DepartureDate;
	public Status Status;
	
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	public Reservation(){}
	
	public Reservation(ScheduleItem scheduleitem) {
		super();
		this.Property_id = scheduleitem.getProperty_id();
		this.Reservation_id = scheduleitem.getReservation_id();
		this.EmailAddress = scheduleitem.getEmailaddress();
		this.ArrivalDate = df.format(scheduleitem.getArrivaldate());
		this.DepartureDate = df.format(scheduleitem.getDeparturedate());
		this.Status = new Status("reserved");
	}
}
