/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.contract;

import net.cbtltd.shared.api.HasService;

public class AgentContractCreate
extends MarketItem
implements HasService {

	public AgentContractCreate(MarketItem marketitem) {
		super(marketitem.getSupplierid(), marketitem.getCustomerid());
	}
}
