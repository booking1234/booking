/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.quote;

import com.google.gwt.core.client.JavaScriptObject;

/** The Class QuoteWidgetItem is the JSNI wrapper of a JSONP object to transfer a quote request from widget to server. */
public class QuoteWidgetItem extends JavaScriptObject {
	
	/**
	 * Instantiates a new quote widget item.
	 */
	protected QuoteWidgetItem() {}
	
	/**
	 * Creates a new JSONP object.
	 *
	 * @return the quote widget item
	 */
	public static native QuoteWidgetItem create() /*-{return new Object();}-*/; //Create an empty instance.
	
	/**
	 * Gets the list price.
	 *
	 * @return the list price.
	 */
	public final native double getPrice() /*-{ return this.price; }-*/;
	
	/**
	 * Gets the quoted price.
	 *
	 * @return the quoted price.
	 */
	public final native double getQuote() /*-{ return this.quote; }-*/;
	
	/**
	 * Gets the deposit percentage required to confirm.
	 *
	 * @return the deposit percentage required to confirm.
	 */
	public final native int getDeposit() /*-{ return this.deposit; }-*/;
	
	/**
	 * Checks if the property is available.
	 *
	 * @return true, if the property is available.
	 */
	public final native boolean getAvailable() /*-{ return this.available; }-*/;
	
	/**
	 * Gets the currency.
	 *
	 * @return the currency
	 */
	public final native String getCurrency() /*-{ return this.currency; }-*/;
	
	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public final native String getMessage() /*-{ return this.message; }-*/;

	/**
	 * String.
	 *
	 * @return the string
	 */
	public final String string() {
		StringBuilder builder = new StringBuilder();
		builder.append("QuoteWidgetItem [getPrice()=");
		builder.append(getPrice());
		builder.append(", getQuote()=");
		builder.append(getQuote());
		builder.append(", getDeposit()=");
		builder.append(getDeposit());
		builder.append(", getAvailable()=");
		builder.append(getAvailable());
		builder.append(", getCurrency()=");
		builder.append(getCurrency());
		builder.append(", getMessage()=");
		builder.append(getMessage());
		builder.append("]");
		return builder.toString();
	}
}