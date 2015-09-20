/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;
import java.util.ArrayList;

import net.cbtltd.client.HasComponents;
import net.cbtltd.client.NameIdRequest;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Table;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class SuggestField is to display and select name ID pairs from a suggested list.
 */
public class SuggestField
extends AbstractField<String>
implements SelectionHandler<Suggestion> {

	private final SuggestRequest nameidRequest = new SuggestRequest();

	private FlowPanel panel = new FlowPanel();
	private Label label;
	private SuggestBox suggestBox;
	private TextBox textBox = new TextBox();
	private Widget button;
	private PopupPanel doubleclickPopup;
	private NameId nameid;
	private int limit = Integer.MAX_VALUE;
	
	/** The horizontal panel. */
	protected final HorizontalPanel horizontalPanel = new HorizontalPanel();
	
	/** The oracle. */
	protected NameSuggestOracle oracle;
	
	/** The default name. */
	protected String defaultName = Model.BLANK;
	
	/**
	 * Instantiates a new suggest field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility of the field.
	 * @param label is the optional text to identify the field.  
	 * @param limit to the maximum number of suggestions to be displayed.
	 * @param tab index of the field.
	 */
	public SuggestField (
			HasComponents form,
			short[] permission,
			String label,
			int limit,
			int tab) {
		this(form, permission, null, label, limit, tab);
	}

	/**
	 * Instantiates a new suggest field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility of the field.
	 * @param action to find the suggestions.
	 * @param label is the optional text to identify the field.  
	 * @param limit to the maximum number of suggestions to be displayed.
	 * @param tab index of the field.
	 */
	public SuggestField (
			HasComponents form,
			short[] permission,
			NameIdAction action,
			String label,
			int limit,
			int tab) {

		oracle = new NameSuggestOracle();
		suggestBox = new SuggestBox(oracle);
		initialize(panel, form, permission, CSS.cbtSuggestField());

		if (label != null) {
			this.label = new Label(label);
			this.label.addStyleName(CSS.cbtSuggestFieldLabel());
			this.label.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {showHelp();}
			});
			panel.add(this.label);
		}

		this.limit = limit;
		nameidRequest.setAction(action); // = new SuggestRequest(action);
		nameidRequest.getAction().setNumrows(limit);

		suggestBox.setTabIndex(tab);
		suggestBox.setLimit(limit);
		suggestBox.addSelectionHandler(this);
		suggestBox.addStyleName(CSS.cbtSuggestFieldField());
		horizontalPanel.add(suggestBox);

		textBox.setTabIndex(tab);
		textBox.setVisible(false);
		textBox.addChangeHandler(form);
		textBox.addStyleName(CSS.cbtSuggestFieldField());
		horizontalPanel.add(textBox);
		panel.add(horizontalPanel);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#onDoubleclick(com.google.gwt.user.client.Event)
	 */
	@Override
	protected void onDoubleclick(Event event) {
		if (doubleclickPopup == null || suggestBox.getText() == null || suggestBox.getText().trim().isEmpty()){return;}
		Grid grid = (Grid)doubleclickPopup.getWidget();
		((TextBox)grid.getWidget(0, 1)).setText(suggestBox.getText());
		doubleclickPopup.setPopupPosition(event.getClientX(), event.getClientY());
		doubleclickPopup.show();
	}

	/**
	 * Sets if the field may be the double clicked to change the name of its name ID pair.
	 *
	 * @param doubleclickable is if the field may be the double clicked to change the name of its name ID pair.
	 */
	public void setDoubleclickable(boolean doubleclickable) {
		if (doubleclickable && doubleclickPopup == null){
			doubleclickPopup = new PopupPanel(false, true);
			doubleclickPopup.setStylePrimaryName(CSS.cbtSuggestFieldPopup());
			Grid grid = new Grid(1,4);
			doubleclickPopup.add(grid);
			
			grid.setWidget(0, 0, new Label(CONSTANTS.allChangeTo()));
			
			grid.setWidget(0, 1, new TextBox());
//			grid.getWidget(0, 1).addStyleName(CSS.cbtPopupFieldText());

			grid.setWidget(0, 2, new Button(CONSTANTS.allOk(), new ClickHandler() {
				@Override
				public void onClick(ClickEvent event){
					String text = ((TextBox)((Grid)doubleclickPopup.getWidget()).getWidget(0, 1)).getText();
					if (text != null && !text.trim().isEmpty() && !text.equals(suggestBox.getText())) {
						setNameId(new NameId(text, getValue()));
					}
					doubleclickPopup.hide();
				}
			}));
			grid.getWidget(0, 2).addStyleName(CSS.cbtSuggestFieldPopupButton());
			
			grid.setWidget(0, 3, new Button(CONSTANTS.allCancel(), new ClickHandler() {
				@Override
				public void onClick(ClickEvent event){
					doubleclickPopup.hide();
				}
			}));
			grid.getWidget(0, 3).addStyleName(CSS.cbtSuggestFieldPopupButton());
		}
		else if (!doubleclickable){doubleclickPopup = null;}
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.logical.shared.SelectionHandler#onSelection(com.google.gwt.event.logical.shared.SelectionEvent)
	 */
	@Override
	public void onSelection(SelectionEvent<Suggestion> event) {
		SuggestResponse selected = (SuggestResponse) event.getSelectedItem();
		setNameId(selected.getNameId());
		fireChange(this);
	};

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#onReset()
	 */
	@Override
	public void onReset() {
		super.onReset();
		setFields(defaultName);
	}

	/**
	 * Adds the button.
	 *
	 * @param button the button
	 */
	public void addButton(Widget button) {
		horizontalPanel.add(button);
		setFieldStyle(CSS.cbtSuggestFieldSecure());
	}
	
	/**
	 * Sets the button style.
	 *
	 * @param style the new button style
	 */
	public void setButtonStyle(String style) {
		if (button != null) {button.addStyleName(style);}
	}

	/**
	 * Sets the default name of the name ID pair.
	 *
	 * @param defaultName the new default name.
	 */
	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
		onReset();
	}

	/**
	 * Sets the label.
	 *
	 * @param text the new label
	 */
	public void setLabel(String text) {
		if (label != null) {label.setText(text);}
	}

	/**
	 * Sets the CSS style of the list.
	 *
	 * @param style the CSS style of the list.
	 */
	public void setFieldStyle(String style) {
		suggestBox.addStyleName(style);
		textBox.addStyleName(style);
	}

	/**
	 * Sets the CSS style of the label.
	 *
	 * @param style  the CSS style of the label.
	 */
	public void setLabelStyle(String style) {
		if (label != null) {label.addStyleName(style);}
	}

	/** Sets the half width CSS style of the list. */
	public void setFieldHalf(){
		setFieldStyle(CSS.cbtSuggestFieldHalf());
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setSecure(boolean)
	 */
	@Override
	public void setSecure(boolean secure) {
		super.setSecure(secure);
		setFieldStyle(CSS.cbtSuggestFieldSecure());
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		suggestBox.setVisible(isEnabled());
		textBox.setVisible(isVisible() && !isEnabled());
		textBox.setEnabled(isEnabled());
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setFocus(boolean)
	 */
	@Override
	public void setFocus(boolean mode){
		suggestBox.setFocus(mode);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setTabIndex(int)
	 */
	@Override
	public void setTabIndex (int index){
		suggestBox.setTabIndex(index);
	}

	/**
	 * Sets the state of the action to suggest name ID pairs.
	 *
	 * @param state the new state.
	 */
	public void setState(String state){
		nameidRequest.getAction().setState(state);
	}

	/**
	 * Sets the type of the action to suggest name ID pairs.
	 *
	 * @param type the new type.
	 */
	public void setType(String type){
		nameidRequest.getAction().setType(type);
	}

	/**
	 * Sets the IDs of the allowed name ID pairs.
	 *
	 * @param ids the new IDs.
	 */
	public void setIds(ArrayList<String> ids) {
		nameidRequest.getAction().setIds(ids);
	}

	/**
	 * Sets that values for all organizations are to be included in the suggestions.
	 * If this is not set the suggested list is populated with values only for the current organization.
	 *
	 * @param allOrganizations is true if the list contains values for all organizations.
	 */
	public void setAllOrganizations(boolean allOrganizations) {
		nameidRequest.setAllOrganizations(allOrganizations);
	}

	/**
	 * Sets that values for the organization are to be included in the suggestions.
	 * If this is not set the suggested list is populated with values only for the current organization.
	 *
	 * @param allOrganizations is true if the list contains values for all organizations.
	 */
	public void setOrganizationid(String organizationid) {
		nameidRequest.getAction().setOrganizationid(organizationid);
	}

	/**
	 * Sets the action to suggest name ID pairs.
	 *
	 * @param action the new action.
	 */
	public void setAction(NameIdAction action) {
		if (action == null) {return;}
		action.setNumrows(limit);
		nameidRequest.setAction(action);
	}

	/**
	 * Gets the service of the action to suggest name ID pairs.
	 *
	 * @return the action service.
	 */
	public Service getActionService() {
		return nameidRequest.getAction().service();
	}

	/**
	 * Sets the current field values.
	 *
	 * @param value the new field value.
	 */
	public void setFields(String value) {
		suggestBox.setText(value);
		textBox.setText(value);
	}

	/**
	 * Sets the current name ID pair.
	 *
	 * @param nameid the new name ID pair.
	 */
	public void setNameId(NameId nameid) {
		this.nameid = nameid;
		if (nameid != null) {setFields(nameid.getName());}
	}

	/**
	 * Gets the current name ID pair.
	 *
	 * @return the current name ID pair.
	 */
	public NameId getNameId() {
		return nameid;
	}

	/**
	 * Gets the current name.
	 *
	 * @return the name.
	 */
	public String getName () {
		return suggestBox.getText().trim();
	}

	/**
	 * Checks if the name does not exist.
	 *
	 * @return true, if the name does not exist.
	 */
	public boolean noName() {
		return getName() == null || getName().isEmpty();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setValue(java.lang.Object)
	 */
	public void setValue(String value) {
		if (value == null || value.isEmpty() || Model.ZERO.equalsIgnoreCase(value)) {setFields(defaultName == null ? Model.BLANK : defaultName);}
		else {nameidRequest.sendId(value);}
		nameid = new NameId(value);
		super.setChanged();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#getValue()
	 */
	public String getValue () {
		return nameid == null || nameid.noId() || nameid.hasName(defaultName) ? defaultValue : nameid.getId();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#noValue()
	 */
//	public boolean noValue() {
//		return getValue() == null;
//	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#hasValue()
	 */
	public boolean hasValue() {
		return !noValue();
	}

	/**
	 * Checks if the field has the specified value.
	 *
	 * @param value the specified value.
	 * @return true, if the field has the specified value.
	 */
	public boolean hasValue(String value) {
		return getValue() == null ? value == null : nameid.hasId(value);
	}

	/**
	 * Checks if the field does not have the specified value.
	 *
	 * @param value the specified value.
	 * @return true, if the field does not have the specified value.
	 */
	public boolean notValue(String value) {
		return !hasValue(value);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#is(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean is(Widget sender){
		return (sender == this);
	}

	/* Inner Class SuggestRequest is to make suggestions. */
	private class SuggestRequest
		extends NameIdRequest {
		
		public SuggestRequest() {}
		
		public void sendId(String id) {
			getAction().setId(id);
			getAction().setSuggested(true);
			execute();
		}
		
		public void sendName(String name)	{
			if (name == null || name.isEmpty()) {setFields(defaultName);}
			else {
				getAction().setName(name);
				getAction().setSuggested(false);
				execute();
			}
		}
		
		public void receive(Table<NameId> suggestion) {
			if (suggestion == null || noAction()) {Window.alert("SuggestField noAction " + ((label == null) ? "null" : label.getText()));}
			else if (getAction().isSuggested()) {setNameId(suggestion.getFirstValue());}
			else {oracle.receive(suggestion);}
			SuggestField.super.setChanged();
		}
	}
	
	/** Inner Class to create an asynchronous call to return suggested names based on the current query. */
	public class NameSuggestOracle
	extends SuggestOracle {
		private SuggestOracle.Request suggestOracleRequest;
		private SuggestOracle.Callback callback;

		/**
		 * Instantiates a new name suggest oracle.
		 */
		public NameSuggestOracle(){}

		/* (non-Javadoc)
		 * @see com.google.gwt.user.client.ui.SuggestOracle#isDisplayStringHTML()
		 */
		@Override
		public boolean isDisplayStringHTML() { return true; }

		/* (non-Javadoc)
		 * @see com.google.gwt.user.client.ui.SuggestOracle#requestSuggestions(com.google.gwt.user.client.ui.SuggestOracle.Request, com.google.gwt.user.client.ui.SuggestOracle.Callback)
		 */
		@Override
		public void requestSuggestions(SuggestOracle.Request suggestOracleRequest, SuggestOracle.Callback callback) {
			if ((suggestOracleRequest == null 
					|| suggestOracleRequest.getQuery() == null 
					|| suggestOracleRequest.getQuery().trim().isEmpty())
					&& 
					(defaultName != null
							&& !defaultName.isEmpty())
				) {setNameId(new NameId(defaultName, null));}
			else {
				this.suggestOracleRequest = suggestOracleRequest;
				this.callback = callback;
				nameidRequest.sendName(suggestOracleRequest.getQuery());
			}
		}

		/**
		 * Receives the suggested list of name ID pairs.
		 *
		 * @param suggestion the suggested list of name ID pairs.
		 */
		public void receive(Table<NameId> suggestion) {
			if (suggestion == null || suggestion.noValue()) {return;}
			SuggestOracle.Response response = new SuggestOracle.Response();
			ArrayList<NameId> nameids = suggestion.getValue();
			ArrayList<SuggestOracle.Suggestion> suggestions = new ArrayList<SuggestOracle.Suggestion>(nameids.size());
			for (NameId nameid : nameids){suggestions.add(new SuggestResponse(nameid.getName(), nameid.getId()));}
			response.setSuggestions(suggestions);
			if (callback == null || suggestOracleRequest == null) Window.alert("SuggestField NameSuggestOracle " + callback + " " + suggestOracleRequest);
			else callback.onSuggestionsReady(suggestOracleRequest, response);
		}
	}
}
