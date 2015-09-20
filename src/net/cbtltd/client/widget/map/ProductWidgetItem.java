/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.map;

import com.google.gwt.core.client.JavaScriptObject;

/** The Class ProductWidgetItem is the JSNI wrapper of a JSONP object to transfer a position between widget and server. */
public class ProductWidgetItem extends JavaScriptObject {
	
	/**
	 * Instantiates a new map widget item.
	 */
	protected ProductWidgetItem() {}
	
	/**
	 * Creates a new JSONP object.
	 *
	 * @return the map widget item.
	 */
	public static native ProductWidgetItem create() /*-{return new Object();}-*/; 
	
	/**
	 * Gets the product ID.
	 *
	 * @return the product ID
	 */
	public final native String getId() /*-{ return this.id; }-*/;
	
	/**
	 * Gets the product name.
	 *
	 * @return the product name
	 */
	public final native String getName() /*-{ return this.name; }-*/;
	
	/**
	 * Gets the product address.
	 *
	 * @return the product address
	 */
	public final native String getPhysicaladdress() /*-{ return this.physicaladdress; }-*/;
	
	/**
	 * Gets the latitude.
	 *
	 * @return the latitude.
	 */
	public final native double getLatitude() /*-{ return this.latitude; }-*/;
	
	/**
	 * Gets the longitude.
	 *
	 * @return the longitude
	 */
	public final native double getLongitude() /*-{ return this.longitude; }-*/;
	
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
		builder.append("MapWidgetItem [getId()=");
		builder.append(getId());
		builder.append(", getName()=");
		builder.append(getName());
		builder.append(", getPhysicaladdress()=");
		builder.append(getPhysicaladdress());
		builder.append(", getLatitude()=");
		builder.append(getLatitude());
		builder.append(", getLongitude()=");
		builder.append(getLongitude());
		builder.append(", getMessage()=");
		builder.append(getMessage());
		builder.append("]");
		return builder.toString();
	}
}