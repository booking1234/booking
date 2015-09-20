/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.calendar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import net.cbtltd.client.Component;
import net.cbtltd.client.Components;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.resource.Hosts;
import net.cbtltd.client.widget.NameIdWidgetItems;
import net.cbtltd.client.widget.RazorWidget;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.License;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.reservation.AvailableItem;
import net.cbtltd.shared.reservation.AvailableWidget;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mybookingpal.config.RazorConfig;

/** The Class CalendarWidget is to display availability in a calendar format via a widget that can be hosted by a web page. */
public class CalendarWidget
extends VerticalPanel
implements ChangeHandler, HasComponents {

	private static final CalendarConstants CONSTANTS = GWT.create(CalendarConstants.class);
	private static final Hosts HOSTS = GWT.create(Hosts.class);
	private static final DateTimeFormat EF = DateTimeFormat.getFormat("EEE");
	private static final DateTimeFormat MF = DateTimeFormat.getFormat("MMMM yyyy");
	private static final DateTimeFormat DNF = DateTimeFormat.getFormat("d");
	private static final Image loader = new Image(AbstractField.BUNDLE.loader());
	private final Navigator navigator = new Navigator();
	private static ListField productField;
	private Grid daysGrid;
	private Date displayedMonth;
	private boolean rpc;
	private String pos;
	private static final Components COMPONENTS = new Components();

	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasComponents#addComponent(net.cbtltd.client.Component)
	 */
	public void addComponent(Component component) {COMPONENTS.add(component);}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasComponents#onRefresh()
	 */
	public void onRefresh() {COMPONENTS.onRefresh();}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasComponents#hasChanged()
	 */
	public boolean hasChanged() {return COMPONENTS.hasChanged();}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasComponents#onReset(java.lang.String)
	 */
	public void onReset(String state) {COMPONENTS.onReset();}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	public void onClick(ClickEvent click) {}

	/* A timer to schedule periodic refreshes og the widget. */
	private final Timer refreshTimer = new Timer() {
		public void run() {getCalendar();}
	};

	/**
	 * Instantiates a new calendar widget.
	 * If GWT RPC is used the widget must be in an iFrame in the host HTML page to prevent cross-site problems.
	 * If JSONP is used the widget JavaScript must be served from the same site as the host HTML page.
	 *
	 * @param rpc is true if GWT RPC is to be used, else JSONP is used.
	 * @param pos the point of sale code of the organization hosting the widget.
	 * @param productid the ID of the product to be displayed.
	 */
	public CalendarWidget(boolean rpc, String pos, String productid) {
		try {
			if (productid == null || productid.isEmpty()) {throw new RuntimeException(Error.product_id.getMessage());}
			this.rpc = rpc;
			this.pos = pos;
			this.setStylePrimaryName("CalendarWidget");
			int tab = 0;

			//-----------------------------------------------
			// Product field
			//-----------------------------------------------
			productField = new ListField(this, null,
					new NameIdAction(Service.PRODUCT),
					CONSTANTS.productLabel(),
					false,
					tab++);
			productField.setFieldStyle("Field");
			productField.setLabelStyle("Label");
			productField.setAllOrganizations(true);
			productField.setIds(NameId.getCdlist(productid));
			productField.setVisible(productid.split(",").length > 1); // multiple properties
			productField.setHelpText(CONSTANTS.productHelp());
			//this.add(productField);

			loader.setVisible(false);
			HorizontalPanel bar = new HorizontalPanel();
			bar.add(productField);
			bar.add(loader);
			this.add(bar);

			//-----------------------------------------------
			// Calendar grid
			//-----------------------------------------------
			daysGrid = new Grid(7, 7);
			drawWeekLine(this);
			drawDayGrid(this);

			this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			navigator.addChangeHandler(this);
			navigator.setAbsolute(true);
			this.add(navigator);

			this.add(RazorWidget.getHome());
			
			getProduct(productid);
			setDisplayedMonth(RazorWidget.getDate());
			refreshTimer.cancel();
			refreshTimer.schedule(RazorWidget.delay);
		}
		catch (Throwable x) {
			x.printStackTrace();
			throw new RuntimeException(Error.widget_calendar.getMessage() + " " + x.getMessage());
		}
	}

	/* The action to refresh the widget for the selected product and target month. */
	private void getCalendar() {
		refreshTimer.cancel();
		refreshTimer.schedule(30000);

		if (rpc) {calendarWidget.execute();}
		else {getJsonpCalendar();}
	}

	/* The RPC action to refresh the widget for the selected product and target month. */
	private GuardedRequest<Table<AvailableItem>> calendarWidget = new GuardedRequest<Table<AvailableItem>>() {
		protected boolean error() {return productField.noValue() || displayedMonth == null;}
		protected void send() {super.send(new AvailableWidget(productField.getValue(), getMonthFirstDay(), Time.addDuration(getMonthFirstDay(), 60, Time.DAY)));}
		protected void receive(Table<AvailableItem> response) {renderAvailableItems(response);}
	};

	/*
	 * Removes the state styles from the calendar.
	 *
	 * @param formatter the state style formatter.
	 * @param i the row.
	 * @param j the column.
	 */
	private void removeStyles(CellFormatter formatter, int i, int j) {
		formatter.removeStyleName(i, j, Reservation.State.Arrived.name());
		formatter.removeStyleName(i, j, Reservation.State.Briefed.name());
		formatter.removeStyleName(i, j, Reservation.State.Closed.name());
		formatter.removeStyleName(i, j, Reservation.State.Confirmed.name());
		formatter.removeStyleName(i, j, Reservation.State.Departed.name());
		formatter.removeStyleName(i, j, Reservation.State.FullyPaid.name());
		formatter.removeStyleName(i, j, Reservation.State.PreDeparture.name());
		formatter.removeStyleName(i, j, Reservation.State.Provisional.name());
		formatter.removeStyleName(i, j, Reservation.State.Reserved.name());
	}
	
	/*
	 * Removes the styles from the calendar.
	 *
	 * @param formatter the state style formatter.
	 */
	private void removeStyles(CellFormatter formatter) {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {removeStyles(formatter, i, j);}
		}
	}

	/*
	 * Render the colour of the reserved cells from a table of available item objects.
	 * 
	 * @param response the table of available items.
	 */
	/**
	 * Render available items.
	 *
	 * @param response the response
	 */
	private void renderAvailableItems(Table<AvailableItem> response) {
		Log.debug("renderAvailableItems " + response);
		CellFormatter formatter = daysGrid.getCellFormatter();
		removeStyles(formatter);
		ArrayList<AvailableItem> items = response == null ? null : response.getValue();
		if (items == null || items.isEmpty()) {return;}
		Collections.sort(items);
		Date cursor = getDaysGridOrigin(displayedMonth);
		int i = 0;
		int j = 0;
		for (AvailableItem item : items) {
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
					formatter.addStyleName(i, j, item.getState());
					cursor = Time.addDuration(cursor, 1, Time.DAY);
					j++;
					if (j >= 7) {i++; j = 0;}
					if (i > 6) {return;}
				}
			}
		}
	}


	/* The JSONP action to refresh the widget for the selected product and target month. */
	private void getJsonpCalendar() {

		if (productField.noValue() || displayedMonth == null) {return;}

		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();

		String url = HOSTS.jsonUrl()
			+ "?service=" + JSONRequest.CALENDAR 
			+ "&pos=" + pos 
			+ "&productid=" + productField.getValue() 
			+ "&date=" + RazorWidget.DF.format(getDaysGridOrigin(displayedMonth))
		;

		loader.setVisible(true);
		jsonp.requestObject(url, new AsyncCallback<CalendarWidgetItems>() {

			@Override
			public void onFailure(Throwable x) {
				loader.setVisible(false);
				throw new RuntimeException(Error.widget_calendar.getMessage() +  " " + x.getMessage());
			}

			@Override
			public void onSuccess(CalendarWidgetItems response) {
				loader.setVisible(false);
				renderCalendarWidgetItems(response);
			}
		});
	}

	/*
	 * Render the colour of the reserved cells from JSON calendar widget items.
	 * 
	 * @param response the table of available items.
	 */
	private void renderCalendarWidgetItems(CalendarWidgetItems response) {
		CellFormatter formatter = daysGrid.getCellFormatter();
		removeStyles(formatter);
		if (response.getItems() == null || response.getItems().length() == 0) {return;}
		int row = 0;
		int col = 0;
		Date cursor = getDaysGridOrigin(displayedMonth);
		for (int index = 0; index < response.getItems().length(); index++) {
			CalendarWidgetItem item = response.getItems().get(index);
			Date date = RazorWidget.DF.parse(item.getDate());
			while (cursor.before(date)) {
				//removeStyles(formatter, row, col);
				cursor = Time.addDuration(cursor, 1, Time.DAY);
				col++;
				if (col >= 7) {row++; col = 0;}
				if (row > 6) {return;}
			}
			formatter.addStyleName(row, col, item.getState() == null ? Reservation.State.Confirmed.name() : item.getState());
			cursor = Time.addDuration(cursor, 1, Time.DAY);
			col++;
			if (col >= 7) {row++; col = 0;}
			if (row > 6) {return;}
		}
	}

	/* 
	 * The request callback to get product name ID pairs.
	 * 
	 *  @param productid the ID of the selected product.
	 */
	private void getProduct(String productid) {
		if (rpc) {productField.onRefresh();}
		else {getJsonpProductNameIds(productid);}	
	}

	/* 
	 * The JSONP request callback to get product name ID pairs.
	 * 
	 *  @param productid the ID of the selected product.
	 */
	private void getJsonpProductNameIds(String productid) {

		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		String url = HOSTS.jsonUrl()
			+ "?service=" + JSONRequest.NAMEID
			+ "&pos=" + pos 
			+ "&model=" + NameId.Type.Product.name() 
			+ "&id=" + productid 
		;

		loader.setVisible(true);
		jsonp.requestObject(url, new AsyncCallback<NameIdWidgetItems>() {

			@Override
			public void onFailure(Throwable x) {
				loader.setVisible(false);
				throw new RuntimeException(Error.nameid_json.getMessage() + " " + x.getMessage());
			}

			@Override
			public void onSuccess(NameIdWidgetItems response) {
				loader.setVisible(false);
				if (response != null && response.getItems() != null && response.getItems().length() > 0) {
					ArrayList<NameId> items = new ArrayList<NameId>();
					for (int index = 0; index < response.getItems().length(); index++) {
						items.add(new NameId(response.getItems().get(index).getName(), response.getItems().get(index).getId()));
					}
					productField.setItems(items);
				}
			}
		});
	}

	/* Sets a date in the month to be displayed. */
	private void setDisplayedMonth(Date displayedMonth) {
		this.displayedMonth = displayedMonth;
		displayMonth();
	}

	/**
	 * The navigator change handler.
	 *
	 * @param change the change
	 */
	public void onChange(ChangeEvent change) {
		if (productField.sent(change)) {getCalendar();}
		else if (navigator.sent(change)) {
			int index = navigator.getIndex();
			if (index == 0) {changeMonth(-12);}
			else if (index == 1) {changeMonth(-1);}
			else if (index == 2) {changeMonth(1);}
			else if (index == 3) {changeMonth(12);}
			else {Window.alert("No Month");}
		}
	}

	/*
	 * Changes the displayed month.
	 * 
	 * @param months to add to the displayed month
	 */
	private void changeMonth(int months) {
		displayedMonth = addMonths(this.displayedMonth, months);
		displayMonth();
	}

	/* Displays the current month. */
	private void displayMonth() {
		if (this.displayedMonth == null) {this.displayedMonth = new Date();}
		navigator.setLabel(MF.format(this.displayedMonth));
		this.drawDaysGridContent(this.displayedMonth);
		getCalendar();
	}

	/*
	 * Draws the week line.
	 * 
	 * @param panel on which to draw.
	 */
	private void drawWeekLine(Panel panel) {
		Grid weekLine = new Grid(1, 7);
		weekLine.setStyleName("WeekLine");
		Date weekFirstday = getWeekFirstDay();
		for (int i = 0; i < 7; i++) {weekLine.setText(0, i, EF.format(addDays(weekFirstday, i)));}
		panel.add(weekLine);
	}

	/*
	 * Draws the day grid.
	 * 
	 * @param panel on which to draw.
	 */
	private void drawDayGrid(Panel panel) {
		// for clickable cells
		//		this.daysGrid.addTableListener(new TableListener() {
		//			public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
		//				Date date = addDays(getDaysGridOrigin(displayedMonth), row * 7 + cell);
		//				cellClicked(date, row, cell);
		//			};
		//		});
		daysGrid.setStyleName("DayGrid");
		panel.add(daysGrid);
	}

	/*
	 * Draws the days into the days grid. Days drawn are the days of the displayed month
	 * and few days after and before the displayed month.
	 * 
	 * @param displayedMonth the date in the displayed month.
	 */
	private void drawDaysGridContent(Date displayedMonth) {
		CellFormatter formatter = daysGrid.getCellFormatter();
		Date cursor = this.getDaysGridOrigin(displayedMonth);
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				daysGrid.setText(i, j, DNF.format(cursor));
				removeStyleNames(i, j, formatter);
				if (areEquals(new Date(), cursor)) {
					if (displayedMonth.getMonth() == cursor.getMonth()) {formatter.addStyleName(i, j, "CurrentMonthSelected");}
					else {formatter.addStyleName(i, j, "DaySelected");}
				}
				else if (cursor.before(new Date())) {formatter.addStyleName(i, j, "DayPast");}
				//				else if (isInWeekEnd(cursor)) {
				//					if (displayedMonth.getMonth() == cursor.getMonth())	{formatter.addStyleName(i, j, "availableWidgetCurrentMonthWeekEnd());}
				//					else {formatter.addStyleName(i, j, "weekEnd());}
				//				}
				else if (displayedMonth.getMonth() == cursor.getMonth()) {formatter.addStyleName(i, j, "CurrentMonthOtherDay");}
				else {formatter.addStyleName(i, j, "DayOther");}
				cursor = addDays(cursor, 1);
			}
		}
	}

	/*
	 * Removes the cell style names.
	 * 
	 * @param row the row of the cell to change.
	 * @param col the column of the cell to change.
	 * @formatter row the cell formatter to use.
	 */
	private void removeStyleNames(int row, int col, CellFormatter formatter) {
		formatter.removeStyleName(row, col, "DayPast");
		formatter.removeStyleName(row, col, "DaySelected");
		formatter.removeStyleName(row, col, "CurrentMonthSelected");
		formatter.removeStyleName(row, col, "DayOther");
		formatter.removeStyleName(row, col, "CurrentMonthOtherDay");
		formatter.removeStyleName(row, col, "DayNotAvailable");
	}

	/*
	 * Returns the first day to display. If the month first day is after the 5th
	 * day of the week, it return the first day of the week. Else, it returns
	 * the first day of the week before.
	 * 
	 * @param displayedMonth the currently displayed month.
	 * 
	 * @return the first day to display in the grid.
	 */
	private Date getDaysGridOrigin(Date displayedMonth) {
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

	/*
	 * Adds the specified number of days to the date.
	 * 
	 * @param date the date to add to.
	 * @param days the specified number of days.
	 * @return the new date.
	 */
	private static Date addDays(Date date, int days) {
		return new Date(date.getYear(), date.getMonth(), date.getDate() + days);
	}

	/*
	 * Adds the specified number of months to the date.
	 * 
	 * @param date the date to add to.
	 * @param months the specified number of months.
	 * @return the new date.
	 */
	private static Date addMonths(Date date, int months) {
		return new Date(date.getYear(), date.getMonth() + months, date.getDate());
	}

	/*
	 * Checks if two dates are the same.
	 * 
	 * @param one the first date.
	 * @param another the other date.
	 * @return true if the dates are the same.
	 */
	private static boolean areEquals(Date one, Date another) {
		return one.getDate() == another.getDate()
				&& one.getMonth() == another.getMonth()
				&& one.getYear() == another.getYear();
	}

	/*
	 * Returns the first date of the currently displayed month.
	 * 
	 * @return the first day of the month.
	 */
	private Date getMonthFirstDay() {
		return getMonthFirstDay(displayedMonth);
	}

	/*
	 * Returns the first date of a month containing the specified date.
	 * 
	 * @param date the specified date.
	 * @return the first day of the month.
	 */
	private static Date getMonthFirstDay(Date date) {
		Date current = date;
		while (current.getDate() != 1) {
			current = new Date(current.getYear(), current.getMonth(), current
					.getDate() - 1);
		}
		return current;
	}

	/*
	 * Returns the index of the specified day in the week.
	 * Example : sunday = 0, monday = 1 .... which depends on the locale.
	 * 
	 * @param day the specified day in the week.
	 * @return the index of the day
	 */
	private static int getWeekDayIndex(Date day) {
		//DateLocale locale = (DateLocale) GWT.create(DateLocale.class);
		//int[] daysOrder = locale.getDAY_ORDER();
		int[] daysOrder = { 1, 2, 3, 4, 5, 6, 0 };
		int dayIndex = day.getDay();
		for (int i = 0; i < 7; i++) {
			if (dayIndex == daysOrder[i]) {return i;}
		}
		return -1;
	}

	/*
	 * Returns the first day of the current week.
	 * 
	 * @return date of the first day
	 */
	private static Date getWeekFirstDay() {
		return getWeekFirstDay(new Date());
	}

	/*
	 * Returns the first day of the week containing the specified date.
	 * 
	 * @param date the specified date.
	 * @return date of the first day of the week.
	 */
	private static Date getWeekFirstDay(Date date) {
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

	/* The Inner Class Navigator provides the buttons and label to navigate forward and back in time. */
	private class Navigator
	extends Composite
	implements ClickHandler, HasChangeHandlers {

		private final FlowPanel panel = new FlowPanel();
		private final Label first = new Label("<<");
		private final Label last = new Label(">>");
		private final Label next = new Label(">");
		private final Label prev = new Label("<");
		private final Label label = new Label();
		private boolean absolute = false;
		private boolean enabled = true;
		private int index;
		private int end;

		/** Instantiates a new navigator. */
		public Navigator() {
			initWidget(panel);

			final HorizontalPanel navigator = new HorizontalPanel();
			panel.add(navigator);

			next.addClickHandler(this);
			next.addStyleName("Button");

			prev.addClickHandler(this);
			prev.addStyleName("Button");

			last.addClickHandler(this);
			last.addStyleName("Button");

			first.addClickHandler(this);
			first.addStyleName("Button");

			label.addStyleName("Control");
			label.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

			navigator.add(first);
			navigator.add(prev);
			navigator.add(label);
			navigator.add(next);
			navigator.add(last);
		}

		/**
		 * Adds the specified change handler.
		 *
		 * @param handler to be added.
		 * @return the handler registration
		 */
		public HandlerRegistration addChangeHandler(ChangeHandler handler) {
			return addDomHandler(handler, ChangeEvent.getType());
		}

		/**
		 * Fires a change event.
		 * 
		 * @param sender of the event.
		 */
		protected void fireChange(Widget sender) {
			NativeEvent nativeEvent = Document.get().createChangeEvent();
			DomEvent.fireNativeEvent(nativeEvent, sender);
		}

		/**
		 * Checks if this widget sent the change event.
		 * 
		 * @param event when changed.
		 * @return true, if this widget sent the change event.
		 */
		public boolean sent(ChangeEvent event) {
			return this == event.getSource();
		}

		/**
		 * Handles the clicking of any of the buttons in the navigator as follows:
		 * >> button sets the offset to max(end - limit, start)
		 * > button sets the offset to max (offset + limit, end)
		 * < button sets the offset to min (offset - limit, start)
		 * << button sets the offset to start
		 * Fires a change event on any change in offset.
		 *
		 * @param event when clicked
		 */
		@Override
		public void onClick(ClickEvent event) {
			if (!enabled) {return;}
			else if (event.getSource() == first) {index = 0;}
			else if (event.getSource() == prev) {index = (absolute ? 1 : (index <= 0)? 0 : index - 1);}
			else if (event.getSource() == next) {index = (absolute ? 2 : (index == end)? end : index + 1);}
			else if (event.getSource() == last) {index = absolute ? 3 : end;}
			setLabel();
			fireChange(this);
		}

		/**
		 * Sets the absolute.
		 *
		 * @param absolute the new absolute
		 */
		public void setAbsolute(boolean absolute) {
			this.absolute = absolute;
		}

		/**
		 * Sets the label.
		 */
		private void setLabel() {
			label.setText("" + (index + 1) + " of " + (end + 1));
		}

		/**
		 * Sets the label.
		 *
		 * @param text the new label
		 */
		public void setLabel(String text) {
			label.setText(text);
		}

		/**
		 * Gets the index.
		 *
		 * @return the index
		 */
		public int getIndex() {
			return index;
		}
	}
}



