/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.client.HasComponents;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;

// TODO: Auto-generated Javadoc
/**
 * The Class DoublespanField extends DoubleField to manage two related double precifion numbers.
 */
public class DoublespanField 
extends DoubleField {
	
	private TextBox tofield = new TextBox();
	private Double defaultTovalue;

	/**
	 * Instantiates a new doublespan field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.  
	 * @param formatter the formatter to format and parse the textual representation of the field value.
	 * @param tab index of the field.
	 */
	public DoublespanField(
			HasComponents form, 
			short[] permission, 
			String label, 
			NumberFormat formatter, 
			int tab) {
		super(form, permission, label, formatter, tab);
		
		tofield.addStyleName(CSS.cbtNumberFieldField());
		tofield.setTabIndex(tab);
		tofield.addChangeHandler(form);
		tofield.addChangeHandler(this);
		tofield.setTextAlignment(TextBoxBase.ALIGN_RIGHT);
		value.add(tofield);
	}
	
	/**
	 * Resets field to default values.
	 */
	@Override
	public void onReset() {
		super.onReset();
		if (defaultTovalue != null) {setTovalue(defaultTovalue);}
	}

	/**
	 * Sets the default to value.
	 *
	 * @param value the new default to value
	 */
	public void setDefaulttovalue(Double value) {
		this.defaultTovalue = value;
	}

	/**
	 * Sets the unit value.
	 *
	 * @param value the new unit value
	 */
	public void setTovalue(Double value) {
		tofield.setText(NF.format((value == null) ? 0.0 : value));
		super.setChanged();
	}

	/**
	 * Gets the to value.
	 *
	 * @return the to value
	 */
	public Double getTovalue() {
		Double value;
		try {value = NF.parse(tofield.getText());}
		catch (NumberFormatException x) {value = defaultTovalue;}
		super.setText(NF.format((value == null) ? 0.0 : value));
		return value;
	}
	
	/**
	 * Checks if there is no to value.
	 *
	 * @return true, if there is no to value.
	 */
	public boolean noTovalue() {
		return (tofield == null || tofield.getText() == null || tofield.getText().isEmpty());
	}

}
