package net.cbtltd.rest.kigo;

public class AmenityCategory {
	private int AMENITY_CATEGORY_ID;
	private String AMENITY_CATEGORY_LABEL;
	
	public AmenityCategory() {}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AmenityCategory [AMENITY_CATEGORY_ID=");
		builder.append(AMENITY_CATEGORY_ID);
		builder.append(", AMENITY_CATEGORY_LABEL=");
		builder.append(AMENITY_CATEGORY_LABEL);
		builder.append("]");
		return builder.toString();
	}
}
