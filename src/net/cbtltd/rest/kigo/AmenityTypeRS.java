/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	3.0.8
 */
package net.cbtltd.rest.kigo;


public class AmenityTypeRS extends Response {

	private AmenityType API_REPLY;

	AmenityTypeRS () {}	//no args constructor
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AmenityTypeRS [API_REPLY=");
		builder.append(API_REPLY);
		builder.append("]");
		return builder.toString();
	}
}