/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.reservation;

import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Price;

public class ServicepriceRequest extends Price {

	private int row = 0;
	
	public ServicepriceRequest() {}
	
	public ServicepriceRequest(String productid, String supplierid, String name, int row) {
		entitytype = NameId.Type.Product.name();
		entityid = productid;
		partyid = supplierid;
		this.name = name;
		this.row = row;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
}
