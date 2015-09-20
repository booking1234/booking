package net.cbtltd.rest.kigo;

public class Amenity {
	private int AMENITY_ID;
	private int AMENITY_CATEGORY_ID;
	private String AMENITY_LABEL;
	
	public Amenity() {}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Amenity [AMENITY_ID=");
		builder.append(AMENITY_ID);
		builder.append(", AMENITY_CATEGORY_ID=");
		builder.append(AMENITY_CATEGORY_ID);
		builder.append(", AMENITY_LABEL=");
		builder.append(AMENITY_LABEL);
		builder.append("]");
		return builder.toString();
	}
}
