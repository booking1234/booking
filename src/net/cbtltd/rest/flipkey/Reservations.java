/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.flipkey;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

//@XmlSeeAlso({net.cbtltd.rest.flipkey.Reservation.class}) //VIP to load into context
@XmlRootElement(name="Reservations")
public class Reservations {
//implements HasXsl {
	@XStreamOmitField

	public Collection<Reservation> Reservation;
	private String xsl;

	public Reservations(){}
	
	public Reservations(Collection<Reservation> reservation, String xsl) {
		super();
		this.Reservation = reservation;
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
}
