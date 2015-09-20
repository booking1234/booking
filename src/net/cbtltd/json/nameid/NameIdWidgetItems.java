/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.json.nameid;

import java.util.Arrays;

import net.cbtltd.json.JSONResponse;

public class NameIdWidgetItems implements JSONResponse {
	private NameIdWidgetItem[] items;
	private String message;

	public NameIdWidgetItem[] getItems() {
		return items;
	}

	public void setItems(NameIdWidgetItem[] items) {
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
		builder.append("AvailableWidgetItems [items=");
		builder.append(Arrays.toString(items));
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
	
}
