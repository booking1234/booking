/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.workflow;

import java.util.ArrayList;
import java.util.HashMap;

import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Workflow;

public class WorkflowProgress
extends Workflow {

	private String actorid;
//	private String states;
	HashMap<String, ArrayList<String>> states;
	
	public String getActorid() {
		return actorid;
	}

	public void setActorid(String actorid) {
		this.actorid = actorid;
	}

	public String getStates() {
		return NameId.getCdlist(states);
	}

	public void setStates(HashMap<String, ArrayList<String>> states) {
		this.states = states;
	}

	public String getProcesses() {
		return NameId.getCdlist(new ArrayList<String>(states.keySet()));
	}


//	public void setStatemap(HashMap<String, ArrayList<String>> states) {
//		this.states = NameId.getCdlist(states);
//	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WorkflowProgress [actorid=");
		builder.append(actorid);
		builder.append(", states=");
		builder.append(states);
		builder.append(", getProcesses()=");
		builder.append(getProcesses());
		builder.append(", service()=");
		builder.append(service());
		builder.append("]");
		return builder.toString();
	}

}
