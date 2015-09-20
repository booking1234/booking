/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.price;

import com.google.gwt.core.client.JavaScriptObject;

/** The Class PriceWidgetItem is the JSNI wrapper of a JSONP object to transfer a price request from widget to server. */
public class PriceWidgetItem extends JavaScriptObject {
	
	/**
	 * Instantiates a new price widget item.
	 */
	protected PriceWidgetItem() {}
	
	/**
	 * Creates a new JSONP object.
	 *
	 * @return the price widget item.
	 */
	public static native PriceWidgetItem create() /*-{return new Object();}-*/; 
	
	/**
	 * Gets the date from which prices are required.
	 *
	 * @return the date from which prices are required.
	 */
	public final native String getFromdate() /*-{ return this.fromdate; }-*/;
	
	/**
	 * Gets the date to which prices are required.
	 *
	 * @return the date to which prices are required.
	 */
	public final native String getTodate() /*-{ return this.todate; }-*/;
	
	/**
	 * Gets the price.
	 *
	 * @return the price.
	 */
	public final native double getPrice() /*-{ return this.price; }-*/;
	
	/**
	 * Gets the minimum price.
	 *
	 * @return the minimum price.
	 */
	public final native double getMinimum() /*-{ return this.minimum; }-*/;
	
	/**
	 * Gets the currency.
	 *
	 * @return the currency
	 */
	public final native String getCurrency() /*-{ return this.currency; }-*/;

	/**
	 * Gets the string value of the JSONP object.
	 *
	 * @return the string
	 */
	public final String string() {
		StringBuilder builder = new StringBuilder();
		builder.append("PriceWidgetItem [getFromdate()=");
		builder.append(getFromdate());
		builder.append(", getTodate()=");
		builder.append(getTodate());
		builder.append(", getPrice()=");
		builder.append(getPrice());
		builder.append(", getMinimum()=");
		builder.append(getMinimum());
		builder.append(", getCurrency()=");
		builder.append(getCurrency());
		builder.append("]");
		return builder.toString();
	}
	
}