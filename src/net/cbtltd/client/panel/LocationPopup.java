package net.cbtltd.client.panel;

import java.util.ArrayList;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.HasValue;
import net.cbtltd.client.LocalRequest;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.CommandButton;
import net.cbtltd.client.field.DoublespanField;
import net.cbtltd.client.field.DoubleunitField;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.location.LocationConstants;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.location.AreaNameId;
import net.cbtltd.shared.location.CountryNameId;
import net.cbtltd.shared.location.LocationCreate;
import net.cbtltd.shared.location.LocationDelete;
import net.cbtltd.shared.location.LocationRead;
import net.cbtltd.shared.location.LocationUpdate;
import net.cbtltd.shared.location.RegionNameId;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LocationPopup
	extends AbstractPopup<Location> {

	private static final LocationConstants CONSTANTS = GWT.create(LocationConstants.class);
//	private static final LocationBundle BUNDLE = LocationBundle.INSTANCE;
//	private static final LocationCssResource CSS = BUNDLE.css();
	private static final String[] UNITS = {Unit.FOT, Unit.MTR};

	private static GuardedRequest<Location> locationCreate;
	private static GuardedRequest<Location> locationRead;
	private static GuardedRequest<Location> locationUpdate;
	private static GuardedRequest<Location> locationDelete;

	private TextField nameField;
	private ListField countryField;
	private ListField regionField;
	private ListField areaField;
	private TextField codeField;
	private TextField iataField;
	private DoublespanField latlngField;
	private DoubleunitField altitudeField;
	private TextAreaField notesField;

	private static HasValue<String> parentField;
	private String id;
	private boolean noId() { return id == null || id.isEmpty();}

	/** Instantiates a new location pop up panel. */
	public LocationPopup() {
		super(true);
		createActions();
		setWidget(createContent());
	}

	private static LocationPopup instance;
	
	/**
	 * Gets the single instance of LocationPopup.
	 *
	 * @return single instance of LocationPopup
	 */
	public static synchronized LocationPopup getInstance() {
		if (instance == null) {instance = new LocationPopup();}
		parentField = null;
		return instance;
	}

	/**
	 * Shows the panel for an location of the specified type.
	 *
	 * @param type the specified type.
	 * @param parentField the field that invoked the pop up panel.
	 */
	public void show(HasValue<String> parentField) {
		LocationPopup.parentField = parentField;
		onReset(Location.INITIAL);
		locationCreate.execute(true);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue()
	 */
	@Override
	public Location getValue() {return getValue(new Location());}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue(java.lang.Object)
	 */
	private Location getValue(Location location){
		location.setState(state);
		location.setOrganizationid(AbstractRoot.getOrganizationid());
		location.setActorid(AbstractRoot.getActorid());
		location.setId(id);
		location.setName(nameField.getValue());
		location.setCountry(countryField.getValue());
		location.setRegion(regionField.getValue());
		location.setArea(areaField.getValue());
		location.setCode(codeField.getValue());
		location.setIata(iataField.getValue());
		location.setNotes(notesField.getValue());
		location.setLatitude(latlngField.getValue());
		location.setLongitude(latlngField.getTovalue());
		location.setAltitude(altitudeField.getValue());
		Log.debug("getValue " + location);
		return location;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(Location location) {
		Log.debug("setValue " + location);
		if (location == null) {onReset(Location.CREATED);}
		else {
			setResetting(true);
			onStateChange(location.getState());
			id = location.getId();
			nameField.setValue(location.getName());
			countryField.setValue(location.getCountry());
			regionField.setValue(location.getRegion());
			areaField.setValue(location.getArea());
			codeField.setValue(location.getCode());
			iataField.setValue(location.getIata());
			notesField.setValue(location.getNotes());
			latlngField.setValue(location.getLatitude());
			latlngField.setTovalue(location.getLongitude());
			altitudeField.setValue(location.getAltitude());
			center();
			setResetting(false);
		}
	}

	/*
	 * Creates the panel of location fields.
	 * 
	 * @return the panel of location fields.
	 */
	private VerticalPanel createContent() {
		VerticalPanel form = new VerticalPanel();
		final Label title = new Label(CONSTANTS.locationLabel());
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);

		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				LocationPopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);

		//-----------------------------------------------
		// Name (Resort) field
		//-----------------------------------------------
		nameField = new TextField(this, null,
				CONSTANTS.nameLabel(),
				tab++);
		nameField.setMaxLength(50);
		nameField.setHelpText(CONSTANTS.nameHelp());
		form.add(nameField);

		//-----------------------------------------------
		// Country field
		//-----------------------------------------------
		countryField = new ListField(this, null,
				new CountryNameId(),
				CONSTANTS.countryLabel(),
				false,
				tab++);
		countryField.setHelpText(CONSTANTS.countryHelp());
		form.add(countryField);

		//-----------------------------------------------
		// Region field
		//-----------------------------------------------
		regionField = new ListField(this, null,
				new RegionNameId(),
				CONSTANTS.regionLabel(),
				false,
				tab++);
		regionField.setHelpText(CONSTANTS.regionHelp());
		form.add(regionField);

		//-----------------------------------------------
		// Area field
		//-----------------------------------------------
		areaField = new ListField(this, null,
				new AreaNameId(),
				CONSTANTS.areaLabel(),
				false,
				tab++);
		areaField.setHelpText(CONSTANTS.areaHelp());
		form.add(areaField);

		//-----------------------------------------------
		// ISO Code field
		//-----------------------------------------------
		codeField = new TextField(this,  null,
				CONSTANTS.codeLabel(),
				tab++);
		codeField.setEnabled(false);
		codeField.setFieldHalf();
		codeField.setHelpText(CONSTANTS.codeHelp());
		//form.add(codeField);

		//-----------------------------------------------
		// IATA Code field
		//-----------------------------------------------
		iataField = new TextField(this,  null,
				CONSTANTS.iataLabel(),
				tab++);
		iataField.setEnabled(false);
		iataField.setFieldHalf();
		iataField.setHelpText(CONSTANTS.iataHelp());
		//form.add(iataField);

		//-----------------------------------------------
		// Latitude field
		//-----------------------------------------------
		latlngField = new DoublespanField(this, null,
				CONSTANTS.latlngLabel(),
				AbstractField.LF,
				tab++);
		latlngField.setHelpText(CONSTANTS.latlngHelp());
		form.add(latlngField);
		
		//-----------------------------------------------
		// Altitude field
		//-----------------------------------------------
		altitudeField = new DoubleunitField(this, null,
				NameId.getList(CONSTANTS.units(), UNITS),
				CONSTANTS.altitudeLabel(),
				AbstractField.IF,
				tab++);
		altitudeField.setHelpText(CONSTANTS.altitudeHelp());
		form.add(altitudeField);
		
		//-----------------------------------------------
		// Notes field
		//-----------------------------------------------
		notesField = new TextAreaField(this, null,
				CONSTANTS.notesLabel(),
				tab++);
		notesField.setMaxLength(100);
		form.add(notesField);

		form.add(createCommands());
		onRefresh();
		onReset(Location.CREATED);
		return form;
	}

	/*
	 * Creates the command bar.
	 * 
	 * @return the command bar.
	 */
	private HorizontalPanel createCommands() {
		final HorizontalPanel bar = new HorizontalPanel();
		bar.addStyleName(AbstractField.CSS.cbtAbstractCommand());

		final LocalRequest resetRequest = new LocalRequest() {protected void perform() {hide();}};

		//-----------------------------------------------
		// Save button
		//-----------------------------------------------
		final CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), locationUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.setTitle(AbstractField.CONSTANTS.helpSave());
		bar.add(saveButton);

		//-----------------------------------------------
		// Delete button
		//-----------------------------------------------
		final CommandButton deleteButton = new CommandButton(this, AbstractField.CONSTANTS.allDelete(), locationDelete, tab++);
		deleteButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		deleteButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		deleteButton.setTitle(AbstractField.CONSTANTS.helpDelete());
		bar.add(deleteButton);

		//-----------------------------------------------
		// Cancel button
		//-----------------------------------------------
		final CommandButton cancelButton = new CommandButton(this, AbstractField.CONSTANTS.allCancel(), resetRequest, tab++);
		cancelButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		cancelButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		cancelButton.setTitle(AbstractField.CONSTANTS.helpCancel());
		bar.add(cancelButton);

		//-----------------------------------------------
		// Transition array to define the finite state machine.
		//-----------------------------------------------
		fsm = new ArrayList<Transition>();
		fsm.add(new Transition(Location.INITIAL, cancelButton, Location.CREATED));
		fsm.add(new Transition(Location.INITIAL, saveButton, Location.CREATED));

		fsm.add(new Transition(Location.CREATED, saveButton, Location.CREATED));
		fsm.add(new Transition(Location.CREATED, deleteButton, Location.CREATED));
		return bar;
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Create Location
		//-----------------------------------------------
		locationCreate = new GuardedRequest<Location>() {
			protected boolean error() {return false;}
			protected void send() {super.send(getValue(new LocationCreate()));}
			protected void receive(Location location){setValue(location);}
		};

		//-----------------------------------------------
		// Read Location
		//-----------------------------------------------
		locationRead = new GuardedRequest<Location>() {
			protected boolean error() {
				return ifMessage(noId(), Level.TERSE, AbstractField.CONSTANTS.errId(), nameField);
			}
			protected void send() {super.send(getValue(new LocationRead()));}
			protected void receive(Location location){setValue(location);}
		};

		//-----------------------------------------------
		// Update Location
		//-----------------------------------------------
		locationUpdate = new GuardedRequest<Location>() {
			protected boolean error() {
				return (
						ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), nameField)
						|| ifMessage(noId(), Level.TERSE, AbstractField.CONSTANTS.errId(), nameField)
						|| ifMessage(nameField.noValue(), Level.TERSE, CONSTANTS.nameError(), nameField)
						|| ifMessage(countryField.noValue(), Level.TERSE, CONSTANTS.countryError(), countryField)
						|| ifMessage(regionField.noValue(), Level.TERSE, CONSTANTS.regionError(), regionField)
						|| ifMessage(latlngField.noValue(), Level.TERSE, CONSTANTS.latitudeError(), latlngField)
						|| ifMessage(latlngField.noTovalue(), Level.TERSE, CONSTANTS.longitudeError(), latlngField)
						|| ifMessage(altitudeField.noValue(), Level.TERSE, CONSTANTS.altitudeError(), altitudeField)
				);
			}
			protected void send() {super.send(getValue(new LocationUpdate()));}
			protected void receive(Location location){
				if (parentField != null) {parentField.setValue(location.getId());}
				hide();
			}
		};

		//-----------------------------------------------
		// Delete Location
		//-----------------------------------------------
		locationDelete = new GuardedRequest<Location>() {
			protected String popup() {return AbstractField.CONSTANTS.errDeleteOK();}
			public void cancel() {state = oldstate;}
			protected boolean error() {return ifMessage(noId(), Level.TERSE, AbstractField.CONSTANTS.errId(), nameField);}
			protected void send() {super.send(getValue(new LocationDelete()));}
			protected void receive(Location location){
				if (parentField != null) {parentField.setValue(null);}
				hide();
			}
		};

	}
}
