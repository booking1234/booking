package net.cbtltd.server.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarUtil {
	/**
	 * Format date from one pattern to another.
	 * 
	 * @param fromFormat current date format
	 * @param toFormat desired date format
	 * @param dateToFormat date to be formatted
	 * @return formatted date
	 * @throws ParseException
	 */
	public static String formatDate(String fromFormat, String toFormat, String dateToFormat) throws ParseException {
		SimpleDateFormat fromDateFormatter = new SimpleDateFormat(fromFormat);
		Date date = fromDateFormatter.parse(dateToFormat);
		String dateFormatted = formatDate(toFormat, date);
		return dateFormatted;
	}
	
	/**
	 * Format date to specific pattern.
	 * 
	 * @param toFormat pattern to apply
	 * @param dateToFormat date to be formatted
	 * @return formatted date
	 */
	public static String formatDate(String toFormat, Date dateToFormat) {
		SimpleDateFormat toDateFormatter = new SimpleDateFormat(toFormat);
		String formattedDate = toDateFormatter.format(dateToFormat);
		return formattedDate;
	}
}
