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
import net.cbtltd.client.field.DoubleunitField;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.rule.RuleConstants;
import net.cbtltd.shared.License;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Rule;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.Value;
import net.cbtltd.shared.finance.CurrencyNameId;
import net.cbtltd.shared.rule.RuleCreate;
import net.cbtltd.shared.rule.RuleDelete;
import net.cbtltd.shared.rule.RuleRead;
import net.cbtltd.shared.rule.RuleUpdate;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/** The Class RulePopup is to display and change rules. */
public class RulePopup extends AbstractPopup<Rule> {

	private static final RuleConstants CONSTANTS = GWT.create(RuleConstants.class);
	private static final String[] UNITS = {Unit.EA, Unit.DAY, Unit.WEE, Unit.MON};

	private static GuardedRequest<Rule> ruleCreate;
	private static GuardedRequest<Rule> ruleRead;
	private static GuardedRequest<Rule> ruleUpdate;
	private static GuardedRequest<Rule> ruleDelete;

	private static SuggestField nameField;
	private static DatespanField fromtodateField;
	private static DoubleunitField quantityField;
	private static DoubleunitField valueField;

	private static TableField<Rule> parentTable;
	private static String id;
	private boolean noId() {return id == null || id.isEmpty();}
	private static String modelid;
	private boolean noModelid() {return modelid == null || modelid.isEmpty();}

	/** Instantiates a new rule pop up panel. */
	public RulePopup() {
		super(true);
		createActions();
		setWidget(createContent());
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
	}

	private static RulePopup instance;

	/**
	 * Gets the single instance of RulePopup.
	 * 
	 * @return single instance of RulePopup
	 */
	public static synchronized RulePopup getInstance() {
		if (instance == null) {
			instance = new RulePopup();
		}
		RulePopup.id = null;
		RulePopup.modelid = null;
		RulePopup.parentTable = null;
		return instance;
	}

	/**
	 * Shows the panel for the specified rule.
	 * 
	 * @param rule
	 *            the specified rule.
	 * @param parentTable
	 *            the table that invoked the pop up panel.
	 */
	public void show(Rule rule, TableField<Rule> parentTable) {
		RulePopup.id = rule.getId();
		RulePopup.parentTable = parentTable;
		ruleRead.execute(true);
	}

	/**
	 * Shows the panel for the specified rule type.
	 * 
	 * @param type
	 *            the specified rule type.
	 * @param parentTable
	 *            the table that invoked the pop up panel.
	 */
	public void show(TableField<Rule> parentTable) {
		RulePopup.parentTable = parentTable;
		onReset(Rule.INITIAL);
		RulePopup.valueField.setUnitvalue(AbstractRoot.getCurrency());
		ruleCreate.execute(true);
	}

