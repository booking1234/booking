/**
 * 
 */
package net.cbtltd.rest.homeaway;

import java.util.Map;

import net.cbtltd.rest.common.utils.CommonUtils;

/**
 * @author Suresh Kumar S
 * 
 */
public class EscapiaNetUtils extends CommonUtils {

	public static String US_COUNTRY_CODE = "US";
	public static String BED_ROOM = "Bedroom";
	public static String BATH_ROOM = "Bathroom";
	public static String STANDARD = "Standard";
	public static String THUMBNAIL = "Thumbnail";
	
	public static String ESCAPIA_WSDL_URL="bp.homeaway.escapia.wsdl.url";
	public static String ESCAPIA_USER="bp.homeaway.escapia.username";
	public static String ESCAPIA_PWD="bp.homeaway.escapia.pwd";
	
	public static final String CURRENCY = "USD";
	public static final String MONTH_DAY_YEAR = "MM/dd/yyyy";
	
	public static Map<String,String> escapiaNetAttributes = attributes;
	
	public enum AvailabilityType{ AVAILABLE, CHECKIN, CHECKOUT };
	
	public static Map<String, String> getATTRIBUTES() {
		return escapiaNetAttributes;
	}
}
