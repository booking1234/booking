/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field.visualization;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.cbtltd.client.HasComponents;
import net.cbtltd.shared.VisualizationData;
import net.cbtltd.shared.api.HasTableService;

import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.AnnotatedTimeLine;

/**
 * The Class TimeLineField extends VisualizationField for annotated time line charts.
 * The first column should be of type date or date-time.
 * Any number of columns can follow, all must be numeric.
 * Each column is displayed as a separate line.
 * If the option displayAnnotations is used, two more string columns can be added after each
 * numeric column: an annotation title and an annotation text. Both of these columns are optional,
 * and if only one is used, it is considered as the annotation title.
 * data.setValue(0, 0, new Date(year = 2008 - 1900, month = 1, day = 1));
 * Chart Date = X Date (date - 1900), Name = Y Name, Y = Y Value
 * @see <pre>http://code.google.com/docreader/#p=gwt-google-apis&s=gwt-google-apis&t=Visualization</pre>
 * @see <pre>http://code.google.com/apis/visualization/documentation/gallery/annotatedtimeline.html</pre>
 */
public class TimeLineField
extends VisualizationField {

	private AnnotatedTimeLine field = null;

	public TimeLineField(
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
		else if (field == null) {VisualizationUtils.loadVisualizationApi(onLoadCallback, AnnotatedTimeLine.PACKAGE);}
		else {field.draw(getDataTable(), getOptions());}
	}

	/*
	 * Gets the data table version of the current values.
	 * 
	 * @return data table version of values.
	 */
	private DataTable getDataTable() {
		data = DataTable.create();
		Map<Date, Integer>dates = new HashMap<Date, Integer>();
		Map<String, Integer>names = new HashMap<String, Integer>();

		int row = 0;
		int col = 0;
		for(VisualizationData value : values){
			Date date = value.getDate();
			String name = value.getName();
			if (!dates.containsKey(date)){
				data.addRow();
				data.addColumn(ColumnType.DATE, "Date");
				dates.put(date, row++);
			}
			if (!names.containsKey(name)){
				data.addColumn(ColumnType.NUMBER, name);
				names.put(name, col++);
			}
			data.setValue(dates.get(date), names.get(name), value.getY());
		}
		return data;
	}

	/*
	 * Gets the chart options.
	 * 
	 * @return options the chart options.
	 */
	private AnnotatedTimeLine.Options getOptions() {
		AnnotatedTimeLine.Options options = AnnotatedTimeLine.Options.create();
		options.setDisplayAnnotations(true);
		return options;
	}

	/* Function called when loaded. */
	private final Runnable onLoadCallback = new Runnable() {
		@Override
		public void run() {
			field = new AnnotatedTimeLine(getDataTable(), getOptions(), width + "px", height + "px");
			field.addStyleName(style);
			panel.add(field);
		}
	};
}
