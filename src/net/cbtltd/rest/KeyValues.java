/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.shared.KeyValue;
import net.cbtltd.shared.api.HasXsl;

@XmlRootElement(name="keyvalues")
public class KeyValues implements HasXsl {

	public String entity;
	public String id;
	public String type;
	public String message;
	public Collection<KeyValue> keyvalue;
	private String xsl; //NB HAS GET&SET = private

	public KeyValues() {}

	public KeyValues(String entity, String id, String type, String message, Collection<KeyValue> keyvalue, String xsl) {
		this.entity = entity;
		this.id = id;
		this.type = type;
		this.message = message;
		this.keyvalue = keyvalue;
		this.xsl = xsl;
	}

	public String getXsl() {
		return xsl;
	}

	public void setXsl(String xsl) {
		this.xsl = xsl;
	}
}