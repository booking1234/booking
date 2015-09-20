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
import net.cbtltd.client.resource.Hosts;
import net.cbtltd.client.resource.party.PartyBundle;
import net.cbtltd.client.resource.party.PartyConstants;
import net.cbtltd.client.resource.party.PartyCssResource;
import net.cbtltd.shared.License;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Party.Type;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.finance.CurrencyNameId;
import net.cbtltd.shared.location.CountryNameId;
import net.cbtltd.shared.party.LanguageNameId;
import net.cbtltd.shared.party.PartyCreate;
import net.cbtltd.shared.party.PartyExists;
import net.cbtltd.shared.party.PartyRead;
import net.cbtltd.shared.party.PartyUpdate;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/** The Class PartyPopup is to display and change party instances. */
public class PartyPopup
extends AbstractPopup<Party> {

	private static final PartyConstants CONSTANTS = GWT.create(PartyConstants.class);
	private static final Hosts HOSTS = GWT.create(Hosts.class);
	private static final PartyBundle BUNDLE = PartyBundle.INSTANCE;
	private static final PartyCssResource CSS = BUNDLE.css();
	
	/** The Constant Array TYPES contains the allowed party types. */
	public static final String[] TYPES = {Type.Actor.name(), Type.Affiliate.name(), Type.Agent.name(), Type.Customer.name(), Type.Employee.name(), Type.Employer.name(), Type.Jurisdiction.name(), Type.Organization.name(), Type.Owner.name(), Type.Supplier.name()};
	
	private static GuardedRequest<Party> partyCreate;
	private static GuardedRequest<Party> partyExists;
	private static GuardedRequest<Party> partyRead;
	private static GuardedRequest<Party> partyUpdate;

	private static TextField emailaddressField;
	private static SuggestField partyField;
	private static TextField firstnameField;
	private static TextField lastnameField;
	private static TextField dayphoneField;
	private static TextField mobilephoneField;
	private static TextField faxphoneField;
	private static TextField extranameField;
	private static TextField affiliateField;
	private static TextAreaField postaladdressField;
	private static TextField postalcodeField;
	private static ListField countryField;
	private static ListField currencyField;
	private static ListField languageField;
	private static Label messageField;

	private static HasValue<String> parentField; // field that invoked the popup
	private static TableField<Party> parentTable; // table that invoked popup
	private static Party.Type type;
	private static Label titleLabel;
	
	/** Instantiates a new party pop up panel. */
	public PartyPopup() {
		super(false);
		CSS.ensureInjected();
		createActions();
		setWidget(createContent());
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
	}
	
	private static PartyPopup instance;
	
	/**
	 * Gets the single instance of PartyPopup.
	 *
	 * @return single instance of PartyPopup
	 */
	public static synchronized PartyPopup getInstance() {
		if (instance == null) {instance = new PartyPopup();}
		return instance;
	}

	/**
	 * Shows the panel for the specified party ID.
	 *
	 * @param id the specified party ID.
	 */
	public void show(String id) {
		setEnabled(false);
		partyField.setValue(id);
		partyRead.execute();
	}

	/**
	 * Shows the panel for the specified party type and instance.
	 *
	 * @param state the specified party type.
	 * @param party the specified party instance
	 * @param parentTable the table that invoked the pop up panel.
	 */
	public void show(Party party) {
		partyField.setValue(party.getId());
		partyRead.execute();
	}

	/**
	 * Shows the panel for the specified party type.
	 *
	 * @param type the specified party type.
	 * @param parentTable the table that invoked the pop up panel.
	 */
	public void show(Party.Type type, HasValue<String> parentField, TableField<Party> parentTable) {
		PartyPopup.type = type;
		PartyPopup.parentField = parentField;
		PartyPopup.parentTable = parentTable;
		onReset(Party.INITIAL);
		messageField.setVisible(type == Party.Type.Affiliate);
		partyCreate.execute(true);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {
		if (partyField.sent(change)) {partyRead.execute();}
		else if (emailaddressField.sent(change)) {partyExists.execute();}
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
		party.setId(partyField.getValue());
		if (partyField.isVisible()) {party.setName(partyField.getName());}
		else if (firstnameField.noValue()) {party.setName(lastnameField.getValue());}
		else if (lastnameField.noValue()) {party.setName(firstnameField.getValue());}
		else {party.setName(lastnameField.getValue(), firstnameField.getValue());}
		party.setCreatorid(AbstractRoot.getOrganizationid());
		party.setEmailaddress(emailaddressField.getValue());
		party.setDayphone(dayphoneField.getValue());
		party.setMobilephone(mobilephoneField.getValue());
		party.setFaxphone(faxphoneField.getValue());
		party.setExtraname(extranameField.getValue());
		party.setPostaladdress(postaladdressField.getValue());
		party.setPostalcode(postalcodeField.getValue());
		party.setCountry(countryField.getValue());
		party.setCurrency(currencyField.getValue());
		party.setLanguage(languageField.getValue());
		party.setType(type == null ? null : type.name());
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
			firstnameField.setValue(party.getFirstName());
			lastnameField.setValue(party.getFamilyName());
			emailaddressField.setValue(party.getEmailaddresses());
			dayphoneField.setValue(party.getDayphone());
			mobilephoneField.setValue(party.getMobilephone());
			faxphoneField.setValue(party.getFaxphone());
			extranameField.setValue(party.getExtraname());
			affiliateField.setText(HOSTS.affiliateUrl() + Party.encrypt(party.getId()));
			postaladdressField.setValue(party.getPostaladdress());
			postalcodeField.setValue(party.getPostalcode());
			countryField.setValue(party.getCountry());
			currencyField.setValue(party.getCurrency());
			languageField.setValue(party.getLanguage());

			emailaddressField.setEnabled(hasState(Party.INITIAL)); // || party.noRoles());
			titleLabel.setText(CONSTANTS.partyLabel());
			center();
			setResetting(false);
		}
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
		
		titleLabel = new Label();
		titleLabel.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(titleLabel);
		
		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				PartyPopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);
		
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
		// Family or Company Name field
		//-----------------------------------------------
		lastnameField = new TextField(this, null,
				CONSTANTS.lastnameLabel(),
				tab++);
		lastnameField.setMaxLength(50);
		lastnameField.setReadOption(Party.INITIAL, true);
		lastnameField.setHelpText(CONSTANTS.lastnameHelp());
		form.add(lastnameField);

		//-----------------------------------------------
		// First Name field
		//-----------------------------------------------
		firstnameField = new TextField(this, null,
				CONSTANTS.firstnameLabel(),
				tab++);
		firstnameField.setMaxLength(50);
		firstnameField.setReadOption(Party.INITIAL, true);
		firstnameField.setHelpText(CONSTANTS.firstnameHelp());
		form.add(firstnameField);

		//-----------------------------------------------
		// Extra Name field
		//-----------------------------------------------
		extranameField = new TextField(this, null,
				CONSTANTS.extranameLabel(),
				tab++);
		extranameField.setHelpText(CONSTANTS.extranameHelp());
		form.add(extranameField);

		//-----------------------------------------------
		// Day Phone field
		//-----------------------------------------------
		dayphoneField = new TextField(this, null,
				CONSTANTS.dayphoneLabel(),
				tab++);
		dayphoneField.setHelpText(CONSTANTS.dayphoneHelp());
		form.add(dayphoneField);

		//-----------------------------------------------
		// Mobile Phone field
		//-----------------------------------------------
		mobilephoneField = new TextField(this, null,
				CONSTANTS.mobilephoneLabel(),
				tab++);
		mobilephoneField.setHelpText(CONSTANTS.mobilephoneHelp());
		form.add(mobilephoneField);

		//-----------------------------------------------
		// Fax Phone field
		//-----------------------------------------------
		faxphoneField = new TextField(this,  null,
				CONSTANTS.faxphoneLabel(),
				tab++);
		faxphoneField.setHelpText(CONSTANTS.faxphoneHelp());
		form.add(faxphoneField);

		//-----------------------------------------------
		// Affiliate URL field
		//-----------------------------------------------
		affiliateField = new TextField(this, null,
				CONSTANTS.affiliateLabel(),
				tab++);
		affiliateField.setVisible(false);
		affiliateField.setHelpText(CONSTANTS.affiliateHelp());
		form.add(affiliateField);

		//-----------------------------------------------
		// Postal Address field
		//-----------------------------------------------
		postaladdressField = new TextAreaField(this, null,
				CONSTANTS.postaladdressLabel(),
				tab++);
		postaladdressField.setMaxLength(100);
		postaladdressField.setHelpText(CONSTANTS.postaladdressHelp());
		form.add(postaladdressField);

		//-----------------------------------------------
		// Postal Code field
		//-----------------------------------------------
		postalcodeField = new TextField(this,  null,
				CONSTANTS.postalcodeLabel(),
				tab++);
		postalcodeField.setMaxLength(20);
		postalcodeField.setFieldHalf();
		postalcodeField.setHelpText(CONSTANTS.postalcodeHelp());

		//-----------------------------------------------
		// Country field
		//-----------------------------------------------
		countryField = new ListField(this, null,
				new CountryNameId(),
				CONSTANTS.countryLabel(),
				true,
				tab++);
		countryField.setDefaultValue(AbstractRoot.getCountry());
		countryField.setAllOrganizations(true);
		countryField.setFieldHalf();
		countryField.setHelpText(CONSTANTS.countryHelp());
		
		//-----------------------------------------------
		// Language field
		//-----------------------------------------------
		languageField = new ListField(this, null,
				new LanguageNameId(),
				CONSTANTS.languageLabel(),
				true,
				tab++);
		languageField.setDefaultValue(AbstractRoot.getLanguage());
		languageField.setAllOrganizations(true);
		languageField.setFieldHalf();
		languageField.setHelpText(CONSTANTS.languageHelp());

		//-----------------------------------------------
		// Currency field
		//-----------------------------------------------
		currencyField = new ListField(this, null,
				new CurrencyNameId(),
				CONSTANTS.currencyLabel(),
				true,
				tab++);
		currencyField.setDefaultValue(AbstractRoot.getCurrency());
		currencyField.setAllOrganizations(true);
		currencyField.setFieldHalf();
		currencyField.setHelpText(CONSTANTS.currencyHelp());

		Grid grid = new Grid(2,2);
		form.add(grid);
		grid.setWidget(0, 0, postalcodeField);
		grid.setWidget(0, 1, countryField);
		grid.setWidget(1, 0, languageField);
		grid.setWidget(1, 1, currencyField);

		messageField = new Label(CONSTANTS.messageLabel());
		messageField.setVisible(false);
		messageField.setStyleName(CSS.messageStyle());
		form.add(messageField);

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
		
		final LocalRequest cancelRequest = new LocalRequest() {protected void perform() {hide();}};

		//-----------------------------------------------
		// Save button
		//-----------------------------------------------
		final CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), partyUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.setTitle(AbstractField.CONSTANTS.helpSave());
		bar.add(saveButton);

		//-----------------------------------------------
		// Cancel button
		//-----------------------------------------------
		final CommandButton cancelButton = new CommandButton(this, AbstractField.CONSTANTS.allCancel(), cancelRequest, tab++);
		cancelButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		cancelButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		cancelButton.setTitle(AbstractField.CONSTANTS.helpCancel());
		bar.add(cancelButton);

		//-----------------------------------------------
		// Transition array that defines the finite state machine
		//-----------------------------------------------
		fsm = new ArrayList<Transition>();
		fsm.add(new Transition(Party.INITIAL, cancelButton, Party.CREATED));
		fsm.add(new Transition(Party.INITIAL, saveButton, Party.CREATED));
		
		fsm.add(new Transition(Party.CREATED, cancelButton, Party.CREATED));
		fsm.add(new Transition(Party.CREATED, saveButton, Party.CREATED));

		return bar;
	}

	/* Gets the field next which to display the name help message. */
	private Widget getTargetField() {
		if (lastnameField.isVisible()) {return lastnameField;}
		else {return partyField;}
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
				if (party != null ) {
					party.setState(Party.CREATED);
					setValue(party);
					AbstractField.addMessage(Level.TERSE, CONSTANTS.partyexistsError(), emailaddressField);
				}
			}
		};

		//-----------------------------------------------
		// Create Party
		//-----------------------------------------------
		partyCreate = new GuardedRequest<Party>() {
			protected String popup() {return hasChanged() ? AbstractField.CONSTANTS.errChangeOK() : null;}
			public void cancel() {state = oldstate;}
			protected boolean error() {return false;} //ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), getTargetField());}
			protected void send() {super.send(getValue(new PartyCreate()));}
			protected void receive(Party party){setValue(party);}
		};

		//-----------------------------------------------
		// Read Party
		//-----------------------------------------------
		partyRead = new GuardedRequest<Party>() {
			protected boolean error() {return partyField.noValue();}
			protected void send() {super.send(getValue(new PartyRead()));}
			protected void receive(Party party){setValue(party);}
		};

		//-----------------------------------------------
		// Update Party
		//-----------------------------------------------
		partyUpdate = new GuardedRequest<Party>() {
			protected boolean error() {
				return (
//						ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), getTargetField())
						ifMessage(partyField.noValue() && lastnameField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), getTargetField())
						|| ifMessage(type == null, Level.TERSE, AbstractField.CONSTANTS.errTypes(), getTargetField())
						|| ifMessage(emailaddressField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errEmailaddress(), emailaddressField)
						|| ifMessage(!Party.isEmailAddress(emailaddressField.getValue()), Level.ERROR, AbstractField.CONSTANTS.errEmailaddress(), emailaddressField)
				);
			}
			protected void send() {super.send(getValue(new PartyUpdate()));}
			protected void receive(Party party) {
				if (parentField != null && party != null) {parentField.setValue(party.getId());}
				if (parentTable != null) {parentTable.execute(true);}
//				setValue(party);
				hide();
			}
		};

	}
}
