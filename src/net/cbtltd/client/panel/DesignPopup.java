/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.panel;

import java.util.Date;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.DatespanField;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.ReportButton;
import net.cbtltd.client.field.ShuttleField;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.SuggestspanField;
import net.cbtltd.client.resource.report.ReportBundle;
import net.cbtltd.client.resource.report.ReportConstants;
import net.cbtltd.client.resource.report.ReportCssResource;
import net.cbtltd.shared.Design;
import net.cbtltd.shared.Event;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.NameIdState;
import net.cbtltd.shared.NameIdType;
import net.cbtltd.shared.Report;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.design.DesignRead;
import net.cbtltd.shared.journal.JournalCurrency;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/** The Class DesignPopup is to generate and display report instances. */
public class DesignPopup
extends AbstractPopup<Design> {

	private static final ReportConstants CONSTANTS = GWT.create(ReportConstants.class);
	private static final ReportBundle BUNDLE = ReportBundle.INSTANCE;
	private static final ReportCssResource CSS = BUNDLE.css();

	/* The action to get a report design from the server. */
	private GuardedRequest<Design> designRead = new GuardedRequest<Design>() {
		protected boolean error() {return designField.noValue();}
		protected void send() {super.send(getValue(new DesignRead()));}
		protected void receive(Design design){setValue(design);}
	};

	private static ListField designField;
	private static SuggestspanField namerangeField;
	private static ListField processField;
	private static SuggestField accountField;
	private static SuggestField entityField;
	private static DatespanField fromtodateField;
	private static ShuttleField statesField;
	private static ShuttleField typesField;
	private static ListField currencyField;
	private static ListField formatField;
	private static ReportButton reportButton;
	
	/** Instantiates a new report pop up panel. */
	public DesignPopup() {
		super(true);
		setWidget(createContent());
		CSS.ensureInjected();
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
	}
	
	private static DesignPopup instance;
	
	/**
	 * Gets the single instance of DesignPopup.
	 *
	 * @return single instance of DesignPopup
	 */
	public static synchronized DesignPopup getInstance() {
		if (instance == null) {instance = new DesignPopup();}
		return instance;
	}
	
	/**
	 * Shows the panel for the specified report design.
	 *
	 * @param report the specified report design.
	 */
	public void show(String designid) {
		onReset(Design.CREATED);
		designField.setValue(designid);
		designField.setEnabled(designid == null);
		namerangeField.setVisible(false);
		namerangeField.setLabel(CONSTANTS.reportRange());
		fromtodateField.setVisible(false);
		accountField.setVisible(false);
		processField.setVisible(false);
		entityField.setVisible(false);
		currencyField.setVisible(false);
		statesField.setVisible(false);
		typesField.setVisible(false);
		designRead.execute();
		center();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#getValue()
	 */
	@Override
	public Design getValue() {return getValue(new Design());}

	/*
	 * Gets the specified design action with its attribute values set.
	 *
	 * @param party the specified design action.
	 * @return the design action with its attribute values set.
	 */
	private static Design getValue(Design design) {
		design.setOrganizationid(AbstractRoot.getOrganizationid());
		design.setActorid(AbstractRoot.getActorid());
		design.setId(designField.getValue());
		return design;
	}

	/*
	 * Sets the name field action for the report design and enable it if it has a service.
	 * The service defines from which table the name id list to populate the field is to be read, if any.
	 *
	 * @param design the new value
	 */
	public void setValue(Design design) {
		if (design == null) {Log.error("setValue(design) = null");}
		//namerangeField.setEnabled(design.hasService());
		namerangeField.setVisible(design.hasNamelabel());
		namerangeField.setLabel(design.getNamelabel());
		namerangeField.setAction(design.noNamelabel() || design.noService() ? null : new NameIdAction(design.getService(), design.getNametype()));
		fromtodateField.setVisible(design.getDaterangeenabled());
		accountField.setVisible(design.getAccountenabled());
		processField.setVisible(design.getProcessenabled());
		entityField.setVisible(design.hasEntitytype());
		currencyField.setVisible(design.getCurrencyenabled());
		statesField.setVisible(design.getStatesenabled());
		statesField.setAction(new NameIdState(design.getService()));
		typesField.setVisible(design.getTypesenabled());
		typesField.setAction(new NameIdType(design.getService()));
		center();
	}

	/*
	 * Gets the specified report action with its attribute values set.
	 *
	 * @param party the specified report action.
	 * @return the report action with its attribute values set.
	 */
	private Report getValue(Report report) {
		report.setOrganizationid(AbstractRoot.getOrganizationid()); //organizationField.getId());
		report.setActorid(AbstractRoot.getActorid());
		report.setState(state);
		report.setDate(new Date());
		report.setDesign(designField.getValue());
		report.setProcess(processField.hasValue() ? processField.getValue() : null);
		report.setAccountid(accountField.hasValue() ? accountField.getValue() : null);
		report.setEntitytype(entityField.hasValue() ? entityField.getActionService().name() : null);
		report.setEntityid(entityField.hasValue() ? entityField.getValue() : null);
		report.setFromname(namerangeField.isVisible() ? namerangeField.getName() : null);
		report.setToname(namerangeField.isVisible() ? namerangeField.getToname() : null);
		report.setFromdate(fromtodateField.isVisible() ? Time.getDateStart(fromtodateField.getValue()) : null);
		report.setTodate(fromtodateField.isVisible() ? Time.getDateEnd(fromtodateField.getTovalue()) : null);
		report.setStatelist(statesField.getValue());
		report.setTypelist(typesField.getValue());
		report.setCurrency(currencyField.getValue());
		report.setLanguage(AbstractRoot.getLanguage());
		report.setFormat(formatField.getValue());
		Log.debug("getValue " + report);
		return report;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {
		if (designField.sent(change)) {designRead.execute(); return;}
		else if (namerangeField.sent(change)) {namerangeField.setEqual();}
	}
	
	/*
	 * Creates the panel of report fields.
	 * 
	 * @return the panel of report fields.
	 */
	private VerticalPanel createContent() {
		final VerticalPanel form = new VerticalPanel();
		final Label title = new Label(CONSTANTS.designTitle());
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);

		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				DesignPopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);

		//-----------------------------------------------
		// Report Design field
		//-----------------------------------------------
		designField = new ListField(this, null,
				new NameIdAction(Service.REPORT),
				CONSTANTS.designLabel(),
				true,
				tab++);
		designField.setHelpText(CONSTANTS.designHelp());
		form.add(designField);

		//-----------------------------------------------
		// Name Range field
		//-----------------------------------------------
		namerangeField = new SuggestspanField(this, null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.namerangeLabel(),
				20,
				tab++);
		namerangeField.setFieldHalf();
		namerangeField.setTofieldHalf();
		namerangeField.setVisible(false);
		namerangeField.setHelpText(CONSTANTS.namerangeHelp());
		form.add(namerangeField);

		//-----------------------------------------------
		// Process field
		//-----------------------------------------------
		processField = new ListField(this,  null,
				NameId.getList(AbstractField.CONSTANTS.processList(), Event.PROCESSES),
				CONSTANTS.processLabel(),
				CONSTANTS.processEmpty(),
				tab++);
		processField.setVisible(false);
		processField.setHelpText(CONSTANTS.processHelp());
		form.add(processField);

		//-----------------------------------------------
		// Account field
		//-----------------------------------------------
		accountField = new SuggestField(this, null,
				new NameIdAction(Service.ACCOUNT),
				CONSTANTS.accountLabel(),
				20,
				tab++);
		accountField.setVisible(false);
		accountField.setDefaultName(CONSTANTS.accountEmpty());
		accountField.setHelpText(CONSTANTS.accountHelp());
		form.add(accountField);

		//-----------------------------------------------
		// Entity (subaccount) field
		//-----------------------------------------------
		entityField = new SuggestField(this, null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.partyLabel(),
				20,
				tab++);
		entityField.setVisible(false);
		entityField.setDefaultName(CONSTANTS.entityEmpty());
		entityField.setHelpText(CONSTANTS.entityHelp());
		form.add(entityField);

		//-----------------------------------------------
		// From and To Dates
		//-----------------------------------------------
		fromtodateField = new DatespanField(this, null,
				CONSTANTS.fromtodateLabel(),
				tab++);
		fromtodateField.setVisible(false);
		fromtodateField.setHelpText(CONSTANTS.fromtodateHelp());
		form.add(fromtodateField);

		//-----------------------------------------------
		// States field
		//-----------------------------------------------
		statesField = new ShuttleField(this, null,
				new NameIdState(Service.WORKFLOW),
				CONSTANTS.statesLabel(),
				tab++);
		statesField.setVisible(false);
		statesField.setFieldStyle(CSS.statesStyle());
		statesField.setHelpText(CONSTANTS.statesHelp());
		form.add(statesField);

		//-----------------------------------------------
		// Types field
		//-----------------------------------------------
		typesField = new ShuttleField(this, null,
				new NameIdType(Service.ACCOUNT),
				CONSTANTS.typesLabel(),
				tab++);
		typesField.setVisible(false);
		//typesField.setPreselected(true);
		typesField.setFieldStyle(CSS.typesStyle());
		typesField.setHelpText(CONSTANTS.typesHelp());
		form.add(typesField);

		//-----------------------------------------------
		// Currency field
		//-----------------------------------------------
		currencyField = new ListField(this, null,
				new JournalCurrency(),
				CONSTANTS.currencyLabel(),
				false,
				tab++);
		currencyField.setVisible(false);
		currencyField.setDefaultValue(AbstractRoot.getCurrency());
		currencyField.setHelpText(CONSTANTS.currencyHelp());
		form.add(currencyField);

		//-----------------------------------------------
		// Format field
		//-----------------------------------------------
		formatField = new ListField(this, null,
				NameId.getList(CONSTANTS.formats(), Report.FORMATS),
				CONSTANTS.formatLabel(),
				false,
				tab++);
		formatField.setDefaultValue(Report.PDF);
		formatField.setHelpText(CONSTANTS.formatHelp());
		form.add(formatField);

		final HorizontalPanel bar = new HorizontalPanel();
		bar.addStyleName(AbstractField.CSS.cbtAbstractCommand());
		form.add(bar);
		
		//-----------------------------------------------
		// Report button
		//-----------------------------------------------
		reportButton = new ReportButton(this, CONSTANTS.reportButton(), tab++) {
			public Report getReport() {return getValue(new Report());}
		};
		reportButton.setTitle(CONSTANTS.reportHelp());
		bar.add(reportButton);
		
		//-----------------------------------------------
		// Cancel button
		//-----------------------------------------------
		final Button cancelButton = new Button(AbstractField.CONSTANTS.allCancel(), new ClickHandler() {
			public void onClick(ClickEvent event) {DesignPopup.this.hide();}
		});
		cancelButton.addStyleName(AbstractField.CSS.cbtCommandButton());
		cancelButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		cancelButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		bar.add(cancelButton);

		onRefresh();
		onReset(Report.CREATED);
		return form;
	}
}
