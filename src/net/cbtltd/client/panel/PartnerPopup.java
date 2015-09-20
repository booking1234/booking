/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.panel;

import java.util.ArrayList;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.LocalRequest;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.CheckField;
import net.cbtltd.client.field.CommandButton;
import net.cbtltd.client.field.DoubleField;
import net.cbtltd.client.field.IntegerField;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.MessagePanel;
import net.cbtltd.client.field.SpinnerField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.form.AccessControl;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.Hosts;
import net.cbtltd.client.resource.partner.PartnerBundle;
import net.cbtltd.client.resource.partner.PartnerConstants;
import net.cbtltd.client.resource.partner.PartnerCssResource;
import net.cbtltd.shared.License;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Partner;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.finance.CurrencyNameId;
import net.cbtltd.shared.partner.PartnerCreate;
import net.cbtltd.shared.partner.PartnerDelete;
import net.cbtltd.shared.partner.PartnerRead;
import net.cbtltd.shared.partner.PartnerUpdate;
import net.cbtltd.shared.party.PartyRead;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mybookingpal.config.RazorConfig;

/**
 * The Class PartnerPopup is to display and change partner instances.
 * The location ID of the partner is the property ID where the partner is used.
 * The owner of the partner defaults to the owner of the property but can be changed.
 * An partner can be depreciated according to its depreciation type.
 */
