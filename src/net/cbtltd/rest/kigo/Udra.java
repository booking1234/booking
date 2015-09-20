package net.cbtltd.rest.kigo;

import java.util.Arrays;

public class Udra {

	private int UDRA_ID;
	private String UDRA_TEXT;
	private int UDRA_CHOICE_ID;
	private UdraChoice[] UDRA_CHOICES;
	
	public Udra() {}

	public Udra(int UDRA_ID, String UDRA_TEXT, int UDRA_CHOICE_ID, UdraChoice[] UDRA_CHOICES) {
		super();
		this.UDRA_ID = UDRA_ID;
		this.UDRA_TEXT = UDRA_TEXT;
		this.UDRA_CHOICE_ID = UDRA_CHOICE_ID;
		this.UDRA_CHOICES = UDRA_CHOICES;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Udra [UDRA_ID=");
		builder.append(UDRA_ID);
		builder.append(", UDRA_TEXT=");
		builder.append(UDRA_TEXT);
		builder.append(", UDRA_CHOICE_ID=");
		builder.append(UDRA_CHOICE_ID);
		builder.append(", UDRA_CHOICES=");
		builder.append(Arrays.toString(UDRA_CHOICES));
		builder.append("]");
		return builder.toString();
	}
}
