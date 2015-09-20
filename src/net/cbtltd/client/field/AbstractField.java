/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.ArrayList;
import java.util.HashMap;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.Component;
import net.cbtltd.client.HasCommand;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.HasValue;
import net.cbtltd.client.resource.FieldBundle;
import net.cbtltd.client.resource.FieldConstants;
import net.cbtltd.client.resource.FieldCssResource;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.client.constants.NumberConstants;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class AbstractField is the parent of all concrete field classes to display and change a value of type T.
 * Global resources such as string and number constants, formatters, styles and images are defined here.
 * The visibility and accessibility of the field is controlled by a number of attributes:
 *  the writeable flag indicates if the current user has permission to change the field value.
 *  the readable flag indicates if the current user has permission to see the field.
 *  the option visible flag indicates if the field is visible with its current option(s).
 *  the option enabled flag indicates if the field is enabled with the current option(s).
 *  the visible flag indicates if the field is visible.
 *  the enabled flag indicates if the field is enabled.
 *  the secure flag indicates if the field is encrypted.
 *  
 * The lock image is displayed next to the field if it is secure.
 * The changed value is zero or equal to the most recent hash of the value of the field,
 *  which is used to determine if the field value has been changed since it was last set.
 * The help text is the message displayed when the field label is clicked.
 * 
 * The default value is the value to which the field is set by the onReset() function.
 *
 * The class must implement the Component interface for it to be contained by a HasComponents element.
 * 
 * The class also manages click, change and other browser events using the browser's native event management system.
 * 
 * @param <T> the generic type of the value managed by the field.
 * 
 * @see for Appearance wrappers 
 * http://code.google.com/p/google-web-toolkit/wiki/CellBackedWIdgets#Appearance_Pattern
 * http://www.sencha.com/blog/ext-gwt-3-appearance-design
 * TODO:
 */
