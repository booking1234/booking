/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.cbtltd.client.HasComponents;
import net.cbtltd.client.NameIdRequest;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.Table;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/** The Class ListField is to display and select from a list of optional values. */
public class ListField
extends AbstractField<String> {

	private NameIdRequest nameidRequest;

	private final FlowPanel panel = new FlowPanel();
	private Label label;
	private final HorizontalPanel horizontalPanel = new HorizontalPanel();
	private final ListBox field = new ListBox();
	private Widget button;
	private PopupPanel doubleclickPopup;
	private String blankLabel = Model.BLANK;
	private boolean addBlankItem;
	private boolean sorted = true;
	private static final int MAX_ITEMS = 500;

	/**
	 * Instantiates a new list field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility of the field.
	 * @param items to populate the list field.
	 * @param label is the optional text to identify the field.  
	 * @param blankLabel the text to be displayed for the optional zero value option.
	 * @param tab index of the field.
	 */
	public ListField(
			HasComponents form,
			short[] permission,
			List<NameId> items,
			String label,
			String blankLabel,
			int tab) {
		
		this(form, permission, label, true, blankLabel, tab);

		this.blankLabel = blankLabel;
		
		if (addBlankItem) {field.addItem(blankLabel, Model.ZERO);}
		if (items != null) {
			if (sorted) {Collections.sort(items);}
			for (NameId item : items) {field.addItem(item.getName(), item.getId());}
		}
	}

	/**
	 * Instantiates a new list field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility of the field.
	 * @param items to populate the list field.
	 * @param label is the optional text to identify the field.  
	 * @param addBlankItem the flag to indicate it a blank item is to be added to the list.
	 * @param tab index of the field.
	 */
	public ListField(
			HasComponents form,
			short[] permission,
			List<NameId> items,
			String label,
			boolean addBlankItem,
			int tab) {
		
		this(form, permission, label, addBlankItem, Model.BLANK, tab);
		
		if (addBlankItem) {field.addItem(blankLabel, Model.ZERO);}
		if (items != null && !items.isEmpty()) {
			if (sorted) {Collections.sort(items);}
			for (NameId item : items) {field.addItem(item.getName(), item.getId());}
		}
	}

	/**
	 * Instantiates a new list field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility of the field.
	 * @param action to populate the list field with a request to a NameId service.
	 * @param label is the optional text to identify the field.  
	 * @param addBlankItem the flag to indicate it a blank item is to be added to the list.
	 * @param tab index of the field.
	 */
	public ListField (
			HasComponents form,
			short[] permission,
			NameIdAction action,
			String label,
			boolean addBlankItem,
			int tab) {
		this(form, permission, label, addBlankItem, Model.BLANK, tab);
		
		nameidRequest = new NameIdRequest(action) {
			public void receive(Table<NameId> response) {
				if (response != null && response.hasValue()) {
					setItems(response.getValue());
					onReset();
				}
			}
		};
		nameidRequest.getAction().setNumrows(MAX_ITEMS);
	}

	/**
	 * Instantiates a new list field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility of the field.
	 * @param action to populate the list field with a request to a NameId service.
	 * @param label is the optional text to identify the field.  
	 * @param blankLabel the text to be displayed for the optional zero value option.
	 * @param tab index of the field.
	 */
	public ListField (
			HasComponents form,
			short[] permission,
			NameIdAction action,
			String label,
			String blankLabel,
			int tab) {
		this(form, permission, label, true, blankLabel, tab);
		
		this.blankLabel = blankLabel;
		nameidRequest = new NameIdRequest(action) {
			public void receive(Table<NameId> response) {
				if (response != null && response.hasValue()) {
					setItems(response.getValue());
					onReset();
				}
			}
		};
		nameidRequest.getAction().setNumrows(MAX_ITEMS);
	}

	/**
	 * Instantiates a new list field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility of the field.
	 * @param label is the optional text to identify the field.  
	 * @param addBlankItem the flag to indicate it a blank item is to be added to the list.
	 * @param tab index of the field.
	 */
	public ListField (
			HasComponents form,
			short[] permission,
			String label,
			boolean addBlankItem,
			int tab) {
		this(form, permission, label, addBlankItem, Model.BLANK, tab);
	}

	/*
	 * Instantiates a new list field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility of the field.
	 * @param action to populate the list field with a request to a NameId service.
	 * @param label is the optional text to identify the field.  
	 * @param addBlankItem the flag to indicate it a blank item is to be added to the list.
	 * @param blankLabel the text to be displayed for the optional zero value option.
	 * @param tab index of the field.
	 */
	private ListField (
			HasComponents form,
			short[] permission,
			String label,
			boolean addBlankItem,
			String blankLabel,
			int tab) {
		initialize(panel, form, permission, CSS.cbtListField());

		if (label != null){
			this.label  = new Label(label);
			this.label.addStyleName(CSS.cbtListFieldLabel());
			this.label.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {showHelp();}
			});
			panel.add(this.label);
		}

		this.addBlankItem = addBlankItem;

		field.addStyleName(CSS.cbtListFieldField());
		field.setTabIndex(tab);
		field.addChangeHandler(form);
		horizontalPanel.add(field);
		horizontalPanel.add(lock);
		panel.add(horizontalPanel);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#is(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean is(Widget sender){
		return (sender == field);
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#onDoubleclick(com.google.gwt.user.client.Event)
	 */
	@Override
	protected void onDoubleclick(Event event) {
		if (doubleclickPopup == null || getName() == null || noValue()){return;}
		Grid grid = (Grid)doubleclickPopup.getWidget();
		((TextBox)grid.getWidget(0, 1)).setText(getName());
		doubleclickPopup.setPopupPosition(event.getClientX(), event.getClientY());
		doubleclickPopup.show();
	}

	/**
	 * Sets if the field may be the double clicked to change the name of its name ID pair.
	 *
	 * @param doubleclickable is if the field may be the double clicked to change the name of its name ID pair.
	 */
	public void setDoubleclickable(boolean doubleclickable) {
		if (doubleclickable && doubleclickPopup == null) {
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
					if (text != null && !text.trim().isEmpty() && !text.equals(getName())) {
						setName(text, getValue());
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

	/**
	 * Refreshes the action to populate the list field with a request to a NameId service.
	 */
	@Override
	public void onRefresh() {
		if (nameidRequest != null) {nameidRequest.execute();}
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
	 * Gets the service of the action to list name ID pairs.
	 *
	 * @return the action service.
	 */
	public Service getActionService(){
		return nameidRequest == null ? null :nameidRequest.getAction().service();
	}

	/**
	 * Sets the list items.
	 *
	 * @param items to populate the list.
	 */
	public void setItems(List<NameId> items) {
		field.clear();
		if (addBlankItem) {field.addItem(blankLabel, Model.ZERO);}
		if (items != null) {
			for (NameId item : items) {
				if (item.getName() != null) {
					field.addItem(item.getName(), item.getId());
				}
			}
		}
		onReset();
		setEnabled(field.getItemCount() > 1);
	}

	/**
	 * Gets an array of the list values.
	 *
	 * @return the list value array.
	 */
	public String[] getItemArray() {
		String[] items = new String[field.getItemCount()];
		for (int index = 0; index < field.getItemCount(); index++) {
			items[index] = field.getValue(index);
		}
		return items;
	}
	
	/**
	 * Sets the action to populate the list field with a request to a NameId service.
	 * Note that the action is not executed unless the onRefresh() method is invoked.
	 *
	 * @param action the NameId action.
	 */
	public void setAction(NameIdAction action) {
		if (nameidRequest != null) {nameidRequest.execute(action);}
	}

	/**
	 * Sets that values for all organizations are to be included in the list.
	 * If this is not set the list is populated with values only for the current organization.
	 *
	 * @param allOrganizations is true if the list contains values for all organizations.
	 */
	public void setAllOrganizations(boolean allOrganizations) {
		if (nameidRequest != null) {nameidRequest.setAllOrganizations(allOrganizations);}
	}

	/**
	 * Sets if the list is to be sorted into alphanumeric order.
	 *
	 * @param sorted is true if the list is to be sorted into alphanumeric order.
	 */
	public void setSorted(boolean sorted) {
		this.sorted = sorted;
	}

	/**
	 * Sets that only values of the specified type are to be included in the list.
	 * This is typically used as a filter applied in a NameId service.
	 *
	 * @param type the specified type.
	 */
	public void setType(String type) {
		if (nameidRequest != null) {nameidRequest.getAction().setType(type);}
	}
	
	/**
	 * Sets the IDs of the allowed name ID pairs.
	 *
	 * @param ids the new IDs.
	 */
	public void setIds(ArrayList<String> ids) {
		if (ids == null || ids.isEmpty()) {return;}
		else {nameidRequest.getAction().setIds(ids);}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		field.setEnabled(isEnabled());
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
		field.addStyleName(style);
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
		setFieldStyle(CSS.cbtListFieldHalf());
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setSecure(boolean)
	 */
	@Override
	public void setSecure(boolean secure) {
		super.setSecure(secure);
		setFieldStyle(CSS.cbtListFieldSecure());
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setFocus(boolean)
	 */
	@Override
	public void setFocus(boolean focussed){
		field.setFocus(focussed);
	}

	/**
	 * Sets the visible item count of the list.
	 *
	 * @param count the new visible item count.
	 */
	public void setVisibleItemCount(int count) {
		field.setVisibleItemCount(count);
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int tab){
		field.setTabIndex(tab);
	}

	/**
	 * Checks if the selected item has the specified text.
	 *
	 * @param text the specified text.
	 * @return true, if the selected item has the specified text.
	 */
	public boolean hasText(String text) {
		if (field == null || field.getItemCount() == 0) {return false;}
		return field.getItemText(field.getSelectedIndex()).equals(text);
	}

	/**
	 * Resets the text of a value in the list, or adds the value if it is not in the list.
	 *
	 * @param text the new text.
	 * @param value the existing or new value.
	 */
	public void setNameValue(String text, String value) {
		if (text == null || text.trim().isEmpty()) {return;}
		for (int i = 0; i < field.getItemCount(); i++) {
			if (field.getValue(i).equalsIgnoreCase(value)){
				field.setItemText(i, text);
				return;
			}
		}
		int index = field.getItemCount();
		field.addItem(text, value);
		field.setSelectedIndex(index);
	}

	/**
	 * Gets the text of the currently selected item in the list.
	 *
	 * @return selected item text.
	 */
	public String getName() {
		if (field == null || field.getItemCount() == 0) {return Model.BLANK;}
		return field.getItemText(field.getSelectedIndex());
	}

	/**
	 * Sets the selected index to the list item having the specified text.
	 *
	 * @param text the text to be selected.
	 */
	public void setName(String text, String value) {
		for (int i = 0; i < field.getItemCount(); i++) {
			if (field.getValue(i).equalsIgnoreCase(value)) {
				field.setItemText(i, text);
				field.setSelectedIndex(i);
				return;
			}
		}
		field.setSelectedIndex(0);
	}

	/**
	 * Sets the selected index to the list item having the specified text.
	 *
	 * @param text the text to be selected.
	 */
	public void setName(String text) {
		if (text == null || text.isEmpty() || text.equalsIgnoreCase(Model.ZERO)) {field.setSelectedIndex(0);}
		else {
			for (int i = 0; i < field.getItemCount(); i++){
				if (field.getItemText(i).equalsIgnoreCase(text)){
					field.setSelectedIndex(i);
					super.setChanged();
					return;
				}
			}
			field.setSelectedIndex(0);
		}
		super.setChanged();
	}

	/**
	 * Checks if the name does not exist.
	 *
	 * @return true, if the name does not exist.
	 */
	public boolean noName() {
		return getName() == null || getName().isEmpty();
	}

	/**
	 * Sets the selected value in the list.
	 *
	 * @param value the value to be selected.
	 */
	public void setValue(String value) {
		if (secure) {value = (value == null) ? null : Model.decrypt(value);}
		if (value == null || value.isEmpty() || value.equalsIgnoreCase(Model.ZERO)) {field.setSelectedIndex(0);}
		else {
			for (int i = 0; i < field.getItemCount(); i++) {
				if (field.getValue(i).equalsIgnoreCase(value)){
					field.setSelectedIndex(i);
					super.setChanged();
					return;
				}
			}
			int index = field.getItemCount();
			field.addItem(blankLabel, value);
			field.setSelectedIndex(index);
		}
		super.setChanged();
	}

	/**
	 * Gets the selected value from the list.
	 *
	 * @return selected the selected value.
	 */
	public String getValue() {
		if (field.getItemCount() == 0 || field.getSelectedIndex() < 0) {return Model.ZERO;}
		else if (secure) {return Model.encrypt(field.getValue(field.getSelectedIndex()));}
		else {return field.getValue(field.getSelectedIndex());}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#noValue()
	 */
	public boolean noValue() {
		return getValue() == null || field.getItemCount() == 0 || hasValue(Model.ZERO);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#hasValue()
	 */
	public boolean hasValue() {
		return !noValue();
	}

	/**
	 * Checks if the specified value is selected.
	 *
	 * @param value the specified value.
	 * @return true if the specified value is selected.
	 */
	public boolean hasValue(String value) {
		if (field.getItemCount() == 0) {return false;}
		else {return field.getValue(field.getSelectedIndex()).equals(value);}
	}
}
