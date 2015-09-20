package net.cbtltd.rest.kigo;

public class Photo {
	private int PROP_ID;
	private String PHOTO_ID;
	private boolean PHOTO_PANORAMIC;
	private String PHOTO_NAME;
	private String PHOTO_COMMENTS;
	private int PHOTO_SIZE;
	
	public Photo() {}

	public Photo(int PROP_ID, String PHOTO_ID) {
		super();
		this.PROP_ID = PROP_ID;
		this.PHOTO_ID = PHOTO_ID;
	}

	public int getPROP_ID() {
		return PROP_ID;
	}

	public String getPHOTO_ID() {
		return PHOTO_ID;
	}

	public boolean isPHOTO_PANORAMIC() {
		return PHOTO_PANORAMIC;
	}

	public String getPHOTO_NAME() {
		return PHOTO_NAME;
	}

	public String getPHOTO_COMMENTS() {
		return PHOTO_COMMENTS;
	}

	public int getPHOTO_SIZE() {
		return PHOTO_SIZE;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Photo [PROP_ID=");
		builder.append(PROP_ID);
		builder.append(", PHOTO_ID=");
		builder.append(PHOTO_ID);
		builder.append(", PHOTO_PANORAMIC=");
		builder.append(PHOTO_PANORAMIC);
		builder.append(", PHOTO_NAME=");
		builder.append(PHOTO_NAME);
		builder.append(", PHOTO_COMMENTS=");
		builder.append(PHOTO_COMMENTS);
		builder.append(", PHOTO_SIZE=");
		builder.append(PHOTO_SIZE);
		builder.append("]");
		return builder.toString();
	}
	
}
