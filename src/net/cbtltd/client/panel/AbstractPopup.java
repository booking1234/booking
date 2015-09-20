/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.panel;

import java.util.ArrayList;

import net.cbtltd.client.AbstractRequest;
import net.cbtltd.client.Component;
import net.cbtltd.client.Components;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.HasCommand;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.MessagePanel;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.form.Transition;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class AbstractPopup is the parent of all concrete pop up panel classes to display and change a value of type T.
 * It extends the GWT PopupPanel which enables it to be rendered above another panel and then hidden.
 * It also implements HasComponents so that it can contain Component types such as fields and buttons.
 *
 * @param <T> the type of value displayed and changed by this class.
 * @see com.google.gwt.user.client.ui.PopupPanel
 * @see net.cbtltd.client.HasComponents
 */
public abstract class AbstractPopup<T>
extends PopupPanel
implements HasComponents {

	private final Components components = new Components();
	private boolean resetting = false;
	
	/** The finite state machine to control the appearance and behaviour of the form. */
	protected ArrayList<Transition> fsm;
	
	/** The current state of the form, which is used by the finite state machine. */
	protected String state;
	
	/** The old state is the previous state when executing a state transition - the transition is from sate oldsate to state if successful. */
	protected String oldstate;
	
	/** The tab counter which is incremented each time a field or button is added. */
	protected int tab = 1000;

	/**
	 * Instantiates a new abstract pop up panel.
	 *
	 * @param test is true if the panel is modal.
	 */
	public AbstractPopup(boolean test) {
		super(test);
		AbstractField.CSS.ensureInjected();
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
		setGlassEnabled(true);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasComponents#onReset(java.lang.String)
	 */
	public void onReset(String state) {
		setResetting(true);
		components.onReset();
		onStateChange(state);
		setResetting(false);
	}

	/**
	 * Sets if the fields are enabled and can be changed.
	 *
	 * @param enabled is true if the field can be changed.
	 */
	public void setEnabled(boolean enabled){
		components.setEnabled(enabled);
	}

	/**
	 * Checks if the panel is being reset or updated.
	 *
	 * @return true if the panel is being reset or updated.
	 */
	protected boolean isResetting() {return resetting;}
	
	/**
	 * Checks if the panel is reset and is not being updated.
	 *
	 * @return true, if is reset.
	 */
	protected boolean isReset() {return !isResetting();}
	
	/**
	 * Sets the reset or update status of the panel.
	 *
	 * @param resetting is true when a reset or update starts, and false when it ends.
	 */
	public void setResetting(boolean resetting) {this.resetting = resetting;}

	/**
	 * Displays a pop up message if a condition is satisfied.
	 *
	 * @param condition the condition is true if the message is to be displayed.
	 * @param level the level of the message which dictates its importance and sets its colour.
	 * @param text the text to be displayed in the message.
	 * @param target the field or other widget next to which the message is to be displayed.
	 * @return true, if the condition is satisfied.
	 */
	protected boolean ifMessage(boolean condition, Level level, String text, UIObject target) {
		if (condition) {AbstractField.addMessage(level, text, target);}
		return condition;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasComponents#addComponent(net.cbtltd.client.Component)
	 */
	public void addComponent(Component component) {components.add(component);}
	
	/**
	 * Removes the specified component from the form.
	 *
	 * @param component the component to be removed.
	 */
	public void removeComponent(Component component) {if (components != null) {components.remove(component);}}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasComponents#onRefresh()
	 */
	public void onRefresh() {components.onRefresh();}
	
	/**
	 * Changes the option of the components of the form to the specified value.
	 *
	 * @param option the specified option value.
	 */
	public void onOptionChange(String option) {components.onOptionChange(option);}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ChangeHandler#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	public void onChange(ChangeEvent change) {}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	public void onClick(ClickEvent click) {execute(fsm, click);}
	
	/**
	 * Gets the current value of the panel, which is typically a data transfer object.
	 *
	 * @return the value of the panel.
	 */
	public abstract T getValue();
	
	/**
	 * Sets the panel to the specified value, which is typically a data transfer object.
	 *
	 * @param value the specified value.
	 */
	public abstract void setValue(T value);
	
	/**
	 * Checks if the panel has no current state.
	 *
	 * @return true, if the form has no current state.
	 */
	protected boolean noState() {return state == null;}
	
	/**
	 * Checks if the panel had a state prior to the most recent transition.
	 *
	 * @return true, if the panel had a state prior to the most recent transition.
	 */
	public boolean hadState(String state) {return this.oldstate != null && this.oldstate.equals(state);}
	
	/**
	 * Checks if the panel is in the specified state.
	 *
	 * @param state the specified state
	 * @return true, if the panel is in the specified state.
	 */
	public boolean hasState(String state) {return this.state != null && this.state.equals(state);}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasComponents#hasChanged()
	 */
	public boolean hasChanged() {return components.hasChanged();}
	
	/**
	 * Handles changes to the panel state.
	 *
	 * @param state the new state.
	 */
	protected void onStateChange(String state) {
		this.state = state;
		components.onStateChange(fsm, state);
		components.onOptionChange(state);
	}

	/**
	 * Executes the finite state machine in response to the specified event.
	 *
	 * @param fsm is the finite state machine defining the rules governing state transition.
	 * @param event the event that triggers the transition.
	 */
	public final void execute(ArrayList<Transition> fsm, ClickEvent event) {
		if (fsm == null || fsm.isEmpty()) {return;}
		if (GuardedRequest.isSending()) {new MessagePanel(Level.VERBOSE, AbstractField.CONSTANTS.allLoading()).center(); return;} //Window.alert("Still Loading from Server"); return;}
		for (Transition t : fsm) {
			HasCommand c = t.getCommand();
			AbstractRequest r = c.getRequest();
			oldstate = state;
			if (t.inState(state) && t.is((Widget) event.getSource())) {
				state = t.getToState();
				if (r == null || r.execute()) {onStateChange(state);}
				else {state = oldstate;}
				return;
			}
		}
	}
}
