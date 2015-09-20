/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.book;

import com.google.gwt.core.client.JavaScriptObject;

/** The Class BookWidgetItem is the JSNI wrapper of a JSONP object to transfer an reservation from widget to server. */
public class BookWidgetItem extends JavaScriptObject {
	
	/**
	 * Instantiates a new book widget item.
	 */
	protected BookWidgetItem() {}
	
	/**
	 * Creates a new JSONP object.
	 *
	 * @return the book widget item
	 */
	public static native BookWidgetItem create() /*-{return new Object();}-*/;
	
	/**
	 * Gets the ID of the organization making the reservation.
	 *
	 * @return the ID of the organization making the reservation.
	 */
	public final native String getOrganizationid() /*-{ return this.organizationid; }-*/;
	
	/**
	 * Gets the name of the guest.
	 *
	 * @return the name of the guest.
	 */
	public final native String getName() /*-{ return this.name; }-*/;
	
	/**
	 * Gets the state of the reservation.
	 *
	 * @return the state
	 */
	public final native String getState() /*-{ return this.state; }-*/;
	
	/**
	 * Gets the quoted price.
	 *
	 * @return the quoted price.
	 */
	public final native double getQuote() /*-{ return this.quote; }-*/;
	
	/**
	 * Gets the amount payable.
	 *
	 * @return the amount payable.
	 */
	public final native double getAmount() /*-{ return this.amount; }-*/;
	
	/**
	 * Gets the currency of payment.
	 *
	 * @return the currency of payment.
	 */
	public final native String getCurrency() /*-{ return this.currency; }-*/;
	
	/**
	 * Gets the guest's message.
	 *
	 * @return the guest's message.
	 */
	public final native String getMessage() /*-{ return this.message; }-*/;

	/**
	 * Checks if there is no guest name.
	 *
	 * @return true, if there is no guest name.
	 */
	public final boolean noName() {return getName() == null || getName().isEmpty();}
	
	/**
	 * Gets the string value of the JSONP object.
	 *
	 * @return the string.
	 */
	public final String string() {
		StringBuilder builder = new StringBuilder();
		builder.append("BookWidgetItem [getOrganizationid()=");
		builder.append(getOrganizationid());
		builder.append(", getName()=");
		builder.append(getName());
		builder.append(", getState()=");
		builder.append(getState());
		builder.append(", getQuote()=");
		builder.append(getQuote());
		builder.append(", getAmount()=");
		builder.append(getAmount());
		builder.append(", getCurrency()=");
		builder.append(getCurrency());
		builder.append(", getMessage()=");
		builder.append(getMessage());
		builder.append("]");
		return builder.toString();
	}
}
