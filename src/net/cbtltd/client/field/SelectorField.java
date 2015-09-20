/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import net.cbtltd.client.HasComponents;
import net.cbtltd.client.RemoteRequest;
import net.cbtltd.shared.AttributeMapAction;
import net.cbtltd.shared.MapResponse;
import net.cbtltd.shared.NameId;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class SelectorField is to select items from a value having multiple options.
 * Its primary use is to display and select the attributes of an entity.
 * The field value is a list of key-option(s) where one or more option(s) can be selected for each key.
 * Keys that may have only one option at a time are added to the unique keys array.
 * The class normally renders the key-option(s) in a grid having a check box for each option.
 * The option(s) are indented relative to their key by VALUE_PREFIX.
 * A simple list of selected values may be displayed by setting setVisibleGrid(true).
 * Typically the selector grid would be hidden by setting setValue(false) when the simple list is displayed.
 */
public final class SelectorField
extends AbstractField<HashMap<String,ArrayList<String>>>
implements ClickHandler {

	private MapRequest mapRequest;

	private final VerticalPanel panel = new VerticalPanel();
	private Label label;
	/* Each field row has the option name, key, id and value */
	private final Grid field = new Grid(0,4);
	/* Each grid row has only its name. */
	private final Grid grid = new Grid();
	/* The list of keys that may have only one option at a time. */
	private String[] uniquekeys;
	/* The Constant VALUE_PREFIX indents option(s) relative to its key. */
	private static final String VALUE_PREFIX = "\u00A0\u00A0\u00A0"; //java
	/* The Constant KEY indicates that the field row is a kev - other rows are options. */
	private static final String KEY = "KEY";
	/* True if the keys and options are sorted alphabetically. */
	private boolean sorted = true;
	
	/* Inner Class MapRequest extends RemoteRequest to fetch attribute maps to be rendered from the server. */
	private class MapRequest
	extends RemoteRequest<AttributeMapAction, MapResponse<NameId>> {

		private AttributeMapAction action;

		public MapRequest(AttributeMapAction action){
			this.action = action;
		}

		protected void receive(MapResponse<NameId> response) {
			setItems(response.getValue());
		}

		public void execute(AttributeMapAction action) {
			this.action = action;
			execute();
		}

		public boolean execute() {
			if (action == null || action.noKeys()) {return true;}
			super.send(action);
			return false;
		}

		public void setAction(AttributeMapAction action) {
			this.action = action;
		}

		public AttributeMapAction getAction() {
			return action;
		}
	}

	/**
	 * Instantiates a new selector field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param map of items to populate the available key-option(s).
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field.
	 */
	public SelectorField(
			HasComponents form,
			short[] permission,
			HashMap<NameId, ArrayList<NameId>> map,
			String label,
			int tab) {

		this(form, permission, label, tab);
		setItems(map);
	}

	/**
	 * Instantiates a new selector field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param action to populate the available key-option(s) from the server.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field.
	 */
	public SelectorField(
			HasComponents form,
			short[] permission,
			AttributeMapAction action,
			String label,
			int tab) {
		this(form, permission, label, tab);
		mapRequest = new MapRequest(action);
	}

	/*
	 * Instantiates a new selector field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field.
	 */
	private SelectorField(
			HasComponents form,
			short[] permission,
			String label,
			int tab) {

		initialize(panel, form, permission, CSS.cbtSelectorField());

		if(label != null) {
			this.label = new Label(label);
			this.label.addStyleName(CSS.cbtSelectorFieldLabel());
			this.label.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {showHelp();}
			});
			panel.add(this.label);
		}

		field.addClickHandler(this);
		field.setBorderWidth(0);
		panel.add(field);
		grid.setVisible(false);
		panel.add(grid);
		setTabIndex(tab);
	}

	/**
	 * Handles click events and renders the field accordingly.
	 * Fires a change event if an option changes.
	 *
	 * @param event when clicked.
	 */
	@Override
	public void onClick(ClickEvent event) {
		if (isEnabled() && event.getSource() == field) {
			CheckBox cb;
			HTMLTable.Cell cell = field.getCellForEvent(event);
			if (cell == null) {return;}
			int selectedRow = cell.getRowIndex();
			String key = field.getText(selectedRow, 1);
			if (KEY.equalsIgnoreCase(field.getText(selectedRow, 2))) {
				cb = (CheckBox) field.getWidget(selectedRow, 3);
				boolean keyvalue = cb.getValue();
				for (int row = 0; row < field.getRowCount(); row++) {
					if (row != selectedRow && key.equalsIgnoreCase(field.getText(row, 1))) {
						cb = (CheckBox) field.getWidget(row, 3);
						cb.setValue(keyvalue);
					}
				}
			}

			cb = (CheckBox) field.getWidget(selectedRow, 3);
			if (cb.getValue() && isUniquekey(key)) {
				for (int row = 0; row < field.getRowCount(); row++) {
					if (row != selectedRow && key.equalsIgnoreCase(field.getText(row, 1))) {
						cb = (CheckBox) field.getWidget(row, 3);
						cb.setValue(false);
					}
				}
			}
			fireChange(this);
		}
	}

	/*
	 * Gets if the specified key may only have a single option.
	 * 
	 * @param key the specified key.
	 * @return true if key may only have a single option.
	 */
	private boolean isUniquekey(String key) {
		if (uniquekeys == null){return false;}
		for (String uniquekey : uniquekeys) {
			if (uniquekey.equalsIgnoreCase(key)){return true;}
		}
		return false;
	}

	/** Refreshes the available key-option(s) from the server. */
	@Override
	public void onRefresh() {
		if (mapRequest != null) {mapRequest.execute();}
	}

	/**
	 * Sets the array of keys for which only one option may be selected at a time.
	 *
	 * @param uniquekeys the new array of unique keys.
	 */
	public void setUniquekeys(String[] uniquekeys) {
		this.uniquekeys = uniquekeys;
	}

	/**
	 * Sets and executes the action to get the available key-option(s) from the server.
	 *
	 * @param action the new action to get the available key-option(s) from the server.
	 */
	public void setAction(AttributeMapAction action) {
		if (mapRequest != null) {mapRequest.setAction(action);}
		onRefresh();
	}

	/**
	 * Sets the field label text.
	 *
	 * @param text the new label text.
	 */
	public void setLabel(String text) {
		if (label != null) {label.setText(text);}
	}

	/**
	 * Sets if the key-option(s) are to be sorted alphabetically.
	 *
	 * @param sorted is true if the key-option(s) are to be sorted alphabetically.
	 */
	public void setSorted(boolean sorted) {
		this.sorted = sorted;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int index){}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setVisible(boolean)
	 */
	public void setVisible(boolean visible){
		field.setVisible(visible);
	}

	/**
	 * Sets if the simple grid display is visible.
	 *
	 * @param visible is true if the simple grid display is visible.
	 */
	public void setVisibleGrid(boolean visible) {
		grid.setVisible(visible);
	}

	/**
	 * Sets the CSS style of the field value.
	 *
	 * @param style the CSS style of the field.
	 */
	public void setFieldStyle(String style) {
		field.addStyleName(style);
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
		//		grid.setFocus(focused);
	}

	/** Clears the available key-option(s) from the field. */
	public void clear(){
		field.resize(0, 4);
	}

	/** De-selects all selected options for all keys. */
	public void deselect() {
		for (int row = 0; row < field.getRowCount(); row++) {
			CheckBox cb = (CheckBox) field.getWidget(row, 3);
			cb.setValue(false);
		}
	}

	/** Selects all selected options for all keys. */
	public void select() {
		for (int row = 0; row < field.getRowCount(); row++) {
			CheckBox cb = (CheckBox) field.getWidget(row, 3);
			cb.setValue(true);
		}
	}

	/**
	 * Renders the field from the specified map of available key-option(s) into the field.
	 *
	 * @param map the map of available key-option(s).
	 */
	public void setItems(HashMap<NameId, ArrayList<NameId>> map) {
		if (map == null) {return;}
		int row = 0;
		ArrayList<NameId>keys = new ArrayList<NameId>(map.keySet());
		if (sorted) {Collections.sort(keys);}
		Grid.RowFormatter fmtRow = field.getRowFormatter();
		Grid.CellFormatter fmtCell = field.getCellFormatter();
		for (NameId key : keys) {
			ArrayList<NameId> values = map.get(key);
			field.resize(row + 1 + values.size(), 4);
			field.setText(row, 0, key.getName());
			field.setText(row, 1, key.getId());
			field.setText(row, 2, KEY);
			CheckBox cb = new CheckBox();
			cb.addClickHandler(this);
			cb.setVisible(true);//TODO:
			cb.addStyleName(CSS.cbtSelectorFieldField());
			field.setWidget(row, 3, cb);
			fmtCell.addStyleName(row, 1, CSS.cbtSelectorFieldNull());
			fmtCell.addStyleName(row, 2, CSS.cbtSelectorFieldNull());
			fmtRow.addStyleName(row, CSS.cbtSelectorFieldKey());
			row++;
			for (NameId value : values) {
				field.setText(row, 0, VALUE_PREFIX + value.getName());
				field.setText(row, 1, key.getId());
				field.setText(row, 2, value.getId());
				cb = new CheckBox();
				cb.addClickHandler(this);
				cb.addStyleName(CSS.cbtSelectorFieldField());
				field.setWidget(row, 3, cb);
				fmtCell.addStyleName(row, 1, CSS.cbtSelectorFieldNull());
				fmtCell.addStyleName(row, 2, CSS.cbtSelectorFieldNull());
				fmtRow.addStyleName(row, CSS.cbtSelectorFieldValue());
				row++;
			}
		}
	}

	/**
	 * Sets the selected options in the field from the specified map of selected key-option(s).
	 *
	 * @param value the map of selected key-option(s).
	 */
	public void setValue(HashMap<String, ArrayList<String>> value) {
		for (int row = 0; row < field.getRowCount(); row++) {
			String key = field.getText(row, 1);
			String id = field.getText(row, 2);
			CheckBox cb = (CheckBox) field.getWidget(row, 3);
			if (cb == null) {Window.alert("SelectorField setValue " + row);}
			if (value == null) {cb.setValue(false);}
			else if (value.containsKey(key)) {cb.setValue(value.get(key).contains(id));}
		}
		setGrid();
		super.setChanged();
	}

	/* Renders the simple grid from the current map of selected key-option(s) in the field. */
	private void setGrid() {
		Grid.RowFormatter fmtRow = grid.getRowFormatter();
		int row = 0;
		for (int index = 0; index < field.getRowCount(); index++) {
			CheckBox cb = (CheckBox) field.getWidget(index, 3);
			boolean isKey = KEY.equals(field.getText(index, 2));
			if (isKey || cb.getValue()) {
				grid.resize(row + 1, 1);
				fmtRow.addStyleName(row, isKey ? CSS.cbtSelectorFieldKey() : CSS.cbtSelectorFieldValue());
				grid.setText(row, 0, field.getText(index, 0));
				row++;
			}
		}
	}

	/**
	 * Gets the selected options of the field.
	 *
	 * @return map the map of selected key-option(s).
	 */
	public HashMap<String,ArrayList<String>> getValue() {
		ArrayList<String> item = null;
		HashMap<String, ArrayList<String>> value = new HashMap<String,ArrayList<String>>();
		for (int row = 0; row < field.getRowCount(); row++) {
			String key = field.getText(row, 1);
			String id = field.getText(row, 2);
			CheckBox cb = (CheckBox) field.getWidget(row, 3);
			if (cb.getValue()) {
				if (!value.containsKey(key)) {
					item = new ArrayList<String>();
					value.put(key, item);
				}
				item.add(id);
			}
		}
		return value;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#noValue()
	 */
	public boolean noValue() {
		return getValue().keySet() == null || getValue().keySet().isEmpty();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#is(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean is(Widget sender){
		return (sender == this);
	}

}