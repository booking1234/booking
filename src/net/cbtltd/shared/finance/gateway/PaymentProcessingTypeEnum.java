package net.cbtltd.shared.finance.gateway;


/**
 * Payment Processing Type Enum.
 */
public enum PaymentProcessingTypeEnum {

	GATEWAY(1),		/* Payment gateway selected (BookingPal, PayPal, Authorize .NET). */
	Mail(2),		/* Booking confirmations via email. */
	API(3),			/* API(PMS support credit card processing); */
	ACH(4);

	private Integer type;
	
	/**
	 * Instantiates a new payment processing type enum.
	 *
	 * @param type the type
	 */
	PaymentProcessingTypeEnum(Integer type) {
		this.type = type;
	}
	
	/**
	 * Integer value of element. 
	 *
	 * @return the integer
	 */
	public Integer type() {return type;}
	
	/**
	 * Gets the element by Integer.
	 *
	 * @param type
	 * @return element by Integer.
	 */
	public static PaymentProcessingTypeEnum getByInt(Integer type) {
		switch(type) {
		case 1 : return GATEWAY;
		case 2 : return Mail;
		case 3 : return API;
		case 0 : return ACH;
		default : return null;
		}
	}
	
}
