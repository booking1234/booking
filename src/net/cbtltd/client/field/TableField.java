/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.field.table.ActionHeader;
import net.cbtltd.client.field.table.ColumnModel;
import net.cbtltd.client.field.table.SortableHeader;
import net.cbtltd.client.field.table.TableError;
import net.cbtltd.client.field.table.TableExecutor;
import net.cbtltd.client.field.table.TextHeader;
import net.cbtltd.client.resource.table.TableBundle;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.api.HasTable;
import net.cbtltd.shared.api.HasTableService;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionModel;

/**
 * The Class TableField is to display and edit a table having rows of type T.
 *
 * @param <T> the generic row type.
 */
public class TableField<T>
extends AbstractField<ArrayList<T>> {

	private static final CellTable.Resources tableResources = GWT.create(TableBundle.class); //TODO: specialize
	private static final SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	private final FlowPanel panel = new FlowPanel();
	private final FlowPanel emptyValue = new FlowPanel();
	private final CellTable<T> table; // = new CellTable<T>();
	private final TablePager pager; // = new TablePager(TextLocation.CENTER);
	private Label label;
	
	/** The has footer. */
	protected boolean hasFooter = true;
	private TableRequest tableRequest;
	private TableError tableError;
	private TableExecutor tableExecutor;
//	private int startrow = 0;
	
	  /**
	   * Construct a {@link SimplePager} with the specified resources.
	   * 
	   * @param location the location of the text relative to the buttons
	   * @param pagerResources the {@link Resources} to use
	   * @param showFastForwardButton if true, show a fast-forward button that
	   *          advances by a larger increment than a single page
	   * @param fastForwardRows the number of rows to jump when fast forwarding
	   * @param showLastPageButton if true, show a button to go the the last page
	   */
	private class TablePager extends SimplePager {
		
		public TablePager(TextLocation location) {
			super(location, pagerResources, false, 0, true);
		}
		
		public void onRangeOrRowCountChanged() {
			//send(request);
		}

		@Override
		public void firstPage() {
			super.firstPage();
			tableRequest.execute(true);
		}

		@Override
		public void lastPage() {
			super.lastPage();
			tableRequest.execute(true);			
		}

		@Override
		public void nextPage() {
			super.nextPage();
//			startrow += table.getPageSize();
			tableRequest.execute(true);
		}

		@Override
		public void previousPage() {
			super.previousPage();
			tableRequest.execute(true);			
		}
	};

	/**
	 * The Class TableRequest.
	 */
	public class TableRequest
	extends GuardedRequest<Table<T>> {

		private HasTableService action;
		private String orderby = HasTable.ORDER_BY_NAME;
		
		/**
		 * Instantiates a new table request.
		 *
		 * @param action the action
		 */
		public TableRequest(HasTableService action){this.action = action;}
		
		/* (non-Javadoc)
		 * @see net.cbtltd.client.GuardedRequest#error()
		 */
		protected boolean error() {return tableError == null ? false : tableError.error();}
		
		/**
		 * Sets the orderby.
		 *
		 * @param orderby the new orderby
		 */
		protected void setOrderby(String orderby) {this.orderby = orderby;}
		
		/**
		 * No orderby.
		 *
		 * @return true, if successful
		 */
		protected boolean noOrderby() {return orderby == null || orderby.isEmpty();}

		/* (non-Javadoc)
		 * @see net.cbtltd.client.GuardedRequest#send()
		 */
		protected void send() {
			if (tableExecutor != null) {tableExecutor.execute(action);} // Populates the action with parameters to send to server to get rows.
			action.setStartrow(table.getPageStart());
			action.setNumrows(table.getPageSize());
			action.setOrderby(orderby);
			super.send(action);
		}

		/* (non-Javadoc)
		 * @see net.cbtltd.client.GuardedRequest#receive(net.cbtltd.shared.api.HasResponse)
		 */
		protected void receive(Table<T> response) {
			if (response == null || response.noValue()) {
				table.setRowCount(0, true);
				setEmpty(true);
			}
			else {
				table.setRowData(action.getStartrow(), resetValue(response.getValue()));
				table.setRowCount(response.getDatasize(), true);
				pager.setVisible(response.getDatasize() > table.getPageSize());
				setEmpty(false);
			}
			setChanged();
			fireChange(TableField.this);
		}
	}

	private final List<SortableHeader> sortableHeaders = new ArrayList<SortableHeader>();

	/**
	 * Instantiates a new table field.
	 *
	 * @param form the form
	 * @param permission the permission
	 * @param action the action
	 * @param pageSize the page size
	 * @param tab the tab
	 */
	public TableField (
			HasComponents form,
			short[] permission,
			HasTableService action, 
			int pageSize,
			int tab) {
		this(form, permission, action, null, null, null, pageSize, tab);
	}

	/**
	 * Instantiates a new table field.
	 *
	 * @param form the form
	 * @param permission the permission
	 * @param action the action
	 * @param label the label
	 * @param pageSize the page size
	 * @param tab the tab
	 */
	public TableField (
			HasComponents form,
			short[] permission,
			HasTableService action, 
			String label,
			int pageSize,
			int tab) {
		this(form, permission, action, null, null, label, pageSize, tab);
	}

	/**
	 * Instantiates a new table field.
	 *
	 * @param form the form
	 * @param permission the permission
	 * @param action the action
	 * @param selectionModel the selection model
	 * @param pageSize the page size
	 * @param tab the tab
	 */
	public TableField (
			HasComponents form,
			short[] permission,
			HasTableService action, 
			SelectionModel<T> selectionModel,
			int pageSize,
			int tab) {
		this(form, permission, action, selectionModel, null, null, pageSize, tab);
	}

	/**
	 * Instantiates a new table field.
	 *
	 * @param form the form
	 * @param permission the permission
	 * @param action the action
	 * @param selectionModel the selection model
	 * @param keyProvider the key provider
	 * @param pageSize the page size
	 * @param tab the tab
	 */
	public TableField (
			HasComponents form,
			short[] permission,
			HasTableService action, 
			SelectionModel<T> selectionModel,
			ProvidesKey<T> keyProvider,
			int pageSize,
			int tab) {
		this(form, permission, action, selectionModel, keyProvider, null, pageSize, tab);
	}

	/**
	 * Instantiates a new table field.
	 *
	 * @param form the form
	 * @param permission the permission
	 * @param action the action
	 * @param selectionModel the selection model
	 * @param label the label
	 * @param pageSize the page size
	 * @param tab the tab
	 */
	public TableField (
			HasComponents form,
			short[] permission,
			HasTableService action, 
			SelectionModel<T> selectionModel,
			String label,
			int pageSize,
			int tab) {
		this(form, permission, action, selectionModel, null, label, pageSize, tab);
	}

	/**
	 * Instantiates a new table field.
	 *
	 * @param form the form
	 * @param permission the permission
	 * @param action the action
	 * @param selectionModel the selection model
	 * @param keyProvider the key provider
	 * @param label the label
	 * @param pageSize the page size
	 * @param tab the tab
	 */
	public TableField (
			HasComponents form,
			short[] permission,
			HasTableService action, 
			SelectionModel<T> selectionModel,
			ProvidesKey<T> keyProvider,
			String label,
			int pageSize,
			int tab) {

		initialize(panel, form, permission, CSS.cbtTableField());

		tableRequest = new TableRequest(action);

		if (label != null) {
			this.label = new Label(label);
			this.label.addStyleName(CSS.cbtTableFieldLabel());
			this.label.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {showHelp();}
			});
			panel.add(this.label);
		}

		table = new CellTable<T>(pageSize, tableResources, keyProvider);
