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
import net.cbtltd.client.field.CommandButton;
import net.cbtltd.client.field.DateField;
import net.cbtltd.client.field.DatespanField;
import net.cbtltd.client.field.DoubleField;
import net.cbtltd.client.field.MoneyField;
import net.cbtltd.client.field.ReportButton;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.field.table.ActionCell;
import net.cbtltd.client.field.table.ActionCell.Delegate;
import net.cbtltd.client.field.table.ActionHeader;
import net.cbtltd.client.field.table.TableError;
import net.cbtltd.client.field.table.TableExecutor;
import net.cbtltd.client.panel.PartyPopup;
import net.cbtltd.client.panel.TaskPopup;
import net.cbtltd.client.panel.ValuePopup;
import net.cbtltd.client.resource.lease.LeaseBundle;
import net.cbtltd.client.resource.lease.LeaseConstants;
import net.cbtltd.client.resource.lease.LeaseCssResource;
import net.cbtltd.shared.Design;
import net.cbtltd.shared.DoubleResponse;
import net.cbtltd.shared.Lease;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Report;
import net.cbtltd.shared.Rule;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Task;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.Value;
import net.cbtltd.shared.api.HasResetId;
import net.cbtltd.shared.journal.EventJournal;
import net.cbtltd.shared.lease.LeaseCreate;
import net.cbtltd.shared.lease.LeaseEntities;
import net.cbtltd.shared.lease.LeaseEventJournalBalance;
import net.cbtltd.shared.lease.LeaseEventJournalTable;
import net.cbtltd.shared.lease.LeaseRead;
import net.cbtltd.shared.lease.LeaseRuleTable;
import net.cbtltd.shared.lease.LeaseUpdate;
import net.cbtltd.shared.party.EmployeeNameId;
import net.cbtltd.shared.task.MaintenanceTaskTable;

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
import com.google.gwt.user.client.ui.Button;
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

