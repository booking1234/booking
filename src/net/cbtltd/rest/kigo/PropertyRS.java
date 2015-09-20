/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	3.0.8
 */
package net.cbtltd.rest.kigo;

public class PropertyRS extends Response {
	private Property API_REPLY;

	public PropertyRS () {} // no arg constructor
	
	public Property getAPI_REPLY() {
		return API_REPLY;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PropertyResponse [API_REPLY=");
		builder.append(API_REPLY);
		builder.append("]");
		return builder.toString();
	}
}