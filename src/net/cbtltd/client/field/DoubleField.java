/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.client.HasComponents;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.i18n.client.NumberFormat;

/**
 * The Class DoubleField is to display and change double precision floating point values.
 */
public class DoubleField
extends NumberField<Double> {

	private Double minimumValue;
	private Double maximumValue;
	protected NumberFormat NF;

	/**
	 * Instantiates a new double field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.  
	 * @param formatter the formatter to format and parse the textual representation of the field value.
	 * @param tab index of the field
	 */
	public DoubleField(
			HasComponents form, 
			short[] permission, 
			String label, 
			NumberFormat formatter,
			int tab) {
		super(form, permission, label, tab);
		this.NF = formatter;
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
	public void setRange(Double minimumValue, Double maximumValue) {
		this.minimumValue = minimumValue;
		this.maximumValue = maximumValue;
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setValue(java.lang.Object)
	 */
	public void setValue(Double value) {
		super.setText(NF.format((value == null) ? 0.0 : value));
		super.setChanged();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#getValue()
	 */
	public Double getValue() {
		Double value;
		try {value = NF.parse(super.getText());}
		catch (NumberFormatException x) {value = defaultValue;}
		super.setText(NF.format((value == null) ? 0.0 : value));
		return value;
	}
}
