/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.flipkey;

import java.util.Collection;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.cbtltd.shared.api.HasXsl;

@XmlSeeAlso({net.cbtltd.rest.flipkey.History.class}) //VIP to load int context
@XmlRootElement(name="items")
public class HistoryList 
implements HasXsl {
	public String title;
	public String license;
	public String manager;
	public Date date;
	public Collection<net.cbtltd.rest.flipkey.History> item;
	private String xsl; //NB HAS GET&SET = private

	public HistoryList() {}

	public HistoryList(String title, String license, String manager, Date date, Collection<net.cbtltd.rest.flipkey.History> item, String xsl) {
		super();
		this.title = title;
		this.license = license;
		this.manager = manager;
		this.date = date;
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
