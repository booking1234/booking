/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	3.0.8
 */
package net.cbtltd.rest.kigo;

public class PhotoRS extends Response {
	
	private String API_REPLY;

	public PhotoRS () {} // no arg constructor
	
	public String getAPI_REPLY() {
		return API_REPLY;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PhotoRS [API_REPLY LENGTH=");
		builder.append(API_REPLY.length());
		builder.append("]");
		return builder.toString();
	}
}