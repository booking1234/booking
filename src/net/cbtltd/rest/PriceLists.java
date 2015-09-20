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

@XmlRootElement(name = "pricelists")
public class PriceLists 
implements HasXsl {
	public String type;
	public Collection<PriceList> pricelist;
	private String message; //NB HAS GET&SET = private
	private String xsl; //NB HAS GET&SET = private

	public PriceLists(){}
	
	public PriceLists(String type, String message, Collection<PriceList> pricelist, String xsl) {
		super();
		this.type = type;
		this.message = message;
		this.pricelist = pricelist;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
