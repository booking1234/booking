/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.panel;

import java.util.ArrayList;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.HasValue;
import net.cbtltd.client.LocalRequest;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.CommandButton;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.finance.FinanceConstants;
import net.cbtltd.shared.Account;
import net.cbtltd.shared.Finance;
import net.cbtltd.shared.Finance.Type;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.finance.CurrencyNameId;
import net.cbtltd.shared.finance.FinanceCreate;
import net.cbtltd.shared.finance.FinanceDelete;
import net.cbtltd.shared.finance.FinanceRead;
import net.cbtltd.shared.finance.FinanceUpdate;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/** The Class FinancePopup is to display and change finance instances. */
public class FinancePopup 
extends AbstractPopup<Finance> {

	private static final FinanceConstants CONSTANTS = GWT.create(FinanceConstants.class);
//	private static final FinanceBundle BUNDLE = FinanceBundle.INSTANCE;
//	private static final FinanceCssResource CSS = BUNDLE.css();
	
	/** The Constant Array TYPES contains the allowed cash and bank account types. */
	public static final String[] TYPES = {Type.Bank.name(), Type.Card.name(), Type.Cash.name()};

	private static GuardedRequest<Finance> financeCreate;
	private static GuardedRequest<Finance> financeRead;
	private static GuardedRequest<Finance> financeUpdate;
	private static GuardedRequest<Finance> financeDelete;

	private static ListField typeField;
	private static SuggestField financeField;
	private static SuggestField ownerField;	
	private static TextField nameField;
	private static TextField banknameField;
	private static TextField branchnameField;
	private static TextField branchnumberField;
	private static TextField accountnumberField;
	private static TextField ibanswiftField;
	private static ListField ccmonthField;
	private static ListField ccyearField;
	private static TextField cccodeField;
	private static ListField currencyField;
	private static TextAreaField notesField;

	private static HasValue<String> parentField; // field that invoked the popup
	private static TableField<Finance> parentTable; // table that invoked the popup
	
	/** Instantiates a new finance pop up panel. */
	public FinancePopup() {
		super(true);
		createActions();
		setWidget(createContent());
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
	}

	private static FinancePopup instance;
	
	/**
	 * Gets the single instance of FinancePopup.
	 *
	 * @return single instance of FinancePopup
	 */
	public static synchronized FinancePopup getInstance() {
		if (instance == null) {instance = new FinancePopup();}
		return instance;
	}
	
	/**
	 * Shows the panel for the specified cash or bank account ID.
	 *
	 * @param id the ID of the specified cash or bank account
	 */
	public void show(String id) {
		financeField.setValue(id);
		financeRead.execute();
	}
	
	/**
	 * Shows the panel for the specified cash or bank account instance.
	 *
	 * @param finance the cash or bank account instance.
	 * @param currencies the allowed currencies.
	 * @param parentField the field that invoked the pop up panel.
	 */
	public void show(Finance finance, ArrayList<NameId> currencies, TableField<Finance> parentField) {
		FinancePopup.parentTable = parentField;
		typeField.setValue(finance.getType());
		if (currencies != null) {currencyField.setItems(currencies);}
		financeField.setValue(finance.getId());
		financeRead.execute();
	}
	
	/**
	 * Shows the panel for the specified cash or bank account type.
	 *
	 * @param type the cash or bank account type.
	 * @param currencies the allowed currencies.
	 * @param parentTable the table that invoked the pop up panel.
	 */
	public void show(Finance.Type type, ArrayList<NameId> currencies, TableField<Finance> parentTable) {
		FinancePopup.parentTable = parentTable;
		onReset(Finance.INITIAL);
		typeField.setValue(type.name());
		if (currencies != null) {currencyField.setItems(currencies);}
		financeCreate.execute(true);
	}
	
	/**
	 * Shows the panel for the specified cash or bank account type.
	 *
	 * @param type the cash or bank account type.
	 * @param currencies the allowed currencies.
	 * @param parentTable the table that invoked the pop up panel.
	 */
	public void show(Finance.Type type, HasValue<String> parentField) {
		FinancePopup.parentField = parentField;
		onReset(Finance.INITIAL);
		typeField.setValue(type.name());
		financeCreate.execute(true);
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {
		if (typeField.sent(change)) {setView();}
		if (financeField.sent(change)) {financeRead.execute();}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue()
	 */
	@Override
	public Finance getValue() {return getValue(new Finance());}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue(java.lang.Object)
	 */
	private Finance getValue(Finance finance){
		finance.setState(state);
		finance.setOrganizationid(AbstractRoot.getOrganizationid());
		finance.setActorid(AbstractRoot.getActorid());
		finance.setType(typeField.getValue());
		finance.setId(financeField.getValue());
		if (financeField.isVisible()){finance.setName(financeField.getName());}
		else {finance.setName(nameField.getValue());}
		finance.setOwnerid(ownerField.getValue());
		finance.setAccountid(Account.FINANCE);
		finance.setBankname(banknameField.getValue());
		finance.setBranchname(branchnameField.getValue());
		finance.setBranchnumber(branchnumberField.getValue());
		finance.setAccountnumber(accountnumberField.getValue());
		finance.setIbanswift(ibanswiftField.getValue());
		finance.setMonth(ccmonthField.getValue());
		finance.setYear(ccyearField.getValue());
		finance.setCode(cccodeField.getValue());
		finance.setCurrency(currencyField.getValue());
		finance.setPublicText(notesField.getText());
		Log.debug("getValue " + finance);
		return finance;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(Finance finance) {
		Log.debug("setValue " + finance);
		if (finance == null) {onReset(Finance.CREATED);}
		else {
			setResetting(true);
			onStateChange(finance.getState());
			typeField.setValue(finance.getType());
			financeField.setValue(finance.getId());
			nameField.setValue(finance.getName());
			ownerField.setValue(finance.getOwnerid());
			banknameField.setValue(finance.getBankname());
			branchnameField.setValue(finance.getBranchname());
			branchnumberField.setValue(finance.getBranchnumber());
			accountnumberField.setValue(finance.getAccountnumber());
			ibanswiftField.setValue(finance.getIbanswift());
			ccmonthField.setValue(finance.getMonth());
			ccyearField.setValue(finance.getYear());
			cccodeField.setValue(finance.getCode());
			currencyField.setValue(finance.getCurrency());
			notesField.setText(finance.getPublicText());
			setView();
			setResetting(true);
		}
	}

	/* Sets the visible fields according to the current cash or bank account type. */
	private void setView() {
		banknameField.setVisible(typeField.hasValue(Type.Bank.name()));
		branchnameField.setVisible(typeField.hasValue(Type.Bank.name()));
		branchnumberField.setVisible(typeField.hasValue(Type.Bank.name()));
		ibanswiftField.setVisible(typeField.hasValue(Type.Bank.name()));
		accountnumberField.setVisible(typeField.hasValue(Type.Bank.name()) || typeField.hasValue(Type.Card.name()));
		ccmonthField.setVisible(typeField.hasValue(Type.Card.name()));
		ccyearField.setVisible(typeField.hasValue(Type.Card.name()));
		cccodeField.setVisible(typeField.hasValue(Type.Card.name()));
		center();
	}
	
	/*
	 * Creates the panel of cash or bank account fields.
	 * 
	 * @return the panel of cash or bank account fields.
	 */
	private VerticalPanel createContent() {
		final VerticalPanel form = new VerticalPanel();
		final Label title = new Label(CONSTANTS.financeLabel());
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);
		
		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				FinancePopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);
		
		//-----------------------------------------------
		// Account Type field
		//-----------------------------------------------
		typeField = new ListField(this,  null,
				NameId.getList(CONSTANTS.typeNames(), TYPES),
				CONSTANTS.typeLabel(),
				false,
				tab++);
		typeField.setDefaultValue(Finance.Type.Bank.name());
		typeField.setHelpText(CONSTANTS.typeHelp());
		form.add(typeField);
		
		//-----------------------------------------------
		// Bank or Cash Account Name field
		//-----------------------------------------------
		financeField = new SuggestField(this, null,
				new NameIdAction(Service.FINANCE),
				CONSTANTS.nameLabel(),
				20,
				tab++);
		financeField.setDoubleclickable(true);
		financeField.setReadOption(Finance.CREATED, true);
		financeField.setHelpText(CONSTANTS.nameHelp());
		form.add(financeField);

		//-----------------------------------------------
		// New Account Name field
		//-----------------------------------------------
		nameField = new TextField(this, null,
				CONSTANTS.nameLabel(),
				tab++);
		nameField.setMaxLength(50);
		nameField.setReadOption(Finance.INITIAL, true);
		nameField.setHelpText(CONSTANTS.nameHelp());
		form.add(nameField);

		//-----------------------------------------------
		// Owner field
		//-----------------------------------------------
		ownerField = new SuggestField(this, null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.ownerLabel(),
				20,
				tab++);
		ownerField.setHelpText(CONSTANTS.ownerHelp());
		form.add(ownerField);

		//-----------------------------------------------
		// Bank Name field
		//-----------------------------------------------
		banknameField = new TextField(this, null,
				CONSTANTS.banknameLabel(),
				tab++);
		banknameField.setSecure(true);
		banknameField.setHelpText(CONSTANTS.banknameHelp());
		form.add(banknameField);

		//-----------------------------------------------
		// Branch Name field
		//-----------------------------------------------
		branchnameField = new TextField(this, null,
				CONSTANTS.branchnameLabel(),
				tab++);
		branchnameField.setSecure(true);
		branchnameField.setHelpText(CONSTANTS.branchnameHelp());
		form.add(branchnameField);

		//-----------------------------------------------
		// Branch Number field
		//-----------------------------------------------
		branchnumberField = new TextField(this,  null,
				CONSTANTS.branchnumberLabel(),
				tab++);
		branchnumberField.setSecure(true);
		branchnumberField.setHelpText(CONSTANTS.branchnumberHelp());
		form.add(branchnumberField);

		//-----------------------------------------------
		// Account Number field
		//-----------------------------------------------
		accountnumberField = new TextField(this,  null,
				CONSTANTS.accountnumberLabel(),
				tab++);
		accountnumberField.setSecure(true);
		accountnumberField.setHelpText(CONSTANTS.accountnumberHelp());
		form.add(accountnumberField);

		//-----------------------------------------------
		// IBAN/SWIFT Number field
		//-----------------------------------------------
		ibanswiftField = new TextField(this,  null,
				CONSTANTS.ibanswiftLabel(),
				tab++);
		ibanswiftField.setHelpText(CONSTANTS.ibanswiftHelp());
		form.add(ibanswiftField);

		//-----------------------------------------------
		// Card Expiry Month field
		//-----------------------------------------------
		ccmonthField = new ListField(this,  null,
				Finance.getMonths(),
				AbstractField.CONSTANTS.allCreditcard(),
				false,
				tab++);
		ccmonthField.setSecure(true);
		//ccmonthField.setHelpText(CONSTANTS.ccmonthHelp());
		ccmonthField.setFieldStyle(AbstractField.CSS.cbtListFieldCCmonth());

		//-----------------------------------------------
		// Card Expiry Year field
		//-----------------------------------------------
		ccyearField = new ListField(this,  null,
				Finance.getYears(),
				null,
				false,
				tab++);
		ccyearField.setSecure(true);
		ccyearField.setFieldStyle(AbstractField.CSS.cbtListFieldCCyear());
		//ccyearField.setHelpText(CONSTANTS.ccyearHelp());

		//-----------------------------------------------
		// Card Security Code field
		//-----------------------------------------------
		cccodeField = new TextField(this, null,
				null,
				tab++);
		cccodeField.setSecure(true);
		cccodeField.setFieldStyle(AbstractField.CSS.cbtTextFieldCCcode());
		//cccodeField.setHelpText(CONSTANTS.cccodeHelp());

		HorizontalPanel ccc = new HorizontalPanel();
		ccc.add(ccmonthField);
		ccc.add(ccyearField);
		ccc.add(cccodeField);
		form.add(ccc);
		
		//-----------------------------------------------
		// Currency field
		//-----------------------------------------------
		currencyField = new ListField(this,  null,
				new CurrencyNameId(),
				CONSTANTS.currencyLabel(),
				false,
				tab++);
		currencyField.setFieldHalf();
		currencyField.setDefaultValue(AbstractRoot.getCurrency());
		form.add(currencyField);

		//-----------------------------------------------
		// Notes field
		//-----------------------------------------------
		notesField = new TextAreaField(this, null,
				CONSTANTS.notesLabel(),
				tab++);
		notesField.setMaxLength(10000);
		notesField.setHelpText(CONSTANTS.notesHelp());
		form.add(notesField);
		form.add(createCommands());

		onRefresh();
		onReset(Finance.CREATED);
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
		// Create button
		//-----------------------------------------------
		final CommandButton createButton = new CommandButton(this, AbstractField.CONSTANTS.allCreate(), financeCreate, tab++);
		createButton.addStyleName(AbstractField.CSS.cbtGradientGreen());
		createButton.setTitle(AbstractField.CONSTANTS.helpCreate());
		bar.add(createButton);
		
		//-----------------------------------------------
		// Save button
		//-----------------------------------------------
		final CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), financeUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.setTitle(AbstractField.CONSTANTS.helpSave());
		bar.add(saveButton);
		
		//-----------------------------------------------
		// Delete button
		//-----------------------------------------------
		final CommandButton deleteButton = new CommandButton(this, AbstractField.CONSTANTS.allDelete(), financeDelete, tab++);
		deleteButton.addStyleName(AbstractField.CSS.cbtGradientRed());
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
		fsm.add(new Transition(Finance.INITIAL, saveButton, Finance.CREATED));
		fsm.add(new Transition(Finance.INITIAL, cancelButton, Finance.CREATED));
		
		//fsm.add(new Transition(Finance.CREATED, createButton, Finance.INITIAL));
		fsm.add(new Transition(Finance.CREATED, saveButton, Finance.CREATED));
		fsm.add(new Transition(Finance.CREATED, deleteButton, Finance.CREATED));
		return bar;
	}

	/* Gets the field next which to display the name help mesage. */
	private Widget getTargetField() {
		if (nameField.isVisible()) {return nameField;}
		else {return financeField;}
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Create Finance
		//-----------------------------------------------
		financeCreate = new GuardedRequest<Finance>() {
			protected boolean error() {return ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), getTargetField());}
			protected void send() {super.send(getValue(new FinanceCreate()));}
			protected void receive(Finance finance){setValue(finance);}
		};

		//-----------------------------------------------
		// Read Finance
		//-----------------------------------------------
		financeRead = new GuardedRequest<Finance>() {
			protected boolean error() {return financeField.noValue();}
			protected void send() {super.send(getValue(new FinanceRead()));}
			protected void receive(Finance finance){setValue(finance);}
		};

		//-----------------------------------------------
		// Update Finance
		//-----------------------------------------------
		financeUpdate = new GuardedRequest<Finance>() {
			protected boolean error() {
				return (
						ifMessage(financeField.noValue() && nameField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), getTargetField())
						|| ifMessage(ownerField.noValue(), Level.TERSE, CONSTANTS.ownerError(), ownerField)
						|| ifMessage(typeField.hasValue(Type.Bank.name()) && banknameField.noValue(), Level.TERSE, CONSTANTS.banknameError(), banknameField)
						|| ifMessage(typeField.hasValue(Type.Bank.name()) && branchnameField.noValue() && branchnumberField.noValue(), Level.TERSE, CONSTANTS.branchnameError(), branchnameField)
						|| ifMessage(typeField.hasValue(Type.Bank.name()) && accountnumberField.noValue(), Level.TERSE, CONSTANTS.accountnumberError(), accountnumberField)
						|| ifMessage(typeField.hasValue(Type.Card.name()) && cccodeField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errCreditcard(), cccodeField)
						|| ifMessage(typeField.hasValue(Type.Card.name()) && ccmonthField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errCreditcard(), ccmonthField)
						|| ifMessage(typeField.hasValue(Type.Card.name()) && ccyearField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errCreditcard(), ccyearField)
						|| ifMessage(currencyField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errCurrency(), currencyField)
				);
			}
			
			protected void send() {super.send(getValue(new FinanceUpdate()));}
			protected void receive(Finance finance){
				if (parentTable != null) {parentTable.execute(true);}
				hide();
			}
		};

		//-----------------------------------------------
		// Delete Finance
		//-----------------------------------------------
		financeDelete = new GuardedRequest<Finance>() {
			protected String popup() {return AbstractField.CONSTANTS.errChangeOK();}
			protected boolean error() {return ifMessage(financeField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), getTargetField());}			
			protected void send() {super.send(getValue(new FinanceDelete()));}
			protected void receive(Finance finance){
				if (parentTable != null) {parentTable.execute(true);}
				hide();
			}
		};	
	}
}
