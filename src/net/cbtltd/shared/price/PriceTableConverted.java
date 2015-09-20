/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.price;

import net.cbtltd.shared.Price;
import net.cbtltd.shared.api.HasTableService;

public class PriceTableConverted
extends Price
implements HasTableService {

	private String orderby;
	private int startrow;
	private int numrows;
	
	public PriceTableConverted() {}
	
	public PriceTableConverted(String partyid, String entitytype, String entityid, String currency) {
		this.partyid = partyid;
		this.entitytype = entitytype;
		this.entityid = entityid;
		this.currency = currency;
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
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append(" OrderBy ").append(getOrderby());
		sb.append(" StartRow ").append(getStartrow());
		sb.append(" NumRows ").append(getNumrows());
		return sb.toString();
	}

}
