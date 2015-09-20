/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.journal;

import net.cbtltd.shared.Service;
import net.cbtltd.shared.api.HasTableService;

public class EventJournalTable 
extends EventJournal
implements HasTableService {

	private String id;
	private String orderby;
	private int startrow;
	private int numrows;
	
	public Service service() {return Service.JOURNAL;}

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
		builder.append("EventJournalTable [id=");
		builder.append(id);
		builder.append(", state=");
		builder.append(state);
		builder.append(", orderby=");
		builder.append(orderby);
		builder.append(", startrow=");
		builder.append(startrow);
		builder.append(", numrows=");
		builder.append(numrows);
		builder.append(", actionid=");
		builder.append(actionid);
		builder.append(", matchid=");
		builder.append(matchid);
		builder.append(", locationid=");
		builder.append(locationid);
		builder.append(", accountid=");
		builder.append(accountid);
		builder.append(", entitytype=");
		builder.append(entitytype);
		builder.append(", entityid=");
		builder.append(entityid);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", unit=");
		builder.append(unit);
		builder.append(", unitprice=");
		builder.append(unitprice);
		builder.append(", debitamount=");
		builder.append(debitamount);
		builder.append(", creditamount=");
		builder.append(creditamount);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", description=");
		builder.append(description);
		builder.append(", accountname=");
		builder.append(accountname);
		builder.append(", entityname=");
		builder.append(entityname);
		builder.append(", locationname=");
		builder.append(locationname);
		builder.append(", groupby=");
		builder.append(groupby);
		builder.append(", service=");
		builder.append(service);
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
		builder.append(", values=");
		builder.append(values);
		builder.append(", attributes=");
		builder.append(attributemap);
		builder.append(", texts=");
		builder.append(texts);
		builder.append(", name=");
		builder.append(name);
		builder.append(", service()=");
		builder.append(service());
		builder.append("]");
		return builder.toString();
	}

}
