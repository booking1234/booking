package net.cbtltd.server.api;

import java.util.List;

import net.cbtltd.shared.finance.gateway.CustomerPaymentProfile;

public interface CustomerPaymentProfileMapper {
	void create(CustomerPaymentProfile profile);	
	CustomerPaymentProfile read (Integer id);	
	CustomerPaymentProfile readByReservation(Integer reservationId);
	void update(CustomerPaymentProfile profile);
	void delete(Integer id);
	
	List<CustomerPaymentProfile> list();
}
