/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.panel;

import java.util.ArrayList;

import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.LocalRequest;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.CommandButton;
import net.cbtltd.client.field.GridField;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.rate.RateBundle;
import net.cbtltd.client.resource.rate.RateConstants;
import net.cbtltd.client.resource.rate.RateCssResource;
import net.cbtltd.shared.Event;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Rate;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.rate.RateCreate;
import net.cbtltd.shared.rate.RateRead;
import net.cbtltd.shared.rate.RateUpdate;
import net.cbtltd.shared.reservation.ReservationRead;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;

/** 
 * The Class RatePopup is to display and enter guest feedback instances.
 * This includes multiple choice lists for a guest's rating of the property, opinions, reasons for stay, and important qualities.
 * The lists are similar to those used by TripAdvisor, and are also obtained from the FlipKey web service.
 * @see net.cbtltd.rest.flipkey.parse.ParseService
 */
public class RatePopup
extends AbstractPopup<Event<Rate>> {

	private static final RateConstants CONSTANTS = GWT.create(RateConstants.class);
	private static final RateBundle BUNDLE = RateBundle.INSTANCE;
	private static final RateCssResource CSS = BUNDLE.css();

	private static GuardedRequest<Event<Rate>> rateCreate;
	private static GuardedRequest<Event<Rate>> rateRead;
	private static GuardedRequest<Event<Rate>> rateUpdate;
	private static GuardedRequest<Reservation> reservationRead;

	private static SuggestField rateField;
	private static SuggestField customerField;
	private static SuggestField productField;
	private static TextAreaField notesField;
	private static GridField<Rate> ratingTable;
	private static GridField<Rate> opinionTable;
	private static GridField<Rate> reasonTable;
	private static GridField<Rate> qualityTable;

	private static Reservation reservation = new Reservation();
	private static Label message;
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue()
	 */
	@Override
	public Event<Rate> getValue() {return getValue(new Event<Rate>());}

	/**
	 * Gets the ratings.
	 *
	 * @return the ratings.
	 */
	public static final ArrayList<NameId> getRatings() {return NameId.getList(CONSTANTS.ratingNames(), Rate.RATING_LIST);}
	
	/**
	 * Gets the opinions.
	 *
	 * @return the opinions.
	 */
	public static final ArrayList<NameId> getOpinions() {return NameId.getList(CONSTANTS.opinionNames(), Rate.OPINION_LIST);}
	
	/**
	 * Gets the reasons.
	 *
	 * @return the reasons.
	 */
	public static final ArrayList<NameId> getReasons() {return NameId.getList(CONSTANTS.reasonNames(), Rate.REASON_LIST);}
	
	/**
	 * Gets the qualities.
	 *
	 * @return the qualities.
	 */
	public static final ArrayList<NameId> getQualities() {return NameId.getList(CONSTANTS.qualityNames(), Rate.QUALITY_LIST);}

	/** Instantiates a new guest feedback pop up panel. */
	public RatePopup() {
		super(false);
		AbstractField.CSS.ensureInjected();
		CSS.ensureInjected();
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
		createActions();
		setWidget(createContent());
	}

	private static RatePopup instance;
	
	/**
	 * Gets the single instance of RatePopup.
	 *
	 * @return single instance of RatePopup
	 */
	public static synchronized RatePopup getInstance() {
		if (instance == null) {instance = new RatePopup();}
		return instance;
	}

	/**
	 * Shows the panel for the specified reservation.
	 *
	 * @param reservationid the ID of the specified reservation.
	 */
	public void show(String reservationid, Label message) {
		reservation.setId(reservationid);
		RatePopup.message = message;
		reservationRead.execute(true);
		rateCreate.execute(true);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change){
		if (rateField.sent(change))  {rateRead.execute();}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue(java.lang.Object)
	 */
	private Event<Rate> getValue(Event<Rate> event) {
		event.setState(state);
		event.setOrganizationid(reservation.getOrganizationid());
		event.setActorid(Party.NO_ACTOR);
		event.setParentid(reservation.getId());
		event.setProcess(Event.Type.Rate.name());
		event.setType(Event.NOMINAL);
		event.setNotes(notesField.getValue());
		ArrayList<Rate> items = new ArrayList<Rate>();
		items.addAll(ratingTable.getValue());
		items.addAll(opinionTable.getValue());
		items.addAll(reasonTable.getValue());
		items.addAll(qualityTable.getValue());
		event.setItems(items);
		Log.debug("getValue " + event);
		return event;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(Event<Rate> event) {
		Log.debug("setValue " + event);
		if (event == null) {onReset(Rate.CREATED);}
		else {
			setResetting(true);
			onStateChange(event.getState());
			rateField.setValue(event.getId()); 
			notesField.setValue(event.getNotes());
			if (event.hasItems()){
				customerField.setValue(event.getItem(0).getCustomerid());
				customerField.setEnabled(event.noParentid());
				productField.setValue(event.getItem(0).getProductid());
				productField.setEnabled(event.noParentid());
			}
			ratingTable.setValue(event.getItems());
			opinionTable.setValue(event.getItems());
			reasonTable.setValue(event.getItems());
			qualityTable.setValue(event.getItems());
			setResetting(false);
		}	
		center();
	}

	/*
	 * Gets the specified reservation action with its attributes set.
	 *
	 * @param action the specified action.
	 * @return the action with its attributes set.
	 */
	private Reservation getValue(Reservation action) {
		action.setId(reservation.getId());
		return action;
	}

	/*
	 * Sets the fields with the attributes of the specified reservation.
	 *
	 * @param reservation the specified reservation.
	 */
	private void setValue(Reservation reservation) {
		RatePopup.reservation = reservation;
		customerField.setValue(reservation.getCustomerid());
		customerField.setEnabled(reservation.noCustomerid());
		productField.setValue(reservation.getProductid());
		productField.setEnabled(reservation.noProductid());
	}

	/*
	 * Creates the panel of feedback fields.
	 * 
	 * @return the panel of feedback fields.
	 */
	public VerticalPanel createContent() {
		final VerticalPanel panel = new VerticalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		add(panel);
		final Label rateLabel = new Label(CONSTANTS.rateLabel());
		rateLabel.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		panel.add(rateLabel);
		
		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		panel.add(closeButton);

		final Label rateHeader = new Label(CONSTANTS.rateHeader());
		rateHeader.addStyleName(AbstractField.CSS.cbtAbstractField());
		rateHeader.addStyleName(CSS.rateHeader());
		
		panel.add(rateHeader);
		final HorizontalPanel content = new HorizontalPanel();
		content.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.add(content);

		final VerticalPanel form = new VerticalPanel();
		content.add(form);
		
		//-----------------------------------------------
		// Name field, sets all properties once selected
		//-----------------------------------------------
		rateField = new SuggestField(this, null,
				new NameIdAction(Service.RATE),
				CONSTANTS.ratenameLabel(),
				20,
				tab++);
		rateField.setFieldHalf();
		//form.add(rateField);

		//-----------------------------------------------
		// Customer field
		//-----------------------------------------------
		customerField = new SuggestField(this,  null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.customerLabel(),
				20,
				tab++);
		customerField.setType(Party.Type.Customer.name());
		customerField.setAllOrganizations(true);
		customerField.setHelpText(CONSTANTS.customerHelp());
		form.add(customerField);

		//-----------------------------------------------
		// Product field
		//-----------------------------------------------
		productField = new SuggestField(this,  null,
				new NameIdAction(Service.PRODUCT),
				CONSTANTS.productLabel(),
				20,
				tab++);
		productField.setType(Product.Type.Accommodation.name());
		productField.setAllOrganizations(true);
		productField.setHelpText(CONSTANTS.productHelp());
		form.add(productField);

		//-----------------------------------------------
		// Comment field
		//-----------------------------------------------
		notesField = new TextAreaField(this,  null,
				CONSTANTS.notesLabel(),
				tab++);
		notesField.setFieldStyle(CSS.notesField());
		notesField.setMaxLength(3000);
		form.add(notesField);

		//-----------------------------------------
		// Ratings table
		//-----------------------------------------------
		ratingTable = new GridField<Rate>(this, null,
				CONSTANTS.ratingHeaders(),
				getRatings().size(),
				tab++) {
			
			public void setRowValue(int row, Rate rate) {
				if (rate != null && rate.hasType(Rate.RATING)) {
					field.setHTML(row, 0, rate.getName());
					RadioButton rb = new RadioButton(Rate.RATING + String.valueOf(row));
					rb.setValue(true);
					rb.setVisible(false);
					field.setWidget(row, 1, rb);
					for (int col = 1; col < RatePopup.CONSTANTS.ratingHeaders().length; col++) {
						rb = new RadioButton(Rate.RATING + String.valueOf(row));
						rb.setValue(rate.hasQuantity(col));
						field.setWidget(row, col, rb);
					}
				}
			}
			
			public Rate getRowValue(int row) {
				for (int col = 1; col < RatePopup.CONSTANTS.ratingHeaders().length; col++) {
					RadioButton rb = (RadioButton)field.getWidget(row, col);
					if (rb != null && rb.getValue()) {return new Rate(field.getHTML(row, 0), Rate.RATING, col);}
				}
				return new Rate(field.getHTML(row, 0), Rate.RATING, 0);
			}
		};

		ArrayList<Rate> ratings = new ArrayList<Rate>();
		for (NameId item : getRatings()){ratings.add(new Rate(item.getId(), Rate.RATING, 0));}
		ratingTable.setDefaultValue(ratings);
		form.add(ratingTable);

		content.add(createTables());
		onRefresh();
		onReset(Rate.CREATED);
		return panel;
	}
	
	/*
	 * Creates the panel of feedback selection tables.
	 * 
	 * @return the panel of feedback selection tables.
	 */
	private HorizontalPanel createTables() {
		HorizontalPanel tablePanel = new HorizontalPanel();
		
		//-----------------------------------------------
		// Reason table
		//-----------------------------------------------
		reasonTable = new GridField<Rate>(this, null,
				CONSTANTS.reasonHeaders(),
				getReasons().size(),
				tab++) {
			
			public void setRowValue(int row, Rate rate) {
				if (rate != null && rate.hasType(Rate.REASON)){
					field.setWidget(row++, 0, new CheckBox(rate.getName()));
				}
			}
			
			public Rate getRowValue(int row) {
				CheckBox cb = (CheckBox)field.getWidget(row, 0);
				return cb == null ? null : new Rate(cb.getName(), Rate.REASON, cb.getValue());
			}
		};
		ArrayList<Rate> reasons = new ArrayList<Rate>();
		for (NameId item : getReasons()){reasons.add(new Rate(item.getId(), Rate.REASON, 0));}
		reasonTable.setDefaultValue(reasons);
		tablePanel.add(reasonTable);

		//-----------------------------------------------
		// Quality table
		//-----------------------------------------------
		qualityTable = new GridField<Rate>(this, null,
				CONSTANTS.qualityHeaders(),
				getQualities().size(),
				tab++) {
			
			public void setRowValue(int row, Rate rate) {
				if (rate != null && rate.hasType(Rate.QUALITY)){
					field.setWidget(row++, 0, new CheckBox(rate.getName()));
				}
			}
			
			public Rate getRowValue(int row) {
				CheckBox cb = (CheckBox)field.getWidget(row, 0);
				return cb == null ? null : new Rate(cb.getName(), Rate.QUALITY, cb.getValue());
			}
		};
		ArrayList<Rate> qualities = new ArrayList<Rate>();
		for (NameId item : getQualities()) {qualities.add(new Rate(item.getId(), Rate.QUALITY, 0));}
		qualityTable.setDefaultValue(qualities);
		tablePanel.add(qualityTable);

		//-----------------------------------------------
		// Opinion table
		//-----------------------------------------------
		opinionTable = new GridField<Rate>(this, null,
				CONSTANTS.opinionHeaders(),
				getOpinions().size(),
				tab++) {
			
			public void setRowValue(int row, Rate rate) {
				if (rate != null && rate.hasType(Rate.OPINION)){
					field.setHTML(row, 0, rate.getName());
					RadioButton rb = new RadioButton(Rate.OPINION + String.valueOf(row));
					rb.setValue(true);
					rb.setVisible(false);
					field.setWidget(row, 1, rb);
					for (int col=1; col < RatePopup.CONSTANTS.opinionHeaders().length; col++) {
						rb = new RadioButton(Rate.OPINION + String.valueOf(row));
						rb.setValue(rate.hasQuantity(col));
						field.setWidget(row, col, rb);
					}
				}
			}
			
			public Rate getRowValue(int row) {
				for (int col = 1; col < RatePopup.CONSTANTS.opinionHeaders().length; col++) {
					RadioButton rb = (RadioButton)field.getWidget(row, col);
					if (rb != null && rb.getValue()) {return new Rate(field.getHTML(row, 0), Rate.OPINION, col);}
				}
				return new Rate(field.getHTML(row, 0), Rate.OPINION, 0);

			}
		};
		ArrayList<Rate> opinions = new ArrayList<Rate>();
		for (NameId item : getOpinions()){opinions.add(new Rate(item.getId(), Rate.OPINION, 0));}
		opinionTable.setDefaultValue(opinions);
		
		VerticalPanel opinionPanel = new VerticalPanel();
		opinionPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		opinionPanel.add(opinionTable);
		opinionPanel.add(createCommands());
		tablePanel.add(opinionPanel);

		return tablePanel;
	}

	/*
	 * Creates the command bar.
	 * 
	 * @return the command bar.
	 */
	private HorizontalPanel createCommands() {
		final HorizontalPanel bar = new HorizontalPanel();
		bar.addStyleName(AbstractField.CSS.cbtAbstractCommand());
		bar.addStyleName(CSS.commandBar());

		LocalRequest cancelRequest = new LocalRequest() {
			protected void perform() {
				message.setText(CONSTANTS.cancelMessage());
				hide();
			}
		};
		
		//-----------------------------------------------
		// Save button
		//-----------------------------------------------
		final CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), rateUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.setTitle(CONSTANTS.saveHelp());
		bar.add(saveButton);

		//-----------------------------------------------
		// Cancel button
		//-----------------------------------------------
		final CommandButton cancelButton = new CommandButton(this, AbstractField.CONSTANTS.allCancel(), cancelRequest, tab++);
		cancelButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		cancelButton.setTitle(CONSTANTS.cancelHelp());
		bar.add(cancelButton);

		//-----------------------------------------------
		// Transition array to define the finite state machine
		//-----------------------------------------------
		fsm = new ArrayList<Transition>();
		fsm.add(new Transition(Rate.CREATED, saveButton, Rate.CREATED));
		fsm.add(new Transition(Rate.CREATED, cancelButton, Rate.CREATED));
		return bar;
	}
	
	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Create Feedback (Rate)
		//-----------------------------------------------
		rateCreate = new GuardedRequest<Event<Rate>>() {
			protected boolean error() {return reservation.noId();}
			protected void send() {super.send(getValue(new RateCreate()));}
			protected void receive(Event<Rate> event){setValue(event);}
		};

		//-----------------------------------------------
		// Read Feedback (Rate)
		//-----------------------------------------------
		rateRead = new GuardedRequest<Event<Rate>>() {
			protected boolean error() {return rateField.noValue();}
			protected void send() {super.send(getValue(new RateRead()));}
			protected void receive(Event<Rate> event){setValue(event);}	
		};

		//-----------------------------------------------
		// Update Feedback (Rate)
		//-----------------------------------------------
		rateUpdate = new GuardedRequest<Event<Rate>>() {
			protected boolean error() {
				return (
						ifMessage(customerField.noValue(), Level.ERROR, CONSTANTS.customerError(), customerField)
						|| ifMessage(productField.noValue(), Level.ERROR, CONSTANTS.productError(), productField)
						|| ifMessage(ratingTable.getRowValue(1).getQuantity() < 1, Level.TERSE, CONSTANTS.ratingError(), ratingTable)
				);
			}
			protected void send() {super.send(getValue(new RateUpdate()));}
			protected void receive(Event<Rate> event){
				message.setText(CONSTANTS.saveMessage());
				hide();
			}
		};

		//-----------------------------------------------
		// Read Reservation
		//-----------------------------------------------
		reservationRead = new GuardedRequest<Reservation>() {
			protected boolean error() {return reservation.noId();}
			protected void send() {super.send(getValue(new ReservationRead()));}
			protected void receive(Reservation reservation) {setValue(reservation);}
		};
	}
}

