/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.widget.account;

import java.util.Date;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.Component;
import net.cbtltd.client.Components;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.DatespanField;
import net.cbtltd.client.field.DoubleField;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.ReportButton;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.table.ActionCell;
import net.cbtltd.client.field.table.ActionCell.Delegate;
import net.cbtltd.client.field.table.TableError;
import net.cbtltd.client.field.table.TableExecutor;
import net.cbtltd.client.panel.JournalPopup;
import net.cbtltd.client.resource.reservation.ReservationBundle;
import net.cbtltd.client.resource.reservation.ReservationCssResource;
import net.cbtltd.client.widget.RazorWidget;
import net.cbtltd.shared.Account;
import net.cbtltd.shared.Design;
import net.cbtltd.shared.DoubleResponse;
import net.cbtltd.shared.Event;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Report;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Session;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.journal.EventJournal;
import net.cbtltd.shared.report.AccountActionTable;
import net.cbtltd.shared.report.AccountJournalBalance;
import net.cbtltd.shared.reservation.ReservationEventJournalBalance;
import net.cbtltd.shared.session.SessionAutoLogin;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

/** The Class PriceWidget is to display price tables via a widget that can be hosted by a web page. */
public class AccountWidget
extends VerticalPanel
implements ChangeHandler, HasComponents {

	/* Request to log in to the server automatically. */
	private final GuardedRequest<Session> sessionAutologin = new GuardedRequest<Session>() {
		protected boolean error() {return pos == null || pos.isEmpty();}
		protected void send() {super.send(new SessionAutoLogin(pos));}
		protected void receive(Session session) {setValue(session);}
	};

	private GuardedRequest<DoubleResponse> accountBalance = new GuardedRequest<DoubleResponse>() {
		protected boolean error() {
			return (
				AbstractRoot.noOrganizationid()
				|| accountField.noValue()
				|| fromtodateField.noValue()
			);
		}
		protected void send() {super.send(getValue(new AccountJournalBalance()));}
		protected void receive(DoubleResponse response) {balanceField.setValue(response == null ? 0.0 : response.getValue());}
	};

	/*
	 * Gets the specified report action with its attribute values set.
	 *
	 * @param party the specified report action.
	 * @return the report action with its attribute values set.
	 */
	private Report getValue(Report report) {
		report.setOrganizationid(AbstractRoot.getOrganizationid());
		report.setActorid(AbstractRoot.getActorid());
		report.setDate(new Date());
		report.setAccountid(accountField.noValue() ? null : accountField.getValue());
		report.setEntitytype(entityField.noValue() ? null : entityField.getActionService().name());
		report.setEntityid(entityField.noValue() ? null : entityField.getValue());
		report.setFromdate(Time.getDateStart(fromtodateField.getValue()));
		report.setTodate(Time.getDateEnd(fromtodateField.getTovalue()));
		report.setGroupby(fromtodateField.getUnit());
		report.setCurrency(AbstractRoot.getCurrency());
		Log.debug("getValue " + report);
		return report;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#setValue(net.cbtltd.shared.api.HasState)
	 */
	private void setValue(Session session) {
		if (session == null || session.notState(Session.LOGGED_IN)) {Window.alert(AbstractField.CONSTANTS.errPOScode());}
		else {
			AbstractRoot.setSession(session);
			setup();
		}
	}

	private static final AccountConstants CONSTANTS = GWT.create(AccountConstants.class);
	private static final ReservationBundle BUNDLE = ReservationBundle.INSTANCE;
//	private static final ReservationCssResource CSS = BUNDLE.css();
	private static final Components COMPONENTS = new Components();
//	private static final Image loader = new Image(AbstractField.BUNDLE.loader());
	private static final int EVENTACTION_ROWS = 16;

	private static ListField accountField;
	private static ListField entityField;
	private static DatespanField fromtodateField;
	private static DoubleField balanceField;
	private static TableField<EventJournal> eventjournalTable;
	private boolean rpc;
	private String pos;
	private String accountid;
	private int tab = 0;

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
		if (accountField.sent(change)) {
			setEntityAction();
		}
		if (!eventjournalTable.sent(change)) {getAccount();}
	}

	/* A timer to schedule periodic refreshes of the widget. */
	private final Timer refreshTimer = new Timer() {
		public void run() {getAccount();}
	};

	/**
	 * Instantiates a new price widget.
	 * If GWT RPC is used the widget must be in an iFrame in the host HTML page to prevent cross-site problems.
	 * If JSONP is used the widget JavaScript must be served from the same site as the host HTML page.
	 *
	 * @param rpc is true if GWT RPC is to be used, else JSONP is used.
	 * @param pos the point of sale code of the organization hosting the widget.
	 * @param accountid the ID of the product to be priced.
	 */
	public AccountWidget(boolean rpc, String pos, String accountid) {
		this.rpc = rpc;
		this.pos = pos;
		this.accountid = accountid;
		this.setStylePrimaryName("AccountWidget");
		sessionAutologin.execute();
	}
	
	public void setup() {
		AbstractField.CSS.ensureInjected();
		final HorizontalPanel form = new HorizontalPanel();
		this.add(form);
		
		//-----------------------------------------------
		// Account field
		//-----------------------------------------------
		accountField = new ListField(this, null,
				new NameIdAction(Service.ACCOUNT),
				CONSTANTS.accountLabel(),
				true,
				tab++);
		accountField.setFieldStyle("Field");
		accountField.setLabelStyle("Label");
		accountField.setIds(NameId.getCdlist(accountid));
//			accountField.setHelpText(CONSTANTS.accountHelp());
		form.add(accountField);
		
		//-----------------------------------------------
		// Entity field
		//-----------------------------------------------
		entityField = new ListField(this, null,
				new NameIdAction(Service.PRODUCT),
				CONSTANTS.entityLabel(),
				false,
				tab++);
		entityField.setFieldStyle("Field");
		entityField.setLabelStyle("Label");
		entityField.setVisible(false);
		form.add(entityField);

		//-----------------------------------------------
		// From and To Dates
		//-----------------------------------------------
		fromtodateField = new DatespanField(this, null,
				CONSTANTS.fromtodateLabel(),
				tab++);
		fromtodateField.setFieldStyle("Field");
		fromtodateField.setLabelStyle("Label");
		fromtodateField.setHelpText(CONSTANTS.fromtodateHelp());
		form.add(fromtodateField);

		//-----------------------------------------------
		// Balance field
		//-----------------------------------------------
		balanceField = new DoubleField(this, null,
				CONSTANTS.balanceLabel(),
				AbstractField.AF,
				tab++);
		balanceField.setEnabled(false);
		balanceField.setFieldStyle("Field");
		balanceField.setLabelStyle("Label");
		//TODO: form.add(balanceField);

		//-----------------------------------------------
		// Financial Transaction selection handler
		//-----------------------------------------------
		final NoSelectionModel<EventJournal> selectionModel = new NoSelectionModel<EventJournal>();
		SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				JournalPopup.getInstance().show(selectionModel.getLastSelectedObject(), null, eventjournalTable);
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// Financial Transaction table
		//-----------------------------------------------
		eventjournalTable = new TableField<EventJournal>(this, null, 
				new AccountActionTable(),
				selectionModel, 
				EVENTACTION_ROWS,
				tab++);

		eventjournalTable.setEmptyValue(eventactiontableEmpty());
		eventjournalTable.setOrderby(Event.DATE);

		eventjournalTable.setTableError(new TableError() {
			@Override
			public boolean error() {return (
				accountField.noValue()
			);}
		});

		eventjournalTable.setTableExecutor(new TableExecutor<AccountActionTable>() {
			public void execute(AccountActionTable action) {
				action.setOrganizationid(AbstractRoot.getOrganizationid());
				action.setAccountid(accountField.getValue());
				action.setCurrency(AbstractRoot.getCurrency());
				action.setFromdate(fromtodateField.getValue());
				action.setTodate(fromtodateField.getTovalue());
				action.setTypes("'Current Liability','Current Asset','Capital','Expense','Long Term Liability','Fixed Asset','Income','Other Asset','CostOfSales','CurrentAsset','LongTermLiability','CurrentLiability'");
				accountBalance.execute();
			}
		});
		
		int col = 0;

		//-----------------------------------------------
		// Change button
		//-----------------------------------------------
		Column<EventJournal, EventJournal> changeButton = new Column<EventJournal, EventJournal>( 
				new ActionCell<EventJournal>(AbstractField.CONSTANTS.allChange(), AbstractField.CSS.cbtTableFieldChangeButton(),
						new Delegate<EventJournal>(){
					public void execute( EventJournal eventaction ) {
						JournalPopup.getInstance().show(eventaction, null, eventjournalTable);
					}
				}
				)
		)
		{
			public EventJournal getValue(EventJournal eventjournal){return eventjournal;}//TODO: selectForm(row); 
		};

		eventjournalTable.addColumn(changeButton);

		//-----------------------------------------------
		// Process column
		//-----------------------------------------------
		Column<EventJournal, String> process = new Column<EventJournal, String>(new TextCell()) {
			@Override
			public String getValue( EventJournal eventjournal ) {return eventjournal.getProcess();}
		};
		eventjournalTable.addStringColumn(process, CONSTANTS.eventactionHeaders()[col++], EventJournal.PROCESS);

		//-----------------------------------------------
		// State column
		//-----------------------------------------------
//			Column<EventJournal, String> state = new Column<EventJournal, String>( new TextCell()) {
//				@Override
//				public String getValue( EventJournal eventjournal ) {return eventjournal.getState();}
//			};
//			eventjournalTable.addStringColumn(state, CONSTANTS.eventactionHeaders()[col++], EventJournal.STATE);

		//-----------------------------------------------
		// Name column
		//-----------------------------------------------
		Column<EventJournal, String> name = new Column<EventJournal, String>( new TextCell() ) {
			@Override
			public String getValue( EventJournal eventjournal ) {return eventjournal.getName();}
		};
		eventjournalTable.addStringColumn(name, CONSTANTS.eventactionHeaders()[col++], EventJournal.NAME);

		//-----------------------------------------------
		// Date column
		//-----------------------------------------------
		Column<EventJournal, Date> date = new Column<EventJournal, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( EventJournal eventjournal ) {return eventjournal.getDate();}
		};
		eventjournalTable.addDateColumn(date, CONSTANTS.eventactionHeaders()[col++], EventJournal.DATE);

		//-----------------------------------------------
		// Debit Amount column
		//-----------------------------------------------
		Column<EventJournal, Number> debitAmount = new Column<EventJournal, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( EventJournal eventjournal ) {return eventjournal.getDebitamount();}
		};
		eventjournalTable.addNumberColumn( debitAmount, CONSTANTS.eventactionHeaders()[col++], EventJournal.DEBITAMOUNT);

		//-----------------------------------------------
		// Credit Amount column
		//-----------------------------------------------
		Column<EventJournal, Number> creditAmount = new Column<EventJournal, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( EventJournal eventjournal ) {return eventjournal.getCreditamount();}
		};
		eventjournalTable.addNumberColumn( creditAmount, CONSTANTS.eventactionHeaders()[col++], EventJournal.CREDITAMOUNT);

		//-----------------------------------------------
		// Currency column
		//-----------------------------------------------
		Column<EventJournal, String> currency = new Column<EventJournal, String>( new TextCell() ) {
			@Override
			public String getValue( EventJournal eventjournal ) {return eventjournal.getCurrency();}
		};
		eventjournalTable.addStringColumn(currency, CONSTANTS.eventactionHeaders()[col++], EventJournal.UNIT);

		//-----------------------------------------------
		// Description column
		//-----------------------------------------------
		Column<EventJournal, String> description = new Column<EventJournal, String>( new TextCell() ) {
			@Override
			public String getValue( EventJournal eventjournal ) {return eventjournal.getDescription();}
		};
		eventjournalTable.addStringColumn(description, CONSTANTS.eventactionHeaders()[col++], EventJournal.DESCRIPTION);
