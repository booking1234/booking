/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.form;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.LocalRequest;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.CheckField;
import net.cbtltd.client.field.CheckFields;
import net.cbtltd.client.field.CommandButton;
import net.cbtltd.client.field.DoubleField;
import net.cbtltd.client.field.ImageGallery;
import net.cbtltd.client.field.LocationField;
import net.cbtltd.client.field.MapField;
import net.cbtltd.client.field.ProgressField;
import net.cbtltd.client.field.SliderField;
import net.cbtltd.client.field.SpinnerField;
import net.cbtltd.client.field.StackField;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.field.ToggleField;
import net.cbtltd.client.field.table.ActionCell;
import net.cbtltd.client.field.table.ActionCell.Delegate;
import net.cbtltd.client.field.table.ActionHeader;
import net.cbtltd.client.field.table.ImageCell;
import net.cbtltd.client.field.table.TableError;
import net.cbtltd.client.field.table.TableExecutor;
import net.cbtltd.client.panel.AlertPopup;
import net.cbtltd.client.panel.AssetPopup;
import net.cbtltd.client.panel.AuditPopup;
import net.cbtltd.client.panel.FeaturePopup;
import net.cbtltd.client.panel.LocationPopup;
import net.cbtltd.client.panel.PartyPopup;
import net.cbtltd.client.panel.PricePopup;
import net.cbtltd.client.panel.ServicepricePopup;
import net.cbtltd.client.panel.TaxPopup;
import net.cbtltd.client.panel.YieldPopup;
import net.cbtltd.client.resource.product.ProductBundle;
import net.cbtltd.client.resource.product.ProductConstants;
import net.cbtltd.client.resource.product.ProductCssResource;
import net.cbtltd.shared.Alert;
import net.cbtltd.shared.Asset;
import net.cbtltd.shared.Attribute;
import net.cbtltd.shared.AttributeMapAction;
import net.cbtltd.shared.Audit;
import net.cbtltd.shared.License;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Task;
import net.cbtltd.shared.Tax;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.Yield;
import net.cbtltd.shared.alert.AlertTable;
import net.cbtltd.shared.api.HasResetId;
import net.cbtltd.shared.api.HasTable;
import net.cbtltd.shared.asset.AssetTable;
import net.cbtltd.shared.audit.AuditTable;
import net.cbtltd.shared.party.LanguageNameId;
import net.cbtltd.shared.price.PriceTable;
import net.cbtltd.shared.price.ProductFeatureTable;
import net.cbtltd.shared.price.ServicePriceTable;
import net.cbtltd.shared.product.NoPartofNameId;
import net.cbtltd.shared.product.ProductCopy;
import net.cbtltd.shared.product.ProductCreate;
import net.cbtltd.shared.product.ProductDelete;
import net.cbtltd.shared.product.ProductRead;
import net.cbtltd.shared.product.ProductTable;
import net.cbtltd.shared.product.ProductUpdate;
import net.cbtltd.shared.tax.TaxTable;
import net.cbtltd.shared.yield.YieldTable;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;


