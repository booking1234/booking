/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.price;

import net.cbtltd.shared.Price;

public class PriceUpdate extends Price {

	public PriceUpdate() {}
	
	public PriceUpdate(String id, String state) {
//		setCost(price.getCost());
//		setCurrency(price.getCurrency());
//		setDate(price.getDate());
//		setEntityid(price.getEntityid());
//		setEntityname(price.getEntityname());
//		setEntitytype(price.getEntitytype());
//		setId(price.getId());
//		setMinimum(price.getMinimum());
//		setName(price.getName());
//		setOrganizationid(price.getOrganizationid());
//		setPartyid(price.getPartyid());
//		setPartyname(price.getPartyname());
//		setQuantity(price.getQuantity());
//		setState(price.getState());
		setId(id);
		setState(state);
	}
}
