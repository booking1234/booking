/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.json.culture;

import net.cbtltd.json.JSONResponse;

public class CultureWidgetItem implements JSONResponse {
	private String culture;
	private String message;

	public String getCulture() {
		return culture;
	}

	public void setCulture(String culture) {
		this.culture = culture;
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
		builder.append("CultureWidgetItem [culture=");
		builder.append(culture);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}

}