package net.cbtltd.rest.kigo;

public class UdpaChoice {
	
	private int UDRA_CHOICE_ID;
	private String UDRA_CHOICE_LABEL;
	
	public UdpaChoice() {}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UdraChoice [UDRA_CHOICE_ID=");
		builder.append(UDRA_CHOICE_ID);
		builder.append(", UDRA_CHOICE_LABEL=");
		builder.append(UDRA_CHOICE_LABEL);
		builder.append("]");
		return builder.toString();
	}
}
