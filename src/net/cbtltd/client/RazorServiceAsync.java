/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client;


import net.cbtltd.shared.api.HasResponse;
import net.cbtltd.shared.api.HasService;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The Interface RazorServiceAsync enables the GWT RPC service on the client.
 * @see net.cbtltd.client.AbstractRoot
 */
public interface RazorServiceAsync {
	
	/**
	 * Sends a request to the server and handles the response.
	 *
	 * @param <R> the type of the response.
	 * @param service the service used to respond to the request.
	 * @param callback the callback function to handle the response.
	 */
	<R extends HasResponse> void send(HasService service, AsyncCallback<R> callback);
}
