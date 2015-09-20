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
import net.cbtltd.client.field.DoubleunitField;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.SpinnerField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.price.PriceConstants;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.finance.CurrencyNameId;
import net.cbtltd.shared.price.PriceCreate;
import net.cbtltd.shared.price.PriceDelete;
import net.cbtltd.shared.price.PriceRead;
import net.cbtltd.shared.price.PriceUpdate;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/** The Class PricePopup is to display and change price instances. */
public class PricePopup extends AbstractPopup<Price> {

	private static final PriceConstants CONSTANTS = GWT.create(PriceConstants.class);
	private static final String[] UNITS = {Unit.EA, Unit.DAY, Unit.WEE, Unit.MON};

	private static GuardedRequest<Price> priceCreate;
	private static GuardedRequest<Price> priceRead;
	private static GuardedRequest<Price> priceUpdate;
	private static GuardedRequest<Price> priceDelete;

	private static TextField nameField;
	private static DatespanField fromtodateField;
	private static SpinnerField availableField;
	private static DoubleunitField quantityField;
	private static DoubleunitField valueField;
	private static DoubleField minimumField;
	private static ListField ruleField;

	private static String productid;
//	private static Integer available;
	private static TableField<Price> parentTable;
	private static String id;

	private boolean noId() {
		return id == null || id.isEmpty();
	}

	private boolean noProductid() {
		return productid == null || productid.isEmpty();
	}

	/*
	 * Checks if the price date range overlaps with another.
	 * 
	 * @return true, if the price date range overlaps with another.
	 */
	private static boolean overlaps() {
		if (parentTable == null) {
			return false;
		}
		for (Price price : parentTable.getValue()) {
			if (price != null && PricePopup.id != null
					&& !PricePopup.id.equalsIgnoreCase(price.getId())
					&& !fromtodateField.getTovalue().before(price.getDate())
					&& !fromtodateField.getValue().after(price.getTodate())) {
				return true;
			}
		}
		return false;
	}

	/** Instantiates a new price pop up panel. */
	public PricePopup() {
		super(true);
		createActions();
		setWidget(createContent());
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
	}

	private static PricePopup instance;

	/**
	 * Gets the single instance of PricePopup.
	 * 
	 * @return single instance of PricePopup
	 */
	public static synchronized PricePopup getInstance() {
		if (instance == null) {
			instance = new PricePopup();
		}
		PricePopup.id = null;
		PricePopup.productid = null;
		PricePopup.parentTable = null;
		return instance;
	}

	/**
	 * Shows the panel for the specified price.
	 * 
	 * @param price
	 *            the specified price.
	 * @param parentTable
	 *            the table that invoked the pop up panel.
	 */
	public void show(Price price, TableField<Price> parentTable) {
		PricePopup.id = price.getId();
		PricePopup.parentTable = parentTable;
		priceRead.execute(true);
	}

	/**
	 * Shows the panel for the specified price type.
	 * 
	 * @param type
	 *            the specified price type.
	 * @param parentTable
	 *            the table that invoked the pop up panel.
	 */
	public void show(TableField<Price> parentTable) {
		PricePopup.parentTable = parentTable;
		onReset(Price.INITIAL);
		PricePopup.valueField.setUnitvalue(AbstractRoot.getCurrency());
		priceCreate.execute(true);
	}

