/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.MessagePanel;
import net.cbtltd.client.form.AbstractForm;
import net.cbtltd.client.form.AccessControl;
import net.cbtltd.client.panel.BrochurePopup;
import net.cbtltd.client.panel.OfflinePopup;
import net.cbtltd.client.panel.PartyPopup;
import net.cbtltd.client.panel.Popups;
import net.cbtltd.client.panel.RatePopup;
import net.cbtltd.client.panel.ReservationPopup;
import net.cbtltd.client.panel.SessionPopup;
import net.cbtltd.client.resource.FieldBundle;
import net.cbtltd.shared.Country;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.ModelValue;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Session;
import net.cbtltd.shared.api.HasResetId;
import net.cbtltd.shared.api.HasState;
import net.cbtltd.shared.reservation.Brochure;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.maps.client.LoadApi;
import com.google.gwt.maps.client.LoadApi.LoadLibrary;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

/**
 * The Class AbstractRoot is the main entry point of the application.
 * Application options are set by URL parameters to control access and program flow.
 * For example, the brochure parameter is used to display the specified brochure or product.
 * The rate parameter is used to display a feedback rating form for the specified reservation.
 * The schedule parameter is used to display the availability schedule for the specified agent or manager.
 * The search parameter is used to display the look and book form for the specified agent or manager.
 * If there is no parameter of if the parameter is incorrect then the main application is displayed.
 * 
 * TODO:
 * @see http://code.google.com/p/gwt-google-apis/
 * @see browser size http://setmy.browsersize.com/1152x864
 * @see http://resizemybrowser.com/
 * javascript:moveTo(0,0);resizeTo(1024,768);
 * @see http://stackoverflow.com/questions/7452040/cross-browser-resize-browser-window-in-javascript
 * if(navigator.userAgent.toLowerCase().indexOf('chrome') > -1)
    var t = setTimeout("resize()", 200);
else
    resize();

function resize() {
    var innerWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    var innerHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
    var targetWidth = 800;
    var targetHeight = 600;
    window.resizeBy(targetWidth-innerWidth, targetHeight-innerHeight);
}

 */
