package net.cbtltd.rest.registration;

import java.util.HashMap;
import java.util.Map;

public enum TIME_INTERVAL {
	ZERO_DAY(0, "0:DAY", 0), ONE_DAY(1, "1:DAY", 1), THRE_DAYS(2, "3:DAY", 3), FIFTH_DAYS(3, "5:DAY", 5), SEVENTH_DAYS(4, "7:DAY", 7), TEN_DAYS(
			5, "10:DAY", 10), TWO_WEE(6, "2:WEE", 14), ONE_MON(7, "1:MON", 30), TWO_MON(8, "2:MON", 60), THRE_MON(9, "3:MON", 90);

	private int key;
	private String label;
	private int daysCount;

	public int getKey() {
		return key;
	}

	public int getDaysCount() {
		return daysCount;
	}

	public String getLabel() {
		return label;
	}

	/**
	 * A mapping between the integer code and its corresponding interval  to
	 * facilitate lookup by code.
	 */
	private static Map<Integer, TIME_INTERVAL> codeToIntervalMapping;
	private static Map<Integer, TIME_INTERVAL> daysToIntervalMapping;

	private TIME_INTERVAL(int code, String label, int days) {
		this.key = code;
		this.label = label;
		this.daysCount = days;
	}

	/* By Code Mapping */	
	public static TIME_INTERVAL getDate(int i) {
		if (codeToIntervalMapping == null) {
			initCodeMapping();
		}
		return codeToIntervalMapping.get(i);
	}

	private static void initCodeMapping() {
		codeToIntervalMapping = new HashMap<Integer, TIME_INTERVAL>();
		for (TIME_INTERVAL s : values()) {
			codeToIntervalMapping.put(s.key, s);
		}
	}	
	/* End by Code Mapping */
	
	/* By Days Mapping */	
	public static TIME_INTERVAL getKey(int i) {
		if (daysToIntervalMapping == null) {
			initDaysMapping();
		}
		return daysToIntervalMapping.get(i);
	}
	
	private static void initDaysMapping() {
		daysToIntervalMapping = new HashMap<Integer, TIME_INTERVAL>();
		for (TIME_INTERVAL s : values()) {
			daysToIntervalMapping.put(s.daysCount, s);
		}
	}	
	/* End By Days Mapping */
	
}
