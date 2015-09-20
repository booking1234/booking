package net.cbtltd.shared.finance.gateway;

import java.util.HashMap;
import java.util.Map;

public enum CreditCardType {
	MASTER_CARD(),
	VISA(),
	AMERICAN_EXPRESS(),
	DINES_CLUB(),
	DISCOVER,
	JCB();
	
	private static final Map<Integer, CreditCardType> idToTypeMap = new HashMap<Integer, CreditCardType>();
	
	static {
		for(int index = 0; index < CreditCardType.values().length; index++) {
			CreditCardType creditCardType = CreditCardType.values()[index];
			idToTypeMap.put(index, creditCardType);
		}
	}
	
	public static CreditCardType get(Integer index) {
		return idToTypeMap.get(index);
	}
}
