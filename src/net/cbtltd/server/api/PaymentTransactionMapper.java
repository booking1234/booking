package net.cbtltd.server.api;

import java.util.List;

import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.PaymentTransaction;

public interface PaymentTransactionMapper {
	void create(PaymentTransaction paymentTransaction);
	PaymentTransaction read(int id);
	void update(PaymentTransaction paymentTransaction);
	void delete(int id);
	List<PaymentTransaction> readByReservationId(int reservationId);
	List<PaymentTransaction> listLast24hours(NameIdAction action);
}
