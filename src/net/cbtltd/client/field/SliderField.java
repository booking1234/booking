/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.client.HasComponents;
import net.cbtltd.client.field.slider.Slider;
import net.cbtltd.client.field.slider.SliderEvent;
import net.cbtltd.client.field.slider.SliderListener;
import net.cbtltd.client.field.slider.SliderOption;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/** The Class SliderField is to display and change an integer value using the jQuery slider widget. */
public class SliderField
extends AbstractField<Integer[]>
implements SliderListener {

	private static int id = 0;
	private final FlowPanel panel = new FlowPanel();
	private final Grid field = new Grid(1, 4);
	private final Label unit = new Label();
	private Label label;
	private Slider slider;
	private Double[] valueMap;//	eg Double[] distances = {0.5, 1.0, 2.0, 5.0, 10.0, 20.0, 50.0, 100.0};
	private int sliderCount = 0;
	private Double[] range = new Double[4]; //min, max, step, unlimited
	private Double scale = 1.0;
	
	/**
	 * Instantiates a new slider field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.
	 * @param args is the array of arguments to set the slider range, step and initial value(s).
	 * @param tab index of the field.
	 */
	public SliderField (
			HasComponents form,
			short[] permission,
			String label,
			Integer[] args, // min, max, step, initial value(s) - two for range
			int tab) {

		initialize(panel, form, permission, CSS.cbtSliderField());

		if (label != null) {
			this.label  = new Label(label);
			this.label.addStyleName(CSS.cbtSliderFieldLabel());
			this.label.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {showHelp();}
			});
			panel.add(this.label);
		}

		sliderCount = args.length - 3;
		
		range[0] = args[0].doubleValue();
		range[1] = args[1].doubleValue();
		range[2] = args[2].doubleValue();
		range[3] = range[1];
		
		if (args.length >= 5) {
			field.setWidget(0, 0, getWidget(args[3]));
			field.getWidget(0, 0).addStyleName(CSS.cbtSliderFieldMin());
			field.setWidget(0, 2, getWidget(args[4]));
			field.getWidget(0, 2).addStyleName(CSS.cbtSliderFieldMax());
			field.setWidget(0, 3, unit);
			field.getWidget(0, 3).addStyleName(CSS.cbtSliderFieldUnit());
			defaultValue = new Integer[2];
			defaultValue[0] = args[3];
			defaultValue[1] = args[4];
		}
		else {
			field.setWidget(0, 2, getWidget(args[3]));
			field.getWidget(0, 2).addStyleName(CSS.cbtSliderFieldMax());
			field.setWidget(0, 3, unit);
			field.getWidget(0, 3).addStyleName(CSS.cbtSliderFieldUnit());
			defaultValue = new Integer[1];
			defaultValue[0] = args[3];
		}

		JSONObject options = Slider.getOptions(args[0], args[1], getInitial(args));
		if (args.length == 5) {options.put(SliderOption.RANGE.toString(), JSONBoolean.getInstance(true));}
		if (args[2] != 0) {options.put(SliderOption.STEP.toString(), new JSONNumber(args[2]));}
		slider = new Slider(String.valueOf(id++), options);
		slider.addListener(this);
		slider.addStyleName(CSS.cbtSliderFieldSlider());
		field.setWidget(0, 1, slider);

		field.addStyleName(CSS.cbtSliderFieldField());
		panel.add(field);
	}

	/**
	 * Sets the slider scale factor.
	 * For example, the range, step and initial value(s) of a target price would change if the pricing unit changes from per each to per dozen.
	 * The scale factor changes from 1.0 to 12.0 in this example.
	 * 
	 * @param scale the new scale factor.
	 */
	public void setScale(Double scale) {
		if (this.scale == null || this.scale <= 0.0 || this.scale == scale) {return;}

		Double factor = scale / this.scale;
		//Window.alert("SliderField setScale " + this.scale + ", " + scale + ", " + factor + ", " + range[0] + ", " + range[1]);
		this.scale = scale;

		range[0] *= factor;
		slider.setMinimum(range[0].intValue());
		range[1] *= factor;
		slider.setMaximum(range[1].intValue());
		range[2] *= factor;

		Integer[] value = getValue();
		for (int index = 0; index < value.length; index++) {
			Double val = value[index] * factor;
			value[index] = val.intValue();
		}
		setValue(value);
	}
	
	/* Gets the initial value(s) of the slider. */
	private int[] getInitial(Integer[] value) {
		if (value.length < 4) {return null;}
		int[] initial = new int[value.length - 3];
		for (int i = 3; i < value.length; i++) {initial[i - 3] = value[i];}
		return initial;
	}

	/**
	 * Gets the widget that renders the specified value.
	 * THIS IS PUBLIC SO IT CAN BE OVERRIDEN!
	 *
	 * @param value the specified value.
	 * @return the widget that renders the specified value.
	 */
	public Widget getWidget(int value) {
		Double val = (valueMap == null) ? value : valueMap[value];
		if (value > range[1]) {return new Label(NUMBER_CONSTANTS.infinity());}
		else if (val > -1.0 && val < 1.0) {return new Label(QF.format(val));}
		else {return new Label(IF.format(val));}
	}

	/*
	 * Render the field as a grid of widgets for the specified values.
	 *
	 * @param values the specified values.
	 */
	private void setGrid(int[] values) {
		if (range[3] > 0.0 && Double.valueOf(values[sliderCount - 1])*1.02 >= range[1]) {values[sliderCount - 1] = range[3].intValue();}

		if (field.getWidget(0, 0) == null) {
			field.setWidget(0, 2, getWidget(values[0]));
			field.getWidget(0, 2).addStyleName(CSS.cbtSliderFieldMax());
			field.setWidget(0, 3, unit);
			field.getWidget(0, 3).addStyleName(CSS.cbtSliderFieldUnit());
		}
		else {
			field.setWidget(0, 0, getWidget(values[0]));
			field.getWidget(0, 0).addStyleName(CSS.cbtSliderFieldMin());
			field.setWidget(0, 2, getWidget(values[1]));
			field.getWidget(0, 2).addStyleName(CSS.cbtSliderFieldMax());
			field.setWidget(0, 3, unit);
			field.getWidget(0, 3).addStyleName(CSS.cbtSliderFieldUnit());
		}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.slider.SliderListener#onChange(net.cbtltd.client.field.slider.SliderEvent)
	 */
	@Override
	public void onChange(SliderEvent event) {
		if (event.getSource() == slider && event.hasOriginalEvent() && isEnabled()) {
			setGrid(event.getValues());
			fireChange(this);
		}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.slider.SliderListener#onSlide(net.cbtltd.client.field.slider.SliderEvent)
	 */
	@Override
	public boolean onSlide(SliderEvent event) {
		if (event.getSource() == slider && isEnabled()) {
			setGrid(event.getValues());
		}
		return isEnabled();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.slider.SliderListener#onStart(net.cbtltd.client.field.slider.SliderEvent)
	 */
	@Override
	public void onStart(SliderEvent event) { }

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.slider.SliderListener#onStop(net.cbtltd.client.field.slider.SliderEvent)
	 */
	@Override
	public void onStop(SliderEvent event) { }

	/**
	 * Sets the default value to be used when the field is reset.
	 *
	 * @param value the new default value.
	 */
	public void setDefaultValue(Integer value) {
		if (value >= range[0] && value <= range[1]) {defaultValue[0] = value;}
	}

	/**
	 * Sets the array of values to map the integer value of the slider to the external value of the field.
	 * For example, a slider having eight steps might be mapped to a non-liner set of values by the array:
	 * Double[] valueMap = {0.5, 1.0, 2.0, 5.0, 10.0, 20.0, 50.0, 100.0);
	 * The first step returns 0.5, the second returns 1.0, the third returns 2.0 and so on, and vice versa.
	 *
	 * @param valueMap the new value map.
	 */
	public void setValueMap(Double[] valueMap) {
		this.valueMap = valueMap;
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

	/**
	 * Sets the CSS style of the slider.
	 *
	 * @param style the CSS style of the slider.
	 */
	public void setSliderStyle(String style) {
		slider.addStyleName(style);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setSecure(boolean)
	 */
	@Override
	public void setSecure(boolean secure) {
		super.setSecure(secure);
		setFieldStyle(CSS.cbtTextFieldSecure());
	}

	/**
	 * Sets the value from which there is no maximum value os is considered unlimited.
	 * An unlimited value is displayed by the infinity sign in the slider.
	 *
	 * @param value the new unlimited value
	 */
	public void setUnlimitedValue(Integer value) {
		range[3] = value.doubleValue();
	}

	/**
	 * Sets if the slider is visible.
	 *
	 * @param visible is true if the slider is visible.
	 */
	public void setFieldVisible(boolean visible){
		slider.setVisible(visible);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setFocus(boolean)
	 */
	@Override
	public void setFocus(boolean focussed) {}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int tab) {}

	/**
	 * Sets the name of the slider's unit of measure.
	 *
	 * @param text the new unit of measure.
	 */
	public void setUnit(String text) {
		this.unit.setText(text);
	}

	/**
	 * Gets the value mapped by the value map for the specified slider index.
	 *
	 * @param index the specified index.
	 * @return the mapped value of the index.
	 */
	public Double getMappedValue(int index) {
		return 	(valueMap == null) ?  0.0 : valueMap[slider.getValueAtIndex(index)];
	}

	/**
	 * Gets the mapped values from the current slider values.
	 *
	 * @return the mapped values.
	 */
	public Double[] getMappedValue() {
		Double[] value = new Double[sliderCount];
		if (valueMap != null) {
			for (int index = 0; index < sliderCount; index++) {value[index] = valueMap[slider.getValueAtIndex(index)];}
		}
		return value;
	}

	/** Resets the maximum range of the slider to its initial values. */
	public void setMaxRange() {
		Integer[] maxrange = new Integer[2];
		maxrange[0] = range[0].intValue();
		maxrange[1] = range[1].intValue();
		setValue(maxrange);
	}
	
	/**
	 * Sets the values of the slider.
	 *
	 * @param value the new values.
	 */
	public void setValue(Integer[] value) {
		if (sliderCount <= 0 || value == null || value.length != sliderCount) {Window.alert("SliderField setValue value=" + value.length + " slider=" + sliderCount); return;}
		int[] values = new int[sliderCount];
		for (int index = 0; index < sliderCount; index++) {values[index] = (value == null || value[index] == null ? 0 : value[index]);}
		slider.setValues(values);
		setGrid(values);
		super.setChanged();
	}

	/**
	 * Sets the value of a slider that has a single button.
	 *
	 * @param value the new value.
	 */
	public void setValue(Integer value) {
		Integer[] values = new Integer[sliderCount];
		values[0] = value;
		setValue(values);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#getValue()
	 */
	public Integer[] getValue() {
		Integer[] value = new Integer[sliderCount];
		for (int index = 0; index < sliderCount; index++) {value[index] = slider.getValueAtIndex(index);}
		if (range[3] > 0.0 && Double.valueOf(value[sliderCount - 1])*1.02 >= range[1]) {value[sliderCount - 1] = range[3].intValue();}
		return value;
	}

	/**
	 * Gets the value at the specified index.
	 *
	 * @param index the specified index.
	 * @return the value at the index.
	 */
	public Integer getValue(int index) {
		if (noValue()) {return new Integer(0);}
		else if (range[3] > 0.0 && Double.valueOf(slider.getValueAtIndex(index)) * 1.02 >= range[1]) {return new Integer(range[3].intValue());}
		else {return new Integer(slider.getValueAtIndex(index));}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasValue#noValue()
	 */
	public boolean noValue() {return getValue() == null || getValue().length <= 0;}


	/* (non-Javadoc)
	 * @see net.cbtltd.client.Component#hasChanged()
	 */
	public boolean hasChanged() {return false;}
	//TODO public boolean hasChanged() {return noValue() ? changed != 0 : changed != getValue().toString().hashCode();}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#is(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean is(Widget sender) {
		return (sender == this);
	}
}