/** The Class ProductForm is to display and change Product instances. */
public class ProductForm
extends AbstractForm<Product> {

	private static final ProductConstants CONSTANTS = GWT.create(ProductConstants.class);
	private static final ProductBundle BUNDLE = ProductBundle.INSTANCE;
	private static final ProductCssResource CSS = BUNDLE.css();

	private static final int ASSET_ROWS = 19;
	private static final int AUDIT_ROWS = 19;
	private static final int FEATURE_ROWS = 19;
	private static final int PRICE_ROWS = 19;
	private static final int TAX_ROWS = 19;
	private static final int PRODUCT_ROWS = 50;
	private static final int SERVICE_ROWS = 5;
	private static final int SERVICEPRICE_ROWS = 18;
	private static final int YIELD_ROWS = 19;

	private static GuardedRequest<Product> productCopy;
	private static GuardedRequest<Product> productCreate;
	private static GuardedRequest<Product> productRead;
	private static GuardedRequest<Product> productUpdate;
	private static GuardedRequest<Product> productDelete;

//	private static ListField productField;
	private static SuggestField productField;
	private static ProgressField progressField;
	private static TextField newnameField;
	private static SuggestField ownerField;
	private static SuggestField locationField;
	private static SuggestField partofField;
	private static TextField stateField;
	private static ToggleField offlineField;
	private static SliderField ratingField;
	private static DoubleField commissionField;
	private static DoubleField discountField;
	private static DoubleField ownerdiscountField;
	private static SpinnerField roomField;
	private static SpinnerField bathroomField;
	private static SpinnerField toiletField;
	private static SpinnerField adultField;
	private static SpinnerField childField;
	private static SpinnerField infantField;
	private static SpinnerField quantityField;

	private static CheckField dynamicField;
	private static TextField codeField;
	private static TextField idField;
	private static TextAreaField physicaladdressField;

	private static StackLayoutPanel stackPanel;
	private static StackField attributeField;
	private static TextAreaField notesField;
	private static TextAreaField descriptionField;
	private static TextAreaField contentsField;
	private static TextAreaField optionsField;
	private static TextAreaField conditionsField;
	private static MapField mapField;
	private static ImageGallery imageGallery;
	private static LocationField geolocationField;
	private static DoubleField latitudeField;
	private static DoubleField longitudeField;

	private static TableField<Alert> alertTable;
	private static TableField<Asset> assetTable;
	private static TableField<Audit> auditTable;
	private static TableField<Product> productTable;
	private static TableField<Price> priceTable;
	private static TableField<Tax> taxTable;
	private static TableField<Yield> yieldTable;
	private static TableField<Price> featureTable;
	private static TableField<Price> servicepriceTable;
	private static TextAreaField[] serviceTexts;
	private static SpinnerField refreshField;
	private static SpinnerField linenchangeField;
	private static CheckFields servicedaysField;

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#permission()
	 */
	@Override
	public short[] permission(){return AccessControl.PRODUCT_PERMISSION;}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#getValue()
	 */
	@Override
	public Product getValue() {return getValue(new Product());}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {
		if (productField.sent(change)) {productRead.execute();}
		//TODO:		else if (newnameField.sent(change)) {productField.setName(newnameField.getValue(), productField.getValue());}
		else if (newnameField.sent(change)) {productField.setNameId(new NameId(newnameField.getValue(), productField.getValue()));}  //TODO: test
		else if (stateField.sent(change)) {onStateChange(stateField.getValue());}
		else if (locationField.sent(change)) {geolocationField.setName(locationField.getName());}
		else if (geolocationField.sent(change)) {mapField.setValue(geolocationField.getValue());}
		else if (latitudeField.sent(change) 
				&& latitudeField.hasValue() 
				&& latitudeField.inRange() 
				&& longitudeField.hasValue()
				&& longitudeField.getValue() != 0.0
		) {mapField.setValue(LatLng.newInstance(latitudeField.getValue(), longitudeField.getValue()));}
		else if (longitudeField.sent(change) 
				&& longitudeField.hasValue() 
				&& longitudeField.inRange() 
				&& latitudeField.hasValue()
				&& latitudeField.getValue() != 0.0
		) {mapField.setValue(LatLng.newInstance(latitudeField.getValue(), longitudeField.getValue()));}
		else if (productTable.sent(change)) {
			if (productTable.noValue()) {stackPanel.remove(productTable);}
			else {
				stackPanel.add(productTable, CONSTANTS.productLabel(), 1.5);
				productTable.execute();
			}
			partofField.setVisible(productTable.noValue());
		}
		
		if (quantityField.getValue() != 1 && partofField.hasValue()) {
			AbstractField.addMessage(Level.ERROR, CONSTANTS.quantityError(), quantityField);
			quantityField.setValue(1);
		}
//TODO: CJM		quantityField.setValue(partofField.hasValue() ? 1 : quantityField.getValue());
		codeField.setVisible(partofField.hasValue());
		mapField.setEmpty(mapField.hasDefaultValue());
		progressField.setValue(totalProgress());
		triggerResize();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent click) {
		if (progressField.sent(click)) {new ProgressPopup().center();}
		else {super.onClick(click);} //for fsm
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onReset(net.cbtltd.shared.api.HasNameId)
	 */
	@Override
	public void onReset(HasResetId resetid) {
		productField.setValue(resetid.getResetid());
		productRead.execute();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#initialize()
	 */
	public void initialize() {
		AbstractField.CSS.ensureInjected();
		CSS.ensureInjected();

		final ScrollPanel scroll = new ScrollPanel();
		add(scroll);
		final HorizontalPanel panel = new HorizontalPanel();
		panel.addStyleName(AbstractField.CSS.cbtAbstractWidth());
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		scroll.add(panel);
		final HorizontalPanel content = new HorizontalPanel();
		panel.add(content);

		createActions();
		content.add(createContent());
		stackPanel = new StackLayoutPanel(com.google.gwt.dom.client.Style.Unit.EM);
		content.add(stackPanel);
		stackPanel.addSelectionHandler(new SelectionHandler<Integer>() {
			public void onSelection(SelectionEvent<Integer> event) {
				if (event.getSelectedItem() != 0 && productField.noValue()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.productError(), productField);}
				refreshStackPanel();
			}
		});
		stackPanel.addStyleName(CSS.stackStyle());
		stackPanel.add(createDescription(), CONSTANTS.descriptionLabel(), 1.5);
		stackPanel.add(createText(), CONSTANTS.textLabel(), 1.5);
		stackPanel.add(mapField, CONSTANTS.mapLabel(), 1.5);
		stackPanel.add(imageGallery, CONSTANTS.imageLabel(), 1.5);
		stackPanel.add(createPrice(), CONSTANTS.priceLabel(), 1.5);
		stackPanel.add(createTax(), CONSTANTS.taxLabel(), 1.5);
		stackPanel.add(createYield(), CONSTANTS.yieldLabel(), 1.5);
		stackPanel.add(createFeature(), CONSTANTS.featureLabel(), 1.5);
		stackPanel.add(createAsset(), CONSTANTS.assetLabel(), 1.5);
		stackPanel.add(createAudit(), CONSTANTS.auditLabel(), 1.5);
		stackPanel.add(createAlert(), CONSTANTS.alertLabel(), 1.5);
		
		productTable = createProduct();
//		stackPanel.add(createService(), CONSTANTS.serviceLabel(), 1.5);
		createService();
		onRefresh();
		onReset(Product.CREATED);
		triggerResize();
	}

	/* Refreshes the open stack panel */
	private void refreshStackPanel() {
		if (priceTable != null) {priceTable.execute();} //for progress bar
		if (yieldTable != null) {yieldTable.execute();} //for progress bar
		switch (stackPanel.getVisibleIndex()) {
//		case 4: priceTable.execute(); break;
		case 5: taxTable.execute(); break;
//		case 6: yieldTable.execute(); break;
		case 7: featureTable.execute(); break;
		case 8: assetTable.execute(); break;
		case 9: auditTable.execute(); break;
		case 10: alertTable.execute(); break;
		}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#getValue(net.cbtltd.shared.api.HasState)
	 */
	private Product getValue(Product product) {
		product.setState(state);
		product.setOrganizationid(AbstractRoot.getOrganizationid());
		product.setActorid(AbstractRoot.getActorid());
		product.setId(productField.getValue());
		if (newnameField.isVisible()) {product.setName(newnameField.getValue());}
		else {product.setName(productField.getName());}
		product.setSupplierid(AbstractRoot.getOrganizationid());
		product.setPartofid(partofField.noValue() ? null : partofField.getValue());
		product.setLocationid(locationField.getValue());
		product.setOwnerid(ownerField.getValue());
		product.setRank(offlineField.getValue() ? -1.0 : 0.0);
		product.setCommission(commissionField.getValue());
		product.setDiscount(discountField.getValue());
		product.setOwnerdiscount(ownerdiscountField.getValue());
		product.setCode(codeField.getValue());
		product.setDynamicpricingenabled(dynamicField.getValue());
		product.setRating(ratingField.getValue(0));
		product.setRoom(roomField.getValue());
		product.setBathroom(bathroomField.getValue());
		product.setToilet(toiletField.getValue());
		product.setAdult(adultField.getValue());
		product.setChild(childField.getValue());
		product.setInfant(infantField.getValue());
		product.setQuantity(quantityField.getValue());
		product.setUnit(Unit.DAY);
		product.setCurrency(AbstractRoot.getCurrency());
		product.setPhysicaladdress(physicaladdressField.getValue());
		product.setLatitude(mapField.getLatitude());
		product.setLongitude(mapField.getLongitude());
		//product.setLatLng(mapField.getValue());
		product.setLinenchange(linenchangeField.getValue());
		product.setRefresh(refreshField.getValue());
		product.setServicedays(servicedaysField.getValue());
		product.setType(Product.Type.Accommodation.name());
		product.setPrivateText(AbstractRoot.getOrganizationid(), notesField.getText());
		product.setPublicText(descriptionField.getText());
		product.setContentsText(contentsField.getText());
		product.setOptionsText(optionsField.getText());
		product.setConditionsText(conditionsField.getText());
		product.setAttributemap(attributeField.getValue());
		product.setImageurls(imageGallery.getValue());

		for (int index = 0; index < SERVICE_ROWS; index++) {product.setServiceText(serviceTexts[index].getText(), Task.SERVICE_TYPES[index]);}
		Log.debug("getValue " + product);
		return product;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#setValue(net.cbtltd.shared.api.HasState)
	 */
	public void setValue(Product product) {
		Log.debug("setValue " + product);
		if (product == null) {onReset(Product.CREATED);}
		else {
			setResetting(true);
			onStateChange(product.getState());
			stateField.setValue(state);
			productField.setValue(product.getId());
			newnameField.setValue(product.getName());
			partofField.setValue(product.getPartofid());
			locationField.setValue(product.getLocationid());
			ownerField.setValue(product.getOwnerid());
			offlineField.setValue(product.noRank());
			commissionField.setValue(product.getCommissionValue());
			discountField.setValue(product.getDiscount());
			ownerdiscountField.setValue(product.getOwnerdiscount());
			codeField.setValue(product.getCode());
			dynamicField.setValue(product.getDynamicpricingenabled());
			ratingField.setValue(product.getRating());
			roomField.setValue(product.getRoom());
			bathroomField.setValue(product.getBathroom());
			toiletField.setValue(product.getToilet());
			adultField.setValue(product.getAdult());
			childField.setValue(product.getChild());
			infantField.setValue(product.getInfant());
			quantityField.setValue(product.getQuantity());
			physicaladdressField.setValue(product.getPhysicaladdress());
			idField.setText(product.getId());
			mapField.setValue(LatLng.newInstance(product.getLatitude(),product.getLongitude()));
			mapField.setEmpty(mapField.hasDefaultValue());
			mapField.setPositionVisible(product.hasLatLng());
			linenchangeField.setValue(product.getLinenchange());
			refreshField.setValue(product.getRefresh());
			servicedaysField.setValue(product.getServicedays());

			notesField.setText(product.getPrivateText(AbstractRoot.getOrganizationid()));
			descriptionField.setText(product.getPublicText());
			contentsField.setText(product.getContentsText());
			optionsField.setText(product.getOptionsText());
			conditionsField.setText(product.getConditionsText());
			attributeField.setValue(product.getAttributemap());
			imageGallery.setRoot(NameId.Type.Product.name(), product.getId());
			imageGallery.setValue(product.getImageurls());
			imageGallery.setRootURL(product.getProductImageRootLocation());

			idField.setVisible(!hasState(Product.INITIAL));

			refreshStackPanel();
			setResetting(false);
		}
	}

	/* 
	 * Creates the primary panel.
	 * 
	 * @return the primary panel.
	 */
	private FlowPanel createContent() {
		final FlowPanel panel = new FlowPanel();
		final VerticalPanel form =  new VerticalPanel();
		form.addStyleName(AbstractField.CSS.cbtAbstractControl());
		panel.add(form);
		final Label title = new Label(CONSTANTS.titleLabel());
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);

		//-----------------------------------------------
		// Name field, sets all properties once selected
		//-----------------------------------------------
		productField = new SuggestField(this, null,
				new NameIdAction(Service.PRODUCT),
				CONSTANTS.productnameLabel(),
				20,
				tab++);
		productField.setType(Product.Type.Accommodation.name());
		productField.setDoubleclickable(true);
		productField.setReadOption(Product.CREATED, true);
		productField.setReadOption(Product.SUSPENDED, true);
		productField.setHelpText(CONSTANTS.productnameHelp());
		form.add(productField);

		//-----------------------------------------------
		// New Name field
		//-----------------------------------------------
		newnameField = new TextField(this, null,
				CONSTANTS.productnameLabel(),
				tab++);
		newnameField.setReadOption(Product.INITIAL, true);
		newnameField.setHelpText(CONSTANTS.productnameHelp());
		form.add(newnameField);

		//-----------------------------------------------
		// Product progress bar
		//-----------------------------------------------
		progressField = new ProgressField(this, null, CONSTANTS.progressLabel(), 0, 100, tab);
		progressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
		progressField.setHelpText(CONSTANTS.progressHelp());
		form.add(progressField);

		//-----------------------------------------------
		// Owner field
		//-----------------------------------------------
		ownerField = new SuggestField(this, null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.ownerLabel(),
				20,
				tab++);
		ownerField.setLabelStyle(CSS.productStyle());
		ownerField.setType(Party.Type.Owner.name());
		ownerField.setHelpText(CONSTANTS.ownerHelp());

		final Image ownerButton = new Image(AbstractField.BUNDLE.plus());
		ownerButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PartyPopup.getInstance().show(Party.Type.Owner, ownerField, null);
			}
		});
		ownerButton.setTitle(CONSTANTS.ownerbuttonHelp());
		ownerField.addButton(ownerButton);
		form.add(ownerField);

		//-----------------------------------------------
		// Location field
		//-----------------------------------------------
		locationField = new SuggestField(this, null,
				new NameIdAction(Service.LOCATION),
				CONSTANTS.locationLabel(),
				20,
				tab++);
		locationField.setLabelStyle(CSS.productStyle());
		locationField.setHelpText(CONSTANTS.locationHelp());
		
		final Image locationButton = new Image(AbstractField.BUNDLE.plus());
		locationButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				LocationPopup.getInstance().show(locationField);
			}
		});
		locationButton.setTitle(CONSTANTS.locationbuttonHelp());
		locationButton.setVisible(AbstractRoot.getSession().hasPermission(AccessControl.SUPERUSER));
		locationField.addButton(locationButton);
		form.add(locationField);

		//-----------------------------------------------
		// Part of field - if this is part of another product
		//-----------------------------------------------
		partofField = new SuggestField(this, null,
				new NoPartofNameId(),
				CONSTANTS.partofLabel(),
				20,
				tab++);
		partofField.setDefaultName(" "); //CONSTANTS.partofEmpty());
		partofField.setHelpText(CONSTANTS.partofHelp());
		form.add(partofField);

		//-----------------------------------------------
		// Rating range with one anchor slider
		//-----------------------------------------------
		Integer[] RATINGS = {0, 10, 1, 6};
		ratingField = new SliderField(this, null, CONSTANTS.ratingLabel(), RATINGS,	tab++) {
			public Widget getWidget(int value) {return new Image(AbstractField.STARS[value]);}			
		};
		ratingField.setEnabled(false);
		ratingField.setSliderStyle(CSS.ratingSlider());
		ratingField.setHelpText(CONSTANTS.ratingHelp());
		form.add(ratingField);		

		//-----------------------------------------------
		// State field
		//-----------------------------------------------
		stateField = new TextField(this,  null,
				CONSTANTS.stateLabel(),
				tab++);
		stateField.setEnabled(false);
		stateField.setFieldHalf();
		stateField.setHelpText(CONSTANTS.stateHelp());
		//form.add(stateField);

		//-----------------------------------------------
		// Check to show on and off line availability
		//-----------------------------------------------
		offlineField = new ToggleField(this, null,
				CONSTANTS.offlineLabel(),
				CONSTANTS.offlineupLabel(),
				CONSTANTS.offlinedownLabel(),
				tab++);
		offlineField.setEnabled(AbstractRoot.getSession().hasPermission(AccessControl.SUPERUSER));//TODO
		offlineField.setLabelStyle(CSS.offlineLabel());
		offlineField.setFieldStyle(CSS.offlineField());
		offlineField.setHelpText(CONSTANTS.offlineHelp());

		final HorizontalPanel fo = new HorizontalPanel();
		fo.add(stateField);
		fo.add(offlineField);
		form.add(fo);
		
		//-----------------------------------------------
		// Code or type of unit
		//-----------------------------------------------
		codeField = new TextField(this, null, CONSTANTS.codeLabel(), tab++);
		codeField.setFieldHalf();
		codeField.setVisible(false);
		codeField.setHelpText(CONSTANTS.codeHelp());

		//-----------------------------------------------
		// Number of units
		//-----------------------------------------------
		quantityField = new SpinnerField(this, null, 1, 99, CONSTANTS.quantityLabel(),	tab++);
		quantityField.setFieldStyle(CSS.quantityField());
		quantityField.setDefaultValue(1);
		quantityField.setHelpText(CONSTANTS.quantityHelp());
		
		HorizontalPanel cq = new HorizontalPanel();
		form.add(cq);
		cq.add(quantityField);
		cq.add(codeField);

		//-----------------------------------------------
		// Manager's Commission field
		//-----------------------------------------------
		commissionField = new DoubleField(this, null, 
				CONSTANTS.commissionLabel(), 
				AbstractField.AF, 
				tab++);
		commissionField.setDefaultValue(20.0);
		commissionField.setRange(0.0, 100.0);
//		commissionField.setFieldStyle(CSS.discountField());
		commissionField.setHelpText(CONSTANTS.commissionHelp());

		//-----------------------------------------------
		// Agent's Commission field
		//-----------------------------------------------
		discountField = new DoubleField(this, null, 
				CONSTANTS.discountField(), 
				AbstractField.AF, 
				tab++);
		discountField.setDefaultValue(20.0);
		discountField.setRange(0.0, 100.0);
//		discountField.setFieldStyle(CSS.discountField());
		discountField.setHelpText(CONSTANTS.discountHelp());
		
		//-----------------------------------------------
		// Owner's Discount field
		//-----------------------------------------------
		ownerdiscountField = new DoubleField(this, null, 
				CONSTANTS.ownerdiscountLabel(), 
				AbstractField.AF, 
				tab++);
		ownerdiscountField.setDefaultValue(0.0);
		ownerdiscountField.setRange(0.0, 100.0);
		ownerdiscountField.setFieldStyle(CSS.discountField());
		ownerdiscountField.setHelpText(CONSTANTS.ownerdiscountHelp());
		
		HorizontalPanel cd = new HorizontalPanel();
		form.add(cd);
		cd.add(commissionField);
		cd.add(discountField);
