/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.panel;
import java.util.Date;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.MessagePanel;
import net.cbtltd.client.form.AccessControl;
import net.cbtltd.client.resource.auto.AutoConstants;
import net.cbtltd.shared.Account;
import net.cbtltd.shared.Area;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Event;
import net.cbtltd.shared.Journal;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.Location;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Session;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.journal.JournalUpdate;
import net.cbtltd.shared.party.PartyCreate;
import net.cbtltd.shared.reservation.AvailableItem;
import net.cbtltd.shared.reservation.LookBook;
import net.cbtltd.shared.reservation.ReservationCreate;
import net.cbtltd.shared.reservation.ReservationEntities;
import net.cbtltd.shared.reservation.ReservationUpdate;
import net.cbtltd.shared.session.SessionAutoLogin;
import net.cbtltd.trial.test.Data;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * The Class AutoPopup is to test RPC and JSONP messages.
 * The RPC mode uses the standard GWT asynchronous messages;which requires that the widget be in an iFrame in a host HTML page.
 * The JSONP mode uses JSONP messages, which requires that the widget JavaScript be served from the host server.
 */
public class AutoPopup extends PopupPanel {

	private final AutoConstants CONSTANTS = GWT.create(AutoConstants.class);
	private final int wait = getParameter("wait", 15000);
	private final int searchresults = getParameter("sr", 20);
	private final int leadtime = getParameter("lt", 400);
	private final int lengthofstay = getParameter("los", 10);
	private final int lookbookratio = getParameter("lbr", 20);
	private final int invoiceratio = getParameter("ir", 3);
	private final int receiptratio = getParameter("rr", 2);

	private String pos;
	private AvailableItem item;
	private Party customer;
	private Reservation reservation;
	private ReservationEntities reservationentities;
	private Grid grid;
	private boolean terminate = false;

	/** Instantiates a new auto pop up panel. */
	public AutoPopup() {
		super(true);
		RootLayoutPanel.get().add(createContent());
	}

	private static AutoPopup instance;

	/**
	 * Gets the single instance of AutoPopup.
	 *
	 * @return single instance of AutoPopup
	 */
	public static synchronized AutoPopup getInstance() {
		if (instance == null) {instance = new AutoPopup();}
		AbstractField.CSS.ensureInjected();
		Log.debug("AutoPopup " + instance);
		return instance;
	}

	/**
	 * The entry point method which inserts the rendered panel(s) into the container HTML page.
	 */
	public void show() {
		try {
			pos = Model.encrypt(Data.getRandomString(Data.AGENT_IDS));
			if (pos == null || pos.isEmpty()) {throw new RuntimeException(CONSTANTS.posError());}
			Log.debug("sessionAutologin " + pos);
			sessionAutologin.execute();
		}
		catch (Throwable x) {
			x.printStackTrace();
			Log.debug("Load Error " + x.getStackTrace());
			AbstractField.addMessage(Level.ERROR, CONSTANTS.posError() + " " + pos, null);
		}
	}

	/*
	 * Creates the panel of account fields.
	 * 
	 * @return the panel of account fields.
	 */
	private VerticalPanel createContent() {
		VerticalPanel form = new VerticalPanel();
		final Label title = new Label(CONSTANTS.autoLabel());
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);

		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {	terminate = true;}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		closeButton.setTitle(CONSTANTS.closeHelp());
		form.add(closeButton);
		grid = new Grid(5, 3);
		grid.setHeight("100px");
		form.add(grid);
		//RootLayoutPanel.get().add(grid);

		int row = 0;
		int col = 0;
		grid.setText(row, col++,"Parameter");
		grid.setText(row, col++,"Number of Requests");
		grid.setText(row, col++,"Average Duration (ms)");

		row++; col = 0;
		grid.setText(row, col++,"Property Searches");
		grid.setWidget(row, col++, new TextBox());
		grid.setWidget(row, col++, new TextBox());

		row++; col = 0;
		grid.setText(row, col++,"Reservations Made");
		grid.setWidget(row, col++, new TextBox());
		grid.setWidget(row, col++, new TextBox());

