/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared;

import com.google.gwt.i18n.client.DateTimeFormat;
import java.util.Date;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

public enum Time {
	MILLISECOND (1),
	SECOND   (1000),
	MINUTE   (60*1000),
	HOUR    (60*60*1000),
	DAY   (24*60*60*1000),
	WEEK  (7*24*60*60*1000),
	MONTH  (30*24*60*60*1000), //nominal
	QUARTER  (90*24*60*60*1000), //nominal
	YEAR  (52*7*24*60*60*1000);

	private long milliseconds;
	public static long SERVER_TZ_OFFSET = -120 * MINUTE.milliseconds();
//	public static long CLIENT_TZ_OFFSET = new Date().getTimezoneOffset() * MINUTE.milliseconds();
//	public static boolean isServerTZ() {return CLIENT_TZ_OFFSET == SERVER_TZ_OFFSET;}
	public static int SUNDAY = 0;
	public static int MONDAY = 1;
	public static int TUESDAY = 2;
	public static int WEDNESDAY = 3;
	public static int THURSDAY = 4;
	public static int FRIDAY = 5;
	public static int SATURDAY = 6;

	Time(int milliseconds) {
		this.milliseconds = milliseconds;
	}

	public long milliseconds()   { return milliseconds; }

	public static long fromInt(int time) {
		return fromInt(time, Time.MILLISECOND);
	}

	public static long fromInt(int time, Time unit) {
		return (long) time * unit.milliseconds();
	}

	public static long fromDouble(double time) {
		return fromDouble(time, Time.MILLISECOND);
	}

	public static long fromDouble(double time, Time unit) {
		return (long) time * unit.milliseconds();
	}

	public static double toDouble(long time) {
		return toDouble(time , Time.MILLISECOND);
	}

	public static double toDouble(long time, Time unit) {
		return (double) time / unit.milliseconds();
	}

	/**
	 * Gets the enumerated Time unit from the specified UN/CEFACT unit.
	 * 
	 * @param unit the specified UN/CEFACT unit.
	 * @return the enumerated Time unit.
	 */
	public static Time getUnit(String unit){
		if (unit.equalsIgnoreCase(Unit.SEC)){ return Time.SECOND;}
		else if (unit.equalsIgnoreCase(Unit.MIN)){ return Time.MINUTE;}
		else if (unit.equalsIgnoreCase(Unit.HUR)){ return Time.HOUR;}
		else if (unit.equalsIgnoreCase(Unit.DAY)){ return Time.DAY;}
		else if (unit.equalsIgnoreCase(Unit.WEE)){ return Time.WEEK;}
		else if (unit.equalsIgnoreCase(Unit.MON)){ return Time.MONTH;}
		else if (unit.equalsIgnoreCase(Unit.ANN)){ return Time.YEAR;}
		return Time.MILLISECOND;
	}

	/**
	 * Checks if the specified dates are on the same UTC day.
	 * 
	 * @param one the date of one day.
	 * @param other the date of other day.
	 * @return true if parameters are same UTC day.
	 */
	public static boolean isSameDay(Date one, Date other) {
		return (one == other) || getDay(one) == getDay(other);
	}

	/**
	 * Gets the UTC day for the specified date.
	 * 
	 * @param date the specified date.
	 * @return the UTC day for the date.
	 */
	public static int getDay(Date date) {
		return date == null ? 0 : getDay(date.getTime());
	}

	/**
	 * Gets the UTC day for the specified time.
	 * 
	 * @param time the specified time (milliseconds).
	 * @return the UTC day for the date.
	 */
	public static int getDay(long time) {
		return (int)((time - SERVER_TZ_OFFSET)/DAY.milliseconds());
	}

	/**
	 * Gets the client day for the specified time.
	 * 
	 * @param date the specified date.
	 * @return the client day for the date.
	 */
	public static int getClientDay(Date date) {
		return (int)(date == null ? 0 : date.getTime()/DAY.milliseconds());
	}

	/**
	 * Gets the UTC month for the specified date.
	 * 
	 * @param date the specified date.
	 * @return the UTC month for the date.
	 */
	public static int getMonth(Date date) {
		return date.getYear() * 12 + date.getMonth();
	}

	/**
	 * Gets the UTC date for the specified day.
	 * 
	 * @param day the specified day.
	 * @return the UTC date for the day.
	 */
	public static Date getDate(int day) {
		Date date = new Date();
		date.setTime(day * DAY.milliseconds() + SERVER_TZ_OFFSET);
		return date;
	}

	/**
	 * Gets the client date for the specified day.
	 * 
	 * @param day the specified day.
	 * @return the client date for the day.
	 */
	public static Date getClientDate(int day) {
		Date date = new Date();
		date.setTime(day * DAY.milliseconds());
		return date;
	}

