package net.cbtltd.rest.kigo;

import java.util.Arrays;

public class Property {
	
	private int PROP_ID;
	private String PROP_NAME;
	private int PROP_PROVIDER_OWNER_ID;
	private int PROP_PROVIDER_RA_ID;
	private boolean PROP_INSTANT_BOOK;
	private String PROP_STREETNO;
	private String PROP_ADDR1;
	private String PROP_ADDR2;
	private String PROP_ADDR3;
	private String PROP_APTNO;
	private String PROP_POSTCODE;
	private String PROP_CITY;
	private String PROP_REGION;
	private String PROP_COUNTRY;
	private String PROP_PHONE;
	private String PROP_AXSCODE;
	private Double PROP_LATITUDE;
	private Double PROP_LONGITUDE;
	private int PROP_BEDROOMS;
	private int PROP_BEDS;
	private int PROP_BATHROOMS;
	private int PROP_TOILETS;
	private int PROP_TYPE_ID;
	private int PROP_SIZE;
	private String PROP_SIZE_UNIT;
	private int PROP_MAXGUESTS;
	private int PROP_MAXGUESTS_ADULTS;
	private int PROP_MAXGUESTS_CHILDREN;
	private int PROP_MAXGUESTS_BABIES;
	private String PROP_FLOOR;
	private Boolean PROP_ELEVATOR;
	private String PROP_CIN_TIME;
	private String PROP_COUT_TIME;
	private int PROP_STAY_MIN;
	private String PROP_STAY_MIN_UNIT;
	private int PROP_STAY_MAX;
	private String PROP_STAY_MAX_UNIT;
	private String PROP_SHORTDESCRIPTION;
	private String PROP_DESCRIPTION;
	private String PROP_AREADESCRIPTION;
	private String PROP_INVENTORY;
	private String PROP_ARRIVAL_SHEET;
	private Number PROP_RATE_NIGHTLY_FROM;
	private Number PROP_RATE_NIGHTLY_TO;
	private Number PROP_RATE_WEEKLY_FROM;
	private Number PROP_RATE_WEEKLY_TO;
	private Number PROP_RATE_MONTHLY_FROM;
	private Number PROP_RATE_MONTHLY_TO;
	private String PROP_RATE_CURRENCY;
	private int[] PROP_ACTIVITIES;
	private int[] PROP_AMENITIES;
	private int[] PROP_BED_TYPES;
	private Photo[] PROP_PHOTOS;
	private Udpa[] PROP_UDPA;
	
	public Property () {}	//no args constructor

	public Property (int PROP_ID) {
		this.PROP_ID = PROP_ID;
	}
	
	public int getPROP_ID() {
		return PROP_ID;
	}

	public String getPROP_NAME() {
		return PROP_NAME;
	}

	public int getPROP_PROVIDER_OWNER_ID() {
		return PROP_PROVIDER_OWNER_ID;
	}

	public int getPROP_PROVIDER_RA_ID() {
		return PROP_PROVIDER_RA_ID;
	}

	public boolean isPROP_INSTANT_BOOK() {
		return PROP_INSTANT_BOOK;
	}

	public String getPROP_STREETNO() {
		return PROP_STREETNO;
	}

	public boolean noPROP_STREETNO() {
		return PROP_STREETNO == null || PROP_STREETNO.isEmpty();
	}

	public boolean hasPROP_STREETNO() {
		return !noPROP_STREETNO();
	}
	
	public String getPROP_ADDR1() {
		return PROP_ADDR1;
	}

	public boolean noPROP_ADDR1() {
		return PROP_ADDR1 == null || PROP_ADDR1.isEmpty();
	}

	public boolean hasPROP_ADDR1() {
		return !noPROP_ADDR1();
	}

	public String getPROP_ADDR2() {
		return PROP_ADDR2;
	}

	public boolean noPROP_ADDR2() {
		return PROP_ADDR2 == null || PROP_ADDR2.isEmpty();
	}
	
