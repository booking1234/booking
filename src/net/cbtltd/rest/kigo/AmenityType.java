package net.cbtltd.rest.kigo;

import java.util.Arrays;

public class AmenityType {
	private AmenityCategory[] AMENITY_CATEGORY;
	private Amenity[] AMENITY;
	
	public AmenityType() {}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AmenityType [AMENITY_CATEGORY=");
		builder.append(Arrays.toString(AMENITY_CATEGORY));
		builder.append(", AMENITY=");
		builder.append(Arrays.toString(AMENITY));
		builder.append("]");
		return builder.toString();
	}
}
