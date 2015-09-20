/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.calendar;


import com.google.gwt.core.client.JavaScriptObject;

/** The Class CalendarWidgetItem is the JSNI wrapper of a JSONP object to transfer the calendar date between widget and server. */
public class CalendarWidgetItem extends JavaScriptObject {

	/**
	 * Instantiates a new calendar widget item.
	 */
	protected CalendarWidgetItem() {}
	
	/**
	 * Creates a new JSONP object.
	 *
	 * @return the calendar widget item
	 */
	public static native CalendarWidgetItem create() /*-{return new Object();}-*/; //Create an empty instance.
	
	/**
	 * Gets the date in the month to be displayed.
	 *
	 * @return the date in the month to be displayed.
	 */
	public final native String getDate() /*-{ return this.date; }-*/;

	/**
	 * Gets the state of the reservation.
	 *
	 * @return the state of the reservation.
	 */
	public final native String getState() /*-{ return this.state; }-*/;

	/**
	 * Gets the string value of the JSONP object.
	 *
	 * @return the string
	 */
	public final String string() {
		StringBuilder builder = new StringBuilder();
		builder.append("CalendarWidgetItem [date=");
		builder.append(getDate());
		builder.append(", state=");
		builder.append(getState());
		builder.append("]");
		return builder.toString();
	}
}
