package net.cbtltd.rest.kigo;

import java.util.Arrays;

public class Booking {
	private int RES_ID;
	private int PROP_ID;
	private boolean RES_IS_FOR;
	private String RES_STATUS;
	private String RES_CREATE;
	private String RES_CHECK_IN;
	private String RES_CHECK_OUT;
	private int RES_N_ADULTS;
	private int RES_N_CHILDREN;
	private int RES_N_BABIES;
	private String RES_COMMENT;
	private String RES_COMMENT_GUEST;
	private String RES_GUEST_FIRSTNAME;
	private String RES_GUEST_LASTNAME;
	private String RES_GUEST_EMAIL;
	private String RES_GUEST_PHONE;
	private String RES_GUEST_COUNTRY;
	private Payment PMT_G2RA;
	private OnlineBooking ob;
	private Udra[] RES_UDRA;

	public enum Status{CONFIRMED,HOLD,CANCELED,CANCELED_HOLD};
	
	Booking() {}

	public Booking(
			int RES_ID
		) {
		super();
		this.RES_ID = RES_ID;
	}

	public Booking(
			int PROP_ID, 
			String RES_CREATE, 
			String RES_CHECK_IN,
			String RES_CHECK_OUT, 
			int RES_N_ADULTS, 
			int RES_N_CHILDREN,
			int RES_N_BABIES
		) {
		super();
		this.PROP_ID = PROP_ID;
		this.RES_CREATE = RES_CREATE;
		this.RES_CHECK_IN = RES_CHECK_IN;
		this.RES_CHECK_OUT = RES_CHECK_OUT;
		this.RES_N_ADULTS = RES_N_ADULTS;
		this.RES_N_CHILDREN = RES_N_CHILDREN;
		this.RES_N_BABIES = RES_N_BABIES;
	}

	public Booking(
			int RES_ID,
			int PROP_ID, 
			String RES_CHECK_IN, 
			String RES_CHECK_OUT, 
			int RES_N_ADULTS,
			int RES_N_CHILDREN, 
			int RES_N_BABIES, 
			String RES_COMMENT,
			String RES_COMMENT_GUEST, 
			Udra[] RES_UDRA
		) {
		super();
		this.RES_ID = RES_ID;
		this.PROP_ID = PROP_ID;
		this.RES_CHECK_IN = RES_CHECK_IN;
		this.RES_CHECK_OUT = RES_CHECK_OUT;
		this.RES_N_ADULTS = RES_N_ADULTS;
		this.RES_N_CHILDREN = RES_N_CHILDREN;
		this.RES_N_BABIES = RES_N_BABIES;
		this.RES_COMMENT = RES_COMMENT;
		this.RES_COMMENT_GUEST = RES_COMMENT_GUEST;
		this.RES_UDRA = RES_UDRA;
	}

	public void setRES_ID(int rES_ID) {
		RES_ID = rES_ID;
	}

	public void setPROP_ID(int pROP_ID) {
		PROP_ID = pROP_ID;
	}

	public void setRES_IS_FOR(boolean rES_IS_FOR) {
		RES_IS_FOR = rES_IS_FOR;
	}

	public void setRES_STATUS(String rES_STATUS) {
		RES_STATUS = rES_STATUS;
	}

	public void setRES_CREATE(String rES_CREATE) {
		RES_CREATE = rES_CREATE;
	}

	public void setRES_CHECK_IN(String rES_CHECK_IN) {
		RES_CHECK_IN = rES_CHECK_IN;
	}

	public void setRES_CHECK_OUT(String rES_CHECK_OUT) {
		RES_CHECK_OUT = rES_CHECK_OUT;
	}

	public void setRES_N_ADULTS(int rES_N_ADULTS) {
		RES_N_ADULTS = rES_N_ADULTS;
	}

	public void setRES_N_CHILDREN(int rES_N_CHILDREN) {
		RES_N_CHILDREN = rES_N_CHILDREN;
	}

	public void setRES_N_BABIES(int rES_N_BABIES) {
		RES_N_BABIES = rES_N_BABIES;
	}

	public void setRES_COMMENT(String rES_COMMENT) {
		RES_COMMENT = rES_COMMENT;
	}

	public void setRES_COMMENT_GUEST(String rES_COMMENT_GUEST) {
		RES_COMMENT_GUEST = rES_COMMENT_GUEST;
	}

	public void setRES_GUEST_FIRSTNAME(String rES_GUEST_FIRSTNAME) {
		RES_GUEST_FIRSTNAME = rES_GUEST_FIRSTNAME;
	}

