/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.panel;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.LocalRequest;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.CommandButton;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.field.VshuttleField;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.contact.ContactBundle;
import net.cbtltd.client.resource.contact.ContactConstants;
import net.cbtltd.client.resource.contact.ContactCssResource;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Task;
import net.cbtltd.shared.task.Contact;
import net.cbtltd.shared.task.Contact.Type;
import net.cbtltd.shared.task.ContactCreate;
import net.cbtltd.shared.task.ContactUpdate;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/** The Class ContactPopup is to display and change contact instances. */
public class ContactPopup 
extends AbstractPopup<Contact> {

	private static final ContactConstants CONSTANTS = GWT.create(ContactConstants.class);
	private static final ContactBundle BUNDLE = ContactBundle.INSTANCE;
	private static final ContactCssResource CSS = BUNDLE.css();
	private static final int ADDRESSEE_ROWS = 20;

	private static GuardedRequest<Contact> contactCreate;
	private static GuardedRequest<Contact> contactUpdate;
	private static GuardedRequest<Contact> contactDelete;

	private static TextBox mediumField;
	private static TextAreaField messageField;
	private static VshuttleField addresseesField;

	private static String id;
	private static String parentid;
	private static NameId.Type parenttype;
	private static TableField<Contact> parentTable;	

	/** Instantiates a new contact pop up panel. */
	public ContactPopup() {
		super(true);
		createActions();
		setWidget(createContent());
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
	}

	private static ContactPopup instance;
	
	/**
	 * Gets the single instance of ContactPopup.
	 *
	 * @return single instance of ContactPopup
	 */
	public static synchronized ContactPopup getInstance() {
		if (instance == null) {instance = new ContactPopup();}
		ContactPopup.parenttype = null;
		ContactPopup.parentid = null;
		ContactPopup.parentTable = null;
		return instance;
	}

	/**
	 * Shows the panel for the specified parent and contact.
	 *
	 * @param parenttype the specified parent type.
	 * @param parentid the specified parent ID.
	 * @param contact the specified contact.
	 * @param parentTable the table that invoked the pop up panel.
	 */
	public void show(NameId.Type parenttype, String parentid, Contact contact, TableField<Contact> parentTable) {
		ContactPopup.parenttype = parenttype;
		ContactPopup.parentid = parentid;
		ContactPopup.parentTable = parentTable;
		setValue(contact);
	}

	/**
	 * Shows the panel for the specified parent.
	 *
	 * @param parenttype the specified parent type.
	 * @param parentid the specified parent ID.
	 * @param parentTable the table that invoked the pop up panel.
	 */
	public void show(NameId.Type parenttype, String parentid, TableField<Contact> parentTable) {
		ContactPopup.parenttype = parenttype;
		ContactPopup.parentid = parentid;
		ContactPopup.parentTable = parentTable;
		onReset(Contact.INITIAL);
		contactCreate.execute(true);
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue()
	 */
	@Override
	public Contact getValue() {return getValue(new Contact());}
	
	private Contact getValue(Contact contact) {
		contact.setState(state);
		contact.setId(id);
		contact.setActivity(parenttype.name());
		contact.setActorid(AbstractRoot.getActorid());
		contact.setDate(new Date());
		contact.setAdressees(addresseesField.getValue());
		contact.setName(addresseesField.getName(45));
		contact.setNotes(messageField.getValue());
		contact.setOrganizationid(AbstractRoot.getOrganizationid());
		contact.setParentid(parentid);
		contact.setProcess(Task.Type.Marketing.name());
		contact.setState(Contact.CREATED);
		contact.setType(mediumField.getValue());
		Log.debug("getValue " + contact);
		return contact;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(Contact contact) {
		//Window.alert("setValue " + contact);
		Log.debug("setValue " + contact);
		onStateChange(contact.getState());
		id = contact.getId();
		mediumField.setValue(contact.getType());
		messageField.setValue(contact.getNotes());
		addresseesField.setValue(contact.getAdressees());
		center();
	}

	/*
	 * Creates the panel of contact fields.
	 * 
	 * @return the panel of contact fields.
	 */
	private VerticalPanel createContent() {
		final VerticalPanel form = new VerticalPanel();
		final Label titleLabel = new Label(CONSTANTS.contactTitle());
		titleLabel.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(titleLabel);
		
		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ContactPopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);

		//-----------------------------------------------
		// Medium field
		//-----------------------------------------------
		form.add(createMedium());

		//-----------------------------------------------
		// Message field
		//-----------------------------------------------
		messageField = new TextAreaField(this, null,
				CONSTANTS.messageLabel(),
				tab++);
		messageField.setMaxLength(1000);
		messageField.setFieldStyle(CSS.messagefieldStyle());
		form.add(messageField);

		//-----------------------------------------------
		// Adressees field
		//-----------------------------------------------
		addresseesField = new VshuttleField(this, null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.addresseesLabel(),
				ADDRESSEE_ROWS,
				tab++);
		addresseesField.setType(Party.Type.Customer.name());
		addresseesField.setSelectAllEnabled(false);
		addresseesField.setAvailableStyle(CSS.addresseesavailableStyle());
		addresseesField.setSelectedStyle(CSS.addresseesselectedStyle());
		
		Image addresseesButton = new Image(AbstractField.BUNDLE.plus());
		addresseesButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PartyPopup.getInstance().show(Party.Type.Customer, null, null);
			}
		});
		addresseesButton.addStyleName(CSS.mediumbuttonStyle());
		addresseesButton.setTitle(CONSTANTS.addresseesHelp());
		addresseesField.addButton(addresseesButton);
		
		form.add(addresseesField);
		
		form.add(createCommands());
		onRefresh();
		onReset(Contact.CREATED);
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
		
		final LocalRequest cancelRequest = new LocalRequest() {protected void perform() {hide();}};

		//-----------------------------------------------
		// Save button
		//-----------------------------------------------
		final CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), contactUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		bar.add(saveButton);
		
		//-----------------------------------------------
		// Delete button
		//-----------------------------------------------
		final CommandButton deleteButton = new CommandButton(this, AbstractField.CONSTANTS.allDelete(), contactDelete, tab++);
		deleteButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		deleteButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		bar.add(deleteButton);
		
		//-----------------------------------------------
		// Cancel button
		//-----------------------------------------------
		final CommandButton cancelButton = new CommandButton(this, AbstractField.CONSTANTS.allCancel(), cancelRequest, tab++);
		cancelButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		cancelButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		bar.add(cancelButton);

		//-----------------------------------------------
		// Transition array to define the finite state machine
		//-----------------------------------------------
		fsm = new ArrayList<Transition>();

		fsm.add(new Transition(Contact.INITIAL, saveButton, Contact.CREATED));
		fsm.add(new Transition(Contact.INITIAL, cancelButton, Contact.CREATED));
		
		fsm.add(new Transition(Contact.CREATED, saveButton, Contact.CREATED));
		fsm.add(new Transition(Contact.CREATED, deleteButton, Contact.FINAL));

		return bar;
	}

	/*
	 * Creates the medium selector.
	 * 
	 * @return the medium selector.
	 */
	private FlowPanel createMedium() {
		
		final FlowPanel panel = new FlowPanel();
		panel.addStyleName(AbstractField.CSS.cbtAbstractField());

		final Label label = new Label(CONSTANTS.mediumLabel());
		label.addStyleName(AbstractField.CSS.cbtTextFieldLabel());
		panel.add(label);
		
		final HorizontalPanel field = new HorizontalPanel();
		field.addStyleName(AbstractField.CSS.cbtTextFieldField());
		panel.add(field);
		
		mediumField = new TextBox();
		mediumField.addStyleName(CSS.mediumfieldStyle());
		field.add(mediumField);

		//-----------------------------------------------
		// Email button
		//-----------------------------------------------
		final Image emailButton = new Image(BUNDLE.email());
		emailButton.addStyleName(CSS.mediumbuttonStyle());
		emailButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				mediumField.setText(Type.Email.name());
			}
		});
		emailButton.setTitle(CONSTANTS.emailHelp());
		field.add(emailButton);

		//-----------------------------------------------
		// Phone button
		//-----------------------------------------------
		final Image phoneButton = new Image(BUNDLE.phone());
		phoneButton.addStyleName(CSS.mediumbuttonStyle());
		phoneButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				mediumField.setText(Type.Phone.name());
			}
		});
		phoneButton.setTitle(CONSTANTS.phoneHelp());
		field.add(phoneButton);

		//-----------------------------------------------
		// SMS button
		//-----------------------------------------------
		final Image smsButton = new Image(BUNDLE.sms());
		smsButton.addStyleName(CSS.mediumbuttonStyle());
		smsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				mediumField.setText(Type.SMS.name());
			}
		});
		smsButton.setTitle(CONSTANTS.smsHelp());
		field.add(smsButton);

		//-----------------------------------------------
		// FaceBook button
		//-----------------------------------------------
		final Image facebookButton = new Image(BUNDLE.facebook());
		facebookButton.addStyleName(CSS.mediumbuttonStyle());
		facebookButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				mediumField.setText(Type.Facebook.name());
			}
		});
		facebookButton.setTitle(CONSTANTS.facebookHelp());
		field.add(facebookButton);

		return panel;
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Create Contact
		//-----------------------------------------------
		contactCreate = new GuardedRequest<Contact>() {
			protected boolean error() {return parenttype == null || parentid == null || parentid.isEmpty();}
			protected void send() {super.send(getValue(new ContactCreate()));}
			protected void receive(Contact contact) {
				setValue(contact);
				//messageField.setText(new Text(Product.getPrivateId(AbstractRoot.getOrganizationid(), task.getProductid()), task.getName(), Text.Type.Contact, Text.Format.Text, new Date(), "", AbstractRoot.getLanguage()));
			}
		};

		//-----------------------------------------------
		// Update Contact
		//-----------------------------------------------
		contactUpdate = new GuardedRequest<Contact>() {
			protected boolean error() {
				return (
						ifMessage(parenttype == null || parentid == null || parentid.isEmpty() || id == null || id.isEmpty(), Level.ERROR, AbstractField.CONSTANTS.errId(), mediumField)
						|| ifMessage(mediumField.getText() == null || mediumField.getText().isEmpty(), Level.ERROR, CONSTANTS.notesError(), mediumField)
						|| ifMessage(messageField.noValue(), Level.ERROR, CONSTANTS.notesError(), messageField)
				);
			}
			protected void send() {super.send(getValue(new ContactUpdate()));}
			protected void receive(Contact contact) {
				if (parentTable!= null) {parentTable.execute(true);}
				addresseesField.deselect();
				hide();
			}
		};

		//-----------------------------------------------
		// Delete Contact
		//-----------------------------------------------
		contactDelete = new GuardedRequest<Contact>() {
			protected String popup() {return AbstractField.CONSTANTS.errDeleteOK();}
			protected boolean error() {return ifMessage(id == null || id.isEmpty(), Level.TERSE, AbstractField.CONSTANTS.errId(), messageField);}
			protected void send() {super.send(getValue(new ContactUpdate()));}
			protected void receive(Contact contact) {
				if (parentTable!= null) {parentTable.execute(true);}
				hide();
			}
		};
	}
}
