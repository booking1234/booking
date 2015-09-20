/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.api;

import java.util.ArrayList;
import java.util.Date;

public interface HasSchedule {
//	String getId();
	String getState();
	Date getFromdate();
	boolean isDayBooked(Date date);
	int getDaysToEnd(Date date);
	String getActivityid();
	String getLabel(int length);
	void addItem(HasSchedule model);
	ArrayList<HasSchedule> getItems();
}
