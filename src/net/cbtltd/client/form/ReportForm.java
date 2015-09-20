/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.form;

import java.util.Date;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.DatespanField;
import net.cbtltd.client.field.DoubleField;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.NameIdField;
import net.cbtltd.client.field.ShuttleField;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.table.ActionCell;
import net.cbtltd.client.field.table.ActionCell.Delegate;
import net.cbtltd.client.field.table.TableError;
import net.cbtltd.client.field.table.TableExecutor;
import net.cbtltd.client.field.visualization.ColumnChartField;
import net.cbtltd.client.field.visualization.LineChartField;
import net.cbtltd.client.field.visualization.PieChartField;
import net.cbtltd.client.panel.AccountPopup;
import net.cbtltd.client.panel.DesignPopup;
import net.cbtltd.client.panel.JournalPopup;
import net.cbtltd.client.resource.report.ReportBundle;
import net.cbtltd.client.resource.report.ReportConstants;
import net.cbtltd.client.resource.report.ReportCssResource;
import net.cbtltd.shared.Account;
import net.cbtltd.shared.DoubleResponse;
import net.cbtltd.shared.Event;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.NameIdState;
import net.cbtltd.shared.NameIdType;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Report;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.journal.EventJournal;
import net.cbtltd.shared.journal.JournalCurrency;
import net.cbtltd.shared.party.Organization;
import net.cbtltd.shared.report.AccountActionTable;
import net.cbtltd.shared.report.AccountChart;
import net.cbtltd.shared.report.AccountJournalBalance;
import net.cbtltd.shared.report.AccountSummary;
import net.cbtltd.shared.report.AccountSummaryTable;
import net.cbtltd.shared.report.AverageRateChart;
import net.cbtltd.shared.report.ConfirmedChart;
import net.cbtltd.shared.report.FeedbackChart;
import net.cbtltd.shared.report.OccupancyChart;
import net.cbtltd.shared.report.OutcomeChart;
import net.cbtltd.shared.report.ProfitChart;
import net.cbtltd.shared.report.RevenueChart;
import net.cbtltd.shared.report.RevparChart;
import net.cbtltd.shared.report.StarterChart;
import net.cbtltd.shared.report.WorkflowChart;

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
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
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
import com.google.gwt.visualization.client.LegendPosition;

/**
 * The Class ReportForm is to enter and display financial and other information about the property manager which employs the currently logged in user.
 */
