/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.field.table;


import net.cbtltd.client.resource.FieldBundle;

import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

/**
 * A {@link Header} subclass that maintains sorting state and displays an icon
 * to indicate the sort direction.
 */
public class SortableHeader extends Header<String> {

	private static final FieldBundle RESOURCES = GWT.create(FieldBundle.class);
	private static final int IMAGE_WIDTH = 16;
	private static final String DOWN_ARROW = makeImage(RESOURCES.downArrow());
	private static final String UP_ARROW = makeImage(RESOURCES.upArrow());

	private static String makeImage(ImageResource resource) {
		AbstractImagePrototype proto = AbstractImagePrototype.create(resource);
		return proto.getHTML().replace("style='",
		"style='position:absolute;right:0px;top:0px;");
	}

	private boolean reverseSort = false;
	private boolean sorted = false;
	private String header;
	private String columnName; //DB column name or null if sort is local

	public SortableHeader(String header, String columnName) {
		super(new ClickableTextCell());
		this.header = header;
		this.columnName = columnName;
	}

	public boolean isReverseSort() {
		return reverseSort;
	}

	@Override
	public String getValue() {
		return header;
	}

	@Override
	public void render(Context context, SafeHtmlBuilder shb) {
		StringBuilder sb = new StringBuilder();
		sb.append("<div style='position:relative;cursor:hand;cursor:pointer;");
		sb.append("padding-right:");
		sb.append(IMAGE_WIDTH);
		sb.append("px;'>");
		if (sorted) {
			if (reverseSort) {sb.append(DOWN_ARROW);}
			else {sb.append(UP_ARROW);}
		}
		else {sb.append("<div style='position:absolute;display:none;'></div>");}
		sb.append("<div>");
		sb.append(header);
		sb.append("</div></div>");
		shb.append(SafeHtmlUtils.fromTrustedString(sb.toString()));
	}
	
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public boolean noColumnName() {
		return columnName == null || columnName.isEmpty();
	}
	
	public void setReverseSort(boolean reverseSort) {
		this.reverseSort = reverseSort;
	}

	public void setSorted(boolean sorted) {
		this.sorted = sorted;
	}

	public void toggleReverseSort() {
		this.reverseSort = !this.reverseSort;
	}
}
