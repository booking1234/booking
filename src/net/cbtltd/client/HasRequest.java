/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client;

import net.cbtltd.shared.api.HasResponse;

/**
 * The Interface HasRequest is implemented by requests sent to the server.
 *
 * @param <R> the type of the action sent to the server.
 */
public interface HasRequest<R extends HasResponse> {
	abstract boolean execute();
}