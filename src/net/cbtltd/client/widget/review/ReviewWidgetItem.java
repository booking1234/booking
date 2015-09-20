/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.review;

import com.google.gwt.core.client.JavaScriptObject;

/** The Class ReviewWidgetItem is the JSNI wrapper of a JSONP object to transfer a guest's feedback from widget to server. */
public class ReviewWidgetItem extends JavaScriptObject {
	
	/**
	 * Instantiates a new review widget item.
	 */
	protected ReviewWidgetItem() {}
	
	/**
	 * Creates a new JSONP object.
	 *
	 * @return the review widget item
	 */
	public static native ReviewWidgetItem create() /*-{return new Object();}-*/; //Create an empty instance.
	
	/**
	 * Gets the date of the feedback.
	 *
	 * @return the date of the feedback.
	 */
	public final native String getDate() /*-{ return this.date; }-*/;
	
	/**
	 * Gets the rating.
	 *
	 * @return the rating
	 */
	public final native double getRating() /*-{ return this.rating; }-*/;
	
	/**
	 * Gets the notes.
	 *
	 * @return the notes
	 */
	public final native String getNotes() /*-{ return this.notes; }-*/;

	/**
	 * Gets the string value of the JSONP object.
	 *
	 * @return the string.
	 */
	public final String string() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReviewWidgetItem [getDate()=");
		builder.append(getDate());
		builder.append(", getRating()=");
		builder.append(getRating());
		builder.append(", getNotes()=");
		builder.append(getNotes());
		builder.append("]");
		return builder.toString();
	}
	
}