/** The Class LeaseForm is to display and change information about a lease. */
public class LeaseForm 
extends AbstractForm<Lease> {

	private static final LeaseConstants CONSTANTS = GWT.create(LeaseConstants.class);
	private static final LeaseBundle BUNDLE = LeaseBundle.INSTANCE;
	private static final LeaseCssResource CSS = BUNDLE.css();

	private static final int EVENTJOURNAL_ROWS = 200;
	private static final int MAINTENANCE_ROWS = 16;
	private static final int RULE_ROWS = 16;

	private static GuardedRequest<Lease> leaseCreate;
	private static GuardedRequest<Lease> leaseReset;
	private static GuardedRequest<Lease> leaseRead;
	private static GuardedRequest<Lease> leaseUpdate;
	private static GuardedRequest<Lease> leaseCancel;
	private static GuardedRequest<Lease> leaseClose;
	private static GuardedRequest<DoubleResponse> leaseBalance;
	private static GuardedRequest<LeaseEntities> leaseentitiesRead;
	
	private static SuggestField leaseField;
	private static TextField idField;
	private static TextField stateField;
	private static DateField dateField;
	private static DateField duedateField;
	private static DatespanField fromtodateField;
	private static SuggestField customerField;
	private static SuggestField agentField;
	private static SuggestField marketField;
	private static SuggestField outcomeField;
	private static SuggestField actorField;
	private static SuggestField productField;
	private static MoneyField priceField;
	private static MoneyField depositField;
	private static DoubleField balanceField;

	private static StackLayoutPanel stackPanel;
	private static TextAreaField leasetextField;
	private static TextAreaField customertextField;

	private static TableField<Rule> ruleTable;
	private static TableField<Task> maintenanceTable;
	private static TableField<EventJournal> eventjournalTable;

	private static LeaseEntities leaseentities;

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#permission()
	 */
	@Override
	public short[] permission(){return AccessControl.LEASE_PERMISSION;}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#getValue()
	 */
	@Override
	public Lease getValue() {return getValue(new Lease());}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {
		if (leaseField.sent(change)) {leaseRead.execute();}
		else if (customerField.sent(change)) {
			customertextField.setText(Lease.getCustomerText(AbstractRoot.getOrganizationid(), customerField.getValue(), AbstractRoot.getLanguage()));
		}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onReset(net.cbtltd.shared.api.HasNameId)
	 */
	@Override
	public void onReset(HasResetId reset) {
		if (reset != null && reset.getResetid() != null && !reset.getResetid().isEmpty()) {
			leaseField.setValue(reset.getResetid());
			leaseRead.execute();
		}
		else if (reset instanceof Lease) {
			setValue((Lease) reset);
			leaseReset.execute();
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
				if (event.getSelectedItem() != 0 && leaseField.noValue()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.leaseError(), leaseField);}
				refreshStackPanel();
			}
		});

		stackPanel.addStyleName(CSS.stackStyle());
		stackPanel.add(createText(), CONSTANTS.textLabel(), 1.5);
		stackPanel.add(createMaintenance(), CONSTANTS.maintenanceLabel(), 1.5);
		stackPanel.add(createFinancial(), CONSTANTS.financeLabel(), 1.5);
		onRefresh();
		onReset(Lease.State.Created.name());
	}

	/* Refreshes the open stack panel */
	private void refreshStackPanel() {
		switch (stackPanel.getVisibleIndex()) {
		case 0: ruleTable.execute(); break;
		case 1: maintenanceTable.execute(); break;
		case 2: eventjournalTable.execute(); break;
		}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#getValue(net.cbtltd.shared.api.HasState)
	 */
	private Lease getValue(Lease lease) {
		lease.setState(state);
		lease.setId(leaseField.getValue());
		if (lease.noOrganizationid()) {lease.setOrganizationid(AbstractRoot.getOrganizationid());}
		lease.setActorid(actorField.getValue());
		lease.setUnit(Unit.DAY);
		lease.setCustomerid(customerField.getValue());
		lease.setAgentid(agentField.getValue());
		lease.setMarket(marketField.getValue());
		lease.setOutcome(outcomeField.getValue());
		lease.setDate(Time.getDateServer(dateField.getValue()));
		lease.setDuedate(Time.getDateServer(duedateField.getValue()));
		lease.setFromdate(Time.getDateServer(fromtodateField.getValue()));
		lease.setTodate(Time.getDateServer(fromtodateField.getTovalue()));
		
		lease.setProcess(NameId.Type.Lease.name());
		lease.setProductid(productField.getValue());
		lease.setCustomerText(customertextField.getText());
		lease.setNotes(leasetextField.getValue());
		lease.setUnit(Unit.DAY); //unitField.getValue());
		lease.setPrice(priceField.getValue());
		lease.setCurrency(priceField.getUnitvalue());
		lease.setDeposit(depositField.getValue());
		
		//Window.alert("getValue " + lease);
		return lease;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#setValue(net.cbtltd.shared.api.HasState)
	 */
	public void setValue(Lease lease) {
		if (lease == null) {onReset(Lease.State.Created.name());}
		else {
			setResetting(true);
			onStateChange(lease.getState());
			stateField.setValue(lease.getState());
			leaseField.setValue(lease.getId());
			leaseField.setEnabled(!lease.hasState(Lease.State.Initial.name()));
			idField.setText(lease.getId());
			dateField.setValue(Time.getDateClient(lease.getDate()));
			duedateField.setValue(Time.getDateClient(lease.getDuedate()));
			fromtodateField.setValue(Time.getDateClient(lease.getFromdate()));
			fromtodateField.setTovalue(Time.getDateClient(lease.getTodate()));
			customerField.setValue(lease.getCustomerid());
			agentField.setValue(lease.getAgentid());
			actorField.setValue(lease.getActorid());
			marketField.setValue(lease.getMarket());
			outcomeField.setValue(lease.getOutcome());
			productField.setValue(lease.getProductid());
			leasetextField.setValue(lease.getNotes());
			priceField.setValue(lease.getPrice());
			priceField.setUnitvalue(lease.getCurrency());
			depositField.setValue(lease.getDeposit());

			if (lease.noCustomerid()) {customertextField.setValue(Model.BLANK);}
			else {
				customertextField.setText(Lease.getCustomerText(AbstractRoot.getOrganizationid(), customerField.getValue(), AbstractRoot.getLanguage()));
			}
			
			refreshStackPanel();
			leaseentitiesRead.execute();
			setResetting(false);
		}
	}

	/*
	 * Gets the specified lease entities action with its attribute values set.
	 *
	 * @param action the specified lease entities action.
	 * @return the lease entities action with its attribute values set.
	 */
	private LeaseEntities getValue(LeaseEntities action) {
		action.setLease(getValue());
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
		final Label title = new Label(CONSTANTS.leaseLabel());
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);

		//-----------------------------------------------
		// Lease number field
		//-----------------------------------------------
		leaseField = new SuggestField(this, null,
				new NameIdAction(Service.RESERVATION),
				CONSTANTS.leasenameLabel(),
				20,
				tab++);
		leaseField.setFieldHalf();
		leaseField.setHelpText(CONSTANTS.leaseHelp());

		//-----------------------------------------------
		// Lease ID field
		//-----------------------------------------------
		idField = new TextField(this, null,
				CONSTANTS.idLabel(),
				tab++);
		idField.setEnabled(false);
		idField.setVisible(AbstractRoot.hasPermission(AccessControl.PARTNER_ROLES));
		idField.setFieldHalf();
		idField.setHelpText(CONSTANTS.idHelp());
		
		HorizontalPanel ri = new HorizontalPanel();
		ri.add(leaseField);
		ri.add(idField);
		form.add(ri);

		//-----------------------------------------------
		// Date is when the lease was created
		//-----------------------------------------------
		dateField = new DateField(this, null,
				CONSTANTS.dateLabel(),
				tab++);
		dateField.setEnabled(false);
		dateField.setHelpText(CONSTANTS.dateHelp());
		form.add(dateField);

		//-----------------------------------------------
		// State field
		//-----------------------------------------------
		stateField = new TextField(this, null,
				CONSTANTS.stateLabel(),
				tab++);
		stateField.setEnabled(false);
		stateField.setFieldHalf();
		stateField.setHelpText(CONSTANTS.stateHelp());
		form.add(stateField);

		//-----------------------------------------------
		// Due Date is when the next lease state is due
		//-----------------------------------------------
		duedateField = new DateField(this, null,
				CONSTANTS.duedateLabel(),
				tab++);
		duedateField.setHelpText(CONSTANTS.duedateHelp());
		form.add(duedateField);
		
		//-----------------------------------------------
		// Customer (Lesee) field
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
		marketField.setState(NameId.Type.Lease.name() + AbstractRoot.getOrganizationid());
		marketField.setHelpText(CONSTANTS.marketHelp());

		final Image starterButton = new Image(AbstractField.BUNDLE.plus());
		starterButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ValuePopup.getInstance().show(Value.Type.MarketingStarter, CONSTANTS.marketLabel(), NameId.Type.Lease.name() + AbstractRoot.getOrganizationid(), marketField);
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
		outcomeField.setState(NameId.Type.Lease.name() + AbstractRoot.getOrganizationid());
		outcomeField.setHelpText(CONSTANTS.outcomeHelp());

		final Image outcomeButton = new Image(AbstractField.BUNDLE.plus());
		outcomeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ValuePopup.getInstance().show(Value.Type.MarketingOutcome, CONSTANTS.outcomeLabel(), NameId.Type.Lease.name() + AbstractRoot.getOrganizationid(), outcomeField);
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
		// Start and End Dates
		//-----------------------------------------------
		fromtodateField = new DatespanField(this, null,
				CONSTANTS.fromtodateLabel(),
				tab++);
		fromtodateField.setHelpText(CONSTANTS.fromtodateHelp());
		form.add(fromtodateField);

		//-----------------------------------------------
		// Price field
		//-----------------------------------------------
		priceField = new MoneyField(this, null,
				CONSTANTS.priceLabel(),
				tab++);
		priceField.setEnabled(AbstractRoot.writeable(AccessControl.QUOTE_PERMISSION));
		priceField.setUnitenabled(false);
		priceField.setDefaultUnitvalue(AbstractRoot.getCurrency());
		priceField.setHelpText(CONSTANTS.priceHelp());

		//-----------------------------------------------
		// Deposit field
		//-----------------------------------------------
		depositField = new MoneyField(this, null,
				CONSTANTS.depositLabel(),
				tab++);
		depositField.setUnitenabled(false);
		depositField.setHelpText(CONSTANTS.depositHelp());
		
		HorizontalPanel dd = new HorizontalPanel();
		form.add(dd);
		dd.add(priceField);
		dd.add(depositField);

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

		LocalRequest cancelRequest = new LocalRequest() {protected void perform() {onReset(Lease.State.Created.name());}};

		//-----------------------------------------------
		// Create button
		//-----------------------------------------------
		CommandButton createButton = new CommandButton(this, AbstractField.CONSTANTS.allCreate(), leaseCreate, tab++);
		createButton.addStyleName(AbstractField.CSS.cbtGradientGreen());
		createButton.setTitle(CONSTANTS.createHelp());
		bar.add(createButton);
		
		//-----------------------------------------------
		// Close button
		//-----------------------------------------------
		CommandButton closeButton = new CommandButton(this, CONSTANTS.closeLabel(), leaseClose, tab++);
		closeButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		closeButton.setTitle(CONSTANTS.closeHelp());
		bar.add(closeButton);
		
		//-----------------------------------------------
		// Save button
		//-----------------------------------------------
		CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), leaseUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.setTitle(CONSTANTS.saveHelp());
		bar.add(saveButton);
		
		//-----------------------------------------------
		// Cancel Lease button
		//-----------------------------------------------
		CommandButton axedButton = new CommandButton(this, AbstractField.CONSTANTS.allCancel(), leaseCancel, tab++);
		axedButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		axedButton.setTitle(CONSTANTS.axedHelp());
		bar.add(axedButton);
		
		//-----------------------------------------------
		// Restore button
		//-----------------------------------------------
		CommandButton restoreButton = new CommandButton(this, AbstractField.CONSTANTS.allRestore(), leaseUpdate, tab++);
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
		fsm.add(new Transition(Lease.State.Initial.name(), saveButton, Lease.State.Created.name()));
		fsm.add(new Transition(Lease.State.Initial.name(), closeButton, Lease.State.Closed.name()));
		fsm.add(new Transition(Lease.State.Initial.name(), cancelButton, Lease.State.Created.name()));

		fsm.add(new Transition(Lease.State.Created.name(), createButton, Lease.State.Initial.name()));
		fsm.add(new Transition(Lease.State.Created.name(), saveButton, Lease.State.Created.name()));
		fsm.add(new Transition(Lease.State.Created.name(), axedButton, Lease.State.Cancelled.name()));

		fsm.add(new Transition(Lease.State.Cancelled.name(), createButton, Lease.State.Initial.name()));
		fsm.add(new Transition(Lease.State.Cancelled.name(), saveButton, Lease.State.Cancelled.name()));
		fsm.add(new Transition(Lease.State.Cancelled.name(), restoreButton, Lease.State.Created.name()));

		fsm.add(new Transition(Lease.State.Closed.name(), createButton, Lease.State.Initial.name()));
		fsm.add(new Transition(Lease.State.Closed.name(), saveButton, Lease.State.Closed.name()));
		fsm.add(new Transition(Lease.State.Closed.name(), restoreButton, Lease.State.Created.name()));

		return bar;
	}

	/* 
	 * Creates the text stack panel.
	 * 
	 * @return the text stack panel.
	 */
	private VerticalPanel createText() {
		final VerticalPanel form = new VerticalPanel();
		final HorizontalPanel panel = new HorizontalPanel();
		form.add(panel);
		
		//-----------------------------------------------
		// Lease Notes field
		//-----------------------------------------------
		leasetextField = new TextAreaField(this, null,
				 CONSTANTS.notesLabel(),
				tab++);
		leasetextField.setMaxLength(3000);
		leasetextField.setFieldStyle(CSS.descriptionField());
		leasetextField.setDefaultValue(CONSTANTS.notesEmpty());
		leasetextField.setHelpText(CONSTANTS.notesHelp());
		form.add(leasetextField);

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
		
		form.add(createTextcommands());
		return form;
	}
	
	/* 
	 * Creates the hospitality commands bar.
	 * 
	 * @return the hospitality commands bar.
	 */
	private HorizontalPanel createTextcommands() {
		final HorizontalPanel bar = new HorizontalPanel();
		

		//-----------------------------------------------
		// Quotation Report button
		//-----------------------------------------------
		final ReportButton quotationReport = new ReportButton(null, CONSTANTS.quotationLabel(), tab++) {
			public Report getReport() {
				Report report = new Report();
				report.setOrganizationid(AbstractRoot.getOrganizationid());
				report.setActorid(AbstractRoot.getActorid());
				report.setDesign(Design.RESERVATION_QUOTATION);
				report.setFromtoname(leaseField.getName());
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
				report.setFromtoname(leaseField.getName());
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
				report.setFromtoname(leaseField.getName());
				report.setLanguage(AbstractRoot.getLanguage());
				report.setState(Report.CREATED);
				return report;
			}
		};
		bar.add(detailReport);

		return bar;
	}

	/* 
	 * Creates the panel displayed when there are no rules.
	 * 
	 * @return the empty panel.
	 */
	private Widget ruletableEmpty() {
		FlowPanel panel = new FlowPanel();
		Button emptyButton = new Button(AbstractField.CONSTANTS.allCreate());
		emptyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (leaseField.noValue()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.leaseError(), leaseField);}
