/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.client.HasComponents;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/** The Class CheckField is to display and change a boolean value. */
public class CheckField
extends AbstractField<Boolean>
implements ClickHandler {

	private final FlowPanel panel = new FlowPanel();
	private final CheckBox field = new CheckBox();

	/**
	 * Instantiates a new check field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param tab index of the field
	 */
	public CheckField(
			HasComponents form,
			short[] permission,
			int tab) {
		this(form, permission, null, tab);
	}

	/**
	 * Instantiates a new check field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field.
	 */
	public CheckField(
			HasComponents form,
			short[] permission,
			String label,
			int tab) {
		initialize(panel, form, permission, CSS.cbtCheckField());

		field.setText(label);
		field.setTabIndex(tab);
		field.addClickHandler(this);
//		field.addMouseOverHandler(new MouseOverHandler() {
//			@Override
//			public void onMouseOver(MouseOverEvent event) {
//				Window.alert("onMouseOver");
//				showHelp();
//			}
//		});
		panel.add(field);
	}

	/**
	 * Handles check box clicks.
	 *
	 * @param event when clicked.
	 */
	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() == field) {fireClick(this);}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#is(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean is(Widget sender) {return (sender == this);}

	/**
	 * Sets if the field is enabled.
	 *
	 * @param enabled is true if the field value can be changed.
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
	 * Sets the CSS style of the check box.
	 *
	 * @param style the CSS style of the check box.
	 */
	public void setFieldStyle(String style) {
		field.addStyleName(style);
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
	public Boolean getValue() {
		return field.getValue();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setValue(java.lang.Object)
	 */
	public void setValue(Boolean value) {
		field.setValue(value == null ? false : value);
		super.setChanged();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#noValue()
	 */
	public boolean noValue(){
		return (field == null);
	}

}