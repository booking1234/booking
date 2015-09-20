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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/** The Class MultilistField is to display and select from multiple lists. */
public class MultilistField 
extends AbstractField<ArrayList<String>>
implements ClickHandler {

	private NameIdRequest nameidRequest;

	private final VerticalPanel panel = new VerticalPanel();
	private final ScrollPanel scroll = new ScrollPanel();
	private HorizontalPanel header;
	private Label label;
	private Button select;
	private Button remove;
	private Grid field = new Grid(); //name, id, value
	private static final int MAX_ITEMS = 500;

	/**
	 * Instantiates a new multiple list field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param action to populate the list.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field.
	 */
	public MultilistField(
			HasComponents form,
			short[] permission,
			NameIdAction action,
			String label,
			int tab) {
		this (form, permission, label, tab);

		nameidRequest = new NameIdRequest(action) {
			public void receive(Table<NameId> response) {
				setItems(response.getValue());
				if (response.hasValue()) {onReset();}
			}
		};
		nameidRequest.getAction().setNumrows(MAX_ITEMS);
	}

	/**
	 * Instantiates a new multiple list field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param items the list of items to populate the list.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field.
	 */
	public MultilistField(
			HasComponents form,
			short[] permission,
			ArrayList<NameId> items,
			String label,
			int tab) {
		this(form, permission, label, tab);

		setItems(items);
	}


	/*
	 * Instantiates a new multiple list field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field.
	 */
	private MultilistField(
			HasComponents form,
			short[] permission,
			String label,
			int tab) {

		initialize(panel, form, permission, CSS.cbtMultilistField());

		if(label != null) {
			header = new HorizontalPanel();
			header.addStyleName(CSS.cbtMultilistFieldLabel());
			panel.add(header);

			this.label = new Label(label);
			this.label.addStyleName(CSS.cbtMultilistFieldLabel());
			header.add(this.label);

			select = new Button();
			select.setHTML("&gt;&gt;");
			select.addClickHandler(this);
			select.addStyleName(CSS.cbtMultilistFieldButton());
			header.add(select);

			remove = new Button();
			remove.setHTML("&lt;&lt;");
			remove.addClickHandler(this);
			remove.addStyleName(CSS.cbtMultilistFieldButton());
			header.add(remove);
		}

		field.addClickHandler(this);
		field.addStyleName(CSS.cbtMultilistFieldGrid());
		field.setBorderWidth(0);
		scroll.add(field);
		panel.add(scroll);
		setTabIndex(tab);
	}

	/**
	 * Handles click events and fires a change event on any change.
	 *
	 * @param event when clicked.
	 */
	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() == select) {
			for (int row = 0; row < field.getRowCount(); row++) {
				CheckBox cb = (CheckBox) field.getWidget(row, 2);
				if (cb == null) {Window.alert("select " + row);}
				cb.setValue(true);
			}
		}
		else if (event.getSource() == remove) {
			for (int row = 0; row < field.getRowCount(); row++) {
				CheckBox cb = (CheckBox) field.getWidget(row, 2);
				if (cb == null) {Window.alert("remove " + row);}
				cb.setValue(false);
			}			
		}
		else if (isEnabled()) {
			HTMLTable.Cell cell = field.getCellForEvent(event);
			int selectedRow = cell.getRowIndex();
			CheckBox cb = (CheckBox) field.getWidget(selectedRow, 2);
			cb.setValue (!cb.getValue());
			fireChange(this);
		}
	}

	/** Refreshes the list values. */
	@Override
	public void onRefresh() {
		nameidRequest.execute();
	}

	/**
	 * Sets the action to refresh the list.
	 *
	 * @param action the new action to refresh the list.
	 */
	public void setAction(NameIdAction action) {
		if (nameidRequest != null) {nameidRequest.execute(action);}
	}

	/** Executes the action to refresh the list. */
	public void execute() {
		if (nameidRequest != null) {nameidRequest.execute();}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int index){
		//grid.setTabIndex(index);
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

	/** Clears the field values. */
	public void clear(){
		field.resize(0, 4);
	}

	/** De-selects all selected items. */
	public void deselect() {
		for (int row = 0; row < field.getRowCount(); row++) {
			CheckBox cb = (CheckBox) field.getWidget(row, 3);
			cb.setValue(false);
		}
	}

	/*
	 * Sets the selected items.
	 * 
	 * @param values the new values of the field.
	 */
	private void setItems(ArrayList<NameId> values) {
		if (values == null || values.isEmpty()) {return;}
		int row = 0;
		Collections.sort(values);
		Grid.RowFormatter fmtRow = field.getRowFormatter();
		Grid.CellFormatter fmtCell = field.getCellFormatter();
		field.resize(values.size(), 3);
		fmtCell.addStyleName(row, 1, CSS.cbtMultilistFieldNull());
		for (NameId value : values) {
			field.setText(row, 0, value.getName());
			field.setText(row, 1, value.getId());
			CheckBox cb = new CheckBox();
			cb.addClickHandler(this);
			field.setWidget(row, 2, cb);
			fmtCell.addStyleName(row, 1, CSS.cbtMultilistFieldNull());
			fmtRow.addStyleName(row++, CSS.cbtMultilistFieldValue());
		}
	}

	/**
	 * Sets the selected items.
	 * 
	 * @param value the new values of the field.
	 */
	public void setValue(ArrayList<String> value) {
		for (int row = 0; row < field.getRowCount(); row++) {
			String id = field.getText(row, 1);
			CheckBox cb = (CheckBox) field.getWidget(row, 2);
			if (cb == null) {Window.alert("MultilistField setValue null at " + row);}
			if (value == null) {cb.setValue(false);}
			else {cb.setValue(value.contains(id));}
		}
		super.setChanged();
	}

	/**
	 * Gets the selected items.
	 * 
	 * @return the values of the field.
	 */
	public ArrayList<String> getValue() {

		ArrayList<String> value = new ArrayList<String>();
		for (int row = 0; row < field.getRowCount(); row++) {
			String id = field.getText(row, 1);
			CheckBox cb = (CheckBox) field.getWidget(row, 2);
			if (cb.getValue()) {value.add(id);}
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
	 * @param widget is the  specified widget.
	 * @return true if this is the specified widget.
	 */
	@Override
	public boolean is(Widget widget){
		return (widget == this);
	}

}
