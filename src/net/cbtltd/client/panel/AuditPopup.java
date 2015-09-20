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
import net.cbtltd.client.field.DateField;
import net.cbtltd.client.field.RatingField;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.form.AccessControl;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.audit.AuditBundle;
import net.cbtltd.client.resource.audit.AuditConstants;
import net.cbtltd.client.resource.audit.AuditCssResource;
import net.cbtltd.shared.Audit;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Value;
import net.cbtltd.shared.audit.AuditCreate;
import net.cbtltd.shared.audit.AuditDelete;
import net.cbtltd.shared.audit.AuditRead;
import net.cbtltd.shared.audit.AuditUpdate;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/** 
 * The Class AuditPopup is to display and enter property audit instances.
 * This includes multiple choice lists for each audited item.
 */
public class AuditPopup
extends AbstractPopup<Audit> {

	private static final AuditConstants CONSTANTS = GWT.create(AuditConstants.class);
	private static final AuditBundle BUNDLE = AuditBundle.INSTANCE;
	private static final AuditCssResource CSS = BUNDLE.css();

	private static GuardedRequest<Audit> auditCreate;
	private static GuardedRequest<Audit> auditRead;
	private static GuardedRequest<Audit> auditUpdate;
	private static GuardedRequest<Audit> auditDelete;

	private static DateField dateField;
	private static SuggestField nameField;
	private static RatingField ratingField;
	private static TextAreaField notesField;
	private static TableField<Audit> parentTable;	
	
	private static String id;
	private static boolean noId() {return id == null || id.isEmpty();}
	private static String productid;
	private static boolean noProductid() {return productid == null || productid.isEmpty();}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue()
	 */
	@Override
	public Audit getValue() {return getValue(new Audit());}

	/** Instantiates a new audit pop up panel. */
	public AuditPopup() {
		super(false);
		AbstractField.CSS.ensureInjected();
		CSS.ensureInjected();
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
		createActions();
		setWidget(createContent());
	}

	private static AuditPopup instance;
	
	/**
	 * Gets the single instance of AuditPopup.
	 *
	 * @return single instance of AuditPopup
	 */
	public static synchronized AuditPopup getInstance() {
		if (instance == null) {instance = new AuditPopup();}
		return instance;
	}

	/**
	 * Shows the panel for a new audit of the property to be audited.
	 *
	 * @param productid the ID of the property to be audited.
	 * @param parentTable the table from which the panel was invoked.
	 */
	public void show(String productid, TableField<Audit> parentTable) {
		AuditPopup.id = null;
		AuditPopup.productid = productid;
		AuditPopup.parentTable = parentTable;
		onReset(Audit.INITIAL);
		auditCreate.execute(true);
	}

	/**
	 * Shows the panel for an existing audit.
	 *
	 * @param audit the audit to be displayed.
	 * @param parentTable the table from which the panel was invoked.
	 */
	public void show(Audit audit, TableField<Audit> parentTable) {
		AuditPopup.id = audit.getId();
		AuditPopup.productid = audit.getProductid();
		AuditPopup.parentTable = parentTable;
		auditRead.execute(true);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue(java.lang.Object)
	 */
	private Audit getValue(Audit audit) {
		audit.setState(state);
		audit.setOrganizationid(AbstractRoot.getOrganizationid());
		audit.setId(id);
		audit.setProductid(productid);
		audit.setName(nameField.getValue());
		audit.setDate(dateField.getValue());
		audit.setRating(ratingField.getValue());
		audit.setNotes(notesField.getValue());
		Log.debug("getValue " + audit);
		return audit;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(Audit audit) {
		Log.debug("setValue " + audit);
		if (audit == null) {onReset(Audit.INITIAL);}
		else {
			setResetting(true);
			onStateChange(audit.getState());
			id = audit.getId();
			productid = audit.getProductid();
			nameField.setValue(audit.getName()); 
			dateField.setValue(audit.getDate());
			ratingField.setValue(audit.getRating());
			notesField.setValue(audit.getNotes());
			setResetting(false);
		}	
		center();
	}

	/*
	 * Creates the panel of audit fields.
	 * 
	 * @return the panel of audit fields.
	 */
	public VerticalPanel createContent() {
		final VerticalPanel panel = new VerticalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		add(panel);
		final Label auditLabel = new Label(CONSTANTS.auditLabel());
		auditLabel.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		panel.add(auditLabel);
		
		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		panel.add(closeButton);

		final HorizontalPanel content = new HorizontalPanel();
		content.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.add(content);

		final VerticalPanel form = new VerticalPanel();
		content.add(form);
		
		//-----------------------------------------------
		// Audit Date field
		//-----------------------------------------------
		dateField = new DateField(this,  null,
				CONSTANTS.dateLabel(),
				tab++);
		dateField.setHelpText(CONSTANTS.dateHelp());
		form.add(dateField);

		//-----------------------------------------------
		// Audit Name field
		//-----------------------------------------------
		nameField = new SuggestField(this, null,
				new NameIdAction(Service.ATTRIBUTE),
				CONSTANTS.nameLabel(),
				20,
				tab++);
		nameField.setState(AbstractRoot.getOrganizationid());
		nameField.setType(Value.Type.AuditName.name());
		nameField.setHelpText(CONSTANTS.nameHelp());
		
		final Image nameButton = new Image(AbstractField.BUNDLE.plus());
		nameButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (AbstractRoot.noOrganizationid()) {AbstractField.addMessage(Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), nameField);}
				else {ValuePopup.getInstance().show(Value.Type.AuditName, CONSTANTS.nameLabel(), AbstractRoot.getOrganizationid(), nameField);}
			}
		});
		nameButton.setTitle(CONSTANTS.namebuttonHelp());
		nameField.addButton(nameButton);
		
		form.add(nameField);

		//-----------------------------------------------
		// Rating range with one anchor slider
		//-----------------------------------------------
		ratingField = new RatingField(this, null,
				CONSTANTS.ratingLabel(),
				10,
				tab++) {
		};
		ratingField.setLo(CONSTANTS.ratingLo());
		ratingField.setHi(CONSTANTS.ratingHi());
		ratingField.setDefaultValue(5);
		ratingField.setHelpText(CONSTANTS.ratingHelp());
		form.add(ratingField);

		//-----------------------------------------------
		// Comment field
		//-----------------------------------------------
		notesField = new TextAreaField(this,  null,
				CONSTANTS.notesLabel(),
				tab++);
		notesField.setFieldStyle(CSS.notesField());
		notesField.setMaxLength(255);
		notesField.setHelpText(CONSTANTS.notesHelp());
		form.add(notesField);

		form.add(createCommands());

		onRefresh();
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
		bar.addStyleName(CSS.commandBar());

		LocalRequest cancelRequest = new LocalRequest() {protected void perform() {hide();;}};
		
		//-----------------------------------------------
		// Save button
		//-----------------------------------------------
		final CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), auditUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.setTitle(CONSTANTS.saveHelp());
		bar.add(saveButton);

		//-----------------------------------------------
		// Delete button
		//-----------------------------------------------
		final CommandButton deleteButton = new CommandButton(this, AccessControl.DELETE_PERMISSION, AbstractField.CONSTANTS.allDelete(), auditDelete, tab++);
		deleteButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		deleteButton.setTitle(AbstractField.CONSTANTS.helpDelete());
		bar.add(deleteButton);

		//-----------------------------------------------
		// Cancel button
		//-----------------------------------------------
		final CommandButton cancelButton = new CommandButton(this, AbstractField.CONSTANTS.allCancel(), cancelRequest, tab++);
		cancelButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		cancelButton.setTitle(CONSTANTS.cancelHelp());
		bar.add(cancelButton);

		//-----------------------------------------------
		// Transition array to define the finite state machine
		//-----------------------------------------------
		fsm = new ArrayList<Transition>();
		fsm.add(new Transition(Audit.INITIAL, saveButton, Audit.CREATED));
		fsm.add(new Transition(Audit.INITIAL, cancelButton, Audit.CREATED));

		fsm.add(new Transition(Audit.CREATED, saveButton, Audit.CREATED));
		fsm.add(new Transition(Audit.CREATED, deleteButton, Audit.FINAL));
		return bar;
	}
	
	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Create Audit
		//-----------------------------------------------
		auditCreate = new GuardedRequest<Audit>() {
			protected boolean error() {return noProductid();}
			protected void send() {super.send(getValue(new AuditCreate()));}
			protected void receive(Audit audit){setValue(audit);}
		};

		//-----------------------------------------------
		// Read Audit
		//-----------------------------------------------
		auditRead = new GuardedRequest<Audit>() {
			protected boolean error() {return noId();}
			protected void send() {super.send(getValue(new AuditRead()));}
			protected void receive(Audit audit) {setValue(audit);}	
		};

		//-----------------------------------------------
		// Update Audit
		//-----------------------------------------------
		auditUpdate = new GuardedRequest<Audit>() {
			protected boolean error() {
				return (
						ifMessage(noId(), Level.ERROR, AbstractField.CONSTANTS.errId(), dateField)
						|| ifMessage(noProductid(), Level.ERROR, CONSTANTS.parentError(), dateField)
						|| ifMessage(dateField.noValue(), Level.ERROR, CONSTANTS.dateError(), dateField)
						|| ifMessage(ratingField.noValue(), Level.ERROR, CONSTANTS.ratingError(), ratingField)
				);
			}
			protected void send() {super.send(getValue(new AuditUpdate()));}
			protected void receive(Audit audit){
				parentTable.execute(true);
				hide();
			}
		};

		//-----------------------------------------------
		// Delete Audit 
		//-----------------------------------------------
		auditDelete = new GuardedRequest<Audit>() {
			protected boolean error() {return noId();}
			protected void send() {super.send(getValue(new AuditDelete()));}
			protected void receive(Audit audit){setValue(audit);}	
		};
	}
}

