/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.api;

import com.google.gwt.user.client.rpc.IsSerializable;

import net.cbtltd.shared.Service;


public interface HasService extends IsSerializable { //Interface for GWT RPC request
	Service service();
}
