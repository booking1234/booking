/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.api;

public interface HasState {
//	String INITIAL = "Initial";
//	String FINAL = "Final";
	void setState(String state);
	String getState();	
}