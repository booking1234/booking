/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget;

import java.util.Date;

import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.widget.account.AccountWidget;
import net.cbtltd.client.widget.book.BookWidget;
import net.cbtltd.client.widget.calendar.CalendarWidget;
import net.cbtltd.client.widget.image.ImageWidget;
import net.cbtltd.client.widget.map.MapWidget;
import net.cbtltd.client.widget.price.PriceWidget;
import net.cbtltd.client.widget.quote.QuoteWidget;
import net.cbtltd.client.widget.review.ReviewWidget;
import net.cbtltd.client.widget.route.RouteWidget;
import net.cbtltd.client.widget.text.TextWidget;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.License;
import net.cbtltd.client.resource.Hosts;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.mybookingpal.config.RazorConfig;

/**
 * The Class RazorWidget is the entry point for simple widgets that can use both RPC and JSONP messages.
 * The RPC mode uses the standard GWT asynchronous messages, which requires that the widget be in an iFrame in a host HTML page.
 * The JSONP mode uses JSONP messages, which requires that the widget JavaScript be served from the host server.
 * 
 * @see <pre>http://eggsylife.co.uk/2010/04/22/gwt-2-jsonp-and-javascript-overlays-with-jsonprequestbuilder/</pre>
 * @see <pre>http://www.devcomments.com/JsonpRequestBuilder-and-HTTPS-Problem-at1014012.htm</pre>
 */
public class RazorWidget
implements EntryPoint {

	/** The Enum State is to show if a request is successful or not. */
	public enum State {SUCCESS, FAILURE};

	/** The initial delay in milliseconds to allow the widget to be set up before sending a refresh request. */
	public static final int delay = 5000;

	/** The formatter to be used for dates in MySQL date format. */
	public static final DateTimeFormat DF = DateTimeFormat.getFormat("yyyy-MM-dd");
	
	private static final RazorConstants CONSTANTS = GWT.create(RazorConstants.class);
	private static final Hosts HOSTS = GWT.create(Hosts.class);
//	private static final Dictionary PROPERTIES = Dictionary.getDictionary("widgets");

	/**
	 * Gets the value of a property having the specified name.
	 *
	 * @param name the specified name.
	 * @return the value of the property.
	 */
	public static final String getParameter(String name) {
		try {
			String value = Window.Location.getParameter(name);
			if (value == null || value.isEmpty()) {
				Dictionary PROPERTIES = Dictionary.getDictionary("widgets");
				value = PROPERTIES.get(name);
			}
			return value;
		}
		catch (Throwable x){return null;}
	}
	
	/**
	 * Checks if a property having the specified name exists and has the specified value.
	 *
	 * @param name the name of the property.
	 * @param value the specified value.
	 * @return true, if a property having the specified name exists and has the specified value.
	 */
	public static final boolean hasValue(String name, String value) {
		String target = getParameter(name);
		return target != null && target.equalsIgnoreCase(value);
	}
	
	/*
	 * Checks if the widget is to use RPC.
	 *
	 * @return true, if the widget is to use RPC.
	 */
	public static final boolean isRpc() {
		return hasValue("rpc", "true");
	}
	
	/**
	 * Gets the specified currency if any or default USD. 
	 * 
	 * @return the specified currency or USD if none. 
	 */
	public static final String getCurrency() {
		String currency = RazorWidget.getParameter("currency");
		return (currency == null || currency.isEmpty() || currency.length() != 3) ? Currency.Code.USD.name() : currency;
	}

	/**
	 * Gets the specified date if any. 
	 * 
	 * @return the specified date or the current date if none. 
	 */
	public static final Date getDate() {
		Date date = new Date();
		try {date = RazorWidget.DF.parse(RazorWidget.getParameter("date"));}
		catch (Throwable x) {}
		return date == null ? new Date() : date;
	}
	
	/**
	 * Gets the specified language if any or default en. 
	 * 
	 * @return the specified language or en if none. 
	 */
	public static final String getLanguage() {
		String language = RazorWidget.getParameter("language");
		return (language == null || language.isEmpty() || language.split(",").length <= 0) ? Language.EN : language.split(",")[0].toUpperCase();
	}

	/**
	 * Gets the allowed languages. 
	 * 
	 * @return the allowed languages. 
	 */
	public static final String getLanguages() {
		String languagestring = RazorWidget.getParameter("language");
		if (languagestring == null || languagestring.isEmpty()) {return Language.getTranslatableCdlist();}
		else {return languagestring;}
	}

	/** 
	 * Gets the number of rows to show in the table. 
	 * 
	 * @return the number of rows in the table. 
	 */
	public static final int getRows() {
		try {return Integer.valueOf(getParameter("rows"));}
		catch (Throwable x) {return 5;}
	}
	
	/**
	 * Checks if the host is secure or is using JSONP.
	 *
	 * @return true, if the host is secure or is using JSONP.
	 */
	public static final boolean isSecure() {
		return Window.Location.getHref().startsWith(HOSTS.jsonUrl().substring(0, 5)) || !isRpc();
	}

	/**
	 * Gets the Powered By clickable logo.
	 * 
	 * @return the Powered By clickable logo.
	 */
	public static FlowPanel getHome() {
		final FlowPanel panel = new FlowPanel();
		panel.addStyleName("Logo");
		final Image image = new Image("razor.png");
		image.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.open(HOSTS.homeUrl(), "_blank",	"menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=yes");
			}
		});
		image.setTitle(CONSTANTS.helpHome());
