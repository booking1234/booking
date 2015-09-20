/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;

import net.cbtltd.shared.api.HasProcess;
import net.cbtltd.shared.api.HasResetId;

public class Process
extends Model
implements HasProcess, HasResetId {

	//Types
//	public static final String CONTACT = "Contact";
//	public static final String CONTRACT = "Contract";
//	public static final String RESERVATION = "Reservation";
//	public static final String TASK = "Task";

	public static final String PROCESS = "process";
	public static final String STATE = "state";
	public static final String DUEDATE = "duedate";
	public static final String DONEDATE = "donedate";
	public static final String NOTES = "notes";
	public static final String ACTORNAME = "actorname";
	public static final String PARENTNAME = "parentname";

	protected String parentid;
    protected String actorid;
    protected String locationid;
	protected String parentname;
	protected String actorname;
	protected String process;
	protected String activity;
    protected String notes;
	protected Date duedate;
    protected Date date;
    protected Date donedate;

    public Service service() {return Service.WORKFLOW;}
    
    
//	public String getResetid() {
//		return id;
//	}
//
	public String getResetid() {
		if (hasProcess(Task.Type.Service.name())) {return parentid;}
		else if (hasProcess(Task.Type.Maintenance.name())) {return parentid;}
		else {return id;}
	}

	@XmlTransient
	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public boolean noParentid() {
		return parentid == null || parentid.isEmpty() || parentid.equalsIgnoreCase(Model.ZERO);
	}

	public boolean hasParentid() {
		return !noParentid();
	}

	public String getActorid() {
		return actorid;
	}

	public void setActorid(String actorid) {
		this.actorid = actorid;
	}

	public boolean noActorid() {
		return actorid == null || actorid.isEmpty() || actorid.equals(Model.ZERO);
	}

	public boolean hasActorid() {
		return !noActorid();
	}

	public boolean hasActorid(String actorid) {
		return this.actorid.equals(actorid);
	}

	@XmlTransient
	public String getLocationid() {
		return locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	@XmlTransient
	public String getParentname() {
		return parentname;
	}

	public void setParentname(String parentname) {
		this.parentname = parentname;
	}

	public boolean noParentname() {
		return parentname == null || parentname.isEmpty();
	}

	public boolean hasParentname() {
		return !noParentname();
	}

	public String getActorname() {
		return actorname;
	}

	public void setActorname(String actor) {
		this.actorname = actor;
	}

	@XmlTransient
	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public boolean noProcess() {
		return process == null || process.isEmpty() || process.equalsIgnoreCase(Model.ZERO);
	}

	public boolean hasProcess() {
		return !noProcess();
	}

	public boolean hasProcess(String process) {
		return this.process != null && process != null && this.process.equalsIgnoreCase(process);
	}

	public boolean notProcess(String process) {
		return !hasProcess(process);
	}

	@XmlTransient
	public String getActivity() {
		return activity;
	}

	public void setActivity(String parenttype) {
		this.activity = parenttype;
	}

	public boolean noActivity() {
		return activity == null || activity.isEmpty() || activity.equalsIgnoreCase(Model.ZERO);
	}

	public boolean hasActivity() {
		return !noActivity();
	}

	public boolean hasActivity(String activity) {
		return this.activity != null && this.activity.equals(activity);
	}

//	public ServiceOld getActivityNameService() {
//		return Model.getNameService(activity);
//	}
	
	public String getNotes() {
		return notes;
	}

	public String getNotes(int length) {
		return NameId.trim(notes, length);
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public boolean noNotes() {
		return notes == null || notes.isEmpty();
	}

	public boolean hasNotes() {
		return !noNotes();
	}

	public Date getDate() {
		return date;
	}

	public boolean noDate() {
		return date == null;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDuedate() {
		return duedate;
	}

	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}

	public boolean noDuedate() {
		return duedate == null || duedate.before(new Date());
	}

	public Date getDonedate() {
		return donedate;
	}

	public void setDonedate(Date donedate) {
		this.donedate = donedate;
	}
	
	public void setDone() {
		this.donedate = new Date();
	}

	public boolean isDone() {
		return (donedate != null);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Process [parentid=");
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
		builder.append("]");
		return builder.toString();
	}

}
