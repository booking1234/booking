/**
 * @author	abookingnet
 * @see			License at http://razor-cloud.com/razor/License.html
 * @version		2.00
 */
package net.cbtltd.client.panel;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.Component;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.LocalRequest;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.CommandButton;
import net.cbtltd.client.field.DateField;
import net.cbtltd.client.field.DoubleField;
import net.cbtltd.client.field.DoubleunitField;
import net.cbtltd.client.field.HelpLabel;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.MessagePanel;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.Hosts;
import net.cbtltd.client.resource.journal.JournalBundle;
import net.cbtltd.client.resource.journal.JournalConstants;
import net.cbtltd.client.resource.journal.JournalCssResource;
import net.cbtltd.client.widget.RazorWidget;
import net.cbtltd.client.widget.book.PayWidgetItem;
import net.cbtltd.shared.Account;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Event;
import net.cbtltd.shared.Finance;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.Journal;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Report;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.Tax;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.finance.CurrencyNameId;
import net.cbtltd.shared.finance.FinanceRead;
import net.cbtltd.shared.journal.EventJournal;
import net.cbtltd.shared.journal.JournalCreate;
import net.cbtltd.shared.journal.JournalDelete;
import net.cbtltd.shared.journal.JournalItemTable;
import net.cbtltd.shared.journal.JournalRead;
import net.cbtltd.shared.journal.JournalUpdate;
import net.cbtltd.shared.party.PartyLicense;
import net.cbtltd.shared.party.PartyRead;
import net.cbtltd.shared.reservation.ReservationEntities;
import net.cbtltd.shared.tax.TaxTable;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.mybookingpal.config.RazorConfig;

/**
 * The Class JournalPopup is the primary accounting panel for several different types of financial transaction.
 * The financial ledger is a store of transactions which have an attribute for each aspect including:
 *  the user which entered the transaction
 *  the physical location at which the transaction was entered
 *  the type of transaction or process (Journal, Payment, Purchase, PurchaseSale, Receipt, Reservation Sale, Sale)
 *  the effective date of the transaction
 *  the due date for any follow up action
 *  the date on which follow up action was done
 *  an optional activity to which the transaction is allocated (reservation, Task)
 * 	the organization to which the transaction belongs
 *  the ledger account to which the transaction is posted
 *  the sub-ledger (entity type) to which the transaction is posted
 *  the sub-account (entity ID) to which the transaction is posted
 *  the debit amount (financial value) of the transaction
 *  the credit amount (financial value) of the transaction
 *  the currency code of the transaction
 *  an optional description of the transaction
 *  an optional location at which the transaction took place
 *  an optional physical quantity of the transaction
 *  an optional unit of measure of this physical quantity
 *  
 * Transactions are grouped by event so that their total amount is zero (debit amount _ credit amount = 0)
 *  This grouping is determined by the posting rules which depend on the type of process, the tax regime, 
 *  the organizations, locations, currencies and entities involved, and so on.
 *  
 *  For example, all financial processes have balanced debit and credit entries.
 *  Taxable transactions result in entries to debit or credit the relevant tax jurisdiction(s) in addition the principal transactions.
 *  A payment made by one organization by another results in matching loan account entries in the ledger of each organization.
 *  A receipt in one currency to settle a debt in another results in postings to currency control accounts.
 *  
 *  Accountants are familiar with these concepts which are often represented by "T" diagrams
 *  This class implements these posting rules for a number of process types including:
 *  Journal, Payment, Purchase, PurchaseSale, Receipt, Reservation Sale and Sale.
 *   
 * The class controls visibility of fields to those relevant to each type of process,
 *  and it validates data and creates transactions based on the posting rules implemented for each process type.
 *  A process can be reversed (a Credit Note is the reverse of a Sale, for example) by setting the reversed field to true.
 */
