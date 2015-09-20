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
@XStreamAlias("items")
@XmlRootElement(name="items")
public class Items implements HasXsl {

	public String entity;
	public String id;
	public String type;
	public String message;
	@XStreamImplicit(itemFieldName="item")
	public Collection<NameId> item;
	private String xsl; //NB HAS GET&SET = private

	public Items() {}

	public Items(String entity, String id, String type, String message, Collection<NameId> item, String xsl) {
		this.entity = entity;
		this.id = id;
		this.type = type;
		this.message = message;
		this.item = item;
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
		builder.append("Items [entity=");
		builder.append(entity);
		builder.append(", id=");
		builder.append(id);
		builder.append(", type=");
		builder.append(type);
		builder.append(", message=");
		builder.append(message);
		builder.append(", item=");
		builder.append(item);
		builder.append(", xsl=");
		builder.append(xsl);
		builder.append("]");
		return builder.toString();
	}
}