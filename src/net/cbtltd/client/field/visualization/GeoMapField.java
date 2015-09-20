/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field.visualization;

import java.util.ArrayList;

import net.cbtltd.client.HasComponents;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.VisualizationData;
import net.cbtltd.shared.api.HasTableService;

import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.GeoMap;
import com.google.gwt.visualization.client.visualizations.GeoMap.DataMode;

/**
 * The Class GeoMapField extends VisualizationField for geographic map charts.
 * Two address formats are supported, each of which supports a different number of columns,
 * and different data types for each column.
 * All addresses in the table must use one or the other; you cannot mix types.
 * Latitude/Longitude locations. This format works only when the dataMode option is 'markers'.
 * The location is entered in two columns, plus two optional columns:
 * [Number, Required] A latitude. Positive numbers are north, negative numbers are south.
 * [Number, Required] A longitude. Positive numbers are east, negative numbers are west.
 * [Number, Optional] A numeric value displayed when the user hovers over this region.
 * [String, Optional] Additional string text displayed when the user hovers over this region.
 *
 * Address, country name, or region name locations. This format works with the dataMode option
 * set to either 'markers' or 'regions'. The location is entered in one column, plus two optional columns:
 * [String, Required] A map location. This can be a string country name (for example, "England"),
 * a region name, or a specific address (for example, "1600 Pennsylvania Ave").
 * ISO-3166 codes are also accepted (for example, "RU" or "US-CAL").
 * [Number, Optional] A numeric value displayed when the user hovers over this region.
 * [String, Optional] Additional string text displayed when the user hovers over this region.
 * Your DataTable must include every optional column preceding any column that you want to use.
 * So, for example, if you want to specify a lat/long table, and only wanted to use columns 1, 2, and 4,
 * your DataTable must still define column 3 (though you don't need to add any values to it):
 *
 * dataTable = new google.visualization.DataTable();
 * dataTable.addRows(1);
 * dataTable.addColumn('number', 'LATITUDE', 'Latitude');
 * dataTable.addColumn('number', 'LONGITUDE', 'Longitude');
 * dataTable.addColumn('number', 'VALUE', 'Value'); // Won't use this column, but still must define it.
 * dataTable.addColumn('string', 'HOVER', 'HoverText');
 *
 * dataTable.setValue(0,0,47.00);
 * dataTable.setValue(0,1,-122.00);
 * dataTable.setValue(0,3,"Hello World!");
 *
 * The area to display on the map. (Surrounding areas will be displayed as well.)
 * Can be either a country code (in upper case ISO-3166 format), or a one of the following strings:
 * world - (Whole world) 005 - (South America) 013 - (Central America) 021 - (North America)
 * 002 - (All of Africa) 017 - (Central Africa) 015 - (Northern Africa) 018 - (Southern Africa)
 * 030 - (Eastern Asia) 034 - (Southern Asia) 035 - (Asia/Pacific region) 143 - (Central Asia)
 * 145 - (Middle East) 151 - (Northern Asia) 154 - (Northern Europe) 155 - (Western Europe) 039 - (Southern Europe)
 *
 * Chart Id = Value, Name = Hover Text, X = Latitude, Y = Longitude
 * @see <pre>http://code.google.com/docreader/#p=gwt-google-apis&s=gwt-google-apis&t=Visualization</pre>
 * @see <pre>http://code.google.com/apis/visualization/documentation/gallery/geomap.html</pre>
 */
public class GeoMapField
extends VisualizationField {
	private GeoMap field = null;
	private static final String region = "world";
	private static final String[] REGIONS = {"005", "013", "021", "002", "017", "015", "018", "030", "034", "035", "143", "145", "151", "154", "155", "039"};

	/**
	 * Gets the list of region name ID pairs. 
	 * 
	 * @return list of region name ID pairs. 
	 */
	public ArrayList<NameId> getRegions(){return NameId.getList(CONSTANTS.allRegions(), REGIONS);}

	public GeoMapField (
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
		else if (field == null) {VisualizationUtils.loadVisualizationApi(onLoadCallback, GeoMap.PACKAGE);}
		else {field.draw(getDataTable(), getOptions());}
	}

	/*
	 * Gets the data table version of the current values.
	 * 
	 * @return data table version of values.
	 */
	public DataTable getDataTable() {
		data = DataTable.create();
		data.addColumn(ColumnType.NUMBER, "LATITUDE", "Latitude");
		data.addColumn(ColumnType.NUMBER, "LONGITUDE", "Longitude");
		data.addColumn(ColumnType.NUMBER, "VALUE", "Value");
		data.addColumn(ColumnType.STRING, "HOVER", "Hover Text");

		int row = 0;
		for (VisualizationData value: values){
			data.addRow();
			data.setValue(row, 0, value.getX()); //latitude
			data.setValue(row, 1, value.getY()); //longitude
			data.setValue(row, 2, Double.valueOf(value.getCategory())); //value
			data.setValue(row, 3, value.getName()); //hover text
			row++;
		}
		return data;
	}

	/*
	 * Gets the chart options.
	 * 
	 * @return options the chart options.
	 */
	private GeoMap.Options getOptions() {
		GeoMap.Options options = GeoMap.Options.create();
	    options.setDataMode(DataMode.MARKERS);
	    options.setRegion(region);
	    options.setShowLegend(true);
	    return options;
	}

	/* Function called when loaded. */
	private final Runnable onLoadCallback = new Runnable() {
		@Override
		public void run() {
				field = new GeoMap(getDataTable(), getOptions());
				field.addStyleName(style);
//				field.addSelectHandler(new SelectHandler() {
//					public void onSelect(SelectEvent event) {
//						if (enabled){
//							JsArray<Selection> selections = field.getSelections();
//							if (selections == null || selections.length() == 0){return;}
//							Selection value = selections.get(0);
//							if (value.isCell()) {selectedRow = value.getRow(); selectedCol= value.getColumn();}
//							else if (value.isColumn()){selectedCol = value.getColumn();}
//							else if (value.isRow()){selectedRow = value.getRow();}
//							changeListeners.fireChange(field);
//						}
//					}
//				});
			panel.add(field);
		}
	};
}