	/**
	 * Shows the panel for the specified price type and product.
	 * 
	 * @param type
	 *            the specified price type.
	 * @param productid
	 *            the ID of the specified product.
	 * @param parentTable
	 *            the table that invoked the pop up panel.
	 */
	public void show(String productid, Integer available, TableField<Price> parentTable) {
		PricePopup.productid = productid;
		PricePopup.parentTable = parentTable;
		onReset(Price.INITIAL);
		availableField.setRange(0, available);
		availableField.setValue(available);
		priceCreate.execute(true);
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
		ifMessage(overlaps(), Level.TERSE, CONSTANTS.overlapsError(), fromtodateField);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue()
	 */
	@Override
	public Price getValue() {
		return getValue(new Price());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue(java.lang.Object)
	 */
	private Price getValue(Price price) {
		price.setState(state);
		price.setId(id);
		price.setOrganizationid(AbstractRoot.getOrganizationid());
		price.setActorid(AbstractRoot.getActorid());
		price.setPartyid(AbstractRoot.getOrganizationid());
		price.setEntitytype(NameId.Type.Product.name());
		price.setEntityid(productid);
		price.setName(nameField.getValue());
		price.setType(NameId.Type.Reservation.name());
		price.setDate(Time.getDateServer(fromtodateField.getValue()));
		price.setTodate(Time.getDateServer(fromtodateField.getTovalue()));
		price.setAvailable(availableField.getValue());
		price.setQuantity(quantityField.getValue());
		price.setUnit(quantityField.getUnitvalue());
		price.setValue(valueField.getValue());
		price.setCurrency(valueField.getUnitvalue());
		price.setRule(ruleField.getValue());
		price.setMinimum(minimumField.getValue());
		Log.debug("getValue " + price);
		return price;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(Price price) {
		Log.debug("setValue " + price);
		if (price == null) {
			return;
		} else {
			setResetting(true);
			onStateChange(price.getState());
			id = price.getId();
			productid = price.getEntityid();
			nameField.setValue(price.getName());
			fromtodateField.setValue(Time.getDateClient(price.getDate()));
			fromtodateField.setTovalue(Time.getDateClient(price.getTodate()));
			availableField.setValue(price.getAvailable());
			quantityField.setValue(price.getQuantity());
			quantityField.setUnitvalue(price.getUnit());
			valueField.setValue(price.getValue());
			valueField.setUnitvalue(price.getCurrency());
			minimumField.setValue(price.getMinimum());
			ruleField.setValue(price.getRule());
			center();
			setResetting(false);
		}
	}

	/*
	 * Creates the panel of price fields.
	 * 
	 * @return the panel of price fields.
	 */
	private VerticalPanel createContent() {
		final VerticalPanel form = new VerticalPanel();
		final Label title = new Label(CONSTANTS.priceLabel());
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);

		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				PricePopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);

		// -----------------------------------------------
		// Price Name field
		// -----------------------------------------------
		nameField = new TextField(this, null, CONSTANTS.nameLabel(), tab++);
		nameField.setMaxLength(50);
		nameField.setDefaultValue(Price.RACK_RATE);
		nameField.setHelpText(CONSTANTS.nameHelp());
		form.add(nameField);

		// -----------------------------------------------
		// Date span field
		// -----------------------------------------------
		fromtodateField = new DatespanField(this, null,
				CONSTANTS.fromtodateLabel(), tab++);
		fromtodateField.setHelpText(CONSTANTS.fromtodateError());
		form.add(fromtodateField);

		//-----------------------------------------------
		// Number of units
		//-----------------------------------------------
		availableField = new SpinnerField(this, null, 0, 99, CONSTANTS.availableLabel(), tab++);
//		availableField.setFieldStyle(CSS.quantityField());
		availableField.setDefaultValue(1);
		availableField.setHelpText(CONSTANTS.availableHelp());
		form.add(availableField);
		
		// -----------------------------------------------
		// Break Quantity field
		// -----------------------------------------------
		quantityField = new DoubleunitField(this, null, 
				NameId.getList(CONSTANTS.units(), UNITS),
				CONSTANTS.quantityLabel(), 
				AbstractField.QF, 
				tab++);
		quantityField.setRange(0.0, 1000000.0);
		quantityField.setHelpText(CONSTANTS.quantityHelp());
//TODO: CJM		form.add(quantityField);

		// -----------------------------------------------
		// Unit Price field
		// -----------------------------------------------
		valueField = new DoubleunitField(this, null, new CurrencyNameId(),
				CONSTANTS.valueLabel(), AbstractField.AF, tab++);
		valueField.setRange(0.01, 1000000.0);
		valueField.setHelpText(CONSTANTS.valueHelp());
		form.add(valueField);

		// -----------------------------------------------
		// Minimum Price field
		// -----------------------------------------------
		minimumField = new DoubleField(this, null, CONSTANTS.minimumLabel(),
				AbstractField.AF, tab++);
		minimumField.setHelpText(CONSTANTS.minimumHelp());

		// -----------------------------------------------
		// Price Rule field
		// -----------------------------------------------
		ruleField = new ListField(this, null, 
				NameId.getList(AbstractField.CONSTANTS.priceRules(),
				Price.RULES), CONSTANTS.ruleLabel(), false, tab++);
		ruleField.setDefaultValue(Price.Rule.AnyCheckIn.name());
		ruleField.setFieldHalf();
		ruleField.setHelpText(CONSTANTS.ruleHelp());

		HorizontalPanel mr = new HorizontalPanel();
		mr.add(minimumField);
		mr.add(ruleField);
		form.add(mr);

		form.add(createCommands());

		onRefresh();
		onReset(Price.CREATED);
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
				AbstractField.CONSTANTS.allSave(), priceUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		saveButton.setTitle(CONSTANTS.saveHelp());
		bar.add(saveButton);

		// -----------------------------------------------
		// Delete button
		// -----------------------------------------------
		final CommandButton deleteButton = new CommandButton(this,
				AbstractField.CONSTANTS.allDelete(), priceDelete, tab++);
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

		fsm.add(new Transition(Price.INITIAL, saveButton, Price.CREATED));
		fsm.add(new Transition(Price.INITIAL, cancelButton, Price.CREATED));

		fsm.add(new Transition(Price.CREATED, saveButton, Price.CREATED));
		fsm.add(new Transition(Price.CREATED, deleteButton, Price.CREATED));

		return bar;
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		// -----------------------------------------------
		// Create Price
		// -----------------------------------------------
		priceCreate = new GuardedRequest<Price>() {
			protected boolean error() {return noProductid();}
			protected void send() {super.send(getValue(new PriceCreate()));}
			protected void receive(Price price) {
				setValue(price);
			}
		};

		// -----------------------------------------------
		// Read Price
		// -----------------------------------------------
		priceRead = new GuardedRequest<Price>() {
			protected boolean error() {
				return (ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE,
						AbstractField.CONSTANTS.errOrganizationid(),
						fromtodateField) || ifMessage(noId(), Level.TERSE,
						AbstractField.CONSTANTS.errId(), fromtodateField));
			}

			protected void send() {
				super.send(getValue(new PriceRead()));
			}

			protected void receive(Price price) {
				setValue(price);
			}
		};

		// -----------------------------------------------
		// Update Price
		// -----------------------------------------------
		priceUpdate = new GuardedRequest<Price>() {
			protected boolean error() {
				return (ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), fromtodateField)
						|| ifMessage(nameField.noValue(), Level.TERSE, CONSTANTS.nameError(), nameField)
						|| ifMessage(fromtodateField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.noTovalue(), Level.TERSE, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.isEndBeforeStart(), Level.TERSE, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
						|| ifMessage(ruleField.noValue(), Level.TERSE, CONSTANTS.ruleError(), ruleField)
						|| ifMessage(overlaps(), Level.TERSE, CONSTANTS.overlapsError(), fromtodateField)
						|| ifMessage(availableField.noValue(), Level.TERSE, CONSTANTS.availableError(), availableField)
						|| ifMessage(!availableField.inRange(), Level.TERSE, CONSTANTS.availableError(), availableField)
						|| ifMessage(valueField.noValue(), Level.TERSE, CONSTANTS.valueError(), valueField)
						|| ifMessage(valueField.getValue() <= 0.0, Level.TERSE, CONSTANTS.valueError(), valueField) 
						|| ifMessage(valueField.noUnitvalue(), Level.TERSE, AbstractField.CONSTANTS.errCurrency(), valueField));
			}

			protected void send() {
				super.send(getValue(new PriceUpdate()));
			}

			protected void receive(Price price) {
				if (parentTable != null) {
					parentTable.execute(true);
				}
				hide();
			}
		};

		// -----------------------------------------------
		// Delete Price
		// -----------------------------------------------
		priceDelete = new GuardedRequest<Price>() {
			protected String popup() {
				return AbstractField.CONSTANTS.errDeleteOK();
			}

			protected boolean error() {
				return ifMessage(noId(), Level.TERSE,
						AbstractField.CONSTANTS.errId(), fromtodateField);
			}

			protected void send() {
				super.send(getValue(new PriceDelete()));
			}

			protected void receive(Price price) {
				if (parentTable != null) {
					parentTable.execute(true);
				}
				hide();
			}
		};

	}
}