public class ReportForm
extends AbstractForm<Report> {

//	/* The action to get a report design from the server. */
//	private static GuardedRequest<Design> designRead = new GuardedRequest<Design>() {
//		protected boolean error() {return designField.noValue();}
//		protected void send() {super.send(getValue(new DesignRead()));}
//		protected void receive(Design design){setValue(design);}
//	};

	/* The action to calculate an account balance on the server. */
	private GuardedRequest<DoubleResponse> accountBalance = new GuardedRequest<DoubleResponse>() {
		protected boolean error() {
			return (
				organizationField.noValue()
				|| fromtodateField.noValue()
				|| fromtodateField.noUnit()
			);
		}
		protected void send() {super.send(getValue(new AccountJournalBalance()));}
		protected void receive(DoubleResponse response) {balanceField.setValue(response == null ? 0.0 : response.getValue());}
	};

	private static final ReportConstants CONSTANTS = GWT.create(ReportConstants.class);
	private static final ReportBundle BUNDLE = ReportBundle.INSTANCE;
	private static final ReportCssResource CSS = BUNDLE.css();
	private static final int ACCOUNTJOURNAL_ROWS = 13;
	private static final int ACCOUNTSUMMARY_ROWS = 20;
	private static final int MAX_DATA_POINTS = 60;
	
	private static NameIdField organizationField;
	private static ListField processField;
	private static SuggestField accountField;
	private static SuggestField entityField;
	private static DatespanField fromtodateField;
	private static ShuttleField statesField;
	private static ShuttleField typesField;

//	private static ListField designField;
//	private static SuggestspanField namerangeField;
	private static ListField currencyField;
//	private static ListField formatField;
//	private static ReportButton reportButton;

	private static StackLayoutPanel stackPanel;

	private static FlowPanel financialPanel;
	private static DoubleField balanceField;
	private static TableField<EventJournal> accountjournalTable;
	private static TableField<AccountSummary> accountsummaryTable;
	private static LineChartField accountsummaryChart;
	private static LineChartField revenuesummaryChart;
	private static LineChartField profitsummaryChart;
	private static LineChartField averagerateChart;
	private static LineChartField revparChart;
	private static LineChartField occupancyChart;
	private static LineChartField confirmedChart;
	private static LineChartField feedbackChart;
	private static PieChartField starterChart;
	private static PieChartField outcomeChart;
	private static ColumnChartField workflowChart;
	/* The list of period types. */
	private static final String[] PERIODS = { Time.DAY.name(), Time.WEEK.name(), Time.MONTH.name(), Time.QUARTER.name(), Time.YEAR.name() };

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#permission()
	 */
	@Override
	public short[] permission(){return AccessControl.REPORT_PERMISSION;}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#getValue()
	 */
	@Override
	public Report getValue() {return getValue(new Report());}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#hasChanged()
	 */
	@Override
	public boolean hasChanged() {return false;}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onFocus()
	 */
	@Override
	public void onFocus() {
		organizationField.setId(AbstractRoot.getOrganizationid());
		organizationField.setName(AbstractRoot.getOrganizationname());
		accountjournalTable.execute();
		accountBalance.execute();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {
		if (accountField.sent(change)) {setEntityAction();}

		if (stackPanel.getVisibleIndex() == 0 && !accountjournalTable.sent(change)) {
			accountjournalTable.execute();
			accountBalance.execute();
		}
		else if (stackPanel.getVisibleIndex() == 1 && !accountsummaryTable.sent(change)) {
			accountsummaryTable.execute();
		}
		else if (stackPanel.getVisibleIndex() == 2) {
			accountsummaryChart.setLabel(typesField.noValue() || !typesField.isAvailable() ? "All Types" : "Types: " + NameId.getCdlist(typesField.getValue()).replaceAll("'",""));
			accountsummaryChart.execute();
		}
		else if (stackPanel.getVisibleIndex() == 3) {
			revenuesummaryChart.setLabel(entityField.getName());
			revenuesummaryChart.execute();
		}
		else if (stackPanel.getVisibleIndex() == 4) {
			profitsummaryChart.setLabel(entityField.getName());
			profitsummaryChart.execute();
		}
		else if (stackPanel.getVisibleIndex() == 5) {
			averagerateChart.setLabel(entityField.getName());
			averagerateChart.execute();
		}
		else if (stackPanel.getVisibleIndex() == 6) {
			revparChart.setLabel(entityField.getName());
			revparChart.execute();
		}
		else if (stackPanel.getVisibleIndex() == 7) {
			occupancyChart.setLabel(entityField.getName());
			occupancyChart.execute();
		}
		else if (stackPanel.getVisibleIndex() == 8) {
			confirmedChart.setLabel(entityField.getName());
			confirmedChart.execute();
		}
		else if (stackPanel.getVisibleIndex() == 9) {
			feedbackChart.setLabel(entityField.getName());
			feedbackChart.execute();
		}
		else if (stackPanel.getVisibleIndex() == 10) {
			starterChart.execute();
			outcomeChart.execute();
		}
		else if (stackPanel.getVisibleIndex() == 11) {
			workflowChart.setLabel(statesField.noValue() || !statesField.isAvailable() ? "All States" : "States: " + NameId.getCdlist(statesField.getValue()).replaceAll("'",""));
			workflowChart.execute();
		}
	}

	/*
	 * Gets the specified report action with its attribute values set.
	 *
	 * @param party the specified report action.
	 * @return the report action with its attribute values set.
	 */
	private Report getValue(Report report) {
		report.setOrganizationid(organizationField.getId());
		report.setActorid(AbstractRoot.getActorid());
		report.setState(state);
		report.setDate(new Date());
		report.setProcess(processField.noValue() ? null : processField.getValue());
		report.setAccountid(accountField.noValue() ? null : accountField.getValue());
		report.setEntitytype(entityField.noValue() ? null : entityField.getActionService().name());
		report.setEntityid(entityField.noValue() ? null : entityField.getValue());
		report.setFromdate(Time.getDateStart(fromtodateField.getValue()));
		report.setTodate(Time.getDateEnd(fromtodateField.getTovalue()));
		report.setGroupby(fromtodateField.getUnit());
		report.setStatelist(statesField.getValue());
		report.setTypelist(typesField.getValue());
		report.setCurrency(currencyField.getValue());
		Log.debug("getValue " + report);
		return report;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#setValue(net.cbtltd.shared.api.HasState)
	 */
	public void setValue(Report report) {}

	/* Set the refresh action of the entity field according to the selected account. */
	private void setEntityAction() {
		entityField.setEnabled(true);
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
		else if(accountField.hasValue()){entityField.setEnabled(false);}
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

		FlowPanel west = new FlowPanel();
		content.add(west);
		west.add(createControl());
		
		FlowPanel shadow = new FlowPanel();
		shadow.addStyleName(AbstractField.CSS.cbtAbstractShadow());
		panel.add(shadow);

		//-----------------------------------------------
		// Stack Panel
		//-----------------------------------------------
		stackPanel = new StackLayoutPanel(com.google.gwt.dom.client.Style.Unit.EM);
		content.add(stackPanel);
		stackPanel.addStyleName(CSS.stackStyle());
		stackPanel.add(createFinancialpanel(), CONSTANTS.accountjournaltableLabel(), 1.5);
		stackPanel.add(createSummaryactiontable(), CONSTANTS.accountsummarytableLabel(), 1.5);
		stackPanel.setTitle(CONSTANTS.chartzoomHelp());
		stackPanel.addSelectionHandler(
				new SelectionHandler<Integer>() {
					public void onSelection(SelectionEvent<Integer> event) {

						processField.setValue(null);
						accountField.setValue(null);
						entityField.setValue(null);
						statesField.deselect();
						statesField.setVisible(false);
						typesField.deselect();
						typesField.setVisible(false);
					
						switch (event.getSelectedItem()) {
						case 0:	
							processField.setEnabled(true);
							accountField.setEnabled(true);
							entityField.setLabel(CONSTANTS.partyLabel());
							entityField.setEnabled(true);
							entityField.setAction(new NameIdAction(Service.PARTY));
							typesField.setVisible(true);
							typesField.setAction(new NameIdType(Service.ACCOUNT));
							accountjournalTable.execute(); 
							accountBalance.execute();
							break;
						case 1:	
							processField.setEnabled(true);
							accountField.setEnabled(true);
							entityField.setLabel(CONSTANTS.partyLabel());
							entityField.setEnabled(true);
							entityField.setAction(new NameIdAction(Service.PARTY));
							typesField.setVisible(true);
							typesField.setAction(new NameIdType(Service.ACCOUNT));
							accountsummaryTable.execute(); 
							break;
						case 2:
							processField.setEnabled(true);
							accountField.setEnabled(true);
							entityField.setLabel(CONSTANTS.partyLabel());
							entityField.setEnabled(true);
							entityField.setAction(new NameIdAction(Service.PARTY));
							typesField.setVisible(true);
							typesField.setAction(new NameIdType(Service.ACCOUNT));
							accountsummaryChart.execute();
							break;
						case 3:
							processField.setEnabled(false);
							accountField.setEnabled(false);
							entityField.setLabel(CONSTANTS.accommodationLabel());
							entityField.setEnabled(true);
							entityField.setAction(new NameIdAction(Service.PRODUCT, Product.Type.Accommodation.name()));
							revenuesummaryChart.execute(); 
							break;
						case 4:
							processField.setEnabled(false);
							accountField.setEnabled(false);
							entityField.setLabel(CONSTANTS.accommodationLabel());
							entityField.setEnabled(true);
							entityField.setAction(new NameIdAction(Service.PRODUCT, Product.Type.Accommodation.name()));
							profitsummaryChart.execute();
							break;
						case 5:
							processField.setEnabled(false);
							accountField.setEnabled(false);
							entityField.setLabel(CONSTANTS.accommodationLabel());
							entityField.setEnabled(true);
							entityField.setAction(new NameIdAction(Service.PRODUCT, Product.Type.Accommodation.name()));
							averagerateChart.execute();
							break;
						case 6:
							processField.setEnabled(false);
							accountField.setEnabled(false);
							entityField.setLabel(CONSTANTS.accommodationLabel());
							entityField.setEnabled(true);
							entityField.setAction(new NameIdAction(Service.PRODUCT, Product.Type.Accommodation.name()));
							revparChart.execute();
							break;
						case 7:
							processField.setEnabled(false);
							accountField.setEnabled(false);
							entityField.setLabel(CONSTANTS.accommodationLabel());
							entityField.setEnabled(true);
							entityField.setAction(new NameIdAction(Service.PRODUCT, Product.Type.Accommodation.name()));
							occupancyChart.execute();
							break;
						case 8:
							processField.setEnabled(false);
							accountField.setEnabled(false);
							entityField.setLabel(CONSTANTS.accommodationLabel());
							entityField.setEnabled(true);
							entityField.setAction(new NameIdAction(Service.PRODUCT, Product.Type.Accommodation.name()));
							confirmedChart.execute();
							break;
						case 9:
							processField.setEnabled(false);
							accountField.setEnabled(false);
							entityField.setLabel(CONSTANTS.accommodationLabel());
							entityField.setEnabled(true);
							entityField.setAction(new NameIdAction(Service.PRODUCT, Product.Type.Accommodation.name()));
							feedbackChart.execute();
							break;
						case 10:
							processField.setEnabled(false);
							accountField.setEnabled(false);
							entityField.setLabel(CONSTANTS.marketingLabel());
							entityField.setEnabled(true);
							entityField.setAction(new NameIdAction(Service.PRODUCT, Product.Type.Marketing.name()));
							starterChart.execute();
							outcomeChart.execute();
							break;
						case 11:
							processField.setEnabled(false);
							accountField.setEnabled(false);
							entityField.setLabel(CONSTANTS.actorLabel());
							entityField.setEnabled(true);
							entityField.setAction(new NameIdAction(Service.PARTY, Party.Type.Actor.name()));
							statesField.setVisible(true);
							statesField.setAction(new NameIdState(Service.WORKFLOW));
							workflowChart.execute();
							break;
						}
					}
				}
		);

		//-----------------------------------------------
		// Account Summary Chart
		//-----------------------------------------------
		accountsummaryChart = new LineChartField(this, null, new AccountChart(), null, tab++);
		accountsummaryChart.setEmptyValue(accountsummarychartEmpty());
		accountsummaryChart.setFieldStyle(CSS.chartStyle());
		accountsummaryChart.setTitle(CONSTANTS.chartzoomHelp());
		accountsummaryChart.setZoom(new Runnable() {
			public void run() {new AccountSummaryPopup().show();}
		});
		accountsummaryChart.setTableError(new TableError() {
			@Override
			public boolean error() {
				return (
					ifMessage(organizationField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), organizationField)
					|| ifMessage(fromtodateField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
					|| ifMessage(fromtodateField.noTovalue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
					|| ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
					|| ifMessage(fromtodateField.getDuration() > MAX_DATA_POINTS, Level.ERROR, CONSTANTS.durationError(), fromtodateField)
				);
			}
		});
		accountsummaryChart.setTableExecutor(new TableExecutor<AccountChart>() {
			public void execute(AccountChart action) {getValue(action);}
		});
		stackPanel.add(accountsummaryChart, CONSTANTS.accountsummarychartLabel(), 1.5);

		//-----------------------------------------------
		// Revenue Summary Chart
		//-----------------------------------------------
		revenuesummaryChart = new LineChartField(this, null, new RevenueChart(), null, tab++);
		revenuesummaryChart.setEmptyValue(revenuesummarychartEmpty());
		revenuesummaryChart.setFieldStyle(CSS.chartStyle());
		revenuesummaryChart.setLabel(entityField.getName());
		revenuesummaryChart.setZoom(new Runnable() {
			public void run() {new RevenuePopup().show();}
		});
		revenuesummaryChart.setTableError(new TableError() {
			@Override
			public boolean error() {
				return (
						ifMessage(organizationField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), organizationField)
						|| ifMessage(fromtodateField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.noTovalue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
						|| ifMessage(fromtodateField.getDuration() > MAX_DATA_POINTS, Level.ERROR, CONSTANTS.durationError(), fromtodateField)
				);
			}
		});
		revenuesummaryChart.setTableExecutor(new TableExecutor<RevenueChart>() {
			public void execute(RevenueChart action) {getValue(action);}
		});
		stackPanel.add(revenuesummaryChart, CONSTANTS.revenuesummarychartLabel(), 1.5);

		//-----------------------------------------------
		// Profit Summary Chart
		//-----------------------------------------------
		profitsummaryChart = new LineChartField(this, null, new ProfitChart(), null, tab++);
		profitsummaryChart.setEmptyValue(profitsummarychartEmpty());
		profitsummaryChart.setFieldStyle(CSS.chartStyle());
		profitsummaryChart.setLabel(entityField.getName());
		profitsummaryChart.setZoom(new Runnable() {
			public void run() {new ProfitPopup().show();}
		});
		profitsummaryChart.setTableError(new TableError() {
			@Override
			public boolean error() {
				return (
						organizationField.noValue()
						|| fromtodateField.noValue()
						|| fromtodateField.noUnit()
				);
			}
		});
		profitsummaryChart.setTableExecutor(new TableExecutor<ProfitChart>() {
			public void execute(ProfitChart action) {getValue(action);}
		});
		stackPanel.add(profitsummaryChart, CONSTANTS.profitsummarychartLabel(), 1.5);

		//-----------------------------------------------
		// Average Daily Rate Chart
		//-----------------------------------------------
		averagerateChart = new LineChartField(this, null, new AverageRateChart(), null, tab++);
		averagerateChart.setEmptyValue(averageratechartEmpty());
		averagerateChart.setFieldStyle(CSS.chartStyle());
		averagerateChart.setLabel(entityField.getName());
		averagerateChart.setZoom(new Runnable() {
			public void run() {new AverageRatePopup().show();}
		});
		averagerateChart.setTableError(new TableError() {
			@Override
			public boolean error() {
				return (
						ifMessage(organizationField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), organizationField)
						|| ifMessage(fromtodateField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.noTovalue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
						|| ifMessage(fromtodateField.getDuration() > MAX_DATA_POINTS, Level.ERROR, CONSTANTS.durationError(), fromtodateField)
				);
			}
		});
		averagerateChart.setTableExecutor(new TableExecutor<AverageRateChart>() {
			public void execute(AverageRateChart action) {getValue(action);}
		});
		stackPanel.add(averagerateChart, CONSTANTS.averageratechartLabel(), 1.5);

		//-----------------------------------------------
		// Revenue PAR Chart
		//-----------------------------------------------
		revparChart = new LineChartField(this, null, new RevparChart(), null, tab++);
		revparChart.setEmptyValue(revparchartEmpty());
		revparChart.setFieldStyle(CSS.chartStyle());
		revparChart.setLabel(entityField.getName());
		revparChart.setZoom(new Runnable() {
			public void run() {new RevparPopup().show();}
		});
		revparChart.setTableError(new TableError() {
			@Override
			public boolean error() {
				return (
						ifMessage(organizationField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), organizationField)
						|| ifMessage(fromtodateField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.noTovalue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
						|| ifMessage(fromtodateField.getDuration() > MAX_DATA_POINTS, Level.ERROR, CONSTANTS.durationError(), fromtodateField)
				);
			}
		});
		revparChart.setTableExecutor(new TableExecutor<RevparChart>() {
			public void execute(RevparChart action) {getValue(action);}
		});
		stackPanel.add(revparChart, CONSTANTS.revparchartLabel(), 1.5);

		//-----------------------------------------------
		// Occupancy Chart
		//-----------------------------------------------
		occupancyChart = new LineChartField(this, null, new OccupancyChart(), null, tab++);
		occupancyChart.setEmptyValue(occupancychartEmpty());
		occupancyChart.setFieldStyle(CSS.chartStyle());
		occupancyChart.setLabel(entityField.getName());
		occupancyChart.setRange(0.0, 100.0);
		occupancyChart.setZoom(new Runnable() {
			public void run() {new OccupancyPopup().show();}
		});
		occupancyChart.setTableError(new TableError() {
			@Override
			public boolean error() {
				return (
						ifMessage(organizationField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), organizationField)
						|| ifMessage(fromtodateField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.noTovalue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
						|| ifMessage(fromtodateField.getDuration() > MAX_DATA_POINTS, Level.ERROR, CONSTANTS.durationError(), fromtodateField)
				);
			}
		});
		occupancyChart.setTableExecutor(new TableExecutor<OccupancyChart>() {
			public void execute(OccupancyChart action) {getValue(action);}
		});
		stackPanel.add(occupancyChart, CONSTANTS.occupancychartLabel(), 1.5);

		//-----------------------------------------------
		// Confirmed Reservation Value Chart
		//-----------------------------------------------
		confirmedChart = new LineChartField(this, null, new ConfirmedChart(), null, tab++);
		confirmedChart.setEmptyValue(confirmedchartEmpty());
		confirmedChart.setFieldStyle(CSS.chartStyle());
		confirmedChart.setLabel(entityField.getName());
		confirmedChart.setZoom(new Runnable() {
			public void run() {new ConfirmedPopup().show();}
		});
		confirmedChart.setTableError(new TableError() {
			@Override
			public boolean error() {
				return (
						ifMessage(organizationField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), organizationField)
						|| ifMessage(fromtodateField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.noTovalue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
						|| ifMessage(fromtodateField.getDuration() > MAX_DATA_POINTS, Level.ERROR, CONSTANTS.durationError(), fromtodateField)
				);
			}
		});
		confirmedChart.setTableExecutor(new TableExecutor<ConfirmedChart>() {
			public void execute(ConfirmedChart action) {getValue(action);}
		});
		stackPanel.add(confirmedChart, CONSTANTS.confirmedchartLabel(), 1.5);

		//-----------------------------------------------
		// Guest Feedback Chart
		//-----------------------------------------------
		feedbackChart = new LineChartField(this, null, new FeedbackChart(), null, tab++);
		feedbackChart.setEmptyValue(feedbackchartEmpty());
		feedbackChart.setFieldStyle(CSS.chartStyle());
		feedbackChart.setLabel(entityField.getName());
		feedbackChart.setZoom(new Runnable() {
			public void run() {new FeedbackPopup().show();}
		});
		feedbackChart.setTableError(new TableError() {
			@Override
			public boolean error() {
				return (
						ifMessage(organizationField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), organizationField)
						|| ifMessage(fromtodateField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.noTovalue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
						|| ifMessage(fromtodateField.getDuration() > MAX_DATA_POINTS, Level.ERROR, CONSTANTS.durationError(), fromtodateField)
				);
			}
		});
		feedbackChart.setTableExecutor(new TableExecutor<FeedbackChart>() {
			public void execute(FeedbackChart action) {getValue(action);}
		});
		stackPanel.add(feedbackChart, CONSTANTS.feedbackchartLabel(), 1.5);

		//-----------------------------------------------
		// Marketing Triggers Pie Diagram
		//-----------------------------------------------
		HorizontalPanel marketingPanel = new HorizontalPanel();
		starterChart = new PieChartField(this, null, new StarterChart(), null, tab++);
		starterChart.setLabel(CONSTANTS.starterchartLabel());
		starterChart.setPosition(LegendPosition.BOTTOM);
		starterChart.setEmptyValue(starterchartEmpty());
		starterChart.setFieldStyle(CSS.charthalfStyle());
		starterChart.setZoom(new Runnable() {
			public void run() {new MarketPopup().show();}
		});
		starterChart.setTableError(new TableError() {
			@Override
			public boolean error() {
				return (
						ifMessage(organizationField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), organizationField)
						|| ifMessage(fromtodateField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.noTovalue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
						|| ifMessage(fromtodateField.getDuration() > MAX_DATA_POINTS, Level.ERROR, CONSTANTS.durationError(), fromtodateField)
				);
			}
		});
		starterChart.setTableExecutor(new TableExecutor<StarterChart>() {
			public void execute(StarterChart action) {getValue(action);}
		});
		marketingPanel.add(starterChart);

		//-----------------------------------------------
		// Marketing Outcomes Pie Diagram
		//-----------------------------------------------
		outcomeChart = new PieChartField(this, null, new OutcomeChart(), null, tab++);
		outcomeChart.setLabel(CONSTANTS.outcomechartLabel());
		outcomeChart.setPosition(LegendPosition.BOTTOM);
		outcomeChart.setEmptyValue(outcomechartEmpty());
		outcomeChart.setFieldStyle(CSS.charthalfStyle());
		outcomeChart.setZoom(new Runnable() {
			public void run() {new MarketPopup().show();}
		});
		outcomeChart.setTableError(new TableError() {
			@Override
			public boolean error() {
				return (
						ifMessage(organizationField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), organizationField)
						|| ifMessage(fromtodateField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.noTovalue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
						|| ifMessage(fromtodateField.getDuration() > MAX_DATA_POINTS, Level.ERROR, CONSTANTS.durationError(), fromtodateField)
				);
			}
		});
		outcomeChart.setTableExecutor(new TableExecutor<OutcomeChart>() {
			public void execute(OutcomeChart action) {getValue(action);}
		});
		marketingPanel.add(outcomeChart);

		stackPanel.add(marketingPanel, CONSTANTS.marketingchartLabel(), 1.5);

		//-----------------------------------------------
		// Work Flow Status Chart
		//-----------------------------------------------
		workflowChart = new ColumnChartField(this, null, new WorkflowChart(), null, tab++);
		workflowChart.setRange(0.0, 100.0);
		workflowChart.setEmptyValue(workflowchartEmpty());
		workflowChart.setFieldStyle(CSS.chartStyle());
		workflowChart.setZoom(new Runnable() {
			public void run() {new WorkflowPopup().show();}
		});
		workflowChart.setTableError(new TableError() {
			@Override
			public boolean error() {
				return (
						ifMessage(organizationField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), organizationField)
						|| ifMessage(fromtodateField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.noTovalue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
						|| ifMessage(fromtodateField.getDuration() > MAX_DATA_POINTS, Level.ERROR, CONSTANTS.durationError(), fromtodateField)
				);
			}
		});
		workflowChart.setTableExecutor(new TableExecutor<WorkflowChart>() {
			public void execute(WorkflowChart action) {getValue(action);}
		});
		stackPanel.add(workflowChart, CONSTANTS.workflowchartLabel(), 1.5);

		onRefresh();
		onReset(Organization.CREATED);
	}

	/* 
	 * Creates the panel displayed when there are no results for the account summary chart.
	 * 
	 * @return the empty panel.
	 */
	private Widget accountsummarychartEmpty() {
		FlowPanel panel = new FlowPanel();
		panel.add(new HTML(CONSTANTS.accountsummarychartEmpty()));
		panel.addStyleName(AbstractField.CSS.cbtTableFieldEmpty());
		panel.add(new Image(BUNDLE.accountsummarychartEmpty()));
		return panel;
	}

	/* 
	 * Creates the panel displayed when there are no results for the average rate chart.
	 * 
	 * @return the empty panel.
	 */
	private Widget averageratechartEmpty() {
		FlowPanel panel = new FlowPanel();
		panel.add(new HTML(CONSTANTS.averageratechartEmpty()));
		panel.addStyleName(AbstractField.CSS.cbtTableFieldEmpty());
		panel.add(new Image(BUNDLE.averageratechartEmpty()));
		return panel;
	}

	/* 
	 * Creates the panel displayed when there are no results for the confirmed reservation value chart.
	 * 
	 * @return the empty panel.
	 */
	private Widget confirmedchartEmpty() {
		FlowPanel panel = new FlowPanel();
		panel.add(new HTML(CONSTANTS.confirmedchartEmpty()));
		panel.addStyleName(AbstractField.CSS.cbtTableFieldEmpty());
		panel.add(new Image(BUNDLE.confirmedchartEmpty()));
		return panel;
	}

	/* 
	 * Creates the panel displayed when there are no results for the guest feedback chart.
	 * 
	 * @return the empty panel.
	 */
	private Widget feedbackchartEmpty() {
		FlowPanel panel = new FlowPanel();
		panel.add(new HTML(CONSTANTS.feedbackchartEmpty()));
		panel.addStyleName(AbstractField.CSS.cbtTableFieldEmpty());
		panel.add(new Image(BUNDLE.feedbackchartEmpty()));
		return panel;
	}

	/* 
	 * Creates the panel displayed when there are no results for the occupancy chart.
	 * 
	 * @return the empty panel.
	 */
	private Widget occupancychartEmpty() {
		FlowPanel panel = new FlowPanel();
		panel.add(new HTML(CONSTANTS.occupancychartEmpty()));
		panel.addStyleName(AbstractField.CSS.cbtTableFieldEmpty());
		panel.add(new Image(BUNDLE.occupancychartEmpty()));
		return panel;
	}

	/* 
	 * Creates the panel displayed when there are no results for the profit summary chart.
	 * 
	 * @return the empty panel.
	 */
	private Widget profitsummarychartEmpty() {
		FlowPanel panel = new FlowPanel();
		panel.add(new HTML(CONSTANTS.profitsummarychartEmpty()));
		panel.addStyleName(AbstractField.CSS.cbtTableFieldEmpty());
		panel.add(new Image(BUNDLE.profitsummarychartEmpty()));
		return panel;
	}

	/* 
	 * Creates the panel displayed when there are no results for the revenue PAR chart.
	 * 
	 * @return the empty panel.
	 */
	private Widget revparchartEmpty() {
		FlowPanel panel = new FlowPanel();
		panel.add(new HTML(CONSTANTS.revparchartEmpty()));
		panel.addStyleName(AbstractField.CSS.cbtTableFieldEmpty());
		panel.add(new Image(BUNDLE.revparchartEmpty()));
		return panel;
	}

	/* 
	 * Creates the panel displayed when there are no results for the revenue summary chart.
	 * 
	 * @return the empty panel.
	 */
	private Widget revenuesummarychartEmpty() {
		FlowPanel panel = new FlowPanel();
		panel.add(new HTML(CONSTANTS.revenuesummarychartEmpty()));
		panel.addStyleName(AbstractField.CSS.cbtTableFieldEmpty());
		panel.add(new Image(BUNDLE.revenuesummarychartEmpty()));
		return panel;
	}

	/* 
	 * Creates the panel displayed when there are no results for the marketing triggers chart.
	 * 
	 * @return the empty panel.
	 */
	private Widget starterchartEmpty() {
		FlowPanel panel = new FlowPanel();
		panel.add(new HTML(CONSTANTS.starterchartEmpty()));
		panel.addStyleName(AbstractField.CSS.cbtTableFieldEmpty());
		panel.add(new Image(BUNDLE.starterchartEmpty()));
		return panel;
	}

	/* 
	 * Creates the panel displayed when there are no results for the marketing outcomes chart.
	 * 
	 * @return the empty panel.
	 */
	private Widget outcomechartEmpty() {
		FlowPanel panel = new FlowPanel();
		panel.add(new HTML(CONSTANTS.outcomechartEmpty()));
		panel.addStyleName(AbstractField.CSS.cbtTableFieldEmpty());
		panel.add(new Image(BUNDLE.outcomechartEmpty()));
		return panel;
	}

	/* 
	 * Creates the panel displayed when there are no results for the work flow status chart.
	 * 
	 * @return the empty panel.
	 */
	private Widget workflowchartEmpty() {
		FlowPanel panel = new FlowPanel();
		panel.add(new HTML(CONSTANTS.workflowchartEmpty()));
		panel.addStyleName(AbstractField.CSS.cbtTableFieldEmpty());
		panel.add(new Image(BUNDLE.workflowchartEmpty()));
		return panel;
	}

	/* 
	 * Creates the control panel.
	 * 
	 * @return the control panel.
	 */
	private VerticalPanel createControl() {
//		final FlowPanel panel = new FlowPanel();
		final VerticalPanel form =  new VerticalPanel();
		form.addStyleName(AbstractField.CSS.cbtAbstractControl());
//		panel.add(form);
		final Label title = new Label(CONSTANTS.designTitle());
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);

		//-----------------------------------------------
		// Organization field
		//-----------------------------------------------
		organizationField = new NameIdField(this, null,
				CONSTANTS.organizationLabel(),
				tab++);
		organizationField.setEnabled(false);
		organizationField.setHelpText(CONSTANTS.organizationHelp());
		form.add(organizationField);

		//-----------------------------------------------
		// Process field
		//-----------------------------------------------
		processField = new ListField(this,  null,
				NameId.getList(AbstractField.CONSTANTS.processList(), Event.PROCESSES),
				CONSTANTS.processLabel(),
				CONSTANTS.processEmpty(),
				tab++);
		processField.setHelpText(CONSTANTS.processHelp());
		form.add(processField);

		//-----------------------------------------------
		// Account field
		//-----------------------------------------------
		accountField = new SuggestField(this, null,
				new NameIdAction(Service.ACCOUNT),
				CONSTANTS.accountLabel(),
				20,
				tab++);
		accountField.setDoubleclickable(false);
		accountField.setDefaultName(CONSTANTS.accountEmpty());
		accountField.setHelpText(CONSTANTS.accountHelp());

		final Image accountButton = new Image(AbstractField.BUNDLE.plus());
		accountButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				AccountPopup.getInstance().show(Account.Type.Expense, accountField);
			}
		});
		accountButton.setTitle(CONSTANTS.accountbuttonHelp());
		accountField.addButton(accountButton);
		form.add(accountField);

		//-----------------------------------------------
		// Entity (subaccount) field
		//-----------------------------------------------
		entityField = new SuggestField(this, null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.partyLabel(),
				20,
				tab++);
		entityField.setDefaultName(CONSTANTS.entityEmpty());
		entityField.setHelpText(CONSTANTS.entityHelp());
		form.add(entityField);

		//-----------------------------------------------
		// Currency field
		//-----------------------------------------------
		currencyField = new ListField(this, null,
				new JournalCurrency(),
				CONSTANTS.currencyLabel(),
				false,
				tab++);
		currencyField.setDefaultValue(AbstractRoot.getCurrency());
		currencyField.setHelpText(CONSTANTS.currencyHelp());
		form.add(currencyField);

		//-----------------------------------------------
		// From and To Dates
		//-----------------------------------------------
		fromtodateField = new DatespanField(this, null,
				CONSTANTS.fromtodateLabel(),
				tab++);
		fromtodateField.setUnits(NameId.getList(CONSTANTS.periods(), PERIODS));
		fromtodateField.setDefaultUnit(Time.MONTH.name());
		fromtodateField.setFieldStyle(CSS.fromtodateField());
		fromtodateField.setHelpText(CONSTANTS.fromtodateHelp());
		form.add(fromtodateField);

		//-----------------------------------------------
		// States field
		//-----------------------------------------------
		statesField = new ShuttleField(this, null,
				new NameIdState(Service.WORKFLOW),
				CONSTANTS.statesLabel(),
				tab++);
		statesField.setVisible(false);
		//statesField.setPreselected(true);
		statesField.setFieldStyle(CSS.statesStyle());
		statesField.setHelpText(CONSTANTS.statesHelp());
		form.add(statesField);

		//-----------------------------------------------
		// Types field
		//-----------------------------------------------
		typesField = new ShuttleField(this, null,
				new NameIdType(Service.ACCOUNT),
				CONSTANTS.typesLabel(),
				tab++);
		//typesField.setVisible(false);
		//typesField.setPreselected(true);
		typesField.setFieldStyle(CSS.typesStyle());
		typesField.setHelpText(CONSTANTS.typesHelp());
		form.add(typesField);

		//-----------------------------------------------
		// Design button is to create a report 
		//-----------------------------------------------
		final Button designButton = new Button(CONSTANTS.designButton(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				DesignPopup.getInstance().show(null);
			}
		});
		designButton.addStyleName(CSS.reportButton());
		form.add(designButton);

		return form;
	}

	/* 
	 * Creates the panel of financial commands.
	 * 
	 * @return the panel of financial commands.
	 */
	private HorizontalPanel createFinancialcommands() {

		final HorizontalPanel bar = new HorizontalPanel();
		bar.addStyleName(CSS.financialcommandBar());

		//-----------------------------------------------
		// Sale button
		//-----------------------------------------------
		final Button saleButton = new Button(CONSTANTS.saleButton());
		saleButton.setTitle(CONSTANTS.saleHelp());
		saleButton.addStyleName(AbstractField.CSS.cbtCommandButton());
		saleButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				JournalPopup.getInstance().show(Event.Type.Sale, null, accountjournalTable);
			}
		});
		bar.add(saleButton);

		//-----------------------------------------------
		// Receipt button
		//-----------------------------------------------
		final Button receiptButton = new Button(CONSTANTS.receiptButton());
		receiptButton.setTitle(CONSTANTS.receiptHelp());
		receiptButton.addStyleName(AbstractField.CSS.cbtCommandButton());
		receiptButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		receiptButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				JournalPopup.getInstance().show(Event.Type.Receipt, null, accountjournalTable);
			}
		});
		bar.add(receiptButton);

		//-----------------------------------------------
		// Purchase button
		//-----------------------------------------------
		final Button purchaseButton = new Button(CONSTANTS.purchaseButton());
		purchaseButton.setTitle(CONSTANTS.purchaseHelp());
		purchaseButton.addStyleName(AbstractField.CSS.cbtCommandButton());
		purchaseButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		purchaseButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				JournalPopup.getInstance().show(Event.Type.Purchase, null, accountjournalTable);
			}
		});
		bar.add(purchaseButton);

		//-----------------------------------------------
		// Purchase For button
		//-----------------------------------------------
		final Button purchasesaleButton = new Button(CONSTANTS.purchasesaleButton());
		purchasesaleButton.setTitle(CONSTANTS.purchasesaleHelp());
		purchasesaleButton.addStyleName(AbstractField.CSS.cbtCommandButton());
		purchasesaleButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		purchasesaleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				JournalPopup.getInstance().show(Event.Type.PurchaseSale, null, accountjournalTable);
			}
		});
		bar.add(purchasesaleButton);

		//-----------------------------------------------
		// Payment button
		//-----------------------------------------------
		final Button paymentButton = new Button(CONSTANTS.paymentButton());
		paymentButton.setTitle(CONSTANTS.paymentHelp());
		paymentButton.addStyleName(AbstractField.CSS.cbtCommandButton());
		paymentButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		paymentButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				JournalPopup.getInstance().show(Event.Type.Payment, null, accountjournalTable);
			}
		});
		bar.add(paymentButton);

		//-----------------------------------------------
		// Journal button
		//-----------------------------------------------
		final Button journalButton = new Button(CONSTANTS.journalButton());
		journalButton.setTitle(CONSTANTS.journalHelp());
		journalButton.addStyleName(AbstractField.CSS.cbtCommandButton());
		journalButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		journalButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				JournalPopup.getInstance().show(Event.Type.Journal, null, accountjournalTable);
			}
		});
		bar.add(journalButton);

		//-----------------------------------------------
		// Balance field
		//-----------------------------------------------
		balanceField = new DoubleField(this, null,
				CONSTANTS.balanceLabel(),
				AbstractField.AF,
				tab++);
		balanceField.setEnabled(false);
		balanceField.setFieldStyle(CSS.balanceField());

		bar.add(balanceField);
		return bar;
	}

	/* 
	 * Creates the panel displayed when there are no account journals.
	 * 
	 * @return the empty panel.
	 */
	private Widget accountjournaltableEmpty() {
		final FlowPanel panel = new FlowPanel();
		panel.add(new HTML(CONSTANTS.accountjournaltableEmpty()));
		panel.addStyleName(AbstractField.CSS.cbtTableFieldEmpty());
		panel.add(new Image(BUNDLE.accountjournaltableEmpty()));
		return panel;
	}

	/* 
	 * Creates the account journal table stack panel.
	 * 
	 * @return the account journal table stack panel.
	 */
	private FlowPanel createFinancialpanel() {
		financialPanel = new FlowPanel();
		financialPanel.add(createFinancialcommands());

		final FlowPanel scroll = new FlowPanel();
		financialPanel.add(scroll);
		//-----------------------------------------------
		// Account Journal selection handler
		//-----------------------------------------------
		final NoSelectionModel<EventJournal> selectionModel = new NoSelectionModel<EventJournal>();
		SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				JournalPopup.getInstance().show(selectionModel.getLastSelectedObject(), null, accountjournalTable);
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// Account Journal table
		//-----------------------------------------------
		accountjournalTable = new TableField<EventJournal>(this, null, 
				new AccountActionTable(),
				selectionModel, 
				ACCOUNTJOURNAL_ROWS,
				tab++);

		accountjournalTable.setEmptyValue(accountjournaltableEmpty());
		accountjournalTable.setOrderby(Event.DATE);
		accountjournalTable.setTableError(new TableError() {
			@Override
			public boolean error() {return (
					organizationField.noValue()
					|| fromtodateField.noValue()
					|| fromtodateField.noUnit()
			);}
		});

		accountjournalTable.setTableExecutor(new TableExecutor<AccountActionTable>() {
			public void execute(AccountActionTable action) {
				getValue(action);
			}
		});

		int col = 0;

		//-----------------------------------------------
		// Create / Change buttons
		//-----------------------------------------------
		Column<EventJournal, EventJournal> changeButton = new Column<EventJournal, EventJournal>( 
				new ActionCell<EventJournal>(AbstractField.CONSTANTS.allChange(), AbstractField.CSS.cbtTableFieldChangeButton(),
						new Delegate<EventJournal>(){
					public void execute( EventJournal eventjournal ){
						JournalPopup.getInstance().show(eventjournal, null, accountjournalTable);
					}
				}
				)
		)
		{
			public EventJournal getValue(EventJournal eventjournal){return eventjournal;}//TODO: selectForm(row); 
		};
		accountjournalTable.addColumn(changeButton);

		//-----------------------------------------------
		// Date column
		//-----------------------------------------------
		Column<EventJournal, Date> date = new Column<EventJournal, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( EventJournal eventjournal ) {return Time.getDateClient(eventjournal.getDate());}
		};
		accountjournalTable.addDateColumn(date, CONSTANTS.accountactionHeaders()[col++], EventJournal.DATE);

		//-----------------------------------------------
		// Process column
		//-----------------------------------------------
		Column<EventJournal, String> process = new Column<EventJournal, String>(new TextCell()) {
			@Override
			public String getValue( EventJournal eventjournal ) {return eventjournal.getProcess();}
		};
		accountjournalTable.addStringColumn(process, CONSTANTS.accountactionHeaders()[col++], EventJournal.PROCESS);

		//-----------------------------------------------
		// Name column
		//-----------------------------------------------
		Column<EventJournal, String> name = new Column<EventJournal, String>( new TextCell() ) {
			@Override
			public String getValue( EventJournal eventjournal ) {return eventjournal.getName();}
		};
		accountjournalTable.addStringColumn(name, CONSTANTS.accountactionHeaders()[col++], EventJournal.NAME);

		//-----------------------------------------------
		// Account column
		//-----------------------------------------------
		Column<EventJournal, String> accountname = new Column<EventJournal, String>( new TextCell() ) {
			@Override
			public String getValue( EventJournal eventjournal ) {return eventjournal.getAccountname();}
		};
		accountjournalTable.addStringColumn(accountname, CONSTANTS.accountactionHeaders()[col++], EventJournal.ACCOUNTNAME);

		//-----------------------------------------------
		// Entity column
		//-----------------------------------------------
		Column<EventJournal, String> entityname = new Column<EventJournal, String>( new TextCell() ) {
			@Override
			public String getValue( EventJournal eventjournal ) {return eventjournal.getEntityname(15);}
		};
		accountjournalTable.addStringColumn(entityname, CONSTANTS.accountactionHeaders()[col++], EventJournal.ENTITYNAME);

		//-----------------------------------------------
		// Debit column
		//-----------------------------------------------
		Column<EventJournal, Number> debitAmount = new Column<EventJournal, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( EventJournal eventjournal ) {return eventjournal.getDebitamount();}
		};
		accountjournalTable.addNumberColumn( debitAmount, CONSTANTS.accountactionHeaders()[col++], EventJournal.DEBITAMOUNT);

		//-----------------------------------------------
		// Credit column
		//-----------------------------------------------
		Column<EventJournal, Number> creditAmount = new Column<EventJournal, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( EventJournal eventjournal ) {return eventjournal.getCreditamount();}
		};
		accountjournalTable.addNumberColumn( creditAmount, CONSTANTS.accountactionHeaders()[col++], EventJournal.CREDITAMOUNT);

		//-----------------------------------------------
		// Currency column
		//-----------------------------------------------
		Column<EventJournal, String> currency = new Column<EventJournal, String>( new TextCell() ) {
			@Override
			public String getValue( EventJournal eventjournal ) {return eventjournal.getCurrency();}
		};
		accountjournalTable.addStringColumn(currency, CONSTANTS.accountactionHeaders()[col++]);

		scroll.add(accountjournalTable);
		return financialPanel;
	}

	/* 
	 * Creates the panel displayed when there are no account summaries.
	 * 
	 * @return the empty panel.
	 */
	private Widget accountsummarytableEmpty() {
		FlowPanel panel = new FlowPanel();
		panel.add(new HTML(CONSTANTS.accountsummarytableEmpty()));
		panel.addStyleName(AbstractField.CSS.cbtTableFieldEmpty());
		panel.add(new Image(BUNDLE.accountsummarytableEmpty()));
		return panel;
	}

	/* 
	 * Creates the account summary table stack panel.
	 * 
	 * @return the account summary table stack panel.
	 */
	private TableField<AccountSummary> createSummaryactiontable() {
		//-----------------------------------------------
		// Account Summary selection handler
		//-----------------------------------------------
		final NoSelectionModel<AccountSummary> selectionModel = new NoSelectionModel<AccountSummary>();
		SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				AccountSummary summary = selectionModel.getLastSelectedObject();
				accountField.setValue(summary.getAccountid());
				setEntityAction();
				entityField.setValue(summary.getEntityid());
				processField.setValue(summary.getProcess());
				fromtodateField.setUnit(summary.getGroupby());
				accountjournalTable.execute();
				accountBalance.execute();
				stackPanel.showWidget(0);
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// Account Summary table
		//-----------------------------------------------
		accountsummaryTable = new TableField<AccountSummary>(this, null, 
				new AccountSummaryTable(),
				selectionModel, 
				ACCOUNTSUMMARY_ROWS, 
				tab++);

		accountsummaryTable.setEmptyValue(accountsummarytableEmpty());
		accountsummaryTable.setOrderby(AccountSummary.DATE);
		accountsummaryTable.setStyleName(CSS.summaryStyle());

		accountsummaryTable.setTableError(new TableError() {
			public boolean error() {
				return (
					ifMessage(organizationField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), organizationField)
					|| ifMessage(fromtodateField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
					|| ifMessage(fromtodateField.noTovalue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
					|| ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
					|| ifMessage(fromtodateField.getDuration() > MAX_DATA_POINTS, Level.ERROR, CONSTANTS.durationError(), fromtodateField)
				);
			}
		});

		accountsummaryTable.setTableExecutor(new TableExecutor<AccountSummaryTable>() {
			public void execute(AccountSummaryTable action) {
				getValue(action);
			}
		});

		int col = 0;

		//-----------------------------------------------
		// Date column
		//-----------------------------------------------
		Column<AccountSummary, Date> date = new Column<AccountSummary, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( AccountSummary accountsummary ) {return Time.getDateClient(accountsummary.getDate());}
		};
		accountsummaryTable.addDateColumn(date, CONSTANTS.accountsummaryHeaders()[col++], AccountSummary.DATE);

		//-----------------------------------------------
		// Process column
		//-----------------------------------------------
		Column<AccountSummary, String> process = new Column<AccountSummary, String>(new TextCell()) {
			@Override
			public String getValue( AccountSummary accountsummary ) {return accountsummary.getProcess();}
		};
		accountsummaryTable.addStringColumn(process, CONSTANTS.accountsummaryHeaders()[col++], AccountSummary.PROCESS);

		//-----------------------------------------------
		// Account column
		//-----------------------------------------------
		Column<AccountSummary, String> accountname = new Column<AccountSummary, String>(new TextCell()) {
			@Override
			public String getValue( AccountSummary accountsummary ) {return accountsummary.getAccountname();}
		};
		accountsummaryTable.addStringColumn(accountname, CONSTANTS.accountsummaryHeaders()[col++], AccountSummary.ACCOUNTNAME);

		//-----------------------------------------------
		// Entity column
		//-----------------------------------------------
		Column<AccountSummary, String> entityname = new Column<AccountSummary, String>(new TextCell()) {
			@Override
			public String getValue( AccountSummary accountsummary ) {return accountsummary.getEntityname(18);}
		};
		accountsummaryTable.addStringColumn(entityname, CONSTANTS.accountsummaryHeaders()[col++], AccountSummary.ENTITYNAME);

		//-----------------------------------------------
		// Debit column
		//-----------------------------------------------
		Column<AccountSummary, Number> debitAmount = new Column<AccountSummary, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( AccountSummary accountsummary ) {return accountsummary.getDebitamount();}
		};
		accountsummaryTable.addNumberColumn( debitAmount, CONSTANTS.accountsummaryHeaders()[col++], AccountSummary.DEBITAMOUNT);

		//-----------------------------------------------
		// Credit column
		//-----------------------------------------------
		Column<AccountSummary, Number> creditAmount = new Column<AccountSummary, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( AccountSummary accountsummary ) {return accountsummary.getCreditamount();}
		};
		accountsummaryTable.addNumberColumn( creditAmount, CONSTANTS.accountsummaryHeaders()[col++], AccountSummary.CREDITAMOUNT);

		//-----------------------------------------------
		// Amount column
		//-----------------------------------------------
		Column<AccountSummary, Number> amount = new Column<AccountSummary, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( AccountSummary accountsummary ) {return accountsummary.getAmount();}
		};
		accountsummaryTable.addNumberColumn(amount, CONSTANTS.accountsummaryHeaders()[col++]);

		//-----------------------------------------------
		// Count column
		//-----------------------------------------------
		Column<AccountSummary, Number> count = new Column<AccountSummary, Number>(new NumberCell(AbstractField.IF)) {
			@Override
			public Integer getValue( AccountSummary accountsummary ) {return accountsummary.getCount();}
		};
		accountsummaryTable.addNumberColumn( count, CONSTANTS.accountsummaryHeaders()[col++]);

		return accountsummaryTable;
	}
	
	/* Inner Class ChartPopup to display chart detail panels. */
	private static class ChartPopup extends PopupPanel {
		private final FlowPanel panel = new FlowPanel();

		public ChartPopup(Widget content) {
			super(false);
			setWidget(panel);
			setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());

			final Label closeButton = new Label();
			closeButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ChartPopup.this.hide();
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

	/* Inner Class WorkflowPopup to display the detail work flow charts. */
	private class WorkflowPopup extends ChartPopup {
		public WorkflowPopup() {
			super(null);
			final ColumnChartField chart = new ColumnChartField(null, null, new WorkflowChart(), null, tab++);
			chart.setRange(0.0, 100.0);
			//chart.setEmptyValue(workflowchartEmpty());
			chart.setFieldStyle(CSS.chartzoomStyle());
			chart.setTableError(new TableError() {
				@Override
				public boolean error() {
					return (
							ifMessage(organizationField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), organizationField)
							|| ifMessage(fromtodateField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
							|| ifMessage(fromtodateField.noTovalue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
							|| ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
							|| ifMessage(fromtodateField.getDuration() > MAX_DATA_POINTS, Level.ERROR, CONSTANTS.durationError(), fromtodateField)
					);
				}
			});
			chart.setTableExecutor(new TableExecutor<WorkflowChart>() {
				public void execute(WorkflowChart action) {getValue(action);}
			});
			this.add(chart);
			chart.execute();
		}
	}

	/* Inner Class MarketPopup to display the detail marketing charts. */
	private class MarketPopup extends ChartPopup {
		public MarketPopup() {
			super(null);
			final PieChartField starter = new PieChartField(null, null, new StarterChart(), null, tab++);
			starter.setLabel(CONSTANTS.starterchartLabel());
			starter.setPosition(LegendPosition.BOTTOM);
			//chart.setEmptyValue(starterchartEmpty());
			starter.setFieldStyle(CSS.chartzoomhalfStyle());
			starter.setTableError(new TableError() {
				@Override
				public boolean error() {
					return (
							ifMessage(organizationField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), organizationField)
							|| ifMessage(fromtodateField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
							|| ifMessage(fromtodateField.noTovalue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
							|| ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
							|| ifMessage(fromtodateField.getDuration() > MAX_DATA_POINTS, Level.ERROR, CONSTANTS.durationError(), fromtodateField)
					);
				}
			});
			starter.setTableExecutor(new TableExecutor<StarterChart>() {
				public void execute(StarterChart action) {getValue(action);}
			});
			
			final PieChartField outcome = new PieChartField(null, null, new OutcomeChart(), null, tab++);
			outcome.setLabel(CONSTANTS.outcomechartLabel());
			outcome.setPosition(LegendPosition.BOTTOM);
			//chart.setEmptyValue(starterchartEmpty());
			outcome.setFieldStyle(CSS.chartzoomhalfStyle());
			outcome.setTableError(new TableError() {
				@Override
				public boolean error() {
					return (
							ifMessage(organizationField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), organizationField)
							|| ifMessage(fromtodateField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
							|| ifMessage(fromtodateField.noTovalue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
							|| ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
							|| ifMessage(fromtodateField.getDuration() > MAX_DATA_POINTS, Level.ERROR, CONSTANTS.durationError(), fromtodateField)
					);
				}
			});
			outcome.setTableExecutor(new TableExecutor<OutcomeChart>() {
				public void execute(OutcomeChart action) {getValue(action);}
			});
			
			final HorizontalPanel so = new HorizontalPanel();
			so.add(starter);
			so.add(outcome);
			this.add(so);
			
			starter.execute();
			outcome.execute();
		}
	}
	
	/* Inner Class ConfirmedPopup to display the detail confirmed reservation charts. */
	private class ConfirmedPopup extends ChartPopup {
		public ConfirmedPopup() {
			super(null);
			final LineChartField chart = new LineChartField(null, null, new ConfirmedChart(), null, tab++);
			//chart.setEmptyValue(confirmedchartEmpty());
			chart.setFieldStyle(CSS.chartzoomStyle());
			chart.setLabel(entityField.getName());
			chart.setTableError(new TableError() {
				@Override
				public boolean error() {
					return (
							ifMessage(organizationField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), organizationField)
							|| ifMessage(fromtodateField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
							|| ifMessage(fromtodateField.noTovalue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
							|| ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
							|| ifMessage(fromtodateField.getDuration() > MAX_DATA_POINTS, Level.ERROR, CONSTANTS.durationError(), fromtodateField)
					);
				}
			});
			chart.setTableExecutor(new TableExecutor<ConfirmedChart>() {
				public void execute(ConfirmedChart action) {getValue(action);}
			});
			this.add(chart);
			chart.execute();
		}
	}

	/* Inner Class FeedbackPopup to display the detail guest feedback charts. */
	private class FeedbackPopup extends ChartPopup {
		public FeedbackPopup() {
			super(null);
			final LineChartField chart = new LineChartField(null, null, new FeedbackChart(), null, tab++);
			//chart.setEmptyValue(feedbackchartEmpty());
			chart.setFieldStyle(CSS.chartzoomStyle());
			chart.setLabel(entityField.getName());
			chart.setTableError(new TableError() {
				@Override
				public boolean error() {
					return (
							ifMessage(organizationField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), organizationField)
							|| ifMessage(fromtodateField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
							|| ifMessage(fromtodateField.noTovalue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
							|| ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
							|| ifMessage(fromtodateField.getDuration() > MAX_DATA_POINTS, Level.ERROR, CONSTANTS.durationError(), fromtodateField)
					);
				}
			});
			chart.setTableExecutor(new TableExecutor<FeedbackChart>() {
				public void execute(FeedbackChart action) {getValue(action);}
			});
			this.add(chart);
			chart.execute();
		}
	}

	/* Inner Class OccupancyPopup to display the detail occupancy charts. */
	private class OccupancyPopup extends ChartPopup {
		public OccupancyPopup() {
			super(null);
			final LineChartField chart = new LineChartField(null, null, new OccupancyChart(), null, tab++);
			//chart.setEmptyValue(occupancychartEmpty());
			chart.setFieldStyle(CSS.chartzoomStyle());
			chart.setLabel(entityField.getName());
			chart.setTableError(new TableError() {
				@Override
				public boolean error() {
					return (
							ifMessage(organizationField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), organizationField)
							|| ifMessage(fromtodateField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
							|| ifMessage(fromtodateField.noTovalue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
							|| ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
							|| ifMessage(fromtodateField.getDuration() > MAX_DATA_POINTS, Level.ERROR, CONSTANTS.durationError(), fromtodateField)
					);
				}
			});
			chart.setTableExecutor(new TableExecutor<OccupancyChart>() {
				public void execute(OccupancyChart action) {getValue(action);}
			});
			this.add(chart);
			chart.execute();
		}
	}

	/* Inner Class RevparPopup to display the detail revenue per available room charts. */
	private class RevparPopup extends ChartPopup {
		public RevparPopup() {
			super(null);
			final LineChartField chart = new LineChartField(null, null, new RevparChart(), null, tab++);
			//chart.setEmptyValue(revparchartEmpty());
			chart.setFieldStyle(CSS.chartzoomStyle());
			chart.setLabel(entityField.getName());
			chart.setTableError(new TableError() {
				@Override
				public boolean error() {
					return (
							ifMessage(organizationField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), organizationField)
							|| ifMessage(fromtodateField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
							|| ifMessage(fromtodateField.noTovalue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
							|| ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
							|| ifMessage(fromtodateField.getDuration() > MAX_DATA_POINTS, Level.ERROR, CONSTANTS.durationError(), fromtodateField)
					);
				}
			});
			chart.setTableExecutor(new TableExecutor<RevparChart>() {
				public void execute(RevparChart action) {getValue(action);}
			});
			this.add(chart);
			chart.execute();
		}
	}

	/* Inner Class AverageRatePopup to display the detail average daily rate charts. */
	private class AverageRatePopup extends ChartPopup {
		public AverageRatePopup() {
			super(null);
			final LineChartField chart = new LineChartField(null, null, new AverageRateChart(), null, tab++);
			//chart.setEmptyValue(averageratechartEmpty());
			chart.setFieldStyle(CSS.chartzoomStyle());
			chart.setLabel(entityField.getName());
			chart.setTableError(new TableError() {
				@Override
				public boolean error() {
					return (
							ifMessage(organizationField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), organizationField)
							|| ifMessage(fromtodateField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
							|| ifMessage(fromtodateField.noTovalue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
							|| ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
							|| ifMessage(fromtodateField.getDuration() > MAX_DATA_POINTS, Level.ERROR, CONSTANTS.durationError(), fromtodateField)
					);
				}
			});
			chart.setTableExecutor(new TableExecutor<AverageRateChart>() {
				public void execute(AverageRateChart action) {getValue(action);}
			});
			this.add(chart);
			chart.execute();
		}
	}

	/* Inner Class ProfitPopup to display the detail profit charts. */
	private class ProfitPopup extends ChartPopup {
		public ProfitPopup() {
			super(null);
			final LineChartField chart = new LineChartField(null, null, new ProfitChart(), null, tab++);
			//chart.setEmptyValue(profitsummarychartEmpty());
			chart.setFieldStyle(CSS.chartzoomStyle());
			chart.setLabel(entityField.getName());
			chart.setTableError(new TableError() {
				@Override
				public boolean error() {
					return (
							ifMessage(organizationField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), organizationField)
							|| ifMessage(fromtodateField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
							|| ifMessage(fromtodateField.noTovalue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
							|| ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
							|| ifMessage(fromtodateField.getDuration() > MAX_DATA_POINTS, Level.ERROR, CONSTANTS.durationError(), fromtodateField)
					);
				}
			});
			chart.setTableExecutor(new TableExecutor<ProfitChart>() {
				public void execute(ProfitChart action) {getValue(action);}
			});
			this.add(chart);
			chart.execute();
		}
	}

	/* Inner Class RevenuePopup to display the detail revenue charts. */
	private class RevenuePopup extends ChartPopup {
		public RevenuePopup() {
			super(null);
			final LineChartField chart = new LineChartField(null, null, new RevenueChart(), null, tab++);
			//chart.setEmptyValue(revenuesummarychartEmpty());
			chart.setFieldStyle(CSS.chartzoomStyle());
			chart.setLabel(entityField.getName());
			chart.setTableError(new TableError() {
				@Override
				public boolean error() {
					return (
							ifMessage(organizationField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), organizationField)
							|| ifMessage(fromtodateField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
							|| ifMessage(fromtodateField.noTovalue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
							|| ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
							|| ifMessage(fromtodateField.getDuration() > MAX_DATA_POINTS, Level.ERROR, CONSTANTS.durationError(), fromtodateField)
					);
				}
			});
			chart.setTableExecutor(new TableExecutor<RevenueChart>() {
				public void execute(RevenueChart action) {getValue(action);}
			});
			this.add(chart);
			chart.execute();
		}
	}
	
	/* Inner Class AccountSummaryPopup to display the detail account charts. */
	private class AccountSummaryPopup extends ChartPopup {
		public AccountSummaryPopup() {
			super(null);
			final LineChartField chart = new LineChartField(null, null, new AccountChart(), null, tab++);
			//chart.setEmptyValue(accountsummarychartEmpty());
			chart.setFieldStyle(CSS.chartzoomStyle());
			chart.setTableError(new TableError() {
				@Override
				public boolean error() {
					return (
							ifMessage(organizationField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), organizationField)
							|| ifMessage(fromtodateField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
							|| ifMessage(fromtodateField.noTovalue(), Level.ERROR, AbstractField.CONSTANTS.errDate(), fromtodateField)
							|| ifMessage(fromtodateField.isEndBeforeStart(), Level.ERROR, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
							|| ifMessage(fromtodateField.getDuration() > MAX_DATA_POINTS, Level.ERROR, CONSTANTS.durationError(), fromtodateField)
					);
				}
			});
			chart.setTableExecutor(new TableExecutor<AccountChart>() {
				public void execute(AccountChart action) {getValue(action);}
			});
			this.add(chart);
			chart.execute();
		}
	}

}
