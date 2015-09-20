/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.ArrayList;
import java.util.Collections;

import net.cbtltd.client.HasComponents;
import net.cbtltd.client.NameIdRequest;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Table;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class ShuttleField.
 */
public final class ShuttleField
extends AbstractField<ArrayList<String>>
implements ClickHandler {

	private NameIdRequest nameidRequest;

	private final VerticalPanel shuttle = new VerticalPanel();
	private final FlowPanel panel = new FlowPanel();
	private final Grid grid = new Grid(1,3);
	private final FlowPanel heading = new FlowPanel();
	private final VerticalPanel buttons = new VerticalPanel();
	private ShuttleBox available = new ShuttleBox(true);
	private ShuttleBox selected = new ShuttleBox(true);
	private Label label;
	private final Button select = new Button();
	private final Button remove = new Button();
	private final Button selectAll = new Button();
	private final Button removeAll = new Button();
	private static final int MAX_ITEMS = 500;
	private String[] disabled;
	private boolean preselected = false;

	/**
	 * Instantiates a new shuttle field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param action the NameId action to populate the list of available items from the server.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field.
	 */
	public ShuttleField(
			HasComponents form,
			short[] permission,
			NameIdAction action,
			String label,
			int tab) {
		this (form, permission, label, tab);

		nameidRequest = new NameIdRequest(action) {
			public void receive(Table<NameId> response) {
				if (response != null && response.hasValue()) {
					onReset();
					setAvailable(response.getValue());
				}
			}
		};
		nameidRequest.getAction().setNumrows(MAX_ITEMS);
	}

	/**
	 * Instantiates a new shuttle field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param items the list of NameId items to populate the list of available items.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field.
	 */
	public ShuttleField(
			HasComponents form,
			short[] permission,
			ArrayList<NameId> items,
			String label,
			int tab) {
		this(form, permission, label, tab);

		setAvailable(items);
	}

	/*
	 * Instantiates a new shuttle field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field.
	 */
	private ShuttleField(
			HasComponents form,
			short[] permission,
			String label,
			int tab) {

		initialize(shuttle, form, permission, CSS.cbtShuttleField());

		if(label != null) {
			this.label = new Label(label);
			this.label.addStyleName(CSS.cbtShuttleFieldLabel());
			this.label.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {showHelp();}
			});
			heading.add(this.label);
		}

		shuttle.add(heading);
		shuttle.add(panel);

		select.setHTML("&gt;");
		select.addClickHandler(this);
		select.addStyleName(CSS.cbtShuttleFieldButton());

		remove.setHTML("&lt;");
		remove.addClickHandler(this);
		remove.addStyleName(CSS.cbtShuttleFieldButton());

		selectAll.setHTML("&gt;&gt;");
		selectAll.addClickHandler(this);
		selectAll.addStyleName(CSS.cbtShuttleFieldButton());

		removeAll.setHTML("&lt;&lt;");
		removeAll.addClickHandler(this);
		removeAll.addStyleName(CSS.cbtShuttleFieldButton());

		buttons.add(selectAll);
		buttons.add(select);
		buttons.add(remove);
		buttons.add(removeAll);

		buttons.setCellVerticalAlignment(select, HasVerticalAlignment.ALIGN_MIDDLE);
		buttons.setCellVerticalAlignment(remove, HasVerticalAlignment.ALIGN_MIDDLE);
		buttons.setCellVerticalAlignment(selectAll, HasVerticalAlignment.ALIGN_MIDDLE);
		buttons.setCellVerticalAlignment(removeAll, HasVerticalAlignment.ALIGN_MIDDLE);

		available.setVisibleItemCount(6);
		available.addStyleName(CSS.cbtShuttleFieldAvailable());

		selected.setVisibleItemCount(6);
		selected.addStyleName(CSS.cbtShuttleFieldSelected());

		grid.setWidget(0,0,available);
		grid.setWidget(0,1,buttons);
		grid.setWidget(0,2,selected);
		panel.add(grid);
		setTabIndex(tab);
		//TODO: implement drag and drop for HTML5 browsers
	}

	/**
	 * Handles clicking of the shuttle selector buttons as follows:
	 * >> button transfers all available items to the selected list
	 * > button transfers the selected available item, if any, to the selected list
	 * < button transfers the selected selected item, if any, to the available list
	 * << button transfers all selected items to the available list
	 * 
	 * Fires a change event on any change.
	 *
	 * @param event when any button is clicked.
	 */
	@Override
	public void onClick(ClickEvent event) {
		boolean hasChanged = false;
		int i = 0;
		if (event.getSource() == select) { 		// handle select being clicked
			if (isDisabled(available.getValue(available.getSelectedIndex()))) {return;}
			while ((i = available.getSelectedIndex()) >= 0){
				selected.insertItem(available.getItemText(i), available.getValue(i));
				available.removeItem(i);
				hasChanged = true;
			}
		} else if (event.getSource() == remove) {	// handle remove being clicked
			if (isDisabled(selected.getValue(selected.getSelectedIndex()))) {return;}
			while((i = selected.getSelectedIndex()) >= 0) {
				available.insertItem(selected.getItemText(i), selected.getValue(i));
				selected.removeItem(i);
				hasChanged = true;
			}
		} else if (event.getSource() == selectAll) { // handle selectAll being clicked
			while (available.getItemCount() > 0){
				if (isDisabled(available.getValue(0))) {return;}
				selected.insertItem(available.getItemText(0), available.getValue(0));
				available.removeItem(0);
				hasChanged = true;
			}
		} else if (event.getSource() == removeAll) { // handle removeAll being clicked
			if (isDisabled(selected.getValue(0))) {return;}
			while (selected.getItemCount() > 0){
				available.insertItem(selected.getItemText(0), selected.getValue(0));
				selected.removeItem(0);
				hasChanged = true;
			}
		}
		if (hasChanged) {fireChange(this);}
	}

	/* Checks if the field is disabled.*/
	private boolean isDisabled(String value) {
		if (disabled == null){return false;}
		for (int i = 0; i < disabled.length; i++) {
			if (disabled[i].equalsIgnoreCase(value)){return true;}
		}
		return false;
	}

	/** Refreshes the available list from the server. */
	@Override
	public void onRefresh() {
		if (nameidRequest != null) {nameidRequest.execute();}
	}

	/**
	 * Sets and executes the NameId action to refresh the available item list from the server.
	 *
	 * @param action the new action to refresh the available item list from the server.
	 */
	public void setAction(NameIdAction action) {
		if (nameidRequest != null) {nameidRequest.execute(action);}
	}

	/** Executes the NameId action to refresh the list of available items from the server. */
	public void execute() {
		if (nameidRequest != null) {nameidRequest.execute();}
	}

	/**
	 * Sets that values for all organizations are to be included in the list of available items.
	 * If this is not set the list is populated with values only for the current organization.
	 *
	 * @param allOrganizations is true if the list contains values for all organizations.
	 */
	public void setAllOrganizations(boolean allOrganizations) {
		if (nameidRequest != null) {nameidRequest.setAllOrganizations(allOrganizations);}
	}

	/**
	 * Sets that only values of the specified type are to be included in the list of available items..
	 * This is typically used as a filter applied in a NameId service.
	 *
	 * @param type the specified type.
	 */
	public void setType(String type){
		if (nameidRequest != null) {nameidRequest.getAction().setType(type);}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int index){
		available.setTabIndex(index);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		select.setEnabled(isEnabled());
		selectAll.setEnabled(isEnabled());
		remove.setEnabled(isEnabled());
		removeAll.setEnabled(isEnabled());
		available.setEnabled(isEnabled());
		selected.setEnabled(isEnabled());
	}

	/**
	 * Sets the array of disabled items which may not be selected.
	 *
	 * @param disabled the array of disabled items which may not be selected.
	 */
	public void setDisabled(String[] disabled){
		this.disabled = disabled;
	}

	/**
	 * Sets the CSS style of the available item list.
	 *
	 * @param style the CSS style of the available item list.
	 */
	public void setAvailableStyle(String style) {
		available.addStyleName(style);
	}

	/**
	 * Sets the CSS style of the selected item list.
	 *
	 * @param style the CSS style of the selected item list.
	 */
	public void setSelectedStyle(String style) {
		selected.addStyleName(style);
	}

	/**
	 * Sets the CSS style of the field value.
	 *
	 * @param style the CSS style of the field.
	 */
	public void setFieldStyle(String style) {
		setAvailableStyle(style);
		setSelectedStyle(style);
	}

	/**
	 * Sets the CSS style of the field label.
	 *
	 * @param style the CSS style of the field label.
	 */
	public void setLabelStyle(String style) {
		label.addStyleName(style);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setSecure(boolean)
	 */
	@Override
	public void setSecure(boolean secure) {
		super.setSecure(secure);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setFocus(boolean)
	 */
	@Override
	public void setFocus(boolean focused){
		available.setFocus(focused);
	}

	/**
	 * Sets a single default value.
	 *
	 * @param value to add to the list of default selected values.
	 */
	public void setDefaultValue(String value) {
		if (value == null) {return;}
		ArrayList<String> values = new ArrayList<String>();
		values.add(value);
		setDefaultValue(values);
	}

	/**
	 * Sets if the field is preselected.
	 *
	 * @param preselected true, if the field is preselected.
	 */
	public void setPreselected(boolean preselected) {
		this.preselected = preselected;
	}

	/**  Clears the available and selected lists. */
	public void clear(){
		available.clear();
		selected.clear();
	}

	/** De-selects all selected items. */
	public void deselect() {
		while (selected.getItemCount() > 0) {
			available.insertItem(selected.getItemText(0), selected.getValue(0));
			selected.removeItem(0);
		}
	}

	/** Selects all available items. */
	public void selectAll() {
		while (available.getItemCount() > 0){
			if (isDisabled(available.getValue(0))) {return;}
			selected.insertItem(available.getItemText(0), available.getValue(0));
			available.removeItem(0);
		}
	}

	/**
	 * Sets the list of available items.
	 *
	 * @param nameids the list of available items.
	 */
	public void setAvailable(ArrayList<NameId> nameids) {
		available.clear();
		selected.clear();
		if (nameids == null || nameids.isEmpty()) {return;}
		Collections.sort(nameids);
		for (NameId nameid : nameids) {available.addItem(nameid.getName(), nameid.getId());}
		if (preselected) {selectAll();}
		else {available.setSelectedIndex(0);}
		fireChange(available);
	}

	/**
	 * Checks if the specified value is available.
	 *
	 * @param value the specified value.
	 * @return true if the specified value is available.
	 */
	public boolean hasAvailableValue(String value) {
		for (int index = 0; index < available.getItemCount(); index++) {
			if (available.getValue(index).equalsIgnoreCase(value)) {return true;}
		}
		return false;
	}

	/**
	 * Checks if the specified value is not available.
	 *
	 * @param value the specified value.
	 * @return true if the specified value not is available.
	 */
	public boolean noAvailableValue(String value) {
		return !hasAvailableValue(value);
	}

	/**
	 * Checks if the specified value is selected.
	 *
	 * @param value the specified value.
	 * @return true if the specified value is selected.
	 */
	public boolean hasSelectedValue(String value) {
		for (int index = 0; index < selected.getItemCount(); index++) {
			if (selected.getValue(index).equalsIgnoreCase(value)) {return true;}
		}
		return false;
	}

	/**
	 * Checks if the specified value is not selected.
	 *
	 * @param value the specified value.
	 * @return true if the specified value is not selected.
	 */
	public boolean noSelectedValue(String value) {
		return !hasSelectedValue(value);
	}

	/**
	 * Adds a value to the selected list.
	 *
	 * @param value to add to the selected list
	 */
	public void addValue(String value) {
		if (value == null) {return;}
		ArrayList<String> values = getValue();
		if (values == null) {values = new ArrayList<String>();}
		if (!values.contains(value)) {values.add(value);}
		setValue(values);
	}

	/**
	 * Sets the list of selected items.
	 *
	 * @param values the list of selected items.
	 */
	public void setValue(ArrayList<String> values){
		deselect();
		if (values != null) {
			if (secure) {values = Model.decryptList(values);}
			int index = 0;
			while(index < available.getItemCount()) {
				if (values.contains(available.getValue(index))) {
					selected.addItem(available.getItemText(index), available.getValue(index));
					available.removeItem(index);
				} else index++;
			}
		}
		super.setChanged();
	}

	/**
	 * Gets the list of selected items.
	 *
	 * @return values the list of selected items.
	 */
	public ArrayList<String> getValue() {
		ArrayList<String> values = new ArrayList<String>(selected.getItemCount());
		for (int i = 0; i < selected.getItemCount(); i++) {values.add(selected.getValue(i));}
		if (secure) {values = Model.encryptList(values);}
		return values;
	}

	/**
	 * Checks if no items are selected.
	 *
	 * @return true if no items are selected
	 */
	public boolean noValue() {
		return getValue() == null || getValue().isEmpty();
	}

	/**
	 * Checks if the specified value is selected.
	 *
	 * @param value the specified value.
	 * @return true if the specified value is selected.
	 */
	public boolean hasValue(String value) {
		for (int i = 0; i < selected.getItemCount(); i++) {
			if (selected.getValue(i).equalsIgnoreCase(value)) {return true;}
		}
		return false;
	}

	/**
	 * Checks if any items are available.
	 *
	 * @return true, if any items are available.
	 */
	public boolean isAvailable() {
		return available.getItemCount() != 0;
	}

	/**
	 * Checks if any items are selected.
	 *
	 * @return true, if any items are selected.
	 */
	public boolean isSelected() {
		return selected.getItemCount() != 0;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#is(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean is(Widget sender){
		return (sender == this);
	}

}
