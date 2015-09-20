/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.form;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.Razor;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.DatespanField;
import net.cbtltd.client.field.HelpHTML;
import net.cbtltd.client.field.HelpLabel;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.LocationField;
import net.cbtltd.client.field.MapField;
import net.cbtltd.client.field.SliderField;
import net.cbtltd.client.field.SpinnerField;
import net.cbtltd.client.field.StackField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.field.ToggleField;
import net.cbtltd.client.field.table.ImageCell;
import net.cbtltd.client.field.table.StyledNumberCell;
import net.cbtltd.client.field.table.TableError;
import net.cbtltd.client.field.table.TableExecutor;
import net.cbtltd.client.field.table.TableSelectionModel;
import net.cbtltd.client.panel.BrochurePopup;
import net.cbtltd.client.panel.PartyPopup;
import net.cbtltd.client.panel.ReservationPopup;
import net.cbtltd.client.resource.search.SearchBundle;
import net.cbtltd.client.resource.search.SearchConstants;
import net.cbtltd.client.resource.search.SearchCssResource;
import net.cbtltd.shared.Attribute;
import net.cbtltd.shared.AttributeMapAction;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Currencyrate;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Position;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.finance.CurrencyrateNameId;
import net.cbtltd.shared.product.ProductPositions;
import net.cbtltd.shared.reservation.AvailableItem;
import net.cbtltd.shared.reservation.LookBook;
import net.cbtltd.shared.reservation.LookBookSpecial;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.base.LatLngBounds;
import com.google.gwt.maps.client.events.zoom.ZoomChangeMapEvent;
import com.google.gwt.maps.client.events.zoom.ZoomChangeMapHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;

/**
 * The Class LookBookForm is to display a table of available properties that satisfy a set of selection criteria.
 * Each row represents a property that is available with its property manager, rack and quoted prices, star rating, and so on.
 * Select a property in the table to highlight where it in in the map, and click a button to display its brochure or to book it.
 * Use the scroll bar to the right of the table if it has more rows than fit on the page.
 */
