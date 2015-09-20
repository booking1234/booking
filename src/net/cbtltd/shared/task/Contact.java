/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.task;

import java.util.ArrayList;

import net.cbtltd.shared.Process;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.api.HasService;

public class Contact
extends Process
implements HasService {

	public static final String INITIAL = "Initial";
	public static final String CREATED = "Created";
	public static final String FINAL = "Final";

	public static final String NAME = "event.name";
	public static final String DATE = "event.date";
	public static final String DUEDATE = "event.duedate";
	public static final String DONEDATE = "event.donedate";
	public static final String NOTES = "event.notes";
	public static final String PROCESS = "event.process";
	public static final String STATE = "event.state";
	public static final String TYPE = "event.type";

	public enum Type {Email, Phone, SMS, Facebook};

	private String type;
	private ArrayList<String> adressees;
	
	public Service service() {return Service.TASK;}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<String> getAdressees() {
		return adressees;
	}

	public void setAdressees(ArrayList<String> adressees) {
		this.adressees = adressees;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Contact [type=");
		builder.append(type);
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
