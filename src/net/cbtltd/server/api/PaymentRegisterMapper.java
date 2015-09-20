package net.cbtltd.server.api;

import java.util.List;

import net.cbtltd.shared.finance.gateway.PaymentRegister;

public interface PaymentRegisterMapper {
	void create(PaymentRegister paymentRegister);	
	PaymentRegister read (Integer id);	
	void update(PaymentRegister paymentRegister);
	void delete(Integer id);
	
	List<PaymentRegister> list();
}
