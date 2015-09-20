/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.json.rate;

import java.util.Arrays;

import net.cbtltd.json.JSONResponse;

public class RateWidgetItems implements JSONResponse {
	private RateWidgetItem[] items;
	private String message;

	public RateWidgetItem[] getItems() {
		return items;
	}

	public void setItems(RateWidgetItem[] items) {
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
		builder.append("RateWidgetItems [items=");
		builder.append(Arrays.toString(items));
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
	
}
