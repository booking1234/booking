/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.journal;

import net.cbtltd.shared.Event;
import net.cbtltd.shared.Journal;

public class JournalTaskUpdate extends Event<Journal> {
	private String taskid;
	private String taskstate;
	
	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getTaskstate() {
		return taskstate;
	}

	public void setTaskstate(String taskstate) {
		this.taskstate = taskstate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JournalTaskUpdate [taskid=");
		builder.append(taskid);
		builder.append(", taskstate=");
		builder.append(taskstate);
		builder.append(", type=");
		builder.append(type);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", items=");
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
		builder.append("]");
		return builder.toString();
	}
}
