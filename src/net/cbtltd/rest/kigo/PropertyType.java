package net.cbtltd.rest.kigo;

public class PropertyType {
	private int PROP_TYPE_ID;
	private String PROP_TYPE_LABEL;
	
	public PropertyType() {}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PropertyType [PROP_TYPE_ID=");
		builder.append(PROP_TYPE_ID);
		builder.append(", PROP_TYPE_LABEL=");
		builder.append(PROP_TYPE_LABEL);
		builder.append("]");
		return builder.toString();
	}
}
