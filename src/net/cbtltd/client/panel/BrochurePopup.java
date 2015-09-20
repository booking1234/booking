/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.panel;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.Component;
import net.cbtltd.client.Components;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.LocalRequest;
import net.cbtltd.client.Razor;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.CalendarWidget;
import net.cbtltd.client.field.CommandButton;
import net.cbtltd.client.field.DatespanField;
import net.cbtltd.client.field.DoubleunitField;
import net.cbtltd.client.field.ImageField;
import net.cbtltd.client.field.LabelField;
import net.cbtltd.client.field.MapField;
import net.cbtltd.client.field.MessagePanel;
import net.cbtltd.client.field.RouteField;
import net.cbtltd.client.field.SelectorField;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.field.ToggleField;
import net.cbtltd.client.field.table.ActionHeader;
import net.cbtltd.client.field.table.ListCell;
import net.cbtltd.client.field.table.TableError;
import net.cbtltd.client.field.table.TableExecutor;
import net.cbtltd.client.field.visualization.ColumnChartField;
import net.cbtltd.client.field.visualization.LineChartField;
import net.cbtltd.client.form.AccessControl;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.Hosts;
import net.cbtltd.client.resource.brochure.BrochureBundle;
import net.cbtltd.client.resource.brochure.BrochureConstants;
import net.cbtltd.client.resource.brochure.BrochureCssResource;
import net.cbtltd.shared.Alert;
import net.cbtltd.shared.Attribute;
import net.cbtltd.shared.AttributeMapAction;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Event;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Rate;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.finance.CurrencyrateNameId;
import net.cbtltd.shared.price.PriceTableConverted;
import net.cbtltd.shared.product.ProductRead;
import net.cbtltd.shared.rate.RateColumnChart;
import net.cbtltd.shared.rate.RateLineChart;
import net.cbtltd.shared.rate.RateNote;
import net.cbtltd.shared.rate.RateNoteTable;
import net.cbtltd.shared.reservation.AvailableItem;
import net.cbtltd.shared.reservation.Brochure;
import net.cbtltd.shared.reservation.BrochurePrice;
import net.cbtltd.shared.reservation.BrochureProduct;
import net.cbtltd.shared.reservation.BrochureRead;
import net.cbtltd.shared.reservation.BrochureUpdate;
import net.cbtltd.shared.reservation.PriceResponse;
import net.cbtltd.shared.task.Contact;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.LegendPosition;
import com.mybookingpal.config.RazorConfig;

