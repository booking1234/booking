/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.client.HasComponents;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;

public class ToggleField
extends AbstractField<Boolean>
implements ClickHandler {

	private final FlowPanel panel = new FlowPanel();
	private Label label;
	private ToggleButton  field;

	public ToggleField(
			HasComponents form,
			short[] permission,
			String label,
			String upLabel,
			String downLabel,
			int tab) {

		initialize(panel, form, permission, CSS.cbtToggleField());

		super.setDefaultValue(false);
		
		if (label != null) {
			this.label  = new Label(label);
			this.label.addStyleName(CSS.cbtToggleFieldLabel());
			this.label.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {showHelp();}
			});
			panel.add(this.label);
		}

		field = new ToggleButton (upLabel, downLabel);
		field.setTabIndex(tab);
		field.setDown(true);
		field.addStyleName(CSS.cbtToggleFieldField());
		field.addClickHandler(this);
		panel.add(field);
		panel.add(lock);
	}

	/*****************************************************************************************
	 * Handles click events
	 ****************************************************************************************/
	@Override
	public boolean is(Widget sender){
		return (sender == this);
	}
	
	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() == field) {
			fireChange(this);
		}
	}

	/*****************************************************************************************
	 * General - setters and getters
	 ****************************************************************************************/
	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		field.setEnabled(isEnabled());
	}

	public Boolean getDefaultValue() {
		return field.isDown();
	}

	public void setDefaultValue(Boolean defaultValue) {
		field.setDown(defaultValue);
	}

	public void setFieldStyle(String style) {
		field.addStyleName(style);
	}

	public void setLabelStyle(String style) {
		if (label != null) {label.addStyleName(style);}
	}

	public void setFieldVisible(boolean visible){
		label.setVisible(visible);
		field.setVisible(visible);
	}

	@Override
	public void setFocus(boolean focussed){
		field.setFocus(focussed);
	}

	@Override
	public void setTabIndex(int tab){
		field.setTabIndex(tab);
	}

//	public String getText() {
//		return field.getText();
//	}

	public boolean noValue() {
		return field == null;// || field.getText().trim().isEmpty();
	}

//	public boolean hasValue(String value) {
//		return field.getText().trim().equals(value);
//	}

	public Boolean getValue() { //false = up true = down
		return field.isDown();
	}

	public void setValue(Boolean value) {
		if (value == null) {field.setDown(false);}
		else {field.setDown(value);}
		super.setChanged();
	}
}
