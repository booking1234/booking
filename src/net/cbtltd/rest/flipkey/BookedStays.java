/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.flipkey;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;

//@XmlSeeAlso({net.cbtltd.rest.flipkey.BookedStay.class}) //VIP to load into context
public class BookedStays {
	@XmlAttribute (name = "property_id")
	public String Property_id;
	public Collection<BookedStay> BookedStay;

	public BookedStays() {}

	public BookedStays(String property_id, Collection<BookedStay> bookedstay) {
		super();
		this.Property_id = property_id;
		this.BookedStay = bookedstay;
	}
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("\nproperty_id ").append(Property_id);
		sb.append(" bookedstay ").append(BookedStay);
		return sb.toString();
	}
}