//			eventjournalTable.addStyleName(CSS.eventactionStyle());

		this.add(eventjournalTable);
		
		this.add(createFinancialcommands());
		this.add(createFinancialreports());

		onRefresh();
		//getAccount();
		refreshTimer.cancel();
		refreshTimer.schedule(RazorWidget.delay);
	}

	/* 
	 * Creates the panel displayed when there are no financial transactions.
	 * 
	 * @return the empty panel.
	 */
	private Widget eventactiontableEmpty() {
		FlowPanel panel = new FlowPanel();
		panel.add(new HTML(CONSTANTS.tableEmpty()));
		panel.addStyleName("Empty");
		return panel;
	}
	
	/* 
	 * Creates the panel of financial commands.
	 * 
	 * @return the panel of financial commands.
	 */
	private HorizontalPanel createFinancialcommands() {
		
		final HorizontalPanel bar = new HorizontalPanel();
//		bar.addStyleName(CSS.financialcommandBar());

		//-----------------------------------------------
		// Reservation Sale button
		//-----------------------------------------------
		final Button reservationsaleButton = new Button(CONSTANTS.reservationsaleButton());
		reservationsaleButton.setTitle(CONSTANTS.reservationsaleHelp());
		reservationsaleButton.addStyleName("Button");
		reservationsaleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				JournalPopup.getInstance().show(Event.Type.ReservationSale, eventjournalTable);
			}
		});
		bar.add(reservationsaleButton);
		
		//-----------------------------------------------
		// Sundry Sale button
		//-----------------------------------------------
		final Button saleButton = new Button(CONSTANTS.saleButton());
		saleButton.setTitle(CONSTANTS.saleHelp());
		saleButton.addStyleName("Button");
		saleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				JournalPopup.getInstance().show(Event.Type.Sale, eventjournalTable);
			}
		});
		bar.add(saleButton);
		
		//-----------------------------------------------
		// Receipt button
		//-----------------------------------------------
		final Button receiptButton = new Button(CONSTANTS.receiptButton());
		receiptButton.setTitle(CONSTANTS.receiptHelp());
		receiptButton.addStyleName("Button");
		receiptButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				JournalPopup.getInstance().show(Event.Type.Receipt, eventjournalTable);
			}
		});
		bar.add(receiptButton);
		
		//-----------------------------------------------
		// Purchase button
		//-----------------------------------------------
		final Button purchaseButton = new Button(CONSTANTS.purchaseButton());
		purchaseButton.setTitle(CONSTANTS.purchaseHelp());
		purchaseButton.addStyleName("Button");
		purchaseButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				JournalPopup.getInstance().show(Event.Type.Purchase, eventjournalTable);
			}
		});
		bar.add(purchaseButton);
		
		//-----------------------------------------------
		// Purchase For button
		//-----------------------------------------------
		final Button purchasesaleButton = new Button(CONSTANTS.purchasesaleButton());
		purchasesaleButton.setTitle(CONSTANTS.purchasesaleHelp());
		purchasesaleButton.addStyleName("Button");
		purchasesaleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				JournalPopup.getInstance().show(Event.Type.PurchaseSale, eventjournalTable);
			}
		});
		bar.add(purchasesaleButton);
		
		//-----------------------------------------------
		// Payment button
		//-----------------------------------------------
		final Button paymentButton = new Button(CONSTANTS.payButton());
		paymentButton.setTitle(CONSTANTS.payHelp());
		paymentButton.addStyleName("Button");
		paymentButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				JournalPopup.getInstance().show(Event.Type.Payment, eventjournalTable);
			}
		});
		bar.add(paymentButton);
		

		return bar;
	}
		/* 
		 * Creates the panel of financial reports.
		 * 
		 * @return the panel of financial reports.
		 */
		private HorizontalPanel createFinancialreports() {
			
			final HorizontalPanel bar = new HorizontalPanel();
//			bar.addStyleName(CSS.financialcommandBar());

		//-----------------------------------------------
		// Account Transactions Report button
		//-----------------------------------------------
		final ReportButton accountTransactions = new ReportButton(null, CONSTANTS.accounttransactionsLabel(), tab++) {
			@Override
			public Report getReport() {
				Report report = new Report();;
				report.setOrganizationid(AbstractRoot.getOrganizationid());
				report.setActorid(AbstractRoot.getActorid());
				report.setDesign(Design.ACCOUNT_TRANSACTION);
				report.setFromtoname(accountField.getName());
				report.setLanguage(AbstractRoot.getLanguage());
				report.setState(Report.CREATED);
				return report;
			}
		};
		accountTransactions.setLabelStyle("Report");
		accountTransactions.setTitle(CONSTANTS.accounttransactionsHelp());
		bar.add(accountTransactions);
		
		//-----------------------------------------------
		// Customer Statement Report button
		//-----------------------------------------------
		final ReportButton accountSummary =  new ReportButton(null, CONSTANTS.accountsummaryLabel(), tab++) {
			@Override
			public Report getReport() {
				Report report = new Report();;
				report.setOrganizationid(AbstractRoot.getOrganizationid());
				report.setActorid(AbstractRoot.getActorid());
				report.setDesign(Design.ACCOUNT_SUMMARY);
				report.setFromtoname(accountField.getName());
				report.setLanguage(AbstractRoot.getLanguage());
				report.setState(Report.CREATED);
				return report;
			}
		};
		accountSummary.setLabelStyle("Report");
		accountSummary.setTitle(CONSTANTS.accountsummaryHelp());
		bar.add(accountSummary);

		return bar;
	}
	
	/* Set the refresh action of the entity field according to the selected account. */
	private void setEntityAction() {
		entityField.setVisible(true);
		if (accountField.hasValue(Account.ACCOUNTS_PAYABLE)) {entityField.setAction(new NameIdAction(Service.PARTY, Party.Type.Supplier.name()));}
		else if(accountField.hasValue(Account.ACCOUNTS_RECEIVABLE)) {entityField.setAction(new NameIdAction(Service.PARTY, Party.Type.Customer.name()));}
		else if(accountField.hasValue(Account.PURCHASE_SUSPENSE)) {entityField.setAction(new NameIdAction(Service.PARTY, Party.Type.Supplier.name()));}
		else if(accountField.hasValue(Account.SALES_REVENUE)) {entityField.setAction(new NameIdAction(Service.PRODUCT, Product.Type.Accommodation.name()));}
		else if(accountField.hasValue(Account.TRADING_LOANS)) {entityField.setAction(new NameIdAction(Service.PARTY, null));}
		else if(accountField.hasValue(Account.VAT_INPUT)) {entityField.setAction(new NameIdAction(Service.PARTY, Party.Type.Jurisdiction.name()));}
		else if(accountField.hasValue(Account.VAT_OUTPUT)) {entityField.setAction(new NameIdAction(Service.PARTY, Party.Type.Jurisdiction.name()));}
		else if(accountField.hasValue(Account.ACCUMULATED_DEPRECIATION)) {entityField.setAction(new NameIdAction(Service.ASSET, null));}
		else if(accountField.hasValue(Account.ASSET_COST)) {entityField.setAction(new NameIdAction(Service.ASSET, null));}
		else if(accountField.hasValue(Account.DEPRECIATION_EXPENSE)) {entityField.setAction(new NameIdAction(Service.ASSET, null));}
		//			else if(accountField.hasValue(Account.ACCUMULATED_DEPRECIATION)) {entityField.setAction(new NameIdAction(Service.ASSET));}
		else if(accountField.hasValue(Account.FINANCE)) {entityField.setAction(new NameIdAction(Service.FINANCE, null));}
		else if(accountField.hasValue(Account.INVENTORY)) {entityField.setAction(new NameIdAction(Service.PRODUCT, Product.Type.Inventory.name()));}
		else if(accountField.hasValue(Account.COST_OF_SALES)) {entityField.setAction(new NameIdAction(Service.TASK, null));}
		else if(accountField.hasValue()){entityField.setVisible(false);}
	}

	
	/* The request callback to refresh prices */
	private void getAccount() {
		if (rpc) {eventjournalTable.execute();}
//		else {getJsonpPrice();}
	}
}

