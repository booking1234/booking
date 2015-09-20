package net.cbtltd.rest.kigo;

public class Payment extends Response {
	private Number RENTDOWN_DUE;
	private String RENTDOWN_DUE_DATE;
	private Number RENTDOWN_PAID;
	private String RENTDOWN_PAID_DATE;
	private String RENTDOWN_METHOD;
	private Number RENTREMAINING_DUE;
	private String RENTREMAINING_DUE_DATE;
	private Number RENTREMAINING_PAID;
	private String RENTREMAINING_PAID_DATE;
	private String RENTREMAINING_METHOD;
	private Number DEPOSIT_DUE;
	private String DEPOSIT_DUE_DATE;
	private Number DEPOSIT_PAID;
	private String DEPOSIT_PAID_DATE;
	private String DEPOSIT_METHOD;
	private Number OTHER_DUE;
	private String OTHER_DUE_DATE;
	private Number OTHER_PAID;
	private String OTHER_PAID_DATE;
	private String OTHER_METHOD;
	
	public Payment() {}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append("Payment [RENTDOWN_DUE=");
		builder.append(RENTDOWN_DUE);
		builder.append(", RENTDOWN_DUE_DATE=");
		builder.append(RENTDOWN_DUE_DATE);
		builder.append(", RENTDOWN_PAID=");
		builder.append(RENTDOWN_PAID);
		builder.append(", RENTDOWN_PAID_DATE=");
		builder.append(RENTDOWN_PAID_DATE);
		builder.append(", RENTDOWN_METHOD=");
		builder.append(RENTDOWN_METHOD);
		builder.append(", RENTREMAINING_DUE=");
		builder.append(RENTREMAINING_DUE);
		builder.append(", RENTREMAINING_DUE_DATE=");
		builder.append(RENTREMAINING_DUE_DATE);
		builder.append(", RENTREMAINING_PAID=");
		builder.append(RENTREMAINING_PAID);
		builder.append(", RENTREMAINING_PAID_DATE=");
		builder.append(RENTREMAINING_PAID_DATE);
		builder.append(", RENTREMAINING_METHOD=");
		builder.append(RENTREMAINING_METHOD);
		builder.append(", DEPOSIT_DUE=");
		builder.append(DEPOSIT_DUE);
		builder.append(", DEPOSIT_DUE_DATE=");
		builder.append(DEPOSIT_DUE_DATE);
		builder.append(", DEPOSIT_PAID=");
		builder.append(DEPOSIT_PAID);
		builder.append(", DEPOSIT_PAID_DATE=");
		builder.append(DEPOSIT_PAID_DATE);
		builder.append(", DEPOSIT_METHOD=");
		builder.append(DEPOSIT_METHOD);
		builder.append(", OTHER_DUE=");
		builder.append(OTHER_DUE);
		builder.append(", OTHER_DUE_DATE=");
		builder.append(OTHER_DUE_DATE);
		builder.append(", OTHER_PAID=");
		builder.append(OTHER_PAID);
		builder.append(", OTHER_PAID_DATE=");
		builder.append(OTHER_PAID_DATE);
		builder.append(", OTHER_METHOD=");
		builder.append(OTHER_METHOD);
		builder.append("]");
		return builder.toString();
	}
}
