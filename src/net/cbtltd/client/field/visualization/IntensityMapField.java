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
import com.google.gwt.visualization.client.visualizations.IntensityMap;
import com.google.gwt.visualization.client.visualizations.IntensityMap.Region;

/**
 * The Class IntensityMapField extends VisualizationField for intensity map charts.
 * The first column should be a string, and contain country ISO codes or USA state codes.
 * Any number of columns can follow, all must be numeric.
 * Each column is displayed as a separate map tab.
 * Chart Id = Country Code, Name = Y Name, Y = Y Value
 * @see <pre>http://code.google.com/docreader/#p=gwt-google-apis&s=gwt-google-apis&t=Visualization</pre>
 * @see <pre>http://code.google.com/apis/visualization/documentation/gallery/intensitymap.html</pre>
 */
public class IntensityMapField
extends VisualizationField {
	private IntensityMap field = null;
	private DataTable data;
	private Region region = Region.WORLD;

	public IntensityMapField(
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
		else if (field == null) {VisualizationUtils.loadVisualizationApi(onLoadCallback, IntensityMap.PACKAGE);}
		else {field.draw(getDataTable(), getOptions());}
	}

	/*
	 * Gets the data table version of the current values.
	 * 
	 * @return data table version of values.
	 */
	public DataTable getDataTable() {
		data = DataTable.create();
		Map<String, Integer>countries = new HashMap<String, Integer>();
		Map<String, Integer>names = new HashMap<String, Integer>();

		int row = 0;
		int col = 1;
		data.addColumn(ColumnType.STRING, "Country");
		for (VisualizationData value: values){
			String country = value.getCategory();
			String name = value.getName();
			if (!countries.containsKey(country)){
				data.addRow();
				data.setValue(row, 0, country);
				countries.put(country, row++);
			}
			if (!names.containsKey(name)){
				data.addColumn(ColumnType.NUMBER, name);
				names.put(name, col++);
			}
			data.setValue(countries.get(country), names.get(name), value.getY(inverted));
		}
		return data;
	}

	/*
	 * Gets the chart options.
	 * 
	 * @return options the chart options.
	 */
	private IntensityMap.Options getOptions() {
		IntensityMap.Options options = IntensityMap.Options.create();
		options.setRegion(region);
		return options;
	}

	/* Function called when loaded. */
	private final Runnable onLoadCallback = new Runnable() {
		@Override
		public void run() {
			field = new IntensityMap(getDataTable(), getOptions());
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
