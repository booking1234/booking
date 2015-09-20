/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.panel;

import java.util.ArrayList;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.HasValue;
import net.cbtltd.client.LocalRequest;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.CommandButton;
import net.cbtltd.client.field.DoubleunitField;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.NameIdField;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.price.PriceConstants;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Tax;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.finance.CurrencyNameId;
import net.cbtltd.shared.price.PriceRead;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/** The Class QuotePopup is to display and change reservation quote details. */
public class QuotePopup 
extends AbstractPopup<Price> {

	private static final PriceConstants CONSTANTS = GWT.create(PriceConstants.class);
//	private static final PriceBundle BUNDLE = PriceBundle.INSTANCE;
//	private static final PriceCssResource CSS = BUNDLE.css();

	private static GuardedRequest<Price> featureRead;

	private static ListField featureField;
	private static NameIdField priceField;
	private static DoubleunitField quantityField;
	private static DoubleunitField valueField;
	private static SuggestField supplierField;
	private static TextAreaField notesField;
	
	private static String reservationid;
	private static HasValue<Double> quoteField;
	private static TableField<Price> parentTable;	
	private static Price oldprice;
	
	/** Instantiates a new quote detail pop up panel. */
	public QuotePopup() {
		super(true);
		createActions();
		setWidget(createContent());
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
	}

	private static QuotePopup instance;
	
	/**
	 * Gets the single instance of ReservationFeaturePopup.
	 *
	 * @return single instance of ReservationFeaturePopup
	 */
	public static synchronized QuotePopup getInstance() {
		if (instance == null) {instance = new QuotePopup();}
		oldprice = null;
		parentTable = null;
		return instance;
	}
	
	/**
	 * Shows the panel for the specified price.
	 *
	 * @param price the specified price.
	 * @param parentTable the table that invoked the pop up panel.
	 */
	public void show(Price price, HasValue<Double> quoteField, TableField<Price> parentTable) {
		QuotePopup.quoteField = quoteField;
		QuotePopup.parentTable = parentTable;
		QuotePopup.oldprice = price;
		setValue(price);
	}

	/**
	 * Shows the panel for the specified parent reservation.
	 * Sets the action to populate the feature field to the reservation property features.
	 *
	 * @param reservation the ID of the parent reservation.
	 * @param parentTable the table that invoked the pop up panel.
	 */
	public void show(String reservationid, String productid, HasValue<Double> quoteField, TableField<Price> parentTable) {
		QuotePopup.reservationid = reservationid;
		QuotePopup.quoteField = quoteField;
		QuotePopup.parentTable = parentTable;
		NameIdAction action = new NameIdAction(Service.PRICE);
		action.setType(NameId.Type.Feature.name());
		action.setId(productid);
		featureField.setAction(action);
		Price price = new Price();
		price.setEntitytype(NameId.Type.Reservation.name());
		price.setEntityid(reservationid);
//		price.setType(Price.Type.Optional.name());
		price.setPartyid(AbstractRoot.getOrganizationid());
		price.setState(Price.INITIAL);
		setValue(price);
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {
		if (featureField.sent(change)) {featureRead.execute();}
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
		price.setId(priceField.getId());
		price.setName(priceField.getName());
//		price.setType(Price.Type.Optional.name());
		price.setPartyid(AbstractRoot.getOrganizationid());
		price.setEntitytype(NameId.Type.Reservation.name());
		price.setEntityid(reservationid);
		price.setName(featureField.getName());
		price.setQuantity(quantityField.getValue());
		price.setUnit(quantityField.getUnitvalue());
		price.setValue(valueField.getValue());
		price.setCurrency(valueField.getUnitvalue());
		price.setSupplierid(supplierField.getValue());
		price.setPublicText(notesField.getText());
		Log.debug("getValue " + price);
		return price;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(Price price) {
		Log.debug("setValue " + price);
		if (price == null) {return;}
		setResetting(true);
		onStateChange(price.getState());
		priceField.setId(price.getId());
		priceField.setName(price.getName());
		quantityField.setValue(price.getQuantity());
		quantityField.setUnitvalue(price.getUnit());
		valueField.setValue(price.getValue());
		valueField.setUnitvalue(price.getCurrency());
		supplierField.setValue(price.getSupplierid());
		notesField.setText(price.getPublicText());
		
//		quantityField.setEnabled(price.isOptional());
//		valueField.setEnabled(price.isOptional());
//		supplierField.setEnabled(price.isOptional());
//		notesField.setEnabled(price.isOptional());
		setResetting(false);
		center();
	}
	
	/*
	 * Creates the panel of price fields.
	 * 
	 * @return the panel of price fields.
	 */
	private VerticalPanel createContent() {
		final VerticalPanel form = new VerticalPanel();
		final Label title = new Label(CONSTANTS.featurepriceLabel());
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);
		
		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				QuotePopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);

		//-----------------------------------------------
		// Feature field
		//-----------------------------------------------
		featureField = new ListField(this, null,
				new NameIdAction(Service.PRICE),
				CONSTANTS.featureLabel(),
				true,
				tab++);
		featureField.setReadOption(Price.INITIAL, true);
		featureField.setType(NameId.Type.Feature.name());
		featureField.setHelpText(CONSTANTS.featureHelp());
		form.add(featureField);

		//-----------------------------------------------
		// Price field
		//-----------------------------------------------
		priceField = new NameIdField(this, null,
				CONSTANTS.featureLabel(),
				tab++);
		priceField.setReadOption(Price.CREATED, true);
		priceField.setEnabled(false);
		priceField.setHelpText(CONSTANTS.featureHelp());
		form.add(priceField);

		//-----------------------------------------------
		// Quantity field
		//-----------------------------------------------
		quantityField = new DoubleunitField(this, null,
				NameId.getList(AbstractField.CONSTANTS.allUnits(), Unit.UNITS),
				CONSTANTS.quantityLabel(),
				AbstractField.IF,
				tab++);
		quantityField.setDefaultValue(1.0);
		quantityField.setDefaultUnitvalue(Unit.EA);
		quantityField.setUnitenabled(false);
		quantityField.setHelpText(CONSTANTS.quantityHelp());
		form.add(quantityField);

		//-----------------------------------------------
		// Sales Unit Price field
		//-----------------------------------------------
		valueField = new DoubleunitField(this, null,
				new CurrencyNameId(),
				CONSTANTS.valueLabel(),
				AbstractField.AF,
				tab++);
		valueField.setDefaultUnitvalue(AbstractRoot.getCurrency());
		valueField.setUnitenabled(false);
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

		//-----------------------------------------------
		// Notes field
		//-----------------------------------------------
		notesField = new TextAreaField(this, null,
				CONSTANTS.notesLabel(),
				tab++);
		notesField.setMaxLength(10000);
		notesField.setHelpText(CONSTANTS.notesHelp());
		form.add(notesField);
		form.add(createCommands());
		
		onRefresh();
		onReset(Price.INITIAL);
		return form;
	}

	private static final Double getQuote() {
		Double quote = 0.0;
		ArrayList<Price> prices = parentTable.getValue();
		for (Price price : prices) {
			if (!price.hasType(Tax.Type.SalesTaxIncluded.name())) {quote += price.getTotalvalue();}
		}
		return quote;		
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

		final LocalRequest updateRequest = new LocalRequest() {
			protected void perform() {
				if (parentTable!= null) {parentTable.replaceValue(oldprice, getValue());}
				if (quoteField != null) {quoteField.setValue(getQuote());}
				hide();
			}
		};

		final LocalRequest deleteRequest = new LocalRequest() {
			protected void perform() {
				if (parentTable!= null) {parentTable.removeValue(oldprice);}
				if (quoteField != null) {quoteField.setValue(getQuote());}
				hide();
			}
		};

		//-----------------------------------------------
		// Save button
		//-----------------------------------------------
		final CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), updateRequest, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		saveButton.setTitle(CONSTANTS.saveHelp());
		bar.add(saveButton);
		
		//-----------------------------------------------
		// Delete button
		//-----------------------------------------------
		final CommandButton deleteButton = new CommandButton(this, AbstractField.CONSTANTS.allDelete(), deleteRequest, tab++);
		deleteButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		deleteButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		deleteButton.setTitle(CONSTANTS.deleteHelp());
		bar.add(deleteButton);
		
		//-----------------------------------------------
		// Cancel button
		//-----------------------------------------------
		final CommandButton cancelButton = new CommandButton(this, AbstractField.CONSTANTS.allCancel(), cancelRequest, tab++);
		cancelButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		cancelButton.setTitle(AbstractField.CONSTANTS.helpCancel());
		bar.add(cancelButton);

		//-----------------------------------------------
		// Transition array to define the finite state machine
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
		// Read Feature
		//-----------------------------------------------
		featureRead = new GuardedRequest<Price>() {
			protected boolean error() {return featureField.noValue();}
			protected void send() {super.send(new PriceRead(featureField.getValue()));}
			protected void receive(Price price) {setValue(price);}
		};

	}
}
