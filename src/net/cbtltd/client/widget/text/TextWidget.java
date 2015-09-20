/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.text;

import java.util.ArrayList;

import net.cbtltd.client.Component;
import net.cbtltd.client.Components;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.LabelField;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.widget.NameIdWidgetItems;
import net.cbtltd.client.widget.RazorWidget;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.License;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Text;
import net.cbtltd.client.resource.Hosts;
import net.cbtltd.shared.text.WidgetText;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mybookingpal.config.RazorConfig;

/** The Class TextWidget is to display text via a widget that can be hosted by a web page. */
public class TextWidget
extends VerticalPanel
implements ChangeHandler, HasComponents {

	private static final TextConstants CONSTANTS = GWT.create(TextConstants.class);
	private static final Hosts HOSTS = GWT.create(Hosts.class);
	private static final Components COMPONENTS = new Components();
	private static final Image loader = new Image(AbstractField.BUNDLE.loader());
	
	private static ListField productField;
	private static LabelField labelField;
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
		if (productField.sent(change) || labelField.sent(change)) {getText();}
	}

	/* A timer to schedule periodic refreshes of the widget. */
	private final Timer refreshTimer = new Timer() {
		public void run() {getText();}
	};

	/**
	 * Instantiates a new text widget.
	 * If GWT RPC is used the widget must be in an iFrame in the host HTML page to prevent cross-site problems.
	 * If JSONP is used the widget JavaScript must be served from the same site as the host HTML page.
	 *
	 * @param rpc is true if GWT RPC is to be used, else JSONP is used.
	 * @param pos the point of sale code of the organization hosting the widget.
	 * @param productid the ID of the product whose position is to be shown on the map.
	 */
	public TextWidget(boolean rpc, String pos, String productid) {
		try {
			if (productid == null || productid.isEmpty()) {throw new RuntimeException(Error.product_id.getMessage());}
			this.rpc = rpc;
			this.pos = pos;
			this.setStylePrimaryName("TextWidget");
			
			int tab = 0;

			//-----------------------------------------------
			// Product field
			//-----------------------------------------------
			productField = new ListField(this, null,
					new NameIdAction(Service.PRODUCT),
//TODO:					new NoOfflineNameId(),
					CONSTANTS.productLabel(),
					false,
					tab++);
			productField.setFieldStyle("Field");
			productField.setLabelStyle("Label");
			productField.setAllOrganizations(true);
			productField.setIds(NameId.getCdlist(productid));
			productField.setVisible(productid.split(",").length > 1); // multiple properties
			productField.setHelpText(CONSTANTS.productHelp());
			
			loader.setVisible(false);

			final HorizontalPanel bar = new HorizontalPanel();
			bar.add(productField);
			bar.add(loader);
			this.add(bar);
			
	
			//-----------------------------------------------
			// Text Output field
			//-----------------------------------------------
			labelField = new LabelField(this, null,
					CONSTANTS.types()[0],
					Language.getTranslatableNameIds(),
					tab++);
			labelField.setLabelStyle("Label");
			labelField.setTypeStyle("Type");
			labelField.setAllOrganizations(true);
			labelField.setEnabled(false);
			labelField.setTypeVisible(rpc);
			
			ScrollPanel scroll = new ScrollPanel();
			scroll.addStyleName("Text");
			this.add(scroll);
			scroll.add(labelField);

			this.add(RazorWidget.getHome());

			getProduct(productid);
			labelField.onRefresh();
			refreshTimer.cancel();
			refreshTimer.schedule(RazorWidget.delay);
		}
		catch (Throwable x) {
			x.printStackTrace();
			throw new RuntimeException(Error.widget_text.getMessage() + " " + x.getMessage());
		}
	}

	
	/* The request callback to refresh the text. */
	private void getText() {
		if (rpc) {textWidget.execute();}
		else {getJsonpText();}
	}

	/* The GWT RPC request callback to refresh the text. */
	final GuardedRequest<Text> textWidget = new GuardedRequest<Text>() {
		protected boolean error() {return productField.noValue();}
		protected void send() {super.send(new WidgetText(NameId.Type.Product.name() + productField.getValue() + RazorWidget.getParameter("text"), labelField.getLanguage()));}
		protected void receive(Text response) {
			if (response == null) {AbstractField.addMessage(Level.ERROR, CONSTANTS.productError(), productField);}
			else {labelField.setText(response);}
		}
	};

	/* The JSONP request callback to refresh the text. */
	private void getJsonpText() {
		
		if (productField.noValue()) {return;}

		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		String url = HOSTS.jsonUrl()
			+ "?service=" + JSONRequest.TEXT 
			+ "&pos=" + pos 
			+ "&model=" + NameId.Type.Product.name()
			+ "&id=" + productField.getValue()
			+ "&language=" + labelField.getLanguage()
			+ "&type=" + RazorWidget.getParameter("text")
		;

		loader.setVisible(true);
		jsonp.requestObject(url, new AsyncCallback<TextWidgetItem>() {

			@Override
			public void onFailure(Throwable x) {
				loader.setVisible(false);
				throw new RuntimeException(Error.widget_text.getMessage() + " " + x.getMessage());
			}

			@Override
			public void onSuccess(TextWidgetItem response) {
				loader.setVisible(false);
				if (response == null) {AbstractField.addMessage(Level.ERROR, CONSTANTS.productError(), labelField);}
				else {labelField.setValue(response.getMessage());}
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
