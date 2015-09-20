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
import net.cbtltd.client.field.CommandButton;
import net.cbtltd.client.field.DatespanField;
import net.cbtltd.client.field.DoubleField;
import net.cbtltd.client.field.IntegerField;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.form.AccessControl;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.license.LicenseConstants;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.license.LicenseCreate;
import net.cbtltd.shared.license.LicenseDelete;
import net.cbtltd.shared.license.LicenseRead;
import net.cbtltd.shared.license.LicenseUpdate;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * The Class LicensePopup is to display and change license instances.
 * The location ID of the license is the property ID where the license is used.
 * The owner of the license defaults to the owner of the property but can be changed.
 * An license can be depreciated according to its depreciation type.
 */
public class LicensePopup
extends AbstractPopup<License> {

	private static final LicenseConstants CONSTANTS = GWT.create(LicenseConstants.class);
//	private static final LicenseBundle BUNDLE = LicenseBundle.INSTANCE;
//	private static final LicenseCssResource CSS = BUNDLE.css();

	private static GuardedRequest<License> licenseCreate;
	private static GuardedRequest<License> licenseRead;
	private static GuardedRequest<License> licenseUpdate;
	private static GuardedRequest<License> licenseDelete;

	private static SuggestField downstreamidField;
	private static ListField productField;
	private static ListField typeField;
	private static IntegerField waitField;
	private static DatespanField fromtodateField;
	private static DoubleField subscriptionField;
	private static DoubleField transactionField;
	private static DoubleField upstreamField;
	private static DoubleField downstreamField;
	private static TextAreaField notesField;
	private static TableField<License> parentTable;	

	private static String id;
	private static boolean noId() {return id == null || id.isEmpty();}
	
	/** Instantiates a new license pop up panel.
	 */
	public LicensePopup() {
		super(true);
		createActions();
		setWidget(createContent());
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
	}

	private static LicensePopup instance;
	
	/**
	 * Gets the single instance of LicensePopup.
	 *
	 * @return single instance of LicensePopup
	 */
	public static synchronized LicensePopup getInstance() {
		if (instance == null) {instance = new LicensePopup();}
		id = null;
		notesField.setValue(Model.BLANK);
		fromtodateField.setTovalue(null);
		return instance;
	}

	/**
	 * Shows the panel for a new license for the specified entity.
	 *
	 * @param entitytype the type of entity being licenseed.
	 * @param entityid the ID of the entity being licenseed.
	 * @param parentTable the parent table.
	 */
	public void show(TableField<License> parentTable) {
		LicensePopup.parentTable = parentTable;
		licenseCreate.execute(true);
	}

	/**
	 * Shows the panel for the specified license.
	 *
	 * @param license the specified license.
	 * @param parentTable the parent table.
	 */
	public void show(License license, TableField<License> parentTable) {
		LicensePopup.parentTable = parentTable;
		LicensePopup.id = license.getId();
		licenseRead.execute(true);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue()
	 */
	@Override
	public License getValue() {return getValue(new License());}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue(java.lang.Object)
	 */
	private License getValue(License license) {
		license.setState(state);
		license.setId(id);
		license.setOrganizationid(AbstractRoot.getOrganizationid());
		license.setActorid(AbstractRoot.getActorid());
		license.setId(id);
		license.setUpstreamid(AbstractRoot.getOrganizationid());
		license.setDownstreamid(downstreamidField.getValue());
		license.setProductid(productField.getValue()); //TODO: set null for zero???
		license.setType(typeField.getValue());
		license.setWait(waitField.getValue());
		license.setFromdate(fromtodateField.getValue());
		license.setTodate(fromtodateField.getTovalue());
		license.setSubscription(subscriptionField.getValue());
		license.setTransaction(transactionField.getValue());
		license.setUpstream(upstreamField.getValue());
		license.setDownstream(downstreamField.getValue());
		license.setNotes(notesField.getValue());
		Log.debug("getValue " + license);
		return license;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(License license) {
		Log.debug("setValue " + license);
		if (license != null) {
			setResetting(true);
			onStateChange(license.getState());
			id = license.getId();
			downstreamidField.setValue(license.getDownstreamid());
			productField.setValue(license.getProductid());
			typeField.setValue(license.getType());
			waitField.setValue(license.getWait());
			fromtodateField.setValue(license.getFromdate());
			fromtodateField.setTovalue(license.getTodate());
			subscriptionField.setValue(license.getSubscription());
			transactionField.setValue(license.getTransaction());
			upstreamField.setValue(license.getUpstream());
			downstreamField.setValue(license.getDownstream());
			notesField.setValue(license.getNotes());
			center();
			setResetting(false);
		}
	}

	/*
	 * Creates the panel of license fields.
	 * 
	 * @return the panel of license fields.
	 */
	private VerticalPanel createContent() {
		final VerticalPanel form = new VerticalPanel();
		final Label title = new Label(CONSTANTS.licenseLabel());
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);

		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				LicensePopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);

		//-----------------------------------------------
		// Agent field
		//-----------------------------------------------
		downstreamidField = new SuggestField(this, null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.agentLabel(),
				20,
				tab++);
		downstreamidField.setType(Party.Type.Agent.name());
		downstreamidField.setHelpText(CONSTANTS.agentHelp());
		form.add(downstreamidField);

		//-----------------------------------------------
		// Product field
		//-----------------------------------------------
		productField = new ListField(this, null,
				new NameIdAction(Service.PRODUCT),
				CONSTANTS.productLabel(),
				true,
				tab++);
		productField.setType(Product.Type.Accommodation.name());
		productField.setHelpText(CONSTANTS.productHelp());
		form.add(productField);
		
		//-----------------------------------------------
		// Type field
		//-----------------------------------------------
		typeField = new ListField(this, null,
				NameId.getList(CONSTANTS.types(), License.TYPES),
				CONSTANTS.typeLabel(),
				false,
				tab++);
		typeField.setDefaultValue(License.Type.All.name());
		typeField.setFieldHalf();
		typeField.setHelpText(CONSTANTS.typeHelp());
		
		//-----------------------------------------------
		// Alert Wait field
		//-----------------------------------------------
		waitField = new IntegerField(this, null,
				CONSTANTS.waitLabel(),
				tab++);
		waitField.setDefaultValue(1000);
		waitField.setRange(0, 60000);
		waitField.setEnabled(AbstractRoot.hasPermission(AccessControl.SUPERUSER));
		waitField.setHelpText(CONSTANTS.waitHelp());

		HorizontalPanel ts = new HorizontalPanel();
		form.add(ts);
		ts.add(typeField);
		ts.add(waitField);
		
		//-----------------------------------------------
		// Date span field
		//-----------------------------------------------
		fromtodateField = new DatespanField(this, null,
				CONSTANTS.fromtodateLabel(),
				tab++);
		fromtodateField.setHelpText(CONSTANTS.fromtodateHelp());
		form.add(fromtodateField);

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
		subscriptionField.setEnabled(AbstractRoot.getSession().hasPermission(AccessControl.LICENSE));
		
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
		transactionField.setEnabled(AbstractRoot.getSession().hasPermission(AccessControl.LICENSE));
		
		HorizontalPanel st = new HorizontalPanel();
		form.add(st);
		st.add(subscriptionField);
		st.add(transactionField);

		//-----------------------------------------------
		// Upstream field
		//-----------------------------------------------
		upstreamField = new DoubleField(this, null,
				CONSTANTS.upstreamLabel(),
				AbstractField.AF,
				tab++);
		upstreamField.setHelpText(CONSTANTS.upstreamHelp());
		upstreamField.setDefaultValue(0.0);
		upstreamField.setRange(0.0, 100.0);
		upstreamField.setEnabled(AbstractRoot.getSession().hasPermission(AccessControl.UPSTREAM));
		
		//-----------------------------------------------
		// Transaction field
		//-----------------------------------------------
		downstreamField = new DoubleField(this, null,
				CONSTANTS.downstreamLabel(),
				AbstractField.AF,
				tab++);
		downstreamField.setHelpText(CONSTANTS.downstreamHelp());
		downstreamField.setDefaultValue(0.0);
		downstreamField.setRange(0.0, 100.0);
		downstreamField.setEnabled(AbstractRoot.getSession().hasPermission(AccessControl.DOWNSTREAM));
		
		HorizontalPanel ud = new HorizontalPanel();
		form.add(ud);
		ud.add(upstreamField);
		ud.add(downstreamField);

		//-----------------------------------------------
		// Notes field
		//-----------------------------------------------
		notesField = new TextAreaField(this, null,
				CONSTANTS.notesLabel(),
				//				new LanguageNameId(),
				tab++);
		notesField.setMaxLength(255);
		notesField.setHelpText(CONSTANTS.notesHelp());
		form.add(notesField);

		form.add(createCommands());
		onRefresh();
		onReset(License.CREATED);
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
		final CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), licenseUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.setTitle(AbstractField.CONSTANTS.helpSave());
		bar.add(saveButton);

		//-----------------------------------------------
		// Restore button
		//-----------------------------------------------
		final CommandButton restoreButton = new CommandButton(this, AbstractField.CONSTANTS.allRestore(), licenseUpdate, tab++);
		restoreButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		restoreButton.setTitle(AbstractField.CONSTANTS.helpRestore());
		bar.add(restoreButton);

		//-----------------------------------------------
		// Suspend button
		//-----------------------------------------------
		final CommandButton suspendButton = new CommandButton(this, AbstractField.CONSTANTS.allSuspend(), licenseUpdate, tab++);
		suspendButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		suspendButton.setTitle(AbstractField.CONSTANTS.helpSuspend());
		bar.add(suspendButton);

		//-----------------------------------------------
		// Delete button
		//-----------------------------------------------
		final CommandButton deleteButton = new CommandButton(this, AbstractField.CONSTANTS.allDelete(), licenseDelete, tab++);
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
		fsm.add(new Transition(License.INITIAL, cancelButton, License.CREATED));
		fsm.add(new Transition(License.INITIAL, saveButton, License.CREATED));
		fsm.add(new Transition(License.INITIAL, suspendButton, License.SUSPENDED));
		
		fsm.add(new Transition(License.CREATED, saveButton, License.CREATED));
		fsm.add(new Transition(License.CREATED, suspendButton, License.SUSPENDED));
		fsm.add(new Transition(License.CREATED, deleteButton, License.CREATED));
		
		fsm.add(new Transition(License.SUSPENDED, saveButton, License.SUSPENDED));
		fsm.add(new Transition(License.SUSPENDED, restoreButton, License.CREATED));
		fsm.add(new Transition(License.SUSPENDED, cancelButton, License.SUSPENDED));
		return bar;
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Create License
		//-----------------------------------------------
		licenseCreate = new GuardedRequest<License>() {
			protected boolean error() {
				return (
						ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), downstreamidField)
				);
			}
			protected void send() {super.send(getValue(new LicenseCreate()));}
			protected void receive(License license){setValue(license);}
		};

		//-----------------------------------------------
		// Read License
		//-----------------------------------------------
		licenseRead = new GuardedRequest<License>() {
			protected boolean error() {return noId();}
			protected void send() {super.send(getValue(new LicenseRead()));}
			protected void receive(License license){setValue(license);}
		};

		//-----------------------------------------------
		// Update License
		//-----------------------------------------------
		licenseUpdate = new GuardedRequest<License>() {
			protected boolean error() {
				return (
						ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), downstreamidField)
						|| ifMessage(waitField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errState(), waitField)
						|| ifMessage(typeField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errType(), typeField)
						|| ifMessage(fromtodateField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.noTovalue(), Level.TERSE, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.isEndBeforeStart(), Level.TERSE, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
						|| ifMessage(transactionField.noValue(), Level.TERSE, CONSTANTS.transactionError(), transactionField)
				);
			}
			protected void send() {super.send(getValue(new LicenseUpdate()));}
			protected void receive(License license) {
				parentTable.execute(true);
				hide();
			}
		};

		//-----------------------------------------------
		// Delete License
		//-----------------------------------------------
		licenseDelete = new GuardedRequest<License>() {
			protected boolean error() {return ifMessage(noId(), Level.TERSE, AbstractField.CONSTANTS.errId(), notesField);}
			protected void send() {super.send(getValue(new LicenseDelete()));}
			protected void receive(License license) {
				parentTable.execute(true);
				hide();
			}
		};
	}
}
