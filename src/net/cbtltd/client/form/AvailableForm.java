/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.Razor;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.DateField;
import net.cbtltd.client.field.HelpLabel;
import net.cbtltd.client.field.LocationField;
import net.cbtltd.client.field.MessagePopup;
import net.cbtltd.client.field.SliderField;
import net.cbtltd.client.field.SpinnerField;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.ToggleField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.table.ClickableNumberCell;
import net.cbtltd.client.field.table.ImageCell;
import net.cbtltd.client.field.table.TableError;
import net.cbtltd.client.field.table.TableExecutor;
import net.cbtltd.client.field.table.TextHeader;
import net.cbtltd.client.panel.BrochurePopup;
import net.cbtltd.client.panel.ReservationPopup;
import net.cbtltd.client.resource.available.AvailableBundle;
import net.cbtltd.client.resource.available.AvailableConstants;
import net.cbtltd.client.resource.available.AvailableCssResource;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.api.HasSchedule;
import net.cbtltd.shared.reservation.Available;
import net.cbtltd.shared.reservation.AvailableItem;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;


/**
 * The Class AvailableForm is to display a schedule of property availability in tabular form.
 * Each row represents a property, and each column represents a day that either has or may be rented.
 * Cells representing days that are reserved are filled with a coloured block, and if not rented are clear.
 * The block colour represents the state of the reservation, and for property managers, the block contains the name of the guest.
 * Controls above the schedule let the user set the property manager, schedule start date, and attributes to filter the result set.
 * A navigator to allow paging is displayed in the footer of the schedule if it has more rows than fit on a page.
 */
