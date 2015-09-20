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
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.CommandButton;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.product.ProductConstants;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Product.Type;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Tax;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.product.ProductCreate;
import net.cbtltd.shared.product.ProductDelete;
import net.cbtltd.shared.product.ProductRead;
import net.cbtltd.shared.product.ProductUpdate;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/** The Class ProductPopup is to display and change product instances. */
public class ProductPopup
extends AbstractPopup<Product> {

	private static final ProductConstants CONSTANTS = GWT.create(ProductConstants.class);
//	private static final ProductBundle BUNDLE = ProductBundle.INSTANCE;
//	private static final ProductCssResource CSS = BUNDLE.css();

	/** The Constant Array TYPES contains the allowed product types. */
	public static final String[] TYPES = { Type.Accommodation.name(), Type.Consumable.name(), Type.Inventory.name(), Type.Maintenance.name(), Type.Marketing.name() };

	private static GuardedRequest<Product> productCreate;
	private static GuardedRequest<Product> productRead;
	private static GuardedRequest<Product> productUpdate;
	private static GuardedRequest<Product> productDelete;

	private static final Label titleLabel = new Label();
	private static SuggestField productField;
	private static TextField nameField;
	private static SuggestField ownerField;
	private static SuggestField supplierField;
	private static ListField taxField;
	private static ListField typeField;
	private static ListField unitField;
	private static TextAreaField descriptionField;
	private static HasValue<String> parentField; // field that invoked the popup

	/** Instantiates a new product pop up panel.
	 */
	public ProductPopup() {
		super(true);
		createActions();
		setWidget(createContent());
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
	}

	private static ProductPopup instance;
	
	/**
	 * Gets the single instance of ProductPopup.
	 *
	 * @return single instance of ProductPopup
	 */
	public static synchronized ProductPopup getInstance() {
		if (instance == null) {instance = new ProductPopup();}
		return instance;
	}

	/**
	 * Shows the panel for the product with specified ID.
	 *
	 * @param id the ID of the specified product.
	 */
	public void show(String id) {
		productField.setValue(id);
		productRead.execute();
	}

	/**
	 * Shows the panel for the specified product.
	 *
	 * @param product the specified product.
	 */
	public void show(Product product) {
		productField.setValue(product.getId());
		productRead.execute();
	}

	/**
	 * Shows the panel for the specified product type.
	 *
	 * @param type the specified product type.
	 * @param parentField the field that invoked the pop up panel.
	 */
	public void show(Product.Type type, HasValue<String> parentField) {
		ProductPopup.parentField = parentField;
		onReset(Product.INITIAL);
		ownerField.setValue(AbstractRoot.getOrganizationid());
		if (Product.Type.Marketing == type) {supplierField.setValue(AbstractRoot.getOrganizationid());}
		typeField.setValue(type.name());
		unitField.setValue(Unit.EA);
		productCreate.execute(true);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue()
	 */
	@Override
	public Product getValue() {return getValue(new Product());}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue(java.lang.Object)
	 */
	private Product getValue(Product product){
		product.setState(state);
		product.setOrganizationid(AbstractRoot.getOrganizationid());
		product.setId(productField.getValue());
		if (productField.isVisible()){product.setName(productField.getName());}
		else {product.setName(nameField.getValue());}
		product.setCurrency(AbstractRoot.getCurrency());
		product.setOwnerid(AbstractRoot.getOrganizationid());
		product.setSupplierid(supplierField.getValue());
		product.setTax(taxField.getValue());
		product.setType(typeField.getValue());
		product.setUnit(unitField.getValue());
		product.setPrivateText(AbstractRoot.getOrganizationid(), descriptionField.getText());
		Log.debug("getValue " + product);
		return product;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(Product product) {
		Log.debug("setValue " + product);
		if (product == null) {onReset(Product.CREATED);}
		else {
			setResetting(true);
			onStateChange(product.getState());
			typeField.setValue(product.getType());
			productField.setValue(product.getId());
			nameField.setValue(product.getName());
			ownerField.setValue(product.getOwnerid());
			supplierField.setValue(product.getSupplierid());
			taxField.setValue(product.getTax());
			unitField.setValue(product.getUnit());
			//descriptionField.setText(product.getPublicText());
			descriptionField.setText(product.getPrivateText(AbstractRoot.getOrganizationid()));

			int index = Product.Type.valueOf(typeField.getValue()).ordinal();
			titleLabel.setText(CONSTANTS.productLabels()[index]);
			nameField.setLabel(CONSTANTS.productnameLabels()[index]);
			descriptionField.setLabel(CONSTANTS.descriptionLabels()[index]);

			ownerField.setVisible(product.hasType(Type.Accommodation.name()));
			supplierField.setVisible(product.hasType(Type.Accommodation.name()) || product.hasType(Type.Consumable.name()) || product.hasType(Type.Inventory.name()) || product.hasType(Type.Maintenance.name()));
			taxField.setVisible(product.hasType(Type.Accommodation.name()) || product.hasType(Type.Consumable.name()) || product.hasType(Type.Inventory.name()) || product.hasType(Type.Maintenance.name()));
			unitField.setVisible(product.hasType(Type.Consumable.name()) || product.hasType(Type.Inventory.name()) || product.hasType(Type.Maintenance.name()));
			typeField.setVisible(false);		
			center();
			setResetting(false);
		}
	}

	/*
	 * Creates the panel of product fields.
	 * 
	 * @return the panel of product fields.
	 */
	private VerticalPanel createContent() {
		VerticalPanel form = new VerticalPanel();
		//		final Label title = new Label(CONSTANTS.productLabels()[type.ordinal()]);
		titleLabel.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(titleLabel);

		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ProductPopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);

		//-----------------------------------------------
		// Product field
		//-----------------------------------------------
		productField = new SuggestField(this, null,
				new NameIdAction(Service.PRODUCT),
				CONSTANTS.productnameLabels()[0],
				20,
				tab++);
		productField.setDoubleclickable(true);
		productField.setReadOption(Product.CREATED, true);
		form.add(productField);

		//-----------------------------------------------
		// Name field
		//-----------------------------------------------
		nameField = new TextField(this, null,
				CONSTANTS.productnameLabels()[0],
				tab++);
		nameField.setMaxLength(50);
		nameField.setReadOption(Product.INITIAL, true);
		form.add(nameField);

		//-----------------------------------------------
		// Owner field
		//-----------------------------------------------
		ownerField = new SuggestField(this, null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.ownerLabel(),
				20,
				tab++);
		ownerField.setType(Party.Type.Owner.name());
		form.add(ownerField);

		//-----------------------------------------------
		// Supplier field
		//-----------------------------------------------
		supplierField = new SuggestField(this, null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.supplierLabel(),
				20,
				tab++);
		supplierField.setType(Party.Type.Supplier.name());
		Image supplierButton = new Image(AbstractField.BUNDLE.plus());
		supplierButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PartyPopup.getInstance().show(Party.Type.Supplier, supplierField, null);
			}
		});
		supplierField.addButton(supplierButton);
		supplierField.setFieldStyle(AbstractField.CSS.cbtSuggestFieldSecure());
		form.add(supplierField);

		//-----------------------------------------------
		// Sales Tax field
		//-----------------------------------------------
		taxField = new ListField(this,  null,
				new NameIdAction(Service.TAX),
				CONSTANTS.stateLabel(),
				false,
				tab++);
		taxField.setType(Tax.Type.SalesTaxIncluded.name());
		taxField.setDefaultValue(Tax.VAT_NORMAL);
		//form.add(taxField);

		//-----------------------------------------------
		// Unit field
		//-----------------------------------------------
		unitField = new ListField(this, null,
				NameId.getList(AbstractField.CONSTANTS.allUnits(), Unit.UNITS),
				CONSTANTS.unitLabel(),
				false,
				tab++);
		unitField.setDefaultValue(Unit.EA);
		//form.add(unitField);

		//-----------------------------------------------
		// Notes field
		//-----------------------------------------------
		descriptionField = new TextAreaField(this, null,
				CONSTANTS.descriptionLabels()[0],
				//				new LanguageNameId(),
				tab++);
		descriptionField.setMaxLength(Text.MAX_TEXT_SIZE);
		form.add(descriptionField);

		//-----------------------------------------------
		// Type shuttle
		//-----------------------------------------------
		typeField = new ListField(this, null,
				NameId.getList(CONSTANTS.productTypes(), TYPES),
				CONSTANTS.typeLabel(),
				false,
				tab++);
		typeField.setDefaultValue(Product.Type.Accommodation.name());
		//		form.add(typeField);

		form.add(createCommands());

		onRefresh();
		onReset(Product.CREATED);
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
		final CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), productUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.setTitle(AbstractField.CONSTANTS.helpSave());
		bar.add(saveButton);

		//-----------------------------------------------
		// Delete button
		//-----------------------------------------------
		final CommandButton deleteButton = new CommandButton(this, AbstractField.CONSTANTS.allDelete(), productDelete, tab++);
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
		fsm.add(new Transition(Product.INITIAL, cancelButton, Product.CREATED));
		fsm.add(new Transition(Product.INITIAL, saveButton, Product.CREATED));
		
		fsm.add(new Transition(Product.CREATED, saveButton, Product.CREATED));
		fsm.add(new Transition(Product.CREATED, deleteButton, Product.CREATED));
		return bar;
	}

	/* Gets the field next which to display the name help mesage. */
	private Widget getTargetField() {
		if (nameField.isVisible()) {return nameField;}
		else {return productField;}
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Create Product
		//-----------------------------------------------
		productCreate = new GuardedRequest<Product>() {
			protected String popup() {return hasChanged() ? AbstractField.CONSTANTS.errChangeOK() : null;}
			public void cancel() {state = oldstate;}
			protected boolean error() {return AbstractRoot.noOrganizationid();}
			protected void send() {super.send(getValue(new ProductCreate()));}
			protected void receive(Product product){setValue(product);}
		};

		//-----------------------------------------------
		// Read Product
		//-----------------------------------------------
		productRead = new GuardedRequest<Product>() {
			protected boolean error() {return productField.noValue();}
			protected void send() {super.send(getValue(new ProductRead()));}
			protected void receive(Product product){setValue(product);}
		};

		//-----------------------------------------------
		// Update Product
		//-----------------------------------------------
		productUpdate = new GuardedRequest<Product>() {
			protected boolean error() {
				return (
						ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), getTargetField())
						|| ifMessage(nameField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), getTargetField())
						|| ifMessage(ownerField.noValue(), Level.TERSE, CONSTANTS.ownerError(), ownerField)
						//|| ifMessage(supplierField.noValue(), Level.TERSE, CONSTANTS.supplierError(), supplierField)
						|| ifMessage(unitField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errUnit(), unitField)
						|| ifMessage(typeField.noValue(), Level.TERSE, CONSTANTS.typeError(), typeField)
				);
			}
			protected void send() {super.send(getValue(new ProductUpdate()));}
			protected void receive(Product product) {
				if (parentField != null && product != null) {
					parentField.setValue(product.getId());
					parentField.fireChange();
				}
				hide();
			}
		};

		//-----------------------------------------------
		// Delete Product
		//-----------------------------------------------
		productDelete = new GuardedRequest<Product>() {
			protected String popup() {return AbstractField.CONSTANTS.errDeleteOK();}
			public void cancel() {state = oldstate;}
			protected boolean error() {return ifMessage(nameField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), getTargetField());}
			protected void send() {super.send(getValue(new ProductDelete()));}
			protected void receive(Product product){
				if (parentField != null) {parentField.setValue(null);}
				hide();
			}
		};
	}
}
