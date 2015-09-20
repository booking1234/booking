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
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.serviceprice.ServicepriceConstants;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.finance.CurrencyNameId;
import net.cbtltd.shared.price.PriceCreate;
import net.cbtltd.shared.price.PriceDelete;
import net.cbtltd.shared.price.PriceUpdate;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/** The Class ServicepricePopup is to display and change service price instances. */
public class ServicepricePopup 
extends AbstractPopup<Price> {

	private static final ServicepriceConstants CONSTANTS = GWT.create(ServicepriceConstants.class);

	private static final String[] SERVICES = {Product.Servicetype.DayofArrival.name(), Product.Servicetype.DayofDeparture.name(), Product.Servicetype.RefreshDay.name(), Product.Servicetype.LinenChange.name()};

	private static GuardedRequest<Price> priceCreate;
	private static GuardedRequest<Price> priceUpdate;
	private static GuardedRequest<Price> priceDelete;

	private static ListField serviceField;
	private static DatespanField fromtodateField;
	private static DoubleunitField valueField;
	private static SuggestField supplierField;

	private static String productid;
	private static Price price;
	private static TableField<Price> parentField;	
	private static String id;
	private boolean noProductid() {return productid == null || productid.isEmpty();}

	/* 
	 * Checks if the price date range overlaps with another. 
	 * 
	 * @return true, if the price date range overlaps with another.
	 */
	private static boolean overlaps() {
		if (parentField == null) {return false;}
		for (Price price : parentField.getValue()) {
			if (ServicepricePopup.price != price
					&& supplierField.hasValue(price.getPartyid())
					&& serviceField.hasValue(price.getName())
					&& !fromtodateField.getTovalue().before(price.getDate()) 
					&& !fromtodateField.getValue().after(price.getTodate())
			) {return true;}
		}
		return false;
	}
	
	/** Instantiates a new service price pop up panel. */
	public ServicepricePopup() {
		super(true);
		createActions();
		setWidget(createContent());
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
	}

	private static ServicepricePopup pricePopup;
	
	/**
	 * Gets the single instance of ServicepricePopup.
	 *
	 * @return single instance of ServicepricePopup
	 */
	public static synchronized ServicepricePopup getInstance() {
		if (pricePopup == null) {pricePopup = new ServicepricePopup();}
		ServicepricePopup.productid = null;
		ServicepricePopup.price = null;
		ServicepricePopup.parentField = null;
		return pricePopup;
	}
	
	/**
	 * Shows the panel for the specified service price.
	 *
	 * @param price the price
	 * @param parentField the field that invoked the pop up panel.
	 */
	public void show(Price price, TableField<Price> parentField) {
		ServicepricePopup.price = price;
		ServicepricePopup.parentField = parentField;
		setValue(price);
		center();
	}

	/**
	 * Shows the panel for the specified service price type and product.
	 *
	 * @param type the specified price type.
	 * @param productid the ID of the specified product.
	 * @param parentField the field that invoked the pop up panel.
	 */
	public void show(String productid, TableField<Price> parentField) {
		ServicepricePopup.productid = productid;
		ServicepricePopup.parentField = parentField;
		onReset(Price.INITIAL);
		priceCreate.execute(true);
		center();
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {
		ifMessage(overlaps(), Level.TERSE, CONSTANTS.overlapsError(), fromtodateField);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue()
	 */
	@Override
	public Price getValue() {return getValue(new Price());}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue(java.lang.Object)
	 */
	private Price getValue(Price price) {
		price.setState(state);
		price.setId(id);
		price.setOrganizationid(AbstractRoot.getOrganizationid());
		price.setActorid(AbstractRoot.getActorid());
		price.setPartyid(supplierField.getValue());
		price.setEntitytype(NameId.Type.Product.name());
		price.setEntityid(productid);
		price.setName(serviceField.getValue());
		price.setDate(fromtodateField.getValue());
		price.setTodate(fromtodateField.getTovalue());
		price.setQuantity(0.0);
		price.setUnit(Unit.DAY);
		price.setValue(valueField.getValue());
		price.setCurrency(valueField.getUnitvalue());
		Log.debug("getValue " + price);
		return price;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(Price price) {
		Log.debug("setValue " + price);
		if (price == null) {return;}
		else {
			setResetting(true);
			onStateChange(price.getState());
			id = price.getId();
			productid = price.getEntityid();
			serviceField.setValue(price.getName());
			supplierField.setValue(price.getPartyid());
			fromtodateField.setValue(price.getDate());
			fromtodateField.setTovalue(price.getTodate());
			valueField.setValue(price.getValue());
			valueField.setUnitvalue(price.getCurrency());
//			priceminimumField.setValue(price.getMinimum());
//			productField.setVisible(price.hasName(Price.Type.SERVICE.name()));
//			priceminimumField.setVisible(price.hasName(Price.Type.RESERVATION.name()));
//			supplierField.setVisible(price.hasName(Price.Type.SERVICE.name()));
			setResetting(false);
		}
	}
	
	/* 
	 * Checks if there is no product ID. 
	 * 
	 * @return true, if there is no product ID. 
	 */
	private boolean noEntityid() {
		return productid == null || productid.isEmpty();
	}

	/*
	 * Creates the panel of service price fields.
	 * 
	 * @return the panel of service price fields.
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
				ServicepricePopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);

		//-----------------------------------------------
		// Service Type field
		//-----------------------------------------------
		serviceField = new ListField(this, null,
				NameId.getList(CONSTANTS.servicetypeLabels(), SERVICES),
				CONSTANTS.serviceLabel(),
				false,
				tab++);
		serviceField.setHelpText(CONSTANTS.serviceHelp());
		form.add(serviceField);

		//-----------------------------------------------
		// Date span field
		//-----------------------------------------------
		fromtodateField = new DatespanField(this, null,
				CONSTANTS.fromtodateLabel(),
				tab++);
		fromtodateField.setDefaultDuration(365.0);
		fromtodateField.setHelpText(CONSTANTS.fromtodateHelp());
		form.add(fromtodateField);

		//-----------------------------------------------
		// Value field
		//-----------------------------------------------
		valueField = new DoubleunitField(this, null,
				new CurrencyNameId(),
				CONSTANTS.valueLabel(),
				AbstractField.AF,
				tab++);
		valueField.setHelpText(CONSTANTS.valueHelp());
		form.add(valueField);

		//-----------------------------------------------
		// Supplier field
		//-----------------------------------------------
		supplierField = new SuggestField(this, null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.supplierLabel(),
				20,
				tab++);
		supplierField.setType(Party.Type.Supplier.name());
		supplierField.setHelpText(CONSTANTS.supplierHelp());
		form.add(supplierField);

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
		
		final LocalRequest resetRequest = new LocalRequest() {protected void perform() {hide();}};

		//-----------------------------------------------
		// Save button
		//-----------------------------------------------
		final CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), priceUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.setTitle(CONSTANTS.saveHelp());
		bar.add(saveButton);
		
		//-----------------------------------------------
		// Delete button
		//-----------------------------------------------
		final CommandButton deleteButton = new CommandButton(this, AbstractField.CONSTANTS.allDelete(), priceDelete, tab++);
		deleteButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		deleteButton.setTitle(CONSTANTS.deleteHelp());
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
		fsm.add(new Transition(Price.INITIAL, saveButton, Price.CREATED));
		fsm.add(new Transition(Price.INITIAL, cancelButton, Price.CREATED));
		
		fsm.add(new Transition(Price.CREATED, saveButton, Price.CREATED));
		fsm.add(new Transition(Price.CREATED, deleteButton, Price.CREATED));

		return bar;
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Create Price
		//-----------------------------------------------
		priceCreate = new GuardedRequest<Price>() {
			protected boolean error() {return noProductid();}
			protected void send() {super.send(getValue(new PriceCreate()));}
			protected void receive(Price price) {setValue(price);}
		};

		//-----------------------------------------------
		// Update Price
		//-----------------------------------------------
		priceUpdate = new GuardedRequest<Price>() {
			protected boolean error() {
				return (
						ifMessage(noEntityid(), Level.TERSE, AbstractField.CONSTANTS.errId(), serviceField)
						|| ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), serviceField)
						|| ifMessage(fromtodateField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.noTovalue(), Level.TERSE, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.isEndBeforeStart(), Level.TERSE, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
						|| ifMessage(overlaps(), Level.TERSE, CONSTANTS.overlapsError(), fromtodateField)
						|| ifMessage(valueField.noValue(), Level.TERSE, CONSTANTS.valueError(), valueField)
						|| ifMessage(valueField.noUnitvalue(), Level.TERSE, AbstractField.CONSTANTS.errCurrency(), valueField)
						|| ifMessage(supplierField.noValue(), Level.TERSE, CONSTANTS.supplierError(), supplierField)
				);
			}
			protected void send() {super.send(getValue(new PriceUpdate()));}
			protected void receive(Price price) {
				if (parentField!= null) {parentField.execute(true);}
				hide();
			}
		};

		//-----------------------------------------------
		// Delete Price
		//-----------------------------------------------
		priceDelete = new GuardedRequest<Price>() {
			protected String popup() {return AbstractField.CONSTANTS.errDeleteOK();}
			protected boolean error() {return ifMessage(noEntityid(), Level.TERSE, AbstractField.CONSTANTS.errId(), serviceField);}
			protected void send() {super.send(getValue(new PriceDelete()));}
			protected void receive(Price price) {
				if (parentField!= null) {parentField.execute(true);}
				hide();
			}
		};
	}
}
