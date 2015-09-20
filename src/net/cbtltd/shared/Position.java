/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import net.cbtltd.shared.api.HasResponse;
import net.cbtltd.shared.api.HasState;

import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.user.client.rpc.IsSerializable;

public class Position 
implements IsSerializable, HasResponse, HasState {

	public static final String[] STATES = {Location.CREATED};

	protected Double latitude;
	protected Double longitude;
	private Double altitude;
	private int status;

	public LatLng getCoordinate() {
		if (latitude == null || longitude == null) {
			return null;
		}
		return LatLng.newInstance(latitude, longitude);
	}

	void setCoordinate(LatLng value) {
		if (value == null) {
			return;
		}
		latitude = value.getLatitude();
		longitude = value.getLongitude();
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

	public boolean noLatLng() {
		return latitude == null || longitude == null; // || altitude == null;
	}

	public void setLatLng(LatLng latlng) {
		latitude = latlng.getLatitude();
		longitude = latlng.getLongitude();
	}

	public LatLng getLatLng() {
		return LatLng.newInstance(latitude, longitude);
	}

	public String getState() {
		return Location.CREATED;
	}

	public void setState(String state) {
		//this.state = state;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Position [latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", altitude=");
		builder.append(altitude);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
}
