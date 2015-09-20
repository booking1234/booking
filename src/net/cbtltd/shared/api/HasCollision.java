/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.api;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.shared.NameId;

public interface HasCollision {
	String getId();
	String getProductid();
	Date getFromdate();
	Date getTodate();
	Integer getQuantity();
	void addCollisions(ArrayList<NameId> collisions);
	void setCollisions(ArrayList<NameId> collisions);
	ArrayList<NameId> getCollisions();
}
