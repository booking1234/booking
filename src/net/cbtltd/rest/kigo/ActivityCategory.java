package net.cbtltd.rest.kigo;

public class ActivityCategory {
	private int ACTIVITY_CATEGORY_ID;
	private String ACTIVITY_CATEGORY_LABEL;
	
	public ActivityCategory() {}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ActivityCategory [ACTIVITY_CATEGORY_ID=");
		builder.append(ACTIVITY_CATEGORY_ID);
		builder.append(", ACTIVITY_CATEGORY_LABEL=");
		builder.append(ACTIVITY_CATEGORY_LABEL);
		builder.append("]");
		return builder.toString();
	}
}
