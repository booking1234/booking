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
import com.google.gwt.user.client.ui.Widget;

public class ProgressField 
extends AbstractField<Integer> {

	private final FlowPanel panel = new FlowPanel();
	private Label label;
	private ProgressBar field;
	
	/**
	 * Create a progress bar within the given range starting at the specified progress amount.
	 * 
	 * @param minProgress the minimum progress
	 * @param maxProgress the maximum progress
	 */
	
	public ProgressField(
			HasComponents form,
			short[] permission,
			String label,
			Integer minProgress, 
			Integer maxProgress,
			int tab) {
		initialize(panel, form, permission, CSS.cbtTextField());

		super.setDefaultValue(0);
		
		if (label != null){
			this.label  = new Label(label);
			this.label.addStyleName(CSS.cbtTextFieldLabel());
			this.label.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {showHelp();}
			});
			panel.add(this.label);
		}

		field = new ProgressBar(minProgress, maxProgress);
		panel.add(field);
		panel.add(lock);
	}

 	/*****************************************************************************************
	 * Events - handles events
	 ****************************************************************************************/
	@Override
	public boolean is(Widget sender){
		return (sender == field);
	}

	/*****************************************************************************************
	 * General - setters and getters
	 ****************************************************************************************/
	public void setLo(int lo) {
		field.lo = lo;
	}

	public void setLm(int lm) {
		field.lm = lm;
	}

	public void setHm(int hm) {
		field.hm = hm;
	}

	@Override
	public void setFocus(boolean focussed){
		//field.setFocus(focussed);
	}

	@Override
	public void setTabIndex(int tab){
		//field.setTabIndex(tab);
	}

	public void setLabelStyle(String style) {
		label.addStyleName(style);
	}

	public void setFieldStyle(String style) {
		field.setBarStyleName(style);
	}

//	public void setTextFirstHalfStyleName(String style) {
//		field.setTextFirstHalfStyleName(style);
//	}
//
//	public void setTextSecondHalfStyleName(String style) {
//		field.setTextSecondHalfStyleName(style);
//	}

//	public void setTextStyleName(String style) {
//		field.setTextStyleName(style);
//	}

	public void setTextVisible(boolean visible) {
		field.setTextVisible(visible);
	}
	
	public Integer getPercent() {
		return field.getPercent();
	}

	public Integer getValue() {
		return field.getProgress();
	}

	public void setValue(Integer value) {
		field.setProgress(value);
		super.setChanged();
	}

}
