package net.cbtltd.shared.registration;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;


/**
 * Useful utilities for registration process.
 */

public class RegistrationUtils {

	public static Integer getInteger(String value){
		
		Integer result = null;
		try {
			if(StringUtils.isNumeric(value)){
				result = Integer.parseInt(value);
			}
		} catch (NumberFormatException e) {
			return null;
		}
		return result;
	}
	
	public static Double getDouble(String value){
		
		Double result = null;
		try {
			if (NumberUtils.isNumber(value)){
				result = Double.parseDouble(value);
			}
		} catch (NumberFormatException e) {
		    // string is not a number
		}
		return result;
	}
	
}
