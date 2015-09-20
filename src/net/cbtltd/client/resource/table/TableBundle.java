/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.table;

import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;
import com.google.gwt.user.cellview.client.CellTable;

/**
 * A ClientBundle that provides images for this widget.
 */
public interface TableBundle extends CellTable.Resources {

	@Source("Table.css")  @CssResource.NotStrict 
	CellTable.Style cellTableStyle();

	@Source("../image/cellTableHeaderBackground.png")
	@ImageOptions(repeatStyle = RepeatStyle.Horizontal, flipRtl = true)
	ImageResource cellTableFooterBackground();

	@Source("../image/cellTableHeaderBackground.png")
	@ImageOptions(repeatStyle = RepeatStyle.Horizontal, flipRtl = true)
	ImageResource cellTableHeaderBackground();

	@Source("../image/cellTableLoading.gif")
	@ImageOptions(flipRtl = true)
	ImageResource cellTableLoading();

	@Source("../image/cellListSelectedBackground.png")
	@ImageOptions(repeatStyle = RepeatStyle.Horizontal, flipRtl = true)
	ImageResource cellTableSelectedBackground();

	@Source("../image/sortAscending.png")
	@ImageOptions(flipRtl = true)
	ImageResource cellTableSortAscending();

	@Source("../image/sortDescending.png")
	@ImageOptions(flipRtl = true)
	ImageResource cellTableSortDescending();

}



