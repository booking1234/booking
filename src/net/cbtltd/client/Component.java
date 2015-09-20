/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client;

import java.util.ArrayList;

/**
 * The Interface Component interface is implemented by types contained in HasComponent types.
 */
public interface Component {
	
	/** The ENABLED constant indicates if the component is enabled and can be used. */
	String ENABLED = "Enabled";
	
	/** The DISABLED constant indicates if the component is disabled and can not be used. */
	String DISABLED = "Disabled";
	
	/**
	 * Checks if the component has changed since the last occurrence of setChanged().
	 *
	 * @return true, if the component has changed.
	 */
	boolean hasChanged();
	
	/** Sets the field used to determine if the component has changed since it was set. */
	void setChanged();
	
	/** Refreshes the component. */
	void onRefresh();
	
	/** Resets the component to its default value(s). */
	void onReset();
	
	/**
	 * Handles changes to the component option.
	 *
	 * @param option the component option that has changed.
	 */
	void onOptionChange(String option);
	
	/**
	 * Handles changes to the state of the container of the component.
	 * The finite state machine of the container determines which commands are active in the new state.
	 * A list of these commands is passed to its component(s). 
	 *
	 * @param commands the commands that are active in the new state.
	 */
	void onStateChange(ArrayList<HasCommand> commands);
	
	/**
	 * Sets if the field is enabled and can be changed.
	 *
	 * @param enabled is true if the field can be changed.
	 */
	void setEnabled(boolean enabled);
}