//		cd.add(ownerdiscountField);

		//-----------------------------------------------
		// Number of rooms
		//-----------------------------------------------
		roomField = new SpinnerField(this, null, 1,	99,	CONSTANTS.roomLabel(), tab++);
		roomField.setFieldStyle(CSS.spinnerField());
		roomField.setDefaultValue(1);
		roomField.setHelpText(CONSTANTS.roomHelp());

		//-----------------------------------------------
		// Number of bathrooms
		//-----------------------------------------------
		bathroomField = new SpinnerField(this, null, 0,	99,	CONSTANTS.bathroomLabel(), tab++);
		bathroomField.setFieldStyle(CSS.spinnerField());
		bathroomField.setDefaultValue(1);
		bathroomField.setHelpText(CONSTANTS.bathroomHelp());

		//-----------------------------------------------
		// Number of toilets
		//-----------------------------------------------
		toiletField = new SpinnerField(this, null, 0,	99,	CONSTANTS.toiletLabel(), tab++);
		toiletField.setFieldStyle(CSS.spinnerField());
		toiletField.setDefaultValue(1);
		toiletField.setHelpText(CONSTANTS.toiletHelp());

		HorizontalPanel rbt = new HorizontalPanel();
		form.add(rbt);
		rbt.add(roomField);
		rbt.add(bathroomField);
		rbt.add(toiletField);

		//-----------------------------------------------
		// Maximum number of adults
		//-----------------------------------------------
		adultField = new SpinnerField(this, null, 1, 99, CONSTANTS.personLabel(),	tab++);
		adultField.setFieldStyle(CSS.spinnerField());
		adultField.setDefaultValue(2);
		adultField.setHelpText(CONSTANTS.personHelp());
		
		//-----------------------------------------------
		// Maximum number of children
		//-----------------------------------------------
		childField = new SpinnerField(this, null, 0, 99, CONSTANTS.childLabel(),	tab++);
		childField.setFieldStyle(CSS.spinnerField());
		childField.setDefaultValue(0);
		childField.setHelpText(CONSTANTS.childHelp());
		
		//-----------------------------------------------
		// Maximum number of infants
		//-----------------------------------------------
		infantField = new SpinnerField(this, null, 0, 99, CONSTANTS.infantLabel(),	tab++);
		infantField.setFieldStyle(CSS.spinnerField());
		infantField.setDefaultValue(0);
		infantField.setHelpText(CONSTANTS.infantHelp());
		
		HorizontalPanel abc = new HorizontalPanel();
		form.add(abc);
		abc.add(adultField);
		abc.add(childField);
		abc.add(infantField);

		//-----------------------------------------------
		// Check to enable dynamic pricing
		//-----------------------------------------------
		dynamicField = new CheckField(this, null,
				CONSTANTS.dynamicLabel(),
				tab++);
		dynamicField.setDefaultValue(false);
		dynamicField.setHelpText(CONSTANTS.dynamicHelp());
