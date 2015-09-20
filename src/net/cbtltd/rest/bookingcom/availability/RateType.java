package net.cbtltd.rest.bookingcom.availability;

/**
 * enum to hold all Ratetypes
 * @author nibodha
 *
 */
public enum  RateType {
	STANDARD_RATE(145473,"STANDARD RATE"),NON_REFUNDABLE_RATE(300917,"NON REFUNDABLE RATE"),SPECIAL_RATE(2349410,"SPECIAL RATE");
	 
	private Integer rateId;
	private String name;
 
	/**
	 * To construct RateType enum with rateid(s) and name
	 * @param s
	 * @param name
	 */
	private RateType(Integer s,String name) {
		this.rateId = s;
		this.name=name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the rateId
	 */
	public Integer getRateId() {
		return rateId;
	}
 
}