	public boolean hasPROP_ADDR2() {
		return !noPROP_ADDR2();
	}

	public String getPROP_ADDR3() {
		return PROP_ADDR3;
	}

	public boolean noPROP_ADDR3() {
		return PROP_ADDR3 == null || PROP_ADDR3.isEmpty();
	}

	public boolean hasPROP_ADDR3() {
		return !noPROP_ADDR3();
	}

	public String getPROP_APTNO() {
		return PROP_APTNO;
	}

	public boolean noPROP_APTNO() {
		return PROP_APTNO == null || PROP_APTNO.isEmpty();
	}

	public boolean hasPROP_APTNO() {
		return !noPROP_APTNO();
	}

	public String getPROP_POSTCODE() {
		return PROP_POSTCODE;
	}

	public String getPROP_CITY() {
		return PROP_CITY;
	}

	public boolean noPROP_CITY() {
		return PROP_CITY == null || PROP_CITY.isEmpty();
	}

	public boolean hasPROP_CITY() {
		return !noPROP_CITY();
	}

	public String getPROP_REGION() {
		return PROP_REGION;
	}
	
	public boolean noPROP_REGION() {
		return PROP_REGION == null || PROP_REGION.isEmpty();
	}

	public boolean hasPROP_REGION() {
		return !noPROP_REGION();
	}


	public String getPROP_COUNTRY() {
		return PROP_COUNTRY;
	}

	public String getPROP_PHONE() {
		return PROP_PHONE;
	}

	public String getPROP_AXSCODE() {
		return PROP_AXSCODE;
	}

	public Double getPROP_LATITUDE() {
		return PROP_LATITUDE;
	}

	public boolean noPROP_LATITUDE() {
		return PROP_LATITUDE == null || PROP_LATITUDE == 0.0;
	}
	
	public Double getPROP_LONGITUDE() {
		return PROP_LONGITUDE;
	}

	public boolean noPROP_LONGITUDE() {
		return PROP_LONGITUDE == null || PROP_LONGITUDE == 0.0;
	}
	
	public int getPROP_BEDROOMS() {
		return PROP_BEDROOMS;
	}

	public int getPROP_BEDS() {
		return PROP_BEDS;
	}

	public int getPROP_BATHROOMS() {
		return PROP_BATHROOMS;
	}

	public int getPROP_TOILETS() {
		return PROP_TOILETS;
	}

	public int getPROP_TYPE_ID() {
		return PROP_TYPE_ID;
	}

	public int getPROP_SIZE() {
		return PROP_SIZE;
	}

	public String getPROP_SIZE_UNIT() {
		return PROP_SIZE_UNIT;
	}

	public int getPROP_MAXGUESTS() {
		return PROP_MAXGUESTS;
	}

	public int getPROP_MAXGUESTS_ADULTS() {
		return PROP_MAXGUESTS_ADULTS;
	}

	public int getPROP_MAXGUESTS_CHILDREN() {
		return PROP_MAXGUESTS_CHILDREN;
	}

	public int getPROP_MAXGUESTS_BABIES() {
		return PROP_MAXGUESTS_BABIES;
	}

	public String getPROP_FLOOR() {
		return PROP_FLOOR;
	}

	public Boolean getPROP_ELEVATOR() {
		return PROP_ELEVATOR;
	}

	public String getPROP_CIN_TIME() {
		return PROP_CIN_TIME;
	}

	public String getPROP_COUT_TIME() {
		return PROP_COUT_TIME;
	}

	public int getPROP_STAY_MIN() {
		return PROP_STAY_MIN;
	}

	public String getPROP_STAY_MIN_UNIT() {
		return PROP_STAY_MIN_UNIT;
	}

	public int getPROP_STAY_MAX() {
		return PROP_STAY_MAX;
	}

	public String getPROP_STAY_MAX_UNIT() {
		return PROP_STAY_MAX_UNIT;
	}

