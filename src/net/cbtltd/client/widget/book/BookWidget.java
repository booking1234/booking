/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.book;

import java.util.ArrayList;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.Component;
import net.cbtltd.client.Components;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.DoubleunitField;
import net.cbtltd.client.field.HelpLabel;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.ReportButton;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.panel.LoadingPopup;
import net.cbtltd.client.resource.FieldBundle;
import net.cbtltd.client.resource.Hosts;
import net.cbtltd.client.widget.DatespanField;
import net.cbtltd.client.widget.NameIdWidgetItems;
import net.cbtltd.client.widget.RazorWidget;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Design;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.Finance;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.License;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Report;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Time;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mybookingpal.config.RazorConfig;

/** The Class BookWidget is to display and enter reservations via a widget that can be hosted by a web page. */
public class BookWidget
extends VerticalPanel
implements ChangeHandler, HasComponents {

	private static final BookConstants CONSTANTS = GWT.create(BookConstants.class);
	private static final FieldBundle BUNDLE = FieldBundle.INSTANCE;

	private static ListField productField;
	private static TextField emailaddressField;
	private static TextField firstnameField;
	private static TextField familynameField;
	private static DatespanField fromtodateField;
	private static HorizontalPanel logoImages;
	private static TextField cardholderField;
	private static TextField cardnumberField;
	private static HelpLabel carddetailLabel;
	private static ListField cardmonthField;
	private static ListField cardyearField;
	private static TextField cardcodeField;
	private static DoubleunitField quoteField;
	private static DoubleunitField amountField;
	private static TextAreaField notesField;
	private static Button submitButton;
	private static Button resetButton;
	private static ReportButton confirmationReport;
	private static Image loader;
	private String pos;
	private String reservationid;
	private boolean isBook() {return reservationid == null;}

	private static final Components COMPONENTS = new Components();
	private static final Hosts HOSTS = GWT.create(Hosts.class);
	
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
		if (emailaddressField.sent(change)) {getJsonpParty();}
		if (productField.sent(change)) {setPaymentVisible(false);}
		//if (productField.sent(change) || fromtodateField.sent(change)) {getJsonpAvailable();}
		if (productField.sent(change)
				|| fromtodateField.sent(change)
				|| emailaddressField.sent(change)
		) {getJsonpPriceAvailable();}
	}

	/**
	 * Instantiates a new book widget, and displays payment fields after checking if it is hosted by a RazorWidget.isSecure() server.
	 *
	 * @param pos the point of sale code of the organization hosting the widget.
	 * @param productid the ID of the product to be booked.
	 */
	public BookWidget(String pos, String productid) {
		try {
			String reservationid = RazorWidget.getParameter("reservationid");
			if (reservationid == null && productid == null) {throw new RuntimeException(Error.product_id.getMessage());}
			this.pos = pos;
			this.reservationid = reservationid;
			this.setStylePrimaryName("BookWidget");
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
			// Arrival and Departure Dates
			//-----------------------------------------------
			fromtodateField = new DatespanField(this, null,
					CONSTANTS.fromtodateLabel(),
					tab++);
			fromtodateField.setFieldStyle("Field");
			fromtodateField.setLabelStyle("Label");
			fromtodateField.setDefaultDuration(1.0);
			fromtodateField.setHelpText(CONSTANTS.fromtodateHelp());
			this.add(fromtodateField);

			//-----------------------------------------------
			// Guest Email Address field
			//-----------------------------------------------
			emailaddressField = new TextField(this,  null,
					CONSTANTS.emailaddressLabel(),
					tab++);
			emailaddressField.setFieldStyle("Field");
			emailaddressField.setLabelStyle("Label");
			emailaddressField.setMaxLength(100);
			emailaddressField.setHelpText(CONSTANTS.emailaddressHelp());
			this.add(emailaddressField);

			//-----------------------------------------------
			// Guest First Name field
			//-----------------------------------------------
			firstnameField = new TextField(this,  null,
					CONSTANTS.firstnameLabel(),
					tab++);
			firstnameField.setFieldStyle("Field");
			firstnameField.setLabelStyle("Label");
			firstnameField.setMaxLength(100);
			firstnameField.setHelpText(CONSTANTS.firstnameHelp());
			this.add(firstnameField);

			//-----------------------------------------------
			// Guest Family Name field
			//-----------------------------------------------
			familynameField = new TextField(this,  null,
					CONSTANTS.familynameLabel(),
					tab++);
			familynameField.setFieldStyle("Field");
			familynameField.setLabelStyle("Label");
			familynameField.setMaxLength(100);
			familynameField.setHelpText(CONSTANTS.familynameHelp());
			this.add(familynameField);

			//-----------------------------------------------
			// Notes field
			//-----------------------------------------------
			notesField = new TextAreaField(this, null,
					CONSTANTS.notesLabel(),
					tab++);
			notesField.setFieldStyle("Notes");
			notesField.setLabelStyle("Label");
			notesField.setHelpText(CONSTANTS.notesHelp());
			this.add(notesField);

			//-----------------------------------------------
			// Logo field
			//-----------------------------------------------
			final Image paygate= new Image(BUNDLE.paygate());
//			final Image godaddy= new Image(BUNDLE.godaddy());
			final Image thawte= new Image(BUNDLE.thawte());
			final Image mastercard= new Image(BUNDLE.mastercard());
			final Image visa= new Image(BUNDLE.visa());

			logoImages = new HorizontalPanel();
			logoImages.setTitle(CONSTANTS.logoLabel());
			logoImages.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			logoImages.addStyleName("Logos");
			logoImages.add(paygate);
			logoImages.add(thawte);
			logoImages.add(mastercard);
			logoImages.add(visa);
			logoImages.setVisible(false);
			this.add(logoImages);

			//-----------------------------------------------
			// Card Holder field
			//-----------------------------------------------
			cardholderField = new TextField(this, null,
					CONSTANTS.cardholderLabel(),
					tab++);
			cardholderField.setVisible(false);
			cardholderField.setMaxLength(50);
			cardholderField.setHelpText(CONSTANTS.cardholderHelp());
			this.add(cardholderField);

			//-----------------------------------------------
			// Card Number field
			//-----------------------------------------------
			cardnumberField = new TextField(this,  null,
					CONSTANTS.cardnumberLabel(),
					tab++);
			cardnumberField.setVisible(false);
			cardnumberField.setHelpText(CONSTANTS.cardnumberHelp());
			this.add(cardnumberField);

			//-----------------------------------------------
			// Card Expiry Month field
			//-----------------------------------------------
			cardmonthField = new ListField(this,  null,
					Finance.getMonths(),
					null,
					false,
					tab++);
			cardmonthField.setVisible(false);
			carddetailLabel = new HelpLabel(CONSTANTS.carddetailLabel(), CONSTANTS.carddetailHelp(), cardmonthField);
			carddetailLabel.setVisible(false);
			this.add(carddetailLabel);
			cardmonthField.setFieldStyle(AbstractField.CSS.cbtListFieldCCmonth());

			//-----------------------------------------------
			// Card Expiry Year field
			//-----------------------------------------------
			cardyearField = new ListField(this,  null,
					Finance.getYears(),
					null,
					false,
					tab++);
			cardyearField.setVisible(false);
			cardyearField.setFieldStyle(AbstractField.CSS.cbtListFieldCCyear());

			//-----------------------------------------------
			// Card Security Code field
			//-----------------------------------------------
			cardcodeField = new TextField(this, null,
					null,
					tab++);
			cardcodeField.setVisible(false);
			cardcodeField.setFieldStyle(AbstractField.CSS.cbtTextFieldCCcode());

			HorizontalPanel ccc = new HorizontalPanel();
			ccc.add(cardmonthField);
			ccc.add(cardyearField);
			ccc.add(cardcodeField);
			this.add(ccc);

			//-----------------------------------------------
			// Quote Amount field
			//-----------------------------------------------
			quoteField = new DoubleunitField(this, null,
//					NameId.getList(Currency.EXCHANGE_CURRENCY_NAMES, Currency.EXCHANGE_CURRENCIES),
					Currency.getConvertibleCurrencyNameIds(),
					CONSTANTS.quoteLabel(),
					AbstractField.AF,
					tab++);
			quoteField.setVisible(false);
			quoteField.setUnitvalue(RazorWidget.getCurrency());
			quoteField.setEnabled(false);
			quoteField.setUnitenabled(false);
			quoteField.setHelpText(CONSTANTS.quoteHelp());
			this.add(quoteField);

			//-----------------------------------------------
			// Deposit Amount field
			//-----------------------------------------------
			amountField = new DoubleunitField(this, null,
//					NameId.getList(Currency.EXCHANGE_CURRENCY_NAMES, Currency.EXCHANGE_CURRENCIES),
					Currency.getConvertibleCurrencyNameIds(),
					CONSTANTS.amountLabel(),
					AbstractField.AF,
					tab++);
			amountField.setVisible(false);
			amountField.setUnitvalue(RazorWidget.getCurrency());
			amountField.setHelpText(CONSTANTS.amountHelp());
			amountField.setEnabled(false);
			amountField.setUnitenabled(false);
			this.add(amountField);

			final HorizontalPanel bar = new HorizontalPanel();
			this.add(bar);

			//-----------------------------------------------
			// Submit button
			//-----------------------------------------------
			submitButton = new Button(CONSTANTS.submitButton(), new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (isBook()) {getJsonpBook();}
					else {getJsonpReservationRS();}
				}
			});
			submitButton.setTitle(CONSTANTS.submitHelp());
			submitButton.addStyleName("Button");
			submitButton.setVisible(false);
			bar.add(submitButton);

			//-----------------------------------------------
			// Reset button
			//-----------------------------------------------
			resetButton = new Button(CONSTANTS.resetButton(), new ClickHandler() {
				public void onClick(ClickEvent event) {
					onReset("Initial");
					setPaymentVisible(false);
					submitButton.setVisible(false);
					resetButton.setVisible(false);
					confirmationReport.setVisible(false);
				}
			});
			resetButton.setTitle(CONSTANTS.resetHelp());
			resetButton.addStyleName("Button");
			resetButton.setVisible(false);
			bar.add(resetButton);

			//-----------------------------------------------
			// Confirmation button
			//-----------------------------------------------
			confirmationReport = new ReportButton(null, CONSTANTS.confirmationLabel(), tab++) {
				public Report getReport() {
					Report report = new Report();
					report.setDesign(Design.RESERVATION_CONFIRMATION);
					report.setActorid(Party.NO_ACTOR);
					report.setLanguage(AbstractRoot.getLanguage());
					report.setState(Report.CREATED);
					return report;
				}
			};
			confirmationReport.addStyleName("Report");
			confirmationReport.setVisible(false);
			confirmationReport.setVisible(RazorWidget.isRpc());
			bar.add(confirmationReport);

			loader = new Image(AbstractField.BUNDLE.loader());
			loader.setVisible(false);
			bar.add(loader);

			this.add(RazorWidget.getHome());

			if (reservationid == null) {getJsonpProductNameIds(productid);}
			else {getJsonpReservation(reservationid);}
		}
		catch (Throwable x) {throw new RuntimeException(Error.widget_book.getMessage() + " " + x.getMessage());}
	}

	private void setPaymentVisible(boolean visible) {
		logoImages.setVisible(RazorWidget.isSecure() && visible);
		cardholderField.setVisible(RazorWidget.isSecure() && visible);
		cardnumberField.setVisible(RazorWidget.isSecure() && visible);
		cardmonthField.setVisible(RazorWidget.isSecure() && visible);
		carddetailLabel.setVisible(RazorWidget.isSecure() && visible);
		cardyearField.setVisible(RazorWidget.isSecure() && visible);
		cardcodeField.setVisible(RazorWidget.isSecure() && visible);
		quoteField.setVisible(visible);
		amountField.setVisible(visible);
	}
	
	/* The JSONP request callback to get availability. */
