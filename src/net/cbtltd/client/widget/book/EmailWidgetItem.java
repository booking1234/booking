/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.book;

import com.google.gwt.core.client.JavaScriptObject;

/** The Class EmailWidgetItem is the JSNI wrapper of a JSONP object to transfer a party from server to widget. */
public class EmailWidgetItem extends JavaScriptObject {
	
	/**
	 * Instantiates a new party widget item.
	 */
	protected EmailWidgetItem() {}
	
	/**
	 * Creates a new JSONP object.
	 *
	 * @return the party widget item.
	 */
	public static native EmailWidgetItem create() /*-{return new Object();}-*/;
	
	/**
	 * Gets the family name of the party.
	 *
	 * @return the family name of the party.
	 */
	public final native String getFamilyname() /*-{ return this.familyname; }-*/;
	
	/**
	 * Gets the first (given) name of the party.
	 *
	 * @return the first name of the party.
	 */
	public final native String getFirstname() /*-{ return this.firstname; }-*/;
	
	/**
	 * Gets the message.
	 *
	 * @return the message.
	 */
	public final native String getMessage() /*-{ return this.message; }-*/;

	/**
	 * Gets the string value of the JSONP object.
	 *
	 * @return the string
	 */
	public final String string() {
		StringBuilder builder = new StringBuilder();
		builder.append("EmailWidgetItem [getFamilyname()=");
		builder.append(getFamilyname());
		builder.append(", getFirstname()=");
		builder.append(getFirstname());
		builder.append(", getMessage()=");
		builder.append(getMessage());
		builder.append("]");
		return builder.toString();
	}
}