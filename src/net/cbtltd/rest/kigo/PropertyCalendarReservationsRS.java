package net.cbtltd.rest.kigo;

import java.util.Arrays;

public class PropertyCalendarReservationsRS extends Response {

	private Booking[] API_REPLY;

	PropertyCalendarReservationsRS () {}	//no args constructor

	public Booking[] getReservations() {
		return API_REPLY;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PropertyCalendarReservationsRS [API_REPLY=");
		builder.append(Arrays.toString(API_REPLY));
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
}
