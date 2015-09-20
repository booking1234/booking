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
import net.cbtltd.client.field.CheckFields;
import net.cbtltd.client.field.CommandButton;
import net.cbtltd.client.field.DoubleunitField;
import net.cbtltd.client.field.GridField;
import net.cbtltd.client.field.ImageField;
import net.cbtltd.client.field.IntegerField;
import net.cbtltd.client.field.IntegerunitField;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.NameIdField;
import net.cbtltd.client.field.PasswordField;
import net.cbtltd.client.field.ProgressField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.field.VshuttleField;
import net.cbtltd.client.field.table.ActionCell;
import net.cbtltd.client.field.table.ActionCell.Delegate;
import net.cbtltd.client.field.table.ActionHeader;
import net.cbtltd.client.field.table.TableError;
import net.cbtltd.client.field.table.TableExecutor;
import net.cbtltd.client.panel.ActorPopup;
import net.cbtltd.client.panel.ContractPopup;
import net.cbtltd.client.panel.FinancePopup;
import net.cbtltd.client.panel.LicensePopup;
import net.cbtltd.client.panel.PartnerPopup;
import net.cbtltd.client.panel.TaxPopup;
import net.cbtltd.client.resource.Hosts;
import net.cbtltd.client.resource.organization.OrganizationBundle;
import net.cbtltd.client.resource.organization.OrganizationConstants;
import net.cbtltd.client.resource.organization.OrganizationCssResource;
import net.cbtltd.shared.Contract;
import net.cbtltd.shared.Finance;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Session;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.Tax;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.Workflow;
import net.cbtltd.shared.api.HasResetId;
import net.cbtltd.shared.api.HasTable;
import net.cbtltd.shared.contract.ContractTable;
import net.cbtltd.shared.contract.DiscountTable;
import net.cbtltd.shared.finance.CurrencyNameId;
import net.cbtltd.shared.finance.FinanceTable;
import net.cbtltd.shared.license.LicenseTable;
import net.cbtltd.shared.location.CountryNameId;
import net.cbtltd.shared.partner.PartnerTable;
import net.cbtltd.shared.party.Agent;
import net.cbtltd.shared.party.AgentCreate;
import net.cbtltd.shared.party.LanguageNameId;
import net.cbtltd.shared.party.Organization;
import net.cbtltd.shared.party.OrganizationActorTable;
import net.cbtltd.shared.party.OrganizationCreate;
import net.cbtltd.shared.party.OrganizationRead;
import net.cbtltd.shared.party.OrganizationUpdate;
import net.cbtltd.shared.party.PartyCreatorList;
import net.cbtltd.shared.party.PartyDelete;
import net.cbtltd.shared.party.PartyExists;
import net.cbtltd.shared.party.PartySessionTable;
import net.cbtltd.shared.tax.TaxTable;

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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
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
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

/**
 * The Class OrganizationForm is to manage information about the property manager or agency which employs 
 * the currently logged in user.
 */
