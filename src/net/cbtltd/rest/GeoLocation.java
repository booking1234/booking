package net.cbtltd.rest;

import org.springframework.beans.BeanUtils;

import net.cbtltd.shared.Location;

public class GeoLocation extends net.cbtltd.shared.Location {
	
	private String zipCode;
	
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public GeoLocation() {}
	
	public Location clone(Location location) {
		BeanUtils.copyProperties(location, this);
		return this;
	}
}
