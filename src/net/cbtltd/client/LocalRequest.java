/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client;

import net.cbtltd.client.field.MessagePopup;


// TODO: Auto-generated Javadoc
/**
 * The Class LocalRequest is the parent of requests that are local to the client.
 */
public abstract class LocalRequest
extends AbstractRequest {
	
	private boolean popped = false;

	/**
	 * Checks if the request any has error(s).
	 *
	 * @return true, if the request has error(s) - override as required.
	 */
	protected boolean error () {return false;}
	
	/** Performs the request. */
	protected abstract void perform();
	
	/**
	 * Pops up a message if not null.
	 *
	 * @return the text in the message.
	 */
	protected String popup() {return null;}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.AbstractRequest#cancel()
	 */
	public void cancel() {}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.AbstractRequest#execute(boolean)
	 */
	public boolean execute(boolean force) {
		return execute();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.AbstractRequest#execute()
	 */
	public boolean execute() {
		if (!popped && popup() != null) {
			popped = true;
			MessagePopup.getInstance().showYesNo(popup(), this);
			return false;
		}
		popped = false;
		
		if (error()) {return false;}
		else {
			perform();
			return true;
		}
	}
}