//TODO:				else {RulePopup.getInstance().show(Rule.Type.Rule, leaseentities, ruleTable);}
			}
		});
		panel.add(emptyButton);
		
		panel.add(new HTML(CONSTANTS.ruletableEmpty()));
		panel.addStyleName(AbstractField.CSS.cbtTableFieldEmpty());
		panel.add(new Image(BUNDLE.ruletableEmpty()));
		return panel;
	}
	
	/* 
	 * Creates the rules stack panel.
	 * 
	 * @return the rules stack panel.
	 */
	private TableField<Rule> createRule() {

		//-----------------------------------------------
		// Rule selection handler
		//-----------------------------------------------
		final NoSelectionModel<Rule> selectionModel = new NoSelectionModel<Rule>();
		SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
//TODO:				RulePopup.getInstance().show(selectionModel.getLastSelectedObject(), ruleTable);
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// Rule table
		//-----------------------------------------------
		ruleTable = new TableField<Rule>(this, null, 
				new LeaseRuleTable(),
				selectionModel, 
				RULE_ROWS,
				tab++);

		ruleTable.setEmptyValue(ruletableEmpty());
//		ruleTable.setOrderby(Rule.DUEDATE);

		ruleTable.setTableError(new TableError() {
			@Override
			public boolean error() {return (
				leaseField.noValue()
			);}
		});

		ruleTable.setTableExecutor(new TableExecutor<LeaseRuleTable>() {
			public void execute(LeaseRuleTable action) {
				action.setId(leaseField.getValue());
			}
		});
		
		int col = 0;

		//-----------------------------------------------
		// Change button
		//-----------------------------------------------
		final Column<Rule, Rule> changeButton = new Column<Rule, Rule>( 
				new ActionCell<Rule>(AbstractField.CONSTANTS.allChange(), AbstractField.CSS.cbtTableFieldChangeButton(),
						new Delegate<Rule>(){
					public void execute( Rule rule ) {
//TODO:						RulePopup.getInstance().show(rule, ruleTable);
					}
				}
				)
		)
		{
			public Rule getValue(Rule rule){return rule;}//TODO: selectForm(row); 
		};

		//-----------------------------------------------
		// Create button
		//-----------------------------------------------
		final ActionHeader<Rule> createButton = new ActionHeader<Rule>(
				new ActionCell<Rule>(
						AbstractField.CONSTANTS.allCreate(), 
						AbstractField.CSS.cbtTableFieldCreateButton(),
						new Delegate<Rule>(){
							public void execute(Rule rule ) {
								if (leaseField.noValue()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.leaseError(), leaseField);}
//TODO:								else {RulePopup.getInstance().show(Rule.Type.Rule, leaseentities, ruleTable);}
							}
						}
				)
		)
		{
			public Rule getValue(Rule rule){
				return rule;
			}
		};

		ruleTable.addColumn(changeButton, createButton);

		//-----------------------------------------------
		// Name column
		//-----------------------------------------------
		Column<Rule, String> name = new Column<Rule, String>( new TextCell() ) {
			@Override
			public String getValue( Rule rule ) {return rule.getName();}
		};
		ruleTable.addStringColumn(name, CONSTANTS.ruleHeaders()[col++], Rule.NAME);

		//-----------------------------------------------
		// Quantity column
		//-----------------------------------------------
		Column<Rule, Number> amount = new Column<Rule, Number>(new NumberCell(AbstractField.QF)) {
			@Override
			public Double getValue( Rule rule ) {return rule.getQuantity();}
		};
		ruleTable.addNumberColumn( amount, CONSTANTS.ruleHeaders()[col++], Rule.QUANTITY);

		//-----------------------------------------------
		// Unit column
		//-----------------------------------------------
		Column<Rule, String> currency = new Column<Rule, String>( new TextCell() ) {
			@Override
			public String getValue( Rule rule ) {return rule.getUnit();}
		};
		ruleTable.addStringColumn(currency, CONSTANTS.ruleHeaders()[col++]);

		//-----------------------------------------------
		// Value column
		//-----------------------------------------------
		Column<Rule, Number> value = new Column<Rule, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( Rule rule ) {return rule.getValue();}
		};
		ruleTable.addNumberColumn( value, CONSTANTS.ruleHeaders()[col++], Rule.VALUE);

		//-----------------------------------------------
		// From Date column
		//-----------------------------------------------
		Column<Rule, Date> fromdate = new Column<Rule, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( Rule rule ) {return Time.getDateClient(rule.getFromdate());}
		};
		ruleTable.addDateColumn(fromdate, CONSTANTS.ruleHeaders()[col++], Rule.FROMDATE);

		//-----------------------------------------------
		// To Date column
		//-----------------------------------------------
		Column<Rule, Date> todate = new Column<Rule, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( Rule rule ) {return Time.getDateClient(rule.getTodate());}
		};
		ruleTable.addDateColumn(todate, CONSTANTS.ruleHeaders()[col++], Rule.TODATE);

		return ruleTable;
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
				if (leaseField.noValue()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.leaseError(), leaseField);}
