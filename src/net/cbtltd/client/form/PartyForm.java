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
import net.cbtltd.client.Razor;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.CommandButton;
import net.cbtltd.client.field.DoubleField;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.ProgressField;
import net.cbtltd.client.field.ReportButton;
import net.cbtltd.client.field.ShuttleField;
import net.cbtltd.client.field.StackField;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.field.table.ActionCell;
import net.cbtltd.client.field.table.ActionCell.Delegate;
import net.cbtltd.client.field.table.ActionHeader;
import net.cbtltd.client.field.table.TableError;
import net.cbtltd.client.field.table.TableExecutor;
import net.cbtltd.client.panel.ContactPopup;
import net.cbtltd.client.panel.FinancePopup;
import net.cbtltd.client.panel.JournalPopup;
import net.cbtltd.client.resource.Hosts;
import net.cbtltd.client.resource.party.PartyBundle;
import net.cbtltd.client.resource.party.PartyConstants;
import net.cbtltd.client.resource.party.PartyCssResource;
import net.cbtltd.shared.Attribute;
import net.cbtltd.shared.AttributeMapAction;
import net.cbtltd.shared.Design;
import net.cbtltd.shared.DoubleResponse;
import net.cbtltd.shared.Event;
import net.cbtltd.shared.Finance;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Party.Type;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Report;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.api.HasResetId;
import net.cbtltd.shared.api.HasTable;
import net.cbtltd.shared.finance.CurrencyNameId;
import net.cbtltd.shared.journal.EventJournal;
import net.cbtltd.shared.journal.EventJournalBalance;
import net.cbtltd.shared.journal.EventJournalTable;
import net.cbtltd.shared.location.CountryNameId;
import net.cbtltd.shared.party.AgentNameIdAction;
import net.cbtltd.shared.party.LanguageNameId;
import net.cbtltd.shared.party.PartyCreate;
import net.cbtltd.shared.party.PartyCreatorList;
import net.cbtltd.shared.party.PartyDelete;
import net.cbtltd.shared.party.PartyExists;
import net.cbtltd.shared.party.PartyRead;
import net.cbtltd.shared.party.PartyRelationTable;
import net.cbtltd.shared.party.PartyUpdate;
import net.cbtltd.shared.reservation.ReservationTable;
import net.cbtltd.shared.task.Contact;
import net.cbtltd.shared.task.ContactTable;

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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

/**
 * The Class PartyForm is to manage information about the property manager or agency which employs 
 * the currently logged in user.
 */