//		{
//			  @Override
//			  protected void onLoadingStateChanged(LoadingState state) {
//				  table.setVisible(state == LoadingState.LOADED);
//				  Window.alert("onLoadingStateChanged");
//			    Widget message = null;
//			    if (state == LoadingState.LOADING) {
//			      // Loading indicator.
//			      message = loadingIndicatorContainer;
//			    } else if (state == LoadingState.LOADED && getPresenter().isEmpty()) {
//			      // Empty table.
//			      message = emptyTableWidgetContainer;
//			    }
//
//			    // Switch out the message to display.
//			    if (message != null) {
//			      messagesPanel.showWidget(messagesPanel.getWidgetIndex(message));
//			    }
//
//			    // Adjust the colspan of the messages panel container.
//			    tbodyLoadingCell.setColSpan(Math.max(1, getRealColumnCount()));
//
//			    // Show the correct container.
//			    showOrHide(getChildContainer(), message == null);
//			    showOrHide(tbodyLoading, message != null);
//
//			    // Fire an event.
//			    super.onLoadingStateChanged(state);
//			  }
//		}; //TODO: add loading indicator
		
		table.setPageStart(0);
		table.setPageSize(pageSize);

		if (selectionModel == null) {
			//table.setSelectionEnabled(false);
			table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
		}
		else {
			table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
			table.setSelectionModel(selectionModel);
		}

