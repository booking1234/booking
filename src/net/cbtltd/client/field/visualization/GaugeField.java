/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field.visualization;

import net.cbtltd.client.HasComponents;
import net.cbtltd.shared.VisualizationData;
import net.cbtltd.shared.api.HasTableService;

import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.Gauge;

/**
 * The Class GaugeField extends VisualizationField for guage charts.
 * Each numeric value is displayed as a gauge. Two alternative data formats are supported:
 * Two columns. The first column should be a string, and contain the gauge label. 
 * The second column should be a number, and contain the gauge value.
 * Any number of numeric columns. The label of each gauge is the column's label.
 * Chart Name = Y Name, Y = Y Value
 * @see <pre>http://code.google.com/docreader/#p=gwt-google-apis&s=gwt-google-apis&t=Visualization</pre>
 * @see <pre>http://code.google.com/apis/visualization/documentation/gallery/gauge.html</pre>
 */

public class GaugeField 
extends VisualizationField {
	private Gauge field = null;

	public GaugeField(
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
		else if (field == null) {VisualizationUtils.loadVisualizationApi(onLoadCallback, Gauge.PACKAGE);}
		else {field.draw(getDataTable(), getOptions());}
	}

	/*
	 * Gets the data table version of the current values.
	 * 
	 * @return data table version of values.
	 */
	private DataTable getDataTable() {
		data = DataTable.create();
		data.addColumn(ColumnType.STRING, "Label");
		data.addColumn(ColumnType.NUMBER, "Value");

		int row = 0;
		for (VisualizationData value: values){
			data.addRow();
			data.setValue(row, 0, value.getName());
			data.setValue(row, 1, value.getY(inverted));
			row++;
		}
		return data;
	}

	/*
	 * Gets the chart options.
	 * 
	 * @return options the chart options.
	 */
	private Gauge.Options getOptions() {
		Gauge.Options options = Gauge.Options.create();
		return options;
	}
	
	/* Function called when loaded. */
	private final Runnable onLoadCallback = new Runnable() {
		@Override
		public void run() {
			field = new Gauge(getDataTable(), getOptions());
			field.addStyleName(style);
			field.setTitle(label);
			panel.add(field);
		}
	};
}


