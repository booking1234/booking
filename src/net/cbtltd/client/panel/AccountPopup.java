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
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.account.AccountConstants;
import net.cbtltd.shared.Account;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.account.AccountCreate;
import net.cbtltd.shared.account.AccountDelete;
import net.cbtltd.shared.account.AccountRead;
import net.cbtltd.shared.account.AccountUpdate;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/** The Class AccountPopup is to display and change account instances. */
public class AccountPopup
extends AbstractPopup<Account> {

	private static final AccountConstants CONSTANTS = GWT.create(AccountConstants.class);
//	private static final AccountBundle BUNDLE = AccountBundle.INSTANCE;
//	private static final AccountCssResource CSS = BUNDLE.css();

	private static GuardedRequest<Account> accountCreate;
	private static GuardedRequest<Account> accountRead;
	private static GuardedRequest<Account> accountUpdate;
	private static GuardedRequest<Account> accountDelete;

	private static TextField nameField;
	private static ListField entitytypeField;
	private static ListField typeField;
	private static TextField idField;
	private static TextAreaField notesField;
	private static HasValue<String> parentField;
//	private String id;
//	private boolean noId() { return id == null || id.isEmpty();}
	
	/** Instantiates a new account pop up panel. */
	public AccountPopup() {
		super(true);
		createActions();
		setWidget(createContent());
	}

	private static AccountPopup instance;
	
	/**
	 * Gets the single instance of AccountPopup.
	 *
	 * @return single instance of AccountPopup
	 */
	public static synchronized AccountPopup getInstance() {
		if (instance == null) {instance = new AccountPopup();}
		parentField = null;
		nameField.setValue(null);
		entitytypeField.setValue(null);
		return instance;
	}

	/**
	 * Shows the panel for an account of the specified type.
	 *
	 * @param type the specified type.
	 * @param parentField the field that invoked the pop up panel.
	 */
	public void show(Account.Type type, HasValue<String> parentField) {
		AccountPopup.parentField = parentField;
		onReset(Account.INITIAL);
		typeField.setValue(type.name());
		accountCreate.execute(true);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue()
	 */
	@Override
	public Account getValue() {return getValue(new Account());}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue(java.lang.Object)
	 */
	private Account getValue(Account account){
		account.setState(state);
		account.setOrganizationid(AbstractRoot.getOrganizationid());
		account.setActorid(AbstractRoot.getActorid());
		account.setId(idField.getValue());
		account.setName(nameField.getValue());
		account.setType(typeField.getValue());
		account.setSubledger(entitytypeField.getValue());
		account.setNotes(notesField.getValue());
		Log.debug("getValue " + account);
		return account;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(Account account) {
		Log.debug("setValue " + account);
		if (account == null) {onReset(Account.CREATED);}
		else {
			setResetting(true);
			onStateChange(account.getState());
			idField.setValue(account.getId());
			nameField.setValue(account.getName());
			entitytypeField.setValue(account.getSubledger());
			typeField.setValue(account.getType());
			notesField.setValue(account.getNotes());
			center();
			setResetting(false);
		}
	}

	/*
	 * Creates the panel of account fields.
	 * 
	 * @return the panel of account fields.
	 */
	private VerticalPanel createContent() {
		VerticalPanel form = new VerticalPanel();
		final Label title = new Label(CONSTANTS.accountLabel());
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);

		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				AccountPopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);

		//-----------------------------------------------
		// Name field
		//-----------------------------------------------
		nameField = new TextField(this, null,
				CONSTANTS.accountnameLabel(),
				tab++);
		nameField.setMaxLength(50);
		form.add(nameField);

		//-----------------------------------------------
		// Type field
		//-----------------------------------------------
		typeField = new ListField(this, null,
				NameId.getList(CONSTANTS.accountTypes(), Account.TYPES),
				CONSTANTS.typeLabel(),
				false,
				tab++);
		typeField.setDefaultValue(Account.Type.Expense.name());
		form.add(typeField);

		//-----------------------------------------------
		// Subledger field
		//-----------------------------------------------
		entitytypeField = new ListField(this,  null,
				NameId.getList(CONSTANTS.subledgers(), NameId.TYPES),
				CONSTANTS.subledgerLabel(),
				true,
				tab++);
		form.add(entitytypeField);

		//-----------------------------------------------
		// Product ID field
		//-----------------------------------------------
		idField = new TextField(this, null,
				CONSTANTS.idLabel(),
				tab++);
		idField.setEnabled(false);
		idField.setVisible(false);
		idField.setFieldHalf();
		idField.setHelpText(CONSTANTS.idHelp());
		form.add(idField);

		//-----------------------------------------------
		// Notes field
		//-----------------------------------------------
		notesField = new TextAreaField(this, null,
				CONSTANTS.notesLabel(),
				tab++);
		notesField.setMaxLength(1000);
		form.add(notesField);

		form.add(createCommands());
		onRefresh();
		onReset(Account.CREATED);
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
		// Save button
		//-----------------------------------------------
		final CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), accountUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.setTitle(AbstractField.CONSTANTS.helpSave());
		bar.add(saveButton);

		//-----------------------------------------------
		// Delete button
		//-----------------------------------------------
		final CommandButton deleteButton = new CommandButton(this, AbstractField.CONSTANTS.allDelete(), accountDelete, tab++);
		deleteButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		deleteButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		deleteButton.setTitle(AbstractField.CONSTANTS.helpDelete());
		bar.add(deleteButton);

		//-----------------------------------------------
		// Cancel button
		//-----------------------------------------------
		final CommandButton cancelButton = new CommandButton(this, AbstractField.CONSTANTS.allCancel(), resetRequest, tab++);
		cancelButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		cancelButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		cancelButton.setTitle(AbstractField.CONSTANTS.helpCancel());
		bar.add(cancelButton);

		//-----------------------------------------------
		// Transition array to define the finite state machine.
		//-----------------------------------------------
		fsm = new ArrayList<Transition>();
		fsm.add(new Transition(Account.INITIAL, cancelButton, Account.CREATED));
		fsm.add(new Transition(Account.INITIAL, saveButton, Account.CREATED));

		fsm.add(new Transition(Account.CREATED, saveButton, Account.CREATED));
		fsm.add(new Transition(Account.CREATED, deleteButton, Account.CREATED));
		return bar;
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Create Account
		//-----------------------------------------------
		accountCreate = new GuardedRequest<Account>() {
			protected boolean error() {return false;}
			protected void send() {super.send(getValue(new AccountCreate()));}
			protected void receive(Account account){setValue(account);}
		};

		//-----------------------------------------------
		// Read Account
		//-----------------------------------------------
		accountRead = new GuardedRequest<Account>() {
			protected boolean error() {
				return ifMessage(idField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), nameField);
			}
			protected void send() {super.send(getValue(new AccountRead()));}
			protected void receive(Account account){setValue(account);}
		};

		//-----------------------------------------------
		// Update Account
		//-----------------------------------------------
		accountUpdate = new GuardedRequest<Account>() {
			protected boolean error() {
				return (
						ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), nameField)
						|| ifMessage(idField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), nameField)
						|| ifMessage(nameField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), nameField)
						|| ifMessage(typeField.noValue(), Level.TERSE, CONSTANTS.typeError(), typeField)
				);
			}
			protected void send() {super.send(getValue(new AccountUpdate()));}
			protected void receive(Account account){
				if (parentField != null) {parentField.setValue(account.getId());}
				hide();
			}
		};

		//-----------------------------------------------
		// Delete Account
		//-----------------------------------------------
		accountDelete = new GuardedRequest<Account>() {
			protected String popup() {return AbstractField.CONSTANTS.errDeleteOK();}
			public void cancel() {state = oldstate;}
			protected boolean error() {return ifMessage(idField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), nameField);}
			protected void send() {super.send(getValue(new AccountDelete()));}
			protected void receive(Account account){
				if (parentField != null) {parentField.setValue(null);}
				hide();
			}
		};

	}
}
