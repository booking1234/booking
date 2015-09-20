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
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Table;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/** The Class OptionField is to display and select from a list of options. */
public final class OptionField
extends AbstractField<ArrayList<String>>
implements ClickHandler {

	private NameIdRequest nameidRequest;
	private final VerticalPanel panel = new VerticalPanel();
	private Label label;
	private final Grid field = new Grid(0, 2); //name, id
	private final Grid grid = new Grid(); //name only
	private boolean selected;
	
	/**
	 * Instantiates a new option field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param items to populate the list field.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field.
	 */
	public OptionField(
			HasComponents form,
			short[] permission,
			ArrayList<NameId> items,
			String label,
			int tab) {
		
		this(form, permission, label, tab);
		
		if (items != null) {
			Collections.sort(items);
			setItems(items);
		}
	}

	/**
	 * Instantiates a new option field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param action the action for obtaining the available lists from the server.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field.
	 */
	public OptionField (
			HasComponents form,
			short[] permission,
			NameIdAction action,
			String label,
			int tab) {
		this(form, permission, label, tab);
		
		nameidRequest = new NameIdRequest(action) {
			public void receive(Table<NameId> response) {
				if (response != null && response.hasValue()) {
					setItems(response.getValue());
					onReset();
				}
			}
		};
	}

	/*
	 * Instantiates a new option field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field.
	 */
	private OptionField (
			HasComponents form,
			short[] permission,
			String label,
			int tab) {
		initialize(panel, form, permission, CSS.cbtSelectorField());

		if (label != null){
			this.label  = new Label(label);
			this.label.addStyleName(CSS.cbtSelectorFieldLabel());
			this.label.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {showHelp();}
			});
			panel.add(this.label);
		}

		field.addClickHandler(this);
		field.addStyleName(CSS.cbtSelectorFieldField());
		panel.add(field);
		grid.setVisible(false);
		panel.add(grid);
		setTabIndex(tab);
	}

	/**
	 * Handles click events.
	 *
	 * @param event when clicked.
	 */
	@Override
	public void onClick(ClickEvent event) {
//TODO:		if (!options.isEnabled()) {return;}
//		else if (event.getSource() == field) {
//			HTMLTable.Cell cell = field.getCellForEvent(event);
//			if (cell == null) {return;}
//			int selectedRow = cell.getRowIndex();
//			String key = field.getText(selectedRow, 1);
//			CheckBox cb = (CheckBox) field.getWidget(selectedRow, 3);
//			cb.setValue (!cb.getValue());
//			if (cb.getValue() && unique) {
//				for (int row = 0; row < field.getRowCount(); row++) {
//					if (row != selectedRow && key.equalsIgnoreCase(field.getText(row, 1))) {
//						cb = (CheckBox) field.getWidget(row, 3);
//						cb.setValue(false);
//					}
//				}
//			}
//			fireChange(this);
//		} else {Window.alert("Clicked");}
	}

	/** Refreshes the field. */
	@Override
	public void onRefresh() {
		if (nameidRequest != null) {nameidRequest.execute();}
	}


	/** Executes the refresh action.  */
	public void execute() {
		if (nameidRequest != null) {nameidRequest.execute();}
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
	 * Sets if the grid is visible.
	 *
	 * @param visible is true if the grid is visible.
	 */
	public void setVisibleGrid(boolean visible){
		grid.setVisible(visible);
	}

	/**
	 * Sets if the label is visible.
	 *
	 * @param visible is true if the label is visible.
	 */
	public void setVisibleLabel(boolean visible){
		label.setVisible(visible);
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
	public void setFocus(boolean focused) {}

	/** Clears the field values. */
	public void clear(){
		field.resize(0, 2);
	}

	/** De-selects selected items. */
	public void deselect() {
		for (int row = 0; row < field.getRowCount(); row++) {
			CheckBox cb = (CheckBox) field.getWidget(row, 0);
			cb.setValue(false);
		}
	}

	/**
	 * Sets the list of available items from the specified name id values.
	 *
	 * @param values the specified name id values.
	 */
	public void setItems(ArrayList<NameId> values) {
		if (values == null) {return;}
		Collections.sort(values);
		Grid.RowFormatter fmtRow = field.getRowFormatter();
		Grid.CellFormatter fmtCell = field.getCellFormatter();
		field.resize(values.size(), 2);
		int row = 0;
		for (NameId value : values) {
			field.setWidget(row, 0, new CheckBox(value.getName()));
			fmtCell.addStyleName(row, 0, CSS.cbtSelectorField());
			field.setText(row, 1, value.getId());
			fmtCell.addStyleName(row, 1, CSS.cbtSelectorFieldNull());
			fmtRow.addStyleName(row, CSS.cbtSelectorFieldValue());
			row++;
		}
	}
	
	/**
	 * Sets the list of selected items from the specified IDs.
	 *
	 * @param value the specified IDs.
	 */
	public void setValue(ArrayList<String> value) {
		for (int row = 0; row < field.getRowCount(); row++) {
			CheckBox cb = (CheckBox) field.getWidget(row, 0);
			String id = field.getText(row, 1);
			if (cb == null) {Window.alert("setValue " + row);}
			if (value == null) {cb.setValue(false);}
			else {cb.setValue(value.contains(id));}
		}
		super.setChanged();
	}

	/**
	 * Gets the list of IDs from the selected items.
	 *
	 * @return the list of selected IDs.
	 */
	public ArrayList<String> getValue() {
		ArrayList<String> value = new ArrayList<String>();
		for (int row = 0; row < field.getRowCount(); row++) {
			CheckBox cb = (CheckBox) field.getWidget(row, 0);
			if (cb.getValue()) {value.add(field.getText(row, 1));}
		}
		return value;
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#noValue()
	 */
	public boolean noValue() {
		return getValue() == null || getValue().isEmpty();
	}
	
	/**
	 * Checks if this is the specified widget.
	 *
	 * @param widget is the specified widget.
	 * @return true if this is the specified widget.
	 */
	@Override
	public boolean is(Widget widget){
		return (widget == this);
	}

}