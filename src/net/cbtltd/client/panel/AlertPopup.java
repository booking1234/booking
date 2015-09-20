/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.panel;

import java.util.ArrayList;

import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.LocalRequest;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.CommandButton;
import net.cbtltd.client.field.DatespanField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.alert.AlertConstants;
import net.cbtltd.shared.Alert;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.alert.AlertCreate;
import net.cbtltd.shared.alert.AlertDelete;
import net.cbtltd.shared.alert.AlertRead;
import net.cbtltd.shared.alert.AlertUpdate;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * The Class AlertPopup is to display and change alert instances.
 * The location ID of the alert is the property ID where the alert is used.
 * The owner of the alert defaults to the owner of the property but can be changed.
 * An alert can be depreciated according to its depreciation type.
 */
public class AlertPopup
extends AbstractPopup<Alert> {

	private static final AlertConstants CONSTANTS = GWT.create(AlertConstants.class);
//	private static final AlertBundle BUNDLE = AlertBundle.INSTANCE;
//	private static final AlertCssResource CSS = BUNDLE.css();

	private static GuardedRequest<Alert> alertCreate;
	private static GuardedRequest<Alert> alertRead;
	private static GuardedRequest<Alert> alertUpdate;
	private static GuardedRequest<Alert> alertDelete;

	private static DatespanField fromtodateField;
	private static TextAreaField nameField;
	private static TableField<Alert> parentTable;	

	private static String id;
	private static boolean noId() {return id == null || id.isEmpty();}
	private static String entitytype;
	private static boolean noEntitytype() {return entitytype == null || entitytype.isEmpty();}
	private static String entityid;
	private static boolean noEntityid() {return entityid == null || entityid.isEmpty();}
	
	/** Instantiates a new alert pop up panel.
	 */
	public AlertPopup() {
		super(true);
		createActions();
		setWidget(createContent());
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
	}

	private static AlertPopup instance;
	
	/**
	 * Gets the single instance of AlertPopup.
	 *
	 * @return single instance of AlertPopup
	 */
	public static synchronized AlertPopup getInstance() {
		if (instance == null) {instance = new AlertPopup();}
		id = null;
		entityid = null;
		nameField.setValue(Model.BLANK);
		fromtodateField.setTovalue(null);
		return instance;
	}

	/**
	 * Shows the panel for a new alert for the specified entity.
	 *
	 * @param entitytype the type of entity being alerted.
	 * @param entityid the ID of the entity being alerted.
	 * @param parentTable the parent table.
	 */
	public void show(String entitytype, String entityid, TableField<Alert> parentTable) {
		AlertPopup.entitytype = entitytype;
		AlertPopup.entityid = entityid;
		AlertPopup.parentTable = parentTable;
		alertCreate.execute(true);
	}

	/**
	 * Shows the panel for the specified alert.
	 *
	 * @param alert the specified alert.
	 * @param parentTable the parent table.
	 */
	public void show(Alert alert, TableField<Alert> parentTable) {
		AlertPopup.parentTable = parentTable;
		AlertPopup.id = alert.getId();
		AlertPopup.entitytype = alert.getEntitytype();
		AlertPopup.entityid = alert.getEntityid();
		alertRead.execute(true);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue()
	 */
	@Override
	public Alert getValue() {return getValue(new Alert());}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue(java.lang.Object)
	 */
	private Alert getValue(Alert alert) {
		alert.setId(id);
		alert.setEntitytype(entitytype);
		alert.setEntityid(entityid);
		alert.setName(nameField.getValue());
		alert.setFromdate(fromtodateField.getValue());
		alert.setTodate(fromtodateField.getTovalue());
		Log.debug("getValue " + alert);
		return alert;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(Alert alert) {
		Log.debug("setValue " + alert);
		if (alert != null) {
			setResetting(true);
			id = alert.getId();
			entitytype = alert.getEntitytype();
			entityid = alert.getEntityid();
			nameField.setValue(alert.getName());
			fromtodateField.setValue(alert.getFromdate());
			fromtodateField.setTovalue(alert.getTodate());
			center();
			setResetting(false);
		}
	}

	/*
	 * Creates the panel of alert fields.
	 * 
	 * @return the panel of alert fields.
	 */
	private VerticalPanel createContent() {
		final VerticalPanel form = new VerticalPanel();
		final Label title = new Label(CONSTANTS.alertLabel());
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);

		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				AlertPopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);

		//-----------------------------------------------
		// Date span field
		//-----------------------------------------------
		fromtodateField = new DatespanField(this, null,
				CONSTANTS.fromtodateLabel(),
				tab++);
		fromtodateField.setTovalue(null);
		fromtodateField.setHelpText(CONSTANTS.fromtodateHelp());
		form.add(fromtodateField);

		//-----------------------------------------------
		// Name field
		//-----------------------------------------------
		nameField = new TextAreaField(this, null,
				CONSTANTS.nameLabel(),
				//				new LanguageNameId(),
				tab++);
		nameField.setMaxLength(254);
		nameField.setHelpText(CONSTANTS.nameHelp());
		form.add(nameField);

		form.add(createCommands());
		onRefresh();
		onReset(Alert.CREATED);
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
		final CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), alertUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.setTitle(AbstractField.CONSTANTS.helpSave());
		bar.add(saveButton);

		//-----------------------------------------------
		// Delete button
		//-----------------------------------------------
		final CommandButton deleteButton = new CommandButton(this, AbstractField.CONSTANTS.allDelete(), alertDelete, tab++);
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
		// Transition array that defines the finite state machine
		//-----------------------------------------------
		fsm = new ArrayList<Transition>();
		fsm.add(new Transition(Alert.INITIAL, cancelButton, Alert.CREATED));
		fsm.add(new Transition(Alert.INITIAL, saveButton, Alert.CREATED));
		
		fsm.add(new Transition(Alert.CREATED, saveButton, Alert.CREATED));
		fsm.add(new Transition(Alert.CREATED, deleteButton, Alert.CREATED));
		return bar;
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Create Alert
		//-----------------------------------------------
		alertCreate = new GuardedRequest<Alert>() {
			protected boolean error() {
				return (
						ifMessage(noEntitytype(), Level.TERSE, CONSTANTS.entityError(), nameField)
						|| ifMessage(noEntityid(), Level.TERSE, CONSTANTS.entityError(), nameField)
				);
			}
			protected void send() {super.send(getValue(new AlertCreate()));}
			protected void receive(Alert alert){setValue(alert);}
		};

		//-----------------------------------------------
		// Read Alert
		//-----------------------------------------------
		alertRead = new GuardedRequest<Alert>() {
			protected boolean error() {return noId();}
			protected void send() {super.send(getValue(new AlertRead()));}
			protected void receive(Alert alert){setValue(alert);}
		};

		//-----------------------------------------------
		// Update Alert
		//-----------------------------------------------
		alertUpdate = new GuardedRequest<Alert>() {
			protected boolean error() {
				return (
						ifMessage(noEntitytype(), Level.TERSE, CONSTANTS.entityError(), nameField)
						|| ifMessage(noEntityid(), Level.TERSE, CONSTANTS.entityError(), nameField)
						|| ifMessage(nameField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), nameField)
						|| ifMessage(fromtodateField.noValue(), Level.TERSE, CONSTANTS.fromtodateError(), fromtodateField)
				);
			}
			protected void send() {super.send(getValue(new AlertUpdate()));}
			protected void receive(Alert alert) {
				parentTable.execute(true);
				hide();
			}
		};

		//-----------------------------------------------
		// Delete Alert
		//-----------------------------------------------
		alertDelete = new GuardedRequest<Alert>() {
			protected boolean error() {return ifMessage(noId(), Level.TERSE, AbstractField.CONSTANTS.errId(), nameField);}
			protected void send() {super.send(getValue(new AlertDelete()));}
			protected void receive(Alert alert) {
				parentTable.execute(true);
				hide();
			}
		};
	}
}
