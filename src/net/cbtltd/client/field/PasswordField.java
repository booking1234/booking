/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.client.HasComponents;
import net.cbtltd.shared.Model;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class PasswordField.
 */
public class PasswordField
extends AbstractField<String> {
	
	/** The panel. */
	protected final FlowPanel panel = new FlowPanel();
	
	/** The label. */
	protected Label label;
	
	/** The field. */
	protected PasswordTextBox field = new PasswordTextBox();
	
	/**
	 * Instantiates a new password field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field.
	 */
	public PasswordField(
			HasComponents form,
			short[] permission,
			String label,
			int tab) {

		initialize(panel, form, permission, CSS.cbtPasswordField());

		super.setDefaultValue(Model.BLANK);
		if (label != null){
			this.label  = new Label(label);
			this.label.addStyleName(CSS.cbtPasswordFieldLabel());
			this.label.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {showHelp();}
			});
			panel.add(this.label);
		}

		field.addStyleName(CSS.cbtPasswordFieldField());
		field.setTabIndex(tab);
		field.addChangeHandler(form);
		panel.add(field);
		panel.add(lock);
	}

	/**
	 * Sets if the field can be changed.
	 *
	 * @param enabled the new enabled
	 */
	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		field.setEnabled(isEnabled());
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setFocus(boolean)
	 */
	@Override
	public void setFocus(boolean focussed){
		field.setFocus(focussed);
	}

	/**
	 * Sets the CSS style of the field value.
	 *
	 * @param style the CSS style of the field.
	 */
	public void setFieldStyle(String style) {
		field.addStyleName(style);
	}

	/**
	 * Sets the CSS style of the field label.
	 *
	 * @param style the CSS style of the field label.
	 */
	public void setLabelStyle(String style) {
		label.addStyleName(style);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setSecure(boolean)
	 */
	@Override
	public void setSecure(boolean secure) {
		super.setSecure(secure);
		setFieldStyle(CSS.cbtPasswordFieldSecure());
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int tab){
		field.setTabIndex(tab);
	}

	/** Clears the password value. */
	public void clear(){
		field.setText(Model.BLANK);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#getValue()
	 */
	public String getValue() {
		if (secure) {return Model.encrypt(field.getText().trim());}
		else {return field.getText().trim();}
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setValue(java.lang.Object)
	 */
	public void setValue(String value) {
		if (secure) {field.setText(Model.decrypt(value));}
		else {field.setText(value);}
		super.setChanged();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#noValue()
	 */
	public boolean noValue() {
		return (field == null || field.getText() == null  || field.getText().trim().isEmpty());
	}

	/**
	 * Checks if the field has the specified value.
	 *
	 * @param value the specified value.
	 * @return true, if the field has the specified value.
	 */
	public boolean hasValue(String value) {
		if (secure) {return field.getText().equals(Model.decrypt(value));}
		else {return field.getText().equals(value);}
	}
	
	/**
	 * Checks if the field does not have the specified value.
	 *
	 * @param value the specified value.
	 * @return true, if the field does not have the specified value.
	 */
	public boolean notValue(String value) {
		return !hasValue(value);
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#is(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean is(Widget sender){
		return (sender == field);
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#hasChanged()
	 */
	@Override
	public boolean hasChanged() {
		return false;
	}
}
