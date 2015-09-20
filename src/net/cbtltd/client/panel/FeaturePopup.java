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
import net.cbtltd.client.field.CheckField;
import net.cbtltd.client.field.CommandButton;
import net.cbtltd.client.field.DatespanField;
import net.cbtltd.client.field.DoubleunitField;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.NameIdField;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.price.PriceConstants;
import net.cbtltd.shared.License;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Value;
import net.cbtltd.shared.Price.Rule;
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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/** The Class FeaturePopup is to display and change product feature instances. */
public class FeaturePopup 
extends AbstractPopup<Price> {

	private static final PriceConstants CONSTANTS = GWT.create(PriceConstants.class);
//	private static final PriceBundle BUNDLE = PriceBundle.INSTANCE;
//	private static final PriceCssResource CSS = BUNDLE.css();
	public static final String[] ENTITYTYPES = {NameId.Type.Mandatory.name(), NameId.Type.MandatoryPerDay.name(), NameId.Type.Feature.name()}; 

	private static GuardedRequest<Price> priceCreate;
	private static GuardedRequest<Price> priceUpdate;
	private static GuardedRequest<Price> priceDelete;
	
	private static SuggestField featureField;
	private static NameIdField priceField;
	private static SuggestField typeField;
	private static ListField entitytypeField;
	private static DatespanField fromtodateField;
	private static DoubleunitField valueField;
	private static SuggestField supplierField;
	private static TextAreaField notesField;
	
	private static String productid;
	private static TableField<Price> parentTable;	

	/* 
	 * Checks if the price date range overlaps with another. 
	 * 
	 * @return true, if the price date range overlaps with another.
	 */
	private boolean overlaps() {
		if (parentTable == null) {return false;}
		for (Price price : parentTable.getValue()) {
			if (!price.hasId(priceField.getId()) && featureField.hasValue(price.getName()) && !fromtodateField.getTovalue().before(price.getDate()) && !fromtodateField.getValue().after(price.getTodate())) {return true;}
		}
		return false;
	}
	
	/** Instantiates a new product feature pop up panel. */
	public FeaturePopup() {
		super(true);
		createActions();
		setWidget(createContent());
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
	}

	private static FeaturePopup instance;
	
	/**
	 * Gets the single instance of ProductFeaturePopup.
	 *
	 * @return single instance of ProductFeaturePopup
	 */
	public static synchronized FeaturePopup getInstance() {
		if (instance == null) {instance = new FeaturePopup();}
		FeaturePopup.productid = null;
		FeaturePopup.parentTable = null;
		return instance;
	}
	
	/**
	 * Shows the panel for the specified price.
	 *
	 * @param price the specified price.
	 * @param parentTable the table that invoked the pop up panel.
	 */
	public void show(Price price, TableField<Price> parentTable) {
		FeaturePopup.parentTable = parentTable;
		setValue(price);
	}

	/**
	 * Shows the panel for the parent property.
	 *
	 * @param productid the ID of the parent property.
	 * @param parentTable the table that invoked the pop up panel.
	 */
	public void show(String productid, TableField<Price> parentTable) {
		FeaturePopup.productid = productid;
		FeaturePopup.parentTable = parentTable;
		onReset(Price.INITIAL);
		priceCreate.execute(true);
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {	}

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
		price.setOrganizationid(AbstractRoot.getOrganizationid());
		price.setActorid(AbstractRoot.getActorid());
		price.setPartyid(AbstractRoot.getOrganizationid());
		price.setEntitytype(entitytypeField.getValue());
		price.setEntityid(productid);
		price.setName(featureField.getValue());
		price.setType(typeField.getValue());
		price.setDate(Time.getDateServer(fromtodateField.getValue()));
		price.setTodate(Time.getDateServer(fromtodateField.getTovalue()));
		price.setQuantity(1.0);
//		price.setUnit(costField.getUnitvalue());
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
		else {
			setResetting(true);
			onStateChange(price.getState());
			priceField.setId(price.getId());
			priceField.setName(price.getName());
			featureField.setValue(price.getName());
			typeField.setValue(price.getType());
			entitytypeField.setValue(price.getEntitytype());
//			productid = price.getEntityid();
			fromtodateField.setValue(Time.getDateClient(price.getDate()));
			fromtodateField.setTovalue(Time.getDateClient(price.getTodate()));
			valueField.setValue(price.getValue());
			valueField.setUnitvalue(price.getCurrency());
			supplierField.setValue(price.getSupplierid());
			notesField.setText(price.getPublicText());
			setResetting(false);
			center();
		}
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
				FeaturePopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);

		//-----------------------------------------------
		// Feature field
		//-----------------------------------------------
		featureField = new SuggestField(this, null,
				new NameIdAction(Service.ATTRIBUTE),
				CONSTANTS.featureLabel(),
				20,
				tab++);
		featureField.setReadOption(Price.INITIAL, true);
		featureField.setState(AbstractRoot.getOrganizationid()); //features are organization wide
		featureField.setType(Value.Type.ProductFeature.name());
		featureField.setHelpText(CONSTANTS.featureHelp());

		final Image featureButton = new Image(AbstractField.BUNDLE.plus());
		featureButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (AbstractRoot.noOrganizationid()) {AbstractField.addMessage(Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), featureField);}
				else {ValuePopup.getInstance().show(Value.Type.ProductFeature, CONSTANTS.featureLabel(), AbstractRoot.getOrganizationid(), featureField);}
			}
		});
		featureButton.setTitle(CONSTANTS.featurebuttonHelp());
		featureField.addButton(featureButton);
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
		// Feature Type field
		//-----------------------------------------------
		typeField = new SuggestField(this, null,
				new NameIdAction(Service.ATTRIBUTE),
				CONSTANTS.typeLabel(),
				20,
				tab++);
		typeField.setState(AbstractRoot.getOrganizationid()); //feature types are organization wide
		typeField.setType(Value.Type.FeatureType.name());
		typeField.setHelpText(CONSTANTS.typeHelp());

		final Image typeButton = new Image(AbstractField.BUNDLE.plus());
		typeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (AbstractRoot.noOrganizationid()) {AbstractField.addMessage(Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), typeField);}
				else {ValuePopup.getInstance().show(Value.Type.FeatureType, CONSTANTS.typeLabel(), AbstractRoot.getOrganizationid(), typeField);}
			}
		});
		typeButton.setTitle(CONSTANTS.typebuttonHelp());
		typeField.addButton(typeButton);
		form.add(typeField);

		// -----------------------------------------------
		// Entity Type field
		// -----------------------------------------------
		entitytypeField = new ListField(this, null, NameId.getList(CONSTANTS.entityTypes(),	ENTITYTYPES), CONSTANTS.entitytypeLabel(), false, tab++);
		entitytypeField.setDefaultValue(NameId.Type.Feature.name());
		entitytypeField.setHelpText(CONSTANTS.entitytypeHelp());
		form.add(entitytypeField);
		
		//-----------------------------------------------
		// Date span field
		//-----------------------------------------------
		fromtodateField = new DatespanField(this, null,
				CONSTANTS.fromtodateLabel(),
				tab++);
		fromtodateField.setHelpText(CONSTANTS.fromtodateError());
		form.add(fromtodateField);

		//-----------------------------------------------
		// Unit Price field
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

