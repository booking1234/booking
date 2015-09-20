package net.cbtltd.shared.api;

import net.cbtltd.shared.finance.GatewayAction;

public interface IsGateway {
	String getAltpartyid();
	void createPayment(GatewayAction action);
	GatewayAction getPayment(GatewayAction action);
	void createRefund(GatewayAction action);

}
