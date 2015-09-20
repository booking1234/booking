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

@XmlRootElement(name = "provisional")
@Description(value = "Tentative or provisional reservation", target = DocTarget.RESPONSE)
public class Provisional implements HasXsl {
	
	private String reservationid;
	private String reservationname;
	private String productid;
	private String productname;
	private String arrive;
	private String depart;
	private String emailaddress;
	private String familyname;
	private String firstname;
	private String rack;
	private String quote;
	private String sto;
	private String deposit;
	private String currency;
	private String alert;
	private String notes;
	private String terms;
	private String message;
	private String xsl;
	
	public Provisional() {}
	
	public Provisional(String message) {
		super();
		this.message = message;
	}

	public Provisional(
			String reservationid, 
			String reservationname, 
			String productid, 
			String productname, 
			String arrive,
			String depart, 
			String emailaddress, 
			String familyname,
			String firstname, 
			String rack, 
			String quote, 
			String sto, 
			String deposit,
			String currency, 
			String alert, 
			String notes, 
			String terms, 
			String message, 
			String xsl) {
		super();
		this.reservationid = reservationid;
		this.reservationname = reservationname;
		this.productid = productid;
		this.productname = productname;
		this.arrive = arrive;
		this.depart = depart;
		this.emailaddress = emailaddress;
		this.familyname = familyname;
		this.firstname = firstname;
		this.rack = rack;
		this.quote = quote;
		this.sto = sto;
		this.deposit = deposit;
		this.currency = currency;
		this.alert = alert;
		this.notes = notes;
		this.terms = terms;
		this.message = message;
		this.xsl = xsl;
	}

	public Provisional(
			String productid, 
			String productname, 
			String arrive,
			String depart,
			String quote, 
			String currency, 
			String alert, 
			String message, 
			String xsl) {
		super();
		this.productid = productid;
		this.productname = productname;
		this.arrive = arrive;
		this.depart = depart;
		this.quote = quote;
		this.currency = currency;
		this.alert = alert;
		this.message = message;
		this.xsl = xsl;
	}

	@Description(value = "The reservation ID that is globally unique", target = DocTarget.METHOD)
	public String getReservationid() {
		return reservationid;
	}

	public void setReservationid(String reservationid) {
		this.reservationid = reservationid;
	}

	@Description(value = "The reservation number that is unique to the manager but not globally unique", target = DocTarget.METHOD)
	public String getReservationname() {
		return reservationname;
	}

	public void setReservationname(String reservationname) {
		this.reservationname = reservationname;
	}

	@Description(value = "ID of the reserved property", target = DocTarget.METHOD)
	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	@Description(value = "Name of the reserved property", target = DocTarget.METHOD)
	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	@Description(value = "Check in date of the reservation", target = DocTarget.METHOD)
	public String getCheckin() {
		return arrive;
	}

	public void setCheckin(String arrive) {
		this.arrive = arrive;
	}

	@Description(value = "Arrival date of the reservation", target = DocTarget.METHOD)
	public String getArrive() {
		return arrive;
	}

	public void setArrive(String arrive) {
		this.arrive = arrive;
	}

	@Description(value = "Check out date of the reservation", target = DocTarget.METHOD)
	public String getCheckout() {
		return depart;
	}
	
	public void setCheckout(String depart) {
		this.depart = depart;
	}

	@Description(value = "Departure date of the reservation", target = DocTarget.METHOD)
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

	@Description(value = "Rack rate or list price of the reservation", target = DocTarget.METHOD)
	public String getRack() {
		return rack;
	}

	public void setRack(String rack) {
		this.rack = rack;
	}

	@Description(value = "Best available rate for the reservation", target = DocTarget.METHOD)
	public String getBar() {
		return quote;
	}

	@Description(value = "Quoted price of the reservation", target = DocTarget.METHOD)
	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}

	@Description(value = "Standard tourism operator rate for the reservation", target = DocTarget.METHOD)
	public String getSto() {
		return sto;
	}

	public void setSto(String sto) {
		this.sto = sto;
	}

	@Description(value = "Deposit required to confirm a reservation", target = DocTarget.METHOD)
	public String getDeposit() {
		return deposit;
	}

	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	@Description(value = "Currency code of the prices", target = DocTarget.METHOD)
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Description(value = "Alert(s) for the period of the quote", target = DocTarget.METHOD)
	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	@Description(value = "Reservation notes or comments", target = DocTarget.METHOD)
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Description(value = "Terms and conditions which apply", target = DocTarget.METHOD)
	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	@Description(value = "Diagnostic message in the response", target = DocTarget.METHOD)
	public String getMessage() {
		return message;
	}

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