//		setSize("100%", "100%"); //keep for scroll bars
		panel.add(table);

		pager = new TablePager(TextLocation.CENTER);
		pager.setDisplay(table);
		pager.setPage(0);
		pager.setPageSize(pageSize);
		pager.setRangeLimited(true);
		pager.setVisible(false);
		pager.addStyleName(CSS.cbtTableFieldNavigator());
		panel.add(pager);
		
		emptyValue.addStyleName(CSS.cbtTableFieldEmpty());
		emptyValue.setVisible(false);
		panel.add(emptyValue);
	}

	/**
	 * Gets the sortable header.
	 *
	 * @param header description
	 * @param columnName of sort column in dbms
	 * @return sortable header
	 */
	protected SortableHeader getSortableHeader(String header, String columnName) {
		final SortableHeader sortableHeader = new SortableHeader(header, columnName);
		if (tableRequest.noOrderby()) {tableRequest.setOrderby(columnName);}
		sortableHeaders.add(sortableHeader);
		sortableHeader.setUpdater(new ValueUpdater<String>() {
			public void update(String value) {
				sortableHeader.setSorted(true);
				sortableHeader.toggleReverseSort();

				for (SortableHeader otherHeader : sortableHeaders) {
					if (otherHeader != sortableHeader) {
						otherHeader.setSorted(false);
						otherHeader.setReverseSort(true);
					}
				}

				if (sortableHeader.noColumnName() || !pager.isVisible()) { //TODO: also if less than page ie: no navigator
					//local sort
					Column<T, ?> column = table.getColumn(sortableHeaders.indexOf(sortableHeader));
					List<T> values = getSortedValues(column, table.getDisplayedItems(), sortableHeader.isReverseSort());
					table.setRowData(0, values);
				}
				else {
					// dbms sort
					String sortCol = sortableHeader.getColumnName(); // Request sorted rows.
					if (sortableHeader.isReverseSort()) {sortCol += HasTable.ORDER_BY_DESC;}
					pager.firstPage(); 	// Go to the first page of the newly-sorted results
					tableRequest.setOrderby(sortCol);
					pager.firstPage(); 	// Go to the first page of the newly-sorted results
					tableRequest.execute();
				}
			}
		});
		return sortableHeader;
	}

	/**
	 * Gets the sorted values.
	 *
	 * @param column the column
	 * @param values the values
	 * @param isReverseSort the is reverse sort
	 * @return the sorted values
	 */
	public List<T> getSortedValues(Column<T, ?> column, List<T> values, boolean isReverseSort) {
		ArrayList <ColumnModel<T>> columnModels = new ArrayList<ColumnModel<T>>();
		for (T value : values ) {columnModels.add(new ColumnModel<T>(column, value));}
		if (isReverseSort) {Collections.reverse(columnModels);}
		else {Collections.sort(columnModels);}
		values = new ArrayList<T>();
		for (ColumnModel<T> columnModel : columnModels ) {values.add(columnModel.getModel());}
		return values;
	}

	/**
	 * Removes the column.
	 *
	 * @param column the column
	 */
	public void removeColumn(Column<T, ?> column) {
		try {table.removeColumn(column);}
		catch(IllegalArgumentException x) {}
	}

	/**
	 * Adds the column.
	 *
	 * @param column the column
	 */
	public void addColumn(Column<T, ?> column) {
		try {table.addColumn(column);}
		catch(IllegalArgumentException x) {}
	}

	/**
	 * Adds the column.
	 *
	 * @param column the column
	 * @param label the label
	 */
	public void addColumn(Column<T, ?> column, String label) {
		if (hasFooter) {table.addColumn(column, new TextHeader(label), new TextHeader(""));}
		else {table.addColumn(column, new TextHeader(label));}
	}

	/**
	 * Adds the column.
	 *
	 * @param column the column
	 * @param header the header
	 */
	public void addColumn(Column<T, ?> column, TextHeader header) {
		if (hasFooter) {table.addColumn(column, header, new TextHeader(""));}
		else {table.addColumn(column, header);}
	}

	/**
	 * Adds the column.
	 *
	 * @param column the column
	 * @param header the header
	 */
	public void addColumn(Column<T, ?> column, ActionHeader<?> header) {
		if (hasFooter) {table.addColumn(column, header, new TextHeader(""));}
		else {table.addColumn(column, header);}
	}

	/**
	 * Adds the column.
	 *
	 * @param col the col
	 * @param headerHtml the header html
	 */
	public void addColumn(Column<T, ?> col, SafeHtml headerHtml) {
		 table.addColumn(col, headerHtml);
	}

	 /**
 	 * Adds the column.
 	 *
 	 * @param column the column
 	 * @param label the label
 	 * @param columnName indicates that the column is sortable on the database column named columnName.
 	 * If columnName is null then the sort is performed on the in memory local table without a call to the dbms.
 	 */
	public void addColumn(final Column<T, ?> column, final String label, final String columnName) {
		if (hasFooter) {table.addColumn(column, getSortableHeader(label, columnName), new TextHeader(""));}
		else {table.addColumn(column, getSortableHeader(label, columnName));}
	}

	/**
	 * Adds the boolean column.
	 *
	 * @param column the column
	 * @param label the label
	 */
	public void addBooleanColumn(Column<T, Boolean> column, String label) {
		if (hasFooter) {table.addColumn(column, new TextHeader(label), new TextHeader(""));}
		else {table.addColumn(column, new TextHeader(label));}
	}

	/**
	 * Adds the boolean column.
	 *
	 * @param column the column
	 * @param label the label
	 * @param columnName the column name
	 */
	public void addBooleanColumn(Column<T, Boolean> column, String label, final String columnName) {
		if (hasFooter) {table.addColumn(column, getSortableHeader(label, columnName), new TextHeader(""));}
		else {table.addColumn(column, getSortableHeader(label, columnName));}
	}

	/**
	 * Adds the date column.
	 *
	 * @param column the column
	 * @param label the label
	 */
	public void addDateColumn(Column<T, Date> column, String label) {
		if (hasFooter) {table.addColumn(column, new TextHeader(label), new TextHeader(""));}
		else {table.addColumn(column, new TextHeader(label));}
	}

	/**
	 * Adds the date column.
	 *
	 * @param column the column
	 * @param label the label
	 * @param columnName the column name
	 */
	public void addDateColumn(Column<T, Date> column, String label, final String columnName) {
		if (hasFooter) {table.addColumn(column, getSortableHeader(label, columnName));}
		else {table.addColumn(column, getSortableHeader(label, columnName), new TextHeader(""));}
	}

	/**
	 * Adds the number column.
	 *
	 * @param column the column
	 * @param label the label
	 */
	public void addNumberColumn(Column<T, Number> column, String label) {
		column.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		if (hasFooter) {table.addColumn(column, new TextHeader(label), new TextHeader(""));}
		else {table.addColumn(column, new TextHeader(label));}
	}

	/**
	 * Adds the number column.
	 *
	 * @param column the column
	 * @param label the label
	 * @param columnName the column name
	 */
	public void addNumberColumn(Column<T, Number> column, String label, final String columnName) {
		column.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		if (hasFooter) {table.addColumn(column, getSortableHeader(label, columnName), new TextHeader(""));}
		else {table.addColumn(column, getSortableHeader(label, columnName));}
	}

	/**
	 * Adds the string column.
	 *
	 * @param column the column
	 * @param label the label
	 * @return the text header
	 */
	public TextHeader addStringColumn(Column<T, String> column, String label) {
		TextHeader header = new TextHeader(label);
		if (hasFooter) {table.addColumn(column, header, new TextHeader(""));}
		else {table.addColumn(column, header);}
		return header;
	}

	/**
	 * Adds the string column.
	 *
	 * @param column the column
	 * @param label the label
	 * @param columnName the column name
	 */
	public void addStringColumn(Column<T, String> column, String label, final String columnName) {
		if (hasFooter) {table.addColumn(column, getSortableHeader(label, columnName), new TextHeader(""));}
		else {table.addColumn(column, getSortableHeader(label, columnName));}
	}

	/**
	 * Sets the table error.
	 *
	 * @param tableError the new table error
	 */
	public void setTableError(TableError tableError) {
		this.tableError = tableError;
	}

	/**
	 * Sets the table executor.
	 *
	 * @param tableExecutor the new table executor
	 */
	public void setTableExecutor(TableExecutor tableExecutor) {
		this.tableExecutor = tableExecutor;
	}

	/**
	 * Redraw.
	 */
	public void redraw() {
		table.redraw();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int tab){
		//		pager.setTabIndex(tab);
	}

	private void setEmpty(boolean empty) {
		if (emptyValue.getWidgetCount() > 0) {
			table.setVisible(!empty);
			if (empty) {pager.setVisible(false);}
			emptyValue.setVisible(empty);
		}
	}
	
	/**
	 * Sets the empty value.
	 *
	 * @param content the new empty value
	 */
	public void setEmptyValue(Widget content) {
		emptyValue.add(content);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setFocus(boolean)
	 */
	@Override
	public void setFocus(boolean focus){
		//pager.setFocus(focus);
	}

	/**
	 * Sets the empty style.
	 *
	 * @param style the new empty style
	 */
	public void setEmptyStyle(String style) {
		emptyValue.addStyleName(style);
	}

	/**
	 * Sets the field style.
	 *
	 * @param style the new field style
	 */
	public void setFieldStyle(String style) {
		table.addStyleName(style);
	}

	/**
	 * Sets the label style.
	 *
	 * @param style the new label style
	 */
	public void setLabelStyle(String style) {
		if (label != null) {label.addStyleName(style);}
	}

	/**
	 * Sets the orderby.
	 *
	 * @param orderBy the new orderby
	 */
	public void setOrderby(String orderBy) {tableRequest.setOrderby(orderBy);}

	public void setPageSize(int pageSize) {
		table.setPageSize(pageSize);
		execute(true);
	}

	/**
	 * Sets the width.
	 *
	 * @param value the value
	 * @param fixed the fixed
	 */
	public void setWidth(String value, boolean fixed){
		table.setWidth(value, fixed);
	}

	/**
	 * Sets the column width.
	 *
	 * @param column the column
	 * @param width the width
	 * @param unit the unit
	 */
	public void setColumnWidth(Column<T, ?> column, double width, Unit unit) {
		table.setColumnWidth(column, width, unit);
	}

	/**
	 * Execute.
	 *
	 * @param refresh the refresh
	 */
	public void execute(boolean refresh) {
		pager.firstPage();
		if (tableRequest != null) {tableRequest.execute(refresh);}
	}
	
	/**
	 * Execute.
	 */
	public void execute() {
		pager.firstPage();
		if (tableRequest != null) {tableRequest.execute();}
	}
	
	/**
	 * Sets the checks for footer.
	 *
	 * @param hasFooter the new checks for footer
	 */
	public void setHasFooter(boolean hasFooter) {this.hasFooter = hasFooter;}

	/**
	 * Gets the first item.
	 *
	 * @return the first item
	 */
	public T getFirstItem() {
		return noValue() ? null : getValue().get(0);
	}

	/**
	 * Reset value.
	 *
	 * @param values the values
	 * @return the array list
	 */
	protected ArrayList<T> resetValue(ArrayList<T> values) { //override in ScheduleField
		return values;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setValue(java.lang.Object)
	 */
	public void setValue(ArrayList<T> values) {
		setValues(values);
		setChanged();
	}
	
	private void setValues(ArrayList<T> values) {
		if (values == null || values.isEmpty()) {clear();}
		else {
			values = resetValue(values);
			table.setRowCount(values.size(), true);
			table.setRowData(0, values);
			setEmpty(false);
		}
		table.redraw();
	}

	/**
	 * Clear table values.
	 */
	public void clear() {
		table.setRowCount(0, true);
		setEmpty(true);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#getValue()
	 */
	public ArrayList<T> getValue() {
		return new ArrayList<T>(table.getDisplayedItems());
	}

	/**
	 * Adds the value to the table.
	 *
	 * @param value the value to be added
	 */
	public void addValue(T value) {
		ArrayList<T> values = getValue();
		values.add(value);
		setValue(values);
	}

	/**
	 * Adds the list of values to the table.
	 *
	 * @param value the list of values to be added
	 */
	public void addValue(ArrayList<T> value) {
		ArrayList<T> values = getValue();
		values.addAll(value);
		setValue(values);
	}

	/**
	 * Removes the value from the table.
	 *
	 * @param value the value to be removed.
	 */
	public void replaceValue(T oldvalue, T value) {
		ArrayList<T> values = getValue();
		values.remove(oldvalue);
		values.add(value);
		setValues(values);
	}

	/**
	 * Removes the value from the table.
	 *
	 * @param value the value to be removed.
	 */
	public void removeValue(T value) {
		ArrayList<T> values = getValue();
		values.remove(value);
		setValues(values);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#noValue()
	 */
	public boolean noValue() {
		return getValue() == null || getValue().isEmpty();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#hasValue()
	 */
	public boolean hasValue() {
		return !noValue();
	}
	
	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#is(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean is(Widget sender){return (sender == this);}
	
	/**
	 * Deselect.
	 */
	public void deselect() {
		SelectionModel<? super T> selectionModel = table.getSelectionModel();
		if (selectionModel != null) {selectionModel.setSelected(null, false);}
	}
}