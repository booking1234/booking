/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import net.cbtltd.client.resource.FieldBundle;
import net.cbtltd.client.resource.FieldCssResource;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class Navigator
extends Composite
implements ClickHandler, HasChangeHandlers {

	private static final FieldBundle BUNDLE = FieldBundle.INSTANCE;
	private static final FieldCssResource CSS = BUNDLE.css();
	private FlowPanel panel = new FlowPanel();
	private Image first = new Image(BUNDLE.pageFirst());
	private Image last = new Image(BUNDLE.pageLast());
	private Image next = new Image(BUNDLE.pageNext());
	private Image prev = new Image(BUNDLE.pagePrevious());
	private Label label = new Label();
	private boolean absolute = false;
	private boolean enabled = true;
	private int index;
	private int end;

	/** Instantiates a new navigator. */
	public Navigator() {
		initWidget(panel);
		
		panel.addStyleName(CSS.cbtNavigator());
		final HorizontalPanel navigator = new HorizontalPanel();
		panel.add(navigator);

		next.addClickHandler(this);
		next.addStyleName(CSS.cbtNavigatorButton());

		prev.addClickHandler(this);
		prev.addStyleName(CSS.cbtNavigatorButton());

		last.addClickHandler(this);
		last.addStyleName(CSS.cbtNavigatorButton());

		first.addClickHandler(this);
		first.addStyleName(CSS.cbtNavigatorButton());

		label.addStyleName(CSS.cbtNavigatorLabel());

		navigator.add(first);
		navigator.add(prev);
		navigator.add(label);
		navigator.add(next);
		navigator.add(last);
	}

	/**
	 * Adds the specified change handler.
	 * 
	 * @param handler to be added.
	 */
	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return addDomHandler(handler, ChangeEvent.getType());
	}
	
	/**
	 * Fires a change event.
	 * @param sender of event.
	 */
	protected void fireChange(Widget sender) {
		NativeEvent nativeEvent = Document.get().createChangeEvent();
		DomEvent.fireNativeEvent(nativeEvent, sender);
	}

	/**
	 * Checks if this sent the specified event.
	 * 
	 * @param event the specified event.
	 * @return true if this sent the specified event.
	 */
	public boolean sent(ChangeEvent event) {
		return this == event.getSource();
	}
	
	/**
	 * Handles the clicking of any of the buttons in the navigator as follows:
	 * >> button sets the offset to max(end - limit, start)
	 * > button sets the offset to max (offset + limit, end)
	 * < button sets the offset to min (offset - limit, start)
	 * << button sets the offset to start
	 * Fires a change event on any change in offset.
	 * 
	 * @param event the click event.
	 */
	@Override
	public void onClick(ClickEvent event) {
		if (!enabled) {return;}
		else if (event.getSource() == first) {index = 0;}
		else if (event.getSource() == prev) {index = (absolute ? 1 : (index <= 0)? 0 : index - 1);}
		else if (event.getSource() == next) {index = (absolute ? 2 : (index == end)? end : index + 1);}
		else if (event.getSource() == last) {index = absolute ? 3 : end;}
		setLabel();
		fireChange(this);
	}

	/**
	 * Sets if the navigator has an absolute or relative index.
	 * 
	 * @param absolute is true if the navigator has an absolute index.
	 */
	public void setAbsolute(boolean absolute) {
		this.absolute = absolute;
	}

	/* Sets the label according to the current index and range. */
	private void setLabel() {
		label.setText("" + (index + 1) + " of " + (end + 1));
	}

	/**
	 * Sets the label according to the specified value.
	 * 
	 * @param text the specified value.
	 */
	public void setLabel(String text) {
		label.setText(text);
	}

	/**
	 * Sets if the navigator is enabled and can respond to clicks.
	 * 
	 * @param enabled is true if the navigator is enabled.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Gets the current index.
	 * 
	 * @return the current index.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Sets the current index.
	 * 
	 * @param index the new current index.
	 */
	public void setIndex(int index) {
		this.index = index;
		setLabel();
	}

	/**
	 * Sets the last index.
	 * 
	 * @param end the new last index.
	 */
	public void setEnd(int end) {
		this.end = end;
		setLabel();
	}
}
