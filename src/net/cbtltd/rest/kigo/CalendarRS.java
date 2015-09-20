/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	3.0.8
 */
package net.cbtltd.rest.kigo;

import net.cbtltd.shared.Reservation;


public class CalendarRS extends Response {

	private int RES_ID;
	private String RES_STATUS;
	private boolean RES_IS_FOR;
	private String RES_CHECK_IN;
	private String RES_CHECK_OUT;

	public CalendarRS () {}	//no args constructor
	
	public int getRES_ID() {
		return RES_ID;
	}

	public void setRES_ID(int rES_ID) {
		RES_ID = rES_ID;
	}

	public String getRES_STATUS() {
		return RES_STATUS;
	}

	public void setRES_STATUS(String rES_STATUS) {
		RES_STATUS = rES_STATUS;
	}

	public String getState() {
		return RES_STATUS == null || RES_STATUS.equalsIgnoreCase("HOLD") ? Reservation.State.Provisional.name() : Reservation.State.Closed.name();
	}
	
	public boolean isRES_IS_FOR() {
		return RES_IS_FOR;
	}

	public void setRES_IS_FOR(boolean rES_IS_FOR) {
		RES_IS_FOR = rES_IS_FOR;
	}

	public String getRES_CHECK_IN() {
		return RES_CHECK_IN;
	}

	public void setRES_CHECK_IN(String rES_CHECK_IN) {
		RES_CHECK_IN = rES_CHECK_IN;
	}

	public String getRES_CHECK_OUT() {
		return RES_CHECK_OUT;
	}

	public void setRES_CHECK_OUT(String rES_CHECK_OUT) {
		RES_CHECK_OUT = rES_CHECK_OUT;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CalendarRS [RES_ID=");
		builder.append(RES_ID);
		builder.append(", RES_STATUS=");
		builder.append(RES_STATUS);
		builder.append(", RES_IS_FOR=");
		builder.append(RES_IS_FOR);
		builder.append(", RES_CHECK_IN=");
		builder.append(RES_CHECK_IN);
		builder.append(", RES_CHECK_OUT=");
		builder.append(RES_CHECK_OUT);
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
}