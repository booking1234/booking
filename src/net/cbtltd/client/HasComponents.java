/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickHandler;

/** The Interface HasComponents is implemented by types that contain Component types. */
public interface HasComponents
extends ChangeHandler, ClickHandler {
	
	/**
	 * Adds the specified component to the container.
	 *
	 * @param component the specified component.
	 */
	void addComponent(Component component);
	
	/**
	 * Checks if any components have changed.
	 *
	 * @return true, if any components have changed.
	 */
	boolean hasChanged();
	
	/**
	 * Handles state change events.
	 *
	 * @param state the new state.
	 */
	void onReset(String state);
	
	/**
	 * Refreshes the container.
	 */
	void onRefresh();
}
