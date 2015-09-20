package net.cbtltd.server.api;

import java.util.List;

import net.cbtltd.shared.NameId;
import net.cbtltd.shared.PaymentGatewayProvider;

public interface PaymentGatewayProviderMapper {
	void create(PaymentGatewayProvider paymentGatewayProvider);
	PaymentGatewayProvider read(int id);
	PaymentGatewayProvider readByName(String name);
	List<PaymentGatewayProvider> list();
	List<NameId> nameid();
	void update(PaymentGatewayProvider paymentGatewayProvider);
	void delete(int id);
}
