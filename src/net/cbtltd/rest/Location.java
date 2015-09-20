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

@XmlRootElement(name = "location")
@Description(value = "Location and position data", target = DocTarget.RESPONSE)
public class Location
implements HasXsl {
    private String locationid;
    private String country;
    private String code;
    private String name;
    private String state;
    private String subdivision;
    private String type;
    private String date;
    private String iata;
    private String coordinates;
    private String remarks;
    private Double latitude;
    private Double longitude;
    private Double altitude;
    private String message;
	private String xsl; //NB HAS GET&SET = private

	public Location() {}

	public Location(String message) {
		this.message = message;
	}

	@Description(value = "Location identity", target = DocTarget.METHOD)
	public String getLocationid() {
		return locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	@Description(value = "Location country code", target = DocTarget.METHOD)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Description(value = "Location ISO code", target = DocTarget.METHOD)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Description(value = "Location name", target = DocTarget.METHOD)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Description(value = "Location subdivision (state, province, county etc)", target = DocTarget.METHOD)
	public String getSubdivision() {
		return subdivision;
	}

	public void setSubdivision(String subdivision) {
		this.subdivision = subdivision;
	}

	@Description(value = "Location type", target = DocTarget.METHOD)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Description(value = "Location creation date", target = DocTarget.METHOD)
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Description(value = "Location IATA code", target = DocTarget.METHOD)
	public String getIata() {
		return iata;
	}

	public void setIata(String iata) {
		this.iata = iata;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	@Description(value = "Location ISO remarks", target = DocTarget.METHOD)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Description(value = "Location latitude", target = DocTarget.METHOD)
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Description(value = "Location longitude", target = DocTarget.METHOD)
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Description(value = "Location altitude in metres", target = DocTarget.METHOD)
	public Double getAltitude() {
		return altitude;
	}

	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}

	public boolean noCoordinate() {
		return latitude == null || longitude == null; // || altitude == null;
	}
	
	//---------------------------------------------------------------------------------
	// Implements Model interface
	//---------------------------------------------------------------------------------
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

	public boolean hasState(String state) {
		return this.state != null && this.state.equals(state);
	}

	@Description(value = "Location ID", target = DocTarget.METHOD)
	public String getId(){
		return locationid;
	}
	
	public void setId(String id){
		locationid = id;
	}

	public boolean noId() {
		return getId() == null || getId().isEmpty();
	}
	
	public boolean hasId() {
		return !noId();
	}
	
	
	//---------------------------------------------------------------------------------
	// Exception message
	//---------------------------------------------------------------------------------
	@Description(value = "Diagnostic message in the response", target = DocTarget.METHOD)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Location [locationid=");
		builder.append(locationid);
		builder.append(", country=");
		builder.append(country);
		builder.append(", code=");
		builder.append(code);
		builder.append(", name=");
		builder.append(name);
		builder.append(", state=");
		builder.append(state);
		builder.append(", subdivision=");
		builder.append(subdivision);
		builder.append(", type=");
		builder.append(type);
		builder.append(", date=");
		builder.append(date);
		builder.append(", iata=");
		builder.append(iata);
		builder.append(", coordinates=");
		builder.append(coordinates);
		builder.append(", remarks=");
		builder.append(remarks);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", altitude=");
		builder.append(altitude);
		builder.append(", xsl=");
		builder.append(xsl);
		builder.append("]");
		return builder.toString();
	}
}