//TODO:				else {TaskPopup.getInstance().show(Task.Type.Maintenance, leaseentities, maintenanceTable);}
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
				leaseField.noValue()
			);}
		});

		maintenanceTable.setTableExecutor(new TableExecutor<MaintenanceTaskTable>() {
			public void execute(MaintenanceTaskTable action) {
				action.setId(leaseField.getValue());
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
								if (leaseField.noValue()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.leaseError(), leaseField);}
//TODO:								else {TaskPopup.getInstance().show(Task.Type.Maintenance, leaseentities, maintenanceTable);}
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
//TODO:				if (!blocked()) {JournalPopup.getInstance().show(Event.Type.Sale, getValue(), leaseentities, eventjournalTable);}
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
//TODO:				if (!blocked()) {JournalPopup.getInstance().show(Event.Type.Receipt, getValue(), leaseentities, eventjournalTable);}
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
//TODO:				if (!blocked()) {JournalPopup.getInstance().show(Event.Type.Purchase, getValue(), leaseentities, eventjournalTable);}
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
//TODO:				if (!blocked()) {JournalPopup.getInstance().show(Event.Type.PurchaseSale, getValue(), leaseentities, eventjournalTable);}
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
//TODO:				if (!blocked()) {JournalPopup.getInstance().show(Event.Type.Payment, getValue(), leaseentities, eventjournalTable);}
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
				report.setFromtoname(leaseField.getName());
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
				report.setFromtoname(leaseField.getName());
				report.setLanguage(AbstractRoot.getLanguage());
				report.setState(Report.CREATED);
				return report;
			}
		};
		customerStatement.setLabelStyle(CSS.reportButton());
		customerStatement.setTitle(CONSTANTS.customerstatementHelp());

		//-----------------------------------------------
		// Lease Statement Report button
		//-----------------------------------------------
		final ReportButton leaseStatement =  new ReportButton(null, CONSTANTS.leasestatementLabel(), tab++) {
			@Override
			public Report getReport() {
				Report report = new Report();;
				report.setOrganizationid(AbstractRoot.getOrganizationid());
				report.setActorid(AbstractRoot.getActorid());
				report.setDesign(Design.RESERVATION_STATEMENT);
				report.setFromtoname(leaseField.getName());
				report.setLanguage(AbstractRoot.getLanguage());
				report.setState(Report.CREATED);
				return report;
			}
		};
		leaseStatement.setLabelStyle(CSS.reportButton());
		leaseStatement.setTitle(CONSTANTS.leasestatementHelp());

		final HorizontalPanel balancePanel = new HorizontalPanel();
		balancePanel.add(balanceLabel);
		balancePanel.add(balanceField);
		
		final HorizontalPanel reportPanel = new HorizontalPanel();
		reportPanel.add(reportLabel);
		reportPanel.add(agentStatement);
		reportPanel.add(customerStatement);
		reportPanel.add(leaseStatement);
		
		final VerticalPanel panel = new VerticalPanel();
		panel.add(balancePanel);
		panel.add(reportPanel);
		
		bar.add(panel);
		return bar;
	}

	/* Checks if the lease is blocked for financial transactions.
	 * 
	 * @return if the lease is blocked.
	 */
	private boolean blocked() {
		return (
				ifMessage (leaseField.noValue(), Level.ERROR, CONSTANTS.leaseError(), leaseField)
				|| ifMessage(leaseField.noValue(), Level.ERROR, CONSTANTS.leaseError(), leaseField)
				|| ifMessage(leaseField.hasValue(Lease.State.Cancelled.name()), Level.ERROR, CONSTANTS.financestateError(), leaseField)
				|| ifMessage(leaseField.hasValue(Lease.State.Closed.name()), Level.ERROR, CONSTANTS.financestateError(), leaseField)
				|| ifMessage(leaseField.hasValue(Lease.State.Initial.name()), Level.ERROR, CONSTANTS.financestateError(), leaseField)
				|| ifMessage(leaseField.hasValue(Lease.State.Final.name()), Level.ERROR, CONSTANTS.financestateError(), leaseField)
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
//TODO:				JournalPopup.getInstance().show(selectionModel.getLastSelectedObject(), leaseentities, eventjournalTable);
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// Financial Transaction table
		//-----------------------------------------------
		eventjournalTable = new TableField<EventJournal>(this, null, 
				new LeaseEventJournalTable(),
				selectionModel, 
				EVENTJOURNAL_ROWS,
				tab++);

		eventjournalTable.setEmptyValue(eventactiontableEmpty());

		eventjournalTable.setTableError(new TableError() {
			@Override
			public boolean error() {return (
				leaseField.noValue()
			);}
		});

		eventjournalTable.setTableExecutor(new TableExecutor<LeaseEventJournalTable>() {
			public void execute(LeaseEventJournalTable action) {
				action.setId(leaseField.getValue());
				leaseBalance.execute(true);
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
//TODO:						JournalPopup.getInstance().show(eventaction, leaseentities, eventjournalTable);
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
	 * Checks if the lease has any errors to prevent it being updated on the server.
	 * 
	 * @return true, if the lease has errors.
	 */
	private boolean updateError() {
		return (
				ifMessage(leaseField.noValue(), Level.ERROR, CONSTANTS.leaseError(), leaseField)
				|| ifMessage(fromtodateField.noValue(), Level.ERROR, CONSTANTS.fromdateError(), fromtodateField)
				|| ifMessage(fromtodateField.noTovalue(), Level.ERROR, CONSTANTS.todateError(), fromtodateField)
				|| ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
				|| ifMessage(fromtodateField.getDuration(Time.DAY) < 1.0, Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
				|| ifMessage(productField.noValue(), Level.ERROR, CONSTANTS.productError(), productField)
				|| ifMessage(customerField.noValue() && agentField.noValue(), Level.ERROR, CONSTANTS.agentError(), customerField)
				|| ifMessage(dateField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), dateField)
				|| ifMessage(priceField.noValue(), Level.ERROR, CONSTANTS.priceError(), priceField)
				|| ifMessage(priceField.noUnitvalue(), Level.ERROR, AbstractField.CONSTANTS.errCurrency(), priceField)
		);
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Cancel Lease
		//-----------------------------------------------
		leaseCancel = new GuardedRequest<Lease>() {
			protected String popup() {return AbstractField.CONSTANTS.errCancelOK();}
			public void cancel() {state = oldstate;}
			protected boolean error() {return ifMessage(leaseField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), leaseField);}
			protected void send() {super.send(getValue(new LeaseUpdate()));}
			protected void receive(Lease lease) {setValue(lease);}
		};

		//-----------------------------------------------
		// Close Lease
		//-----------------------------------------------
		leaseClose = new GuardedRequest<Lease>() {
			protected String popup() {return CONSTANTS.errCloseOK();}
			public void cancel() {state = oldstate;}
			protected boolean error() {return ifMessage(leaseField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), leaseField);}
			protected void send() {super.send(getValue(new LeaseUpdate()));}
			protected void receive(Lease lease) {setValue(lease);}
		};

		//-----------------------------------------------
		// Create Lease
		//-----------------------------------------------
		leaseCreate = new GuardedRequest<Lease>() {
			protected String popup() {return hasChanged() ? AbstractField.CONSTANTS.errChangeOK() : null;}
			public void cancel() {state = oldstate;}
			protected boolean error() {return ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), leaseField);}
			protected void send() {
				onReset(Lease.State.Initial.name());
				super.send(getValue(new LeaseCreate()));
			}
			protected void receive(Lease lease) {setValue(lease);}
		};

		//-----------------------------------------------
		// Initialize Lease
		//-----------------------------------------------
		leaseReset = new GuardedRequest<Lease>() {
			protected boolean error() {return AbstractRoot.noOrganizationid();}
			protected void send() {super.send(getValue(new LeaseCreate()));}
			protected void receive(Lease lease) {setValue(lease);}
		};

		//-----------------------------------------------
		// Read Lease
		//-----------------------------------------------
		leaseRead = new GuardedRequest<Lease>() {
			protected boolean error() {return leaseField.noValue();}
			protected void send() {super.send(getValue(new LeaseRead()));}
			protected void receive(Lease lease) {setValue(lease);}
		};

		//-----------------------------------------------
		// Update Lease
		//-----------------------------------------------
		leaseUpdate = new GuardedRequest<Lease>() {
			protected String popup() {return notState(Lease.State.Initial.name()) && notState(Lease.State.Created.name()) && productField.hasChanged() ? CONSTANTS.productchangeError() : null;}
			public void cancel() {state = oldstate;}
			protected boolean error() {return updateError();}
			protected void send() {super.send(getValue(new LeaseUpdate()));}
			protected void receive(Lease lease) {setValue(lease);}
		};

		//-----------------------------------------------
		// Get Lease Financial Balance
		//-----------------------------------------------
		leaseBalance = new GuardedRequest<DoubleResponse>() {
			protected boolean error() {return leaseField.noValue();}
			protected void send() {super.send(getValue(new LeaseEventJournalBalance()));}
			protected void receive(DoubleResponse response) {balanceField.setValue(response == null || response.noValue() ? 0.0 : response.getValue());}
		};

		//-----------------------------------------------
		// Read Lease Entities (Agent, Guest, Property)
		//-----------------------------------------------
		leaseentitiesRead = new GuardedRequest<LeaseEntities>() {
			protected boolean error() {return leaseField.noValue();}
			protected void send() {super.send(getValue(new LeaseEntities()));}
			protected void receive(LeaseEntities response) {leaseentities = response;}
		};
	}
}
