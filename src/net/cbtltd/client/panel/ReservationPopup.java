/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.panel;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.LocalRequest;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.CommandButton;
import net.cbtltd.client.field.DatespanField;
import net.cbtltd.client.field.DoubleunitField;
import net.cbtltd.client.field.HelpLabel;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.MoneyField;
import net.cbtltd.client.field.ReportButton;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.form.AccessControl;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.Hosts;
import net.cbtltd.client.resource.reservation.ReservationConstants;
import net.cbtltd.client.widget.book.BookWidgetItem;
import net.cbtltd.shared.Alert;
import net.cbtltd.shared.Finance;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.finance.CurrencyrateNameId;
import net.cbtltd.shared.party.PartyExists;
import net.cbtltd.shared.reservation.AvailableItem;
import net.cbtltd.shared.reservation.PriceResponse;
import net.cbtltd.shared.reservation.ReservationPrice;
import net.cbtltd.shared.reservation.ReservationRead;
import net.cbtltd.shared.reservation.ReservationUpdate;
import net.cbtltd.shared.reservation.ReservationWidget;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/** The Class ReservationPopup is to create, display and change reservation instances. */
public class ReservationPopup
extends AbstractPopup<Reservation> {

	private static final ReservationConstants CONSTANTS = GWT.create(ReservationConstants.class);
	private static final Hosts HOSTS = GWT.create(Hosts.class);
//	private static final ReservationBundle BUNDLE = ReservationBundle.INSTANCE;
//	private static final ReservationCssResource CSS = BUNDLE.css();
	private static final DateTimeFormat DF = DateTimeFormat.getFormat("yyyy-MM-dd"); //MySQL date format
	private static final boolean secure = Window.Location.getHref().startsWith(HOSTS.jsonUrl().substring(0, 5));
	private static final String pos = Window.Location.getParameter(AbstractRoot.Type.pos.name());
	private static final boolean widget = (pos != null);

	private static GuardedRequest<Party> partyExists;
	private static GuardedRequest<Reservation> reservationBook;
	private static GuardedRequest<Reservation> reservationCreate;
	private static GuardedRequest<Reservation> reservationRead;
	private static GuardedRequest<Reservation> reservationSubmit;
	private static GuardedRequest<Reservation> reservationUpdate;
	private static GuardedRequest<PriceResponse> reservationPrice;

	private static SuggestField reservationField;
	private static DatespanField fromtodateField;
//	private static SuggestField agentField;
//	private static SuggestField supplierField;
	private static SuggestField productField;
	private static MoneyField quoteField;
	private static MoneyField priceField;
	private static MoneyField costField;
	private static SuggestField customerField; //with popup
	private static TextField emailaddressField;
	private static TextField firstnameField;
	private static TextField familynameField;
	private static HorizontalPanel logoImages;
	private static TextField cardholderField;
	private static TextField cardnumberField;
	private static HelpLabel carddetailLabel;
	private static ListField cardmonthField;
	private static ListField cardyearField;
	private static TextField cardcodeField;
	private static DoubleunitField amountField;
	private static TextAreaField messageField;
	private static TextAreaField notesField;

	private static ReportButton confirmationReport;
	private static Image loader;

	private static TableField<AvailableItem> parentTable;
//	private static ArrayList<AvailableItem> availableitems;
//	private static int availableIndex = 0;
	private ArrayList<NameId> collisions;
	private boolean noCollisions() {return collisions == null || collisions.isEmpty();}
	private boolean hasCollisions() {return !noCollisions();}
	private String getCollisionlist() {return getCollisionlist(collisions);}
	private static String getCollisionlist(ArrayList<NameId> collisions) {return NameId.getCdlist(NameId.getNameList(collisions));}
	private ArrayList<Price> quoteDetail = new ArrayList<Price>();

	private static String getAlertlist(ArrayList<Alert> alerts) {
		if (alerts == null || alerts.isEmpty()) {return null;}
		StringBuilder sb =  new StringBuilder();
		for (Alert alert : alerts) {sb.append(AbstractRoot.getDF().format(alert.getFromdate())).append(" - ").append(AbstractRoot.getDF().format(alert.getTodate())).append(" ").append(alert.getName()).append("\n");}
		return sb.toString();
	}

	/** Instantiates a new reservation pop up panel. */
	public ReservationPopup() {
		super(true);
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
		if (AbstractRoot.noOrganizationid()) {
			if (widget) {
				try {AbstractRoot.setOrganizationid(Model.decrypt(pos));}
				catch (Throwable x) {AbstractRoot.setOrganizationid(Party.CBT_LTD_PARTY);}
			}
			else {AbstractRoot.setOrganizationid(Party.CBT_LTD_PARTY);}
			AbstractRoot.setActorid(Party.NO_ACTOR);
		}
		createActions();
		setWidget(createContent());
	}

	private static ReservationPopup instance;
	
	/**
	 * Gets the single instance of ReservationPopup.
	 *
	 * @return single instance of ReservationPopup
	 */
	public static synchronized ReservationPopup getInstance() {
		if (instance == null) {instance = new ReservationPopup();}

		costField.setVisible(!widget);
		customerField.setVisible(!widget);

		emailaddressField.setVisible(widget);
		firstnameField.setVisible(widget);
		familynameField.setVisible(widget);
		logoImages.setVisible(widget && secure);
		cardholderField.setVisible(widget && secure);
		cardnumberField.setVisible(widget && secure);
		carddetailLabel.setVisible(widget && secure);
		cardmonthField.setVisible(widget && secure);
		cardyearField.setVisible(widget && secure);
		cardcodeField.setVisible(widget && secure);
		amountField.setVisible(widget);
		messageField.setVisible(false);
		
		return instance;
	}
	
	/**
	 * Shows the panel for a reservation of the specified product by the specified agency.
	 *
	 * @param productid the ID of the specified product.
	 * @param agentid the ID of the specified agency
	 */
	public void show(String productid) {
		AbstractRoot.setCurrency(Window.Location.getParameter("currency"));
		onStateChange(Reservation.State.Initial.name());
		productField.setValue(productid);
		reservationBook.execute(true);
	}
	
	/**
	 * Shows the panel for the specified reservation.
	 *
	 * @param reservation the specified reservation.
	 * @param parentTable the table that invoked the pop up panel.
	 */
	public void show(Reservation reservation, TableField<AvailableItem> parentTable) {
		ReservationPopup.parentTable = parentTable;
		setValue(reservation);
		if (reservation.noId()) {reservationCreate.execute(true);}
		else {reservationRead.execute();}
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {
		if (emailaddressField.sent(change)) {partyExists.execute();}
		else if (fromtodateField.sent(change)) {reservationPrice.execute();}
		else if (quoteField.sent(change)) {ifMessage(quoteField.getValue() < costField.getValue(), Level.TERSE, CONSTANTS.quotecostError(), quoteField);}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue()
	 */
	@Override
	public Reservation getValue() {return getValue(new Reservation());}

	private Reservation getValue(Reservation reservation) {
		reservation.setState(state);
		reservation.setId(reservationField.getValue());
		reservation.setName(reservationField.getName());
		reservation.setActorid(Party.NO_ACTOR);
		reservation.setAgentid(AbstractRoot.getOrganizationid()); //TODO:
		reservation.setArrivaltime(Reservation.ARRIVALTIME);
		reservation.setDeparturetime(Reservation.DEPARTURETIME);
		reservation.setUnit(Unit.DAY);
		reservation.setCustomerid(customerField.getValue());
		reservation.setAgentid(AbstractRoot.getOrganizationid());
		reservation.setDate(new Date());
		reservation.setDuedate(Time.addDuration(reservation.getDate(), 2.0, Time.DAY));
		reservation.setFromdate(Time.getDateServer(fromtodateField.getValue()));
		reservation.setTodate(Time.getDateServer(fromtodateField.getTovalue()));
		reservation.setProcess(NameId.Type.Reservation.name());
		reservation.setProductid(productField.getValue());
		reservation.setNotes(notesField.getValue());
		reservation.setUnit(Unit.DAY);
		reservation.setQuote(quoteField.getValue());
		reservation.setCurrency(quoteField.getUnitvalue());
		reservation.setPrice(priceField.getValue());
		reservation.setCost(costField.getValue());
		reservation.setCurrency(quoteField.getUnitvalue());
		reservation.setQuotedetail(quoteDetail);
		Log.debug("getValue " + reservation);
		return reservation;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(Reservation reservation) {
		Log.debug("setValue " + reservation);
		collisions = null;
		if (reservation == null){onReset(Reservation.State.Initial.name());}
		else if (reservation.hasCollisions()) {
			collisions = reservation.getCollisions();
			AbstractField.addMessage(Level.TERSE, CONSTANTS.collisionError() + getCollisionlist(), fromtodateField);
		}
		else {
			setResetting(true);
			onStateChange(reservation.getState());
			reservationField.setValue(reservation.getId());
			fromtodateField.setValue(Time.getDateClient(reservation.getFromdate()));
			fromtodateField.setTovalue(Time.getDateClient(reservation.getTodate()));
			customerField.setValue(reservation.getCustomerid());
			productField.setValue(reservation.getProductid());
			notesField.setValue(reservation.getNotes());
			quoteField.setValue(reservation.getQuote());
			quoteField.setUnitvalue(reservation.getCurrency());
			priceField.setValue(reservation.getPrice());
			priceField.setUnitvalue(reservation.getCurrency());
			costField.setValue(reservation.getCost());
			costField.setUnitvalue(reservation.getCurrency());
			amountField.setValue(reservation.getQuote());
			amountField.setUnitvalue(reservation.getCurrency());
			reservationPrice.execute();
			center();
			setResetting(false);
		}
	}
	
	/*
	 * Gets the specified party action with its attribute set.
	 *
	 * @param action the specified action.
	 * @return the action with its attributes set.
	 */
	private Party getValue(Party party) {
		party.setEmailaddress(emailaddressField.getValue());
		return party;
	}
	
	/*
	 * Creates the panel of reservation fields.
	 * 
	 * @return the panel of reservation fields.
	 */
	private VerticalPanel createContent() {
		VerticalPanel form = new VerticalPanel();
		final Label title = new Label(CONSTANTS.titleLabel());
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);

		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ReservationPopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);

		//-----------------------------------------------
		// Reservation number
		//-----------------------------------------------
		reservationField = new SuggestField(this, null,
				new NameIdAction(Service.RESERVATION),
				CONSTANTS.reservationnameLabel(),
				20,
				tab++);
		reservationField.setFieldHalf();
		reservationField.setEnabled(false);
		reservationField.setHelpText(CONSTANTS.reservationHelp());
		form.add(reservationField);

		//-----------------------------------------------
		// Supplier field
		//-----------------------------------------------
//		supplierField = new SuggestField(this,  null,
//				new NameIdAction(Service.PARTY),
//				CONSTANTS.supplierLabel(),
//				20,
//				tab++);
//		supplierField.setType(Party.Type.Organization.name());
//		supplierField.setEnabled(false);
//		supplierField.setHelpText(CONSTANTS.supplierHelp());
//		form.add(supplierField);
		
		//-----------------------------------------------
		// Product field
		//-----------------------------------------------
		productField = new SuggestField(this, null,
				new NameIdAction(Service.PRODUCT),
				CONSTANTS.productLabel(),
				20,
				tab++);
		productField.setType(Product.Type.Accommodation.name());
		productField.setAllOrganizations(true);
		productField.setEnabled(false);
		productField.setHelpText(CONSTANTS.productHelp());
		form.add(productField);

		//-----------------------------------------------
		// Agent field
		//-----------------------------------------------
//		agentField = new SuggestField(this,  null,
//				new NameIdAction(Service.PARTY),
//				CONSTANTS.agentLabel(),
//				20,
//				tab++);
//		agentField.setEnabled(false);
//		agentField.setHelpText(CONSTANTS.agentHelp());
//		form.add(agentField);
//		
		//-----------------------------------------------
		// Customer (Guest) field
		//-----------------------------------------------
		customerField = new SuggestField(this,  null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.customerLabel(),
				20,
				tab++);
		customerField.setType(Party.Type.Customer.name());
		customerField.setHelpText(CONSTANTS.customerHelp());
		
		Image customerButton = new Image(AbstractField.BUNDLE.plus());
		customerButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PartyPopup.getInstance().show(Party.Type.Customer, customerField, null);
			}
		});
		customerField.addButton(customerButton);
		customerField.setFieldStyle(AbstractField.CSS.cbtSuggestFieldSecure());
		form.add(customerField);

		//-----------------------------------------------
		// Guest Email Address field
		//-----------------------------------------------
		emailaddressField = new TextField(this,  null,
				CONSTANTS.emailaddressLabel(),
				tab++);
		emailaddressField.setMaxLength(100);
		emailaddressField.setHelpText(CONSTANTS.emailaddressHelp());
		form.add(emailaddressField);

		//-----------------------------------------------
		// Guest First Name field
		//-----------------------------------------------
		firstnameField = new TextField(this,  null,
				CONSTANTS.firstnameLabel(),
				tab++);
		firstnameField.setMaxLength(100);
		firstnameField.setHelpText(CONSTANTS.firstnameHelp());
		form.add(firstnameField);

		//-----------------------------------------------
		// Guest Family Name field
		//-----------------------------------------------
		familynameField = new TextField(this,  null,
				CONSTANTS.familynameLabel(),
				tab++);
		familynameField.setMaxLength(100);
		familynameField.setHelpText(CONSTANTS.familynameHelp());
		form.add(familynameField);

		//-----------------------------------------------
		// Arrival and Departure Dates
		//-----------------------------------------------
		fromtodateField = new DatespanField(this, null,
				CONSTANTS.fromtodateLabel(),
				tab++);
