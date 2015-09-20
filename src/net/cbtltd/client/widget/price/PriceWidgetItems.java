/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.price;



import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/** The Class PriceWidgetItems is the JSNI wrapper of a JSONP object to transfer prices from server to widget. */
public class PriceWidgetItems extends JavaScriptObject {

	/**
	 * Instantiates a new price list widget.
	 */
	protected PriceWidgetItems() {}
	
	/**
	 * Gets the price list.
	 *
	 * @return the price list.
	 */
	public final native JsArray<PriceWidgetItem> getItems() /*-{return this.items;}-*/;
	
	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public final native String getMessage() /*-{ return this.message; }-*/;

	/**
	 * Gets the string value of the JSONP object.
	 *
	 * @return the string
	 */
	public final String string() {
		StringBuilder builder = new StringBuilder();
		builder.append("PriceWidgetItems [getItems()=");
		builder.append(getItems());
		builder.append(", getMessage()=");
		builder.append(getMessage());
		builder.append("]");
		return builder.toString();
	}
}
