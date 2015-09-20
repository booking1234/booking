/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.price;

import java.util.Date;

public class WidgetPriceTable 
extends PriceTable {

	public WidgetPriceTable () {}
	
	public WidgetPriceTable (
			String entitytype,
			String entityid,
			Date date,
			String currency, 
			String type, 
			int startrow,
			int numrows) {
		setEntitytype(entitytype);
		setEntityid(entityid);
		setCurrency(currency);
		setType(type);
		setDate(date);
		setStartrow(startrow);
		setNumrows(numrows);
	}

}
