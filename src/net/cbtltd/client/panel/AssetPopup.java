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
import net.cbtltd.client.field.IntegerunitField;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.MoneyField;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.asset.AssetConstants;
import net.cbtltd.shared.Asset;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.Value;
import net.cbtltd.shared.asset.AssetCreate;
import net.cbtltd.shared.asset.AssetDelete;
import net.cbtltd.shared.asset.AssetRead;
import net.cbtltd.shared.asset.AssetUpdate;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * The Class AssetPopup is to display and change asset instances.
 * The location ID of the asset is the property ID where the asset is used.
 * The owner of the asset defaults to the owner of the property but can be changed.
 * An asset can be depreciated according to its depreciation type.
 */
public class AssetPopup
extends AbstractPopup<Asset> {

	private static final AssetConstants CONSTANTS = GWT.create(AssetConstants.class);
//	private static final AssetBundle BUNDLE = AssetBundle.INSTANCE;
//	private static final AssetCssResource CSS = BUNDLE.css();

	private static GuardedRequest<Asset> assetCreate;
	private static GuardedRequest<Asset> assetRead;
	private static GuardedRequest<Asset> assetUpdate;
	private static GuardedRequest<Asset> assetDelete;

	private static TextField nameField;
	private static ListField typeField;
	private static SuggestField placeField;
	private static SuggestField ownerField;
	private static SuggestField supplierField;
	private static TextField codeField;
	private static DatespanField fromtodateField;
	private static IntegerunitField quantityField;
	private static MoneyField costField;
	private static MoneyField priceField;
	private static TextAreaField descriptionField;
	private static TableField<Asset> parentTable;	

	private static String id;
	private static boolean noId() {return id == null || id.isEmpty();}
	private static String parentid;
	private static boolean noParentid() {return parentid == null || parentid.isEmpty();}
	
	/** Instantiates a new asset pop up panel.
	 */
	public AssetPopup() {
		super(true);
		createActions();
		setWidget(createContent());
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
	}

	private static AssetPopup instance;
	
	/**
	 * Gets the single instance of AssetPopup.
	 *
	 * @return single instance of AssetPopup
	 */
	public static synchronized AssetPopup getInstance() {
		if (instance == null) {instance = new AssetPopup();}
		id = null;
		parentid = null;
		nameField.setValue(Model.BLANK);
		fromtodateField.setTovalue(null);
		quantityField.setValue(1);
		quantityField.setUnitvalue(Unit.EA);
		costField.setValue(0.0);
		priceField.setValue(0.0);
		return instance;
	}

	/**
	 * Shows the panel for a new asset for the specified property ID.
	 *
	 * @param parentid the ID of the property where the asset is located.
	 * @param ownerid the ID of the owner of the property.
	 * @param parentTable the parent table.
	 */
	public void show(String parentid, String ownerid, TableField<Asset> parentTable) {
		AssetPopup.parentid = parentid;
		AssetPopup.parentTable = parentTable;
		onReset(Asset.INITIAL);
		ownerField.setValue(ownerid);
		assetCreate.execute(true);
	}

	/**
	 * Shows the panel for the specified asset.
	 *
	 * @param asset the specified asset.
	 * @param parentTable the parent table.
	 */
	public void show(Asset asset, TableField<Asset> parentTable) {
		AssetPopup.parentTable = parentTable;
		AssetPopup.id = asset.getId();
		AssetPopup.parentid = asset.getParentid();
		assetRead.execute();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue()
	 */
	@Override
	public Asset getValue() {return getValue(new Asset());}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue(java.lang.Object)
	 */
	private Asset getValue(Asset asset) {
		asset.setState(state);
		asset.setOrganizationid(AbstractRoot.getOrganizationid());
		asset.setActorid(AbstractRoot.getActorid());
		asset.setParentid(parentid);
		asset.setId(id);
		asset.setName(nameField.getValue());
		asset.setType(typeField.getValue());
		asset.setPlace(placeField.getValue());
		asset.setOwnerid(ownerField.getValue());
		asset.setSupplierid(supplierField.getValue());
		asset.setCode(codeField.getValue());
		asset.setDateacquired(fromtodateField.getValue());
		asset.setDatedisposed(fromtodateField.getTovalue());
		asset.setQuantity(quantityField.getValue());
		asset.setUnit(quantityField.getUnitvalue());
		asset.setCost(costField.getValue());
		asset.setPrice(priceField.getValue());
		asset.setCurrency(AbstractRoot.getCurrency());
		asset.setNotes(descriptionField.getValue());
		Log.debug("getValue " + asset);
		return asset;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(Asset asset) {
		Log.debug("setValue " + asset);
		if (asset == null) {onReset(Asset.CREATED);}
		else {
			setResetting(true);
			onStateChange(asset.getState());
			parentid = asset.getParentid();
			id = asset.getId();
			nameField.setValue(asset.getName());
			typeField.setValue(asset.getType());
			placeField.setState(asset.getParentid());
			placeField.setValue(asset.getPlace());
			ownerField.setValue(asset.getOwnerid());
			supplierField.setValue(asset.getSupplierid());
			codeField.setValue(asset.getCode());
			fromtodateField.setValue(asset.getDateacquired());
			fromtodateField.setTovalue(asset.getDatedisposed());
			quantityField.setValue(asset.getQuantity());
			quantityField.setUnitvalue(asset.getUnit());
			costField.setValue(asset.getCost());
			costField.setUnitvalue(asset.getCurrency());
			priceField.setValue(asset.getCost());
			priceField.setUnitvalue(asset.getCurrency());
			descriptionField.setValue(asset.getNotes());
			center();
			setResetting(false);
		}
	}

	/*
	 * Creates the panel of asset fields.
	 * 
	 * @return the panel of asset fields.
	 */
	private VerticalPanel createContent() {
		final VerticalPanel form = new VerticalPanel();
		final Label title = new Label(CONSTANTS.assetLabel());
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);

		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				AssetPopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);

		//-----------------------------------------------
		// Name field
		//-----------------------------------------------
		nameField = new TextField(this, null,
				CONSTANTS.assetnameLabel(),
				tab++);
		nameField.setMaxLength(50);
		nameField.setHelpText(CONSTANTS.assetnameHelp());
		form.add(nameField);

		//-----------------------------------------------
		// Type list
		//-----------------------------------------------
		typeField = new ListField(this, null,
				NameId.getList(CONSTANTS.assetTypes(), Asset.TYPES),
				CONSTANTS.typeLabel(),
				false,
				tab++);
		//typeField.setDefaultValue(Asset.Type.Furniture.name());
		typeField.setHelpText(CONSTANTS.typeHelp());
		form.add(typeField);

		//-----------------------------------------------
		// Place field
		//-----------------------------------------------
		placeField = new SuggestField(this, null,
				new NameIdAction(Service.ATTRIBUTE),
				CONSTANTS.placeLabel(),
				20,
				tab++);
		placeField.setType(Value.Type.AssetPlace.name());
		placeField.setHelpText(CONSTANTS.placeHelp());

		final Image placeButton = new Image(AbstractField.BUNDLE.plus());
		placeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (noParentid()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.assetError(), placeField);}
				else {ValuePopup.getInstance().show(Value.Type.AssetPlace, CONSTANTS.placeLabel(), parentid, placeField);}
			}
		});
		placeButton.setTitle(CONSTANTS.placebuttonHelp());
		placeField.addButton(placeButton);
		form.add(placeField);

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

		final Image supplierButton = new Image(AbstractField.BUNDLE.plus());
		supplierButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PartyPopup.getInstance().show(Party.Type.Supplier, supplierField, null);
			}
		});
		supplierField.addButton(supplierButton);
		supplierField.setFieldStyle(AbstractField.CSS.cbtSuggestFieldSecure());
		form.add(supplierField);

		//-----------------------------------------------
		// Owner field
		//-----------------------------------------------
		ownerField = new SuggestField(this, null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.ownerLabel(),
				20,
				tab++);
		ownerField.setType(Party.Type.Owner.name());
		ownerField.setHelpText(CONSTANTS.ownerHelp());
		ownerField.setDefaultValue(AbstractRoot.getOrganizationid());
		form.add(ownerField);

		//-----------------------------------------------
		// Serial Number field
		//-----------------------------------------------
		codeField = new TextField(this, null,
				CONSTANTS.serialnumberLabel(),
				tab++);
		codeField.setMaxLength(50);
		codeField.setHelpText(CONSTANTS.serialnumberHelp());
		form.add(codeField);

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
		// Quantity field
		//-----------------------------------------------
		quantityField = new IntegerunitField(this, null,
				NameId.getList(AbstractField.CONSTANTS.allUnits(), Unit.UNITS),
				CONSTANTS.quantityLabel(),
				tab++);
		quantityField.setDefaultValue(1);
		quantityField.setDefaultUnitvalue(Unit.EA);
		quantityField.setHelpText(CONSTANTS.quantityHelp());
		form.add(quantityField);

		//-----------------------------------------------
		// Original Cost field
		//-----------------------------------------------
		costField = new MoneyField(this, null,
				CONSTANTS.costLabel(),
				tab++);
		costField.setHelpText(CONSTANTS.costHelp());
		form.add(costField);

		//-----------------------------------------------
		// Replacement Cost field
		//-----------------------------------------------
		priceField = new MoneyField(this, null,
				CONSTANTS.priceLabel(),
				tab++);
		priceField.setHelpText(CONSTANTS.priceHelp());
		form.add(priceField);

		//-----------------------------------------------
		// Notes field
		//-----------------------------------------------
		descriptionField = new TextAreaField(this, null,
				CONSTANTS.descriptionLabel(),
				//				new LanguageNameId(),
				tab++);
		descriptionField.setMaxLength(5000);
		descriptionField.setHelpText(CONSTANTS.descriptionHelp());
		form.add(descriptionField);

		form.add(createCommands());

		onRefresh();
		onReset(Asset.CREATED);
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
		final CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), assetUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.setTitle(AbstractField.CONSTANTS.helpSave());
		bar.add(saveButton);

		//-----------------------------------------------
		// Delete button
		//-----------------------------------------------
		final CommandButton deleteButton = new CommandButton(this, AbstractField.CONSTANTS.allDelete(), assetDelete, tab++);
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
		fsm.add(new Transition(Asset.INITIAL, cancelButton, Asset.CREATED));
		fsm.add(new Transition(Asset.INITIAL, saveButton, Asset.CREATED));
		
		fsm.add(new Transition(Asset.CREATED, saveButton, Asset.CREATED));
		fsm.add(new Transition(Asset.CREATED, deleteButton, Asset.CREATED));
		return bar;
	}

	/* Gets the field next which to display the name help mesage. */