//		//-----------------------------------------------
//		// Purchase Price field
//		//-----------------------------------------------
//		costField = new DoubleunitField(this, null,
//				NameId.getList(AbstractField.CONSTANTS.allUnits(), Unit.UNITS),
//				CONSTANTS.costLabel(),
//				AbstractField.AF,
//				tab++);
//		costField.setDefaultUnitvalue(Unit.EA);
//		costField.setHelpText(CONSTANTS.costHelp());
//		form.add(costField);

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
		
		final LocalRequest cancelRequest = new LocalRequest() {protected void perform() {hide();}};

		//-----------------------------------------------
		// Save button
		//-----------------------------------------------
		final CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), priceUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		saveButton.setTitle(CONSTANTS.saveHelp());
		bar.add(saveButton);
		
		//-----------------------------------------------
		// Delete button
		//-----------------------------------------------
		final CommandButton deleteButton = new CommandButton(this, AbstractField.CONSTANTS.allDelete(), priceDelete, tab++);
		deleteButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		deleteButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		deleteButton.setTitle(CONSTANTS.deleteHelp());
		bar.add(deleteButton);
		
		//-----------------------------------------------
		// Cancel button
		//-----------------------------------------------
		final CommandButton cancelButton = new CommandButton(this, AbstractField.CONSTANTS.allCancel(), cancelRequest, tab++);
		cancelButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		cancelButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
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
		// Create Feature
		//-----------------------------------------------
		priceCreate = new GuardedRequest<Price>() {
			protected boolean error() {return false;}
			protected void send() {super.send(getValue(new PriceCreate()));}
			protected void receive(Price price) {setValue(price);}
		};

		//-----------------------------------------------
		// Update Feature
		//-----------------------------------------------
		priceUpdate = new GuardedRequest<Price>() {
			protected boolean error() {
				return (
						ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), featureField)
//						|| ifMessage(featureField.noValue(), Level.TERSE, CONSTANTS.featureError(), featureField)
						|| ifMessage(fromtodateField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.noTovalue(), Level.TERSE, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.isEndBeforeStart(), Level.TERSE, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
						|| ifMessage(overlaps(), Level.TERSE, CONSTANTS.overlapsError(), fromtodateField)
						|| ifMessage(valueField.noValue(), Level.TERSE, CONSTANTS.valueError(), valueField)
						|| ifMessage(valueField.noUnitvalue(), Level.TERSE, AbstractField.CONSTANTS.errCurrency(), valueField)
				);
			}
			protected void send() {super.send(getValue(new PriceUpdate()));}
			protected void receive(Price price) {
				if (parentTable!= null) {parentTable.execute(true);}
				hide();
			}
		};

		//-----------------------------------------------
		// Delete Feature
		//-----------------------------------------------
		priceDelete = new GuardedRequest<Price>() {
			protected String popup() {return AbstractField.CONSTANTS.errDeleteOK();}
			protected boolean error() {return ifMessage(priceField.noId(), Level.TERSE, AbstractField.CONSTANTS.errId(), featureField);}
			protected void send() {super.send(getValue(new PriceDelete()));}
			protected void receive(Price price) {
				if (parentTable!= null) {parentTable.execute(true);}
				hide();
			}
		};
	}
}
