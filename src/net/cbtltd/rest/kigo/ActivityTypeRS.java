/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	3.0.8
 */
package net.cbtltd.rest.kigo;


public class ActivityTypeRS extends Response {

	private ActivityType API_REPLY;

	ActivityTypeRS () {}	//no args constructor
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ActivityTypeRS [API_REPLY=");
		builder.append(API_REPLY);
		builder.append("]");
		return builder.toString();
	}
}