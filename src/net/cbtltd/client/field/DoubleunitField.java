/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.List;

import net.cbtltd.client.HasComponents;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.i18n.client.NumberFormat;

/**
 * The Class DoubleunitField extends DoubleField is to also display and select its unit of measure.
 */
public class DoubleunitField 
extends DoubleField {
	
	private ListField unitlist;

	/**
	 * Instantiates a new doubleunit field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.  
	 * @param formatter the formatter to format and parse the textual representation of the field value.
	 * @param tab index of the field.
	 */
	public DoubleunitField(
			HasComponents form, 
			short[] permission, 
			List<NameId> items,
			String label, 
			NumberFormat formatter, 
			int tab) {
		super(form, permission, label, formatter, tab);
		unitlist = new ListField(form, permission, items, null, false, tab);
		unitlist.setFieldStyle(CSS.cbtNumberFieldUnit());
		unitlist.setVisible(isEnabled());
		unitlist.addChangeHandler(form);
		value.add(unitlist);
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#sent(com.google.gwt.user.client.ui.ChangeEvent)
	 */
	@Override
	public boolean sent(ChangeEvent change) {return field == change.getSource() || unitlist == change.getSource();}

	/**
	 * Instantiates a new doubleunit field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param action the NameId action to populate the list of permissible units of measure.
	 * @param label is the optional text to identify the field.  
	 * @param format the formatter to format and parse the textual representation of the field value.
	 * @param tab index of the field
	 */
	public DoubleunitField(
			HasComponents form, 
			short[] permission, 
			NameIdAction action,
			String label, 
			NumberFormat format, 
			int tab) {
		super(form, permission, label, format, tab);
		unitlist = new ListField(form, permission, action, null, false, tab);
		unitlist.setFieldStyle(CSS.cbtNumberFieldUnit());
		unitlist.setVisible(isEnabled());
		value.add(unitlist);
	}
	
	/**
	 * Sets if the unit of measure of the field is enabled.
	 *
	 * @param enabled is true if the unit of measure of the field is enabled.
	 */
	public void setUnitenabled(boolean enabled){
		unitlist.setEnabled(enabled);
	}

	/**
	 * Sets the default unit value.
	 *
	 * @param value the new default unit value
	 */
	public void setDefaultUnitvalue(String value) {
		unitlist.setDefaultValue(value);
	}

	/**
	 * Sets the CSS style of the unit value.
	 *
	 * @param style the CSS style of the unit.
	 */
	public void setUnitStyle(String style) {
		unitlist.addStyleName(style);
	}

	/**
	 * Sets the unit value.
	 *
	 * @param value the new unit value
	 */
	public void setUnitvalue(String value) {
		unitlist.setValue(value);
		super.setChanged();
	}

	/**
	 * Gets the unit value.
	 *
	 * @return the unit value
	 */
	public String getUnitvalue() {
		return 	unitlist.getValue();
	}

	/**
	 * Checks if there is no unit value.
	 *
	 * @return true, if if there is no unit value.
	 */
	public boolean noUnitvalue() {
		return unitlist.noValue();
	}

	/**
	 * Checks if there is a unit value.
	 *
	 * @return true, if if there is a unit value.
	 */
	public boolean hasUnitvalue() {
		return !noUnitvalue();
	}

	/**
	 * Checks if there is a unit value of the specified value.
	 *
	 * @param value the specified value.
	 * @return true, if there is a unit value of the specified value.
	 */
	public boolean hasUnitvalue(String value) {
		return unitlist.hasValue(value);
	}
}
