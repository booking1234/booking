/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.book;

import com.google.gwt.core.client.JavaScriptObject;

/** The Class ReservationWidgetItem is the JSNI wrapper of a JSONP object to transfer a reservation between server and widget. */
public class ReservationWidgetItem extends JavaScriptObject {
	
	/**
	 * Instantiates a new reservation widget item.
	 */
	protected ReservationWidgetItem() {}
	
	/**
	 * Creates a new JSONP object.
	 *
	 * @return the reservation widget item
	 */
	public static native ReservationWidgetItem create() /*-{return new Object();}-*/; //Create an empty instance.
	
	/**
	 * Gets the ID of the organization that manages the reserved property.
	 *
	 * @return the ID of the organization that manages the reserved property.
	 */
	public final native String getOrganizationid() /*-{ return this.organizationid; }-*/;
	
	/**
	 * Gets the ID of the reserved property.
	 *
	 * @return the ID of the reserved property.
	 */
	public final native String getProductid() /*-{ return this.productid; }-*/;
	
	/**
	 * Gets the name of the reserved property.
	 *
	 * @return the name of the reserved property.
	 */
	public final native String getProductname() /*-{ return this.productname; }-*/;
	
	/**
	 * Gets the state of the reservation.
	 *
	 * @return the state of the reservation.
	 */
	public final native String getState() /*-{ return this.state; }-*/;
	
	/**
	 * Gets the email address of the guest.
	 *
	 * @return the email address of the guest.
	 */
	public final native String getEmailaddress() /*-{ return this.emailaddress; }-*/;
	
	/**
	 * Gets the name of the guest.
	 *
	 * @return the name of the guest.
	 */
	public final native String getPartyname() /*-{ return this.partyname; }-*/;
	
	/**
	 * Gets the date from which the property is reserved (arrival date).
	 *
	 * @return the date from which the property is reserved.
	 */
	public final native String getFromdate() /*-{ return this.fromdate; }-*/;
	
	/**
	 * Gets the departure date.
	 *
	 * @return the departure date.
	 */
	public final native String getTodate() /*-{ return this.todate; }-*/;
	
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
	 * Gets the first name of the guest.
	 *
	 * @return the first name of the guest.
	 */
	public final String getFirstname() {
		if (getPartyname() == null || getPartyname().isEmpty()){return "";}
		String[] args = getPartyname().split(",");
		return args.length > 1 ? args[1] : args[0];
	}

	/**
	 * Gets the family name of the guest.
	 *
	 * @return the family name of the guest.
	 */
	public final String getFamilyname() {
		if (getPartyname() == null || getPartyname().isEmpty()){return "";}
		String[] args = getPartyname().split(",");
		return args[0];
	}

	/**
	 * Gets the string version of the JSONP object.
	 *
	 * @return the string.
	 */
	public final String string() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReservationWidgetItem [getOrganizationid()=");
		builder.append(getOrganizationid());
		builder.append(", getProductid()=");
		builder.append(getProductid());
		builder.append(", getProductname()=");
		builder.append(getProductname());
		builder.append(", getState()=");
		builder.append(getState());
		builder.append(", getEmailaddress()=");
		builder.append(getEmailaddress());
		builder.append(", getPartyname()=");
		builder.append(getPartyname());
		builder.append(", getFromdate()=");
		builder.append(getFromdate());
		builder.append(", getTodate()=");
		builder.append(getTodate());
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