//	private void getJsonpAvailable() {
//		if (productField.noValue() || fromtodateField.noValue() || fromtodateField.getDuration(Time.DAY) < 1) {return;}
//		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
//		String url = HasUrls.JSON_URL
//			+ "?service=" + JsonRequest.AVAILABLE
//			+ "&pos=" + pos 
//			+ "&productid=" + productField.getValue() 
//			+ "&fromdate=" + RazorWidget.DF.format(fromtodateField.getValue()) 
//			+ "&todate=" + RazorWidget.DF.format(fromtodateField.getTovalue())
//		;
//
//		loader.setVisible(true);
//		jsonp.requestObject(url, new AsyncCallback<AvailableWidgetItem>() {
//
//			@Override
//			public void onFailure(Throwable x) {
//				loader.setVisible(false);
//				setPaymentVisible(false);
//				throw new RuntimeException("getJsonpAvailable:" + x.getMessage());
//			}
//
//			@Override
//			public void onSuccess(AvailableWidgetItem response) {
//				loader.setVisible(false);
//				if (!response.isAvailable()) {
//					setPaymentVisible(false);
//					AbstractField.addMessage(Level.ERROR, CONSTANTS.availableError(), fromtodateField);
//				}
//			}
//		});
//	}

	/* The JSONP request callback to create reservation. */
	private void getJsonpBook() {

		if (RazorWidget.ifMessage(fromtodateField.noValue(), Level.ERROR, CONSTANTS.fromdateError(), fromtodateField)
				|| RazorWidget.ifMessage(fromtodateField.noTovalue(), Level.ERROR, CONSTANTS.todateError(), fromtodateField)
				|| RazorWidget.ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, CONSTANTS.endBeforeStartError(), fromtodateField)
				|| RazorWidget.ifMessage(fromtodateField.getDuration(Time.DAY) < 1, Level.ERROR, CONSTANTS.endBeforeStartError(), fromtodateField)
				|| RazorWidget.ifMessage(emailaddressField.noValue(), Level.ERROR, CONSTANTS.emailaddressError(), emailaddressField)
				|| RazorWidget.ifMessage(!Party.isEmailAddress(emailaddressField.getValue()), Level.ERROR, CONSTANTS.emailaddressError(), emailaddressField)
				|| RazorWidget.ifMessage(familynameField.noValue(), Level.ERROR, CONSTANTS.familynameError(), familynameField)
				|| RazorWidget.ifMessage(firstnameField.noValue(), Level.ERROR, CONSTANTS.firstnameError(), firstnameField)
				|| RazorWidget.ifMessage(cardholderField.isVisible() && cardholderField.noValue(), Level.ERROR, CONSTANTS.cardholderError(), cardholderField)
				|| RazorWidget.ifMessage(cardnumberField.isVisible() && cardnumberField.noValue(), Level.ERROR, CONSTANTS.cardnumberError(), cardnumberField)
				|| RazorWidget.ifMessage(cardnumberField.isVisible() && !Finance.isCreditCardNumber(cardnumberField.getValue()), Level.ERROR, CONSTANTS.cardnumberError(), cardnumberField)
				|| RazorWidget.ifMessage(cardmonthField.isVisible() && cardmonthField.noValue(), Level.ERROR, CONSTANTS.cardmonthError(), cardmonthField)
				|| RazorWidget.ifMessage(cardyearField.isVisible() && cardyearField.noValue(), Level.ERROR, CONSTANTS.cardyearError(), cardyearField)
				|| RazorWidget.ifMessage(cardcodeField.isVisible() && cardcodeField.noValue(), Level.ERROR, CONSTANTS.cardcodeError(), cardcodeField)
		) {return;}

		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		String url = HOSTS.jsonUrl()
			+ "?service=" + JSONRequest.BOOK
			+ "&pos=" + pos 
			+ "&productid=" + productField.getValue() 
			+ "&fromdate=" + RazorWidget.DF.format(fromtodateField.getValue())
			+ "&todate=" + RazorWidget.DF.format(fromtodateField.getTovalue())
			+ "&emailaddress=" + emailaddressField.getValue()
			+ "&familyname=" + familynameField.getValue()
			+ "&firstname=" + firstnameField.getValue()
			+ "&notes=" + notesField.getValue()
			+ "&cardholder=" + cardholderField.getValue()
			+ "&cardnumber=" + cardnumberField.getValue()
			+ "&cardmonth=" + cardmonthField.getValue()
			+ "&cardyear=" + cardyearField.getValue()
			+ "&cardcode=" + cardcodeField.getValue()
			+ "&amount=" + amountField.getValue()
		;

		loader.setVisible(true);
		jsonp.requestObject(url, new AsyncCallback<BookWidgetItem>() {

			@Override
			public void onFailure(Throwable x) {
				LoadingPopup.getInstance().hide();
				loader.setVisible(false);
				setPaymentVisible(false);
				throw new RuntimeException("getJsonpBook:" + x.getMessage());
			}

			@Override
			public void onSuccess(BookWidgetItem response) {
				loader.setVisible(false);
				if (response.getState().equalsIgnoreCase(RazorWidget.State.SUCCESS.name())) {
					confirmationReport.getReport().setOrganizationid(response.getOrganizationid());
					confirmationReport.getReport().setFromtoname(response.getName());
					confirmationReport.setVisible(true);
					submitButton.setVisible(false);
					resetButton.setVisible(true);
					setPaymentVisible(response.getQuote() > 0.0);
					quoteField.setValue(response.getQuote());
					quoteField.setUnitvalue(response.getCurrency());
					amountField.setValue(response.getAmount());
					amountField.setUnitvalue(response.getCurrency());
					StringBuilder sb = new StringBuilder();
					sb.append("Reservation Number: " + response.getName());
					sb.append("\nArrival Date: " + AbstractRoot.getDF().format(fromtodateField.getValue()));
					sb.append("\nDeparture Date: " + AbstractRoot.getDF().format(fromtodateField.getTovalue()));
					sb.append("\nProperty: " + productField.getName());
					AbstractField.addMessage(Level.VERBOSE, sb.toString(), emailaddressField);
				}
				else {
					submitButton.setVisible(true);
					AbstractField.addMessage(Level.ERROR, response.getMessage(), emailaddressField);
				}
			}
		});
	}

	/* The JSONP request callback to get customer. */
	private void getJsonpParty() {
		if (emailaddressField.noValue()) {return;}
		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		String url = HOSTS.jsonUrl()
			+ "?service=" + JSONRequest.EMAIL
			+ "&pos=" + pos 
			+ "&emailaddress=" + emailaddressField.getValue() 
		;

		loader.setVisible(true);
		jsonp.requestObject(url, new AsyncCallback<EmailWidgetItem>() {

			@Override
			public void onFailure(Throwable x) {
				loader.setVisible(false);
				throw new RuntimeException(Error.party_json + " " + x.getMessage());
			}

			@Override
			public void onSuccess(EmailWidgetItem response) {
				loader.setVisible(false);
				if (response != null) {
					familynameField.setValue(response.getFamilyname());
					firstnameField.setValue(response.getFirstname());
				}
			}
		});
	}

	/* The JSONP request callback to get availability and price. */
	private void getJsonpPriceAvailable() {
		if (productField.noValue()
				|| fromtodateField.noValue() 
				|| fromtodateField.noTovalue() 
				|| fromtodateField.isEndBeforeStart() 
				|| fromtodateField.getDuration(Time.DAY) < 1
		) {return;}

		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		String url = HOSTS.jsonUrl()
			+ "?service=" + JSONRequest.BOOK_AVAILABLE
			+ "&pos=" + pos 
			+ "&productid=" + productField.getValue() 
			+ "&fromdate=" + RazorWidget.DF.format(fromtodateField.getValue()) 
			+ "&todate=" + RazorWidget.DF.format(fromtodateField.getTovalue())
			+ "&emailaddress=" + emailaddressField.getValue() 
		;

		loader.setVisible(true);
		jsonp.requestObject(url, new AsyncCallback<BookWidgetItem>() {

			@Override
			public void onFailure(Throwable x) {
				loader.setVisible(false);
				setPaymentVisible(false);
				throw new RuntimeException(Error.price_json + " " + x.getMessage());
			}

			@Override
			public void onSuccess(BookWidgetItem response) {
				loader.setVisible(false);
				if (response.getState().equalsIgnoreCase(RazorWidget.State.SUCCESS.name())) {
					setPaymentVisible(response.getQuote() > 0.0);
					quoteField.setValue(response.getQuote());
					quoteField.setUnitvalue(response.getCurrency());
					amountField.setValue(response.getAmount());
					amountField.setUnitvalue(response.getCurrency());
					confirmationReport.setVisible(false);
					submitButton.setVisible(true);
					resetButton.setVisible(true);
				}
				else {
					setPaymentVisible(false);
					AbstractField.addMessage(Level.ERROR, response.getMessage(), fromtodateField);
				}
			}
		});
	}

	/* The JSONP request callback to get product name id pairs. */
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

	/* The JSONP request callback to get reservation with reservation id. */
	private void getJsonpReservation(String reservationid) {

		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		String url = HOSTS.jsonUrl()
			+ "?service=" + JSONRequest.RESERVATION
			+ "&pos=" + pos 
			+ "&id=" + reservationid
		;

		loader.setVisible(true);
		jsonp.requestObject(url, new AsyncCallback<ReservationWidgetItem>() {

			@Override
			public void onFailure(Throwable x) {
				loader.setVisible(false);
				throw new RuntimeException(Error.reservation_json.getMessage() + " " + x.getMessage());
			}

			@Override
			public void onSuccess(ReservationWidgetItem response) {
				loader.setVisible(false);
				productField.setValue(response.getProductid());
				emailaddressField.setValue(response.getEmailaddress());
				firstnameField.setValue(response.getFirstname());
				familynameField.setValue(response.getFamilyname());
				fromtodateField.setValue(RazorWidget.DF.parse(response.getFromdate()));
				fromtodateField.setTovalue(RazorWidget.DF.parse(response.getTodate()));
				amountField.setValue(response.getAmount());
				amountField.setUnitvalue(response.getCurrency());
				notesField.setValue(response.getMessage());
				setPaymentVisible(response.getQuote() > 0.0);
				confirmationReport.setVisible(false);
				submitButton.setVisible(true);
				resetButton.setVisible(true);
			}
		});
	}

	/* The JSONP request callback to pay for a reservation. */
	private void getJsonpReservationRS() {

		if (RazorWidget.ifMessage(fromtodateField.noValue(), Level.ERROR, CONSTANTS.fromdateError(), fromtodateField)
				|| RazorWidget.ifMessage(fromtodateField.noTovalue(), Level.ERROR, CONSTANTS.todateError(), fromtodateField)
				|| RazorWidget.ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, CONSTANTS.endBeforeStartError(), fromtodateField)
				|| RazorWidget.ifMessage(fromtodateField.getDuration(Time.DAY) < 1, Level.ERROR, CONSTANTS.endBeforeStartError(), fromtodateField)
				|| RazorWidget.ifMessage(emailaddressField.noValue(), Level.ERROR, CONSTANTS.emailaddressError(), emailaddressField)
				|| RazorWidget.ifMessage(!Party.isEmailAddress(emailaddressField.getValue()), Level.ERROR, CONSTANTS.emailaddressError(), emailaddressField)
				|| RazorWidget.ifMessage(familynameField.noValue(), Level.ERROR, CONSTANTS.familynameError(), familynameField)
				|| RazorWidget.ifMessage(firstnameField.noValue(), Level.ERROR, CONSTANTS.firstnameError(), firstnameField)
				|| RazorWidget.ifMessage(amountField.noValue() || amountField.getValue() <= 0.0, Level.ERROR, CONSTANTS.amountError(), amountField)
				|| RazorWidget.ifMessage(cardholderField.noValue(), Level.ERROR, CONSTANTS.cardholderError(), cardholderField)
				|| RazorWidget.ifMessage(cardnumberField.noValue(), Level.ERROR, CONSTANTS.cardnumberError(), cardnumberField)
				|| RazorWidget.ifMessage(!Finance.isCreditCardNumber(cardnumberField.getValue()), Level.ERROR, CONSTANTS.cardnumberError(), cardnumberField)
				|| RazorWidget.ifMessage(cardmonthField.noValue(), Level.ERROR, CONSTANTS.cardmonthError(), cardmonthField)
				|| RazorWidget.ifMessage(cardyearField.noValue(), Level.ERROR, CONSTANTS.cardyearError(), cardyearField)
				|| RazorWidget.ifMessage(cardcodeField.noValue() || cardcodeField.getValue().length() != 3, Level.ERROR, CONSTANTS.cardcodeError(), cardcodeField)
		) {return;}

		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		String url = HOSTS.jsonUrl()
			+ "?service=" + JSONRequest.RESERVATION
			+ "&method=set" 
			+ "&pos=" + pos 
			+ "&reservationid=" + reservationid
			+ "&emailaddress=" + emailaddressField.getValue()
			+ "&familyname=" + familynameField.getValue()
			+ "&firstname=" + firstnameField.getValue()
			+ "&cardholder=" + cardholderField.getValue()
			+ "&cardnumber=" + cardnumberField.getValue()
			+ "&cardmonth=" + cardmonthField.getValue()
			+ "&cardyear=" + cardyearField.getValue()
			+ "&cardcode=" + cardcodeField.getValue()
			+ "&amount=" + amountField.getValue()
		;

		loader.setVisible(true);
		jsonp.requestObject(url, new AsyncCallback<PayWidgetItem>() {

			@Override
			public void onFailure(Throwable x) {
				LoadingPopup.getInstance().hide();
				loader.setVisible(false);
				throw new RuntimeException(Error.reservation_json.getMessage() + " " + x.getMessage());
			}

			@Override
			public void onSuccess(PayWidgetItem response) {
				loader.setVisible(false);
				if (response.hasState(RazorWidget.State.SUCCESS.name())) {
					StringBuilder sb = new StringBuilder();
					sb.append("Payment Reference: " + response.getName());
					sb.append("\nAmount: " + response.getAmount() + " " + response.getCurrency());
					submitButton.setVisible(false);
					resetButton.setVisible(true);
				}
				else {AbstractField.addMessage(Level.ERROR, response.getMessage(), cardholderField);}
			}
		});
	}
}