	public String getPROP_SHORTDESCRIPTION() {
		return PROP_SHORTDESCRIPTION;
	}

	public String getPROP_DESCRIPTION() {
		return PROP_DESCRIPTION;
	}

	public String getPROP_AREADESCRIPTION() {
		return PROP_AREADESCRIPTION;
	}

	public String getPROP_INVENTORY() {
		return PROP_INVENTORY;
	}

	public String getPROP_ARRIVAL_SHEET() {
		return PROP_ARRIVAL_SHEET;
	}

	public Number getPROP_RATE_NIGHTLY_FROM() {
		return PROP_RATE_NIGHTLY_FROM;
	}

	public Number getPROP_RATE_NIGHTLY_TO() {
		return PROP_RATE_NIGHTLY_TO;
	}

	public Number getPROP_RATE_WEEKLY_FROM() {
		return PROP_RATE_WEEKLY_FROM;
	}

	public Number getPROP_RATE_WEEKLY_TO() {
		return PROP_RATE_WEEKLY_TO;
	}

	public Number getPROP_RATE_MONTHLY_FROM() {
		return PROP_RATE_MONTHLY_FROM;
	}

	public Number getPROP_RATE_MONTHLY_TO() {
		return PROP_RATE_MONTHLY_TO;
	}

	public String getPROP_RATE_CURRENCY() {
		return PROP_RATE_CURRENCY;
	}

	public boolean noPROP_RATE_CURRENCY() {
		return PROP_RATE_CURRENCY == null || PROP_RATE_CURRENCY.isEmpty();
	}

	public int[] getPROP_ACTIVITIES() {
		return PROP_ACTIVITIES;
	}

	public int[] getPROP_AMENITIES() {
		return PROP_AMENITIES;
	}

	public int[] getPROP_BED_TYPES() {
		return PROP_BED_TYPES;
	}

	public Photo[] getPROP_PHOTOS() {
		return PROP_PHOTOS;
	}

