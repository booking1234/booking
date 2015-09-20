package net.cbtltd.shared.finance.gateway.dibs.model;

import java.util.HashMap;
import java.util.Map;

public abstract class DibsResponseUtil {
	protected static final Map<Integer, String> responseErrors; 
	
	static {
		responseErrors = new HashMap<Integer, String>();
		responseErrors.put(0, "Rejected by acquirer.");
		responseErrors.put(1, "Communication problems.");
		responseErrors.put(2, "Error in the parameters sent to the DIBS server. An additional parameter called \"message\" is returned, with a value that may help identifying the error.");
		responseErrors.put(3, "Error at the acquirer.");
		responseErrors.put(4, "Credit card expired.");
		responseErrors.put(5, "Your shop does not support this credit card type, the credit card type could not be identified, or the credit card number was not modulus correct.");
		responseErrors.put(6, "Instant capture failed.");
		responseErrors.put(7, "The order number (orderid) is not unique.");
		responseErrors.put(8, "There number of amount parameters does not correspond to the number given in the split parameter.");
		responseErrors.put(9, "Control numbers (cvc) are missing.");
		responseErrors.put(10, "The credit card does not comply with the credit card type.");
		responseErrors.put(11, "Declined by DIBS Defender.");
		responseErrors.put(20, "Cancelled by user at 3D Secure authentication step.");
	}
	
	public static String getReasonText(Integer reason) {
		return responseErrors.get(reason);
	}
}
