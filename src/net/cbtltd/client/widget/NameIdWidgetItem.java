/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget;

import com.google.gwt.core.client.JavaScriptObject;

/** The Class NameIdWidgetItem is the JSNI wrapper of a JSONP object to transfer a name ID pair from server to widget. */
public class NameIdWidgetItem extends JavaScriptObject {
	
	/**
	 * Instantiates a new name id widget item.
	 */
	protected NameIdWidgetItem() {}
	
	/**
	 * Creates a new JSONP object.
	 *
	 * @return the name id widget item
	 */
	public static native NameIdWidgetItem create() /*-{return new Object();}-*/; //Create an empty instance.
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public final native String getId() /*-{ return this.id; }-*/;
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public final native String getName() /*-{ return this.name; }-*/;

	/**
	 * Gets the string value of the JSONP object.
	 *
	 * @return the string
	 */
	public final String string() {
		StringBuilder builder = new StringBuilder();
		builder.append("NameIdWidgetItem [getId()=");
		builder.append(getId());
		builder.append(", getName()=");
		builder.append(getName());
		builder.append("]");
		return builder.toString();
	}
}