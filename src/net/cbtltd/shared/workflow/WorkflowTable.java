/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.workflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Task;
import net.cbtltd.shared.api.HasState;
import net.cbtltd.shared.api.HasTableService;

public class WorkflowTable
implements HasState, HasTableService {
	
	private Date enddate;
	private String id; //	organizationid;
	private String actorid;
	private String state;
	private String orderby;
	private int startrow;
	private int numrows;
	protected HashMap<String,ArrayList<String>> selection;

	public WorkflowTable() {}
	
	public Service service() {return Service.WORKFLOW;}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActorid() {
		return actorid;
	}

	public void setActorid(String actorid) {
		this.actorid = actorid;
	}

	public String getMaintenancestates() {
		return NameId.getCdlist(selection.get(Task.Type.Maintenance.name()));
	}

	public String getMarketingstates() {
		return NameId.getCdlist(selection.get(Task.Type.Marketing.name()));
	}

	public String getReservationstates() {
		return NameId.getCdlist(selection.get(NameId.Type.Reservation.name()));
	}

	public String getServicestates() {
		return NameId.getCdlist(selection.get(Task.Type.Service.name()));
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public boolean noOrderby() {
		return orderby == null || orderby.isEmpty();
	}
	
	public int getStartrow() {
		return startrow;
	}

	public void setStartrow(int startrow) {
		this.startrow = startrow;
	}

	public int getNumrows() {
		return numrows;
	}

	public void setNumrows(int numrows) {
		this.numrows = numrows;
	}

	public HashMap<String, ArrayList<String>> getSelection() {
		return selection;
	}

	public void setSelection(HashMap<String, ArrayList<String>> selection) {
		this.selection = selection;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WorkflowTable [enddate=");
		builder.append(enddate);
		builder.append(", id=");
		builder.append(id);
		builder.append(", actorid=");
		builder.append(actorid);
		builder.append(", state=");
		builder.append(state);
		builder.append(", orderby=");
		builder.append(orderby);
		builder.append(", startrow=");
		builder.append(startrow);
		builder.append(", numrows=");
		builder.append(numrows);
		builder.append(", selection=");
		builder.append(selection);
		builder.append(", service()=");
		builder.append(service());
		builder.append(", getMaintenancestates()=");
		builder.append(getMaintenancestates());
		builder.append(", getMarketingstates()=");
		builder.append(getMarketingstates());
		builder.append(", getReservationstates()=");
		builder.append(getReservationstates());
		builder.append(", getServicestates()=");
		builder.append(getServicestates());
		builder.append("]");
		return builder.toString();
	}
}