public class SearchForm
extends AbstractForm<LookBook> {

	private static final SearchConstants CONSTANTS = GWT.create(SearchConstants.class);
	private static final SearchBundle BUNDLE = SearchBundle.INSTANCE;
	private static final SearchCssResource CSS = BUNDLE.css();
	private static final int TABLE_ROWS = 240;
	private static final int ZOOM_BASE = 16;
	private static final int ZOOM_POSITIONS = 7;
	private static final int DEFAULT_DISTANCE_INDEX = 6; //2;
	
	/* Request to get a currency exchange rate from the server. */
	private final GuardedRequest<Currencyrate> currencyrateRead = new GuardedRequest<Currencyrate>() {
		protected boolean error() {return currencyField.noValue();}
		protected void send() {super.send(getValue(new Currencyrate()));}
		
		/* (non-Javadoc)
		 * @see net.cbtltd.client.GuardedRequest#receive(net.cbtltd.shared.api.HasResponse)
		 */
		protected void receive(Currencyrate response) {
			if (response == null || response.getRate() == null) {AbstractField.addMessage(Level.ERROR, CONSTANTS.currencyError(), currencyField);}
			else {
				exchangerate = response.getRate();
				if (specialField.getValue()) {pricerangeField.setScale(priceunitField.getValue() ? daysrangeField.getValue(1) * exchangerate : exchangerate);}
				else {pricerangeField.setScale(priceunitField.getValue() ? durationField.getValue(0) * exchangerate : exchangerate);}
			}
		}
	};

	/* Gets position summary when zoomed out */
	private static GuardedRequest<Table<Position>> productPositions = new GuardedRequest<Table<Position>>() {
		protected boolean error() {return mapField.getZoom() > ZOOM_POSITIONS;}
		protected void send() {super.send(new ProductPositions(mapField.getLatitude(), mapField.getLongitude(), mapField.getZoom()));}
		protected void receive(Table<Position> value) {
			mapField.clear();
			if (value == null || value.getValue() == null) {return;}
			for (Position item : value.getValue()) {
				int count = item.getAltitude().intValue();
				mapField.addMarker(LatLng.newInstance(item.getLatitude(), item.getLongitude()), String.valueOf(count) + " " + (count <= 1 ? CONSTANTS.position() : CONSTANTS.positions()));
			}
		}
	};

	private static Button advancedLabel;
	private static Button basicLabel;
	private static LocationField locationField;
	private static SliderField distanceField;
	private static ToggleField distanceunitField;
	private static ToggleField specialField;
	private static DatespanField fromtodateField;
	private static ToggleField offlineField;
	private static SliderField durationField;
	private static SliderField daysrangeField;
	private static HTML whatLabel;
	private static HTML actionLabel;
	private static HelpLabel countLabel;
	private static HelpLabel exactcountLabel;
	private static SpinnerField countField;
	private static ToggleField countunitField;
	private static HorizontalPanel pricePanel;
	private static HelpLabel priceunitLabel;
	private static ToggleField priceunitField; //false = night true = stay
	private static SliderField pricerangeField;
	private static HelpLabel currencyLabel;
	private static ListField currencyField;
	private static SliderField ratingField;
	private static SliderField commissionField;
	private static DisclosurePanel attributeLabel;
	private static StackField attributeField;
	private static Button attributeButton;
	private static TextField emailaddressField;
	private static MapField mapField;

	private static TableField<AvailableItem> availableTable;
	private static TableSelectionModel<AvailableItem> availableSelectionModel;
	private static TableField<AvailableItem> specialTable;
	private static TableSelectionModel<AvailableItem> specialSelectionModel;
//	private static AvailableItem selecteditem; // current selected item
	private static Double exchangerate = 1.0;

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#permission()
	 */
	@Override
	public short[] permission(){return AccessControl.SEARCH_PERMISSION;}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#getValue()
	 */
	@Override
	public LookBook getValue() {return getValue(new LookBook());}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#hasChanged()
	 */
	public boolean hasChanged() {return false;}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {

		//Window.alert("onChange " + change.getSource().getClass());
		
		if (isReset() &&  priceunitField.getValue() && !specialField.getValue() && fromtodateField.getDuration(Time.DAY) < 1) {
			priceunitField.setValue(false);
			AbstractField.addMessage(Level.ERROR, CONSTANTS.dateRangeError(), fromtodateField);
		}

		if (locationField.sent(change)) {mapField.setValue(locationField.getValue());}
		else if (mapField.sent(change) || distanceField.sent(change) || distanceunitField.sent(change)) {setZoomLevel();}
		else if (fromtodateField.sent(change) && !specialField.getValue()) {durationField.setValue(fromtodateField.getDuration(Time.DAY).intValue());}
		else if (durationField.sent(change) && !specialField.getValue()) {fromtodateField.setDuration(Double.valueOf(durationField.getValue(0)), Time.DAY);}
		else if (availableTable.sent(change)) {setMarkers(availableTable.getValue());}
		else if (currencyField.sent(change)) {currencyrateRead.execute();}
		else if (specialField.sent(change)) {
			fromtodateField.setDuration(specialField.getValue() ? 365.0 : 0.0, Time.DAY);
			fromtodateField.setValue(Time.getDateStart());
		}
		else if (specialTable.sent(change)) {setMarkers(specialTable.getValue());}

		if (priceunitField.sent(change) || durationField.sent(change) || daysrangeField.sent(change) || specialField.sent(change)) {
			if (specialField.getValue()) {pricerangeField.setScale(priceunitField.getValue() ? daysrangeField.getValue(1) * exchangerate : exchangerate);}
			else {pricerangeField.setScale(priceunitField.getValue() ? durationField.getValue(0) * exchangerate : exchangerate);}
		}

		if (availableTable.sent(change)) {availableTable.deselect();}
		else { //refresh tables with ! to prevent loop back
		    if (locationField.sent(change)){
			availableTable.execute();
		    }
		    
		}
		if (specialTable.sent(change)) {specialTable.deselect();}
		else {
		    if (locationField.sent(change)){
			specialTable.execute();
		    }
		}

		attributeButton.setVisible(attributeField.hasValue());
		fromtodateField.setLabel(specialField.getValue() ? CONSTANTS.fromtodatespecialLabel() : CONSTANTS.fromtodateLabel());
		durationField.setVisible(!specialField.getValue());
		daysrangeField.setVisible(specialField.getValue());
		availableTable.setVisible(!specialField.getValue());
		specialTable.setVisible(specialField.getValue());

		AbstractRoot.setBooleanValue(Party.Value.Countunit.name(), countunitField.getValue());
		AbstractRoot.setValue(Party.Value.Currency.name(), currencyField.getValue());
		AbstractRoot.setIntegerValue(Party.Value.Distance.name(), distanceField.getValue(0));
		AbstractRoot.setBooleanValue(Party.Value.DistanceUnit.name(), distanceunitField.getValue());
		AbstractRoot.setDoubleValue(Party.Value.Latitude.name(), mapField.getLatitude());
		AbstractRoot.setDoubleValue(Party.Value.Longitude.name(), mapField.getLongitude());
		AbstractRoot.setIntegerValue(Party.Value.ZoomLevel.name(), mapField.getZoom());
		AbstractRoot.setBooleanValue(Party.Value.Priceunit.name(), priceunitField.getValue());

		setResetting(false);
		triggerResize();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onFocus()
	 */
	@Override
	public void onFocus() {
		availableTable.execute(true);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#initialize()
	 */
	public void initialize() {
		AbstractField.CSS.ensureInjected();
		CSS.ensureInjected();
		
		final ScrollPanel scroll = new ScrollPanel();
		add(scroll);
		final HorizontalPanel panel = new HorizontalPanel();
		scroll.add(panel);
		panel.add(createMap());
		panel.add(createSearch());
		final ScrollPanel east = new ScrollPanel();
		panel.add(east);
		final FlowPanel table = new FlowPanel();
		east.addStyleName(CSS.tableScroll());
		east.add(table);
		table.add(createAvailableTable());
		table.add(createSpecialTable());

		emailaddressField = new TextField(this, null, null, tab++);
		emailaddressField.setMaxLength(100);
		onRefresh();
		onReset(Reservation.State.Provisional.name());
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#getValue(net.cbtltd.shared.api.HasState)
	 */
	private LookBook getValue(LookBook lookbook) {
		if (AbstractRoot.readable(AccessControl.ORGANIZATION_ROLES)) {lookbook.setOrganizationid(AbstractRoot.getOrganizationid());}
		else {lookbook.setOrganizationid(null);}
		lookbook.setAgentid(AbstractRoot.getOrganizationid());
		lookbook.setState(Reservation.State.Provisional.name());
		lookbook.setLatitude(mapField.getLatitude());
		lookbook.setLongitude(mapField.getLongitude());
		lookbook.setDistance(distanceField.getMappedValue(0));
		lookbook.setDistanceunit(distanceunitField.getValue());
		lookbook.setOffline(offlineField.getValue());
		lookbook.setFromdate(fromtodateField.getValue());
		lookbook.setTodate(fromtodateField.getTovalue());
		lookbook.setSpecial(specialField.getValue());
		lookbook.setSpecialmin(daysrangeField.getValue(0));
		lookbook.setSpecialmax(daysrangeField.getValue(1));
		lookbook.setCount(Integer.valueOf(countField.getValue()));
		lookbook.setCountunit(countunitField.getValue());
		lookbook.setExactcount(advancedLabel.isVisible());
		lookbook.setPricemin(pricerangeField.getValue(0));
		lookbook.setPricemax(pricerangeField.getValue(1));
		lookbook.setPriceunit(priceunitField.getValue());
		lookbook.setCurrency(currencyField.getValue());
		lookbook.setRating(ratingField.getValue(0));
		lookbook.setDiscount(commissionField.getValue(0));
		lookbook.setAttributes(attributeField.getValue());
		AbstractRoot.setIntegerValue(Party.Value.LookBookCount.name(), AbstractRoot.getIntegerValue(Party.Value.LookBookCount.name()) + 1);
		Log.debug("getValue " + lookbook);
		return lookbook;
	}

	/*
	 * Gets the specified currency rate action with its attribute values set.
	 *
	 * @param currencyrate the specified currency rate action.
	 * @return the currency rate action.
	 */
	private Currencyrate getValue(Currencyrate currencyrate) {
		currencyrate.setId(Currency.Code.USD.name());
		currencyrate.setToid(currencyField.getValue());
		currencyrate.setDate(new Date());
		return currencyrate;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#setValue(net.cbtltd.shared.api.HasState)
	 */
	public void setValue(LookBook available) {
		fromtodateField.setValue(available.getFromdate());
		fromtodateField.setTovalue(available.getTodate());
		currencyField.setValue(available.getCurrency());
		attributeField.setValue(available.getAttributes());
	}

	/*
	 * Checks if the table of available properties has no selected items.
	 * 
	 * @return true if no items are selected.
	 */
	private boolean noSelectedItems() {
		if (availableTable.isVisible()) {return availableSelectionModel.isEmpty();}
		else if (specialTable.isVisible()) {return specialSelectionModel.isEmpty();}
		else return true;
	}

	/*
	 * Checks if the table of available properties has no selected items.
	 * 
	 * @return true if no items are selected.
	 */
	private int countSelectedItems() {
		if (availableTable.isVisible()) {return availableSelectionModel.getSelectedSet().size();}
		else if (specialTable.isVisible()) {return specialSelectionModel.getSelectedSet().size();}
		else return 0;
	}

	/*
	 * Gets the list of items selected in the table of available properties.
	 * 
	 * @return the list of items selected items.
	 */
	private ArrayList<AvailableItem> getSelectedItems() {
		if (availableTable.isVisible()) {return newSelectedItems(availableSelectionModel);}
		else if (specialTable.isVisible()) {return newSelectedItems(specialSelectionModel);}
		else {return null;}
	}

	/*
	 * Gets the list of items selected in the table of available properties.
	 * The client dates are set to the server dates because they are used by reservation and brochure 
	 * pop up panels which expect server dates.
	 * 
	 * @return the list of items selected items.
	 */
	private ArrayList<AvailableItem> newSelectedItems(TableSelectionModel<AvailableItem> selectionModel) {
		ArrayList<AvailableItem> items = new ArrayList<AvailableItem>();
		for (AvailableItem item : selectionModel.getSelectedSet()) {
			if (item.noFromdate()) {item.setFromdate(fromtodateField.getValue());}
			if (item.noTodate()) {item.setTodate(fromtodateField.getTovalue());}
			items.add(item);
		}
		return items;
	}

	/*
	 * Sets markers in the map to indicate the position of the available properties.
	 * 
	 * @param the list of available properties.
	 */
	private void setMarkers(ArrayList<AvailableItem> items) {
		mapField.clear();
		if (items != null && !items.isEmpty()) {
			LatLngBounds bounds = LatLngBounds.newInstance(LatLng.newInstance(0.0, 0.0), LatLng.newInstance(0.0, 0.0));
			for (AvailableItem item : items) {
				if (item.hasLatLng()) {
					mapField.addMarker(item.getLatLng(), item.getProductname());
					bounds.extend(item.getLatLng());
				}
			}
//TODO:			mapField.setValue(bounds.getCenter());
		}
		setZoomLevel();
	}

	/* Sets the map zoom level according to the positions of the available properties. */
	private void setZoomLevel() {
		Integer value = distanceField.getValue(0);
		Integer level = ZOOM_BASE - value;

		//Window.alert("Distance to zoom " + distanceField.getMappedValue(0) + " = " + level);
		level = (level > 20) ? 20 : level;
		level = (level < 3) ? 3 : level;
//		if (mapField.hasDefaultValue()) {level = 3;}
		mapField.setZoom(level);
//		if (locationField.noValue()) {locationField.setLocality(mapField.getCenter());}
		AbstractRoot.setIntegerValue(Party.Value.ZoomLevel.name(), level);
		AbstractRoot.setIntegerValue(Party.Value.ProgressActivity.name(), AbstractRoot.getIntegerValue(Party.Value.ProgressActivity.name()) + 1);
	}

	/*
	 * Sets the values and visibility of search criteria fields.
	 * 
	 * @param visible is true if the advanced search criteria are to be displayed.
	 */
	private void setAdvancedSearch(boolean visible) {
		if (!visible) {
			distanceField.setValue(DEFAULT_DISTANCE_INDEX);
			specialField.setValue(false);
			pricerangeField.setMaxRange();
			//currencyField.setValue(AbstractRoot.getCurrency());
			ratingField.setValue(0);
			commissionField.setValue(0);
			attributeField.deselect();
			setAttributeInvisible(true);
		}
		advancedLabel.setVisible(!visible);
		basicLabel.setVisible(visible);
		distanceField.setVisible(visible);
		distanceunitField.setVisible(visible);
		specialField.setVisible(visible);
		durationField.setVisible(visible);
		countLabel.setVisible(visible);
		exactcountLabel.setVisible(!visible);
		//pricePanel.setVisible(visible);
		pricerangeField.setVisible(visible);
		ratingField.setVisible(visible);
		commissionField.setVisible(!isWidget() && visible);
		attributeLabel.setVisible(visible);
		if (availableTable != null) {availableTable.execute(true);}
	}

	/*
	 * Creates the panel of search criteria fields.
	 * 
	 * @return the panel of search criteria fields.
	 */
	private VerticalPanel createSearch() {
		final VerticalPanel form = new VerticalPanel();
		form.addStyleName(AbstractField.CSS.cbtAbstractControl());

		//-----------------------------------------------
		// Advanced button
		//-----------------------------------------------
//		advancedLabel = new HTML(CONSTANTS.advancedLabel());
		advancedLabel = new Button(CONSTANTS.advancedLabel());
		advancedLabel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setAdvancedSearch(true);
			}
		});
		advancedLabel.addStyleName(CSS.searchLabel());
		advancedLabel.addStyleName(AbstractField.CSS.cbtAbstractCursor());
		form.add(advancedLabel);

		//-----------------------------------------------
		// Basic button
		//-----------------------------------------------
//		basicLabel = new HTML(CONSTANTS.basicLabel());
		basicLabel = new Button(CONSTANTS.basicLabel());
		basicLabel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setAdvancedSearch(false);
			}
		});
		basicLabel.addStyleName(CSS.searchLabel());
		basicLabel.addStyleName(AbstractField.CSS.cbtAbstractCursor());
		form.add(basicLabel);

		//-----------------------------------------------
		// Location field
		//-----------------------------------------------
		locationField = new LocationField(this, null,
				null,
				CONSTANTS.locationButton(),
				tab++);
		locationField.setDefaultValue(LatLng.newInstance(AbstractRoot.getDoubleValue(Party.Value.Latitude.name()), AbstractRoot.getDoubleValue(Party.Value.Longitude.name())));
		locationField.setFieldStyle(CSS.locationStyle());
		locationField.setHelpText(CONSTANTS.locationHelp());

		final HelpHTML whereLabel = new HelpHTML(CONSTANTS.whereLabel(), CONSTANTS.locationHelp(), locationField);
		whereLabel.addStyleName(CSS.searchLabel());
		whereLabel.addStyleName(CSS.searchLabelTop());
		form.add(whereLabel);
		form.add(locationField);

		//-----------------------------------------------
		// Distance from centre with one anchor slider
		// DISTANCE_INDEX is the zero based index into DISTANCE_MAP
		// DISTANCE_MAP translates the index into the distances:
		// 500m, 1km, 2km, 5km, 10km, 20km, 50km, 100km
		//-----------------------------------------------
		final Integer[] DISTANCE_INDEX = {0, 7, 1, 7};
		final Double[] DISTANCE_MAP = {0.5, 1.0, 2.0, 5.0, 10.0, 20.0, 50.0, 100.0};
		distanceField = new SliderField(this, null,
				CONSTANTS.distanceLabel(),
				DISTANCE_INDEX,
				tab++);
		distanceField.setValueMap(DISTANCE_MAP);
		distanceField.setUnit(CONSTANTS.distanceUnit());
		distanceField.setDefaultValue(DEFAULT_DISTANCE_INDEX);
		distanceField.setFieldStyle(CSS.distanceStyle());
		distanceField.setHelpText(CONSTANTS.distanceHelp());
