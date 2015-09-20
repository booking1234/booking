package net.cbtltd.rest.kigo;

public class Activity {
	private int ACTIVITY_ID;
	private int ACTIVITY_CATEGORY_ID;
	private String ACTIVITY_LABEL;
	
	public Activity() {}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Activity [ACTIVITY_ID=");
		builder.append(ACTIVITY_ID);
		builder.append(", ACTIVITY_CATEGORY_ID=");
		builder.append(ACTIVITY_CATEGORY_ID);
		builder.append(", ACTIVITY_LABEL=");
		builder.append(ACTIVITY_LABEL);
		builder.append("]");
		return builder.toString();
	}
}