public class PartyForm
extends AbstractForm<Party> {

	private static final PartyConstants CONSTANTS = GWT.create(PartyConstants.class);
	private static final Hosts HOSTS = GWT.create(Hosts.class);
	private static final PartyBundle BUNDLE = PartyBundle.INSTANCE;
	private static final PartyCssResource CSS = BUNDLE.css();
	private static final String[] TYPES = {
		Type.Actor.name(), 
		Type.Affiliate.name(), 
		Type.Agent.name(), 
		Type.Customer.name(), 
		Type.Employee.name(), 
		Type.Employer.name(),
		Type.Jurisdiction.name(),
		Type.Organization.name(),
		Type.Owner.name(),
		Type.Supplier.name()
	};

	private static final int CONTACT_ROWS = 25;
	private static final int RELATION_ROWS = 26;
	private static final int RESERVATION_ROWS = 26;
	private static final int EVENTJOURNAL_ROWS = 18;

	private static GuardedRequest<Party> partyCreate;
	private static GuardedRequest<Party> partyRead;
	private static GuardedRequest<Party> partyUpdate;
	private static GuardedRequest<Party> partyExists;
	private static GuardedRequest<Party> partyDelete;
	private static GuardedRequest<DoubleResponse> partyBalance;
	private static GuardedRequest<Table<NameId>> creatorNameIds;
//	private static GuardedRequest<Table<Reservation>> reservationHistory;

	//Party
	private static SuggestField partyField;
	private static TextField firstnameField;
	private static TextField lastnameField;
//	private static ProgressField progressField;
	private static TextField emailaddressField;
	private static ShuttleField typesField;
	private static ListField creatorField;
	private static TextField dayphoneField;
	private static TextField faxphoneField;
	private static TextField mobilephoneField;
	private static TextField nightphoneField;
	private static TextField webaddressField;
	private static TextField extranameField;
	private static TextField posField;
	private static TextField affiliateField;
	private static TextField postalcodeField;
	private static ListField countryField;
	private static ListField currencyField;
	private static ListField languageField;
	private static TextField idField;
	private static TextField taxnumberField;
	private static ListField financeField;
	private static ListField jurisdictionField;
	private static DoubleField balanceField;
	
	private static TextAreaField postaladdressField;
	private static TextAreaField descriptionField;
	private static TextAreaField notesField;

	private static StackLayoutPanel stackPanel;
	private static StackField attributeField;
	private static TableField<Contact> contactTable;
	private static TableField<Party> relationTable;
	private static TableField<Reservation> reservationTable;
	private static TableField<EventJournal> eventjournalTable;

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#permission()
	 */
	@Override
	public short[] permission(){return AccessControl.PARTY_PERMISSION;}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#getValue()
	 */
	@Override
	public Party getValue() {return getValue(new Party());}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {
		if (partyField.sent(change)) {partyRead.execute();}
		else if (emailaddressField.sent(change)) {partyExists.execute();}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onReset(net.cbtltd.shared.api.HasNameId)
	 */
	@Override
	public void onReset(HasResetId resetid) {
		partyField.setValue(resetid.getResetid());
		partyRead.execute();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#initialize()
	 */
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
		stackPanel.setVisible(true);
		stackPanel.addSelectionHandler(new SelectionHandler<Integer>() {
			public void onSelection(SelectionEvent<Integer> event) {
				if (event.getSelectedItem() != 0 && partyField.noValue()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.partyError(), partyField);}
				refreshStackPanel();
			}
		});
		stackPanel.addStyleName(CSS.stackStyle());
		stackPanel.add(createDescription(), CONSTANTS.descriptionLabel(), 1.5);
		stackPanel.add(createContact(), CONSTANTS.contactLabel(), 1.5);
		stackPanel.add(createReservation(), CONSTANTS.reservationLabel(), 1.5);
		stackPanel.add(createRelation(), CONSTANTS.relationLabel(), 1.5);
//		stackPanel.add(createFinance(), CONSTANTS.financesLabel(), 1.5);
		stackPanel.add(createEventJournal(), CONSTANTS.eventjournalLabel(), 1.5);
		onRefresh();
		onReset(Party.CREATED);
//		Window.alert("initialized");
	}

	/* Refreshes the open stack panel */
	private void refreshStackPanel() {
		switch (stackPanel.getVisibleIndex()) {
		case 1:	contactTable.execute(); break;
		case 2: reservationTable.execute(); break;
		case 3: relationTable.execute(); break;
		case 4: eventjournalTable.execute(); break;
		}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#getValue(net.cbtltd.shared.api.HasState)
	 */
	/**
	 * 
	 *
	 * @param party 
	 * @return 
	 */
	private Party getValue(Party party) {
		party.setState(state);
		party.setOldstate(oldstate);
		party.setOrganizationid(AbstractRoot.getOrganizationid());
		party.setActorid(AbstractRoot.getActorid());
		party.setId(partyField.getValue());
		if (partyField.isVisible()) {party.setName(partyField.getName());}
		else if (firstnameField.noValue()) {party.setName(lastnameField.getValue());}
		else if (lastnameField.noValue()) {party.setName(firstnameField.getValue());}
		else {party.setName(lastnameField.getValue(), firstnameField.getValue());}
		party.setEmailaddress(emailaddressField.getValue());
		party.setCreatorid(creatorField.getValue());
		party.setDayphone(dayphoneField.getValue());
		party.setFaxphone(faxphoneField.getValue());
		party.setMobilephone(mobilephoneField.getValue());
		party.setNightphone(nightphoneField.getValue());
		party.setWebaddress(webaddressField.getValue());
		party.setJurisdictionid(jurisdictionField.getValue());
		party.setTaxnumber(taxnumberField.getValue());
		party.setExtraname(extranameField.getValue());
		party.setFinanceid(financeField.getValue());
		party.setPostaladdress(postaladdressField.getValue());
		party.setPostalcode(postalcodeField.getValue());
		party.setPublicText(descriptionField.getText());
		party.setPrivateText(AbstractRoot.getOrganizationid(), notesField.getText());
		party.setCountry(countryField.getValue());
		party.setCurrency(currencyField.getValue());
		party.setLanguage(languageField.getValue());
		party.setTypes(typesField.getValue());
		Log.debug("getValue " + party);
		return party;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#setValue(net.cbtltd.shared.api.HasState)
	 */
	public void setValue(Party party) {
		Log.debug("setValue " + party);
		if (party == null) {onReset(Party.CREATED);}
		else {
			setResetting(true);
			onStateChange(party.getState());
			partyField.setValue(party.getId());
			firstnameField.setValue(party.getFirstName());
			lastnameField.setValue(party.getFamilyName());
			emailaddressField.setValue(party.getEmailaddresses());
			emailaddressField.setEnabled(hasState(Party.INITIAL) || party.noRoles());
			creatorField.setValue(party.getCreatorid());
			dayphoneField.setValue(party.getDayphone());
			faxphoneField.setValue(party.getFaxphone());
			mobilephoneField.setValue(party.getMobilephone());
			nightphoneField.setValue(party.getNightphone());
			webaddressField.setValue(party.getWebaddress());
			posField.setText(Party.encrypt(party.getId()));
			affiliateField.setText(HOSTS.affiliateUrl() + Party.encrypt(party.getId()));
			extranameField.setValue(party.getExtraname());
			financeField.setValue(party.getFinanceid());
			postaladdressField.setValue(party.getPostaladdress());
			postalcodeField.setValue(party.getPostalcode());
			idField.setText(party.getId());
			jurisdictionField.setValue(party.getJurisdictionid());
			taxnumberField.setValue(party.getTaxnumber());
			notesField.setText(party.getPrivateText(AbstractRoot.getOrganizationid()));
			descriptionField.setText(party.getPublicText());
			countryField.setValue(party.getCountry());
			currencyField.setValue(party.getCurrency());
			languageField.setValue(party.getLanguage());
			typesField.setValue(party.getTypes());

			idField.setVisible(!hasState(Party.INITIAL));
			posField.setVisible(!hasState(Party.INITIAL) && AbstractRoot.getSession().hasPermission(AccessControl.SUPERUSER));

			refreshStackPanel();

			emailaddressField.setEnabled(hasState(Party.INITIAL) || party.hasCreatorid(AbstractRoot.getOrganizationid())); // || party.noRoles());
//			posField.setVisible(!hasState(Party.INITIAL));
			affiliateField.setVisible(!hasState(Party.INITIAL));

			setResetting(false);
		}
	}

	/**
	 * 
	 *
	 * @param action 
	 * @return 
	 */
	private EventJournal getValue(EventJournal action) {
		action.setOrganizationid(AbstractRoot.getOrganizationid());
		action.setEntitytype(NameId.Type.Party.name());
		action.setEntityid(partyField.getValue());
		action.setCurrency(currencyField.getValue());
		return action;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#setValue(net.cbtltd.shared.api.HasState)
	 */
	/* 
	 * Creates the primary panel.
	 * 
	 * @return the primary panel.
	 */
	/**
	 * 
	 *
	 * @return 
	 */
	private FlowPanel createContent() {
		final FlowPanel panel = new FlowPanel();
		final VerticalPanel form =  new VerticalPanel();
		form.addStyleName(AbstractField.CSS.cbtAbstractControl());
		panel.add(form);
		final Label title = new Label(CONSTANTS.titleLabel());
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);

		//-----------------------------------------------
		// Party field
		//-----------------------------------------------
		
		if (!AbstractRoot.noSession() && AbstractRoot.session.hasPermission(AccessControl.ADMINISTRATOR)){
		    AgentNameIdAction nameIdAction = new AgentNameIdAction(Service.PARTY);
		    nameIdAction.setOrganizationid(AbstractRoot.getOrganizationid());
		    partyField = new SuggestField(this, null, 
			    	nameIdAction, CONSTANTS.partynameLabel(),
				20, tab++);
		}else {
		    partyField = new SuggestField(this, null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.partynameLabel(),
				20,
				tab++);
		}
		
		partyField.setReadOption(Product.CREATED, true);
		partyField.setReadOption(Product.SUSPENDED, true);
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
		// Party progress bar
		//-----------------------------------------------
//		progressField = new ProgressField(this, null, CONSTANTS.progressLabel(), 0, 100, tab);
//		progressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
//		progressField.setReadOption(Product.CREATED, true);
//		progressField.setReadOption(Product.SUSPENDED, true);
//		progressField.setHelpText(CONSTANTS.progressHelp());
//		form.add(progressField);

		//-----------------------------------------------
		// Email address to identify party
		//-----------------------------------------------
		emailaddressField = new TextField(this, null,
				CONSTANTS.emailaddressLabel(),
				tab++);
		emailaddressField.setMaxLength(100);
		emailaddressField.setHelpText(CONSTANTS.emailaddressHelp());
		form.add(emailaddressField);

		//-----------------------------------------------
		// Types field
		//-----------------------------------------------
		typesField = new ShuttleField(this, null,
				NameId.getList(CONSTANTS.partyTypes(), TYPES),
				CONSTANTS.typesLabel(),
				tab++);
		typesField.setFieldStyle(CSS.typesStyle());
		typesField.setHelpText(CONSTANTS.typesHelp());
		form.add(typesField);

		//-----------------------------------------------
		// Creator field
		//-----------------------------------------------
		creatorField = new ListField(this, null,
				CONSTANTS.creatorLabel(),
				false,
				tab++);
		creatorField.setVisible(false);
		creatorField.setHelpText(CONSTANTS.creatorHelp());
		form.add(creatorField);
		
		//-----------------------------------------------
		// Day Phone field
		//-----------------------------------------------
		dayphoneField = new TextField(this, null,
				CONSTANTS.dayphoneLabel(),
				AbstractRoot.getFormatPhone(),
				tab++);
		dayphoneField.setMaxLength(20);
		dayphoneField.setFieldHalf();
		dayphoneField.setHelpText(CONSTANTS.dayphoneHelp());

		//-----------------------------------------------
		// Fax Phone field
		//-----------------------------------------------
		faxphoneField = new TextField(this,  null,
				CONSTANTS.faxphoneLabel(),
				AbstractRoot.getFormatPhone(),
				tab++);
		faxphoneField.setMaxLength(20);
		faxphoneField.setFieldHalf();
		faxphoneField.setHelpText(CONSTANTS.faxphoneHelp());
		
		HorizontalPanel df = new HorizontalPanel();
		form.add(df);
		df.add(dayphoneField);
		df.add(faxphoneField);

		//-----------------------------------------------
		// Mobile Phone field
		//-----------------------------------------------
		mobilephoneField = new TextField(this, null,
				CONSTANTS.mobilephoneLabel(),
				AbstractRoot.getFormatPhone(),
				tab++);
		mobilephoneField.setMaxLength(20);
		mobilephoneField.setFieldHalf();
		mobilephoneField.setHelpText(CONSTANTS.mobilephoneHelp());

		//-----------------------------------------------
		// Night Phone (Skype) field
		//-----------------------------------------------
		nightphoneField = new TextField(this,  null,
				CONSTANTS.nightphoneLabel(),
				tab++);
		nightphoneField.setMaxLength(20);
		nightphoneField.setFieldHalf();
		nightphoneField.setHelpText(CONSTANTS.nightphoneHelp());
		
		HorizontalPanel mn = new HorizontalPanel();
		form.add(mn);
		mn.add(mobilephoneField);
		mn.add(nightphoneField);

		//-----------------------------------------------
		// Web Address field
		//-----------------------------------------------
		webaddressField = new TextField(this,  null,
				CONSTANTS.webaddressLabel(),
				tab++);
		webaddressField.setMaxLength(100);
		webaddressField.setHelpText(CONSTANTS.webaddressHelp());
//		form.add(webaddressField);

		//-----------------------------------------------
		// Contact field
		//-----------------------------------------------
		extranameField = new TextField(this, null,
				CONSTANTS.extranameLabel(),
				tab++);
		extranameField.setMaxLength(20);
		extranameField.setHelpText(CONSTANTS.extranameHelp());
		form.add(extranameField);

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

		//-----------------------------------------------
		// Party ID field
		//-----------------------------------------------
		idField = new TextField(this, null,
				CONSTANTS.idLabel(),
				tab++);
		idField.setEnabled(false);
//		idField.setVisible(false);
		idField.setFieldHalf();
		idField.setHelpText(CONSTANTS.idHelp());
		//form.add(idField);

		//-----------------------------------------------
		// POS Code field
		//-----------------------------------------------
		posField = new TextField(this, null,
				CONSTANTS.posLabel(),
				tab++);
//		posField.setEnabled(false);
		posField.setVisible(false);
		posField.setFieldHalf();
		posField.setHelpText(CONSTANTS.posHelp()); // CBT=039d737e208a0819 Unallocated=077033573582b133
		//form.add(posField);

		//-----------------------------------------------
		// Tax Number field
		//-----------------------------------------------
		taxnumberField = new TextField(this, null,
				CONSTANTS.taxnumberLabel(),
				tab++);
		taxnumberField.setFieldHalf();
		taxnumberField.setHelpText(CONSTANTS.taxnumberHelp());

		Grid grid = new Grid(3,2);
		form.add(grid);
		grid.setWidget(0, 0, postalcodeField);
		grid.setWidget(0, 1, countryField);
		grid.setWidget(1, 0, languageField);
		grid.setWidget(1, 1, currencyField);
		grid.setWidget(2, 0, idField);
		grid.setWidget(2, 1, taxnumberField);

		//-----------------------------------------------
		// Affiliate URL field
		//-----------------------------------------------
		affiliateField = new TextField(this, null,
				CONSTANTS.affiliateLabel(),
				tab++);
		affiliateField.setHelpText(CONSTANTS.affiliateHelp());
		//form.add(affiliateField);

		//-----------------------------------------------
		// Finance field
		//-----------------------------------------------
		financeField = new ListField(this, null,
				new NameIdAction(Service.FINANCE),
				CONSTANTS.financeLabel(),
				true,
				tab++);
		financeField.setHelpText(CONSTANTS.financeHelp());

		final Image financeButton = new Image(AbstractField.BUNDLE.plus());
		financeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				FinancePopup.getInstance().show(Finance.Type.Bank, financeField);
			}
		});
		financeField.addButton(financeButton);
		//form.add(financeField);
		
		//-----------------------------------------------
		// Jurisdiction field
		//-----------------------------------------------
		jurisdictionField = new ListField(this, null,
				new NameIdAction(Service.PARTY, Party.Type.Jurisdiction.name()),
				CONSTANTS.jurisdictionLabel(),
				true,
				tab++);
		jurisdictionField.setAllOrganizations(true);
		jurisdictionField.setHelpText(CONSTANTS.jurisdictionHelp());
//		form.add(jurisdictionField);

		form.add(createCommands());

		FlowPanel shadow = new FlowPanel();
		shadow.addStyleName(AbstractField.CSS.cbtAbstractShadow());
		panel.add(shadow);

		return panel;
	}

	/* 
	 * Creates the command bar.
	 * 
	 * @return the command bar.
	 */
	/**
	 * 
	 *
	 * @return 
	 */
	private HorizontalPanel createCommands() {

		final HorizontalPanel bar = new HorizontalPanel();
		bar.addStyleName(AbstractField.CSS.cbtAbstractCommand());

		final LocalRequest resetRequest = new LocalRequest() {protected void perform() {onFocus();}}; //onReset(Party.CREATED);}};

		//-----------------------------------------------
		// Create button
		//-----------------------------------------------
		CommandButton createButton = new CommandButton(this, AbstractField.CONSTANTS.allCreate(), partyCreate, tab++);
		createButton.addStyleName(AbstractField.CSS.cbtGradientGreen());
		createButton.setTitle(CONSTANTS.createHelp());
		bar.add(createButton);

		//-----------------------------------------------
		// Save button
		//-----------------------------------------------
		final CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), partyUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.setTitle(CONSTANTS.saveHelp());
		bar.add(saveButton);

		//-----------------------------------------------
		// Delete button
		//-----------------------------------------------
		final CommandButton deleteButton = new CommandButton(this, AbstractField.CONSTANTS.allDelete(), partyDelete, tab++);
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
		// The array of transitions to define the finite state machine.
		//-----------------------------------------------
		fsm = new ArrayList<Transition>();
		fsm.add(new Transition(Party.INITIAL, saveButton, Party.CREATED));
		fsm.add(new Transition(Party.INITIAL, cancelButton, Party.CREATED));
		fsm.add(new Transition(Party.CREATED, createButton, Party.INITIAL));
		fsm.add(new Transition(Party.CREATED, saveButton, Party.CREATED));
		fsm.add(new Transition(Party.CREATED, deleteButton, Party.CREATED));

		return bar;
	}

	/* 
	 * Creates the description stack panel.
	 * 
	 * @return the description stack panel.
	 */
	/**
	 * 
	 *
	 * @return 
	 */
	private HorizontalPanel createDescription () {
		HorizontalPanel panel = new HorizontalPanel();

		FlowPanel text = new FlowPanel();
		panel.add(text);
		
		//-----------------------------------------------
		// Notes field
		//-----------------------------------------------
		notesField = new TextAreaField(this, null,
				CONSTANTS.notesLabel(),
				new LanguageNameId(),
				tab++);
		notesField.setFieldStyle(CSS.notesStyle());
		notesField.setDefaultValue(CONSTANTS.notesEmpty());
		notesField.setMaxLength(Text.MAX_TEXT_SIZE);
		text.add(notesField);

		//-----------------------------------------------
		// Description field
		//-----------------------------------------------
		descriptionField = new TextAreaField(this, null,
				CONSTANTS.descriptionLabel(),
//				new LanguageNameId(),
				tab++);
		descriptionField.setFieldStyle(CSS.notesStyle());
		descriptionField.setDefaultValue(CONSTANTS.descriptionEmpty());
		descriptionField.setMaxLength(Text.MAX_TEXT_SIZE);
		text.add(descriptionField);

		//-----------------------------------------------
		// Attribute shuttle
		//-----------------------------------------------
		ScrollPanel scroll = new ScrollPanel();
		panel.add(scroll);
		scroll.addStyleName(CSS.attributeStyle());

		attributeField = new StackField(this, null,
				new AttributeMapAction(Attribute.PARTY, Attribute.RZ, AbstractRoot.getLanguage()),
				CONSTANTS.attributeLabel(),
				tab++);
		attributeField.setUniquekeys(Attribute.UNIQUE_KEYS);
		scroll.add(attributeField);
		return panel;
	}
	
	/* 
	 * Creates the panel displayed when there are no contacts.
	 * 
	 * @return the empty panel.
	 */
	/**
	 * 
	 *
	 * @return 
	 */
	private Widget contacttableEmpty() {
		FlowPanel panel = new FlowPanel();

		Button emptyButton = new Button(AbstractField.CONSTANTS.allCreate());
		emptyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (partyField.noValue()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.partyError(), partyField);}
				else {ContactPopup.getInstance().show(NameId.Type.Party, partyField.getValue(), contactTable);}
			}
		});
		panel.add(emptyButton);

		panel.add(new HTML(CONSTANTS.contacttableEmpty()));
		panel.add(new Image(BUNDLE.contacttableEmpty()));
		return panel;
	}

	/* 
	 * Creates the contact list stack panel.
	 * 
	 * @return the contact list stack panel.
	 */
	/**
	 * 
	 *
	 * @return 
	 */
	private ScrollPanel createContact() {
		ScrollPanel panel = new ScrollPanel();

		//-----------------------------------------------
		// Contact selection handler
		//-----------------------------------------------
		final NoSelectionModel<Contact> selectionModel = new NoSelectionModel<Contact>();
		final SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				if (partyField.noValue()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.partyError(), partyField);}
				else {ContactPopup.getInstance().show(NameId.Type.Party, partyField.getValue(), selectionModel.getLastSelectedObject(), contactTable);}
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// Contact table
		//-----------------------------------------------
		contactTable = new TableField<Contact>(this, null, 
				new ContactTable(),
				selectionModel, 
				CONTACT_ROWS,
				tab++);

		contactTable.setTableError(new TableError() {
			@Override
			public boolean error() {return partyField.noValue();}
		});

		contactTable.setTableExecutor(new TableExecutor<ContactTable>() {
			public void execute(ContactTable action) {
				action.setActivity(NameId.Type.Party.name());
				action.setId(partyField.getValue());
			}
		});

		contactTable.setEmptyValue(contacttableEmpty());
		contactTable.setOrderby(Contact.DATE);

		int col = 0;

		//-----------------------------------------------
		// Change button
		//-----------------------------------------------
		final Column<Contact, Contact> changeButton = new Column<Contact, Contact> ( 
				new ActionCell<Contact>(AbstractField.CONSTANTS.allChange(), AbstractField.CSS.cbtTableFieldChangeButton(),
						new Delegate<Contact>(){
					public void execute(Contact contact) {
						if (partyField.noValue()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.partyError(), partyField);}
						else {ContactPopup.getInstance().show(NameId.Type.Party, partyField.getValue(), contact, contactTable);}
					}
				}
				)
		)
		{
			public Contact getValue(Contact contact){return contact;}
		};

		//-----------------------------------------------
		// Create button
		//-----------------------------------------------
		final ActionHeader<Contact> createButton = new ActionHeader<Contact>(
				new ActionCell<Contact>(
						AbstractField.CONSTANTS.allCreate(),
						AbstractField.CSS.cbtTableFieldCreateButton(),
						new Delegate<Contact>(){
							public void execute(Contact contact ) {
								if (partyField.noValue()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.partyError(), partyField);}
								else {ContactPopup.getInstance().show(NameId.Type.Party, partyField.getValue(), contactTable);
								}
							}
						}
				)
		)
		{
			public Contact getValue(Contact contact){
				return contact;
			}
		};

		contactTable.addColumn(changeButton, createButton);

		//-----------------------------------------------
		// Date column
		//-----------------------------------------------
		Column<Contact, Date> date = new Column<Contact, Date>(new DateCell(AbstractRoot.getDTF())) {
			@Override
			public Date getValue( Contact contact ) {return Time.getDateClient(contact.getDate());}
		};
		contactTable.addDateColumn(date, CONSTANTS.contactHeaders()[col++], Contact.DATE);

		//-----------------------------------------------
		// Addressee column
		//-----------------------------------------------
		Column<Contact, String> name = new Column<Contact, String>(new TextCell()) {
			@Override
			public String getValue( Contact contact ) {return contact.getName(30);}
		};
		contactTable.addStringColumn(name, CONSTANTS.contactHeaders()[col++], Contact.NAME);

		//-----------------------------------------------
		// Medium column
		//-----------------------------------------------
		Column<Contact, String> type = new Column<Contact, String>( new TextCell()) {
			@Override
			public String getValue( Contact contact ) {return contact.getType();}
		};
		contactTable.addStringColumn(type, CONSTANTS.contactHeaders()[col++], Contact.TYPE);

		//-----------------------------------------------
		// Notes column
		//-----------------------------------------------
		Column<Contact, String> notes = new Column<Contact, String>( new TextCell() ) {
			@Override
			public String getValue( Contact contact ) {return contact.getNotes(80);}
		};
		contactTable.addStringColumn(notes, CONSTANTS.contactHeaders()[col++], Contact.NOTES);

		panel.add(contactTable);
		return panel;
	}

	/* 
	 * Creates the panel displayed when there are no relations.
	 * 
	 * @return the panel displayed when there are no relations.
	 */
	/**
	 * 
	 *
	 * @return 
	 */
	private Widget relationtableEmpty() {
		FlowPanel panel = new FlowPanel();

		panel.add(new HTML(CONSTANTS.relationtableEmpty()));

		panel.add(new Image(BUNDLE.relationtableEmpty()));
		return panel;
	}

	/* 
	 * Creates the relation table stack panel.
	 * 
	 * @return the relation table stack panel.
	 */
	/**
	 * 
	 *
	 * @return 
	 */
	private ScrollPanel createRelation() {
		ScrollPanel panel = new ScrollPanel();
		
		//-----------------------------------------------
		// Relationship Selection model
		//-----------------------------------------------
		final NoSelectionModel<Party> selectionModel = new NoSelectionModel<Party>();
		SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				Party party = selectionModel.getLastSelectedObject();
				partyField.setValue(party.getId());
				partyRead.execute(true);
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// Relationship table
		//-----------------------------------------------
		relationTable = new TableField<Party>(this, null, 
				new PartyRelationTable(),
				selectionModel,
				RELATION_ROWS, 
				tab++);

		relationTable.setEmptyValue(relationtableEmpty());
		reservationTable.setOrderby(Party.STATE); // + HasTable.ORDER_BY_DESC);
//		relationTable.addStyleName(CSS.relationStyle());

		relationTable.setTableError(new TableError() {
			@Override
			public boolean error() {return partyField.noValue();}
		});

		relationTable.setTableExecutor(new TableExecutor<PartyRelationTable>() {
			public void execute(PartyRelationTable action) {action.setId(partyField.getValue());}
		});

		int col = 0;

		//-----------------------------------------------
		// Rank column
		//-----------------------------------------------
		Column<Party, Number> rank = new Column<Party, Number>(new NumberCell(AbstractField.IF)) {
			@Override
			public Integer getValue( Party relation ) {return relation.getRank();}
		};
		relationTable.addNumberColumn( rank, CONSTANTS.relationtableHeaders()[col++]);
		
		//-----------------------------------------------
		// Relationship column
		//-----------------------------------------------
		Column<Party, String> state = new Column<Party, String>(new TextCell()) {
			@Override
			public String getValue( Party relation ) {return relation.getState();}
		};
		relationTable.addStringColumn(state, CONSTANTS.relationtableHeaders()[col++]);

		//-----------------------------------------------
		// Name column
		//-----------------------------------------------
		Column<Party, String> name = new Column<Party, String>(new TextCell()) {
			@Override
			public String getValue( Party relation ) {return relation.getName(20);}
		};
		relationTable.addStringColumn(name, CONSTANTS.relationtableHeaders()[col++]);

		//-----------------------------------------------
		// Emailaddress column
		//-----------------------------------------------
		Column<Party, String> emailaddress = new Column<Party, String>(new TextCell()) {
			@Override
			public String getValue( Party relation ) {return relation.getEmailaddress();}
		};
		relationTable.addStringColumn(emailaddress, CONSTANTS.relationtableHeaders()[col++]);

		//-----------------------------------------------
		// Phone column
		//-----------------------------------------------
		Column<Party, String> dayphone = new Column<Party, String>(new TextCell()) {
			@Override
			public String getValue( Party relation ) {return relation.getDayphone();}
		};
		relationTable.addStringColumn(dayphone, CONSTANTS.relationtableHeaders()[col++]);

		//-----------------------------------------------
		// Mobile Phone column
		//-----------------------------------------------
		Column<Party, String> mobilephone = new Column<Party, String>(new TextCell()) {
			@Override
			public String getValue( Party relation ) {return relation.getMobilephone();}
		};
		relationTable.addStringColumn(mobilephone, CONSTANTS.relationtableHeaders()[col++]);
		panel.add(relationTable);

		return panel;
	}

	/* 
	 * Creates the panel displayed when there are no reservations.
	 * 
	 * @return the panel displayed when there are no reservations.
	 */
	/**
	 * 
	 *
	 * @return 
	 */
	private Widget reservationtableEmpty() {
		FlowPanel panel = new FlowPanel();

		panel.add(new HTML(CONSTANTS.reservationtableEmpty()));
		panel.add(new Image(BUNDLE.reservationtableEmpty()));
		return panel;
	}

	/* 
	 * Creates the reservation table stack panel.
	 * 
	 * @return the reservation table stack panel.
	 */
	/**
	 * 
	 *
	 * @return 
	 */
	private ScrollPanel createReservation() {
		ScrollPanel panel = new ScrollPanel();

		//-----------------------------------------------
		// Reservation selection model
		//-----------------------------------------------
		final NoSelectionModel<Reservation> selectionModel = new NoSelectionModel<Reservation>();
		SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				if (AbstractRoot.readable(AccessControl.ORGANIZATION_ROLES)) {
					AbstractRoot.render(Razor.RESERVATION_TAB, selectionModel.getLastSelectedObject());
				}
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		reservationTable = new TableField<Reservation>(this, null, 
				new ReservationTable(),
				selectionModel,
				RESERVATION_ROWS, 
				tab++);

		reservationTable.setEmptyValue(reservationtableEmpty());
		reservationTable.setOrderby(Reservation.NAME + HasTable.ORDER_BY_DESC);

		reservationTable.setTableError(new TableError() {
			@Override
			public boolean error() {return AbstractRoot.noOrganizationid() || partyField.noValue();}
		});

		reservationTable.setTableExecutor(new TableExecutor<ReservationTable>() {
			@Override
			public void execute(ReservationTable action) {
				action.setOrganizationid(AbstractRoot.getOrganizationid());
				action.setId(partyField.getValue());
			}
		});

		int col = 0;

		//-----------------------------------------------
		// Reference Number column
		//-----------------------------------------------
		Column<Reservation, String> name = new Column<Reservation, String>(new TextCell()) {
			@Override
			public String getValue( Reservation reservation ) {return reservation.getName();}
		};
		reservationTable.addStringColumn(name, CONSTANTS.reservationtableHeaders()[col++], Reservation.NAME);

		//-----------------------------------------------
		// Property Name column
		//-----------------------------------------------
		Column<Reservation, String> productname = new Column<Reservation, String>( new TextCell()) {
			@Override
			public String getValue( Reservation reservation ) {return reservation.getProductname();}
		};
		reservationTable.addStringColumn(productname, CONSTANTS.reservationtableHeaders()[col++], Reservation.PRODUCTNAME);

//		//-----------------------------------------------
//		// Actor Name column
//		//-----------------------------------------------
//		Column<Reservation, String> actorname = new Column<Reservation, String>( new TextCell()) {
//			@Override
//			public String getValue( Reservation reservation ) {return reservation.getActorname();}
//		};
//		reservationTable.addStringColumn(actorname, CONSTANTS.reservationtableHeaders()[col++], Reservation.ACTORNAME);

		//-----------------------------------------------
		// State column
		//-----------------------------------------------
		Column<Reservation, String> state = new Column<Reservation, String>( new TextCell()) {
			@Override
			public String getValue( Reservation reservation ) {return reservation.getState();}
		};
		reservationTable.addStringColumn(state, CONSTANTS.reservationtableHeaders()[col++], Reservation.STATE);

		//-----------------------------------------------
		// Date column
		//-----------------------------------------------
		Column<Reservation, Date> date = new Column<Reservation, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( Reservation eventjournal ) {return Time.getDateClient(eventjournal.getDate());}
		};
		reservationTable.addDateColumn(date, CONSTANTS.reservationtableHeaders()[col++], Reservation.DATE);

		//-----------------------------------------------
		// From Date column
		//-----------------------------------------------
		Column<Reservation, Date> fromdate = new Column<Reservation, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( Reservation eventjournal ) {return Time.getDateClient(eventjournal.getFromdate());}
		};
		reservationTable.addDateColumn(fromdate, CONSTANTS.reservationtableHeaders()[col++], Reservation.FROMDATE);

		//-----------------------------------------------
		// To Date column
		//-----------------------------------------------
		Column<Reservation, Date> todate = new Column<Reservation, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( Reservation eventjournal ) {return Time.getDateClient(eventjournal.getTodate());}
		};
		reservationTable.addDateColumn(todate, CONSTANTS.reservationtableHeaders()[col++], Reservation.TODATE);

		//-----------------------------------------------
		// Notes column
		//-----------------------------------------------
		Column<Reservation, String> notes = new Column<Reservation, String>( new TextCell() ) {
			@Override
			public String getValue( Reservation reservation ) {return reservation.getNotes(100);}
		};
		reservationTable.addStringColumn(notes, CONSTANTS.reservationtableHeaders()[col++], Reservation.NOTES);

		panel.add(reservationTable);
		return panel;
	}

