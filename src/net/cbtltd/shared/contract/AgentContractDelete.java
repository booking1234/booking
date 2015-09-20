/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.contract;

import net.cbtltd.shared.Contract;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.api.HasService;

public class AgentContractDelete
extends Contract
implements HasService {

	public AgentContractDelete(Contract contract) {
		super(contract.getName(), contract.getOrganizationid(), contract.getPartyid());
	}
	
	@Override
	public Service service() {return Service.CONTRACT;}

	public String getId() {
		return id;
	}
}