public class PartnerPopup
extends AbstractPopup<Partner> {

	private static final PartnerConstants CONSTANTS = GWT.create(PartnerConstants.class);
	private static final Hosts HOSTS = GWT.create(Hosts.class);
	private static final PartnerBundle BUNDLE = PartnerBundle.INSTANCE;
	private static final PartnerCssResource CSS = BUNDLE.css();
	private static final String ALL_CRON = "*";
	
	private static GuardedRequest<Partner> partnerCreate;
	private static GuardedRequest<Partner> partnerRead;
	private static GuardedRequest<Partner> partnerUpdate;
	private static GuardedRequest<Partner> partnerDelete;
	private static GuardedRequest<Party> partyRead;

	private static ListField partyField;
	private static ListField currencyField;
	private static ListField dateformatField;
	private static IntegerField alertwaitField;
	private static SpinnerField alertminuteField;
	private static SpinnerField alerthourField;
	private static SpinnerField alertweekField;
	private static SpinnerField alertmonthField;
	private static IntegerField pricewaitField;
	private static SpinnerField priceminuteField;
	private static SpinnerField pricehourField;
	private static SpinnerField priceweekField;
	private static SpinnerField pricemonthField;
	private static IntegerField productwaitField;
	private static SpinnerField productminuteField;
	private static SpinnerField producthourField;
	private static SpinnerField productweekField;
	private static SpinnerField productmonthField;
	private static IntegerField schedulewaitField;
	private static SpinnerField scheduleminuteField;
	private static SpinnerField schedulehourField;
	private static SpinnerField scheduleweekField;
	private static SpinnerField schedulemonthField;
	private static IntegerField specialwaitField;
	private static SpinnerField specialminuteField;
	private static SpinnerField specialhourField;
	private static SpinnerField specialweekField;
	private static SpinnerField specialmonthField;
	private static TextField apikeyField;
	private static TextField webaddressField;
	private static CheckField bookofflineField;
	private static TextField bookemailaddressField;
	private static TextField bookwebaddressField;
	private static DoubleField commissionField;
	private static DoubleField discountField;
	private static DoubleField subscriptionField;
	private static DoubleField transactionField;
	private static TableField<Partner> parentTable;	

	private static String id;
	private static boolean noId() {return id == null || id.isEmpty();}
	
	/** Instantiates a new partner pop up panel.
	 */
	public PartnerPopup() {
		super(true);
		createActions();
		setWidget(createContent());
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
	}

	private static PartnerPopup instance;
	
	/**
	 * Gets the single instance of PartnerPopup.
	 *
	 * @return single instance of PartnerPopup
	 */
	public static synchronized PartnerPopup getInstance() {
		if (instance == null) {instance = new PartnerPopup();}
		id = null;
		partyField.setValue(null);
		return instance;
	}

	/**
	 * Shows the panel for a new partner for the specified entity.
	 *
	 * @param parentTable the parent table.
	 */
	public void show(TableField<Partner> parentTable) {
		PartnerPopup.parentTable = parentTable;
		onRefresh();
		onStateChange(Partner.INITIAL);
		partnerCreate.execute(true);
	}

	/**
	 * Shows the panel for the specified partner.
	 *
	 * @param partner the specified partner.
	 * @param parentTable the parent table.
	 */
	public void show(Partner partner, TableField<Partner> parentTable) {
		PartnerPopup.parentTable = parentTable;
		PartnerPopup.id = partner.getId();
		partnerRead.execute(true);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {
		if (partyField.sent(change)) {partyRead.execute();}
		else if (bookofflineField.sent(change)) {
			bookemailaddressField.setVisible(bookofflineField.getValue());
			bookwebaddressField.setVisible(bookofflineField.getValue());
		}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue()
	 */
	@Override
	public Partner getValue() {return getValue(new Partner());}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue(java.lang.Object)
	 */
	private Partner getValue(Partner partner) {
		partner.setState(state);
		partner.setOrganizationid(AbstractRoot.getOrganizationid());
		partner.setActorid(AbstractRoot.getActorid());
		partner.setId(id);
		partner.setPartyid(partyField.getValue());
		partner.setPartyname(partyField.getName());
		partner.setApikey(apikeyField.getValue());
		partner.setBookoffline(bookofflineField.getValue());
		partner.setBookemailaddress(bookemailaddressField.getValue());
		partner.setBookwebaddress(bookwebaddressField.getValue());
		partner.setCurrency(currencyField.getValue());
		partner.setDateformat(dateformatField.getValue());
		partner.setWebaddress(webaddressField.getValue());
		partner.setCommission(commissionField.getValue());
		partner.setDiscount(discountField.getValue());
		partner.setSubscription(subscriptionField.getValue());
		partner.setTransaction(transactionField.getValue());
		partner.setAlertwait(alertwaitField.getValue());
		partner.setPricewait(pricewaitField.getValue());
		partner.setProductwait(productwaitField.getValue());
		partner.setSchedulewait(schedulewaitField.getValue());
		partner.setSpecialwait(specialwaitField.getValue());
		partner.setAlertcron(getAlertcron());
		partner.setPricecron(getPricecron());
		partner.setProductcron(getProductcron());
		partner.setSchedulecron(getSchedulecron());
		partner.setSpecialcron(getSpecialcron());
		Log.debug("getValue " + partner);
		return partner;
	}

	private String getCronString(Integer value) {
		return value == -1 ? ALL_CRON : String.valueOf(value);
	}

//	private String getCronMonth(Integer value) {
//		return value == 0 ? ALL_CRON : String.valueOf(value);
//	}
//
	private String getAlertcron() {
		return getCronString(alertminuteField.getValue()) + " " + getCronString(alerthourField.getValue()) + " " + getCronString(alertmonthField.getValue()) + " * " + getCronString(alertweekField.getValue());
	}

	private String getPricecron() {
		return getCronString(priceminuteField.getValue()) + " " + getCronString(pricehourField.getValue()) + " " + getCronString(pricemonthField.getValue()) + " * " + getCronString(priceweekField.getValue());
	}

	private String getProductcron() {
		return getCronString(productminuteField.getValue()) + " " + getCronString(producthourField.getValue()) + " " + getCronString(productmonthField.getValue()) + " * " + getCronString(productweekField.getValue());
	}

	private String getSchedulecron() {
		return getCronString(scheduleminuteField.getValue()) + " " + getCronString(schedulehourField.getValue()) + " " + getCronString(schedulemonthField.getValue()) + " * " + getCronString(scheduleweekField.getValue());
	}

	private String getSpecialcron() {
		return getCronString(specialminuteField.getValue()) + " " + getCronString(specialhourField.getValue()) + " " + getCronString(specialmonthField.getValue()) + " * " + getCronString(specialweekField.getValue());
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(Partner partner) {
		Log.debug("setValue " + partner);
		if (partner != null) {
			setResetting(true);
			onStateChange(partner.getState());
			id = partner.getId();
			partyField.setValue(partner.getPartyid());
			partyField.setName(partner.getPartyname());
			apikeyField.setValue(partner.getApikey());
			bookofflineField.setValue(partner.getBookoffline());
			bookemailaddressField.setValue(partner.getBookemailaddress());
			bookwebaddressField.setValue(partner.getBookwebaddress());
			currencyField.setValue(partner.getCurrency());
			dateformatField.setValue(partner.getDateformat());
			webaddressField.setValue(partner.getWebaddress());
			commissionField.setValue(partner.getCommission());
			discountField.setValue(partner.getDiscount());
			subscriptionField.setValue(partner.getSubscription());
			transactionField.setValue(partner.getTransaction());
			alertwaitField.setValue(partner.getAlertwait());
			pricewaitField.setValue(partner.getPricewait());
			productwaitField.setValue(partner.getProductwait());
			schedulewaitField.setValue(partner.getSchedulewait());
			specialwaitField.setValue(partner.getSpecialwait());
			setAlertcron(partner.getAlertcron());
			setPricecron(partner.getPricecron());
			setProductcron(partner.getProductcron());
			setSchedulecron(partner.getSchedulecron());
			setSpecialcron(partner.getSpecialcron());
			
			bookemailaddressField.setVisible(partner.getBookoffline());
			bookwebaddressField.setVisible(partner.getBookoffline());

			center();
			setResetting(false);
		}
	}

	private Integer getCronIndex(String value, int i) {
		String[] args = value.split(" ");
		if (args.length != 5) {new MessagePanel(Level.VERBOSE, CONSTANTS.cronError() + " " + value).center();}
		return ALL_CRON.equalsIgnoreCase(args[i]) ? -1 : Integer.valueOf(args[i]);
	}
	
	private void setAlertcron(String value) {
		alertminuteField.setValue(getCronIndex(value,0));
		alerthourField.setValue(getCronIndex(value,1));
		alertweekField.setValue(getCronIndex(value,4));
		alertmonthField.setValue(getCronIndex(value,2));
	}

	private void setPricecron(String value) {
		priceminuteField.setValue(getCronIndex(value,0));
		pricehourField.setValue(getCronIndex(value,1));
		priceweekField.setValue(getCronIndex(value,4));
		pricemonthField.setValue(getCronIndex(value,2));
	}

	private void setProductcron(String value) {
		productminuteField.setValue(getCronIndex(value,0));
		producthourField.setValue(getCronIndex(value,1));
		productweekField.setValue(getCronIndex(value,4));
		productmonthField.setValue(getCronIndex(value,2));
	}

	private void setSchedulecron(String value) {
		scheduleminuteField.setValue(getCronIndex(value,0));
		schedulehourField.setValue(getCronIndex(value,1));
		scheduleweekField.setValue(getCronIndex(value,4));
		schedulemonthField.setValue(getCronIndex(value,2));
	}

	private void setSpecialcron(String value) {
		specialminuteField.setValue(getCronIndex(value,0));
		specialhourField.setValue(getCronIndex(value,1));
		specialweekField.setValue(getCronIndex(value,4));
		specialmonthField.setValue(getCronIndex(value,2));
	}

	/*
	 * Creates the panel of partner fields.
	 * 
	 * @return the panel of partner fields.
	 */
	private VerticalPanel createContent() {
		CSS.ensureInjected();
		final VerticalPanel form = new VerticalPanel();
		final Label title = new Label(CONSTANTS.partnerLabel());
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);

		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				PartnerPopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);

		//-----------------------------------------------
		// Party list
		//-----------------------------------------------
		partyField = new ListField(this, null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.partyLabel(),
				true,
				tab++);
		partyField.setType(Party.Type.Organization.name());
		partyField.setHelpText(CONSTANTS.partyHelp());
		form.add(partyField);

		//-----------------------------------------------
		// API Key field
		//-----------------------------------------------
		apikeyField = new TextField(this, null,
				CONSTANTS.apikeyLabel(),
				tab++);
		apikeyField.setMaxLength(255);
		apikeyField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_SCHEDULE));
		apikeyField.setHelpText(CONSTANTS.apikeyHelp());
		form.add(apikeyField);

		//-----------------------------------------------
		// Default Web Address field
		//-----------------------------------------------
		webaddressField = new TextField(this, null,
				CONSTANTS.webaddressLabel(),
				tab++);
		webaddressField.setMaxLength(255);
		webaddressField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_DEFAULT));
		webaddressField.setHelpText(CONSTANTS.webaddressHelp());
		form.add(webaddressField);

		//-----------------------------------------------
		// Check if mandatory feature
		//-----------------------------------------------
		bookofflineField = new CheckField(this, null,
				CONSTANTS.bookofflineLabel(),
				tab++);
		bookofflineField.setDefaultValue(false);
		bookofflineField.setHelpText(CONSTANTS.bookofflineHelp());
		form.add(bookofflineField);
		form.add(bookofflineField);

		//-----------------------------------------------
		// Booking Email Address field
		//-----------------------------------------------
		bookemailaddressField = new TextField(this, null,
				CONSTANTS.bookemailaddressLabel(),
				tab++);
		bookemailaddressField.setMaxLength(255);
		bookemailaddressField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_DEFAULT));
		bookemailaddressField.setVisible(false);
		bookemailaddressField.setHelpText(CONSTANTS.bookemailaddressHelp());
		form.add(bookemailaddressField);
		
		//-----------------------------------------------
		// Booking Web Address field
		//-----------------------------------------------
		bookwebaddressField = new TextField(this, null,
				CONSTANTS.bookwebaddressLabel(),
				tab++);
		bookwebaddressField.setMaxLength(255);
		bookwebaddressField.setDefaultValue(HOSTS.offlineUrl());
		bookwebaddressField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_DEFAULT));
		bookwebaddressField.setVisible(false);
		bookwebaddressField.setHelpText(CONSTANTS.bookwebaddressHelp());
		form.add(bookwebaddressField);
		
		//-----------------------------------------------
		// Currency field
		//-----------------------------------------------
		currencyField = new ListField(this, null,
				new CurrencyNameId(),
				CONSTANTS.currencyLabel(),
				false,
				tab++);
		currencyField.setAllOrganizations(true);
		currencyField.setDefaultValue(AbstractRoot.getCurrency());
		currencyField.setFieldHalf();
		currencyField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_DEFAULT));
		currencyField.setHelpText(CONSTANTS.currencyHelp());

		//-----------------------------------------------
		// Date Format field
		//-----------------------------------------------
		dateformatField = new ListField(this, null,
				NameId.getList(Party.DATE_FORMATS, Party.DATE_FORMATS), //CONSTANTS.dateformats(), CONSTANTS.dateformats()),
				CONSTANTS.dateformatLabel(),
				false,
				tab++);
		dateformatField.setDefaultValue(Party.YYYYMMDD); //ISO
		dateformatField.setFieldHalf();
		dateformatField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_DEFAULT));
		dateformatField.setHelpText(CONSTANTS.dateformatHelp());

		HorizontalPanel cf = new HorizontalPanel();
		form.add(cf);
		cf.add(currencyField);
		cf.add(dateformatField);

		//-----------------------------------------------
		// Commission field
		//-----------------------------------------------
		commissionField = new DoubleField(this, null,
				CONSTANTS.commissionLabel(),
				AbstractField.AF,
				tab++);
		commissionField.setDefaultValue(20.0);
		commissionField.setRange(0.0, 100.0);
		commissionField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_DEFAULT));
		commissionField.setHelpText(CONSTANTS.commissionHelp());

		//-----------------------------------------------
		// Discount field
		//-----------------------------------------------
		discountField = new DoubleField(this, null,
				CONSTANTS.discountLabel(),
				AbstractField.AF,
				tab++);
		discountField.setDefaultValue(20.0);
		discountField.setRange(0.0, 100.0);
		discountField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_DEFAULT));
		discountField.setHelpText(CONSTANTS.discountHelp());

		HorizontalPanel cd = new HorizontalPanel();
		form.add(cd);
		cd.add(commissionField);
		cd.add(discountField);

		//-----------------------------------------------
		// Subscription field
		//-----------------------------------------------
		subscriptionField = new DoubleField(this, null,
				CONSTANTS.subscriptionLabel(),
				AbstractField.IF,
				tab++);
		subscriptionField.setHelpText(CONSTANTS.subscriptionHelp());
		subscriptionField.setDefaultValue(5.0);
		subscriptionField.setRange(0.0, 10000.0);
		subscriptionField.setEnabled(AbstractRoot.getSession().hasPermission(AccessControl.SUPERUSER));
		
		//-----------------------------------------------
		// Transaction field
		//-----------------------------------------------
		transactionField = new DoubleField(this, null,
				CONSTANTS.transactionLabel(),
				AbstractField.AF,
				tab++);
		transactionField.setHelpText(CONSTANTS.transactionHelp());
		transactionField.setDefaultValue(2.0);
		transactionField.setRange(0.0, 100.0);
		transactionField.setEnabled(AbstractRoot.getSession().hasPermission(AccessControl.SUPERUSER));
		
		HorizontalPanel st = new HorizontalPanel();
		form.add(st);
		st.add(subscriptionField);
		st.add(transactionField);

		//-----------------------------------------------
		// Schedule grid
		//-----------------------------------------------
		final Label settingsLabel = new Label(CONSTANTS.settingsLabel());
		settingsLabel.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(settingsLabel);

		Grid grid = new Grid(6,6);
		form.add(grid);

		final Label cronLabel = new Label(CONSTANTS.cronLabel());
		cronLabel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new MessagePanel(Level.VERBOSE, CONSTANTS.cronHelp()).showRelativeTo(cronLabel);
			}
		});
		grid.setWidget(0, 0, cronLabel);
		
		final Label waitLabel = new Label(CONSTANTS.waitLabel());
		cronLabel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new MessagePanel(Level.VERBOSE, CONSTANTS.waitHelp()).showRelativeTo(waitLabel);
			}
		});
		grid.setWidget(0, 1, waitLabel);

		final Label minuteLabel = new Label(CONSTANTS.minuteLabel());
		cronLabel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new MessagePanel(Level.VERBOSE, CONSTANTS.minuteHelp()).showRelativeTo(minuteLabel);
			}
		});
		grid.setWidget(0, 2, minuteLabel);
		
		final Label hourLabel = new Label(CONSTANTS.hourLabel());
		cronLabel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new MessagePanel(Level.VERBOSE, CONSTANTS.hourHelp()).showRelativeTo(hourLabel);
			}
		});
		grid.setWidget(0, 3, hourLabel);
		
		final Label weekLabel = new Label(CONSTANTS.weekLabel());
		cronLabel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new MessagePanel(Level.VERBOSE, CONSTANTS.weekHelp()).showRelativeTo(weekLabel);
			}
		});
		grid.setWidget(0, 4, weekLabel);
		
		final Label monthLabel = new Label(CONSTANTS.monthLabel());
		cronLabel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new MessagePanel(Level.VERBOSE, CONSTANTS.monthHelp()).showRelativeTo(monthLabel);
			}
		});
		grid.setWidget(0, 5, monthLabel);
		
		//-----------------------------------------------
		// Alert Wait field
		//-----------------------------------------------
		alertwaitField = new IntegerField(this, null,
				null,
				tab++);
		alertwaitField.setDefaultValue(1000);
		alertwaitField.setRange(100, 60000);
		alertwaitField.setFieldStyle(CSS.waitStyle());
		alertwaitField.setEnabled(AbstractRoot.hasPermission(AccessControl.SUPERUSER));
		alertwaitField.setHelpText(CONSTANTS.waitHelp());

		//-----------------------------------------------
		// Alert Minute list
		//-----------------------------------------------
		alertminuteField = new SpinnerField(this, null,
				-1,
				59,
				null,
				tab++);
		alertminuteField.setDefaultValue(0);
		alertminuteField.setFieldStyle(CSS.cronStyle());
		alertminuteField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_SCHEDULE));
		alertminuteField.setHelpText(CONSTANTS.alertHelp());

		//-----------------------------------------------
		// Alert Hour list
		//-----------------------------------------------
		alerthourField = new SpinnerField(this, null,
				-1,
				23,
				null,			
				tab++);
		alerthourField.setDefaultValue(0);
		alerthourField.setFieldStyle(CSS.cronStyle());
		alerthourField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_SCHEDULE));
		alerthourField.setHelpText(CONSTANTS.alertHelp());

		//-----------------------------------------------
		// Alert Week list
		//-----------------------------------------------
		alertweekField = new SpinnerField(this, null,
				-1,
				6,
				null,			
				tab++);
		alertweekField.setDefaultValue(0);
		alertweekField.setFieldStyle(CSS.cronStyle());
		alertweekField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_SCHEDULE));
		alertweekField.setHelpText(CONSTANTS.alertHelp());

		//-----------------------------------------------
		// Alert Month list
		//-----------------------------------------------
		alertmonthField = new SpinnerField(this, null,
				-1,
				31,
				null,			
				tab++);
		alertmonthField.setDefaultValue(0);
		alertmonthField.setFieldStyle(CSS.cronStyle());
		alertmonthField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_SCHEDULE));
		alertmonthField.setHelpText(CONSTANTS.alertHelp());

		grid.setWidget(1, 0, new Label(CONSTANTS.alertLabel()));
		grid.setWidget(1, 1, alertwaitField);
		grid.setWidget(1, 2, alertminuteField);
		grid.setWidget(1, 3, alerthourField);
		grid.setWidget(1, 4, alertweekField);
		grid.setWidget(1, 5, alertmonthField);

		//-----------------------------------------------
		// Price Wait field
		//-----------------------------------------------
		pricewaitField = new IntegerField(this, null,
				null,
				tab++);
		pricewaitField.setDefaultValue(1000);
		pricewaitField.setRange(100, 60000);
		pricewaitField.setFieldStyle(CSS.waitStyle());
		pricewaitField.setEnabled(AbstractRoot.hasPermission(AccessControl.SUPERUSER));
		pricewaitField.setHelpText(CONSTANTS.waitHelp());

		//-----------------------------------------------
		// Price Minute list
		//-----------------------------------------------
		priceminuteField = new SpinnerField(this, null,
				-1,
				59,
				null,			
				tab++);
		priceminuteField.setDefaultValue(0);
		priceminuteField.setFieldStyle(CSS.cronStyle());
		priceminuteField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_SCHEDULE));
		priceminuteField.setHelpText(CONSTANTS.priceHelp());

		//-----------------------------------------------
		// Price Hour list
		//-----------------------------------------------
		pricehourField = new SpinnerField(this, null,
				-1,
				23,
				null,			
				tab++);
		pricehourField.setDefaultValue(0);
		pricehourField.setFieldStyle(CSS.cronStyle());
		pricehourField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_SCHEDULE));
		pricehourField.setHelpText(CONSTANTS.priceHelp());

		//-----------------------------------------------
		// Price Week list
		//-----------------------------------------------
		priceweekField = new SpinnerField(this, null,
				-1,
				6,
				null,			
				tab++);
		priceweekField.setDefaultValue(0);
		priceweekField.setFieldStyle(CSS.cronStyle());
		priceweekField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_SCHEDULE));
		priceweekField.setHelpText(CONSTANTS.priceHelp());

		//-----------------------------------------------
		// Price Month list
		//-----------------------------------------------
		pricemonthField = new SpinnerField(this, null,
				-1,
				31,
				null,			
				tab++);
		pricemonthField.setDefaultValue(0);
		pricemonthField.setFieldStyle(CSS.cronStyle());
		pricemonthField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_SCHEDULE));
		pricemonthField.setHelpText(CONSTANTS.priceHelp());

		grid.setWidget(2, 0, new Label(CONSTANTS.priceLabel()));
		grid.setWidget(2, 1, pricewaitField);
		grid.setWidget(2, 2, priceminuteField);
		grid.setWidget(2, 3, pricehourField);
		grid.setWidget(2, 4, priceweekField);
		grid.setWidget(2, 5, pricemonthField);

		//-----------------------------------------------
		// Product Wait field
		//-----------------------------------------------
		productwaitField = new IntegerField(this, null,
				null,
				tab++);
		productwaitField.setDefaultValue(1000);
		productwaitField.setRange(100, 60000);
		productwaitField.setFieldStyle(CSS.waitStyle());
		productwaitField.setEnabled(AbstractRoot.hasPermission(AccessControl.SUPERUSER));
		productwaitField.setHelpText(CONSTANTS.waitHelp());

		//-----------------------------------------------
		// Product Minute list
		//-----------------------------------------------
		productminuteField = new SpinnerField(this, null,
				-1,
				59,
				null,			
				tab++);
		productminuteField.setDefaultValue(0);
		productminuteField.setFieldStyle(CSS.cronStyle());
		productminuteField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_SCHEDULE));
		productminuteField.setHelpText(CONSTANTS.productHelp());

		//-----------------------------------------------
		// Product Hour list
		//-----------------------------------------------
		producthourField = new SpinnerField(this, null,
				-1,
				23,
				null,			
				tab++);
		producthourField.setDefaultValue(0);
		producthourField.setFieldStyle(CSS.cronStyle());
		producthourField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_SCHEDULE));
		producthourField.setHelpText(CONSTANTS.productHelp());

		//-----------------------------------------------
		// Product Week list
		//-----------------------------------------------
		productweekField = new SpinnerField(this, null,
				-1,
				6,
				null,			
				tab++);
		productweekField.setDefaultValue(0);
		productweekField.setFieldStyle(CSS.cronStyle());
		productweekField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_SCHEDULE));
		productweekField.setHelpText(CONSTANTS.productHelp());

		//-----------------------------------------------
		// Product Month list
		//-----------------------------------------------
		productmonthField = new SpinnerField(this, null,
				-1,
				31,
				null,			
				tab++);
		productmonthField.setDefaultValue(0);
		productmonthField.setFieldStyle(CSS.cronStyle());
		productmonthField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_SCHEDULE));
		productmonthField.setHelpText(CONSTANTS.productHelp());

		grid.setWidget(3, 0, new Label(CONSTANTS.productLabel()));
		grid.setWidget(3, 1, productwaitField);
		grid.setWidget(3, 2, productminuteField);
		grid.setWidget(3, 3, producthourField);
		grid.setWidget(3, 4, productweekField);
		grid.setWidget(3, 5, productmonthField);

		//-----------------------------------------------
		// Schedule Wait field
		//-----------------------------------------------
		schedulewaitField = new IntegerField(this, null,
				null,
				tab++);
		schedulewaitField.setDefaultValue(1000);
		schedulewaitField.setRange(100, 60000);
		schedulewaitField.setFieldStyle(CSS.waitStyle());
		schedulewaitField.setEnabled(AbstractRoot.hasPermission(AccessControl.SUPERUSER));
		schedulewaitField.setHelpText(CONSTANTS.waitHelp());

		//-----------------------------------------------
		// Schedule Minute list
		//-----------------------------------------------
		scheduleminuteField = new SpinnerField(this, null,
				-1,
				59,
				null,			
				tab++);
		scheduleminuteField.setDefaultValue(0);
		scheduleminuteField.setFieldStyle(CSS.cronStyle());
		scheduleminuteField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_SCHEDULE));
		scheduleminuteField.setHelpText(CONSTANTS.scheduleHelp());

		//-----------------------------------------------
		// Schedule Hour list
		//-----------------------------------------------
		schedulehourField = new SpinnerField(this, null,
				-1,
				23,
				null,			
				tab++);
		schedulehourField.setDefaultValue(0);
		schedulehourField.setFieldStyle(CSS.cronStyle());
		schedulehourField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_SCHEDULE));
		schedulehourField.setHelpText(CONSTANTS.scheduleHelp());

		//-----------------------------------------------
		// Schedule Week list
		//-----------------------------------------------
		scheduleweekField = new SpinnerField(this, null,
				-1,
				6,
				null,			
				tab++);
		scheduleweekField.setDefaultValue(0);
		scheduleweekField.setFieldStyle(CSS.cronStyle());
		scheduleweekField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_SCHEDULE));
		scheduleweekField.setHelpText(CONSTANTS.scheduleHelp());

		//-----------------------------------------------
		// Schedule Month list
		//-----------------------------------------------
		schedulemonthField = new SpinnerField(this, null,
				-1,
				31,
				null,			
				tab++);
		schedulemonthField.setDefaultValue(0);
		schedulemonthField.setFieldStyle(CSS.cronStyle());
		schedulemonthField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_SCHEDULE));
		schedulemonthField.setHelpText(CONSTANTS.scheduleHelp());

		grid.setWidget(4, 0, new Label(CONSTANTS.scheduleLabel()));
		grid.setWidget(4, 1, schedulewaitField);
		grid.setWidget(4, 2, scheduleminuteField);
		grid.setWidget(4, 3, schedulehourField);
		grid.setWidget(4, 4, scheduleweekField);
		grid.setWidget(4, 5, schedulemonthField);

		//-----------------------------------------------
		// Special Wait field
		//-----------------------------------------------
		specialwaitField = new IntegerField(this, null,
				null,
				tab++);
		specialwaitField.setDefaultValue(1000);
		specialwaitField.setRange(100, 60000);
		specialwaitField.setFieldStyle(CSS.waitStyle());
		specialwaitField.setEnabled(AbstractRoot.hasPermission(AccessControl.SUPERUSER));
		specialwaitField.setHelpText(CONSTANTS.waitHelp());

		//-----------------------------------------------
		// Special Minute list
		//-----------------------------------------------
		specialminuteField = new SpinnerField(this, null,
				-1,
				59,
				null,			
				tab++);
		specialminuteField.setDefaultValue(0);
		specialminuteField.setFieldStyle(CSS.cronStyle());
		specialminuteField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_SCHEDULE));
		specialminuteField.setHelpText(CONSTANTS.specialHelp());

		//-----------------------------------------------
		// Special Hour list
		//-----------------------------------------------
		specialhourField = new SpinnerField(this, null,
				-1,
				23,
				null,			
				tab++);
		specialhourField.setDefaultValue(0);
		specialhourField.setFieldStyle(CSS.cronStyle());
		specialhourField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_SCHEDULE));
		specialhourField.setHelpText(CONSTANTS.specialHelp());

		//-----------------------------------------------
		// Special Week list
		//-----------------------------------------------
		specialweekField = new SpinnerField(this, null,
				-1,
				6,
				null,			
				tab++);
		specialweekField.setDefaultValue(0);
		specialweekField.setFieldStyle(CSS.cronStyle());
		specialweekField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_SCHEDULE));
		specialweekField.setHelpText(CONSTANTS.specialHelp());

		//-----------------------------------------------
		// Special Month list
		//-----------------------------------------------
		specialmonthField = new SpinnerField(this, null,
				-1,
				31,
				null,			
				tab++);
		specialmonthField.setDefaultValue(0);
		specialmonthField.setFieldStyle(CSS.cronStyle());
		specialmonthField.setEnabled(AbstractRoot.hasPermission(AccessControl.PARTNER_SCHEDULE));
		specialmonthField.setHelpText(CONSTANTS.specialHelp());

		grid.setWidget(5, 0, new Label(CONSTANTS.specialLabel()));
		grid.setWidget(5, 1, specialwaitField);
		grid.setWidget(5, 2, specialminuteField);
		grid.setWidget(5, 3, specialhourField);
		grid.setWidget(5, 4, specialweekField);
		grid.setWidget(5, 5, specialmonthField);

		form.add(createCommands());
		onRefresh();
		onReset(Partner.CREATED);
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
		final CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), partnerUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.setTitle(AbstractField.CONSTANTS.helpSave());
		bar.add(saveButton);

		//-----------------------------------------------
		// Restore button
		//-----------------------------------------------
		final CommandButton restoreButton = new CommandButton(this, AbstractField.CONSTANTS.allRestore(), partnerUpdate, tab++);
		restoreButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		restoreButton.setTitle(AbstractField.CONSTANTS.helpRestore());
		bar.add(restoreButton);

		//-----------------------------------------------
		// Suspend button
		//-----------------------------------------------
		final CommandButton suspendButton = new CommandButton(this, AbstractField.CONSTANTS.allSuspend(), partnerUpdate, tab++);
		suspendButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		suspendButton.setTitle(AbstractField.CONSTANTS.helpSuspend());
		bar.add(suspendButton);

		//-----------------------------------------------
		// Delete button
		//-----------------------------------------------
		final CommandButton deleteButton = new CommandButton(this, AbstractField.CONSTANTS.allDelete(), partnerDelete, tab++);
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
		// Transition array that defines the finite state machine
		//-----------------------------------------------
		fsm = new ArrayList<Transition>();
		fsm.add(new Transition(Partner.INITIAL, cancelButton, Partner.CREATED));
		fsm.add(new Transition(Partner.INITIAL, saveButton, Partner.CREATED));
		fsm.add(new Transition(Partner.INITIAL, suspendButton, Partner.SUSPENDED));
		
		fsm.add(new Transition(Partner.CREATED, saveButton, Partner.CREATED));
		fsm.add(new Transition(Partner.CREATED, suspendButton, Partner.SUSPENDED));
		fsm.add(new Transition(Partner.CREATED, deleteButton, Partner.CREATED));

		fsm.add(new Transition(Partner.SUSPENDED, saveButton, Partner.SUSPENDED));
		fsm.add(new Transition(Partner.SUSPENDED, restoreButton, Partner.CREATED));
		fsm.add(new Transition(Partner.SUSPENDED, cancelButton, Partner.SUSPENDED));
		return bar;
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Create Partner
		//-----------------------------------------------
		partnerCreate = new GuardedRequest<Partner>() {
			protected boolean error() {
				return (
						ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), partyField)
				);
			}
			protected void send() {super.send(getValue(new PartnerCreate()));}
			protected void receive(Partner partner){setValue(partner);}
		};

		//-----------------------------------------------
		// Read Partner
		//-----------------------------------------------
		partnerRead = new GuardedRequest<Partner>() {
			protected boolean error() {return noId();}
			protected void send() {super.send(getValue(new PartnerRead()));}
			protected void receive(Partner partner){setValue(partner);}
		};

		//-----------------------------------------------
		// Update Partner
		//-----------------------------------------------
		partnerUpdate = new GuardedRequest<Partner>() {
			protected boolean error() {
				return (
					ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), partyField)
						|| ifMessage(partyField.noValue(), Level.TERSE, CONSTANTS.partyError(), partyField)
						|| ifMessage(currencyField.noValue(), Level.TERSE, CONSTANTS.currencyError(), currencyField)
						|| ifMessage(dateformatField.noValue(), Level.TERSE, CONSTANTS.dateformatError(), dateformatField)
						|| ifMessage(webaddressField.noValue(), Level.TERSE, CONSTANTS.webaddressError(), webaddressField)
						|| ifMessage(bookofflineField.getValue() && (bookemailaddressField.noValue() || !Party.isEmailAddress(bookemailaddressField.getValue())), Level.TERSE, CONSTANTS.bookemailaddressError(), bookemailaddressField)
						|| ifMessage(bookofflineField.getValue() && bookwebaddressField.noValue(), Level.TERSE, CONSTANTS.bookwebaddressError(), bookwebaddressField)
						|| ifMessage(commissionField.noValue(), Level.TERSE, CONSTANTS.commissionError(), commissionField)
						|| ifMessage(discountField.noValue(), Level.TERSE, CONSTANTS.discountError(), discountField)
				);
			}
			protected void send() {super.send(getValue(new PartnerUpdate()));}
			protected void receive(Partner partner) {
				parentTable.execute(true);
				hide();
			}
		};

		//-----------------------------------------------
		// Delete Partner
		//-----------------------------------------------
		partnerDelete = new GuardedRequest<Partner>() {
			protected boolean error() {return ifMessage(noId(), Level.TERSE, AbstractField.CONSTANTS.errId(), partyField);}
			protected void send() {super.send(getValue(new PartnerDelete()));}
			protected void receive(Partner partner) {
				parentTable.execute(true);
				hide();
			}
		};
		
		//-----------------------------------------------
		// Read Party
		//-----------------------------------------------
		partyRead = new GuardedRequest<Party>() {
			protected boolean error() {return partyField.noValue();}
			protected void send() {super.send(new PartyRead(partyField.getValue()));}
			protected void receive(Party party) {
				bookemailaddressField.setValue(party.getEmailaddress());
				webaddressField.setValue(party.getWebaddress());
			}
		};
	}
}