//	private Widget getTargetField() {
//		if (nameField.isVisible()) {return nameField;}
//		else {return assetField;}
//	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Create Asset
		//-----------------------------------------------
		assetCreate = new GuardedRequest<Asset>() {
			protected boolean error() {
				return (
						ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), nameField)
						|| ifMessage(noParentid(), Level.TERSE, CONSTANTS.parentError(), nameField)
				);
			}
			protected void send() {super.send(getValue(new AssetCreate()));}
			protected void receive(Asset asset){
				setValue(asset);
				fromtodateField.setTovalue(null);
			}
		};

		//-----------------------------------------------
		// Read Asset
		//-----------------------------------------------
		assetRead = new GuardedRequest<Asset>() {
			protected boolean error() {return noId();}
			protected void send() {super.send(getValue(new AssetRead()));}
			protected void receive(Asset asset){setValue(asset);}
		};

		//-----------------------------------------------
		// Update Asset
		//-----------------------------------------------
		assetUpdate = new GuardedRequest<Asset>() {
			protected boolean error() {
				return (
						ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), nameField)
						|| ifMessage(nameField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), nameField)
						|| ifMessage(typeField.noValue(), Level.TERSE, CONSTANTS.typeError(), typeField)
						|| ifMessage(ownerField.noValue(), Level.TERSE, CONSTANTS.ownerError(), ownerField)
//						|| ifMessage(supplierField.noValue(), Level.TERSE, CONSTANTS.supplierError(), supplierField)
						|| ifMessage(costField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errUnit(), costField)
				);
			}
			protected void send() {super.send(getValue(new AssetUpdate()));}
			protected void receive(Asset asset) {
				parentTable.execute(true);
				hide();
			}
		};

		//-----------------------------------------------
		// Delete Asset
		//-----------------------------------------------
		assetDelete = new GuardedRequest<Asset>() {
			protected String popup() {return AbstractField.CONSTANTS.errDeleteOK();}
			public void cancel() {state = oldstate;}
			protected boolean error() {return ifMessage(nameField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), nameField);}
			protected void send() {super.send(getValue(new AssetDelete()));}
			protected void receive(Asset asset) {
				parentTable.execute(true);
				hide();
			}
		};
	}
}
