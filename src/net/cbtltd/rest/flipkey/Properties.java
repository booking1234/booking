/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.flipkey;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.cbtltd.shared.api.HasXsl;

@XmlSeeAlso({net.cbtltd.rest.flipkey.Updated.class}) //VIP to load into context
@XmlRootElement(name="PropertySummary")
public class Properties
	implements HasXsl {
	public Collection<net.cbtltd.rest.flipkey.Updated> Property;
	private String xsl;

	public Properties() {}

	public Properties(Collection<net.cbtltd.rest.flipkey.Updated> Property, String xsl) {
		super();
		this.Property = Property;
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
