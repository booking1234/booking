/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.api;

import com.google.gwt.user.client.rpc.IsSerializable;

public interface HasNameId
extends IsSerializable, Comparable<HasNameId> {
	String getName();
	void setName(String name);
	boolean noName();
	boolean hasName();
	boolean hasName(String name);
	String getId();
	void setId(String id);
	public boolean noId();
	public boolean hasId();
}
