/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget;

import java.util.Date;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.widget.datepicker.DatePicker;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Model;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class DateField is to display and change date values. 
 * Dates may be entered in a formatted text box or selected from a pop up calendar.
 * 
 * Date processing is not something that is easily done in GWT in a consistent cross-platform way. 
 * For example: get today’s date, add a number of days, compute the number of days between two dates, etc. 
 * This is because Java doesn’t have a class to represent a day and the Date class is more like a time stamp: 
 * it encapsulates a moment in time stored as the number of milliseconds to/from the moment called the epoch, 
 * which is arbitrarily fixed at January 1, 1970, 00:00:00 GMT.
 * The day of month, month, year, etc. methods have been deprecated since the introduction of the Calendar class (JDK 1.1). 
 * Unfortunately the Calendar class isn’t available in GWT’s JRE emulation library. 
 * So one must use the deprecated methods of class Date. For example to create a Date that corresponds to a given day, 
 * one would use new Date(y, m, d). When instantiating a Date object using these deprecated methods, each JavaScript 
 * implementation tries to do its best to guess the correct time stamp, based on things such as the current time zone and 
 * whether daylight savings time was effective at that time, and of course these rules vary by OS, JavaScript implementation 
 * and depend on the browser's regional settings. You will not notice this problem if all you do is use dates on the client-side; 
 * the problem really becomes a nightmare when you mix locally instantiated dates with serialized dates from the server.
 * The awkward but working solution is to subtract Date.getTimezoneOffset() from Date.getTime() to cancel out the client’s 
 * time zone and daylight savings time. This is implemented in the Razor Time class.
 * @see net.cbtltd.shared.Time
 */
public class DateField
extends AbstractField<Date>
implements ClickHandler {

	protected final FlowPanel panel = new FlowPanel();
	private Label label;
	protected TextBox field = new TextBox();
	private DatePicker picker = new DatePicker();
	protected PopupPanel popup = new PopupPanel(true);

	/**
	 * Instantiates a new date field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field
	 */
	public DateField(
			HasComponents form,
			short[] permission,
			String label,
			int tab) {
		initialize(panel, form, permission, CSS.cbtDateField());

		if (label != null){
			this.label  = new Label(label);
			this.label.addStyleName(CSS.cbtDateFieldLabel());
			this.label.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {showHelp();}
			});
			panel.add(this.label);
		}

		Date now = new Date();
		setDefaultValue(new Date(now.getYear(), now.getMonth(), now.getDay()));
		setValue(new Date(now.getYear(), now.getMonth(), now.getDay()));
//		setDefaultValue(Time.getDateClientStart());
//		setValue(Time.getDateClientStart());

		picker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			public void onValueChange(ValueChangeEvent<Date> event) {
				setValueAndFireChange(event.getValue());
				popup.hide();
			}
		});
		popup.add(picker);

		field.addStyleName(CSS.cbtDateFieldField());
		field.setTabIndex(tab);
		field.addClickHandler(this);
		panel.add(field);
	}

	/**
	 * Handles click events
	 *
	 * @param event when clicked.
	 */
	@Override
	public void onClick(ClickEvent event){
		if (event.getSource() == field) {
			int top = this.getAbsoluteTop() - 200;
			popup.setPopupPosition(this.getAbsoluteLeft() + 150, top < 0 ? 0 : top);
			popup.show();
		}
	}

	/**
	 * Sets if the field is enabled.
	 *
	 * @param enabled is true if the field value can be changed.
	 */
	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		field.setEnabled(isEnabled());
	}

	/**
	 * Sets the CSS style of the text box.
	 *
	 * @param style the CSS style of the text box.
	 */
	public void setFieldStyle(String style) {
		field.addStyleName(style);
	}

	/**
	 * Sets the CSS style of the label.
	 *
	 * @param style  the CSS style of the label.
	 */
	public void setLabelStyle(String style) {
		label.addStyleName(style);
	}

	/**
	 * Sets the text of the label.
	 *
	 * @param text of the label.
	 */
	public void setLabel(String text) {
		this.label.setText(text);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setSecure(boolean)
	 */
	@Override
	public void setSecure(boolean secure) {
		super.setSecure(secure);
		setFieldStyle(CSS.cbtDateFieldSecure());
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setFocus(boolean)
	 */
	@Override
	public void setFocus(boolean focussed){
		field.setFocus(focussed);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int tab){
		field.setTabIndex(tab);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#getValue()
	 */
	public Date getValue() {
		try {return AbstractRoot.getDF().parse(field.getText());}
		catch(IllegalArgumentException x) {return new Date();}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setValue(java.lang.Object)
	 */
	public void setValue(Date date) {
		if (date == null) {
			field.setText(Model.BLANK);
			picker.setValue(new Date());
		}
		else {
			field.setText(AbstractRoot.getDF().format(date));
			picker.setValue(date);
		}
		super.setChanged();
	}

	/**
	 * Sets the value and fires a change event.
	 *
	 * @param date the new date value.
	 */
	public void setValueAndFireChange(Date date) {
		setValue(date);
		fireChange(this);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#noValue()
	 */
	public boolean noValue(){
		return (field.getText() == null || field.getText().isEmpty());
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#is(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean is(Widget sender){
		return (sender == this);
	}
}
