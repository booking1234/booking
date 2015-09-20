/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	3.0.8
 */
package net.cbtltd.rest.kigo;

import java.util.Arrays;

public class CalendarsRS extends Response {

	private CalendarRS[] API_REPLY;

	CalendarsRS () {}	//no args constructor
	
	
	public CalendarRS[] getAPI_REPLY() {
		return API_REPLY;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CalendarsRS [API_REPLY=");
		builder.append(Arrays.toString(API_REPLY));
		builder.append("]");
		return builder.toString();
	}
}