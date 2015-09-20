/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.Razor;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.ProgressField;
import net.cbtltd.client.field.SelectorField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.table.StyledDateCell;
import net.cbtltd.client.field.table.TableError;
import net.cbtltd.client.field.table.TableExecutor;
import net.cbtltd.client.resource.workflow.WorkflowBundle;
import net.cbtltd.client.resource.workflow.WorkflowConstants;
import net.cbtltd.client.resource.workflow.WorkflowCssResource;
import net.cbtltd.shared.DoubleResponse;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Process;
import net.cbtltd.shared.Reservation;
import net.cbtltd.shared.Task;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Workflow;
import net.cbtltd.shared.party.WorkflowNameId;
import net.cbtltd.shared.workflow.WorkflowProgress;
import net.cbtltd.shared.workflow.WorkflowTable;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;

/** The Class WorkflowForm is to display work items for the selected user(s) and criteria. */
public class WorkflowForm
extends AbstractForm<WorkflowTable> {

	private static final WorkflowConstants CONSTANTS = GWT.create(WorkflowConstants.class);
	private static final WorkflowBundle BUNDLE = WorkflowBundle.INSTANCE;
	private static final WorkflowCssResource CSS = BUNDLE.css();

	private static final int ROWS = 31;

	private static ListField actorField;
	private static SelectorField selectorField;
	private static ProgressField progressField;
	private static TableField<Process> workflowTable;
	private static GuardedRequest<DoubleResponse> workflowProgress;

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#permission()
	 */
	@Override
	public short[] permission(){return AccessControl.RESERVATION_PERMISSION;}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#getValue()
	 */
	@Override
	public WorkflowTable getValue() {return getValue(new WorkflowTable());}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#hasChanged()
	 */
	@Override
	public boolean hasChanged() {return false;}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {
		if (actorField.sent(change)	|| selectorField.sent(change)) {
			workflowProgress.execute();
			workflowTable.execute();
		}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onClick(com.google.gwt.event.dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent click) {}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onFocus()
	 */
	@Override
	public void onFocus() {
//		actorField.onRefresh();
//		actorField.setValue(AbstractRoot.getActorid());
		workflowProgress.execute(true);
		workflowTable.execute(true);
	}

	/**
	 * Executed when window is resized
	 */
//	protected void onResizeForm() {
//		workflowTable.setPageSize(getPageRows());
//		//SCHEDULE_COLS = getPageCols();
//	}

//	private static int getPageRows() {
//		int pageheight = Window.getClientHeight() - 180;
//		int pageSize = pageheight/22;
//		if (pageSize > 33) {pageSize = pageheight/23;}
//		if (pageSize > 60) {pageSize = pageheight/24;}
//		if (pageSize > 80) {pageSize = pageheight/29;}
//		return pageSize < 10 ? 10 : pageSize;
//	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#initialize()
	 */
	@Override
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

		content.add(createControl());
		content.add(getWorkitemTable());
		
		onRefresh();
		onReset(Workflow.CREATED);
		selectorField.select();
	}
	
	/*
	 * Gets the specified work flow table action with its attribute values set.
	 *
	 * @param party the specified work flow table action.
	 * @return the work flow table action with its attribute values set.
	 */
	private WorkflowTable getValue(WorkflowTable action) {
		action.setId(AbstractRoot.getOrganizationid());
		action.setEnddate(Time.addDuration(new Date(), 3.0, Time.DAY));
		action.setActorid(actorField.noValue() ? null : actorField.getValue());
		action.setSelection(selectorField.getValue());
		Log.debug("getValue " + action);
		return action;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#setValue(net.cbtltd.shared.api.HasState)
	 */
	public void setValue(WorkflowTable action) {
		Log.debug("setValue " + action);
		setResetting(true);
//		actorField.setValue(action.getActorid());
//		selectorField.setValue(action.getSelection());
		setResetting(false);
	}

	/*
	 * Gets the specified work flow progress action with its attribute values set.
	 *
	 * @param party the specified work flow progress action.
	 * @return the work flow progress action with its attribute values set.
	 */
	private Workflow getValue(WorkflowProgress action) {
		action.setId(AbstractRoot.getOrganizationid());
		action.setActorid(actorField.noValue() ? null : actorField.getValue());
		action.setStates(selectorField.getValue());
		return action;
	}

	/* 
	 * Creates the control panel.
	 * 
	 * @return the control panel.
	 */
	private VerticalPanel createControl() {

		VerticalPanel panel = new VerticalPanel();
		VerticalPanel form = new VerticalPanel();
		panel.add(form);
		form.addStyleName(AbstractField.CSS.cbtAbstractControl());
		final Label title = new Label(CONSTANTS.titleLabel());
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);

		//-----------------------------------------------
		// Actors list
		//-----------------------------------------------
		actorField = new ListField(this, null,
				new WorkflowNameId(),
				CONSTANTS.actorLabel(),
				CONSTANTS.actorAll(),
				tab++);
		actorField.setHelpText(CONSTANTS.actorHelp());
		form.add(actorField);

		//-----------------------------------------------
		// Task progress bar
		//-----------------------------------------------
		progressField = new ProgressField(this, null, CONSTANTS.progressLabel(), 0, 100, tab);
		progressField.addStyleName(AbstractField.CSS.cbtCommandProgress());
		progressField.setHelpText(CONSTANTS.progressHelp());
		form.add(progressField);

		final HashMap<NameId, ArrayList<NameId>> selection = new HashMap<NameId, ArrayList<NameId>>();
		int index = 0;
		ArrayList<NameId> states;

		states = new ArrayList<NameId>();
		states.add(new NameId(CONSTANTS.reservationStates()[index++], Reservation.State.Provisional.name()));
		states.add(new NameId(Reservation.State.Reserved.name()));
		states.add(new NameId(Reservation.State.Confirmed.name()));
		states.add(new NameId(Reservation.State.FullyPaid.name()));
		states.add(new NameId(Reservation.State.Briefed.name()));
		states.add(new NameId(Reservation.State.Arrived.name()));
		states.add(new NameId(Reservation.State.PreDeparture.name()));
		selection.put(new NameId(CONSTANTS.reservationLabel(), NameId.Type.Reservation.name()), states);

		index = 0;
		states = new ArrayList<NameId>();
		states.add(new NameId(CONSTANTS.marketingStates()[index++], Task.CREATED));
		states.add(new NameId(CONSTANTS.marketingStates()[index++], Task.CONTACTED));
//		states.add(new NameId(CONSTANTS.marketingStates()[index++], Task.VALIDATED));
//		states.add(new NameId(CONSTANTS.marketingStates()[index++], Task.CANCELLED));
//		states.add(new NameId(CONSTANTS.marketingStates()[index++], Task.COMPLETED));
		selection.put(new NameId(CONSTANTS.marketingLabel(), Task.Type.Marketing.name()), states);

//		index = 0;
//		states = new ArrayList<NameId>();
//		states.add(new NameId(CONSTANTS.serviceStates()[index++], Task.CREATED));
//		states.add(new NameId(CONSTANTS.serviceStates()[index++], Task.COMPLETED));
//		selection.put(new NameId(CONSTANTS.serviceLabel(), Task.Type.Service.name()), states);
		
		index = 0;
		states = new ArrayList<NameId>();
		states.add(new NameId(CONSTANTS.maintenanceStates()[index++], Task.CREATED));
//		states.add(new NameId(CONSTANTS.maintenanceStates()[index++], Task.STARTED));
//		states.add(new NameId(CONSTANTS.maintenanceStates()[index++], Task.COMPLETED));
//		states.add(new NameId(CONSTANTS.maintenanceStates()[index++], Task.CANCELLED));
		selection.put(new NameId(CONSTANTS.maintenanceLabel(), Task.Type.Maintenance.name()), states);

		selectorField = new SelectorField(this, null,
				selection,
				CONSTANTS.selectorLabel(),
				tab++);
		selectorField.setSorted(false);
//		selectorField.setLabelStyle(CSS.selectorLabel());
		selectorField.setHelpText(CONSTANTS.selectorHelp());
		form.add(selectorField);

		FlowPanel shadow = new FlowPanel();
		shadow.addStyleName(AbstractField.CSS.cbtAbstractShadow());
		panel.add(shadow);

		return panel;
	}

	/* 
	 * Creates the panel displayed when there are no work items.
	 * 
	 * @return the empty panel.
	 */
	private Widget workflowtableEmpty() {
		FlowPanel panel = new FlowPanel();
		panel.add(new HTML(CONSTANTS.workflowtableEmpty()));
		panel.add(new Image(BUNDLE.workflowtableEmpty()));
		return panel;
	}
	
	/* 
	 * Creates the work item table.
	 * 
	 * @return the work item table.
	 */
	private TableField<Process> getWorkitemTable() {
		//-----------------------------------------------
		// Work Item selection model
		//-----------------------------------------------
		final NoSelectionModel<Process> selectionModel = new NoSelectionModel<Process>();
		SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				Process process = selectionModel.getLastSelectedObject();
				if (process.hasProcess(NameId.Type.Reservation.name())) {AbstractRoot.render(Razor.RESERVATION_TAB, process);}
				else if (process.hasProcess(Task.Type.Service.name())) {AbstractRoot.render(Razor.RESERVATION_TAB, process);}
				else if (process.hasProcess(Task.Type.Maintenance.name())) {AbstractRoot.render(Razor.RESERVATION_TAB, process);}
				else if (process.hasProcess(Task.Type.Marketing.name())) {AbstractRoot.render(Razor.TASK_TAB, process);}
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// The key provider to form keys from row values.
		// This is abused to set the colour style of the row.
		//-----------------------------------------------
		final ProvidesKey<Process> keyProvider = new ProvidesKey<Process>() {
			public String getKey(Process item) {
				double duration = Time.getDuration(Time.getDateStart(), item.getDuedate(), Time.DAY);
				if (duration > 7){return "color: green;";}
				else if (duration > 2){return "color: blue;";}
				else if (duration > -1){return "color: black;";}
				else {return "color: red;";}
			}
		};

		//-----------------------------------------------
		// Work Item table
		//-----------------------------------------------
		workflowTable = new TableField<Process>(this, null, 
				new WorkflowTable(),
				selectionModel,
				keyProvider,
				ROWS,
//				getPageRows(), 
				tab++);
		
		workflowTable.setEmptyValue(workflowtableEmpty());
		workflowTable.setOrderby(Process.DUEDATE);
		workflowTable.setStyleName(CSS.workitemStyle());
		workflowTable.setTableError(new TableError() {
			public boolean error() {return false;}
		});
		
		workflowTable.setTableExecutor(new TableExecutor<WorkflowTable>() {
			public void execute(WorkflowTable action) {
				getValue(action);
			}
		});
		
		int col = 0;

		//-----------------------------------------------
		// Due Date column
		//-----------------------------------------------
		Column<Process, Date> date = new Column<Process, Date>(new StyledDateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue(Process reservation) {return Time.getDateClient(reservation.getDuedate());}
		};
		workflowTable.addDateColumn(date, CONSTANTS.workitemHeaders()[col++], Process.DUEDATE);

		//-----------------------------------------------
		// Process column
		//-----------------------------------------------
		Column<Process, String> process = new Column<Process, String>(new TextCell()) {
			@Override
			public String getValue( Process reservation ) {return reservation.getProcess();}
		};
		workflowTable.addStringColumn(process, CONSTANTS.workitemHeaders()[col++], Process.PROCESS);

		//-----------------------------------------------
		// Name column
		//-----------------------------------------------
		Column<Process, String> name = new Column<Process, String>( new TextCell() ) {
			@Override
			public String getValue( Process reservation ) {return reservation.getName();}
		};
		workflowTable.addStringColumn(name, CONSTANTS.workitemHeaders()[col++], Process.NAME);

		//-----------------------------------------------
		// State column
		//-----------------------------------------------
		Column<Process, String> state = new Column<Process, String>( new TextCell()) {
			@Override
			public String getValue( Process reservation ) {return reservation.getState();}
		};
		workflowTable.addStringColumn(state, CONSTANTS.workitemHeaders()[col++], Process.STATE);

		//-----------------------------------------------
		// Actor column
		//-----------------------------------------------
		Column<Process, String> actorname = new Column<Process, String>( new TextCell()) {
			@Override
			public String getValue( Process reservation ) {return reservation.getActorname();}
		};
		workflowTable.addStringColumn(actorname, CONSTANTS.workitemHeaders()[col++], Process.ACTORNAME);

		//-----------------------------------------------
		// Customer column
		//-----------------------------------------------
		Column<Process, String> parentname = new Column<Process, String>( new TextCell()) {
			@Override
			public String getValue( Process reservation ) {return reservation.getParentname();}
		};
		workflowTable.addStringColumn(parentname, CONSTANTS.workitemHeaders()[col++], Process.PARENTNAME);

		//-----------------------------------------------
		// Notes column
		//-----------------------------------------------
		Column<Process, String> notes = new Column<Process, String>( new TextCell() ) {
			@Override
			public String getValue( Process reservation ) {return reservation.getNotes(100);}
		};
		workflowTable.addStringColumn(notes, CONSTANTS.workitemHeaders()[col++], Process.NOTES);

		return workflowTable;
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Get Work Flow Progress
		//-----------------------------------------------
		workflowProgress = new GuardedRequest<DoubleResponse>() {
			protected boolean error() {return AbstractRoot.noOrganizationid();}
			protected void send() {super.send(getValue(new WorkflowProgress()));}
			protected void receive(DoubleResponse response) {
				if (response == null || response.noValue()) {progressField.setValue(0);}
				else {progressField.setValue(response.getValue().intValue());}
			}
		};
	}
}
