/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.flipkey;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.shared.api.HasXsl;

//@XmlSeeAlso({net.cbtltd.rest.flipkey.BookedStays.class}) //VIP to load into context
@XmlRootElement(name="Availability")
public class Availability
implements HasXsl {
	public Collection<BookedStays> BookedStays;
	private String xsl;

	public Availability() {}
	
	public Availability(Collection<BookedStays> bookedstays, String xsl) {
		super();
		this.BookedStays = bookedstays;
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
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("\nbookedstays ").append(BookedStays);
		sb.append(" xsl ").append(xsl);
		return sb.toString();
	}

}
