/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.soap.ota.server;

import java.util.Date;

import net.cbtltd.shared.Time;

/** 
 * The Class OtaRoomStay is used to get room stay data from the database for OTA messages.
 * @see net.cbtltd.soap.ota.OtaService
 * @see net.cbtltd.server.api.ReservationMapper
 */
public class OtaRoomStay {

	private String productid;
	private String hotelname;
	private String hotelgroup;
	private String dayphone;
	private String faxphone;
	private String mobilephone;
	private String roomtype;
	private String postalcode;
	private String locationid;
	private String city;
	private String countrycode;
	private String countryname;
	private String statecode;
	private String statename;
	private Double latitude;
	private Double longitude;
	private Double altitude;
	private Double value;
	private Double minimum;
	private String unit;
	private String currency;
	private Date date;
	private Date todate;

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getHotelname() {
		return hotelname;
	}

	public void setHotelname(String hotelname) {
		this.hotelname = hotelname;
	}

	public String getHotelgroup() {
		return hotelgroup;
	}

	public void setHotelgroup(String hotelgroup) {
		this.hotelgroup = hotelgroup;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getLocationid() {
		return locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	public String getDayphone() {
		return dayphone;
	}

	public void setDayphone(String dayphone) {
		this.dayphone = dayphone;
	}

	public String getFaxphone() {
		return faxphone;
	}

	public void setFaxphone(String faxphone) {
		this.faxphone = faxphone;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getRoomtype() {
		return roomtype;
	}

	public void setRoomtype(String roomtype) {
		this.roomtype = roomtype;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountrycode() {
		return countrycode;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	public String getCountryname() {
		return countryname;
	}

	public void setCountryname(String countryname) {
		this.countryname = countryname;
	}

	public String getStatecode() {
		return statecode;
	}

	public void setStatecode(String statecode) {
		this.statecode = statecode;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
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

	public Double getAltitude() {
		return altitude;
	}

	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}

	public Double getValue() {
		return value == null ? 0.0 : value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Double getMinimum() {
		return minimum == null ? 0.0 : minimum;
	}

	public void setMinimum(Double minimum) {
		this.minimum = minimum;
	}

	public Double getTotal() {
		return Math.max(getValue() * getDuration(), getMinimum());
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getTodate() {
		return todate;
	}

	public void setTodate(Date todate) {
		this.todate = todate;
	}

	public Double getDuration() {
		if (date == null || todate == null || !todate.after(date)) {return 1.0;}
		else {return Time.getDuration(date, todate, Time.DAY);}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OtaRoomStay [productid=");
		builder.append(productid);
		builder.append(", hotelname=");
		builder.append(hotelname);
		builder.append(", hotelgroup=");
		builder.append(hotelgroup);
		builder.append(", dayphone=");
		builder.append(dayphone);
		builder.append(", faxphone=");
		builder.append(faxphone);
		builder.append(", mobilephone=");
		builder.append(mobilephone);
		builder.append(", roomtype=");
		builder.append(roomtype);
		builder.append(", postalcode=");
		builder.append(postalcode);
		builder.append(", locationid=");
		builder.append(locationid);
		builder.append(", city=");
		builder.append(city);
		builder.append(", countrycode=");
		builder.append(countrycode);
		builder.append(", countryname=");
		builder.append(countryname);
		builder.append(", statecode=");
		builder.append(statecode);
		builder.append(", statename=");
		builder.append(statename);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", altitude=");
		builder.append(altitude);
		builder.append(", value=");
		builder.append(value);
		builder.append(", minimum=");
		builder.append(minimum);
		builder.append(", unit=");
		builder.append(unit);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", date=");
		builder.append(date);
		builder.append(", todate=");
		builder.append(todate);
		builder.append("]");
		return builder.toString();
	}
}
