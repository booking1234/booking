/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.contract;

import java.util.ArrayList;

import net.cbtltd.shared.Contract;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.api.HasService;

public class AgentContractsDelete
implements HasService {

	private String organizationid;	//organizationid for return of organization namids
	private ArrayList<Contract> contracts;
	
	public AgentContractsDelete(String organizationid, ArrayList<Contract> contracts) {
		super();
		this.organizationid = organizationid;
		this.contracts = contracts;
	}

	@Override
	public Service service() {return Service.CONTRACT;}

	public String getOrganizationid() {
		return organizationid;
	}

	public ArrayList<Contract> getContracts() {
		return contracts;
	}
	
}
