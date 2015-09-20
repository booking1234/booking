/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.Date;

import net.cbtltd.client.HasComponents;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

/** The Class TimeField is to display and change a time value. */
public class TimeField
extends AbstractField<String> {

	private Label label;
	protected final FlowPanel panel = new FlowPanel();
	protected final TextBoxBase field = new TextBox();
	
	/**
	 * Instantiates a new time field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.
	 * @param tab index of the field.
	 */
	public TimeField(
			HasComponents form,
			short[] permission,
			String label,
			int tab) {
		initialize(panel, form, permission, CSS.cbtTimeField());

		super.setDefaultValue(TF.format(new Date()));
		
		if (label != null){
			this.label  = new Label(label);
			this.label.addStyleName(CSS.cbtTimeFieldLabel());
			this.label.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {showHelp();}
			});
			panel.add(this.label);
		}

		field.addStyleName(CSS.cbtTimeFieldField());
		field.setTabIndex(tab);
		field.setTitle(label);
		field.addChangeHandler(form);
		field.setTextAlignment(TextBoxBase.ALIGN_LEFT);
		panel.add(field);
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

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#getValue()
	 */
	public String getValue() {
		String value = addSeconds(field.getText());
		try {TF.parse(value);}
		catch (IllegalArgumentException x) {value = defaultValue;}
		return value;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setValue(java.lang.Object)
	 */
	public void setValue(String value) {
		field.setText(removeSeconds(value == null ? defaultValue : value));
		super.setChanged();
	}

	/** 
	 * Adds seconds to specified time value. 
	 * 
	 * @param value the specified time value.
	 * @return the value with seconds added.
	 */
	protected String addSeconds(String value) {
		if (value == null || value.isEmpty()) {return value;}
		else {return value + ":00";}
	}
	
	/** 
	 * Removes seconds from specified time value. 
	 * 
	 * @param value the specified time value.
	 * @return the value with seconds removed.
	 */
	protected String removeSeconds(String value) {
		if (value == null || value.isEmpty()) {return value;}
		String[] args = value.split(":");
		if (args.length >= 2) {return args[0] + ":" + args[1];}
		else {return value;}
	}
	
	/**
	 * Checks if is null.
	 *
	 * @return true, if is null
	 */
	public boolean isNull(){
		return (field == null || field.getText() == null);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#is(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean is(Widget sender){
		return (sender == field);
	}
}