//	/* 
//	 * Creates the panel displayed when there are no cash or bank accounts.
//	 * 
//	 * @return the panel displayed when there are no cash or bank accounts.
//	 */
//	private Widget financetableEmpty() {
//		FlowPanel panel = new FlowPanel();
//
//		Button emptyButton = new Button(AbstractField.CONSTANTS.allCreate());
//		emptyButton.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				if (partyField.noValue()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.partyError(), partyField);}
//				else {FinancePopup.getInstance().show(Finance.Type.Bank, null, financeTable);}
//			}
//		});
//		panel.add(emptyButton);
//
//		panel.add(new HTML(CONSTANTS.financetableEmpty()));
//
//		panel.add(new Image(BUNDLE.financetableEmpty()));
//		return panel;
//	}
//
//	/* 
//	 * Creates the cash and bank account table stack panel.
//	 * 
//	 * @return the cash and bank account table stack panel.
//	 */
//	/**
//	 * 
//	 *
//	 * @return 
//	 */
//	private ScrollPanel createFinance() {
//		ScrollPanel panel = new ScrollPanel();
//
//		financeTable = new TableField<Finance>(this, null, 
//				new FinanceTable(),
//				FINANCE_ROWS, 
//				tab++);
//
//		financeTable.setEmptyValue(financetableEmpty());
//		financeTable.setOrderby(Finance.NAME);
////		financeTable.addStyleName(CSS.relationStyle());
//
//		financeTable.setTableError(new TableError() {
//			@Override
//			public boolean error() {return partyField.noValue();}
//		});
//
//		financeTable.setTableExecutor(new TableExecutor<FinanceTable>() {
//			@Override
//			public void execute(FinanceTable action) {action.setId(partyField.getValue());}
//		});
//
//		int col = 0;
//
//		//-----------------------------------------------
//		// Change / Create buttons
//		//-----------------------------------------------
//		Column<Finance, Finance> changeButton = new Column<Finance, Finance>( 
//				new ActionCell<Finance>(AbstractField.CONSTANTS.allChange(), AbstractField.CSS.cbtTableFieldChangeButton(),
//						new Delegate<Finance>(){
//					public void execute( Finance finance ){
//						FinancePopup.getInstance().show(finance, null, financeTable);
//					}
//				}
//				)
//		)
//		{
//			public Finance getValue(Finance finance){return finance;}
//		};
//
//		ActionHeader<Finance> createButton = new ActionHeader<Finance>(
//				new ActionCell<Finance>(
//						AbstractField.CONSTANTS.allCreate(), 
//						AbstractField.CSS.cbtTableFieldCreateButton(),
//						new Delegate<Finance>(){
//							public void execute(Finance finance ) {
//								if (partyField.noValue()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.partyError(), partyField);}
//								else {FinancePopup.getInstance().show(Finance.Type.Bank, null, financeTable);}
//							}
//						}
//				)
//		)
//		{
//			public Finance getValue(Finance finance){
//				return finance;
//			}
//		};
//
//		financeTable.addColumn(changeButton, createButton);
//
//		//-----------------------------------------------
//		// Account Name column
//		//-----------------------------------------------
//		Column<Finance, String> name = new Column<Finance, String>(new TextCell()) {
//			@Override
//			public String getValue( Finance finance ) {return finance.getName();}
//		};
//		financeTable.addStringColumn(name, CONSTANTS.financetableHeaders()[col++], Finance.NAME);
//
//		//-----------------------------------------------
//		// Bank Name column
//		//-----------------------------------------------
//		Column<Finance, String> bankname = new Column<Finance, String>( new TextCell()) {
//			@Override
//			public String getValue( Finance finance ) {return Model.decrypt(finance.getBankname());}
//		};
//		financeTable.addStringColumn(bankname, CONSTANTS.financetableHeaders()[col++], Finance.BANKNAME);
//
//		//-----------------------------------------------
//		// Branch Number column
//		//-----------------------------------------------
//		Column<Finance, String> branchnumber = new Column<Finance, String>( new TextCell()) {
//			@Override
//			public String getValue( Finance finance ) {return Model.decrypt(finance.getBranchnumber());}
//		};
//		financeTable.addStringColumn(branchnumber, CONSTANTS.financetableHeaders()[col++], Finance.BRANCHNUMBER);
//
//		//-----------------------------------------------
//		// Account Number column
//		//-----------------------------------------------
//		Column<Finance, String> accountnumber = new Column<Finance, String>( new TextCell()) {
//			@Override
//			public String getValue( Finance finance ) {return Model.decrypt(finance.getAccountnumber());}
//		};
//		financeTable.addStringColumn(accountnumber, CONSTANTS.financetableHeaders()[col++], Finance.ACCOUNTNUMBER);
//
//		//-----------------------------------------------
//		// IBAN/SWIFT column
//		//-----------------------------------------------
//		Column<Finance, String> ibanswift = new Column<Finance, String>( new TextCell()) {
//			@Override
//			public String getValue( Finance finance ) {return finance.getIbanswift();}
//		};
//		financeTable.addStringColumn(ibanswift, CONSTANTS.financetableHeaders()[col++], Finance.IBANSWIFT);
//
//		//-----------------------------------------------
//		// Currency column
//		//-----------------------------------------------
//		Column<Finance, String> currency = new Column<Finance, String>( new TextCell()) {
//			@Override
//			public String getValue( Finance finance ) {return finance.getCurrency();}
//		};
//		financeTable.addStringColumn(currency, CONSTANTS.financetableHeaders()[col++]);
//
//		panel.add(financeTable);
//		return panel;
//	}
//
	/* 
	 * Creates the panel displayed when there are no financial transactions.
	 * 
	 * @return the empty panel.
	 */
	private Widget eventjournaltableEmpty() {
		FlowPanel panel = new FlowPanel();
		panel.add(new HTML(CONSTANTS.eventjornaltableEmpty()));
		panel.addStyleName(AbstractField.CSS.cbtTableFieldEmpty());
		panel.add(new Image(BUNDLE.eventjornaltableEmpty()));
		return panel;
	}
	/* 
	 * Creates the financial transaction table stack panel.
	 * 
	 * @return the financial transaction table stack panel.
	 */
	/**
	 * 
	 *
	 * @return 
	 */
	private FlowPanel createEventJournal() {

		final FlowPanel panel = new FlowPanel();
		panel.add(createFinancialcommands());
		
		//-----------------------------------------------
		// Financial Transaction selection handler
		//-----------------------------------------------
		final NoSelectionModel<EventJournal> selectionModel = new NoSelectionModel<EventJournal>();
		SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				JournalPopup.getInstance().show(selectionModel.getLastSelectedObject(), null, eventjournalTable);
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// Financial Transaction table
		//-----------------------------------------------
		eventjournalTable = new TableField<EventJournal>(this, null, 
				new EventJournalTable(),
				selectionModel, 
				EVENTJOURNAL_ROWS,
				tab++);

		eventjournalTable.setEmptyValue(eventjournaltableEmpty());
		eventjournalTable.setOrderby(EventJournal.DATE);
		
		eventjournalTable.setTableError(new TableError() {
			@Override
			public boolean error() {return (AbstractRoot.noOrganizationid() || partyField.noValue());}
		});

		eventjournalTable.setTableExecutor(new TableExecutor<EventJournalTable>() {
			public void execute(EventJournalTable action) {
				action.setOrganizationid(AbstractRoot.getOrganizationid());
				action.setEntitytype(NameId.Type.Party.name());
				action.setEntityid(partyField.getValue());
				partyBalance.execute(true);
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
						JournalPopup.getInstance().show(eventaction, null, eventjournalTable);
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
		// State column
		//-----------------------------------------------
		Column<EventJournal, String> state = new Column<EventJournal, String>( new TextCell()) {
			@Override
			public String getValue( EventJournal eventjournal ) {return eventjournal.getState();}
		};
		eventjournalTable.addStringColumn(state, CONSTANTS.eventjournalHeaders()[col++], EventJournal.STATE);

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

		panel.add(eventjournalTable);
		return panel;
	}
	
	/* 
	 * Creates the panel of financial commands.
	 * 
	 * @return the panel of financial commands.
	 */
	/**
	 * 
	 *
	 * @return 
	 */
	private HorizontalPanel createFinancialcommands() {
		
		final HorizontalPanel bar = new HorizontalPanel();
//		bar.addStyleName(CSS.financialcommandBar());

		//-----------------------------------------------
		// Reservation Sale button
		//-----------------------------------------------
//		final Button reservationsaleButton = new Button(CONSTANTS.reservationsaleButton());
//		reservationsaleButton.setTitle(CONSTANTS.reservationsaleHelp());
//		reservationsaleButton.addStyleName(AbstractField.CSS.cbtCommandButton());
//		reservationsaleButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
//		reservationsaleButton.addStyleName(CSS.financialcommandButton());
//		reservationsaleButton.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				if (!blocked()) {JournalPopup.getInstance().show(Event.Type.ReservationSale, getValue(), featureTable.getValue(), null, eventjournalTable);}
//			}
//		});
//		bar.add(reservationsaleButton);
		
		//-----------------------------------------------
		// Sundry Sale button
		//-----------------------------------------------
		final Button saleButton = new Button(CONSTANTS.saleButton());
		saleButton.setTitle(CONSTANTS.saleHelp());
		saleButton.addStyleName(AbstractField.CSS.cbtCommandButton());
		saleButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
//		saleButton.addStyleName(CSS.financialcommandButton());
		saleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				JournalPopup.getInstance().show(Event.Type.Sale, null, eventjournalTable);
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
//		receiptButton.addStyleName(CSS.financialcommandButton());
		receiptButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				JournalPopup.getInstance().show(Event.Type.Receipt, null, eventjournalTable);
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
//		purchaseButton.addStyleName(CSS.financialcommandButton());
		purchaseButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				JournalPopup.getInstance().show(Event.Type.Purchase,  null, eventjournalTable);
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
//		purchasesaleButton.addStyleName(CSS.financialcommandButton());
		purchasesaleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				JournalPopup.getInstance().show(Event.Type.PurchaseSale,  null, eventjournalTable);
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
//		paymentButton.addStyleName(CSS.financialcommandButton());
		paymentButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				JournalPopup.getInstance().show(Event.Type.Payment, null, eventjournalTable);
			}
		});
		bar.add(paymentButton);
		
		final Label balanceLabel =  new Label(CONSTANTS.balanceLabel());
//		balanceLabel.addStyleName(CSS.balanceLabel());
		
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
//		reportLabel.addStyleName(CSS.balanceLabel());
		
		//-----------------------------------------------
		// Party Statement Report button
		//-----------------------------------------------
		final ReportButton partyStatement =  new ReportButton(null, CONSTANTS.partystatementLabel(), tab++) {
			@Override
			public Report getReport() {
				Report report = new Report();;
				report.setOrganizationid(AbstractRoot.getOrganizationid());
				report.setActorid(AbstractRoot.getActorid());
				report.setDesign(Design.PARTY_STATEMENT);
				report.setFromtoname(partyField.getName());
				report.setLanguage(AbstractRoot.getLanguage());
				report.setState(Report.CREATED);
				report.setCurrency(currencyField.getValue());
				return report;
			}
		};
		partyStatement.setLabelStyle(CSS.reportButton());
		partyStatement.setTitle(CONSTANTS.partystatementHelp());
		
		final HorizontalPanel balancePanel = new HorizontalPanel();
		balancePanel.add(balanceLabel);
		balancePanel.add(balanceField);
		
		final HorizontalPanel reportPanel = new HorizontalPanel();
		reportPanel.add(reportLabel);
		reportPanel.add(partyStatement);
		
		final VerticalPanel panel = new VerticalPanel();
		panel.add(balancePanel);
		panel.add(reportPanel);
		
		bar.add(panel);
		return bar;
	}

	/* Gets the target widget for product name messages. */
	private Widget getTargetField() {
		if (lastnameField.isVisible()) {return lastnameField;}
		else {return partyField;}
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Create Party 
		//-----------------------------------------------
		partyCreate = new GuardedRequest<Party>() {
			protected boolean error() {return false;}
			protected void send() {
				onReset(Party.INITIAL);
				creatorField.setValue(AbstractRoot.getCreatorid());
				super.send(getValue(new PartyCreate()));
			}
			protected void receive(Party party) {setValue(party);}
		};

		//-----------------------------------------------
		// Read Party 
		//-----------------------------------------------
		partyRead = new GuardedRequest<Party>() {
			protected boolean error() {return partyField.noValue();}
			protected void send() {super.send(getValue(new PartyRead()));}
			protected void receive(Party response) {setValue(response);}
		};

		//-----------------------------------------------
		// Update Party 
		//-----------------------------------------------
		partyUpdate = new GuardedRequest<Party>() {
			protected boolean error() {
				return (
						ifMessage(noState(), Level.TERSE, AbstractField.CONSTANTS.errState(), getTargetField())
						|| ifMessage(partyField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errId(), getTargetField())
						|| ifMessage(emailaddressField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errEmailaddress(), emailaddressField)
						|| ifMessage(!Party.isEmailAddress(emailaddressField.getValue()), Level.ERROR, AbstractField.CONSTANTS.errEmailaddress(), emailaddressField)
//						|| ifMessage(!Party.isPhoneNumber(dayphoneField.getValue()), Level.ERROR, AbstractField.CONSTANTS.errPhoneNumber(), dayphoneField)
//						|| ifMessage(hadState(Party.INITIAL) && passwordField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errPassword(), passwordField)
						|| ifMessage(typesField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errTypes(), typesField)
				);
			}
			protected void send() {super.send(getValue(new PartyUpdate()));}
			protected void receive(Party response) {setValue(response);}
		};

		//-----------------------------------------------
		// Delete Party 
		//-----------------------------------------------
		partyDelete = new GuardedRequest<Party>() {
			protected String popup() {return AbstractField.CONSTANTS.errDeleteOK();}
			public void cancel() {state = oldstate;}
			protected boolean error() {return ifMessage(partyField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errId(), getTargetField());}
			protected void send() {super.send(getValue(new PartyDelete()));}
			protected void receive(Party response) {setValue(response);}
		};
		
		//-----------------------------------------------
		// Check if a Party with the Email Address exists
		//-----------------------------------------------
		partyExists = new GuardedRequest<Party>() {
			protected boolean error() {return emailaddressField.noValue();}
			protected void send() {super.send(getValue(new PartyExists()));}
			protected void receive(Party party) {
				if (party != null && party.isAgent()) {
					AbstractField.addMessage(Level.TERSE, CONSTANTS.partyexistsasagentError(), emailaddressField);
					emailaddressField.setValue(Model.BLANK);
				}
				else if (party != null && party.isManager()) {
					AbstractField.addMessage(Level.TERSE, CONSTANTS.partyexistsasorganizationError(), emailaddressField);
					emailaddressField.setValue(Model.BLANK);
				}
				else if (party != null) {
					party.setState(Party.CREATED);
					setValue(party);
					AbstractField.addMessage(Level.TERSE, CONSTANTS.partyexistsError(), emailaddressField);
				}
				else {creatorNameIds.execute();}
			}
		};

		//-----------------------------------------------
		// Get Party Financial Balance
		//-----------------------------------------------
		partyBalance = new GuardedRequest<DoubleResponse>() {
			protected boolean error() {return partyField.noValue();}
			protected void send() {super.send(getValue(new EventJournalBalance()));}
			protected void receive(DoubleResponse response) {balanceField.setValue(response == null || response.noValue() ? 0.0 : response.getValue());}
		};

		//-----------------------------------------------
		// Read List of Creator NameIds
		//-----------------------------------------------
		creatorNameIds = new GuardedRequest<Table<NameId>>() {
			protected boolean error() {return emailaddressField.noValue();}
			protected void send() {super.send(getValue(new PartyCreatorList()));}
			protected void receive(Table<NameId> response) {
				if (response.noValue()) {creatorField.setVisible(false);}
				else {
					creatorField.setItems(response.getValue());
					creatorField.setVisible(true);
				}
			}
		};
	}

	/* Gets the total progress towards completion of the form. */
	private int totalProgress() {
		if (partyField.noValue() || hasState(Party.INITIAL)) {return 0;}
		double progress = 0;
		progress += dataProgress() * 0.15;
//		progress += contracttextProgress() * 0.10;
		progress += descriptionProgress() * 0.15;
		progress += emailaddressProgress() * 0.10;
		progress += contactProgress() * 0.10;
		progress += relationProgress() * 0.10;
		progress += defaultProgress() * 0.10;
		progress += financeProgress() * 0.10;
		return (int) progress;
	}

	private double dataProgress() {
		double progress = 0;
		progress += partyField.noValue() ? 0.0 : 40.0;
		progress += extranameField.noValue() ? 0.0 : 10.0;
		progress += dayphoneField.noValue() ? 0.0 : 10.0;
		progress += mobilephoneField.noValue() ? 0.0 : 10.0;
		progress += faxphoneField.noValue() ? 0.0 : 10.0;
		progress += webaddressField.noValue() ? 0.0 : 10.0;
		progress += postaladdressField.noValue() ? 0.0 : 10.0;
		return progress;
	}

