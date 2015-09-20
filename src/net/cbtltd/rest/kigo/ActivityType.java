package net.cbtltd.rest.kigo;

import java.util.Arrays;

public class ActivityType {
	private ActivityCategory[] ACTIVITY_CATEGORY;
	private Activity[] ACTIVITY;
	
	public ActivityType() {}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ActivityType [ACTIVITY_CATEGORY=");
		builder.append(Arrays.toString(ACTIVITY_CATEGORY));
		builder.append(", ACTIVITY=");
		builder.append(Arrays.toString(ACTIVITY));
		builder.append("]");
		return builder.toString();
	}
}
