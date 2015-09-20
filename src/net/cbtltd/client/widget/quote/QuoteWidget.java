/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.quote;

import java.util.ArrayList;

import net.cbtltd.client.Component;
import net.cbtltd.client.Components;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.DoubleField;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.widget.DatespanField;
import net.cbtltd.client.widget.NameIdWidgetItems;
import net.cbtltd.client.widget.RazorWidget;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.License;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Service;
import net.cbtltd.client.resource.Hosts;
import net.cbtltd.shared.reservation.PriceResponse;
import net.cbtltd.shared.reservation.WidgetQuote;

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

/** The Class QuoteWidget is to display quotations via a widget that can be hosted by a web page. */
public class QuoteWidget
extends VerticalPanel
implements ChangeHandler, HasComponents {

	private static final QuoteConstants CONSTANTS = GWT.create(QuoteConstants.class);
	private static final Hosts HOSTS = GWT.create(Hosts.class);
	private static final Components COMPONENTS = new Components();
	private static final Image loader = new Image(AbstractField.BUNDLE.loader());

	private static ListField productField;
	private static DatespanField fromtodateField;
	private static ListField currencyField;
	private static DoubleField priceField;
	private static DoubleField quoteField;
	private static DoubleField saveField;
	private static DoubleField percentField;
	private static DoubleField depositField;
	private static TextField  availableField;
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
	public void onChange(ChangeEvent change) {
//		if (fromtodateField.sent(change)) {fromtodateField.setTovalue(Time.addDuration(fromtodateField.getValue(), 1.0, Time.DAY));}
		getQuote();
	}

	/* A timer to schedule periodic refreshes of the widget. */
	private final Timer refreshTimer = new Timer() {
		public void run() {getQuote();}
	};

	/**
	 * Instantiates a new quote widget.
	 * If GWT RPC is used the widget must be in an iFrame in the host HTML page to prevent cross-site problems.
	 * If JSONP is used the widget JavaScript must be served from the same site as the host HTML page.
	 *
	 * @param rpc is true if GWT RPC is to be used, else JSONP is used.
	 * @param pos the point of sale code of the organization hosting the widget.
	 * @param productid the ID of the product to be quoted.
	 */
	public QuoteWidget(boolean rpc, String pos, String productid) {
		try {
			if (productid == null || productid.isEmpty()) {throw new RuntimeException(Error.product_id.getMessage());}
			this.rpc = rpc;
			this.pos = pos;
			this.setStylePrimaryName("QuoteWidget");
			
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
			currencyField.setValue(RazorWidget.getCurrency());
			currencyField.setFieldStyle("Field");
			currencyField.setLabelStyle("Label");
			currencyField.setHelpText(CONSTANTS.currencyHelp());
			//this.add(currencyField);
			
			loader.setVisible(false);

			final HorizontalPanel bar = new HorizontalPanel();
			bar.add(currencyField);
			bar.add(loader);
			this.add(bar);

			//-----------------------------------------------
			// Arrival Date is the date of arrival
			//-----------------------------------------------
			fromtodateField = new DatespanField(this, null,
					CONSTANTS.fromdateField(),
					tab++);
			fromtodateField.setDefaultDuration(1.0);
			fromtodateField.setFieldStyle("Field");
			fromtodateField.setLabelStyle("Label");
			fromtodateField.setHelpText(CONSTANTS.fromdateHelp());
			this.add(fromtodateField);

			//-----------------------------------------------
			// Rack Rate field
			//-----------------------------------------------
			priceField = new DoubleField(this, null,
					CONSTANTS.priceField(),
					AbstractField.AF,
					tab++);
			priceField.setFieldStyle("Field");
			priceField.setLabelStyle("Label");
			priceField.setHelpText(CONSTANTS.priceHelp());
			priceField.setVisible(false);
			priceField.setEnabled(false);
			//this.add(priceField);

			//-----------------------------------------------
			// Quotation field
			//-----------------------------------------------
			quoteField = new DoubleField(this,  null,
					CONSTANTS.quoteField(),
					AbstractField.AF,
					tab++);
			quoteField.setFieldStyle("Field");
			quoteField.setLabelStyle("Label");
			quoteField.setHelpText(CONSTANTS.quoteHelp());
			quoteField.setVisible(false);
			quoteField.setEnabled(false);
			//this.add(quoteField);

			final HorizontalPanel amount = new HorizontalPanel();
			amount.add(priceField);
			amount.add(quoteField);
			this.add(amount);

			//-----------------------------------------------
			// Savings Amount field
			//-----------------------------------------------
			saveField = new DoubleField(this,  null,
					CONSTANTS.saveField(),
					AbstractField.AF,
					tab++);
			saveField.setFieldStyle("Field");
			saveField.setLabelStyle("Label");
			saveField.setHelpText(CONSTANTS.saveHelp());
			saveField.setVisible(false);
			saveField.setEnabled(false);
			//this.add(saveField);

			//-----------------------------------------------
			// Savings Percent field
			//-----------------------------------------------
			percentField = new DoubleField(this,  null,
					CONSTANTS.percentField(),
					AbstractField.QF,
					tab++);
			percentField.setFieldStyle("Field");
			percentField.setLabelStyle("Label");
			percentField.setHelpText(CONSTANTS.percentHelp());
			percentField.setVisible(false);
			percentField.setEnabled(false);
			//this.add(percentField);

			final HorizontalPanel save = new HorizontalPanel();
			save.add(saveField);
			save.add(percentField);
			this.add(save);

			//-----------------------------------------------
			// Deposit Percentage field
			//-----------------------------------------------
			depositField = new DoubleField(this, null,
					CONSTANTS.depositLabel(),
					AbstractField.AF,
					tab++);
			depositField.setFieldStyle("Field");
			depositField.setLabelStyle("Label");
			depositField.setDefaultValue(50.);
			depositField.setRange(0., 100.);
			depositField.setHelpText(CONSTANTS.depositHelp());
			//this.add(depositField);
			
			//-----------------------------------------------
			// Available field
			//-----------------------------------------------
			availableField = new TextField(this,  null,
					CONSTANTS.availableLabel(),
					tab++);
			availableField.setFieldHalf();
			availableField.setFieldStyle("Available");
			availableField.setLabelStyle("Label");
			availableField.setEnabled(false);
			availableField.setHelpText(CONSTANTS.availableHelp());
			//this.add(availableField);
			
			final HorizontalPanel da = new HorizontalPanel();
			da.add(depositField);
			da.add(availableField);
			this.add(da);

			this.add(RazorWidget.getHome());

			getProduct(productid);
			refreshTimer.cancel();
			refreshTimer.schedule(10000);
		}
		catch (Throwable x) {
			x.printStackTrace();
			throw new RuntimeException(Error.widget_quote.getMessage() + " " + x.getMessage());
		}
	}

	/* The request callback to refresh quote. */
	private void getQuote() {
		if (rpc) {quoteWidget.execute();}
		else {getJsonpQuote();}
	}

	/* The RPC request callback to refresh quote. */
	final GuardedRequest<PriceResponse> quoteWidget = new GuardedRequest<PriceResponse>() {
		protected boolean error() {
			return (
					productField.noValue()
					|| fromtodateField.noValue()
					|| fromtodateField.noTovalue()
					|| fromtodateField.isEndBeforeStart()
					|| currencyField.noValue()
			);
		}
		protected void send() {super.send(new WidgetQuote(productField.getValue(), fromtodateField.getValue(), fromtodateField.getTovalue(), currencyField.getValue()));}
		protected void receive(PriceResponse response) {
			if (response.noValue()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.priceError(), priceField);}
			else {setFields(response.getValue(), response.getQuote(), response.getCurrency(), response.getDeposit(), !response.hasCollisions());}
		}
	};

	/* The JSONP request callback to refresh quote. */
	private void getJsonpQuote() {
		if (productField.noValue() 
				|| fromtodateField.noValue() 
					|| fromtodateField.noTovalue()
					|| fromtodateField.isEndBeforeStart()
				|| currencyField.noValue()
		) {return;}

		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		String url = HOSTS.jsonUrl()
			+ "?service=" + JSONRequest.QUOTE 
			+ "&pos=" + pos 
			+ "&productid=" + productField.getValue()
			+ "&fromdate=" + RazorWidget.DF.format(fromtodateField.getValue()) 
			+ "&todate=" + RazorWidget.DF.format(fromtodateField.getTovalue())
			+ "&currency=" + currencyField.getValue()
		;

		loader.setVisible(true);
		jsonp.requestObject(url, new AsyncCallback<QuoteWidgetItem>() {

			@Override
			public void onFailure(Throwable x) {
				loader.setVisible(false);
				throw new RuntimeException(Error.widget_quote.getMessage() + " " + x.getMessage());
			}

			@Override
			public void onSuccess(QuoteWidgetItem response) {
				loader.setVisible(false);
				//availableField.setVisible(response != null);
				if (response == null) {AbstractField.addMessage(Level.ERROR, CONSTANTS.priceError(), fromtodateField);}
				else {setFields(response.getPrice(), response.getQuote(), response.getCurrency(), response.getDeposit(), response.getAvailable());}
			}
		});
	}

	private void setFields(double price, double quote, String currency, double deposit, boolean available) {
		priceField.setValue(price);
		priceField.setVisible(price > 0.0);
		quoteField.setValue(quote);
		currencyField.setValue(currency);
		double save = 0.0;
		save = price - quote;
		priceField.setVisible(!nosavings() && save >= 0.0);
		quoteField.setVisible(nosavings() || save != 0.0);
		saveField.setValue(save);
		saveField.setVisible(!nosavings() && save > 0.0);
		Double percent = save * 100.0 / price;
		percentField.setValue(percent);
		percentField.setVisible(!nosavings() && save > 0.0);
		depositField.setValue(deposit);
		availableField.setValue(available ? CONSTANTS.availableError() : CONSTANTS.collideError());
	}
	
	/* 
	 * Checks if the widget must not show the savings fields.
	 * 
	 * @return true, if the widget must not show the savings fields.
	 */
	private static final boolean nosavings() {
		try {
			String nosavings = RazorWidget.getParameter("nosavings");
			return nosavings != null && nosavings.equalsIgnoreCase("true");
		}
		catch (Throwable x) {return false;}
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
	
	/* 
	 * The JSONP request callback to get product name ID pairs.
	 * 
	 * @param productid the selected product.
	 */
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
				}
			}
		});
	}
}
