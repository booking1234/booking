/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.text;

import com.google.gwt.core.client.JavaScriptObject;

/** The Class TextWidgetItem is the JSNI wrapper of a JSONP object to transfer text between widget and server. */
public class TextWidgetItem extends JavaScriptObject {
	
	/**
	 * Instantiates a new text widget item.
	 */
	protected TextWidgetItem() {}
	
	/**
	 * Creates a new JSONP object.
	 *
	 * @return the text widget item.
	 */
	public static native TextWidgetItem create() /*-{return new Object();}-*/; 
	
	/**
	 * Gets the text ID.
	 *
	 * @return the text ID
	 */
	public final native String getId() /*-{ return this.id; }-*/;
	
	/**
	 * Gets the language code.
	 *
	 * @return the language code.
	 */
	public final native String getLanguage() /*-{ return this.language; }-*/;
	
	/**
	 * Gets the text type.
	 *
	 * @return the text type
	 */
	public final native String getType() /*-{ return this.type; }-*/;
	
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
		builder.append(", getLanguage()=");
		builder.append(getLanguage());
		builder.append(", getType()=");
		builder.append(getType());
		builder.append(", getMessage()=");
		builder.append(getMessage());
		builder.append("]");
		return builder.toString();
	}
}