	/**
	 * Gets the start of the current day.
	 * 
	 * @return the start of the day.
	 */
	public static Date getDateStart() {
		return getDateStart(new Date());
	}

	/**
	 * Gets the start of the specified date.
	 * 
	 * @param date the specified date.
	 * @return the start of the day.
	 */
	public static Date getDateStart(Date date) {
		int year = date.getYear();
		int month = date.getMonth();
		int day = date.getDate();
		return new Date(year, month, day);
	}

	/**
	 * Gets the end of the current day.
	 * 
	 * @return the end of the day.
	 */
	public static Date getDateEnd() {
		return getDateEnd(new Date());
	}

	/**
	 * Gets the end of the specified date.
	 * 
	 * @param date the specified date.
	 * @return the end of the day.
	 */
	public static Date getDateEnd(Date date) {
		int day = getDay(date) + 1;
		date.setTime(day * DAY.milliseconds() + SERVER_TZ_OFFSET - 1);
		return date;
	}

	/**
	 * Gets the start of the current week.
	 * 
	 * @return the start of the week.
	 */
	public static Date getWeekStart() {
		return getWeekStart(new Date());
	}

	/**
	 * Gets the start of the specified week.
	 * 
	 * @param date the specified date.
	 * @return the start of the week.
	 */
	public static Date getWeekStart(Date date) {
		return addDuration(date, 1 - date.getDay(), DAY);
	}

	/**
	 * Gets the end of the current week.
	 * 
	 * @return the end of the week.
	 */
	public static Date getWeekEnd() {
		return getWeekEnd(new Date());
	}

	/**
	 * Gets the end of the specified week.
	 * 
	 * @param date the specified date.
	 * @return the end of the week.
	 */
	public static Date getWeekEnd(Date date) {
		return addDuration(date, 7 - date.getDay(), DAY);
	}

	/**
	 * Gets the start of the current month.
	 * 
	 * @return the start of the month.
	 */
	public static Date getMonthStart() {
		return getMonthStart(new Date());
	}

	/**
	 * Gets the start of the specified month.
	 * 
	 * @param date the specified date.
	 * @return the start of the month.
	 */
	public static Date getMonthStart(Date date) {
		return new Date(date.getYear(), date.getMonth(), 1);
	}

	/**
	 * Gets the end of the current month.
	 * 
	 * @return the end of the month.
	 */
	public static Date getMonthEnd() {
		return getMonthEnd(new Date());
	}

	/**
	 * Gets the end of the specified month.
	 * 
	 * @param date the specified date.
	 * @return the end of the month.
	 */
	public static Date getMonthEnd(Date date) {
		return addDuration(new Date(date.getYear(), date.getMonth() + 1, 1), -1);
	}

	/**
	 * Gets the start of the current quarter.
	 * 
	 * @return the start of the quarter.
	 */
	public static Date getQuarterStart() {
		return getQuarterStart(new Date());
	}

	/**
	 * Gets the start of the specified quarter.
	 * 
	 * @param date the specified date.
	 * @return the start of the quarter.
	 */
	public static Date getQuarterStart(Date date) {
		int quarter = date.getMonth() / 3;
		return new Date(date.getYear(), quarter * 3, 1);
	}

	/**
	 * Gets the end of the current quarter.
	 * 
	 * @return the end of the quarter.
	 */
	public static Date getQuarterEnd() {
		return getQuarterEnd(new Date());
	}

	/**
	 * Gets the end of the specified quarter.
	 * 
	 * @param date the specified date.
	 * @return the end of the quarter.
	 */
	public static Date getQuarterEnd(Date date) {
		int quarter = date.getMonth() / 3;
		return addDuration(new Date(date.getYear(), (quarter + 1) * 3, 1), -1);
	}

	/**
	 * Gets the start of the current year.
	 * 
	 * @return the start of the year.
	 */
	public static Date getYearStart() {
		return getYearStart(new Date());
	}

	/**
	 * Gets the start of the specified year.
	 * 
	 * @param date the specified date.
	 * @return the start of the year.
	 */
	public static Date getYearStart(Date date) {
		return new Date(date.getYear(), 0, 1);
	}

	/**
	 * Gets the end of the current year.
	 * 
	 * @return the end of the year.
	 */
	public static Date getYearEnd() {
		return getYearEnd(new Date());
	}

	/**
	 * Gets the end of the specified year.
	 * 
	 * @param date the specified date.
	 * @return the end of the year.
	 */
	public static Date getYearEnd(Date date) {
		return addDuration(new Date(date.getYear() + 1, 0, 1), -1);
	}