	public void setRES_GUEST_LASTNAME(String rES_GUEST_LASTNAME) {
		RES_GUEST_LASTNAME = rES_GUEST_LASTNAME;
	}

	public void setRES_GUEST_EMAIL(String rES_GUEST_EMAIL) {
		RES_GUEST_EMAIL = rES_GUEST_EMAIL;
	}

	public void setRES_GUEST_PHONE(String rES_GUEST_PHONE) {
		RES_GUEST_PHONE = rES_GUEST_PHONE;
	}

	public void setRES_GUEST_COUNTRY(String rES_GUEST_COUNTRY) {
		RES_GUEST_COUNTRY = rES_GUEST_COUNTRY;
	}

	public void setPMT_G2RA(Payment pMT_G2RA) {
		PMT_G2RA = pMT_G2RA;
	}

	public void setOb(OnlineBooking ob) {
		this.ob = ob;
	}

	public void setRES_UDRA(Udra[] rES_UDRA) {
		RES_UDRA = rES_UDRA;
	}

	public int getRES_ID() {
		return RES_ID;
	}

	public int getPROP_ID() {
		return PROP_ID;
	}

	public boolean isRES_IS_FOR() {
		return RES_IS_FOR;
	}

	public String getRES_STATUS() {
		return RES_STATUS;
	}

	public String getRES_CREATE() {
		return RES_CREATE;
	}

	public String getRES_CHECK_IN() {
		return RES_CHECK_IN;
	}

	public String getRES_CHECK_OUT() {
		return RES_CHECK_OUT;
	}

	public int getRES_N_ADULTS() {
		return RES_N_ADULTS;
	}

	public int getRES_N_CHILDREN() {
		return RES_N_CHILDREN;
	}

	public int getRES_N_BABIES() {
		return RES_N_BABIES;
	}

	public String getRES_COMMENT() {
		return RES_COMMENT;
	}

	public String getRES_COMMENT_GUEST() {
		return RES_COMMENT_GUEST;
	}

	public String getRES_GUEST_FIRSTNAME() {
		return RES_GUEST_FIRSTNAME;
	}

	public String getRES_GUEST_LASTNAME() {
		return RES_GUEST_LASTNAME;
	}

	public String getRES_GUEST_EMAIL() {
		return RES_GUEST_EMAIL;
	}

	public String getRES_GUEST_PHONE() {
		return RES_GUEST_PHONE;
	}

	public String getRES_GUEST_COUNTRY() {
		return RES_GUEST_COUNTRY;
	}

	public Payment getPMT_G2RA() {
		return PMT_G2RA;
	}

	public OnlineBooking getOb() {
		return ob;
	}

	public Udra[] getRES_UDRA() {
		return RES_UDRA;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Reservation [RES_ID=");
		builder.append(RES_ID);
		builder.append(", PROP_ID=");
		builder.append(PROP_ID);
		builder.append(", RES_IS_FOR=");
		builder.append(RES_IS_FOR);
		builder.append(", RES_STATUS=");
		builder.append(RES_STATUS);
		builder.append(", RES_CREATE=");
		builder.append(RES_CREATE);
		builder.append(", RES_CHECK_IN=");
		builder.append(RES_CHECK_IN);
		builder.append(", RES_CHECK_OUT=");
		builder.append(RES_CHECK_OUT);
		builder.append(", RES_N_ADULTS=");
		builder.append(RES_N_ADULTS);
		builder.append(", RES_N_CHILDREN=");
		builder.append(RES_N_CHILDREN);
		builder.append(", RES_N_BABIES=");
		builder.append(RES_N_BABIES);
		builder.append(", RES_COMMENT=");
		builder.append(RES_COMMENT);
		builder.append(", RES_COMMENT_GUEST=");
		builder.append(RES_COMMENT_GUEST);
		builder.append(", RES_GUEST_FIRSTNAME=");
		builder.append(RES_GUEST_FIRSTNAME);
		builder.append(", RES_GUEST_LASTNAME=");
		builder.append(RES_GUEST_LASTNAME);
		builder.append(", RES_GUEST_EMAIL=");
		builder.append(RES_GUEST_EMAIL);
		builder.append(", RES_GUEST_PHONE=");
		builder.append(RES_GUEST_PHONE);
		builder.append(", RES_GUEST_COUNTRY=");
		builder.append(RES_GUEST_COUNTRY);
		builder.append(", PMT_G2RA=");
		builder.append(PMT_G2RA);
		builder.append(", ob=");
		builder.append(ob);
		builder.append(", RES_UDRA=");
		builder.append(Arrays.toString(RES_UDRA));
		builder.append("]");
		return builder.toString();
	}
}
