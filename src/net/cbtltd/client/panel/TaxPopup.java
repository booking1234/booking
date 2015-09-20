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
import net.cbtltd.client.field.DateField;
import net.cbtltd.client.field.DoubleField;
import net.cbtltd.client.field.IntegerField;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.tax.TaxConstants;
import net.cbtltd.shared.Account;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Tax;
import net.cbtltd.shared.Value;
import net.cbtltd.shared.tax.TaxCreate;
import net.cbtltd.shared.tax.TaxDelete;
import net.cbtltd.shared.tax.TaxExists;
import net.cbtltd.shared.tax.TaxUpdate;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/** The Class TaxPopup is to display and change tax instances. */
public class TaxPopup
extends AbstractPopup<Tax> {

	private static final TaxConstants CONSTANTS = GWT.create(TaxConstants.class);

	private static GuardedRequest<Tax> taxCreate;
	private static GuardedRequest<Tax> taxExists;
	private static GuardedRequest<Tax> taxUpdate;
	private static GuardedRequest<Tax> taxDelete;

	private static SuggestField partyField;
	private static SuggestField nameField;
	private static ListField typeField;
	private static ListField accountField;
	private static DateField dateField;
	private static IntegerField thresholdField;
	private static DoubleField amountField;
	private static TextAreaField notesField;

	private static String id;
	private static boolean noId() {return 	id == null || id.isEmpty();}
	private static String entitytype;
	private static boolean noEntitytype() {return entitytype == null || entitytype.isEmpty();}
	private static String entityid;
	private static boolean noEntityid() {return entityid == null || entityid.isEmpty();}
	private static TableField<Tax> parentTable;	

	/** Instantiates a new tax pop up panel. */
	public TaxPopup() {
		super(true);
		createActions();
		setWidget(createContent());
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
	}

	private static TaxPopup instance;
	
	/**
	 * Gets the single instance of TaxPopup.
	 *
	 * @return single instance of TaxPopup
	 */
	public static synchronized TaxPopup getInstance() {
		if (instance == null) {instance = new TaxPopup();}
		return instance;
	}

	/**
	 * Shows the panel for the specified entity.
	 *
	 * @param partyid the ID of the specified entity.
	 */
	public void show(String entitytype, String entityid, TableField<Tax> parentTable) {
		onReset(Tax.INITIAL);
		TaxPopup.id = null;
		TaxPopup.entitytype = entitytype;
		TaxPopup.entityid = entityid;
		TaxPopup.parentTable = parentTable;
		taxCreate.execute(true);
	}

	/**
	 * Shows the panel for the specified tax instance.
	 *
	 * @param tax the tax
	 */
	public void show(String entitytype, String entityid, Tax tax, TableField<Tax> parentTable) {
		TaxPopup.entitytype = entitytype;
		TaxPopup.entityid = entityid;
		TaxPopup.parentTable = parentTable;
		setValue(tax);
//		taxRead.execute(true);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {
		if (accountField.sent(change) 
			|| nameField.sent(change) 
			|| partyField.sent(change) 
			|| typeField.sent(change)
		) {taxExists.execute(true);}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue()
	 */
	@Override
	public Tax getValue() {return getValue(new Tax());}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue(java.lang.Object)
	 */
	private Tax getValue(Tax action) {
		action.setState(state);
		action.setId(id);
		action.setEntitytype(entitytype);
		action.setEntityid(entityid);
//		action.setOrganizationid(AbstractRoot.getOrganizationid());
		action.setAccountid(accountField.getValue());
		action.setPartyid(partyField.getValue());
		action.setName(nameField.getValue());
		action.setType(typeField.getValue());
		action.setDate(dateField.getValue());
		action.setThreshold(thresholdField.getValue());
		action.setAmount(amountField.getValue());
		action.setNotes(notesField.getValue());
		Log.debug("getValue " + action);
		return action;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(Tax tax) {
		Log.debug("setValue " + tax);
		if (tax == null) {}
		else {
			setResetting(true);
			onStateChange(tax.getState());
			id = tax.getId();
			accountField.setValue(tax.getAccountid());
			partyField.setValue(tax.getPartyid());
			nameField.setValue(tax.getName());
			typeField.setValue(tax.getType());
			dateField.setValue(tax.getDate());
			thresholdField.setValue(tax.getThreshold());
			amountField.setValue(tax.getAmount());
			notesField.setValue(tax.getNotes());
			thresholdField.setVisible(tax.hasType(Tax.Type.IncomeTax) || tax.hasType(Tax.Type.PayrollTax));
			center();
			setResetting(false);
		}
	}

	/*
	 * Creates the panel of tax fields.
	 * 
	 * @return the panel of tax fields.
	 */
	private VerticalPanel createContent() {
		VerticalPanel form = new VerticalPanel();
		final Label title = new Label(CONSTANTS.titleLabel());
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);

		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TaxPopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);

		
		//-----------------------------------------------
		// Jurisdiction field
		//-----------------------------------------------
		partyField = new SuggestField(this, null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.partyLabel(),
				20,
				tab++);
		partyField.setType(Party.Type.Jurisdiction.name());
		partyField.setWriteOption(Tax.INITIAL, true);
		partyField.setAllOrganizations(true);
		partyField.setHelpText(CONSTANTS.partyHelp());
		
		final Image partyButton = new Image(AbstractField.BUNDLE.plus());
		partyButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//PartyPopup.getInstance().show(partyField.getValue());
				PartyPopup.getInstance().show(Party.Type.Jurisdiction, partyField, null);
			}
		});
		partyButton.setTitle(CONSTANTS.partybuttonHelp());
		partyField.addButton(partyButton);
		form.add(partyField);

		//-----------------------------------------------
		// Name field
		//-----------------------------------------------
		nameField = new SuggestField(this, null,
				new NameIdAction(Service.ATTRIBUTE),
				CONSTANTS.nameLabel(),
				20,
				tab++);
		nameField.setType(Value.Type.TaxName.name());
		nameField.setState(Party.CBT_LTD_PARTY);
		nameField.setWriteOption(Tax.INITIAL, true);
		nameField.setHelpText(CONSTANTS.nameHelp());

		final Image nameButton = new Image(AbstractField.BUNDLE.plus());
		nameButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ValuePopup.getInstance().show(Value.Type.TaxName, CONSTANTS.nameLabel(), Party.CBT_LTD_PARTY, nameField);
			}
		}); 
		nameButton.setTitle(CONSTANTS.partybuttonHelp());
		nameField.addButton(nameButton);
		form.add(nameField);

		//-----------------------------------------------
		// Type list
		//-----------------------------------------------
		typeField = new ListField(this, null,
				NameId.getList(CONSTANTS.taxTypes(), Tax.TYPES),
				CONSTANTS.typeLabel(),
				false,
				tab++);
				typeField.setDefaultValue(Tax.Type.SalesTaxIncluded.name());
				typeField.setWriteOption(Tax.INITIAL, true);
		form.add(typeField);

		//-----------------------------------------------
		// Account field
		//-----------------------------------------------
		accountField = new ListField(this, null,
				new NameIdAction(Service.ACCOUNT),
				CONSTANTS.accountLabel(),
				false,
				tab++);
		accountField.setDefaultValue(Account.TAX_CONTROL);
		accountField.setWriteOption(Tax.INITIAL, true);
		accountField.setHelpText(CONSTANTS.accountHelp());
		form.add(accountField);

		//-----------------------------------------------
		// Date field - when effective
		//-----------------------------------------------
		dateField = new DateField(this, null,
				CONSTANTS.dateLabel(),
				tab++);
		dateField.setWriteOption(Tax.INITIAL, true);
		dateField.setHelpText(CONSTANTS.dateHelp());
		form.add(dateField);

		//-----------------------------------------------
		// Threshold field
		//-----------------------------------------------
		thresholdField = new IntegerField(this, null,
				CONSTANTS.thresholdLabel(),
				tab++);
		thresholdField.setWriteOption(Tax.INITIAL, true);
		thresholdField.setHelpText(CONSTANTS.thresholdHelp());
		form.add(thresholdField);

		//-----------------------------------------------
		// Tax Percentage field
		//-----------------------------------------------
		amountField = new DoubleField(this, null,
				CONSTANTS.amountLabel(),
				AbstractField.QF,
				tab++);
		amountField.setWriteOption(Tax.INITIAL, true);
		amountField.setHelpText(CONSTANTS.amountHelp());
		form.add(amountField);

		//-----------------------------------------------
		// Notes field
		//-----------------------------------------------
		notesField = new TextAreaField(this, null,
				CONSTANTS.notesLabel(),
				tab++);
		notesField.setHelpText(CONSTANTS.notesHelp());
		notesField.setHelpText(CONSTANTS.notesHelp());
		form.add(notesField);

		form.add(createCommands());
		onRefresh();
		onReset(Tax.CREATED);
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

		final LocalRequest resetRequest = new LocalRequest() {protected void perform() {onReset(Tax.CREATED);}};

		//-----------------------------------------------
		// Create button
		//-----------------------------------------------
		final CommandButton createButton = new CommandButton(this, AbstractField.CONSTANTS.allCreate(), taxCreate, tab++);
		createButton.addStyleName(AbstractField.CSS.cbtGradientGreen());
		createButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		createButton.setTitle(AbstractField.CONSTANTS.helpCreate());
		bar.add(createButton);

		//-----------------------------------------------
		// Save button
		//-----------------------------------------------
		final CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), taxUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		saveButton.setTitle(AbstractField.CONSTANTS.helpSave());
		bar.add(saveButton);

		//-----------------------------------------------
		// Delete button
		//-----------------------------------------------
		final CommandButton deleteButton = new CommandButton(this, AbstractField.CONSTANTS.allDelete(), taxDelete, tab++);
		deleteButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		deleteButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		deleteButton.setTitle(AbstractField.CONSTANTS.helpDelete());
		bar.add(deleteButton);

		//-----------------------------------------------
		// Cancel button
		//-----------------------------------------------
		final CommandButton cancelButton = new CommandButton(this, AbstractField.CONSTANTS.allCancel(), resetRequest, tab++);
		cancelButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		cancelButton.setTitle(AbstractField.CONSTANTS.helpCancel());
		bar.add(cancelButton);

		//-----------------------------------------------
		// Transition array to define the finite state machine
		//-----------------------------------------------
		fsm = new ArrayList<Transition>();
		fsm.add(new Transition(Tax.INITIAL, cancelButton, Tax.CREATED));
		fsm.add(new Transition(Tax.INITIAL, saveButton, Tax.CREATED));
		fsm.add(new Transition(Tax.CREATED, saveButton, Tax.CREATED));
		fsm.add(new Transition(Tax.CREATED, deleteButton, Tax.CREATED));
		return bar;
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Create Tax
		//-----------------------------------------------
		taxCreate = new GuardedRequest<Tax>() {
			protected String popup() {return hasChanged() ? AbstractField.CONSTANTS.errChangeOK() : null;}
			protected boolean error() {return false;}
			protected void send() {super.send(getValue(new TaxCreate()));}
			protected void receive(Tax tax){setValue(tax);}
		};

		//-----------------------------------------------
		// Exists Tax
		//-----------------------------------------------
		taxExists = new GuardedRequest<Tax>() {
			protected boolean error() {return (
					partyField.noValue()
					|| nameField.noValue()
			);}
			protected void send() {super.send(getValue(new TaxExists()));}
			protected void receive(Tax tax){setValue(tax);}
		};

		//-----------------------------------------------
		// Update Tax
		//-----------------------------------------------
		taxUpdate = new GuardedRequest<Tax>() {
			protected boolean error() {
				return (
						ifMessage(noId(), Level.TERSE, CONSTANTS.partyError(), partyField)
						|| ifMessage(noEntitytype(), Level.TERSE, CONSTANTS.partyError(), partyField)
						|| ifMessage(noEntityid(), Level.TERSE, CONSTANTS.partyError(), partyField)
						|| ifMessage(partyField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), partyField)
						|| ifMessage(nameField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), nameField)
						|| ifMessage(typeField.noValue(), Level.TERSE, CONSTANTS.typeError(), typeField)
				);
			}
			protected void send() {super.send(getValue(new TaxUpdate()));}
			protected void receive(Tax tax){
				if (parentTable != null) {parentTable.execute(true);}
				hide();
			}
		};

		//-----------------------------------------------
		// Delete Tax
		//-----------------------------------------------
		taxDelete = new GuardedRequest<Tax>() {
			protected String popup() {return AbstractField.CONSTANTS.errDeleteOK();}
			protected boolean error() {return (
					ifMessage(noId(), Level.TERSE, CONSTANTS.partyError(), partyField)
			);}
			protected void send() {super.send(getValue(new TaxDelete()));}
			protected void receive(Tax tax) {
				if (parentTable != null) {parentTable.execute(true);}
				hide();
			}
		};

	}
}
