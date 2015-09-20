/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.client.HasComponents;
import net.cbtltd.shared.License;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class CheckFields displays an array of check boxes whose value is returned as a string of 0 and 1 characters.
 * The value of a checked box is 1, and that of an unchecked box is zero.
 */
public class CheckFields 
extends AbstractField<String> {

	private Panel panel; // = new HorizontalPanel();
	private CheckBox[] field;

	/**
	 * Instantiates a new array of check boxes.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param labels is the optional array of text labels to identify the check boxes.  
	 * @param tab index of the field
	 */
	public CheckFields(
			HasComponents form,
			short[] permission,
			String[] labels,
			boolean vertical,
			int tab) {
		initialize(panel = vertical ? new VerticalPanel() : new HorizontalPanel(), form, permission, CSS.cbtCheckField());

		field = new CheckBox[labels.length];
		for (int index = 0; index < labels.length; index++) {
			field[index] = new CheckBox(labels[index]);
			panel.add(field[index]);
		}
		field[0].setTabIndex(tab);
	}

	/**
	 * Sets if the field is enabled.
	 *
	 * @param enabled is true if the field value can be changed.
	 */
	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		for (int index = 0; index < field.length; index++) {field[index].setEnabled(isEnabled());}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setFocus(boolean)
	 */
	@Override
	public void setFocus(boolean focussed){
		field[0].setFocus(focussed);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int tab){
		field[0].setTabIndex(tab);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#getValue()
	 */
	public String getValue() {
		String value = "";
		for (int index = 0; index < field.length; index++) {value = value + (field[index].getValue() ? "1" : "0");}
		return value;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setValue(java.lang.Object)
	 */
	public void setValue(String value) {
		if (value != null) {
			for (int index = 0; index < field.length; index++) {field[index].setValue(value.substring(index, index + 1).equals("1"));}
		}
		super.setChanged();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#noValue()
	 */
	public boolean noValue(){
		return (field == null || field.length == 0);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#is(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean is(Widget sender) {
		return (sender == this);
	}
}
