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
import net.cbtltd.client.field.CheckField;
import net.cbtltd.client.field.CommandButton;
import net.cbtltd.client.field.IntegerField;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.contract.ContractBundle;
import net.cbtltd.client.resource.contract.ContractConstants;
import net.cbtltd.client.resource.contract.ContractCssResource;
import net.cbtltd.shared.Contract;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.contract.ContractCreate;
import net.cbtltd.shared.contract.ContractDelete;
import net.cbtltd.shared.contract.ContractUpdate;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/** The Class ContractPopup is to display and change contract instances. */
public class ContractPopup 
extends AbstractPopup<Contract> {

	private static final ContractConstants CONSTANTS = GWT.create(ContractConstants.class);
	private static final ContractBundle BUNDLE = ContractBundle.INSTANCE;
	private static final ContractCssResource CSS = BUNDLE.css();

	private static GuardedRequest<Contract> contractCreate;
	private static GuardedRequest<Contract> contractUpdate;
	private static GuardedRequest<Contract> contractDelete;

	private static SuggestField agentField;
	private static CheckField stateField;
	private static IntegerField discountField;
	private static TextAreaField notesField;
	private static TableField<Contract> parentTable;
	private static String id;
	private boolean noValue() {return id == null || id.isEmpty();}

	/** Instantiates a new contract pop up panel. */
	public ContractPopup() {
		super(true);
		AbstractField.CSS.ensureInjected();
		CSS.ensureInjected();
		createActions();
		setWidget(createContent());
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
	}

	private static ContractPopup instance;
	
	/**
	 * Gets the single instance of ContractPopup.
	 *
	 * @return single instance of ContractPopup
	 */
	public static synchronized ContractPopup getInstance() {
		if (instance == null) {instance = new ContractPopup();}
		return instance;
	}

	/**
	 * Show the panel for the specified contract.
	 *
	 * @param contract the specified contract.
	 * @param parentTable the table that invoked the pop up panel.
	 */
	public void show(Contract contract, TableField<Contract> parentTable) {
		ContractPopup.parentTable = parentTable;
		setValue(contract);
	}

	/**
	 * Shows the panel for the specified contract type.
	 *
	 * @param type the specified contract type.
	 * @param parentTable the table that invoked the pop up panel.
	 */
	public void show(Contract.Type type, TableField<Contract> parentTable) {
		ContractPopup.parentTable = parentTable;
		onReset(Contract.INITIAL);
		contractCreate.execute(true);
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
	public Contract getValue() {return getValue(new Contract());}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue(java.lang.Object)
	 */
	private Contract getValue(Contract contract) {
		contract.setState(state);
		contract.setId(ContractPopup.id);
		contract.setOrganizationid(AbstractRoot.getOrganizationid());
		contract.setActorid(AbstractRoot.getActorid());
		contract.setName(NameId.Type.Reservation.name());
		contract.setPartyid(agentField.getValue());
		contract.setProcess(NameId.Type.Contract.name());
		contract.setState(stateField.getValue() ? Contract.SUSPENDED : Contract.CREATED);
		contract.setDiscount(discountField.getValue());
		contract.setCreditterm(Contract.NONE);
		contract.setCreditlimit(0.0);
		contract.setTarget(0.0);
		contract.setCurrency(AbstractRoot.getCurrency());
		contract.setDate(new Date());
		contract.setDuedate(contract.getDate());
		contract.setNotes(notesField.getValue());
		Log.debug("getValue " + contract);
		return contract;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(Contract contract) {
		Log.debug("getValue " + contract);
		if (contract == null) {onReset(Contract.CREATED);}
		else {
			setResetting(true);
			onStateChange(contract.getState());
			ContractPopup.id = contract.getId();
			agentField.setValue(contract.getPartyid());
			stateField.setValue(contract.hasState(Contract.SUSPENDED));
			discountField.setValue(contract.getDiscount());
			notesField.setValue(contract.getNotes());
			center();
			setResetting(false);
		}
	}

	/*
	 * Creates the panel of contract fields.
	 * 
	 * @return the panel of contract fields.
	 */
	private VerticalPanel createContent() {
		final VerticalPanel form = new VerticalPanel();
		final Label title = new Label(CONSTANTS.contractLabel());
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);
		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ContractPopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);

		//-----------------------------------------------
		// Agent field
		//-----------------------------------------------
		agentField = new SuggestField(this, null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.agentLabel(),
				20,
				tab++);
		agentField.setType(Party.Type.Agent.name());
		agentField.setHelpText(CONSTANTS.agentHelp());
		
		Image agentButton = new Image(AbstractField.BUNDLE.plus());
		agentButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PartyPopup.getInstance().show(Party.Type.Agent, agentField, null);
			}
		});
		agentButton.setTitle(CONSTANTS.agentbuttonHelp());
		agentField.addButton(agentButton);
		form.add(agentField);

		//-----------------------------------------------
		// State field
		//-----------------------------------------------
		stateField = new CheckField(this, null,
				CONSTANTS.stateLabel(),
				tab++);
		stateField.setHelpText(CONSTANTS.stateHelp());
		form.add(stateField);

		//-----------------------------------------------
		// Agent's Commission field
		//-----------------------------------------------
		discountField = new IntegerField(this, null,
				CONSTANTS.discountLabel(),
				tab++);
		discountField.setDefaultValue(20);
		discountField.setRange(0, 50);
		discountField.setHelpText(CONSTANTS.discountHelp());
		form.add(discountField);

		//-----------------------------------------------
		// Contract Notes field
		//-----------------------------------------------
		notesField = new TextAreaField(this, null,
				CONSTANTS.notesLabel(),
				tab++);
		notesField.setMaxLength(1000);
		notesField.setHelpText(CONSTANTS.notesHelp());
		form.add(notesField);

		form.add(createCommands());

		onRefresh();
		onReset(Contract.CREATED);
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
		// Save button
		//-----------------------------------------------
		final CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), contractUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		bar.add(saveButton);

		//-----------------------------------------------
		// Delete button
		//-----------------------------------------------
		final CommandButton deleteButton = new CommandButton(this, AbstractField.CONSTANTS.allDelete(), contractDelete, tab++);
		deleteButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		deleteButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		bar.add(deleteButton);

		//-----------------------------------------------
		// Cancel button
		//-----------------------------------------------
		final CommandButton cancelButton = new CommandButton(this, AbstractField.CONSTANTS.allCancel(), cancelRequest, tab++);
		cancelButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		cancelButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		bar.add(cancelButton);

		//-----------------------------------------------
		// Transition array to define the finite state machine
		//-----------------------------------------------
		fsm = new ArrayList<Transition>();
		fsm.add(new Transition(Contract.INITIAL, cancelButton, Contract.CREATED));
		fsm.add(new Transition(Contract.INITIAL, saveButton, Contract.CREATED));

		fsm.add(new Transition(Contract.CREATED, saveButton, Contract.CREATED));
		fsm.add(new Transition(Contract.CREATED, deleteButton, Contract.CREATED));

		fsm.add(new Transition(Contract.SUSPENDED, saveButton, Contract.SUSPENDED));
		fsm.add(new Transition(Contract.SUSPENDED, deleteButton, Contract.CREATED));
		return bar;
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Create Contract
		//-----------------------------------------------
		contractCreate = new GuardedRequest<Contract>() {
			protected boolean error() {return AbstractRoot.noOrganizationid();}
			protected void send() {super.send(getValue(new ContractCreate()));}
			protected void receive(Contract contract) {setValue(contract);}		
		};

		//-----------------------------------------------
		// Update Contract
		//-----------------------------------------------
		contractUpdate = new GuardedRequest<Contract>() {
			protected boolean error() {
				return (
						ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), agentField)
						|| ifMessage(agentField.noValue(), Level.TERSE, CONSTANTS.agentError(), agentField)
						|| ifMessage(discountField.noValue(), Level.TERSE, CONSTANTS.discountError(), discountField)
				);
			}
			protected void send() {super.send(getValue(new ContractUpdate()));}
			protected void receive(Contract contract) {
				parentTable.execute(true);
				hide();
			}
		};

		//-----------------------------------------------
		// Delete Contract
		//-----------------------------------------------
		contractDelete = new GuardedRequest<Contract>() {
			protected String popup() {return AbstractField.CONSTANTS.errDeleteOK();}
			protected boolean error() {return ifMessage(noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), agentField);}
			protected void send() {super.send(getValue(new ContractDelete()));}
			protected void receive(Contract contract) {
				parentTable.execute(true);
				hide();
			}
		};
	}
}
