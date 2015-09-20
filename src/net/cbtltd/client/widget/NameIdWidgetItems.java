/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget;


import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/** The Class NameIdWidgetItems is the JSNI wrapper of a JSONP object to transfer a list of name ID pairs from server to widget. */
public class NameIdWidgetItems extends JavaScriptObject {

	/**
	 * Instantiates a new name id list widget.
	 */
	protected NameIdWidgetItems() {}
	
	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	public final native JsArray<NameIdWidgetItem> getItems() /*-{return this.items;}-*/;
	
	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public final native String getMessage() /*-{return this.message;}-*/;

	/**
	 * Gets the string value of the JSONP object.
	 *
	 * @return the string
	 */
	public final String string() {
		StringBuilder builder = new StringBuilder();
		builder.append("NameIdWidgetItems [getItems()=");
		builder.append(getItems());
		builder.append(", getMessage()=");
		builder.append(getMessage());
		builder.append("]");
		return builder.toString();
	}
}
