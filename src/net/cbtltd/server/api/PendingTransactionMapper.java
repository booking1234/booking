package net.cbtltd.server.api;

import java.util.Date;
import java.util.List;

import net.cbtltd.shared.PendingTransaction;
import net.cbtltd.shared.Reservation;

public interface PendingTransactionMapper  {
	void create(PendingTransaction pendingTransaction);
	PendingTransaction read(int id);
	void update(PendingTransaction pendingTransaction);
	void delete(int id);
	
	PendingTransaction readByReservation(Reservation reservation);
	List<PendingTransaction> readActiveTransactionsByDate(Date date);
}
