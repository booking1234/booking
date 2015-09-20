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
import net.cbtltd.shared.api.HasXsl;

@XStreamAlias("ratings")
@XmlRootElement(name="ratings")
public class Ratings implements HasXsl {

	public String type;
	public String id;
	public String message;
	
	@XStreamImplicit(itemFieldName="rating")
	public Collection<Rating> rating;
	private String xsl; //NB HAS GET&SET = private

	public Ratings() {}

	public Ratings(Collection<Rating> rating) {
		this.rating = rating;
	}

	public Ratings(String type, String id, String message, Collection<Rating> rating, String xsl) {
		this.type = type;
		this.id = id;
		this.message = message;
		this.rating = rating;
		this.xsl = xsl;
	}

	public String getXsl() {
		return xsl;
	}

	public void setXsl(String xsl) {
		this.xsl = xsl;
	}
}