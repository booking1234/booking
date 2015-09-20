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
@XStreamAlias("attributes")
@XmlRootElement(name="attributes")
public class Attributes implements HasXsl {

	public String type;
	public String id;
	@XStreamImplicit
	public Collection<Attribute> attribute;
	private String message;
	private String xsl; //NB HAS GET&SET = private

	public Attributes() {}

	public Attributes(Collection<Attribute> attribute) {
		this.attribute = attribute;
	}

	public Attributes(String type, String id, String message, Collection<Attribute> attribute, String xsl) {
		this.type = type;
		this.id = id;
		this.message = message;
		this.attribute = attribute;
		this.xsl = xsl;
	}

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Attributes [type=");
		builder.append(type);
		builder.append(", id=");
		builder.append(id);
		builder.append(", attribute=");
		builder.append(attribute);
		builder.append(", message=");
		builder.append(message);
		builder.append(", xsl=");
		builder.append(xsl);
		builder.append("]");
		return builder.toString();
	}
}