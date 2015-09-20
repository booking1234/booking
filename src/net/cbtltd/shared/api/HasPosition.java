/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.api;

public interface HasPosition {
	void setLocationid(String locationid);
	String getLocationid();
	boolean hasLocationid();
	boolean hasLatLng();
}