//	/* The JSONP request callback to refresh prices */
//	private void getJsonpPrice() {
//		if (entityField.noValue() || accountField.noValue()) {return;}
//
//		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
//		String url = HasUrls.JSON_URL
//			+ "?service=" + JSONRequest.PRICE
//			+ "&pos=" + pos 
//			+ "&productid=" + entityField.getValue()
//			+ "&date=" + RazorWidget.DF.format(RazorWidget.getDate())
//			+ "&rows=" + RazorWidget.getRows()
//			+ "&currency=" + accountField.getValue()
//		;
//
//		loader.setVisible(true);
//		jsonp.requestObject(url, new AsyncCallback<PriceWidgetItems>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				loader.setVisible(false);
//				Window.alert("getJsonpPrice onFailure");
//				throw new RuntimeException("getJsonpPrice Failure:" + caught.getMessage());
//			}
//
//			@Override
//			public void onSuccess(PriceWidgetItems response) {
//				loader.setVisible(false);
//				if (response == null || response.getItems() == null || response.getItems().length() == 0) {AbstractField.addMessage(Level.ERROR, CONSTANTS.priceError(), priceGrid);}
//				else {
//					ArrayList<Price> prices = new ArrayList<Price>();
//					for (int row = 0; row < response.getItems().length(); row++) {
//						PriceWidgetItem value = response.getItems().get(row);
//						Log.debug("row " + row + " " + value.string());
//						Price price = new Price();
//						price.setDate(RazorWidget.DF.parse(value.getFromdate()));
//						price.setTodate(RazorWidget.DF.parse(value.getTodate()));
//						price.setValue(value.getPrice());
//						price.setMinimum(value.getMinimum());
//						prices.add(price);
//					}
//					priceGrid.setValue(prices);
//				}
//			}
//		});
//	}
//	
//	/* 
//	 * The request callback to get product name ID pairs.
//	 * 
//	 *  @param productid the ID of the selected product.
//	 */
//	private void getProduct(String productid) {
//		if (rpc) {entityField.onRefresh();}
//		else {getJsonpProduct(productid);}	
//	}
//	
//	/* The JSONP request callback to get product name ID pairs. */
//	private void getJsonpProduct(String productid) {
//
//		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
//		String url = HasUrls.JSON_URL
//		+ "?service=" + JSONRequest.PRODUCT
//		+ "&pos=" + pos 
//		+ "&productid=" + productid 
//		;
//
//		loader.setVisible(true);
//		jsonp.requestObject(url, new AsyncCallback<NameIdWidgetItems>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				loader.setVisible(false);
//				throw new RuntimeException("Failure:" + caught.getMessage());
//			}
//
//			@Override
//			public void onSuccess(NameIdWidgetItems response) {
//				loader.setVisible(false);
//				if (response != null && response.getItems() != null && response.getItems().length() > 0) {
//					ArrayList<NameId> items = new ArrayList<NameId>();
//					for (int index = 0; index < response.getItems().length(); index++) {
//						items.add(new NameId(response.getItems().get(index).getName(), response.getItems().get(index).getId()));
//					}
//					entityField.setItems(items);
//					getJsonpPrice();
//				}
//			}
//		});
//	}
//}
