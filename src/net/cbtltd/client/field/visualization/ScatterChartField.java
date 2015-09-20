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
import com.google.gwt.visualization.client.visualizations.ScatterChart;

/**
 * The Class ScatterChartField extends VisualizationField for scatter charts.
 * Two or more columns are required, all must be numeric.
 * The values in the first column are used for the X-axis.
 * The values in following columns are used for the Y-axis.
 * Each column is displayed with a separate color.
 * Chart X = X Value, Name = Y Name, Y = Y Value
 * @see <pre>http://code.google.com/docreader/#p=gwt-google-apis&s=gwt-google-apis&t=Visualization</pre>
 * @see <pre>http://code.google.com/apis/visualization/documentation/gallery/scatterchart.html</pre>
 */
public class ScatterChartField
extends VisualizationField {
	private ScatterChart field = null;

	public ScatterChartField(
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
		else if (field == null) {VisualizationUtils.loadVisualizationApi(onLoadCallback, ScatterChart.PACKAGE);}
		else {field.draw(getDataTable(), getOptions());}
	}

	/*
	 * Gets the data table version of the current values.
	 * 
	 * @return data table version of values.
	 */
	public DataTable getDataTable() {
		data = DataTable.create();
		Map<Double, Integer>xAxis = new HashMap<Double, Integer>();
		Map<String, Integer>names = new HashMap<String, Integer>();

		int row = 0;
		int col = 1;
		data.addColumn(ColumnType.NUMBER, "X");
		for(VisualizationData value : values){
			Double xValue = value.getX();
			String name = value.getName();
			if (!xAxis.containsKey(xValue)){
				data.addRow();
				data.setValue(row, 0, xValue);
				xAxis.put(xValue, row++);
			}
			if (!names.containsKey(name)){
				data.addColumn(ColumnType.NUMBER, name);
				names.put(name, col++);
			}
			data.setValue(xAxis.get(xValue), names.get(name), value.getY(inverted));
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
	private ScatterChart.Options getOptions() {
		ScatterChart.Options options = ScatterChart.Options.create();
		options.setLegend(position);
		options.setReverseAxis(reversed);
		if (label != null){options.setTitle(label);}
		return options;
	}

	/* Function called when loaded. */
	private final Runnable onLoadCallback = new Runnable() {
		@Override
		public void run() {
			field = new ScatterChart(getDataTable(), getOptions());
			field.addStyleName(style);
			field.addSelectHandler(new SelectHandler() {
				@Override
				public void onSelect(SelectEvent event) {
					if (isEnabled()){
						JsArray<Selection> selections = field.getSelections();
						if (selections != null && selections.length() > 0){
							Selection value = selections.get(0);
							if (value.isCell()) {selectedRow = value.getRow(); selectedCol= value.getColumn();}
							else if (value.isColumn()){selectedCol = value.getColumn();}
							else if (value.isRow()){selectedRow = value.getRow();}
							fireChange(field);
						}
						if (zoom != null) {zoom.run();}
					}
				}
			});
			panel.add(field);
		}
	};
}
