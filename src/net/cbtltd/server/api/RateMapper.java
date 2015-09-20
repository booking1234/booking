/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;

import net.cbtltd.json.Parameter;
import net.cbtltd.rest.Rating;
import net.cbtltd.rest.Review;
import net.cbtltd.shared.Rate;
import net.cbtltd.shared.VisualizationData;
import net.cbtltd.shared.rate.RateNote;
import net.cbtltd.shared.rate.RateNoteTable;

public interface RateMapper
extends ItemMapper<Rate> {
	void updateproduct(String productid);
	ArrayList<VisualizationData> columnchart(String productid); //ColumnChart
	ArrayList<VisualizationData> linechart(String productid); //LineChart
	ArrayList<RateNote> review(RateNoteTable action); //Notes
	ArrayList<Rating> restrating(String id); //RESTRating
	ArrayList<Review> restreview(String id); //RESTReview
	ArrayList<net.cbtltd.json.rate.RateWidgetItem> ratewidget(Parameter action); //JSON Rates
	ArrayList<net.cbtltd.json.review.ReviewWidgetItem> widgetreview(RateNoteTable action); //JSON Reviews
}