public class OrganizationForm
extends AbstractForm<Organization> {

	private static final OrganizationConstants CONSTANTS = GWT.create(OrganizationConstants.class);
	private static final Hosts HOSTS = GWT.create(Hosts.class);
	private static final OrganizationBundle BUNDLE = OrganizationBundle.INSTANCE;
	private static final OrganizationCssResource CSS = BUNDLE.css();

	private static final int ACTOR_ROWS = 200;
	private static final int FINANCE_ROWS = 200;
	private static final int PARTNER_ROWS = 19;
	private static final int SESSION_ROWS = 24;
	private static final int TAX_ROWS = 19;

	private static GuardedRequest<Organization> agentCreate;
	private static GuardedRequest<Organization> organizationCreate;
	private static GuardedRequest<Organization> organizationRead;
	private static GuardedRequest<Organization> organizationUpdate;
	private static GuardedRequest<Party> partyExists;
	private static GuardedRequest<Organization> partyDelete;
	private static GuardedRequest<Table<NameId>> creatorNameIds;

	//Organization
	private static NameIdField organizationField;
	private static ProgressField progressField;
	private static TextField emailaddressField;
	private static ListField creatorField;
	private static PasswordField passwordField;
	private static PasswordField checkpasswordField;
	private static TextField dayphoneField;
	private static TextField faxphoneField;
	private static TextField mobilephoneField;
	private static TextField nightphoneField;
	private static TextField webaddressField;
	private static TextField extranameField;
	private static TextField affiliateField;
	private static TextField idField;
	private static TextField posField;
	private static TextField postalcodeField;
	private static ListField financeField;
	
	private static StackLayoutPanel stackPanel;
	private static ImageField logoField;
	private static TextAreaField postaladdressField;
	private static TextAreaField descriptionField;
	private static TextAreaField contracttextField;

	//Locale
	private static ListField countryField;
	private static TextField taxnumberField;
	private static TextAreaField urlField;
	private static ListField currencyField;
	private static ListField languageField;
	private static DoubleunitField depositField;
	private static IntegerunitField payfullField;
	private static ListField paytypeField;
	private static VshuttleField currenciesField;
	private static VshuttleField languagesField;
	private static CheckFields optionsField;

	private static TableField<Contract> contractTable;
	private static TableField<Contract> discountTable; // Agent view
	private static TableField<License> licenseTable; // Upstream view
	private static TableField<Party> actorTable;
	private static TableField<Partner> partnerTable;
	private static TableField<Session> sessionTable;
	private static TableField<Finance> financeTable;
	private static TableField<Tax> taxTable;
	private static GridField<Workflow> workflowGrid;

	private static Label registerLink = new Label(AbstractField.CONSTANTS.registerLabel());;
	
	private static Widget createDescription;
	private static Widget createContracttext;
	private static Widget createContracttable;
	private static Widget createActor;
	private static Widget createTax;
	private static Widget createFinance;
	private static Widget createLicense;
	private static Widget createPartner;
	private static Widget createLocale;
	private static Widget createWorkflow;
	private static Widget createDiscounttable;

	private static Party actor;
	
	private static String posValue;
	private static String emailValue;
	private static String registerUrl;

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#permission()
	 */
	@Override
	public short[] permission(){return AccessControl.ORGANIZATION_PERMISSION;}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#getValue()
	 */
	@Override
	public Organization getValue() {return getValue(new Organization());}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {
		if (emailaddressField.sent(change)) {partyExists.execute();}
		else if (checkpasswordField.sent(change)) {ifMessage(!checkpasswordField.getValue().equals(passwordField.getValue()), Level.ERROR, CONSTANTS.checkpasswordError(), checkpasswordField);}
		else if (sessionTable.sent(change)) {sessionTable.setVisible(sessionTable.hasValue());}
		progressField.setValue(totalProgress());
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent click) {
		if (progressField.sent(click)) {new ProgressPopup().center();}
		else {super.onClick(click);} //for fsm
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onFocus()
	 */
	@Override
	public void onFocus() {
		organizationField.setName(AbstractRoot.getOrganizationname());
		organizationField.setId(AbstractRoot.getOrganizationid());
		organizationRead.execute();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onReset(net.cbtltd.shared.api.HasNameId)
	 */
	@Override
	public void onReset(HasResetId resetid) {
		if (resetid instanceof Organization && resetid.getResetid() == null) {organizationCreate.execute();}
		else if (resetid instanceof Agent && resetid.getResetid() == null) {agentCreate.execute();}
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
		stackPanel.setVisible(false);
		stackPanel.addSelectionHandler(new SelectionHandler<Integer>() {
			public void onSelection(SelectionEvent<Integer> event) {refreshStackPanel();}
		});
		stackPanel.addStyleName(CSS.stackStyle());
		content.add(stackPanel);

		createDescription = createDescription();
		createContracttext = createContracttext();
		createContracttable = createContracttable();
		createActor = createActor();
		createTax = createTax();
		createFinance = createFinance();
		createLicense = createLicence();
		createPartner = createPartner();
		createLocale = createDefaults();
		createWorkflow = createWorkflow();
		createDiscounttable = createDiscounttable();
		initRegisterLink();
		onRefresh();
		onReset(Organization.CREATED);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#getValue(net.cbtltd.shared.api.HasState)
	 */
	private Organization getValue(Organization organization) {
		organization.setState(state);
		organization.setOldstate(oldstate);
		organization.setId(organizationField.getId());
		organization.setOrganizationid(AbstractRoot.getOrganizationid());
		organization.setActorid(AbstractRoot.getActorid());
		organization.setEmployerid(organizationField.getId());
		organization.setName(organizationField.getName());
		organization.setEmailaddress(emailaddressField.getValue());
		organization.setPassword(passwordField.getValue());
		organization.setCreatorid(creatorField.getValue());
		organization.setDayphone(dayphoneField.getValue());
		organization.setFaxphone(faxphoneField.getValue());
		organization.setMobilephone(mobilephoneField.getValue());
		organization.setNightphone(nightphoneField.getValue());
		organization.setWebaddress(webaddressField.getValue());
//		organization.setJurisdictionid(jurisdictionField.getValue());
		organization.setTaxnumber(taxnumberField.getValue());
		organization.setExtraname(extranameField.getValue());
		organization.setFinanceid(financeField.getValue());
		organization.setPostaladdress(postaladdressField.getValue());
		organization.setPostalcode(postalcodeField.getValue());
		organization.setPublicText(descriptionField.getText());
		organization.setContractText(contracttextField.getText());
		organization.setCountry(countryField.getValue());
		organization.setCurrency(currencyField.getValue());
		organization.setLanguage(languageField.getValue());
		organization.setOptions(optionsField.getValue());
		organization.setDoubleValue(Party.Value.Deposit.name(), depositField.getValue());
		organization.setValue(Party.Value.DepositType.name(), depositField.getUnitvalue());
		organization.setIntegerValue(Party.Value.Payfull.name(), payfullField.getValue());
		organization.setValue(Party.Value.Payunit.name(), payfullField.getUnitvalue());
		organization.setValue(Party.Value.Paytype.name(), paytypeField.getValue());
		
		//organization.setValue(Party.Value.Interhome.name(), urlField.getValue());
		organization.setUrlText(urlField.getText());
		organization.setCurrencies(currenciesField.getValue());
		organization.setLanguages(languagesField.getValue());
		organization.setWorkflows(workflowGrid.getValue());
		Log.debug("getValue " + organization);
		return organization;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#setValue(net.cbtltd.shared.api.HasState)
	 */
	public void setValue(Organization organization) {
		Log.debug("setValue " + organization);
		if (organization == null) {onReset(Organization.CREATED);}
		else {
			setResetting(true);
			onStateChange(organization.getState());
			organizationField.setId(organization.getId());
			organizationField.setName(organization.getName());
			emailaddressField.setValue(organization.getEmailaddresses());
			passwordField.setValue(Model.BLANK);
			creatorField.setValue(organization.getCreatorid());
			dayphoneField.setValue(organization.getDayphone());
			faxphoneField.setValue(organization.getFaxphone());
			mobilephoneField.setValue(organization.getMobilephone());
			nightphoneField.setValue(organization.getNightphone());
			webaddressField.setValue(organization.getWebaddress());
			idField.setText(organization.getId());
			posField.setText(Party.encrypt(organization.getId()));
			affiliateField.setText(HOSTS.affiliateUrl() + Party.encrypt(organization.getId()));
			extranameField.setValue(organization.getExtraname());
			financeField.setValue(organization.getFinanceid());
			postaladdressField.setValue(organization.getPostaladdress());
			postalcodeField.setValue(organization.getPostalcode());
//			jurisdictionField.setValue(organization.getJurisdictionid());
			taxnumberField.setValue(organization.getTaxnumber());
			logoField.setValue(Text.getLogo(organization.getId()));
			descriptionField.setText(organization.getPublicText());
			contracttextField.setText(organization.getContractText());
			currenciesField.setValue(organization.getCurrencies());
			languagesField.setValue(organization.getLanguages());
			countryField.setValue(organization.getCountry());
			currencyField.setValue(organization.getCurrency());
			languageField.setValue(organization.getLanguage());
			optionsField.setValue(organization.getOptions());
			depositField.setValue(organization.getDoubleValue(Party.Value.Deposit.name()));
			depositField.setUnitvalue(organization.getValue(Party.Value.DepositType.name()));
			payfullField.setValue(organization.getIntegerValue(Party.Value.Payfull.name()));
			payfullField.setUnitvalue(organization.getValue(Party.Value.Payunit.name()));
			paytypeField.setValue(organization.getValue(Party.Value.Paytype.name()));
			urlField.setText(organization.getUrlText());
			workflowGrid.setValue(organization.getWorkflows());

			posValue = Party.encrypt(organization.getId());
			emailValue = organization.getEmailaddresses();
			registerUrl = "http://uat.mybookingpal.com/registration/index.html?pos=" + posValue + "&email="
					+ emailValue;
			
			refreshStackPanel();

			stackPanel.clear();
			if (AbstractRoot.hasPermission(AccessControl.AGENCY)) {
				stackPanel.add(createDescription, CONSTANTS.descriptionLabel(), 1.5);
				stackPanel.add(createContracttext, CONSTANTS.contracttextLabel(), 1.5);
				stackPanel.add(createActor, CONSTANTS.actorLabel(), 1.5);
				stackPanel.add(createDiscounttable, CONSTANTS.contractLabel(), 1.5);			
			}
			else if (AbstractRoot.hasPermission(AccessControl.ADMINISTRATOR)) {
				stackPanel.add(createDescription, CONSTANTS.descriptionLabel(), 1.5);
				stackPanel.add(createContracttext, CONSTANTS.contracttextLabel(), 1.5);
				stackPanel.add(createContracttable, CONSTANTS.contractLabel(), 1.5);
				stackPanel.add(createActor, CONSTANTS.actorLabel(), 1.5);
				stackPanel.add(createTax, CONSTANTS.taxLabel(), 1.5);
				stackPanel.add(createFinance, CONSTANTS.financesLabel(), 1.5);
				stackPanel.add(createLocale, CONSTANTS.localeLabel(), 1.5);
				stackPanel.add(createWorkflow, CONSTANTS.workflowLabel(), 1.5);
				if (AbstractRoot.hasPermission(AccessControl.LICENSE_ROLES)) {stackPanel.add(createLicense, CONSTANTS.licenseLabel(), 1.5);}
				if (AbstractRoot.hasPermission(AccessControl.PARTNER_ROLES)) {stackPanel.add(createPartner, CONSTANTS.partnerLabel(), 1.5);}
			}

			passwordField.setVisible(hasState(Organization.INITIAL));
			checkpasswordField.setVisible(hasState(Organization.INITIAL));
			idField.setVisible(!hasState(Organization.INITIAL));
			posField.setVisible(!hasState(Organization.INITIAL));
			affiliateField.setVisible(!hasState(Organization.INITIAL));
			stackPanel.setVisible(!hasState(Organization.INITIAL));

			partnerTable.execute();

			setResetting(false);
		}
	}
	
	/* Refreshes the open stack panel */
	private void refreshStackPanel() {
		if (AbstractRoot.hasPermission(AccessControl.AGENCY)) {
			switch (stackPanel.getVisibleIndex()) {
			case 2: contractTable.execute(); break;
			case 3: actorTable.execute(); break;
			case 4: discountTable.execute(); break;
			}
		}
		else {
			switch (stackPanel.getVisibleIndex()) {
			case 2: contractTable.execute(); break;
			case 3: actorTable.execute(); break;
			case 4: taxTable.execute(); break;
			case 5: financeTable.execute(); break;
			case 8: licenseTable.execute(); break;
			}
		}
	}

	/*
	 * Gets the specified party action with its attribute values set.
	 *
	 * @param party the specified party action.
	 * @return the party action with its attribute values set.
	 */
	private Party getValue(Party party) {
		party.setEmailaddress(emailaddressField.getValue());
		return party;
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#setValue(net.cbtltd.shared.api.HasState)
	 */
	public void setValue(Party party) {
		Log.debug("setValue " + party);
		if (party == null) {onReset(Organization.CREATED);}
		else {
			setResetting(true);
			onStateChange(party.getState());
			organizationField.setEnabled(true);
			organizationField.setId(party.getId());
			organizationField.setName(party.getName());
			emailaddressField.setValue(party.getEmailaddresses());
			passwordField.setValue(Model.BLANK);
			creatorField.setValue(party.getCreatorid());
			dayphoneField.setValue(party.getDayphone());
			faxphoneField.setValue(party.getFaxphone());
			mobilephoneField.setValue(party.getMobilephone());
			nightphoneField.setValue(party.getNightphone());
			webaddressField.setValue(party.getWebaddress());
			idField.setValue(party.getId());
			posField.setText(Party.encrypt(party.getId()));
			affiliateField.setText(HOSTS.affiliateUrl() + Party.encrypt(party.getId()));
			extranameField.setValue(party.getExtraname());
			financeField.setValue(party.getFinanceid());
			postaladdressField.setValue(party.getPostaladdress());
			postalcodeField.setValue(party.getPostalcode());
//			jurisdictionField.setValue(party.getJurisdictionid());
			taxnumberField.setValue(party.getTaxnumber());
			logoField.setValue(Text.getLogo(party.getId()));
			descriptionField.setText(party.getPublicText());
			contracttextField.setText(party.getContractText());
			countryField.setValue(party.getCountry());
			currencyField.setValue(party.getCurrency());
			languageField.setValue(party.getLanguage());
			optionsField.setValue(party.getOptions());
			depositField.setValue(party.getDoubleValue(Party.Value.Deposit.name()));
			depositField.setUnitvalue(party.getValue(Party.Value.DepositType.name()));
			payfullField.setValue(party.getIntegerValue(Party.Value.Payfull.name()));
			payfullField.setUnitvalue(party.getValue(Party.Value.Payunit.name()));
			paytypeField.setValue(party.getValue(Party.Value.Paytype.name()));
			urlField.setText(party.getUrlText());

			posValue = Party.encrypt(party.getId());
			emailValue = party.getEmailaddresses();
			registerUrl = "http://demo.razor-cloud.com/registration/index.html?pos=" + posValue + "&email="
					+ emailValue;
			
			setResetting(false);
		}
	}
	
	/* 
	 * Creates the primary panel.
	 * 
	 * @return the primary panel.
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
		// Organization field
		//-----------------------------------------------
		organizationField = new NameIdField(this, null,
				CONSTANTS.organizationnameLabel(),
				tab++);
		organizationField.setHelpText(CONSTANTS.organizationnameHelp());
		form.add(organizationField);

		//-----------------------------------------------
		// Organization progress bar
		//-----------------------------------------------
		progressField = new ProgressField(this, null, CONSTANTS.progressLabel(), 0, 100, tab);
		progressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
		progressField.setHelpText(CONSTANTS.progressHelp());
		form.add(progressField);

		//-----------------------------------------------
		// Email address to identify organization
		//-----------------------------------------------
		emailaddressField = new TextField(this, null,
				CONSTANTS.emailaddressLabel(),
				tab++);
		emailaddressField.setMaxLength(100);
		emailaddressField.setHelpText(CONSTANTS.emailaddressHelp());
		form.add(emailaddressField);

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
		// ID field
		//-----------------------------------------------
		idField = new TextField(this, null,
				CONSTANTS.idLabel(),
				tab++);
		idField.setEnabled(false);
		idField.setVisible(false);
		idField.setFieldHalf();
		idField.setHelpText(CONSTANTS.idHelp());

		//-----------------------------------------------
		// POS Code field
		//-----------------------------------------------
		posField = new TextField(this, null,
				CONSTANTS.posLabel(),
				tab++);
//		posField.setEnabled(false);
		posField.setVisible(false);
		posField.setFieldHalf();
		posField.setHelpText(CONSTANTS.posHelp());

		HorizontalPanel ip = new HorizontalPanel();
		form.add(ip);
		ip.add(idField);
		ip.add(posField);

		//-----------------------------------------------
		// Web Address field
		//-----------------------------------------------
		webaddressField = new TextField(this,  null,
				CONSTANTS.webaddressLabel(),
				tab++);
		webaddressField.setMaxLength(100);
		webaddressField.setHelpText(CONSTANTS.webaddressHelp());
		form.add(webaddressField);

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
		// Affiliate URL field
		//-----------------------------------------------
		affiliateField = new TextField(this, null,
				CONSTANTS.affiliateLabel(),
				tab++);
		affiliateField.setVisible(false);
		affiliateField.setHelpText(CONSTANTS.affiliateHelp());
		form.add(affiliateField);

		//-----------------------------------------------
		// Finance field
		//-----------------------------------------------
		financeField = new ListField(this, null,
				new NameIdAction(Service.FINANCE),
				CONSTANTS.financeLabel(),
				false,
				tab++);
		financeField.setHelpText(CONSTANTS.financeHelp());
		form.add(financeField);
		
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
		form.add(postalcodeField);

		form.add(createCommands());
		
		form.add(registerLink);

		FlowPanel shadow = new FlowPanel();
		shadow.addStyleName(AbstractField.CSS.cbtAbstractShadow());
		panel.add(shadow);
		
		return panel;
	}
	
	private void initRegisterLink() {
		registerLink.setTitle(CONSTANTS.registerLabel());
		registerLink.addStyleName(AbstractField.CSS.cbtCommandHyperlink());
		registerLink.setVisible(true);
		registerLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.open(registerUrl, "_blank", "menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=yes");
			}
		});
	}
	
	/* 
	 * Creates the command bar.
	 * 
	 * @return the command bar.
	 */
	private HorizontalPanel createCommands() {

		final HorizontalPanel bar = new HorizontalPanel();
		bar.addStyleName(AbstractField.CSS.cbtAbstractCommand());

		final LocalRequest resetRequest = new LocalRequest() {protected void perform() {onFocus();}}; //onReset(Organization.CREATED);}};

		//-----------------------------------------------
		// Save button
		//-----------------------------------------------
		final CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), organizationUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		saveButton.setTitle(CONSTANTS.saveHelp());
		bar.add(saveButton);

		//-----------------------------------------------
		// Delete button
		//-----------------------------------------------
		final CommandButton deleteButton = new CommandButton(this, CONSTANTS.deleteButton(), partyDelete, tab++);
		deleteButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		deleteButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		deleteButton.setTitle(CONSTANTS.deleteHelp());
		bar.add(deleteButton);

		//-----------------------------------------------
		// Cancel button
		//-----------------------------------------------
		final CommandButton cancelButton = new CommandButton(this, AbstractField.CONSTANTS.allCancel(), resetRequest, tab++);
		cancelButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		cancelButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		cancelButton.setTitle(CONSTANTS.cancelHelp());
		bar.add(cancelButton);

		//-----------------------------------------------
		// The array of transitions to define the finite state machine.
		//-----------------------------------------------
		fsm = new ArrayList<Transition>();
		fsm.add(new Transition(Organization.INITIAL, saveButton, Organization.CREATED));
		fsm.add(new Transition(Organization.INITIAL, cancelButton, Organization.CREATED));
		//		fsm.add(new Transition(Organization.CREATED, createButton, Organization.INITIAL));
		//		fsm.add(new Transition(Organization.CREATED, copyButton, Organization.INITIAL));
		fsm.add(new Transition(Organization.CREATED, saveButton, Organization.CREATED));
		fsm.add(new Transition(Organization.CREATED, deleteButton, Organization.CREATED));

		return bar;
	}

	/* 
	 * Creates the description and logo upload stack panel.
	 * 
	 * @return the description and logo upload stack panel.
	 */
	private VerticalPanel createDescription() {
		final VerticalPanel panel = new VerticalPanel();

		//-----------------------------------------------
		// Description field
		//-----------------------------------------------
		descriptionField = new TextAreaField(this, null,
				CONSTANTS.descriptiontextLabel(),
				new LanguageNameId(),
				tab++);
		descriptionField.setFieldStyle(CSS.descriptionField());
		descriptionField.setDefaultValue(CONSTANTS.descriptionEmpty());
		descriptionField.setMaxLength(Text.MAX_TEXT_SIZE);
		panel.add(descriptionField);

		//-----------------------------------------------
		// Logo field
		//-----------------------------------------------
		logoField = new ImageField(this, null,
				null, //CONSTANTS.reportheaderLabel(),
				tab++);
		logoField.setFieldStyle(CSS.imageField());
		logoField.setUploadValue(Text.getLogo(AbstractRoot.getOrganizationid()));
		logoField.setTitle(CONSTANTS.imageLabel());
		panel.add(logoField);

		return panel;
	}

	/* 
	 * Creates the panel displayed when there is no contract table.
	 * 
	 * @return the empty panel.
	 */
	private TextAreaField createContracttext() {
		contracttextField = new TextAreaField(this, null,
				CONSTANTS.contracttextLabel(),
				new LanguageNameId(),
				tab++);
		contracttextField.setFieldStyle(CSS.contracttextField());
		contracttextField.setDefaultValue(CONSTANTS.contracttextEmpty());
		contracttextField.setMaxLength(Text.MAX_TEXT_SIZE);
		return contracttextField;
	}

	/* 
	 * Creates the contract list stack panel.
	 * 
	 * @return the contract list stack panel.
	 */
	private Widget contracttableEmpty() {
		FlowPanel panel = new FlowPanel();

		Button emptyButton = new Button(AbstractField.CONSTANTS.allCreate());
		emptyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ContractPopup.getInstance().show(Contract.Type.Reservation, contractTable);
			}
		});
		panel.add(emptyButton);

		panel.add(new HTML(CONSTANTS.contracttableEmpty()));
		//panel.addStyleName(CSS.contractEmpty());

		panel.add(new Image(BUNDLE.contracttableEmpty()));
		return panel;
	}

	/* 
	 * Creates the contract list stack panel.
	 * 
	 * @return the contract list stack panel.
	 */
	private ScrollPanel createContracttable() {
		final ScrollPanel panel = new ScrollPanel();
		panel.addStyleName(CSS.actorStyle());

		contractTable = new TableField<Contract>(this, null, 
				new ContractTable(),
				200, 
				tab++);

		contractTable.setEmptyValue(contracttableEmpty());
		contractTable.setOrderby(Contract.PARTY);

		contractTable.setTableError(new TableError() {
			@Override
			public boolean error() {return organizationField.noId();}
		});

		contractTable.setTableExecutor(new TableExecutor<ContractTable>() {
			@Override
			public void execute(ContractTable action) {action.setId(organizationField.getId());}
		});

		int col = 0;

		//-----------------------------------------------
		// Change / Create buttons
		//-----------------------------------------------
		Column<Contract, Contract> changeButton = new Column<Contract, Contract>( 
				new ActionCell<Contract>(AbstractField.CONSTANTS.allChange(), AbstractField.CSS.cbtTableFieldChangeButton(),
						new Delegate<Contract>(){
					public void execute(Contract contract ) {
						ContractPopup.getInstance().show(contract, contractTable);
					}
				}
				)
		)
		{
			public Contract getValue(Contract contract){return contract;}
		};

		ActionHeader<Contract> createButton = new ActionHeader<Contract>(
				new ActionCell<Contract>(
						AbstractField.CONSTANTS.allCreate(), 
						AbstractField.CSS.cbtTableFieldCreateButton(),
						new Delegate<Contract>(){
							public void execute(Contract contract ) {
								ContractPopup.getInstance().show(Contract.Type.Reservation, contractTable);
							}
						}
				)
		)
		{
			public Contract getValue(Contract contract){
				return contract;
			}
		};

		contractTable.addColumn(changeButton, createButton);

		//-----------------------------------------------
		// Party column
		//-----------------------------------------------
		Column<Contract, String> partyname = new Column<Contract, String>(new TextCell()) {
			@Override
			public String getValue( Contract contract ) {return contract.getPartyname();}
		};
		contractTable.addStringColumn(partyname, CONSTANTS.contracttableHeaders()[col++], Contract.PARTY);

		//-----------------------------------------------
		// Date column
		//-----------------------------------------------
		Column<Contract, Date> date = new Column<Contract, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( Contract contract ) {return Time.getDateClient(contract.getDate());}
		};
		contractTable.addDateColumn(date, CONSTANTS.contracttableHeaders()[col++], Contract.DATE);

		//-----------------------------------------------
		// State column
		//-----------------------------------------------
		Column<Contract, String> state = new Column<Contract, String>( new TextCell()) {
			@Override
			public String getValue( Contract contract ) {return contract.getState();}
		};
		contractTable.addStringColumn(state, CONSTANTS.contracttableHeaders()[col++], Contract.STATE);

		//-----------------------------------------------
		// Discount column
		//-----------------------------------------------
		Column<Contract, Number> discount = new Column<Contract, Number>(new NumberCell(AbstractField.QF)) {
			@Override
			public Integer getValue( Contract contract ) {return contract.getDiscount();}
		};
		contractTable.addNumberColumn( discount, CONSTANTS.contracttableHeaders()[col++], Contract.DISCOUNT);

		//-----------------------------------------------
		// Notes column
		//-----------------------------------------------
		Column<Contract, String> notes = new Column<Contract, String>(new TextCell()) {
			@Override
			public String getValue( Contract contract ) {return contract.getNotes(100);}
		};
		contractTable.addStringColumn(notes, CONSTANTS.contracttableHeaders()[col++], Contract.PARTY);

		panel.add(contractTable);
		return panel;
	}

	/* 
	 * Creates the panel displayed when there is no discount table.
	 * 
	 * @return the empty panel.
	 */
	private Widget getDiscountEmpty() {
		FlowPanel panel = new FlowPanel();

		panel.add(new HTML(CONSTANTS.discountEmpty()));
		panel.addStyleName(CSS.contractEmpty());

		panel.add(new Image(BUNDLE.contracttableEmpty()));
		return panel;
	}

	/* 
	 * Creates the discount table stack panel.
	 * 
	 * @return the discount table stack panel.
	 */
	private ScrollPanel createDiscounttable() {
		final ScrollPanel panel = new ScrollPanel();
		panel.addStyleName(CSS.actorStyle());

		discountTable = new TableField<Contract>(this, null, 
				new DiscountTable(),
				200, 
				tab++);

		discountTable.setEmptyValue(getDiscountEmpty());
		discountTable.setOrderby(Contract.PARTY);

		discountTable.setTableError(new TableError() {
			@Override
			public boolean error() {return organizationField.noId();}
		});

		discountTable.setTableExecutor(new TableExecutor<DiscountTable>() {
			@Override
			public void execute(DiscountTable action) {action.setId(organizationField.getId());}
		});

		int col = 0;

		//-----------------------------------------------
		// Party column
		//-----------------------------------------------
		Column<Contract, String> partyname = new Column<Contract, String>(new TextCell()) {
			@Override
			public String getValue( Contract contract ) {return contract.getPartyname();}
		};
		discountTable.addStringColumn(partyname, CONSTANTS.contracttableHeaders()[col++], Contract.PARTY);

		//-----------------------------------------------
		// Date column
		//-----------------------------------------------
		Column<Contract, Date> date = new Column<Contract, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( Contract contract ) {return Time.getDateClient(contract.getDate());}
		};
		discountTable.addDateColumn(date, CONSTANTS.contracttableHeaders()[col++], Contract.DATE);

		//-----------------------------------------------
		// State column
		//-----------------------------------------------
		Column<Contract, String> state = new Column<Contract, String>( new TextCell()) {
			@Override
			public String getValue( Contract contract ) {return contract.getState();}
		};
		discountTable.addStringColumn(state, CONSTANTS.contracttableHeaders()[col++], Contract.STATE);

		//-----------------------------------------------
		// Discount column
		//-----------------------------------------------
		Column<Contract, Number> discount = new Column<Contract, Number>(new NumberCell(AbstractField.QF)) {
			@Override
			public Integer getValue( Contract contract ) {return contract.getDiscount();}
		};
		discountTable.addNumberColumn( discount, CONSTANTS.contracttableHeaders()[col++], Contract.DISCOUNT);

		panel.add(discountTable);
		return panel;
	}

	/* 
	 * Creates the panel displayed when there are no users.
	 * 
	 * @return the panel displayed when there are no users.
	 */
	private Widget actortableEmpty() {
		FlowPanel panel = new FlowPanel();

		Button emptyButton = new Button(AbstractField.CONSTANTS.allCreate());
		emptyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ActorPopup.getInstance().show(actorTable);
			}
		});
		panel.add(emptyButton);

		panel.add(new HTML(CONSTANTS.actortableEmpty()));
		//panel.addStyleName(CSS.actorEmpty());

		panel.add(new Image(BUNDLE.actortableEmpty()));
		return panel;
	}

	/* 
	 * Creates the user (actor) table stack panel.
	 * 
	 * @return the user table stack panel.
	 */
	private ScrollPanel createActor() {
		ScrollPanel scroll = new ScrollPanel();
		HorizontalPanel panel = new HorizontalPanel();
		scroll.add(panel);
		
		//-----------------------------------------------
		// Party Selection model
		//-----------------------------------------------
		final NoSelectionModel<Party> selectionModel = new NoSelectionModel<Party>();
		SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				actor = selectionModel.getLastSelectedObject();
				sessionTable.execute();
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// Party table
		//-----------------------------------------------
		actorTable = new TableField<Party>(this, null, 
				new OrganizationActorTable(),
				selectionModel,
				ACTOR_ROWS, 
				tab++);

		actorTable.setEmptyValue(actortableEmpty());
		actorTable.addStyleName(CSS.actorStyle());

		actorTable.setTableError(new TableError() {
			@Override
			public boolean error() {return organizationField.noId();}
		});

		actorTable.setTableExecutor(new TableExecutor<OrganizationActorTable>() {
			public void execute(OrganizationActorTable action) {action.setId(organizationField.getId());}
		});

		int col = 0;

		//-----------------------------------------------
		// Change button
		//-----------------------------------------------
		Column<Party, Party> changeButton = new Column<Party, Party>( 
				new ActionCell<Party>(AbstractField.CONSTANTS.allChange(), AbstractField.CSS.cbtTableFieldChangeButton(),
						new Delegate<Party>(){
					public void execute( Party actor ){
						ActorPopup.getInstance().show(actor, actorTable);
					}
				}
				)
		)
		{
			public Party getValue(Party actor){return actor;}
		};

		//-----------------------------------------------
		// Create button
		//-----------------------------------------------
		ActionHeader<Party> createButton = new ActionHeader<Party>(
				new ActionCell<Party>(
						AbstractField.CONSTANTS.allCreate(), 
						AbstractField.CSS.cbtTableFieldCreateButton(),
						new Delegate<Party>(){
							public void execute(Party actor ) {
								ActorPopup.getInstance().show(actorTable);
							}
						}
				)
		)
		{
			public Party getValue(Party actor) {
				return actor;
			}
		};

		actorTable.addColumn(changeButton, createButton);

		//-----------------------------------------------
		// Name column
		//-----------------------------------------------
		Column<Party, String> name = new Column<Party, String>(new TextCell()) {
			@Override
			public String getValue( Party actor ) {return actor.getName(20);}
		};
		actorTable.addStringColumn(name, CONSTANTS.actortableHeaders()[col++], Party.NAME);

		//-----------------------------------------------
		// Email Address column
		//-----------------------------------------------
		Column<Party, String> emailaddress = new Column<Party, String>(new TextCell()) {
			@Override
			public String getValue( Party actor ) {return actor.getEmailaddress();}
		};
		actorTable.addStringColumn(emailaddress, CONSTANTS.actortableHeaders()[col++], Party.EMAILADDRESS);

		//-----------------------------------------------
		// Phone column
		//-----------------------------------------------
		Column<Party, String> dayphone = new Column<Party, String>(new TextCell()) {
			@Override
			public String getValue( Party actor ) {return actor.getDayphone();}
		};
		actorTable.addStringColumn(dayphone, CONSTANTS.actortableHeaders()[col++], Party.DAYPHONE);

		//-----------------------------------------------
		// Mobile Phone column
		//-----------------------------------------------
		Column<Party, String> mobilephone = new Column<Party, String>(new TextCell()) {
			@Override
			public String getValue( Party actor ) {return actor.getMobilephone();}
		};
		actorTable.addStringColumn(mobilephone, CONSTANTS.actortableHeaders()[col++], Party.MOBILEPHONE);
		panel.add(actorTable);

		//-----------------------------------------------
		// Session table
		//-----------------------------------------------
		sessionTable = new TableField<Session>(this, null, 
				new PartySessionTable(),
				SESSION_ROWS, 
				tab++);

		sessionTable.setOrderby(Session.ID + HasTable.ORDER_BY_DESC);
		sessionTable.addStyleName(CSS.sessionStyle());
		sessionTable.setVisible(false);
		sessionTable.setTableError(new TableError() {
			@Override
			public boolean error() {
				return (
					actor == null 
					|| actor.noId()
				);
			}
		});

		sessionTable.setTableExecutor(new TableExecutor<PartySessionTable>() {
			@Override
			public void execute(PartySessionTable action) {action.setId(actor.getId());}
		});

		col = 0;

		//-----------------------------------------------
		// ID column
		//-----------------------------------------------
		Column<Session, String> id = new Column<Session, String>(new TextCell()) {
			@Override
			public String getValue( Session session ) {return session.getId();}
		};
		sessionTable.addStringColumn(id, CONSTANTS.sessiontableHeaders()[col++], Session.ID);

		//-----------------------------------------------
		// Login column
		//-----------------------------------------------
		Column<Session, Date> login = new Column<Session, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( Session session ) {return Time.getDateClient(session.getLogin());}
		};
		sessionTable.addDateColumn(login, CONSTANTS.sessiontableHeaders()[col++], Session.LOGIN);

		//-----------------------------------------------
		// Logout column
		//-----------------------------------------------
		Column<Session, Date> logout = new Column<Session, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( Session session ) {return Time.getDateClient(session.getLogout());}
		};
		sessionTable.addDateColumn(logout, CONSTANTS.sessiontableHeaders()[col++], Session.LOGOUT);

		panel.add(sessionTable);

		return scroll;
	}

	/* 
	 * Creates the panel displayed when there is no price list.
	 * 
	 * @return the empty panel.
	 */
	private Widget taxtableEmpty() {
		FlowPanel panel = new FlowPanel();

		final Button emptyButton = new Button(AbstractField.CONSTANTS.allCreate());
		emptyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (organizationField.noValue()) {AbstractField.addMessage(Level.TERSE, CONSTANTS.organizationError(), organizationField);}
				else {TaxPopup.getInstance().show(NameId.Type.Party.name(), AbstractRoot.getOrganizationid(), taxTable);}
			}
		});
		panel.add(emptyButton);

		panel.add(new HTML(CONSTANTS.taxtableEmpty()));
		panel.add(new Image(BUNDLE.taxtableEmpty()));
		return panel;
	}

	/* 
	 * Creates the tax table stack panel.
	 * 
	 * @return the tax table stack panel.
	 */
	private ScrollPanel createTax() {
		final ScrollPanel panel = new ScrollPanel();
		//-----------------------------------------------
		// Selection model
		//-----------------------------------------------
		final NoSelectionModel<Tax> selectionModel = new NoSelectionModel<Tax>();
		SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				TaxPopup.getInstance().show(NameId.Type.Party.name(), AbstractRoot.getOrganizationid(), selectionModel.getLastSelectedObject(), taxTable);
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// Tax table
		//-----------------------------------------------
		taxTable = new TableField<Tax>(this, null,
				new TaxTable(),
				TAX_ROWS, 
				tab++);

		taxTable.setEmptyValue(taxtableEmpty());
		taxTable.setOrderby(Tax.DATE);

		taxTable.setTableError(new TableError() {
			@Override
			public boolean error() {return (
				AbstractRoot.noOrganizationid()
			);}
		});

		taxTable.setTableExecutor(new TableExecutor<TaxTable>() {
			public void execute(TaxTable action) {
//				action.setPartyid(AbstractRoot.getOrganizationid());
				action.setEntitytype(NameId.Type.Party.name());
				action.setEntityid(AbstractRoot.getOrganizationid());
			}
		});

		int col = 0;

		//-----------------------------------------------
		// Change button
		//-----------------------------------------------
		final Column<Tax, Tax> changeButton = new Column<Tax, Tax> ( 
				new ActionCell<Tax>(AbstractField.CONSTANTS.allChange(), AbstractField.CSS.cbtTableFieldChangeButton(),
						new Delegate<Tax>(){
					public void execute(Tax tax){
						TaxPopup.getInstance().show(NameId.Type.Party.name(), AbstractRoot.getOrganizationid(), tax, taxTable);
					}
				}
				)
		)
		{
			public Tax getValue(Tax price){return price;}
		};

		//-----------------------------------------------
		// Create button
		//-----------------------------------------------
		final ActionHeader<Tax> createButton = new ActionHeader<Tax>(
				new ActionCell<Tax>(
						AbstractField.CONSTANTS.allCreate(),
						AbstractField.CSS.cbtTableFieldCreateButton(),
						new Delegate<Tax>(){
							public void execute(Tax price ) {
								if (AbstractRoot.noOrganizationid()) {AbstractField.addMessage(Level.TERSE, CONSTANTS.organizationError(), organizationField);}
								else {TaxPopup.getInstance().show(NameId.Type.Party.name(), AbstractRoot.getOrganizationid(), taxTable);}
							}
						}
				)
		)
		{
			public Tax getValue(Tax price){
				return price;
			}
		};

		taxTable.addColumn(changeButton, createButton);

		//-----------------------------------------------
		// Jurisdiction column
		//-----------------------------------------------
		Column<Tax, String> party = new Column<Tax, String>(new TextCell()) {
			@Override
			public String getValue( Tax tax ) {return tax.getPartyname();}
		};
		taxTable.addStringColumn(party, CONSTANTS.taxtableHeaders()[col++]);

		//-----------------------------------------------
		// Name column
		//-----------------------------------------------
		Column<Tax, String> name = new Column<Tax, String>(new TextCell()) {
			@Override
			public String getValue( Tax tax ) {return tax.getName();}
		};
		taxTable.addStringColumn(name, CONSTANTS.taxtableHeaders()[col++]);

		//-----------------------------------------------
		// Type column
		//-----------------------------------------------
		Column<Tax, String> type = new Column<Tax, String>(new TextCell()) {
			@Override
			public String getValue( Tax tax ) {return tax.getType();}
		};
		taxTable.addStringColumn(type, CONSTANTS.taxtableHeaders()[col++]);

		//-----------------------------------------------
		// From Date column
		//-----------------------------------------------
		Column<Tax, Date> date = new Column<Tax, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( Tax tax ) {return Time.getDateClient(tax.getDate());}
		};
		taxTable.addDateColumn(date, CONSTANTS.taxtableHeaders()[col++], Tax.DATE);

		//-----------------------------------------------
		// Amount column
		//-----------------------------------------------
		Column<Tax, Number> amount = new Column<Tax, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( Tax tax ) {return tax.getAmount();}
		};
		taxTable.addNumberColumn( amount, CONSTANTS.taxtableHeaders()[col++], Tax.AMOUNT);

		//-----------------------------------------------
		// Threshold column
		//-----------------------------------------------