// TODO:		form.add(dynamicField);

		//-----------------------------------------------
		// Product ID field
		//-----------------------------------------------
		idField = new TextField(this, null,
				CONSTANTS.idLabel(),
				tab++);
		idField.setEnabled(false);
		idField.setVisible(false);
		idField.setFieldHalf();
		idField.setHelpText(CONSTANTS.idHelp());
		form.add(idField);

		//-----------------------------------------------
		// Postal Address field
		//-----------------------------------------------
		physicaladdressField = new TextAreaField(this, null,
				CONSTANTS.physicaladdressLabel(),
				tab++);
		physicaladdressField.setMaxLength(100);
		physicaladdressField.setHelpText(CONSTANTS.physicaladdressHelp());
		form.add(physicaladdressField);

		//-----------------------------------------------
		// Map field
		//-----------------------------------------------
		mapField = new MapField(this, null, tab++);
		mapField.setFieldStyle(CSS.mapStyle());
		mapField.setPositionVisible(false);
		mapField.setStreetviewClickable(false);
		mapField.setMapTypeId(MapTypeId.HYBRID);
		mapField.setEmptyValue(mapfieldEmpty());

		//-----------------------------------------------
		// Image field
		//-----------------------------------------------
		imageGallery = new ImageGallery(this, null,
				CONSTANTS.imageLabelPopup(),
				CONSTANTS.imageLabelSetup(),
				null,
				tab++);
		imageGallery.setEmptyValue(imagegalleryEmpty());

		form.add(createCommands());

		FlowPanel shadow = new FlowPanel();
		shadow.addStyleName(AbstractField.CSS.cbtAbstractShadow());
		panel.add(shadow);

		return panel;
	}

	/* 
	 * Creates the description stack panel.
	 * 
	 * @return the description stack panel.
	 */
	private Widget createDescription () {
		HorizontalPanel panel = new HorizontalPanel();

		//-----------------------------------------------
		// Description field
		//-----------------------------------------------
		descriptionField = new TextAreaField(this, null,
				CONSTANTS.descriptiontextLabel(),
				new LanguageNameId(),
				tab++);
		descriptionField.setFieldStyle(CSS.descriptionField());
		descriptionField.setDefaultValue(CONSTANTS.descriptionEmpty());
		descriptionField.setMaxLength(Text.MAX_TEXT_SIZE);
//TODO:		descriptionField.setHelpText(text)
		panel.add(descriptionField);

		//-----------------------------------------------
		// Attribute shuttle
		//-----------------------------------------------
		ScrollPanel scroll = new ScrollPanel();
		panel.add(scroll);
		scroll.addStyleName(CSS.attributeStyle());

		attributeField = new StackField(this, null,
				new AttributeMapAction(Attribute.PROPERTY, Attribute.RZ, AbstractRoot.getLanguage()),
				CONSTANTS.attributeLabel(),
				tab++);
		attributeField.setUniquekeys(Attribute.UNIQUE_KEYS);
		scroll.add(attributeField);
		return panel;
	}

	/* 
	 * Creates the panel of commands.
	 * 
	 * @return the panel of commands.
	 */
	private HorizontalPanel createCommands() {

		final HorizontalPanel bar = new HorizontalPanel();
		bar.addStyleName(AbstractField.CSS.cbtAbstractCommand());

		final LocalRequest resetRequest = new LocalRequest() {protected void perform() {onReset(Product.CREATED);}};

		//-----------------------------------------------
		// Create button
		//-----------------------------------------------
		CommandButton createButton = new CommandButton(this, AbstractField.CONSTANTS.allCreate(), productCreate, tab++);
		createButton.setTitle(CONSTANTS.createHelp());
		createButton.addStyleName(AbstractField.CSS.cbtGradientGreen());
		createButton.addStyleName(AbstractField.CSS.cbtCommandButtonFour());
		bar.add(createButton);

		//-----------------------------------------------
		// Copy button
		//-----------------------------------------------
		CommandButton copyButton = new CommandButton(this, AbstractField.CONSTANTS.allCopy(), productCopy, tab++);
		copyButton.setTitle(CONSTANTS.copyHelp());
		copyButton.addStyleName(AbstractField.CSS.cbtGradientGreen());
		copyButton.addStyleName(AbstractField.CSS.cbtCommandButtonFour());
		bar.add(copyButton);

		//-----------------------------------------------
		// Save button
		//-----------------------------------------------
		CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), productUpdate, tab++);
		saveButton.setTitle(CONSTANTS.saveHelp());
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.addStyleName(AbstractField.CSS.cbtCommandButtonFour());
		bar.add(saveButton);

		//-----------------------------------------------
		// Suspend button
		//-----------------------------------------------
		CommandButton suspendButton = new CommandButton(this, AbstractField.CONSTANTS.allSuspend(), productUpdate, tab++);
		suspendButton.setTitle(CONSTANTS.deleteHelp());
		suspendButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		suspendButton.addStyleName(AbstractField.CSS.cbtCommandButtonFour());
		bar.add(suspendButton);

		//-----------------------------------------------
		// Restore button
		//-----------------------------------------------
		CommandButton restoreButton = new CommandButton(this, AbstractField.CONSTANTS.allRestore(), productUpdate, tab++);
		restoreButton.setTitle(CONSTANTS.deleteHelp());
		restoreButton.addStyleName(AbstractField.CSS.cbtGradientGreen());
		restoreButton.addStyleName(AbstractField.CSS.cbtCommandButtonFour());
		bar.add(restoreButton);

		//-----------------------------------------------
		// Delete button
		//-----------------------------------------------
		CommandButton deleteButton = new CommandButton(this, AbstractField.CONSTANTS.allDelete(), productDelete, tab++);
		deleteButton.setTitle(CONSTANTS.deleteHelp());
		deleteButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		deleteButton.addStyleName(AbstractField.CSS.cbtCommandButtonFour());
		bar.add(deleteButton);

		//-----------------------------------------------
		// Cancel button
		//-----------------------------------------------
		CommandButton cancelButton = new CommandButton(this, AbstractField.CONSTANTS.allCancel(), resetRequest, tab++);
		cancelButton.setTitle(CONSTANTS.productcancelHelp());
		cancelButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		cancelButton.addStyleName(AbstractField.CSS.cbtCommandButtonFour());
		bar.add(cancelButton);

		//-----------------------------------------------
		// The array of transitions to define the finite state machine.
		//-----------------------------------------------
		fsm = new ArrayList<Transition>();
		fsm.add(new Transition(Product.INITIAL, saveButton, Product.CREATED));
		fsm.add(new Transition(Product.INITIAL, cancelButton, Product.CREATED));

		fsm.add(new Transition(Product.CREATED, createButton, Product.INITIAL));
		fsm.add(new Transition(Product.CREATED, copyButton, Product.INITIAL));
		fsm.add(new Transition(Product.CREATED, saveButton, Product.CREATED));
		fsm.add(new Transition(Product.CREATED, suspendButton, Product.SUSPENDED));

		fsm.add(new Transition(Product.SUSPENDED, restoreButton, Product.CREATED));
		fsm.add(new Transition(Product.SUSPENDED, saveButton, Product.SUSPENDED));
		fsm.add(new Transition(Product.SUSPENDED, deleteButton, Product.CREATED));

		return bar;
	}

	/* 
	 * Creates the panel displayed when there is no location map.
	 * 
	 * @return the empty panel.
	 */
	private VerticalPanel mapfieldEmpty() {
		final VerticalPanel panel = new VerticalPanel();
		panel.add(new HTML(CONSTANTS.mapfieldEmpty()));
		final HorizontalPanel horizontal = new HorizontalPanel();
		panel.add(horizontal);
		final VerticalPanel form = new VerticalPanel();
		horizontal.add(form);
		horizontal.add(new Image(BUNDLE.mapfieldEmpty()));

		latitudeField = new DoubleField(this, null, AbstractField.CONSTANTS.locationLatitude(), AbstractField.LF, tab++);
		latitudeField.setRange(-90.0, 90.0);
		form.add(latitudeField);

		longitudeField = new DoubleField(this, null, AbstractField.CONSTANTS.locationLongitude(), AbstractField.LF, tab++);
		longitudeField.setRange(-180.0, 180.0);
		form.add(longitudeField);

		geolocationField = new LocationField(this, null,
				CONSTANTS.geolocationLabel(),
				null,
				tab++);
		geolocationField.addStyleName(CSS.locationField());
		form.add(geolocationField);
		return panel;
	}

	/* 
	 * Creates the panel displayed when there is no image gallery.
	 * 
	 * @return the empty panel.
	 */
	private Widget imagegalleryEmpty() {
		final FlowPanel panel = new FlowPanel();
		panel.add(new HTML(CONSTANTS.imagegalleryEmpty()));
		panel.add(new Image(BUNDLE.imagegalleryEmpty()));
		return panel;
	}

	/* 
	 * Creates the miscellaneous text stack panel.
	 * 
	 * @return the miscellaneous text stack panel.
	 */
	private Grid createText() {
		Grid panel = new Grid(2, 2);
		//-----------------------------------------------
		// Contents Text Field
		//-----------------------------------------------
		contentsField = new TextAreaField(this, null,
				CONSTANTS.contentsLabel(),
				new LanguageNameId(),
				tab++);
		contentsField.setFieldStyle(CSS.textField());
		contentsField.setDefaultValue(CONSTANTS.contentsEmpty());
		contentsField.setMaxLength(Text.MAX_TEXT_SIZE);
		panel.setWidget(0, 0, contentsField);

		//-----------------------------------------------
		// Options Text Field
		//-----------------------------------------------
		optionsField = new TextAreaField(this, null,
				CONSTANTS.optionsLabel(),
				new LanguageNameId(),
				tab++);
		optionsField.setFieldStyle(CSS.textField());
		optionsField.setDefaultValue(CONSTANTS.optionsEmpty());
		optionsField.setMaxLength(Text.MAX_TEXT_SIZE);
		panel.setWidget(1, 0, optionsField);

		//-----------------------------------------------
		// Condition Text field - for Guests
		//-----------------------------------------------
		conditionsField = new TextAreaField(this, null,
				CONSTANTS.conditionsLabel(),
				new LanguageNameId(),
				tab++);
		conditionsField.setFieldStyle(CSS.textField());
		conditionsField.setDefaultValue(CONSTANTS.conditionsEmpty());
		conditionsField.setMaxLength(Text.MAX_TEXT_SIZE);
		panel.setWidget(0, 1, conditionsField);

		//-----------------------------------------------
		// Notes field
		//-----------------------------------------------
		notesField = new TextAreaField(this, null,
				CONSTANTS.notesLabel(),
				new LanguageNameId(),
				tab++);
		notesField.setFieldStyle(CSS.textField());
		notesField.setDefaultValue(CONSTANTS.notesEmpty());
		notesField.setMaxLength(Text.MAX_TEXT_SIZE);
		panel.setWidget(1, 1, notesField);

		return panel;
	}

	/* 
	 * Creates the panel displayed when there is no alert list.
	 * 
	 * @return the empty panel.
	 */
	private Widget alerttableEmpty() {
		FlowPanel panel = new FlowPanel();

		Button emptyButton = new Button(AbstractField.CONSTANTS.allCreate());
		emptyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (productField.noValue()) {AbstractField.addMessage(Level.TERSE, CONSTANTS.productError(), productField);}
				else {AlertPopup.getInstance().show(NameId.Type.Product.name(), productField.getValue(), alertTable);}
			}
		});
		panel.add(emptyButton);

		panel.add(new HTML(CONSTANTS.alerttableEmpty()));
		panel.add(new Image(BUNDLE.alerttableEmpty()));
		return panel;
	}
	
	/* 
	 * Creates the alert table stack panel.
	 * This lists the alerts in the property for inventory and insurance purposes.
	 * 
	 * @return the alert table stack panel.
	 */
	private ScrollPanel createAlert() {
		ScrollPanel panel = new ScrollPanel();
		
		//-----------------------------------------------
		// Selection model
		//-----------------------------------------------
		final NoSelectionModel<Alert> selectionModel = new NoSelectionModel<Alert>();
		SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				AlertPopup.getInstance().show(selectionModel.getLastSelectedObject(), alertTable);
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// Alert table
		//-----------------------------------------------
		alertTable = new TableField<Alert>(this, null,
				new AlertTable(),
				ASSET_ROWS, 
				tab++);

		alertTable.setEmptyValue(alerttableEmpty());
		alertTable.setOrderby(Alert.FROMDATE + HasTable.ORDER_BY_DESC);
		
		alertTable.setTableError(new TableError() {
			@Override
			public boolean error() {return (productField.noValue());}
		});

		alertTable.setTableExecutor(new TableExecutor<AlertTable>() {
			public void execute(AlertTable action) {
				action.setEntitytype(NameId.Type.Product.name());
				action.setEntityid(productField.getValue());
			}
		});

//		alertTable.setFieldStyle(CSS.alertTable());
		
		int col = 0;

		//-----------------------------------------------
		// Change button
		//-----------------------------------------------
		final Column<Alert, Alert> changeButton = new Column<Alert, Alert> ( 
				new ActionCell<Alert>(AbstractField.CONSTANTS.allChange(), AbstractField.CSS.cbtTableFieldChangeButton(),
						new Delegate<Alert>(){
					public void execute(Alert alert) {
						AlertPopup.getInstance().show(alert, alertTable);
					}
				}
				)
		)
		{
			public Alert getValue(Alert alert){return alert;}
		};

		//-----------------------------------------------
		// Create button
		//-----------------------------------------------
		final ActionHeader<Alert> createButton = new ActionHeader<Alert>(
				new ActionCell<Alert>(
						AbstractField.CONSTANTS.allCreate(),
						AbstractField.CSS.cbtTableFieldCreateButton(),
						new Delegate<Alert>(){
							public void execute(Alert alert ) {
								if (productField.noValue()) {AbstractField.addMessage(Level.TERSE, CONSTANTS.productError(), productField);}
								else {AlertPopup.getInstance().show(NameId.Type.Product.name(), productField.getValue(), alertTable);}
							}
						}
				)
		)
		{
			public Alert getValue(Alert alert){
				return alert;
			}
		};

		alertTable.addColumn(changeButton, createButton);
		
		//-----------------------------------------------
		// From Date column
		//-----------------------------------------------
		Column<Alert, Date> fromdate = new Column<Alert, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( Alert alert ) {return Time.getDateClient(alert.getFromdate());}
		};
		alertTable.addDateColumn(fromdate, CONSTANTS.alerttableHeaders()[col++], Alert.FROMDATE);

		//-----------------------------------------------
		// To Date column
		//-----------------------------------------------
		Column<Alert, Date> todate = new Column<Alert, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( Alert alert ) {return Time.getDateClient(alert.getTodate());}
		};
		alertTable.addDateColumn(todate, CONSTANTS.alerttableHeaders()[col++], Alert.TODATE);

		//-----------------------------------------------
		// Message column
		//-----------------------------------------------
		Column<Alert, String> name = new Column<Alert, String>(new TextCell()) {
			@Override
			public String getValue( Alert alert ) {return alert.getName();}
		};
		alertTable.addStringColumn(name, CONSTANTS.alerttableHeaders()[col++], Alert.NAME);
	
		panel.add(alertTable);
		return panel;
	}

	/* 
	 * Creates the panel displayed when there is no asset list.
	 * 
	 * @return the empty panel.
	 */
	private Widget assettableEmpty() {
		FlowPanel panel = new FlowPanel();

		Button emptyButton = new Button(AbstractField.CONSTANTS.allCreate());
		emptyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (productField.noValue()) {AbstractField.addMessage(Level.TERSE, CONSTANTS.productError(), productField);}
				else {AssetPopup.getInstance().show(productField.getValue(), ownerField.getValue(), assetTable);}
			}
		});
		panel.add(emptyButton);

		panel.add(new HTML(CONSTANTS.assettableEmpty()));
		panel.add(new Image(BUNDLE.assettableEmpty()));
		return panel;
	}
	
	/* 
	 * Creates the asset table stack panel.
	 * This lists the assets in the property for inventory and insurance purposes.
	 * 
	 * @return the asset table stack panel.
	 */
	private ScrollPanel createAsset() {
		ScrollPanel panel = new ScrollPanel();
		
		//-----------------------------------------------
		// Selection model
		//-----------------------------------------------
		final NoSelectionModel<Asset> selectionModel = new NoSelectionModel<Asset>();
		SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				AssetPopup.getInstance().show(selectionModel.getLastSelectedObject(), assetTable);
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// Asset table
		//-----------------------------------------------
		assetTable = new TableField<Asset>(this, null,
				new AssetTable(),
				ASSET_ROWS, 
				tab++);

		assetTable.setEmptyValue(assettableEmpty());
		assetTable.setOrderby(Asset.DATEACQUIRED + HasTable.ORDER_BY_DESC);
		
		assetTable.setTableError(new TableError() {
			@Override
			public boolean error() {return (productField.noValue());}
		});

		assetTable.setTableExecutor(new TableExecutor<AssetTable>() {
			public void execute(AssetTable action) {action.setParentid(productField.getValue());}
		});

		assetTable.setFieldStyle(CSS.assetTable());
		
		int col = 0;

		//-----------------------------------------------
		// Change button
		//-----------------------------------------------
		final Column<Asset, Asset> changeButton = new Column<Asset, Asset> ( 
				new ActionCell<Asset>(AbstractField.CONSTANTS.allChange(), AbstractField.CSS.cbtTableFieldChangeButton(),
						new Delegate<Asset>(){
					public void execute(Asset asset){
						AssetPopup.getInstance().show(asset, assetTable);
					}
				}
				)
		)
		{
			public Asset getValue(Asset asset){return asset;}
		};

		//-----------------------------------------------
		// Create button
		//-----------------------------------------------
		final ActionHeader<Asset> createButton = new ActionHeader<Asset>(
				new ActionCell<Asset>(
						AbstractField.CONSTANTS.allCreate(),
						AbstractField.CSS.cbtTableFieldCreateButton(),
						new Delegate<Asset>(){
							public void execute(Asset asset ) {
								if (productField.noValue()) {AbstractField.addMessage(Level.TERSE, CONSTANTS.productError(), productField);}
								else {AssetPopup.getInstance().show(productField.getValue(), ownerField.getValue(), assetTable);}
							}
						}
				)
		)
		{
			public Asset getValue(Asset asset){
				return asset;
			}
		};

		assetTable.addColumn(changeButton, createButton);
		
		//-----------------------------------------------
		// Name column
		//-----------------------------------------------
		Column<Asset, String> name = new Column<Asset, String>(new TextCell()) {
			@Override
			public String getValue( Asset asset ) {return asset.getName();}
		};
		assetTable.addStringColumn(name, CONSTANTS.assettableHeaders()[col++], Asset.NAME);

		//-----------------------------------------------
		// Place column
		//-----------------------------------------------
		Column<Asset, String> place = new Column<Asset, String>(new TextCell()) {
			@Override
			public String getValue( Asset asset ) {return asset.getPlace();}
		};
		assetTable.addStringColumn(place, CONSTANTS.assettableHeaders()[col++], Asset.PLACE);

		//-----------------------------------------------
		// State column
		//-----------------------------------------------
//		Column<Asset, String> state = new Column<Asset, String>(new TextCell()) {
//			@Override
//			public String getValue( Asset asset ) {return asset.getState();}
//		};
//		assetTable.addStringColumn(state, CONSTANTS.assettableHeaders()[col++]);

		//-----------------------------------------------
		// Date Acquired column
		//-----------------------------------------------
		Column<Asset, Date> date = new Column<Asset, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( Asset asset ) {return Time.getDateClient(asset.getDateacquired());}
		};
		assetTable.addDateColumn(date, CONSTANTS.assettableHeaders()[col++], Asset.DATEACQUIRED);

		//-----------------------------------------------
		// Code or Serial Number column
		//-----------------------------------------------
		Column<Asset, String> code = new Column<Asset, String>(new TextCell()) {
			@Override
			public String getValue( Asset asset ) {return asset.getCode();}
		};
		assetTable.addStringColumn(code, CONSTANTS.assettableHeaders()[col++], Asset.CODE);

		//-----------------------------------------------
		// Quantity column
		//-----------------------------------------------
		Column<Asset, Number> quantity = new Column<Asset, Number>(new NumberCell(AbstractField.IF)) {
			@Override
			public Integer getValue( Asset asset ) {return asset.getQuantity();}
		};
		assetTable.addNumberColumn( quantity, CONSTANTS.assettableHeaders()[col++], Asset.QUANTITY);

		//-----------------------------------------------
		// Unit column
		//-----------------------------------------------
		Column<Asset, String> unit = new Column<Asset, String>(new TextCell()) {
			@Override
			public String getValue( Asset asset ) {return asset.getUnit();}
		};
		assetTable.addStringColumn(unit, CONSTANTS.assettableHeaders()[col++], Asset.UNIT);

		//-----------------------------------------------
		// Cost column
		//-----------------------------------------------
		Column<Asset, Number> cost = new Column<Asset, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( Asset asset ) {return asset.getCost();}
		};
		assetTable.addNumberColumn( cost, CONSTANTS.assettableHeaders()[col++], Asset.COST);

		//-----------------------------------------------
		// Price column
		//-----------------------------------------------
		Column<Asset, Number> price = new Column<Asset, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( Asset asset ) {return asset.getPrice();}
		};
		assetTable.addNumberColumn( price, CONSTANTS.assettableHeaders()[col++], Asset.PRICE);

		//-----------------------------------------------
		// Currency column
		//-----------------------------------------------
		Column<Asset, String> currency = new Column<Asset, String>(new TextCell()) {
			@Override
			public String getValue( Asset asset ) {return asset.getCurrency();}
		};
		assetTable.addStringColumn(currency, CONSTANTS.assettableHeaders()[col++], Asset.CURRENCY);
		
		//-----------------------------------------------
		// Notes column
		//-----------------------------------------------
		Column<Asset, String> notes = new Column<Asset, String>(new TextCell()) {
			@Override
			public String getValue( Asset asset ) {return asset.getNotes(100);}
		};
		assetTable.addStringColumn(notes, CONSTANTS.assettableHeaders()[col++], Asset.CURRENCY);
		
		panel.add(assetTable);
		return panel;
	}

	/* 
	 * Creates the panel displayed when there are no audit items.
	 * 
	 * @return the panel displayed when there are no audit items.
	 */
	private Widget audittableEmpty() {
		FlowPanel panel = new FlowPanel();

		Button emptyButton = new Button(AbstractField.CONSTANTS.allCreate());
		emptyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (productField.noValue()) {AbstractField.addMessage(Level.TERSE, CONSTANTS.productError(), productField);}
				else {AuditPopup.getInstance().show(productField.getValue(), auditTable);}
			}
		});
		panel.add(emptyButton);

		panel.add(new HTML(CONSTANTS.audittableEmpty()));
		//panel.addStyleName(CSS.auditEmpty());

		panel.add(new Image(BUNDLE.audittableEmpty()));
		return panel;
	}

	/* 
	 * Creates the audit table stack panel.
	 * 
	 * @return the audit table stack panel.
	 */
	private ScrollPanel createAudit() {
		ScrollPanel panel = new ScrollPanel();

		//-----------------------------------------------
		// Selection model
		//-----------------------------------------------
		final NoSelectionModel<Audit> selectionModel = new NoSelectionModel<Audit>();
		SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				AuditPopup.getInstance().show(selectionModel.getLastSelectedObject(), auditTable);
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		auditTable = new TableField<Audit>(this, null, 
				new AuditTable(),
				selectionModel,
				AUDIT_ROWS, 
				tab++);

		auditTable.setEmptyValue(audittableEmpty());
		//TODO:auditTable.setOrderby(Audit.DATE + HasTable.ORDER_BY_DESC);

		auditTable.setTableError(new TableError() {
			@Override
			public boolean error() {return productField.noValue();}
		});

		auditTable.setTableExecutor(new TableExecutor<AuditTable>() {
			@Override
			public void execute(AuditTable action) {action.setProductid(productField.getValue());}
		});

		int col = 0;

		//-----------------------------------------------
		// Change button
		//-----------------------------------------------
		final Column<Audit, Audit> changeButton = new Column<Audit, Audit>( 
				new ActionCell<Audit>(AbstractField.CONSTANTS.allChange(), AbstractField.CSS.cbtTableFieldChangeButton(),
						new Delegate<Audit>(){
					public void execute( Audit audit ){
						AuditPopup.getInstance().show(audit, auditTable);
					}
				}
				)
		)
		{
			public Audit getValue(Audit audit){return audit;}
		};

		//-----------------------------------------------
		// Create button
		//-----------------------------------------------
		final ActionHeader<Audit> createButton = new ActionHeader<Audit>(
				new ActionCell<Audit>(
						AbstractField.CONSTANTS.allCreate(), 
						AbstractField.CSS.cbtTableFieldCreateButton(),
						new Delegate<Audit>(){
							public void execute(Audit audit ) {
								if (productField.noValue()) {AbstractField.addMessage(Level.TERSE, CONSTANTS.productError(), productField);}
								else {AuditPopup.getInstance().show(productField.getValue(), auditTable);}
							}
						}
				)
		)
		{
			public Audit getValue(Audit audit){
				return audit;
			}
		};

		auditTable.addColumn(changeButton, createButton);

		//-----------------------------------------------
		// Audit Date column
		//-----------------------------------------------
		Column<Audit, Date> eventdate = new Column<Audit, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( Audit price ) {return Time.getDateClient(price.getDate());}
		};
		auditTable.addDateColumn(eventdate, CONSTANTS.audittableHeaders()[col++], Audit.DATE);

		//-----------------------------------------------
		// Audit Name column
		//-----------------------------------------------
		Column<Audit, String> name = new Column<Audit, String>(new TextCell()) {
			@Override
			public String getValue( Audit audit ) {return audit.getName();}
		};
		auditTable.addStringColumn(name, CONSTANTS.audittableHeaders()[col++], Audit.NAME);

		//-----------------------------------------------
		// Value column
		//-----------------------------------------------
		Column<Audit, Integer> rating = new Column<Audit, Integer>( new ImageCell<Integer>(AbstractField.STARS, CSS.ratingStyle())) {
			@Override
			public Integer getValue( Audit audit ) {return audit.getRating() + 1;}
		};
		auditTable.addColumn(rating, CONSTANTS.audittableHeaders()[col++], Audit.RATING);

		//-----------------------------------------------
		// Notes column
		//-----------------------------------------------
		Column<Audit, String> description = new Column<Audit, String>( new TextCell()) {
			@Override
			public String getValue( Audit audit ) {return audit.getNotes(100);}
		};
		auditTable.addStringColumn(description, CONSTANTS.audittableHeaders()[col++], Audit.NOTES);

		panel.add(auditTable);
		return panel;
	}

	/* 
	 * Creates the component product table stack panel.
	 * This is displayed only for composite properties.
	 * 
	 * @return the component product table stack panel.
	 */
	private TableField<Product> createProduct() {
		//-----------------------------------------------
		// Selection model
		//-----------------------------------------------
		final NoSelectionModel<Product> selectionModel = new NoSelectionModel<Product>();
		SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				//TODO: load component into product form PricePopup.getInstance().show(selectionModel.getLastSelectedObject(), priceTable);
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// Product table
		//-----------------------------------------------
		productTable = new TableField<Product>(this, null,
				new ProductTable(),
				PRODUCT_ROWS, 
				tab++);

		productTable.setTableError(new TableError() {
			@Override
			public boolean error() {return (
					productField.noValue()
					|| AbstractRoot.noOrganizationid()
			);}
		});

		productTable.setTableExecutor(new TableExecutor<ProductTable>() {
			public void execute(ProductTable action) {
				action.setOrganizationid(AbstractRoot.getOrganizationid());
				action.setId(productField.getValue());
			}
		});

		productTable.setFieldStyle(CSS.productTable());
		
		int col = 0;

		//-----------------------------------------------
		// Name column
		//-----------------------------------------------
		Column<Product, String> name = new Column<Product, String>(new TextCell()) {
			@Override
			public String getValue( Product product ) {return product.getName();}
		};
		productTable.addStringColumn(name, CONSTANTS.producttableHeaders()[col++]);

		//-----------------------------------------------
		// Type column
		//-----------------------------------------------
		Column<Product, String> category = new Column<Product, String>(new TextCell()) {
			@Override
			public String getValue( Product product ) {return product.getCode();}
		};
		productTable.addStringColumn(category, CONSTANTS.producttableHeaders()[col++]);

		//-----------------------------------------------
		// State column
		//-----------------------------------------------
		Column<Product, String> state = new Column<Product, String>(new TextCell()) {
			@Override
			public String getValue( Product product ) {return product.getState();}
		};
		productTable.addStringColumn(state, CONSTANTS.producttableHeaders()[col++]);

		//-----------------------------------------------
		// Bedroom column
		//-----------------------------------------------
		Column<Product, Number> room = new Column<Product, Number>(new NumberCell(AbstractField.QF)) {
			@Override
			public Integer getValue( Product product ) {return product.getRoom();}
		};
		productTable.addNumberColumn( room, CONSTANTS.producttableHeaders()[col++]);

		//-----------------------------------------------
		// Person column
		//-----------------------------------------------
		Column<Product, Number> person = new Column<Product, Number>(new NumberCell(AbstractField.QF)) {
			@Override
			public Integer getValue( Product product ) {return product.getPerson();}
		};
		productTable.addNumberColumn( person, CONSTANTS.producttableHeaders()[col++]);

		return productTable;
	}

	/* 
	 * Creates the panel displayed when there is no price list.
	 * 
	 * @return the empty panel.
	 */
	private Widget pricetableEmpty() {
		FlowPanel panel = new FlowPanel();

		Button emptyButton = new Button(AbstractField.CONSTANTS.allCreate());
		emptyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (productField.noValue()) {AbstractField.addMessage(Level.TERSE, CONSTANTS.productError(), productField);}
				else {PricePopup.getInstance().show(productField.getValue(), quantityField.getValue(), priceTable);}
			}
		});
		panel.add(emptyButton);

		panel.add(new HTML(CONSTANTS.pricetableEmpty()));
		panel.add(new Image(BUNDLE.pricetableEmpty()));
		return panel;
	}

	/* 
	 * Creates the price list stack panel.
	 * 
	 * @return the price list stack panel.
	 */
	private ScrollPanel createPrice() {
		ScrollPanel panel = new ScrollPanel();
		//-----------------------------------------------
		// Selection model
		//-----------------------------------------------
		final NoSelectionModel<Price> selectionModel = new NoSelectionModel<Price>();
		SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				PricePopup.getInstance().show(selectionModel.getLastSelectedObject(), priceTable);
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// Price table
		//-----------------------------------------------
		priceTable = new TableField<Price>(this, null,
				new PriceTable(),
				PRICE_ROWS, 
				tab++);

		priceTable.setEmptyValue(pricetableEmpty());
		priceTable.setOrderby(Price.DATE);

		priceTable.setTableError(new TableError() {
			@Override
			public boolean error() {return (
					productField.noValue()
					|| AbstractRoot.noOrganizationid()
			);}
		});

		priceTable.setTableExecutor(new TableExecutor<PriceTable>() {
			public void execute(PriceTable action) {
				action.setPartyid(AbstractRoot.getOrganizationid());
				action.setEntitytype(NameId.Type.Product.name());
				action.setEntityid(productField.getValue());
			}
		});

		int col = 0;

		//-----------------------------------------------
		// Change button
		//-----------------------------------------------
		final Column<Price, Price> changeButton = new Column<Price, Price> ( 
				new ActionCell<Price>(AbstractField.CONSTANTS.allChange(), AbstractField.CSS.cbtTableFieldChangeButton(),
						new Delegate<Price>(){
					public void execute(Price price){
						PricePopup.getInstance().show(price, priceTable);
					}
				}
				)
		)
		{
			public Price getValue(Price price){return price;}
		};

		//-----------------------------------------------
		// Create button
		//-----------------------------------------------
		final ActionHeader<Price> createButton = new ActionHeader<Price>(
				new ActionCell<Price>(
						AbstractField.CONSTANTS.allCreate(),
						AbstractField.CSS.cbtTableFieldCreateButton(),
						new Delegate<Price>(){
							public void execute(Price price ) {
								if (productField.noValue()) {AbstractField.addMessage(Level.TERSE, CONSTANTS.productError(), productField);}
								else {PricePopup.getInstance().show(productField.getValue(), quantityField.getValue(), priceTable);}
							}
						}
				)
		)
		{
			public Price getValue(Price price){
				return price;
			}
		};

		priceTable.addColumn(changeButton, createButton);

		//-----------------------------------------------
		// From Date column
		//-----------------------------------------------
		Column<Price, Date> date = new Column<Price, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( Price price ) {return Time.getDateClient(price.getDate());}
		};
		priceTable.addDateColumn(date, CONSTANTS.pricetableHeaders()[col++], Price.DATE);

		//-----------------------------------------------
		// To Date column
		//-----------------------------------------------
		Column<Price, Date> todate = new Column<Price, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( Price price ) {return Time.getDateClient(price.getTodate());}
		};
		priceTable.addDateColumn(todate, CONSTANTS.pricetableHeaders()[col++], Price.TODATE);
		
		//-----------------------------------------------
		// Description column
		//-----------------------------------------------
		Column<Price, String> name = new Column<Price, String>(new TextCell()) {
			@Override
			public String getValue( Price price ) {return price.getName();}
		};
		priceTable.addStringColumn(name, CONSTANTS.pricetableHeaders()[col++]);

		//-----------------------------------------------
		// Available column
		//-----------------------------------------------
		Column<Price, Number> available = new Column<Price, Number>(new NumberCell(AbstractField.IF)) {
			@Override
			public Integer getValue( Price price ) {return price.getAvailable();}
		};
		priceTable.addNumberColumn( available, CONSTANTS.pricetableHeaders()[col++], Price.AVAILABLE);

