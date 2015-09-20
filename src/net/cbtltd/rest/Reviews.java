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

@XmlRootElement(name="reviews")
@XStreamAlias("reviews")
public class Reviews implements HasXsl {

	public String type;
	public String id;
	public String message;
	@XStreamImplicit
	public Collection<Review> review;
	private String xsl; //NB HAS GET&SET = private

	public Reviews() {}

	public Reviews(Collection<Review> rating) {
		this.review = rating;
	}

	public Reviews(String type, String id, String message, Collection<Review> review, String xsl) {
		this.type = type;
		this.id = id;
		this.message = message;
		this.review = review;
		this.xsl = xsl;
	}

	public String getXsl() {
		return xsl;
	}

	public void setXsl(String xsl) {
		this.xsl = xsl;
	}
}