public class JournalPopup
extends AbstractPopup<Event<Journal>>  {

	private static final JournalConstants CONSTANTS = GWT.create(JournalConstants.class);
	private static final Hosts HOSTS = GWT.create(Hosts.class);
	private static final JournalBundle BUNDLE = JournalBundle.INSTANCE;
	private static final JournalCssResource CSS = BUNDLE.css();
	private static final int JOURNAL_ROWS = 140;

	//Events
	private static GuardedRequest<Event<Journal>> journalDelete;
	private static GuardedRequest<Event<Journal>> journalSave;
	private static GuardedRequest<Event<Journal>> journalUpdate;
	private static GuardedRequest<Event<Journal>> journalCreate;
	private static GuardedRequest<Event<Journal>> journalRead;
	private static GuardedRequest<Finance> financeRead;
	private static GuardedRequest<Party> organizationRead;
	private static GuardedRequest<Party> organizationLicense;
	private static GuardedRequest<Report> reportView;
	private static GuardedRequest<Table<Tax>> taxTable;

	private static Label reversedLabel;
	private static Label titleField;
	private static Image reportButton;
	private static Image reportLoader;
	private static SuggestField eventnameField;
	private static SuggestField entityField;
	private static Image entityButton;
	private static SuggestField supplierField;
	private static ListField processField;
	private static SuggestField accountField;
	private static ListField financeField;
	private static DateField eventdateField;
	private static DoubleField exchangerateField;
	private static DoubleField debitamountField;
	private static DoubleField creditamountField;
	private static DoubleunitField amountField;
	private static ListField taxField;
	private static TextAreaField descriptionField;

	private static HorizontalPanel logoImages;
	private static TextField cardholderField;
	private static TextField cardnumberField;
	private static HelpLabel carddetailLabel;
	private static ListField cardmonthField;
	private static ListField cardyearField;
	private static TextField cardcodeField;

	private static boolean isCardPayment = false;
	private static boolean isCardReceipt = false;
	private static CommandButton itemButton;
	private static CommandButton saveButton;
	private static CommandButton cancelButton;
	private static Button submitButton;
	private static Image loader;

	private static TableField<Journal> journalTable;
	private static TableField<EventJournal> parentField;

	private static Journal selectedjournal;
	private static Finance finance;
	private static Party organization;
	private static boolean hasTaxnumber() {return organization == null ? false : organization.hasTaxnumber();}
	private static ReservationEntities reservationentities;
	private static Event.Type type = Event.Type.Journal;
	private static ArrayList<Price> prices;
	private static ArrayList<Tax> taxes;
	private static Price price = null;
	private static int priceIndex = 0;
	private static Party.Type[] partytypes = {null, Party.Type.Supplier, Party.Type.Supplier, Party.Type.Supplier, Party.Type.Customer, Party.Type.Customer, Party.Type.Customer};
	private static boolean reversed = false;
	private static String message;

	/**
	 * Instantiates a new journal pop up panel.
	 */
	public JournalPopup() {
		super(false);
		AbstractField.CSS.ensureInjected();
		CSS.ensureInjected();
		createActions();
		setWidget(createContent());
		setModal(true);
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
	}

	private static JournalPopup instance;

	/**
	 * Gets the single instance of JournalPopup and resets its field values.
	 *
	 * @return single instance of JournalPopup
	 */
	public static synchronized JournalPopup getInstance() {
		if (instance == null) {
			instance = new JournalPopup();
			organizationRead.execute();
		}

		prices = null;
		price = null;
		financeField.onRefresh();

		eventdateField.setValue(new Date());
		accountField.setValue(null);
		entityField.setIds(null);
		entityField.setType(null);
		selectedjournal = null;
		parentField = null;
		reversed = false;

		finance = null;
		cardholderField.setValue("");
		cardnumberField.setValue("");
		cardmonthField.setValue("");
		cardyearField.setValue("");
		cardcodeField.setValue("");
		message = null;
		
		return instance;
	}

	/**
	 * Prepare the journal pop up panel for display and send a request to get the data from the JournalService.
	 * On receipt of the response the pop up panel is displayed in the center of the screen.
	 *
	 * @param eventname the process reference code (event name)
	 */
	public void show(String eventname) {
		parentField = null;
		eventnameField.setValue(eventname);
		journalRead.execute();
	}

	/**
	 * Prepare the journal pop up panel for display and send a request to get the data from the JournalService.
	 * On receipt of the response the pop up panel is displayed in the center of the screen.
	 *
	 * @param eventaction the ID of the reservation or task to which the process belongs
	 * @param reservationentities the information about the reservation to which the process belongs.
	 * @param parentField the field from which the process is called, typically a list of financial transactions.
	 */
	public void show(EventJournal eventaction, ReservationEntities reservationentities, TableField<EventJournal> parentField) {
		JournalPopup.parentField = parentField;
		JournalPopup.reservationentities = reservationentities;
		eventnameField.setValue(eventaction.getId());
		journalRead.execute();
	}

	/**
	 * Prepare the journal pop up panel and send a request to create a new instance to the JournalService.
	 * On receipt of the response the pop up panel is displayed in the center of the screen.
	 *
	 * @param type the type of process to be created
	 * @param reservationentities the information about the reservation to which the process belongs.
	 * @param parentField the field from which the process is called, typically a list of financial transactions.
	 */
	public void show(Event.Type type, ReservationEntities reservationentities, TableField<EventJournal> parentField) {
		JournalPopup.type = type;
		JournalPopup.parentField = parentField;
		JournalPopup.reservationentities = reservationentities;
		onReset(Event.INITIAL);
		processField.setValue(type.name());
		journalCreate.execute(true);
	}

	/**
	 * Prepare the journal pop up panel and send a request to create a new instance to the JournalService.
	 * On receipt of the response the pop up panel is displayed in the center of the screen.
	 *
	 * @param type the type of process to be created
	 * @param reservation_form the reservation to which the process is to be allocated.
	 * @param reservationentities the information about the reservation to which the process belongs.
	 * @param parentField the field from which the process is called, typically a list of financial transactions.
	 */
	public void show(Event.Type type, TableField<EventJournal> parentField) {
		show(type, null, null, null, parentField);
	}

	/**
	 * Prepare the journal pop up panel and send a request to create a new instance to the JournalService.
	 * On receipt of the response the pop up panel is displayed in the center of the screen.
	 *
	 * @param type the type of process to be created
	 * @param reservation the reservation to which the process is to be allocated.
	 * @param reservationentities the information about the reservation to which the process belongs.
	 * @param parentField the field from which the process is called, typically a list of financial transactions.
	 */
	public void show(Event.Type type, Reservation reservation, ReservationEntities reservationentities, TableField<EventJournal> parentField) {
		show(type, reservation, null, reservationentities, parentField);
	}

	/**
	 * Prepare the journal pop up panel and send a request to create a new instance.
	 * On receipt of the response the pop up panel is displayed in the center of the screen.
	 *
	 * @param type the type of process to be created
	 * @param reservation the reservation to which the process is to be allocated.
	 * @param price the priced feature to be invoiced
	 * @param reservationentities the information about the reservation to which the process belongs.
	 * @param parentField the field from which the process is called, typically a list of financial transactions.
	 */
	public void show(Event.Type type, Reservation reservation, ArrayList<Price> prices, ReservationEntities reservationentities, TableField<EventJournal> parentField) {
		if (GuardedRequest.isSending()) {new MessagePanel(Level.VERBOSE, AbstractField.CONSTANTS.allLoading()).center();}
		else {
			JournalPopup.type = type;
			JournalPopup.prices = prices;
			JournalPopup.priceIndex = 0;
			JournalPopup.parentField = parentField;
			JournalPopup.reservationentities = reservationentities;
			onReset(Event.INITIAL);
			processField.setValue(type.name());
			amountField.setValue(0.0);
			amountField.setUnitvalue(AbstractRoot.getCurrency());
			if (reservationentities == null) {descriptionField.setValue(Model.BLANK);}
			else if (type == Event.Type.Journal) {descriptionField.setValue(Model.BLANK);}
			else if (type == Event.Type.ReservationSale) {nextPrice(); return;}
			else {descriptionField.setValue(reservationentities.getReservation().getName());}
			journalCreate.execute(true);
		}
	}

	/*
	 * Gets the default reservation sale description.
	 *
	 * @return the default reservation sale description.
	 */
	private String getDescriptionReservationSale() {
		StringBuilder sb = new StringBuilder();
		sb.append(CONSTANTS.descriptionReservationSale()).append(reservationentities.getReservation().getName());
		if (reservationentities.hasCustomer()) {sb.append(" for ").append(reservationentities.getCustomer().getName());}
		else if (reservationentities.hasAgent()) {sb.append(" for ").append(reservationentities.getAgent().getName());}
		sb.append(" from ").append(AbstractRoot.getDF().format(reservationentities.getReservation().getFromdate()));
		sb.append(" to ").append(AbstractRoot.getDF().format(reservationentities.getReservation().getTodate()));
		return sb.toString();
	}

	/*
	 * Gets the next available price, or hides the panel if there are none more.
	 * Creates a PurchaseSale transaction if the price has a supplier, else creates a Sale transaction.
	 */
	private void nextPrice() {
		while (prices != null && !prices.isEmpty() && prices.size() > priceIndex) {
			price = prices.get(priceIndex++);
			if (price.hasType(Price.TAX_INCLUDED)) {continue;}
			accountField.setValue(Account.SALES_REVENUE);
			amountField.setValue(price.getTotalvalue());
			processField.setValue(type.name());
			amountField.setUnitvalue(price.getCurrency());
			descriptionField.setValue(price.getName());
			if (price.hasType(Price.RATE)) {descriptionField.setValue(getDescriptionReservationSale());}
			else if (price.noSupplierid()) {type = Event.Type.Sale;}
			else {
				type = Event.Type.PurchaseSale;
				supplierField.setValue(price.getSupplierid());
			}
			if ((price.hasType(Price.RATE) || price.hasType(Price.YIELD)) 
					&& reservationentities != null 
			) {type = Event.Type.ReservationSale;}
			if ((price.hasType(Price.RATE) || price.hasType(Price.YIELD)) 
					&& reservationentities != null 
					&& reservationentities.hasAgent(entityField.getValue())
			) {amountField.setValue(price.getTotalvalue() * reservationentities.getDiscountfactor());}
			processField.setValue(type.name());
			journalCreate.execute(true);
			return;
		}
		if (parentField != null) {parentField.execute(true);}
		hide();
	}

	/**
	 * Handles change events.
	 *
	 * @param change the event when changed.
	 */
	@Override
	public void onChange(ChangeEvent change) {
		if (eventnameField.sent(change)) {journalRead.execute();}
		else if (financeField.sent(change)) {financeRead.execute();}
		else if (accountField.sent(change)) {setEntityAction();}
		else if (entityField.sent(change)
				&& reservationentities != null 
				&& reservationentities.hasAgent(entityField.getValue())
				&& price != null
				&& (price.hasType(Price.RATE) || price.hasType(Price.YIELD))
		) {amountField.setValue(price.getTotalvalue() * reservationentities.getDiscountfactor());}
		if (selectedjournal != null) {
			getValue(selectedjournal);
			journalTable.redraw();
		}
		setView();
	}

	/* Sets which fields are visible depending on the process type and security of the service, and displays the journal pop up panel. */
	private void setView() {
		type = processField.noValue() ? Event.Type.Journal : Event.Type.valueOf(processField.getValue());
		reversedLabel.setVisible(journalTable.noValue());
		titleField.setText(reversed ? CONSTANTS.reversedNames()[type.ordinal()] : CONSTANTS.processNames()[type.ordinal()]);

		boolean isJournal = processField.hasValue(Event.Type.Journal.name());
		boolean isPayment = processField.hasValue(Event.Type.Payment.name());
		boolean isPurchase = processField.hasValue(Event.Type.Purchase.name());
		boolean isReceipt = processField.hasValue(Event.Type.Receipt.name());
		boolean isSale = processField.hasValue(Event.Type.Sale.name());
		boolean isPurchaseSale = processField.hasValue(Event.Type.PurchaseSale.name());
		boolean isReservationSale = processField.hasValue(Event.Type.ReservationSale.name());
		boolean isSecure = Window.Location.getHref().startsWith(HOSTS.jsonUrl().substring(0, 5));

		isCardPayment = isSecure && isPayment && entityField.hasValue(Party.CBT_LTD_PARTY);
		isCardReceipt = isSecure && isReceipt && financeField.hasValue(Finance.CBT_ZAR_FINANCE);
		boolean isCardVisible = isCardPayment || isCardReceipt;
		boolean isCardSelected = (isCardPayment && cardnumberField.hasValue()) || isCardReceipt;

		if (isPayment || isReceipt) {accountField.setValue(Account.FINANCE);}
		else if (isPurchase) {accountField.setType(Account.Type.Expense.name());}
		else if (isSale) {accountField.setType(Account.Type.Income.name());}
		else if (isJournal) {accountField.setType(null);}
		//		else if (isReservationSale) {center(); return;}

		entityField.setLabel(CONSTANTS.subaccountLabels()[type.ordinal()]);
		entityButton.setVisible(type != Event.Type.ReservationSale && type != Event.Type.Journal);

		if (reservationentities != null && reservationentities.getIds() != null) {entityField.setIds(reservationentities.getIds());}

		processField.setEnabled(journalTable.noValue());
		eventnameField.setEnabled(journalTable.noValue());
		eventdateField.setEnabled(journalTable.noValue());
		journalTable.setVisible(journalTable.hasValue());
		financeField.setVisible(isPayment || isReceipt || isPurchase || isPurchaseSale);
		supplierField.setVisible(isPurchaseSale);
		accountField.setVisible(isJournal || isPurchase || isSale);
		amountField.setVisible(!isJournal);
		//		amountField.setUnitenabled(!isReservationSale);
		debitamountField.setVisible(isJournal);
		creditamountField.setVisible(isJournal);
		taxField.setVisible((isPurchase || isSale) && hasTaxnumber());

		exchangerateField.setVisible(finance != null && !finance.hasCurrency(amountField.getUnitvalue()));
		exchangerateField.setLabel(getExchangeLabel());

		logoImages.setVisible(isCardVisible);
		cardholderField.setVisible(isCardVisible);
		cardnumberField.setVisible(isCardVisible);
		carddetailLabel.setVisible(isCardVisible);
		cardmonthField.setVisible(isCardVisible);
		cardyearField.setVisible(isCardVisible);
		cardcodeField.setVisible(isCardVisible);

		itemButton.setVisible(!isCardSelected);
		saveButton.setVisible(!isCardSelected && journalTable.hasValue());
		submitButton.setVisible(isCardSelected);

		center();
	}

	/**
	 * Gets the current journal instance.
	 *
	 * @return the current journal instance.
	 */
	@Override
	public Event<Journal> getValue() {return getValue(new Event<Journal>());}

	/**
	 * Updates the event parameter with the current journal field values.
	 *
	 * @param event the event action to be updated with current journal field values.
	 * @return the action with the updates.
	 */
	public Event<Journal> getValue(Event<Journal> event) {
		event.setState(state);
		event.setOrganizationid(AbstractRoot.getOrganizationid());
		event.setActorid(AbstractRoot.getActorid());
		event.setProcess(processField.getValue());
		event.setId(eventnameField.getValue());
		event.setName(eventnameField.getName());
		event.setActivity(NameId.Type.Reservation.name());
		event.setParentid(reservationentities == null ? null :reservationentities.getId());
		event.setDate(Time.getDateServer(eventdateField.getValue()));
		event.setNotes(message);
		event.setProcess(processField.getValue());
		event.setType(Event.ACCOUNTING); //TODO:
		event.setItems(journalTable.getValue());
		Log.debug("getValue " + event);
		return event;
	}

	/**
	 * Sets the journal field values with the event parameter.
	 *
	 * @param event the event action to update the current journal field values.
	 */
	public void setValue(Event<Journal> event) {
		Log.debug("setValue " + event);
		if (event == null) {onReset(Event.CREATED);}
		else {
			setResetting(true);
			onStateChange(event.getState());
			processField.setValue(event.getProcess());
			eventnameField.setValue(event.getId());
			eventdateField.setValue(Time.getDateClient(event.getDate()));
			journalTable.setValue(event.getItems());
			setValue((Journal)journalTable.getFirstItem());
			onOptionChange(event.getDonedate() == null ? Component.ENABLED : Component.DISABLED);
			setView();
			taxTable.execute();
			setResetting(false);
		}
	}

	/* 
	 * Updates the parameter journal line item to the current journal field values.
	 * 
	 * @param journal line item to be updated.
	 */
	private Journal getValue(Journal journal) {
		journal.setAccountid(accountField.getValue());
		if (financeField.isVisible()) {
			journal.setEntitytype(NameId.Type.Finance.name());
			journal.setEntityid(financeField.getValue());
		}
		if (entityField.isVisible()) {
			journal.setEntitytype(NameId.Type.Party.name());
			journal.setEntityid(entityField.getValue());
		}
		journal.setDebitamount(debitamountField.getValue());
		journal.setCreditamount(creditamountField.getValue());
		journal.setDescription(descriptionField.getValue());
		return journal;
	}

	/* 
	 * Sets the current journal field values with the parameter journal line item.
	 * 
	 * @param journal line item with which to update the fields.
	 */
	private void setValue(Journal journal) {
		if (journal == null) {return;}
		accountField.setValue(journal.getAccountid());
		if (journal.hasEntitytype(NameId.Type.Finance)) {financeField.setValue(journal.getEntityid());}
		else if (journal.hasEntitytype(NameId.Type.Party)) {entityField.setValue(journal.getEntityid());}
		amountField.setValue(journal.getAmount());
		amountField.setUnitvalue(journal.getCurrency());
		debitamountField.setValue(journal.getDebitamount());
		creditamountField.setValue(journal.getCreditamount());
		descriptionField.setValue(journal.getDescription());
	}

	private Finance getValue(Finance finance){
		finance.setOrganizationid(AbstractRoot.getOrganizationid());
		finance.setId(financeField.getValue());
		return finance;
	}

	/**
	 * Sets the bank or cash account to be used for payments and receipts.
	 *
	 * @param finance the new bank or cash account.
	 */
	public void setValue(Finance finance) {
		JournalPopup.finance = finance;
		if (finance != null) {amountField.setUnitvalue(finance.getCurrency());}
	}

	/*
	 * Updates the parameter organization (party) with the current session organization ID.
	 *
	 * @param organization the organization to be updated.
	 */
	private Party getValue(Party organization){
		organization.setOrganizationid(AbstractRoot.getOrganizationid());
		organization.setId(AbstractRoot.getOrganizationid());
		return organization;
	}

	/*
	 * Sets the party to be used, which may be a customer, supplier or other party to the transaction.
	 *
	 * @param organization the new party to be used.
	 */
	private void setValue(Party organization) {
		JournalPopup.organization = organization;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue(java.lang.Object)
	 */
	private Tax getValue(TaxTable action) {
		if (reservationentities == null) {
			action.setEntitytype(NameId.Type.Party.name());
			action.setEntityid(AbstractRoot.getOrganizationid());
		}
		else {
			action.setEntitytype(NameId.Type.Product.name());
			action.setEntityid(reservationentities.getProduct().getId());
		}
		//action.setOrganizationid(AbstractRoot.getOrganizationid());
		action.setOrderby(Tax.ID);
		action.setNumrows(Integer.MAX_VALUE);
		action.setStartrow(0);
		Log.debug("getValue " + action);
		return action;
	}

	/* Create and initialize a report instance with parameters needed to generate a printable document for this journal. */  
	private Report getReport() {
		Report report = new Report();;
		report.setOrganizationid(AbstractRoot.getOrganizationid());
		report.setActorid(AbstractRoot.getActorid());
		report.setFromname(eventnameField.getName());
		report.setToname(eventnameField.getName());
		report.setCurrency(amountField.getUnitvalue());
		report.setDate(new Date());
		report.setDesign(type.name());
		report.setFormat(Report.PDF);
		report.setState(Report.CREATED);
		return report;
	}

	/* 
	 * Sets the field refresh actions based on the values of account and entity of the current journal.
	 * For example, the account ACCOUNTS_PAYABLE has sub-accounts of type Supplier. 
	 * but the account ACCOUNTS_RECEIVABLE has sub-accounts of type Customer.
	 */
	private void setEntityAction() {
		entityField.setEnabled(true);
		financeField.setEnabled(true);
		if (accountField.hasValue(Account.ACCOUNTS_PAYABLE)) {entityField.setAction(new NameIdAction(Service.PARTY, Party.Type.Supplier.name()));}
		else if(accountField.hasValue(Account.ACCOUNTS_RECEIVABLE)) {entityField.setAction(new NameIdAction(Service.PARTY, Party.Type.Customer.name()));}
		else if(accountField.hasValue(Account.PURCHASE_SUSPENSE)) {entityField.setAction(new NameIdAction(Service.PARTY, Party.Type.Supplier.name()));}
		//		else if(accountField.hasValue(Account.SALES_REVENUE)) {entityField.setAction(new NameIdAction(Service.PRODUCT, Product.Type.Accommodation.name()));}
		else if (accountField.hasValue(Account.SALES_REVENUE)) {entityField.setAction(new NameIdAction(Service.PARTY, Party.Type.Customer.name()));}
		else if(accountField.hasValue(Account.TRADING_LOANS)) {entityField.setAction(new NameIdAction(Service.PARTY, null));}
		else if(accountField.hasValue(Account.VAT_INPUT)) {entityField.setAction(new NameIdAction(Service.PARTY, Party.Type.Jurisdiction.name()));}
		else if(accountField.hasValue(Account.VAT_OUTPUT)) {entityField.setAction(new NameIdAction(Service.PARTY, Party.Type.Jurisdiction.name()));}
		else if(accountField.hasValue(Account.FINANCE)) {entityField.setAction(new NameIdAction(Service.FINANCE, null));}
		else if(accountField.hasValue(Account.COST_OF_SALES)) {entityField.setAction(new NameIdAction(Service.PRODUCT, null));}
		else {entityField.setEnabled(type != Event.Type.Journal);} //needed for sales and expense accounts
	}

	/* Gets if a party (customer, supplier, jurisdiction etc) is involved in the current process. */ 
	private boolean isParty() {
		return (accountField.hasValue(Account.ACCOUNTS_PAYABLE)
				|| accountField.hasValue(Account.ACCOUNTS_RECEIVABLE)
				|| accountField.hasValue(Account.PURCHASE_SUSPENSE)
				|| accountField.hasValue(Account.SALES_REVENUE)
				|| accountField.hasValue(Account.TRADING_LOANS)
				|| accountField.hasValue(Account.VAT_INPUT)
				|| accountField.hasValue(Account.VAT_OUTPUT)
				);
	}

	/*
	 * Checks if journal exists and increment if it does.
	 * 
	 * @param arg is the journal to be created or added
	 */
	private void getJournal(Journal arg) {
		if (!journalTable.noValue()) {
			for (Journal journal : journalTable.getValue()) {
				if (arg.isEqualto(journal)) {
					journal.addQuantity(arg.getQuantity());
					journal.addDebitamount(arg.getDebitamount());
					journal.addCreditamount(arg.getCreditamount());
					return;
				}
			}
		}
		journalTable.addValue(arg);
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
	private void post(
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
		post(organizationid, organizationname, accountid,accountname, entitytype, entityid, entityname, currency, debitamount, creditamount, 1.0, Unit.EA, description);
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
	private void post(
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
				new Journal(
						eventnameField.getValue(),
						Model.ZERO,
						organizationid,
						organizationname,
						Model.ZERO,
						accountid,
						accountname,
						entitytype,
						entityid,
						entityname,
						reversed ? -quantity : quantity,
						unit,
						0.0,
						currency,
						reversed ? creditamount : debitamount,
						reversed ? debitamount : creditamount,
						description
					)
				);
	}

	/*
	 * Post Finance - posts a journal which involves bank or cash accounts (finance) to the ledger.
	 * This automatically accounts for loan account entries if the journal organization is not the same as the finance organization.
	 * This automatically accounts for currency conversion entries if the journal currency is not the same as the finance currency.
	 * 
	 * @param isPayment is true if the process is a payment, false if a receipt
	 */
	private void postFinance(boolean isPayment) {
		if (finance == null) {Window.alert("JournalPopup postFinance " + finance);}
		if (finance.hasCurrency(amountField.getUnitvalue())) {
			post(
					AbstractRoot.getOrganizationid(),
					AbstractRoot.getOrganizationname(),
					Account.FINANCE, 
					Account.FINANCE_NAME, 
					NameId.Type.Finance.name(),
					financeField.getValue(),
					financeField.getName(),
					amountField.getUnitvalue(),
					isPayment ? 0.0 : amountField.getValue(),
							isPayment ? amountField.getValue() : 0.0,
									descriptionField.getValue()
					);
		}
		else {
			Double rate = exchangerateField.getValue(); //TODO: WebService.getRate(amountField.getUnitvalue(), finance.getCurrency(), eventdateField.getValue());
			Double debitbank = isPayment ? 0.0 : Event.round(amountField.getValue() * rate, 2);
			Double creditbank = isPayment ? Event.round(amountField.getValue() * rate, 2) : 0.0;
			post(
					AbstractRoot.getOrganizationid(),
					AbstractRoot.getOrganizationname(),
					Account.CURRENCY_CONTROL, 
					Account.CURRENCY_CONTROL_NAME, 
					NameId.Type.Account.name(),
					Model.ZERO,
					Model.BLANK, 
					amountField.getUnitvalue(), 
					isPayment ? 0.0 : amountField.getValue(),
							isPayment ? amountField.getValue() : 0.0,
									descriptionField.getValue()
					);
			post(
					AbstractRoot.getOrganizationid(),
					AbstractRoot.getOrganizationname(),
					Account.CURRENCY_CONTROL, 
					Account.CURRENCY_CONTROL_NAME, 
					NameId.Type.Account.name(),
					Model.ZERO, 
					Model.BLANK,
					finance.getCurrency(),
					creditbank,
					debitbank,
					descriptionField.getValue()
					);
			post(
					AbstractRoot.getOrganizationid(),
					AbstractRoot.getOrganizationname(),
					Account.FINANCE, 
					Account.FINANCE_NAME,
					NameId.Type.Finance.name(),
					financeField.getValue(),
					financeField.getName(),
					finance.getCurrency(),
					debitbank, 
					creditbank, 
					descriptionField.getValue()
					);
		}

		if (AbstractRoot.hasOrganizationid(finance.getOwnerid())) {return;}

		post(
				finance.getOwnerid(),
				finance.getOwnername(),
				Account.TRADING_LOANS,
				Account.TRADING_LOANS_NAME,
				NameId.Type.Party.name(),
				AbstractRoot.getOrganizationid(),
				AbstractRoot.getOrganizationname(),
				amountField.getUnitvalue(),
				isPayment ? 0.0 : amountField.getValue(),
						isPayment ? amountField.getValue() : 0.0,
								descriptionField.getValue()
				);
		post(
				AbstractRoot.getOrganizationid(),
				AbstractRoot.getOrganizationname(),
				Account.TRADING_LOANS,
				Account.TRADING_LOANS_NAME,
				NameId.Type.Party.name(),
				finance.getOwnerid(),
				finance.getOwnername(),
				amountField.getUnitvalue(),
				isPayment ? 0.0 : amountField.getValue(),
						isPayment ? amountField.getValue() : 0.0,
								descriptionField.getValue()
				);
	}

	/*
	 * Post Payment - posts a payment or receipt event to the ledger.
	 * 
	 * @param isPayment is true if a payment, false if a receipt
	 */
	private void postPayment (boolean isPayment) {
		post(
				AbstractRoot.getOrganizationid(),
				AbstractRoot.getOrganizationname(),
				isPayment ? Account.ACCOUNTS_PAYABLE : Account.ACCOUNTS_RECEIVABLE,
						isPayment ? Account.ACCOUNTS_PAYABLE_NAME : Account.ACCOUNTS_RECEIVABLE_NAME,
								NameId.Type.Party.name(),
								entityField.getValue(),
								entityField.getName(),
								amountField.getUnitvalue(),
								isPayment ? amountField.getValue() : 0.0,
										isPayment ? 0.0 : amountField.getValue(),
												descriptionField.getValue()
				);
		postFinance(isPayment);
		if (AbstractRoot.hasOrganizationid(Party.CBT_LTD_PARTY)) {
			//TODO: post reverse to manager accounts
			post(
					entityField.getValue(),
					entityField.getName(),
					isPayment ? Account.ACCOUNTS_RECEIVABLE : Account.ACCOUNTS_PAYABLE,
							isPayment ? Account.ACCOUNTS_RECEIVABLE_NAME : Account.ACCOUNTS_PAYABLE_NAME,
									NameId.Type.Party.name(),
									AbstractRoot.getOrganizationid(),
									AbstractRoot.getOrganizationname(),
									amountField.getUnitvalue(),
									isPayment ? 0.0 : amountField.getValue(),
											isPayment ? amountField.getValue() : 0.0,
													descriptionField.getValue()
					);
			post(
					entityField.getValue(),
					entityField.getName(),
					Account.FINANCE,
					Account.FINANCE_NAME,
					NameId.Type.Finance.name(),
					Finance.CBT_USD_FINANCE,
					Finance.CBT_USD_FINANCE_NAME,
					amountField.getUnitvalue(),
					isPayment ? amountField.getValue() : 0.0,
							isPayment ? 0.0 : amountField.getValue(),
									descriptionField.getValue()
					);
			//Checks if license is OK
			organizationLicense.execute(true);
		}
	}

	/*
	 * Post Payment - posts a receipt via the payment gateway.
	 */
	private void postCardReceipt () {
		Double amount = amountField.getValue();
		Double fee = Event.round(amount * Finance.CBT_ZAR_FINANCE_FEE);
		post(
				AbstractRoot.getOrganizationid(),
				AbstractRoot.getOrganizationname(),
				Account.ACCOUNTS_RECEIVABLE,
				Account.ACCOUNTS_RECEIVABLE_NAME,
				NameId.Type.Party.name(),
				entityField.getValue(),
				entityField.getName(),
				amountField.getUnitvalue(),
				0.0,
				amount,
				descriptionField.getValue()
				);
		post(
				AbstractRoot.getOrganizationid(),
				AbstractRoot.getOrganizationname(),
				Account.FINANCE,
				Account.FINANCE_NAME,
				NameId.Type.Finance.name(),
				Finance.CBT_USD_FINANCE,
				Finance.CBT_USD_FINANCE_NAME,
				amountField.getUnitvalue(),
				amount - fee,
				0.0,
				descriptionField.getValue()
				);
		post(
				AbstractRoot.getOrganizationid(),
				AbstractRoot.getOrganizationname(),
				Account.CARD_MERCHANT_FEES,
				Account.CARD_MERCHANT_FEES_NAME,
				NameId.Type.Party.name(),
				null,
				null,
				amountField.getUnitvalue(),
				fee,
				0.0,
				descriptionField.getValue() //TODO:
			);
	}

	/*
	 * Post Purchase - posts a purchase or sale event to the ledger, including entries for taxes where appropriate.
	 * 
	 * @param isPurchase is true if a purchase, false if a sale
	 * @param isPurchaseSale is true if a purchase sale, false if an accommodation sale
	 */
	private void postPurchase (boolean isPurchase) {

		if (financeField.noValue() && entityField.noValue()) {
			AbstractField.addMessage(Level.ERROR, CONSTANTS.entityorfinanceError(), entityField);
			return;
		}

		Double taxrate = 0.0;
		try {taxrate = Double.valueOf(taxField.getValue());}
		catch (NumberFormatException x) {AbstractField.addMessage(Level.ERROR, CONSTANTS.taxError() + taxField.getValue(), taxField);}

		Double totalamount = amountField.getValue();
		Double taxamount = Event.round(hasTaxnumber() ? taxrate * totalamount / (100.0 + taxrate): 0.0);
		Double lineamount = totalamount - taxamount;

		post(
				AbstractRoot.getOrganizationid(),
				AbstractRoot.getOrganizationname(),
				accountField.getValue(),
				accountField.getName(),
				NameId.Type.Account.name(),
				accountField.getValue(),
				accountField.getName(),
				amountField.getUnitvalue(),
				isPurchase ? lineamount : 0.0,
						isPurchase ? 0.0 : lineamount,
								descriptionField.getValue()
				);

		post(
				AbstractRoot.getOrganizationid(),
				AbstractRoot.getOrganizationname(),
				isPurchase ? Account.VAT_INPUT : Account.VAT_OUTPUT,
						isPurchase ? Account.VAT_INPUT_NAME : Account.VAT_OUTPUT_NAME,
								NameId.Type.Account.name(),
								Model.ZERO,
								Model.BLANK,
								amountField.getUnitvalue(),
								isPurchase ? taxamount : 0.0,
										isPurchase ? 0.0 : taxamount,
												descriptionField.getValue()
				);

		if (financeField.hasValue() && entityField.hasValue()) {
			post(
					AbstractRoot.getOrganizationid(),
					AbstractRoot.getOrganizationname(),
					isPurchase ? Account.ACCOUNTS_PAYABLE : Account.ACCOUNTS_RECEIVABLE,
							isPurchase ? Account.ACCOUNTS_PAYABLE_NAME : Account.ACCOUNTS_RECEIVABLE_NAME,
									NameId.Type.Party.name(),
									entityField.getValue(),
									entityField.getName(),
									amountField.getUnitvalue(),
									totalamount,
									totalamount,
									descriptionField.getValue()
					);
			postFinance(isPurchase);
		}

		else if (financeField.hasValue()) {postFinance(isPurchase);}

		else if (entityField.hasValue()) {
			post(
					AbstractRoot.getOrganizationid(),
					AbstractRoot.getOrganizationname(),
					isPurchase ? Account.ACCOUNTS_PAYABLE : Account.ACCOUNTS_RECEIVABLE,
							isPurchase ? Account.ACCOUNTS_PAYABLE_NAME : Account.ACCOUNTS_RECEIVABLE_NAME,
									NameId.Type.Party.name(),
									entityField.getValue(),
									entityField.getName(),
									amountField.getUnitvalue(),
									isPurchase ? 0.0 : totalamount,
											isPurchase ? totalamount : 0.0,
													descriptionField.getValue()
					);
		}
	}

	/*
	 * Post Purchase Sale - post a purchase made for another party.
	 */
	private void postPurchaseSale () { 

		Double totalamount = amountField.getValue();

		post(
				AbstractRoot.getOrganizationid(),
				AbstractRoot.getOrganizationname(),
				Account.ACCOUNTS_RECEIVABLE,
				Account.ACCOUNTS_RECEIVABLE_NAME,
				NameId.Type.Party.name(),
				entityField.getValue(),
				entityField.getName(),
				amountField.getUnitvalue(),
				totalamount,
				0.0,
				descriptionField.getValue()
				);

		post(
				AbstractRoot.getOrganizationid(),
				AbstractRoot.getOrganizationname(),
				Account.ACCOUNTS_PAYABLE,
				Account.ACCOUNTS_PAYABLE_NAME,
				NameId.Type.Party.name(),
				supplierField.getValue(),
				supplierField.getName(),
				amountField.getUnitvalue(),
				0.0,
				totalamount,
				descriptionField.getValue()
				);

		if (financeField.isVisible() && financeField.hasValue()) {
			post(
					AbstractRoot.getOrganizationid(),
					AbstractRoot.getOrganizationname(),
					Account.ACCOUNTS_PAYABLE,
					Account.ACCOUNTS_PAYABLE_NAME,
					NameId.Type.Party.name(),
					supplierField.getValue(),
					supplierField.getName(),
					amountField.getUnitvalue(),
					totalamount,
					0.0,
					descriptionField.getValue()
					);

			postFinance(true);
		}
	}

	/*
	 * Post Journal - post a journal line item
	 */
	private void postJournal() {
		post(
				AbstractRoot.getOrganizationid(),
				AbstractRoot.getOrganizationname(),
				accountField.getValue(),
				accountField.getName(),
				accountField.hasValue(Account.FINANCE) ? NameId.Type.Finance.name() : NameId.Type.Party.name(),
						entityField.getValue(),
						entityField.getName(),
						//							accountField.hasValue(Account.FINANCE) ? NameId.Type.Finance.name() : NameId.Type.Party.name(),
						//									accountField.hasValue(Account.FINANCE) ? financeField.getValue() : entityField.getValue(),
						//									accountField.hasValue(Account.FINANCE) ? financeField.getN() : entityField.getName(),
						amountField.getUnitvalue(),
						debitamountField.getValue(),
						creditamountField.getValue(),
						descriptionField.getValue()
				);
	}

	/*
	 * Reservation Sale - post a sale for a reservation which includes entries for manager commission, 
	 * owner revenue and sales taxes where applicable.
	 */
	private final void postReservationSale() {

		Reservation reservation = reservationentities.getReservation();
		Party supplier = reservationentities.getManager();
		Party owner = reservationentities.getOwner();
		Product product = reservationentities.getProduct();
		if (reservationentities == null || supplier == null || owner == null || product == null) {Window.alert("JournalPopup postReservationSale " + (reservationentities == null) + "," + (supplier == null) + "," + (owner == null) + "," + (product == null));}

		taxField.setName(Tax.VAT_NORMAL); //TODO: check

		Double taxrate = 0.0;
		try {taxrate = taxField.noValue() ? 0.0 : Double.valueOf(taxField.getValue());}
		catch (NumberFormatException x) {AbstractField.addMessage(Level.ERROR, CONSTANTS.taxError() + taxField.getValue(), taxField);}

		boolean ownermanager = owner.hasId(supplier.getId());
		Double supplieramount = Event.round(ownermanager ? amountField.getValue() : amountField.getValue() * product.getCommissionValue() / 100.0);
		Double suppliertax = Event.round(supplier.hasTaxnumber() ? taxrate * supplieramount / (100.0 + taxrate): 0.0);
		Double suppliersale = supplieramount - suppliertax;
		Double owneramount = ownermanager ? 0.0 : amountField.getValue() - supplieramount;
		Double ownertax = Event.round(owner.hasTaxnumber() ? taxrate * owneramount / (100.0 + taxrate): 0.0);
		Double ownersale = owneramount - ownertax;
		Double quantity = reservation.getDuration(Time.DAY);

		// CR Owner Sales
		post(
				owner.getId(),
				owner.getName(),
				Account.SALES_REVENUE,
				Account.SALES_REVENUE_NAME,
				NameId.Type.Product.name(),
				product.getId(),
				product.getName(),
				amountField.getUnitvalue(),
				0.0,
				ownersale,
				quantity, 
				Unit.DAY,
				descriptionField.getValue()
				);

		// CR Owner VAT
		post(
				owner.getId(),
				owner.getName(),
				Account.VAT_OUTPUT,
				Account.VAT_OUTPUT_NAME,
				null,
				null,
				null,
				amountField.getUnitvalue(),
				0.0,
				ownertax,
				descriptionField.getValue()
				);

		// DR Owner Accounts Receivable 
		post(
				owner.getId(),
				owner.getName(),
				Account.ACCOUNTS_RECEIVABLE,
				Account.ACCOUNTS_RECEIVABLE_NAME,
				NameId.Type.Party.name(),
				supplier.getId(),
				supplier.getName(),
				amountField.getUnitvalue(),
				ownersale + ownertax,
				0.0,
				descriptionField.getValue()
				);

		// CR Manager Accounts Payable
		post(
				supplier.getId(),
				supplier.getName(),
				Account.ACCOUNTS_PAYABLE,
				Account.ACCOUNTS_PAYABLE_NAME,
				NameId.Type.Party.name(),
				owner.getId(),
				owner.getName(),
				amountField.getUnitvalue(),
				0.0,
				ownersale + ownertax,
				descriptionField.getValue()
				);

		// CR Manager Sales
		post(
				supplier.getId(),
				supplier.getName(),
				Account.SALES_REVENUE,
				Account.SALES_REVENUE_NAME,
				NameId.Type.Product.name(),
				product.getId(),
				product.getName(),
				amountField.getUnitvalue(),
				0.0,
				suppliersale,
				quantity, 
				Unit.DAY,
				descriptionField.getValue()
				);

		// CR Manager VAT
		post(
				supplier.getId(),
				supplier.getName(),
				Account.VAT_OUTPUT,
				Account.VAT_OUTPUT_NAME,
				null,
				null,
				null,
				amountField.getUnitvalue(),
				0.0,
				suppliertax,
				descriptionField.getValue()
				);

		// DR Manager Accounts Receivable from Customer
		post(
				supplier.getId(),
				supplier.getName(),
				Account.ACCOUNTS_RECEIVABLE,
				Account.ACCOUNTS_RECEIVABLE_NAME,
				NameId.Type.Party.name(),
				entityField.getValue(), //Agent if exists, else customer: STO for agent, Quote for Customer
				entityField.getName(),
				amountField.getUnitvalue(),
				ownersale + ownertax + suppliersale + suppliertax,
				0.0,
				descriptionField.getValue()
				);
	}

	/* 
	 * Gets the currency exchange rate label to be displayed when the journal and finance currencies are not the same.
	 *
	 * @return the currency exchange rate label.
	 */
	private String getExchangeLabel() {
		return finance == null ? "" : CONSTANTS.exchangerateLabel() + amountField.getUnitvalue() + " - " + finance.getCurrency();
	}

	/* Initializes the journal panel fields before they are used. */
	private HorizontalPanel createContent() {
		HorizontalPanel panel = new HorizontalPanel();
		VerticalPanel form = new VerticalPanel();
		panel.add(form);

		reversedLabel = new Label(CONSTANTS.reversedLabel());
		reversedLabel.addStyleName(CSS.reversedField());
		form.add(reversedLabel);
		titleField = new Label(CONSTANTS.journalLabel());
		titleField.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		titleField.addStyleName(AbstractField.CSS.cbtAbstractPointer());
		titleField.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (journalTable.noValue()) {
					reversed = !reversed;
					titleField.setText(reversed ? CONSTANTS.reversedNames()[type.ordinal()] : CONSTANTS.processNames()[type.ordinal()]);
				}
			}
		});
		HorizontalPanel titlePanel = new HorizontalPanel();
		titlePanel.add(titleField);
		reportButton = new Image(AbstractField.BUNDLE.printer());
		reportButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				reportView.execute();
			}
		});
		reportButton.setTitle(AbstractField.CONSTANTS.helpPrinter());
		titlePanel.add(reportButton);
		reportLoader = new Image(AbstractField.BUNDLE.loader());
		reportLoader.setVisible(false);
		titlePanel.add(reportLoader);
		form.add(titlePanel);

		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {hide();}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);

		//-----------------------------------------------
		// Process field
		//-----------------------------------------------
		processField = new ListField(this,  null,
				NameId.getList(AbstractField.CONSTANTS.processList(), Event.PROCESSES),
				CONSTANTS.processLabel(),
				true,
				tab++);
		processField.setFieldHalf();
		//form.add(processField);

		//-----------------------------------------------
		// Name field
		//-----------------------------------------------
		eventnameField = new SuggestField(this, null,
				new NameIdAction(Service.JOURNAL),
				CONSTANTS.eventnameLabel(),
				20,
				tab++);
		eventnameField.setFieldHalf();
		eventnameField.setHelpText(CONSTANTS.eventnameHelp());
		form.add(eventnameField);

		//-----------------------------------------------
		// Date field which is the effective date of the transaction
		// It is not a timestamp of when the event was captured
		//-----------------------------------------------
		eventdateField = new DateField(this, null,
				CONSTANTS.eventdateLabel(),
				tab++);
		eventdateField.setHelpText(CONSTANTS.eventdateHelp());
		form.add(eventdateField);

		//-----------------------------------------------
		// Tax Category field
		//-----------------------------------------------
		taxField = new ListField(this, null,
				new NameIdAction(Service.TAX),
				CONSTANTS.taxLabel(),
				false,
				tab++);
		taxField.setType(Tax.Type.SalesTaxIncluded.name());
		taxField.setLabelStyle(CSS.taxLabel());
		taxField.setFieldHalf();
		taxField.setFieldStyle(CSS.taxField());
		taxField.setHelpText(CONSTANTS.taxHelp());
		form.add(taxField);

		//-----------------------------------------------
		// Finance field
		//-----------------------------------------------
		financeField = new ListField(this,  null,
				new NameIdAction(Service.FINANCE),
				CONSTANTS.financeLabel(),
				true,
				tab++);
		financeField.setFieldStyle(CSS.financeField());
		financeField.setVisible(false);
		financeField.setHelpText(CONSTANTS.financeHelp());
		form.add(financeField);

		//-----------------------------------------------
		// Account field
		//-----------------------------------------------
		accountField = new SuggestField(this,  null,
				new NameIdAction(Service.ACCOUNT),
				CONSTANTS.accountLabel(),
				20,
				tab++);
		accountField.setType(Account.Type.Expense.name());
		accountField.setVisible(false);
		accountField.setHelpText(CONSTANTS.accountHelp());
		form.add(accountField);

		//-----------------------------------------------
		// Supplier field
		//-----------------------------------------------
		supplierField = new SuggestField(this, null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.supplierLabel(),
				20,
				tab++);

		Image supplierButton = new Image(AbstractField.BUNDLE.plus());
		supplierButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PartyPopup.getInstance().show(Party.Type.Customer, supplierField, null);
			}
		});
		supplierButton.setTitle(CONSTANTS.partybuttonHelp());
		supplierField.addButton(supplierButton);
		supplierField.setHelpText(CONSTANTS.supplierHelp());
		form.add(supplierField);

		//-----------------------------------------------
		// Party field
		//-----------------------------------------------
		entityField = new SuggestField(this, null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.subaccountLabels()[0],
				20,
				tab++);
		entityField.setHelpText(CONSTANTS.partyHelp());

		entityButton = new Image(AbstractField.BUNDLE.plus());
		entityButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PartyPopup.getInstance().show(partytypes[type.ordinal()], entityField, null);
			}
		});
		entityButton.setTitle(CONSTANTS.partybuttonHelp());
		entityField.addButton(entityButton);
		form.add(entityField);

		//-----------------------------------------------
		// Amount field
		//-----------------------------------------------
		amountField = new DoubleunitField(this, null,
				new CurrencyNameId(),
				CONSTANTS.amountLabel(),
				AbstractField.AF,
				tab++);
		amountField.setHelpText(CONSTANTS.amountHelp());
		form.add(amountField);

		//-----------------------------------------------
		// Debit Amount field
		//-----------------------------------------------
		debitamountField = new DoubleField(this, null,
				CONSTANTS.debitamountLabel(),
				AbstractField.AF,
				tab++);
		debitamountField.setHelpText(CONSTANTS.debitamountHelp());
		debitamountField.setVisible(false);

		//-----------------------------------------------
		// Credit Amount field
		//-----------------------------------------------
		creditamountField = new DoubleField(this, null,
				CONSTANTS.creditamountLabel(),
				AbstractField.AF,
				tab++);
		creditamountField.setVisible(false);
		creditamountField.setHelpText(CONSTANTS.creditamountHelp());

		final HorizontalPanel dc = new HorizontalPanel();
		dc.add(debitamountField);
		dc.add(creditamountField);
		form.add(dc);

		//-----------------------------------------------
		// Description field
		//-----------------------------------------------
		descriptionField = new TextAreaField(this, null,
				CONSTANTS.descriptionLabel(),
				tab++);
		descriptionField.setFieldStyle(CSS.descriptionField());
		descriptionField.setMaxLength(1000);
		descriptionField.setHelpText(CONSTANTS.descriptionHelp());
		form.add(descriptionField);

		//-----------------------------------------------
		// Exchange Rate field
		//-----------------------------------------------
		exchangerateField = new DoubleField(this, null,
				getExchangeLabel(),
				AbstractField.XF,
				tab++);
		exchangerateField.setVisible(false);
		exchangerateField.setHelpText(CONSTANTS.exchangerateHelp());
		form.add(exchangerateField);

		//-----------------------------------------------
		// Logo field
		//-----------------------------------------------
		final Image paygate= new Image(AbstractField.BUNDLE.paygate());
		final Image godaddy= new Image(AbstractField.BUNDLE.godaddy());
		final Image thawte= new Image(AbstractField.BUNDLE.thawte());
		final Image mastercard= new Image(AbstractField.BUNDLE.mastercard());
		final Image visa= new Image(AbstractField.BUNDLE.visa());

		logoImages = new HorizontalPanel();
		logoImages.setTitle(CONSTANTS.logoLabel());
		logoImages.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		logoImages.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		logoImages.add(paygate);
		//logoImages.add(godaddy);
		logoImages.add(thawte);
		logoImages.add(mastercard);
		logoImages.add(visa);
		form.add(logoImages);

		//-----------------------------------------------
		// Card Holder field
		//-----------------------------------------------
		cardholderField = new TextField(this, null,
				CONSTANTS.cardholderLabel(),
				tab++);
		cardholderField.setVisible(false);
		cardholderField.setMaxLength(50);
		cardholderField.setHelpText(CONSTANTS.cardholderHelp());
		form.add(cardholderField);

		//-----------------------------------------------
		// Card Number field
		//-----------------------------------------------
		cardnumberField = new TextField(this,  null,
				CONSTANTS.cardnumberLabel(),
				tab++);
		cardnumberField.setVisible(false);
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
		cardmonthField.setVisible(false);
		carddetailLabel = new HelpLabel(CONSTANTS.carddetailLabel(), CONSTANTS.carddetailHelp(), cardmonthField);
		carddetailLabel.setVisible(false);
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
		cardyearField.setVisible(false);
		cardyearField.setFieldStyle(AbstractField.CSS.cbtListFieldCCyear());

		//-----------------------------------------------
		// Card Security Code field
		//-----------------------------------------------
		cardcodeField = new TextField(this, null,
				null,
				tab++);
		cardcodeField.setVisible(false);
		cardcodeField.setFieldStyle(AbstractField.CSS.cbtTextFieldCCcode());

		HorizontalPanel ccc = new HorizontalPanel();
		ccc.add(cardmonthField);
		ccc.add(cardyearField);
		ccc.add(cardcodeField);
		form.add(ccc);

		form.add(createCommands());

		//-----------------------------------------------
		// Available selection change handler
		//-----------------------------------------------
		final NoSelectionModel<Journal> selectionModel = new NoSelectionModel<Journal>();
		SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				selectedjournal = selectionModel.getLastSelectedObject();
				setValue(selectedjournal);
				processField.setValue(Event.Type.Journal.name());
				setView();
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// Journal table
		//-----------------------------------------------
		journalTable = new TableField<Journal>(this, null, 
				new JournalItemTable(),
				selectionModel,
				CONSTANTS.journaltableLabel(),
				JOURNAL_ROWS, 
				tab++);

		journalTable.setLabelStyle(CSS.journaltableLabel());
		journalTable.setVisible(false);

		int col = 0;

		//-----------------------------------------------
		// Organization column
		//-----------------------------------------------
		Column<Journal, String> organization = new Column<Journal, String>(new TextCell()) {
			@Override
			public String getValue( Journal journal ) {return journal.getOrganizationname();}
		};
		journalTable.addStringColumn(organization, CONSTANTS.journalHeaders()[col++]);

		//-----------------------------------------------
		// Account column
		//-----------------------------------------------
		Column<Journal, String> process = new Column<Journal, String>(new TextCell()) {
			@Override
			public String getValue( Journal journal ) {return journal.getAccountname();}
		};
		journalTable.addStringColumn(process, CONSTANTS.journalHeaders()[col++]);

		//-----------------------------------------------
		// Subaccount column
		//-----------------------------------------------
		Column<Journal, String> entityname = new Column<Journal, String>( new TextCell() ) {
			@Override
			public String getValue( Journal journal ) {return journal.getEntityname();}
		};
		journalTable.addStringColumn(entityname, CONSTANTS.journalHeaders()[col++]);

		//-----------------------------------------------
		// Debit column
		//-----------------------------------------------
		Column<Journal, Number> debitamount = new Column<Journal, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( Journal journal ) {return journal.getDebitamount();}
		};
		journalTable.addNumberColumn( debitamount, CONSTANTS.journalHeaders()[col++]);

		//-----------------------------------------------
		// Credit column
		//-----------------------------------------------
		Column<Journal, Number> creditamount = new Column<Journal, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( Journal journal ) {return journal.getCreditamount();}
		};
		journalTable.addNumberColumn( creditamount, CONSTANTS.journalHeaders()[col++]);

		//-----------------------------------------------
		// Currency column
		//-----------------------------------------------
		Column<Journal, String> currency = new Column<Journal, String>( new TextCell() ) {
			@Override
			public String getValue( Journal journal ) {return journal.getCurrency();}
		};
		journalTable.addStringColumn(currency, CONSTANTS.journalHeaders()[col++]);

		//-----------------------------------------------
		// Description column
		//-----------------------------------------------
		//		Column<Journal, String> description = new Column<Journal, String>( new TextCell() ) {
		//			@Override
		//			public String getValue( Journal journal ) {return journal.getDescription();}
		//		};
		//		journalTable.addStringColumn(description, CONSTANTS.journalHeaders()[col++]);

		panel.add(journalTable);

		onRefresh();
		onReset(Event.CREATED);
		return panel;
	}

	/* Initializes the journal panel commands before they are used. */
	private HorizontalPanel createCommands() {
		final HorizontalPanel bar = new HorizontalPanel();
		bar.addStyleName(AbstractField.CSS.cbtAbstractCommand());

		//-----------------------------------------------
		// Local action to cancel the journal
		//-----------------------------------------------
		LocalRequest cancelRequest = new LocalRequest() {
			protected String popup() {return hasChanged() ? AbstractField.CONSTANTS.errChangeOK() : null;}
			protected void perform() {hide();} //nextFeature();} //TODO: hide();}
		};

		//-----------------------------------------------
		// Local action to create a new line item for the journal
		//-----------------------------------------------
		LocalRequest itemRequest = new LocalRequest() {
			protected boolean error() {
				return (
						ifMessage(AbstractRoot.noOrganizationid(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), processField)
						|| ifMessage(processField.noValue(), Level.ERROR, CONSTANTS.processError(), processField)
						|| ifMessage(eventnameField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errId(), eventnameField)
						|| ifMessage(eventdateField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), eventdateField)
						|| ifMessage(processField.hasValue(Event.Type.Purchase.name()) && accountField.noValue(), Level.ERROR, CONSTANTS.accountError(), accountField)
						|| ifMessage((processField.hasValue(Event.Type.Payment.name()) || processField.hasValue(Event.Type.Receipt.name())) && financeField.noValue(), Level.ERROR, CONSTANTS.financeError(), financeField)
						|| ifMessage(isParty() && entityField.noValue(), Level.ERROR, CONSTANTS.partyError(), entityField)
						|| ifMessage(amountField.noValue(), Level.ERROR, CONSTANTS.amountError(), amountField)
						|| ifMessage((processField.hasValue(Event.Type.Payment.name()) || processField.hasValue(Event.Type.Receipt.name()))
								&& !finance.hasCurrency(amountField.getUnitvalue()) 
								&& (exchangerateField.noValue() || exchangerateField.getValue() <= 0.01) , Level.ERROR, CONSTANTS.exchangerateError(), financeField)
				);
			}

			protected void perform() {
				if (!error()) {
					boolean isJournal = processField.hasValue(Event.Type.Journal.name());
					boolean isPayment = processField.hasValue(Event.Type.Payment.name());
					boolean isPurchase = processField.hasValue(Event.Type.Purchase.name());
					boolean isPurchaseSale = processField.hasValue(Event.Type.PurchaseSale.name());
					boolean isReceipt = processField.hasValue(Event.Type.Receipt.name());
					boolean isSale = processField.hasValue(Event.Type.Sale.name());
					boolean isReservationSale = processField.hasValue(Event.Type.ReservationSale.name());

					if (isPayment) {postPayment(true);}
					else if (isPurchase) {postPurchase(true);}
					else if (isPurchaseSale) {postPurchaseSale();}
					else if (isReceipt) {postPayment(false);}
					else if (isSale) {postPurchase(false);}
					else if (isReservationSale) {postReservationSale();}
					else {postJournal();}
					accountField.setValue(null);
					Double balance = getBalance();
					debitamountField.setValue(balance > 0.0 ? 0.0 : balance);
					creditamountField.setValue(balance < 0.0 ? 0.0 : balance);
					setView();
				}
			}
		};

		//-----------------------------------------------
		// Button to add a new line item to the journal
		//-----------------------------------------------
		itemButton = new CommandButton(this, CONSTANTS.itemLabel(), itemRequest, tab++);
		itemButton.addStyleName(AbstractField.CSS.cbtGradientGreen());
		itemButton.setTitle(CONSTANTS.itemHelp());
		bar.add(itemButton);

		//-----------------------------------------------
		// Button to save the journal to the database
		//-----------------------------------------------
		saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), journalUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.setTitle(CONSTANTS.saveHelp());
		bar.add(saveButton);

		//-----------------------------------------------
		// Button to submit a payment to the payment gateway
		//-----------------------------------------------
		submitButton = new Button(AbstractField.CONSTANTS.allSubmit());
		submitButton.addStyleName(AbstractField.CSS.cbtCommandButton());
		submitButton.addStyleName(AbstractField.CSS.cbtGradientGreen());
		submitButton.setTitle(CONSTANTS.submitHelp());
		submitButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (isCardPayment) {getJsonpPay(Currency.Code.USD.name());}
				else if (isCardReceipt) {getJsonpReceive(Currency.Code.ZAR.name());}
			}
		});
		bar.add(submitButton);

		//-----------------------------------------------
		// Button to delete the journal from the database
		//-----------------------------------------------
		final CommandButton deleteButton = new CommandButton(this, AbstractField.CONSTANTS.allDelete(), journalDelete, tab++);
		deleteButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		deleteButton.setTitle(CONSTANTS.deleteHelp());
		bar.add(deleteButton);

		//-----------------------------------------------
		// Button to cancel the current journal
		//-----------------------------------------------
		cancelButton = new CommandButton(this, AbstractField.CONSTANTS.allCancel(), cancelRequest, tab++);
		cancelButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		cancelButton.setTitle(CONSTANTS.cancelHelp());
		bar.add(cancelButton);

		//-----------------------------------------------
		// Image to show a request to a service is in progress
		//-----------------------------------------------
		loader = new Image(AbstractField.BUNDLE.loader());
		loader.setVisible(false);
		bar.add(loader);

		//-----------------------------------------------
		// Transition array to define this finite state machine.
		//-----------------------------------------------
		fsm = new ArrayList<Transition>();
		fsm.add(new Transition(Event.INITIAL, itemButton, Event.INITIAL));
		fsm.add(new Transition(Event.INITIAL, saveButton, Event.CREATED));
		fsm.add(new Transition(Event.INITIAL, cancelButton, Event.CREATED));

		fsm.add(new Transition(Event.CREATED, itemButton, Event.CREATED));
		fsm.add(new Transition(Event.CREATED, saveButton, Event.CREATED));
		fsm.add(new Transition(Event.CREATED, deleteButton, Event.CREATED));

		return bar;
	}

	/*
	 * Gets the current financial balance of the journal.
	 * 
	 * @return the current financial balance.
	 */
	private Double getBalance() {
		if (journalTable == null || journalTable.noValue()) {return 0.0;}
		Double balance = 0.0;
		for (Journal journal : journalTable.getValue()) {balance += journal.getAmount();}
		return balance;
	}

	/*
	 * Checks if journal has an entity error.
	 * 
	 * @return true if there is an entity error else false
	 */
	private boolean entityError() {
		if (journalTable.hasValue()) {
			for (Journal journal : journalTable.getValue()) {
				if (journal.entityError()) {return true;}
			}
		}
		return false;
	}

	/* Initializes the journal panel actions before they are used. */
	private void createActions() {

		//-----------------------------------------------
		// Remote action to create a new journal instance.
		//-----------------------------------------------
		journalCreate = new GuardedRequest<Event<Journal>>() {
			protected boolean error() {return AbstractRoot.noOrganizationid();}
			protected void send() {super.send(getValue(new JournalCreate()));}
			protected void receive(Event<Journal> event) {setValue(event);}
		};

		//-----------------------------------------------
		// Remote action to read an existing journal instance.
		//-----------------------------------------------
		journalRead = new GuardedRequest<Event<Journal>>() {
			protected boolean error() {return eventnameField.noValue();}
			protected void send() {super.send(getValue(new JournalRead()));}
			protected void receive(Event<Journal> event){setValue(event);}
		};

		//-----------------------------------------------
		// Remote action to update an existing journal instance.
		//-----------------------------------------------
		journalUpdate = new GuardedRequest<Event<Journal>>() {
			protected boolean error() {
				return (
						ifMessage(AbstractRoot.noOrganizationid(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), eventnameField)
						|| ifMessage(AbstractRoot.noActorid(), Level.ERROR, AbstractField.CONSTANTS.errActorid(), eventnameField)
						|| ifMessage(processField.noValue(), Level.ERROR, CONSTANTS.processError(), processField)
						|| ifMessage(eventnameField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errId(), eventnameField)
						|| ifMessage(journalTable.noValue(), Level.ERROR, CONSTANTS.journaltableError(), processField)
						|| ifMessage(financeField.isVisible() && financeField.hasValue(Finance.CBT_ZAR_FINANCE), Level.ERROR, CONSTANTS.gatewaynoZARError(), financeField)
						|| ifMessage(entityError(), Level.ERROR, CONSTANTS.entityError(), eventnameField)
						|| ifMessage(Math.abs(getBalance()) >= 0.01, Level.ERROR, CONSTANTS.balancedError() + AbstractField.AF.format(getBalance()), journalTable)
				);
			}
			protected void send() {super.send(getValue(new JournalUpdate()));}
			protected void receive(Event<Journal> event) {nextPrice();}
		};


		//-----------------------------------------------
		// Remote action to save an existing journal instance without checking its values.
		//-----------------------------------------------
		journalSave = new GuardedRequest<Event<Journal>>() {
			protected boolean error() {return false;}
			protected void send() {super.send(getValue(new JournalUpdate()));}
			protected void receive(Event<Journal> event) {
				if (parentField != null) {parentField.execute(true);}
			}
		};

		//-----------------------------------------------
		// Remote action to delete an existing journal instance from the database.
		//-----------------------------------------------
		journalDelete = new GuardedRequest<Event<Journal>>() {
			protected String popup() {return AbstractField.CONSTANTS.errDeleteOK();}
			protected boolean error() {return (
					ifMessage(eventnameField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errId(), eventnameField)
					|| ifMessage(isGateway(), Level.ERROR, CONSTANTS.gatewayError(), eventnameField)
					);}
			protected void send() {super.send(getValue(new JournalDelete()));}
			protected void receive(Event<Journal> event){
				if (parentField != null) {parentField.execute(true);}
				hide();
			}
		};

		//-----------------------------------------------
		// Remote action to read an existing bank or cash account (finance) instance from the database.
		//-----------------------------------------------
		financeRead = new GuardedRequest<Finance>() {
			protected boolean error() {return financeField.noValue();}
			protected void send() {super.send(getValue(new FinanceRead()));}
			protected void receive(Finance finance){setValue(finance);}
		};

		//-----------------------------------------------
		// Remote action to check if party is now licensed
		//-----------------------------------------------
		organizationLicense = new GuardedRequest<Party>() {
			protected boolean error() {return AbstractRoot.noOrganizationid();}
			protected void send() {super.send(getValue(new PartyLicense()));}
			protected void receive(Party party){}
		};

		//-----------------------------------------------
		// Remote action to read the session organization instance from the database.
		//-----------------------------------------------
		organizationRead = new GuardedRequest<Party>() {
			protected boolean error() {return AbstractRoot.noOrganizationid();}
			protected void send() {super.send(getValue(new PartyRead()));}
			protected void receive(Party party){setValue(party);}
		};

		//-----------------------------------------------
		// Remote action to create a printable report for this journal.
		//-----------------------------------------------
		reportView = new GuardedRequest<Report>() {
			protected boolean error() {return (getReport() == null || getReport().noDesign() || getReport().noNameRange());}
			protected void send() {
				reportButton.setVisible(false);
				reportLoader.setVisible(true);
				super.send(getReport());
			}
			public void receive(Report report) {
				reportLoader.setVisible(false);
				reportButton.setVisible(true);
				ReportPopup.getInstance().show(report);
			}
		};
		
		//-----------------------------------------------
		// Read Guest Comments
		//-----------------------------------------------
		taxTable = new GuardedRequest<Table<Tax>> () {
			protected boolean error() {return false;}
			protected void send() {super.send(getValue(new TaxTable()));}
			protected void receive(Table<Tax> response) {taxes = response.getValue();
//			Window.alert("taxes " + taxes);
			}
		};
	}

	/*
	 * Sends a JSONP payment request to the payment gateway.
	 * 
	 * @param tocurrency the currency accepted by the payment gateway.
	 */
	private void getJsonpPay(String tocurrency) {

		if (ifMessage(AbstractRoot.noOrganizationid(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), processField)
				|| ifMessage(AbstractRoot.noActorid(), Level.ERROR, AbstractField.CONSTANTS.errActorid(), processField)
				|| ifMessage(processField.noValue(), Level.ERROR, CONSTANTS.processError(), processField)
				|| ifMessage(eventnameField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errId(), eventnameField)
				|| ifMessage(!entityField.hasValue(Party.CBT_LTD_PARTY), Level.ERROR, CONSTANTS.gatewayCBTError(), entityField)
				|| ifMessage(financeField.noValue(), Level.ERROR, CONSTANTS.financeError(), financeField)
				|| ifMessage(amountField.noValue() || amountField.getValue() <= 0.0, Level.ERROR, CONSTANTS.amountError(), amountField)
				|| ifMessage(cardholderField.noValue(), Level.ERROR, CONSTANTS.cardholderError(), cardholderField)
				|| ifMessage(cardnumberField.noValue(), Level.ERROR, CONSTANTS.cardnumberError(), cardnumberField)
				|| ifMessage(!Finance.isCreditCardNumber(cardnumberField.getValue()), Level.ERROR, CONSTANTS.cardnumberError(), cardnumberField)
				|| ifMessage(cardmonthField.noValue(), Level.ERROR, CONSTANTS.cardmonthError(), cardmonthField)
				|| ifMessage(cardyearField.noValue(), Level.ERROR, CONSTANTS.cardyearError(), cardyearField)
				|| ifMessage(cardcodeField.noValue(), Level.ERROR, CONSTANTS.cardcodeError(), cardcodeField)) {return;}

		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		String url = HOSTS.jsonUrl()
				+ "?service=" + JSONRequest.PAY
				+ "&pos=" + Window.Location.getParameter(AbstractRoot.Type.pos.name()) 
				+ "&organizationid=" + AbstractRoot.getOrganizationid()
				+ "&financeid=" + financeField.getValue()
				+ "&eventname=" + eventnameField.getValue()
				+ "&amount=" + amountField.getValue()
				+ "&currency=" + amountField.getUnitvalue()
				+ "&tocurrency=" + tocurrency
				+ "&emailaddress=" + AbstractRoot.getSession().getEmailaddress()
				+ "&cardholder=" + cardholderField.getValue()
				+ "&cardnumber=" + cardnumberField.getValue()
				+ "&cardmonth=" + cardmonthField.getValue()
				+ "&cardyear=" + cardyearField.getValue()
				+ "&cardcode=" + cardcodeField.getValue()
				;

		loader.setVisible(true);
		jsonp.requestObject(url, new AsyncCallback<PayWidgetItem>() {

			@Override
			public void onFailure(Throwable caught) {
				LoadingPopup.getInstance().hide();
				loader.setVisible(false);
				throw new RuntimeException("getJsonpPay:" + caught.getMessage());
			}

			@Override
			public void onSuccess(PayWidgetItem response) {
				loader.setVisible(false);
				message = response.getMessage();
				if (response.getState().equalsIgnoreCase(RazorWidget.State.SUCCESS.name())) {
					finance.setCurrency(amountField.getUnitvalue());
					finance.setOwnerid(AbstractRoot.getOrganizationid());
					Double amount = Event.round(amountField.getValue() / response.getAmount(), 2);
					exchangerateField.setValue(amountField.getValue() / amount);
					amountField.setValue(amount);
					amountField.setUnitvalue(response.getCurrency());
					postPayment(true);
					state = Event.CREATED;
					journalSave.execute();
					setView();
					submitButton.setVisible(false);
					cancelButton.setVisible(false);
				}
				else {AbstractField.addMessage(Level.ERROR, message, cardholderField);}
			}
		});
	}

	/*
	 * Sends a JSONP request to the payment gateway for a payment to be made to the licensor.
	 * 
	 * @param tocurrency the currency accepted by the payment gateway.
	 */
	private void getJsonpReceive(String tocurrency) {

		if (ifMessage(AbstractRoot.noOrganizationid(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), processField)
				|| ifMessage(AbstractRoot.noActorid(), Level.ERROR, AbstractField.CONSTANTS.errActorid(), processField)
				|| ifMessage(processField.noValue(), Level.ERROR, CONSTANTS.processError(), processField)
				|| ifMessage(eventnameField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errId(), eventnameField)
				|| ifMessage(!financeField.hasValue(Finance.CBT_ZAR_FINANCE), Level.ERROR, CONSTANTS.gatewayZARError(), financeField)
				|| ifMessage(amountField.noValue() || amountField.getValue() <= 0.0, Level.ERROR, CONSTANTS.amountError(), amountField)
				|| ifMessage(cardholderField.noValue(), Level.ERROR, CONSTANTS.cardholderError(), cardholderField)
				|| ifMessage(cardnumberField.noValue(), Level.ERROR, CONSTANTS.cardnumberError(), cardnumberField)
				|| ifMessage(!Finance.isCreditCardNumber(cardnumberField.getValue()), Level.ERROR, CONSTANTS.cardnumberError(), cardnumberField)
				|| ifMessage(cardmonthField.noValue(), Level.ERROR, CONSTANTS.cardmonthError(), cardmonthField)
				|| ifMessage(cardyearField.noValue(), Level.ERROR, CONSTANTS.cardyearError(), cardyearField)
				|| ifMessage(cardcodeField.noValue(), Level.ERROR, CONSTANTS.cardcodeError(), cardcodeField)) {return;}

		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		String url = HOSTS.jsonUrl()
				+ "?service=" + JSONRequest.RECEIVE
				+ "&pos=" + Window.Location.getParameter(AbstractRoot.Type.pos.name()) 
				+ "&organizationid=" + AbstractRoot.getOrganizationid()
				+ "&financeid=" + financeField.getValue()
				+ "&eventname=" + eventnameField.getValue()
				+ "&amount=" + amountField.getValue()
				+ "&currency=" + amountField.getUnitvalue()
				+ "&tocurrency=" + tocurrency
				+ "&emailaddress=" + AbstractRoot.getSession().getEmailaddress()
				+ "&cardholder=" + cardholderField.getValue()
				+ "&cardnumber=" + cardnumberField.getValue()
				+ "&cardmonth=" + cardmonthField.getValue()
				+ "&cardyear=" + cardyearField.getValue()
				+ "&cardcode=" + cardcodeField.getValue()
				;

		loader.setVisible(true);
		jsonp.requestObject(url, new AsyncCallback<PayWidgetItem>() {

			@Override
			public void onFailure(Throwable caught) {
				loader.setVisible(false);
				LoadingPopup.getInstance().hide();
				throw new RuntimeException("getJsonpReceive:" + caught.getMessage());
			}

			@Override
			public void onSuccess(PayWidgetItem response) {
				loader.setVisible(false);
				message = response.getMessage();
				if (response.hasState(RazorWidget.State.SUCCESS.name())) {
					finance.setOwnerid(AbstractRoot.getOrganizationid());
					postCardReceipt();
					state = Event.CREATED; //TODO: FROZEN
					journalSave.execute();
					setView();
					submitButton.setVisible(false);
					cancelButton.setVisible(false);
				}
				else {AbstractField.addMessage(Level.ERROR, message, cardholderField);}
			}
		});
	}

	/* Gets if the journal has a line item for the payment gateway. */
	private static boolean isGateway() {
		for (Journal journal : journalTable.getValue()) {
			if (journal.isGateway()) {return true;}
		}
		return false;
	}
}