//		//-----------------------------------------------
//		// Quantity column
//		//-----------------------------------------------
//		Column<Price, Number> quantity = new Column<Price, Number>(new NumberCell(AbstractField.IF)) {
//			@Override
//			public Double getValue( Price price ) {return price.getQuantity();}
//		};
//		priceTable.addNumberColumn( quantity, CONSTANTS.pricetableHeaders()[col++], Price.QUANTITY);
//
//		//-----------------------------------------------
//		// Unit column
//		//-----------------------------------------------
//		Column<Price, String> unit = new Column<Price, String>(new TextCell()) {
//			@Override
//			public String getValue( Price price ) {return getUnitName(price.getUnit());}
//		};
//		priceTable.addStringColumn(unit, CONSTANTS.pricetableHeaders()[col++]);

		//-----------------------------------------------
		// Price column
		//-----------------------------------------------
		Column<Price, Number> price = new Column<Price, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( Price price ) {return price.getValue();}
		};
		priceTable.addNumberColumn( price, CONSTANTS.pricetableHeaders()[col++], Price.VALUE);

		//-----------------------------------------------
		// Minimum Price column
		//-----------------------------------------------
		Column<Price, Number> minimum = new Column<Price, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( Price price ) {return price.getMinimum();}
		};
		priceTable.addNumberColumn( minimum, CONSTANTS.pricetableHeaders()[col++], Price.VALUE);

		//-----------------------------------------------
		// Currency column
		//-----------------------------------------------
		Column<Price, String> currency = new Column<Price, String>(new TextCell()) {
			@Override
			public String getValue( Price price ) {return price.getCurrency();}
		};
		priceTable.addStringColumn(currency, CONSTANTS.pricetableHeaders()[col++]);

		//-----------------------------------------------
		// Rule column
		//-----------------------------------------------
		Column<Price, String> rule = new Column<Price, String>(new TextCell()) {
			@Override
			public String getValue( Price price ) {return getRuleName(price.getRule());}
		};
		priceTable.addStringColumn(rule, CONSTANTS.pricetableHeaders()[col++]);

		panel.add(priceTable);
		return panel;
	}

	private String getRuleName(String id) {
		ArrayList<NameId> nameids = NameId.getList(AbstractField.CONSTANTS.priceRules(), Price.RULES);
		for (NameId nameid: nameids) {
			if (nameid.hasId(id)) {return nameid.getName();}
		}
		return id;
	}
	
	private String getUnitName(String id) {
		ArrayList<NameId> nameids = NameId.getList(AbstractField.CONSTANTS.allUnits(), Unit.UNITS);
		for (NameId nameid: nameids) {
			if (nameid.hasId(id)) {return nameid.getName();}
		}
		return id;
	}
	
	/* 
	 * Creates the panel displayed when there is no price list.
	 * 
	 * @return the empty panel.
	 */
	private Widget taxtableEmpty() {
		FlowPanel panel = new FlowPanel();

		final Button emptyButton = new Button(AbstractField.CONSTANTS.allCreate());
		emptyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (productField.noValue()) {AbstractField.addMessage(Level.TERSE, CONSTANTS.productError(), productField);}
				else {TaxPopup.getInstance().show(NameId.Type.Product.name(), productField.getValue(), taxTable);}
			}
		});
		panel.add(emptyButton);

		panel.add(new HTML(CONSTANTS.taxtableEmpty()));
		panel.add(new Image(BUNDLE.taxtableEmpty()));
		return panel;
	}

	/* 
	 * Creates the tax table stack panel.
	 * 
	 * @return the tax table stack panel.
	 */
	private ScrollPanel createTax() {
		final ScrollPanel panel = new ScrollPanel();
		//-----------------------------------------------
		// Selection model
		//-----------------------------------------------
		final NoSelectionModel<Tax> selectionModel = new NoSelectionModel<Tax>();
		SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				TaxPopup.getInstance().show(NameId.Type.Product.name(), productField.getValue(), selectionModel.getLastSelectedObject(), taxTable);
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// Tax table
		//-----------------------------------------------
		taxTable = new TableField<Tax>(this, null,
				new TaxTable(),
				TAX_ROWS, 
				tab++);

		taxTable.setEmptyValue(taxtableEmpty());
		taxTable.setOrderby(Tax.DATE);

		taxTable.setTableError(new TableError() {
			@Override
			public boolean error() {return (
					productField.noValue()
			);}
		});

		taxTable.setTableExecutor(new TableExecutor<TaxTable>() {
			public void execute(TaxTable action) {
				action.setEntitytype(NameId.Type.Product.name());
				action.setEntityid(productField.getValue());
			}
		});

		int col = 0;

		//-----------------------------------------------
		// Change button
		//-----------------------------------------------
		final Column<Tax, Tax> changeButton = new Column<Tax, Tax> ( 
				new ActionCell<Tax>(AbstractField.CONSTANTS.allChange(), AbstractField.CSS.cbtTableFieldChangeButton(),
						new Delegate<Tax>(){
					public void execute(Tax tax){
						TaxPopup.getInstance().show(NameId.Type.Product.name(), productField.getValue(), tax, taxTable);
					}
				}
				)
		)
		{
			public Tax getValue(Tax tax) {return tax;}
		};

		//-----------------------------------------------
		// Create button
		//-----------------------------------------------
		final ActionHeader<Tax> createButton = new ActionHeader<Tax>(
				new ActionCell<Tax>(
						AbstractField.CONSTANTS.allCreate(),
						AbstractField.CSS.cbtTableFieldCreateButton(),
						new Delegate<Tax>(){
							public void execute(Tax price ) {
								if (productField.noValue()) {AbstractField.addMessage(Level.TERSE, CONSTANTS.productError(), productField);}
								else {TaxPopup.getInstance().show(NameId.Type.Product.name(), productField.getValue(), taxTable);}
							}
						}
				)
		)
		{
			public Tax getValue(Tax tax) {return tax;}
		};

		taxTable.addColumn(changeButton, createButton);

		//-----------------------------------------------
		// Jurisdiction column
		//-----------------------------------------------
		Column<Tax, String> party = new Column<Tax, String>(new TextCell()) {
			@Override
			public String getValue( Tax tax ) {return tax.getPartyname();}
		};
		taxTable.addStringColumn(party, CONSTANTS.taxtableHeaders()[col++]);

		//-----------------------------------------------
		// Name column
		//-----------------------------------------------
		Column<Tax, String> name = new Column<Tax, String>(new TextCell()) {
			@Override
			public String getValue( Tax tax ) {return tax.getName();}
		};
		taxTable.addStringColumn(name, CONSTANTS.taxtableHeaders()[col++]);

		//-----------------------------------------------
		// Type column
		//-----------------------------------------------
		Column<Tax, String> type = new Column<Tax, String>(new TextCell()) {
			@Override
			public String getValue( Tax tax ) {return tax.getType();}
		};
		taxTable.addStringColumn(type, CONSTANTS.taxtableHeaders()[col++]);

		//-----------------------------------------------
		// From Date column
		//-----------------------------------------------
		Column<Tax, Date> date = new Column<Tax, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( Tax tax ) {return Time.getDateClient(tax.getDate());}
		};
		taxTable.addDateColumn(date, CONSTANTS.taxtableHeaders()[col++], Tax.DATE);

		//-----------------------------------------------
		// Amount column
		//-----------------------------------------------
		Column<Tax, Number> amount = new Column<Tax, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( Tax tax ) {return tax.getAmount();}
		};
		taxTable.addNumberColumn( amount, CONSTANTS.taxtableHeaders()[col++], Tax.AMOUNT);

		//-----------------------------------------------
		// Threshold column
		//-----------------------------------------------
