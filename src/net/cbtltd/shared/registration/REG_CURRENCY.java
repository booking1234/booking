package net.cbtltd.shared.registration;

import java.util.HashMap;
import java.util.Map;

public enum REG_CURRENCY {
			AUSTRALIAN(1, "AUD"), BRITISH(2, "GBP"), Canadian(3, "CAD"), Euro(4, "EUR"), SOUTHAFRICAN(5, "ZAR"), US(6, "USD");
	
	private int key;
	private String label;
	
	public int getKey() {
		return key;
	}

	public String getLabel() {
		return label;
	}
	
	private REG_CURRENCY(int code, String currency) {
		this.key = code;
		this.label = currency;
	}
	
	/**
	 * A mapping between the integer code and its corresponding currency  to
	 * facilitate lookup by code.
	 */
	private static Map<Integer, REG_CURRENCY> keyToCurrencyMapping;
	private static Map<String, REG_CURRENCY> currencyToKeyMapping;
	
		
	public static REG_CURRENCY getCurrency(int i) {
		if (keyToCurrencyMapping == null) {
			initMapping();
		}
		return keyToCurrencyMapping.get(i);
	}

	private static void initMapping() {
		keyToCurrencyMapping = new HashMap<Integer, REG_CURRENCY>();
		for (REG_CURRENCY s : values()) {
			keyToCurrencyMapping.put(s.key, s);
		}
	}
	
	public static REG_CURRENCY getCurrencyIndex(String c) {
		if (currencyToKeyMapping == null) {
			initMappingByCurrency();
		}
		return currencyToKeyMapping.get(c);
	}

	private static void initMappingByCurrency() {
		currencyToKeyMapping = new HashMap<String, REG_CURRENCY>();
		for (REG_CURRENCY s : values()) {
			currencyToKeyMapping.put(s.label, s);
		}
	}

	
	
	
}
