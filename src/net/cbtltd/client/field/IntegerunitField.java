/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.List;

import com.google.gwt.user.client.Window;

import net.cbtltd.client.HasComponents;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;

/**
 * The Class IntegerunitField extends IntegerField is to also display and select its unit of measure.
 */
public class IntegerunitField 
extends IntegerField {
	
	private ListField unitlist;

	/**
	 * Instantiates a new double unit field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field.
	 */
//	public IntegerunitField(
//			HasComponents form, 
//			short[] permission, 
//			String label,
//			int tab) {
//		this(form, permission, new UnitNameId(), label, tab);
//	}
	
	/**
	 * Instantiates a new doubleunit field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.  
	 * @param formatter the formatter to format and parse the textual representation of the field value.
	 * @param tab index of the field.
	 */
	public IntegerunitField(
			HasComponents form, 
			short[] permission, 
			List<NameId> items,
			String label, 
			int tab) {
		super(form, permission, label, tab);
		unitlist = new ListField(form, permission, items, null, false, tab);
		unitlist.setFieldStyle(CSS.cbtNumberFieldUnit());
		unitlist.setVisible(isEnabled());
		value.add(unitlist);
	}
	
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
	public IntegerunitField(
			HasComponents form, 
			short[] permission, 
			NameIdAction action,
			String label, 
			int tab) {
		super(form, permission, label, tab);
		unitlist = new ListField(form, permission, action, null, false, tab);
		unitlist.setFieldStyle(CSS.cbtNumberFieldUnit());
		unitlist.setVisible(isEnabled());
		value.add(unitlist);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.Component#onReset()
	 */
	@Override
	public void onReset() {
		unitlist.onReset();
		super.onReset();
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
