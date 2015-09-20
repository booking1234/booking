/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client;

import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.MessagePanel;
import net.cbtltd.client.panel.LoadingPopup;
import net.cbtltd.shared.api.HasResponse;
import net.cbtltd.shared.api.HasService;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The Class RemoteRequest is a means by which to send asynchronous requests to the server
 * and to handle both successful and failed responses. Consider using GuardedRequest in preference to this class.
 * @see net.cbtltd.client.GuardedRequest
 * 
 * @param <P> the action type sent to the server.
 * @param <R> the response type received from the server.
 */
public abstract class RemoteRequest<P, R extends HasResponse>
extends AbstractRequest
implements HasRequest<R> {
	
	/**
	 * Receives the response to the request from the server.
	 *
	 * @param response the response from the server.
	 */
	protected abstract void receive(R response);

	/**
	 * Sends a request to the server.
	 *
	 * @param action the action snet to the server.
	 */
	protected void send(HasService action)	{
		LoadingPopup.getInstance().center();
		AbstractRoot.getService().send(action, new AsyncCallback<R>() {
			
			public void onFailure(Throwable caught) {
				LoadingPopup.getInstance().hide();
				new MessagePanel(Level.ERROR, caught.getMessage()).center();
			} 

			public void onSuccess(R response) {
				LoadingPopup.getInstance().hide();
				receive(response);
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.AbstractRequest#cancel()
	 */
	public void cancel() {} // never used

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
		new MessagePanel(Level.ERROR, "Cannot use null argument execute").center();
		return false;
	}
	
	/**
	 * Executes the action.
	 *
	 * @param action the action to be sent to the server.
	 */
	public abstract void execute(P action);
}
