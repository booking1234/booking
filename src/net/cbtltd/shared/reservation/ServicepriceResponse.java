/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.reservation;

import net.cbtltd.shared.DoubleResponse;

public class ServicepriceResponse extends DoubleResponse {

	private int row = 0;

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ServicepriceResponse [row=");
		builder.append(row);
		builder.append(", getValue()=");
		builder.append(getValue());
		builder.append("]");
		return builder.toString();
	}
	
}
