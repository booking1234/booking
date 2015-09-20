/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.panel;

import java.util.ArrayList;

import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.LocalRequest;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.AbstractField.Level;
import net.cbtltd.client.field.CommandButton;
import net.cbtltd.client.field.DatespanField;
import net.cbtltd.client.field.DoubleunitField;
import net.cbtltd.client.field.IntegerField;
import net.cbtltd.client.field.ListField;
import net.cbtltd.client.field.TableField;
import net.cbtltd.client.form.Transition;
import net.cbtltd.client.resource.yield.YieldConstants;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Time;
import net.cbtltd.shared.Yield;
import net.cbtltd.shared.yield.YieldCreate;
import net.cbtltd.shared.yield.YieldDelete;
import net.cbtltd.shared.yield.YieldUpdate;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/** The Class YieldPopup is to display and change yield management rules. */
public class YieldPopup 
extends AbstractPopup<Yield> {

	private static final YieldConstants CONSTANTS = GWT.create(YieldConstants.class);
//	private static final YieldBundle BUNDLE = YieldBundle.INSTANCE;
//	private static final YieldCssResource CSS = BUNDLE.css();
	
	private static GuardedRequest<Yield> yieldCreate;
	private static GuardedRequest<Yield> yieldUpdate;
	private static GuardedRequest<Yield> yieldDelete;

	private static ListField yieldnameField;
	private static DatespanField fromtodateField;
	private static DoubleunitField amountField;
	private static ListField dayofweekField;
	private static IntegerField gapfillerField;
	private static IntegerField earlybirdField;
	private static IntegerField lastminuteField;
	private static IntegerField lengthofstayField;
	private static IntegerField occupancyaboveField;
	private static IntegerField occupancybelowField;
	private static DatespanField weekendField;
	private static String id;
	private static String productid;
	private static Yield yield;
	private static TableField<Yield> parentField;

	private boolean noId() {return id == null || id.isEmpty();}
	private boolean noProductid() {return productid == null || productid.isEmpty();}
	
	private boolean overlaps() {
		if (parentField == null) {return false;}
		for (Yield yield : parentField.getValue()) {
			if (YieldPopup.yield != yield && yieldnameField.hasValue(yield.getName()) && yield.hasParam(getValue().getParam()) && !fromtodateField.getTovalue().before(yield.getFromdate()) && !fromtodateField.getValue().after(yield.getTodate())) {return true;}
		}
		return false;
	}
	
	/** Instantiates a new yield management rule pop up panel. */
	public YieldPopup() {
		super(true);
		createActions();
		setWidget(createContent());
		setStylePrimaryName(AbstractField.CSS.cbtAbstractPopup());
	}

	private static YieldPopup yieldPopup;
	
	/**
	 * Gets the single instance of YieldPopup.
	 *
	 * @return single instance of YieldPopup
	 */
	public static synchronized YieldPopup getInstance() {
		if (yieldPopup == null) {yieldPopup = new YieldPopup();}
		YieldPopup.productid = null;
		YieldPopup.yield = null;
		YieldPopup.parentField = null;
		return yieldPopup;
	}
	
	/**
	 * Shows the panel for the specified yield management rule.
	 *
	 * @param yield the specified yield management rule.
	 * @param parentField the field that invoked the pop up panel.
	 */
	public void show(Yield yield, TableField<Yield> parentField) {
		YieldPopup.yield = yield;
		YieldPopup.parentField = parentField;
		setValue(yield);
	}

	/**
	 * Shows the panel for the specified product.
	 *
	 * @param productid the ID of the specified product.
	 * @param parentField the field that invoked the pop up panel.
	 */
	public void show(String productid, TableField<Yield> parentField) {
		YieldPopup.productid = productid;
		YieldPopup.parentField = parentField;
		onReset(Yield.INITIAL);
		yieldCreate.execute(true);
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#onChange(com.google.gwt.event.dom.client.ChangeEvent)
	 */
	@Override
	public void onChange(ChangeEvent change) {
		if (yieldnameField.sent(change)) {setView();}
		ifMessage(overlaps(), Level.TERSE, CONSTANTS.overlapsError(), fromtodateField);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue()
	 */
	@Override
	public Yield getValue() {return getValue(new Yield());}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#getValue(java.lang.Object)
	 */
	public Yield getValue(Yield yield) {
		yield.setState(state);
		yield.setId(id);
		yield.setEntitytype(NameId.Type.Product.name());
		yield.setEntityid(productid);
		yield.setName(yieldnameField.getValue());
		yield.setAmount(amountField.getValue());
		yield.setModifier(amountField.getUnitvalue());
		yield.setFromdate(Time.getDateServer(fromtodateField.getValue()));
		yield.setTodate(Time.getDateServer(fromtodateField.getTovalue()));

		if (yield.hasName(Yield.DATE_RANGE)) {yield.setParam(Time.getDay(fromtodateField.getValue()));}
		else if (yield.hasName(Yield.DAY_OF_WEEK)) {yield.setParam(Integer.valueOf(dayofweekField.getValue()));}
		else if (yield.hasName(Yield.GAP_FILLER)) {yield.setParam(gapfillerField.getValue());}
		else if (yield.hasName(Yield.EARLY_BIRD)) {yield.setParam(earlybirdField.getValue());}
		else if (yield.hasName(Yield.LAST_MINUTE)) {yield.setParam(lastminuteField.getValue());}
		else if (yield.hasName(Yield.LENGTH_OF_STAY)) {yield.setParam(lengthofstayField.getValue());}
		else if (yield.hasName(Yield.OCCUPANCY_ABOVE)) {yield.setParam(occupancyaboveField.getValue());}
		else if (yield.hasName(Yield.OCCUPANCY_BELOW)) {yield.setParam(occupancybelowField.getValue());}
		else if (yield.hasName(Yield.WEEKEND)) {yield.setParam(Time.getDay(weekendField.getValue()));}
		Log.debug("getValue " + yield);
		return yield;
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.panel.AbstractPopup#setValue(java.lang.Object)
	 */
	public void setValue(Yield yield) {
		Log.debug("setValue " + yield);
		if (yield == null) {return;}
		else {
			setResetting(true);
			onStateChange(state);
			id = yield.getId();
			productid = yield.getEntityid();
			yieldnameField.setValue(yield.getName());
			amountField.setValue(yield.getAmount());
			amountField.setUnitvalue(yield.getModifier());
			fromtodateField.setValue(Time.getDateClient(yield.getFromdate()));
			fromtodateField.setTovalue(Time.getDateClient(yield.getTodate()));
			
			if (yield.hasName(Yield.DAY_OF_WEEK)) {dayofweekField.setValue(String.valueOf(yield.getParam()));}
			else if (yield.hasName(Yield.GAP_FILLER)) {gapfillerField.setValue(yield.getParam());}
			else if (yield.hasName(Yield.EARLY_BIRD)) {earlybirdField.setValue(yield.getParam());}
			else if (yield.hasName(Yield.LAST_MINUTE)) {lastminuteField.setValue(yield.getParam());}
			else if (yield.hasName(Yield.LENGTH_OF_STAY)) {lengthofstayField.setValue(yield.getParam());}
			else if (yield.hasName(Yield.OCCUPANCY_ABOVE)) {occupancyaboveField.setValue(yield.getParam());}
			else if (yield.hasName(Yield.OCCUPANCY_BELOW)) {occupancybelowField.setValue(yield.getParam());}
//			else if (yield.hasName(Yield.WEEKEND)) {weekendField.setValue(String.valueOf(yield.getParam()));}
			setView();
			setResetting(false);
		}
	}

	/* Sets which fields are visible depending on the yield management rule. */
	private void setView() {
		//fromtodateField.setVisible(yieldnameField.hasValue(Yield.DATE_RANGE));
		dayofweekField.setVisible(yieldnameField.hasValue(Yield.DAY_OF_WEEK));
		gapfillerField.setVisible(yieldnameField.hasValue(Yield.GAP_FILLER));
		earlybirdField.setVisible(yieldnameField.hasValue(Yield.EARLY_BIRD));
		lastminuteField.setVisible(yieldnameField.hasValue(Yield.LAST_MINUTE));
		lengthofstayField.setVisible(yieldnameField.hasValue(Yield.LENGTH_OF_STAY));
		occupancyaboveField.setVisible(yieldnameField.hasValue(Yield.OCCUPANCY_ABOVE));
		occupancybelowField.setVisible(yieldnameField.hasValue(Yield.OCCUPANCY_BELOW));
		weekendField.setVisible(yieldnameField.hasValue(Yield.WEEKEND));
		center();
	}
	
	/*
	 * Creates the panel of yield management fields.
	 * 
	 * @return the panel of yield management fields.
	 */
	private VerticalPanel createContent() {
		final VerticalPanel form = new VerticalPanel();
		final Label title = new Label(CONSTANTS.yieldLabel());
		title.addStyleName(AbstractField.CSS.cbtAbstractPopupLabel());
		form.add(title);
		
		final Label closeButton = new Label();
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				YieldPopup.this.hide();
			}
		});
		closeButton.addStyleName(AbstractField.CSS.cbtAbstractPopupClose());
		form.add(closeButton);

		//-----------------------------------------------
		// Yield Rule field
		//-----------------------------------------------
		yieldnameField = new ListField(this, null,
				NameId.getList(CONSTANTS.yieldRules(), Yield.RULES),
				CONSTANTS.yieldnameLabel(),
				false,
				tab++);
		yieldnameField.setHelpText(CONSTANTS.yieldnameHelp());
		form.add(yieldnameField);

		//-----------------------------------------------
		// Yield Date Range
		//-----------------------------------------------
		fromtodateField = new DatespanField(this, null,
				CONSTANTS.fromtodateLabel(),
				tab++);
		fromtodateField.setDefaultDuration(365.0);
		fromtodateField.setHelpText(CONSTANTS.fromtodateHelp());
		form.add(fromtodateField);

		//-----------------------------------------------
		// Day of Week Parameter field
		//-----------------------------------------------
		dayofweekField = new ListField(this, null,
				NameId.getList(CONSTANTS.yieldDays(), Yield.DAYS),
				CONSTANTS.dayofweekLabel(),
				false,
				tab++);
		dayofweekField.setFieldHalf();
		dayofweekField.setHelpText(CONSTANTS.dayofweekHelp());
		form.add(dayofweekField);

		//-----------------------------------------------
		// Fill Gap Nights field
		//-----------------------------------------------
		gapfillerField = new IntegerField(this, null,
				CONSTANTS.gapfillerLabel(),
				tab++);
		gapfillerField.setRange(1, 100);
		gapfillerField.setHelpText(CONSTANTS.gapfillerHelp());
		form.add(gapfillerField);

		//-----------------------------------------------
		// Early Bird Days field
		//-----------------------------------------------
		earlybirdField = new IntegerField(this, null,
				CONSTANTS.earlybirdLabel(),
				tab++);
		earlybirdField.setRange(1, 1000);
		earlybirdField.setHelpText(CONSTANTS.earlybirdHelp());
		form.add(earlybirdField);

		//-----------------------------------------------
		// Last Minute Days field
		//-----------------------------------------------
		lastminuteField = new IntegerField(this, null,
				CONSTANTS.lastminuteLabel(),
				tab++);
		lastminuteField.setRange(1, 100);
		lastminuteField.setHelpText(CONSTANTS.lastminuteHelp());
		form.add(lastminuteField);

		//-----------------------------------------------
		// Length of Stay field
		//-----------------------------------------------
		lengthofstayField = new IntegerField(this, null,
				CONSTANTS.lengthofstayLabel(),
				tab++);
		lengthofstayField.setRange(1, 100);
		lengthofstayField.setHelpText(CONSTANTS.lengthofstayHelp());
		form.add(lengthofstayField);

		//-----------------------------------------------
		// Occupancy Above Percent field
		//-----------------------------------------------
		occupancyaboveField = new IntegerField(this, null,
				CONSTANTS.occupancyaboveLabel(),
				tab++);
		occupancyaboveField.setRange(1, 100);
		occupancyaboveField.setHelpText(CONSTANTS.occupancyaboveHelp());
		form.add(occupancyaboveField);

		//-----------------------------------------------
		// Occupancy Below Percent field
		//-----------------------------------------------
		occupancybelowField = new IntegerField(this, null,
				CONSTANTS.occupancybelowLabel(),
				tab++);
		occupancybelowField.setRange(1, 100);
		occupancybelowField.setHelpText(CONSTANTS.occupancybelowHelp());
		form.add(occupancybelowField);

		//-----------------------------------------------
		// Yield Amount field
		//-----------------------------------------------
		amountField = new DoubleunitField(this, null,
				NameId.getList(CONSTANTS.yieldModifiers(), Yield.MODIFIERS),
				CONSTANTS.amountLabel(),
				AbstractField.QF,
				tab++);
		amountField.setRange(1.0, 10000.0);
		amountField.setDefaultUnitvalue(Yield.DECREASE_PERCENT);
		amountField.setHelpText(CONSTANTS.amountHelp());
		form.add(amountField);
		
		//-----------------------------------------------
		// Yield Weekend
		//-----------------------------------------------
		weekendField = new DatespanField(this, null, 
				CONSTANTS.fromtodateLabel(),
				tab++);
		weekendField.setDefaultDuration(60.0);
		weekendField.setHelpText(CONSTANTS.weekendHelp());
		form.add(weekendField);

		form.add(createCommands());

		onRefresh();
		onReset(Yield.CREATED);
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

		//-----------------------------------------------
		// Save button
		//-----------------------------------------------
		final CommandButton saveButton = new CommandButton(this, AbstractField.CONSTANTS.allSave(), yieldUpdate, tab++);
		saveButton.addStyleName(AbstractField.CSS.cbtGradientBlue());
		saveButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		saveButton.setTitle(CONSTANTS.saveHelp());
		bar.add(saveButton);
		
		//-----------------------------------------------
		// Delete button
		//-----------------------------------------------
		final CommandButton deleteButton = new CommandButton(this, AbstractField.CONSTANTS.allDelete(), yieldDelete, tab++);
		deleteButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		deleteButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		deleteButton.setTitle(CONSTANTS.deleteHelp());
		bar.add(deleteButton);
		
		//-----------------------------------------------
		// Cancel button
		//-----------------------------------------------
		final CommandButton cancelButton = new CommandButton(this, AbstractField.CONSTANTS.allCancel(), cancelRequest, tab++);
		cancelButton.addStyleName(AbstractField.CSS.cbtGradientRed());
		cancelButton.addStyleName(AbstractField.CSS.cbtCommandButtonTwo());
		cancelButton.setTitle(AbstractField.CONSTANTS.helpCancel());
		bar.add(cancelButton);

		//-----------------------------------------------
		// Transition array to define the finite state machine.
		//-----------------------------------------------
		fsm = new ArrayList<Transition>();
		fsm.add(new Transition(Yield.INITIAL, cancelButton, Yield.CREATED));
		fsm.add(new Transition(Yield.INITIAL, saveButton, Yield.CREATED));
		
		fsm.add(new Transition(Yield.CREATED, saveButton, Yield.CREATED));
		fsm.add(new Transition(Yield.CREATED, deleteButton, Yield.CREATED));
		return bar;
	}

	/* Creates the actions that may be executed. */
	private void createActions() {

		//-----------------------------------------------
		// Create Yield Management Rule
		//-----------------------------------------------
		yieldCreate = new GuardedRequest<Yield>() {
			protected boolean error() {return noProductid();}
			protected void send() {super.send(getValue(new YieldCreate()));}
			protected void receive(Yield yield) {setValue(yield);}
		};

		//-----------------------------------------------
		// Create Yield Management Rule
		//-----------------------------------------------
		yieldUpdate = new GuardedRequest<Yield>() {
			protected boolean error() {
				return (
						ifMessage(noProductid(), Level.TERSE, AbstractField.CONSTANTS.errId(), yieldnameField)
						|| ifMessage(yieldnameField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errId(), yieldnameField)
						|| ifMessage(fromtodateField.noValue(), Level.TERSE, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.noTovalue(), Level.TERSE, AbstractField.CONSTANTS.errDate(), fromtodateField)
						|| ifMessage(fromtodateField.isEndBeforeStart(), Level.TERSE, AbstractField.CONSTANTS.errDateEndBeforeStart(), fromtodateField)
						|| ifMessage(overlaps(), Level.TERSE, CONSTANTS.overlapsError(), fromtodateField)
				);
			}
			protected void send() {super.send(getValue(new YieldUpdate()));}
			protected void receive(Yield yield) {
				if (parentField!= null) {parentField.execute(true);}
				hide();
			}
		};

		//-----------------------------------------------
		// Create Yield Management Rule
		//-----------------------------------------------
		yieldDelete = new GuardedRequest<Yield>() {
			protected String popup() {return AbstractField.CONSTANTS.errDeleteOK();}
			protected boolean error() {return ifMessage(noId(), Level.TERSE, AbstractField.CONSTANTS.errId(), yieldnameField);}
			protected void send() {super.send(getValue(new YieldDelete()));}
			protected void receive(Yield yield) {
				if (parentField!= null) {parentField.execute(true);}
				hide();
			}
		};
	}
}
