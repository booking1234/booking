/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.review;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/** The Class ReviewWidgetItems is the JSNI wrapper of a JSONP object to transfer guest feedback from server to widget. */
public class ReviewWidgetItems extends JavaScriptObject {

	/**
	 * Instantiates a new review widget items.
	 */
	protected ReviewWidgetItems() {}
	
	/**
	 * Gets the feedback.
	 *
	 * @return the feedback.
	 */
	public final native JsArray<ReviewWidgetItem> getItems() /*-{return this.items;}-*/;
	
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
		builder.append("ReviewWidgetItems [getItems()=");
		builder.append(getItems());
		builder.append(", getMessage()=");
		builder.append(getMessage());
		builder.append("]");
		return builder.toString();
	}
}
