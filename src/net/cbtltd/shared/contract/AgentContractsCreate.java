/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.contract;

import java.util.ArrayList;

import net.cbtltd.shared.Service;
import net.cbtltd.shared.api.HasService;

public class AgentContractsCreate
implements HasService {

	private String organizationid;	//organizationid for return of organization namids
	private ArrayList<MarketItem> marketitems;
	
	public AgentContractsCreate(String organizationid, ArrayList<MarketItem> marketitems) {
		this.organizationid = organizationid;
		this.marketitems = marketitems;
	}
	
	public Service service() {return Service.CONTRACT;}

	public String getOrganizationid() {
		return organizationid;
	}

	public ArrayList<MarketItem> getMarketitems() {
		return marketitems;
	}
}
