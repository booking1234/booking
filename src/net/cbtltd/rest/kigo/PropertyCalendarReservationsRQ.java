package net.cbtltd.rest.kigo;

public class PropertyCalendarReservationsRQ {
	private int PROP_ID;
	private String LIST_START_DATE;
	private String LIST_END_DATE;

	PropertyCalendarReservationsRQ () {}	//no args constructor

	public PropertyCalendarReservationsRQ(int PROP_ID, String LIST_START_DATE, String LIST_END_DATE) {
		super();
		this.PROP_ID = PROP_ID;
		this.LIST_START_DATE = LIST_START_DATE;
		this.LIST_END_DATE = LIST_END_DATE;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PropertyCalendarReservationsRQ [PROP_ID=");
		builder.append(PROP_ID);
		builder.append(", LIST_START_DATE=");
		builder.append(LIST_START_DATE);
		builder.append(", LIST_END_DATE=");
		builder.append(LIST_END_DATE);
		builder.append("]");
		return builder.toString();
	}
}
