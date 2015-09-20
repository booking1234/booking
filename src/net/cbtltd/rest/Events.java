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

@XmlRootElement(name = "events")
public class Events
implements HasXsl {
	public String message;
	public Collection<Event> event;
	private String xsl; //NB HAS GET&SET = private

	public Events(){} //NB HAS NULL ARGUMENT CONSTRUCTOR

	public Events(String message, Collection<Event> event, String xsl) {
		super();
		this.message = message;
		this.event = event;
		this.xsl = xsl;
	}

	//---------------------------------------------------------------------------------
	// Implements HasXsl interface
	//---------------------------------------------------------------------------------
	public String getXsl() {
		return xsl;
	}

	public void setXsl(String xsl) {
		this.xsl = xsl;
	}

}
