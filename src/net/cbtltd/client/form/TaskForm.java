/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.form;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.client.AbstractRoot;
import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.LocalRequest;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.CommandButton;
import net.cbtltd.client.field.DatespanField;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.SuggestField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.field.TextAreaField;
import net.cbtltd.client.field.TextField;
import net.cbtltd.client.field.table.ActionCell;
import net.cbtltd.client.field.table.ActionCell.Delegate;
import net.cbtltd.client.field.table.ActionHeader;
import net.cbtltd.client.field.table.TableError;
import net.cbtltd.client.field.table.TableExecutor;
import net.cbtltd.client.panel.ContactPopup;
import net.cbtltd.client.panel.PartyPopup;
import net.cbtltd.client.panel.ProductPopup;
import net.cbtltd.client.panel.ValuePopup;
import net.cbtltd.client.resource.task.TaskBundle;
import net.cbtltd.client.resource.task.TaskConstants;
import net.cbtltd.client.resource.task.TaskCssResource;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Event;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Party.Type;
import net.cbtltd.shared.Product;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Task;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Unit;
import net.cbtltd.shared.Value;
import net.cbtltd.shared.api.HasResetId;
import net.cbtltd.shared.journal.EventJournal;
import net.cbtltd.shared.journal.EventJournalTable;
import net.cbtltd.shared.party.EmployeeNameId;
import net.cbtltd.shared.party.PartyCreator;
import net.cbtltd.shared.party.PartyPartnerExists;
import net.cbtltd.shared.task.Contact;
import net.cbtltd.shared.task.ContactTable;
import net.cbtltd.shared.task.TaskCreate;
import net.cbtltd.shared.task.TaskDelete;
import net.cbtltd.shared.task.TaskRead;
import net.cbtltd.shared.task.TaskUpdate;

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
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

