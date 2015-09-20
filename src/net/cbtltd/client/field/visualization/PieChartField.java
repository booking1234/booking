/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field.visualization;

import net.cbtltd.client.HasComponents;
import net.cbtltd.shared.VisualizationData;
import net.cbtltd.shared.api.HasTableService;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Window;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.PieChart;

/**
 * The Class PieChartField extends VisualizationField for pie charts.
 * Two columns. The first column should be a string, and contain the slice label.
 * The second column should be a number, and contain the slice value.
 * Chart Name = Y Name, Y = Y Value
 * @see <pre>http://code.google.com/docreader/#p=gwt-google-apis&s=gwt-google-apis&t=VisualizationGettingStarted</pre>
 * @see <pre>http://code.google.com/apis/visualization/documentation/gallery/piechart.html</pre>
 */
public class PieChartField
extends VisualizationField {
	private PieChart field = null;

	public PieChartField(
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
		else if (field == null) {VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);}
		else {field.draw(getDataTable(), getOptions());}
	}

	/*
	 * Gets the data table version of the current values.
	 * 
	 * @return data table version of values.
	 */
	public DataTable getDataTable() {
		data = DataTable.create();
		data.addColumn(ColumnType.STRING, "Label");
		data.addColumn(ColumnType.NUMBER, "Value");

		int row = 0;
		for (VisualizationData value : values){
			data.addRow();
			data.setValue(row, 0, value.getName());
			data.setValue(row, 1, value.getY());
			row++;
		}
		return data;
	}

	/*
	 * Gets the chart options.
	 * 
	 * @return options the chart options.
	 */
	private PieChart.Options getOptions() {
		PieChart.Options options = PieChart.Options.create();
		options.set3D(raised);
		//options.setColors(colors);
		options.setLegend(position);
		if (label != null){options.setTitle(label);}
		return options;
	}

	/* Function called when loaded. */
	private final Runnable onLoadCallback = new Runnable() {
		@Override
		public void run() {
			field = new PieChart(getDataTable(), getOptions());
			field.addStyleName(style);
			field.addSelectHandler(new SelectHandler() {
				@Override
				public void onSelect(SelectEvent event) {
					if (isEnabled()){
						JsArray<Selection> selections = field.getSelections();
						if (selections != null && selections.length() < 0){
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