//		form.add(distanceField);

		//-----------------------------------------------
		// Toggle field between kilometer/mile
		//-----------------------------------------------
		distanceunitField = new ToggleField(this, null,
				CONSTANTS.distanceunitLabel(),
				CONSTANTS.distanceunitKmt(),
				CONSTANTS.distanceunitSmi(),
				tab++);
		distanceunitField.setFieldStyle(CSS.distanceunitStyle());
//		distanceunitField.setDefaultValue(AbstractRoot.getBooleanValue(Party.Value.Countunit.name()));
		distanceunitField.setHelpText(CONSTANTS.countunitHelp());
		
		HorizontalPanel distance = new HorizontalPanel();
		distance.addStyleName(AbstractField.CSS.cbtAbstractField());
		distance.addStyleName(CSS.labelStyle());
		form.add(distance);
		distance.add(distanceField);
		distance.add(distanceunitField);

		final HTML whenLabel = new HTML(CONSTANTS.whenLabel(), true);
		whenLabel.addStyleName(CSS.searchLabel());
		form.add(whenLabel);

		//-----------------------------------------------
		// Check to show only specials
		//-----------------------------------------------
		specialField = new ToggleField(this, null,
				CONSTANTS.specialLabel(),
				CONSTANTS.specialupLabel(),
				CONSTANTS.specialdownLabel(),
				tab++);
		specialField.setLabelStyle(CSS.specialLabel());
		specialField.setFieldStyle(CSS.specialField());
		specialField.setHelpText(CONSTANTS.specialHelp());
		form.add(specialField);

		//-----------------------------------------------
		// Date Range for reservation or special
		//-----------------------------------------------
		fromtodateField = new DatespanField(this, null,
				CONSTANTS.fromtodateLabel(),
				tab++);
		fromtodateField.setFieldStyle(CSS.fromtodateField());
		fromtodateField.setHelpText(CONSTANTS.fromtodateHelp());
		//form.add(fromtodateField);

		//-----------------------------------------------
		// Check to show on and off line availability
		//-----------------------------------------------
		offlineField = new ToggleField(this, null,
				CONSTANTS.offlineLabel(),
				CONSTANTS.offlineupLabel(),
				CONSTANTS.offlinedownLabel(),
				tab++);
		offlineField.setLabelStyle(CSS.offlineLabel());
		offlineField.setFieldStyle(CSS.offlineField());
		offlineField.setHelpText(CONSTANTS.offlineHelp());
		//form.add(specialField);
		final HorizontalPanel fo = new HorizontalPanel();
		fo.add(fromtodateField);
		fo.add(offlineField);
		form.add(fo);
		
		//-----------------------------------------------
		// Days field
		//-----------------------------------------------
		final Integer[] DAYS = {0, 90, 1, 0};
		durationField = new SliderField(this, null,
				CONSTANTS.durationLabel(),
				DAYS,
				tab++);
		durationField.setUnit(CONSTANTS.durationUnit());
		durationField.setFieldStyle(CSS.daysStyle());
		durationField.setHelpText(CONSTANTS.durationHelp());
		form.add(durationField);

		//-----------------------------------------------
		// Duration range with two anchor slider
		//-----------------------------------------------
		final Integer[] DAYSRANGE = {1, 30, 1, 1, 30};
		daysrangeField = new SliderField(this, null,
				CONSTANTS.daysrangeLabel(),
				DAYSRANGE,
				tab++);
		daysrangeField.setUnit(CONSTANTS.daysrangeUnit());
		daysrangeField.setFieldStyle(CSS.pricerangeStyle());
		daysrangeField.setVisible(false);
		daysrangeField.setHelpText(CONSTANTS.daysrangeHelp());
		form.add(daysrangeField);

		whatLabel = new HTML(CONSTANTS.whatLabel(), true);
		whatLabel.addStyleName(CSS.searchLabel());
		whatLabel.setVisible(true);
		form.add(whatLabel);

		HorizontalPanel count = new HorizontalPanel();
		count.addStyleName(AbstractField.CSS.cbtAbstractField());
		count.addStyleName(CSS.labelStyle());
		form.add(count);

		//-----------------------------------------------
		// Count field for number of people/rooms
		//-----------------------------------------------
		countField = new SpinnerField(this, null,
				1,
				99,
				null, //CONSTANTS.countLabel(),				
				tab++);
		countField.setFieldStyle(CSS.countField());
		countField.setDefaultValue(1);
		countField.setHelpText(CONSTANTS.countHelp());
		countLabel = new HelpLabel(CONSTANTS.countLabel(), CONSTANTS.countHelp(), countField);
		count.add(countLabel);
		exactcountLabel = new HelpLabel(CONSTANTS.countLabel(), CONSTANTS.countHelp(), countField);
		count.add(exactcountLabel);
		count.add(countField);

		//-----------------------------------------------
		// Toggle field between people/rooms
		//-----------------------------------------------
		countunitField = new ToggleField(this, null,
				null, //CONSTANTS.countunitLabel(),
				CONSTANTS.countunitBedroom(),
				CONSTANTS.countunitPerson(),
				tab++);
		countunitField.setFieldStyle(CSS.countunitStyle());
		countunitField.setDefaultValue(AbstractRoot.getBooleanValue(Party.Value.Countunit.name()));
		countunitField.setHelpText(CONSTANTS.countunitHelp());
		HelpLabel countunitLabel = new HelpLabel(CONSTANTS.countunitLabel(), CONSTANTS.countunitHelp(), countunitField);
		count.add(countunitLabel);
		count.add(countunitField);

		pricePanel = new HorizontalPanel();
		pricePanel.addStyleName(AbstractField.CSS.cbtAbstractField());
		form.add(pricePanel);

		//-----------------------------------------------
		// Currency field
		//-----------------------------------------------
		pricePanel.addStyleName(CSS.labelStyle());
		currencyField = new ListField(this,  null,
				new CurrencyrateNameId(),
				null,
				false,
				tab++);
		currencyField.setFieldStyle(CSS.currencyField());
		currencyField.setDefaultValue(AbstractRoot.getValue(Party.Value.Currency.name()));
		currencyField.setHelpText(CONSTANTS.currencyHelp());
		currencyLabel = new HelpLabel(CONSTANTS.currencyLabel(), CONSTANTS.currencyHelp(), currencyField);
		pricePanel.add(currencyLabel);
		pricePanel.add(currencyField);

		//-----------------------------------------------
		// Price range unit field between stay/night
		//-----------------------------------------------
		priceunitField = new ToggleField(this, null,
				null,
				CONSTANTS.priceunitNight(),
				CONSTANTS.priceunitStay(),
				tab++);
		priceunitField.setFieldStyle(CSS.priceunitStyle());
		priceunitField.setDefaultValue(AbstractRoot.getBooleanValue(Party.Value.Priceunit.name()));
		priceunitField.setHelpText(CONSTANTS.priceunitHelp());
		priceunitLabel = new HelpLabel(CONSTANTS.priceunitLabel(), CONSTANTS.priceunitHelp(), priceunitField);
		pricePanel.add(priceunitLabel);
		pricePanel.add(priceunitField);

		//-----------------------------------------------
		// Price range with two anchor slider
		//-----------------------------------------------
		final Integer[] PRICERANGE = {0, 2000, 50, 0, 2000};
		pricerangeField = new SliderField(this, null,
				CONSTANTS.pricerangeLabel(),
				PRICERANGE,
				tab++);
		pricerangeField.setUnlimitedValue(Integer.MAX_VALUE);
		pricerangeField.setFieldStyle(CSS.pricerangeStyle());
		pricerangeField.setHelpText(CONSTANTS.pricerangeHelp());
		form.add(pricerangeField); 

		//-----------------------------------------------
		// Rating range with one anchor slider
		//-----------------------------------------------
		final Integer[] RATINGS = {0, 10, 1, 0};
		ratingField = new SliderField(this, null,
				CONSTANTS.ratingLabel(),
				RATINGS,
				tab++) {
			public Widget getWidget(int value) {
				return new Image(AbstractField.STARS[value]);
			}			
		};
		ratingField.setSliderStyle(CSS.ratingSlider());
		ratingField.setHelpText(CONSTANTS.ratingHelp());
		form.add(ratingField);

		//-----------------------------------------------
		// Minimum commission with one anchor slider
		//-----------------------------------------------
		Integer[] COMMISSION = {0, 50, 1, 0};
		commissionField = new SliderField(this, null,
				CONSTANTS.commissionLabel(),
				COMMISSION,
				tab++);
		commissionField.setUnit(CONSTANTS.commissionUnit());
		commissionField.setFieldStyle(CSS.commissionStyle());
		commissionField.setHelpText(CONSTANTS.commissionHelp());
		form.add(commissionField);

		//-----------------------------------------------
		// Attribute shuttle
		//-----------------------------------------------
		attributeField = new StackField(this, null,
				new AttributeMapAction(Attribute.ACCOMMODATION_SEARCH, Attribute.RZ, AbstractRoot.getLanguage()),
				null,
				tab++);
		attributeField.setUniquekeys(Attribute.UNIQUE_KEYS);
		attributeField.setHelpText(CONSTANTS.attributeHelp());

		attributeButton = new Button(CONSTANTS.attributeButton(), new ClickHandler() {
			public void onClick(ClickEvent event) {
				attributeField.deselect();
				attributeButton.setVisible(false);
				event.stopPropagation();
				availableTable.execute();
				specialTable.execute();
			}
		});
