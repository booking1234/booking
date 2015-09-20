/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.datepicker;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

/** Model used to get calendar information for {@link DatePicker} and its subclasses. */
@SuppressWarnings(/* Required to use Date API in gwt */{"deprecation"})
public class CalendarModel {

	/** The number of weeks normally displayed in a month. */
	public static final int WEEKS_IN_MONTH = 6;

	/** Number of days normally displayed in a week. */
	public static final int DAYS_IN_WEEK = 7;

	private static final String[] dayOfWeekNames = new String[7];
	private static String[] dayOfMonthNames = new String[32];
	private final Date currentMonth;

	/** Instantiates a new calendar model. */
	public CalendarModel() {
		currentMonth = new Date();

		CalendarUtil.setToFirstDayOfMonth(currentMonth);

		// Finding day of week names
		Date date = new Date();
		for (int i = 1; i <= 7; i++) {
			date.setDate(i);
			int dayOfWeek = date.getDay();
			dayOfWeekNames[dayOfWeek] = getDayOfWeekFormatter().format(date);
		}

		// Finding day of month names
		date.setMonth(0);

		for (int i = 1; i < 32; ++i) {
			date.setDate(i);
			dayOfMonthNames[i] = getDayOfMonthFormatter().format(date);
		}
	}

	/**
	 * Formats the current month name.
	 * 
	 * @return the formatted month name.
	 */
	public String formatCurrentMonth() {
		return getMonthAndYearFormatter().format(currentMonth);
	}

	/**
	 * Formats a specified date's day of month.
	 * 
	 * @param date the specified date.
	 * @return the day of the month.
	 */
	public String formatDayOfMonth(Date date) {
		return dayOfMonthNames[date.getDate()];
	}

	/**
	 * Format a day in the week, for example "Monday".
	 * 
	 * @param dayInWeek the day in the week to format.
	 * @return the formatted day in the week.
	 */
	public String formatDayOfWeek(int dayInWeek) {
		return dayOfWeekNames[dayInWeek];
	}

	/**
	 * Gets the first day of the first week in the current month.
	 * 
	 * @return the first day.
	 */
	public Date getCurrentFirstDayOfFirstWeek() {
		int wkDayOfMonth1st = currentMonth.getDay();
		int start = CalendarUtil.getStartingDayOfWeek();
		if (wkDayOfMonth1st == start) {
			// always return a copy to allow SimpleCalendarView to adjust first
			// display date
			return new Date(currentMonth.getTime());
		} else {
			Date d = new Date(currentMonth.getTime());
			int offset = wkDayOfMonth1st - start > 0 ? wkDayOfMonth1st - start
					: DAYS_IN_WEEK - (start - wkDayOfMonth1st);
			CalendarUtil.addDaysToDate(d, -offset);
			return d;
		}
	}

	/**
	 * Gets the date representation of the current month.
	 * 
	 * @return the date representation of the current month.
	 */
	public Date getCurrentMonth() {
		return currentMonth;
	}

	/**
	 * Checks if the specified date is in the current month.
	 * 
	 * @param date the specified date.
	 * @return true, if the specified date is in the current month.
	 */
	public boolean isInCurrentMonth(Date date) {
		return currentMonth.getMonth() == date.getMonth();
	}

	/**
	 * Sets the current month and year for the specified date.
	 * 
	 * @param date the specified date.
	 */
	public void setCurrentMonth(Date date) {
		this.currentMonth.setYear(date.getYear());
		this.currentMonth.setMonth(date.getMonth());
	}

	/**
	 * Shifts the current date by the given number of months. The day
	 * of the month will be pinned to the original value as far as possible.
	 * 
	 * @param deltaMonths - number of months to be added to the current date
	 */
	public void shiftCurrentMonth(int deltaMonths) {
		CalendarUtil.addMonthsToDate(currentMonth, deltaMonths);
		refresh();
	}

	/**
	 * Gets the date of month formatter.
	 * 
	 * @return the day of month formatter
	 */
	protected DateTimeFormat getDayOfMonthFormatter() {
		return DateTimeFormat.getFormat("d");
	}

	/**
	 * Gets the day of week formatter.
	 * 
	 * @return the day of week formatter
	 */
	protected DateTimeFormat getDayOfWeekFormatter() {
		return DateTimeFormat.getFormat("ccccc");
	}

	/**
	 * Gets the month and year formatter.
	 * 
	 * @return the month and year formatter
	 */
	protected DateTimeFormat getMonthAndYearFormatter() {
		return DateTimeFormat.getFormat("MMM yyyy");
	}

	/** Refresh the current model as needed. */
	protected void refresh() {}

}