//		Column<Tax, Number> threshold = new Column<Tax, Number>(new NumberCell(AbstractField.AF)) {
//			@Override
//			public Integer getValue( Tax tax ) {return tax.getThreshold();}
//		};
//		taxTable.addNumberColumn( threshold, CONSTANTS.taxtableHeaders()[col++], Tax.THRESHOLD);

		//-----------------------------------------------
		// Notes column
		//-----------------------------------------------
		Column<Tax, String> notes = new Column<Tax, String>(new TextCell()) {
			@Override
			public String getValue( Tax tax ) {return tax.getNotes();}
		};
		taxTable.addStringColumn(notes, CONSTANTS.taxtableHeaders()[col++]);

		panel.add(taxTable);
		return panel;
	}

	/* 
	 * Creates the panel displayed when there are no cash or bank accounts.
	 * 
	 * @return the panel displayed when there are no cash or bank accounts.
	 */
	private Widget financetableEmpty() {
		FlowPanel panel = new FlowPanel();

		Button emptyButton = new Button(AbstractField.CONSTANTS.allCreate());
		emptyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				FinancePopup.getInstance().show(Finance.Type.Bank, currenciesField.getNameIds(), financeTable);
			}
		});
		panel.add(emptyButton);

		panel.add(new HTML(CONSTANTS.financetableEmpty()));
		//panel.addStyleName(CSS.financeEmpty());

		panel.add(new Image(BUNDLE.financetableEmpty()));
		return panel;
	}

	/* 
	 * Creates the cash and bank account table stack panel.
	 * 
	 * @return the cash and bank account table stack panel.
	 */
	private ScrollPanel createFinance() {
		final ScrollPanel panel = new ScrollPanel();

		financeTable = new TableField<Finance>(this, null, 
				new FinanceTable(),
				FINANCE_ROWS, 
				tab++);

		financeTable.setEmptyValue(financetableEmpty());
		financeTable.setOrderby(Finance.NAME);
		financeTable.addStyleName(CSS.actorStyle());

		financeTable.setTableError(new TableError() {
			@Override
			public boolean error() {return organizationField.noId();}
		});

		financeTable.setTableExecutor(new TableExecutor<FinanceTable>() {
			@Override
			public void execute(FinanceTable action) {action.setId(organizationField.getId());}
		});

		int col = 0;

		//-----------------------------------------------
		// Change / Create buttons
		//-----------------------------------------------
		Column<Finance, Finance> changeButton = new Column<Finance, Finance>( 
				new ActionCell<Finance>(AbstractField.CONSTANTS.allChange(), AbstractField.CSS.cbtTableFieldChangeButton(),
						new Delegate<Finance>(){
					public void execute( Finance finance ){
						FinancePopup.getInstance().show(finance, currenciesField.getNameIds(), financeTable);
					}
				}
				)
		)
		{
			public Finance getValue(Finance finance){return finance;}
		};

		ActionHeader<Finance> createButton = new ActionHeader<Finance>(
				new ActionCell<Finance>(
						AbstractField.CONSTANTS.allCreate(), 
						AbstractField.CSS.cbtTableFieldCreateButton(),
						new Delegate<Finance>(){
							public void execute(Finance finance ) {
								FinancePopup.getInstance().show(Finance.Type.Bank, currenciesField.getNameIds(), financeTable);
							}
						}
				)
		)
		{
			public Finance getValue(Finance finance){
				return finance;
			}
		};

		financeTable.addColumn(changeButton, createButton);

		//-----------------------------------------------
		// Account Name column
		//-----------------------------------------------
		Column<Finance, String> name = new Column<Finance, String>(new TextCell()) {
			@Override
			public String getValue( Finance finance ) {return finance.getName();}
		};
		financeTable.addStringColumn(name, CONSTANTS.financetableHeaders()[col++], Finance.NAME);

		//-----------------------------------------------
		// Bank Name column
		//-----------------------------------------------
		Column<Finance, String> bankname = new Column<Finance, String>( new TextCell()) {
			@Override
			public String getValue( Finance finance ) {return Model.decrypt(finance.getBankname());}
		};
		financeTable.addStringColumn(bankname, CONSTANTS.financetableHeaders()[col++], Finance.BANKNAME);

		//-----------------------------------------------
		// Branch Number column
		//-----------------------------------------------
		Column<Finance, String> branchnumber = new Column<Finance, String>( new TextCell()) {
			@Override
			public String getValue( Finance finance ) {return Model.decrypt(finance.getBranchnumber());}
		};
		financeTable.addStringColumn(branchnumber, CONSTANTS.financetableHeaders()[col++], Finance.BRANCHNUMBER);

		//-----------------------------------------------
		// Account Number column
		//-----------------------------------------------
		Column<Finance, String> accountnumber = new Column<Finance, String>( new TextCell()) {
			@Override
			public String getValue( Finance finance ) {return Model.decrypt(finance.getAccountnumber());}
		};
		financeTable.addStringColumn(accountnumber, CONSTANTS.financetableHeaders()[col++], Finance.ACCOUNTNUMBER);

		//-----------------------------------------------
		// IBAN/SWIFT column
		//-----------------------------------------------
		Column<Finance, String> ibanswift = new Column<Finance, String>( new TextCell()) {
			@Override
			public String getValue( Finance finance ) {return finance.getIbanswift();}
		};
		financeTable.addStringColumn(ibanswift, CONSTANTS.financetableHeaders()[col++], Finance.IBANSWIFT);

		//-----------------------------------------------
		// Currency column
		//-----------------------------------------------
		Column<Finance, String> currency = new Column<Finance, String>( new TextCell()) {
			@Override
			public String getValue( Finance finance ) {return finance.getCurrency();}
		};
		financeTable.addStringColumn(currency, CONSTANTS.financetableHeaders()[col++]);

		panel.add(financeTable);
		return panel;
	}

	/* 
	 * Creates the license list stack panel.
	 * 
	 * @return the license list stack panel.
	 */
	private Widget licensetableEmpty() {
		FlowPanel panel = new FlowPanel();

		Button emptyButton = new Button(AbstractField.CONSTANTS.allCreate());
		emptyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				LicensePopup.getInstance().show(licenseTable);
			}
		});
		panel.add(emptyButton);

		panel.add(new HTML(CONSTANTS.licensetableEmpty()));
		panel.add(new Image(BUNDLE.licensetableEmpty()));
		return panel;
	}

	/* 
	 * Creates the license list stack panel.
	 * 
	 * @return the license list stack panel.
	 */
	private ScrollPanel createLicence() {
		final ScrollPanel panel = new ScrollPanel();
		panel.addStyleName(CSS.actorStyle());

		licenseTable = new TableField<License>(this, null, 
				new LicenseTable(),
				200, 
				tab++);

		licenseTable.setEmptyValue(licensetableEmpty());
		licenseTable.setOrderby(License.FROMDATE);

		licenseTable.setTableError(new TableError() {
			@Override
			public boolean error() {return organizationField.noId();}
		});

		licenseTable.setTableExecutor(new TableExecutor<LicenseTable>() {
			@Override
			public void execute(LicenseTable action) {action.setUpstreamid(organizationField.getId());}
		});

		int col = 0;

		//-----------------------------------------------
		// Change / Create buttons
		//-----------------------------------------------
		Column<License, License> changeButton = new Column<License, License>( 
				new ActionCell<License>(AbstractField.CONSTANTS.allChange(), AbstractField.CSS.cbtTableFieldChangeButton(),
						new Delegate<License>(){
					public void execute(License license ) {
						LicensePopup.getInstance().show(license, licenseTable);
					}
				}
				)
		)
		{
			public License getValue(License license){return license;}
		};

		ActionHeader<License> createButton = new ActionHeader<License>(
				new ActionCell<License>(
						AbstractField.CONSTANTS.allCreate(), 
						AbstractField.CSS.cbtTableFieldCreateButton(),
						new Delegate<License>(){
							public void execute(License license ) {
								LicensePopup.getInstance().show(licenseTable);
							}
						}
				)
		)
		{
			public License getValue(License license){
				return license;
			}
		};

		licenseTable.addColumn(changeButton, createButton);

		//-----------------------------------------------
		// Downstream Party column
		//-----------------------------------------------
		Column<License, String> partyname = new Column<License, String>(new TextCell()) {
			@Override
			public String getValue( License license ) {return license.getDownstreamname();}
		};
		licenseTable.addStringColumn(partyname, CONSTANTS.licensetableHeaders()[col++], License.PARTY);


		//-----------------------------------------------
		// Product column
		//-----------------------------------------------
		Column<License, String> productname = new Column<License, String>(new TextCell()) {
			@Override
			public String getValue( License license ) {return license.getProductname();}
		};
		licenseTable.addStringColumn(productname, CONSTANTS.licensetableHeaders()[col++], License.PRODUCT);

		//-----------------------------------------------
		// From Date column
		//-----------------------------------------------
		Column<License, Date> fromdate = new Column<License, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( License license ) {return Time.getDateClient(license.getFromdate());}
		};
		licenseTable.addDateColumn(fromdate, CONSTANTS.licensetableHeaders()[col++], License.FROMDATE);

		//-----------------------------------------------
		// To Date column
		//-----------------------------------------------
		Column<License, Date> todate = new Column<License, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( License license ) {return Time.getDateClient(license.getFromdate());}
		};
		licenseTable.addDateColumn(todate, CONSTANTS.licensetableHeaders()[col++], License.TODATE);

		//-----------------------------------------------
		// State column
		//-----------------------------------------------
		Column<License, String> state = new Column<License, String>(new TextCell()) {
			@Override
			public String getValue( License license ) {return license.getState();}
		};
		licenseTable.addStringColumn(state, CONSTANTS.licensetableHeaders()[col++], License.STATE);

		//-----------------------------------------------
		// Subscription column
		//-----------------------------------------------
		Column<License, Number> discount = new Column<License, Number>(new NumberCell(AbstractField.IF)) {
			@Override
			public Double getValue( License license ) {return license.getSubscription();}
		};
		licenseTable.addNumberColumn( discount, CONSTANTS.licensetableHeaders()[col++], License.SUBSCRIPTION);

		//-----------------------------------------------
		// Transaction column
		//-----------------------------------------------
		Column<License, Number> transaction = new Column<License, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( License license ) {return license.getTransaction();}
		};
		licenseTable.addNumberColumn( transaction, CONSTANTS.licensetableHeaders()[col++], License.TRANSACTION);

		//-----------------------------------------------
		// Upstream column
		//-----------------------------------------------
		Column<License, Number> upstream = new Column<License, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( License license ) {return license.getUpstream();}
		};
		licenseTable.addNumberColumn( upstream, CONSTANTS.licensetableHeaders()[col++], License.UPSTREAM);

		//-----------------------------------------------
		// Downstream column
		//-----------------------------------------------
		Column<License, Number> downsteam = new Column<License, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( License license ) {return license.getDownstream();}
		};
		licenseTable.addNumberColumn( downsteam, CONSTANTS.licensetableHeaders()[col++], License.DOWNSTREAM);

		//-----------------------------------------------
		// Notes column
		//-----------------------------------------------
		Column<License, String> notes = new Column<License, String>(new TextCell()) {
			@Override
			public String getValue( License license ) {return license.getNotes(100);}
		};
		licenseTable.addStringColumn(notes, CONSTANTS.licensetableHeaders()[col++], License.PARTY);

		panel.add(licenseTable);
		return panel;
	}

	/* 
	 * Creates the panel displayed when there is no partner list.
	 * 
	 * @return the empty panel.
	 */
	private Widget partnertableEmpty() {
		FlowPanel panel = new FlowPanel();

		Button emptyButton = new Button(AbstractField.CONSTANTS.allCreate());
		emptyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (organizationField.noValue()) {AbstractField.addMessage(Level.TERSE, CONSTANTS.organizationError(), organizationField);}
				else {PartnerPopup.getInstance().show(partnerTable);}
			}
		});
		panel.add(emptyButton);

		panel.add(new HTML(CONSTANTS.partnertableEmpty()));
		panel.add(new Image(BUNDLE.partnertableEmpty()));
		return panel;
	}
	
	/* 
	 * Creates the partner table stack panel.
	 * This lists the partners in the property for inventory and insurance purposes.
	 * 
	 * @return the partner table stack panel.
	 */
	private ScrollPanel createPartner() {
		ScrollPanel panel = new ScrollPanel();
		
		//-----------------------------------------------
		// Selection model
		//-----------------------------------------------
		final NoSelectionModel<Partner> selectionModel = new NoSelectionModel<Partner>();
		SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				PartnerPopup.getInstance().show(selectionModel.getLastSelectedObject(), partnerTable);
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// Partner table
		//-----------------------------------------------
		partnerTable = new TableField<Partner>(this, null,
				new PartnerTable(),
				PARTNER_ROWS, 
				tab++);

		partnerTable.setEmptyValue(partnertableEmpty());
		partnerTable.setOrderby(Partner.PARTYNAME);
		
		partnerTable.setTableError(new TableError() {
			@Override
			public boolean error() {return (AbstractRoot.noOrganizationid());}
		});

		partnerTable.setTableExecutor(new TableExecutor<PartnerTable>() {
			public void execute(PartnerTable action) {
				action.setOrganizationid(AbstractRoot.getOrganizationid());
				action.setState(null);
			}
		});

		int col = 0;

		//-----------------------------------------------
		// Change button
		//-----------------------------------------------
		final Column<Partner, Partner> changeButton = new Column<Partner, Partner> ( 
				new ActionCell<Partner>(AbstractField.CONSTANTS.allChange(), AbstractField.CSS.cbtTableFieldChangeButton(),
						new Delegate<Partner>(){
					public void execute(Partner partner) {
						PartnerPopup.getInstance().show(partner, partnerTable);
					}
				}
				)
		)
		{
			public Partner getValue(Partner partner){return partner;}
		};

		//-----------------------------------------------
		// Create button
		//-----------------------------------------------
		final ActionHeader<Partner> createButton = new ActionHeader<Partner>(
				new ActionCell<Partner>(
						AbstractField.CONSTANTS.allCreate(),
						AbstractField.CSS.cbtTableFieldCreateButton(),
						new Delegate<Partner>(){
							public void execute(Partner partner ) {
								if (organizationField.noValue()) {AbstractField.addMessage(Level.TERSE, CONSTANTS.organizationError(), organizationField);}
								else {PartnerPopup.getInstance().show(partnerTable);}
							}
						}
				)
		)
		{
			public Partner getValue(Partner partner){
				return partner;
			}
		};

		partnerTable.addColumn(changeButton, createButton);
		
		//-----------------------------------------------
		// Partner Name column
		//-----------------------------------------------
		Column<Partner, String> partyname = new Column<Partner, String>(new TextCell()) {
			@Override
			public String getValue( Partner partner ) {return partner.getPartyname();}
		};
		partnerTable.addStringColumn(partyname, CONSTANTS.partnertableHeaders()[col++], Partner.PARTYNAME);

		//-----------------------------------------------
		// State column
		//-----------------------------------------------
		Column<Partner, String> state = new Column<Partner, String>(new TextCell()) {
			@Override
			public String getValue( Partner partner ) {return partner.getState();}
		};
		partnerTable.addStringColumn(state, CONSTANTS.partnertableHeaders()[col++]);

		//-----------------------------------------------
		// Currency column
		//-----------------------------------------------
		Column<Partner, String> currency = new Column<Partner, String>(new TextCell()) {
			@Override
			public String getValue( Partner partner ) {return partner.getCurrency();}
		};
		partnerTable.addStringColumn(currency, CONSTANTS.partnertableHeaders()[col++]);

		//-----------------------------------------------
		// Date Format column
		//-----------------------------------------------
		Column<Partner, String> dateformat = new Column<Partner, String>(new TextCell()) {
			@Override
			public String getValue( Partner partner ) {return partner.getDateformat();}
		};
		partnerTable.addStringColumn(dateformat, CONSTANTS.partnertableHeaders()[col++]);

		//-----------------------------------------------
		// Commission column
		//-----------------------------------------------
		Column<Partner, Number> commission = new Column<Partner, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( Partner partner ) {return partner.getCommission();}
		};
		partnerTable.addNumberColumn( commission, CONSTANTS.partnertableHeaders()[col++]);

		//-----------------------------------------------
		// Discount column
		//-----------------------------------------------
		Column<Partner, Number> discount = new Column<Partner, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( Partner partner ) {return partner.getDiscount();}
		};
		partnerTable.addNumberColumn( discount, CONSTANTS.partnertableHeaders()[col++]);

		//-----------------------------------------------
		// Alert CRON column
		//-----------------------------------------------
		Column<Partner, String> alertcron = new Column<Partner, String>(new TextCell()) {
			@Override
			public String getValue( Partner partner ) {return partner.getAlertcron();}
		};
		partnerTable.addStringColumn(alertcron, CONSTANTS.partnertableHeaders()[col++]);

		//-----------------------------------------------
		// Price CRON column
		//-----------------------------------------------
		Column<Partner, String> pricecron = new Column<Partner, String>(new TextCell()) {
			@Override
			public String getValue( Partner partner ) {return partner.getPricecron();}
		};
		partnerTable.addStringColumn(pricecron, CONSTANTS.partnertableHeaders()[col++]);

		//-----------------------------------------------
		// Product CRON column
		//-----------------------------------------------
		Column<Partner, String> productcron = new Column<Partner, String>(new TextCell()) {
			@Override
			public String getValue( Partner partner ) {return partner.getProductcron();}
		};
		partnerTable.addStringColumn(productcron, CONSTANTS.partnertableHeaders()[col++]);

		//-----------------------------------------------
		// Schedule CRON column
		//-----------------------------------------------
		Column<Partner, String> schedulecron = new Column<Partner, String>(new TextCell()) {
			@Override
			public String getValue( Partner partner ) {return partner.getSchedulecron();}
		};
		partnerTable.addStringColumn(schedulecron, CONSTANTS.partnertableHeaders()[col++]);

		//-----------------------------------------------
		// Special CRON column
		//-----------------------------------------------
		Column<Partner, String> specialcron = new Column<Partner, String>(new TextCell()) {
			@Override
			public String getValue( Partner partner ) {return partner.getSpecialcron();}
		};
		partnerTable.addStringColumn(specialcron, CONSTANTS.partnertableHeaders()[col++]);

		//-----------------------------------------------
		// Default Web Address column
		//-----------------------------------------------
		Column<Partner, String> webaddress = new Column<Partner, String>(new TextCell()) {
			@Override
			public String getValue( Partner partner ) {return partner.getWebaddress();}
		};
		partnerTable.addStringColumn(webaddress, CONSTANTS.partnertableHeaders()[col++]);

		//-----------------------------------------------
		// Book Email Address column
		//-----------------------------------------------
		Column<Partner, String> bookemailaddress = new Column<Partner, String>(new TextCell()) {
			@Override
			public String getValue( Partner partner ) {return partner.getBookemailaddress();}
		};
		partnerTable.addStringColumn(bookemailaddress, CONSTANTS.partnertableHeaders()[col++]);
	
		//-----------------------------------------------
		// Book Web Address column
		//-----------------------------------------------
		Column<Partner, String> bookwebaddress = new Column<Partner, String>(new TextCell()) {
			@Override
			public String getValue( Partner partner ) {return partner.getBookwebaddress();}
		};
		partnerTable.addStringColumn(bookwebaddress, CONSTANTS.partnertableHeaders()[col++]);
	
		panel.add(partnerTable);
		return panel;
	}

	/* 
	 * Creates the default values stack panel.
	 * 
	 * @return the default values stack panel.
	 */
	private FlowPanel createDefaults() {
		final FlowPanel panel = new FlowPanel();
		final HorizontalPanel defaults = new HorizontalPanel();
		panel.add(defaults);
		final FlowPanel form = new FlowPanel();
		defaults.add(form);
		final Grid grid = new Grid(3, 2);
		defaults.add(grid);

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
		countryField.setHelpText(CONSTANTS.countryHelp());
		form.add(countryField);

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
		languageField.setHelpText(CONSTANTS.languageHelp());
		form.add(languageField);

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
		currencyField.setHelpText(CONSTANTS.currencyHelp());
		form.add(currencyField);

		//-----------------------------------------------
		// Jurisdiction field
		//-----------------------------------------------
//		jurisdictionField = new ListField(this, null,
//				new NameIdAction(Service.PARTY, Party.Type.Jurisdiction.name()),
//				CONSTANTS.jurisdictionLabel(),
//				false,
//				tab++);
//		final Image jurisdictionButton = new Image(AbstractField.BUNDLE.plus());
//		jurisdictionButton.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				JurisdictionPopup.getInstance().show(jurisdictionField.getValue());
//			}
//		}); 
//		jurisdictionField.addButton(jurisdictionButton);
//		jurisdictionField.setVisible(AbstractRoot.hasPermission(AccessControl.SUPERUSER));
//		jurisdictionField.setTitle(CONSTANTS.jurisdictionbuttonHelp());
//		jurisdictionField.setAllOrganizations(true);
//		jurisdictionField.setHelpText(CONSTANTS.jurisdictionHelp());
//		form.add(jurisdictionField);

		//-----------------------------------------------
		// Tax Number field
		//-----------------------------------------------
		taxnumberField = new TextField(this, null,
				CONSTANTS.taxnumberLabel(),
				tab++);
		taxnumberField.setHelpText(CONSTANTS.taxnumberHelp());
		form.add(taxnumberField);

		//-----------------------------------------------
		// PMS Code or URL field
		//-----------------------------------------------
		urlField = new TextAreaField(this, null,
				CONSTANTS.urlLabel(),
				tab++);
		urlField.setFieldStyle(CSS.urlStyle());
		urlField.setHelpText(CONSTANTS.urlHelp());
		form.add(urlField);

		//-----------------------------------------------
		// Initial Deposit Percent field
		//-----------------------------------------------
		depositField = new DoubleunitField(this, null,
				NameId.getList(Party.DEPOSITS, Party.DEPOSITS),
				CONSTANTS.depositLabel(),
				AbstractField.AF,
				tab++);
		depositField.setRange(0., 10000.); // TODO : check why 10000
		depositField.setDefaultValue(50.);
//		depositField.setDefaultUnitvalue(Party.DEPOSITS[0]);
		depositField.setLabelStyle(CSS.depositLabel());
		depositField.setHelpText(CONSTANTS.depositHelp());
		form.add(depositField);

		//-----------------------------------------------
		// Full Payment Days  field 
		//-----------------------------------------------
		payfullField = new IntegerunitField(this, null,
				NameId.getList(AbstractField.CONSTANTS.dwmUnits(), Unit.DWM_UNITS),
				CONSTANTS.payfullLabel(),
				tab++);
		payfullField.setDefaultValue(30);
		payfullField.setHelpText(CONSTANTS.payfullHelp());
		form.add(payfullField);

		//-----------------------------------------------
		// Payment Policy field
		//-----------------------------------------------
		final String[] DEPOSIT_TYPES = {Organization.Paytype.None.name(), Organization.Paytype.Print.name(), Organization.Paytype.Email.name(), Organization.Paytype.SMS.name()};
		paytypeField = new ListField(this, null,
				NameId.getList(OrganizationForm.CONSTANTS.depositTypes(), DEPOSIT_TYPES),
				CONSTANTS.paytypeLabel(),
				false,
				tab++);
		paytypeField.setDefaultValue(Organization.Paytype.None.name());
		paytypeField.setFieldHalf();
		paytypeField.setHelpText(CONSTANTS.paytypeHelp());
		form.add(paytypeField);

		//-----------------------------------------------
		// Options
		//-----------------------------------------------
		optionsField = new CheckFields(this, null, CONSTANTS.options(), true, tab++);
		optionsField.setDefaultValue("11");
		form.add(optionsField);

		//-----------------------------------------------
		// Currencies
		//-----------------------------------------------
		grid.setWidget(0, 0, new Label(CONSTANTS.currenciesLabel()));
		grid.getWidget(0, 0).addStyleName(CSS.localeLabel());
		grid.setWidget(1, 0, new Label(CONSTANTS.currenciesText()));
		grid.getWidget(1, 0).addStyleName(CSS.localeText());
		currenciesField = new VshuttleField(this, null,
				new CurrencyNameId(),
				null,
				20,
				tab++);
		currenciesField.setAllOrganizations(true);
		currenciesField.setSelectAllEnabled(false);
		currenciesField.setAvailableStyle(CSS.availableStyle());
		grid.setWidget(2, 0, currenciesField);

		//-----------------------------------------------
		// Languages
		//-----------------------------------------------
		grid.setWidget(0, 1, new Label(CONSTANTS.languagesLabel()));
		grid.getWidget(0, 1).addStyleName(CSS.localeLabel());
		grid.setWidget(1, 1, new Label(CONSTANTS.languagesText()));
		grid.getWidget(1, 1).addStyleName(CSS.localeText());
		languagesField = new VshuttleField(this, null,
				new LanguageNameId(),
				null,
				20,
				tab++);
		languagesField.setAllOrganizations(true);
		languagesField.setSelectAllEnabled(false);
		languagesField.setAvailableStyle(CSS.availableStyle());
		grid.setWidget(2, 1, languagesField);

		final HorizontalPanel party = new HorizontalPanel();
		panel.add(party);
		
		//-----------------------------------------------
		// Party field
		//-----------------------------------------------
//		partyField = new SuggestField(this, null,
//				new NameIdAction(Service.PARTY),
//				CONSTANTS.partynameLabel(),
//				20,
//				tab++);
//		partyField.setReadOption(Party.CREATED, true);
//		partyField.setDoubleclickable(true);
//		partyField.setHelpText(CONSTANTS.partynameHelp());
//		party.add(partyField);

		//-----------------------------------------------
		// Party Type shuttle
		//-----------------------------------------------
		//String[] DISABLED = {Type.AGENT.name(), Type.ORGANIZATION.name()};
//		partytypesField = new ShuttleField(this, null,
//				NameId.getList(CONSTANTS.partyTypes(), Party.TYPES),
//				CONSTANTS.partytypesLabel(),
//				tab++);
//		partytypesField.setEnabled(false);
//		partytypesField.setFieldStyle(CSS.partytypesStyle());
//		partytypesField.setHelpText(CONSTANTS.partytypesHelp());
//		party.add(partytypesField);

		return panel;
	}

	/* 
	 * Gets the work flow state name from the specified value.
	 * 
	 * @param value the specified value.
	 * @return the work flow state name.
	 */
	private String getStatename(String value) {
		if (Reservation.WORKFLOW_STATES.length != CONSTANTS.workflowStates().length) {Window.alert("OrganizationForm getStatename unequal");}
		for (int index = 0; index < Reservation.WORKFLOW_STATES.length; index++) {
			if (Reservation.WORKFLOW_STATES[index].equalsIgnoreCase(value)) {return CONSTANTS.workflowStates()[index];}
		}
		return null;
	}

	/* 
	 * Creates the work flow rules stack panel.
	 * 
	 * @return the work flow rules stack panel.
	 */
	private FlowPanel createWorkflow() {
		final FlowPanel panel = new FlowPanel();
		final HTML workflowText = new HTML(CONSTANTS.workflowText());
		panel.add(workflowText);
		final String[] WORKFLOW_DATES = {Reservation.FROMDATE, Reservation.DATE, Reservation.TODATE};
		final ArrayList<NameId> DATENAMES = NameId.getList(CONSTANTS.workflowDates(), WORKFLOW_DATES);

		workflowGrid = new GridField<Workflow>(this, null,
				CONSTANTS.workflowHeaders(),
				Reservation.WORKFLOW_STATES.length,
				tab++) {

			@Override
			public void setRowValue(int row, Workflow value) {
				
				CheckBox enabledField = new CheckBox(getStatename(value.getState()));
				enabledField.setFormValue(value.getState());
				enabledField.setValue(value.getEnabled());
				enabledField.addStyleName(OrganizationForm.CSS.workflowState());
				workflowGrid.setWidget(row, 0, enabledField);

				IntegerField durationField = new IntegerField(this, null, null, 0);
				durationField.setValue(value.getDuration());
				durationField.setFieldStyle(OrganizationForm.CSS.workflowDuration());
				workflowGrid.setWidget(row, 1, durationField);

				workflowGrid.setText(row, 2, value.getUnit().toString());
				
				ToggleButton priorField = new ToggleButton(OrganizationForm.CONSTANTS.workflowLabelAfter(), OrganizationForm.CONSTANTS.workflowLabelBefore());
				priorField.setDown(value.getPrior());
				priorField.addStyleName(OrganizationForm.CSS.workflowWhen());
				workflowGrid.setWidget(row, 3, priorField);

				ListField datenameField = new ListField(this, null, DATENAMES, null, false, 0);
				datenameField.setValue(value.getDatename());
				datenameField.setFieldStyle(OrganizationForm.CSS.workflowDatename());
				workflowGrid.setWidget(row, 4, datenameField);
			}

			@Override
			public Workflow getRowValue(int row) {
				CheckBox enabledField = (CheckBox)workflowGrid.getWidget(row, 0);
				IntegerField durationField = (IntegerField)workflowGrid.getWidget(row, 1);
				ToggleButton priorField = (ToggleButton)workflowGrid.getWidget(row, 3);
				ListField datenameField = (ListField)workflowGrid.getWidget(row, 4);
				Workflow value = new Workflow(organizationField.getId(), NameId.Type.Reservation.name(), enabledField.getFormValue(), enabledField.getValue(), priorField.isDown(), datenameField.getValue(), Time.DAY.name(), durationField.getValue());
				//Window.alert("getRowValue " + value);
				return value;
			}
		};

		ArrayList<Workflow> defaultValue = new ArrayList<Workflow>();
		defaultValue.add(new Workflow(organizationField.getId(), NameId.Type.Reservation.name(), Reservation.WORKFLOW_STATES[0], true, false, Reservation.DATE, Time.DAY.name(), 2));//RESERVED
		defaultValue.add(new Workflow(organizationField.getId(), NameId.Type.Reservation.name(), Reservation.WORKFLOW_STATES[1], true, false, Reservation.DATE, Time.DAY.name(), 3));//CONFIRMED
		defaultValue.add(new Workflow(organizationField.getId(), NameId.Type.Reservation.name(), Reservation.WORKFLOW_STATES[2], true, true, Reservation.FROMDATE, Time.DAY.name(), 35));//FULLYPAID
		defaultValue.add(new Workflow(organizationField.getId(), NameId.Type.Reservation.name(), Reservation.WORKFLOW_STATES[3], true, true, Reservation.FROMDATE, Time.DAY.name(), 7));//BRIEFED
		defaultValue.add(new Workflow(organizationField.getId(), NameId.Type.Reservation.name(), Reservation.WORKFLOW_STATES[4], true, true, Reservation.FROMDATE, Time.DAY.name(), 1));//ARRIVED
		defaultValue.add(new Workflow(organizationField.getId(), NameId.Type.Reservation.name(), Reservation.WORKFLOW_STATES[5], true, true, Reservation.TODATE, Time.DAY.name(), 2));//PREDEPARTED
		defaultValue.add(new Workflow(organizationField.getId(), NameId.Type.Reservation.name(), Reservation.WORKFLOW_STATES[6], true, true, Reservation.TODATE, Time.DAY.name(), 7));//DEPARTED
//		defaultValue.add(new Workflow(organizationField.getId(), NameId.Type.Reservation.name(), Reservation.WORKFLOW_STATES[7], true, false, Reservation.TODATE, Time.DAY.name(), 7));//DEPARTED
		workflowGrid.setDefaultValue(defaultValue);

		panel.add(workflowGrid);
		return panel;
	}
	


	//-----------------------------------------------
	// Register button
	//-----------------------------------------------
