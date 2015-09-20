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
import net.cbtltd.client.LocalRequest;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.CheckField;
import net.cbtltd.client.field.CommandButton;
import net.cbtltd.client.field.DateField;
import net.cbtltd.client.field.DatespanField;
import net.cbtltd.client.field.DoubleField;
import net.cbtltd.client.field.GridField;
import net.cbtltd.client.field.IntegerField;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.MessagePopup;
import net.cbtltd.client.field.MoneyField;
import net.cbtltd.client.field.ReportButton;
import net.cbtltd.client.field.SpinnerField;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.field.TimespanField;
import net.cbtltd.client.field.table.ActionCell;
import net.cbtltd.client.field.table.ActionCell.Delegate;
import net.cbtltd.client.field.table.ActionHeader;
import net.cbtltd.client.field.table.TableError;
import net.cbtltd.client.field.table.TableExecutor;
import net.cbtltd.client.panel.JournalPopup;
import net.cbtltd.client.panel.PartyPopup;
import net.cbtltd.client.panel.QuotePopup;
import net.cbtltd.client.panel.TaskPopup;
import net.cbtltd.client.panel.ValuePopup;
import net.cbtltd.client.resource.reservation.ReservationBundle;
import net.cbtltd.client.resource.reservation.ReservationConstants;
import net.cbtltd.client.resource.reservation.ReservationCssResource;
import net.cbtltd.shared.Account;
import net.cbtltd.shared.Alert;
import net.cbtltd.shared.Design;
import net.cbtltd.shared.DoubleResponse;
import net.cbtltd.shared.Event;
import net.cbtltd.shared.Finance;
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
import net.cbtltd.shared.Task;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.Value;
import net.cbtltd.shared.api.HasResetId;
import net.cbtltd.shared.journal.EventJournal;
import net.cbtltd.shared.journal.JournalTaskUpdate;
import net.cbtltd.shared.party.EmployeeNameId;
import net.cbtltd.shared.reservation.PriceResponse;
import net.cbtltd.shared.reservation.ReservationCreate;
import net.cbtltd.shared.reservation.ReservationEntities;
import net.cbtltd.shared.reservation.ReservationEventJournalBalance;
import net.cbtltd.shared.reservation.ReservationEventJournalTable;
import net.cbtltd.shared.reservation.ReservationHistory;
import net.cbtltd.shared.reservation.ReservationPrice;
import net.cbtltd.shared.reservation.ReservationPriceAdjust;
import net.cbtltd.shared.reservation.ReservationRead;
import net.cbtltd.shared.reservation.ReservationUndo;
import net.cbtltd.shared.reservation.ReservationUpdate;
import net.cbtltd.shared.reservation.ServicepriceRequest;
import net.cbtltd.shared.reservation.ServicepriceResponse;
import net.cbtltd.shared.task.MaintenanceTaskTable;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

/** The Class ReservationForm is to display and change information about a reservation. */
public class ReservationForm 
extends AbstractForm<Reservation> {

	private static final ReservationConstants CONSTANTS = GWT.create(ReservationConstants.class);
	private static final ReservationBundle BUNDLE = ReservationBundle.INSTANCE;
	private static final ReservationCssResource CSS = BUNDLE.css();

	private static final int EVENTJOURNAL_ROWS = 200;
	private static final int FEATURE_ROWS = 16;
	private static final int MAINTENANCE_ROWS = 16;

	private static GuardedRequest<Reservation> reservationCreate;
	private static GuardedRequest<Reservation> reservationReset;
	private static GuardedRequest<Reservation> reservationRead;
	private static GuardedRequest<Reservation> reservationUpdate;
	private static GuardedRequest<Reservation> reservationUndo;
	private static GuardedRequest<Reservation> reservationCancel;
	private static GuardedRequest<Reservation> reservationClose;
	private static GuardedRequest<Table<Reservation>> reservationHistory;
	private static GuardedRequest<DoubleResponse> reservationBalance;
	private static GuardedRequest<PriceResponse> reservationPrice;
	private static GuardedRequest<PriceResponse> reservationPriceAdjust;
	private static GuardedRequest<ServicepriceResponse> servicepriceRead;
	private static GuardedRequest<ReservationEntities> reservationentitiesRead;
	private static GuardedRequest<Task> taskUpdate;
	
	private static SuggestField reservationField;
	private static TextField idField;
	private static TextField altidField;
	private static TextField stateField;
	private static DateField dateField;
	private static DateField duedateField;
	private static DatespanField fromtodateField;
	private static TimespanField fromtotimeField;
	private static TimespanField servicetimesField;
	private static SuggestField customerField;
	private static SuggestField agentField;
	private static SuggestField marketField;
	private static SuggestField outcomeField;
	private static SuggestField actorField;
	private static SuggestField productField;
	private static TextField flatField;
	private static MoneyField quoteField;
	private static DoubleField costField;
	private static DoubleField extraField;
	private static DoubleField discountField;
	private static DoubleField depositField;
	private static DoubleField balanceField;
	private static SpinnerField adultField;
	private static SpinnerField childField;
	private static SpinnerField infantField;
	private static SpinnerField quantityField;

	private static StackLayoutPanel stackPanel;
	private static TextAreaField reservationtextField;
	private static TextAreaField historytextField;
	private static TextAreaField customertextField;

	private static SuggestField serviceproviderField;
	private static SuggestField servicepayerField;
	private static TableField<Price> featureTable;
	private static GridField<Task> serviceGrid;
	private static TableField<Task> maintenanceTable;
	private static TableField<EventJournal> eventjournalTable;

	private static CheckField cardcheckField;
	private static TextField cardholderField;
	private static TextField cardnumberField;
	private static ListField cardmonthField;
	private static ListField cardyearField;
	private static TextField cardcodeField;
	private static ReservationEntities reservationentities;

	private static Transition oldTransition;
	private static Transition dueTransition;
	private static CommandButton oldButton;
	private static CommandButton dueButton;
	
	private boolean noprice = false;
	private String altpartyid = null;
	private ArrayList<NameId> collisions;
	private boolean noCollisions() {return collisions == null || collisions.isEmpty();}
	private boolean hasCollisions() {return !noCollisions();}
	private String getCollisionlist() {return getCollisionlist(collisions);}
	private static String getCollisionlist(ArrayList<NameId> collisions) {return NameId.trim(NameId.getCdlist(NameId.getNameList(collisions)), 36);}
	
	private static String getAlertlist(ArrayList<Alert> alerts) {
		if (alerts == null || alerts.isEmpty()) {return null;}
		StringBuilder sb =  new StringBuilder();
		for (Alert alert : alerts) {sb.append(AbstractRoot.getDF().format(alert.getFromdate())).append(" - ").append(AbstractRoot.getDF().format(alert.getTodate())).append(" ").append(alert.getName()).append("\n");}
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#permission()
	 */
	@Override
	public short[] permission(){return AccessControl.RESERVATION_PERMISSION;}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#getValue()
	 */
	@Override
	public Reservation getValue() {return getValue(new Reservation());}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {
		if (reservationField.sent(change)) {reservationRead.execute();}
		else if (customerField.sent(change)) {
			reservationHistory.execute();
			customertextField.setText(Reservation.getCustomerText(AbstractRoot.getOrganizationid(), customerField.getValue(), AbstractRoot.getLanguage()));
		}
		else if (agentField.sent(change)) {reservationPrice.execute();}
		else if (quoteField.sent(change)) {reservationPriceAdjust.execute();}
		else if (fromtodateField.sent(change) || agentField.sent(change) || productField.sent(change)) {
			if (stateField.hasValue(Reservation.State.Initial.name()) 
					|| stateField.hasValue(Reservation.State.Provisional.name())
			) {reservationPrice.execute();} 
			else {MessagePopup.getInstance().showYesNo(CONSTANTS.pricechangeError(), reservationPrice);}
		}
		else if (cardcheckField.sent(change)) {
			boolean visible = cardcheckField.getValue();
			cardholderField.setVisible(visible);
			cardnumberField.setVisible(visible);
			cardmonthField.setVisible(visible);
			cardyearField.setVisible(visible);
			cardcodeField.setVisible(visible);
		}
		if (quantityField.sent(change)) {reservationPrice.execute();}
		if (productField.sent(change)) {reservationentitiesRead.execute();}
		//if (fromtodateField.sent(change)) {setServiceTasks();}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onReset(net.cbtltd.shared.api.HasNameId)
	 */
	@Override
	public void onReset(HasResetId reset) {
		Log.debug("onReset read " + reset);
		if (reset != null && reset.getResetid() != null && !reset.getResetid().isEmpty()) {
			reservationField.setValue(reset.getResetid());
			reservationRead.execute();
		}
		else if (reset instanceof Reservation) {
			setValue((Reservation) reset);
			reservationReset.execute();
		}
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
		final HorizontalPanel panel = new HorizontalPanel();
		panel.addStyleName(AbstractField.CSS.cbtAbstractWidth());
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		scroll.add(panel);

		final HorizontalPanel content = new HorizontalPanel();
		panel.add(content);

		createActions();

		content.add(createContent());
		stackPanel = new StackLayoutPanel(com.google.gwt.dom.client.Style.Unit.EM);
		content.add(stackPanel);

		stackPanel.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				if (event.getSelectedItem() != 0 && reservationField.noValue()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.reservationError(), reservationField);}
				refreshStackPanel();
			}
		});

		stackPanel.addStyleName(CSS.stackStyle());
		stackPanel.add(createHospitality(), CONSTANTS.hospitalityLabel(), 1.5);
		stackPanel.add(createFeature(), CONSTANTS.featureLabel(), 1.5);
		stackPanel.add(createMaintenance(), CONSTANTS.maintenanceLabel(), 1.5);
		stackPanel.add(createFinancial(), CONSTANTS.financeLabel(), 1.5);
		createService();
//		stackPanel.add(createService(), CONSTANTS.serviceLabel(), 1.5);
		onRefresh();
		onReset(Reservation.State.Provisional.name());
	}

	/* Refreshes the open stack panel */
	private void refreshStackPanel() {
		switch (stackPanel.getVisibleIndex()) {
		case 0: reservationHistory.execute(); break;
//		case 1: featureTable.execute(true); break;
		case 2: maintenanceTable.execute(); break;
		case 3: eventjournalTable.execute(); break;
		}
	}

	/*
	 * Checks if the reservation has multiple units.
	 * 
	 *  @return true, if the reservation has multiple units.
	 */
