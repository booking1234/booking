/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.TreeSet;

import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.HasComponents;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.reservation.AvailableItem;
import net.cbtltd.shared.reservation.AvailableWidget;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class CalendarWidget extends the DateWidget to display the availability of a property in a month grid.
 */
public class CalendarWidget
extends DateWidget<AvailableItem> {

	private AvailableItem value;
	private ArrayList<AvailableItem> bookeditems;
	private TreeSet<Date> dates = new TreeSet<Date>();
	private String productid;

	/* Create a timer to refresh the widget periodically. */
	private Timer refreshTimer = new Timer(){
		public void run(){;} //TODO
	};

	/* The action to refresh the widget given its product ID and first day of the target month. */
	private GuardedRequest<Table<AvailableItem>> availableWidget = new GuardedRequest<Table<AvailableItem>>() {
		protected boolean error() {return noProductId();}
		protected void send() {super.send(new AvailableWidget(getProductid(), getMonthFirstDay(), Time.addDuration(getMonthFirstDay(), 60, Time.DAY)));}
		protected void receive(Table<AvailableItem> response) {setAvailableItems(response == null ? null : response.getValue());}
	};

	/**
	 * Instantiates a new calendar widget.
	 *
	 * @param form is the form or other HasComponents element that contains the widget.
	 */
	public CalendarWidget(HasComponents form) {
		if (form != null) {
			form.addComponent(this);
			addChangeHandler(form);
			addClickHandler(form);
		}
		sinkEvents(Event.MOUSEEVENTS | Event.ONDBLCLICK | NativeEvent.BUTTON_RIGHT);
	}

	/**
	 * @param sender is the widget which sent the event.
	 * @return true if this widget sent the event.
	 */
	@Override
	public boolean is(Widget sender){return (sender == this);}

	/*
	 * Set the colour of the reserved cells from a list of available item objects.
	 * 
	 * @param items the list of available items.
	 */
	private void setAvailableItems(ArrayList<AvailableItem> items) {
		bookeditems = new ArrayList<AvailableItem>();
		if (items != null) {bookeditems.addAll(items);}
		if (value != null) {bookeditems.add(value);}
		Log.debug("setAvailableItems " + bookeditems);
		CellFormatter formatter = daysGrid.getCellFormatter();
		if (bookeditems == null || bookeditems.isEmpty()) {clear();}
		else {
			Collections.sort(bookeditems);
			Date cursor = getDaysGridOrigin(displayedMonth);
			int i = 0;
			int j = 0;
			for (AvailableItem item : bookeditems) {
				Date start = Time.getDateClient(item.getFromdate());
				Date end = Time.getDateClient(item.getTodate());
				if (start.after(Time.addDuration(cursor, 48, Time.DAY))) {return;}
				if (!end.before(cursor)) {
					while (cursor.before(start)) {
						cursor = Time.addDuration(cursor, 1, Time.DAY);
						j++;
						if (j >= 7) {i++; j = 0;}
						if (i > 6) {return;}
					}
					while (cursor.before(end)) {
//						if (item.noState()) {formatter.addStyleName(i, j, CSS.cbtDateWidgetInitial());}
//						else {formatter.addStyleName(i, j, CSS.cbtDateWidgetNotAvailable());}
						if (!item.noState()) {formatter.addStyleName(i, j, CSS.cbtDateWidgetNotAvailable());}
						cursor = Time.addDuration(cursor, 1, Time.DAY);
						j++;
						if (j >= 7) {i++; j = 0;}
						if (i > 6) {return;}
					}
				}
			}
		}
	}

	/**
	 * Clear the colour of all the cells in the widget.
	 */
	protected void clear() {
		CellFormatter formatter = daysGrid.getCellFormatter();
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				formatter.removeStyleName(i, j, CSS.cbtDateWidgetInitial());
				formatter.removeStyleName(i, j, CSS.cbtDateWidgetNotAvailable());
			}
		}
	}

	/**
	 * Change the displayed month.
	 * @param months to add to the displayed month, eg: 3 to go from October to January
	 */
	protected void changeMonth(int months) {
		super.changeMonth(months);
		availableWidget.execute();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.DateWidget#cellClicked(java.util.Date, int, int)
	 */
	@Override
	protected void cellClicked(Date date, int row, int cell) {
		if (dates.contains(date)) {
			daysGrid.getCellFormatter().removeStyleName(row, cell, CSS.cbtDateWidgetInitial());
			dates.remove(date);
		}
		else {
			daysGrid.getCellFormatter().addStyleName(row, cell, CSS.cbtDateWidgetInitial());
			dates.add(date);
		}
		value.setFromdate(dates.first());
		value.setTodate(Time.addDuration(dates.last(), 1, Time.DAY));
		super.fireChange();
	}

	/**
	 * Check if clicked cell is in the list of booked items.
	 *
	 * @param date of the clicked cell
	 * @return true, if the clicked cell is in the list of booked items.
	 */
	protected boolean inItems(Date date) {
		if (bookeditems == null || bookeditems.isEmpty()) {return false;}
		for (AvailableItem item : bookeditems) {if (date.compareTo(item.getFromdate()) >= 0 && date.compareTo(item.getTodate()) < 0) {return true;}}
		return false;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setFocus(boolean)
	 */
	@Override
	public void setFocus(boolean focused) {
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int index){}

	/**
	 * Refreshes the calendar for the product and date in the value.
	 * @param value the available item object having the product ID and from date to be displayed in the calendar.
	 */
	public void setValue(AvailableItem value) {
		this.value = value;
		if (value != null) {
			productid = value.getProductid();
			setDisplayedMonth(value.getFromdate());
			availableWidget.execute(true);
			dates.clear();
//			for (int day = Time.getDay(value.getFromdate()); day < Time.getDay(value.getTodate()); day++) {
//				dates.add(Time.getDateClient(Time.getDate(day)));
//			}
//			for (int day = Time.getClientDay(value.getFromdate()); day < Time.getClientDay(value.getTodate()); day++) {
//				dates.add(Time.getDateClient(Time.getClientDate(day)));
//			}
//			drawDays();
		}
		super.setChanged();
	}

	/**
	 * Draw the day cells in the calendar.
	 */
	private void drawDays() {
		if (dates.isEmpty()) {return;}
		CellFormatter formatter = daysGrid.getCellFormatter();
		Date cursor = getDaysGridOrigin(displayedMonth);
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				if (dates.contains(cursor)) {formatter.addStyleName(i, j, CSS.cbtDateWidgetInitial());}
				cursor = addDays(cursor, 1);
			}
		}
	}

	/**
	 * @return the selected available item.
	 */
	public AvailableItem getValue() {
		if (dates == null || dates.isEmpty()) {return null;}
		AvailableItem value = new AvailableItem();
		value.setProductid(productid);
		value.setFromdate(dates.first());
		value.setTodate(Time.addDuration(dates.last(), 1, Time.DAY));
		return value;
	}

	/**
	 * @return true if the widget has no value.
	 */
	public boolean noValue() {
		return getValue() == null;
	}

	/* @return the product ID if it exists. */
	private String getProductid() {
		if (productid != null && !productid.isEmpty()) {return productid;}
		else if (bookeditems != null && !bookeditems.isEmpty() && bookeditems.get(0).hasId()) {return bookeditems.get(0).getProductid();}
		else return null;
	}
	
	/* @return true if there is no product ID. */
	private boolean noProductId() {
		return productid == null || productid.isEmpty();
	}
}


