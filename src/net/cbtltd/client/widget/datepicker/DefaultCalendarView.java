/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.datepicker;

import java.util.Date;

import net.cbtltd.client.widget.datepicker.DefaultCalendarView.CellGrid.DateCell;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;

/** The Class DefaultCalendarView is a simple calendar view. */

@SuppressWarnings(/* Date manipulation required */{"deprecation"})
public final class DefaultCalendarView extends CalendarView {

	/**
	 * Cell grid.
	 * Javac bug requires that date be fully specified here.
	 */
	class CellGrid extends CellGridImpl<java.util.Date> {
		/**
		 * A cell representing a date.
		 */
		class DateCell extends Cell {
			private String cellStyle;
			private String dateStyle;

			DateCell(Element td, boolean isWeekend) {
				super(td, new Date());
				cellStyle = "DatePickerDay";
				if (isWeekend) {cellStyle += " " + "DatePickerDayIsWeekend";}
			}

			@Override
			public void addStyleName(String styleName) {
				if (dateStyle.indexOf(" " + styleName + " ") == -1) {dateStyle += styleName + " ";}
				updateStyle();
			}

			public boolean isFiller() {
				return !getModel().isInCurrentMonth(getValue());
			}

			@Override
			public void onHighlighted(boolean highlighted) {
				setHighlightedDate(getValue());
				updateStyle();
			}

			@Override
			public void onSelected(boolean selected) {
				if (selected) {
					getDatePicker().setValue(getValue(), true);
					if (isFiller()) {getDatePicker().setCurrentMonth(getValue());}
				}
				updateStyle();
			}

			@Override
			public void removeStyleName(String styleName) {
				dateStyle = dateStyle.replace(" " + styleName + " ", " ");
				updateStyle();
			}

			public void update(Date current) {
				setEnabled(true);
				getValue().setTime(current.getTime());
				String value = getModel().formatDayOfMonth(getValue());
				setText(value);
				dateStyle = cellStyle;
				if (isFiller()) {dateStyle += " " + "DatePickerDayIsFiller";} 
				else {
					String extraStyle = getDatePicker().getStyleOfDate(current);
					if (extraStyle != null) {
						dateStyle += " " + extraStyle;
					}
				}
				// We want to certify that all date styles have " " before and after
				// them for ease of adding to and replacing them.
				dateStyle += " ";
				updateStyle();
			}

			@Override
			public void updateStyle() {
				String accum = dateStyle;

				if (isHighlighted()) {
					accum += " " + "DatePickerDayIsValueAndHighlighted";
					if (isHighlighted() && isSelected()) {accum += " " + "DatePickerDayIsValueAndHighlighted";}
				}
				if (!isEnabled()) {accum += " " + "DatePickerDayIsDisabled";}
				setStyleName(accum);
			}

			private void setText(String value) {
				DOM.setInnerText(getElement(), value);
			}
		}

		CellGrid() {
			resize(CalendarModel.WEEKS_IN_MONTH + 1, CalendarModel.DAYS_IN_WEEK);
		}

		@Override
		protected void onSelected(Cell lastSelected, Cell cell) {
		}
	}

	private CellGrid grid = new CellGrid();

	private Date firstDisplayed;

	private Date lastDisplayed = new Date();

	/** Instantiates new default calendar view instance. */
	public DefaultCalendarView() {}

	@Override
	public void addStyleToDate(String styleName, Date date) {
		assert getDatePicker().isDateVisible(date) : "You tried to add style " + styleName + " to "
				+ date + ". The calendar is currently showing " + getFirstDate()
				+ " to " + getLastDate();
		getCell(date).addStyleName(styleName);
	}

	@Override
	public Date getFirstDate() {
		return firstDisplayed;
	}

	@Override
	public Date getLastDate() {
		return lastDisplayed;
	}

	@Override
	public boolean isDateEnabled(Date d) {
		return getCell(d).isEnabled();
	}

	@Override
	public void refresh() {
		firstDisplayed = getModel().getCurrentFirstDayOfFirstWeek();

		if (firstDisplayed.getDate() == 1) {
			// show one empty week if date is Monday is the first in month.
			CalendarUtil.addDaysToDate(firstDisplayed, -7);
		}

		lastDisplayed.setTime(firstDisplayed.getTime());

		for (int i = 0; i < grid.getNumCells(); i++) {
			if (i != 0) {
				CalendarUtil.addDaysToDate(lastDisplayed, 1);
			}
			DateCell cell = (DateCell) grid.getCell(i);
			cell.update(lastDisplayed);
		}
	}

	@Override
	public void removeStyleFromDate(String styleName, Date date) {
		getCell(date).removeStyleName(styleName);
	}

	@Override
	public void setEnabledOnDate(boolean enabled, Date date) {
		getCell(date).setEnabled(enabled);
	}

	@Override
	public void setup() {
		// Preparation
		CellFormatter formatter = grid.getCellFormatter();
		int weekendStartColumn = -1;
		int weekendEndColumn = -1;

		// Set up the day labels.
		for (int i = 0; i < CalendarModel.DAYS_IN_WEEK; i++) {
			int shift = CalendarUtil.getStartingDayOfWeek();
			int dayIdx = i + shift < CalendarModel.DAYS_IN_WEEK ? i + shift : i
					+ shift - CalendarModel.DAYS_IN_WEEK;
			grid.setText(0, i, getModel().formatDayOfWeek(dayIdx));

			if (CalendarUtil.isWeekend(dayIdx)) {
				formatter.setStyleName(0, i, "DatePickerWeekendLabel");
				if (weekendStartColumn == -1) {
					weekendStartColumn = i;
				} else {
					weekendEndColumn = i;
				}
			} else {
				formatter.setStyleName(0, i, "DatePickerWeekdayLabel");
			}
		}

		// Set up the calendar grid.
		for (int row = 1; row <= CalendarModel.WEEKS_IN_MONTH; row++) {
			for (int column = 0; column < CalendarModel.DAYS_IN_WEEK; column++) {
				// set up formatter.
				Element e = formatter.getElement(row, column);
				grid.new DateCell(e, column == weekendStartColumn
						|| column == weekendEndColumn);
			}
		}
		initWidget(grid);
		grid.setStyleName("DatePickerDays");
	}

	private DateCell getCell(Date d) {
		int index = CalendarUtil.getDaysBetween(firstDisplayed, d);
		assert (index >= 0);

		DateCell cell = (DateCell) grid.getCell(index);
		if (cell.getValue().getDate() != d.getDate()) {
			throw new IllegalStateException(d + " cannot be associated with cell "
					+ cell + " as it has date " + cell.getValue());
		}
		return cell;
	}
}