//		Column<Tax, Number> threshold = new Column<Tax, Number>(new NumberCell(AbstractField.AF)) {
//			@Override
//			public Integer getValue( Tax tax ) {return tax.getThreshold();}
//		};
//		taxTable.addNumberColumn( threshold, CONSTANTS.taxtableHeaders()[col++], Tax.THRESHOLD);

		//-----------------------------------------------
		// Notes column
		//-----------------------------------------------
		Column<Tax, String> notes = new Column<Tax, String>(new TextCell()) {
			@Override
			public String getValue( Tax tax ) {return tax.getNotes();}
		};
		taxTable.addStringColumn(notes, CONSTANTS.taxtableHeaders()[col++]);

		panel.add(taxTable);
		return panel;
	}

	
	/* 
	 * Creates the panel displayed when there is no yield management rules.
	 * 
	 * @return the empty panel.
	 */
	private Widget yieldtableEmpty() {
		FlowPanel panel = new FlowPanel();

		Button emptyButton = new Button(AbstractField.CONSTANTS.allCreate());
		emptyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (productField.noValue()) {AbstractField.addMessage(Level.TERSE, CONSTANTS.productError(), productField);}
				else {YieldPopup.getInstance().show(productField.getValue(), yieldTable);}
			}
		});
		panel.add(emptyButton);

		panel.add(new HTML(CONSTANTS.yieldtableEmpty()));
		panel.add(new Image(BUNDLE.yieldtableEmpty()));
		return panel;
	}

	/* 
	 * Creates the yield management rules stack panel.
	 * 
	 * @return the yield management rules stack panel.
	 */
	private ScrollPanel createYield() {
		ScrollPanel panel = new ScrollPanel();
		//-----------------------------------------------
		// Yield Selection model
		//-----------------------------------------------
		final NoSelectionModel<Yield> selectionModel = new NoSelectionModel<Yield>();
		SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				YieldPopup.getInstance().show(selectionModel.getLastSelectedObject(), yieldTable);
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// Yield table
		//-----------------------------------------------
		yieldTable = new TableField<Yield>(this, null, 
				new YieldTable(),
				YIELD_ROWS, 
				tab++);

		yieldTable.setEmptyValue(yieldtableEmpty());
		yieldTable.setOrderby(Yield.NAME);

		yieldTable.setTableError(new TableError() {
			@Override
			public boolean error() {return (
					productField.noValue()
					|| AbstractRoot.noOrganizationid()
			);}
		});

		yieldTable.setTableExecutor(new TableExecutor<YieldTable>() {
			public void execute(YieldTable action) {
				action.setEntitytype(NameId.Type.Product.name());
				action.setEntityid(productField.getValue());
			}
		});

		int col = 0;

		//-----------------------------------------------
		// Edit Yield button
		//-----------------------------------------------
		Column<Yield, Yield> changeButton = new Column<Yield, Yield>( 
				new ActionCell<Yield>(AbstractField.CONSTANTS.allChange(), AbstractField.CSS.cbtTableFieldChangeButton(),
						new Delegate<Yield>(){
					public void execute(Yield yield){
						YieldPopup.getInstance().show(yield, yieldTable);
					}
				}
				)
		)
		{
			public Yield getValue(Yield yield){return yield;}
		};

		ActionHeader<Yield> createButtton = new ActionHeader<Yield>(
				new ActionCell<Yield>(
						AbstractField.CONSTANTS.allCreate(), 
						AbstractField.CSS.cbtTableFieldCreateButton(),
						new Delegate<Yield>(){
							public void execute(Yield yield ) {
								if (productField.noValue()) {AbstractField.addMessage(Level.TERSE, CONSTANTS.productError(), productField);}
								else {YieldPopup.getInstance().show(productField.getValue(), yieldTable);}
							}
						}
				)
		)
		{
			public Yield getValue(Yield price){
				return price;
			}
		};

		yieldTable.addColumn(changeButton, createButtton);

		//-----------------------------------------------
		// Rule column
		//-----------------------------------------------
		Column<Yield, String> rule = new Column<Yield, String>(new TextCell()) {
			@Override
			public String getValue( Yield yield ) {return getRule(yield);}
		};
		yieldTable.addStringColumn(rule, CONSTANTS.yieldtableHeaders()[col++], Yield.NAME);

		//-----------------------------------------------
		// Modifier column
		//-----------------------------------------------
		Column<Yield, String> modifier = new Column<Yield, String>(new TextCell()) {
			@Override
			public String getValue( Yield yield ) {return yield.getModifier();}
		};
		yieldTable.addStringColumn(modifier, CONSTANTS.yieldtableHeaders()[col++], Yield.MODIFIER);

		//-----------------------------------------------
		// Amount column
		//-----------------------------------------------
		Column<Yield, Number> amount = new Column<Yield, Number>(new NumberCell(AbstractField.QF)) {
			@Override
			public Double getValue( Yield yield ) {return yield.getAmount();}
		};
		yieldTable.addNumberColumn( amount, CONSTANTS.yieldtableHeaders()[col++], Yield.AMOUNT);

		//-----------------------------------------------
		// From Date column
		//-----------------------------------------------
		Column<Yield, Date> fromdate = new Column<Yield, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( Yield yield ) {return Time.getDateClient(yield.getFromdate());}
		};
		yieldTable.addDateColumn(fromdate, CONSTANTS.yieldtableHeaders()[col++], Yield.FROMDATE);

		//-----------------------------------------------
		// To Date column
		//-----------------------------------------------
		Column<Yield, Date> todate = new Column<Yield, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( Yield yield ) {return Time.getDateClient(yield.getTodate());}
		};
		yieldTable.addDateColumn(todate, CONSTANTS.yieldtableHeaders()[col++], Yield.TODATE);

		panel.add(yieldTable);
		return panel;
	}


	/*
	 * Gets the description of the specified yield management rule.
	 * 
	 * @param yield the specified yield management rule.
	 * @return description of the yield management rule.
	 */
	private String getRule(Yield yield) {
		if (yield.hasName(Yield.DATE_RANGE)) {return yield.getName() + " " + AbstractRoot.getDF().format(Time.getDateClient(yield.getFromdate())) + " - " + AbstractRoot.getDF().format(Time.getDateClient(yield.getTodate()));}
		else if (yield.hasName(Yield.DAY_OF_WEEK)) {return yield.getName() + " " + CONSTANTS.yielddays()[yield.getParam()];}
		else if (yield.hasName(Yield.GAP_FILLER)) {return yield.getName() + " " + yield.getParam() + " Nights";}
		else if (yield.hasName(Yield.EARLY_BIRD)) {return yield.getName() + " " + yield.getParam() + " Days";}
		else if (yield.hasName(Yield.LAST_MINUTE)) {return yield.getName() + " " + yield.getParam() + " Days";}
		else if (yield.hasName(Yield.LENGTH_OF_STAY)) {return yield.getName() + " " + yield.getParam() + " Nights";}
		else if (yield.hasName(Yield.OCCUPANCY_ABOVE)) {return yield.getName() + " " + yield.getParam() + "%";}
		else if (yield.hasName(Yield.OCCUPANCY_BELOW)) {return yield.getName() + " " + yield.getParam() + "%";}
		else if (yield.hasName(Yield.WEEKEND)) {return yield.getName();}
		else return "Unknown Rule";
	}

	/* 
	 * Creates the service rules stack panel.
	 * 
	 * @return the service rules stack panel.
	 */
	private ScrollPanel createService() {
		final ScrollPanel scroll = new ScrollPanel();
		HorizontalPanel panel = new HorizontalPanel();
		scroll.add(panel);
		FlowPanel form = new FlowPanel();
		panel.add(form);
		panel.add(createServiceprice());
		int tab = 1000;
		
		serviceTexts = new TextAreaField[SERVICE_ROWS];

		// Service Rule Texts
		for (int index = 0; index < SERVICE_ROWS; index++) {
			TextAreaField text = new TextAreaField(this, null, tab++);
			text.setValue(CONSTANTS.servicetextEmpty());
			text.setFieldStyle(CSS.servicetextStyle());
			text.setMaxLength(Text.MAX_TEXT_SIZE);
			serviceTexts[index] = text;
		}
		
		// No Service Rule
		Label label = new Label(CONSTANTS.servicetypeLabels()[0]);
		label.addStyleName(CSS.servicelabelStyle());
		form.add(label);
		servicedaysField = new CheckFields(this, null, CONSTANTS.weekdays(), false, tab++);
		servicedaysField.setDefaultValue("0000000");
		form.add(servicedaysField);

		//Arrival Day
		label = new Label(CONSTANTS.servicetypeLabels()[1]);
		label.addStyleName(CSS.servicelabelStyle());
		form.add(label);
		form.add(serviceTexts[1]);

		//Departure Day
		label = new Label(CONSTANTS.servicetypeLabels()[2]);
		label.addStyleName(CSS.servicelabelStyle());
		form.add(label);
		form.add(serviceTexts[2]);

		// Refresh Rule
		HorizontalPanel refreshLabel = new HorizontalPanel();
		form.add(refreshLabel);
		refreshLabel.add(new Label(CONSTANTS.servicetypeLabels()[3]));
		refreshField = new SpinnerField(this, null, 1, 30, null, tab++);
		refreshField.setValue(1);
		refreshLabel.addStyleName(CSS.linenchangeField());
		refreshLabel.add(refreshField);
		refreshLabel.add(new Label(CONSTANTS.servicetypeLabels()[5]));
		refreshLabel.addStyleName(CSS.servicelabelStyle());
		form.add(serviceTexts[3]);

		// Linen Change Rule
		HorizontalPanel linenchangeLabel = new HorizontalPanel();
		form.add(linenchangeLabel);
		linenchangeLabel.add(new Label(CONSTANTS.servicetypeLabels()[4]));
		linenchangeField = new SpinnerField(this, null, 1, 30, null, tab++);
		linenchangeField.setValue(3);
		linenchangeLabel.addStyleName(CSS.linenchangeField());
		linenchangeLabel.add(linenchangeField);
		linenchangeLabel.add(new Label(CONSTANTS.servicetypeLabels()[5]));
		linenchangeLabel.addStyleName(CSS.servicelabelStyle());
		form.add(serviceTexts[4]);
		return scroll;
	}

	/* 
	 * Creates the panel displayed when there are no priced features.
	 * 
	 * @return the panel displayed when there are no priced features.
	 */
	private Widget featuretableEmpty() {
		final FlowPanel panel = new FlowPanel();

		final Button emptyButton = new Button(AbstractField.CONSTANTS.allCreate());
		emptyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (productField.noValue()) {AbstractField.addMessage(Level.TERSE, CONSTANTS.productError(), productField);}
				else {FeaturePopup.getInstance().show(productField.getValue(), featureTable);}
			}
		});
		panel.add(emptyButton);
		panel.add(new HTML(CONSTANTS.featuretableEmpty()));
		panel.add(new Image(BUNDLE.featuretableEmpty()));
		return panel;
	}

	/* 
	 * Creates the feature price table stack panel.
	 * 
	 * @return the feature price table stack panel.
	 */
	private ScrollPanel createFeature() {
		final ScrollPanel panel = new ScrollPanel();

		featureTable = new TableField<Price>(this, null, 
				new ProductFeatureTable(),
				FEATURE_ROWS, 
				tab++);

		featureTable.setEmptyValue(featuretableEmpty());
		featureTable.setOrderby(Price.DATE);
		//featurepriceTable.addStyleName(CSS.actorStyle());

		featureTable.setTableError(new TableError() {
			@Override
			public boolean error() {return productField.noValue();}
		});

		featureTable.setTableExecutor(new TableExecutor<ProductFeatureTable>() {
			@Override
			public void execute(ProductFeatureTable action) {action.setEntityid(productField.getValue());}
		});

		int col = 0;

		//-----------------------------------------------
		// Change button
		//-----------------------------------------------
		final Column<Price, Price> changeButton = new Column<Price, Price>( 
				new ActionCell<Price>(AbstractField.CONSTANTS.allChange(), AbstractField.CSS.cbtTableFieldChangeButton(),
						new Delegate<Price>(){
					public void execute( Price price ){
						FeaturePopup.getInstance().show(price, featureTable);
					}
				}
				)
		)
		{
			public Price getValue(Price price){return price;}
		};

		//-----------------------------------------------
		// Create button
		//-----------------------------------------------
		final ActionHeader<Price> createButton = new ActionHeader<Price>(
				new ActionCell<Price>(
						AbstractField.CONSTANTS.allCreate(), 
						AbstractField.CSS.cbtTableFieldCreateButton(),
						new Delegate<Price>(){
							public void execute(Price price ) {
								if (productField.noValue()) {AbstractField.addMessage(Level.TERSE, CONSTANTS.productError(), productField);}
								else {FeaturePopup.getInstance().show(productField.getValue(), featureTable);}
							}
						}
				)
		)
		{
			public Price getValue(Price price){
				return price;
			}
		};

		featureTable.addColumn(changeButton, createButton);

		//-----------------------------------------------
		// Feature Name column
		//-----------------------------------------------
		Column<Price, String> name = new Column<Price, String>(new TextCell()) {
			@Override
			public String getValue( Price price ) {return price.getName();}
		};
		featureTable.addStringColumn(name, CONSTANTS.featuretableHeaders()[col++], Price.ENTITYNAME);

		//-----------------------------------------------
		// Feature Type column
		//-----------------------------------------------
		Column<Price, String> type = new Column<Price, String>(new TextCell()) {
			@Override
			public String getValue( Price price ) {return price.getType();}
		};
		featureTable.addStringColumn(type, CONSTANTS.featuretableHeaders()[col++], Price.ENTITYNAME);

		//-----------------------------------------------
		// From Date column
		//-----------------------------------------------
		Column<Price, Date> date = new Column<Price, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( Price price ) {return Time.getDateClient(price.getDate());}
		};
		featureTable.addDateColumn(date, CONSTANTS.featuretableHeaders()[col++], Price.DATE);

		//-----------------------------------------------
		// To Date column
		//-----------------------------------------------
		Column<Price, Date> todate = new Column<Price, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( Price price ) {return Time.getDateClient(price.getTodate());}
		};
		featureTable.addDateColumn(todate, CONSTANTS.featuretableHeaders()[col++], Price.TODATE);

		//-----------------------------------------------
		// Price column
		//-----------------------------------------------
		Column<Price, Number> price = new Column<Price, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( Price price ) {return price.getValue();}
		};
		featureTable.addNumberColumn( price, CONSTANTS.featuretableHeaders()[col++], Price.VALUE);

		//-----------------------------------------------
		// Cost column
		//-----------------------------------------------
