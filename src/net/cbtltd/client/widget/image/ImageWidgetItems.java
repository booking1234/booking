/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.image;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/** The Class ImageWidgetItems is the JSNI wrapper of a JSONP object to transfer an image gallery from server to widget. */
public class ImageWidgetItems extends JavaScriptObject {

	/**
	 * Instantiates a new calendar widget items.
	 */
	protected ImageWidgetItems() {}
	
	/**
	 * Gets the items.
	 *
	 * @return the items
	 */
	public final native JsArray<ImageWidgetItem> getItems() /*-{return this.items;}-*/;
	
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
		builder.append("ImageWidgetItems [getItems()=");
		builder.append(getItems());
		builder.append(", getMessage()=");
		builder.append(getMessage());
		builder.append("]");
		return builder.toString();
	}

	
}
