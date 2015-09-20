/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.shared.License;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.api.HasXsl;

@XmlRootElement(name="subdivisions")
public class Subdivisions implements HasXsl {

	public String country;
	public Collection<NameId> item;
	private String message;
	private String xsl;  //NB HAS GET&SET = private

	public Subdivisions() {}

	public Subdivisions(String country, Collection<NameId> item, String message, String xsl) {
		this.country = country;
		this.item = item;
		this.message = message;
		this.xsl = xsl;
	}	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getXsl() {
		return xsl;
	}

	public void setXsl(String xsl) {
		this.xsl = xsl;
	}
}