//	private boolean hasMultipleUnits() {
//		return (reservationentities != null
//				&& reservationentities.getProduct() != null
//				&& reservationentities.getProduct().getQuantity() > 1
//				&& !stateField.hasValue(Reservation.State.Initial.name()) 
//				&& !stateField.hasValue(Reservation.State.Provisional.name()) 
//				&& !stateField.hasValue(Reservation.State.Closed.name()) 
//				&& !stateField.hasValue(Reservation.State.Cancelled.name()));
//	}

	/*
	 * Checks if the reservation has service tasks.
	 * 
	 *  @return true, if the reservation has service tasks.
	 */
	private boolean hasServiceTasks() {
		return (reservationentities != null
				&& reservationentities.getProduct() != null
				&& reservationentities.getProduct().hasServicedays()
				&& !stateField.hasValue(Reservation.State.Initial.name()) 
				&& !stateField.hasValue(Reservation.State.Provisional.name()) 
				&& !stateField.hasValue(Reservation.State.Closed.name()) 
				&& !stateField.hasValue(Reservation.State.Cancelled.name()));
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#getValue(net.cbtltd.shared.api.HasState)
	 */
	private Reservation getValue(Reservation reservation) {
		reservation.setState(state);
		reservation.setOldstate(oldstate);
		// This is a hack for off line reservations which stay in initial state until accepted by the organization
		/*if (reservation.hadState(Reservation.State.Initial.name())
				&& reservationentities != null
				&& reservationentities.noRank()
				&& !AbstractRoot.hasPermission(AccessControl.ADMINISTRATOR)) {
			reservation.setState(Reservation.State.Initial.name());			
		}*/
		reservation.setId(reservationField.getValue());
		reservation.setAltpartyid(altpartyid);
		reservation.setAltid(altidField.getValue());
		if (reservation.noOrganizationid()) {reservation.setOrganizationid(AbstractRoot.getOrganizationid());}
		reservation.setActorid(actorField.getValue());
		reservation.setUnit(Unit.DAY);
		reservation.setCustomerid(customerField.getValue());
		if (agentField.noValue()){
		    reservation.setAgentid(AbstractRoot.getOrganizationid());
		}else{
		    reservation.setAgentid(agentField.getValue());
		}
		reservation.setServiceid(serviceproviderField.getValue());
		reservation.setMarket(marketField.getValue());
		reservation.setOutcome(outcomeField.getValue());
		reservation.setServicepayer(servicepayerField.getValue());
		reservation.setDate(Time.getDateServer(dateField.getValue()));
		reservation.setDuedate(Time.getDateServer(duedateField.getValue()));
		reservation.setFromdate(Time.getDateServer(fromtodateField.getValue()));
		reservation.setTodate(Time.getDateServer(fromtodateField.getTovalue()));
		reservation.setArrivaltime(fromtotimeField.getValue());
		reservation.setDeparturetime(fromtotimeField.getTovalue());
		reservation.setServicefrom(servicetimesField.getValue());
		reservation.setServiceto(servicetimesField.getTovalue());
		reservation.setAdult(adultField.getValue());
		reservation.setChild(childField.getValue());
		reservation.setInfant(infantField.getValue());
		
		reservation.setCardcode(cardcodeField.getValue());
		reservation.setCardholder(cardholderField.getValue());
		reservation.setCardmonth(cardmonthField.getValue());
		reservation.setCardnumber(cardnumberField.getValue());
		reservation.setCardyear(cardyearField.getValue());

		reservation.setProcess(NameId.Type.Reservation.name());
		reservation.setProductid(productField.getValue());
		reservation.setQuantity(quantityField.getValue());
		reservation.setFlat(flatField.getValue());
		reservation.setCustomerText(customertextField.getText());
		reservation.setNotes(reservationtextField.getValue());
		reservation.setUnit(Unit.DAY); //unitField.getValue());
		reservation.setQuote(quoteField.getValue());
		reservation.setCurrency(quoteField.getUnitvalue());
		reservation.setCost(costField.getValue());
		reservation.setExtra(extraField.getValue());
		reservation.setDeposit(depositField.getValue());
		reservation.setTasks(serviceGrid.getValue());
		reservation.setQuotedetail(featureTable.getValue());
		
		Log.debug("getValue " + reservation);
		return reservation;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#setValue(net.cbtltd.shared.api.HasState)
	 */
	public void setValue(Reservation reservation) {
		noprice = false;
		collisions = null;
		if (reservation == null) {onReset(Reservation.State.Provisional.name());}
		else if (reservation.hasCollisions()) {
			collisions = reservation.getCollisions();
			AbstractField.addMessage(Level.TERSE, CONSTANTS.collisionError() + getCollisionlist(), fromtodateField);
		}
		else if (reservation.hasMessage()) {
			AbstractField.addMessage(Level.ERROR, reservation.getMessage(), fromtodateField);
		}
		else {
			setResetting(true);
			onStateChange(reservation.getState());
			stateField.setValue(reservation.getState());
//			if (AbstractRoot.noOrganizationid()) {AbstractRoot.setOrganizationid(reservation.getOrganizationid());}
			reservationField.setValue(reservation.getId());
			reservationField.setEnabled(!reservation.hasState(Reservation.State.Initial.name()));
			idField.setText(reservation.getId());
			altpartyid = reservation.getAltpartyid();
			altidField.setText(reservation.getAltid());
			dateField.setValue(Time.getDateClient(reservation.getDate()));
			duedateField.setValue(Time.getDateClient(reservation.getDuedate()));
			fromtodateField.setValue(Time.getDateClient(reservation.getFromdate()));
			fromtodateField.setTovalue(Time.getDateClient(reservation.getTodate()));
			fromtotimeField.setValue(reservation.getArrivaltime());
			fromtotimeField.setTovalue(reservation.getDeparturetime());
			servicetimesField.setValue(reservation.getServicefrom());
			servicetimesField.setTovalue(reservation.getServiceto());
			customerField.setValue(reservation.getCustomerid());
			agentField.setValue(reservation.getAgentid());
			actorField.setValue(reservation.getActorid());
			marketField.setValue(reservation.getMarket());
			outcomeField.setValue(reservation.getOutcome());
			serviceproviderField.setValue(reservation.getServiceid());
			servicepayerField.setValue(reservation.getServicepayer());
			productField.setValue(reservation.getProductid());
			quantityField.setValue(reservation.getQuantity());
			flatField.setValue(reservation.getFlat());
			reservationtextField.setValue(reservation.getNotes());
			quoteField.setValue(reservation.getQuote());
			quoteField.setUnitvalue(reservation.getCurrency());
			costField.setValue(reservation.getCost());
			extraField.setValue(reservation.getExtra());
			discountField.setValue(reservation.getDiscount());
			depositField.setValue(reservation.getDeposit());
			adultField.setValue(reservation.getAdult());
			childField.setValue(reservation.getChild());
			infantField.setValue(reservation.getInfant());
			
			cardcodeField.setValue(reservation.getCardcode());
			cardholderField.setValue(reservation.getCardholder());
			cardmonthField.setValue(reservation.getCardmonth());
			cardnumberField.setValue(reservation.getCardnumber());
			cardyearField.setValue(reservation.getCardyear());

			if (reservation.noCustomerid()) {
				customertextField.setValue(Model.BLANK);
				historytextField.setValue(Model.BLANK);
			}
			else {
				customertextField.setText(Reservation.getCustomerText(AbstractRoot.getOrganizationid(), customerField.getValue(), AbstractRoot.getLanguage()));
				reservationHistory.execute();
			}
//Window.alert("Reservation " + reservation.getQuotedetail());
			featureTable.setValue(reservation.getQuotedetail());
			
			serviceGrid.setValue(reservation.getTasks());

			oldTransition.setFromState(reservation.getState());
			oldTransition.setToState(reservation.getOldstate());
			oldTransition.setText(getStateLabel(reservation.getOldstate(), Reservation.State.Provisional));

			dueTransition.setFromState(reservation.getState());
			dueTransition.setToState(reservation.getDuestate());
			dueTransition.setText(getStateLabel(reservation.getDuestate(), Reservation.State.Confirmed));

			oldButton.setEnabled(!hasState(Reservation.State.Initial.name()) && !hasState(Reservation.State.Provisional.name()));
			dueButton.setEnabled(!hasState(Reservation.State.Initial.name()) && !hasState(Reservation.State.Departed.name()));

			refreshStackPanel();
			reservationentitiesRead.execute(true);
//			setServiceTasks();
			setResetting(false);
		}
	}

	/*
	 * Gets the substitute text for the specified state.
	 * 
	 * @param the specified state.
	 * @param the state substitute.
	 * @return the substitute text.
	 */
	private String getStateLabel(String state, Reservation.State substitute) {
		int index = (state == null || state.isEmpty()) ? substitute.ordinal() : Reservation.State.valueOf(state).ordinal();
		return CONSTANTS.reservationStates()[index];
	}

	/*
	 * Gets the specified reservation entities action with its attribute values set.
	 *
	 * @param action the specified reservation entities action.
	 * @return the reservation entities action with its attribute values set.
	 */
	private ReservationEntities getValue(ReservationEntities action) {
		action.setReservation(getValue());
		return action;
	}

	/* 
	 * Creates the primary panel.
	 * 
	 * @return the primary panel.
	 */
	private VerticalPanel createContent() {
		final VerticalPanel form = new VerticalPanel();
		form.addStyleName(AbstractField.CSS.cbtAbstractControl());
		final Label title = new Label(CONSTANTS.reservationLabel());
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);

		//-----------------------------------------------
		// Reservation number field
		//-----------------------------------------------
		reservationField = new SuggestField(this, null,
				new NameIdAction(Service.RESERVATION),
				CONSTANTS.reservationnameLabel(),
				20,
				tab++);
		reservationField.setFieldHalf();
		reservationField.setHelpText(CONSTANTS.reservationHelp());

		//-----------------------------------------------
		// Reservation ID field
		//-----------------------------------------------
		idField = new TextField(this, null,
				CONSTANTS.idLabel(),
				tab++);
		idField.setEnabled(false);
		idField.setVisible(AbstractRoot.hasPermission(AccessControl.PARTNER_ROLES));
		idField.setFieldHalf();
		idField.setHelpText(CONSTANTS.idHelp());
		
		HorizontalPanel ri = new HorizontalPanel();
		ri.add(reservationField);
		ri.add(idField);
		form.add(ri);

		//-----------------------------------------------
		// Date is when the reservation was created
		//-----------------------------------------------
		dateField = new DateField(this, null,
				CONSTANTS.dateLabel(),
				tab++);
		dateField.setEnabled(false);
		dateField.setHelpText(CONSTANTS.dateHelp());
//		form.add(dateField);

		//-----------------------------------------------
		// Reservation AltID field
		//-----------------------------------------------
		altidField = new TextField(this, null,
				CONSTANTS.altidLabel(),
				tab++);
		altidField.setEnabled(false);
		altidField.setVisible(AbstractRoot.hasPermission(AccessControl.PARTNER_ROLES));
		altidField.setFieldHalf();
		altidField.setHelpText(CONSTANTS.altidHelp());
		
		HorizontalPanel da = new HorizontalPanel();
		da.add(dateField);
		da.add(altidField);
		form.add(da);

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

		final Image customerButton = new Image(AbstractField.BUNDLE.plus());
		customerButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PartyPopup.getInstance().show(Party.Type.Customer, customerField, null);
			}
		});
		customerButton.setTitle(CONSTANTS.customerbuttonHelp());
		customerField.addButton(customerButton);
		form.add(customerField);

		//-----------------------------------------------
		// Agent field
		//-----------------------------------------------
		agentField = new SuggestField(this,  null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.agentLabel(),
				20,
				tab++);
		agentField.setType(Party.Type.Agent.name());
		agentField.setHelpText(CONSTANTS.agentHelp());
		
		final Image agentButton = new Image(AbstractField.BUNDLE.plus());
		agentButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PartyPopup.getInstance().show(Party.Type.Agent, agentField, null);
			}
		});
		agentButton.setTitle(CONSTANTS.agentbuttonHelp());
		agentField.addButton(agentButton);
		form.add(agentField);

		//-----------------------------------------------
		// Initial (entry) field
		//-----------------------------------------------
		marketField = new SuggestField(this, null,
				new NameIdAction(Service.ATTRIBUTE),
				CONSTANTS.marketLabel(),
				20,
				tab++);
		marketField.setType(Value.Type.MarketingStarter.name());
		marketField.setState(NameId.Type.Reservation.name() + AbstractRoot.getOrganizationid());
		marketField.setHelpText(CONSTANTS.marketHelp());

		final Image starterButton = new Image(AbstractField.BUNDLE.plus());
		starterButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ValuePopup.getInstance().show(Value.Type.MarketingStarter, CONSTANTS.marketLabel(), NameId.Type.Reservation.name() + AbstractRoot.getOrganizationid(), marketField);
			}
		});
		starterButton.setTitle(CONSTANTS.marketbuttonHelp());
		marketField.addButton(starterButton);
		form.add(marketField);

		//-----------------------------------------------
		// Outcome field
		//-----------------------------------------------
		outcomeField = new SuggestField(this, null,
				new NameIdAction(Service.ATTRIBUTE),
				CONSTANTS.outcomeLabel(),
				20,
				tab++);
		outcomeField.setType(Value.Type.MarketingOutcome.name());
		outcomeField.setState(NameId.Type.Reservation.name() + AbstractRoot.getOrganizationid());
		outcomeField.setHelpText(CONSTANTS.outcomeHelp());

		final Image outcomeButton = new Image(AbstractField.BUNDLE.plus());
		outcomeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ValuePopup.getInstance().show(Value.Type.MarketingOutcome, CONSTANTS.outcomeLabel(), NameId.Type.Reservation.name() + AbstractRoot.getOrganizationid(), outcomeField);
			}
		});
		outcomeButton.setTitle(CONSTANTS.outcomebuttonHelp());
		outcomeField.addButton(outcomeButton);
		form.add(outcomeField);

		//-----------------------------------------------
		// Actor field
		//-----------------------------------------------
		actorField = new SuggestField(this, null,
				new EmployeeNameId(),
				CONSTANTS.actorLabel(),
				20,
				tab++);
		actorField.setDefaultValue(AbstractRoot.getActorid());
		actorField.setHelpText(CONSTANTS.actorHelp());
		form.add(actorField);

		//-----------------------------------------------
		// Product field
		//-----------------------------------------------
		productField = new SuggestField(this, null,
				new NameIdAction(Service.PRODUCT),
				CONSTANTS.productLabel(),
				20,
				tab++);
		productField.setType(Product.Type.Accommodation.name());
		productField.setHelpText(CONSTANTS.productHelp());
		form.add(productField);
		
		//-----------------------------------------------
		// Quantity field
		//-----------------------------------------------
		quantityField = new SpinnerField(this, null, 1, 99, CONSTANTS.quantityLabel(), tab++);
		quantityField.setDefaultValue(1);
		quantityField.setVisible(false);
		quantityField.setFieldStyle(CSS.spinnerField());
		quantityField.setHelpText(CONSTANTS.quantityHelp());
		
		//-----------------------------------------------
		// Flat field
		//-----------------------------------------------
		flatField = new TextField(this, null,
				CONSTANTS.flatLabel(),
				tab++);
		flatField.setVisible(false);
		flatField.setFieldStyle(CSS.flatStyle());
		flatField.setLabelStyle(CSS.flatStyle());
		flatField.setHelpText(CONSTANTS.flatHelp());

		HorizontalPanel pq = new HorizontalPanel();
		pq.add(quantityField);
		pq.add(flatField);
		form.add(pq);

		//-----------------------------------------------
		// Arrival and Departure Dates
		//-----------------------------------------------
		fromtodateField = new DatespanField(this, null,
				CONSTANTS.fromtodateLabel(),
				tab++);
		fromtodateField.setHelpText(CONSTANTS.fromtodateHelp());
		form.add(fromtodateField);

		//-----------------------------------------------
		// Check to show CC details
		//-----------------------------------------------
		cardcheckField = new CheckField(this, null,
				CONSTANTS.cardcheckLabel(),
				tab++);
		cardcheckField.setDefaultValue(false);
		form.add(cardcheckField);

		//-----------------------------------------------
		// Card Name field
		//-----------------------------------------------
		cardholderField = new TextField(this,  null,
				CONSTANTS.cardholderLabel(),
				tab++);
		cardholderField.setSecure(true);
		cardholderField.setVisible(false);
		form.add(cardholderField);

		//-----------------------------------------------
		// Card Number field
		//-----------------------------------------------
		cardnumberField = new TextField(this,  null,
				CONSTANTS.cardnumberLabel(),
				tab++);
		cardnumberField.setSecure(true);
		cardnumberField.setVisible(false);
		form.add(cardnumberField);

		//-----------------------------------------------
		// Card Expiry Month field
		//-----------------------------------------------
		cardmonthField = new ListField(this,  null,
				Finance.getMonths(),
				null, //CONSTANTS.carddetailLabel(),
				false,
				tab++);
		cardmonthField.setSecure(true);
		cardmonthField.setVisible(false);
		cardmonthField.setFieldStyle(AbstractField.CSS.cbtListFieldCCmonth());

		//-----------------------------------------------
		// Card Expiry Year field
		//-----------------------------------------------
		cardyearField = new ListField(this,  null,
				Finance.getYears(),
				null,
				false,
				tab++);
		cardyearField.setSecure(true);
		cardyearField.setVisible(false);
		cardyearField.setFieldStyle(AbstractField.CSS.cbtListFieldCCyear());

		//-----------------------------------------------
		// Card Security Code field
		//-----------------------------------------------
		cardcodeField = new TextField(this, null,
				null,
				tab++);
		cardcodeField.setSecure(true);
		cardcodeField.setVisible(false);
		cardcodeField.setFieldStyle(AbstractField.CSS.cbtTextFieldCCcode());

		HorizontalPanel ccc = new HorizontalPanel();
		ccc.add(cardmonthField);
		ccc.add(cardyearField);
		ccc.add(cardcodeField);
		form.add(ccc);

		//-----------------------------------------------
		// Quoted Price field
		//-----------------------------------------------
		quoteField = new MoneyField(this, null,
				CONSTANTS.quoteLabel(),
				tab++);
		quoteField.setEnabled(AbstractRoot.writeable(AccessControl.QUOTE_PERMISSION));
		quoteField.setUnitenabled(false);
		quoteField.setDefaultUnitvalue(AbstractRoot.getCurrency());
		quoteField.setHelpText(CONSTANTS.quoteHelp());
		form.add(quoteField);

		//-----------------------------------------------
		// Rack Rate field
		//-----------------------------------------------
		costField = new DoubleField(this, null,
				CONSTANTS.costLabel(),
				AbstractField.AF,
				tab++);
		costField.setEnabled(false);
		costField.setHelpText(CONSTANTS.costHelp());

		//-----------------------------------------------
		// Extra Items field
		//-----------------------------------------------
		extraField = new DoubleField(this, null,
				CONSTANTS.extraLabel(),
				AbstractField.AF,
				tab++);
		extraField.setEnabled(false);
		extraField.setHelpText(CONSTANTS.extraHelp());

		HorizontalPanel ce = new HorizontalPanel();
		form.add(ce);
		ce.add(costField);
		ce.add(extraField);

		//-----------------------------------------------
		// Discount field
		//-----------------------------------------------
		discountField = new DoubleField(this, null,
				CONSTANTS.discountLabel(),
				AbstractField.AF,
				tab++);
		discountField.setEnabled(false);
		discountField.setRange(0.0, 100.0);
		discountField.setHelpText(CONSTANTS.discountHelp());

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
		
		HorizontalPanel dd = new HorizontalPanel();
		form.add(dd);
		dd.add(depositField);
		dd.add(discountField);

		form.add(createCommands());

		return form;
	}

	/* 
	 * Creates the panel of commands.
	 * 
	 * @return the panel of commands.
	 */
	private HorizontalPanel createCommands() {
		final HorizontalPanel bar = new HorizontalPanel();
		bar.addStyleName(AbstractField.CSS.cbtAbstractCommand());

		LocalRequest cancelRequest = new LocalRequest() {protected void perform() {onReset(Reservation.State.Provisional.name());}};

		//-----------------------------------------------
		// Create button
		//-----------------------------------------------
		CommandButton createButton = new CommandButton(this, AbstractField.CONSTANTS.allCreate(), reservationCreate, tab++);
		createButton.addStyleName(AbstractField.CSS.cbtGradientGreen());
		createButton.setTitle(CONSTANTS.createHelp());
		bar.add(createButton);
		
		//-----------------------------------------------
		// Close button
		//-----------------------------------------------
		CommandButton closeButton = new CommandButton(this, CONSTANTS.closeLabel(), reservationClose, tab++);
		closeButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		closeButton.setTitle(CONSTANTS.closeHelp());
		bar.add(closeButton);
		
		//-----------------------------------------------
		// Save button
		//-----------------------------------------------
		CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), reservationUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.setTitle(CONSTANTS.saveHelp());
		bar.add(saveButton);
		
		//-----------------------------------------------
		// Cancel Reservation button
		//-----------------------------------------------
		CommandButton axedButton = new CommandButton(this, AbstractField.CONSTANTS.allCancel(), reservationCancel, tab++);
		axedButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		axedButton.setTitle(CONSTANTS.axedHelp());
		bar.add(axedButton);
		
		//-----------------------------------------------
		// Restore button
		//-----------------------------------------------
		CommandButton restoreButton = new CommandButton(this, AbstractField.CONSTANTS.allRestore(), reservationUpdate, tab++);
		restoreButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		restoreButton.setTitle(CONSTANTS.restoreHelp());
		bar.add(restoreButton);

		//-----------------------------------------------
		// Cancel button
		//-----------------------------------------------
		CommandButton cancelButton = new CommandButton(this, AbstractField.CONSTANTS.allCancel(), cancelRequest, tab++);
		cancelButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		cancelButton.setTitle(AbstractField.CONSTANTS.helpCancel());
		bar.add(cancelButton);

		//-----------------------------------------------
		// Transition array to define the finite state machine
		//-----------------------------------------------
		fsm = new ArrayList<Transition>();
		fsm.add(new Transition(Reservation.State.Initial.name(), saveButton, Reservation.State.Provisional.name()));
		fsm.add(new Transition(Reservation.State.Initial.name(), closeButton, Reservation.State.Closed.name()));
		fsm.add(new Transition(Reservation.State.Initial.name(), cancelButton, Reservation.State.Provisional.name()));

		fsm.add(new Transition(Reservation.State.Provisional.name(), createButton, Reservation.State.Initial.name()));
		fsm.add(new Transition(Reservation.State.Provisional.name(), saveButton, Reservation.State.Provisional.name()));
		fsm.add(new Transition(Reservation.State.Provisional.name(), axedButton, Reservation.State.Cancelled.name()));

		fsm.add(new Transition(Reservation.State.Reserved.name(), createButton, Reservation.State.Initial.name()));
		fsm.add(new Transition(Reservation.State.Reserved.name(), saveButton, Reservation.State.Reserved.name()));
		fsm.add(new Transition(Reservation.State.Reserved.name(), axedButton, Reservation.State.Cancelled.name()));

		fsm.add(new Transition(Reservation.State.Confirmed.name(), createButton, Reservation.State.Initial.name()));
		fsm.add(new Transition(Reservation.State.Confirmed.name(), saveButton, Reservation.State.Confirmed.name()));
		fsm.add(new Transition(Reservation.State.Confirmed.name(), axedButton, Reservation.State.Cancelled.name()));

		fsm.add(new Transition(Reservation.State.FullyPaid.name(), createButton, Reservation.State.Initial.name()));
		fsm.add(new Transition(Reservation.State.FullyPaid.name(), saveButton, Reservation.State.FullyPaid.name()));
		fsm.add(new Transition(Reservation.State.FullyPaid.name(), axedButton, Reservation.State.Cancelled.name()));

		fsm.add(new Transition(Reservation.State.Briefed.name(), createButton, Reservation.State.Initial.name()));
		fsm.add(new Transition(Reservation.State.Briefed.name(), saveButton, Reservation.State.Briefed.name()));
		fsm.add(new Transition(Reservation.State.Briefed.name(), axedButton, Reservation.State.Cancelled.name()));

		fsm.add(new Transition(Reservation.State.Arrived.name(), createButton, Reservation.State.Initial.name()));
		fsm.add(new Transition(Reservation.State.Arrived.name(), saveButton, Reservation.State.Arrived.name()));
		fsm.add(new Transition(Reservation.State.Arrived.name(), axedButton, Reservation.State.Cancelled.name()));

		fsm.add(new Transition(Reservation.State.PreDeparture.name(), createButton, Reservation.State.Initial.name()));
		fsm.add(new Transition(Reservation.State.PreDeparture.name(), saveButton, Reservation.State.PreDeparture.name()));
		fsm.add(new Transition(Reservation.State.PreDeparture.name(), axedButton, Reservation.State.Cancelled.name()));

		fsm.add(new Transition(Reservation.State.Departed.name(), createButton, Reservation.State.Initial.name()));
		fsm.add(new Transition(Reservation.State.Departed.name(), saveButton, Reservation.State.Departed.name()));

		fsm.add(new Transition(Reservation.State.Cancelled.name(), createButton, Reservation.State.Initial.name()));
		fsm.add(new Transition(Reservation.State.Cancelled.name(), saveButton, Reservation.State.Cancelled.name()));
		fsm.add(new Transition(Reservation.State.Cancelled.name(), restoreButton, Reservation.State.Provisional.name()));

		fsm.add(new Transition(Reservation.State.Closed.name(), createButton, Reservation.State.Initial.name()));
		fsm.add(new Transition(Reservation.State.Closed.name(), saveButton, Reservation.State.Closed.name()));
		fsm.add(new Transition(Reservation.State.Closed.name(), restoreButton, Reservation.State.Provisional.name()));

		return bar;
	}

	/* 
	 * Creates the hospitality stack panel.
	 * 
	 * @return the hospitality stack panel.
	 */
	private VerticalPanel createHospitality() {
		final VerticalPanel form = new VerticalPanel();
		final HorizontalPanel panel = new HorizontalPanel();
		form.add(panel);
		
		//-----------------------------------------------
		// State field
		//-----------------------------------------------
		stateField = new TextField(this, null,
				CONSTANTS.stateLabel(),
				tab++);
		stateField.setEnabled(false);
		stateField.setFieldHalf();
		stateField.setHelpText(CONSTANTS.stateHelp());
		panel.add(stateField);

		//-----------------------------------------------
		// Due Date is when the next reservation state is due
		//-----------------------------------------------
		duedateField = new DateField(this, null,
				CONSTANTS.duedateLabel(),
				tab++);
		duedateField.setHelpText(CONSTANTS.duedateHelp());
		panel.add(duedateField);

		//-----------------------------------------------
		// Arrival & Departure Times field
		//-----------------------------------------------
		fromtotimeField = new TimespanField(this, null,
				CONSTANTS.fromtotimeLabel(),
				tab++);
		fromtotimeField.setDefaultValue(Reservation.ARRIVALTIME);
		fromtotimeField.setDefaultTovalue(Reservation.DEPARTURETIME);
		fromtotimeField.setFieldStyle(CSS.fromtotimeField());
		fromtotimeField.setHelpText(CONSTANTS.fromtotimeHelp());
		panel.add(fromtotimeField);

		//-----------------------------------------------
		// Service Times field
		//-----------------------------------------------
		servicetimesField = new TimespanField(this, null,
				CONSTANTS.servicetimesLabel(),
				tab++);
		servicetimesField.setDefaultValue(Reservation.SERVICEFROM);
		servicetimesField.setDefaultTovalue(Reservation.SERVICETO);
		servicetimesField.setFieldStyle(CSS.fromtotimeField());
		servicetimesField.setHelpText(CONSTANTS.servicetimesHelp());
//		panel.add(servicetimesField);

//		final HorizontalPanel size = new HorizontalPanel();
//		form.add(size);

		//-----------------------------------------------
		// Number of adults
		//-----------------------------------------------
		adultField = new SpinnerField(this, null, 1, 199, CONSTANTS.adultLabel(), tab++);
		adultField.setFieldStyle(CSS.spinnerField());
		adultField.setRange(1, 99);
		adultField.setDefaultValue(2);
		adultField.setHelpText(CONSTANTS.adultHelp());
		panel.add(adultField);

		//-----------------------------------------------
		// Number of children
		//-----------------------------------------------
		childField = new SpinnerField(this, null, 0, 99, CONSTANTS.childLabel(), tab++);
		childField.setFieldStyle(CSS.spinnerField());
		childField.setDefaultValue(0);
		childField.setHelpText(CONSTANTS.childHelp());
		panel.add(childField);
		
		//-----------------------------------------------
		// Number of infants
		//-----------------------------------------------
		infantField = new SpinnerField(this, null, 0, 99, CONSTANTS.infantLabel(), tab++);
		infantField.setFieldStyle(CSS.spinnerField());
		infantField.setDefaultValue(0);
		infantField.setHelpText(CONSTANTS.infantHelp());
		panel.add(infantField);
		
		//-----------------------------------------------
		// Reservation Notes field
		//-----------------------------------------------
		reservationtextField = new TextAreaField(this, null,
				 CONSTANTS.notesLabel(),
				tab++);
		reservationtextField.setMaxLength(3000);
		reservationtextField.setFieldStyle(CSS.descriptionField());
		reservationtextField.setDefaultValue(CONSTANTS.notesEmpty());
		reservationtextField.setHelpText(CONSTANTS.notesHelp());
		form.add(reservationtextField);

		//-----------------------------------------------
		// Guest History field
		//-----------------------------------------------
		historytextField = new TextAreaField(this, null,
				CONSTANTS.historytextLabel(),
				tab++);
		historytextField.setFieldStyle(CSS.descriptionField());
		historytextField.setDefaultValue(CONSTANTS.historytextEmpty());
		historytextField.setEnabled(false);
		historytextField.setHelpText(CONSTANTS.historytextHelp());
		form.add(historytextField);

		//-----------------------------------------------
		// Guest Notes field
		//-----------------------------------------------
		customertextField = new TextAreaField(this, null,
				 CONSTANTS.customertextLabel(),
				tab++);
		customertextField.setFieldStyle(CSS.descriptionField());
		customertextField.setDefaultValue(CONSTANTS.customertextEmpty());
		customertextField.setMaxLength(Text.MAX_TEXT_SIZE);
		customertextField.setHelpText(CONSTANTS.customertextHelp());
		form.add(customertextField);
		
		form.add(createHospitalitycommands());
		
		//onReset(Reservation.State.Provisional.name());
		return form;
	}
	
	/* 
	 * Creates the hospitality commands bar.
	 * 
	 * @return the hospitality commands bar.
	 */
	private HorizontalPanel createHospitalitycommands() {
		final HorizontalPanel bar = new HorizontalPanel();
		
		//-----------------------------------------------
		// Previous State button
		//-----------------------------------------------
		oldButton = new CommandButton(this, CONSTANTS.reservationStates()[Reservation.State.Provisional.ordinal()], reservationUndo, tab++);
		oldButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		oldButton.setVisiblealways(true);
		oldButton.setTitle(CONSTANTS.oldbuttonHelp());
		bar.add(oldButton);
		
		//-----------------------------------------------
		// Next State button
		//-----------------------------------------------
		dueButton = new CommandButton(this, CONSTANTS.reservationStates()[Reservation.State.Confirmed.ordinal()], reservationUpdate, tab++);
		dueButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		dueButton.setVisiblealways(true);
		dueButton.setTitle(CONSTANTS.duebuttonHelp());
		bar.add(dueButton);
		
		//-----------------------------------------------
		// Adds commands to the finite state machine
		//-----------------------------------------------
		oldTransition = new Transition(Reservation.State.Reserved.name(), oldButton, Reservation.State.Provisional.name());
		fsm.add(oldTransition);
		dueTransition = new Transition(Reservation.State.Provisional.name(), dueButton, Reservation.State.Reserved.name());
		fsm.add(dueTransition);

		//-----------------------------------------------
		// Quotation Report button
		//-----------------------------------------------
		final ReportButton quotationReport = new ReportButton(null, CONSTANTS.quotationLabel(), tab++) {
			public Report getReport() {
				Report report = new Report();
				report.setOrganizationid(AbstractRoot.getOrganizationid());
				report.setActorid(AbstractRoot.getActorid());
				report.setDesign(Design.RESERVATION_QUOTATION);
				report.setFromtoname(reservationField.getName());
				report.setLanguage(AbstractRoot.getLanguage());
				report.setState(Report.CREATED);
				report.setFormat(Report.PDF);
				return report;
			}
		};
		bar.add(quotationReport);

		//-----------------------------------------------
		// Confirmation Report button
		//-----------------------------------------------
		final ReportButton confirmationReport = new ReportButton(null, CONSTANTS.confirmationLabel(), tab++) {
			public Report getReport() {
				Report report = new Report();
				report.setOrganizationid(AbstractRoot.getOrganizationid());
				report.setActorid(AbstractRoot.getActorid());
				report.setDesign(Design.RESERVATION_CONFIRMATION);
				report.setFromtoname(reservationField.getName());
				report.setLanguage(AbstractRoot.getLanguage());
				report.setState(Report.CREATED);
				return report;
			}
		};
		bar.add(confirmationReport);

		//-----------------------------------------------
		// Detail Report button
		//-----------------------------------------------
		final ReportButton detailReport = new ReportButton(null, CONSTANTS.detailLabel(), tab++) {
			public Report getReport() {
				Report report = new Report();
				report.setOrganizationid(AbstractRoot.getOrganizationid());
				report.setActorid(AbstractRoot.getActorid());
				report.setDesign(Design.RESERVATION_DETAIL);
				report.setFromtoname(reservationField.getName());
				report.setLanguage(AbstractRoot.getLanguage());
				report.setState(Report.CREATED);
				return report;
			}
		};
		bar.add(detailReport);

		return bar;
	}

	/* 
	 * Creates the panel displayed when there are no priced features.
	 * 
	 * @return the panel displayed when there are no priced features.
	 */
	private Widget featuretableEmpty() {
		final FlowPanel panel = new FlowPanel();

		final Button emptyButton = new Button(AbstractField.CONSTANTS.allCreate());
		emptyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (reservationField.noValue()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.reservationError(), reservationField);}
				else {QuotePopup.getInstance().show(reservationField.getValue(), productField.getValue(), quoteField, featureTable);}
			}
		});
		panel.add(emptyButton);
		panel.add(new HTML(CONSTANTS.featuretableEmpty()));
		panel.add(new Image(BUNDLE.featuretableEmpty()));
		return panel;
	}

	/* 
	 * Creates the feature price table stack panel.
	 * 
	 * @return the feature price table stack panel.
	 */
	private TableField<Price> createFeature() {

		featureTable = new TableField<Price>(this, null, 
				null, //new QuoteDetailTable(),
				FEATURE_ROWS, 
				tab++);

		featureTable.setEmptyValue(featuretableEmpty());
		featureTable.setOrderby(Price.NAME);
		//featurepriceTable.addStyleName(CSS.actorStyle());

		featureTable.setTableError(new TableError() {
			@Override
			public boolean error() {return reservationField.noValue();}
		});

//		featureTable.setTableExecutor(new TableExecutor<QuoteDetailTable>() {
//			@Override
//			public void execute(QuoteDetailTable action) {action.setEntityid(reservationField.getValue());}
//		});

		int col = 0;

		//-----------------------------------------------
		// Change button
		//-----------------------------------------------
		final Column<Price, Price> changeButton = new Column<Price, Price>( 
				new ActionCell<Price>(AbstractField.CONSTANTS.allChange(), AbstractField.CSS.cbtTableFieldChangeButton(),
						new Delegate<Price>(){
					public void execute( Price price ) {
						//AbstractField.addMessage(Level.ERROR, CONSTANTS.optionalError(), reservationField);
						if (reservationField.noValue()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.reservationError(), reservationField);}
						else {QuotePopup.getInstance().show(price, quoteField, featureTable);}
					}
				}
				)
		)
		{
			public Price getValue(Price price){return price;}
		};

		//-----------------------------------------------
		// Create button
		//-----------------------------------------------
		final ActionHeader<Price> createButton = new ActionHeader<Price>(
				new ActionCell<Price>(
						AbstractField.CONSTANTS.allCreate(), 
						AbstractField.CSS.cbtTableFieldCreateButton(),
						new Delegate<Price>(){
							public void execute(Price price ) {
								if (reservationField.noValue()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.reservationError(), reservationField);}
								else {QuotePopup.getInstance().show(reservationField.getValue(), productField.getValue(), quoteField, featureTable);}
							}
						}
				)
		)
		{
			public Price getValue(Price price){
				return price;
			}
		};

		featureTable.addColumn(changeButton, createButton);

		//-----------------------------------------------
		// Feature Name column
		//-----------------------------------------------
		Column<Price, String> name = new Column<Price, String>(new TextCell()) {
			@Override
			public String getValue( Price price ) {return price.getName();}
		};
		featureTable.addStringColumn(name, CONSTANTS.featuretableHeaders()[col++], Price.ENTITYNAME);

		//-----------------------------------------------
		// Feature Type column
		//-----------------------------------------------
		Column<Price, String> state = new Column<Price, String>(new TextCell()) {
			@Override
			public String getValue( Price price ) {return price.getType();}
		};
		featureTable.addStringColumn(state, CONSTANTS.featuretableHeaders()[col++], Price.STATE);

		//-----------------------------------------------
		// Quantity column
		//-----------------------------------------------
		Column<Price, Number> quantity = new Column<Price, Number>(new NumberCell(AbstractField.IF)) {
			@Override
			public Double getValue( Price price ) {return price.getQuantity();}
		};
		featureTable.addNumberColumn( quantity, CONSTANTS.featuretableHeaders()[col++], Price.QUANTITY);

		//-----------------------------------------------
		// Unit column
		//-----------------------------------------------
		Column<Price, String> unit = new Column<Price, String>(new TextCell()) {
			@Override
			public String getValue( Price price ) {return price.getUnit();}
		};
		featureTable.addStringColumn(unit, CONSTANTS.featuretableHeaders()[col++], Price.UNIT);

		//-----------------------------------------------
		// Total Price column
		//-----------------------------------------------
		Column<Price, Number> price = new Column<Price, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( Price price ) {return price.getTotalvalue();}
		};
		featureTable.addNumberColumn( price, CONSTANTS.featuretableHeaders()[col++], Price.VALUE);

		//-----------------------------------------------
		// Currency column
		//-----------------------------------------------
		Column<Price, String> currency = new Column<Price, String>( new TextCell()) {
			@Override
			public String getValue( Price price ) {return price.getCurrency();}
		};
		featureTable.addStringColumn(currency, CONSTANTS.featuretableHeaders()[col++]);

		//-----------------------------------------------
		// Supplier Name column
		//-----------------------------------------------
