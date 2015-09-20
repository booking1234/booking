/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.client.resource.report;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ReportBundle
extends ClientBundle {

	ReportBundle INSTANCE = GWT.create(ReportBundle.class);

	@Source("Report.css") ReportCssResource css();
	@Source("../image/accountjournaltableEmpty.png")	ImageResource accountjournaltableEmpty();
	@Source("../image/accountsummarytableEmpty.png")	ImageResource accountsummarytableEmpty();
	@Source("../image/accountsummarychartEmpty.png")	ImageResource accountsummarychartEmpty();
	@Source("../image/averageratechartEmpty.png")		ImageResource averageratechartEmpty();
	@Source("../image/confirmedchartEmpty.png")			ImageResource confirmedchartEmpty();
	@Source("../image/feedbackchartEmpty.png")			ImageResource feedbackchartEmpty();
	@Source("../image/occupancychartEmpty.png")			ImageResource occupancychartEmpty();
	@Source("../image/outcomechartEmpty.png")			ImageResource outcomechartEmpty();
	@Source("../image/profitsummarychartEmpty.png")		ImageResource profitsummarychartEmpty();
	@Source("../image/revenuesummarychartEmpty.png")	ImageResource revenuesummarychartEmpty();
	@Source("../image/revparchartEmpty.png")			ImageResource revparchartEmpty();
	@Source("../image/starterchartEmpty.png")			ImageResource starterchartEmpty();
	@Source("../image/workflowchartEmpty.png")			ImageResource workflowchartEmpty();
	@Source("../image/shadow_control.png")				ImageResource shadow_control();
	@Source("../image/searchLeft.png")					ImageResource searchLeft();
	@Source("../image/searchCenter.png")				ImageResource searchCenter();
	@Source("../image/searchRight.png")					ImageResource searchRight();
}