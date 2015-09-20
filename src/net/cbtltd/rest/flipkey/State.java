/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.flipkey;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class State {
	@XmlAttribute (name = "state_code")
	public String state_code;
	@XmlValue
	public String State;
	
	State(){}
	
	State(String state_code, String state){
		this.state_code = state_code;
		this.State = state;
	}

}