//		fromtodateField.setDefaultDuration(1.0);
		fromtodateField.setHelpText(CONSTANTS.fromtodateHelp());
		form.add(fromtodateField);

		//-----------------------------------------------
		// Quoted Price field
		//-----------------------------------------------
		quoteField = new MoneyField(this, null,
				CONSTANTS.quoteLabel(),
				tab++);
		quoteField.setEnabled(AbstractRoot.writeable(AccessControl.QUOTE_PERMISSION));
		quoteField.setUnitenabled(false);
		quoteField.setHelpText(CONSTANTS.quoteHelp());
		form.add(quoteField);

		//-----------------------------------------------
		// Rack Rate field
		//-----------------------------------------------
		priceField = new MoneyField(this, null,
				CONSTANTS.priceLabel(),
				tab++);
		priceField.setEnabled(false);
		priceField.setUnitenabled(false);
		priceField.setHelpText(CONSTANTS.priceHelp());
		form.add(priceField);

		//-----------------------------------------------
		// STO field
		//-----------------------------------------------
		costField = new MoneyField(this, null,
				CONSTANTS.discountLabel(),
				tab++);
		costField.setEnabled(false);
		costField.setUnitenabled(false);
		costField.setHelpText(CONSTANTS.discountHelp());
		//form.add(costField);

		//-----------------------------------------------
		// Notes field
		//-----------------------------------------------
		notesField = new TextAreaField(this, null,
				CONSTANTS.notesLabel(),
				tab++);
		notesField.setMaxLength(3000);
		notesField.setHelpText(CONSTANTS.notesHelp());
		form.add(notesField);

		//-----------------------------------------------
		// Logo field
		//-----------------------------------------------
		final Image paygate= new Image(AbstractField.BUNDLE.paygate());
