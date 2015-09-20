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

import com.google.gwt.core.client.JsArray;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.AreaChart;

/**
 * The Class AreaChartField extends VisualizationField for area charts.
 * The first X column should be a string, and contain the category label.
 * Any number of Y columns can follow, all must be numeric.
 * Each column is displayed as a separate line.
 * Chart Id = X Category, Name = Y Name, Y = Y Value
 * @see <pre>http://code.google.com/docreader/#p=gwt-google-apis&s=gwt-google-apis&t=Visualization</pre>
 * @see <pre>http://code.google.com/apis/visualization/documentation/gallery/areachart.html</pre>
 */
public class AreaChartField
extends VisualizationField {
	private AreaChart field = null;

	public AreaChartField(
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
		else if (field == null) {VisualizationUtils.loadVisualizationApi(onLoadCallback, AreaChart.PACKAGE);}
		else {field.draw(getDataTable(), getOptions());}
	}

	/*
	 * Gets the data table version of the current values.
	 * 
	 * @return data table version of values.
	*/
	private DataTable getDataTable() {
		data = DataTable.create();
		Map<String, Integer>categories = new HashMap<String, Integer>();
		Map<String, Integer>names = new HashMap<String, Integer>();

		int row = 0;
		int col = 1;
		data.addColumn(ColumnType.STRING, "Category");
		for(VisualizationData value : values){
			String category = value.getCategory();
			String name = value.getName();
			if (!categories.containsKey(category)){
				data.addRow();
				data.setValue(row, 0, category);
				categories.put(category, row++);
			}
			if (!names.containsKey(name)){
				data.addColumn(ColumnType.NUMBER, name);
				names.put(name, col++);
			}
			data.setValue(categories.get(category), names.get(name), value.getY(inverted));
		}
		if (cumulative){
			double[] totals = new double[data.getNumberOfColumns()];
			for (row = 0; row < data.getNumberOfRows(); row++){
				for (col = 1; col < data.getNumberOfColumns(); col++){
					totals[col] += data.getValueDouble(row, col);
					data.setValue(row, col, totals[col]);
				}
			}
		}
		return data;
	}

	/*
	 * Gets the chart options.
	 * 
	 * @return options the chart options.
	 */
	private AreaChart.Options getOptions() {
		AreaChart.Options options = AreaChart.Options.create();
		options.setLegend(position);
		options.setReverseAxis(reversed);
//		options.setSize(width, height);
		if (label != null){options.setTitle(label);}
		return options;
	}

	/* Function called when loaded. */
      private final Runnable onLoadCallback = new Runnable() {
		@Override
		public void run() {
			field = new AreaChart(getDataTable(),  getOptions());
			field.addStyleName(style);
			field.addSelectHandler(new SelectHandler() {
				@Override
				public void onSelect(SelectEvent event) {
					if (isEnabled()){
						JsArray<Selection> selections = field.getSelections();
						if (selections == null || selections.length() == 0){return;}
						Selection value = selections.get(0);
						if (value.isCell()) {selectedRow = value.getRow(); selectedCol= value.getColumn();}
						else if (value.isColumn()){selectedCol = value.getColumn();}
						else if (value.isRow()){selectedRow = value.getRow();}
						fireChange(field);
					}
				}
			});
			panel.add(field);
		}
	};
}


