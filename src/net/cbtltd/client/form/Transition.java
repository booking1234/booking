/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.form;

import net.cbtltd.client.HasCommand;

import com.google.gwt.user.client.ui.Widget;

/**
 * The Class Transition	defines how to change between states in a finite state machine.
 * A finite state machine is defined by an array or list of transition instances.
 */
public class Transition {
	
	private String fromState;
	private HasCommand command;
	private String toState;

	/**
	 * Instantiates a new transition.
	 * 
	 * @param fromState the state from which to transition.
	 * @param command causing the transition to the new state.
	 * @param toState the state to which to transition.
	 */	
	public Transition(
			String fromState,
			HasCommand command,
			String toState) {
		this.fromState = fromState;
		this.toState = toState;
		this.command = command;
	}

	/**
	 * Checks if the transition is in the specified state.
	 *
	 * @param state the specified state.
	 * @return true, if this is in the specified state.
	 */
	public boolean inState(String state){
		return fromState.equalsIgnoreCase(state);
	}

	/**
	 * Checks if the transition is into the specified state.
	 *
	 * @param state the specified state.
	 * @return true, if this is into the specified state.
	 */
	public boolean intoState(String state){
		return toState.equalsIgnoreCase(state);
	}

	/**
	 * Sets the command to be executed by the transition.
	 *
	 * @param command the new command.
	 */
	public void setCommand(HasCommand command) {
		this.command = command;
	}

	/**
	 * Gets the command to be executed by the transition.
	 *
	 * @return the command to be executed by the transition.
	 */
	public HasCommand getCommand() {
		return command;
	}

	/**
	 * Sets the text on the button that triggers the transition.
	 *
	 * @param text the new button text.
	 */
	public void setText(String text) {
		command.setText(text);
	}

	/**
	 * Gets the text on the button that triggers the transition.
	 *
	 * @return the button text.
	 */
	public String getText() {
		return command.getText();
	}

	/**
	 * Sets the state from which the transition changes.
	 *
	 * @param fromState the new state from which the transition changes.
	 */
	public void setFromState(String fromState) {
		this.fromState = fromState;
	}

	/**
	 * Gets the state from which the transition changes.
	 *
	 * @return the new from which the transition changes.
	 */
	public String getFromState() {
		return fromState;
	}

	/**
	 * Sets the state to which the transition changes.
	 *
	 * @param toState the new state to which the transition changes.
	 */
	public void setToState(String toState) {
		this.toState = toState;
	}

	/**
	 * Gets the state to which the transition changes.
	 *
	 * @return toState the new state to which the transition changes.
	 */
	public String getToState() {
		return toState;
	}

	/**
	 * Checks if this is the specified widget.
	 *
	 * @param widget the specified widget.
	 * @return true, if if this is the specified widget.
	 */
	public boolean is(Widget widget){
		return command.is(widget);
	}
}