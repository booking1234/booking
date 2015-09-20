/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.json.reservation;

import java.util.Arrays;

import net.cbtltd.json.JSONResponse;

public class ReservationWidgetItems implements JSONResponse {

	private ReservationWidgetItem[] items;
	private String message;

	public ReservationWidgetItem[] getItems() {
		return items;
	}

	public void setItems(ReservationWidgetItem[] items) {
		this.items = items;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReservationWidgetItems [items=");
		builder.append(Arrays.toString(items));
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
	
}
