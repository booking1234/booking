package net.cbtltd.shared.finance.gateway;

/**
 * Who hold the funds enum.
 */
public enum FundsHolderEnum {
	
	External(0), 	/* Another payment gateway selected (PayPal, Authorize .NET) or PMS support credit card processing */
	BookingPal(1); 	/* Bookingpal selected to process payments */

	private Integer value;
	
	FundsHolderEnum(Integer value) {
		this.value = value;
	}
	
	public Integer value() {return this.value;}
	public static FundsHolderEnum getByInt(Integer value) {
		switch(value) {
		case 0 : return External;
		case 1 : return BookingPal;
		default : return null;
		}
	}
	
}
