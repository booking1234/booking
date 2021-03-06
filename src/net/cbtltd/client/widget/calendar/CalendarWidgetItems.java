/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.calendar;



import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/** The Class CalendarWidgetItems is the JSNI wrapper of a JSONP object to transfer calendar availability from server to widget. */
public class CalendarWidgetItems extends JavaScriptObject {

	/**
	 * Instantiates a new calendar widget items.
	 */
	protected CalendarWidgetItems() {}
	
	/**
	 * Gets the items.
	 *
	 * @return the items
	 */
	public final native JsArray<CalendarWidgetItem> getItems() /*-{return this.items;}-*/;
	
	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public final native String getMessage() /*-{ return this.message; }-*/;

	/**
	 * Gets the string value of the JSONP object.
	 *
	 * @return the string.
	 */
	public final String string() {
		StringBuilder builder = new StringBuilder();
		builder.append("CalendarWidgetItems [getItems()=");
		builder.append(getItems());
		builder.append(", getMessage()=");
		builder.append(getMessage());
		builder.append("]");
		return builder.toString();
	}

	
}
