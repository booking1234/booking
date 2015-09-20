/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import net.cbtltd.shared.License;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.api.HasXsl;

@XStreamAlias("countries")
@XmlRootElement(name="countries")
public class Countries implements HasXsl {
@XStreamImplicit(itemFieldName="item")
	public Collection<NameId> item;
	private String xsl; //NB HAS GET&SET = private
	private String message;

	public Countries() {}

	public Countries(Collection<NameId> item, String message, String xsl) {
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