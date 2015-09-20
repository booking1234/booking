/*
 * Copyright 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package net.cbtltd.client.field.spinner;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * The Class ValueSpinner is a combination of a {@link TextBox} and a
 * {@link Spinner} to allow spinning and display of the resulting value.
 */
public class ValueSpinner extends HorizontalPanel {

	private Spinner spinner;
	private final TextBox valueBox = new TextBox();

	private SpinnerListener spinnerListener = new SpinnerListener() {
		public void onSpinning(Integer value) {
			if (getSpinner() != null) {
				getSpinner().setValue(value, false);
			}
			valueBox.setText(formatValue(value));
		}
	};

	private KeyPressHandler keyPressHandler = new KeyPressHandler() {

		public void onKeyPress(KeyPressEvent event) {
			int index = valueBox.getCursorPos();
			String previousText = valueBox.getText();
			String newText;
			if (valueBox.getSelectionLength() > 0) {
				newText = previousText.substring(0, valueBox.getCursorPos())
				+ event.getCharCode()
				+ previousText.substring(valueBox.getCursorPos()
						+ valueBox.getSelectionLength(), previousText.length());
			} else {
				newText = previousText.substring(0, index) + event.getCharCode()
				+ previousText.substring(index, previousText.length());
			}
			valueBox.cancelKey();
			try {
				Integer newValue = parseValue(newText);
				if (spinner.isConstrained()
						&& (newValue > spinner.getMax() || newValue < spinner.getMin())) {
					return;
				}
				spinner.setValue(newValue, true);
			} catch (Exception e) {
				valueBox.cancelKey();
			}
		}
	};

	/**
	 * @param value initial value
	 */
	 public ValueSpinner(Integer value) {
		this(value, 0, 0, 1, 99, false);
	}

	 /**
	  * Instantiates a new spinner.
	  * 
	  * @param value initial value
	  * @param min min value
	  * @param max max value
	  */
	 public ValueSpinner(Integer value, int min, int max) {
		 this(value, min, max, 1, 99, true);
	 }

	 /**
	  * Instantiates a new spinner.
	  * 
	  * @param value initial value
	  * @param min min value
	  * @param max max value
	  * @param minStep min value for stepping
	  * @param maxStep max value for stepping
	  */
	 public ValueSpinner(Integer value, int min, int max, int minStep, int maxStep) {
		 this(value, min, max, minStep, maxStep, true);
	 }

	 /**
	  * Instantiates a new spinner.
	  * 
	  * @param value initial value
	  * @param min min value
	  * @param max max value
	  * @param minStep min value for stepping
	  * @param maxStep max value for stepping
	  * @param constrained if set to false min and max value will not have any effect
	  */
	 public ValueSpinner(
			 Integer value, 
			 Integer min, 
			 Integer max, 
			 int minStep, 
			 int maxStep,
			 boolean constrained) {
		 super();
		 setStylePrimaryName("gwt-ValueSpinner");
		 spinner = new Spinner(spinnerListener, value, min, max, minStep, maxStep, constrained);
		 valueBox.setStyleName("textBox");
		 valueBox.addKeyPressHandler(keyPressHandler);
		 setVerticalAlignment(ALIGN_MIDDLE);
		 add(valueBox);
		 VerticalPanel arrowsPanel = new VerticalPanel();
		 arrowsPanel.setStylePrimaryName("arrows");
		 arrowsPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		 arrowsPanel.add(spinner.getIncrementArrow());
		 arrowsPanel.add(spinner.getDecrementArrow());
		 add(arrowsPanel);
	 }

	 /**
	  * @return the Spinner used by this widget
	  */
	 public Spinner getSpinner() {
		 return spinner;
	 }

	 /**
	  * @return the SpinnerListener used to listen to the {@link Spinner} events
	  */
	 public SpinnerListener getSpinnerListener() {
		 return spinnerListener;
	 }

	 /**
	  * @return the TextBox used by this widget
	  */
	 public TextBox getTextBox() {
		 return valueBox;
	 }

	 /**
	  * @return whether this widget is enabled.
	  */
	 public boolean isEnabled() {
		 return spinner.isEnabled();
	 }

	 /**
	  * Sets whether this widget is enabled.
	  * 
	  * @param enabled true to enable the widget, false to disable it
	  */
	 public void setEnabled(boolean enabled) {
		 spinner.setEnabled(enabled);
		 valueBox.setEnabled(enabled);
	 }

	 /**
	  * @param value the value to format
	  * @return the formatted value
	  */
	 protected String formatValue(Integer value) {
		 return String.valueOf(value);
	 }

	 /**
	  * @param value the value to parse
	  * @return the parsed value
	  */
	 protected Integer parseValue(String value) {
		 return Integer.valueOf(value);
	 }
}
