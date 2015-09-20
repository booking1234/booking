/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.image;

import java.util.ArrayList;

import net.cbtltd.client.Component;
import net.cbtltd.client.Components;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.ImageField;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.widget.NameIdWidgetItems;
import net.cbtltd.client.widget.RazorWidget;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.License;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Service;
import net.cbtltd.client.resource.Hosts;
import net.cbtltd.shared.product.WidgetProduct;

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

/** The Class ImageWidget is to display an image gallery via a widget that can be hosted by a web page. */
public class ImageWidget
extends VerticalPanel
implements ChangeHandler, HasComponents {

	private static final ImageConstants CONSTANTS = GWT.create(ImageConstants.class);
	private static final Hosts HOSTS = GWT.create(Hosts.class);
	private static final Components COMPONENTS = new Components();
	private static final Image loader = new Image(AbstractField.BUNDLE.loader());

	private static ListField productField;
	private static ImageField imageField;
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
		if (productField.sent(change)) {getImages();}
	}

	/* A timer to schedule periodic refreshes of the widget. */
	private final Timer refreshTimer = new Timer() {
		public void run() {getImages();}
	};

	/**
	 * Instantiates a new image widget.
	 * If GWT RPC is used the widget must be in an iFrame in the host HTML page to prevent cross-site problems.
	 * If JSONP is used the widget JavaScript must be served from the same site as the host HTML page.
	 *
	 * @param rpc is true if GWT RPC is to be used, else JSONP is used.
	 * @param pos the point of sale code of the organization hosting the widget.
	 * @param productid the ID of the product whose position is to be shown on the map.
	 */
	public ImageWidget(boolean rpc, String pos, String productid) {
		try {
			if (productid == null || productid.isEmpty()) {throw new RuntimeException(Error.product_id.getMessage());}
			this.rpc = rpc;
			this.pos = pos;
			this.setStylePrimaryName("MapWidget");
			
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
			
			loader.setVisible(false);

			final HorizontalPanel bar = new HorizontalPanel();
			bar.add(productField);
			bar.add(loader);
			this.add(bar);

			//-----------------------------------------------
			// Map field
			//-----------------------------------------------
			imageField = new ImageField(this, null,	null, tab++);
			imageField.setEnabled(false);
			imageField.setFieldStyle("Image");
			imageField.setLabelStyle("Label");
			this.add(imageField);

			this.add(RazorWidget.getHome());

			getProduct(productid);
			refreshTimer.cancel();
			refreshTimer.schedule(RazorWidget.delay);
		}
		catch (Throwable x) {
			x.printStackTrace();
			throw new RuntimeException(Error.widget_image.getMessage() + " " +x.getMessage());
		}
	}

	/* The request callback to refresh property position. */
	private void getImages() {
		if (rpc) {imageWidget.execute();}
		else {getJsonpImages();}
	}

	/* The GWT RPC request callback to refresh the property position. */
	final GuardedRequest<Product> imageWidget = new GuardedRequest<Product>() {
		protected boolean error() {return productField.noValue();}
		protected void send() {super.send(new WidgetProduct(productField.getValue()));}
		protected void receive(Product response) {
			if (response == null) {AbstractField.addMessage(Level.ERROR, CONSTANTS.productError(), productField);}
			else {imageField.setValue(response.getImageurls());}
		}
	};

	/* The JSONP request callback to refresh the property position. */
	private void getJsonpImages() {
		if (productField.noValue()) {return;}

		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		String url = HOSTS.jsonUrl()
			+ "?service=" + JSONRequest.IMAGE 
			+ "&pos=" + pos
			+ "&model=" + NameId.Type.Product.name()
			+ "&id=" + productField.getValue()
		;

		loader.setVisible(true);
		jsonp.requestObject(url, new AsyncCallback<ImageWidgetItems>() {

			@Override
			public void onFailure(Throwable x) {
				loader.setVisible(false);
				throw new RuntimeException(Error.product_json.getMessage() + " " + x.getMessage());
			}

			@Override
			public void onSuccess(ImageWidgetItems response) {
				loader.setVisible(false);
				if (response == null || response.getItems() == null && response.getItems().length() == 0) {AbstractField.addMessage(Level.ERROR, CONSTANTS.productError(), imageField);}
				else {
					ArrayList<String> items = new ArrayList<String>();
					for (int index = 0; index < response.getItems().length(); index++) {
						String name = response.getItems().get(index).getNotes();
						items.add(response.getItems().get(index).getURL());
					}
					imageField.setValue(items);
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
				throw new RuntimeException(Error.product_list.getMessage() + " " + x.getMessage());
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
