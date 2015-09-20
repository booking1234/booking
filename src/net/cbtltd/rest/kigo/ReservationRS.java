/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	3.0.8
 */
package net.cbtltd.rest.kigo;

public class ReservationRS extends Response {

	private Booking API_REPLY;

	ReservationRS () {}	//no args constructor
	
	public Booking getBooking() {
		return API_REPLY;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append(", API_REPLY=");
		builder.append(API_REPLY);
		builder.append("]");
		return builder.toString();
	}
}