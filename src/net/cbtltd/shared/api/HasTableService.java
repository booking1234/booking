/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.api;

public interface HasTableService
extends HasTable, HasService {
	String getId();
	void setId(String id);
}