	/**
	 * Gets the client date of the specified server date.
	 * 
	 * @param serverdate the specified server date.
	 * @return the client date.
	 */
	public static Date getDateClient(Date serverdate) {
		if (serverdate == null) {return null;}
		long CLIENT_TZ_OFFSET = serverdate.getTimezoneOffset() * MINUTE.milliseconds();
		Date clientdate = new Date();
		clientdate.setTime(serverdate.getTime() - SERVER_TZ_OFFSET + CLIENT_TZ_OFFSET);
		return clientdate;
	}

	/**
	 * Gets the server date of the specified client date.
	 * 
	 * @param clientdate the specified client date.
	 * @return the server date.
	 */
	public static Date getDateServer(Date clientdate) {
		if (clientdate == null) {return null;}
		long CLIENT_TZ_OFFSET = clientdate.getTimezoneOffset() * MINUTE.milliseconds();
		Date serverdate = new Date();
		serverdate.setTime(clientdate.getTime() + SERVER_TZ_OFFSET - CLIENT_TZ_OFFSET);
		return serverdate;
	}

	/**
	 * Gets the date of the specified date offset by the duration in days.
	 * 
	 * @param date the specified date.
	 * @param duration the integer duration in days.
	 * @return the offset date.
	 */
	public static Date addDuration(Date date, int duration) {
		return addDuration(date, duration, Time.MILLISECOND);
	}

	/**
	 * Gets the date of the specified date offset by the duration in units.
	 * 
	 * @param date the specified date.
	 * @param duration the integer duration in days.
	 * @param unit the time unit of the duration.
	 * @return the offset date.
	 */
	public static Date addDuration(Date date, int duration, Time unit) {
		if (date == null || unit == null) {return new Date();}
		else {return new Date(date.getTime() + (long) duration * unit.milliseconds);}
	}

	/**
	 * Gets the date of the specified date offset by the duration in days.
	 * 
	 * @param date the specified date.
	 * @param duration the double duration in days.
	 * @return the offset date.
	 */
	public static Date addDuration(Date date, double duration) {
		return addDuration(date, duration, Time.MILLISECOND);
	}

	/**
	 * Gets the date of the specified date offset by the duration in units.
	 * 
	 * @param date the specified date.
	 * @param duration the double duration in days.
	 * @param unit the string unit of the duration.
	 * @return the offset date.
	 */
	public static Date addDuration(Date date, double duration, String unit) {
		return addDuration(date, duration, getUnit(unit));
	}

	/**
	 * Gets the date of the specified date offset by the duration in units.
	 * 
	 * @param date the specified date.
	 * @param duration the double duration in days.
	 * @param unit the time unit of the duration.
	 * @return the offset date.
	 */
	public static Date addDuration(Date date, double duration, Time unit) {
		if (date == null){return null;}
		return new Date(date.getTime() + (long)duration * unit.milliseconds());
	}

	/**
	 * Gets the duration between the specified dates.
	 * 
	 * @param fromdate the prior date.
	 * @param todate the later date.
	 * @return duration between the specified dates.
	 */
	public static Double getDuration(Date fromdate, Date todate) {
		return getDuration(fromdate, todate, Time.MILLISECOND);
	}

	/**
	 * Gets the duration between the specified dates.
	 * 
	 * @param fromdate the prior date.
	 * @param todate the later date.
	 * @param unit the time unit of the duration.
	 * @return duration between the specified dates.
	 */
	public static Double getDuration(Date fromdate, Date todate, Time unit) {
		if (fromdate == null || todate == null) {return 0.0;}
		return Time.toDouble(todate.getTime() - fromdate.getTime(), unit);
	}

	/**
	 * Checks if the specified date is between the from and to dates inclusive.
	 * 
	 * @param fromdate the prior date.
	 * @param todate the later date.
	 * @param date the specified date.
	 * @return true, if the date is between the from and to dates inclusive.
	 */
	public static boolean isBetweenDates(Date fromdate, Date todate, Date date){
		if (fromdate == null || todate == null || date == null){return false;}
		else return !date.before(fromdate) && !date.after(todate);
	}
	/**
	 * Checks if the specified date is formatted in YYYY-MM-DD
	 * @param date
	 * @return true, if the date is formatted in YYYY-MM-DD.
	 */ 

	public static boolean isDateCorrectFormat(String date){
		return (date != null && date.length() == 10 &&  date.split("-").length == 3);
	}
	//return date != null && !date.isEmpty();
	
	public static String dateToString(Date value) {
		DateTimeFormat fmt = DateTimeFormat.getFormat("yyyy-MM-dd");
		return fmt.format(value);
	}
	
	/**
	 * Converts the string value to date by yyyy-MM-dd template.
	 * 
	 * @param value date to parse.
	 * @return parsed date.
	 * @throws ParseException string does not match the yyyy-MM-dd template. 
	 */
	public static Date stringToDate(String value) {
		return DateTimeFormat.getFormat("yyyy-MM-dd").parse(value);
	}
}