	/**
	 * Shows the panel for the specified rule type and product.
	 * 
	 * @param type
	 *            the specified rule type.
	 * @param productid
	 *            the ID of the specified product.
	 * @param parentTable
	 *            the table that invoked the pop up panel.
	 */
	public void show(String productid, Integer available, TableField<Rule> parentTable) {
		RulePopup.modelid = productid;
		RulePopup.parentTable = parentTable;
		onReset(Rule.INITIAL);
		ruleCreate.execute(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.cbtltd.client.panel.AbstractPopup#onChange(com.google.gwt.event.dom
	 * .client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue()
	 */
	@Override
	public Rule getValue() {return getValue(new Rule());}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue(java.lang.Object)
	 */
	private Rule getValue(Rule rule) {
		rule.setId(id);
		rule.setModeltype(NameId.Type.Rule.name());
		rule.setModelid(modelid);
		rule.setName(nameField.getValue());
		rule.setFromdate(Time.getDateServer(fromtodateField.getValue()));
		rule.setTodate(Time.getDateServer(fromtodateField.getTovalue()));
		rule.setQuantity(quantityField.getValue());
		rule.setUnit(quantityField.getUnitvalue());
		rule.setValue(valueField.getValue());
		rule.setCurrency(valueField.getUnitvalue());
		Log.debug("getValue " + rule);
		return rule;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(Rule rule) {
		Log.debug("setValue " + rule);
		if (rule == null) {return;}
		else {
			setResetting(true);
			id = rule.getId();
			modelid = rule.getModelid();
			nameField.setValue(rule.getName());
			fromtodateField.setValue(Time.getDateClient(rule.getFromdate()));
			fromtodateField.setTovalue(Time.getDateClient(rule.getTodate()));
			quantityField.setValue(rule.getQuantity());
			quantityField.setUnitvalue(rule.getUnit());
			valueField.setValue(rule.getValue());
			valueField.setUnitvalue(rule.getCurrency());
			center();
			setResetting(false);
		}
	}

	/*
	 * Creates the panel of rule fields.
	 * 
	 * @return the panel of rule fields.
	 */
	private VerticalPanel createContent() {
		final VerticalPanel form = new VerticalPanel();
		final Label title = new Label(CONSTANTS.ruleLabel());
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);

		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RulePopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);

		//-----------------------------------------------
		// Name field
		//-----------------------------------------------
		nameField = new SuggestField(this, null,
				new NameIdAction(Service.ATTRIBUTE),
				CONSTANTS.nameLabel(),
				20,
				tab++);
		nameField.setType(Value.Type.LeaseRule.name());
		nameField.setHelpText(CONSTANTS.nameHelp());

		Image nameButton = new Image(AbstractField.BUNDLE.plus());
		nameButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ValuePopup.getInstance().show(Value.Type.LeaseRule, CONSTANTS.nameLabel(), NameId.Type.Rule.name() + modelid, nameField);
			}
		});
		nameButton.setTitle(CONSTANTS.namebuttonHelp());
		nameField.addButton(nameButton);
		form.add(nameField);

		// -----------------------------------------------
		// Date Range field
		// -----------------------------------------------
		fromtodateField = new DatespanField(this, null,
				CONSTANTS.fromtodateLabel(), tab++);
		fromtodateField.setHelpText(CONSTANTS.fromtodateError());
		fromtodateField.setHelpText(CONSTANTS.fromtodateHelp());
		form.add(fromtodateField);

		// -----------------------------------------------
		// Quantity field
		// -----------------------------------------------
		quantityField = new DoubleunitField(this, null, 
				NameId.getList(CONSTANTS.units(), UNITS),
				CONSTANTS.quantityLabel(), 
				AbstractField.QF, 
				tab++);
		quantityField.setRange(0.0, 1000000.0);
		quantityField.setHelpText(CONSTANTS.quantityHelp());
		form.add(quantityField);

		// -----------------------------------------------
		// Unit Rule field
		// -----------------------------------------------
		valueField = new DoubleunitField(this, null, new CurrencyNameId(),
				CONSTANTS.valueLabel(), AbstractField.AF, tab++);
		valueField.setRange(0.01, 1000000.0);
		valueField.setHelpText(CONSTANTS.valueHelp());
		form.add(valueField);

		form.add(createCommands());

		onRefresh();
		onReset(Rule.CREATED);
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

		final LocalRequest cancelRequest = new LocalRequest() {
			protected void perform() {
				hide();
			}
		};

		// -----------------------------------------------
		// Save button
		// -----------------------------------------------
		final CommandButton saveButton = new CommandButton(this,
				AbstractField.CONSTANTS.allSave(), ruleUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		saveButton.setTitle(CONSTANTS.saveHelp());
		bar.add(saveButton);

		// -----------------------------------------------
		// Delete button
		// -----------------------------------------------
		final CommandButton deleteButton = new CommandButton(this,
				AbstractField.CONSTANTS.allDelete(), ruleDelete, tab++);
		deleteButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		deleteButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		deleteButton.setTitle(CONSTANTS.deleteHelp());
		bar.add(deleteButton);

		// -----------------------------------------------
		// Cancel button
		// -----------------------------------------------
		final CommandButton cancelButton = new CommandButton(this,
				AbstractField.CONSTANTS.allCancel(), cancelRequest, tab++);
		cancelButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		cancelButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		cancelButton.setTitle(AbstractField.CONSTANTS.helpCancel());
		bar.add(cancelButton);

		// -----------------------------------------------
		// Transition array to define the finite state machine
		// -----------------------------------------------
		fsm = new ArrayList<Transition>();

		fsm.add(new Transition(Rule.INITIAL, saveButton, Rule.CREATED));
		fsm.add(new Transition(Rule.INITIAL, cancelButton, Rule.CREATED));

		fsm.add(new Transition(Rule.CREATED, saveButton, Rule.CREATED));
		fsm.add(new Transition(Rule.CREATED, deleteButton, Rule.CREATED));

		return bar;
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		// -----------------------------------------------
		// Create Rule
		// -----------------------------------------------
		ruleCreate = new GuardedRequest<Rule>() {
			protected boolean error() {return noModelid();}
			protected void send() {super.send(getValue(new RuleCreate()));}
			protected void receive(Rule rule) {setValue(rule);}
		};

		// -----------------------------------------------
		// Read Rule
		// -----------------------------------------------
		ruleRead = new GuardedRequest<Rule>() {
			protected boolean error() {
				return (ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE,
						AbstractField.CONSTANTS.errOrganizationid(),
						fromtodateField) || ifMessage(noId(), Level.TERSE,
						AbstractField.CONSTANTS.errId(), fromtodateField));
			}
			protected void send() {super.send(getValue(new RuleRead()));}
			protected void receive(Rule rule) {setValue(rule);}
		};

		// -----------------------------------------------
		// Update Rule
		// -----------------------------------------------
		ruleUpdate = new GuardedRequest<Rule>() {
			protected boolean error() {
				return (ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), fromtodateField)
						|| ifMessage(nameField.noValue(), Level.TERSE, CONSTANTS.nameError(), nameField)
						|| ifMessage(fromtodateField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.noTovalue(), Level.TERSE, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.isEndBeforeStart(), Level.TERSE, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
						|| ifMessage(valueField.noValue(), Level.TERSE, CONSTANTS.valueError(), valueField)
						|| ifMessage(valueField.getValue() <= 0.0, Level.TERSE, CONSTANTS.valueError(), valueField) 
						|| ifMessage(valueField.noUnitvalue(), Level.TERSE, AbstractField.CONSTANTS.errCurrency(), valueField));
			}
			protected void send() {super.send(getValue(new RuleUpdate()));}
			protected void receive(Rule rule) {
				if (parentTable != null) {parentTable.execute(true);}
				hide();
			}
		};

		// -----------------------------------------------
		// Delete Rule
		// -----------------------------------------------
		ruleDelete = new GuardedRequest<Rule>() {
			protected String popup() {return AbstractField.CONSTANTS.errDeleteOK();}
			protected boolean error() {return ifMessage(noId(), Level.TERSE, AbstractField.CONSTANTS.errId(), fromtodateField);}
			protected void send() {super.send(getValue(new RuleDelete()));}
			protected void receive(Rule rule) {
				if (parentTable != null) {parentTable.execute(true);}
				hide();
			}
		};

	}
}
