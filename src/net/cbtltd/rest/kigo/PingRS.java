/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	3.0.8
 */
package net.cbtltd.rest.kigo;

public class PingRS extends Response {

	private String API_REPLY;

	PingRS () {}	//no args constructor
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append(", API_REPLY=");
		builder.append(API_REPLY);
		builder.append("]");
		return builder.toString();
	}
}