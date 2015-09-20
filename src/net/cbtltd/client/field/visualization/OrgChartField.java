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
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.OrgChart;
import com.google.gwt.visualization.client.visualizations.OrgChart.Size;

/**
 * The Class OrgChartField extends VisualizationField for organization charts.
 * The first column is the node name (e.g. employee name), and the
 * second column is the name of the parent node (e.g. manager's name).
 * For each row, the formatted value of the first column is displayed,
 * and the value is used to match a node to its parent node.
 * Chart Id = Parent (eg Manager), Name = Name(eg Employee)
 * @see <pre>http://code.google.com/docreader/#p=gwt-google-apis&s=gwt-google-apis&t=Visualization</pre>
 * @see <pre>http://code.google.com/apis/visualization/documentation/gallery/orgchart.html</pre>
 */
public class OrgChartField
extends VisualizationField {
	private OrgChart field = null;

	public OrgChartField(
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
		else if (field == null) {VisualizationUtils.loadVisualizationApi(onLoadCallback, OrgChart.PACKAGE);}
		else {field.draw(getDataTable(), getOptions());}
	}

	/*
	 * Gets the data table version of the current values.
	 * 
	 * @return data table version of values.
	 */
	public DataTable getDataTable() {
		data = DataTable.create();
		data.addColumn(ColumnType.STRING, "Name");
		data.addColumn(ColumnType.STRING, "Parent");
		data.addRows(values.size());
		int row = 0;
		for(VisualizationData value : values){
			data.setValue(row, 0, value.getName());
			data.setValue(row, 1, value.getCategory());
			row++;
		}
		return data;
	}

	/*
	 * Gets the chart options.
	 * 
	 * @return options the chart options.
	 */
	private OrgChart.Options getOptions() {
		OrgChart.Options options = OrgChart.Options.create();
		if (width + height < 50) {options.setSize(Size.SMALL);}
		else if (width + height > 200) {options.setSize(Size.LARGE);}
		else options.setSize(Size.MEDIUM);
		return options;
	}

	/* Function called when loaded. */
	private final Runnable onLoadCallback = new Runnable() {
		@Override
		public void run() {
			field = new OrgChart(getDataTable(), getOptions());
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

