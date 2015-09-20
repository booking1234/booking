/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.product;

import net.cbtltd.shared.Product;

public class ProductPositions extends Product {
	
	public ProductPositions() {}

	/** 
	 * Transforms zoom level into rounding integer 
	 * 
	 * zoom level doubles the size with each step
	 * zoom level 8 corresponds to a distance of 280km
	 */
	public ProductPositions(Double latitude, Double longitude, Integer zoomLevel) {
		this.latitude = latitude;
		this.longitude = longitude;
		if (zoomLevel > 4 ) {setNumrows(0);}
		else if (zoomLevel > 1) {setNumrows(-1);}
		else {setNumrows(-2);}
	}

	private Double getAngle(double factor) {
		return (getNumrows() < 0 ? 90.0 : 30.0) * factor;
	}

	public Double getNelatitude() {
		return latitude == null ? null : latitude + getAngle(1);
	}

	public Double getNelongitude() {
		return longitude == null ? null : longitude + getAngle(2);
	}

	public Double getSwlatitude() {
		return latitude == null ? null : latitude - getAngle(1);
	}

	public Double getSwlongitude() {
		return longitude == null ? null : longitude - getAngle(2);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductPositions [latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", getNumrows()=");
		builder.append(getNumrows());
		builder.append("]");
		return builder.toString();
	}
}