/** The Class TaskForm is to display and change marketing tasks. */
public class TaskForm
extends AbstractForm<Task> {

	public static final String[] AFFILIATE_TYPES = {Type.Agent.name(), Type.Agent.name(), Type.Organization.name(), Type.Owner.name()};

	private static final TaskConstants CONSTANTS = GWT.create(TaskConstants.class);
	private static final TaskBundle BUNDLE = TaskBundle.INSTANCE;
	private static final TaskCssResource CSS = BUNDLE.css();

	private static final int CONTACT_ROWS = 25;
	private static final int EVENTJOURNAL_ROWS = 14;
	
	private static GuardedRequest<Task> taskCreate;
	private static GuardedRequest<Task> taskRead;
	private static GuardedRequest<Task> taskUpdate;
	private static GuardedRequest<Task> taskFinish;
	private static GuardedRequest<Party> partypartnerExists;
	private static GuardedRequest<Party> partypartnerSubmit;

	//Task
	private static SuggestField taskField;
	private static SuggestField goalField; //Marketing goal
	private static SuggestField starterField; //Initial trigger
	private static SuggestField outcomeField; //Final outcome
	private static SuggestField prospectField; //Prospective customer
	private static ListField actorField;
	private static DatespanField datedueField;

	//Stack
	private static StackLayoutPanel stackPanel;
	private static TextAreaField notesField;
	private static TableField<Contact> contactTable;
	
	//Affiliate
	private static TextField affiliatenameField;
	private static TextField emailaddressField;
	private static ListField affiliatetypeField;
	private static Label affiliatetermsButton;
	private static HTML affiliatetermsText;
	private static TableField<EventJournal> eventjournalTable;

	private static Task.Type type = Task.Type.Marketing;

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#permission()
	 */
	@Override
	public short[] permission(){return AccessControl.TASK_PERMISSION;}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#getValue()
	 */
	@Override
	public Task getValue() {return getValue(new Task());}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {
		if (taskField.sent(change)) {taskRead.execute();}
		else if (goalField.sent(change)) {
			String textid = AbstractRoot.getOrganizationid() + NameId.Type.Product.name() + goalField.getValue();
			notesField.setText(new Text(textid, goalField.getName(), Text.Type.Text, new Date(), "", AbstractRoot.getLanguage()));
			starterField.setState(goalField.getValue());
			outcomeField.setState(goalField.getValue());
		}
		else if (emailaddressField.sent(change)) {partypartnerExists.execute();}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onFocus()
	 */
	@Override
	public void onFocus() {
		eventjournalTable.execute(true);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#onReset(net.cbtltd.shared.api.HasNameId)
	 */
	@Override
	public void onReset(HasResetId resetid) {
		if (resetid != null && resetid.getResetid() != null && !resetid.getResetid().isEmpty()) {
			taskField.setValue(resetid.getResetid());
			taskRead.execute();
		}
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

		createActions();

		content.add(createContent());
		stackPanel = new StackLayoutPanel(com.google.gwt.dom.client.Style.Unit.EM);
		content.add(stackPanel);

		stackPanel.addSelectionHandler(new SelectionHandler<Integer>() {
			public void onSelection(SelectionEvent<Integer> event) {
				if (event.getSelectedItem() != 0 && taskField.noValue()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.taskError(), taskField);}
				refreshStackPanel();
			}
		});

		stackPanel.addStyleName(CSS.stackStyle());
		stackPanel.add(createNotes(), CONSTANTS.tasknotesLabel(), 1.5);
		stackPanel.add(createContactTable(), CONSTANTS.contactLabel(), 1.5);
		stackPanel.add(createAffiliate(), CONSTANTS.affiliateLabel(), 1.5);

		onRefresh();
		onReset(Task.CREATED);
	}

	/* Refreshes the open stack panel */
	private void refreshStackPanel() {
		switch (stackPanel.getVisibleIndex()) {
		case 1: contactTable.execute(); break;
		case 2: eventjournalTable.execute(); break;
		}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#getValue(net.cbtltd.shared.api.HasState)
	 */
	private Task getValue(Task task) {
		task.setState(state);
		task.setId(taskField.getValue());
		task.setOrganizationid(AbstractRoot.getOrganizationid());
		task.setActorid(actorField.getValue());
		task.setProcess(Task.Type.Marketing.name());
		task.setStarter(starterField.getValue());
		task.setOutcome(outcomeField.getValue());
		task.setCustomerid(prospectField.getValue());
		task.setProductid(goalField.getValue());
		task.setDate(new Date());
		task.setDuedate(datedueField.getTovalue());
		task.setQuantity(1.0);
		task.setUnit(Unit.EA);
		task.setCost(0.0);
		task.setPrice(0.0);
		task.setCurrency(AbstractRoot.getCurrency());
		task.setNotes(notesField.getValue());
		Log.debug("getValue " + task);
		return task;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.form.AbstractForm#setValue(net.cbtltd.shared.api.HasState)
	 */
	public void setValue(Task task) {
		Log.debug("setValue " + task);
		if (task == null) {onReset(Task.CREATED);}
		else {
			onStateChange(task.getState());
			setResetting(true);
			onStateChange(task.getState());
			taskField.setValue(task.getId());
			goalField.setValue(task.getProductid());
			starterField.setState(task.getProductid());
			starterField.setValue(task.getStarter());
			outcomeField.setState(task.getProductid());
			outcomeField.setValue(task.getOutcome());
			prospectField.setValue(task.getCustomerid());
			actorField.setValue(task.getActorid());
			datedueField.setValue(task.getDate());
			datedueField.setTovalue(task.getDuedate());
			goalField.setValue(task.getProductid());
			notesField.setValue(task.getNotes());
			refreshStackPanel();
//			contactTable.execute();
//			eventjournalTable.execute();
			setResetting(false);
		}
	}

	/*
	 * Gets the specified party action with its attribute values set.
	 *
	 * @param party the specified party action.
	 * @return the party action with its attribute values set.
	 */
	private Party getValue(Party party) {
		party.setCreatorid(AbstractRoot.getActorid());
		party.setBirthdate(new Date());
		party.setOrganizationid(AbstractRoot.getOrganizationid());
		party.setName(affiliatenameField.getValue());
		party.setEmailaddress(emailaddressField.getValue());
		party.setType(affiliatetypeField.getValue());
		return party;
	}

	/* 
	 * Creates the primary panel.
	 * 
	 * @return the primary panel.
	 */
	private VerticalPanel createContent() {
		final VerticalPanel form = new VerticalPanel();
		form.addStyleName(AbstractField.CSS.cbtAbstractControl());
		final Label title = new Label(CONSTANTS.titleLabels()[type.ordinal()]);
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);

		//-----------------------------------------------
		// Name field, sets all properties once selected
		//-----------------------------------------------
		taskField = new SuggestField(this, null,
				new NameIdAction(Service.TASK),
				CONSTANTS.tasknameLabel(),
				20,
				tab++);
		taskField.setFieldHalf();
		taskField.setType(Task.Type.Marketing.name());
		taskField.setHelpText(CONSTANTS.tasknameError());
		form.add(taskField);

		//-----------------------------------------------
		// Goal field
		//-----------------------------------------------
		goalField = new SuggestField(this, null,
				new NameIdAction(Service.PRODUCT),
				CONSTANTS.goalLabel(),
				20,
				tab++);
		goalField.setType(Product.Type.Marketing.name());
		goalField.setHelpText(CONSTANTS.goalHelp());

		Image goalButton = new Image(AbstractField.BUNDLE.plus());
		goalButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ProductPopup.getInstance().show(Product.Type.Marketing, goalField);
			}
		});
		goalButton.setTitle(CONSTANTS.goalbuttonHelp());
		goalField.addButton(goalButton);
		form.add(goalField);

		//-----------------------------------------------
		// Initial (entry) field
		//-----------------------------------------------
		starterField = new SuggestField(this, null,
				new NameIdAction(Service.ATTRIBUTE),
				CONSTANTS.starterLabel(),
				20,
				tab++);
		starterField.setType(Value.Type.MarketingStarter.name());
		starterField.setHelpText(CONSTANTS.starterHelp());

		Image starterButton = new Image(AbstractField.BUNDLE.plus());
		starterButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (goalField.noValue()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.goalError(), goalField);}
				else {ValuePopup.getInstance().show(Value.Type.MarketingStarter, CONSTANTS.starterLabel(), goalField.getValue(), starterField);}
			}
		});
		starterButton.setTitle(CONSTANTS.starterbuttonHelp());
		starterField.addButton(starterButton);
		form.add(starterField);

		//-----------------------------------------------
		// Final (exit) field
		//-----------------------------------------------
		outcomeField = new SuggestField(this, null,
				new NameIdAction(Service.ATTRIBUTE),
				CONSTANTS.outcomeLabel(),
				20,
				tab++);
		outcomeField.setType(Value.Type.MarketingOutcome.name());
		outcomeField.setHelpText(CONSTANTS.outcomeHelp());

		Image outcomeButton = new Image(AbstractField.BUNDLE.plus());
		outcomeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ValuePopup.getInstance().show(Value.Type.MarketingOutcome, CONSTANTS.outcomeLabel(), goalField.getValue(), outcomeField);
			}
		});
		outcomeButton.setTitle(CONSTANTS.outcomebuttonHelp());
		outcomeField.addButton(outcomeButton);
		form.add(outcomeField);

		//-----------------------------------------------
		// Customer (Guest) field
		//-----------------------------------------------
		prospectField = new SuggestField(this,  null,
				new NameIdAction(Service.PARTY),
				CONSTANTS.prospectLabel(),
				20,
				tab++);
		prospectField.setHelpText(CONSTANTS.prospectHelp());

		Image prospectButton = new Image(AbstractField.BUNDLE.plus());
		prospectButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PartyPopup.getInstance().show(Party.Type.Customer, prospectField, null);
			}
		});
		prospectField.addButton(prospectButton);
		form.add(prospectField);

		//-----------------------------------------------
		// Actor field
		//-----------------------------------------------
		actorField = new ListField(this, null,
				new EmployeeNameId(),
				CONSTANTS.actorLabel(),
				true,
				tab++);
		actorField.setDefaultValue(AbstractRoot.getActorid());
		actorField.setHelpText(CONSTANTS.actorHelp());
		form.add(actorField);

		//-----------------------------------------------
		// Date is when the task order was created
		//-----------------------------------------------
		datedueField = new DatespanField(this, null,
				CONSTANTS.datedueLabel(),
				tab++);
		datedueField.setDuration(3.0, Time.DAY);
		datedueField.setHelpText(CONSTANTS.datedueHelp());
		form.add(datedueField);

		//-----------------------------------------------
		// Send button is to record a customer contact event
		//-----------------------------------------------
		final Button sendButton = new Button(CONSTANTS.sendButton(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (taskField.noValue()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.taskError(), taskField);}
				else {ContactPopup.getInstance().show(NameId.Type.Task, taskField.getValue(), contactTable);
					if (hasState(Task.CREATED) && contactTable.hasValue()) {
						onStateChange(Task.CONTACTED);
						taskUpdate.execute();
					}
				}
			}
		});
		sendButton.addStyleName(CSS.sendButton());
		form.add(sendButton);

		form.add(createCommands());
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

		final LocalRequest resetRequest = new LocalRequest() {protected void perform() {onReset(Task.CREATED);}};

		//-----------------------------------------------
		// Create button
		//-----------------------------------------------
		final CommandButton createButton = new CommandButton(this, AbstractField.CONSTANTS.allStart(), taskCreate, tab++);
		createButton.addStyleName(AbstractField.CSS.cbtGradientGreen());
		createButton.setTitle(CONSTANTS.createHelp());
		bar.add(createButton);

		//-----------------------------------------------
		// Save button
		//-----------------------------------------------
		final CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), taskUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.setTitle(AbstractField.CONSTANTS.helpSave());
		bar.add(saveButton);

		//-----------------------------------------------
		// Complete button
		//-----------------------------------------------
		final CommandButton completeButton = new CommandButton(this, AbstractField.CONSTANTS.allFinish(), taskFinish, tab++);
		completeButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		completeButton.setTitle(CONSTANTS.finishHelp());
		bar.add(completeButton);

		//-----------------------------------------------
		// Delete button
		//-----------------------------------------------
		final CommandButton deleteButton = new CommandButton(this, AbstractField.CONSTANTS.allDelete(), taskFinish, tab++);
		deleteButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		deleteButton.setTitle(AbstractField.CONSTANTS.helpDelete());
		bar.add(deleteButton);

		//-----------------------------------------------
		// Cancel button
		//-----------------------------------------------
		final CommandButton cancelButton = new CommandButton(this, AbstractField.CONSTANTS.allCancel(), resetRequest, tab++);
		cancelButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		bar.add(cancelButton);

		//-----------------------------------------------
		// The array of transitions to define the finite state machine.
		//-----------------------------------------------
		fsm = new ArrayList<Transition>();
		fsm.add(new Transition(Task.INITIAL, cancelButton, Task.CREATED));
		fsm.add(new Transition(Task.INITIAL, saveButton, Task.CREATED));

		fsm.add(new Transition(Task.CREATED, createButton, Task.INITIAL));
		fsm.add(new Transition(Task.CREATED, saveButton, Task.CREATED));
		fsm.add(new Transition(Task.CREATED, completeButton, Task.COMPLETED));

		fsm.add(new Transition(Task.CONTACTED, createButton, Task.INITIAL));
		fsm.add(new Transition(Task.CONTACTED, saveButton, Task.CONTACTED));
		fsm.add(new Transition(Task.CONTACTED, completeButton, Task.COMPLETED));

		fsm.add(new Transition(Task.COMPLETED, createButton, Task.INITIAL));
		fsm.add(new Transition(Task.COMPLETED, saveButton, Task.COMPLETED));
		fsm.add(new Transition(Task.COMPLETED, deleteButton, Task.FINAL));
		return bar;
	}

	/* 
	 * Creates the description stack panel.
	 * 
	 * @return the description stack panel.
	 */
	private TextAreaField createNotes() {
		notesField = new TextAreaField(this, null,
				null,
				tab++);
		notesField.setMaxLength(3000);
		notesField.setDefaultValue(CONSTANTS.notesEmpty());
		notesField.setFieldStyle(CSS.notesStyle());
		return notesField;
	}

	/* 
	 * Creates the panel displayed when there are no contacts.
	 * 
	 * @return the empty panel.
	 */
	private Widget contacttableEmpty() {
		FlowPanel panel = new FlowPanel();
		panel.add(new HTML(CONSTANTS.contacttableEmpty()));
		panel.add(new Image(BUNDLE.contacttableEmpty()));
		return panel;
	}

	/* 
	 * Creates the contact list stack panel.
	 * 
	 * @return the contact list stack panel.
	 */
	private TableField<Contact> createContactTable() {

		//-----------------------------------------------
		// Contact selection handler
		//-----------------------------------------------
		final NoSelectionModel<Contact> selectionModel = new NoSelectionModel<Contact>();
		final SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				Contact contact = selectionModel.getLastSelectedObject();
				ContactPopup.getInstance().show(NameId.Type.Task, taskField.getValue(), contact, contactTable);
				if (hasState(Task.CREATED) && contactTable.hasValue()) {
					onStateChange(Task.CONTACTED);
					taskUpdate.execute();
				}
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// Contact table
		//-----------------------------------------------
		contactTable = new TableField<Contact>(this, null, 
				new ContactTable(),
				selectionModel, 
				CONTACT_ROWS,
				tab++);

		contactTable.setTableError(new TableError() {
			@Override
			public boolean error() {return taskField.noValue();}
		});

		contactTable.setTableExecutor(new TableExecutor<ContactTable>() {
			public void execute(ContactTable action) {
				action.setActivity(NameId.Type.Task.name());
				action.setId(taskField.getValue());
			}
		});

		contactTable.setEmptyValue(contacttableEmpty());
		contactTable.setOrderby(Contact.DATE);

		int col = 0;

		//-----------------------------------------------
		// Change button
		//-----------------------------------------------
		final Column<Contact, Contact> changeButton = new Column<Contact, Contact> ( 
				new ActionCell<Contact>(AbstractField.CONSTANTS.allChange(), AbstractField.CSS.cbtTableFieldChangeButton(),
						new Delegate<Contact>(){
					public void execute(Contact contact) {
						ContactPopup.getInstance().show(NameId.Type.Task, taskField.getValue(), contact, contactTable);
						if (hasState(Task.CREATED) && contactTable.hasValue()) {
							onStateChange(Task.CONTACTED);
							taskUpdate.execute();
						}
					}
				}
				)
		)
		{
			public Contact getValue(Contact contact){return contact;}
		};

		//-----------------------------------------------
		// Create button
		//-----------------------------------------------
		final ActionHeader<Contact> createButton = new ActionHeader<Contact>(
				new ActionCell<Contact>(
						AbstractField.CONSTANTS.allCreate(),
						AbstractField.CSS.cbtTableFieldCreateButton(),
						new Delegate<Contact>(){
							public void execute(Contact contact ) {
								if (taskField.noValue()) {AbstractField.addMessage(Level.ERROR, CONSTANTS.taskError(), taskField);}
								else {ContactPopup.getInstance().show(NameId.Type.Task, taskField.getValue(), contactTable);
									if (hasState(Task.CREATED) && contactTable.hasValue()) {
										onStateChange(Task.CONTACTED);
										taskUpdate.execute();
									}
								}
							}
						}
				)
		)
		{
			public Contact getValue(Contact contact){
				return contact;
			}
		};

		contactTable.addColumn(changeButton, createButton);

		//-----------------------------------------------
		// Date column
		//-----------------------------------------------
		Column<Contact, Date> date = new Column<Contact, Date>(new DateCell(AbstractRoot.getDTF())) {
			@Override
			public Date getValue( Contact contact ) {return Time.getDateClient(contact.getDate());}
		};
		contactTable.addDateColumn(date, CONSTANTS.contactHeaders()[col++], Contact.DATE);

		//-----------------------------------------------
		// Addressee column
		//-----------------------------------------------
		Column<Contact, String> name = new Column<Contact, String>(new TextCell()) {
			@Override
			public String getValue( Contact contact ) {return contact.getName(30);}
		};
		contactTable.addStringColumn(name, CONSTANTS.contactHeaders()[col++], Contact.NAME);

		//-----------------------------------------------
		// Medium column
		//-----------------------------------------------
		Column<Contact, String> type = new Column<Contact, String>( new TextCell()) {
			@Override
			public String getValue( Contact contact ) {return contact.getType();}
		};
		contactTable.addStringColumn(type, CONSTANTS.contactHeaders()[col++], Contact.TYPE);

		//-----------------------------------------------
		// Notes column
		//-----------------------------------------------
		Column<Contact, String> notes = new Column<Contact, String>( new TextCell() ) {
			@Override
			public String getValue( Contact contact ) {return contact.getNotes(80);}
		};
		contactTable.addStringColumn(notes, CONSTANTS.contactHeaders()[col++], Contact.NOTES);

		return contactTable;
	}

	/* 
	 * Creates the affiliate table stack panel.
	 * 
	 * @return the affiliate table stack panel.
	 */
	private FlowPanel createAffiliate() {

		final FlowPanel panel = new FlowPanel();
		final HTML affiliateText = new HTML(CONSTANTS.affiliateText());
		affiliateText.addStyleName(AbstractField.CSS.cbtTableFieldEmpty());
		panel.add(affiliateText);
		
		//-----------------------------------------------
		// Terms & Conditions Button
		//-----------------------------------------------
		affiliatetermsButton = new Label(CONSTANTS.affiliatetermsButton());
		affiliatetermsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				affiliatetermsText.setVisible(!affiliatetermsText.isVisible());
				eventjournalTable.setVisible(!eventjournalTable.isVisible());
				affiliatetermsButton.setText(eventjournalTable.isVisible() ? CONSTANTS.affiliatetermsButton() : CONSTANTS.eventjournaltableButton());
			}
		});
		affiliatetermsButton.addStyleName(CSS.affiliatetermsButton());
		panel.add(affiliatetermsButton);

		final HorizontalPanel form = new HorizontalPanel();
		panel.add(form);
		
		//-----------------------------------------------
		// Name field
		//-----------------------------------------------
		affiliatenameField = new TextField(this, null,
				CONSTANTS.affiliatenameLabel(),
				tab++);
		affiliatenameField.setFieldStyle(CSS.affiliatenameField());
		affiliatenameField.setLabelStyle(CSS.affiliatenameField());
		affiliatenameField.setHelpText(CONSTANTS.affiliatenameHelp());
		form.add(affiliatenameField);
		
		//-----------------------------------------------
		// Email Address field
		//-----------------------------------------------
		emailaddressField = new TextField(this,  null,
				CONSTANTS.emailaddressLabel(),
				tab++);
		emailaddressField.setMaxLength(100);
		emailaddressField.setHelpText(CONSTANTS.emailaddressHelp());
		form.add(emailaddressField);

		//-----------------------------------------------
		// Type field
		//-----------------------------------------------
		affiliatetypeField = new ListField(this, null,
				NameId.getList(CONSTANTS.affiliateTypes(), AFFILIATE_TYPES),
				CONSTANTS.affiliatetypeLabel(),
				false,
				tab++);
		affiliatetypeField.setFieldStyle(CSS.affiliatetypeField());
		affiliatetypeField.setDefaultValue(Party.Type.Organization.name());
		affiliatetypeField.setHelpText(CONSTANTS.affiliatetypeHelp());
		form.add(affiliatetypeField);

		//-----------------------------------------------
		// Submit button
		//-----------------------------------------------
		Button affiliateButton = new Button(AbstractField.CONSTANTS.allSubmit());
		affiliateButton.addStyleName(AbstractField.CSS.cbtCommandButton());
		affiliateButton.addStyleName(AbstractField.CSS.cbtGradientGreen());
		affiliateButton.setTitle(CONSTANTS.submitbuttonHelp());
		affiliateButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				partypartnerSubmit.execute();
			}
		});
		form.add(affiliateButton);
		
		//-----------------------------------------------
		// Terms & Conditions Text
		//-----------------------------------------------
		affiliatetermsText = new HTML(CONSTANTS.affiliatetermsText());
		affiliatetermsText.addStyleName(CSS.affiliatetermstextField());
		affiliatetermsText.setVisible(false);
		panel.add(affiliatetermsText);
		panel.add(eventjournalTable());
		
		return panel;
	}

	/* 
	 * Creates the panel displayed when there are no financial transactions.
	 * 
	 * @return the empty panel.
	 */
	private Widget eventjournaltableEmpty() {
		FlowPanel panel = new FlowPanel();
		panel.add(new HTML(CONSTANTS.eventjournaltableEmpty()));
		panel.add(new Image(BUNDLE.eventjournaltableEmpty()));
		return panel;
	}

	/* 
	 * Creates the financial transaction table stack panel.
	 * 
	 * @return the financial transaction table stack panel.
	 */
	private TableField<EventJournal> eventjournalTable() {

		//-----------------------------------------------
		// Financial Transactions selection handler
		//-----------------------------------------------
		final NoSelectionModel<EventJournal> selectionModel = new NoSelectionModel<EventJournal>();
		final SelectionChangeEvent.Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				//TODO: JournalPopup.getInstance().show(selectionModel.getLastSelectedObject(), eventjournalTable);
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);

		//-----------------------------------------------
		// Financial Transactions table
		//-----------------------------------------------
		eventjournalTable = new TableField<EventJournal>(this, null, 
				new EventJournalTable(),
				selectionModel, 
				CONSTANTS.eventjournalTable(),
				EVENTJOURNAL_ROWS,
				tab++);

		eventjournalTable.setOrderby(Event.DATE);

		eventjournalTable.setTableError(new TableError() {
			@Override
			public boolean error() {return (
					AbstractRoot.noOrganizationid()
					|| AbstractRoot.noActorid()
			);}
		});

		eventjournalTable.setTableExecutor(new TableExecutor<EventJournalTable>() {
			public void execute(EventJournalTable action) {
				action.setOrganizationid(Party.CBT_LTD_PARTY);
				action.setEntitytype(NameId.Type.Party.name());
				action.setEntityid(AbstractRoot.getActorid());
				action.setCurrency(Currency.Code.USD.name());
			}
		});

		eventjournalTable.setEmptyValue(eventjournaltableEmpty());
		eventjournalTable.setOrderby(EventJournal.DATE);

		int col = 0;

		//-----------------------------------------------
		// Process column
		//-----------------------------------------------
		Column<EventJournal, String> process = new Column<EventJournal, String>(new TextCell()) {
			@Override
			public String getValue( EventJournal eventjournal ) {return eventjournal.getProcess();}
		};
		eventjournalTable.addStringColumn(process, CONSTANTS.eventjournalHeaders()[col++], EventJournal.PROCESS);

		//-----------------------------------------------
		// Name column
		//-----------------------------------------------
		Column<EventJournal, String> name = new Column<EventJournal, String>( new TextCell() ) {
			@Override
			public String getValue( EventJournal eventjournal ) {return eventjournal.getName();}
		};
		eventjournalTable.addStringColumn(name, CONSTANTS.eventjournalHeaders()[col++], EventJournal.NAME);

		//-----------------------------------------------
		// Date column
		//-----------------------------------------------
		Column<EventJournal, Date> date = new Column<EventJournal, Date>(new DateCell(AbstractRoot.getDF())) {
			@Override
			public Date getValue( EventJournal eventjournal ) {return Time.getDateClient(eventjournal.getDate());}
		};
		eventjournalTable.addDateColumn(date, CONSTANTS.eventjournalHeaders()[col++], EventJournal.DATE);

		//-----------------------------------------------
		// Debit Amount column
		//-----------------------------------------------
		Column<EventJournal, Number> debitAmount = new Column<EventJournal, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( EventJournal eventjournal ) {return eventjournal.getDebitamount();}
		};
		eventjournalTable.addNumberColumn( debitAmount, CONSTANTS.eventjournalHeaders()[col++], EventJournal.DEBITAMOUNT);

		//-----------------------------------------------
		// Credit Amount column
		//-----------------------------------------------
		Column<EventJournal, Number> creditAmount = new Column<EventJournal, Number>(new NumberCell(AbstractField.AF)) {
			@Override
			public Double getValue( EventJournal eventjournal ) {return eventjournal.getCreditamount();}
		};
		eventjournalTable.addNumberColumn( creditAmount, CONSTANTS.eventjournalHeaders()[col++], EventJournal.CREDITAMOUNT);

		//-----------------------------------------------
		// Currency column
		//-----------------------------------------------
		Column<EventJournal, String> currency = new Column<EventJournal, String>( new TextCell() ) {
			@Override
			public String getValue( EventJournal eventjournal ) {return eventjournal.getCurrency();}
		};
		eventjournalTable.addStringColumn(currency, CONSTANTS.eventjournalHeaders()[col++], EventJournal.UNIT);

		//-----------------------------------------------
		// Description column
		//-----------------------------------------------
		Column<EventJournal, String> description = new Column<EventJournal, String>( new TextCell() ) {
			@Override
			public String getValue( EventJournal eventjournal ) {return eventjournal.getDescription();}
		};
		eventjournalTable.addStringColumn(description, CONSTANTS.eventjournalHeaders()[col++], EventJournal.DESCRIPTION);

		return eventjournalTable;
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Create Task
		//-----------------------------------------------
		taskCreate = new GuardedRequest<Task>() {
			protected String popup() {return hasChanged() ? AbstractField.CONSTANTS.errChangeOK() : null;}
			public void cancel() {state = oldstate;}
			protected boolean error() {return ifMessage(AbstractRoot.noOrganizationid(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), taskField);}
			protected void send() {
				onReset(Task.INITIAL);
				super.send(getValue(new TaskCreate()));
			}
			protected void receive(Task task) {setValue(task);}
		};

		//-----------------------------------------------
		// Read Task
		//-----------------------------------------------
		taskRead = new GuardedRequest<Task>() {
			protected boolean error() {return taskField.noValue();}
			protected void send() {super.send(getValue(new TaskRead()));}
			protected void receive(Task task) {setValue(task);}
		};

		//-----------------------------------------------
		// Update Task
		//-----------------------------------------------
		taskUpdate = new GuardedRequest<Task>() {
			protected boolean error() {
				return (
						ifMessage(AbstractRoot.noOrganizationid(), Level.ERROR, AbstractField.CONSTANTS.errOrganizationid(), taskField)
						|| ifMessage(AbstractRoot.noActorid(), Level.ERROR, AbstractField.CONSTANTS.errActorid(), taskField)
						|| ifMessage(taskField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errId(), taskField)
						|| ifMessage(taskField.noName(), Level.ERROR, AbstractField.CONSTANTS.errId(), taskField)
						|| ifMessage(goalField.noValue(), Level.ERROR, CONSTANTS.goalError(), goalField)
						|| ifMessage(datedueField.noTovalue() || datedueField.getTovalue().before(Time.getDateStart()), Level.ERROR, CONSTANTS.duedateError(), datedueField)
						//						|| ifMessage(addresseesField.noValue(), Level.ERROR, CONSTANTS.addresseesError(), addresseesField)
				);
			}
			protected void send() {super.send(getValue(new TaskUpdate()));}
			protected void receive(Task task) {setValue(task);}
		};

		//-----------------------------------------------
		// Finish Task
		//-----------------------------------------------
		taskFinish = new GuardedRequest<Task>() {
			protected String popup() {return outcomeField.noValue() ? CONSTANTS.finishError() : null;}
			public void cancel() {state = oldstate;}
			protected boolean error() {return ifMessage(taskField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errId(), taskField);}
			protected void send() {super.send(getValue(new TaskDelete()));}
			protected void receive(Task task) {onReset(Task.CREATED);}
		};
		
		//-----------------------------------------------
		// Get Partner Party if it exists
		//-----------------------------------------------
		partypartnerExists = new GuardedRequest<Party>() {
			protected boolean error() {return emailaddressField.noValue();}
			protected void send() {super.send(getValue(new PartyPartnerExists()));}
			protected void receive(Party party) {
				if (party != null) {
					AbstractField.addMessage(Level.TERSE, CONSTANTS.partyexistsError(), emailaddressField);
					emailaddressField.setValue(Model.BLANK);
				}
			}
		};
		
		//-----------------------------------------------
		// Submit Partner Party
		//-----------------------------------------------
		partypartnerSubmit = new GuardedRequest<Party>() {
			protected boolean error() {
				return (
						ifMessage(AbstractRoot.noOrganizationid(), Level.TERSE, AbstractField.CONSTANTS.errOrganizationid(), affiliatenameField)
						|| ifMessage(affiliatenameField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), affiliatenameField)
						|| ifMessage(affiliatetypeField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errTypes(), affiliatetypeField)
						|| ifMessage(emailaddressField.noValue(), Level.ERROR, AbstractField.CONSTANTS.errEmailaddress(), emailaddressField)
						|| ifMessage(!Party.isEmailAddress(emailaddressField.getValue()), Level.ERROR, AbstractField.CONSTANTS.errEmailaddress(), emailaddressField)
				);
			}
			protected void send() {super.send(getValue(new PartyCreator()));}
			protected void receive(Party party) {
					AbstractField.addMessage(Level.TERSE, CONSTANTS.partysubmittedText(), affiliatenameField);
					emailaddressField.setValue(Model.BLANK);
			}
		};
	}
}