//		attributeButton.addStyleName(CSS.attributeButton());
		attributeButton.setVisible(false);
		final HorizontalPanel header = new HorizontalPanel();
		
		attributeLabel = new DisclosurePanel("");//CONSTANTS.attributeLabel());
		
		//http://www.summa-tech.com/blog/2010/04/19/gwt-disclosurepanel-openclose-without-header/
		Widget defaultHeader = attributeLabel.getHeader();
		//HorizontalPanel newHeader = new HorizontalPanel();
		header.add(defaultHeader);
		//Widget widget = new Button(CONSTANTS.attributeLabel());
		header.add(new Button(CONSTANTS.attributeLabel()));
//		attributeLabel.setHeader(newHeader);

//		attributeLabel.getHeader().addStyleName(CSS.attributeHeader());
//		header.add(attributeLabel.getHeader());
		header.add(attributeButton);
		attributeLabel.setHeader(header);
		attributeLabel.setAnimationEnabled(true);
		final ScrollPanel scroll  = new ScrollPanel();
		scroll.add(attributeField);
		scroll.addStyleName(CSS.attributeContent());
		attributeLabel.setContent(scroll);
		attributeLabel.addOpenHandler(new OpenHandler<DisclosurePanel>() {
			public void onOpen(OpenEvent<DisclosurePanel> event) {
				setAttributeInvisible(false);
			}
		});
		attributeLabel.addCloseHandler(new CloseHandler<DisclosurePanel>() {
			public void onClose(CloseEvent<DisclosurePanel> event) {
				setAttributeInvisible(true);
			}
		});
		form.add(attributeLabel);

		actionLabel = new HelpHTML(CONSTANTS.wishLabel(), CONSTANTS.attributeHelp(), attributeLabel.getHeader());
		actionLabel.addStyleName(CSS.searchLabel());
		form.add(actionLabel);

		final HorizontalPanel bar = new HorizontalPanel();
		bar.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		bar.addStyleName(AbstractField.CSS.cbtAbstractCommand());

		Button previewButton = new Button(CONSTANTS.previewButton(), new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (noSelectedItems()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.previewbuttonError(), availableTable);}
				else {BrochurePopup.getInstance().show(getSelectedItems());}
			}
		});
		previewButton.addStyleName(AbstractField.CSS.cbtCommandButton());
		previewButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		previewButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		bar.add(previewButton);

		Button bookButton = new Button(CONSTANTS.bookButton(), new ClickHandler() {
			public void onClick(ClickEvent event) {
				Date fromdate = Time.getDateServer(fromtodateField.getValue());
				Date todate = Time.getDateServer(fromtodateField.getTovalue());
				if (countSelectedItems() != 1) {AbstractField.addMessage(Level.ERROR, CONSTANTS.bookbuttonError(), availableTable);}
				else if (AbstractRoot.readable(AccessControl.ORGANIZATION_ROLES)) {AbstractRoot.render(Razor.RESERVATION_TAB, getSelectedItems().get(0).getReservation(fromdate, todate, AbstractRoot.getActorid(), null, null));}
				else {ReservationPopup.getInstance().show(getSelectedItems().get(0).getReservation(fromdate, todate, AbstractRoot.getActorid(), null, null), availableTable.isVisible() ? availableTable : specialTable);}
			}
		});
		bookButton.addStyleName(AbstractField.CSS.cbtCommandButton());
		bookButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		bookButton.addStyleName(AbstractField.CSS.cbtGradientGreen());
		bar.add(bookButton);

		form.add(bar);

		setAdvancedSearch(false);
		components.onReset();
		currencyrateRead.execute();
		return form;
	}
	
	/* 
	 * Sets if the search attributes are visible. 
	 * 
	 * @param true, if the search attributes are visible. 
	 */
	private void setAttributeInvisible(Boolean visible) {
		actionLabel.setVisible(visible);
		countLabel.setVisible(visible);
		countField.setVisible(visible);
		countunitField.setVisible(visible);
		priceunitLabel.setVisible(visible);
		priceunitField.setVisible(visible);
		currencyLabel.setVisible(visible);
		currencyField.setVisible(visible);
		pricerangeField.setVisible(visible);
		ratingField.setVisible(visible);
		commissionField.setVisible(!isWidget() && visible);
	}

	/*
	 * Creates the map.
	 * 
	 * @return the map.
	 */
	private MapField createMap() {
		mapField = new MapField(this, null, tab++);
		mapField.setFieldStyle(CSS.mapStyle());
		mapField.setEnabled(true);
		mapField.setPositionVisible(false);
		mapField.setStreetviewVisible(false);
		mapField.setStreetviewClickable(false);
		mapField.setZoom(Math.max(3, AbstractRoot.getIntegerValue(Party.Value.ZoomLevel.name())));
		mapField.setMapTypeId(MapTypeId.HYBRID);
		mapField.setDefaultValue(LatLng.newInstance(AbstractRoot.getDoubleValue(Party.Value.Latitude.name()), AbstractRoot.getDoubleValue(Party.Value.Longitude.name())));
		mapField.setScrollWheelZoomEnabled(false);

		mapField.setZoom(AbstractRoot.getIntegerValue(Party.Value.ZoomLevel.name()));

		mapField.addZoomChangeHandler(new ZoomChangeMapHandler() {
			public void onEvent(ZoomChangeMapEvent event) {
				int zoom = mapField.getZoom().intValue();
				int value = Math.max(0, ZOOM_BASE - zoom);
				if (zoom > ZOOM_POSITIONS) {
					distanceField.setValue(value);
//TODO:					availableTable.execute();
				}
				else {
					availableTable.clear(); //setValue(null);
					productPositions.execute();
				}
			}
		});
		return mapField;
	}

	/*
	 * Gets the available property corresponding to the specified position.
	 * 
	 * @param the specified position.
	 * @return the available property.
	 */
	private AvailableItem getAvailableItem(LatLng position) {
		ArrayList<AvailableItem> items = availableTable.getValue();
		if (items == null || items.isEmpty()) {return null;}
		for (AvailableItem item : items) {
			if (position == item.getLatLng()) {return item;}
		}
		return null;
	}

	/*
	 * Creates the panel to be displayed if there are no results to display in the table of available properties.
	 * 
	 * @return the empty panel.
	 */
	private Widget availabletableEmpty() {
		FlowPanel panel = new FlowPanel();
		panel.add(new HTML(CONSTANTS.tableEmpty()));
		panel.addStyleName(AbstractField.CSS.cbtTableFieldEmpty());
		
		Label tableEmpty1 = new Label(CONSTANTS.tableEmpty1());
		tableEmpty1.addStyleName(AbstractField.CSS.cbtTableFieldEmptyHyperlink());
		tableEmpty1.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				locationField.setName("cape town");
				distanceField.setValue(3);
				countField.setValue(2);
				countunitField.setValue(true);
				specialField.setValue(false);
				durationField.setValue(0);
				pricerangeField.setMaxRange();
				ratingField.setValue(0);
				commissionField.setValue(0);
				attributeField.deselect();
			}
		});
		panel.add(tableEmpty1);

		Label tableEmpty2 = new Label(CONSTANTS.tableEmpty2());
		tableEmpty2.addStyleName(AbstractField.CSS.cbtTableFieldEmptyHyperlink());
		tableEmpty2.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				locationField.setName("knysna");
				distanceField.setValue(5);
				countField.setValue(3);
				countunitField.setValue(false);
				specialField.setValue(false);
				durationField.setValue(0);
				pricerangeField.setMaxRange();
				ratingField.setValue(0);
				commissionField.setValue(0);
				attributeField.deselect();
			}
		});
		panel.add(tableEmpty2);

		Label tableEmpty3 = new Label(CONSTANTS.tableEmpty3());
		tableEmpty3.addStyleName(AbstractField.CSS.cbtTableFieldEmptyHyperlink());
		tableEmpty3.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				locationField.setName("mauritius");
				distanceField.setValue(6);
				countField.setValue(4);
				countunitField.setValue(false);
				specialField.setValue(false);
				durationField.setValue(0);
				pricerangeField.setMaxRange();
				ratingField.setValue(0);
				commissionField.setValue(0);
				attributeField.deselect();
			}
		});
		panel.add(tableEmpty3);
		panel.add(new Image(BUNDLE.tableEmpty()));
		return panel;
	}

	/*
	 * Creates the table of available properties.
	 * 
	 * @return the table of available properties.
	 */
	private TableField<AvailableItem> createAvailableTable() {
		
		availableSelectionModel = new TableSelectionModel<AvailableItem>();
		SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {}
		};
		availableSelectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// The key provider that allows keys to be formed from row values.
		// This is used to pass a style to StyledCells.
		//-----------------------------------------------
		final ProvidesKey<AvailableItem> keyProvider = new ProvidesKey<AvailableItem>() {
			public String getKey(AvailableItem item) {
				if (item.isSpecial(0.85)) {return "color: red;";}
				if (item.isSpecial(1.0)) {return "color: orange;";}
				else {return "color: black;";}
			}
		};

		availableTable = new TableField<AvailableItem>(this, null,
				new LookBook(),
				availableSelectionModel,
				keyProvider,
				TABLE_ROWS,
				tab++);

		availableTable.addStyleName(CSS.tableStyle());
		availableTable.setEmptyValue(availabletableEmpty());
		availableTable.setOrderby(AvailableItem.RANK);

		availableTable.setTableError(new TableError() {
			@Override
			public boolean error() {
				return (
						specialField.getValue()
						|| mapField.noValue()
						|| distanceField.noValue()
						|| fromtodateField.noValue()
						|| countField.noValue()
						|| countunitField.noValue()
						|| pricerangeField.noValue()
						|| priceunitField.noValue()
						|| currencyField.noValue()
						|| ratingField.noValue()
						|| commissionField.noValue()
				);
			}
		});

		availableTable.setTableExecutor(new TableExecutor<LookBook>() {
			public void execute(LookBook action) {getValue(action);}
		});

		int col = 0;

		//-----------------------------------------------
		// Product column
		//-----------------------------------------------
		Column<AvailableItem, String> productname = new Column<AvailableItem, String>( new TextCell() ) {
			@Override
			public String getValue( AvailableItem availableitem ) {return availableitem.getProductname(28);}
		};
		availableTable.addStringColumn(productname, CONSTANTS.availableHeader()[col++], AvailableItem.PRODUCTNAME);

		//-----------------------------------------------
		// Product Info column
		//-----------------------------------------------
		Column<AvailableItem, AvailableItem> productinfo = new Column<AvailableItem, AvailableItem>( new ImageCell<AvailableItem>(AbstractField.PRODUCTS, AbstractField.CSS.cbtInfoButton(),
				new ImageCell.Delegate<AvailableItem>() {
			public void execute( AvailableItem availableitem ){
				BrochurePopup.getInstance().show(availableitem);
			}
		}
				)) {
			@Override
			public AvailableItem getValue( AvailableItem availableitem ) {return availableitem;}
		};
		availableTable.addColumn(productinfo, CONSTANTS.availableHeader()[col++], AvailableItem.PRODUCTNAME);

		//-----------------------------------------------
		// Supplier column
		//-----------------------------------------------
		Column<AvailableItem, String> suppliername = new Column<AvailableItem, String>( new TextCell() ) {
			@Override
			public String getValue(AvailableItem availableitem ) {return availableitem.getSuppliername(12);}
		};
		availableTable.addStringColumn(suppliername, CONSTANTS.availableHeader()[col++], AvailableItem.SUPPLIERNAME);

		//-----------------------------------------------
		// Supplier Info column
		//-----------------------------------------------
		if (isWidget()) {col++;}
		else {
			Column<AvailableItem, AvailableItem> supplierinfo = new Column<AvailableItem, AvailableItem>( new ImageCell<AvailableItem>(AbstractField.BUNDLE.info(), AbstractField.CSS.cbtInfoButton(),
					new ImageCell.Delegate<AvailableItem>(){
				public void execute( AvailableItem availableitem ) {
					if (availableitem.hasRank()) {PartyPopup.getInstance().show(availableitem.getSupplierid());}
					else {AbstractField.addMessage(Level.VERBOSE, CONSTANTS.productRank(), offlineField);}
				}
			}
					)) {
				@Override
				public AvailableItem getValue( AvailableItem availableitem ) {return availableitem;}
			};
			availableTable.addColumn(supplierinfo, CONSTANTS.availableHeader()[col++], AvailableItem.PRODUCTNAME);
		}
		
		//-----------------------------------------------
		// Room Count column
		//-----------------------------------------------
		Column<AvailableItem, Number> room = new Column<AvailableItem, Number>(new NumberCell(AbstractField.IF)) {
			@Override
			public Integer getValue( AvailableItem availableitem ) {return availableitem.getRoom();}
		};
		availableTable.addNumberColumn(room, CONSTANTS.availableHeader()[col++], AvailableItem.ROOM);

		//-----------------------------------------------
		// Quote column
		//-----------------------------------------------
		Column<AvailableItem, Number> quote = new Column<AvailableItem, Number>(new StyledNumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( AvailableItem availableitem ) {return availableitem.getQuote();}
		};
		availableTable.addNumberColumn(quote, CONSTANTS.availableHeader()[col++], AvailableItem.QUOTE);

		//-----------------------------------------------
		// STO column
		//-----------------------------------------------
