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

@XmlRootElement(name="units")
public class Units implements HasXsl {
	
	public Collection<NameId> item;
	private String xsl; //NB HAS GET&SET = private

	public Units() {}

	public Units(Collection<NameId> item, String xsl) {
		this.item = item;
		this.xsl = xsl;
	}
	
	public String getXsl() {
		return xsl;
	}

	public void setXsl(String xsl) {
		this.xsl = xsl;
	}
}
