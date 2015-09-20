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
import com.google.gwt.visualization.client.visualizations.MapVisualization;
import com.google.gwt.visualization.client.visualizations.MapVisualization.Type;

/**
 * The Class MapVisualizationField extends VisualizationField for map visualization charts.
 * Two alternative data formats are supported:
 * As LatLng pairs: The first columns should be a number designating the latitude and the second
 * a number designating the longitude. The third (optional) column is a string with the description
 * of the location specified by the coordinates in first two columns.
 *
 * As an address: The first column should be a string that contains an address.
 * The second (optional) column is a string with the description of the location specified by the address in first column.
 * Chart Name = Description, X = Latitude, Y = Longitude
 * @see <pre>http://code.google.com/docreader/#p=gwt-google-apis&s=gwt-google-apis&t=Visualization</pre>
 * @see <pre>http://code.google.com/apis/visualization/documentation/gallery/map.html</pre>
 */
public class MapVisualizationField
extends VisualizationField {
	private MapVisualization field = null;
	private Type type = Type.NORMAL;

	public MapVisualizationField(
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
		else if (field == null) {VisualizationUtils.loadVisualizationApi(onLoadCallback, MapVisualization.PACKAGE);}
		else {field.draw(getDataTable(), getOptions());}
	}

	/*
	 * Gets the data table version of the current values.
	 * 
	 * @return data table version of values.
	 */
	public DataTable getDataTable() {
		//Window.alert("getDataTable in " + values.toString());
		data = DataTable.create();
		data.addColumn(ColumnType.NUMBER, "LATITUDE", "Latitude");
		data.addColumn(ColumnType.NUMBER, "LONGITUDE", "Longitude");
		data.addColumn(ColumnType.STRING, "DESCRIPTION", "Description");

		int row = 0;
		for(VisualizationData value : values){
			data.addRow();
			data.setValue(row, 0, value.getX()); //latitude
			data.setValue(row, 1, value.getY()); //longitude
			data.setValue(row, 2, value.getName()); //description
			row++;
		}
		return data;
	}

	/*
	 * Gets the chart options.
	 * 
	 * @return options the chart options.
	 */
	private MapVisualization.Options getOptions() {
		MapVisualization.Options options = MapVisualization.Options.create();
		options.setEnableScrollWheel(true);
		options.setMapType(type);
		options.setEnableScrollWheel(true);
		options.setShowLine(true);
		options.setShowTip(true);
		return options;
	}

	/* Function called when loaded. */
	private final Runnable onLoadCallback = new Runnable() {
		@Override
		public void run() {
			field = new MapVisualization(getDataTable(), getOptions(), width + "px", height + "px");
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