//TODO:		panel.add(image);
		return panel;
	}

	/**
	 * Displays a pop up message if a condition is satisfied.
	 *
	 * @param condition the condition is true if the message is to be displayed.
	 * @param level the level of the message which dictates its importance and sets its colour.
	 * @param text the text to be displayed in the message.
	 * @param target the field or other widget next to which the message is to be displayed.
	 * @return true, if the condition is satisfied.
	 */
	public static boolean ifMessage(boolean condition, Level level, String text, UIObject target) {
		if (condition) {AbstractField.addMessage(level, text, target);}
		return condition;
	}

	/**
	 * The entry point method which starts the widgets.
	 */
	@Override
	public void onModuleLoad() {
		/*
		 * Installs an UncaughtExceptionHandler which will produce <code>FATAL</code> log messages.
		 */
		Log.setUncaughtExceptionHandler();
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
		@Override
		public void execute() {onModuleLoad2();}
	});
//		Maps.loadMapsApi(HasUrls.MAPS_KEY_OLD, "2", false, new Runnable() {
//			public void run() {execute();}
//		});
	}

	/**
	 * The entry point method which inserts the rendered panel(s) into the container HTML page.
	 */
	public static void onModuleLoad2() {
		try {
			AbstractField.CSS.ensureInjected();
			String pos = getParameter("pos"); //mandatory
			String type = getParameter("type"); //may be comma delimited
			String id = getParameter("id"); //may be comma delimited
			String value = getParameter("value"); //single or comma delimited?
			
			Log.debug("pos " + pos + " type " + type + " id " + id + " value " + value);
			
			if (pos == null || pos.isEmpty()) {throw new RuntimeException(CONSTANTS.posError());}
			
			if (type == null || type.isEmpty()) {throw new RuntimeException(CONSTANTS.typeError());}
			String[] types = type.split(",");
			if (types == null || types.length < 1) {throw new RuntimeException(CONSTANTS.typeError());}

			if (id == null || id.isEmpty()) {throw new RuntimeException(CONSTANTS.idError());}
			String[] ids = id.split(",");
			if (ids == null || ids.length < 1) {throw new RuntimeException(CONSTANTS.idError());}

			if (types.length != ids.length) {throw new RuntimeException(CONSTANTS.sizeError());}

			for (int index = 0; index < ids.length; index++) {
				JSONRequest widget = JSONRequest.valueOf(types[index].trim().toUpperCase());
				if (RootPanel.get(ids[index]) == null) {throw new RuntimeException(CONSTANTS.htmlError() + ids[index]);}
				switch(widget) {
				case BOOK: RootPanel.get(ids[index]).add(new BookWidget(pos, value)); break;
				case CALENDAR: RootPanel.get(ids[index]).add(new CalendarWidget(isRpc(), pos, value)); break;
				case IMAGE: RootPanel.get(ids[index]).add(new ImageWidget(isRpc(), pos, value)); break;
				case JOURNAL: RootPanel.get(ids[index]).add(new AccountWidget(isRpc(), pos, value)); break;
				case MAP: RootPanel.get(ids[index]).add(new MapWidget(isRpc(), pos, value)); break;
				case PRICE: RootPanel.get(ids[index]).add(new PriceWidget(isRpc(), pos, value)); break;
				case QUOTE: RootPanel.get(ids[index]).add(new QuoteWidget(isRpc(), pos, value)); break;
				case REVIEW: RootPanel.get(ids[index]).add(new ReviewWidget(isRpc(), pos, value)); break;
				case ROUTE: RootPanel.get(ids[index]).add(new RouteWidget(isRpc(), pos, value)); break;
				case TEXT: RootPanel.get(ids[index]).add(new TextWidget(isRpc(), pos, value)); break;
				default: throw new RuntimeException(CONSTANTS.invalidtypeError() + types[index]);
				}
			}
		}
		catch (Throwable x) {
			x.printStackTrace();
			Log.error(x.getMessage());
			AbstractField.addMessage(Level.ERROR, x.getMessage(), null);
		}
	}
}
