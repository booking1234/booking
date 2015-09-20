/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.client.HasComponents;
import net.cbtltd.client.field.spinner.Spinner;
import net.cbtltd.client.field.spinner.SpinnerListener;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/** The Class SpinnerField is to display and change an integer value using up/down arrow buttons. */
public class SpinnerField
extends AbstractField<Integer>
implements SpinnerListener {

	private final FlowPanel panel = new FlowPanel();
	private Label label;
	private final HorizontalPanel horizontalPanel = new HorizontalPanel();
	private final TextBox field = new TextBox();
	private Spinner spinner;
	private boolean refreshed = false;
	private boolean cron = true;
	
	/**
	 * Instantiates a new spinner field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param minimumValue the minimum of the allowed value range.
	 * @param maximumValue the maximum of the allowed value range.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field.
	 */
	public SpinnerField(HasComponents form, 
			short[] permission,
			Integer minimumValue,
			Integer maximumValue,
			String label,
			int tab) {
		initialize(panel, form, permission, CSS.cbtSpinnerField());

		if (label != null){
			this.label  = new Label(label);
			this.label.addStyleName(CSS.cbtSpinnerFieldLabel());
			this.label.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {showHelp();}
			});
			panel.add(this.label);
		}

		field.addStyleName(CSS.cbtSpinnerFieldField());
		field.addKeyPressHandler(keyPressHandler);
		field.addChangeHandler(form);

		horizontalPanel.add(field);
		
		spinner = new Spinner(this, 0, minimumValue, maximumValue);
		
		VerticalPanel arrowsPanel = new VerticalPanel();
		arrowsPanel.addStyleName(CSS.cbtSpinnerFieldArrows());
		spinner.getIncrementArrow().addStyleName(CSS.cbtSpinnerFieldArrowUp());
		arrowsPanel.add(spinner.getIncrementArrow());
		spinner.getDecrementArrow().addStyleName(CSS.cbtSpinnerFieldArrowDown());
		arrowsPanel.add(spinner.getDecrementArrow());
		horizontalPanel.add(arrowsPanel);

		panel.add(horizontalPanel);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#onRefresh
	 */
	@Override
	public void onRefresh() {
		refreshed = true;
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.spinner.SpinnerListener#onSpinning(java.lang.Integer)
	 */
	public void onSpinning(Integer value) {
		if (refreshed && value != null && isEnabled()) {
			field.setText(value < 0 && cron ? "*" : String.valueOf(value));
			fireChange(this);
		}
	}

	/* Handles key press events from the spinner widget. */
	private KeyPressHandler keyPressHandler = new KeyPressHandler() {

		public void onKeyPress(KeyPressEvent event) {
			int index = field.getCursorPos();
			String previousText = field.getText();
			String newText;
			if (field.getSelectionLength() > 0) {
				newText = previousText.substring(0, field.getCursorPos())
				+ event.getCharCode()
				+ previousText.substring(field.getCursorPos()
						+ field.getSelectionLength(), previousText.length());
			} else {
				newText = previousText.substring(0, index) + event.getCharCode()
				+ previousText.substring(index, previousText.length());
			}
			field.cancelKey();
			try {
				Integer newValue = Integer.valueOf(newText);
				if (spinner.isConstrained()
						&& (newValue > spinner.getMax() || newValue < spinner.getMin())) {
					return;
				}
				setValue(newValue);
			} catch (Exception e) {field.cancelKey();}
		}
	};

	
	public void setCron(boolean cron) {
		this.cron = cron;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.spinner.SpinnerListener#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		spinner.setEnabled(isEnabled());
	}

	/**
	 * Sets the CSS style of the spinner.
	 *
	 * @param style the CSS style of the spinner.
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
	 * Checks if the spinner is in range.
	 *
	 * @return true, if the spinner is in range.
	 */
	public boolean inRange() {
		if (spinner.getMin() == null || spinner.getMax() == null) {return true;}
		return getValue() >= spinner.getMin() && getValue() <= spinner.getMax();
	}

	/**
	 * Sets the allowed spinner range.
	 *
	 * @param minimumValue the minimum value of the range.
	 * @param maximumValue the maximum value of the range.
	 */
	public void setRange(Integer minimumValue, Integer maximumValue) {
		spinner.setMin(minimumValue);
		spinner.setMax(maximumValue);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#getValue()
	 */
	public Integer getValue() {
		return spinner == null ? null : spinner.getValue();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setValue(java.lang.Object)
	 */
	public void setValue(Integer value) {
		if (spinner == null || value == null) {return;}
		spinner.setValue(value, false);
		field.setText(cron && value < 0 ? "*" : String.valueOf(value));
		super.setChanged();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#is(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean is(Widget sender){
		return (sender == this);
	}
}
