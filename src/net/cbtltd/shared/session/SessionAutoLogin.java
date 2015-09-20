/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.session;

import net.cbtltd.shared.Session;

public class SessionAutoLogin extends Session {

//	private String pos; //point of sale code

	protected SessionAutoLogin() {}
	
	public SessionAutoLogin(String id) {
		this.id = id;
	}
}
