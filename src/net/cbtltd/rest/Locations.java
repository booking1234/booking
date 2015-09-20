/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.shared.License;
import net.cbtltd.shared.api.HasXsl;

@XmlRootElement(name="locations")
public class Locations implements HasXsl {

	public String type;
	public String message;
	public Collection<Location> locations;
	private String xsl; //NB HAS GET&SET = private

	public Locations() {}

	public Locations(String type, String message, Collection<Location> locations, String xsl) {
		super();
		this.type = type;
		this.message = message;
		this.locations = locations;
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