		row++; col = 0;			
		grid.setText(row, col++,"Invoices Created");
		grid.setWidget(row, col++, new TextBox());
		grid.setWidget(row, col++, new TextBox());

		row++; col = 0;			
		grid.setText(row, col++,"Receipts Created");
		grid.setWidget(row, col++, new TextBox());
		grid.setWidget(row, col++, new TextBox());
		//Log.debug("createContent " + form);
		return form;
	}

	/**
	 * Gets the value of a property having the specified name.
	 *
	 * @param name the specified name.
	 * @param value the value
	 * @return the value of the property.
	 */
	public final Integer getParameter(String name, Integer value) {
		String arg = Window.Location.getParameter(name);
		Log.debug("getValue " + value + " " + name + " " + Window.Location.getParameter(name));
		return (arg == null) ? value : Integer.valueOf(arg);
	}

	private long searchtime = 0;
	private int searchcount = 0;

	/**
	 * Lookbook update.
	 *
	 * @param fromtime the fromtime
	 */
	private final void lookbookUpdate(Date fromtime) {
		searchcount++;
		searchtime += Time.getDuration(fromtime, new Date());
		((TextBox)grid.getWidget(1, 1)).setText(String.valueOf(searchcount));
		((TextBox)grid.getWidget(1, 2)).setText(String.valueOf(searchtime/searchcount));
	}

	private long reservationtime = 0;
	private int reservationcount = 0;

	/**
	 * Reservation update.
	 *
	 * @param fromtime the fromtime
	 */
	private final void reservationUpdate(Date fromtime) {
		reservationcount++;
		reservationtime += Time.getDuration(fromtime, new Date());
		((TextBox)grid.getWidget(2, 1)).setText(String.valueOf(reservationcount));
		((TextBox)grid.getWidget(2, 2)).setText(String.valueOf(reservationtime/reservationcount));
	}

	private long saletime = 0;
	private int salecount = 0;

	/**
	 * Sale update.
	 *
	 * @param fromtime the fromtime
	 */
	private final void saleUpdate(Date fromtime) {
		salecount++;
		saletime += Time.getDuration(fromtime, new Date());
		((TextBox)grid.getWidget(3, 1)).setText(String.valueOf(salecount));
		((TextBox)grid.getWidget(3, 2)).setText(String.valueOf(saletime/salecount));
	}

	private long receipttime = 0;
	private int receiptcount = 0;

	/**
	 * Receipt update.
	 *
	 * @param fromtime the fromtime
	 */
	private final void receiptUpdate(Date fromtime) {
		receiptcount++;
		receipttime += Time.getDuration(fromtime, new Date());
		((TextBox)grid.getWidget(4, 1)).setText(String.valueOf(receiptcount));
		((TextBox)grid.getWidget(4, 2)).setText(String.valueOf(receipttime/receiptcount));
	}

	/* Request to log in to the server automatically. */
	private final GuardedRequest<Session> sessionAutologin = new GuardedRequest<Session>() {
		protected boolean error() {return pos == null || pos.isEmpty();}
		protected void send() {super.send(new SessionAutoLogin(pos));}
		protected void receive(Session session) {setValue(session);}
	};

	private final GuardedRequest<Table<AvailableItem>> lookBook = new GuardedRequest<Table<AvailableItem>>() {
		Date fromtime = new Date();
		public boolean error() {return terminate;}
		protected void send() {
			fromtime = new Date();
			super.send(getValue(new LookBook()));
		}
		protected void receive(Table<AvailableItem> result) {
			lookbookUpdate(fromtime);
			if (searchcount%lookbookratio == 0 && result != null && result.getValue().size() > 0) {
				int index = Data.getRandomIndex(result.getValue().size());
				item = result.getValue().get(index);
				String familyname = Data.getRandomString(Data.FAMILY_NAMES);
				String firstname = Data.getRandomString(Data.MALE_NAMES);
				String extraname = Data.getRandomString(Data.FEMALE_NAMES);
				Location location = Data.getRandomLocation(Data.LOCATIONS);

				customer = new PartyCreate();
				customer.setName(familyname + "," + firstname);
				customer.setEmailaddress(firstname.trim().toLowerCase().replaceAll(" ", "") + "@" + familyname.trim().toLowerCase().replaceAll(" ", "") + ".com");
				customer.setExtraname(extraname);
				customer.setCountry(location.getCountry());
				customer.setCurrency(item.getCurrency());
				customer.setLanguage(Language.EN);
				customer.setLatitude(location.getLatitude());
				customer.setLongitude(location.getLongitude());
				customer.setLocationid(location.getId());
				customer.setNotes("Guest is from " + location.getName());
				customer.setOrganizationid(item.getSupplierid());
				customer.setPostalcode(location.getCode());
				customer.setState(Party.CREATED);
				customer.setType(Party.Type.Customer.name());
				customerExists.execute();
			}
			if (wait <= 0) {lookBook.execute();}
			else {
				Timer timer = new Timer() {
					public void run() {lookBook.execute();}
				};
				timer.schedule(wait);
			}
		}
	};

	private final GuardedRequest<Party> customerExists = new GuardedRequest<Party>() {
		protected boolean error() {return false;}
		protected void send() {super.send(customer);}
		protected void receive(Party result) {
			Log.debug("customerExists " + result);
			customer = result;
			reservationCreate.execute();
		}
	};

	//-----------------------------------------------
	// Create Reservation
	//-----------------------------------------------
	private final GuardedRequest<Reservation> reservationCreate = new GuardedRequest<Reservation>() {
		protected boolean error() {return AbstractRoot.noOrganizationid();}
		protected void send() {super.send(getValue(new ReservationCreate()));}
		protected void receive(Reservation result) {
			Log.debug("reservationCreate " + result);
			reservation = result;
			reservationUpdate.execute();
		}
	};

	//-----------------------------------------------
	// Update Reservation
	//-----------------------------------------------
	private final GuardedRequest<Reservation> reservationUpdate = new GuardedRequest<Reservation>() {
		Date fromtime = new Date();
		protected boolean error() {return false;}
		protected void send() {
			fromtime = new Date();
			super.send(getValue(new ReservationUpdate()));
		}
		protected void receive(Reservation result) {
			Log.debug("reservationUpdate " + result);
			reservation = result;
			reservationUpdate(fromtime);
			reservationentitiesRead.execute();
		}
	};

	//-----------------------------------------------
	// Read Reservation Entities (Agent, Guest, Property)
	//-----------------------------------------------
	private GuardedRequest<ReservationEntities> reservationentitiesRead = new GuardedRequest<ReservationEntities>() {
		protected boolean error() {return false;}
		protected void send() {super.send(new ReservationEntities(reservation));}
		protected void receive(ReservationEntities result) {
			Log.debug("reservationentitiesRead " + result);
			reservationentities = result;
			if (reservationcount%invoiceratio == 0) {saleCreate.execute();}
			if (reservationcount%receiptratio == 0) {receiptCreate.execute();}
		}
	};

	//-----------------------------------------------
	// Remote action to create a new sale instance.
	//-----------------------------------------------
	private GuardedRequest<Event<Journal>> saleCreate = new GuardedRequest<Event<Journal>>() {
		Date fromtime = new Date();
		protected boolean error() {return AbstractRoot.noOrganizationid();}
		protected void send() {
			fromtime = new Date();
			super.send(getReservationSale());
		}
		protected void receive(Event<Journal> result){
			Log.debug("saleCreate " + result);
			saleUpdate(fromtime);
		}
	};

	//-----------------------------------------------
	// Remote action to create a new receipt instance.
	//-----------------------------------------------
	private GuardedRequest<Event<Journal>> receiptCreate = new GuardedRequest<Event<Journal>>() {
		Date fromtime = new Date();
		protected boolean error() {return AbstractRoot.noOrganizationid();}
		protected void send() {
			fromtime = new Date();
			super.send(getPayment(false));
		}
		protected void receive(Event<Journal> result) {
			Log.debug("receiptCreate " + result);
			receiptUpdate(fromtime);
		}
	};

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#setValue(net.cbtltd.shared.api.HasState)
	 */
	/**
	 * Sets the value.
	 *
	 * @param session the new value
	 */
	private void setValue(Session session) {
		Log.debug("session " + session);
		if (session == null || session.notState(Session.LOGGED_IN)) {new MessagePanel(Level.ERROR, AbstractField.CONSTANTS.errPOScode());}
		else {
			AbstractRoot.setSession(session);
			lookBook.execute();
		}
		center();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#getValue(net.cbtltd.shared.api.HasState)
	 */
	/**
	 * Gets the value.
	 *
	 * @param lookbook the lookbook
	 * @return the value
	 */
	private LookBook getValue(LookBook lookbook) {
		Area area = Data.getRandomArea(Data.SEARCH_AREAS);
		if (AbstractRoot.readable(AccessControl.ORGANIZATION_ROLES)) {lookbook.setOrganizationid(AbstractRoot.getOrganizationid());}
		else {lookbook.setOrganizationid(null);}
		lookbook.setAgentid(AbstractRoot.getOrganizationid());
		lookbook.setState(Reservation.State.Provisional.name());
		lookbook.setLatitude(area.getLatitude());
		lookbook.setLongitude(area.getLongitude());
		lookbook.setDistance(area.getDistance());
		lookbook.setOffline(false);
		Date fromdate = Data.getRandomDate(leadtime);
		lookbook.setFromdate(fromdate);
		Date todate = Time.addDuration(fromdate, Data.getRandomInteger(1, lengthofstay), Time.DAY);
		lookbook.setTodate(todate);
		lookbook.setSpecial(false);
		lookbook.setSpecialmin(1);
		lookbook.setSpecialmax(10);
		lookbook.setCount(Data.getRandomInteger(1, 14));
		lookbook.setCountunit(true);
		lookbook.setExactcount(false);
		Integer pricemin = Data.getRandomInteger(0, 5000);
		lookbook.setPricemin(pricemin);
		Integer pricemax = pricemin + Data.getRandomInteger(100, 5000);
		lookbook.setPricemax(pricemax);
		lookbook.setPriceunit(true);
		lookbook.setCurrency(Currency.Code.USD.name());
		lookbook.setRating(Data.getRandomIndex(9));
		lookbook.setDiscount(Data.getRandomInteger(0, 50));
		lookbook.setAttributes(null);
		lookbook.setNumrows(searchresults);
		lookbook.setOrderby(AvailableItem.RANK);
		AbstractRoot.setIntegerValue(Party.Value.LookBookCount.name(), AbstractRoot.getIntegerValue(Party.Value.LookBookCount.name()) + 1);
		Log.debug("getValue " + lookbook);
		return lookbook;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#getValue(net.cbtltd.shared.api.HasState)
	 */
	/**
	 * Gets the value.
	 *
	 * @param action the action
	 * @return the value
	 */
	private Reservation getValue(Reservation action) {
		action.setState(Reservation.State.Provisional.name());
		action.setOrganizationid(AbstractRoot.getOrganizationid());
		action.setOldstate(Reservation.State.Initial.name());
		action.setId(reservation == null ? null : reservation.getId());
		action.setActorid(AbstractRoot.getActorid());
		action.setUnit(Unit.DAY);
		action.setCustomerid(customer.getId());
		action.setAgentid(AbstractRoot.getOrganizationid());
		action.setActorid(AbstractRoot.getActorid());
		action.setDate(new Date());
		action.setDuedate(Time.addDuration(action.getDate(), 3.0, Time.DAY));
		action.setFromdate(Time.getDateServer(item.getFromdate()));
		action.setTodate(Time.getDateServer(item.getTodate()));
		action.setArrivaltime("14:00");
		action.setDeparturetime("10:00");
		action.setProcess(NameId.Type.Reservation.name());
		action.setProductid(item.getProductid());
		action.setNotes(customer.getNotes());
		action.setUnit(Unit.DAY); //unitField.getValue());
		action.setPrice(item.getPrice());
		action.setQuote(item.getQuote());
		action.setCost(item.getCost());
		action.setCurrency(item.getCurrency());
		double leadtime = Time.getDuration(action.getDate(), action.getFromdate(), Time.DAY);
		Double deposit = (leadtime > 30.0) ? 50. : 100.;
		action.setDeposit(deposit);
		//reservation.setTasks(serviceGrid.getValue());
		Log.debug("getValue " + action);
		return action;
	}

	/*
	 * Reservation Sale - post a sale for a reservation which includes entries for manager commission, 
	 * owner revenue and sales taxes where applicable.
	 */
	/**
	 * Gets the reservation sale.
	 *
	 * @return the reservation sale
	 */
	private final JournalUpdate getReservationSale() {
		JournalUpdate event = new JournalUpdate();
		event.setState(Event.CREATED);
		event.setOrganizationid(reservation.getOrganizationid());
		event.setActorid(AbstractRoot.getActorid());
		event.setProcess(Event.Type.ReservationSale.name());
		event.setActivity(NameId.Type.Reservation.name());
		event.setParentid(reservation.getId());
		event.setDate(new Date());
		event.setNotes("Test Invoice");
		event.setType(Event.ACCOUNTING);

		Party customer = reservationentities.getCustomer();
		Party owner = reservationentities.getOwner();
		Product product = reservationentities.getProduct();
		Party manager = reservationentities.getManager();
		Double amount = reservation.getQuote();
		String currency = reservation.getCurrency();
		String description = "Invoice " + customer.getName() + " for " + product.getName() + " from " + AbstractRoot.getDF().format(reservation.getFromdate()) + " to " + AbstractRoot.getDF().format(reservation.getTodate());

		Double taxrate = Data.getRandom(0.0, 20.0);
		boolean ownermanager = owner.hasId(manager.getId());
		Double supplieramount = Event.round(ownermanager ? amount : amount * product.getCommissionValue() / 100.0);
		Double suppliertax = Event.round(manager.hasTaxnumber() ? taxrate * supplieramount / (100.0 + taxrate): 0.0);
		Double suppliersale = supplieramount - suppliertax;
		Double owneramount = ownermanager ? 0.0 : amount - supplieramount;
		Double ownertax = Event.round(owner.hasTaxnumber() ? taxrate * owneramount / (100.0 + taxrate): 0.0);
		Double ownersale = owneramount - ownertax;
		Double quantity = reservation.getDuration(Time.DAY);

		// CR Owner Sales
		post(	event,
				owner.getId(),
				owner.getName(),
				Account.SALES_REVENUE,
				Account.SALES_REVENUE_NAME,
				NameId.Type.Product.name(),
				product.getId(),
				product.getName(),
				currency,
				0.0,
				ownersale,
				quantity,
				Unit.DAY,
				description
				);

		// CR Owner VAT
		post(	event,
				owner.getId(),
				owner.getName(),
				Account.VAT_OUTPUT,
				Account.VAT_OUTPUT_NAME,
				null,
				null,
				null,
				currency,
				0.0,
				ownertax,
				description
				);

		// DR Owner Accounts Receivable 
		post(	event,
				owner.getId(),
				owner.getName(),
				Account.ACCOUNTS_RECEIVABLE,
				Account.ACCOUNTS_RECEIVABLE_NAME,
				NameId.Type.Party.name(),
				manager.getId(),
				manager.getName(),
				currency,
				ownersale + ownertax,
				0.0,
				description
				);

		// CR Manager Accounts Payable
		post(	event,
				manager.getId(),
				manager.getName(),
				Account.ACCOUNTS_PAYABLE,
				Account.ACCOUNTS_PAYABLE_NAME,
				NameId.Type.Party.name(),
				owner.getId(),
				owner.getName(),
				currency,
				0.0,
				ownersale + ownertax,
				description
				);

		// CR Manager Sales
		post(	event,
				manager.getId(),
				manager.getName(),
				Account.SALES_REVENUE,
				Account.SALES_REVENUE_NAME,
				NameId.Type.Product.name(),
				product.getId(),
				product.getName(),
				currency,
				0.0,
				suppliersale,
				quantity, 
				Unit.DAY,
				description
				);

		// CR Manager VAT
		post(	event,
				manager.getId(),
				manager.getName(),
				Account.VAT_OUTPUT,
				Account.VAT_OUTPUT_NAME,
				null,
				null,
				null,
				currency,
				0.0,
				suppliertax,
				description
				);

		// DR Manager Accounts Receivable from Customer
		post(	event,
				manager.getId(),
				manager.getName(),
				Account.ACCOUNTS_RECEIVABLE,
				Account.ACCOUNTS_RECEIVABLE_NAME,
				NameId.Type.Party.name(),
				customer.getId(), //Agent if exists, else customer: STO for agent, Quote for Customer
				customer.getName(),
				currency,
				ownersale + ownertax + suppliersale + suppliertax,
				0.0,
				description
				);
		return event;
	}

	/*
	 * Post Payment - posts a payment or receipt event to the ledger.
	 * 
	 * @param isPayment is true if a payment, false if a receipt
	 */
	/**
	 * Gets the payment.
	 *
	 * @param isPayment the is payment
	 * @return the payment
	 */
	private JournalUpdate getPayment (boolean isPayment) {

		JournalUpdate event = new JournalUpdate();
		event.setState(Event.CREATED);
		event.setProcess(Event.Type.ReservationSale.name());
		event.setOrganizationid(AbstractRoot.getOrganizationid());
		event.setActorid(AbstractRoot.getActorid());
		event.setActivity(NameId.Type.Reservation.name());
		event.setParentid(reservation.getId());
		event.setDate(new Date());
		event.setNotes("Test Invoice");
		event.setType(Event.ACCOUNTING);

		Party customer = reservationentities.getCustomer();
		Party owner = reservationentities.getOwner();
		Product product = reservationentities.getProduct();
		Party manager = reservationentities.getManager();
		Double amount = reservation.getQuote();
		String currency = reservation.getCurrency();
		String description = "Received from " + customer.getName() + " for " + product.getName() + " from " + AbstractRoot.getDF().format(reservation.getFromdate()) + " to " + AbstractRoot.getDF().format(reservation.getTodate());

		post(
				event,
				AbstractRoot.getOrganizationid(),
				AbstractRoot.getOrganizationname(),
				isPayment ? Account.ACCOUNTS_PAYABLE : Account.ACCOUNTS_RECEIVABLE,
						isPayment ? Account.ACCOUNTS_PAYABLE_NAME : Account.ACCOUNTS_RECEIVABLE_NAME,
								NameId.Type.Party.name(),
								customer.getId(),
								customer.getName(),
								currency,
								isPayment ? amount : 0.0,
										isPayment ? 0.0 : amount,
												description
				);
		post(
				event,
				AbstractRoot.getOrganizationid(),
				AbstractRoot.getOrganizationname(),
				Account.FINANCE, 
				Account.FINANCE_NAME, 
				NameId.Type.Finance.name(),
				"1",
				"Dummy Bank Account",
				currency,
				isPayment ? 0.0 : amount,
						isPayment ? amount : 0.0,
								description
				);

		return event;
	}

	/*
	 * Post - posts a journal which has no physical attributes to the ledger.
	 * @param organizationid is the ID of the organization of the transaction.
	 * @param organizationname is the name of the organization of the transaction.
	 * @param accountid is the ID of the ledger account of the transaction.
	 * @param accountname is the name of the ledger account of the transaction.
	 * @param entitytype is the sub-ledger (entity type) of the transaction.
	 * @param entityid is the ID of the sub-account(entity id) of the transaction.
	 * @param entityname is the name of the sub-account(entity name) of the transaction.
	 * @param currency is the currency of the amount(s) in the transaction.
	 * @param debitamount is the debit amount in the specified currency.
	 * @param creditamount is the credit amount in the specified currency.
	 * @param description is the description of the transaction.
	 */
	/**
	 * Post.
	 *
	 * @param event the event
	 * @param organizationid the organizationid
	 * @param organizationname the organizationname
	 * @param accountid the accountid
	 * @param accountname the accountname
	 * @param entitytype the entitytype
	 * @param entityid the entityid
	 * @param entityname the entityname
	 * @param currency the currency
	 * @param debitamount the debitamount
	 * @param creditamount the creditamount
	 * @param description the description
	 */
	private void post(
			Event<Journal> event,
			String organizationid,
			String organizationname,
			String accountid,
			String accountname,
			String entitytype,
			String entityid,
			String entityname,
			String currency,
			Double debitamount,
			Double creditamount,
			String description) {
		post(event, organizationid, organizationname, accountid,accountname, entitytype, entityid, entityname, currency, debitamount, creditamount, 1.0, Unit.EA, description);
	}

	/*
	 * Post - posts a journal which has physical attributes to the ledger.
	 * @param organizationid is the ID of the organization of the transaction.
	 * @param organizationname is the name of the organization of the transaction.
	 * @param accountid is the ID of the ledger account of the transaction.
	 * @param accountname is the name of the ledger account of the transaction.
	 * @param entitytype is the sub-ledger (entity type) of the transaction.
	 * @param entityid is the ID of the sub-account(entity id) of the transaction.
	 * @param entityname is the name of the sub-account(entity name) of the transaction.
	 * @param currency is the currency of the amount(s) in the transaction.
	 * @param debitamount is the debit amount in the specified currency.
	 * @param creditamount is the credit amount in the specified currency.
	 * @param quantity is the optional quantity of the transaction.
	 * @param unit is the optional unit of measure of the quantity.
	 * @param description is the description of the transaction.
	 */
	/**
	 * Post.
	 *
	 * @param event the event
	 * @param organizationid the organizationid
	 * @param organizationname the organizationname
	 * @param accountid the accountid
	 * @param accountname the accountname
	 * @param entitytype the entitytype
	 * @param entityid the entityid
	 * @param entityname the entityname
	 * @param currency the currency
	 * @param debitamount the debitamount
	 * @param creditamount the creditamount
	 * @param quantity the quantity
	 * @param unit the unit
	 * @param description the description
	 */
	private void post(
			Event<Journal> event,
			String organizationid,
			String organizationname,
			String accountid,
			String accountname,
			String entitytype,
			String entityid,
			String entityname,
			String currency,
			Double debitamount,
			Double creditamount,
			Double quantity,
			String unit,
			String description) {
		if (Math.abs(debitamount) < 0.01 && Math.abs(creditamount) < 0.01) {return;}
		getJournal(
				event,
				new Journal(
						event.getId(),
						Model.ZERO,
						organizationid,
						organizationname,
						Model.ZERO,
						accountid,
						accountname,
						entitytype,
						entityid,
						entityname,
						quantity,
						unit,
						0.0,
						currency,
						debitamount,
						creditamount,
						description
						)
				);
	}

	/*
	 * Checks if journal exists and increment if it does.
	 * 
	 * @param arg is the current event
	 * @param arg is the journal to be created or added
	 */
	private void getJournal(Event<Journal> event, Journal item) {
		if (event == null) {Log.debug("getJournal event null");}
		if (event.hasItems()) {
			for (Journal journal : event.getItems()) {
				if (item.isEqualto(journal)) {
					journal.addQuantity(item.getQuantity());
					journal.addDebitamount(item.getDebitamount());
					journal.addCreditamount(item.getCreditamount());
					return;
				}
			}
		}
		event.addItem(item);
	}

	/**
	 * Displays a pop up message if a condition is satisfied.
	 *
	 * @param condition the condition is true if the message is to be displayed.
	 * @param level the level of the message which dictates its importance and sets its colour.
	 * @param text the text to be displayed in the message.
	 * @param target the field or other widget next to which the message is to be displayed.
	 * @return true, if the condition is satisfied.
	 */
	public static boolean ifMessage(boolean condition, Level level, String text, UIObject target) {
		if (condition) {AbstractField.addMessage(level, text, target);}
		return condition;
	}
}