//		Column<AvailableItem, Number> cost = new Column<AvailableItem, Number>(new NumberCell(AbstractField.AF)) {
//			@Override
//			public Double getValue( AvailableItem availableitem ) {return availableitem.getCost();}
//		};
//		availableTable.addNumberColumn( cost, CONSTANTS.availableHeader()[col++], AvailableItem.COST);

		//-----------------------------------------------
		// Commission column
		//-----------------------------------------------
		Column<AvailableItem, Number> commission = new Column<AvailableItem, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( AvailableItem availableitem ) {return availableitem.getCommission();}
		};
		availableTable.addNumberColumn( commission, CONSTANTS.availableHeader()[col++], AvailableItem.COMMISSION);

		//-----------------------------------------------
		// Price column
		//-----------------------------------------------
		Column<AvailableItem, Number> rack = new Column<AvailableItem, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( AvailableItem availableitem ) {return availableitem.getPrice();}
		};
		availableTable.addNumberColumn( rack, CONSTANTS.availableHeader()[col++], AvailableItem.PRICE);

		//-----------------------------------------------
		// Rating column
		//-----------------------------------------------
		Column<AvailableItem, Integer> rating = new Column<AvailableItem, Integer>( new ImageCell<Integer>(AbstractField.STARS, CSS.ratingStyle())) {
			@Override
			public Integer getValue( AvailableItem availableitem ) {return availableitem.getKey();}
		};
		availableTable.addColumn(rating, CONSTANTS.availableHeader()[col++], AvailableItem.RATING);

		return availableTable;
	}

	/*
	 * Creates the table of available properties on special offer.
	 * 
	 * @return the table of available properties on special offer.
	 */
	private TableField<AvailableItem> createSpecialTable() {

		specialSelectionModel = new TableSelectionModel<AvailableItem>();
		SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
			}
		};
		specialSelectionModel.addSelectionChangeHandler(selectionHandler);

		specialTable = new TableField<AvailableItem> (this, null,
				new LookBookSpecial(),
				specialSelectionModel,
				TABLE_ROWS,
				tab++);

		specialTable.addStyleName(CSS.tableStyle());
		specialTable.setEmptyValue(availabletableEmpty());

		specialTable.setTableError(new TableError() {
			@Override
			public boolean error() {
				return (
						!specialField.getValue()
						|| mapField.noValue()
						|| distanceField.noValue()
						|| fromtodateField.noValue()
						|| countField.noValue()
						|| countunitField.noValue()
						|| pricerangeField.noValue()
						|| priceunitField.noValue()
						|| currencyField.noValue()
						|| ratingField.noValue()
						|| commissionField.noValue()
						);
			}
		});

		specialTable.setTableExecutor(new TableExecutor<LookBookSpecial>() {
			public void execute(LookBookSpecial action) {
				getValue(action);
			}
		});

		int col = 0;

		//-----------------------------------------------
		// Product column
		//-----------------------------------------------
		Column<AvailableItem, String> name = new Column<AvailableItem, String>( new TextCell() ) {
			@Override
			public String getValue( AvailableItem availableitem ) {return availableitem.getProductname(20);}
		};
		specialTable.addStringColumn(name, CONSTANTS.specialHeader()[col++], AvailableItem.PRODUCTNAME);

		//-----------------------------------------------
		// Product Info column
		//-----------------------------------------------
		Column<AvailableItem, AvailableItem> productinfo = new Column<AvailableItem, AvailableItem>( new ImageCell<AvailableItem>(AbstractField.BUNDLE.info(), AbstractField.CSS.cbtInfoButton(),
				new ImageCell.Delegate<AvailableItem>() {
			public void execute( AvailableItem availableitem ) {
				BrochurePopup.getInstance().show(availableitem);
			}
		}
				)) {
			@Override
			public AvailableItem getValue( AvailableItem availableitem ) {return availableitem;}
		};
		specialTable.addColumn(productinfo, CONSTANTS.specialHeader()[col++], AvailableItem.PRODUCTNAME);

		//-----------------------------------------------
		// Supplier column
		//-----------------------------------------------
		Column<AvailableItem, String> supplier = new Column<AvailableItem, String>( new TextCell() ) {
			@Override
			public String getValue( AvailableItem availableitem ) {return availableitem.getSuppliername(15);}
		};
		specialTable.addStringColumn(supplier, CONSTANTS.specialHeader()[col++], AvailableItem.SUPPLIERNAME);

		//-----------------------------------------------
		// Supplier Info column
		//-----------------------------------------------
		Column<AvailableItem, AvailableItem> supplierinfo = new Column<AvailableItem, AvailableItem>( new ImageCell<AvailableItem>(AbstractField.BUNDLE.info(), AbstractField.CSS.cbtInfoButton(),
				new ImageCell.Delegate<AvailableItem>(){
			public void execute( AvailableItem availableitem ){
				if (availableitem.hasRank()) {PartyPopup.getInstance().show(availableitem.getSupplierid());}
			}
		}
				)) {
			@Override
			public AvailableItem getValue( AvailableItem availableitem ) {return availableitem;}
		};
		specialTable.addColumn(supplierinfo, CONSTANTS.specialHeader()[col++], AvailableItem.PRODUCTNAME);

		//-----------------------------------------------
		// Days column
		//-----------------------------------------------
		Column<AvailableItem, Number> days = new Column<AvailableItem, Number>(new NumberCell(AbstractField.SIF)) {
			@Override
			public Double getValue( AvailableItem availableitem ) {return availableitem.getDuration(Time.DAY);}
		};
		specialTable.addNumberColumn(days, CONSTANTS.specialHeader()[col++], AvailableItem.DURATION);

		//-----------------------------------------------
		// From Date column
		//-----------------------------------------------
		Column<AvailableItem, Date> fromdate = new Column<AvailableItem, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( AvailableItem availableitem ) {return Time.getDateClient(availableitem.getFromdate());}
		};
		specialTable.addDateColumn(fromdate, CONSTANTS.specialHeader()[col++], AvailableItem.FROMDATE);

		//-----------------------------------------------
		// To Date column
		//-----------------------------------------------
		Column<AvailableItem, Date> todate = new Column<AvailableItem, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( AvailableItem availableitem ) {return Time.getDateClient(availableitem.getTodate());}
		};
		specialTable.addDateColumn(todate, CONSTANTS.specialHeader()[col++], AvailableItem.TODATE);

		//-----------------------------------------------
		// Quote column
		//-----------------------------------------------
		Column<AvailableItem, Number> quote = new Column<AvailableItem, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( AvailableItem availableitem ) {return availableitem.getQuote();}
		};
		specialTable.addNumberColumn(quote, CONSTANTS.specialHeader()[col++], AvailableItem.QUOTE);

		//-----------------------------------------------
		// STO column
		//-----------------------------------------------
		//		Column<AvailableItem, Number> cost = new Column<AvailableItem, Number>(new NumberCell(AbstractField.AF)) {
		//			@Override
		//			public Double getValue( AvailableItem availableitem ) {return availableitem.getCost();}
		//		};
		//		specialTable.alignRightCol(col);
		//		specialTable.addNumberColumn( cost, CONSTANTS.specialHeader()[col++], AvailableItem.COST);

		//-----------------------------------------------
		// Commission column
		//-----------------------------------------------
		Column<AvailableItem, Number> commission = new Column<AvailableItem, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( AvailableItem availableitem ) {return availableitem.getCommission();}
		};
		specialTable.addNumberColumn( commission, CONSTANTS.specialHeader()[col++], AvailableItem.COMMISSION);

		//-----------------------------------------------
		// Price column
		//-----------------------------------------------
		Column<AvailableItem, Number> price = new Column<AvailableItem, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( AvailableItem availableitem ) {return availableitem.getPrice();}
		};
		specialTable.addNumberColumn( price, CONSTANTS.specialHeader()[col++], AvailableItem.PRICE);

		//-----------------------------------------------
		// Rating column
		//-----------------------------------------------
		Column<AvailableItem, Integer> rating = new Column<AvailableItem, Integer>( new ImageCell<Integer>(AbstractField.STARS, CSS.ratingStyle())) {
			@Override
			public Integer getValue( AvailableItem availableitem ) {return availableitem.getKey();}
		};
		specialTable.addColumn(rating, CONSTANTS.specialHeader()[col++], AvailableItem.RATING);
		specialTable.setVisible(false);
		return specialTable;
	}

	/* 
	 * Sets the HTML text to be displayed in a map info string and add it the map in the correct position.
	 * 
	 * @param item the property to be displayed.
	 */
	private void setInfoString(AvailableItem item) {
		if (item == null) {return;}
		StringBuilder sb = new StringBuilder();
		sb.append("<table>");
		sb.append("<tr><th>").append(item.getProductname()).append("</th></tr>");
		sb.append("<tr><td>").append(item.getSuppliername()).append("</td></tr>");
		sb.append("<tr><td>").append(currencyField.getValue()).append(" ").append(AbstractField.AF.format(item.getPrice())).append("</td></tr>");
		sb.append("</table>");
		mapField.setInfoString(LatLng.newInstance(item.getLatitude(), item.getLongitude()), sb.toString());
	}
	
	private void triggerResize() {
		if(mapField != null) {
			mapField.triggerResize();
}
	}
}



