/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.panel;

import java.util.ArrayList;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.CommandButton;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.OptionField;
import net.cbtltd.client.field.PasswordField;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.form.AccessControl;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.party.PartyBundle;
import net.cbtltd.client.resource.party.PartyConstants;
import net.cbtltd.client.resource.party.PartyCssResource;
import net.cbtltd.shared.Account;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.party.ActorDelete;
import net.cbtltd.shared.party.ActorRead;
import net.cbtltd.shared.party.ActorUpdate;
import net.cbtltd.shared.party.PartyExists;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/** The Class ActorPopup is to display and change actor instances. */
public class ActorPopup
extends AbstractPopup<Party> {

	private static final PartyConstants CONSTANTS = GWT.create(PartyConstants.class);
	private static final PartyBundle BUNDLE = PartyBundle.INSTANCE;
	private static final PartyCssResource CSS = BUNDLE.css();
	
	private static GuardedRequest<Party> partyExists;
	private static GuardedRequest<Party> actorRead;
	private static GuardedRequest<Party> actorUpdate;
	private static GuardedRequest<Party> actorDelete;

	private static SuggestField partyField;
	private static TextField emailaddressField;
	private static PasswordField passwordField;
	private static PasswordField checkpasswordField;
	private static ListField formatdateField;
	private static TextField formatphoneField;
	private static OptionField rolesField;
//	private static ShuttleField typesField;
	private static TableField<Party> parentTable; // table that invoked popup
	
	/** Instantiates a new party pop up panel. */
	public ActorPopup() {
		super(false);
		CSS.ensureInjected();
		createActions();
		setWidget(createContent());
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
	}
	
	private static ActorPopup instance;
	/**
	 * Gets the single instance of PartyPopup.
	 *
	 * @return single instance of PartyPopup
	 */
	public static synchronized ActorPopup getInstance() {
		if (instance == null) {instance = new ActorPopup();}
		return instance;
	}

	/**
	 * Shows the panel for the specified actor instance.
	 *
	 * @param party the specified party instance
	 * @param parentTable the table that invoked the pop up panel.
	 */
	public void show(Party party, TableField<Party> parentTable) {
		ActorPopup.parentTable = parentTable;
		partyField.setValue(party.getId());
		rolesField.setItems(AbstractRoot.hasPermission(AccessControl.AGENCY) ? getAgentroles() : getOrganizationroles());
		actorRead.execute();
	}

	/**
	 * Shows the panel for a new actor.
	 *
	 * @param parentTable the table that invoked the pop up panel.
	 */
	public void show(TableField<Party> parentTable) {
		ActorPopup.parentTable = parentTable;
		rolesField.setItems(AbstractRoot.hasPermission(AccessControl.AGENCY) ? getAgentroles() : getOrganizationroles());
		onReset(Party.CREATED);
		center();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {
		if (partyField.sent(change)) {actorRead.execute();}
		else if (emailaddressField.sent(change)) {partyExists.execute();}
		else if (checkpasswordField.sent(change)) {ifMessage(!checkpasswordField.getValue().equals(passwordField.getValue()), Level.ERROR, CONSTANTS.checkpasswordError(), checkpasswordField);}
		else if (formatdateField.sent(change)) {AbstractRoot.setFormatDate(formatdateField.getValue());}
		else if (formatphoneField.sent(change)) {AbstractRoot.setFormatPhone(formatphoneField.getValue());}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue()
	 */
	@Override
	public Party getValue() {return getValue(new Party());}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue(java.lang.Object)
	 */
	private Party getValue(Party party) {
		party.setState(state);
		party.setOrganizationid(AbstractRoot.getOrganizationid());
		party.setActorid(AbstractRoot.getActorid());
		party.setId(partyField.getValue());
		party.setName(partyField.getName());
		if (party.noEmployerid()) {party.setEmployerid(AbstractRoot.getOrganizationid());}
//		party.setEmployerid(AbstractRoot.getOrganizationid());
		party.setEmailaddress(emailaddressField.getValue());
		party.setPassword(passwordField.getValue());
		party.setFormatdate(formatdateField.getValue());
		party.setFormatphone(formatphoneField.getValue());
		party.setType(Party.Type.Actor.name());
		party.setRoles(rolesField.getValue());
		Log.debug("getValue " + party);
		return party;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(Party party) {
		Log.debug("setValue " + party);
		if (party == null)  {return;}
		else {
			setResetting(true);
			onStateChange(party.getState());
			partyField.setValue(party.getId());
			emailaddressField.setValue(party.getEmailaddresses());
			formatdateField.setValue(party.getFormatdate());
			formatphoneField.setValue(party.getFormatphone());
			passwordField.setValue(Model.BLANK);
			checkpasswordField.setValue(Model.BLANK);
			rolesField.setValue(party.getRoles());
//			typesField.setValue(party.getTypes());
			center();
			setResetting(false);
		}
	}

	/*
	 * Gets the list of agent roles.
	 * 
	 * @return the list of agent roles.
	 */
	private static ArrayList<NameId> getAgentroles() {
		ArrayList<NameId> modelList = new ArrayList<NameId>();
		if (CONSTANTS.agentroleNames().length != AccessControl.AGENT_ROLES.length) {Window.alert("AgentPopup getAgentroles()");}
		for (int i = 0; i < AccessControl.AGENT_ROLES.length; i++) {modelList.add(new NameId(CONSTANTS.agentroleNames()[i], String.valueOf(AccessControl.AGENT_ROLES[i])));}
		return modelList;
	}

	/*
	 * Gets the list of organization roles.
	 * 
	 * @return the list of organization roles.
	 */
	private static ArrayList<NameId> getOrganizationroles() {
		ArrayList<NameId> modelList = new ArrayList<NameId>();
		if (CONSTANTS.organizationroleNames().length != AccessControl.ORGANIZATION_ROLES.length) {Window.alert("AgentPopup getOrganizationroles()");}
		for (int i = 0; i < AccessControl.ORGANIZATION_ROLES.length; i++) {modelList.add(new NameId(CONSTANTS.organizationroleNames()[i], String.valueOf(AccessControl.ORGANIZATION_ROLES[i])));}
		return modelList;
	}

	/*
	 * Creates the panel of party fields.
	 * 
	 * @return the panel of party fields.
	 */
	private HorizontalPanel createContent() {
		final HorizontalPanel panel = new HorizontalPanel();
		final VerticalPanel form = new VerticalPanel();
		panel.add(form);
		
		final Label titleLabel = new Label(CONSTANTS.actorLabel());
		titleLabel.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(titleLabel);
		
		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ActorPopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);
		
		//-----------------------------------------------
		// Party field
		//-----------------------------------------------
		partyField = new SuggestField(this, null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.partynameLabel(),
				20,
				tab++);
		partyField.setReadOption(Party.CREATED, true);
		partyField.setDoubleclickable(true);
		partyField.setHelpText(CONSTANTS.partynameHelp());
		form.add(partyField);

		//-----------------------------------------------
		// Email Address field
		//-----------------------------------------------
		emailaddressField = new TextField(this,  null,
				CONSTANTS.emailaddressLabel(),
				tab++);
		emailaddressField.setMaxLength(100);
		emailaddressField.setHelpText(CONSTANTS.emailaddressHelp());
		form.add(emailaddressField);

		//-----------------------------------------------
		// Password field
		//-----------------------------------------------
		passwordField = new PasswordField(this, null,
				CONSTANTS.passwordLabel(),
				tab++);
		passwordField.setSecure(true);
		passwordField.setHelpText(CONSTANTS.passwordHelp());
		form.add(passwordField);

		//-----------------------------------------------
		// Check Password field
		//-----------------------------------------------
		checkpasswordField = new PasswordField(this, null,
				CONSTANTS.checkpasswordLabel(),
				tab++);
		checkpasswordField.setSecure(true);
		checkpasswordField.setHelpText(CONSTANTS.checkpasswordHelp());
		form.add(checkpasswordField);

		//-----------------------------------------------
		// Date Format field
		//-----------------------------------------------
		formatdateField = new ListField(this, null,
				NameId.getList(Party.DATE_FORMATS, Party.DATE_FORMATS),
				CONSTANTS.formatdateLabel(),
				false,
				tab++);
		formatdateField.setDefaultValue(Party.MM_DD_YYYY);
		formatdateField.setFieldHalf();
		formatdateField.setHelpText(CONSTANTS.formatdateHelp());
		
		//-----------------------------------------------
		// Phone Format field
		//-----------------------------------------------
		formatphoneField = new TextField(this,  null,
				CONSTANTS.formatphoneLabel(),
				tab++);
		formatphoneField.setMaxLength(25);
		formatphoneField.setFieldHalf();
		formatphoneField.setHelpText(CONSTANTS.formatphoneHelp());

		HorizontalPanel ff = new HorizontalPanel();
		ff.add(formatdateField);
		ff.add(formatphoneField);
		form.add(ff);
		
		//-----------------------------------------------
		// Roles option selector
		//-----------------------------------------------
		rolesField = new OptionField(this, null,
				AbstractRoot.hasPermission(AccessControl.AGENCY) ? getAgentroles() : getOrganizationroles(),
				CONSTANTS.roleLabel(),
				tab++);
		rolesField.setHelpText(CONSTANTS.roleHelp());
		form.add(rolesField);

		form.add(createCommands());
		
		onRefresh();
		onReset(Party.CREATED);
		return panel;
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
		final CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), actorUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.setTitle(AbstractField.CONSTANTS.helpSave());
		bar.add(saveButton);

		//-----------------------------------------------
		// Delete button
		//-----------------------------------------------
		final CommandButton deleteButton = new CommandButton(this, AccessControl.DELETE_PERMISSION, AbstractField.CONSTANTS.allDelete(), actorDelete, tab++);
		deleteButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		deleteButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		deleteButton.setTitle(AbstractField.CONSTANTS.helpDelete());
		bar.add(deleteButton);

		//-----------------------------------------------
		// Transition array that defines the finite state machine
		//-----------------------------------------------
		fsm = new ArrayList<Transition>();
		fsm.add(new Transition(Party.CREATED, saveButton, Party.CREATED));
		fsm.add(new Transition(Party.CREATED, deleteButton, Party.FINAL));

		return bar;
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Check if Party Exists
		//-----------------------------------------------
		partyExists = new GuardedRequest<Party>() {
			protected boolean error() {return emailaddressField.noValue();}
			protected void send() {super.send(getValue(new PartyExists()));}
			protected void receive(Party party) {
				if (party != null ) {setValue(party);}
			}
		};

		//-----------------------------------------------
		// Read Party
		//-----------------------------------------------
		actorRead = new GuardedRequest<Party>() {
			protected boolean error() {return partyField.noValue();}
			protected void send() {super.send(getValue(new ActorRead()));}
			protected void receive(Party party){setValue(party);}
		};

		//-----------------------------------------------
		// Update Party
		//-----------------------------------------------
		actorUpdate = new GuardedRequest<Party>() {
			protected boolean error() {
				return (
						ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), partyField)
						|| ifMessage(partyField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), partyField)
						|| ifMessage(emailaddressField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errEmailaddress(), emailaddressField)
						|| ifMessage(formatdateField.noValue(), Level.ERROR, CONSTANTS.formatdateError(), formatdateField)
						|| ifMessage(formatphoneField.noValue(), Level.ERROR, CONSTANTS.formatphoneError(), formatphoneField)
						|| ifMessage(!Party.isEmailAddress(emailaddressField.getValue()), Level.ERROR, AbstractField.CONSTANTS.errEmailaddress(), emailaddressField)
//						|| ifMessage((typesField.hasValue(Party.Type.Agent.name()) || typesField.hasValue(Party.Type.Organization.name())) && !partyField.hasValue(AbstractRoot.getOrganizationid()), Level.ERROR, CONSTANTS.partytypeError(), partyField)
				);
			}
			protected void send() {super.send(getValue(new ActorUpdate()));}
			protected void receive(Party party) {
				if (parentTable != null) {parentTable.execute(true);}
				hide();
			}
		};

		//-----------------------------------------------
		// Delete Party
		//-----------------------------------------------
		actorDelete = new GuardedRequest<Party>() {
			protected String popup() {return AbstractField.CONSTANTS.errDeleteOK();}
			public void cancel() {state = oldstate;}
			protected boolean error() {
				return (
						ifMessage(partyField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), partyField)
				);
			}
			protected void send() {super.send(getValue(new ActorDelete()));}
			protected void receive(Party party){
				if (parentTable != null) {parentTable.execute(true);}
				hide();
			}
		};	
	}
}
