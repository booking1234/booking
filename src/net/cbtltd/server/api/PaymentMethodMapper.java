package net.cbtltd.server.api;

import java.util.List;

import net.cbtltd.shared.PaymentMethod;

public interface PaymentMethodMapper {

	void create(PaymentMethod item);

	List<PaymentMethod> list();

	PaymentMethod read_by_pm(Integer pdID);
	
	PaymentMethod read(Integer id);
	
	void update(PaymentMethod item);
	
	void delete(int id);

}