/** The Class BrochurePopup is to display product brochures. */
public class BrochurePopup
extends AbstractPopup<Brochure> {

	private static final BrochureConstants CONSTANTS = GWT.create(BrochureConstants.class);
	private static final Hosts HOSTS = GWT.create(Hosts.class);
	private static final BrochureBundle BUNDLE = BrochureBundle.INSTANCE;
	private static final BrochureCssResource CSS = BUNDLE.css();

	private static final HorizontalPanel commandBar = new HorizontalPanel();

	private static GuardedRequest<PriceResponse> brochurePrice;
	private static GuardedRequest<Brochure> brochureProduct;
	private static GuardedRequest<Brochure> brochureRead;
	private static GuardedRequest<Brochure> brochureSend;
	private static GuardedRequest<Product> productRead;
	private static GuardedRequest<Table<RateNote>> ratenotesRequest;

	private static FlowPanel selectPanel;
	private static SuggestField customerField;
	private static ToggleField priceunitField; //true = night false = stay
	private static DoubleunitField quoteField;
	private static DatespanField fromtodateField;
	private static Label nameLabel;
	private static Image ratingField;
	private static LabelField descriptionField;
	private static LabelField contentsField;
	private static LabelField optionsField;
	private static LabelField termsField;
	private static LabelField managerField;
	private static TextBox urlField;
	private static VerticalPanel contentsPanel;
	private static SelectorField attributeField;
	private static CalendarWidget calendarWidget;
	private static MapField mapField;
	private static ImageField imageField;
	private static ScrollPanel reviewPanel;
	private static Grid guestComments;
	private static TableField<Price> priceTable;
	private static ListCell<Price> currencyList;
	private static TextField fromtextField;
	private static RouteField routeField; 
	private static TextAreaField notesField;

	private static ArrayList<AvailableItem> availableitems;
	private static int selectedindex = -1;
	private static String id; //brochure event id or product id
	private static String agentId; // brochure product agent id
	private Double latitude = 0.0;
	private void setLatitude(Double latitude) {this.latitude = latitude;}
	private Double longitude = 0.0;
	private void setLongitude(Double longitude) {this.longitude = longitude;}

	private static String getAlertlist(ArrayList<Alert> alerts) {
		if (alerts == null || alerts.isEmpty()) {return null;}
		StringBuilder sb =  new StringBuilder();
		for (Alert alert : alerts) {sb.append(AbstractRoot.getDF().format(alert.getFromdate())).append(" - ").append(AbstractRoot.getDF().format(alert.getTodate())).append(" ").append(alert.getName()).append("\n");}
		return sb.toString();
	}

	/** Instantiates a new brochure pop up panel. */
	public BrochurePopup() {
		super(false);
		AbstractField.CSS.ensureInjected();
		CSS.ensureInjected();
		final FlowPanel panel = new FlowPanel();
		setWidget(panel);
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());

		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				BrochurePopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		panel.add(closeButton);

		createActions();
		final HorizontalPanel content = new HorizontalPanel();
		panel.add(content);

		final FlowPanel controlPanel = createControl();
		content.add(controlPanel);
		final FlowPanel brochure = new FlowPanel();
		content.add(brochure);
		brochure.add(createHeader());
		brochure.add(createProduct());
//		onRefresh(); //VIP
//		onReset(Brochure.CREATED);
	}

	private static BrochurePopup instance = null;

	/**
	 * Gets the single instance of BrochurePopup.
	 *
	 * @return single instance of BrochurePopup
	 */
	public static synchronized BrochurePopup getInstance() {
		if (instance == null) {instance = new BrochurePopup();}
		instance.onRefresh(); //VIP
		instance.onReset(Brochure.CREATED);
		return instance;
	}

	/**
	 * Shows the panel for a brochure of the specified items.
	 *
	 * @param availableitems the list of specified items.
	 */
	public void show(ArrayList<AvailableItem> availableitems) {
		setAvailableItems(availableitems, 0);
		triggerResize();
	}

	/**
	 * Shows the panel for a brochure of the specified item.
	 *
	 * @param availableitem the specified item.
	 */
	public void show(AvailableItem availableitem) {
		setAvailableItem(availableitem);
		triggerResize();
	}

	/**
	 * Shows the panel for a brochure of the specified brochure.
	 *
	 * @param brochureid the ID of the brochure.
	 * @param organizationid the ID of the company showing the brochure.
	 */
	public void show(String brochureid) {
		BrochurePopup.id = brochureid;
		brochureRead.execute(true);
		triggerResize();
	}

	/**
	 * Shows the panel for a brochure of the specified product in the specified state.
	 *
	 * @param productid the ID of the product.
	 * @param state the specified state.
	 */
	public void show(String productid, String pos, String state) {
		BrochurePopup.id = productid;
		this.agentId = pos;
		this.state = state;
		brochureProduct.execute();
		triggerResize();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {
		if (priceunitField.getValue() && fromtodateField.getDuration(Time.DAY) < 1) {
			priceunitField.setValue(false);
			AbstractField.addMessage(Level.ERROR, CONSTANTS.dateRangeError(), fromtodateField);
		}

//		if (customerField.sent(change)) {customerRead.execute();}
		if (fromtodateField.sent(change) && fromtodateField.getDuration(Time.DAY) > 0) {
			getAvailableitem().setFromdate(fromtodateField.getValue());
			getAvailableitem().setTodate(fromtodateField.getTovalue());
			getAvailableitem().setOrderby(AvailableItem.FROMDATE);
			getAvailableitem().setState(null);
			calendarWidget.setValue(getAvailableitem());
		}
		else if (calendarWidget.sent(change)) {
			AvailableItem item = calendarWidget.getValue();
			if (item == null) {
				fromtodateField.setValue(calendarWidget.getMonthFirstDay());
				fromtodateField.setTovalue(calendarWidget.getMonthFirstDay());
			}
			else {
				fromtodateField.setValue(item.getFromdate());
				fromtodateField.setTovalue(item.getTodate());
			}
		}

		if (priceunitField.sent(change)	
				|| quoteField.sent(change)
				|| fromtodateField.sent(change)
				|| calendarWidget.sent(change)) {brochurePrice.execute();}

		contentsField.resetLanguage(descriptionField.getLanguage());
		optionsField.resetLanguage(descriptionField.getLanguage());
//		termsField.resetLanguage(descriptionField.getLanguage());
		managerField.resetLanguage(descriptionField.getLanguage());
		triggerResize();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue()
	 */

	public Brochure getValue() {return getValue(new Brochure());}
	
	public static boolean noAgentId() {
		return agentId == null || agentId.isEmpty();
	}
	
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue(java.lang.Object)
	 */
	/**
	 * Gets the value.
	 *
	 * @param brochure the brochure
	 * @return the value
	 */
	public Brochure getValue(Brochure brochure) {
		brochure.setState(state);
		brochure.setId(id);
		brochure.setOrganizationid(AbstractRoot.getOrganizationid());
		brochure.setActorid(AbstractRoot.getActorid());
		brochure.setActivity(NameId.Type.Task.name());
		brochure.setCustomerid(customerField.getValue());
		brochure.setCurrency(quoteField.getUnitvalue());
		brochure.setDate(new Date());
		brochure.setDuedate(Time.addDuration(brochure.getDate(), 2, Time.DAY));
		brochure.setNotes(notesField.getValue());
		brochure.setPriceunit(priceunitField.getValue());
		brochure.setType(Contact.Type.Email.name());
		brochure.setAvailableitems(availableitems);
		Log.debug("getValue " + brochure);
		return brochure;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(Brochure brochure) {
		Log.debug("setValue " + brochure);
		if (brochure == null) {return;}
		onStateChange(brochure.getState());
		id = brochure.getId();
		if (AbstractRoot.noOrganizationid()) {AbstractRoot.setOrganizationid(brochure.getOrganizationid());}
		customerField.setValue(brochure.getCustomerid());
		quoteField.setUnitvalue(brochure.getCurrency());
		notesField.setValue(brochure.getNotes());
		priceunitField.setValue(brochure.getPriceunit());
		setAvailableItems(brochure.getAvailableitems(), 0);
		center();
	}

	/**
	 * Sets the product value.
	 *
	 * @param product the new value.
	 */
	public void setValue(Product product) {
		//Window.alert("BrochureForm setValue " + product);
		setResetting(true);
		nameLabel.setText(product.getName());
		setRating(product.getRating());
		setLatitude(product.getLatitude());
		setLongitude(product.getLongitude());
		attributeField.setValue(product.getAttributemap());
		attributeField.setLabel(CONSTANTS.attributeLabel());
		mapField.setValue(LatLng.newInstance(product.getLatitude(),product.getLongitude())); //product.getLatLng());
		ArrayList<String> originImages = new ArrayList<String>(); 
		for (int i = 0; i < product.getImageurls().size(); i++) {
			if (product.getImageurls().get(i).contains(Text.THUMBNAIL_INFIX)){
				String originUrl = product.getImageurls().get(i).replace(Text.THUMBNAIL_INFIX, "");
				originImages.add(originUrl);
			}
		}
		imageField.setValue(originImages);
		descriptionField.setText(product.getPublicText());
		contentsField.setText(product.getContentsText());
		optionsField.setText(product.getOptionsText());
		urlField.setValue(HOSTS.productUrl() + product.getId() + (AbstractRoot.noOrganizationid() ? "" : "&pos=" + Model.encrypt(AbstractRoot.getOrganizationid())));
		setResetting(false);
		center();
	}

	/*
	 * Sets the rating.
	 *
	 * @param product the new rating.
	 */
	private void setRating(Integer rating) {
		if (rating == null) {rating = 5;}
		ratingField.setUrl(AbstractField.STARS[rating].getURL());
	}

	/**
	 * Gets the brochure price value.
	 *
	 * @param action the action
	 * @return the value
	 */
	public BrochurePrice getValue(BrochurePrice action) {
		action.setAgentid(AbstractRoot.getOrganizationid());
		action.setTocurrency(quoteField.getUnitvalue());
		action.setFromdate(fromtodateField.getValue());
		action.setTodate(fromtodateField.getTovalue());
		action.setPriceunit(priceunitField.getValue());
		action.setProductid(getAvailableitem().getProductid());
		action.setSupplierid(getAvailableitem().getSupplierid());
		action.setUnit(getAvailableitem().getUnit());
//		Window.alert("getValue " + action);
		return action;
	}
	
	/*
	 * Gets the specified rate with its attribute set.
	 *
	 * @param action the specified rate.
	 * @return the rate with its attribute set.
	 */
	private Rate getValue(Rate rate) {
		rate.setProductid(getAvailableitem().getProductid());
		return rate;
	}

	/*
	 * Updates table of available products
	 * 
	 * @param availableitem to populate the brochure
	 */
	private static AvailableItem getAvailableitem() {
		return noAvailableitem() ? new AvailableItem() : availableitems.get(selectedindex);
	}

	/* Checks if there are no items in the brochure.
	 * 
	 * @return true, if there are no items in the brochure.
	 */
	private static boolean noAvailableitem() {
		return availableitems == null 
		|| availableitems.isEmpty() 
		|| availableitems.get(0).getProductid() == null
		|| availableitems.get(0).getProductid().isEmpty()
		|| selectedindex < 0;
	}

	/* Checks if there are any items in the brochure.
	 * 
	 * @return true, if there are any items in the brochure.
	 */
	private static boolean hasAvailableitem() {
		return !noAvailableitem();
	}
	
	/**
	 * Sets the brochure item.
	 *
	 * @param availableitem the new brochure item.
	 */
	public void setAvailableItem(AvailableItem availableitem) {
		if (availableitem == null) {Window.alert("setAvailableItem null");}
		if (availableitem.noReservationid()) {
			availableitem.setCurrency(AbstractRoot.getCurrency());
			availableitem.setTodate(new Date());
			availableitem.setFromdate(new Date());
		}
		availableitems = new ArrayList<AvailableItem>();
		availableitems.add(availableitem);
		setAvailableItems(availableitems, 0);
	}

	/**
	 * Updates the table of brochure products.
	 *
	 * @param availableitems the list of items to populate the table.
	 * @param selectedindex the index of the selected item.
	 */
	public void setAvailableItems(ArrayList<AvailableItem> availableitems, int selectedindex) {
		BrochurePopup.availableitems = availableitems;
		BrochurePopup.selectedindex = selectedindex;
		selectPanel.clear();
		if (noAvailableitem()) {return;}

		int index = 0;

		for (AvailableItem availableitem : availableitems) {
//			String quote = AbstractField.AF.format(availableitem.getQuote());
			final ProductLabel productName = new ProductLabel(availableitem.getProductname(40), index, new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					selectAvailableItem(((ProductLabel)event.getSource()).getIndex());
				}
			});

			productName.addStyleName(CSS.availableProduct());
			selectPanel.add(productName);
			index++;
		}
		selectAvailableItem(selectedindex);
		center();
	}

	/*
	 * Sets the selected item to the specified index.
	 * 
	 * @param index the specified index.
	 */
	private void selectAvailableItem(int index) {
		selectedindex = index;
		if (noAvailableitem()) {return;}
		
		productRead.execute();
		priceTable.execute();
		ratenotesRequest.execute(true);

		if (!getAvailableitem().noAgentid()){  						
			termsField.setText(Party.getContractText(getAvailableitem().getAgentid(), Language.EN));
			managerField.setText(Party.getPublicText(getAvailableitem().getAgentid(), Language.EN));
		} else if (!BrochurePopup.noAgentId()){
			termsField.setText(Party.getContractText(agentId, Language.EN));
			managerField.setText(Party.getPublicText(agentId, Language.EN));
		}
		
		for (int i = 0; i < selectPanel.getWidgetCount(); i++) {selectPanel.getWidget(i).removeStyleName(AbstractField.CSS.cbtGradientHead());}
		selectPanel.getWidget(index).addStyleName(AbstractField.CSS.cbtGradientHead());

		quoteField.setValue(getAvailableitem().getQuote());
		quoteField.setDefaultUnitvalue(getAvailableitem().getCurrency());
		quoteField.setUnitvalue(getAvailableitem().getCurrency());
		priceunitField.setValue(getAvailableitem().getPriceunit());
		fromtodateField.setValue(getAvailableitem().getFromdate());
		fromtodateField.setTovalue(getAvailableitem().getTodate());
		calendarWidget.setValue(getAvailableitem());
	}
	
	/* Inner Class ProductLabel extends Label to include an index field. */
	private class ProductLabel extends Label {
		private final int index;

		public ProductLabel(String text, int index, ClickHandler handler) {
			super(text);
			super.addClickHandler(handler);
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	}

	/*
	 * Updates the table of guest comments.
	 * 
	 * @param nameids the guest comments to populate the table.
	 */
	private void setGuestComments(ArrayList<RateNote> notes) {

		Grid.CellFormatter fmtRow = guestComments.getCellFormatter();
		guestComments.clear();
		int row = 0;
		if (notes == null || notes.isEmpty()) {
			guestComments.resize(1, 1);
			guestComments.setText(row++, 0, CONSTANTS.guestcommentNone());
		}
		else {
			guestComments.resize(notes.size() * 2, 1);
			for (RateNote note : notes) {
				fmtRow.addStyleName(row, 0, CSS.guestcommentLabel());
				fmtRow.addStyleName(row, 0, AbstractField.CSS.cbtGradientHead());
				guestComments.setText(row++, 0, CONSTANTS.guestCommentDate() + AbstractRoot.getDF().format(note.getDate()));
				fmtRow.addStyleName(row, 0, CSS.guestcommentField());
				guestComments.setText(row++, 0, note.getNotes());
			}
		}
	}

	/* Constructs brochure selector. */
	private FlowPanel createControl() {
		FlowPanel form = new FlowPanel();
		form.addStyleName(AbstractField.CSS.cbtAbstractControl());
		form.addStyleName(CSS.controlStyle());

		//-----------------------------------------------
		// Customer (Guest) field
		//-----------------------------------------------
		customerField = new SuggestField(this,  null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.customerLabel(),
				20,
				tab++);
		customerField.setType(Party.Type.Customer.name());
		customerField.setHelpText(CONSTANTS.customerHelp());

		Image customerButton = new Image(AbstractField.BUNDLE.plus());
		customerButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PartyPopup.getInstance().show(Party.Type.Customer, customerField, null);
			}
		});
		customerField.addButton(customerButton);
		form.add(customerField);

		//-----------------------------------------------
		// Special Requests
		//-----------------------------------------------
		notesField = new TextAreaField(this, null,
				CONSTANTS.notesLabel(),
				tab++);
		notesField.setFieldStyle(CSS.notesField());
		notesField.setMaxLength(3000);
		notesField.setHelpText(CONSTANTS.notesHelp());
		form.add(notesField);

		//-----------------------------------------------
		// From Date is the date of arrival
		//-----------------------------------------------
		fromtodateField = new DatespanField(this, null,
				CONSTANTS.fromtodateLabel(),
				tab++);
		fromtodateField.setDefaultDuration(1.0);
		fromtodateField.setHelpText(CONSTANTS.fromtodateHelp());
		form.add(fromtodateField);

		//-----------------------------------------------
		// Price range unit field between stay/night
		//-----------------------------------------------
		priceunitField = new ToggleField(this, null,
				CONSTANTS.priceunitLabel(),
				CONSTANTS.priceunitNight(),
				CONSTANTS.priceunitStay(),
				tab++);
		priceunitField.setHelpText(CONSTANTS.priceunitHelp());
		priceunitField.setFieldStyle(CSS.priceunitField());
		form.add(priceunitField);

		//-----------------------------------------------
		// Quote field
		//-----------------------------------------------
		quoteField = new DoubleunitField(this, null,
//				NameId.getList(Currency.EXCHANGE_CURRENCY_NAMES, Currency.EXCHANGE_CURRENCIES),
				new CurrencyrateNameId(),
				null,
				AbstractField.AF,
				tab++);
		quoteField.setDefaultUnitvalue(AbstractRoot.getCurrency());
		quoteField.setFieldStyle(CSS.currencyField());
		quoteField.setUnitStyle(CSS.currencyField());
		form.add(quoteField);
		
		//-----------------------------------------------
		// Product selector
		//-----------------------------------------------
		final Label selectLabel = new Label(CONSTANTS.selectLabel());
		selectLabel.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		selectLabel.addStyleName(CSS.titleStyle());
		selectLabel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				new MessagePanel(Level.VERBOSE, CONSTANTS.selectHelp()).showRelativeTo(selectPanel);
			}
		});
		form.add(selectLabel);

		ScrollPanel scroll = new ScrollPanel();
		form.add(scroll);
		selectPanel = new FlowPanel();
		scroll.addStyleName(CSS.availableStyle());
		scroll.add(selectPanel);

		//-----------------------------------------------
		// Command bar
		//-----------------------------------------------
		commandBar.addStyleName(AbstractField.CSS.cbtAbstractCommand());
		form.add(commandBar);

		//-----------------------------------------------
		// Action to remove an item from the available list.
		//-----------------------------------------------
		final LocalRequest removeRequest = new LocalRequest() {
			protected void perform() {
				if (availableitems == null || availableitems.size() <= 1) {AbstractField.addMessage(Level.ERROR, CONSTANTS.removebuttonError(), selectPanel);}
				else {
					availableitems.remove(selectedindex);
					setAvailableItems(availableitems, 0);
				}
			}
		};

		//-----------------------------------------------
		// Button to remove an item from the available list.
		//-----------------------------------------------
		final CommandButton removeButton = new CommandButton(this, CONSTANTS.removeButton(), removeRequest, tab++);
		removeButton.addStyleName(AbstractField.CSS.cbtCommandButtonFour());
		removeButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		commandBar.add(removeButton);

		//-----------------------------------------------
		// Button to send the brochure to the customer.
		//-----------------------------------------------
		final CommandButton sendButton = new CommandButton(this, CONSTANTS.sendButton(), brochureSend, tab++);
		sendButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		commandBar.add(sendButton);

		//-----------------------------------------------
		// Action to book the selected item.
		//-----------------------------------------------
		final LocalRequest bookRequest = new LocalRequest() {
			protected boolean error() {
				return 	ifMessage(fromtodateField.getDuration(Time.DAY) < 1.0, Level.ERROR, CONSTANTS.fromtodateError(), fromtodateField);
			}
			protected void perform() {
				Date fromdate = Time.getDateServer(fromtodateField.getValue());
				Date todate = Time.getDateServer(fromtodateField.getTovalue());				
				Reservation reservation = getAvailableitem().getReservation(fromdate, todate, AbstractRoot.getActorid(), customerField.getValue(), notesField.getValue());
				if (AbstractRoot.readable(AccessControl.ORGANIZATION_ROLES)) {
					AbstractRoot.render(Razor.RESERVATION_TAB, reservation);
					hide();
				}
				else {ReservationPopup.getInstance().show(reservation, null);}
			}
		};

		//-----------------------------------------------
		// Button to book the selected item.
		//-----------------------------------------------
		final CommandButton bookButton = new CommandButton(this, CONSTANTS.bookButton(), bookRequest, tab++);
		bookButton.addStyleName(AbstractField.CSS.cbtCommandButtonFour());
		bookButton.addStyleName(AbstractField.CSS.cbtGradientGreen());
		commandBar.add(bookButton);

		//-----------------------------------------------
		// Transition array to define the finite state machine
		//-----------------------------------------------
		fsm = new ArrayList<Transition>();
		fsm.add(new Transition(Brochure.CREATED, removeButton, Brochure.CREATED));
		fsm.add(new Transition(Brochure.CREATED, sendButton, Brochure.SENT));
		fsm.add(new Transition(Brochure.CREATED, bookButton, Brochure.SENT));

		fsm.add(new Transition(Brochure.SENT, removeButton, Brochure.SENT));
		fsm.add(new Transition(Brochure.SENT, bookButton, Brochure.SENT));

