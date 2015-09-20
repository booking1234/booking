/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */

package net.cbtltd.client.widget.datepicker;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.PushButton;

/** The Class DefaultMonthSelector is a simple {@link MonthSelector} used for the default date picker. */

public final class DefaultMonthSelector extends MonthSelector {

	private PushButton backwards;
	private PushButton forwards;
	private Grid grid;

	/** Instantiates new default month selector instance. */
	public DefaultMonthSelector() {
		//AbstractField.CSS.ensureInjected();
	}

	@Override
	protected void refresh() {
		String formattedMonth = getModel().formatCurrentMonth();
		grid.setText(0, 1, formattedMonth);
	}

	@Override
	protected void setup() {
		// Set up backwards.
		backwards = new PushButton();
		backwards.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addMonths(-1);
			}
		});

		backwards.getUpFace().setHTML("&laquo;");
		backwards.setStyleName("DatePickerPreviousButton");

		forwards = new PushButton();
		forwards.getUpFace().setHTML("&raquo;");
		forwards.setStyleName("DatePickerNextButton");
		forwards.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addMonths(+1);
			}
		});

		// Set up grid.
		grid = new Grid(1, 3);
		grid.setWidget(0, 0, backwards);
		grid.setWidget(0, 2, forwards);

		CellFormatter formatter = grid.getCellFormatter();
		formatter.setStyleName(0, 1, "DatePickerMonth");
		formatter.setWidth(0, 0, "1");
		formatter.setWidth(0, 1, "100%");
		formatter.setWidth(0, 2, "1");
		grid.setStyleName("DatePickerMonthSelector");
		initWidget(grid);
	}

}
