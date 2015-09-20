/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.widget.datepicker.DatePicker;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Time;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class DatespanField extends DateField to a time span from date to todate.
 * The first date is the start and the second date is the end of the time span.
 * A default duration and time unit may be specified for the field.
 */
public class DatespanField
extends DateField {

	private final TextBoxBase tofield = new TextBox();
	private DatePicker topicker = new DatePicker();
	private PopupPanel topopup = new PopupPanel(true);
	private Double defaultDuration;
	private String defaultUnit;
	private ListBox unitList = new ListBox();

	/**
	 * Instantiates a new date span field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field
	 */
	public DatespanField (
			HasComponents form,
			short[] permission,
			String label,
			int tab) {
		super(form, permission, label, tab);

		Date now = new Date();
		setTovalue(new Date(now.getYear(), now.getMonth(), now.getDay()));
//		setTovalue(Time.getDateClientStart());

		topicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			public void onValueChange(ValueChangeEvent<Date> event) {
				setTovalueAndFireChange(event.getValue());
				topopup.hide();
			}
		});
		topopup.add(topicker);

		tofield.addStyleName(CSS.cbtDateFieldField());
		tofield.setTabIndex(tab);
		tofield.addClickHandler(this);
		panel.add(tofield);
		unitList.addStyleName(CSS.cbtDateFieldUnit());
		unitList.setVisible(false);
		unitList.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				resetValue();
			}
		});
		panel.add(unitList);
	}
	
	/**
	 * Handles click events.
	 * 
	 * @param event when clicked.
	 */
	@Override
	public void onClick(ClickEvent event){
		if (event.getSource() == tofield) {
			int top = this.getAbsoluteTop() - 200;
			topicker.setCurrentMonth(getTovalue());
			topopup.setPopupPosition(this.getAbsoluteLeft() + 150, top < 0 ? 0 : top);
			topopup.show();
		}
		else {super.onClick(event);}
	}
	
	/**
	 * Resets field to default values.
	 */
	@Override
	public void onReset() {
		super.onReset();
		resetValue();
		if (defaultDuration != null) {setTovalue(Time.addDuration(getValue(), defaultDuration, Time.DAY));}
		if (defaultUnit != null) {setUnit(defaultUnit);}
	}

	/** Reset values to new unit. */
	private void resetValue() {
		setValue(getValue());
		setTovalue(getTovalue());
	}
	
	/**
	 * Sets the default duration of the time span.
	 * If the default duration is set the to date is set to date plus default duration each time it changes.
	 * 
	 * @param duration the new default duration
	 */
	public void setDefaultDuration(Double duration) {
		this.defaultDuration = duration;
	}

	/**
	 * Gets the duration.
	 *
	 * @return the duration in the current time unit.
	 */
	public Double getDuration() {
		if (hasUnit(Time.DAY.name())) {return getDuration(Time.DAY);}
		else if (hasUnit(Time.MONTH.name())) {return getDuration(Time.MONTH);}
		else if (hasUnit(Time.YEAR.name())) {return getDuration(Time.YEAR);}
		else {return getDuration(Time.DAY);}
	}

	/**
	 * Gets the duration in the specified unit.
	 *
	 * @param unit the specified unit.
	 * @return the duration in the specified time unit.
	 */
	public Double getDuration(Time unit) {
		return Time.getDuration(getValue(), getTovalue(), unit);
	}
	
	/**
	 * Sets the duration in the specified time unit.
	 *
	 * @param duration of the time span.
	 * @param unit of the duration.
	 */
	public void setDuration(Double duration, Time unit) {
		setTovalue(Time.addDuration(getValue(), duration, unit));
	}

	/**
	 * Sets the default time unit.
	 *
	 * @param defaultUnit the default unit of the time span.
	 */
	public void setDefaultUnit(String defaultUnit) {
		this.defaultUnit = defaultUnit;
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
		tofield.setEnabled(isEnabled());
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.DateField#setFieldStyle(java.lang.String)
	 */
	public void setFieldStyle(String style) {
		field.addStyleName(style);
		tofield.addStyleName(style);
	}

	/**
	 * Gets the end date of the time span.
	 *
	 * @return the new end date of the time span.
	 */
	public Date getTovalue() {
		try {return AbstractRoot.getDF().parse(tofield.getText());}
		catch(IllegalArgumentException x) {return new Date();}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.DateField#setValue(java.util.Date)
	 */
	public void setValue(Date value) {
		if (value == null) {field.setText(Model.BLANK);}
		else {
			if (hasUnit(Time.DAY.name())) {value = Time.getDateStart(value);}
			else if (hasUnit(Time.WEEK.name())) {value = Time.getWeekStart(value);}
			else if (hasUnit(Time.MONTH.name())) {value = Time.getMonthStart(value);}
			else if (hasUnit(Time.QUARTER.name())) {value = Time.getQuarterStart(value);}
			else if (hasUnit(Time.YEAR.name())) {value = Time.getYearStart(value);}
			field.setText(AbstractRoot.getDF().format(value));
			if (defaultDuration != null) {setTovalue(Time.addDuration(getValue(), defaultDuration, Time.DAY));}
		}
		super.setChanged();
	}

	/**
	 * Sets the start date of the time span.
	 *
	 * @param value the new start date of the time span.
	 */
	public void setTovalue(Date value) {
		if (value == null) {tofield.setText(Model.BLANK);}
		else {
			if (hasUnit(Time.DAY.name())) {value = Time.getDateEnd(value);}
			else if (hasUnit(Time.WEEK.name())) {value = Time.getWeekEnd(value);}
			else if (hasUnit(Time.MONTH.name())) {value = Time.getMonthEnd(value);}
			else if (hasUnit(Time.QUARTER.name())) {value = Time.getQuarterEnd(value);}
			else if (hasUnit(Time.YEAR.name())) {value = Time.getYearEnd(value);}
			tofield.setText(AbstractRoot.getDF().format(value));
		}
		super.setChanged();
	}

	/**
	 * Sets the end date of the time span and fires a change event.
	 *
	 * @param value the new end date of the time span.
	 */
	public void setTovalueAndFireChange(Date value) {
		setTovalue(value);
		fireChange(this);
	}

	/**
	 * Returns if the to date of this field sent the change event.
	 *
	 * @param change the change event.
	 * @return true, if the to date of this field sent the change event.
	 */
	public boolean tovalueSent(ChangeEvent change) {
		return tofield == (Widget)change.getSource();
	}

	/**
	 * Returns true if the end date of this field has no value.
	 *
	 * @return true, if the end date of this field has no value.
	 */
	public boolean noTovalue(){
		return (tofield.getText() == null || tofield.getText().isEmpty());
	}
	
	/**
	 * Checks if end date is before start date.
	 *
	 * @return true, if end date is before start date.
	 */
	public boolean isEndBeforeStart(){
		return getValue().after(getTovalue());
	}

	/**
	 * Sets the time units from which the duration unit is selected.
	 *
	 * @param values the time units from which the duration unit is selected.
	 */
	public void setUnits(ArrayList<NameId> values) {
		unitList.setVisible(values != null && values.size() > 1);
		for (NameId value : values) {
			unitList.addItem(value.getName(), value.getId());
		}
	}

	/**
	 * Gets the selected time unit.
	 *
	 * @return the selected time unit.
	 */
	public String getUnit() {
		if (noUnit()) {return null;}
		else {return unitList.getValue(unitList.getSelectedIndex());}
	}

	/**
	 * Sets the selected time unit.
	 *
	 * @param value the new selected time unit.
	 */
	public void setUnit(String value) {
		if (unitList == null || unitList.getItemCount() == 0) {return;}
		if (value == null || value.isEmpty()) {unitList.setSelectedIndex(0);}
		for (int index = 0;index < unitList.getItemCount();index++) {
			if (unitList.getValue(index).equalsIgnoreCase(value)) {
				unitList.setSelectedIndex(index);
				break;
			}
		}
		if (defaultValue != null) {
			resetValue();
			if (defaultDuration != null) {setTovalue(Time.addDuration(getValue(), defaultDuration, Time.DAY));}
		}
		fireChange(this);
	}

	/**
	 * Returns true if the field has no time unit.
	 *
	 * @return true, if the field has no time unit.
	 */
	public boolean noUnit() {
		return unitList == null || unitList.getItemCount() == 0 || unitList.getSelectedIndex() < 0;
	}
	
	/**
	 * Checks if the field has a time unit.
	 *
	 * @return true, if the field has a time unit.
	 */
	public boolean hasUnit() {
		return !noUnit();
	}

	/**
	 * Checks if the field has the specified time unit.
	 *
	 * @param value the specified time unit.
	 * @return true, if the field has the specified time unit.
	 */
	public boolean hasUnit(String value) {
		if (value == null || unitList == null || unitList.getItemCount() == 0) {return false;}
		else {return unitList.getValue(unitList.getSelectedIndex()).equals(value);}
	}

	/**
	 * Returns if the time unit of this field sent the change event.
	 *
	 * @param change the change event.
	 * @return true, if the time unit of this field sent the change event.
	 */
	public boolean unitSent(ChangeEvent change) {
		return unitList == (Widget)change.getSource();
	}
}
