/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.soap.ota.server;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.shared.Time;

/** 
 * The Class OtaSearchParameter is used to get search criteria for OTA messages.
 * @see net.cbtltd.soap.ota.OtaService
 */
public class OtaSearchParameter {

	private int maxresponses;
	private Date arrivaldate;
	private Date departuredate;
	private Double maxrate;
	private Double latitude;
	private Double longitude;
	private Double distance;
	private String distanceunit = "KMT";
	private String currency;
	private String roomtype;
	private String productid;
	private String productname;
	private String ownerid;
	private String supplierid;
	private Integer guestcount;
	private ArrayList<String> otas;
	private ArrayList<String> orotas;

	public int getMaxresponses() {
		return maxresponses;
	}

	public void setMaxresponses(int maxresponses) {
		this.maxresponses = maxresponses;
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

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getDistanceunit() {
		return distanceunit;
	}

	public void setDistanceunit(String distanceunit) {
		this.distanceunit = distanceunit;
	}

	public Double getMaxrate() {
		return maxrate;
	}

	public void setMaxrate(Double maxrate) {
		this.maxrate = maxrate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getRoomtype() {
		return roomtype;
	}

	public void setRoomtype(String roomtype) {
		this.roomtype = roomtype;
	}

	public Integer getGuestcount() {
		return guestcount;
	}

	public void setGuestcount(Integer guestcount) {
		this.guestcount = guestcount;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
	}

	public String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}

	public ArrayList<String> getOtas() {
		return otas;
	}

	public void setOtas(ArrayList<String> otas) {
		this.otas = otas;
	}

	public ArrayList<String> getOrotas() {
		return orotas;
	}

	public void setOrotas(ArrayList<String> orotas) {
		this.orotas = orotas;
	}

	public void setDuration(Double duration) {
		if (duration == null || arrivaldate == null) {
			return;
		}
		departuredate = Time.addDuration(arrivaldate, duration, Time.DAY);
	}

	private Double getAngle() {
		return distance * 0.009; // 360.0/40008.0;
	}

	public boolean noLatLng() {
		return latitude == null || latitude < -90.0 || latitude > 90.0
				|| longitude == null || longitude < -180.0 || longitude > 180.0; // ||
																					// altitude
																					// ==
																					// null;
	}

	public Double getNelatitude() {
		return noLatLng() ? null : latitude + getAngle();
	}

	public Double getNelongitude() {
		return noLatLng() ? null : longitude + getAngle();
	}

	public Double getSwlatitude() {
		return noLatLng() ? null : latitude - getAngle();
	}

	public Double getSwlongitude() {
		return noLatLng() ? null : longitude - getAngle();
	}

	public void addOta(String ota) {
		if (ota == null || ota.isEmpty()) {
			return;
		}
		if (otas == null) {
			otas = new ArrayList<String>();
		}
		otas.add(ota);
	}

	public void addOta(String key, String value) {
		if (key == null || key.isEmpty() || value == null || value.isEmpty()) {
			return;
		}
		if (otas == null) {
			otas = new ArrayList<String>();
		}
		otas.add(key + value);
	}

	public void addOrota(String key, String value) {
		if (key == null || key.isEmpty() || value == null || value.isEmpty()) {
			return;
		}
		if (orotas == null) {
			orotas = new ArrayList<String>();
		}
		orotas.add(key + value);
	}

}
