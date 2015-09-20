/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.panel;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.LocalRequest;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.CommandButton;
import net.cbtltd.client.field.DatespanField;
import net.cbtltd.client.field.DoubleunitField;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.MessagePopup;
import net.cbtltd.client.field.ShuttleField;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.field.ToggleField;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.task.TaskBundle;
import net.cbtltd.client.resource.task.TaskConstants;
import net.cbtltd.client.resource.task.TaskCssResource;
import net.cbtltd.shared.Account;
import net.cbtltd.shared.Event;
import net.cbtltd.shared.Journal;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Task;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.finance.CurrencyNameId;
import net.cbtltd.shared.journal.JournalTaskUpdate;
import net.cbtltd.shared.product.ProductRead;
import net.cbtltd.shared.reservation.ReservationEntities;
import net.cbtltd.shared.task.TaskCreate;
import net.cbtltd.shared.task.TaskDelete;
import net.cbtltd.shared.task.TaskRead;
import net.cbtltd.shared.task.TaskUpdate;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/** The Class TaskPopup is to display and change task instances. */
public class TaskPopup
extends AbstractPopup<Task> {

	private static final TaskConstants CONSTANTS = GWT.create(TaskConstants.class);
	private static final TaskBundle BUNDLE = TaskBundle.INSTANCE;
	private static final TaskCssResource CSS = BUNDLE.css();

	private static GuardedRequest<Task> taskCreate;
	private static GuardedRequest<Task> taskRead;
	private static GuardedRequest<Task> taskUpdate;
	private static GuardedRequest<Task> taskDelete;
//	private static GuardedRequest<PriceResponse> taskPrice;
	private static GuardedRequest<Product> productRead;

	private static Label titleLabel;
	private static SuggestField tasknameField;
	private static TextField stateField;
	private static SuggestField productField;
	private static SuggestField customerField;
	private static ListField actorField;
	private static ToggleField supplieroremployeeField;
	private static SuggestField supplierField;
	private static ShuttleField employeesField;
	private static DatespanField datedueField;
	private static DoubleunitField quantityField;
	private static DoubleunitField priceField;
	private static TextAreaField tasknotesField;
	
	private static Task.Type type;
	private static TableField<Task> parentTable; // table that invoked popup
	private static ReservationEntities reservationentities;

	/** Instantiates a new task pop up panel. */
	public TaskPopup() {
		super(true);
		AbstractField.CSS.ensureInjected();
		CSS.ensureInjected();
		createActions();
		setWidget(createContent());
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
	}
	
	private static TaskPopup taskPopup;
	
	/**
	 * Gets the single instance of TaskPopup.
	 *
	 * @return single instance of TaskPopup
	 */
	public static synchronized TaskPopup getInstance() {
		if (taskPopup == null) {taskPopup = new TaskPopup();}
		return taskPopup;
	}

	/**
	 * Shows the panel for the specified task.
	 *
	 * @param id the ID of the specified task.
	 */
	public void show(String id) {
		tasknameField.setValue(id);
		taskRead.execute();
	}

	/**
	 * Shows the panel for the specified task instance.
	 *
	 * @param task the specified task instance
	 * @param parentTable the table that invoked the pop up panel.
	 */
	public void show(Task task, TableField<Task> parentTable) {
		TaskPopup.parentTable = parentTable;
		tasknameField.setValue(task.getId());
		taskRead.execute();
	}

	/**
	 * Shows the panel for the specified task type.
	 *
	 * @param type the specified task type.
	 * @param reservationentities the entities associated with the task.
	 * @param parentTable the table that invoked the pop up panel.
	 */
	public void show(Task.Type type, ReservationEntities reservationentities, TableField<Task> parentTable) {
		TaskPopup.reservationentities = reservationentities;
		TaskPopup.parentTable = parentTable;
		onReset(Task.INITIAL);
		TaskPopup.type = type;
		customerField.setIds(reservationentities.getIds());
		taskCreate.execute(true);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {
		if (tasknameField.sent(change)) {taskRead.execute();}
		else if (productField.sent(change)) {productRead.execute();}
		else if (supplieroremployeeField.sent(change)) {
			priceField.setVisible(type == Task.Type.Maintenance && !supplieroremployeeField.getValue());
			supplierField.setVisible(type == Task.Type.Maintenance && !supplieroremployeeField.getValue());
			employeesField.setVisible(type == Task.Type.Maintenance && supplieroremployeeField.getValue());
		}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue()
	 */
	@Override
	public Task getValue() {return getValue(new Task());}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue(java.lang.Object)
	 */
	private Task getValue(Task task) {
		task.setState(state);
		task.setOrganizationid(AbstractRoot.getOrganizationid());
		task.setActorid(AbstractRoot.getActorid());
		task.setId(tasknameField.getValue());
		task.setName(tasknameField.getName());
		task.setActivity(NameId.Type.Reservation.name());
		task.setParentid(reservationentities == null ? null : reservationentities.getId());
		task.setProcess(type == null ? null : type.name());
		task.setProductid(productField.getValue());
		task.setCustomerid(customerField.getValue());
		task.setActorid(actorField.getValue());
		task.setResourceEmployee(supplieroremployeeField.getValue());
		if (supplieroremployeeField.getValue()) {task.setResources(employeesField.getValue());}
		else {task.setResource(supplierField.getValue());}
		task.setDate(datedueField.getValue());
		task.setDuedate(datedueField.getTovalue());
		task.setQuantity(quantityField.getValue());
		task.setUnit(quantityField.getUnitvalue());
		//		task.setCost(costField.getValue());
		task.setPrice(priceField.getValue());
		task.setCurrency(priceField.getUnitvalue());
		task.setNotes(tasknotesField.getValue());
		Log.debug("getValue " + task);
		return task;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(Task task) {
		Log.debug("setValue " + task);
		if (task == null) {onReset(Task.CREATED);}
		else {
			setResetting(true);
			onStateChange(task.getState());
			type = Task.Type.valueOf(task.getProcess());
			
			tasknameField.setValue(task.getId());
			stateField.setValue(task.getState());
			datedueField.setValue(task.getDate());
			productField.setValue(task.getProductid());
			customerField.setValue(task.getCustomerid());
			actorField.setValue(task.getActorid());
			supplieroremployeeField.setValue(task.isResourceEmployee());
			if (task.isResourceEmployee()) {employeesField.setValue(task.getResources());}
			else {supplierField.setValue(task.getResource());}
			quantityField.setValue(task.getQuantity());
			quantityField.setUnitvalue(task.getUnit());
			//			costField.setValue(task.getCost());
			priceField.setValue(task.getPrice());
			priceField.setUnitvalue(task.getCurrency());
			tasknotesField.setValue(task.getNotes());
			
			int index = type.ordinal();
			boolean isMaintenance = task.hasProcess(Task.Type.Maintenance.name());
			boolean isService = task.hasProcess(Task.Type.Service.name());
			
			titleLabel.setText(CONSTANTS.titleLabels()[index]);
			productField.setLabel(CONSTANTS.productnameLabels()[index]);
			
			customerField.setVisible(isMaintenance || isService);
			quantityField.setVisible(false);
			priceField.setVisible(isMaintenance);
			supplierField.setVisible(isMaintenance && !supplieroremployeeField.getValue());
			employeesField.setVisible(isMaintenance && supplieroremployeeField.getValue());
			center();			
			setResetting(false);
		}
	}

	/*
	 * Gets the specified product action with its attributes set.
	 *
	 * @param action the specified action.
	 * @return the action with its attributes set.
	 */
	private Product getValue(Product action) {
		action.setId(productField.getValue());
		return action;
	}
	
	/*
	 * Sets the field(s) from the specified product.
	 * 
	 * @param product the specified product.
	 */
	private void setValue(Product product) {
		if (product != null) {supplierField.setValue(product.getSupplierid());}
	}

	/*
	 * Creates the panel of task fields.
	 * 
	 * @return the panel of task fields.
	 */
	private VerticalPanel createContent() {
		VerticalPanel form = new VerticalPanel();
		titleLabel = new Label();
		titleLabel.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(titleLabel);
		
		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TaskPopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);

		//-----------------------------------------------
		// Name field, sets all properties once selected
		//-----------------------------------------------
		tasknameField = new SuggestField(this, null,
				new NameIdAction(Service.TASK),
				CONSTANTS.tasknameLabel(),
				20,
				tab++);
		tasknameField.setFieldHalf();
		tasknameField.setEnabled(false);
		tasknameField.setHelpText(CONSTANTS.tasknameHelp());

		//-----------------------------------------------
		// State field
		//-----------------------------------------------
		stateField = new TextField(this, null,
				CONSTANTS.stateLabel(),
				tab++);
		stateField.setEnabled(false);
		stateField.setFieldHalf();
		stateField.setHelpText(CONSTANTS.stateHelp());

		HorizontalPanel rs = new HorizontalPanel();
		rs.add(tasknameField);
		rs.add(stateField);
		form.add(rs);

		//-----------------------------------------------
		// Product field
		//-----------------------------------------------
		productField = new SuggestField(this, null,
				new NameIdAction(Service.PRODUCT),
				CONSTANTS.productnameLabels()[0],
				20,
				tab++);
		productField.setType(Product.Type.Maintenance.name());
		productField.setHelpText(CONSTANTS.productHelp());
		
		Image productButton = new Image(AbstractField.BUNDLE.plus());
		productButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ProductPopup.getInstance().show(Product.Type.Maintenance, productField);
			}
		});
		productButton.setTitle(CONSTANTS.productbuttonHelp());
		productField.addButton(productButton);
		form.add(productField);
		
		//-----------------------------------------------
		// Customer field
		//-----------------------------------------------
		customerField = new SuggestField(this, null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.payerLabel(),
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
		customerButton.setTitle(CONSTANTS.customerbuttonHelp());
		customerField.addButton(customerButton);
		form.add(customerField);

		//-----------------------------------------------
		// Actor field
		//-----------------------------------------------
		actorField = new ListField(this, null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.actorLabel(),
				false,
				tab++);
		actorField.setType(Party.Type.Actor.name());
		actorField.setDefaultValue(AbstractRoot.getActorid());
		actorField.setHelpText(CONSTANTS.actorHelp());
		form.add(actorField);

		//-----------------------------------------------
		// Supplier or Employee field
		//-----------------------------------------------
		supplieroremployeeField = new ToggleField(this, null,
				CONSTANTS.supplieroremployeeLabel(),
				CONSTANTS.supplieroremployeeupLabel(),
				CONSTANTS.supplieroremployeedownLabel(),
				tab++);
		//form.add(supplieroremployeeField);
		
		//-----------------------------------------------
		// Supplier field
		//-----------------------------------------------
		supplierField = new SuggestField(this, null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.supplierLabel(),
				20,
				tab++);
		supplierField.setDefaultValue(AbstractRoot.getOrganizationid());
		supplierField.setType(Party.Type.Supplier.name());
		supplierField.setHelpText(CONSTANTS.supplierHelp());

		Image supplierButton = new Image(AbstractField.BUNDLE.plus());
		supplierButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PartyPopup.getInstance().show(Party.Type.Supplier, supplierField, null);
			}
		});
		supplierButton.setTitle(CONSTANTS.supplierbuttonHelp());
		supplierField.addButton(supplierButton);
		form.add(supplierField);
		
		//-----------------------------------------------
		// Price field
		//-----------------------------------------------
		priceField = new DoubleunitField(this,  null,
				new CurrencyNameId(),
				CONSTANTS.priceLabel(),
				AbstractField.AF,
				tab++);
		priceField.setHelpText(CONSTANTS.priceHelp());
		form.add(priceField);

		//-----------------------------------------------
		// Employee shuttle
		//-----------------------------------------------
		employeesField = new ShuttleField(this, null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.employeesLabel(),
				tab++);
		employeesField.setType(Party.Type.Employee.name());
		employeesField.setHelpText(CONSTANTS.employeesHelp());
		form.add(employeesField);

		//-----------------------------------------------
		// Due Date is when the work order is entered and due
		//-----------------------------------------------
		datedueField = new DatespanField(this, null,
				CONSTANTS.datedueLabel(),
				tab++);
		datedueField.setDefaultValue(new Date());
		datedueField.setDuration(1.0, Time.DAY);
		datedueField.setHelpText(CONSTANTS.datedueHelp());
		form.add(datedueField);

		//-----------------------------------------------
		// Duration field
		//-----------------------------------------------
		quantityField = new DoubleunitField(this, null,
				NameId.getList(AbstractField.CONSTANTS.allUnits(), Unit.UNITS),
				CONSTANTS.quantityLabel(),
				AbstractField.QF,
				tab++);
		quantityField.setDefaultValue(1.0);
		quantityField.setHelpText(CONSTANTS.quantityHelp());
		form.add(quantityField);

		//-----------------------------------------------
		// Notes field
		//-----------------------------------------------
		tasknotesField = new TextAreaField(this, null,
				CONSTANTS.tasknotesLabel(),
				tab++);
		tasknotesField.setMaxLength(1000);
		tasknotesField.setHelpText(CONSTANTS.tasknotesHelp());
		form.add(tasknotesField);

		form.add(createCommands());

		onRefresh();
		onReset(Task.CREATED);
		return form;
	}

	/*
	 * Creates the command bar.
	 * 
	 * @return the command bar.
	 */
	private HorizontalPanel createCommands() {
		final HorizontalPanel bar = new HorizontalPanel();
		bar.addStyleName(AbstractField.CSS.cbtAbstractCommand());
		
		final LocalRequest cancelRequest = new LocalRequest() {protected void perform() {hide();}};

		final LocalRequest taskPost = new LocalRequest() {
			protected void perform() {postTask();}
		};

		final LocalRequest taskComplete = new LocalRequest() {
			protected void perform() {
				MessagePopup.getInstance().showYesNo(CONSTANTS.taskPost(), taskPost);
				taskUpdate.execute();
				hide();
			}
		};

		//-----------------------------------------------
		// Save button
		//-----------------------------------------------
		final CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), taskUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.setTitle(AbstractField.CONSTANTS.helpSave());
		bar.add(saveButton);

		//-----------------------------------------------
		// Complete button
		//-----------------------------------------------
		final CommandButton completeButton = new CommandButton(this, CONSTANTS.completeButton(), taskComplete, tab++);
		completeButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		completeButton.addStyleName(AbstractField.CSS.cbtGradientGreen());
		completeButton.setTitle(CONSTANTS.completeHelp());
		bar.add(completeButton);

		//-----------------------------------------------
		// Delete button
		//-----------------------------------------------
		final CommandButton deleteButton = new CommandButton(this, AbstractField.CONSTANTS.allDelete(), taskDelete, tab++);
		deleteButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		deleteButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		deleteButton.setTitle(AbstractField.CONSTANTS.helpDelete());
		bar.add(deleteButton);

		//-----------------------------------------------
		// Cancel button
		//-----------------------------------------------
		final CommandButton cancelButton = new CommandButton(this, AbstractField.CONSTANTS.allCancel(), cancelRequest, tab++);
		cancelButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		cancelButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		cancelButton.setTitle(AbstractField.CONSTANTS.helpCancel());
		bar.add(cancelButton);

		//-----------------------------------------------
		// Transition array that defines the finite state machine
		//-----------------------------------------------
		fsm = new ArrayList<Transition>();
		fsm.add(new Transition(Task.INITIAL, cancelButton, Task.CREATED));
		fsm.add(new Transition(Task.INITIAL, saveButton, Task.CREATED));
		
		fsm.add(new Transition(Task.CREATED, saveButton, Task.CREATED));
		fsm.add(new Transition(Task.CREATED, completeButton, Task.COMPLETED));
		
		fsm.add(new Transition(Task.COMPLETED, saveButton, Task.COMPLETED));
		fsm.add(new Transition(Task.COMPLETED, deleteButton, Task.CREATED));
		return bar;
	}

	/* Posts the task valeus to the ledger*/
	private void postTask() {
		Log.debug("postTask " + getValue());
		final Task task = getValue();
		if (ifMessage(customerField.noValue(), Level.TERSE, CONSTANTS.customerError(), customerField)
				|| task.noAmount()
		) {return;}
		
		boolean reversed = task.hasState(Task.CREATED);
		String description = CONSTANTS.descriptionLabel() + reservationentities.getReservation().getName();
		Double amount = task.getAmount();
		Double outputtax = 0.0;
		Double inputtax = 0.0;

		//CR output tax
		if (supplierField.hasValue(AbstractRoot.getOrganizationid()) 
				&& reservationentities != null 
				&& reservationentities.getManager().hasTaxnumber()) {
			outputtax = Event.round(amount * 0.14 / 1.14); //TODO:
		}
		
		//CR input tax
		if (customerField.hasValue(AbstractRoot.getOrganizationid()) 
				&& reservationentities != null 
				&& reservationentities.getManager().hasTaxnumber()) {
			inputtax = Event.round(amount * 0.14 / 1.14); //TODO:
		}

		JournalTaskUpdate event = new JournalTaskUpdate();
		event.setTaskid(task.getId());
		if (reversed) {event.setTaskstate(Task.CREATED);}
		else {event.setTaskstate(Task.COMPLETED);}
		event.setActivity(NameId.Type.Reservation.name());
		event.setActorid(AbstractRoot.getActorid());
		event.setActorname(AbstractRoot.getActorname());
		event.setDate(new Date());
		event.setDonedate(new Date());
		event.setNotes(description);
		event.setOrganizationid(AbstractRoot.getOrganizationid());
		event.setParentid(reservationentities.getId());
		event.setProcess(Event.Type.Purchase.name());
		event.setState(Event.CREATED);
		event.setType(Event.ACCOUNTING);

		if (supplierField.hasValue(AbstractRoot.getOrganizationid())) {
			//CR sales
			event.addItem(new Journal(
					event.getId(),
					Model.ZERO,
					AbstractRoot.getOrganizationid(),
					AbstractRoot.getOrganizationname(),
					Model.ZERO,
					Account.SALES_REVENUE,
					Account.SALES_REVENUE_NAME,
					NameId.Type.Party.name(),
					supplierField.getValue(),
					supplierField.getName(),
					reversed ? -1.0 : 1.0,
					Unit.EA,
					0.0,
					AbstractRoot.getCurrency(),
					reversed ? amount - outputtax: 0.0,
					reversed ? 0.0 : amount - outputtax,
					description
				)
			);
			
			if (Math.abs(outputtax) >= 0.01) {
				//CR output tax account
				event.addItem(new Journal(
						event.getId(),
						Model.ZERO,
						AbstractRoot.getOrganizationid(),
						AbstractRoot.getOrganizationname(),
						Model.ZERO,
						Account.VAT_OUTPUT,
						Account.VAT_OUTPUT_NAME,
						Model.BLANK,
						Model.ZERO,
						Model.BLANK,
						reversed ? -1.0 : 1.0,
						Unit.EA,
						0.0,
						AbstractRoot.getCurrency(),
						reversed ? outputtax : 0.0,
						reversed ? 0.0 : outputtax ,
						description
					)
				);
			}
		}
		else {
			//CR accounts payable
			event.addItem(new Journal(
					event.getId(),
					Model.ZERO,
					AbstractRoot.getOrganizationid(),
					AbstractRoot.getOrganizationname(),
					Model.ZERO,
					Account.ACCOUNTS_PAYABLE,
					Account.ACCOUNTS_PAYABLE_NAME,
					NameId.Type.Party.name(),
					supplierField.getValue(),
					supplierField.getName(),
					reversed ? -1.0 : 1.0,
					Unit.EA,
					0.0,
					AbstractRoot.getCurrency(),
					reversed ? amount : 0.0,
					reversed ? 0.0 : amount,
					description
				)
			);
		}
		
		if (customerField.hasValue(AbstractRoot.getOrganizationid())) {
			//DR maintenance expenses
			event.addItem(new Journal(
					event.getId(),
					Model.ZERO,
					AbstractRoot.getOrganizationid(),
					AbstractRoot.getOrganizationname(),
					Model.ZERO,
					Account.MAINTENANCE_COST,
					Account.MAINTENANCE_COST_NAME,
					NameId.Type.Party.name(),
					customerField.getValue(),
					customerField.getName(),
					reversed ? -1.0 : 1.0,
					Unit.EA,
					0.0,
					AbstractRoot.getCurrency(),
					reversed ? 0.0 : amount - inputtax,
					reversed ? amount - inputtax: 0.0,
					description
				)
			);

			if (Math.abs(inputtax) >= 0.01) {
				//CR input tax account
				event.addItem(new Journal(
						event.getId(),
						Model.ZERO,
						AbstractRoot.getOrganizationid(),
						AbstractRoot.getOrganizationname(),
						Model.ZERO,
						Account.VAT_INPUT,
						Account.VAT_INPUT_NAME,
						Model.BLANK,
						Model.ZERO,
						Model.BLANK,
						reversed ? -1.0 : 1.0,
						Unit.EA,
						0.0,
						AbstractRoot.getCurrency(),
						reversed ? 0.0 : inputtax,
						reversed ? inputtax : 0.0,
						description
					)
				);
			}
		}
		else {
			//DR accounts receivable
			event.addItem(new Journal(
					event.getId(),
					Model.ZERO,
					AbstractRoot.getOrganizationid(),
					AbstractRoot.getOrganizationname(),
					Model.ZERO,
					Account.ACCOUNTS_RECEIVABLE,
					Account.ACCOUNTS_RECEIVABLE_NAME,
					NameId.Type.Party.name(),
					customerField.getValue(),
					customerField.getName(),
					reversed ? -1.0 : 1.0,
					Unit.EA,
					0.0,
					AbstractRoot.getCurrency(),
					reversed ? 0.0 : amount,
					reversed ? amount : 0.0,
					description
				)
			);
		}

		AbstractRoot.getService().send(event, new AsyncCallback<Event>() {
			public void onFailure(Throwable caught) {
				Window.alert("Service request failed " + caught.getMessage());
				caught.printStackTrace();
			} 
			public void onSuccess(Event response) {
				if (parentTable != null) {parentTable.execute(true);}
			}
		});
	}
	
	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Read Product
		//-----------------------------------------------
		productRead = new GuardedRequest<Product>() {
			protected boolean error() {return productField.noValue();}
			protected void send() {super.send(getValue(new ProductRead()));}
			protected void receive(Product product){setValue(product);}
		};

		//-----------------------------------------------
		// Create Task
		//-----------------------------------------------
		taskCreate = new GuardedRequest<Task>() {
//			protected String popup() {return hasChanged() ? AbstractField.CONSTANTS.errChangeOK() : null;}
			protected boolean error() {return ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), tasknameField);}
			protected void send() {super.send(getValue(new TaskCreate()));}
			protected void receive(Task task) {setValue(task);}
		};

		//-----------------------------------------------
		// Read Task
		//-----------------------------------------------
		taskRead = new GuardedRequest<Task>() {
//			protected String popup() {return hasChanged() ? AbstractField.CONSTANTS.errChangeOK() : null;}
			protected boolean error() {return ifMessage(tasknameField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), tasknameField);}
			protected void send() {super.send(getValue(new TaskRead()));}
			protected void receive(Task task) {setValue(task);}
		};

		//-----------------------------------------------
		// Update Task
		//-----------------------------------------------
		taskUpdate = new GuardedRequest<Task>() {
			protected boolean error() {
				return (
						ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), tasknameField)
						|| ifMessage(AbstractRoot.noActorid(), Level.TERSE, AbstractField.CONSTANTS.errActorid(), tasknameField)
						|| ifMessage(tasknameField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), tasknameField)
						|| ifMessage(quantityField.noUnitvalue(), Level.TERSE, AbstractField.CONSTANTS.errUnit(), quantityField)
						|| ifMessage(priceField.noUnitvalue(), Level.TERSE, AbstractField.CONSTANTS.errCurrency(), priceField)
						|| ifMessage(!supplieroremployeeField.getValue() && supplierField.noValue(), Level.TERSE, CONSTANTS.supplierError(), supplierField)
						|| ifMessage(supplieroremployeeField.getValue() && employeesField.noValue(), Level.TERSE, CONSTANTS.employeesError(), employeesField)
				);
			}
			protected void send() {super.send(getValue(new TaskUpdate()));}
			protected void receive(Task task) {
				if (parentTable != null) {parentTable.execute(true);}
				hide();
			}
		};

		//-----------------------------------------------
		// Create Task
		//-----------------------------------------------
		taskDelete = new GuardedRequest<Task>() {
			protected String popup() {return AbstractField.CONSTANTS.errDeleteOK();}
			protected boolean error() {return ifMessage(tasknameField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), tasknameField);}
			protected void send() {super.send(getValue(new TaskDelete()));}
			protected void receive(Task task) {
				if (parentTable != null) {parentTable.execute(true);}
				hide();
			}
		};

		//-----------------------------------------------
		// Refresh Task Prices
		//-----------------------------------------------
//		taskPrice = new GuardedRequest<PriceResponse>() {
//			protected boolean error() {return tasknameField.noValue();}
//			protected void send() {super.send(getValue(new TaskPrice()));}
//			protected void receive(PriceResponse response) {priceField.setValue(response.getValue());}
//		};

	}
}
