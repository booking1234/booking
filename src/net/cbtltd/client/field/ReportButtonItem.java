/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import com.google.gwt.core.client.JavaScriptObject;

/** The Class ReportButtonItem is the JSNI wrapper of a JSONP object to transfer a report repsonse. */
public class ReportButtonItem extends JavaScriptObject {
	
	/**
	 * Instantiates a new quote widget item.
	 */
	protected ReportButtonItem() {}
	
	/**
	 * Creates a new JSONP object.
	 *
	 * @return the quote widget item
	 */
	public static native ReportButtonItem create() /*-{return new Object();}-*/; //Create an empty instance.
	
	public final native String getId() /*-{ return this.id; }-*/;
	public final native String getDesigh() /*-{ return this.design; }-*/;
	public final native String getFormat() /*-{ return this.format; }-*/;
	public final native String getNotes() /*-{ return this.notes; }-*/;

	public final String string() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReportButtonItem [getId()=");
		builder.append(getId());
		builder.append(", getDesigh()=");
		builder.append(getDesigh());
		builder.append(", getFormat()=");
		builder.append(getFormat());
		builder.append(", getNotes()=");
		builder.append(getNotes());
		builder.append("]");
		return builder.toString();
	}
}