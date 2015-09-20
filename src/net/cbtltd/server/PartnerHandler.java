package net.cbtltd.server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.cbtltd.shared.Partner;
import net.cbtltd.shared.api.IsPartner;

public abstract class PartnerHandler {

	private boolean[] stopped = {true, true, true, true, true, true};
	private DateFormat dateformat;
	protected Partner partner;

	public PartnerHandler(Partner partner) {this.partner = partner;}

	/** Starts the API service. */
	public void start(IsPartner.API api) {stopped[api.ordinal()] = false;}

	/** Stops the API service. */
	public void stop(IsPartner.API api) {stopped[api.ordinal()] = true;}

	/**
	 * Gets if the API service is stopped.
	 *
	 * @return true if the API service is stopped.
	 */
	public boolean stopped(IsPartner.API api) {return stopped[api.ordinal()];}

	/**
	 * Gets the api key.
	 *
	 * @return the api key
	 */
	public String getApikey() {return partner.getApikey();}

	/**
	 * Gets the altpartyid.
	 *
	 * @return the altpartyid to identify the organization using the API.
	 */
	public String getAltpartyid() {return partner.getPartyid();}

	/**
	 * Gets the default currency.
	 *
	 * @return the default currency.
	 */
	public String getCurrency() {return partner.getCurrency();}

	/**
	 * Gets the default webaddress.
	 *
	 * @return the default webaddress.
	 */
	public String getWebaddress() {return partner.getWebaddress();}

	/**
	 * Gets the commission.
	 *
	 * @return the default manager's commission percentage.
	 */
	public Double getCommission() {return partner.getCommission();}

	/**
	 * Gets the default discount percentage.
	 *
	 * @return the default agent's discount percentage.
	 */
	public Double getDiscount() {return partner.getDiscount();}

	/**
	 * Gets the alert wait time.
	 *
	 * @return the alert wait time in milliseconds.
	 */
	public Integer getAlertwait() {return partner.getAlertwait();}

	/**
	 * Gets the price wait time.
	 *
	 * @return the price wait time in milliseconds.
	 */
	public Integer getPricewait() {return partner.getPricewait();}

	/**
	 * Gets the product wait time.
	 *
	 * @return the product wait time in milliseconds.
	 */
	public Integer getProductwait() {return partner.getProductwait();}

	/**
	 * Gets the schedule wait time.
	 *
	 * @return the schedule wait time in milliseconds.
	 */
	public Integer getSchedulewait() {return partner.getSchedulewait();}

	/**
	 * Gets the special wait time.
	 *
	 * @return the special wait time in milliseconds.
	 */
	public Integer getSpecialwait() {return partner.getSpecialwait();}

	/**
	 * Gets if reservations are offline.
	 *
	 * @return true if reservations are booked offline.
	 */
	public Boolean getBookoffline() {return partner.getBookoffline();}

	/**
	 * Gets the product rank from the book offline parameter.
	 *
	 * @return -1 if properties are booked offline, otherwise 0.
	 */
	public Double getRank() {return partner.getBookoffline() ? -1.0 : 0.0;}

//	/**
//	 * Offline reservation.
//	 *
//	 * @param sqlSession the current SQL session.
//	 * @param reservation the reservation to be requested.
//	 */
//	public void offlineReservation(SqlSession sqlSession, Reservation reservation) {
//		EmailService.offlineReservation(sqlSession, reservation, partner.getBookemailaddress(), partner.getBookwebaddress());
//	}

	/* (non-Javadoc)
	 * @see net.cbtltd.shared.api.IsForeign#DF()
	 */
	/**
	 * Format.
	 *
	 * @param date the date
	 * @return the string
	 */
	public String format (Date date) {
		if (date == null) {return null;}
		if (dateformat == null){ dateformat = new SimpleDateFormat(partner.getDateformat());}
		return dateformat.format(date);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.shared.api.IsForeign#DF()
	 */
	/**
	 * Parses the.
	 *
	 * @param date the date
	 * @return the date
	 * @throws Throwable the throwable
	 */
	public Date parse (String date) throws Throwable {
		if (date == null) {return null;}
		if (dateformat == null){ dateformat = new SimpleDateFormat(partner.getDateformat());}
		return dateformat.parse(date);
	}
	
}
