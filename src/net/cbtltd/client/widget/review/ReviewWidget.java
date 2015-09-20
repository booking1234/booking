/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.review;

import java.util.ArrayList;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.Component;
import net.cbtltd.client.Components;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.widget.NameIdWidgetItems;
import net.cbtltd.client.widget.RazorWidget;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Event;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.License;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.api.HasTable;
import net.cbtltd.client.resource.Hosts;
import net.cbtltd.shared.rate.RateNote;
import net.cbtltd.shared.rate.RateNoteTable;

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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mybookingpal.config.RazorConfig;

/** The Class ReviewWidget is to display and enter guest feedback via a widget that can be hosted by a web page. */
public class ReviewWidget
extends VerticalPanel
implements ChangeHandler, HasComponents {

	private static final ReviewConstants CONSTANTS = GWT.create(ReviewConstants.class);
	private static final Hosts HOSTS = GWT.create(Hosts.class);
	private static final Components COMPONENTS = new Components();
	private static final Image loader = new Image(AbstractField.BUNDLE.loader());
	
	private static ListField productField;
	private static VerticalPanel form = new VerticalPanel();
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
	public void onChange(ChangeEvent change) {getReview();}

	/* A timer to schedule periodic refreshes of the widget. */
	private final Timer refreshTimer = new Timer() {
		public void run() {getReview();}
	};

	/**
	 * Instantiates a new review widget.
	 * If GWT RPC is used the widget must be in an iFrame in the host HTML page to prevent cross-site problems.
	 * If JSONP is used the widget JavaScript must be served from the same site as the host HTML page.
	 *
	 * @param rpc is true if GWT RPC is to be used, else JSONP is used.
	 * @param pos the point of sale code of the organization hosting the widget.
	 * @param productid the ID of the product to be reviewed.
	 */
	public ReviewWidget(boolean rpc, String pos, String productid) {
		try {
			if (productid == null || productid.isEmpty()) {throw new RuntimeException(Error.product_id.getMessage());}
			this.rpc = rpc;
			this.pos = pos;
			this.setStylePrimaryName("ReviewWidget");

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

			loader.setVisible(false);
			
			final HorizontalPanel bar = new HorizontalPanel();
			bar.add(productField);
			bar.add(loader);
			this.add(bar);

			final Label label = new Label(CONSTANTS.reviewLabel());
			label.addStyleName("Label");
			this.add(label);

			this.add(form);

			this.add(RazorWidget.getHome());

			getProduct(productid);
			refreshTimer.cancel();
			refreshTimer.schedule(RazorWidget.delay);
		}
		catch (Throwable x) {
			x.printStackTrace();
			throw new RuntimeException(Error.widget_review.getMessage() + " " + x.getMessage());
		}
	}

	/* The request callback to refresh guest reviews. */
	private void getReview() {
		if (rpc) {reviewWidget.execute();}
		else {getJsonpReview();}
	}
	
	/* The RPC request callback to refresh guest reviews. */
	final GuardedRequest<Table<RateNote>> reviewWidget = new GuardedRequest<Table<RateNote>>() {
		protected boolean error() {return (RazorWidget.ifMessage(productField.noValue(), Level.TERSE, CONSTANTS.productError(), productField));}
		protected void send() {super.send(new RateNoteTable(productField.getValue(), Event.DATE + HasTable.ORDER_BY_DESC, 0, RazorWidget.getRows()));}
		protected void receive(Table<RateNote> response) {
			if (response == null || response.getValue() == null || response.getValue().isEmpty()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.reviewError(), productField);}
			else {renderNotes(response.getValue());}
		}
	};
	
	/* The JSONP request callback to refresh guest reviews. */
	private void getJsonpReview() {
		if (productField.noValue()) {return;}

		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		String url = HOSTS.jsonUrl()
			+ "?service=" + JSONRequest.REVIEW
			+ "&pos=" + pos 
			+ "&productid=" + productField.getValue()
			+ "&rows=" + RazorWidget.getRows()
		;

		loader.setVisible(true);
		jsonp.requestObject(url, new AsyncCallback<ReviewWidgetItems>() {

			@Override
			public void onFailure(Throwable x) {
				loader.setVisible(false);
				throw new RuntimeException(Error.widget_review.getMessage() + " " + x.getMessage());
			}

			@Override
			public void onSuccess(ReviewWidgetItems response) {
				loader.setVisible(false);
				if (response == null || response.getItems() == null || response.getItems().length() == 0) {AbstractField.addMessage(Level.ERROR, CONSTANTS.reviewError(), productField);}
				else {
					final ArrayList<RateNote> notes = new ArrayList<RateNote>();
					for (int row = 0; row < response.getItems().length(); row++) {
						ReviewWidgetItem value = response.getItems().get(row);
						Log.debug("row " + row + " " + value.string());
						final RateNote note = new RateNote();
						note.setDate(RazorWidget.DF.parse(value.getDate().trim()));
						note.setRating(value.getRating());
						note.setNotes(value.getNotes());
						notes.add(note);
					}
					renderNotes(notes);
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
		else {getJsonpProductNameids(productid);}	
	}

	/*
	 * The JSONP request callback to refresh product name ID pairs.
	 * 
	 * @param productid the ID of the selected product.
	 */
	private void getJsonpProductNameids(String productid) {

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
					getJsonpReview();
				}
			}
		});
	}
	
	/* Render the review notes. */
	private void renderNotes(ArrayList<RateNote> notes) {
		form.clear();
		int row = 0;
		for (RateNote note : notes) {
			Label header = new Label("Date " + AbstractRoot.getDF().format(note.getDate()) + " Rating " + AbstractField.QF.format(note.getRating()));
			header.setStylePrimaryName(AbstractField.CSS.cbtAbstractField());
			header.addStyleName("Header");
			form.add(header);
			TextArea text = new TextArea();
			text.setStylePrimaryName(AbstractField.CSS.cbtAbstractField());
			text.addStyleName(row % 2 == 0 ? "Odd" : "Even");
			text.setValue(note.getNotes());
			form.add(text);
			row++;
		}
		this.add(form);
	}

}
