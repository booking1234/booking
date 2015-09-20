package net.cbtltd.rest.bookingcom.reservation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * To hold meta info like types, price modes
 * @author nibodha
 *
 */
public class ReservationTypes {
	
	private static final Map<Integer, String> addOnTypes;
	
	static {
        Map<Integer, String> aTypes = new HashMap<Integer, String>();
        aTypes.put(1, "Breakfast");
        aTypes.put(2, "Continental breakfast");
        aTypes.put(3, "American breakfast");
        aTypes.put(4, "Buffet breakfast");
        aTypes.put(5, "Full english breakfast");
        aTypes.put(6, "Lunch");
        aTypes.put(7, "Dinner");
        aTypes.put(8, "Half board");
        aTypes.put(9, "Full board");
        aTypes.put(11, "Breakfast for Children");
        aTypes.put(12, "Continental breakfast for Children");
        aTypes.put(13, "American breakfast for Children");
        aTypes.put(14, "Buffet breakfast for Children");
        aTypes.put(15, "Full english breakfast for Children");
        aTypes.put(16, "Lunch for Children");
        aTypes.put(17, "Dinner for Children");
        aTypes.put(18, "Half board for Children");
        aTypes.put(19, "Full board for Children");
        aTypes.put(20, "WiFi");
        aTypes.put(21, "Internet");
        aTypes.put(22, "Parking space");
        aTypes.put(23, "Extrabed");
        aTypes.put(24, "Babycot ");
        addOnTypes = Collections.unmodifiableMap(aTypes);
    }
	
	private static final Map<Integer, String> priceModes;
	
	static {
		Map<Integer, String> aRates = new HashMap<Integer, String>();
		aRates.put(0, "Not applicable");
		aRates.put(1, "Per stay");
		aRates.put(2, "Per person per stay");
		aRates.put(3, "Per night");
		aRates.put(4, "Per person per night");
		aRates.put(5, "Percentage");
		aRates.put(6, "Per person per night restricted ");
		priceModes = Collections.unmodifiableMap(aRates);
	}
	
	/**
	 * @param type
	 * @return type of AddOn
	 */
	public static String getAddOnType(int type) {
		return addOnTypes.get(type);
	}
	
	/**
	 * @param mode
	 * @return price modes
	 */
	public static String getPriceModes(int mode) {
		return priceModes.get(mode);
	}

}