//		Column<Price, String> bankname = new Column<Price, String>( new TextCell()) {
//			@Override
//			public String getValue( Price price ) {return price.getPartyname();}
//		};
//		featureTable.addStringColumn(bankname, CONSTANTS.featuretableHeaders()[col++]);

		return featureTable;
	}

	/* 
	 * Creates the servicing stack panel.
	 * 
	 * @return the servicing stack panel.
	 */
	private ScrollPanel createService() {
		ScrollPanel scroll = new ScrollPanel();
		VerticalPanel panel = new VerticalPanel();
		scroll.add(panel);
		HorizontalPanel form = new HorizontalPanel();
		panel.add(form);

		//-----------------------------------------------
		// Serviced By field
		//-----------------------------------------------
		serviceproviderField = new SuggestField(this,  null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.serviceproviderLabel(),
				20,
				tab++);
		serviceproviderField.setType(Party.Type.Supplier.name());
		serviceproviderField.setHelpText(CONSTANTS.serviceproviderHelp());
		
		final Image serviceproviderButton = new Image(AbstractField.BUNDLE.plus());
		serviceproviderButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PartyPopup.getInstance().show(Party.Type.Supplier, serviceproviderField, null);
			}
		});
		serviceproviderButton.setTitle(CONSTANTS.serviceproviderbuttonHelp());
		serviceproviderField.addButton(serviceproviderButton);
		form.add(serviceproviderField);

		//-----------------------------------------------
		// Service Payer field
		//-----------------------------------------------
		servicepayerField = new SuggestField(this, null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.servicepayerLabel(),
				20,
				tab++);
		servicepayerField.setHelpText(CONSTANTS.servicepayerHelp());
		form.add(servicepayerField);

		//-----------------------------------------------
		// Service Task grid
		//-----------------------------------------------
		serviceGrid = new GridField<Task>(this, null,
				CONSTANTS.serviceHeaders(),
				0,
				tab++) {
			
			@Override
			public void setRowValue(int row, Task task) {
				int col = 0;
				//-----------------------------------------------
				// Service Required check box 
				//-----------------------------------------------
				final ServiceTaskCheckBox checkField = new ServiceTaskCheckBox(row);
				checkField.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						ServiceTaskCheckBox cb = (ServiceTaskCheckBox)event.getSource();
						int row = cb.getRow();
						if (ifMessage(hasServiceTasks() && serviceproviderField.noValue(), Level.TERSE, ReservationForm.CONSTANTS.serviceproviderError(), serviceproviderField)
								|| ifMessage(hasServiceTasks() && servicepayerField.noValue(), Level.TERSE, ReservationForm.CONSTANTS.servicepayerError(), servicepayerField)
						) {cb.setValue(false);}
						else {
							postServiceTask(getRowValue(row));
							taskUpdate.execute(getRowValue(row));
						}
					}
				});
				
				checkField.setValue(task.hasState(Task.COMPLETED));
				serviceGrid.setWidget(row, col++, checkField);

				serviceGrid.setText(row, col++, AbstractRoot.getSDF().format(task.getDuedate()));

				int day = task.noDuedate() ? 0 : task.getDuedate().getDay();
				String[] days = ReservationForm.CONSTANTS.days();
				serviceGrid.setText(row, col++, days[day]);
				
				//-----------------------------------------------
				// Service Type list 
				//-----------------------------------------------
				final ListField typeField = new ListField(this, null, NameId.getList(ReservationForm.CONSTANTS.serviceTypes(), Task.SERVICE_TYPES), null, false, 1);
				typeField.setValue(task.getProductid());
				typeField.setFieldStyle(ReservationForm.CSS.servicetypeStyle());
				serviceGrid.setWidget(row, col++, typeField);

				//-----------------------------------------------
				// Service Price field 
				//-----------------------------------------------
				final DoubleField priceField = new DoubleField(this, null, null, AbstractField.AF, 0);
				if (task.noPrice()) {servicepriceRead.execute(new ServicepriceRequest(task.getProductid(), servicepayerField.getValue(), task.getName(), row));}
				else {priceField.setValue(task.getPrice());}
				priceField.setFieldStyle(ReservationForm.CSS.servicepriceStyle());
				serviceGrid.setWidget(row, col++, priceField);

				//-----------------------------------------------
				// Service Description field 
				//-----------------------------------------------
				final TextAreaField notesField = new TextAreaField(this, null, null, 0);
				notesField.setValue(task.getNotes());
