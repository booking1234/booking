/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	3.0.8
 */
package net.cbtltd.rest.kigo;

import java.util.Arrays;

public class PropertiesRS extends Response {

	private Property[] API_REPLY;

	PropertiesRS () {}	//no args constructor
	
	
	public Property[] getAPI_REPLY() {
		return API_REPLY;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PropertyRS [API_REPLY=");
		builder.append(Arrays.toString(API_REPLY));
		builder.append("]");
		return builder.toString();
	}
}