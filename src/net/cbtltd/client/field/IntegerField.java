/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.client.HasComponents;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.i18n.client.NumberFormat;

/** The Class IntegerField is to display and change integer values. */
public class IntegerField
extends NumberField<Integer> {

	public static final NumberFormat IF = NumberFormat.getFormat(CONSTANTS.allFormatInteger());
	private Integer minimumValue;
	private Integer maximumValue;

	/**
	 * Instantiates a new integer field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field
	 */
	public IntegerField(
			HasComponents form, 
			short[] permission, 
			String label, 
			int tab) {
		super(form, permission, label, tab);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ChangeHandler#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	public void onChange(ChangeEvent event) {
		if (minimumValue != null && getValue() < minimumValue) {addMessage(Level.ERROR, CONSTANTS.errMinimumValue(), this);}
		if (maximumValue != null && getValue() > maximumValue) {addMessage(Level.ERROR, CONSTANTS.errMaximumValue(), this);}
	}
	
	/**
	 * Checks if the field value is in range.
	 *
	 * @return true, if the field value is between the minimum and maximum values, if they exist.
	 */
	public boolean inRange() {
		if (minimumValue == null || maximumValue == null) {return true;}
		return getValue() >= minimumValue && getValue() <= maximumValue;
	}

	/**
	 * Sets the valid range of values.
	 *
	 * @param minimumValue the minimum permissible value of the field.
	 * @param maximumValue the maximum permissible value of the field.
	 */
	public void setRange(Integer minimumValue, Integer maximumValue) {
		this.minimumValue = minimumValue;
		this.maximumValue = maximumValue;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setValue(java.lang.Object)
	 */
	public void setValue(Integer value) {
		super.setText(IF.format((value == null) ? 0 : value));
		super.setChanged();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#getValue()
	 */
	public Integer getValue() {
		Integer value;
		try {
			Double parsed = IF.parse(super.getText());
			value = parsed.intValue();
		}
		catch (NumberFormatException x) {value = defaultValue;}
		super.setText(IF.format((value == null) ? 0 : value));
		return value;
	}
}
