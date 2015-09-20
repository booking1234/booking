/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.rest.flipkey;

public class Location{

    private String locationid;
    private String country;
    private String countryname;
    private String code;
    private String name;
    private String subdivision;
    private String subdivisionname;
    private Double latitude;
    private Double longitude;
    private Double altitude;

	public String getLocationid() {
		return locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public boolean noCountry() {
		return country == null || country.isEmpty();
	}
	
	public String getCountryname() {
		return countryname;
	}

	public void setCountryname(String countryname) {
		this.countryname = countryname;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean noName() {
		return name == null || name.trim().isEmpty();
	}
	
	public String getSubdivision() {
		return subdivision;
	}

	public void setSubdivision(String subdivision) {
		this.subdivision = subdivision;
	}

	public boolean noSubdivision() {
		return subdivision == null || subdivision.isEmpty();
	}
	
	public String getSubdivisionname() {
		return subdivisionname;
	}

	public void setSubdivisionname(String subdivisionname) {
		this.subdivisionname = subdivisionname;
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

//	public boolean noCoordinate() {
//		return latitude == null || longitude == null; // || altitude == null;
//	}
//	
//	@Override
//	public String toString(){
//		StringBuilder sb = new StringBuilder();
//		sb.append(" Name ").append((getName() == null)? "null" : getName());
//		sb.append(" Country ").append((getCountry() == null) ? "null" : getCountry());
//		sb.append(" Subdivision ").append((getSubdivision() == null) ? "null" : getSubdivision());
//		sb.append(" Status ").append((getStatus() == null) ? "null" : getStatus());
//		sb.append(" Code ").append((getCode() == null) ? "null" : getCode());
//		sb.append(" Function ").append((getFunction() == null) ? "null" : getFunction());
//		sb.append(" IATA ").append((getIata() == null) ? "null" : getIata().toString());
//		sb.append(super.toString());
//		return sb.toString();
//	}
}