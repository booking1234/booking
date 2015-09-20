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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class RatingField displays an array of radio buttons whose value is returned as an integer.
 * The value of a checked box is 1, and that of an unchecked box is zero.
 */
public class RatingField 
extends AbstractField<Integer> {

	private static int id = 0;
	private final FlowPanel panel = new FlowPanel();
	private final Label lo = new Label();
	private final Label hi = new Label();
	private Label label;
	private RadioButton[] field;

	/**
	 * Instantiates a new array of check boxes.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.
	 * @param count is the number of buttons.
	 * @param tab index of the field
	 */
	public RatingField(
			HasComponents form,
			short[] permission,
			String label,
			int count,
			int tab) {
		initialize(panel, form, permission, CSS.cbtRatingField());

		if (label != null){
			this.label  = new Label(label);
			this.label.addStyleName(CSS.cbtRatingFieldLabel());
			this.label.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {showHelp();}
			});
			panel.add(this.label);
		}

		final HorizontalPanel buttons = new HorizontalPanel();
		buttons.addStyleName(CSS.cbtRatingFieldField());
		field = new RadioButton[count];
		lo.addStyleName(CSS.cbtRatingFieldLabel());
		buttons.add(lo);
		for (int index = 0; index < count; index++) {
			field[index] = new RadioButton(String.valueOf(id));
			buttons.add(field[index]);
		}
		hi.addStyleName(CSS.cbtRatingFieldLabel());
		buttons.add(hi);
		if (count > 0) {field[0].setTabIndex(tab);}
		panel.add(buttons);
		id++;
	}

	/**
	 * Sets the high value label.
	 *
	 * @param text the new high value label.
	 */
	public void setHi(String text) {
		hi.setText(text);
	}
	
	/**
	 * Sets the low value label.
	 *
	 * @param text the new low value label.
	 */
	public void setLo(String text) {
		lo.setText(text);
	}
	
	/**
	 * Sets if the field is enabled.
	 *
	 * @param enabled is true if the field value can be changed.
	 */
	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		for (int index = 0; index < field.length; index++) {field[index].setEnabled(isEnabled());}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setFocus(boolean)
	 */
	@Override
	public void setFocus(boolean focussed){
		field[0].setFocus(focussed);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int tab){
		field[0].setTabIndex(tab);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#getValue()
	 */
	public Integer getValue() {
		for (int index = 0; index < field.length; index++) {
			if (field[index].getValue()) {return new Integer(index);}
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setValue(java.lang.Object)
	 */
	public void setValue(Integer value) {
		if (value != null) {
			for (int index = 0; index < field.length; index++) {field[index].setValue(value == index);}
		}
		super.setChanged();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#noValue()
	 */
	public boolean noValue(){
		return (field == null || field.length == 0);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#is(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean is(Widget sender) {
		return (sender == this);
	}
}