//	private void initRegister() {
//		final String registerUrl = "http://demo.razor-cloud.com/registration/index.html?pos=" + posField.getText() + "&email= "
//				+ emailaddressField.getText();
//		registerLink.setTitle(CONSTANTS.registerLabel());
//		registerLink.addStyleName(AbstractField.CSS.cbtCommandHyperlink());
//		registerLink.setVisible(true);
//		registerLink.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				Window.open(registerUrl, "_blank", "menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=yes");
//			}
//		});
//	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Create User (Agent)
		//-----------------------------------------------
		agentCreate = new GuardedRequest<Organization>() {
			protected boolean error() {return false;}
			protected void send() {
				onReset(Organization.INITIAL);
				creatorField.setValue(AbstractRoot.getCreatorid());
				super.send(getValue(new AgentCreate()));
			}
			protected void receive(Organization agent){
				setValue(agent);
				Session session = new Session();
				session.setId(Model.ZERO);
				session.setActorid(agent.getId());
				session.setCurrency(agent.getCurrency());
				session.setLanguage(agent.getLanguage());
				session.setEmailaddress(emailaddressField.getValue());
				session.setOrganizationid(agent.getId());
				session.setRank(0);
				session.setState(Session.LOGGED_IN);
				AbstractRoot.setSession(session);
				AbstractRoot.setPermission(AccessControl.AGENCY);
				descriptionField.setDefaultType(session.getLanguage());
				contracttextField.setDefaultType(session.getLanguage());
				createHeader(header, Razor.ORGANIZATION_TAB);
			}
		};

		//-----------------------------------------------
		// Create Organization (Manager or Agency)
		//-----------------------------------------------
		organizationCreate = new GuardedRequest<Organization>() {
			protected boolean error() {return false;}
			protected void send() {
				onReset(Organization.INITIAL);
				creatorField.setValue(AbstractRoot.getCreatorid());
				super.send(getValue(new OrganizationCreate()));
			}
			protected void receive(Organization organization) {
				//Window.alert("OrganizationCreate receive " + organization);
				setValue(organization);
				Session session = new Session();
				session.setId(Model.ZERO);
				session.setActorid(organization.getId());
				session.setCurrency(organization.getCurrency());
				session.setLanguage(organization.getLanguage());
				session.setEmailaddress(emailaddressField.getValue());
				session.setOrganizationid(organization.getId());
				session.setRank(0);
				session.setState(Session.LOGGED_IN);
				AbstractRoot.setSession(session);
				AbstractRoot.setPermission(AccessControl.ADMINISTRATOR);
				descriptionField.setDefaultType(session.getLanguage());
				contracttextField.setDefaultType(session.getLanguage());
				createHeader(header, Razor.ORGANIZATION_TAB);
			}
		};

		//-----------------------------------------------
		// Read Organization (Manager or Agency)
		//-----------------------------------------------
		organizationRead = new GuardedRequest<Organization>() {
			protected boolean error() {return organizationField.noId();}
			protected void send() {super.send(getValue(new OrganizationRead()));}
			protected void receive(Organization response) {setValue(response);}
		};

		//-----------------------------------------------
		// Update Organization (Manager or Agency)
		//-----------------------------------------------
		organizationUpdate = new GuardedRequest<Organization>() {
			protected boolean error() {
				return (
						ifMessage(noState(), Level.TERSE, AbstractField.CONSTANTS.errState(), organizationField)
						|| ifMessage(organizationField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errId(), organizationField)
						|| ifMessage(organizationField.noName(), Level.ERROR, AbstractField.CONSTANTS.errId(), organizationField)
						|| ifMessage(emailaddressField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errEmailaddress(), emailaddressField)
						|| ifMessage(!Party.isEmailAddress(emailaddressField.getValue()), Level.ERROR, AbstractField.CONSTANTS.errEmailaddress(), emailaddressField)
//						|| ifMessage(!Party.isPhoneNumber(dayphoneField.getValue()), Level.ERROR, AbstractField.CONSTANTS.errPhoneNumber(), dayphoneField)
						|| ifMessage(hadState(Party.INITIAL) && passwordField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errPassword(), passwordField)
				);
			}
			protected void send() {super.send(getValue(new OrganizationUpdate()));}
			protected void receive(Organization response) {setValue(response);}
		};

		//-----------------------------------------------
		// Delete Party (Manager or Agency)
		//-----------------------------------------------
		partyDelete = new GuardedRequest<Organization>() {
			protected String popup() {return AbstractField.CONSTANTS.errDeleteOK();}
			public void cancel() {state = oldstate;}
			protected boolean error() {return ifMessage(organizationField.noId(), Level.ERROR, AbstractField.CONSTANTS.errId(), organizationField);}
			protected void send() {super.send(getValue(new PartyDelete()));}
			protected void receive(Organization response) {setValue(response);}
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
		// Read List of Party Types
		//-----------------------------------------------
//		partyTypes = new GuardedRequest<Table<String>>() {
//			protected boolean error() {return partyField.noValue();}
//			protected void send() {super.send(getValue(new PartyTypes(partyField.getValue())));}
//			protected void receive(Table<String> response) {partytypesField.setValue(response.getValue());}
//		};

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
		if (organizationField.noValue() || hasState(Organization.INITIAL)) {return 0;}
		double progress = 0;
		if (AbstractRoot.hasPermission(AccessControl.ADMINISTRATOR)) {
			progress += dataProgress() * 0.15;
			progress += contracttextProgress() * 0.10;
			progress += descriptionProgress() * 0.10;
			progress += emailaddressProgress() * 0.10;
			progress += logoProgress() * 0.10;
			progress += actorProgress() * 0.10;
			progress += defaultProgress() * 0.10;
			progress += financeProgress() * 0.10;
			progress += workflowProgress() * 0.15;
		}
		else if (AbstractRoot.hasPermission(AccessControl.AGENCY)) {
			progress += dataProgress() * 0.20;
			progress += descriptionProgress() * 0.20;
			progress += emailaddressProgress() * 0.20;
			progress += logoProgress() * 0.20;
			progress += actorProgress() * 0.20;
		}
		return (int) progress;
	}

	private double dataProgress() {
		double progress = 0;
		progress += organizationField.noValue() ? 0.0 : 40.0;
		progress += extranameField.noValue() ? 0.0 : 10.0;
		progress += dayphoneField.noValue() ? 0.0 : 10.0;
		progress += mobilephoneField.noValue() ? 0.0 : 10.0;
		progress += faxphoneField.noValue() ? 0.0 : 10.0;
		progress += webaddressField.noValue() ? 0.0 : 10.0;
		progress += postaladdressField.noValue() ? 0.0 : 10.0;
		return progress;
	}

	private double contracttextProgress() {
		if (contracttextField.noValue() || contracttextField.hasDefaultValue()) {return 0.0;}
		else {return Math.min(100.0, contracttextField.getValue().length() / 3.0);}
	}

	private double descriptionProgress() {
		if (descriptionField.noValue() || descriptionField.hasDefaultValue()) {return 0.0;}
		else {return Math.min(100.0, descriptionField.getValue().length() / 3.0);}
	}

	private double emailaddressProgress() {
		return emailaddressField.noValue() || !Party.isEmailAddress(emailaddressField.getValue()) ? 0.0 : 100.0;
	}

	private double logoProgress() {
		return logoField.noValue() ? 0.0 : 100.0;
	}

	private double actorProgress() {
		if (actorTable.noValue()) {return 0.0;}
		else {return Math.min(100.0, actorTable.getValue().size() * 25.0);}
	}

	private double defaultProgress() {
		double progress = 0;
		progress += currenciesField.noValue() ? 0.0 : 20.0;
		progress += languagesField.noValue() ? 0.0 : 20.0;
		progress += countryField.noValue() ? 0.0 : 20.0;
		progress += currencyField.noValue() ? 0.0 : 20.0;
		progress += languageField.noValue() ? 0.0 : 20.0;
		return progress;
	}

	private double financeProgress() {
		return financeTable.noValue() ? 0.0 : 100.0;
	}

	private double workflowProgress() {
		if (workflowGrid.noValue()) {return 0.0;}
		else {return Math.min(100.0, workflowGrid.getNumRows() * 20.0);}
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

			ProgressField dataprogressField = new ProgressField(OrganizationForm.this, null, CONSTANTS.dataprogressLabel(), 0, 100, tab);
			dataprogressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			dataprogressField.setValue((int)dataProgress());
			form.add(dataprogressField);

			ProgressField descriptionprogressField = new ProgressField(OrganizationForm.this, null, CONSTANTS.descriptionprogressLabel(), 0, 100, tab);
			descriptionprogressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			descriptionprogressField.setValue((int)descriptionProgress());
			form.add(descriptionprogressField);

			ProgressField emailaddressprogressField = new ProgressField(OrganizationForm.this, null, CONSTANTS.emailaddressprogressLabel(), 0, 100, tab);
			emailaddressprogressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			emailaddressprogressField.setValue((int)emailaddressProgress());
			form.add(emailaddressprogressField);

			ProgressField contracttextprogressField = new ProgressField(OrganizationForm.this, null, CONSTANTS.contracttextprogressLabel(), 0, 100, tab);
			contracttextprogressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			contracttextprogressField.setValue((int)contracttextProgress());
			contracttextprogressField.setVisible(AbstractRoot.hasPermission(AccessControl.ADMINISTRATOR));
			form.add(contracttextprogressField);

			ProgressField reportheaderprogressField = new ProgressField(OrganizationForm.this, null, CONSTANTS.reportheaderprogressLabel(), 0, 100, tab);
			reportheaderprogressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			reportheaderprogressField.setValue((int)logoProgress());
			reportheaderprogressField.setVisible(AbstractRoot.hasPermission(AccessControl.ADMINISTRATOR));
			form.add(reportheaderprogressField);

			ProgressField actorprogressField = new ProgressField(OrganizationForm.this, null, CONSTANTS.actorprogressLabel(), 0, 100, tab);
			actorprogressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			actorprogressField.setValue((int)actorProgress());
			form.add(actorprogressField);

			ProgressField defaultprogressField = new ProgressField(OrganizationForm.this, null, CONSTANTS.defaultprogressLabel(), 0, 100, tab);
			defaultprogressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			defaultprogressField.setValue((int)defaultProgress());
			defaultprogressField.setVisible(AbstractRoot.hasPermission(AccessControl.ADMINISTRATOR));
			form.add(defaultprogressField);

			ProgressField financeprogressField = new ProgressField(OrganizationForm.this, null, CONSTANTS.financeprogressLabel(), 0, 100, tab);
			financeprogressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			financeprogressField.setValue((int)financeProgress());
			financeprogressField.setVisible(AbstractRoot.hasPermission(AccessControl.ADMINISTRATOR));
			form.add(financeprogressField);

			ProgressField workflowprogressField = new ProgressField(OrganizationForm.this, null, CONSTANTS.workflowprogressLabel(), 0, 100, tab);
			workflowprogressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			workflowprogressField.setValue((int)workflowProgress());
			workflowprogressField.setVisible(AbstractRoot.hasPermission(AccessControl.ADMINISTRATOR));
			form.add(workflowprogressField);

			setWidget(form);
			setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
		}
	}
}

