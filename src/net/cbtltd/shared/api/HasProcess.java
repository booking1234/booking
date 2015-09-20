/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.api;

import java.util.Date;

public interface HasProcess
extends HasState {
	String getProcess();
	String getOrganizationid();
	void setOrganizationid(String organizationid);
	String getActorid();
	void setActorid(String actorid);
	Date getDuedate();
	void setDuedate(Date duedate);
	Date getDonedate();
	void setDonedate(Date donedate);
	String getNotes();
	void setNotes(String notes);
}
