package net.cbtltd.server.api;

import net.cbtltd.shared.ManagerToGateway;

public interface ManagerToGatewayMapper  {
	void create(ManagerToGateway managerToGateway);
	ManagerToGateway read(int id);
	void update(ManagerToGateway managerToGateway);
	void delete(int id);
	void deleteBySupplierId(int id);
	
	ManagerToGateway readBySupplierId(int id);
}