//		final Image godaddy= new Image(AbstractField.BUNDLE.godaddy());
		final Image thawte= new Image(AbstractField.BUNDLE.thawte());
		final Image mastercard= new Image(AbstractField.BUNDLE.mastercard());
		final Image visa= new Image(AbstractField.BUNDLE.visa());

		logoImages = new HorizontalPanel();
		logoImages.setTitle(CONSTANTS.logoLabel());
		logoImages.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		logoImages.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		logoImages.add(paygate);
//		images.add(godaddy);
		logoImages.add(thawte);
		logoImages.add(mastercard);
		logoImages.add(visa);
		form.add(logoImages);
		
		//-----------------------------------------------
		// Card Holder Name field
		//-----------------------------------------------
		cardholderField = new TextField(this, null,
				CONSTANTS.cardholderLabel(),
				tab++);
		cardholderField.setMaxLength(50);
		cardholderField.setHelpText(CONSTANTS.cardholderHelp());
		form.add(cardholderField);

		//-----------------------------------------------
		// Card Number field
		//-----------------------------------------------
		cardnumberField = new TextField(this,  null,
				CONSTANTS.cardnumberLabel(),
				tab++);
		cardnumberField.setHelpText(CONSTANTS.cardnumberHelp());
		form.add(cardnumberField);

		//-----------------------------------------------
		// Card Expiry Month field
		//-----------------------------------------------
		cardmonthField = new ListField(this,  null,
				Finance.getMonths(),
				null,
				false,
				tab++);
		carddetailLabel = new HelpLabel(CONSTANTS.carddetailLabel(), CONSTANTS.carddetailHelp(), cardmonthField);
		form.add(carddetailLabel);
		cardmonthField.setFieldStyle(AbstractField.CSS.cbtListFieldCCmonth());

		//-----------------------------------------------
		// Card Expiry Year field
		//-----------------------------------------------
		cardyearField = new ListField(this,  null,
				Finance.getYears(),
				null,
				false,
				tab++);
		cardyearField.setFieldStyle(AbstractField.CSS.cbtListFieldCCyear());

		//-----------------------------------------------
		// Card Security Code field
		//-----------------------------------------------
		cardcodeField = new TextField(this, null,
				null,
				tab++);
		cardcodeField.setFieldStyle(AbstractField.CSS.cbtTextFieldCCcode());

		HorizontalPanel ccc = new HorizontalPanel();
		ccc.add(cardmonthField);
		ccc.add(cardyearField);
		ccc.add(cardcodeField);
		form.add(ccc);

		//-----------------------------------------------
		// Deposit Amount field
		//-----------------------------------------------
		amountField = new DoubleunitField(this, null,
//				NameId.getList(Currency.EXCHANGE_CURRENCY_NAMES, Currency.EXCHANGE_CURRENCIES),
				new CurrencyrateNameId(),
				CONSTANTS.amountLabel(),
				AbstractField.AF,
				tab++);
		amountField.setHelpText(CONSTANTS.amountHelp());
		amountField.setEnabled(false);
		amountField.setUnitenabled(false);
		form.add(amountField);

		//-----------------------------------------------
		// Message field
		//-----------------------------------------------
		messageField = new TextAreaField(this,  null,
				CONSTANTS.messageLabel(),
				tab++);
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

		//-----------------------------------------------
		// Save button
		//-----------------------------------------------
		final LocalRequest cancelRequest = new LocalRequest() {protected void perform() {hide();}};
		final CommandButton saveButton;
		if (Window.Location.getParameter(AbstractRoot.Type.pos.name()) == null) {
			saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), reservationUpdate, tab++);
			saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
			saveButton.setTitle(CONSTANTS.saveHelp());
		}
		else {
			saveButton = new CommandButton(this, CONSTANTS.submitButton(), reservationSubmit, tab++);
			saveButton.addStyleName(AbstractField.CSS.cbtGradientGreen());
			saveButton.setTitle(CONSTANTS.submitHelp());
		}
		saveButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		bar.add(saveButton);
		
		//-----------------------------------------------
		// Cancel button
		//-----------------------------------------------
		final CommandButton cancelButton = new CommandButton(this, AbstractField.CONSTANTS.allCancel(), cancelRequest, tab++);
		cancelButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		cancelButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		cancelButton.setTitle(AbstractField.CONSTANTS.helpCancel());
		bar.add(cancelButton);

		loader = new Image(AbstractField.BUNDLE.loader());
		loader.setVisible(false);
		bar.add(loader);

		//-----------------------------------------------
		// Transition array that defines the finite state machine
		//-----------------------------------------------
		fsm = new ArrayList<Transition>();
		fsm.add(new Transition(Reservation.State.Initial.name(), saveButton, Reservation.State.Provisional.name()));
		fsm.add(new Transition(Reservation.State.Initial.name(), cancelButton, Reservation.State.Initial.name()));
		return bar;
	}

	/*
	 * Checks if the reservation has any errors to prevent it being updated on the server.
	 * 
	 * @return true, if the reservation has errors.
	 */
	private boolean updateError() {
		return (
				ifMessage(reservationField.noValue(), Level.ERROR, CONSTANTS.reservationError(), reservationField)
				|| ifMessage(fromtodateField.noValue(), Level.ERROR, CONSTANTS.fromdateError(), fromtodateField)
				|| ifMessage(fromtodateField.noTovalue(), Level.ERROR, CONSTANTS.todateError(), fromtodateField)
				|| ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
				|| ifMessage(fromtodateField.getDuration(Time.DAY) < 1.0, Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
				|| ifMessage(productField.noValue(), Level.ERROR, CONSTANTS.productError(), productField)
				|| ifMessage(customerField.noValue() && AbstractRoot.noOrganizationid(), Level.ERROR, CONSTANTS.agentError(), customerField)
				|| ifMessage(priceField.noValue(), Level.ERROR, CONSTANTS.priceError(), priceField)
				|| ifMessage(quoteField.noValue(), Level.ERROR, CONSTANTS.quoteError(), quoteField)
				|| ifMessage(quoteField.noUnitvalue(), Level.ERROR, AbstractField.CONSTANTS.errCurrency(), quoteField)
				|| ifMessage(hasCollisions(), Level.ERROR, CONSTANTS.collisionError() + getCollisionlist(), fromtodateField)
		);
	}

	/*
	 * Checks if the payment has any errors to prevent it being updated on the server.
	 * 
	 * @return true, if the payment has errors.
	 */
	private boolean paymentError() {
		return (
				ifMessage(!secure, Level.ERROR, CONSTANTS.cardError(), amountField)
				|| ifMessage(emailaddressField.noValue(), Level.ERROR, CONSTANTS.emailaddressError(), emailaddressField)
				|| ifMessage(!Party.isEmailAddress(emailaddressField.getValue()), Level.ERROR, CONSTANTS.emailaddressError(), emailaddressField)
				|| ifMessage(familynameField.noValue(), Level.ERROR, CONSTANTS.familynameError(), familynameField)
				|| ifMessage(firstnameField.noValue(), Level.ERROR, CONSTANTS.firstnameError(), firstnameField)
				|| ifMessage(amountField.hasValue() && cardholderField.noValue(), Level.ERROR, CONSTANTS.cardholderError(), cardholderField)
				|| ifMessage(amountField.hasValue() && cardnumberField.noValue(), Level.ERROR, CONSTANTS.cardnumberError(), cardnumberField)
				|| ifMessage(amountField.hasValue() && !Finance.isCreditCardNumber(cardnumberField.getValue()), Level.ERROR, CONSTANTS.cardnumberError(), cardnumberField)
				|| ifMessage(amountField.hasValue() && cardmonthField.noValue(), Level.ERROR, CONSTANTS.cardmonthError(), cardmonthField)
				|| ifMessage(amountField.hasValue() && cardyearField.noValue(), Level.ERROR, CONSTANTS.cardyearError(), cardyearField)
				|| ifMessage(amountField.hasValue() && cardcodeField.noValue(), Level.ERROR, CONSTANTS.cardcodeError(), cardcodeField)
		);
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Check if Customer Exists
		//-----------------------------------------------
		partyExists = new GuardedRequest<Party>() {
			protected boolean error() {return emailaddressField.noValue();}
			protected void send() {super.send(getValue(new PartyExists()));}
			protected void receive(Party party) {
				if (party != null) {
					familynameField.setValue(party.getFamilyName());
					firstnameField.setValue(party.getFirstName());
				}
			}
		};

		//-----------------------------------------------
		// Book Reservation
		//-----------------------------------------------
		reservationBook = new GuardedRequest<Reservation>() {
			protected boolean error() {return productField.noValue();}
			protected void send() {super.send(getValue(new ReservationWidget()));}
			protected void receive(Reservation reservation) {setValue(reservation);}
		};

		//-----------------------------------------------
		// Create Reservation
		//-----------------------------------------------
		reservationCreate = new GuardedRequest<Reservation>() {
			protected boolean error() {return productField.noValue();}
			protected void send() {super.send(getValue(new ReservationWidget()));}
			protected void receive(Reservation reservation) {setValue(reservation);}
		};

		//-----------------------------------------------
		// Read Reservation
		//-----------------------------------------------
		reservationRead = new GuardedRequest<Reservation>() {
			protected boolean error() {return reservationField.noValue();}
			protected void send() {super.send(getValue(new ReservationRead()));}
			protected void receive(Reservation reservation) {setValue(reservation);}
		};

		//-----------------------------------------------
		// Update Reservation
		//-----------------------------------------------
		reservationUpdate = new GuardedRequest<Reservation>() {
			protected boolean error() {return updateError();}
			protected void send() {super.send(getValue(new ReservationUpdate()));}
			protected void receive(Reservation reservation) {
				if (parentTable != null) {parentTable.execute();}
				hide();
			}
		};
		
		//-----------------------------------------------
		// Submit Reservation Payment
		//-----------------------------------------------
		reservationSubmit = new GuardedRequest<Reservation>() {
			protected boolean error() {return updateError() || paymentError();}
			protected void send() {getJsonpBook();}
			protected void receive(Reservation reservation) {
				if (parentTable != null) {parentTable.execute();}
				hide();
			} //setValue(reservation);}
		};
		
		//-----------------------------------------------
		// Recalculate Reservation Prices
		//-----------------------------------------------
		reservationPrice = new GuardedRequest<PriceResponse>() {

			protected boolean error() {
				return (
						ifMessage(fromtodateField.noValue(), Level.TERSE, CONSTANTS.fromdateError(), fromtodateField)
						|| ifMessage(fromtodateField.noTovalue(), Level.TERSE, CONSTANTS.todateError(), fromtodateField)
						|| ifMessage(fromtodateField.isEndBeforeStart(), Level.TERSE, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
						|| ifMessage(productField.noValue(), Level.TERSE, CONSTANTS.productError(), productField)
				);
			}

			protected void send() {super.send(getValue(new ReservationPrice()));}
			
			protected void receive(PriceResponse response) {
				Log.debug("PriceResponse " + response.getDeposit() + " " + response);
				if (response.noValue()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.priceError(), quoteField);}
				else if (response.getCost() < 0.0){AbstractField.addMessage(Level.ERROR, CONSTANTS.suspendedError(), costField);}
				else {
				    	quoteDetail = response.getQuotedetail(); 
					priceField.setValue(response.getValue());
					priceField.setUnitvalue(response.getCurrency());
					quoteField.setValue(response.getQuote());
					quoteField.setUnitvalue(response.getCurrency());
					//quoteField.setEnabled(AbstractRoot.noPermission(AccessControl.AGENT));
					costField.setValue(response.getCost());
					costField.setUnitvalue(response.getCurrency());
					amountField.setValue(response.getDeposit(response.getQuote()));
					amountField.setUnitvalue(response.getCurrency());					
					if (ifMessage(response.hasCollisions(), Level.TERSE, CONSTANTS.collisionError() + getCollisionlist(response.getCollisions()), fromtodateField))
					{ifMessage(response.hasAlerts(), Level.TERSE, getAlertlist(response.getAlerts()), fromtodateField);}
					collisions = response.getCollisions();
				}
			}
		};
	}
	
	/* 
	 * The JSONP request callback to create a reservation.
	 * It is used to make cross-site requests from browsers that are not hosted by Razor.
	 * @see net.cbtltd.json.JSONService
	 */
	private void getJsonpBook() {

		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		String url = HOSTS.jsonUrl()
		+ "?service=" + JSONRequest.BOOK
		+ "&pos=" + Window.Location.getParameter(AbstractRoot.Type.pos.name()) 
		+ "&productid=" + productField.getValue() 
		+ "&fromdate=" + DF.format(fromtodateField.getValue())
		+ "&todate=" + DF.format(fromtodateField.getTovalue())
		+ "&emailaddress=" + emailaddressField.getValue()
		+ "&familyname=" + familynameField.getValue()
		+ "&firstname=" + firstnameField.getValue()
		+ "&cardholder=" + cardholderField.getValue()
		+ "&cardnumber=" + cardnumberField.getValue()
		+ "&cardmonth=" + cardmonthField.getValue()
		+ "&cardyear=" + cardyearField.getValue()
		+ "&cardcode=" + cardcodeField.getValue()
		;

		loader.setVisible(true);
		jsonp.requestObject(url, new AsyncCallback<BookWidgetItem>() {

			@Override
			public void onFailure(Throwable caught) {
				LoadingPopup.getInstance().hide();
				loader.setVisible(false);
				Log.debug("getJsonpBook onFailure");
				throw new RuntimeException("Failure:" + caught.getMessage());
			}

			@Override
			public void onSuccess(BookWidgetItem response) {
				Log.debug("getJsonpBook onSuccess " + response.string());
				loader.setVisible(false);
				onStateChange(response.getState());

				messageField.setVisible(true);
				if (response.noName()) {
					messageField.setValue(response.getMessage());
					messageField.setTitle(response.getMessage());
				}
				else {
					StringBuilder sb = new StringBuilder();
					sb.append("Reservation Number: " + response.getName());
					sb.append("\nArrival Date: " + AbstractRoot.getDF().format(fromtodateField.getValue()));
					sb.append("\nDeparture Date: " + AbstractRoot.getDF().format(fromtodateField.getTovalue()));
					sb.append("\nProperty: " + productField.getName());
					sb.append("\nPrice for Stay: " + AbstractField.AF.format(response.getQuote()) + " " + response.getCurrency());
					sb.append("\nTerms & Conditions\n").append(response.getMessage());
					messageField.setValue(sb.toString());
					messageField.setTitle(sb.toString());
					confirmationReport.getReport().setOrganizationid(response.getOrganizationid());
					confirmationReport.getReport().setFromtoname(response.getName());
					confirmationReport.setVisible(true);
				}
				messageField.setVisible(true);
				if (response.getAmount() > 0.0) {
					amountField.setValue(response.getAmount());
					amountField.setUnitvalue(response.getCurrency());
				}
			}
		});
	}
}
