/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.image;

import com.google.gwt.core.client.JavaScriptObject;

/** The Class MapWidgetItem is the JSNI wrapper of a JSONP object to transfer a position between widget and server. */
public class ImageWidgetItem extends JavaScriptObject {
	
	/**
	 * Instantiates a new map widget item.
	 */
	protected ImageWidgetItem() {}
	
	/**
	 * Creates a new JSONP object.
	 *
	 * @return the map widget item.
	 */
	public static native ImageWidgetItem create() /*-{return new Object();}-*/; 
	
	/**
	 * Gets the product ID.
	 *
	 * @return the product ID
	 */
	public final native String getProductid() /*-{ return this.productid; }-*/;
	
	/**
	 * Gets the latitude.
	 *
	 * @return the latitude.
	 */
	public final native String getURL() /*-{ return this.url; }-*/;
	
	/**
	 * Gets the longitude.
	 *
	 * @return the longitude
	 */
	public final native String getNotes() /*-{ return this.notes; }-*/;
	
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
		builder.append("ImageWidgetItem [url=");
		builder.append(getURL());
		builder.append(", notes=");
		builder.append(getNotes());
		builder.append("]");
		return builder.toString();
	}
}