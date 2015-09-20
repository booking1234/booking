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

import java.util.ArrayList;
import java.util.List;

import net.cbtltd.client.resource.FieldBundle;
import net.cbtltd.client.resource.FieldConstants;
import net.cbtltd.client.resource.FieldCssResource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;


/**
 * The Class Spinner provides two arrows for increasing and decreasing values.
 * @see <pre>http://code.google.com/p/google-web-toolkit/wiki/ImageResource for upgrade.</pre>
 */
public class Spinner {

	public static final FieldConstants CONSTANTS = GWT.create(FieldConstants.class);
	public static final FieldBundle BUNDLE = FieldBundle.INSTANCE;

	private static final int INITIAL_SPEED = 7;

	private final Image arrowDown = new Image();
	private final Image arrowUp = new Image();

	private List<SpinnerListener> spinnerListeners = new ArrayList<SpinnerListener>();
	private int step, minStep, maxStep, initialSpeed = 7;
	private Integer value, min, max;
	private boolean increment;
	private boolean constrained;
	private boolean enabled = true;

	private final Timer timer = new Timer() {
		private int counter = 0;
		private int speed = 7;

		@Override
		public void cancel() {
			super.cancel();
			speed = initialSpeed;
			counter = 0;
		}

		@Override
		public void run() {
			counter++;
			if (speed <= 0 || counter % speed == 0) {
				speed--;
				counter = 0;
				if (increment) {
					increase();
				} else {
					decrease();
				}
			}
			if (speed < 0 && step < maxStep) {
				step += 1;
			}
		}
	};

	private MouseDownHandler mouseDownHandler = new MouseDownHandler() {

		public void onMouseDown(MouseDownEvent event) {
			if (enabled) {
				Image sender = (Image) event.getSource();
				if (sender == arrowUp) {
					AbstractImagePrototype.create(BUNDLE.arrowUpPressed()).applyTo(sender);
					increment = true;
					increase();
				} else {
					AbstractImagePrototype.create(BUNDLE.arrowDownPressed()).applyTo(sender);
					increment = false;
					decrease();
				}
				timer.scheduleRepeating(30);
			}  
		}
	};

	private MouseOverHandler mouseOverHandler = new MouseOverHandler() {
		public void onMouseOver(MouseOverEvent event) {
			if (enabled) {
				Image sender = (Image) event.getSource();
				if (sender == arrowUp) {
					AbstractImagePrototype.create(BUNDLE.arrowUpHover()).applyTo(sender);
				} else {
					AbstractImagePrototype.create(BUNDLE.arrowDownHover()).applyTo(sender);
				}
			}
		}
	};

	private MouseOutHandler mouseOutHandler = new MouseOutHandler() {
		public void onMouseOut(MouseOutEvent event) {
			if (enabled) {
				cancelTimer((Widget) event.getSource());
			}
		}
	};

	private MouseUpHandler mouseUpHandler = new MouseUpHandler() {
		public void onMouseUp(MouseUpEvent event) {
			if (enabled) {
				cancelTimer((Widget) event.getSource());
			}
		}
	};

	/**
	 * Instantiates a new Spinner instance.
	 * 
	 * @param spinner the widget listening to the arrows
	 * @param value initial value
	 * @param min min value
	 * @param max max value
	 */
	  public Spinner(SpinnerListener spinner, Integer value, Integer min, Integer max) {
	    this(spinner, value, min, max, 1, 99, true);
	  }

	/**
	 * Instantiates a new Spinner instance.
	 * 
	 * @param spinner the widget listening to the arrows
	 * @param value initial value
	 * @param min min value
	 * @param max max value
	 * @param minStep min value for stepping
	 * @param maxStep max value for stepping
	 */
	  public Spinner(SpinnerListener spinner, Integer value, Integer min, Integer max,
	      int minStep, int maxStep) {
	    this(spinner, value, min, max, minStep, maxStep, true);
	  }

	/**
	 * Instantiates a new Spinner instance.
	 * 
	 * @param spinner the widget listening to the arrows
	 * @param value initial value
	 * @param min min value
	 * @param max max value
	 * @param minStep min value for stepping
	 * @param maxStep max value for stepping
	 * @param constrained determines if min and max value will take effect
	 */
	public Spinner(SpinnerListener spinner, Integer value, Integer min, Integer max,
			int minStep, int maxStep, boolean constrained) {
		super();
		spinnerListeners.add(spinner);
		this.value = value;
		this.constrained = constrained;
		this.step = minStep;
		this.minStep = minStep;
		this.maxStep = maxStep;
		this.min = min;
		this.max = max;
		this.initialSpeed = INITIAL_SPEED;
		arrowUp.addMouseUpHandler(mouseUpHandler);
		arrowUp.addMouseDownHandler(mouseDownHandler);
		arrowUp.addMouseOverHandler(mouseOverHandler);
		arrowUp.addMouseOutHandler(mouseOutHandler);
		AbstractImagePrototype.create(BUNDLE.arrowUp()).applyTo(arrowUp);
		arrowDown.addMouseUpHandler(mouseUpHandler);
		arrowDown.addMouseDownHandler(mouseDownHandler);
		arrowDown.addMouseOverHandler(mouseOverHandler);
		arrowDown.addMouseOutHandler(mouseOutHandler);
		AbstractImagePrototype.create(BUNDLE.arrowDown()).applyTo(arrowDown);
		fireOnValueChanged();
	}

