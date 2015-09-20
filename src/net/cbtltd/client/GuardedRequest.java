/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client;

import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.MessagePanel;
import net.cbtltd.client.field.MessagePopup;
import net.cbtltd.client.panel.LoadingPopup;
import net.cbtltd.shared.api.HasResponse;
import net.cbtltd.shared.api.HasService;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The Class GuardedRequest is the primary means by which to send asynchronous requests to the server
 * and to handle both successful and failed responses. The class checks that there are no errors before
 * the request is sent (the guard). It also checks if the request has changed since it was last sent and,
 * if not, does not send it unless the action is mandatory. It furthermore delays the request for a short
 * time to ensure that multiple copies of the same request are not send within that period.
 * The class displays a small loading icon when the request is sent and hides it when the response is received.
 * Finally, the class parameter is the type of the particular data transfer object (action) used to communicate 
 * between client and server. Note that this action request type must implement the HasService interface, 
 * and the response must implement the parameter type. 
 *
 * @param <R> the response type which must extend HasResponse.
 */
public abstract class GuardedRequest<R extends HasResponse>
extends AbstractRequest
implements HasRequest<R> {
	private static boolean sending = false;
	private int changed = 0;
	private boolean popped = false;
	private R response;
	
	/**
	 * Checks if the action has any error(s).
	 *
	 * @return true, if the action has any error(s).
	 */
	protected abstract boolean error();
	
	/** Sends the action object to the server. */
	protected abstract void send();
	
	/**
	 * Receives the response from the server.
	 *
	 * @param response the response from the server.
	 */
	protected abstract void receive(R response);
	private HasService delayedaction;
	
	private final Timer delaySend = new Timer() { // Timer used to consolidate multiple requests 
		@Override
		public void run() {
			changed = delayedaction.toString().hashCode();
			sending = true;
			LoadingPopup.getInstance().center();
			AbstractRoot.getService().send(delayedaction, new AsyncCallback<R>() {

				public void onFailure(Throwable caught) {
					LoadingPopup.getInstance().hide();
					new MessagePanel(Level.ERROR, AbstractField.CONSTANTS.errRequest() + caught.getMessage()).center();
					sending = false;
				} 

				public void onSuccess(R response) {
					LoadingPopup.getInstance().hide();
					GuardedRequest.this.response = response;
					receive(response);
					sending = false;
				}
			});
		}
	};

	/** Sends a delayed action. */
	protected void sendaction() {send(delayedaction);}

	/**
	 * Sends the specified action to the server.
	 *
	 * @param action the specified action.
	 */
	protected void send(HasService action) {
		//Window.alert("GuardedRequest send " + changed + " " + action.toString().hashCode());
		if (action == null || action.toString().hashCode() == changed) {receive(response);}
		else {
			delayedaction = action;
			delaySend.cancel();
			delaySend.schedule(100);
		}
	}

	/**
	 * Pops up a message if not null - override if required.
	 *
	 * @return the text of the pop up message.
	 */
	protected String popup() {return null;} //invoke popup if not null
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.AbstractRequest#cancel()
	 */
	public void cancel() {}	//
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.AbstractRequest#execute(boolean)
	 */
	public boolean execute(boolean refresh) {
		changed = 0;
		return execute();
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.AbstractRequest#execute()
	 */
	public boolean execute() {
		if (!popped && popup() != null) {
			popped = true;
			MessagePopup.getInstance().showYesNo(popup(), this);
			return true;
		}
		popped = false;
		
		if (error()) {return false;}
		send();
		return true;
	}

	/**
	 * Checks if the specified action has any error(s) and sends it to the server if it does not.
	 *
	 * @param action the specified action to be send to the server.
	 * @return true, if the specified action has no error(s).
	 */
	public boolean execute(HasService action) {
		if (error()) {return false;}
		else {
			send(action);
			return true;
		}
	}

	public static boolean isSending() {
		return sending;
	}
}
