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
@XStreamAlias("pricelist")
@XmlRootElement(name = "pricelist")
public class PriceList
implements HasXsl {
	public String entity;
	public String id;
	@XStreamImplicit(itemFieldName="prices")
	public Collection<Prices> prices;
	private String message; //NB HAS GET&SET = private
	private String xsl; //NB HAS GET&SET = private
	
	public PriceList() {}

	public PriceList(Collection<Prices> prices) {
		super();
		this.prices = prices;
	}
	
	public PriceList(String entity, String id, Collection<Prices> prices) {
		super();
		this.entity = entity;
		this.id = id;
		this.prices = prices;
	}

	public PriceList(String entity, String id, String message, Collection<Prices> prices, String xsl) {
		super();
		this.entity = entity;
		this.id = id;
		this.message = message;
		this.prices = prices;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PriceList [entity=");
		builder.append(entity);
		builder.append(", id=");
		builder.append(id);
		builder.append(", prices=");
		builder.append(prices);
		builder.append(", message=");
		builder.append(message);
		builder.append(", xsl=");
		builder.append(xsl);
		builder.append("]");
		return builder.toString();
	}
}
