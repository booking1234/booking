/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.book;

import com.google.gwt.core.client.JavaScriptObject;

/** The Class PayWidgetItem is the JSNI wrapper of a JSONP object to transfer a payment request from widget to server. */
public class PayWidgetItem extends JavaScriptObject {
	
	/**
	 * Instantiates a new pay widget item.
	 */
	protected PayWidgetItem() {}
	
	/**
	 * Creates a new JSONP object.
	 *
	 * @return the pay widget item
	 */
	public static native PayWidgetItem create() /*-{return new Object();}-*/;
	
	/**
	 * Gets the ID of the payee.
	 *
	 * @return the ID of the payee.
	 */
	public final native String getId() /*-{ return this.id; }-*/;
	
	/**
	 * Gets the name of the payee.
	 *
	 * @return the name of the payee.
	 */
	public final native String getName() /*-{ return this.name; }-*/;
	
	/**
	 * Gets the payment state.
	 *
	 * @return the payment state
	 */
	public final native String getState() /*-{ return this.state; }-*/;
	
	/**
	 * Gets the amount to be paid.
	 *
	 * @return the amount to be paid.
	 */
	public final native double getAmount() /*-{ return this.amount; }-*/;
	
	/**
	 * Gets the payment currency.
	 *
	 * @return the payment currency
	 */
	public final native String getCurrency() /*-{ return this.currency; }-*/;
	
	/**
	 * Gets the payment message.
	 *
	 * @return the payment message
	 */
	public final native String getMessage() /*-{ return this.message; }-*/;

	/**
	 * Checks if it is in the specified state.
	 *
	 * @param state the specified state.
	 * @return true, if it is in the specified state.
	 */
	public final boolean hasState(String state) {return getState() != null && getState().equals(state);}

	/**
	 * Gets the string value of the JSONP object.
	 *
	 * @return the string.
	 */
	public final String string() {
		StringBuilder builder = new StringBuilder();
		builder.append("PayWidgetItem [getId()=");
		builder.append(getId());
		builder.append(", getName()=");
		builder.append(getName());
		builder.append(", getState()=");
		builder.append(getState());
		builder.append(", getAmount()=");
		builder.append(getAmount());
		builder.append(", getCurrency()=");
		builder.append(getCurrency());
		builder.append(", getMessage()=");
		builder.append(getMessage());
		builder.append("]");
		return builder.toString();
	}
	
}
