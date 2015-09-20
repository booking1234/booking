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

import net.cbtltd.client.field.ProgressBar;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/** The Class VolumeSlider combines a {@link ProgressBar} with a {@link Spinner}. */
public class VolumeSlider extends Composite {
	private Spinner spinner;
	private ProgressBar progressBar;

	private SpinnerListener listener = new SpinnerListener() {
		public void onSpinning(Integer value) {
			progressBar.setProgress(value);
		}
	};

	/**
	 * Instantiates a new volume slider.
	 * 
	 * @param min the minimum value of the spinner.
	 * @param max the maximum value of the spinner.
	 * @param value the initial value of the spinner.
	 */
	public VolumeSlider(Integer min, Integer max, Integer value) {
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setStyleName("gwt-VolumeSlider");
		horizontalPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		progressBar = new ProgressBar(min, max);
		progressBar.setProgress(value);
		//    spinner = new Spinner(listener, value, min, max, 1, 5, true, resources);
		spinner = new Spinner(listener, value, min, max, 1, 5, true);
		SimplePanel leftPanel = new SimplePanel();
		leftPanel.add(spinner.getDecrementArrow());
		leftPanel.setStyleName("decreaseArrow");
		horizontalPanel.add(leftPanel);
		horizontalPanel.add(progressBar);
		progressBar.setWidth("100%");
		horizontalPanel.setCellWidth(progressBar, "100%");
		SimplePanel rightPanel = new SimplePanel();
		rightPanel.add(spinner.getIncrementArrow());
		rightPanel.setStyleName("increaseArrow");
		horizontalPanel.add(rightPanel);
		initWidget(horizontalPanel);
	}

	/**
	 * @return the progress bar
	 */
	public ProgressBar getProgressBar() {
		return progressBar;
	}

	/**
	 * @return the spinner
	 */
	public Spinner getSpinner() {
		return spinner;
	}

	/**
	 * @return Gets whether this widget is enabled
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
	}
}
