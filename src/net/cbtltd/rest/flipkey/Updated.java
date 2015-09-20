/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.flipkey;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

public class Updated {
	@XmlAttribute (name = "property_id")
	public String pi;
	@XmlAttribute (name = "last_update")
	public Date lu;

	public Updated() {}

//	public Updated(String property_id, Date last_update) {
//		super();
//		this.property_id = property_id;
//		this.last_update = last_update;
//	}
//
	@XmlTransient
	public String getProperty_id() {
		return pi;
	}

	public void setProperty_id(String property_id) {
		this.pi = property_id;
	}

	@XmlTransient
	public Date getLast_update() {
		return lu;
	}

	public void setLast_update(Date last_update) {
		this.lu = last_update;
	}
}