public class AvailableForm
extends AbstractForm<Available> {

	private static final AvailableConstants CONSTANTS = GWT.create(AvailableConstants.class);
	private static final AvailableBundle BUNDLE = AvailableBundle.INSTANCE;
	private static final AvailableCssResource CSS = BUNDLE.css();

	private static int ROWS = 21;
//	private static int SCHEDULE_COLS = 60; //getPageCols();
	private static final int COL_WIDTH = 30;

	private static SuggestField organizationField;
	private static DateField fromdateField;
	private static SpinnerField countField;
	private static ToggleField countunitField;
	private static ToggleField exactcountField;
	private static LocationField locationField;
	private static SliderField distanceField;
	private static TableField<AvailableItem> availableTable;
	private static Column<AvailableItem, HasSchedule> schedule;
	private static TextHeader currencyHeader;
	private static int selectedDay;
	private static boolean isWidget = false;

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#permission()
	 */
	@Override
	public short[] permission(){return AccessControl.AVAILABLE_PERMISSION;}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#getValue()
	 */
	@Override
	public Available getValue() {return getValue(new Available());}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#hasChanged()
	 */
	public boolean hasChanged() {return false;}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {
		if (fromdateField.sent(change)) {addSchedule();}
		if (!availableTable.sent(change)) {availableTable.execute();}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onFocus()
	 */
	@Override
	public void onFocus() {
		availableTable.execute(true);
	}

	/**
	 * Executed when window is resized
	 */
//	protected void onResizeForm() {
//		availableTable.setPageSize(getPageRows());
//		addSchedule();
//		availableTable.execute();
//		//SCHEDULE_COLS = getPageCols();
//	}
//
//	private static int getPageRows() {
//		int pageheight = Window.getClientHeight() - 300;
//		int pageSize = pageheight/26;
//		if (pageSize > 28) {pageSize = pageheight/27;}
//		if (pageSize > 31) {pageSize = pageheight/28;}
//		if (pageSize > 35) {pageSize = pageheight/29;}
//		if (pageSize > 45) {pageSize = pageheight/37;}
//		return pageSize < 10 ? 10 : pageSize;
//	}
	
	private static int getScheduleCols() {
		int cols = (Window.getClientWidth() - 400)/30;
		if (cols > 45) {cols = (Window.getClientWidth() - 450)/30;}
		if (cols > 60) {cols = (Window.getClientWidth() - 500)/30;}
		return cols < 30 ? 30 : cols;
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#initialize()
	 */
	@Override
	public void initialize() {
		AbstractField.CSS.ensureInjected();
		CSS.ensureInjected();

		final ScrollPanel scroll = new ScrollPanel();
		add(scroll);
		VerticalPanel panel = new VerticalPanel();
		panel.addStyleName(AbstractField.CSS.cbtAbstractWidth());
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		scroll.add(panel);

		panel.add(createControl());
		FlowPanel shadow = new FlowPanel();
		shadow.addStyleName(CSS.controlshadowStyle());
		panel.add(shadow);
		panel.add(createLegend());
		panel.add(createSchedule());

		onRefresh();
		onReset(Available.CREATED);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#getValue(net.cbtltd.shared.api.HasState)
	 */
	private Available getValue(Available available) {
		organizationField.setVisible(AbstractRoot.readable(AccessControl.AGENCY_PERMISSION));
		if (AbstractRoot.readable(AccessControl.AGENCY_PERMISSION)) {
			available.setOrganizationid(organizationField.noValue() ? null : organizationField.getValue());
		}
		else {
			available.setOrganizationid(AbstractRoot.getOrganizationid());
		}
		available.setAgentid(AbstractRoot.getOrganizationid());
		available.setFromdate(Time.getDateServer(fromdateField.getValue()));
		available.setTodate(Time.getDateServer(Time.addDuration(fromdateField.getValue(), getScheduleCols(), Time.DAY)));
		available.setExactcount(exactcountField.getValue());
		available.setCount(countField.getValue());
		available.setCountunit(countunitField.getValue());
		available.setLatLng(locationField.getValue());
		available.setDistance(distanceField.getMappedValue(0));
		available.setCurrency(AbstractRoot.getCurrency());
		available.setUnit(Time.DAY.toString());
		Log.debug("getValue " + available); //AbstractRoot.readable(AccessControl.AGENCY_PERMISSION) + "," + AccessControl.AGENCY_PERMISSION + ", " + AbstractRoot.getSession());
		return available;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#setValue(net.cbtltd.shared.api.HasState)
	 */
	public void setValue(Available available) {
		Log.debug("setValue " + available);
		organizationField.setValue(available.getOrganizationid());
		fromdateField.setValue(Time.getDateClient(available.getFromdate()));
		exactcountField.setValue(available.getExactcount());
		countField.setValue(available.getCount());
		countunitField.setValue(available.getCountunit());
		locationField.setValue(available.getLatLng());
	}

	/* 
	 * Creates the panel of schedule controls.
	 * 
	 * @return the panel of schedule controls.
	 */
	private HorizontalPanel createControl() {
		HorizontalPanel panel = new HorizontalPanel();
		panel.addStyleName(AbstractField.CSS.cbtAbstractControl());
		panel.addStyleName(CSS.controlStyle());		

		//-----------------------------------------------
		// Organization list
		//-----------------------------------------------
		organizationField = new SuggestField(this, null, new NameIdAction(Service.PARTY), null,	20,	tab++);
		organizationField.setType(Party.Type.Organization.name());
		organizationField.setAllOrganizations(true);
		organizationField.setDefaultName(CONSTANTS.organizationEmpty());
		organizationField.setFieldHalf();
		organizationField.setHelpText(CONSTANTS.organizationHelp());

		//-----------------------------------------------
		// Start Date is the start of the schedule
		//-----------------------------------------------
		fromdateField = new DateField(this, null, null,	tab++);
		fromdateField.setFieldStyle(CSS.fromdateField());

		panel.add(new HelpLabel(CONSTANTS.organizationLabel(), CONSTANTS.organizationHelp(), fromdateField));
		panel.add(organizationField);

		panel.add(new HelpLabel(CONSTANTS.fromdateLabel(), CONSTANTS.fromdateHelp(), fromdateField));
		panel.add(fromdateField);

		//-----------------------------------------------
		// Count field for number of people/rooms
		//-----------------------------------------------
		countField = new SpinnerField(this, null,
				1,
				99,
				null,
				tab++);
		countField.setFieldStyle(CSS.countField());
		countField.setDefaultValue(1);
		panel.add(new HelpLabel(CONSTANTS.countLabel(), CONSTANTS.countHelp(), countField));
		panel.add(countField);

		//-----------------------------------------------
		// Toggle field between people/rooms
		//-----------------------------------------------
		countunitField = new ToggleField(this, null,
				null,
				CONSTANTS.countunitBedroom(),
				CONSTANTS.countunitPerson(),
				tab++);
		countunitField.setFieldStyle(CSS.countunitField());
		panel.add(countunitField);

		//-----------------------------------------------
		// Check for auto login
		//-----------------------------------------------
		exactcountField = new ToggleField(this, null, 
				null, 
				CONSTANTS.exactcountMinimum(),
				CONSTANTS.exactcountExact(),
				tab++);
		exactcountField.setFieldStyle(CSS.exactcountField());
		panel.add(exactcountField);

		//-----------------------------------------------
		// Distance from centre with one anchor slider
		// DISTANCE_INDEX is the zero based index into DISTANCE_MAP
		// DISTANCE_MAP translates the index into the distances:
		// 500m, 1km, 2km, 5km, 10km, 20km, 50km, 100km
		//-----------------------------------------------
		final Integer[] DISTANCE_INDEX = {0, 7, 1, 2};
		final Double[] DISTANCE_MAP = {0.5, 1.0, 2.0, 5.0, 10.0, 20.0, 50.0, 100.0};
		distanceField = new SliderField(this, null,
				null, //CONSTANTS.distanceLabel(),
				DISTANCE_INDEX,
				tab++);
		distanceField.setValueMap(DISTANCE_MAP);
		distanceField.setUnit(CONSTANTS.distanceUnit());
		distanceField.setFieldStyle(CSS.distanceField());
		panel.add(new HelpLabel(CONSTANTS.distanceLabel(), CONSTANTS.distanceHelp(), distanceField));
		panel.add(distanceField);

		//-----------------------------------------------
		// Location shuttle
		//-----------------------------------------------
		locationField = new LocationField(this, null,
				null,
				null,
				tab++);
		locationField.setNullable(true);
		locationField.setFieldStyle(CSS.locationField());
		panel.add(locationField);

		onRefresh();
		onReset(Available.CREATED);
		return panel;
	}

	/*
	 * Creates a legend to interpret between schedule colours and their reservation states.
	 * 
	 * @return the legend for the schedule colours.
	 */
	private Grid createLegend() {
		Grid grid = new Grid(1, Reservation.STATES.length * 3 + 1);
		grid.addStyleName(CSS.legendStyle());
		int col = 0;
		grid.setText(0, col++, CONSTANTS.legendLabel());
		for (int index = 1; index < Reservation.STATES.length - 2; index++) {
			grid.setHTML(0, col++, "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			grid.setHTML(0, col, " ");
			grid.getCellFormatter().addStyleName(0, col, Reservation.STATES[index]);
			grid.getCellFormatter().addStyleName(0, col++, CSS.legendcellStyle());
			grid.setText(0, col++, CONSTANTS.reservationStates()[index]);
		}
		return grid;
	}

	/*
	 * Creates the panel to be displayed if there are no results to display in the schedule.
	 * 
	 * @return the empty panel.
	 */
	private Widget availabletableEmpty() {
		FlowPanel panel = new FlowPanel();
		panel.add(new HTML(CONSTANTS.tableEmpty()));
		panel.addStyleName(AbstractField.CSS.cbtTableFieldEmpty());
		panel.add(new Image(BUNDLE.tableEmpty()));
		return panel;
	}

	/*
	 * Creates the availability schedule.
	 * 
	 * @return the availability schedule.
	 */
	private TableField<AvailableItem> createSchedule() {

		//-----------------------------------------------
		// Schedule selection change handler
		// Opens forms/panels according to the column and reservation which is clicked
		//-----------------------------------------------
		final NoSelectionModel<AvailableItem> selectionModel = new NoSelectionModel<AvailableItem>();
		
		final SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				AvailableItem availableitem = selectionModel.getLastSelectedObject();
				if (availableitem == null || isWidget) {return;}
				Date fromdate = Time.addDuration(Time.getDateServer(fromdateField.getValue()), selectedDay, Time.DAY);
				Date todate = Time.addDuration(fromdate, 1.0, Time.DAY);
				availableitem = availableitem.getItem(fromdate);
				availableitem.setAgentid(AbstractRoot.getOrganizationid());

				if (selectedDay == -1) {
					if (AbstractRoot.readable(AccessControl.AGENT_ROLES)) {BrochurePopup.getInstance().show(availableitem);}
					else {AbstractRoot.render(Razor.PRODUCT_TAB, availableitem.getProduct());}
				}
				else if (selectedDay == -2) {
					BrochurePopup.getInstance().show(availableitem);
				}
				else if (AbstractRoot.readable(AccessControl.AGENT_ROLES) && availableitem.noReservationid() && availableitem.hasPrice()) {
					ReservationPopup.getInstance().show(availableitem.getReservation(fromdate, todate, AbstractRoot.getActorid(), null, null), availableTable);
				}
				else if (AbstractRoot.readable(AccessControl.ORGANIZATION_ROLES) && availableitem.noReservationid() && availableitem.hasPrice()) {
					MessagePopup.getInstance().showYesNo(CONSTANTS.reservationCreate(), Razor.RESERVATION_TAB, availableitem.getReservation(fromdate, todate, AbstractRoot.getActorid(), null, null));
				}
				else if (AbstractRoot.readable(AccessControl.ORGANIZATION_ROLES) && availableitem.hasPrice()) {
					AbstractRoot.render(Razor.RESERVATION_TAB, availableitem.getReservation());
				}
				else if (availableitem.noPrice()) {
					AbstractField.addMessage(Level.TERSE, CONSTANTS.priceError(), fromdateField);
				}
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// Schedule table
		//-----------------------------------------------
		availableTable = new TableField<AvailableItem>(this, null,
				new Available(),
				selectionModel,
				ROWS,
//				getPageRows(),
				tab++) {

			@Override
			protected ArrayList<AvailableItem> resetValue(ArrayList<AvailableItem> values) { //transform schedule lines
				ArrayList<AvailableItem> result = new ArrayList<AvailableItem>();
				if (values != null) {
					AvailableItem lastValue = null;
					for (AvailableItem value : values) {
						if (lastValue == null || !lastValue.getProductid().equalsIgnoreCase(value.getProductid())) { // new schedule line
							lastValue = value;
							result.add(lastValue);
							lastValue.addItem(value);
						}
						else {lastValue.addItem(value);} // add items with the same product id as previous item
					}
				}
				return result;
			}
		};

		availableTable.setEmptyValue(availabletableEmpty());
		availableTable.setStyleName(CSS.availableStyle());

		availableTable.setTableError(new TableError() {
			@Override
			public boolean error() {
				return (
					fromdateField.noValue()
					|| countField.noValue()
					|| countunitField.noValue()
				);
			}
		});

		availableTable.setTableExecutor(new TableExecutor<Available>() {
			@Override
			public void execute(Available action) {
				getValue(action);
			}
		});

		int col = 0;

		//-----------------------------------------------
		// Product column
		//-----------------------------------------------
		Column<AvailableItem, String> productname = new Column<AvailableItem, String>(
				new ClickableTextCell() {
			  @Override
			  public void onBrowserEvent(Context context, Element parent, String value,
			      NativeEvent event, ValueUpdater<String> valueUpdater) {
			    super.onBrowserEvent(context, parent, value, event, valueUpdater);
			    if ("click".equals(event.getType())) {
					selectedDay = -1;
			    }
			  }

		}) {
			@Override
			public String getValue( AvailableItem availableitem ) {return availableitem == null ? "" : availableitem.getProductname(25);}
		};
		availableTable.addStringColumn(productname, CONSTANTS.availableHeader()[col++]); //no sorting for schedule

		//-----------------------------------------------
		// Room Count column
		//-----------------------------------------------
		Column<AvailableItem, Number> room = new Column<AvailableItem, Number>(
				new ClickableNumberCell(
						AbstractField.IF,
						new ClickableNumberCell.Delegate<Number> () {
							@Override
							public void execute(Number value) {
								selectedDay = -1;				
							}
						}
				)) {
			@Override
			public Integer getValue(AvailableItem availableitem ) {return availableitem.getRoom();}
		};
		availableTable.addNumberColumn(room, CONSTANTS.availableHeader()[col++]);

		//-----------------------------------------------
		// Info column
		//-----------------------------------------------
		Column<AvailableItem, AvailableItem> info = new Column<AvailableItem, AvailableItem>(
//			new ImageCell<AvailableItem>(AbstractField.BUNDLE.info(), AbstractField.CSS.cbtInfoButton(),
			new ImageCell<AvailableItem>(AbstractField.PRODUCTS, AbstractField.CSS.cbtInfoButton(),
				new ImageCell.Delegate<AvailableItem>() {
					public void execute( AvailableItem availableitem ) {
						selectedDay = -2;
					}				
				}
			)) {
			
			@Override
			public AvailableItem getValue( AvailableItem availableitem ) {return availableitem;}
		};
		availableTable.addColumn(info, CONSTANTS.availableHeader()[col++]);

		//-----------------------------------------------
		// Price column
		//-----------------------------------------------
		Column<AvailableItem, Number> rack = new Column<AvailableItem, Number>(
				new ClickableNumberCell(
						AbstractField.IF,
						new ClickableNumberCell.Delegate<Number> () {
							@Override
							public void execute(Number value) {
								selectedDay = -1;				
							}
						}						
						)) {
			@Override
			public Double getValue( AvailableItem availableitem ) {return availableitem.getPrice();}
		};
		availableTable.addNumberColumn(rack, CONSTANTS.availableHeader()[col++]);

		//-----------------------------------------------
		// Currency column
		//-----------------------------------------------
		Column<AvailableItem, String> currency = new Column<AvailableItem, String>( 
				new ClickableTextCell() {
					  @Override
					  public void onBrowserEvent(Context context, Element parent, String value,
					      NativeEvent event, ValueUpdater<String> valueUpdater) {
					    super.onBrowserEvent(context, parent, value, event, valueUpdater);
					    if ("click".equals(event.getType())) {
							selectedDay = -1;
					    }
					  }
				}
				) {
			@Override
			public String getValue( AvailableItem availableitem ) {return availableitem.getCurrency();}
		};
		currencyHeader = availableTable.addStringColumn(currency, CONSTANTS.availableHeader()[col++]);

		//-----------------------------------------------
		// Available columns
		//-----------------------------------------------
		addSchedule();

		return availableTable;
	}

	/* 
	 * Creates a composite column to show availability of a property and adds it to the schedule.
	 * Removes the schedule if it exists before adding new schedule based on the current start date.
	 */
	private void addSchedule() {

		if (schedule != null) {availableTable.removeColumn(schedule);}
		currencyHeader.setValue(DateTimeFormat.getFormat("MMM").format(fromdateField.getValue()));

		final List<HasCell<HasSchedule, ?>> listHC = new ArrayList<HasCell<HasSchedule, ?>>();

		for (int day = 0; day <= getScheduleCols(); day++) {
			final AvailableColumn hasC = new AvailableColumn(day);
			listHC.add(hasC);
		}

		final CompositeCell<HasSchedule> compositeCell = new CompositeCell<HasSchedule>(listHC) {
			private String activityid = ""; //so only one column is added per activity
			private int index = 0; //to index the next activity
			
			/**
			 * Render the composite schedule cell as HTML into a {@link SafeHtmlBuilder}, suitable
			 * for passing to {@link Element#setInnerHTML} on a container element. The cell renders
			 * the HasSchedule item as follows:
			 * 1) selects the current schedule value from the list in item, indexed by index
			 *    checks that the selected schedule value is not in the past and increments the index if it is
			 * 2) calculates the cell column date from the fromdateField value and day which has been set in hasC above
			 * 3) if the cell is both in the schedule value and is the first in it, render it having the width and color of the value
			 *    the width is the smaller of the days to end of the value or days to end of the schedule
			 *    the color is the CSS style having the same name as the state of the value specified in 
			 * @see net.cbtltd.resource.available.Available.css
			 * 6) else if the cell is in the schedule value but is not the first in it, render a zero width cell
			 *    if there are no more cells in the schedule value increment the index to get the next schedule value
			 * 8) else  a blank cell to indicate that it is available
			 * 9) finally if it is the last cell in the schedule increment the index to get the next schedule value
			 *  
			 * @param context the {@link com.google.gwt.cell.client.Cell.Context Context} of the cell.
			 * @param item the cell value to be rendered.
			 * @param shb the {@link SafeHtmlBuilder} to be written to.
			 * @param hasCell a {@link HasCell} instance containing the cells to be rendered within this cell.
			 */
			@Override
			protected <X> void render(Context context, HasSchedule item, SafeHtmlBuilder shb, HasCell<HasSchedule, X> hasCell) {
				int day = ((AvailableColumn)hasCell).getDay();
				if (day == 0){ activityid = ""; }					
				Date clientdate = Time.addDuration(fromdateField.getValue(), day, Time.DAY);
				boolean isSunday = (clientdate.getDay() == 0);
				Date serverdate = Time.getDateServer(clientdate);
				HasSchedule value = getValue(item, index);				
								
				while (index < item.getItems().size() && value.getDaysToEnd(serverdate) <= 0) {value = getValue(item, ++index);}
				StringBuilder sb = new StringBuilder();
				sb.append("<div date='").append(AbstractRoot.getDF().format(clientdate)).append("'");
				if (value != null && !value.getActivityid().equals(activityid) && value.isDayBooked(serverdate)) {
					activityid = value.getActivityid();
					int daysToEnd = Math.min(value.getDaysToEnd(serverdate), getScheduleCols() - day  + 1);
					if (daysToEnd <= 1) {index++;}
					sb.append(" style='width:")
					.append(COL_WIDTH * daysToEnd)
					.append("px' class='")
					.append(AbstractField.CSS.cbtScheduleFieldColumn())
					.append(" ")
					.append(value.getState())
					.append("'>");
					shb.append(SafeHtmlUtils.fromTrustedString(sb.toString()));
					hasCell.getCell().render(context, hasCell.getValue(value), shb);
				}
				else if (value != null && value.getActivityid().equals(activityid) && value.isDayBooked(serverdate)) {
					int daysToEnd = Math.min(value.getDaysToEnd(serverdate), getScheduleCols() - day  + 1);
					if (daysToEnd <= 1) { index++; }
					sb.append(" style='width:0' class='")
					.append(AbstractField.CSS.cbtScheduleFieldColumn())
					.append("'>");
					shb.append(SafeHtmlUtils.fromTrustedString(sb.toString()));
				}
				else {
					sb.append(" style='width:")
					.append(COL_WIDTH).append("px' class='")
					.append(AbstractField.CSS.cbtScheduleFieldColumn())
					.append(isSunday ? " " + AbstractField.CSS.cbtScheduleFieldSunday() : "")
					.append("'>&nbsp;"); //VIP for click of blank column
					shb.append(SafeHtmlUtils.fromTrustedString(sb.toString()));
					
				}
				shb.appendHtmlConstant("</div>");
				if (day >= getScheduleCols()) {
					index = 0;
					if(item.getItems().size() == 2) { activityid = "";}
				} //reset for next row
				Log.debug("\nRendered " + sb.toString());
			}
		};

		schedule = new Column<AvailableItem, HasSchedule>(compositeCell) {
			@Override
			public HasSchedule getValue(AvailableItem availableitem ) {return availableitem;}
		};

		//availableTable.addColumn(schedule, getHeader(getLabels(Time.getDateServer(fromdateField.getValue()))));
		availableTable.addColumn(schedule, getHeader(getLabels(fromdateField.getValue())));
	}

	/*
	 * Gets the current activity from its parent and the current index.
	 * 
	 * @param item the parent of the target activity.
	 * @param index the index in list of activities in the parent.
	 * @return the current activity.
	 */
	private HasSchedule getValue(HasSchedule item, int index) {
		if (index < item.getItems().size()){return item.getItems().get(index);}
		else return null;
	}

	/* Inner Class AvailableColumn is to provide cells for the schedule column. */
	private class AvailableColumn implements HasCell<HasSchedule, String> {

		final ClickableTextCell cell = new ClickableTextCell()
		{
			@Override
			public void onBrowserEvent(Context context, Element parent, String value,
					NativeEvent event, ValueUpdater<String> valueUpdater) {
				super.onBrowserEvent(context, parent, value, event, valueUpdater);
				if ("click".equals(event.getType())) {
					selectedDay = day;
				}
			}
		};

		private final int day;

		public AvailableColumn(int day) {
			this.day = day;
		}

		public int getDay() {
			return day;
		}

		@Override
		public Cell<String> getCell() {
			return cell;
		}

		@Override
		public FieldUpdater<HasSchedule, String> getFieldUpdater() {
			return null;
		}

		@Override
		public String getValue(HasSchedule value) {
			return AbstractRoot.readable(AccessControl.ORGANIZATION_ROLES) ? value.getLabel(100) : "|";
		}
	}

	/*
	 * Creates a schedule header from an array of column labels.
	 * 
	 * @param labels the array of to be used in the header.
	 * @return a safe HTML string of column headers.
	 */
	private SafeHtml getHeader(ArrayList<String> labels) {
		StringBuilder sb = new StringBuilder();
		sb.append("<table class='").append(AbstractField.CSS.cbtScheduleFieldTable()).append("'><tr>");
		for (String label : labels) {sb.append("<td width='").append(COL_WIDTH).append("px' class='").append(AbstractField.CSS.cbtScheduleFieldColumn()).append("'>").append(label).append("</td>");}
		sb.append("</tr></table>");
		return SafeHtmlUtils.fromTrustedString(sb.toString());
	}

	/*
	 * Creates an array of column day labels starting at the specified date.
	 * 
	 * @param startDate the date from which to create list.
	 * @return an array of column day labels starting at the specified date.
	 */
	private ArrayList<String> getLabels(Date startDate) {
		long time = startDate.getTime();
		ArrayList<String> labels = new ArrayList<String>(getScheduleCols());
		for (int day = 0; day <= getScheduleCols(); day++) {
//			Date date = Time.getDateClient(new Date(time));
			Date date = new Date(time);
			labels.add(String.valueOf(date.getDate()));
			time += Time.DAY.milliseconds();
		}
		return labels;
	}
}