public abstract class AbstractField<T>
extends Composite
implements Component, HasChangeHandlers, HasClickHandlers, HasValue<T> {

	/** 
	 * The Constant CONSTANTS are the global field constants defined in the FieldConstants.properties file for the target language.
	 * The target language is that specified in the client browser.
	 */
	public static final FieldConstants CONSTANTS = GWT.create(FieldConstants.class);

	/** The Constant NUMBER_CONSTANTS lists standard numeric constants. */
	public static final NumberConstants NUMBER_CONSTANTS = LocaleInfo.getCurrentLocale().getNumberConstants();

	/** The Constant BUNDLE is the global field resource bundle of images, styles and other resources. */
	public static final FieldBundle BUNDLE = FieldBundle.INSTANCE;

	/** The Constant CSS is the global set of cascading style sheets for fields and other common elements. */
	public static final FieldCssResource CSS = BUNDLE.css();

	/** The Constant AF is the default formatter for monetary amounts. */
	public static final NumberFormat AF = NumberFormat.getFormat(CONSTANTS.allFormatAmount());

	/** The Constant DF is the default formatter for date values. */
//	public static final DateTimeFormat DF = DateTimeFormat.getFormat(CONSTANTS.allFormatDate());
//	public static final DateTimeFormat DF = DateTimeFormat.getFormat(AbstractRoot.getFormatDate());

	/** The Constant DTF is the default formatter for date-time values. */
//	public static final DateTimeFormat DTF = DateTimeFormat.getFormat(AbstractRoot.getFormatDateTime());

	/** The Constant SDF is the default formatter for short date-time values. */
//	public static final DateTimeFormat SDF = DateTimeFormat.getFormat(AbstractRoot.getFormatDateShort());

	/** The Constant TF is the default formatter for time values. */
	public static final DateTimeFormat TF = DateTimeFormat.getFormat(CONSTANTS.allFormatTime());

	/** The Constant IF is the default formatter for integer values. */
	public static final NumberFormat IF = NumberFormat.getFormat(CONSTANTS.allFormatInteger());

	/** The Constant SIF is the default formatter for short integer values. */
	public static final NumberFormat SIF = NumberFormat.getFormat(CONSTANTS.allFormatIntegerShort());

	/** The Constant LF is the default formatter for latitude and longitude values. */
	public static final NumberFormat LF = NumberFormat.getFormat(CONSTANTS.allFormatLatLng());

	/** The Constant XF is the default formatter for currency exchange rates. */
	public static final NumberFormat XF = NumberFormat.getFormat(CONSTANTS.allFormatXchange());

	/** The Constant QF is the default formatter for physical quantities. */
	public static final NumberFormat QF = NumberFormat.getFormat(CONSTANTS.allFormatQuantity());

	/** The Enum Level to set the severity level of messages. */
	public static enum Level {ERROR, TERSE, VERBOSE, DEBUG}

	/** The STARS array of guest rating images from zero to five stars in half star steps. */
	public static ImageResource[] STARS = {BUNDLE.star0(), BUNDLE.star0_1(), BUNDLE.star1(), BUNDLE.star1_2(), 
		BUNDLE.star2(), BUNDLE.star2_3(), BUNDLE.star3(), BUNDLE.star3_4(), BUNDLE.star4(), BUNDLE.star4_5(), BUNDLE.star5()};

	/** The PRODUCTS array of product state images. */
	public static ImageResource[] PRODUCTS = {BUNDLE.info(), BUNDLE.alert()};

		/** The writeable flag indicates if the field value may be edited. */
	protected boolean writeable = true;

	/** The readable flag indicates if the field value is readable. */
	protected boolean readable = true;

	/** The option visible flag indicates if the field is visible for the current option value(s). */
	protected boolean optionVisible = true;

	/** The option enabled flag indicates if the field is enabled for the current option value(s). */
	protected boolean optionEnabled = true;

	/** The visible flag indicates if the field is visible. */
	protected boolean visible = true;

	/** The enabled flag indicates if the field is enabled. */
	protected boolean enabled = true;

	/** The secure flag indicates if the field is encrypted. */
	protected boolean secure = false;

	/** The lock image is displayed next to the field if it is secure. */
	protected Image lock;

	/** The changed value is zero or equal to the most recent hash of the value of the field. */
	protected int changed = 0;

	/** The help text is the help text displayed when the field label is clicked. */
	protected String helpText;

	/** The default value is the value to which the field is set by the onReset() function. */
	protected T defaultValue;

	/**
	 * Sets the focus mode of the field.
	 *
	 * @param mode the new focus
	 */
	public abstract void setFocus(boolean mode);

	/**
	 * Sets the tab index of the field.
	 *
	 * @param index the new tab index
	 */
	public abstract void setTabIndex (int index);

	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasValue#getValue()
	 */
	public abstract T getValue();

	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasValue#setValue(java.lang.Object)
	 */
	public abstract void setValue(T value);

	/**
	 * Checks if the field is the sender of an event.
	 *
	 * @param sender the sender
	 * @return true, if successful
	 */
	public abstract boolean is(Widget sender);

	/**
	 * Initialize the field so it can be used - delay until the field is actually needed (lazy loading).
	 *
	 * @param panel is the widget that contains the field elements
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param style is the primary CCS style of the widget 
	 */
	public void initialize (Widget panel, HasComponents form, short[] permission, String style) {
		initWidget(panel);
		writeable = AbstractRoot.writeable(permission);
		readable = writeable || AbstractRoot.readable(permission);
		lock = new Image(FieldBundle.INSTANCE.lock());
		lock.setTitle(CONSTANTS.helpSecureField());
		lock.addStyleName(CSS.cbtAbstractFieldLock());
		lock.setVisible(false);
		CSS.ensureInjected();
		addStyleName(CSS.cbtAbstractField());
		if (style != null) {addStyleName(style);}
		if (form != null) {
			form.addComponent(this);
			addChangeHandler(form);
			addClickHandler(form);
		}
		sinkEvents(Event.MOUSEEVENTS | Event.ONDBLCLICK | NativeEvent.BUTTON_RIGHT);
	}

	/**
	 * Set / get current changed status
	 */
	public void setChanged() {changed = noValue() ? 0 : getValue().toString().hashCode();}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.Component#hasChanged()
	 */
	public boolean hasChanged() {return noValue() ? changed != 0 : changed != getValue().toString().hashCode();}

	/**
	 * Adds the specified change handler.
	 *
	 * @param handler to be added
	 * @return the handler registration
	 */
	@Override
	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return addDomHandler(handler, ChangeEvent.getType());
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasValue#fireChange()
	 */
	public void fireChange() {fireChange(this);}

	/**
	 * Fires change event using the browser event mechanism.
	 *
	 * @param sender of event
	 */
	protected void fireChange(Widget sender) {
		NativeEvent nativeEvent = Document.get().createChangeEvent();
		DomEvent.fireNativeEvent(nativeEvent, sender);
	}

	/**
	 * Adds the specified click handler.
	 *
	 * @param handler to be added
	 * @return the handler registration
	 */
	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addDomHandler(handler, ClickEvent.getType());
	}

	/**
	 * Fires click event using the browser event mechanism.
	 *
	 * @param sender of event
	 */
	protected void fireClick (Widget sender) {
		NativeEvent nativeEvent = Document.get().createChangeEvent();
		DomEvent.fireNativeEvent(nativeEvent, sender);
	}

	/**
	 * Handles browser events - typically responds to mouse events - needed for double clicks etc
	 *
	 * @param event the event
	 */
	@Override
	public void onBrowserEvent(Event event) {
		try {
			super.onBrowserEvent(event);

			switch (DOM.eventGetType(event)) {
			case Event.ONMOUSEDOWN: {
				onMousedown(event);
				return;
			}
			case Event.ONMOUSEUP: {
				onMouseup(event);
				return;
			}
			case Event.ONDBLCLICK: {
				onDoubleclick(event);
				return;
			}
			//case Event.BUTTON_RIGHT:break;
			//case Event.ONMOUSEMOVE:
			//case Event.ONMOUSEOVER:{
			//case Event.ONFOCUS: {
			//case Event.ONMOUSEOUT: {
			}
		} catch (Exception x) {Log.debug("onBrowserEvent " + x.getMessage());}
	}

	/**
	 * Handles double click events.
	 *
	 * @param event to be handled.
	 */
	protected void onDoubleclick(Event event) {} //override

	/**
	 * Handles mouse down events.
	 *
	 * @param event to be handled.
	 */
	protected void onMousedown(Event event) {} //override

	/**
	 * Handles mouse up events.
	 *
	 * @param event to be handled.
	 */
	protected void onMouseup(Event event) {} //override

	private HashMap<String, Boolean> writeOptions;
	private HashMap<String, Boolean> readOptions;

	/* (non-Javadoc)
	 * @see net.cbtltd.client.Component#onOptionChange(java.lang.String)
	 */
	@Override
	public void onOptionChange(String option) {
		if (readOptions != null) {
			if (readOptions.containsKey(option)){setOptionVisible(readOptions.get(option));}
			else {setOptionVisible(false);}
		}
		if (writeOptions != null) {
			if (writeOptions.containsKey(option)) {setOptionEnabled(writeOptions.get(option));}
			else {setOptionEnabled(false);}
		}
	}

	/**
	 * Sets the read option.
	 *
	 * @param option is the name or key to identify the option
	 * @param visible sets if the field is readable with this option
	 */
	public void setReadOption(String option, boolean visible) {
		if (readOptions == null) readOptions = new HashMap<String, Boolean>();
		readOptions.put(option, visible);
	}

	/**
	 * Sets the write option.
	 *
	 * @param option is the name or key to identify the option
	 * @param enabled sets if the field is enabled with this option
	 */
	public void setWriteOption(String option, boolean enabled) {
		if (writeOptions == null) writeOptions = new HashMap<String, Boolean>();
		writeOptions.put(option, enabled);
	}

	/**
	 * Sets the option visible.
	 *
	 * @param visible sets if the field is visible with the current option
	 */
	private void setOptionVisible(boolean visible) {
		this.optionVisible = visible;
		super.setVisible(visible);
	}

	/**
	 * Sets the option enabled.
	 *
	 * @param enabled sets if the field is enabled with the current option
	 */
	protected void setOptionEnabled(boolean enabled) {this.optionEnabled = enabled;}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.Component#onReset()
	 */
	@Override
	public void onReset() {setValue(defaultValue);}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.Component#onRefresh()
	 */
	@Override
	public void onRefresh(){}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.Component#onStateChange(java.util.ArrayList)
	 */
	@Override
	public void onStateChange(ArrayList<HasCommand> commands){}

	/**
	 * Show the help text in a pop up message window
	 */
	public void showHelp() {
		if (helpText != null && !helpText.isEmpty()) {new MessagePanel(Level.VERBOSE, helpText).showRelativeTo(this);}
	}

	/**
	 * Show the message text in a pop up message window with color controlled by the level
	 *
	 * @param level of message which controls its color
	 * @param text of the message
	 * @param target field to which the message pop up is to point
	 */
	public static void addMessage(Level level, String text, UIObject target) {
		if (target == null) {new MessagePanel(level, text).center();}
		else {new MessagePanel(level, text).showRelativeTo(target);}
	}

	/**
	 * Sets the default value of the field.
	 *
	 * @param defaultValue the default value.
	 */
	public void setDefaultValue(T defaultValue) {this.defaultValue = defaultValue;}

	/**
	 * Sets if the field is enabled and can be changed.
	 *
	 * @param enabled is true if the field can be changed.
	 */
	public void setEnabled(boolean enabled) {this.enabled = enabled;}

	/**
	 * Checks if the field is enabled.
	 *
	 * @return true, if it is readable and writeable and option visible and option enabled and enabled
	 */
	public boolean isEnabled() {return readable && writeable && optionVisible && optionEnabled && enabled;}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.UIObject#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
		super.setVisible(isVisible());
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.UIObject#isVisible()
	 */
	public boolean isVisible() {return readable && optionVisible && visible;}

	/**
	 * Checks if the field is encrypted.
	 *
	 * @return true, if is encrypted
	 */
	public boolean isSecure() {return secure;}

	/**
	 * Sets if the field is to be encrypted.
	 *
	 * @param secure is true if the field is encrypted
	 */
	public void setSecure(boolean secure) {
		this.secure = secure;
		lock.setVisible(secure);
	}

	/**
	 * Sets the help text to be displayed when the filed label is clicked.
	 *
	 * @param text to be displayed when the filed label is clicked
	 */
	public void setHelpText(String text) {helpText = text;}

	/**
	 * @param change event sent by a widget
	 * @return true, if this field sent the change event
	 */
	public boolean sent(ChangeEvent change) {return is((Widget)change.getSource());}

	/**
	 * @param click event sent by a widget
	 * @return true, if this field sent the click event
	 */
	public boolean sent(ClickEvent click) {return this == click.getSource();}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasValue#noValue()
	 */
	public boolean noValue() {return getValue() == null;}

	/**
	 * @return true, if this field has a value
	 */
	public boolean hasValue() {return !noValue();}	
}