	public Udpa[] getPROP_UDPA() {
		return PROP_UDPA;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Property [PROP_ID=");
		builder.append(PROP_ID);
		builder.append(", PROP_NAME=");
		builder.append(PROP_NAME);
		builder.append(", PROP_PROVIDER_OWNER_ID=");
		builder.append(PROP_PROVIDER_OWNER_ID);
		builder.append(", PROP_PROVIDER_RA_ID=");
		builder.append(PROP_PROVIDER_RA_ID);
		builder.append(", PROP_INSTANT_BOOK=");
		builder.append(PROP_INSTANT_BOOK);
		builder.append(", PROP_STREETNO=");
		builder.append(PROP_STREETNO);
		builder.append(", PROP_ADDR1=");
		builder.append(PROP_ADDR1);
		builder.append(", PROP_ADDR2=");
		builder.append(PROP_ADDR2);
		builder.append(", PROP_ADDR3=");
		builder.append(PROP_ADDR3);
		builder.append(", PROP_APTNO=");
		builder.append(PROP_APTNO);
		builder.append(", PROP_POSTCODE=");
		builder.append(PROP_POSTCODE);
		builder.append(", PROP_CITY=");
		builder.append(PROP_CITY);
		builder.append(", PROP_REGION=");
		builder.append(PROP_REGION);
		builder.append(", PROP_COUNTRY=");
		builder.append(PROP_COUNTRY);
		builder.append(", PROP_PHONE=");
		builder.append(PROP_PHONE);
		builder.append(", PROP_AXSCODE=");
		builder.append(PROP_AXSCODE);
		builder.append(", PROP_LATITUDE=");
		builder.append(PROP_LATITUDE);
		builder.append(", PROP_LONGITUDE=");
		builder.append(PROP_LONGITUDE);
		builder.append(", PROP_BEDROOMS=");
		builder.append(PROP_BEDROOMS);
		builder.append(", PROP_BEDS=");
		builder.append(PROP_BEDS);
		builder.append(", PROP_BATHROOMS=");
		builder.append(PROP_BATHROOMS);
		builder.append(", PROP_TOILETS=");
		builder.append(PROP_TOILETS);
		builder.append(", PROP_TYPE_ID=");
		builder.append(PROP_TYPE_ID);
		builder.append(", PROP_SIZE=");
		builder.append(PROP_SIZE);
		builder.append(", PROP_SIZE_UNIT=");
		builder.append(PROP_SIZE_UNIT);
		builder.append(", PROP_MAXGUESTS=");
		builder.append(PROP_MAXGUESTS);
		builder.append(", PROP_MAXGUESTS_ADULTS=");
		builder.append(PROP_MAXGUESTS_ADULTS);
		builder.append(", PROP_MAXGUESTS_CHILDREN=");
		builder.append(PROP_MAXGUESTS_CHILDREN);
		builder.append(", PROP_MAXGUESTS_BABIES=");
		builder.append(PROP_MAXGUESTS_BABIES);
		builder.append(", PROP_FLOOR=");
		builder.append(PROP_FLOOR);
		builder.append(", PROP_ELEVATOR=");
		builder.append(PROP_ELEVATOR);
		builder.append(", PROP_CIN_TIME=");
		builder.append(PROP_CIN_TIME);
		builder.append(", PROP_COUT_TIME=");
		builder.append(PROP_COUT_TIME);
		builder.append(", PROP_STAY_MIN=");
		builder.append(PROP_STAY_MIN);
		builder.append(", PROP_STAY_MIN_UNIT=");
		builder.append(PROP_STAY_MIN_UNIT);
		builder.append(", PROP_STAY_MAX=");
		builder.append(PROP_STAY_MAX);
		builder.append(", PROP_STAY_MAX_UNIT=");
		builder.append(PROP_STAY_MAX_UNIT);
		builder.append(", PROP_SHORTDESCRIPTION=");
		builder.append(PROP_SHORTDESCRIPTION);
		builder.append(", PROP_DESCRIPTION=");
		builder.append(PROP_DESCRIPTION);
		builder.append(", PROP_AREADESCRIPTION=");
		builder.append(PROP_AREADESCRIPTION);
		builder.append(", PROP_INVENTORY=");
		builder.append(PROP_INVENTORY);
		builder.append(", PROP_ARRIVAL_SHEET=");
		builder.append(PROP_ARRIVAL_SHEET);
		builder.append(", PROP_RATE_NIGHTLY_FROM=");
		builder.append(PROP_RATE_NIGHTLY_FROM);
		builder.append(", PROP_RATE_NIGHTLY_TO=");
		builder.append(PROP_RATE_NIGHTLY_TO);
		builder.append(", PROP_RATE_WEEKLY_FROM=");
		builder.append(PROP_RATE_WEEKLY_FROM);
		builder.append(", PROP_RATE_WEEKLY_TO=");
		builder.append(PROP_RATE_WEEKLY_TO);
		builder.append(", PROP_RATE_MONTHLY_FROM=");
		builder.append(PROP_RATE_MONTHLY_FROM);
		builder.append(", PROP_RATE_MONTHLY_TO=");
		builder.append(PROP_RATE_MONTHLY_TO);
		builder.append(", PROP_RATE_CURRENCY=");
		builder.append(PROP_RATE_CURRENCY);
		builder.append(", PROP_ACTIVITIES=");
		builder.append(Arrays.toString(PROP_ACTIVITIES));
		builder.append(", PROP_AMENITIES=");
		builder.append(Arrays.toString(PROP_AMENITIES));
		builder.append(", PROP_BED_TYPES=");
		builder.append(Arrays.toString(PROP_BED_TYPES));
		builder.append(", PROP_PHOTOS=");
		builder.append(Arrays.toString(PROP_PHOTOS));
		builder.append(", PROP_UDPA=");
		builder.append(Arrays.toString(PROP_UDPA));
		builder.append("]");
		return builder.toString();
	}
}
