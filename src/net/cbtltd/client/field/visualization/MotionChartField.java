/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field.visualization;

import java.util.HashMap;
import java.util.Map;

import net.cbtltd.client.HasComponents;
import net.cbtltd.shared.VisualizationData;
import net.cbtltd.shared.api.HasTableService;

import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.MotionChart;

/**
 * The Class MotionChartField extends VisualizationField for motion charts.
 * The first column must be of type 'string' and contain the entity names
 * (e.g., "Apples", "Oranges", "Bananas" in the example above).
 * The second column must contain time values. Time can be expressed in a few different ways:
 * Years. Column type: 'number'. Example: 2008.
 * Month, day and year. Column type should be 'date' and the values should be javascript Date instances.
 * Week numbers. Column type should be 'string' and the values should have the pattern YYYYWww,
 * which conforms to ISO 8601. Example: '2008W03'.
 * Quarters. Column type should be 'string' and the values should have the pattern YYYYQq,
 * which conforms to ISO 8601. Example: '2008Q3'.
 * Subsequent columns can be of type 'number' or 'string'.
 * Number columns will show up in the dropdown menus for X, Y, Color and Size axes.
 * String columns will only appear in the dropdown menu for Color.
 * Chart Id = X Category, Date = X Date, Name = Y Name, Y = Y Value
 * @see <pre>http://code.google.com/docreader/#p=gwt-google-apis&s=gwt-google-apis&t=Visualization</pre>
 * @see <pre>http://code.google.com/apis/visualization/documentation/gallery/motionchart.html</pre>
 */
public class MotionChartField
extends VisualizationField {
	private MotionChart field = null;

	public MotionChartField(
			HasComponents form,
			short[] permission,
			HasTableService action, 
			String label,
			int tab) {
		super(form, permission, action, label, tab);
	}

	/** Draws the chart, calling the onLoadCallback when first used. */
	@Override
	protected void draw() {
		if (values == null || values.isEmpty()) {
			if (field != null) {field.removeFromParent();}
			field = null;
		}
		else if (field == null) {VisualizationUtils.loadVisualizationApi(onLoadCallback, MotionChart.PACKAGE);}
		else {field.draw(getDataTable(), getOptions());}
	}

	/*
	 * Gets the data table version of the current values.
	 * 
	 * @return data table version of values.
	 */
	public DataTable getDataTable() {
		DataTable data = DataTable.create();
		Map<String, Integer>names = new HashMap<String, Integer>();
		data.addColumn(ColumnType.STRING, "Category");
		data.addColumn(ColumnType.DATE, "Date");
		int row = 0;
		int col = 2;
		for (VisualizationData value : values){
			data.addRow();
			String name = value.getName();
			if (!names.containsKey(name)){
				data.addColumn(ColumnType.NUMBER, name);
				names.put(name, col++);
			}
			data.setValue(row, 0, value.getName());
			data.setValue(row, 1, value.getDate());
			data.setValue(row, names.get(name), value.getY());
			row++;
		}
		return data;
	}

	/*
	 * Gets the chart options.
	 * 
	 * @return options the chart options.
	 */
	private MotionChart.Options getOptions() {
		MotionChart.Options options = MotionChart.Options.create();
		return options;
	}

	/* Function called when loaded. */
	private final Runnable onLoadCallback = new Runnable() {
		@Override
		public void run() {
			field = new MotionChart(getDataTable(), getOptions());
			field.addStyleName(style);
			panel.add(field);
		}
	};
}