//	private double contracttextProgress() {
//		if (contracttextField.noValue() || contracttextField.hasDefaultValue()) {return 0.0;}
//		else {return Math.min(100.0, contracttextField.getValue().length() / 3.0);}
//	}

private double descriptionProgress() {
		if (descriptionField.noValue() || descriptionField.hasDefaultValue()) {return 0.0;}
		else {return Math.min(100.0, descriptionField.getValue().length() / 3.0);}
	}

	private double emailaddressProgress() {
		return emailaddressField.noValue() || !Party.isEmailAddress(emailaddressField.getValue()) ? 0.0 : 100.0;
	}

	private double contactProgress() {
		if (contactTable.noValue()) {return 0.0;}
		else {return Math.min(100.0, contactTable.getValue().size() * 25.0);}
	}

	private double relationProgress() {
		if (relationTable.noValue()) {return 0.0;}
		else {return Math.min(100.0, relationTable.getValue().size() * 25.0);}
	}

	private double defaultProgress() {
		double progress = 0;
		progress += taxnumberField.noValue() ? 0.0 : 20.0;
		progress += postalcodeField.noValue() ? 0.0 : 20.0;
		progress += countryField.noValue() ? 0.0 : 20.0;
		progress += currencyField.noValue() ? 0.0 : 20.0;
		progress += languageField.noValue() ? 0.0 : 20.0;
		return progress;
	}

	private double financeProgress() {
		return financeField.noValue() ? 0.0 : 100.0;
	}

	/* Inner Class ProgressPopup is to display progress detail in a pop up panel. */
	private class ProgressPopup extends PopupPanel {

		/** Instantiates a new progress pop up panel. */
		public ProgressPopup() {
			super(true);
			AbstractField.CSS.ensureInjected();
			CSS.ensureInjected();

			final VerticalPanel form = new VerticalPanel();
			final Label title = new Label(CONSTANTS.progressTitle());
			title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
			form.add(title);

			final Label closeButton = new Label();
			closeButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ProgressPopup.this.hide();
				}
			});
			closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
			form.add(closeButton);

			ProgressField dataprogressField = new ProgressField(PartyForm.this, null, CONSTANTS.dataprogressLabel(), 0, 100, tab);
			dataprogressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			dataprogressField.setValue((int)dataProgress());
			form.add(dataprogressField);

			ProgressField descriptionprogressField = new ProgressField(PartyForm.this, null, CONSTANTS.descriptionprogressLabel(), 0, 100, tab);
			descriptionprogressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			descriptionprogressField.setValue((int)descriptionProgress());
			form.add(descriptionprogressField);

			ProgressField emailaddressprogressField = new ProgressField(PartyForm.this, null, CONSTANTS.emailaddressprogressLabel(), 0, 100, tab);
			emailaddressprogressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			emailaddressprogressField.setValue((int)emailaddressProgress());
			form.add(emailaddressprogressField);

			ProgressField relationprogressField = new ProgressField(PartyForm.this, null, CONSTANTS.relationprogressLabel(), 0, 100, tab);
			relationprogressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			relationprogressField.setValue((int)relationProgress());
			form.add(relationprogressField);

			ProgressField financeprogressField = new ProgressField(PartyForm.this, null, CONSTANTS.financeprogressLabel(), 0, 100, tab);
			financeprogressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			financeprogressField.setValue((int)financeProgress());
			financeprogressField.setVisible(AbstractRoot.hasPermission(AccessControl.ADMINISTRATOR));
			form.add(financeprogressField);

			setWidget(form);
			setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
		}
	}
}

