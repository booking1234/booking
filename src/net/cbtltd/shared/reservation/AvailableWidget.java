/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.reservation;

import java.util.Date;

public class AvailableWidget
extends Available {
	
	public AvailableWidget() {}
	
	public AvailableWidget(String productid, Date fromdate, Date todate) {
		setProductid(productid);
		setFromdate(fromdate);
		setTodate(todate);
	}

}
