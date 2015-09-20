package net.cbtltd.shared.party;

import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Service;

public class AgentNameIdAction extends NameIdAction {

    public AgentNameIdAction() {
	super(Service.PARTY);
    }
    
    public AgentNameIdAction(Service service) {
	super(service);
    }

    public AgentNameIdAction(Service service, String type) {
	super(service, type);
    }

}
