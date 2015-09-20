/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.flipkey;

import javax.xml.bind.annotation.XmlAttribute;

public class Status {
	@XmlAttribute (name = "value")
	public String value;
	
	Status(){}
	
	Status(String value){
		this.value = value;
	}
}
