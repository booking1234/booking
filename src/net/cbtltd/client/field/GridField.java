/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.ArrayList;

import net.cbtltd.client.Component;
import net.cbtltd.client.Components;
import net.cbtltd.client.HasComponents;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class GridField is to display and change simple tabular data. Use TableField for more complex requirements.
 *
 * @param <T> the generic row type managed by the field.
 */
public abstract class GridField<T>
extends AbstractField<ArrayList<T>>
implements HasComponents, ClickHandler {

	private static final Components components = new Components();
	private final VerticalPanel panel = new VerticalPanel();
	private final String[] headers;
	private Label label;
	private int numRows; // number of rows in table excluding header and footer
	private int selectedCol; // current selected col
	private int selectedRow = -1; // current selected row
	private boolean autosize = false;
	protected final Grid field = new Grid();
	
	/**
	 * Sets the row value.
	 *
	 * @param row the row number.
	 * @param value the value of the row.
	 */
	public abstract void setRowValue(int row, T value);
	
	/**
	 * Gets the row value.
	 *
	 * @param row the row number.
	 * @return the value of the row.
	 */
	public abstract T getRowValue(int row);
	
	/**
	 * Instantiates a new grid field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param headers the array of header strings which must match the number of columns in the grid.
	 * @param numRows the number of rows in the grid.
	 * @param tab index of the field.
	 */
	public GridField (
			HasComponents form,
			short[] permission,
			String[] headers,
			int numRows,
			int tab) {
		this(form, permission, headers, null, numRows, tab);
	}

	/**
	 * Instantiates a new grid field.
	 *
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param headers the array of header strings which must match the number of columns in the grid.
	 * @param label is the optional text to identify the field.  
	 * @param numRows the number of rows in the grid.
	 * @param tab index of the field.
	 */
	public GridField(
			HasComponents form,
			short[] permission,
			String[] headers,
			String label,
			int numRows,
			int tab) {

		initialize(panel, form, permission, CSS.cbtGridField());

		this.numRows = numRows;
		this.headers = headers;
		setTabIndex(tab);

		if (label != null) {
			this.label = new Label(label);
			this.label.addStyleName(CSS.cbtGridFieldLabel());
			this.label.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {showHelp();}
			});
			panel.add(this.label);
		}
		
		field.resize(numRows + 2, headers.length); //TODO:
		field.addClickHandler(this);
		field.addStyleName(CSS.cbtGridFieldField());

		Grid.RowFormatter fmtRow = field.getRowFormatter();
		Grid.CellFormatter fmtCell = field.getCellFormatter();
		fmtRow.addStyleName(0, CSS.cbtGridFieldHeaderRow());
		fmtRow.addStyleName(0, CSS.cbtGradientHead());
		for (int row = 1; row <= numRows + 1; row++) {
			if (row%2 == 0) {fmtRow.addStyleName(row, CSS.cbtGridFieldOddRow());}
			else {fmtRow.addStyleName(row, CSS.cbtGridFieldEvenRow());}

			for (int col = 0; col < headers.length; col++) {
				field.setHTML(0, col, headers[col]);
				if (col%2 == 0) {fmtCell.addStyleName(row, col, CSS.cbtGridFieldEvenCol());}
				else {fmtCell.addStyleName(row, col, CSS.cbtGridFieldOddCol());}
			}
		}

		panel.add(field);

	}

	/**
	 * Implements HasComponents interface.
	 */
	public void addComponent(Component component) {
		components.add(component);
	}

	/**
	 * Handles change events.
	 *
	 * @param event when changed.
	 */
	@Override
	public void onChange(ChangeEvent event) {}

	/**
	 * Handles click events and fires a change event on any change.
	 *
	 * @param event when clicked.
	 */
	@Override
	public void onClick(ClickEvent event) {
		if (!enabled) {return;}
		else if (event.getSource() == field) {
			HTMLTable.Cell cell = field.getCellForEvent(event);
			int oldSelectedRow = selectedRow;
			selectedRow = cell.getRowIndex();
			selectedCol = cell.getCellIndex();
			Grid.RowFormatter fmtRow = field.getRowFormatter();
			if (oldSelectedRow >= 0) {fmtRow.removeStyleName(oldSelectedRow, CSS.cbtGridFieldSelected());}
			fmtRow.addStyleName(selectedRow, CSS.cbtGridFieldSelected());
			fireChange(field);
		}
		else fireChange((Widget)event.getSource()); // another widget eg: row button, column header
	}

	/**
	 * Resets field to default values.
	 *
	 * @param state the state to which the field is reset.
	 */
	@Override
	public void onReset(String state) {
		for (int row = 1; row <= numRows; row++)
			for (int col = 0; col < headers.length; col++)
				field.clearCell(row, col);
		field.clear();
		Grid.RowFormatter fmtRow = field.getRowFormatter();
		if (selectedRow >= 0) {fmtRow.removeStyleName(selectedRow, CSS.cbtGridFieldSelected());}
		selectedRow = -1;
		super.onReset();
	}

	/**
	 * Gets the row formatter of the grid.
	 *
	 * @return the grid row formatter.
	 */
	public Grid.RowFormatter getRowFormatter() {
		return field.getRowFormatter();
	}

	/**
	 * Gets the cell formatter of the grid.
	 *
	 * @return the grid cell formatter.
	 */
	public Grid.CellFormatter getCellFormatter() {
		return field.getCellFormatter();
	}

	/**
	 * Sets the CSS style of the grid elements.
	 *
	 * @param style the CSS style of the grid elements.
	 */
	public void setFieldStyle(String style) {
		field.addStyleName(style);
	}

	/**
	 * Sets the CSS style of the grid label.
	 *
	 * @param style the CSS style of the grid label.
	 */
	public void setLabelStyle(String style) {
		if (label != null) {label.addStyleName(style);}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setFocus(boolean)
	 */
	public void setFocus(boolean mode) {}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setTabIndex(int)
	 */
	public void setTabIndex(int setTabIndex) {}

	/**
	 * Sets the CSS style of the grid row.
	 *
	 * @param row the row number.
	 * @param style the CSS style.
	 */
	public void setRowFormat(int row, String style) {
		field.getRowFormatter().addStyleName(row, style);
	}

	/**
	 * Sets the CSS style of the grid column.
	 *
	 * @param col the column number.
	 * @param style the CSS style.
	 */
	public void setColumnFormat(int col, String style) {
		field.getColumnFormatter().addStyleName(col, style);
	}

	/**
	 * Sets the CSS style of the grid cell.
	 *
	 * @param row the row number.
	 * @param col the column number.
	 * @param style the CSS style.
	 */
	public void setCellFormat(int row, int col, String style) {
		field.getCellFormatter().addStyleName(row, col, style);
	}

	/**
	 * Checks if the grid is automatically resized.
	 *
	 * @return true, if the grid is automatically resized.
	 */
	public boolean isAutosize() {
		return autosize;
	}
	
	/**
	 * Sets if the grid is automatically resized.
	 *
	 * @param autosize is true if the grid is automatically resized.
	 */
	public void setAutosize(boolean autosize) {
		this.autosize = autosize;
	}
	
	/**
	 * Sets the header text of the specified column.
	 *
	 * @param col the column of the header text.
	 * @param text the header text of the column.
	 */
	public void setHeaderText(int col, String text) {
		field.setHTML(0, col, text);
	}

	/**
	 * Gets the selected column.
	 *
	 * @return the selected column.
	 */
	public int getSelectedCol() {
		return selectedCol;
	}

	/**
	 * Checks if the specified column is selected.
	 *
	 * @param col the specified column.
	 * @return true, if the specified column is selected.
	 */
	public boolean isSelectedCol(int col) {
		return col == selectedCol;
	}

	/**
	 * Sets the selected row.
	 *
	 * @param selectedRow the new selected row.
	 */
	public void setSelectedRow(int selectedRow) {
		Grid.RowFormatter fmtRow = field.getRowFormatter();
		if (this.selectedRow >= 0) {fmtRow.removeStyleName(this.selectedRow, CSS.cbtGridFieldSelected());}
		fmtRow.addStyleName(selectedRow, CSS.cbtGridFieldSelected());
		this.selectedRow = selectedRow;
	}

	/**
	 * Gets the selected row.
	 *
	 * @return the selected row.
	 */
	public int getSelectedRow() {
		return selectedRow;
	}

	/**
	 * Checks if the specified row is selected.
	 *
	 * @param row the specified row.
	 * @return true, if the specified row is selected.
	 */
	public boolean isSelectedRow(int row) {
		return row == selectedRow;
	}

	/**
	 * Gets the text value of the specified cell.
	 *
	 * @param row the row of the cell.
	 * @param col the column of the cell.
	 * @return the text in the cell.
	 */
	public String getText(int row, int col) {
		return field.getText(row, col);
	}

	/**
	 * Sets the text value of the specified cell.
	 *
	 * @param row the row of the cell.
	 * @param col the column of the cell.
	 * @param text the text in the cell.
	 */
	public void setText(int row, int col, String text) {
		if (row >= field.getRowCount() || col > field.getColumnCount()){return;}
		field.setText(row, col, text);
	}

	/**
	 * Gets the widget in the specified cell.
	 *
	 * @param row the row of the cell.
	 * @param col the column of the cell.
	 * @return the widget in the cell.
	 */
	public Widget getWidget(int row, int col) {
		return field.getWidget(row, col);
	}

	/**
	 * Sets the widget in the specified cell.
	 *
	 * @param row the row of the cell.
	 * @param col the column of the cell.
	 * @param widget the widget in the cell.
	 */
	public void setWidget(int row, int col, Widget widget) {
		if (row >= field.getRowCount() || col > field.getColumnCount()){return;}
		field.setWidget(row, col, widget);
	}

	/**
	 * Sets the HTML text in the specified cell.
	 *
	 * @param row the row of the cell.
	 * @param col the column of the cell.
	 * @param html the HTML text in the cell.
	 */
	public void setHTML(int row, int col, String html) {
		if (row >= field.getRowCount() || col > field.getColumnCount()){return;}
		field.setHTML(row, col, html);
	}

	/**
	 * Gets the number of rows in the grid.
	 *
	 * @return the number of rows in the grid.
	 */
	public int getNumRows() {
		return numRows;
	}

	/**
	 * Sets the number of rows in the grid.
	 *
	 * @param the number of rows in the grid.
	 */
	public void setNumRows(int numRows) {
		this.numRows = numRows;
		field.resize(numRows + 2, headers.length);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#getValue()
	 */
	public ArrayList<T> getValue() {
		ArrayList<T> value = new ArrayList<T>();
		for (int row = 1; row <= numRows; row++) {value.add(getRowValue(row));}
		return value;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setValue(java.lang.Object)
	 */
	public void setValue(ArrayList<T> values) {
		if (values == null) {values = new ArrayList<T>();}
		if (autosize) {
			field.getRowFormatter().removeStyleName(numRows + 1, CSS.cbtGridFieldHeaderRow());
			field.getRowFormatter().removeStyleName(numRows + 1, CSS.cbtGradientHead());
			setNumRows(values.size());
			field.getRowFormatter().addStyleName(numRows + 1, CSS.cbtGridFieldHeaderRow());
			field.getRowFormatter().addStyleName(numRows + 1, CSS.cbtGradientHead());
		}
		int row = 1;
		for (T value : values) {setRowValue(row++, value);}
		super.setChanged();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#noValue()
	 */
	public boolean noValue(){
		return numRows <= 1;
	}

	/**
	 * Gets the value of the specified row.
	 *
	 * @param row the specified row.
	 * @return the value of the row.
	 */
	public T getValue(int row) {
		return (row > 0 && row <= numRows) ? getRowValue(row) : null;
	}

	/**
	 * Gets the value of the first row.
	 *
	 * @return the value of the first row.
	 */
	public T getFirstvalue() {
		return getValue(1);
	}

	/**
	 * Gets the value of the last row.
	 *
	 * @return the value of the last row.
	 */
	public T getLastvalue() {
		return numRows < 1 ? null : getValue(numRows);
	}

	/**
	 * Gets the value of the selected row.
	 *
	 * @return the value of the selected row.
	 */
	public T getSelectedValue() {
		return getValue(selectedRow);
	}

	/**
	 * Adds the value to the grid.
	 *
	 * @param value the value to be added.
	 */
	public void addValue(T value) {
		ArrayList<T> values = getValue();
		values.add(value);
		setValue(values);
	}

	/**
	 * Adds a check box in the specified row and column with the specified value.
	 *
	 * @param row the specified row.
	 * @param col the specified column.
	 * @param checked is true if the check box is checked.
	 */
	public void addCheckBox(int row, int col, boolean checked) {
		CheckBox checkBox = new CheckBox();
		checkBox.setValue(checked);
		field.setWidget(row, col, checkBox);
	}

	/**
	 * Gets the value of a check box in the specified row and column.
	 *
	 * @param row the specified row.
	 * @param col the specified column.
	 * @return true if the check box is checked.
	 */
	public boolean getCheckBox(int row, int col) {
		return ((CheckBox)getWidget(row, col)).getValue();
	}

	/**
	 * Sets the value of a check box in the specified row and column.
	 *
	 * @param row the specified row.
	 * @param col the specified column.
	 * @param checked is true if the check box is checked.
	 */
	public void setCheckBox(int row, int col, boolean checked) {
		((CheckBox)getWidget(row, col)).setValue(checked);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#is(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean is(Widget sender){
		return (sender == field);
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#hasChanged()
	 */
	@Override
	public boolean hasChanged() {
		return false;
	}
}