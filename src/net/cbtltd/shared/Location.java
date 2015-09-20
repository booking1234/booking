/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @see		ISO standards at http://www.commondatahub.com/state_source.jsp
 * @version	4.0.0
 */
package net.cbtltd.shared;

import javax.print.attribute.standard.MediaSize.ISO;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gwt.maps.client.base.LatLng;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
@XStreamAlias( value = "location")
public class Location extends Model {

	public class GeoLocation {

	}

	public static final String INITIAL = "Initial";
	public static final String FINAL = "Final";
	public static final String CREATED = "Created";
	public static final String CONFLICT = "Conflict";
	public static final String SUSPENDED = "Suspended";
	public static final String DEPRECATED = "Deprecated";
	public static final String[] STATES = { INITIAL, CREATED, SUSPENDED, DEPRECATED, FINAL };

	private static final double getRandom(double min, double max) {
		return min + (Math.random() * (max - min));
	}

	public Location () {}
	
	public Location(int id, String code, String name, String country, String region, double latitude, double longitude) {
		super();
		this.id = String.valueOf(id);
		this.code = code;
		this.name = name;
		this.country = country;
		this.region = region;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Service service() {return Service.LOCATION;}

	private String gname;
	private String code;
	private String country;
	private String region;
	@XStreamAlias( value = "region")
	private String adminarea_lvl_1;
	@XStreamAlias( value = "district")
	private String adminarea_lvl_2;
	private String area;
	private String locationtype;
	private String iata;
	private String notes;
	private String codeinterhome;
	private String coderentalsunited;
	private Double latitude;
	private Double longitude;
	private Double altitude;
	@XStreamOmitField
	private int parentid;
	
	@XStreamOmitField
	private String zipCode;

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public boolean noRegion() {
		return region == null || region.isEmpty();
	}
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getParentID() {
		return parentid;
	}

	public void setParentID(int parentid) {
		this.parentid = parentid;
	}

	public String getIata() {
		return iata;
	}

	public void setIata(String iata) {
		this.iata = iata;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getCodeinterhome() {
		return codeinterhome;
	}

	public void setCodeinterhome(String codeinterhome) {
		this.codeinterhome = codeinterhome;
	}
	
	public String getCoderentalsunited() {
		return coderentalsunited;
	}

	public void setCoderentalsunited(String coderentalsunited) {
		this.coderentalsunited = coderentalsunited;
	}
	
	public Double getLatitude() {
		return latitude;
	}

	public Double getLatitudeNearby(double min, double max) {
		return latitude == null || latitude == 0.0 ? null : latitude + getRandom(min, max);
	}

	public Double getLatitudeNearby() {
		return getLatitudeNearby(-0.015, 0.015);
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public Double getLongitudeNearby(double min, double max) {
		return longitude == null || longitude == 0.0 ? null : longitude + getRandom(min, max);
	}

	public Double getLongitudeNearby() {
		return getLongitudeNearby(-0.015, 0.015);
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

	public void setLatLng(LatLng latlng) {
		if(latlng != null) {
			latitude = latlng.getLatitude();
			longitude = latlng.getLongitude();
		}
	}

	public LatLng getLatLng() {
		if(latitude != null && longitude != null) {
			return LatLng.newInstance(latitude, longitude);
		}
		else {
			return null;
		}
	}

	public boolean noLatLng() {
		return latitude == null || latitude < -90.0 || latitude > 90.0
				|| longitude == null || longitude < -180.0 || longitude > 180.0; 
	}

	public boolean hasLatLng() {
		return !noLatLng();
	}
	
	public boolean zeroLatLng() {
		return latitude == 0.0 && longitude == 0.0;
	}
	public String getAdminarea_lvl_1() {
		return adminarea_lvl_1;
	}

	public void setAdminarea_lvl_1(String adminarea_lvl_1) {
		this.adminarea_lvl_1 = adminarea_lvl_1;
	}

	public String getAdminarea_lvl_2() {
		return adminarea_lvl_2;
	}

	public void setAdminarea_lvl_2(String adminarea_lvl_2) {
		this.adminarea_lvl_2 = adminarea_lvl_2;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public String getLocationtype() {
		return locationtype;
	}

	public void setLocationtype(String locationtype) {
		this.locationtype = locationtype;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Location [code=");
		builder.append(code);
		builder.append(", country=");
		builder.append(country);
		builder.append(", region=");
		builder.append(region);
		builder.append(", adminarea_lvl_1=");
		builder.append(adminarea_lvl_1);
		builder.append(", adminarea_lvl_2=");
		builder.append(adminarea_lvl_2);
		builder.append(", area=");
		builder.append(area);
		builder.append(", locationtype=");
		builder.append(locationtype);
		builder.append(", iata=");
		builder.append(iata);
		builder.append(", notes=");
		builder.append(notes);
		builder.append(", codeinterhome=");
		builder.append(codeinterhome);
		builder.append(", coderentalsunited=");
		builder.append(coderentalsunited);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", altitude=");
		builder.append(altitude);
		builder.append(", organizationid=");
		builder.append(organizationid);
		builder.append(", status=");
		builder.append(status);
		builder.append(", state=");
		builder.append(state);
		builder.append(", values=");
		builder.append(values);
		builder.append(", attributes=");
		builder.append(attributemap);
		builder.append(", texts=");
		builder.append(texts);
		builder.append(", name=");
		builder.append(name);
		builder.append(", gname=");
		builder.append(gname);
		builder.append(", parentid=");
		builder.append(parentid);
		builder.append(", id=");
		builder.append(id);
		builder.append(", service()=");
		builder.append(service());
		builder.append("]");
		return builder.toString();
	}
}