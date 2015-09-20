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

@XmlRootElement(name="quotes")
public class Quotes implements HasXsl {

	public String type;
	public String id;
	public String message;
	public Collection<Quote> quote;
	private String xsl; //NB HAS GET&SET = private

	public Quotes() {} //NB HAS NULL ARGUMENT CONSTRUCTOR

	public Quotes(Collection<Quote> quote) {
		this.quote = quote;
	}

	public Quotes(String type, String id, String message, Collection<Quote> quote, String xsl) {
		this.type = type;
		this.id = id;
		this.message = message;
		this.quote = quote;
		this.xsl = xsl;
	}

	public String getXsl() {
		return xsl;
	}

	public void setXsl(String xsl) {
		this.xsl = xsl;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Quotes [type=");
		builder.append(type);
		builder.append(", id=");
		builder.append(id);
		builder.append(", message=");
		builder.append(message);
		builder.append(", quote=");
		builder.append(quote);
		builder.append(", xsl=");
		builder.append(xsl);
		builder.append("]");
		return builder.toString();
	}

}