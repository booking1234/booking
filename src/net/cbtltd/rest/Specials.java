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

@XmlRootElement(name = "specials")
public class Specials
implements HasXsl {
	public Collection<Quote> quote;
	public String message;
	private String xsl; //NB HAS GET&SET = private

	public Specials(){}

	public Specials(Collection<Quote> quote, String message, String xsl) {
		super();
		this.quote = quote;
		this.message = message;
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