//				else {notesField.setText(Product.getServiceText(productField.getValue(), task.getProductid()));}
				notesField.setFieldStyle(ReservationForm.CSS.servicenotesStyle());
				notesField.setMaxLength(Text.MAX_TEXT_SIZE);
				serviceGrid.setWidget(row, col++, notesField);
			}

			/*
			 * (non-Javadoc)
			 * @see net.cbtltd.client.field.GridField#getRowValue(int)
			 */
			@Override
			public Task getRowValue(int row) {
				int col = 0;
				Task task = new Task();
				task.setActorid(actorField.getValue());
				task.setActivity(NameId.Type.Reservation.name());
				task.setCurrency(AbstractRoot.getCurrency());
				task.setCustomerid(servicepayerField.getValue());
				task.setDate(dateField.getValue());
				task.setOrganizationid(AbstractRoot.getOrganizationid());
				task.setParentid(reservationField.getValue());
				task.setParentname(reservationField.getName());
				task.setProcess(Task.Type.Service.name());
				task.setQuantity(1.0);
				task.setUnit(Unit.EA);
				task.setState(((ServiceTaskCheckBox)serviceGrid.getWidget(row, col++)).getValue() ? Task.COMPLETED : Task.CREATED);
				task.setDuedate(AbstractRoot.getSDF().parse(serviceGrid.getText(row, col++)));
				col++; //for redundant daycolumn
				task.setProductid(((ListField)serviceGrid.getWidget(row, col++)).getValue());
				task.setPrice(((DoubleField)serviceGrid.getWidget(row, col++)).getValue());
				task.setNotes(((TextAreaField)serviceGrid.getWidget(row, col++)).getValue());
				task.setName(reservationField.getName() + "-" + task.getProductid());
				return task;
			}
		};
		serviceGrid.setAutosize(true);
		panel.add(serviceGrid);

		return scroll;
	}

	/* Inner Class ServiceTaskCheckBox extends CheckBox to include its row number. */
	private class ServiceTaskCheckBox extends CheckBox {
		private int row;
		public ServiceTaskCheckBox(int row) {this.row = row;}
		public int getRow() {return row;}
	}
	
	/*
	 * Posts the specified service task to the financial ledger.
	 * 
	 * @param task the specified service task.
	 */
	private void postServiceTask(Task task) {

		if (task == null || task.noAmount()) {return;}

		boolean reversed = task.hasState(Task.CREATED);
		String description = CONSTANTS.servicetaskpostLabel() + task.getName();
		Double amount = task.getAmount();
		Double outputtax = 0.0;
		Double inputtax = 0.0;

		//CR output tax
		if (serviceproviderField.hasValue(AbstractRoot.getOrganizationid()) 
				&& reservationentities != null 
				&& reservationentities.getManager().hasTaxnumber()) {
			outputtax = Event.round(amount * 0.14 / 1.14); //TODO:
		}
		
		//CR input tax
		if (servicepayerField.hasValue(AbstractRoot.getOrganizationid()) 
				&& reservationentities != null 
				&& reservationentities.getManager().hasTaxnumber()) {
			inputtax = Event.round(amount * 0.14 / 1.14); //TODO:
		}
		
		JournalTaskUpdate event = new JournalTaskUpdate();
		event.setTaskid(task.getId());
		if (reversed) {event.setTaskstate(Task.CREATED);}
		else {event.setTaskstate(Task.FINAL);}
		event.setActivity(NameId.Type.Reservation.name());
		event.setActorid(AbstractRoot.getActorid());
		event.setActorname(AbstractRoot.getActorname());
		event.setDate(new Date());
		event.setDonedate(new Date());
		event.setNotes(description);
		event.setOrganizationid(AbstractRoot.getOrganizationid());
		event.setParentid(reservationField.getValue());
		event.setProcess(Event.Type.Purchase.name());
		event.setState(Event.CREATED);
		event.setType(Event.ACCOUNTING);

		if (serviceproviderField.hasValue(AbstractRoot.getOrganizationid())) {
			//CR sales
			event.addItem(new Journal(
					event.getId(),
					Model.ZERO,
					AbstractRoot.getOrganizationid(),
					AbstractRoot.getOrganizationname(),
					Model.ZERO,
					Account.SALES_REVENUE,
					Account.SALES_REVENUE_NAME,
					NameId.Type.Party.name(),
					serviceproviderField.getValue(),
					serviceproviderField.getName(),
					reversed ? -1.0 : 1.0,
					Unit.EA,
					0.0,
					AbstractRoot.getCurrency(),
					reversed ? amount - outputtax: 0.0,
					reversed ? 0.0 : amount - outputtax,
					description
				)
			);
			
			if (Math.abs(outputtax) >= 0.01) {
				//CR output tax account
				event.addItem(new Journal(
						event.getId(),
						Model.ZERO,
						AbstractRoot.getOrganizationid(),
						AbstractRoot.getOrganizationname(),
						Model.ZERO,
						Account.VAT_OUTPUT,
						Account.VAT_OUTPUT_NAME,
						NameId.Type.Account.name(),
						Model.ZERO,
						Model.BLANK,
						reversed ? -1.0 : 1.0,
						Unit.EA,
						0.0,
						AbstractRoot.getCurrency(),
						reversed ? outputtax : 0.0,
						reversed ? 0.0 : outputtax ,
						description
					)
				);
			}
		}
		else {
			//CR accounts payable or sales
			event.addItem(new Journal(
					event.getId(),
					Model.ZERO,
					AbstractRoot.getOrganizationid(),
					AbstractRoot.getOrganizationname(),
					Model.ZERO,
					Account.ACCOUNTS_PAYABLE,
					Account.ACCOUNTS_PAYABLE_NAME,
					NameId.Type.Party.name(),
					serviceproviderField.getValue(),
					serviceproviderField.getName(),
					reversed ? -1.0 : 1.0,
					Unit.EA,
					0.0,
					AbstractRoot.getCurrency(),
					reversed ? amount : 0.0,
					reversed ? 0.0 : amount,
					description
				)
			);
		}
		
		if (servicepayerField.hasValue(AbstractRoot.getOrganizationid())) {
			//DR housekeeping expenses
			event.addItem(new Journal(
					event.getId(),
					Model.ZERO,
					AbstractRoot.getOrganizationid(),
					AbstractRoot.getOrganizationname(),
					Model.ZERO,
					Account.SERVICE_COST,
					Account.SERVICE_COST_NAME,
					NameId.Type.Party.name(),
					servicepayerField.getValue(),
					servicepayerField.getName(),
					reversed ? -1.0 : 1.0,
					Unit.EA,
					0.0,
					AbstractRoot.getCurrency(),
					reversed ? 0.0 : amount - inputtax,
					reversed ? amount - inputtax : 0.0,
					description
				)
			);

			if (Math.abs(inputtax) >= 0.01) {
				//CR input tax account
				event.addItem(new Journal(
						event.getId(),
						Model.ZERO,
						AbstractRoot.getOrganizationid(),
						AbstractRoot.getOrganizationname(),
						Model.ZERO,
						Account.VAT_INPUT,
						Account.VAT_INPUT_NAME,
						NameId.Type.Account.name(),
						Model.ZERO,
						Model.BLANK,
						reversed ? -1.0 : 1.0,
						Unit.EA,
						0.0,
						AbstractRoot.getCurrency(),
						reversed ? 0.0 : inputtax ,
						reversed ? inputtax : 0.0,
						description
					)
				);
			}
		}
		else {
			//DR accounts receivable
			event.addItem(new Journal(
					event.getId(),
					Model.ZERO,
					AbstractRoot.getOrganizationid(),
					AbstractRoot.getOrganizationname(),
					Model.ZERO,
					Account.ACCOUNTS_RECEIVABLE,
					Account.ACCOUNTS_RECEIVABLE_NAME,
					NameId.Type.Party.name(),
					servicepayerField.getValue(),
					servicepayerField.getName(),
					reversed ? -1.0 : 1.0,
					Unit.EA,
					0.0,
					AbstractRoot.getCurrency(),
					reversed ? 0.0 : amount,
					reversed ? amount : 0.0,
					description
				)
			);
		}

		AbstractRoot.getService().send(event, new AsyncCallback<Event>() {
			public void onFailure(Throwable caught) {
				AbstractField.addMessage(Level.ERROR, AbstractField.CONSTANTS.errRequest() + caught.getMessage(), eventjournalTable);
				caught.printStackTrace();
			} 
			public void onSuccess(Event response) {
				eventjournalTable.execute(true);
			}
		});
	}
	
	/* Sets the service tasks for the reservation. */
	private void setServiceTasks() {
		if (!hasServiceTasks() || reservationentities == null || reservationentities.getProduct() == null) {return;}

		int fromDay = Time.getDay(fromtodateField.getValue());
		int toDay = Time.getDay(fromtodateField.getTovalue());

		if (serviceGrid != null && serviceGrid.hasValue()) {
			int gridFromDay = Time.getDay(serviceGrid.getFirstvalue().getDuedate());
			int gridToDay = Time.getDay(serviceGrid.getLastvalue().getDuedate());
			if (fromDay == gridFromDay && toDay == gridToDay) {return;}
		}
		
		int daycount = 0;
		ArrayList<Task> tasks = new ArrayList<Task>();
		for (int day = fromDay; day <= toDay; day++) {
			daycount++;
			Task task = new Task();
			task.setDate(new Date());
			task.setDuedate(Time.getDate(day));
			task.setOrganizationid(AbstractRoot.getOrganizationid());
			task.setActivity(NameId.Type.Reservation.name());
			task.setProcess(Task.Type.Service.name());
			task.setState(Task.CREATED);
			task.setParentid(reservationField.getValue());
			//TODO: set rules
			Product product = reservationentities.getProduct();
			boolean[] servicedays = new boolean[7];//Sunday == 0
			for (int index = 0; index < servicedays.length; index++) {servicedays[index] = product.getServicedays().substring(index, index + 1).equals("1");}
			if (servicedays[task.getDuedate().getDay()]) {continue;} //CJM no task if no service task.setProductid(Task.NONE);}
			else if (day == toDay) {task.setProductid(Task.DEPARTURE);}
			else if (day == fromDay) {task.setProductid(Task.ARRIVAL);}
			else if (daycount > product.getLinenchange()) {
				task.setProductid(Task.LINEN_CHANGE);
				daycount = 0;
			}
			else {task.setProductid(Task.REFRESH);}

			task.setCost(getServicecost(task));
			task.setPrice(getServiceprice(task));
			task.setNotes(getServicenotes(task));
			tasks.add(task);
		}
		serviceGrid.setValue(tasks);
	}

	/*
	 * Gets the cost of the specified service task.
	 * 
	 * @param task the specified service task.
	 * @return the cost of the task.
	 */
	private Double getServicecost(Task task) {
		//TODO: depends on supplier, use price table
		return 0.0;
	}

	/*
	 * Gets the price of the specified service task.
	 * 
	 * @param task the specified service task.
	 * @return the price of the task.
	 */
	private Double getServiceprice(Task task) {
		//TODO: use price table
		return 0.0;
	}

	/*
	 * Gets the description of the specified service task.
	 * 
	 * @param task the specified service task.
	 * @return the description of the task.
	 */
	private String getServicenotes(Task task) {
		return null; //Text is read if not existing
	}

	/* 
	 * Creates the panel displayed when there are no maintenance jobs.
	 * 
	 * @return the empty panel.
	 */
	private Widget maintenancetableEmpty() {
		FlowPanel panel = new FlowPanel();
		Button emptyButton = new Button(AbstractField.CONSTANTS.allCreate());
		emptyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (reservationField.noValue()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.reservationError(), reservationField);}
				else {TaskPopup.getInstance().show(Task.Type.Maintenance, reservationentities, maintenanceTable);}
			}
		});
		panel.add(emptyButton);
		
		panel.add(new HTML(CONSTANTS.maintenancetableEmpty()));
		panel.addStyleName(AbstractField.CSS.cbtTableFieldEmpty());
		panel.add(new Image(BUNDLE.maintenancetableEmpty()));
		return panel;
	}
	
	/* 
	 * Creates the maintenance jobs stack panel.
	 * 
	 * @return the maintenance jobs stack panel.
	 */
	private TableField<Task> createMaintenance() {

		//-----------------------------------------------
		// Maintenance selection handler
		//-----------------------------------------------
		final NoSelectionModel<Task> selectionModel = new NoSelectionModel<Task>();
		SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				TaskPopup.getInstance().show(selectionModel.getLastSelectedObject(), maintenanceTable);
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// Maintenance table
		//-----------------------------------------------
		maintenanceTable = new TableField<Task>(this, null, 
				new MaintenanceTaskTable(),
				selectionModel, 
				MAINTENANCE_ROWS,
				tab++);

		maintenanceTable.setEmptyValue(maintenancetableEmpty());
		maintenanceTable.setOrderby(Task.DUEDATE);

		maintenanceTable.setTableError(new TableError() {
			@Override
			public boolean error() {return (
				reservationField.noValue()
			);}
		});

		maintenanceTable.setTableExecutor(new TableExecutor<MaintenanceTaskTable>() {
			public void execute(MaintenanceTaskTable action) {
				action.setId(reservationField.getValue());
			}
		});
		
		int col = 0;

		//-----------------------------------------------
		// Change button
		//-----------------------------------------------
		final Column<Task, Task> changeButton = new Column<Task, Task>( 
				new ActionCell<Task>(AbstractField.CONSTANTS.allChange(), AbstractField.CSS.cbtTableFieldChangeButton(),
						new Delegate<Task>(){
					public void execute( Task task ) {
						TaskPopup.getInstance().show(task, maintenanceTable);
					}
				}
				)
		)
		{
			public Task getValue(Task task){return task;}//TODO: selectForm(row); 
		};

		//-----------------------------------------------
		// Create button
		//-----------------------------------------------
		final ActionHeader<Task> createButton = new ActionHeader<Task>(
				new ActionCell<Task>(
						AbstractField.CONSTANTS.allCreate(), 
						AbstractField.CSS.cbtTableFieldCreateButton(),
						new Delegate<Task>(){
							public void execute(Task task ) {
								if (reservationField.noValue()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.reservationError(), reservationField);}
								else {TaskPopup.getInstance().show(Task.Type.Maintenance, reservationentities, maintenanceTable);}
							}
						}
				)
		)
		{
			public Task getValue(Task task){
				return task;
			}
		};

		maintenanceTable.addColumn(changeButton, createButton);

		//-----------------------------------------------
		// Due Date column
		//-----------------------------------------------
		Column<Task, Date> date = new Column<Task, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( Task task ) {return Time.getDateClient(task.getDuedate());}
		};
		maintenanceTable.addDateColumn(date, CONSTANTS.maintenanceHeaders()[col++], Task.DUEDATE);

		//-----------------------------------------------
		// Name column
		//-----------------------------------------------
		Column<Task, String> name = new Column<Task, String>( new TextCell() ) {
			@Override
			public String getValue( Task task ) {return task.getName();}
		};
		maintenanceTable.addStringColumn(name, CONSTANTS.maintenanceHeaders()[col++], Task.NAME);

		//-----------------------------------------------
		// Actor column
		//-----------------------------------------------
		Column<Task, String> actorname = new Column<Task, String>( new TextCell() ) {
			@Override
			public String getValue( Task task ) {return task.getActorname();}
		};
		maintenanceTable.addStringColumn(actorname, CONSTANTS.maintenanceHeaders()[col++], Task.ACTORNAME);

		//-----------------------------------------------
		// State column
		//-----------------------------------------------
		Column<Task, String> state = new Column<Task, String>( new TextCell()) {
			@Override
			public String getValue( Task task ) {return task.getState();}
		};
		maintenanceTable.addStringColumn(state, CONSTANTS.maintenanceHeaders()[col++], Task.STATE);

		//-----------------------------------------------
		// Amount column
		//-----------------------------------------------
		Column<Task, Number> amount = new Column<Task, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( Task task ) {return task.getAmount();}
		};
		maintenanceTable.addNumberColumn( amount, CONSTANTS.maintenanceHeaders()[col++], Task.AMOUNT);

		//-----------------------------------------------
		// Currency column
		//-----------------------------------------------
		Column<Task, String> currency = new Column<Task, String>( new TextCell() ) {
			@Override
			public String getValue( Task task ) {return task.getCurrency();}
		};
		maintenanceTable.addStringColumn(currency, CONSTANTS.maintenanceHeaders()[col++]);

		//-----------------------------------------------
		// Notes column
		//-----------------------------------------------
		Column<Task, String> notes = new Column<Task, String>( new TextCell() ) {
			@Override
			public String getValue( Task task ) {return task.getNotes();}
		};
		maintenanceTable.addStringColumn(notes, CONSTANTS.maintenanceHeaders()[col++], Task.NOTES);

		return maintenanceTable;
	}

	/* 
	 * Creates the panel of financial commands.
	 * 
	 * @return the panel of financial commands.
	 */
	private HorizontalPanel createFinancialcommands() {
		
		final HorizontalPanel bar = new HorizontalPanel();
		bar.addStyleName(CSS.financialcommandBar());

		//-----------------------------------------------
		// Reservation Sale button
		//-----------------------------------------------
		final Button reservationsaleButton = new Button(CONSTANTS.reservationsaleButton());
		reservationsaleButton.setTitle(CONSTANTS.reservationsaleHelp());
		reservationsaleButton.addStyleName(AbstractField.CSS.cbtCommandButton());
		reservationsaleButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		reservationsaleButton.addStyleName(CSS.financialcommandButton());
		reservationsaleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!blocked()) {JournalPopup.getInstance().show(Event.Type.ReservationSale, getValue(), featureTable.getValue(), reservationentities, eventjournalTable);}
			}
		});
		bar.add(reservationsaleButton);
		
		//-----------------------------------------------
		// Sundry Sale button
		//-----------------------------------------------
		final Button saleButton = new Button(CONSTANTS.saleButton());
		saleButton.setTitle(CONSTANTS.saleHelp());
		saleButton.addStyleName(AbstractField.CSS.cbtCommandButton());
		saleButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saleButton.addStyleName(CSS.financialcommandButton());
		saleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!blocked()) {JournalPopup.getInstance().show(Event.Type.Sale, getValue(), reservationentities, eventjournalTable);}
			}
		});
		bar.add(saleButton);
		
		//-----------------------------------------------
		// Receipt button
		//-----------------------------------------------
		final Button receiptButton = new Button(CONSTANTS.receiptButton());
		receiptButton.setTitle(CONSTANTS.receiptHelp());
		receiptButton.addStyleName(AbstractField.CSS.cbtCommandButton());
		receiptButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		receiptButton.addStyleName(CSS.financialcommandButton());
		receiptButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!blocked()) {JournalPopup.getInstance().show(Event.Type.Receipt, getValue(), reservationentities, eventjournalTable);}
			}
		});
		bar.add(receiptButton);
		
		//-----------------------------------------------
		// Purchase button
		//-----------------------------------------------
		final Button purchaseButton = new Button(CONSTANTS.purchaseButton());
		purchaseButton.setTitle(CONSTANTS.purchaseHelp());
		purchaseButton.addStyleName(AbstractField.CSS.cbtCommandButton());
		purchaseButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		purchaseButton.addStyleName(CSS.financialcommandButton());
		purchaseButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!blocked()) {JournalPopup.getInstance().show(Event.Type.Purchase, getValue(), reservationentities, eventjournalTable);}
			}
		});
		bar.add(purchaseButton);
		
		//-----------------------------------------------
		// Purchase For button
		//-----------------------------------------------
		final Button purchasesaleButton = new Button(CONSTANTS.purchasesaleButton());
		purchasesaleButton.setTitle(CONSTANTS.purchasesaleHelp());
		purchasesaleButton.addStyleName(AbstractField.CSS.cbtCommandButton());
		purchasesaleButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		purchasesaleButton.addStyleName(CSS.financialcommandButton());
		purchasesaleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!blocked()) {JournalPopup.getInstance().show(Event.Type.PurchaseSale, getValue(), reservationentities, eventjournalTable);}
			}
		});
		bar.add(purchasesaleButton);
		
		//-----------------------------------------------
		// Payment button
		//-----------------------------------------------
		final Button paymentButton = new Button(CONSTANTS.payButton());
		paymentButton.setTitle(CONSTANTS.payHelp());
		paymentButton.addStyleName(AbstractField.CSS.cbtCommandButton());
		paymentButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		paymentButton.addStyleName(CSS.financialcommandButton());
		paymentButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!blocked()) {JournalPopup.getInstance().show(Event.Type.Payment, getValue(), reservationentities, eventjournalTable);}
			}
		});
		bar.add(paymentButton);
		
		final Label balanceLabel =  new Label(CONSTANTS.balanceLabel());
		balanceLabel.addStyleName(CSS.balanceLabel());
		
		//-----------------------------------------------
		// Balance field
		//-----------------------------------------------
		balanceField = new DoubleField(this, null,
				null, //CONSTANTS.balanceLabel(),
				AbstractField.AF,
				tab++);
		balanceField.setEnabled(false);
		balanceField.setFieldStyle(CSS.balanceField());

		final Label reportLabel =  new Label(CONSTANTS.reportLabel());
		reportLabel.addStyleName(CSS.balanceLabel());
		
		//-----------------------------------------------
		// Agent Statement Report button
		//-----------------------------------------------
		final ReportButton agentStatement =  new ReportButton(null, CONSTANTS.agentstatementLabel(), tab++) {
			@Override
			public Report getReport() {
				Report report = new Report();;
				report.setOrganizationid(AbstractRoot.getOrganizationid());
				report.setActorid(AbstractRoot.getActorid());
				report.setDesign(Design.RESERVATION_AGENT_STATEMENT);
				report.setFromtoname(reservationField.getName());
				report.setLanguage(AbstractRoot.getLanguage());
				report.setState(Report.CREATED);
				return report;
			}
		};
		agentStatement.setLabelStyle(CSS.reportButton());
		agentStatement.setTitle(CONSTANTS.agentstatementHelp());
		
		//-----------------------------------------------
		// Customer Statement Report button
		//-----------------------------------------------
		final ReportButton customerStatement =  new ReportButton(null, CONSTANTS.customerstatementLabel(), tab++) {
			@Override
			public Report getReport() {
				Report report = new Report();;
				report.setOrganizationid(AbstractRoot.getOrganizationid());
				report.setActorid(AbstractRoot.getActorid());
				report.setDesign(Design.RESERVATION_CUSTOMER_STATEMENT);
				report.setFromtoname(reservationField.getName());
				report.setLanguage(AbstractRoot.getLanguage());
				report.setState(Report.CREATED);
				return report;
			}
		};
		customerStatement.setLabelStyle(CSS.reportButton());
		customerStatement.setTitle(CONSTANTS.customerstatementHelp());

		//-----------------------------------------------
		// Reservation Statement Report button
		//-----------------------------------------------
		final ReportButton reservationStatement =  new ReportButton(null, CONSTANTS.reservationstatementLabel(), tab++) {
			@Override
			public Report getReport() {
				Report report = new Report();;
				report.setOrganizationid(AbstractRoot.getOrganizationid());
				report.setActorid(AbstractRoot.getActorid());
				report.setDesign(Design.RESERVATION_STATEMENT);
				report.setFromtoname(reservationField.getName());
				report.setLanguage(AbstractRoot.getLanguage());
				report.setState(Report.CREATED);
				return report;
			}
		};
		reservationStatement.setLabelStyle(CSS.reportButton());
		reservationStatement.setTitle(CONSTANTS.reservationstatementHelp());

		final HorizontalPanel balancePanel = new HorizontalPanel();
		balancePanel.add(balanceLabel);
		balancePanel.add(balanceField);
		
		final HorizontalPanel reportPanel = new HorizontalPanel();
		reportPanel.add(reportLabel);
		reportPanel.add(agentStatement);
		reportPanel.add(customerStatement);
		reportPanel.add(reservationStatement);
		
		final VerticalPanel panel = new VerticalPanel();
		panel.add(balancePanel);
		panel.add(reportPanel);
		
		bar.add(panel);
		return bar;
	}

	/* Checks if the reservation is blocked for financial transactions.
	 * 
	 * @return if the reservation is blocked.
	 */
	private boolean blocked() {
		return (
				ifMessage (reservationField.noValue(), Level.ERROR, CONSTANTS.reservationError(), reservationField)
				|| ifMessage(reservationField.noValue(), Level.ERROR, CONSTANTS.reservationError(), reservationField)
				|| ifMessage(reservationField.hasValue(Reservation.State.Cancelled.name()), Level.ERROR, CONSTANTS.financestateError(), reservationField)
				|| ifMessage(reservationField.hasValue(Reservation.State.Closed.name()), Level.ERROR, CONSTANTS.financestateError(), reservationField)
				|| ifMessage(reservationField.hasValue(Reservation.State.Initial.name()), Level.ERROR, CONSTANTS.financestateError(), reservationField)
				|| ifMessage(reservationField.hasValue(Reservation.State.Final.name()), Level.ERROR, CONSTANTS.financestateError(), reservationField)
		);
	}
	
	/* 
	 * Creates the panel displayed when there are no financial transactions.
	 * 
	 * @return the empty panel.
	 */
	private Widget eventactiontableEmpty() {
		FlowPanel panel = new FlowPanel();
		panel.add(new HTML(CONSTANTS.tableEmpty()));
		panel.addStyleName(AbstractField.CSS.cbtTableFieldEmpty());
		panel.add(new Image(BUNDLE.eventactiontableEmpty()));
		return panel;
	}
	
	/* 
	 * Creates the financial transaction table stack panel.
	 * 
	 * @return the financial transaction table stack panel.
	 */
	private FlowPanel createFinancial() {

		final FlowPanel panel = new FlowPanel();
		panel.add(createFinancialcommands());
		
		//-----------------------------------------------
		// Financial Transaction selection handler
		//-----------------------------------------------
		final NoSelectionModel<EventJournal> selectionModel = new NoSelectionModel<EventJournal>();
		SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				JournalPopup.getInstance().show(selectionModel.getLastSelectedObject(), reservationentities, eventjournalTable);
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// Financial Transaction table
		//-----------------------------------------------
		eventjournalTable = new TableField<EventJournal>(this, null, 
				new ReservationEventJournalTable(),
				selectionModel, 
				EVENTJOURNAL_ROWS,
				tab++);

		eventjournalTable.setEmptyValue(eventactiontableEmpty());

		eventjournalTable.setTableError(new TableError() {
			@Override
			public boolean error() {return (
				reservationField.noValue()
			);}
		});

		eventjournalTable.setTableExecutor(new TableExecutor<ReservationEventJournalTable>() {
			public void execute(ReservationEventJournalTable action) {
				action.setId(reservationField.getValue());
				reservationBalance.execute(true);
			}
		});
		
		int col = 0;

		//-----------------------------------------------
		// Change button
		//-----------------------------------------------
		Column<EventJournal, EventJournal> changeButton = new Column<EventJournal, EventJournal>( 
				new ActionCell<EventJournal>(AbstractField.CONSTANTS.allChange(), AbstractField.CSS.cbtTableFieldChangeButton(),
						new Delegate<EventJournal>(){
					public void execute( EventJournal eventaction ) {
						JournalPopup.getInstance().show(eventaction, reservationentities, eventjournalTable);
					}
				}
				)
		)
		{
			public EventJournal getValue(EventJournal eventjournal){return eventjournal;}//TODO: selectForm(row); 
		};

		eventjournalTable.addColumn(changeButton);

		//-----------------------------------------------
		// Process column
		//-----------------------------------------------
		Column<EventJournal, String> process = new Column<EventJournal, String>(new TextCell()) {
			@Override
			public String getValue( EventJournal eventjournal ) {return eventjournal.getProcess();}
		};
		eventjournalTable.addStringColumn(process, CONSTANTS.eventjournalHeaders()[col++], EventJournal.PROCESS);

		//-----------------------------------------------
		// Entity column
		//-----------------------------------------------
		Column<EventJournal, String> entity = new Column<EventJournal, String>( new TextCell()) {
			@Override
			public String getValue( EventJournal eventjournal ) {return eventjournal.getEntityname(15);}
		};
		eventjournalTable.addStringColumn(entity, CONSTANTS.eventjournalHeaders()[col++], EventJournal.STATE);

		//-----------------------------------------------
		// Name column
		//-----------------------------------------------
		Column<EventJournal, String> name = new Column<EventJournal, String>( new TextCell() ) {
			@Override
			public String getValue( EventJournal eventjournal ) {return eventjournal.getName();}
		};
		eventjournalTable.addStringColumn(name, CONSTANTS.eventjournalHeaders()[col++], EventJournal.NAME);

		//-----------------------------------------------
		// Date column
		//-----------------------------------------------
		Column<EventJournal, Date> date = new Column<EventJournal, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( EventJournal eventjournal ) {return Time.getDateClient(eventjournal.getDate());}
		};
		eventjournalTable.addDateColumn(date, CONSTANTS.eventjournalHeaders()[col++], EventJournal.DATE);

		//-----------------------------------------------
		// Debit Amount column
		//-----------------------------------------------
		Column<EventJournal, Number> debitAmount = new Column<EventJournal, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( EventJournal eventjournal ) {return eventjournal.getDebitamount();}
		};
		eventjournalTable.addNumberColumn( debitAmount, CONSTANTS.eventjournalHeaders()[col++], EventJournal.DEBITAMOUNT);

		//-----------------------------------------------
		// Credit Amount column
		//-----------------------------------------------
		Column<EventJournal, Number> creditAmount = new Column<EventJournal, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( EventJournal eventjournal ) {return eventjournal.getCreditamount();}
		};
		eventjournalTable.addNumberColumn( creditAmount, CONSTANTS.eventjournalHeaders()[col++], EventJournal.CREDITAMOUNT);

		//-----------------------------------------------
		// Currency column
		//-----------------------------------------------
		Column<EventJournal, String> currency = new Column<EventJournal, String>( new TextCell() ) {
			@Override
			public String getValue( EventJournal eventjournal ) {return eventjournal.getCurrency();}
		};
		eventjournalTable.addStringColumn(currency, CONSTANTS.eventjournalHeaders()[col++], EventJournal.UNIT);

		//-----------------------------------------------
		// Description column
		//-----------------------------------------------
		Column<EventJournal, String> description = new Column<EventJournal, String>( new TextCell() ) {
			@Override
			public String getValue( EventJournal eventjournal ) {return eventjournal.getDescription();}
		};
		eventjournalTable.addStringColumn(description, CONSTANTS.eventjournalHeaders()[col++], EventJournal.DESCRIPTION);
		eventjournalTable.addStyleName(CSS.eventactionStyle());

		final ScrollPanel scroll = new ScrollPanel();
		scroll.addStyleName(CSS.scrollStyle());
		scroll.add(eventjournalTable); //TODO:
		panel.add(scroll);
		return panel;
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
				|| ifMessage(customerField.noValue() && agentField.noValue(), Level.ERROR, CONSTANTS.agentError(), customerField)
				|| ifMessage(dateField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), dateField)
				|| ifMessage(costField.noValue(), Level.ERROR, CONSTANTS.priceError(), costField)
				|| ifMessage(quoteField.noValue(), Level.ERROR, CONSTANTS.quoteError(), quoteField)
				|| ifMessage(adultField.getValue() < 1, Level.ERROR, CONSTANTS.adultError(), adultField)
				|| ifMessage(flatField.isVisible() && flatField.noValue(), Level.ERROR, CONSTANTS.flatError(), flatField)
				|| ifMessage(quoteField.noUnitvalue(), Level.ERROR, AbstractField.CONSTANTS.errCurrency(), quoteField)
				|| ifMessage(noprice, Level.ERROR, CONSTANTS.priceError(), costField)
				|| ifMessage(hasCollisions(), Level.ERROR, CONSTANTS.collisionError() + getCollisionlist(), fromtodateField)
		);
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Cancel Reservation
		//-----------------------------------------------
		reservationCancel = new GuardedRequest<Reservation>() {
			protected String popup() {return CONSTANTS.errCancelOK();}
			public void cancel() {state = oldstate;}
			protected boolean error() {return ifMessage(reservationField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), reservationField);}
			protected void send() {
				serviceGrid.setValue(null);
				super.send(getValue(new ReservationUpdate()));
			}
			protected void receive(Reservation reservation) {setValue(reservation);}
		};

		//-----------------------------------------------
		// Close Reservation
		//-----------------------------------------------
		reservationClose = new GuardedRequest<Reservation>() {
			protected String popup() {return CONSTANTS.errCloseOK();}
			public void cancel() {state = oldstate;}
			protected boolean error() {return ifMessage(reservationField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), reservationField);}
			protected void send() {super.send(getValue(new ReservationUpdate()));}
			protected void receive(Reservation reservation) {setValue(reservation);}
		};

		//-----------------------------------------------
		// Create Reservation
		//-----------------------------------------------
		reservationCreate = new GuardedRequest<Reservation>() {
			protected String popup() {return hasChanged() ? AbstractField.CONSTANTS.errChangeOK() : null;}
			public void cancel() {state = oldstate;}
			protected boolean error() {return ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), reservationField);}
			protected void send() {
				onReset(Reservation.State.Initial.name());
				super.send(getValue(new ReservationCreate()));
			}
			protected void receive(Reservation reservation) {setValue(reservation);}
		};

		//-----------------------------------------------
		// Initialize Reservation
		//-----------------------------------------------
		reservationReset = new GuardedRequest<Reservation>() {
			protected boolean error() {return AbstractRoot.noOrganizationid();}
			protected void send() {super.send(getValue(new ReservationCreate()));}
			protected void receive(Reservation reservation) {
				setValue(reservation);
				reservationPrice.execute();
			}
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
			protected String popup() {return notState(Reservation.State.Initial.name()) && notState(Reservation.State.Provisional.name()) && productField.hasChanged() ? CONSTANTS.productchangeError() : null;}
			public void cancel() {state = oldstate;}
			protected boolean error() {return updateError();}
			protected void send() {super.send(getValue(new ReservationUpdate()));}
			protected void receive(Reservation reservation) {setValue(reservation);}
		};

		//-----------------------------------------------
		// Undo Reservation
		//-----------------------------------------------
		reservationUndo = new GuardedRequest<Reservation>() {
			protected boolean error() {return updateError();}
			protected void send() {super.send(getValue(new ReservationUndo()));}
			protected void receive(Reservation reservation) {setValue(reservation);}
		};

		//-----------------------------------------------
		// Get Guest Reservation History
		//-----------------------------------------------
		reservationHistory = new GuardedRequest<Table<Reservation>>() {
			protected boolean error() {return (customerField.noValue());}
			protected void send() {super.send(getValue(new ReservationHistory()));}
			protected void receive(Table<Reservation> response) {
				StringBuilder sb = new StringBuilder();
				sb.append(CONSTANTS.historyLabel());
				if (response == null || response.noValue()) {sb.append(CONSTANTS.historynoneLabel());}
				else {
					ArrayList<Reservation> reservations = response.getValue();
					for (Reservation reservation : reservations) {
						sb.append(CONSTANTS.historynameLabel());
						sb.append(reservation.getName());
						sb.append(" ").append(CONSTANTS.historyproductnameLabel());
						sb.append(" ").append(reservation.getProductname());
						sb.append(" ").append(CONSTANTS.historyfromdateLabel());
						sb.append(" ").append(AbstractRoot.getDF().format(Time.getDateClient(reservation.getFromdate())));
						sb.append(" ").append(CONSTANTS.historytodateLabel());
						sb.append(" ").append(AbstractRoot.getDF().format(Time.getDateClient(reservation.getTodate())));
						sb.append(" ").append(CONSTANTS.historyquoteLabel());
						sb.append(" ").append(AbstractField.AF.format(reservation.getQuote()));
						sb.append(" ").append(reservation.getCurrency());
					}
				}
				historytextField.setValue(sb.toString());
			}
		};

		//-----------------------------------------------
		// Get Reservation Financial Balance
		//-----------------------------------------------
		reservationBalance = new GuardedRequest<DoubleResponse>() {
			protected boolean error() {return reservationField.noValue();}
			protected void send() {super.send(getValue(new ReservationEventJournalBalance()));}
			protected void receive(DoubleResponse response) {balanceField.setValue(response == null || response.noValue() ? 0.0 : response.getValue());}
		};

		//-----------------------------------------------
		// Read Reservation Prices
		//-----------------------------------------------
		reservationPrice = new GuardedRequest<PriceResponse>() {
			protected boolean error() {
				return (
						AbstractRoot.noOrganizationid()
						|| AbstractRoot.noActorid()
						|| fromtodateField.noValue()
						|| fromtodateField.noTovalue()
						|| fromtodateField.isEndBeforeStart()
						|| productField.noValue()
				);
			}
			protected void send() {super.send(getValue(new ReservationPrice()));}
			protected void receive(PriceResponse response) {
				if (response.noValue()) {
					//AbstractField.addMessage(Level.ERROR, CONSTANTS.priceError(), priceField);
					noprice = true;
				}
				else if (response.getCost() < 0.0){
					AbstractField.addMessage(Level.ERROR, CONSTANTS.suspendedError(), quoteField);
					noprice = true;
				}
				else {
					depositField.setValue(response.getDeposit());
					costField.setValue(response.getCost());
					extraField.setValue(response.getExtra());
					quoteField.setValue(response.getQuote());
					quoteField.setUnitvalue(response.getCurrency());
					featureTable.setValue(response.getQuotedetail());
					discountField.setValue(response.getDiscount());
					if (reservationentities != null) {reservationentities.getReservation().setDiscountfactor(response.getDiscountfactor());}
					noprice = false;
					if (!ifMessage(response.hasCollisions(), Level.ERROR, CONSTANTS.collisionError() + getCollisionlist(response.getCollisions()), fromtodateField))
					{ifMessage(response.hasAlerts(), Level.TERSE, getAlertlist(response.getAlerts()), fromtodateField);}
				}
				collisions = response.getCollisions();
			}
		};

		//-----------------------------------------------
		// Adjust Reservation Prices
		//-----------------------------------------------
		reservationPriceAdjust = new GuardedRequest<PriceResponse>() {
			protected boolean error() {
				return (
						AbstractRoot.noOrganizationid()
						|| AbstractRoot.noActorid()
						|| fromtodateField.noValue()
						|| fromtodateField.noTovalue()
						|| fromtodateField.isEndBeforeStart()
						|| productField.noValue()
				);
			}
			protected void send() {super.send(getValue(new ReservationPriceAdjust()));}
			protected void receive(PriceResponse response) {
//				if (response.noValue()) {
//					//AbstractField.addMessage(Level.ERROR, CONSTANTS.priceError(), priceField);
//					noprice = true;
//				}
//				else if (response.getCost() < 0.0){
//					AbstractField.addMessage(Level.ERROR, CONSTANTS.suspendedError(), quoteField);
//					noprice = true;
//				}
//				else {
//					depositField.setValue(response.getDeposit());
					costField.setValue(response.getCost());
					extraField.setValue(response.getExtra());
					quoteField.setValue(response.getQuote());
					quoteField.setUnitvalue(response.getCurrency());
					featureTable.setValue(response.getQuotedetail());
					discountField.setValue(response.getDiscount());
//					reservationentities.getReservation().setDiscountfactor(response.getDiscountfactor());
//					noprice = false;
//					ifMessage(response.hasCollisions(), Level.TERSE, CONSTANTS.collisionError() + getCollisionlist(response.getCollisions()), fromtodateField);
//				}
//				collisions = response.getCollisions();
			}
		};

		//-----------------------------------------------
		// Read Reservation Entities (Agent, Guest, Property)
		//-----------------------------------------------
		reservationentitiesRead = new GuardedRequest<ReservationEntities>() {
			protected boolean error() {return reservationField.noValue();}
			protected void send() {super.send(getValue(new ReservationEntities()));}
			protected void receive(ReservationEntities response) {
				reservationentities = response;
				servicepayerField.setIds(response.getIds());
				boolean isMultiple = response.noProduct() ? false : response.getProduct().hasQuantity();
				flatField.setVisible(isMultiple);
				quantityField.setVisible(isMultiple);
				quantityField.setRange(1, response.noProduct() ? 1 : response.getProduct().getQuantity());
			}
		};

		//-----------------------------------------------
		// Read Service Price
		//-----------------------------------------------
		servicepriceRead = new GuardedRequest<ServicepriceResponse>() {
			protected boolean error() {return serviceproviderField.noValue();}
			protected void send() {super.sendaction();}
			protected void receive(ServicepriceResponse response) {
				((DoubleField)serviceGrid.getWidget(response.getRow(), 4)).setValue(response == null || response.noValue() ? 0.0 : response.getValue());}
		};

		//-----------------------------------------------
		// Update Service Task
		//-----------------------------------------------
		taskUpdate = new GuardedRequest<Task>() {
			protected boolean error() {return false;}
			protected void send() {super.sendaction();}
			protected void receive(Task task) {}
		};
	}
}
