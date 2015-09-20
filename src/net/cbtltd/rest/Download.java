/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest;

import javax.xml.bind.annotation.XmlRootElement;

import net.cbtltd.shared.License;
import net.cbtltd.shared.api.HasXsl;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

@XmlRootElement(name = "transfer")
public class Download implements HasXsl {

	private String reservationid;
	private String foreignid;
	private String productid;
	private String arrive;
	private String depart;
	private String emailaddress;
	private String familyname;
	private String firstname;
	private String guestname;
	private String agentname;
	private String notes;
	private String price;
	private String currency;
	private String message;
	private String xsl;
	
	public Download() {}
	
	public Download(String message) {
		super();
		this.message = message;
	}

	public Download(String reservationid, String foreignid, String productid, String arrive,
			String depart, String emailaddress, String familyname,
			String firstname, String guestname, String agentname, String notes, String price, String currency, String message,
			String xsl) {
		super();
		this.reservationid = reservationid;
		this.foreignid = foreignid;
		this.productid = productid;
		this.arrive = arrive;
		this.depart = depart;
		this.emailaddress = emailaddress;
		this.familyname = familyname;
		this.firstname = firstname;
		this.guestname = guestname;
		this.agentname = agentname;
		this.notes = notes;
		this.price = price;
		this.currency = currency;
		this.message = message;
		this.xsl = xsl;
	}

	@Description(value = "Reservation identity in Razor", target = DocTarget.METHOD)
	public String getReservationid() {
		return reservationid;
	}

	public void setReservationid(String reservationid) {
		this.reservationid = reservationid;
	}

	@Description(value = "Reservation identity in originating PMS or application", target = DocTarget.METHOD)
	public String getForeignid() {
		return foreignid;
	}

	public void setForeignid(String foreignid) {
		this.foreignid = foreignid;
	}

	@Description(value = "Product or property identity", target = DocTarget.METHOD)
	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	@Description(value = "Arrival or check in date", target = DocTarget.METHOD)
	public String getArrive() {
		return arrive;
	}

	public void setArrive(String arrive) {
		this.arrive = arrive;
	}

	@Description(value = "Departure or check out date", target = DocTarget.METHOD)
	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	@Description(value = "Email address of primary guest of the reservation", target = DocTarget.METHOD)
	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	@Description(value = "Last name of primary guest of the reservation", target = DocTarget.METHOD)
	public String getLastname() {
		return familyname;
	}

	@Description(value = "Family name of primary guest of the reservation", target = DocTarget.METHOD)
	public String getFamilyname() {
		return familyname;
	}

	public void setFamilyname(String familyname) {
		this.familyname = familyname;
	}

	@Description(value = "First name of primary guest of the reservation", target = DocTarget.METHOD)
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Description(value = "Name primary guest of the reservation", target = DocTarget.METHOD)
	public String getGuestname() {
		return guestname;
	}

	public void setGuestname(String guestname) {
		this.guestname = guestname;
	}

	@Description(value = "Name of the agency which made the reservation", target = DocTarget.METHOD)
	public String getAgentname() {
		return agentname;
	}

	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}

	@Description(value = "Notes, comments and requests for the reservation", target = DocTarget.METHOD)
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Description(value = "Quoted price of the reservation", target = DocTarget.METHOD)
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Description(value = "Currency code of the prices", target = DocTarget.METHOD)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getMessage() {
		return message;
	}

	@Description(value = "Diagnostic message in the response", target = DocTarget.METHOD)
	public void setMessage(String message) {
		this.message = message;
	}

	public String getXsl() {
		return xsl;
	}

	public void setXsl(String xsl) {
		this.xsl = xsl;
	}
}
