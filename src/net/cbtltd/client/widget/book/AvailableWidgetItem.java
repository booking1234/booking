/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.book;

import com.google.gwt.core.client.JavaScriptObject;

/** The Class AvailableWidgetItem is the JSNI wrapper of a JSONP object to transfer an available item from server to widget. */
public class AvailableWidgetItem extends JavaScriptObject {
	
	/**
	 * Instantiates a new available widget item.
	 */
	protected AvailableWidgetItem() {}
	
	/**
	 * Creates a new JSONP object.
	 *
	 * @return the available widget item
	 */
	public static native AvailableWidgetItem create() /*-{return new Object();}-*/; 
	
	/**
	 * Checks if it is available.
	 *
	 * @return true, if it is available.
	 */
	public final native boolean isAvailable() /*-{ return this.available; }-*/;

	/**
	 * Gets the string value of the JSONP object.
	 *
	 * @return the string.
	 */
	public final String string() {
		StringBuilder builder = new StringBuilder();
		builder.append("AvailableWidgetItem [isAvailable()=");
		builder.append(isAvailable());
		builder.append("]");
		return builder.toString();
	}
	
}
