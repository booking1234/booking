package net.cbtltd.rest.kigo;

public class OnlineBooking {
	private String OB_STATE;
	private String OB_STATE_DATE;
	private Number OB_AMOUNT;
	private String OB_CURRENCY;
	private String OB_ENGINE_CODE;
	private String OB_REF;
	
	public enum State {PAID,ERROR};
	
	public OnlineBooking() {}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OnlineBooking [OB_STATE=");
		builder.append(OB_STATE);
		builder.append(", OB_STATE_DATE=");
		builder.append(OB_STATE_DATE);
		builder.append(", OB_AMOUNT=");
		builder.append(OB_AMOUNT);
		builder.append(", OB_CURRENCY=");
		builder.append(OB_CURRENCY);
		builder.append(", OB_ENGINE_CODE=");
		builder.append(OB_ENGINE_CODE);
		builder.append(", OB_REF=");
		builder.append(OB_REF);
		builder.append("]");
		return builder.toString();
	}

}
