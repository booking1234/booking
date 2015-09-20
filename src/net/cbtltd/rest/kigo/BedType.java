package net.cbtltd.rest.kigo;

public class BedType {
	private int BED_TYPE_ID;
	private String BED_TYPE_LABEL;
	
	public BedType() {}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PropertyType [BED_TYPE_ID=");
		builder.append(BED_TYPE_ID);
		builder.append(", BED_TYPE_LABEL=");
		builder.append(BED_TYPE_LABEL);
		builder.append("]");
		return builder.toString();
	}
}
