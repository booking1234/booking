/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field.visualization;

import java.util.ArrayList;

import net.cbtltd.client.GuardedRequest;
import net.cbtltd.client.HasComponents;
import net.cbtltd.client.field.AbstractField;
import net.cbtltd.client.field.table.TableError;
import net.cbtltd.client.field.table.TableExecutor;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.VisualizationData;
import net.cbtltd.shared.api.HasTableService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.LegendPosition;

// TODO: Auto-generated Javadoc
/** The Class VisualizationField is the abstract parent of the fields that display data in graphical format. */
public abstract class VisualizationField
extends AbstractField<ArrayList<VisualizationData>> {
	
	/** The panel. */
	protected final VerticalPanel panel = new VerticalPanel();
	
	/** The empty value. */
	protected final FlowPanel emptyValue = new FlowPanel();
	
	/** The loading image. */
	//protected final Image loadingImage = new Image(BUNDLE.cellTableLoading());
	
	/** The values. */
	protected ArrayList<VisualizationData> values;
	
	/** The data. */
	protected DataTable data;
	
	/** The label. */
	protected String label;
	
	/** The style. */
	protected String style = CSS.cbtVisualizationField();
	
	/** If absolute is true Ymin = min(0,Ymin) else = Ymin. */
	protected boolean absolute = false;
	/** If cumulative is true accumulate Y values. */
	protected boolean cumulative = false; 
	/** If inverted is true inverte the Y axis. */
	protected boolean inverted = false;
	/** If raised is true set the 3D option. */
	protected boolean raised = true; 
	/** If reversed is true reverse the Y values. */
	protected boolean reversed = false;
	/** If smoothed is true smoothe the Y values. */
	protected boolean smoothed = false;
	/** The stacked set the stack option. */
	protected boolean stacked = false;
	/** The position of the legent. */
	protected LegendPosition position = LegendPosition.BOTTOM;
	/** The Y axis minimum value. */
	protected Double minimum;
	/** The Y axis maximum value. */
	protected Double maximum;
	/** The field height in pixels. */
	protected int height = 250;
	/** The field width in pixels. */
	protected int width = 350;
	/** The currently selected column or -1 if not selected. */
	protected int selectedCol = -1;
	/** The currently selected row or -1 if not selected. */
	protected int selectedRow = -1;
	/** The zoom function run when a data point is selected . */
	protected Runnable zoom;
	
	private Runnable receiver;
	private TableRequest tableRequest;
	private TableError tableError;
	private TableExecutor tableExecutor;
	
	/* The guarded request to obtain visualization data table from the server to be displayed graphically. */
	private class TableRequest
	extends GuardedRequest<Table<VisualizationData>> {

		private HasTableService action;
		protected TableRequest(HasTableService action){this.action = action;}
		protected boolean error() {return tableError == null ? false : tableError.error();}
		protected void setAction(HasTableService action) {this.action = action;}
		protected void send() {
			if (tableExecutor != null) {
				tableExecutor.execute(action);
//				loadingImage.setVisible(true);
			}
			super.send(action);
		}

		protected void receive(Table<VisualizationData> response) {
//			loadingImage.setVisible(false);
			if (response == null || response.noValue()) {setEmpty(true);}
			else {
				setValue(response.getValue());
				setEmpty(false);
			}
			if (receiver != null) {receiver.run();}
		}
	}

	/**
	 * Instantiates a new visualization field.
	 * 
	 * @param form is the form or other HasComponents element that contains the field.
	 * @param permission that controls the visibility and accessibility of the field.
	 * @param action to populate the visualization field with a request to a VisualizationData service.
	 * @param label is the optional text to identify the field.  
	 * @param tab index of the field.
	 */
	public VisualizationField (
			HasComponents form,
			short[] permission,
			HasTableService action,
			String label,
			int tab) {
		initialize(panel, form, permission, CSS.cbtVisualizationField());

		this.tableRequest = new TableRequest(action);
		this.label = label;
		setTabIndex(tab);
		VerticalPanel loadingPanel = new VerticalPanel();
		loadingPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		loadingPanel.addStyleName(CSS.cbtVisualizationFieldLoading());
//		loadingImage.setVisible(false);
//		loadingPanel.add(loadingImage);
		panel.add(loadingPanel);
		emptyValue.addStyleName(CSS.cbtTableFieldEmpty());
		emptyValue.setVisible(false);
		panel.add(emptyValue);
	}

	/** Draws the chart, calling the onLoadCallback when first used. */
	protected abstract void draw();

	/**
	 * Sets the action to obtain visualization data table.
	 *
	 * @param action the new action
	 */
	public void setAction(HasTableService action) {
		if (tableRequest == null) {tableRequest = new TableRequest(action);}
		else {tableRequest.setAction(action);}
	}
	
	/**
	 * Sets the receiver of visualization data table.
	 *
	 * @param receiver the new receiver
	 */
	public void setReceiver(Runnable receiver) {
		this.receiver = receiver;
	}

	/**
	 * Sets the zoom pop up panel.
	 *
	 * @param zoom the new zoom pop up panel.
	 */
	public void setZoom(Runnable zoom) {
		this.zoom = zoom;
	}

	/**
	 * Sets the table error guard.
	 *
	 * @param tableError the new table error guard.
	 */
	public void setTableError(TableError tableError) {this.tableError = tableError;}
	
	/**
	 * Sets the table executor.
	 *
	 * @param tableExecutor the new table executor.
	 */
	public void setTableExecutor(TableExecutor tableExecutor) {this.tableExecutor = tableExecutor;}
	
	/**
	 * Executes the action to refresh the visualization data table.
	 *
	 * @param refresh if true forces a refresh of the visualization data table.
	 */
	public void execute(boolean refresh) {
		if (tableRequest != null) {tableRequest.execute(refresh);}
	}
	
	/**
	 * Executes the action to refresh the visualization data table.
	 */
	public void execute() {
		if (tableRequest != null) {tableRequest.execute();}
	}

	/**
	 * Sets the widget to be displayed when there is no data.
	 *
	 * @param content the new empty widget.
	 */
	public void setEmptyValue(Widget content) {
		emptyValue.add(content);
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(int tab){}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setFocus(boolean)
	 */
	@Override
	public void setFocus(boolean focussed){}

	/**
	 * Sets the CSS style of the check box.
	 *
	 * @param style the CSS style of the check box.
	 */
	public void setFieldStyle(String style) {
		this.style = style;
	}

	/**
	 * Sets the absolute flag.
	 *
	 * @param absolute the new absolute flag.
	 */
	public void setAbsolute(boolean absolute) {
		this.absolute = absolute;
	}

	/**
	 * Sets the cumulative flag.
	 *
	 * @param cumulative the new cumulative flag.
	 */
	public void setCumulative(boolean cumulative) {
		this.cumulative = cumulative;
	}

	/**
	 * Sets the inverted flag.
	 *
	 * @param inverted the new inverted flag.
	 */
	public void setInverted(boolean inverted) {
		this.inverted = inverted;
	}

	/**
	 * Sets the minimum value.
	 *
	 * @param minimum the new minimum value.
	 */
	public void setMinimum(Double minimum) {
		this.minimum = minimum;
	}

	/**
	 * Sets the maximum value.
	 *
	 * @param maximum the new maximum value.
	 */
	public void setMaximum(Double maximum) {
		this.maximum = maximum;
	}

	/**
	 * Sets the position of the legend.
	 *
	 * @param position the new position.
	 */
	public void setPosition(LegendPosition position) {
		this.position = position;
	}

	/**
	 * Sets the value range.
	 *
	 * @param minimum the minimum value.
	 * @param maximum the maximum value.
	 */
	public void setRange(Double minimum, Double maximum) {
		this.minimum = minimum;
		this.maximum = maximum;
	}

	/**
	 * Sets the field size.
	 *
	 * @param width the width in pixels.
	 * @param height the height in pixels.
	 */
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Sets the smoothed flag.
	 *
	 * @param smoothed the new smoothed flag.
	 */
	public void setSmoothed(boolean smoothed) {
		this.smoothed = smoothed;
	}

	private boolean isSelected() {
		return selectedCol >=0 && selectedRow >= 0;
	}

	/**
	 * Sets the label.
	 *
	 * @param label the new label.
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#setValue(java.lang.Object)
	 */
	public void setValue(ArrayList<VisualizationData> values) {
		this.values = values;
		setEmpty(noValue());
		if (hasValue()) {draw();}
		super.setChanged();
	}

	/* 
	 * Sets if the field has no data.
	 * 
	 * @param is true if the field has no data.
	 */
	private void setEmpty(boolean empty) {
		if (emptyValue.getWidgetCount() > 0) {
			emptyValue.setVisible(empty);
		}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#getValue()
	 */
	public ArrayList<VisualizationData> getValue() {
		return values;
	}

	/**
	 * Gets the currently selected value.
	 *
	 * @return the selected value.
	 */
	public String getSelectedValue() {
		if (isSelected()){return data.getFormattedValue(selectedRow, selectedCol);}
		else {return null;}
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#noValue()
	 */
	public boolean noValue() {
		return values == null || values.isEmpty();
	}

	/* (non-Javadoc)
	 * @see net.cbtltd.client.field.AbstractField#is(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean is(Widget sender){
		return (sender == this);
	}
}
