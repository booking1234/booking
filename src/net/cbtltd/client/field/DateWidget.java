/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.Date;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * The Class DateWidget displays a calendar month in grid format, highlighting the current day.
 *
 * @param <T> the generic type
 */
public abstract class DateWidget<T>
extends AbstractField<T>
implements ChangeHandler {

	private VerticalPanel panel = new VerticalPanel();
	private final Navigator navigator = new Navigator();
	private static final DateTimeFormat EF = DateTimeFormat.getFormat("EEE");
	private static final DateTimeFormat MF = DateTimeFormat.getFormat("MMMM yyyy");
	private static final DateTimeFormat DNF = DateTimeFormat.getFormat("d");
	protected Grid daysGrid;
	protected Date displayedMonth;

	/**
	 * Instantiates a new date widget.
	 */
	public DateWidget(){
		initWidget(panel);
		setStylePrimaryName(CSS.cbtDateWidget());
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		daysGrid = new Grid(7, 7);

		//-----------------------------------------------
		// Month panel
		//-----------------------------------------------
		drawWeekLine(panel);
		drawDayGrid(panel);

		navigator.addChangeHandler(this);
		navigator.setAbsolute(true);
		panel.add(navigator);
		
		setDisplayedMonth(new Date());
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ChangeHandler#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	public void onChange(ChangeEvent event) {
		int index = navigator.getIndex();
		if (index == 0) {changeMonth(-12);}
		else if (index == 1) {changeMonth(-1);}
		else if (index == 2) {changeMonth(1);}
		else if (index == 3) {changeMonth(12);}
		else {Window.alert("No Month");}
		fireChange(this);
	}
	
	/**
	 * Display the current month.
	 */
	public void displayMonth() {
		if (this.displayedMonth == null) {this.displayedMonth = new Date();}
		navigator.setLabel(MF.format(this.displayedMonth));
		this.drawDaysGridContent(this.displayedMonth);
	}

	/* 
	 * Draw the calendar week line.
	 * 
	 * @param panel containing the calendar
	 */
	private void drawWeekLine(Panel panel) {
		Grid weekLine = new Grid(1, 7);
		weekLine.setStyleName(CSS.cbtDateWidgetWeekLine());
		weekLine.addStyleName(CSS.cbtGradientHead());
		Date weekFirstday = getWeekFirstDay();
		for (int i = 0; i < 7; i++) {weekLine.setText(0, i, EF.format(addDays(weekFirstday, i)));}
		panel.add(weekLine);
	}

	/*
	 * Draw the day grid.
	 * 
	 * @param panel containing the calendar
	 */
	private void drawDayGrid(Panel panel) {
		this.daysGrid.addTableListener(new TableListener() {
			public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
				Date date = addDays(getDaysGridOrigin(displayedMonth), row * 7 + cell);
				cellClicked(date, row, cell);
			};
		});
		daysGrid.setStyleName(CSS.cbtDateWidgetDayGrid());
		panel.add(daysGrid);
	}

	/**
	 * Handle cell clicked.
	 *
	 * @param date the date of the clicked cell.
	 * @param row the calendar row clicked.
	 * @param cell the calendar column clicked.
	 */
	protected void cellClicked(Date date, int row, int cell) {
		//override
	}
	
	/*
	 * Draw the days into the days grid including a few days after and before the displayed month.
	 * 
	 * @param displayedMonth Date of the displayed month
	 */
	private void drawDaysGridContent(Date displayedMonth) {
		CellFormatter formatter = daysGrid.getCellFormatter();
		Date cursor = this.getDaysGridOrigin(displayedMonth);
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				daysGrid.setText(i, j, DNF.format(cursor));
				removeStyleNames(i, j, formatter);
				if (areEquals(new Date(), cursor)) {
					if (displayedMonth.getMonth() == cursor.getMonth()) {formatter.addStyleName(i, j, CSS.cbtDateWidgetCurrentMonthSelected());}
					else {formatter.addStyleName(i, j, CSS.cbtDateWidgetSelected());}
				}
				else if (cursor.before(new Date())) {formatter.addStyleName(i, j, CSS.cbtDateWidgetPast());}
				else if (displayedMonth.getMonth() == cursor.getMonth()) {formatter.addStyleName(i, j, CSS.cbtDateWidgetCurrentMonthOtherDay());}
				else {formatter.addStyleName(i, j, CSS.cbtDateWidgetOtherDay());}
				cursor = addDays(cursor, 1);
			}
		}
	}

	/*
	 * Remove the cell CSS styles.
	 *
	 * @param row the row from which to remove the styles.
	 * @param col the column from which to remove the styles.
	 * @param formatter the cell formatter to be used for formatting.
	 */
	private void removeStyleNames(int row, int col, CellFormatter formatter) {
		formatter.removeStyleName(row, col, CSS.cbtDateWidgetPast());
		formatter.removeStyleName(row, col, CSS.cbtDateWidgetSelected());
		formatter.removeStyleName(row, col, CSS.cbtDateWidgetCurrentMonthSelected());
		formatter.removeStyleName(row, col, CSS.cbtDateWidgetOtherDay());
		formatter.removeStyleName(row, col, CSS.cbtDateWidgetCurrentMonthOtherDay());
		formatter.removeStyleName(row, col, CSS.cbtDateWidgetWeekEnd());
		formatter.removeStyleName(row, col, CSS.cbtDateWidgetCurrentMonthWeekEnd());
		formatter.removeStyleName(row, col, CSS.cbtDateWidgetInitial());
		formatter.removeStyleName(row, col, CSS.cbtDateWidgetNotAvailable());
	}

	/**
	 * Change the displayed month.
	 * 
	 * @param months to add to the displayed month.
	 */
	protected void changeMonth(int months) {
		this.displayedMonth = addMonths(getMonthFirstDay(), months);
		displayMonth();
	}

	/**
	 * Return the first day to display. If the month first day is after the 5th
	 * day of the week, it return the first day of the week. 
	 * Else, it returns the first day of the week before.
	 *
	 * @param displayedMonth the displayed month.
	 * @return The first day to display in the grid.
	 */
	protected Date getDaysGridOrigin(Date displayedMonth) {
		int currentYear = displayedMonth.getYear();
		int currentMonth = displayedMonth.getMonth();
		CellFormatter cfJours = daysGrid.getCellFormatter();
		Date monthFirstDay = new Date(currentYear, currentMonth, 1);
		int indice = getWeekDayIndex(monthFirstDay);
		Date origineTableau;
		if (indice > 4) {origineTableau = getWeekFirstDay(monthFirstDay);}
		else {origineTableau = getWeekFirstDay(addDays(monthFirstDay, -7));}
		return origineTableau;
	}

	/**
	 * Add days to date.
	 *
	 * @param date the date to which days are to be added.
	 * @param days the number of days to add.
	 * @return the new date.
	 */
	public static Date addDays(Date date, int days) {
		return new Date(date.getYear(), date.getMonth(), date.getDate() + days);
	}

	/**
	 * Add months to date.
	 *
	 * @param date the date to which months are to be added.
	 * @param months the number of months to add.
	 * @return the new date
	 */
	public static Date addMonths(Date date, int months) {
		return new Date(date.getYear(), date.getMonth() + months, date.getDate());
	}

	/**
	 * Test if dates are the same.
	 *
	 * @param date1 the first date.
	 * @param date2 the other date.
	 * @return true if the dates are the same.
	 */
	public static boolean areEquals(Date date1, Date date2) {
		return date1.getDate() == date2.getDate()
				&& date1.getMonth() == date2.getMonth()
				&& date1.getYear() == date2.getYear();
	}

	/**
	 * Return the first date of the month displayed by this date widget.
	 *
	 * @return The first day of the month.
	 */
	public Date getMonthFirstDay() {
		return getMonthFirstDay(displayedMonth);
	}
	
	/**
	 * Return the first date of the month containing the specified date.
	 *
	 * @param date the date in the month.
	 * @return The first day of the month.
	 */
	public static Date getMonthFirstDay(Date date) {
		Date current = date;
		while (current.getDate() != 1) {
			current = new Date(current.getYear(), current.getMonth(), current
					.getDate() - 1);
		}
		return current;
	}

	/**
	 * Returns the index of the day in the week.
	 * Example : sunday = 0, monday = 1 ....
	 *
	 * @param day the day of the week.
	 * @return The index of the day.
	 */
	public static int getWeekDayIndex(Date day) {
		//DateLocale locale = (DateLocale) GWT.create(DateLocale.class);
		//int[] daysOrder = locale.getDAY_ORDER();
		int[] daysOrder = { 1, 2, 3, 4, 5, 6, 0 };
		int dayIndex = day.getDay();
		for (int i = 0; i < 7; i++) {
			if (dayIndex == daysOrder[i]) {return i;}
		}
		return -1;
	}

	/**
	 * Returns the first day of the current week.
	 * @return date of the first day.
	 */
	public static Date getWeekFirstDay() {
		return getWeekFirstDay(new Date());
	}

	/**
	 * Returns the first day of the week containing date.
	 *
	 * @param date the date in the week.
	 * @return date of the first day.
	 */
	public static Date getWeekFirstDay(Date date) {
		Date current = date;
		//DateLocale local = (DateLocale) GWT.create(DateLocale.class);
		int[] daysOrder = { 1, 2, 3, 4, 5, 6, 0 };
		//int firstDay = local.getDAY_ORDER()[0];
		int firstDay = daysOrder[0];
		while (current.getDay() != firstDay) {
			current = new Date(current.getYear(), current.getMonth(), current
					.getDate() - 1);
		}
		return current;
	}

	/**
	 * Return if the date is in a week end.
	 * @param date the date
	 * @return true, if the date is in a week end.
	 */
	public static boolean isInWeekEnd(Date date) {
		int dayIndex = date.getDay();
		return (dayIndex == 0 | dayIndex == 6) ? true : false;
	}
	
	/**
	 * Gets the displayed month.
	 *
	 * @return the displayed month.
	 */
	public Date getDisplayedMonth() {
		return displayedMonth;
	}

	/**
	 * Sets the displayed month.
	 *
	 * @param displayedMonth the new displayed month.
	 */
	public void setDisplayedMonth(Date displayedMonth) {
		this.displayedMonth = displayedMonth;
		displayMonth();
	}
}

