/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	3.0.8
 */
package net.cbtltd.rest.kigo;

import java.util.Arrays;

public class BedTypeRS extends Response {

	private BedType[] API_REPLY;

	BedTypeRS () {}	//no args constructor
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BedTypeRS [API_REPLY=");
		builder.append(Arrays.toString(API_REPLY));
		builder.append("]");
		return builder.toString();
	}
}