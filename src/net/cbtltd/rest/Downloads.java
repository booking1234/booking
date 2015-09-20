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

@XmlRootElement(name = "downloads")
public class Downloads
implements HasXsl {
	public String message;
	public Collection<Download> download;
	private String xsl; //NB HAS GET&SET = private

	public Downloads(){}

	public Downloads(String message, Collection<Download> download, String xsl) {
		super();
		this.message = message;
		this.download = download;
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
