/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.task;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Task;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.api.HasSchedule;

public class ScheduleItem
extends Task
implements HasSchedule {

	public static final String TASKID = "id";
	public static final String NAME = "name";
	public static final String STATE = "state";
	public static final String RESOURCE = "resource";
	public static final String PRODUCT = "product";
	public static final String FROMDATE = "fromdate";
	public static final String DUEDATE = "duedate";
	public static final String PRICE = "price";
	public static final String COST = "cost";

	private ArrayList<HasSchedule> items = new ArrayList<HasSchedule>();

	public Service service() {return Service.TASK;}

	//---------------------------------------------------------------------------------
	// Implements HasSchedule interface
	//---------------------------------------------------------------------------------
	public String getActivityid() {
		return id;
	}

	public Date getFromdate() {
		return duedate;
	}

	public String getLabel(int length) {
		return NameId.trim(name, length);
	}

	public boolean isDayBooked(Date date) {
		return date != null && date != null && duedate != null && duedate.after(date);
	}
	
	public int getDaysToEnd(Date date) {
		if (date == null || date == null || duedate == null || duedate.compareTo(date) <= 0) {return 0;}
		else {return Time.getDuration(date, duedate, Time.DAY).intValue() - 1;}
	}
	
	public ArrayList<HasSchedule> getItems() {
		return items;
	}

	public void addItem(HasSchedule item) {
		items.add(item);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ScheduleItem [items=");
		builder.append(items);
		builder.append(", parentid=");
		builder.append(parentid);
		builder.append(", actorid=");
		builder.append(actorid);
		builder.append(", locationid=");
		builder.append(locationid);
		builder.append(", parentname=");
		builder.append(parentname);
		builder.append(", actorname=");
		builder.append(actorname);
		builder.append(", process=");
		builder.append(process);
		builder.append(", activity=");
		builder.append(activity);
		builder.append(", notes=");
		builder.append(notes);
		builder.append(", duedate=");
		builder.append(duedate);
		builder.append(", date=");
		builder.append(date);
		builder.append(", donedate=");
		builder.append(donedate);
		builder.append(", organizationid=");
		builder.append(organizationid);
		builder.append(", status=");
		builder.append(status);
		builder.append(", state=");
		builder.append(state);
		builder.append(", values=");
		builder.append(values);
		builder.append(", attributes=");
		builder.append(attributemap);
		builder.append(", texts=");
		builder.append(texts);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append(", service()=");
		builder.append(service());
		builder.append("]");
		return builder.toString();
	}
}