//		loadingImage.setVisible(false);
//		form.add(loadingImage);
		return form;
	}

	/*
	 * Constructs the brochure header.
	 * 
	 * @return the brochure header.
	 */
	private FlowPanel createHeader() {
		FlowPanel panel = new FlowPanel();
		panel.addStyleName(CSS.headerStyle());
		panel.addStyleName(AbstractField.CSS.cbtGradientHead());

		//-----------------------------------------------
		// Product title
		//-----------------------------------------------
		nameLabel = new Label();
		nameLabel.addStyleName(CSS.nameStyle());
		panel.add(nameLabel);

		//-----------------------------------------------
		// Star Rating
		//-----------------------------------------------
		ratingField = new Image(AbstractField.BUNDLE.star4());
		FlowPanel starLabel = new FlowPanel();
		starLabel.add(ratingField);
		starLabel.addStyleName(CSS.ratingStyle());
		panel.add(starLabel);

		//-----------------------------------------------
		// Route
		//-----------------------------------------------
		final Label routeButton = new Label(CONSTANTS.routeButton());
		routeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new RoutePopup().showRelativeTo(nameLabel);
			}
		});
		routeButton.addStyleName(CSS.hyperlink());
		routeButton.setTitle(CONSTANTS.routeLabel());
		panel.add(routeButton);

		//-----------------------------------------------
		// Reviews
		//-----------------------------------------------
		reviewPanel = new ScrollPanel();
		reviewPanel.addStyleName(CSS.reviewPanel());
		guestComments = new Grid();
		reviewPanel.add(guestComments);

		final Label reviewButton = new Label(CONSTANTS.reviewButton());
		reviewButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new ReviewPopup().center();
			}
		});
		reviewButton.addStyleName(CSS.hyperlink());
		reviewButton.setTitle(CONSTANTS.reviewLabel());
		panel.add(reviewButton);

		//-----------------------------------------------
		// Ratings
		//-----------------------------------------------
		final Label ratingButton = new Label(CONSTANTS.ratingButton());
		ratingButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new RatingPopup().display();
			}
		});
		ratingButton.addStyleName(CSS.hyperlink());
		ratingButton.setTitle(CONSTANTS.ratingLabel());
		panel.add(ratingButton);

		//-----------------------------------------------
		// Terms & Conditions
		//-----------------------------------------------
		termsField = new LabelField(this, null,
				CONSTANTS.termsLabel(),
				Language.getTranslatableNameIds(),
//				new LanguageNameId(),
				tab++);
		termsField.setLabelStyle(CSS.contentsLabel());
		termsField.setFieldStyle(CSS.termsStyle());
		termsField.setEnabled(false);

		final Label termsButton = new Label(CONSTANTS.termsButton());
		termsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new TermsPanel().center();
			}
		});
		termsButton.addStyleName(CSS.hyperlink());
		termsButton.setTitle(CONSTANTS.termsLabel());
		panel.add(termsButton);

		//-----------------------------------------------
		// Manager
		//-----------------------------------------------
		managerField = new LabelField(this, null,
				CONSTANTS.managerLabel(),
				Language.getTranslatableNameIds(),
				tab++);
		managerField.setLabelStyle(CSS.contentsLabel());
		managerField.setFieldStyle(CSS.termsStyle());
		managerField.setEnabled(false);

		final Label managerButton = new Label(CONSTANTS.managerButton());
		managerButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (getAvailableitem().hasRank()) {new HeaderPopup(managerField).center();}
				else {AbstractField.addMessage(Level.VERBOSE, CONSTANTS.managerRank(), managerButton);}
			}
		});
		managerButton.addStyleName(CSS.hyperlink());
		managerButton.setTitle(CONSTANTS.managerLabel());
		panel.add(managerButton);

		//-----------------------------------------------
		// Options
		//-----------------------------------------------
		optionsField = new LabelField(this, null,
				CONSTANTS.optionsLabel(),
				Language.getTranslatableNameIds(),
//				new LanguageNameId(),
				tab++);
		optionsField.setLabelStyle(CSS.contentsLabel());
		optionsField.setFieldStyle(CSS.termsStyle());

		//-----------------------------------------------
		// Contents
		//-----------------------------------------------
		contentsField = new LabelField(this, null,
				CONSTANTS.contentsLabel(),
				Language.getTranslatableNameIds(),
//				new LanguageNameId(),
				tab++);
		contentsField.setLabelStyle(CSS.contentsLabel());
		contentsField.setFieldStyle(CSS.termsStyle());

		final Label contentsButton = new Label(CONSTANTS.contentsButton());
		contentsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new ContentsPanel().center();
			}
		});
		contentsButton.addStyleName(CSS.hyperlink());
		contentsButton.setTitle(CONSTANTS.contentsLabel());
		panel.add(contentsButton);

		contentsPanel = new VerticalPanel();
		contentsPanel.add(contentsField);
		contentsPanel.add(new Label(" "));
		contentsPanel.add(optionsField);
		
		//-----------------------------------------------
		// Attributes
		//-----------------------------------------------
		attributeField = new SelectorField(this, null,
				new AttributeMapAction(Attribute.ACCOMMODATION_SEARCH, Attribute.RZ, AbstractRoot.getLanguage()),
				CONSTANTS.attributeLabel(),
				tab++);
		//		attributeField.setUniquekeys(UNIQUE_KEYS);
		attributeField.setFieldStyle(CSS.attributeField());
		attributeField.setLabelStyle(CSS.attributeLabel());
		attributeField.setVisible(false);
		attributeField.setVisibleGrid(true);

		final Label attributeButton = new Label(CONSTANTS.attributeButton());
		attributeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new AttributesPanel().center();
			}
		});
		attributeButton.addStyleName(CSS.hyperlink());
		attributeButton.setTitle(CONSTANTS.attributeLabel());
		panel.add(attributeButton);

		//-----------------------------------------------
		// Rates
		//-----------------------------------------------
		priceTable = createPrice();
		
		final Label priceButton = new Label(CONSTANTS.priceButton());
		priceButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new PricePanel().center();
			}
		});
		priceButton.addStyleName(CSS.hyperlink());
		priceButton.setTitle(CONSTANTS.priceLabel());
		panel.add(priceButton);

		//-----------------------------------------------
		// URL
		//-----------------------------------------------
		
		urlField = new TextBox();
		urlField.addStyleName(CSS.urlField());
		final Label urlButton = new Label(CONSTANTS.urlButton());
		urlButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new HeaderPopup(urlField).center();
			}
		});
		urlButton.addStyleName(CSS.hyperlink());
		urlButton.setTitle(CONSTANTS.urlLabel());
		panel.add(urlButton);

		return panel;
	}

	/* Inner Class HeaderPopup to display header panels. */
	private static class HeaderPopup extends PopupPanel {
		private final FlowPanel panel = new FlowPanel();

		public HeaderPopup(Widget content) {
			super(true);
			setWidget(panel);
			setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());

			final Label closeButton = new Label();
			closeButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					HeaderPopup.this.hide();
				}
			});
			closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
			panel.add(closeButton);
			if (content != null) {panel.add(content);}
		}

		public void add(Widget widget) {
			panel.add(widget);
		}
	}

	/* Inner Class ReviewPopup to display reviews. */
	private class ReviewPopup extends HeaderPopup {

		public ReviewPopup() {
			super(reviewPanel);
		}
	}

	/* Inner Class RoutePopup to display travel routes. */
	private class RoutePopup extends HeaderPopup 
	implements HasComponents {

		private final Components components = new Components();
		
		public RoutePopup() {
			super(null);

			//-----------------------------------------------
			// From Address field
			//-----------------------------------------------
			fromtextField = new TextField(this, null,
					CONSTANTS.fromtextLabel(),
					tab++);
			fromtextField.setFieldStyle(CSS.fromtextField());
			fromtextField.setHelpText(CONSTANTS.fromtextHelp());
			this.add(fromtextField);

			//-----------------------------------------------
			// Route field
			//-----------------------------------------------
			routeField = new RouteField(this, null,
					CONSTANTS.routeLabel(),
					tab++);
			routeField.setHelpText(CONSTANTS.routeHelp());
			add(routeField);
		}
		
		public void onReset(String state) {
			setResetting(true);
			components.onReset();
			onStateChange(state);
			setResetting(false);
		}
		
		public void onChange(ChangeEvent change) {routeField.setValue(fromtextField.getText() + " to " + AbstractField.LF.format(latitude) + ", " + AbstractField.LF.format(longitude));}
		public boolean hasChanged() {return components.hasChanged();}
		public void addComponent(Component component) {components.add(component);}
		public void onRefresh() {components.onRefresh();}
		public void onClick(ClickEvent click) {}
	}
	
	/* Inner Class RatingPopup to display guest ratings. */
	private class RatingPopup extends HeaderPopup 
	implements HasComponents {

		private final Components components = new Components();
		private ColumnChartField columnchartField;
		private LineChartField linechartField;

		public RatingPopup() {
			super(null);		

			//-----------------------------------------------
			// Column Chart
			//-----------------------------------------------
			columnchartField = new ColumnChartField(this, null,
					new RateColumnChart(),
					CONSTANTS.columnChart(),
					tab++);
			columnchartField.setFieldStyle(CSS.columnchartStyle());
			columnchartField.setRange(0.0, 100.0);
			columnchartField.setPosition(LegendPosition.NONE);
			columnchartField.setEmptyValue(ratingchartEmpty());
			columnchartField.setTableError(new TableError() {
				@Override
				public boolean error() {return noAvailableitem();}
			});
			columnchartField.setTableExecutor(new TableExecutor<RateColumnChart>() {
				public void execute(RateColumnChart action) {getValue(action);}
			});

			//-----------------------------------------------
			// Line Chart
			//-----------------------------------------------
			linechartField = new LineChartField(this, null,
					new RateLineChart(),
					CONSTANTS.lineChart(),
					tab++);
			linechartField.setFieldStyle(CSS.linechartStyle());
			linechartField.setRange(0.0, 100.0);
			linechartField.setPosition(LegendPosition.NONE);
			//linechartField.setEmptyValue(ratingchartEmpty());
			linechartField.setTableError(new TableError() {
				@Override
				public boolean error() {return noAvailableitem();}
			});
			linechartField.setTableExecutor(new TableExecutor<RateLineChart>() {
				@Override
				public void execute(RateLineChart action) {getValue(action);}
			});
			super.add(columnchartField);
			super.add(linechartField);
		}

		public void display() {
			columnchartField.execute();
			linechartField.execute();
			showRelativeTo(nameLabel);
		}

		private Widget ratingchartEmpty() {
			FlowPanel panel = new FlowPanel();
			panel.add(new HTML(CONSTANTS.ratingNone()));
			return panel;
		}

		public void onReset(String state) {
			setResetting(true);
			components.onReset();
			onStateChange(state);
			setResetting(false);
		}

		public boolean hasChanged() {return components.hasChanged();}
		public void addComponent(Component component) {components.add(component);}
		public void onRefresh() {components.onRefresh();}
		public void onChange(ChangeEvent change) {}
		public void onClick(ClickEvent click) {}
	}

	/* Inner Class TermsPanel to display terms and conditions. */
	private class TermsPanel extends HeaderPopup {
		public TermsPanel() {
			super(termsField);
		}
	}

	/* Inner Class ContentsPanel to display contents of the property. */
	private class ContentsPanel extends HeaderPopup {
		public ContentsPanel() {
			super(contentsPanel);
		}
	}

	/* Inner Class AttributesPanel to display attributes of the property. */
	private class AttributesPanel extends HeaderPopup {
		public AttributesPanel() {
			super(attributeField);
		}
	}

	/* Inner Class PricePanel to display the property price table. */
	private class PricePanel extends HeaderPopup {
		public PricePanel() {
			super(priceTable);
			addStyleName(CSS.priceStyle());
		}
	}

	/* Creates price table. */
	private TableField<Price> createPrice() {
		//-----------------------------------------------
		// Price table
		//-----------------------------------------------
		final TableField<Price> priceTable = new TableField<Price>(this, null,
				new PriceTableConverted(),
				12, 
				tab++);

		priceTable.setOrderby(Price.DATE);

		priceTable.setTableError(new TableError() {
			@Override
			public boolean error() {return (
					noAvailableitem()
			);}
		});

		priceTable.setTableExecutor(new TableExecutor<PriceTableConverted>() {
			public void execute(PriceTableConverted action) {
				action.setPartyid(getAvailableitem().getSupplierid());
				action.setEntitytype(NameId.Type.Product.name());
				action.setEntityid(getAvailableitem().getProductid());
				action.setCurrency(currencyList.getValue());
			}
		});

		int col = 0;

		//-----------------------------------------------
		// From Date column
		//-----------------------------------------------
		Column<Price, Date> date = new Column<Price, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( Price price ) {return price.getDate();}
		};
		priceTable.addDateColumn(date, CONSTANTS.pricetableHeaders()[col++], Price.DATE);

		//-----------------------------------------------
		// To Date column
		//-----------------------------------------------
		Column<Price, Date> todate = new Column<Price, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( Price price ) {return price.getTodate();}
		};
		priceTable.addDateColumn(todate, CONSTANTS.pricetableHeaders()[col++], Price.TODATE);

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
		currencyList = new ListCell<Price>(
				Currency.EXCHANGE_CURRENCIES,
				Currency.Code.USD.name(),
				AbstractField.CSS.cbtGradientHead(),
				new ListCell.Delegate<Price>() {
					public void execute(Price price ) {
						priceTable.execute();
					}
				}
		);

		//-----------------------------------------------
		// Change Currency list
		//-----------------------------------------------
		ActionHeader<Price> changeList = new ActionHeader<Price>(currencyList) {
			public Price getValue(Price price){
				return price;
			}
		};

		Column<Price, String> currency = new Column<Price, String>(new TextCell()) {
			@Override
			public String getValue( Price price ) {return price.getCurrency();}
		};
		priceTable.addColumn(currency, changeList);

		return priceTable;
	}

	/*
	 * Creates the product panel.
	 * 
	 * @return the product panel.
	 */
	private Grid createProduct() {
		Grid panel = new Grid(2, 2);
		panel.setWidget(0, 0, createDescription());
		panel.setWidget(1, 1, createCalendar());
		panel.setWidget(0, 1, createMap());
		panel.setWidget(1, 0, createImage());
		
		return panel;
	}

	/*
	 * Creates the description panel.
	 * 
	 * @return the description panel.
	 */
	private ScrollPanel createDescription() {
		ScrollPanel panel = new ScrollPanel();
		descriptionField = new LabelField(this, null,
				CONSTANTS.descriptionLabel(),
				Language.getTranslatableNameIds(),
//				new LanguageNameId(),
				tab++);
		panel.add(descriptionField);
		panel.addStyleName(CSS.descriptionStyle());
		return panel;
	}

	/*
	 * Creates the calendar panel.
	 * 
	 * @return the calendar panel.
	 */
	private CalendarWidget createCalendar() {
		calendarWidget = new CalendarWidget(this);
		calendarWidget.displayMonth();
		return calendarWidget;
	}

	/*
	 * Creates the map panel.
	 * 
	 * @return the map panel.
	 */
	private MapField createMap() {
		mapField = new MapField(this, null,	tab++);
		mapField.setFieldStyle(CSS.mapStyle());
		mapField.setEnabled(false);
		mapField.setPositionVisible(false);
		mapField.setStreetviewVisible(false);
//		mapField.setMapTypeId(MapTypeId.HYBRID);
		mapField.setStreetviewClickable(true);
		
		mapField.setZoom(Math.max(17, AbstractRoot.getIntegerValue(Party.Value.ZoomLevel.name())));
		mapField.setMapTypeId(MapTypeId.HYBRID);
		mapField.setDefaultValue(LatLng.newInstance(AbstractRoot.getDoubleValue(Party.Value.Latitude.name()), AbstractRoot.getDoubleValue(Party.Value.Longitude.name())));
		mapField.setScrollWheelZoomEnabled(false);

		return mapField;
	}

	/*
	 * Creates the image panel.
	 * 
	 * @return the image panel.
	 */
	private VerticalPanel createImage() {
		VerticalPanel panel = new VerticalPanel();
		imageField = new ImageField(this, null, null, tab++);
		imageField.setEnabled(false);
		imageField.setFieldStyle(CSS.imageStyle());
		panel.add(imageField);
		return panel;
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Read Brochure
		//-----------------------------------------------
		brochureSend = new GuardedRequest<Brochure>() {
			protected boolean error() {
				return (
						ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), selectPanel)
						|| ifMessage(AbstractRoot.noActorid(), Level.TERSE, AbstractField.CONSTANTS.errActorid(), selectPanel)
						|| ifMessage (noAvailableitem(), Level.ERROR, CONSTANTS.sendbuttonError(), selectPanel)
						|| ifMessage(customerField.noValue(), Level.TERSE, CONSTANTS.customerError(), customerField)
						|| ifMessage(notesField.noValue(), Level.TERSE, CONSTANTS.notesError(), notesField)
				);
			}
			protected void send() {
				commandBar.setVisible(false);
//				loadingImage.setVisible(true);
				super.send(getValue(new BrochureUpdate()));
			}
			protected void receive(Brochure response){
				onReset(Brochure.CREATED);
				commandBar.setVisible(true);
//				loadingImage.setVisible(false);
				AbstractRoot.setIntegerValue(Party.Value.ProgressBrochure.name(), AbstractRoot.getIntegerValue(Party.Value.ProgressBrochure.name()) + 1);
				hide();
			}
		};

		//-----------------------------------------------
		// Read Brochure by Product ID
		//-----------------------------------------------
		brochureProduct = new GuardedRequest<Brochure>() {
			protected boolean error() {return id == null || id.isEmpty();}
			protected void send() {super.send(getValue(new BrochureProduct()));}
			protected void receive(Brochure response){setValue(response);}
		};

		//-----------------------------------------------
		// Read Brochure by Brochure ID
		//-----------------------------------------------
		brochureRead = new GuardedRequest<Brochure>() {
			protected boolean error() {return id == null || id.isEmpty();}
			protected void send() {super.send(getValue(new BrochureRead()));}
			protected void receive(Brochure response){setValue(response);}
		};

		//-----------------------------------------------
		// Read Brochure Price
		//-----------------------------------------------
		brochurePrice = new GuardedRequest<PriceResponse>() {
			protected boolean error() {
				return (
						ifMessage(quoteField.noUnitvalue(), Level.ERROR, CONSTANTS.availableitemError(), quoteField)
						|| ifMessage(priceunitField.noValue(), Level.ERROR, CONSTANTS.availableitemError(), priceunitField)
						|| ifMessage(noAvailableitem(), Level.ERROR, CONSTANTS.availableitemError(), selectPanel)
				);
			}
			protected void send() {super.send(getValue(new BrochurePrice()));}
			protected void receive(PriceResponse response) {
				if (response == null) {quoteField.setValue(0.0);}
				else if (response.hasCollisions()) {
					quoteField.setValue(response.getQuote());
					AbstractField.addMessage(Level.ERROR, CONSTANTS.collisionError(), fromtodateField);
				}
				else {
					quoteField.setValue(response.getQuote());
					if (response.hasAlerts()) {AbstractField.addMessage(Level.TERSE, getAlertlist(response.getAlerts()), fromtodateField);}
				}
			}
		};

		//-----------------------------------------------
		// Read Product
		//-----------------------------------------------
		productRead = new GuardedRequest<Product> () {
			protected boolean error() {return noAvailableitem();}
			protected void send() {super.send(new ProductRead(getAvailableitem().getProductid()));}
			protected void receive(Product product) {setValue(product);}
		};

		//-----------------------------------------------
		// Read Guest Comments
		//-----------------------------------------------
		ratenotesRequest = new GuardedRequest<Table<RateNote>> () {
			protected boolean error() {return noAvailableitem();}
			protected void send() {super.send(new RateNoteTable(getAvailableitem().getProductid(), Event.DATE, 0, 12));}
			protected void receive(Table<RateNote> response) {setGuestComments(response.getValue());}
		};
	}
	
	public void triggerResize() {
		if(mapField != null) {
			mapField.triggerResize();			
		}
	}
}
