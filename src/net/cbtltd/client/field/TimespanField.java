/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.Date;

import net.cbtltd.client.HasComponents;
import net.cbtltd.shared.Time;

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;

// TODO: Auto-generated Javadoc
/** The Class TimeField is to display and change a time span. */
public class TimespanField 
extends TimeField {

	private final TextBoxBase tofield = new TextBox();
	private String defaultTovalue = TF.format(new Date());

	/**
	 * Instantiates a new timespan field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.
	 * @param tab index of the field.
	 */
	public TimespanField(
			HasComponents form,
			short[] permission,
			String label,
			int tab) {
		super(form, permission, label, tab);
		
		tofield.addStyleName(CSS.cbtTimeFieldField());
		tofield.setTabIndex(tab);
		tofield.setTitle(label);
		tofield.addChangeHandler(form);
		tofield.setTextAlignment(TextBoxBase.ALIGN_LEFT);
		panel.add(tofield);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#onReset()
	 */
	@Override
	public void onReset() {
		setTovalue(defaultTovalue);
		super.onReset();
	}

	/**
	 * Sets the default to value.
	 *
	 * @param value the new default to value.
	 */
	public void setDefaultTovalue(String value){
		this.defaultTovalue = value;
	}

	/**
	 * Gets the duration in the specified time unit.
	 *
	 * @param unit the specified time unit.
	 * @return the duration in the specified time unit.
	 */
	public double getDuration(Time unit) {
		Date fromvalue = TF.parse(getValue());
		Date tovalue = TF.parse(getTovalue());
		return Time.getDuration(fromvalue, tovalue, unit);
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.TimeField#setFieldStyle(java.lang.String)
	 */
	public void setFieldStyle(String style) {
		field.addStyleName(style);
		tofield.addStyleName(style);
	}

	/**
	 * Gets the to value.
	 *
	 * @return the to value.
	 */
	public String getTovalue() {
		String value = addSeconds(tofield.getText());
		try {TF.parse(value);}
		catch (IllegalArgumentException x) {value = defaultTovalue;}
		return value;
	}

	/**
	 * Sets the to value.
	 *
	 * @param value the new to value.
	 */
	public void setTovalue(String value) {
		tofield.setText(removeSeconds(value == null ? defaultTovalue : value));
		super.setChanged();
	}
}
