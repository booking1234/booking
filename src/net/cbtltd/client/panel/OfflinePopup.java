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
import net.cbtltd.client.field.DatespanField;
import net.cbtltd.client.field.DoubleField;
import net.cbtltd.client.field.DoubleunitField;
import net.cbtltd.client.field.IntegerField;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.reservation.ReservationConstants;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.finance.CurrencyrateNameId;
import net.cbtltd.shared.reservation.OfflineAccept;
import net.cbtltd.shared.reservation.OfflineRead;
import net.cbtltd.shared.reservation.OfflineReject;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/** The Class OfflinePopup is to display and accept or reject off line reservation requests. */
public class OfflinePopup 
extends AbstractPopup<Reservation> {

	private static final ReservationConstants CONSTANTS = GWT.create(ReservationConstants.class);
	//private static final ReservationBundle BUNDLE = ReservationBundle.INSTANCE;
	//private static final ReservationCssResource CSS = BUNDLE.css();

	private static GuardedRequest<Reservation> offlineRead;
	private static GuardedRequest<Reservation> offlineAccept;
	private static GuardedRequest<Reservation> offlineReject;

	private static TextField reservationField;
	private static TextField productField;
	private static DatespanField fromtodateField;
//	private static TimespanField fromtotimeField;
	private static DoubleunitField quoteField;
	private static DoubleunitField costField;
	private static DoubleField depositField;
	private static TextAreaField messageField;

	private static String id;
	private boolean noId() {return id == null || id.isEmpty();}

	/** Instantiates a new reservation pop up panel. */
	public OfflinePopup() {
		super(false);
		createActions();
		setWidget(createContent());
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
	}

	private static OfflinePopup instance;
	
	/**
	 * Gets the single instance of ContactPopup.
	 *
	 * @return single instance of ContactPopup
	 */
	public static synchronized OfflinePopup getInstance() {
		if (instance == null) {instance = new OfflinePopup();}
		OfflinePopup.id = null;
		return instance;
	}

	/**
	 * Shows the panel for the specified off line reservation.
	 *
	 * @param id the ID of the specified off line reservation.
	 */
	public void show(String id) {
		OfflinePopup.id = id;
		onReset(Reservation.State.Initial.name());
		offlineRead.execute(true);
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue()
	 */
	@Override
	public Reservation getValue() {return getValue(new Reservation());}
	private Reservation getValue(Reservation reservation) {
		reservation.setState(state);
		reservation.setId(id);
		reservation.setQuote(quoteField.getValue());
		reservation.setCurrency(quoteField.getUnitvalue());
		reservation.setCost(costField.getValue());
		reservation.setDeposit(depositField.getValue());
		Log.debug("getValue " + reservation);
		return reservation;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(Reservation reservation) {
		Log.debug("setValue " + reservation);
		if (reservation == null){onReset(Reservation.State.Initial.name());}
		else {
			setResetting(true);
			onStateChange(reservation.getState());
			id = reservation.getId();
			reservationField.setValue(reservation.getName());
			productField.setValue(reservation.getProductname());
			fromtodateField.setValue(Time.getDateClient(reservation.getFromdate()));
			fromtodateField.setTovalue(Time.getDateClient(reservation.getTodate()));
//			fromtotimeField.setValue(reservation.getArrivaltime());
//			fromtotimeField.setTovalue(reservation.getDeparturetime());
			quoteField.setValue(reservation.getQuote());
			quoteField.setUnitvalue(reservation.getCurrency());
			costField.setVisible(reservation.hasAgentid());
			costField.setValue(reservation.getCost());
			costField.setUnitvalue(reservation.getCurrency());
			depositField.setValue(reservation.getDeposit());
			center();
			setResetting(false);
		}
	}

	/*
	 * Creates the panel of off line reservation fields.
	 * 
	 * @return the panel of off line reservation fields.
	 */
	private VerticalPanel createContent() {
		final VerticalPanel form = new VerticalPanel();
		final Label titleLabel = new Label(CONSTANTS.offlineTitle());
		titleLabel.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(titleLabel);
		
		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				OfflinePopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);

		//-----------------------------------------------
		// Reservation Name field
		//-----------------------------------------------
		reservationField = new TextField(this, null, CONSTANTS.nameLabel(), tab++);
		reservationField.setEnabled(false);
		reservationField.setHelpText(CONSTANTS.reservationHelp());
		form.add(reservationField);
		
		//-----------------------------------------------
		// Product Name field
		//-----------------------------------------------
		productField = new TextField(this, null, CONSTANTS.productLabel(), tab++);
		productField.setEnabled(false);
		productField.setHelpText(CONSTANTS.productHelp());
		form.add(productField);
		
		//-----------------------------------------------
		// Arrival and Departure Dates
		//-----------------------------------------------
		fromtodateField = new DatespanField(this, null,
				CONSTANTS.fromtodateLabel(),
				tab++);
		fromtodateField.setEnabled(false);
		fromtodateField.setHelpText(CONSTANTS.fromtodateHelp());
		form.add(fromtodateField);

		//-----------------------------------------------
		// Quoted Amount field
		//-----------------------------------------------
		quoteField = new DoubleunitField(this, null,
//				NameId.getList(Currency.EXCHANGE_CURRENCY_NAMES, Currency.EXCHANGE_CURRENCIES),
				new CurrencyrateNameId(),
				CONSTANTS.quoteLabel(),
				AbstractField.AF,
				tab++);
		quoteField.setHelpText(CONSTANTS.quoteHelp());
		quoteField.setUnitenabled(false);
		form.add(quoteField);

		//-----------------------------------------------
		// STO Rate field
		//-----------------------------------------------
		costField = new DoubleunitField(this, null,
//				NameId.getList(Currency.EXCHANGE_CURRENCY_NAMES, Currency.EXCHANGE_CURRENCIES),
				new CurrencyrateNameId(),
				CONSTANTS.discountLabel(),
				AbstractField.AF,
				tab++);
		costField.setUnitenabled(false);
		costField.setHelpText(CONSTANTS.discountHelp());
		form.add(costField);

		//-----------------------------------------------
		// Deposit Percentage field
		//-----------------------------------------------
		depositField = new DoubleField(this, null,
				CONSTANTS.depositLabel(),
				AbstractField.AF,
				tab++);
		depositField.setDefaultValue(50.);
		depositField.setRange(0., 100.);
		depositField.setHelpText(CONSTANTS.depositHelp());
		form.add(depositField);

		//-----------------------------------------------
		// Message field
		//-----------------------------------------------
		messageField = new TextAreaField(this,  null,
				CONSTANTS.messageLabel(),
				tab++);
		messageField.setEnabled(false);
		messageField.setMaxLength(Text.MAX_TEXT_SIZE);
		messageField.setHelpText(CONSTANTS.messageHelp());
		form.add(messageField);

		form.add(createCommands());
		onRefresh();
		onReset(Reservation.State.Provisional.name());
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
		
		final LocalRequest cancelRequest = new LocalRequest() {protected void perform() {hide();}};

		//-----------------------------------------------
		// Accept button
		//-----------------------------------------------
		final CommandButton acceptButton = new CommandButton(this, AbstractField.CONSTANTS.allAccept(), offlineAccept, tab++);
		acceptButton.addStyleName(AbstractField.CSS.cbtGradientGreen());
		acceptButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		bar.add(acceptButton);
		
		//-----------------------------------------------
		// Reject button
		//-----------------------------------------------
		final CommandButton rejectButton = new CommandButton(this, AbstractField.CONSTANTS.allReject(), offlineReject, tab++);
		rejectButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		rejectButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		bar.add(rejectButton);
		
		//-----------------------------------------------
		// Transition array to define the finite state machine
		//-----------------------------------------------
		fsm = new ArrayList<Transition>();
		fsm.add(new Transition(Reservation.State.Provisional.name(), acceptButton, Reservation.State.Confirmed.name()));
		fsm.add(new Transition(Reservation.State.Provisional.name(), rejectButton, Reservation.State.Initial.name()));
		return bar;
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Read Off Line Reservation
		//-----------------------------------------------
		offlineRead = new GuardedRequest<Reservation>() {
			protected boolean error() {return noId();}
			protected void send() {super.send(getValue(new OfflineRead()));}
			protected void receive(Reservation reservation) {setValue(reservation);}
		};

		//-----------------------------------------------
		// Accept Off Line Reservation
		//-----------------------------------------------
		offlineAccept = new GuardedRequest<Reservation>() {
			protected String popup() {return AbstractField.CONSTANTS.errAcceptOK();}
			protected boolean error() {
				return (
						ifMessage(noId(), Level.TERSE, AbstractField.CONSTANTS.errId(), messageField)
						|| ifMessage(productField.noValue(), Level.TERSE, CONSTANTS.productError(), productField)
						|| ifMessage(fromtodateField.noValue(), Level.TERSE, CONSTANTS.fromdateError(), fromtodateField)
						|| ifMessage(fromtodateField.noTovalue(), Level.TERSE, CONSTANTS.todateError(), fromtodateField)
						|| ifMessage(fromtodateField.isEndBeforeStart(), Level.TERSE, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
						|| ifMessage(fromtodateField.getDuration(Time.DAY) < 1, Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
				);
			}
			protected void send() {super.send(getValue(new OfflineAccept()));}
			protected void receive(Reservation reservation) {hide();}
		};

		//-----------------------------------------------
		// Reject Off Line Reservation
		//-----------------------------------------------
		offlineReject = new GuardedRequest<Reservation>() {
			protected String popup() {return AbstractField.CONSTANTS.errRejectOK();}
			protected boolean error() {return ifMessage(noId(), Level.TERSE, AbstractField.CONSTANTS.errId(), messageField);}
			protected void send() {super.send(getValue(new OfflineReject()));}
			protected void receive(Reservation reservation) {hide();}
		};
	}
}
