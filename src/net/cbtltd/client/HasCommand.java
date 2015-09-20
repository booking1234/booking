/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client;


import com.google.gwt.user.client.ui.Widget;

/** 
 * The Interface HasCommand is implemented by types used in the Transition objects of a finite state machine.
 * @see net.cbtltd.client.form.Transition
 */
public interface HasCommand extends Component {
	
	/**
	 * Checks if this is the specified widget.
	 *
	 * @param widget the specified widget.
	 * @return true, if this is the specified widget.
	 */
	boolean is(Widget widget);
	
	/**
	 * Gets the request that is executed by this command.
	 *
	 * @return the request that is executed by this command.
	 */
	AbstractRequest getRequest();
	
	/**
	 * Sets the text displayed on the command button.
	 *
	 * @param text the new text displayed on the command button.
	 */
	void setText(String text);
	
	/**
	 * Gets the text displayed on the command button.
	 *
	 * @return the text displayed on the command button.
	 */
	String getText();
}