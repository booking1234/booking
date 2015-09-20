/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.panel;

import java.util.ArrayList;

import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.HasValue;
import net.cbtltd.client.LocalRequest;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.CommandButton;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.FieldConstants;
import net.cbtltd.shared.Value;
import net.cbtltd.shared.value.ValueDelete;
import net.cbtltd.shared.value.ValueUpdate;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * The Class ValuePopup is to display and change value instances.
 * Values are ad hoc labels used in several places in the application.
 * For example, a marketing event has a value to indicate what triggered the
 * prospective sale and another value to indicate what the outcome is.
 * Value types are listed in the Value.Type enumeration.
 * @see net.cbtltd.shared.Value
 */
public class ValuePopup
extends AbstractPopup<Value> {

	private static final FieldConstants CONSTANTS = GWT.create(FieldConstants.class);
//	private static final FieldBundle BUNDLE = FieldBundle.INSTANCE;
//	private static final FieldCssResource CSS = BUNDLE.css();

	private static GuardedRequest<Value> valueUpdate;
	private static GuardedRequest<Value> valueDelete;

	private static TextField nameField;

	private static Value.Type key;
	private static boolean noKey() {return key == null;}
	private static String id;
	private static boolean noId() {return id == null || id.isEmpty();}
	private static HasValue<String> parentField; // field that invoked the popup
	
	/** Instantiates a new value pop up panel. */
	public ValuePopup() {
		super(true);
		createActions();
		setWidget(createContent());
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
	}

	private static ValuePopup instance;
	
	/**
	 * Gets the single instance of ValuePopup.
	 *
	 * @return single instance of ValuePopup
	 */
	public static synchronized ValuePopup getInstance() {
		if (instance == null) {instance = new ValuePopup();}
		nameField.setValue(null);
		return instance;
	}

	/**
	 * Shows the panel for the specified value.
	 *
	 * @param key the key of the key-value pair.
	 * @param id the ID of the entity which has the value. 
	 * @param parentField the field that invoked the pop up panel.
	 */
	public void show(Value.Type key, String label, String id, HasValue<String> parentField) {
		ValuePopup.key = key;
		ValuePopup.id = id;
		ValuePopup.parentField = parentField;
		nameField.setLabel(label);
		if (parentField == null || parentField.noValue()) {
			onReset(Value.INITIAL);}
		else {
			onReset(Value.CREATED);
			nameField.setValue(parentField.getValue());
		}
		center();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue()
	 */
	@Override
	public Value getValue() {return getValue(new Value());}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue(java.lang.Object)
	 */
	public Value getValue(Value value) {
		value.setLink(key.name());
		value.setHeadid(id);
		value.setLineid(nameField.getValue());
		Log.debug("getValue " + value);
		return value;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(Value value) {
		Log.debug("setValue " + value);
		if (value == null) {nameField.setValue(null);}
		else {nameField.setValue(value.getLineid());}
		center();
	}

	/*
	 * Creates the panel of value fields.
	 * 
	 * @return the panel of value fields.
	 */
	private VerticalPanel createContent() {
		VerticalPanel form = new VerticalPanel();
		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ValuePopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);

		//-----------------------------------------------
		// Name field
		//-----------------------------------------------
		nameField = new TextField(this, null,
				CONSTANTS.allValue(),
				tab++);
		nameField.setMaxLength(50);
		form.add(nameField);

		form.add(createCommands());
		onRefresh();
//		onReset(Value.INITIAL);
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
		final CommandButton saveButton = new CommandButton(this, CONSTANTS.allSave(), valueUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		saveButton.setTitle(CONSTANTS.helpSave());
		bar.add(saveButton);

		//-----------------------------------------------
		// Delete button
		//-----------------------------------------------
		final CommandButton deleteButton = new CommandButton(this, CONSTANTS.allDelete(), valueDelete, tab++);
		deleteButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		deleteButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		deleteButton.setTitle(CONSTANTS.helpDelete());
		bar.add(deleteButton);

		//-----------------------------------------------
		// Cancel button
		//-----------------------------------------------
		final CommandButton cancelButton = new CommandButton(this, CONSTANTS.allCancel(), cancelRequest, tab++);
		cancelButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		cancelButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		cancelButton.setTitle(CONSTANTS.helpCancel());
		bar.add(cancelButton);

		//-----------------------------------------------
		// Transition array to define the finite state machine.
		//-----------------------------------------------
		fsm = new ArrayList<Transition>();
		fsm.add(new Transition(Value.INITIAL, cancelButton, Value.CREATED));
		fsm.add(new Transition(Value.INITIAL, saveButton, Value.CREATED));
		
//		fsm.add(new Transition(Value.CREATED, cancelButton, Value.CREATED));
		fsm.add(new Transition(Value.CREATED, saveButton, Value.CREATED));
		fsm.add(new Transition(Value.CREATED, deleteButton, Value.CREATED));
		return bar;
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Update Value
		//-----------------------------------------------
		valueUpdate = new GuardedRequest<Value>() {
			protected boolean error() {
				return (
						ifMessage(noKey(), Level.TERSE, AbstractField.CONSTANTS.errId(), nameField)
						|| ifMessage(noId(), Level.TERSE, AbstractField.CONSTANTS.errId(), nameField)
						|| ifMessage(nameField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), nameField)
				);
			}
			protected void send() {super.send(getValue(new ValueUpdate()));}
			protected void receive(Value value) {
				if (parentField != null) {
					parentField.setValue(nameField.getValue());
					parentField.fireChange();
				}
				hide();
			}
		};

		//-----------------------------------------------
		// Delete Value
		//-----------------------------------------------
		valueDelete = new GuardedRequest<Value>() {
			protected String popup() {return AbstractField.CONSTANTS.errDeleteOK();}
			protected boolean error() {return ifMessage(noId(), Level.TERSE, AbstractField.CONSTANTS.errId(), nameField);}
			protected void send() {super.send(getValue(new ValueDelete()));}
			protected void receive(Value value) {
				if (parentField != null) {parentField.setValue(null);}
				hide();
			}
		};
	}
}
