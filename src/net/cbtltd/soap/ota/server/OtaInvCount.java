/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.soap.ota.server;

import java.util.Date;

/** 
 * The Class OtaInvCount may be used to get inventory count data from the database for OTA messages.
 * @see net.cbtltd.soap.ota.OtaService
 * @see net.cbtltd.server.api.ReservationMapper
 */
public class OtaInvCount {
	private String productid;
	private Date arrivaldate;
	private Date departuredate;

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public Date getArrivaldate() {
		return arrivaldate;
	}

	public void setArrivaldate(Date arrivaldate) {
		this.arrivaldate = arrivaldate;
	}

	public Date getDeparturedate() {
		return departuredate;
	}

	public void setDeparturedate(Date departuredate) {
		this.departuredate = departuredate;
	}
}
