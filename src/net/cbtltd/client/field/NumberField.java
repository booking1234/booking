/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.client.HasComponents;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class NumberField is the abstract parent of DoubleField and IntegerField.
 *
 * @param <T> the numeric type.
 */
public abstract class NumberField<T>
extends AbstractField<T> 
implements ChangeHandler {
	
	private final FlowPanel panel = new FlowPanel();
	protected final HorizontalPanel value = new HorizontalPanel();
	private Label label;
	protected TextBox field = new TextBox();

	/**
	 * Instantiates a new number field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field.
	 */
	public NumberField (
			HasComponents form,
			short[] permission,
			String label,
			int tab) {
		initialize(panel, form, permission, CSS.cbtNumberField());

		if (label != null){
			this.label  = new Label(label);
			this.label.addStyleName(CSS.cbtNumberFieldLabel());
			this.label.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {showHelp();}
			});
			panel.add(this.label);
		}

		field.addStyleName(CSS.cbtNumberFieldField());
		field.setTabIndex(tab);
		field.addChangeHandler(form);
		field.addChangeHandler(this);
		field.setTextAlignment(TextBoxBase.ALIGN_RIGHT);
		value.add(field);
		panel.add(value);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#is(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean is(Widget sender) {
		return (sender == field);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		field.setEnabled(isEnabled());
	}

	/**
	 * Sets the text of the field label.
	 *
	 * @param text the text of the field label.
	 */
	public void setLabel(String text) {
		if (label != null) {label.setText(text);}
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
		if (label != null) {label.addStyleName(style);}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setSecure(boolean)
	 */
	@Override
	public void setSecure(boolean secure) {
		super.setSecure(secure);
		setFieldStyle(CSS.cbtNumberFieldSecure());
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

	/**
	 * Gets the text value of the number.
	 *
	 * @return the text value of the number.
	 */
	public String getText() {
		return field.getText();
	}

	/**
	 * Sets the text value of the number..
	 *
	 * @param value the new text value of the number.
	 */
	public void setText(String value) {
		field.setText(value);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#noValue()
	 */
	public boolean noValue() {
		return (field == null || field.getText() == null || field.getText().isEmpty());
	}
}
