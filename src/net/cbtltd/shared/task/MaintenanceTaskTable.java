/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.task;

import net.cbtltd.shared.Service;
import net.cbtltd.shared.api.HasTableService;

public class MaintenanceTaskTable
implements HasTableService {
	
	private String id;
	private String orderby;
	private int startrow;
	private int numrows;
	
	public Service service() {return Service.TASK;}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReservationTaskTable [id=");
		builder.append(id);
		builder.append(", orderby=");
		builder.append(orderby);
		builder.append(", startrow=");
		builder.append(startrow);
		builder.append(", numrows=");
		builder.append(numrows);
		builder.append(", service()=");
		builder.append(service());
		builder.append("]");
		return builder.toString();
	}
}