//		Column<Price, Number> cost = new Column<Price, Number>(new NumberCell(AbstractField.AF)) {
//			@Override
//			public Double getValue( Price price ) {return price.getCost();}
//		};
//		featureTable.addNumberColumn( cost, CONSTANTS.featuretableHeaders()[col++], Price.COST);

		//-----------------------------------------------
		// Currency column
		//-----------------------------------------------
		Column<Price, String> currency = new Column<Price, String>( new TextCell()) {
			@Override
			public String getValue( Price price ) {return price.getCurrency();}
		};
		featureTable.addStringColumn(currency, CONSTANTS.featuretableHeaders()[col++]);

		//-----------------------------------------------
		// Entity Type column
		//-----------------------------------------------
		Column<Price, String> mandatory = new Column<Price, String>( new TextCell()) {
			@Override
			public String getValue( Price price ) {return price.getEntitytype();}
		};
		featureTable.addStringColumn(mandatory, CONSTANTS.featuretableHeaders()[col++]);

		panel.add(featureTable);
		return panel;
	}

	/* 
	 * Creates the panel displayed when there is no service price list.
	 * 
	 * @return the empty panel.
	 */
	private Widget servicepricetableEmpty() {
		FlowPanel panel = new FlowPanel();

		Button emptyButton = new Button(AbstractField.CONSTANTS.allCreate());
		emptyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (productField.noValue()) {AbstractField.addMessage(Level.TERSE, CONSTANTS.productError(), productField);}
				else {ServicepricePopup.getInstance().show(productField.getValue(), servicepriceTable);}
			}
		});
		panel.add(emptyButton);

		panel.add(new HTML(CONSTANTS.servicepricetableEmpty()));
		panel.add(new Image(BUNDLE.servicepricetableEmpty()));
		return panel;
	}

	/* 
	 * Creates the service price list panel.
	 * 
	 * @return the service price list panel.
	 */
	private TableField<Price> createServiceprice() {
		//-----------------------------------------------
		// Selection model
		//-----------------------------------------------
		final NoSelectionModel<Price> selectionModel = new NoSelectionModel<Price>();
		SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				ServicepricePopup.getInstance().show(selectionModel.getLastSelectedObject(), servicepriceTable);
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// Service Price table
		//-----------------------------------------------
		servicepriceTable = new TableField<Price>(this, null,
				new ServicePriceTable(),
				CONSTANTS.servicepriceLabel(),
				SERVICEPRICE_ROWS,
				tab++);

		servicepriceTable.setEmptyStyle(CSS.servicepriceEmpty());
		servicepriceTable.setEmptyValue(servicepricetableEmpty());
		servicepriceTable.setOrderby(Price.DATE);
		servicepriceTable.setLabelStyle(CSS.servicepriceLabel());

		servicepriceTable.setTableError(new TableError() {
			@Override
			public boolean error() {return (
					productField.noValue()
			);}
		});

		servicepriceTable.setTableExecutor(new TableExecutor<ServicePriceTable>() {
			public void execute(ServicePriceTable action) {
				action.setEntitytype(NameId.Type.Product.name());
				action.setEntityid(productField.getValue());
			}
		});

		int col = 0;

		//-----------------------------------------------
		// Change Service Price button
		//-----------------------------------------------
		Column<Price, Price> changeButton = new Column<Price, Price> ( 
				new ActionCell<Price>(AbstractField.CONSTANTS.allChange(), AbstractField.CSS.cbtTableFieldChangeButton(),
						new Delegate<Price>(){
					public void execute( Price price ){
						ServicepricePopup.getInstance().show(price, servicepriceTable);
					}
				}
				)
		)
		{
			public Price getValue(Price price){return price;}
		};

		//-----------------------------------------------
		// Create Service Price button
		//-----------------------------------------------
		ActionHeader<Price> createButton = new ActionHeader<Price>(
				new ActionCell<Price>(
						AbstractField.CONSTANTS.allCreate(),
						AbstractField.CSS.cbtTableFieldCreateButton(),
						new Delegate<Price>(){
							public void execute(Price price ) {
								if (productField.noValue()) {AbstractField.addMessage(Level.TERSE, CONSTANTS.productError(), productField);}
								else {ServicepricePopup.getInstance().show(productField.getValue(), servicepriceTable);}
							}
						}
				)
		)
		{
			public Price getValue(Price price){
				return price;
			}
		};

		servicepriceTable.addColumn(changeButton, createButton);

		//-----------------------------------------------
		// Procedure Name column
		//-----------------------------------------------
		Column<Price, String> name = new Column<Price, String>(new TextCell()) {
			@Override
			public String getValue( Price price ) {return price.getName();}
		};
		servicepriceTable.addStringColumn(name, CONSTANTS.servicepricetableHeaders()[col++]);

		//-----------------------------------------------
		// From Date column
		//-----------------------------------------------
		Column<Price, Date> date = new Column<Price, Date>(new DateCell(AbstractRoot.getSDF())) {
			@Override
			public Date getValue( Price price ) {return Time.getDateClient(price.getDate());}
		};
		servicepriceTable.addDateColumn(date, CONSTANTS.servicepricetableHeaders()[col++], Price.DATE);

		//-----------------------------------------------
		// To Date column
		//-----------------------------------------------
		Column<Price, Date> todate = new Column<Price, Date>(new DateCell(AbstractRoot.getSDF())) {
			@Override
			public Date getValue( Price price ) {return Time.getDateClient(price.getTodate());}
		};
		servicepriceTable.addDateColumn(todate, CONSTANTS.servicepricetableHeaders()[col++], Price.TODATE);

		//-----------------------------------------------
		// Price column
		//-----------------------------------------------
		Column<Price, Number> price = new Column<Price, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( Price price ) {return price.getValue();}
		};
		servicepriceTable.addNumberColumn( price, CONSTANTS.servicepricetableHeaders()[col++], Price.VALUE);

		//-----------------------------------------------
		// Supplier column
		//-----------------------------------------------
		Column<Price, String> partyname = new Column<Price, String>(new TextCell()) {
			@Override
			public String getValue( Price price ) {return price.getPartyname();}
		};
		servicepriceTable.addStringColumn(partyname, CONSTANTS.servicepricetableHeaders()[col++]);

		return servicepriceTable;
	}

	/* Gets the target widget for product name messages. */
	private Widget getTargetField() {
		if (newnameField.isVisible()) {return newnameField;}
		else {return productField;}
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Copy Product
		//-----------------------------------------------
		productCopy = new GuardedRequest<Product>() {
			protected String popup() {return hasChanged() ? AbstractField.CONSTANTS.errChangeOK() : null;}
			public void cancel() {state = oldstate;}
			protected boolean error() {return AbstractRoot.noOrganizationid();}
			protected void send() {super.send(getValue(new ProductCopy()));}
			protected void receive(Product product) {
				setValue(product);				
				AbstractField.addMessage(Level.TERSE, CONSTANTS.productCopy(), newnameField);
			}
		};

		//-----------------------------------------------
		// Create Product
		//-----------------------------------------------
		productCreate = new GuardedRequest<Product>() {
			protected String popup() {return hasChanged() ? AbstractField.CONSTANTS.errChangeOK() : null;}
			public void cancel() {state = oldstate;}
			protected boolean error() {return AbstractRoot.noOrganizationid();}
			protected void send() {
				onReset(Product.INITIAL);
				super.send(getValue(new ProductCreate()));
			}
			protected void receive(Product product) {setValue(product);}
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
						ifMessage(noState(), Level.TERSE, AbstractField.CONSTANTS.errState(), getTargetField())
						|| ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), getTargetField())
						|| ifMessage(AbstractRoot.noCurrency(), Level.TERSE, AbstractField.CONSTANTS.errCurrency(), getTargetField())
						|| ifMessage(productField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), getTargetField())
						|| ifMessage(locationField.noValue(), Level.TERSE, CONSTANTS.locationError(), locationField)
						|| ifMessage(ownerField.noValue(), Level.TERSE, CONSTANTS.ownerError(), ownerField)
						|| ifMessage(stateField.noValue(), Level.TERSE, CONSTANTS.stateError(), stateField)
						|| ifMessage(partofField.hasValue(newnameField.getValue()), Level.TERSE, CONSTANTS.partofError(), partofField)
						|| ifMessage(mapField.hasDefaultValue(), Level.TERSE, CONSTANTS.mapError(), mapField)
						|| ifMessage(partofField.hasValue() && codeField.noValue(), Level.TERSE, CONSTANTS.codeError(), codeField)
				);
			}
			protected void send() {super.send(getValue(new ProductUpdate()));}
			protected void receive(Product product){setValue(product);}
		};

		//-----------------------------------------------
		// Delete Product
		//-----------------------------------------------
		productDelete = new GuardedRequest<Product>() {
			protected String popup() {return AbstractField.CONSTANTS.errDeleteOK();}
			public void cancel() {state = oldstate;}
			protected boolean error() {return ifMessage(productField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), getTargetField());}
			protected void send() {super.send(getValue(new ProductDelete()));}
			protected void receive(Product product){
				productField.onRefresh();
				partofField.onRefresh();
				setValue(product);
			}
		};
	}

	/* Gets the total progress towards completion of the form. */
	private int totalProgress() {
		if (productField.noValue() && newnameField.noValue()) {return 0;}
		double progress = 0;
		progress += dataProgress() * 0.05;
		progress += attributeProgress() * 0.05;
		progress += descriptionProgress() * 0.15;
		progress += contentsProgress() * 0.05;
		progress += optionsProgress() * 0.05;
		progress += mapProgress() * 0.15;
		progress += imageProgress() * 0.30;
		progress += priceProgress() * 0.15;
		progress += yieldProgress() * 0.05;
		return (int) progress;
	}

	private double dataProgress() {
		double progress = 0;
		progress += productField.noValue() ? 0.0 : 25.0;
		progress += ownerField.noValue() ? 0.0 : 25.0;
		progress += discountField.noValue() ? 0.0 : 25.0;
		progress += commissionField.noValue() ? 0.0 : 25.0;
		return progress;
	}

	private double attributeProgress() {
		if (attributeField.noValue()) {return 0.0;}
		else {return Math.min(100.0, attributeField.getValue().values().size() * 25.0);}
	}

	private double descriptionProgress() {
		if (descriptionField.noValue() || descriptionField.hasDefaultValue()) {return 0.0;}
		else {return Math.min(100.0, descriptionField.getValue().length() / 3.0);}
	}

	private double contentsProgress() {
		if (contentsField.noValue() || contentsField.hasDefaultValue()) {return 0.0;}
		else {return Math.min(100.0, contentsField.getValue().length());}
	}

	private double optionsProgress() {
		if (optionsField.noValue() || optionsField.hasDefaultValue()) {return 0.0;}
		else {return Math.min(100.0, optionsField.getValue().length());}
	}

	private double mapProgress() {
		if (mapField.noValue() || mapField.hasDefaultValue()) {return 0.0;}
		else {return mapField.isDefaultValue(mapField.getValue(), 0.00001)? 0.0 : 100.0;}
	}

	private double imageProgress() {
		if (imageGallery.noValue()) {return 0.0;}
		else {return Math.min(100.0, imageGallery.getValue().size() * 10.0);}
	}

	private double priceProgress() {
		ArrayList<Price> prices = priceTable.getValue();
		for (Price price : prices) {
			if (price.hasTodate() && price.getTodate().after(Time.addDuration(new Date(), 180.0, Time.DAY))) {return 100.0;}
		}
		return 0.0;
	}

	private double yieldProgress() {
		if (yieldTable.noValue()) {return 0.0;}
		else {return Math.min(100.0, yieldTable.getValue().size() * 20.0);}
	}

	private void triggerResize() {
		if(mapField != null) {
			mapField.triggerResize();
		}
	}
	
	/* Inner Class ProgressPopup is to display progress detail in a pop up panel. */
	public class ProgressPopup extends PopupPanel {

		/** Instantiates a new progress pop up panel. */
		public ProgressPopup() {
			super(true);
			AbstractField.CSS.ensureInjected();
			CSS.ensureInjected();

			final VerticalPanel form = new VerticalPanel();
			final Label title = new Label(CONSTANTS.progressTitle());
			title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
			form.add(title);

			final Label closeButton = new Label();
			closeButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ProgressPopup.this.hide();
				}
			});
			closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
			form.add(closeButton);

			ProgressField dataprogressField = new ProgressField(ProductForm.this, null, CONSTANTS.dataprogressLabel(), 0, 100, tab);
			dataprogressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			dataprogressField.setValue((int)dataProgress());
			form.add(dataprogressField);

			ProgressField attributeprogressField = new ProgressField(ProductForm.this, null, CONSTANTS.attributeprogressLabel(), 0, 100, tab);
			attributeprogressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			attributeprogressField.setValue((int)attributeProgress());
			form.add(attributeprogressField);

			ProgressField descriptionprogressField = new ProgressField(ProductForm.this, null, CONSTANTS.descriptionprogressLabel(), 0, 100, tab);
			descriptionprogressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			descriptionprogressField.setValue((int)descriptionProgress());
			form.add(descriptionprogressField);

			ProgressField contentsprogressField = new ProgressField(ProductForm.this, null, CONSTANTS.contentsprogressLabel(), 0, 100, tab);
			contentsprogressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			contentsprogressField.setValue((int)contentsProgress());
			form.add(contentsprogressField);

			ProgressField optionsprogressField = new ProgressField(ProductForm.this, null, CONSTANTS.optionsprogressLabel(), 0, 100, tab);
			optionsprogressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			optionsprogressField.setValue((int)optionsProgress());
			form.add(optionsprogressField);

			ProgressField mapprogressField = new ProgressField(ProductForm.this, null, CONSTANTS.mapprogressLabel(), 0, 100, tab);
			mapprogressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			mapprogressField.setValue((int)mapProgress());
			form.add(mapprogressField);

			ProgressField imageprogressField = new ProgressField(ProductForm.this, null, CONSTANTS.imageprogressLabel(), 0, 100, tab);
			imageprogressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			imageprogressField.setValue((int)imageProgress());
			form.add(imageprogressField);

			ProgressField priceprogressField = new ProgressField(ProductForm.this, null, CONSTANTS.priceprogressLabel(), 0, 100, tab);
			priceprogressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			priceprogressField.setValue((int)priceProgress());
			form.add(priceprogressField);

			ProgressField yieldprogressField = new ProgressField(ProductForm.this, null, CONSTANTS.yieldprogressLabel(), 0, 100, tab);
			yieldprogressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
			yieldprogressField.setValue((int)yieldProgress());
			form.add(yieldprogressField);

			setWidget(form);
			setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
		}
	}
}
