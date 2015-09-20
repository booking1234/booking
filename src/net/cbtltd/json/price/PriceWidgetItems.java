/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.json.price;

import java.util.Arrays;

import net.cbtltd.json.JSONResponse;

public class PriceWidgetItems implements JSONResponse {

	private PriceWidgetItem[] items;
	private String message;

	public PriceWidgetItem[] getItems() {
		return items;
	}

	public void setItems(PriceWidgetItem[] items) {
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
		builder.append("PriceWidgetItems [items=");
		builder.append(Arrays.toString(items));
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
	
}
