/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;

import net.cbtltd.shared.Report;
import net.cbtltd.shared.VisualizationData;
import net.cbtltd.shared.api.HasTableService;
import net.cbtltd.shared.journal.EventJournal;

public interface ReportMapper
extends AbstractMapper<Report> {
	Integer countbydesign(String designid); //CountByDesign
	ArrayList<Report> listbydesign(HasTableService action); //ListByDesign

	Double balancebyaccount(Report action);
	Integer countbyaccount(Report action);
	ArrayList<EventJournal> listbyaccount(Report action);
	Integer countbyaccountsummary(Report action);
	ArrayList<EventJournal> listbyaccountsummary(Report action);

	ArrayList<VisualizationData> accountchart(Report action);
	ArrayList<VisualizationData> averagerate(Report action);
	ArrayList<VisualizationData> confirmed(Report action);
	ArrayList<VisualizationData> feedback(Report action);
	ArrayList<VisualizationData> starter(Report action);
	ArrayList<VisualizationData> outcome(Report action);
	ArrayList<VisualizationData> occupancy(Report action);
	ArrayList<VisualizationData> profitchart(Report action);
	ArrayList<VisualizationData> revenuechart(Report action);
	ArrayList<VisualizationData> revpar(Report action);
	ArrayList<VisualizationData> workflow(Report action);
}