public abstract class AbstractRoot
implements EntryPoint, HistoryListener {

	private static RazorServiceAsync rpcService = (RazorServiceAsync) GWT.create(RazorService.class);
	private static final HandlerManager eventBus = new HandlerManager(null);
	private static final TabLayoutPanel layoutPanel = new TabLayoutPanel(0.0, Unit.EM);
//	private static MessagePanel loading = null;
	public static Session session;
	private static String creatorid = null;
	private static int index = 0;
	private static String logo;
	
	public enum Type {
		affiliate, 
		book, 
		brochure, 
		form,
		logo,
		offline,
		party,
		popup,
		pos,
		product, 
		rate,
		type //widget name(s) or autorazor
	};

	/**
	 * Gets an instance of the service to send asynchronous requests to the Razor server.
	 *
	 * @return the GWT instance for RPC calls to the server.
	 */
	public static RazorServiceAsync getService() {return rpcService;}

	/**
	 * Gets an instance of the event bus used to communicate application events.
	 * This is an alternative to using the browser event mechanism implemented in AbstractField.
	 *
	 * @return the event bus.
	 */
	public static HandlerManager getEventBus() {return eventBus;}

	/**
	 * Get the ID of the affiliate that originated this session.
	 *
	 * @return the ID of the affiliate that originated this session.
	 */
	public static String getCreatorid() {return creatorid == null ? Party.CBT_LTD_PARTY : creatorid;}

	/**
	 * Gets the default country code of this session.
	 * This is the primary country of the employer of the current user.
	 *
	 * @return default country code.
	 */
	public static String getCountry() {
		return session == null ? Country.US : session.getCountry();
	}

	/**
	 * Sets the default country code of this session.
	 *
	 * @param country the new country code.
	 */
	public static void setCountry(String country) {
		if (session != null) {session.setCountry(country);}
	}

	/**
	 * Gets the default currency code of this session.
	 * This is the primary currency of the employer of the current user.
	 *
	 * @return default currency code.
	 */
	public static String getCurrency() {
		return session == null ? Currency.Code.USD.name() : session.getCurrency();
	}

	/**
	 * Sets the default currency code of this session.
	 *
	 * @param currency the new currency code.
	 */
	public static void setCurrency(String currency) {
		if (session != null) {session.setCurrency(currency);}
	}

	/**
	 * Checks if there is a default currency code.
	 *
	 * @return true, if there is a default currency code.
	 */
	public static boolean noCurrency() {
		return session == null || session.noCurrency();
	}

	/**
	 * Gets the default language code of this session.
	 *
	 * @return default language code.
	 */
	public static String getLanguage() {
		return noLanguage() ? Language.EN : session.getLanguage();
	}

	/**
	 * Sets the default language code of this session.
	 *
	 * @param language the new language code.
	 */
	public static void setLanguage(String language) {
		if (session != null) {session.setLanguage(language);}
	}

	/**
	 * Checks if there is a default language code.
	 *
	 * @return true, if there is a default language code.
	 */
	public static boolean noLanguage() {
		return session == null || session.noLanguage();
	}

	/**
	 * Gets the logo for white label sites.
	 *
	 * @return logo.
	 */
	public static String getLogo() {
		return logo;
	}

	/**
	 * Checks if there is no logo image for white label sites.
	 *
	 * @return true, if there is no logo image.
	 */
	public static boolean noLogo() {
		return logo == null;
	}
	
	/**
	 * Checks if there is a logo image for white label sites.
	 *
	 * @return true, if there is a logo image.
	 */
	public static boolean hasLogo() {
		return !noLogo();
	}
	
	/**
	 * Gets the date format of this session.
	 *
	 * @return date format.
	 */
	public static String getFormatDate() {
		return session == null || session.noFormatdate() ? Party.MM_DD_YYYY : session.getFormatdate();
	}

	/**
	 * Sets the date format of this session.
	 *
	 * @param formatdate the date format.
	 */
	public static void setFormatDate(String formatdate) {
		if (session != null) {session.setFormatdate(formatdate);}
	}

	public static DateTimeFormat getDF() {
		return DateTimeFormat.getFormat(getFormatDate());
	}

	/**
	 * Gets the short date format of this session.
	 *
	 * @return short date format.
	 */
	public static String getFormatDateShort() {
		return getFormatDate().replace("dd", "d").replace("MM", "M").replace("YYYY", "YY");
	}

	public static DateTimeFormat getSDF() {
		return DateTimeFormat.getFormat(AbstractRoot.getFormatDateShort());
	}
	
	/**
	 * Gets the date time format of this session.
	 *
	 * @return date time format.
	 */
	public static String getFormatDateTime() {
		return getFormatDate() + Party.HH_MM;
	}

	public static DateTimeFormat getDTF() {
		return DateTimeFormat.getFormat(AbstractRoot.getFormatDateTime());
	}

	/**
	 * Gets the date time second format of this session.
	 *
	 * @return date time format.
	 */
	public static String getFormatDateTimeSecond() {
		return getFormatDate() + Party.HH_MM_SS;
	}

	/**
	 * Gets the phone format of this session.
	 *
	 * @return phone format.
	 */
	public static String getFormatPhone() {
		return session == null || session.noFormatphone() ? Party.PHONE : session.getFormatphone();
	}

	/**
	 * Sets the phone format of this session.
	 *
	 * @param formatphone the phone format.
	 */
	public static void setFormatPhone(String formatphone) {
		if (session != null) {session.setFormatphone(formatphone);}
	}

//	public static void setLoading(MessagePanel allLoading) {
//		AbstractRoot.loading = allLoading;
//	}
//	
//	/**
//	 * Hides loading message on end of asynch call.
//	 */
//	public static void hideLoading() {
//		if (loading != null) {loading.hide();}
//	}
//	
	/**
	 * Sets the string value of the specified key.
	 *
	 * @param key the key which identifies the value.
	 * @param value the value for the specified key.
	 */
	public static void setValue(String key, String value) {
	if (key == null || key.isEmpty() || value == null || session == null || value.equalsIgnoreCase(session.getValue(key))) {return;}
		session.setValue(key, value);
		valueTimer.cancel();
		valueTimer.schedule(60000);
	}
	
	/**
	 * Sets the boolean value of the specified key.
	 *
	 * @param key the key which identifies the value.
	 * @param value the value for the specified key.
	 */
	public static void setBooleanValue(String key, Boolean value) {setValue(key, String.valueOf(value));}
	
	/**
	 * Sets the double value of the specified key.
	 *
	 * @param key the key which identifies the value.
	 * @param value the value for the specified key.
	 */
	public static void setDoubleValue(String key, Double value) {setValue(key, String.valueOf(value));}
	
	/**
	 * Sets the integer value of the specified key.
	 *
	 * @param key the key which identifies the value.
	 * @param value the value for the specified key.
	 */
	public static void setIntegerValue(String key, Integer value) {setValue(key, String.valueOf(value));}
	
	/**
	 * Sets the time value of the specified key.
	 *
	 * @param key the key which identifies the value.
	 * @param value the value for the specified key.
	 */
	public static void setTimeValues(String key, String value) {setValue(key, value);}

	/**
	 * Checks if the specified key has a value.
	 *
	 * @param key the key to be checked.
	 * @return true, if the specified key has a value.
	 */
	public static boolean noValue(String key) {return getValue(key) == null || getValue(key).isEmpty();}
	
	/**
	 * Gets the string value for the specified key.
	 *
	 * @param key the specified key.
	 * @return the value for the specified key.
	 */
	public static String getValue(String key) {return session == null || key == null ? "" : session.getValue(key);}
	
	/**
	 * Gets the boolean value for the specified key.
	 *
	 * @param key the specified key.
	 * @return the value for the specified key.
	 */
	public static Boolean getBooleanValue(String key){return noValue(key) ? false : Boolean.valueOf(getValue(key));}
	
	/**
	 * Gets the double value for the specified key.
	 *
	 * @param key the specified key.
	 * @return the value for the specified key.
	 */
	public static Double getDoubleValue(String key){
		try {return key == null || noValue(key) ? 0.0 : Double.valueOf(getValue(key));}
		catch (Exception x) {return 0.0;}
	}
	
	/**
	 * Gets the integer value for the specified key.
	 *
	 * @param key the specified key.
	 * @return the value for the specified key.
	 */
	public static Integer getIntegerValue(String key){
		try {return noValue(key) ? 0 : Integer.valueOf(getValue(key));}
		catch (Exception x) {return 0;}
	}
	
	/**
	 * Gets the time value for the specified key.
	 *
	 * @param key the specified key.
	 * @return the value for the specified key.
	 */
	public static Date getTimeValue(String key){
		try {return AbstractField.TF.parseStrict(getValue(key));}
		catch (IllegalArgumentException x) {return null;}
	}

	/* The timer that schedules periodic updates of the session values to the server. */
	private static Timer valueTimer = new Timer() {
		private int changed = 0;

		public void run() {
			if (session == null 
					|| session.getValues() == null 
					|| session.getValues().toString() == null 
					|| session.getValues().toString().hashCode() == changed) {return;}
			else {
				changed = session.getValues().toString().hashCode();
				getService().send(new ModelValue(session.getActorid(), session.getValues()), new AsyncCallback<ModelValue>() {
					public void onFailure(Throwable caught) {new MessagePanel(Level.ERROR, caught.getMessage()).show();} 
					public void onSuccess(ModelValue response) {
						if (session != null && response != null && response.hasValues()) {
							session.setValues(response.getValues());
						}
					}
				});
			}
		}
	};

	/**
	 * Gets the current session which was established by a user logging in or via an automatic login
	 *
	 * @return the current session.
	 */
	public static Session getSession() {return session;}

	/**
	 * Checks if there is a current session.
	 *
	 * @return true if there is a current session.
	 */
	public static boolean noSession() {
		return session == null || session.getId() == null; // || session.getId() == Model.ZERO;
	}

	/**

	 * Creates a new session if it does not exist and set its permissions to the specified permission.
	 * @param permission the new permission.
	 */
	public static void setPermission(short permission) {
		if (session == null) {session = new Session();}
		short[] permissions = new short[1];
		permissions[0] = permission;
		session.setPermissions(permissions);
	}

	/**
	 * Checks if the current session has the specified permission.
	 *
	 * @param permission the specified permission.
	 * @return true is the session has the specified permission.
	 */
	public static boolean hasPermission(short permission) {
		return session == null ? false : session.hasPermission(permission);
	}

	/**
	 * Checks if the current session does not have the specified permission.
	 *
	 * @param permission the specified permission.
	 * @return true is the session does not have the specified permission.
	 */
	public static boolean noPermission(short permission) {
		return !hasPermission(permission);
	}

	/**
	 * Checks if the current session has the specified role.
	 *
	 * @param role the specified role.
	 * @return true is the session has the specified role.
	 */
	public static boolean hasPermission(short[] role) {
		return session == null ? false : session.hasPermission(role);
	}

	/**
	 * Checks if the current session has permission to change an object which needs the specified permissions.
	 *
	 * @param needsPermission short[] array of required permissions, or null if there are no needs.
	 * @return true if the array has at least one in the current session permissions, else false.
	 */
	public static boolean writeable(short[] needsPermission) {
		if (session == null || needsPermission == null) {return true;}
		short[] hasPermission = session.getPermission();
		if (hasPermission == null || hasPermission.length == 0){
			new MessagePanel(Level.ERROR, AbstractField.CONSTANTS.errPermission()).show();
			return false;
		}
		Arrays.sort(needsPermission);
		int h = 0;
		int n = 0;
		while ((h < hasPermission.length) && (n < needsPermission.length)){
			if (hasPermission[h] == needsPermission[n]){return true;}
			if (hasPermission[h] < needsPermission[n]) h++;
			else n++;
		}
		return false;
	}

	/**
	 * Checks if the current session has permission to see an object which needs the specified permissions.
	 *
	 * @param needsPermission short[] array of required permissions, or null if there are no needs.
	 * @return true if the array has at least one in the current session permissions, else false.
	 */
	public static boolean readable(short[] needsPermission) {
		if (session == null || needsPermission == null) {return true;}
		for (int i=0; i<needsPermission.length; i++) {
			short need = needsPermission[i];
			if (need < 0) {needsPermission[i] = (short)(need + 32768);} //(short)-need;}
		}
		return writeable(needsPermission);
	}

	/*
	 * Gets the name of the specified form class.
	 *
	 * @param form the specified form class.
	 * @return the the name of the specified form.
	 */
	private static String getFormname(Class<?> form) {
		String formname = form.getName();
		int startIndex = formname.lastIndexOf(".") + 1;
		int endIndex = formname.length();
		return formname.substring(startIndex, endIndex);
	}

	/**
	 * Sets the current organization ID and refresh the forms and fields.
	 *
	 * @param organizationid the new organization ID.
	 */
	public static void setOrganizationid(String organizationid) {
		if (organizationid == null) {return;}
		session.setOrganizationid(organizationid);
		for (int index = 1; index < layoutPanel.getWidgetCount(); index++) { //not session form
			AbstractForm<HasState> form = (AbstractForm<HasState>)layoutPanel.getWidget(index);
			form.onRefresh();
		}
	}

	/**
	 * Sets the ID of the current user (actor).
	 *
	 * @param actorid new the user ID.
	 */
	public static void setActorid(String actorid) {
		if (session == null || actorid == null) {return;}
		else {session.setActorid(actorid);}
	}

	/**
	 * Gets the current organization ID.
	 *
	 * @return the current organization ID.
	 */
	public static String getOrganizationid() {return noSession() ? null : session.getOrganizationid();}
	
	/**
	 * Checks if there is a current organization ID.
	 *
	 * @return true, if there is a current organization ID.
	 */
	public static boolean noOrganizationid() {return noSession() || session.noOrganizationid();}
	
	/**
	 * Checks if the specified and current organization IDs are the same.
	 *
	 * @param organizationid the specified organization ID.
	 * @return true, if the specified and current organization IDs are the same.
	 */
	public static boolean hasOrganizationid(String organizationid) {return session != null && session.hasOrganizationid(organizationid);}
	
	/**
	 * Gets the current organization name.
	 *
	 * @return the current organization name.
	 */
	public static String getOrganizationname() {return session == null ? null : session.getOrganizationname();}
	
	/**
	 * Gets the current user ID.
	 *
	 * @return the current user ID.
	 */
	public static String getActorid() {return (noSession() || hasPermission(AccessControl.AGENT)) ? Party.NO_ACTOR : session.getActorid();	}
	
	/**
	 * Checks if there is a current user.
	 *
	 * @return true, if there is a current user.
	 */
	public static boolean noActorid() {return session == null || session.noActorid();}
	
	/**
	 * Gets the current actor name.
	 *
	 * @return the current actor name.
	 */
	public static String getActorname() {return noSession() ? Model.BLANK : session.getActorname();}
	
	/**
	 * Gets the current actor email address.
	 *
	 * @return the current actor email address.
	 */
	public static String getActoremailaddress() {return noSession() ? Model.BLANK : session.getEmailaddress();}
	
	/**
	 * Gets the rank of the current user. Rank is set by Razor according to the behavior of the user. 
	 *
	 * @return the rank of the current user.
	 */
	public static Integer getRank() {return noSession() || session.getRank() == null ? 0 : session.getRank();}

	/**
	 * Gets the specified URL parameter.
	 *
	 * @param name the name of the parameter.
	 * @return the value of the parameter having the specified name
	 */
	public static String getParameter(String name) {return Window.Location.getParameter(name);}

	/**
	 * Checks if there is no parameter having the specified name.
	 *
	 * @param name the name of the parameter.
	 * @return true if there is no parameter with the specified name
	 */
	public static boolean noParameter(String name) {return getParameter(name) == null || getParameter(name).equalsIgnoreCase(Model.BLANK);}

	/**
	 * Checks if there is a parameter having the specified name.
	 *
	 * @param name the name of the parameter.
	 * @return true if there is a parameter with the specified name
	 */
	protected static boolean hasParameter(String name) {return !noParameter(name);}

	/**
	 * Gets the key value map of URL parameters.
	 *
	 * @return the  key value map of URL parameters.
	 */
	public static Map<String, List<String>> getParameterMap() {return Window.Location.getParameterMap();}

	/**
	 * Gets the entire URL query string.
	 *
	 * @return the entire URL query string.
	 */
	public static String getQueryString() {return Window.Location.getQueryString();}

	/**
	 * Sets the current session when a user logs in or via an automated login.
	 * The session object determines which forms, fields, buttons and entities are accessible in the current session.
	 *
	 * @param session the current session.
	 */
	public static void setSession(Session session) {
		AbstractRoot.session = session;
		setOrganizationid(session.getOrganizationid());
		if (session.getPermission() == null) {return;}
		Arrays.sort(session.getPermission());
		setTabs();
	}

	/**
	 * Creates and initializes a new session.
	 */
	public static void newSession(){
		session = new Session();
		session.setState(Session.LOGGED_OUT);
		short[] permission = {0};
		session.setPermissions(permission);
	}

	/**
	 * Initializes and renders a single form having the specified index.
	 *
	 * @param index the specified index.
	 */
	public static void renderForm(int index) {
		AbstractForm<HasState> form = Razor.instantiate(index);
		layoutPanel.add(form, AbstractField.CONSTANTS.sessionTabs()[index]);
		form.setup(index);
		form.onFocus();
	}

	/**
	 * Initializes all forms and renders the one having the specified index.
	 *
	 * @param index the specified index.
	 */
	public static void renderTabs(int index, HasResetId reset) {
		setTabs();
		layoutPanel.add(Razor.instantiate(index), AbstractField.CONSTANTS.sessionTabs()[index]);
		render(index, reset);
	}

	/**
	 * Renders the form having the specified index.
	 *
	 * @param index the specified index.
	 */
	public static void render(int index) {render(index, null);}

	/**
	 * Renders the form having the specified index and set is values using the specified name and ID.
	 *
	 * @param index the specified index.
	 * @param reset the specified name and ID
	 */
	public static AbstractForm<HasState> render(int index, HasResetId reset) {
		AbstractForm<HasState> form = (AbstractForm<HasState>)layoutPanel.getWidget(index);
		form.setup(index);
		for (int i = 1; i < AbstractField.CONSTANTS.sessionTabs().length; i++) {
			if (i == index) {form.onFocus();}
			else if (i == AbstractRoot.index) {form.onBlur();}
		}
		if (reset != null) {form.onReset(reset);}
		History.newItem(AbstractField.CONSTANTS.sessionTabs()[index]);
		AbstractRoot.index = index;
		layoutPanel.selectTab(index);
		return form;
	}

	/**
	 * Checks if this session has access to the form having the specified index.
	 *
	 * @param index the specified index.
	 * @return true, if this session has access to the form having the specified index.
	 */
	public static boolean permitted(int index) {
		AbstractForm<HasState> form = (AbstractForm<HasState>)layoutPanel.getWidget(index);
		return writeable(form.permission());
	}
	
	/**
	 * Handles history change events created by the browser back/forward buttons.
	 * 
	 * @param historyToken the token taken from the history stack (the form name).
	 */
	public void onHistoryChanged(String historyToken) {
		if (AbstractField.CONSTANTS.sessionTabs()[index].equalsIgnoreCase(historyToken)) {return;}
		for (int index = 0; index < AbstractField.CONSTANTS.sessionTabs().length; index++) {
			if (AbstractField.CONSTANTS.sessionTabs()[index].equalsIgnoreCase(historyToken)){
				render(index);
				return;
			}
		}
	}

	/**
	 * The entry point method which starts the application.
	 */
	@Override
	public void onModuleLoad() {
		/*
		 * Installs an UncaughtExceptionHandler which will produce <code>FATAL</code> log messages.
		 */
		Log.setUncaughtExceptionHandler();
///		applications();
//		Maps.loadMapsApi(HasUrls.GOOGLE_CLIENT_KEY, "2", false, new Runnable() {
//			public void run() {applications();}
//		});
//	}
//
//	private void loadMapApi() {
	    boolean sensor = false;

	    // load all the libs for use in the maps
	    ArrayList<LoadLibrary> loadLibraries = new ArrayList<LoadApi.LoadLibrary>();
	    loadLibraries.add(LoadLibrary.ADSENSE);
	    loadLibraries.add(LoadLibrary.DRAWING);
	    loadLibraries.add(LoadLibrary.GEOMETRY);
	    loadLibraries.add(LoadLibrary.PANORAMIO);
	    loadLibraries.add(LoadLibrary.PLACES);
	    loadLibraries.add(LoadLibrary.WEATHER);
	    loadLibraries.add(LoadLibrary.VISUALIZATION);

	    Runnable onLoad = new Runnable() {
	      @Override
	      public void run() {
	    	  applications();
	      }
	    };

	    LoadApi.go(onLoad, loadLibraries, sensor);
	  }
	
	/**
	 * Inserts the rendered panel(s) into the HTML page containing the application.
	 */
	public void applications() {
		try {
			FieldBundle.INSTANCE.css().ensureInjected();

			//-----------------------------------------------
			// Instantiate forms and attach initial to tab panel
			//-----------------------------------------------
			newSession();

			RootPanel.get("Loading").getElement().removeFromParent();
			DockLayoutPanel appPanel = new DockLayoutPanel(Unit.EM);
			RootLayoutPanel.get().add(appPanel);
			appPanel.add(layoutPanel);

			//LabelField.translate(Language.EN, Language.DE, "Magistrate");
			// advanced widgets
			String affiliate = getParameter(Type.affiliate.name());
			String bookid = getParameter(Type.book.name());
			String brochureid = getParameter(Type.brochure.name());
			logo = getParameter(Type.logo.name());
			String offlineid = getParameter(Type.offline.name());
			String partypos = getParameter(Type.party.name());
			String productid = getParameter(Type.product.name());
			String pos = getParameter(Type.pos.name());
			String rateid = getParameter(Type.rate.name());
			String form = getParameter(Type.form.name());
			String popup = getParameter(Type.popup.name());

//			Window.alert("Popups " + popup);
									
			try {creatorid = affiliate == null || affiliate.isEmpty() ? null : Model.decrypt(affiliate);}
			catch (Exception x) {creatorid = Party.CBT_LTD_PARTY;}

			String orgId = null;
			try {
				if (pos != null) {
					orgId = Model.decrypt(pos);
					setOrganizationid(orgId);
				} else {setOrganizationid(Party.CBT_LTD_PARTY);}				
			}
			catch (Exception x) {setOrganizationid(Party.CBT_LTD_PARTY);}
			AbstractRoot.setActorid(Party.NO_ACTOR);

			if (bookid != null) {ReservationPopup.getInstance().show(bookid);} //product ID
			else if (brochureid != null) {BrochurePopup.getInstance().show(brochureid);}
			else if (offlineid != null) {OfflinePopup.getInstance().show(offlineid);}
			else if (partypos != null) {PartyPopup.getInstance().show(Model.decrypt(partypos));}
			else if (productid != null) {BrochurePopup.getInstance().show(productid, orgId, Brochure.CREATED);}
			else if (rateid != null) {
				FlowPanel panel = new FlowPanel();
				layoutPanel.add(panel);
				Label message = new Label();
				message.setStyleName(AbstractField.CSS.cbtAbstractCentered());
				panel.add(message);
				RatePopup.getInstance().show(rateid, message);
			}
			else if (form != null) {renderForm(Integer.valueOf(form));}
			else if (logo != null) {new SessionPopup().show(true);}
			else if (popup != null) {Popups.render(Integer.valueOf(popup));}
			else {
				History.newItem(AbstractField.CONSTANTS.sessionTabs()[0]);
				History.addHistoryListener(this);
				renderForm(Razor.SESSION_TAB);
			}
		}
		catch (Exception x) {new MessagePanel(Level.ERROR, AbstractField.CONSTANTS.sessionInitialize() + "\n" + x).show();}
	}

	/* Instantiates the application forms and adds them to the controlling tab panel and initializes the history stack. */ 
	private static void setTabs() {
		layoutPanel.clear();
		for (int index = 0; index < AbstractField.CONSTANTS.sessionTabs().length; index++) {
			layoutPanel.add(Razor.instantiate(index), AbstractField.CONSTANTS.sessionTabs()[index]);
		}
	}	
}