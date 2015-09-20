/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.price;

import java.util.ArrayList;

import net.cbtltd.client.Component;
import net.cbtltd.client.Components;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.DoubleField;
import net.cbtltd.client.field.GridField;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.widget.DatespanField;
import net.cbtltd.client.widget.NameIdWidgetItems;
import net.cbtltd.client.widget.RazorWidget;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.License;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Price;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Table;
import net.cbtltd.client.resource.Hosts;
import net.cbtltd.shared.price.WidgetPriceTable;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mybookingpal.config.RazorConfig;

/** The Class PriceWidget is to display price tables via a widget that can be hosted by a web page. */
public class PriceWidget
extends VerticalPanel
implements ChangeHandler, HasComponents {

	private static final PriceConstants CONSTANTS = GWT.create(PriceConstants.class);
	private static final Hosts HOSTS = GWT.create(Hosts.class);
	private static final Components COMPONENTS = new Components();
	private static final Image loader = new Image(AbstractField.BUNDLE.loader());

	private static ListField productField;
	private static ListField currencyField;
	private static GridField<Price> priceGrid;
	private boolean rpc;
	private String pos;

	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasComponents#addComponent(net.cbtltd.client.Component)
	 */
	public void addComponent(Component component) {COMPONENTS.add(component);}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasComponents#onRefresh()
	 */
	public void onRefresh() {COMPONENTS.onRefresh();}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasComponents#hasChanged()
	 */
	public boolean hasChanged() {return COMPONENTS.hasChanged();}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.HasComponents#onReset(java.lang.String)
	 */
	public void onReset(String state) {COMPONENTS.onReset();}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	public void onClick(ClickEvent click) {}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.dom.client.ChangeHandler#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	public void onChange(ChangeEvent change) {getPrice();}

	/* A timer to schedule periodic refreshes of the widget. */
	private final Timer refreshTimer = new Timer() {
		public void run() {getPrice();}
	};

	/**
	 * Instantiates a new price widget.
	 * If GWT RPC is used the widget must be in an iFrame in the host HTML page to prevent cross-site problems.
	 * If JSONP is used the widget JavaScript must be served from the same site as the host HTML page.
	 *
	 * @param rpc is true if GWT RPC is to be used, else JSONP is used.
	 * @param pos the point of sale code of the organization hosting the widget.
	 * @param productid the ID of the product to be priced.
	 */
	public PriceWidget(boolean rpc, String pos, String productid) {
		try {
			if (productid == null || productid.isEmpty()) {throw new RuntimeException(Error.product_id.getMessage());}
			this.rpc = rpc;
			this.pos = pos;
			this.setStylePrimaryName("PriceWidget");

			int tab = 0;

			//-----------------------------------------------
			// Product field
			//-----------------------------------------------
			productField = new ListField(this, null,
					new NameIdAction(Service.PRODUCT),
					CONSTANTS.productLabel(),
					false,
					tab++);
			productField.setFieldStyle("Field");
			productField.setLabelStyle("Label");
			productField.setAllOrganizations(true);
			productField.setIds(NameId.getCdlist(productid));
			productField.setVisible(productid.split(",").length > 1); // multiple properties
			productField.setHelpText(CONSTANTS.productHelp());
			this.add(productField);

			//-----------------------------------------------
			// Currency field
			//-----------------------------------------------
			currencyField = new ListField(this, null,
//					NameId.getList(Currency.EXCHANGE_CURRENCY_NAMES, Currency.EXCHANGE_CURRENCIES),
					Currency.getConvertibleCurrencyNameIds(),
					CONSTANTS.currencyField(),
					false,
					tab++);
			currencyField.setFieldStyle("Field");
			currencyField.setLabelStyle("Label");
			currencyField.setValue(RazorWidget.getCurrency());
			currencyField.setHelpText(CONSTANTS.currencyHelp());
			
			loader.setVisible(false);
			final HorizontalPanel bar = new HorizontalPanel();
			bar.add(currencyField);
			bar.add(loader);
			this.add(bar);
			
			//-----------------------------------------------
			// Price grid
			//-----------------------------------------------
			priceGrid = new GridField<Price>(this, null, CONSTANTS.priceHeaders(), CONSTANTS.priceLabel(), RazorWidget.getRows() - 1, tab++) {

				@Override
				public void setRowValue(int row, Price price) {
					
					int col = 0;
					DatespanField fromtodateField = new DatespanField(this, null, null, 0);
					fromtodateField.setValue(price.getDate());
					fromtodateField.setTovalue(price.getTodate());
					fromtodateField.setFieldStyle(row % 2 == 0 ? "Odd" : "Even");
					fromtodateField.setFieldStyle("Date");
					priceGrid.setWidget(row, col++, fromtodateField);

					DoubleField priceField = new DoubleField(this, null, null, AbstractField.AF, 0);
					priceField.setValue(price.getValue());
					priceField.setFieldStyle(row % 2 == 0 ? "Odd" : "Even");
					priceField.setFieldStyle("Price");
					priceGrid.setWidget(row, col++, priceField);

					DoubleField minimumField = new DoubleField(this, null, null, AbstractField.AF, 0);
					minimumField.setValue(price.getMinimum());
					minimumField.setFieldStyle(row % 2 == 0 ? "Odd" : "Even");
					minimumField.setFieldStyle("Price");
					priceGrid.setWidget(row, col++, minimumField);
				}

				@Override
				public Price getRowValue(int row) {return null;}
			};

			priceGrid.getRowFormatter().addStyleName(0, "Header");
			priceGrid.setLabelStyle("Label");
			priceGrid.setStyleName("Grid");
			priceGrid.setHelpText(CONSTANTS.priceHelp());
			this.add(priceGrid);

			this.add(RazorWidget.getHome());

			getProduct(productid);
			refreshTimer.cancel();
			refreshTimer.schedule(RazorWidget.delay);
		}
		catch (Throwable x) {
			x.printStackTrace();
			throw new RuntimeException(Error.widget_price.getMessage() + " " + x.getMessage());
		}
	}

	/* The request callback to refresh prices */
	private void getPrice() {
		if (rpc) {priceWidget.execute();}
		else {getJsonpPrice();}
	}
	
	/* The action to refresh the widget given its product ID and currency. */
	GuardedRequest<Table<Price>> priceWidget = new GuardedRequest<Table<Price>>() {
		protected boolean error() {
			return (
					RazorWidget.ifMessage(currencyField.noValue(), Level.TERSE, CONSTANTS.currencyError(), currencyField)
					|| RazorWidget.ifMessage(productField.noValue(), Level.TERSE, CONSTANTS.productError(), productField)
			);
		}
		protected void send() {super.send(new WidgetPriceTable(NameId.Type.Product.name(), productField.getValue(), RazorWidget.getDate(), currencyField.getValue(), null, 0, RazorWidget.getRows()));}
		protected void receive(Table<Price> response) {
			if (response == null || response.getValue() == null || response.getValue().isEmpty()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.priceError(), priceGrid);}
			else {priceGrid.setValue(response.getValue());}
		}
	};
	
	/* The JSONP request callback to refresh prices */
	private void getJsonpPrice() {
		if (productField.noValue() || currencyField.noValue()) {return;}

		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		String url = HOSTS.jsonUrl()
			+ "?service=" + JSONRequest.PRICE
			+ "&pos=" + pos 
			+ "&model=" + NameId.Type.Product.name() 
			+ "&id=" + productField.getValue()
			+ "&date=" + RazorWidget.DF.format(RazorWidget.getDate())
			+ "&currency=" + currencyField.getValue()
			+ "&rows=" + RazorWidget.getRows()
		;

		loader.setVisible(true);
		jsonp.requestObject(url, new AsyncCallback<PriceWidgetItems>() {

			@Override
			public void onFailure(Throwable x) {
				loader.setVisible(false);
				throw new RuntimeException(Error.price_json.getMessage() + " " + x.getMessage());
			}

			@Override
			public void onSuccess(PriceWidgetItems response) {
				loader.setVisible(false);
				if (response == null || response.getItems() == null || response.getItems().length() == 0) {AbstractField.addMessage(Level.ERROR, CONSTANTS.priceError(), priceGrid);}
				else {
					ArrayList<Price> prices = new ArrayList<Price>();
					for (int row = 0; row < response.getItems().length(); row++) {
						PriceWidgetItem value = response.getItems().get(row);
						Log.debug("row " + row + " " + value.string());
						Price price = new Price();
						price.setDate(RazorWidget.DF.parse(value.getFromdate()));
						price.setTodate(RazorWidget.DF.parse(value.getTodate()));
						price.setValue(value.getPrice());
						price.setMinimum(value.getMinimum());
						prices.add(price);
					}
					priceGrid.setValue(prices);
				}
			}
		});
	}
	
	/* 
	 * The request callback to get product name ID pairs.
	 * 
	 *  @param productid the ID of the selected product.
	 */
	private void getProduct(String productid) {
		if (rpc) {productField.onRefresh();}
		else {getJsonpProductNameIds(productid);}	
	}
	
	/* The JSONP request callback to get product name ID pairs. */
	private void getJsonpProductNameIds(String productid) {

		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		String url = HOSTS.jsonUrl()
			+ "?service=" + JSONRequest.NAMEID
			+ "&pos=" + pos 
			+ "&model=" + NameId.Type.Product.name() 
			+ "&id=" + productid 
		;

		loader.setVisible(true);
		jsonp.requestObject(url, new AsyncCallback<NameIdWidgetItems>() {

			@Override
			public void onFailure(Throwable x) {
				loader.setVisible(false);
				throw new RuntimeException(Error.nameid_json.getMessage() + " " + x.getMessage());
			}

			@Override
			public void onSuccess(NameIdWidgetItems response) {
				loader.setVisible(false);
				if (response != null && response.getItems() != null && response.getItems().length() > 0) {
					ArrayList<NameId> items = new ArrayList<NameId>();
					for (int index = 0; index < response.getItems().length(); index++) {
						items.add(new NameId(response.getItems().get(index).getName(), response.getItems().get(index).getId()));
					}
					productField.setItems(items);
					getJsonpPrice();
				}
			}
		});
	}
}
