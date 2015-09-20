/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client;


/**
 * The Class AbstractRequest has behaviour common to all local and remote service requests.
 * It is used by the Transition objects that implement finite state machines.
 */
public abstract class AbstractRequest {
	
	/**
	 * Execute the request if any of its parameters have changed since the last time it was executed.
	 *
	 * @return true, if successful
	 */
	public abstract boolean execute();
	
	/**
	 * Execute the request if any of its parameters have changed since the last time it was executed or if force is true.
	 *
	 * @param force is true if the request must be executed even if its parameters have not changed since the last time it was executed.
	 * @return true, if the request was executed successfully.
	 */
	public abstract boolean execute(boolean force);
	
	/**
	 * The method to execute if the pop up cancel/no button is clicked - override if required.
	 */
	public abstract void cancel();
}
