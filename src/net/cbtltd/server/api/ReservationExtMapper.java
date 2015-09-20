package net.cbtltd.server.api;

import java.util.List;

import net.cbtltd.shared.ReservationExt;

public interface ReservationExtMapper {

	public List<ReservationExt> readReservationExt(ReservationExt temp);
	public void create(ReservationExt temp);
	public void update(ReservationExt reservationExt);
	public void delete(String  reservationId);
	
}
