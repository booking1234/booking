package net.cbtltd.rest.rent;

import net.cbtltd.server.ServiceException;
import net.cbtltd.shared.Error;


/**
 * Used for Rent gateway payment.
 */

public class RentPaymentUtils {
	
	private static final Integer PHONE_NUMBER_LENGHT = 10;
	
	public static void main(String[] args) throws Exception {
		
		//String number = RentPaymentUtils.validatePhoneNumber("3-23.12(1)");
		//System.out.println(number);
	}
	
	public RentPaymentUtils() {
		super();
	}

	/**
	 * Format phone number. Must be 10 digit. No dashes, parentheses, dots, etc
	 * @param phoneNumber
	 * @return phoneNumber
	 */	
	public static String validatePhoneNumber(String number){		
		number = number.replace("-", "").replace(".", "");
		number = number.replace("(", "").replace(")", "");
		if (PHONE_NUMBER_LENGHT != number.length()){
			throw new ServiceException(Error.number_format, "phonenumber");
		}
		return number;
	}

}
