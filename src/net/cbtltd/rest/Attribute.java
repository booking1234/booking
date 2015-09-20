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

@XStreamAlias("attribute")
public class Attribute {
	@XStreamImplicit
	public static final String[] ACCOMMODATION_SEARCH = net.cbtltd.shared.Attribute.ACCOMMODATION_SEARCH;
	public String key;
	public Values values;
	
	public Attribute() {}
	
	public Attribute(String key, Collection<String> values) {
		super();
		this.key = key;
		this.values = new Values(values);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Attribute [key=");
		builder.append(key);
		builder.append(", values=");
		builder.append(values);
		builder.append("]");
		return builder.toString();
	}
}
