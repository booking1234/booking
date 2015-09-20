/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;


public class Area
extends Position {

	protected Double distance;
	protected String unit;
	
	public Area () {}

	public Area (Double latitude, Double longitude, Double distance, String unit) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.distance = distance;
		this.unit = unit;
	}
	
	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	private Double getAngle() {
		return distance * 0.009; //360.0/40008.0;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Area [distance=");
		builder.append(distance);
		builder.append(", unit=");
		builder.append(unit);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append("]");
		return builder.toString();
	}
}