	/**
	 * @param listener the listener to add
	 */
	public void addSpinnerListener(SpinnerListener listener) {
		spinnerListeners.add(listener);
	}

	/**
	 * @return the image representing the decreating arrow
	 */
	public Image getDecrementArrow() {
		return arrowDown;
	}

	/**
	 * @return the image representing the increasing arrow
	 */
	public Image getIncrementArrow() {
		return arrowUp;
	}

	/**
	 * @return the maximum value
	 */
	public Integer getMax() {
		return max;
	}

	/**
	 * @return the maximum spinner step
	 */
	public int getMaxStep() {
		return maxStep;
	}

	/**
	 * @return the minimum value
	 */
	public Integer getMin() {
		return min;
	}

	/**
	 * @return the minimum spinner step
	 */
	public int getMinStep() {
		return minStep;
	}

	/**
	 * @return the current value
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * @return true is min and max values are active, false if not
	 */
	public boolean isConstrained() {
		return constrained;
	}

	/**
	 * @return Gets whether this widget is enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param listener the listener to remove
	 */
	public void removeSpinnerListener(SpinnerListener listener) {
		spinnerListeners.remove(listener);
	}

	/**
	 * Sets whether this widget is enabled.
	 * 
	 * @param enabled true to enable the widget, false to disable it
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		if (enabled) {
			AbstractImagePrototype.create(BUNDLE.arrowUp()).applyTo(arrowUp);
			AbstractImagePrototype.create(BUNDLE.arrowDown()).applyTo(arrowDown);
		} else {
			AbstractImagePrototype.create(BUNDLE.arrowUpDisabled()).applyTo(arrowUp);
			AbstractImagePrototype.create(BUNDLE.arrowDownDisabled()).applyTo(arrowDown);
		}
		if (!enabled) {
			timer.cancel();
		}
	}

	/**
	 * @param initialSpeed the initial speed of the spinner. Higher values mean
	 *          lower speed, default value is 7
	 */
	public void setInitialSpeed(int initialSpeed) {
		this.initialSpeed = initialSpeed;
	}

	/**
	 * @param max the maximum value. Will not have any effect if constrained is
	 *          set to false
	 */
	public void setMax(Integer max) {
		this.max = max;
	}

	/**
	 * @param maxStep the maximum step for this spinner
	 */
	public void setMaxStep(int maxStep) {
		this.maxStep = maxStep;
	}

	/**
	 * @param min the minimum value. Will not have any effect if constrained is
	 *          set to false
	 */
	public void setMin(Integer min) {
		this.min = min;
	}

	/**
	 * @param minStep the minimum step for this spinner
	 */
	public void setMinStep(int minStep) {
		this.minStep = minStep;
	}

	/**
	 * @param value sets the current value of this spinner
	 * @param fireEvent fires value changed event if set to true
	 */
	public void setValue(Integer value, boolean fireEvent) {
		this.value = value;
		if (fireEvent) {
			fireOnValueChanged();
		}
	}

	/**
	 * Decreases the current value of the spinner by subtracting current step
	 */
	protected void decrease() {
		value -= step;
		if (constrained && value < min) {
			value = min;
			timer.cancel();
		}
		fireOnValueChanged();
	}

	/**
	 * Increases the current value of the spinner by adding current step
	 */
	protected void increase() {
		value += step;
		if (constrained && value > max) {
			value = max;
			timer.cancel();
		}
		fireOnValueChanged();
	}

	private void cancelTimer(Widget sender) {
		step = minStep;
		if (sender == arrowUp) {
			AbstractImagePrototype.create(BUNDLE.arrowUp()).applyTo((Image) sender);
		} else {
			AbstractImagePrototype.create(BUNDLE.arrowDown()).applyTo((Image) sender);
		}
		timer.cancel();
	}

	private void fireOnValueChanged() {
		for (SpinnerListener listener : spinnerListeners) {
			listener.onSpinning(value);
		}
	}
}
