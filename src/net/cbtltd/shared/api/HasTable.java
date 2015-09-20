/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.api;

public interface HasTable {
	String ORDER_BY_DESC = " desc";
	String ORDER_BY_NAME = "name";
	String getOrderby();
	void setOrderby(String orderby);
	boolean noOrderby();
	int getStartrow();
	void setStartrow(int startrow);
	int getNumrows();
	void setNumrows(int numrows);
}