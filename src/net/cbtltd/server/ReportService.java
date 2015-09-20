/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import java.util.Date;
import java.util.logging.Level;

import net.cbtltd.server.api.DesignMapper;
import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.ReportMapper;
import net.cbtltd.shared.Data;
import net.cbtltd.shared.Design;
import net.cbtltd.shared.DoubleResponse;
import net.cbtltd.shared.JSONRequest;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Report;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.VisualizationData;
import net.cbtltd.shared.api.HasUrls;
import net.cbtltd.shared.design.DesignCreate;
import net.cbtltd.shared.design.DesignDelete;
import net.cbtltd.shared.design.DesignRead;
import net.cbtltd.shared.design.DesignUpdate;
import net.cbtltd.shared.design.ReportTable;
import net.cbtltd.shared.journal.EventJournal;
import net.cbtltd.shared.report.AccountActionTable;
import net.cbtltd.shared.report.AccountChart;
import net.cbtltd.shared.report.AccountJournalBalance;
import net.cbtltd.shared.report.AccountSummaryTable;
import net.cbtltd.shared.report.AverageRateChart;
import net.cbtltd.shared.report.ConfirmedChart;
import net.cbtltd.shared.report.FeedbackChart;
import net.cbtltd.shared.report.OccupancyChart;
import net.cbtltd.shared.report.OutcomeChart;
import net.cbtltd.shared.report.ProfitChart;
import net.cbtltd.shared.report.RevenueChart;
import net.cbtltd.shared.report.RevparChart;
import net.cbtltd.shared.report.StarterChart;
import net.cbtltd.shared.report.WorkflowChart;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;

/** 
 * The Class ReportService responds to report requests.
 * 
 * @see http://www.birt-exchange.org/org/wiki/index.php?title=Configuration
 * @see http://wiki.eclipse.org/Birt_3.7_Migration_Guide VIP!!
 * @see http://www.eclipse.org/forums/index.php/mv/msg/170815/547384/#msg_547384
 * @see http://dmitrygusev.blogspot.com/2011/09/running-birt-reports-in-tomcat.html
 * @see http://wiki.eclipse.org/Change_Database_URL_at_runtime_based_on_Parameter_%28BIRT%29
 */
public final class ReportService
implements IsService {

	private static final Logger LOG = Logger.getLogger(ReportService.class.getName());
	private static ReportService service;
	private static IReportEngine engine = null;

	/**
	 * Gets the single instance of ReportService to manage report Design instances and to generate reports with them.
	 * @see net.cbtltd.shared.Design
	 * @see net.cbtltd.shared.Report
	 *
	 * @return single instance of ReportService.
	 */
	public static synchronized ReportService getInstance() {
		if (service == null) {service = new ReportService();}
		return service;
	}

	/**
	 * Executes the DesignCreate action to create a Design instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Design execute(SqlSession sqlSession, DesignCreate action) {
		try {sqlSession.getMapper(DesignMapper.class).create(action);}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the DesignRead action to read a Design instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Design execute(SqlSession sqlSession, DesignRead action) {
		Design design = null;
		try {
			design = sqlSession.getMapper(DesignMapper.class).read(action.getId());
			//design.setRoles(RelationService.read(sqlSession, Relation.DESIGN_ROLE, design.getId(), null));
		}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return design;
	}

	/**
	 * Executes the DesignUpdate action to update a Design instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Design execute(SqlSession sqlSession, DesignUpdate action) {
		try {
			sqlSession.getMapper(DesignMapper.class).update(action);
			RelationService.replace(sqlSession, Relation.DESIGN_ROLE, action.getId(), action.getRoles());
			MonitorService.update(sqlSession, Data.Origin.CONSOLE, NameId.Type.Design, action);
		}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the DesignDelete action to delete a Design instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Design execute(SqlSession sqlSession, DesignDelete action) {
		try {
			sqlSession.getMapper(DesignMapper.class).update(action);
			RelationService.delete(sqlSession, Relation.DESIGN_ROLE, action.getId(), null);
		}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return null;
	}

	/**
	 * Executes the NameIdAction action to read a list of design NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, NameIdAction action) {
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(DesignMapper.class).nameidbyid(action));}
			else {table.setValue(sqlSession.getMapper(DesignMapper.class).nameidbyname(action));}
		}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}
	
	/**
	 * Executes the AccountActionTable action to read a table of EventJournal (journal event transaction) instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<EventJournal> execute(SqlSession sqlSession, AccountActionTable action) {
		LOG.debug("AccountActionTable " + action);
		Table<EventJournal> table = new Table<EventJournal>();
		try {
			table.setDatasize(sqlSession.getMapper(ReportMapper.class).countbyaccount(action));
			table.setValue(sqlSession.getMapper(ReportMapper.class).listbyaccount(action));
		}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the AccountSummaryTable action to read a table of EventJournal (journal event summary) instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<EventJournal> execute(SqlSession sqlSession, AccountSummaryTable action) {
		Table<EventJournal> table = new Table<EventJournal>();
		try {table.setValue(sqlSession.getMapper(ReportMapper.class).listbyaccountsummary(action));} 
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the AccountJournalBalance action to calculate the current balance of a financial account.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the double response
	 */
	public final DoubleResponse execute(SqlSession sqlSession, AccountJournalBalance action) {
		LOG.debug("AccountJournalBalance " + action);
		DoubleResponse response = new DoubleResponse();
		try {response.setValue(sqlSession.getMapper(ReportMapper.class).balancebyaccount(action));} 
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return response;
	}
	
	/**
	 * Executes the AccountChart action to read a table of VisualizationData instances for a chart.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<VisualizationData> execute(SqlSession sqlSession, AccountChart action) {
		Table<VisualizationData> table = new Table<VisualizationData>();
		try {table.setValue(sqlSession.getMapper(ReportMapper.class).accountchart(action));} 
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}
	
	/**
	 * Executes the AverageRateChart action to read a table of VisualizationData instances for a chart.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<VisualizationData> execute(SqlSession sqlSession, AverageRateChart action) {
		Table<VisualizationData> response = new Table<VisualizationData>();
		try {response.setValue(sqlSession.getMapper(ReportMapper.class).averagerate(action));} 
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return response;
	}
	
	/**
	 * Executes the ConfirmedChart action to read a table of VisualizationData instances for a chart.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<VisualizationData> execute(SqlSession sqlSession, ConfirmedChart action) {
		Table<VisualizationData> response = new Table<VisualizationData>();
		try {response.setValue(sqlSession.getMapper(ReportMapper.class).confirmed(action));} 
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return response;
	}

	/**
	 * Executes the OccupancyChart action to read a table of VisualizationData instances for a chart.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<VisualizationData> execute(SqlSession sqlSession, OccupancyChart action) {
		Table<VisualizationData> response = new Table<VisualizationData>();
		try {response.setValue(sqlSession.getMapper(ReportMapper.class).occupancy(action));} 
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return response;
	}

	/**
	 * Executes the ProfitChart action to read a table of VisualizationData instances for a chart.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<VisualizationData> execute(SqlSession sqlSession, ProfitChart action) {
		Table<VisualizationData> response = new Table<VisualizationData>();
		try {response.setValue(sqlSession.getMapper(ReportMapper.class).profitchart(action));} 
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return response;
	}

	/**
	 * Executes the FeedbackChart action to read a table of VisualizationData instances for a chart.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public static final Table<VisualizationData> execute(SqlSession sqlSession, FeedbackChart action) {
		Table<VisualizationData> table = new Table<VisualizationData>();
		try {table.setValue(sqlSession.getMapper(ReportMapper.class).feedback(action));} 
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the RevenueChart action to read a table of VisualizationData instances for a chart.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<VisualizationData> execute(SqlSession sqlSession, RevenueChart action) {
		Table<VisualizationData> response = new Table<VisualizationData>();
		try {response.setValue(sqlSession.getMapper(ReportMapper.class).revenuechart(action));} 
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return response;
	}

	/**
	 * Executes the RevparChart action to read a table of VisualizationData instances for a chart.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<VisualizationData> execute(SqlSession sqlSession, RevparChart action) {
		Table<VisualizationData> response = new Table<VisualizationData>();
		try {response.setValue(sqlSession.getMapper(ReportMapper.class).revpar(action));} 
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return response;
	}

	/**
	 * Executes the OutcomeChart action to read a table of VisualizationData instances for a chart.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public static final Table<VisualizationData> execute(SqlSession sqlSession, OutcomeChart action) {
		Table<VisualizationData> table = new Table<VisualizationData>();
		try {table.setValue(sqlSession.getMapper(ReportMapper.class).outcome(action));} 
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the StarterChart action to read a table of VisualizationData instances for a chart.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public static final Table<VisualizationData> execute(SqlSession sqlSession, StarterChart action) {
		Table<VisualizationData> table = new Table<VisualizationData>();
		try {table.setValue(sqlSession.getMapper(ReportMapper.class).starter(action));} 
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the WorkflowChart action to read a table of VisualizationData instances for a chart.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public static final Table<VisualizationData> execute(SqlSession sqlSession, WorkflowChart action) {
		Table<VisualizationData> table = new Table<VisualizationData>();
		try {table.setValue(sqlSession.getMapper(ReportMapper.class).workflow(action));} 
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/* Starts the BIRT report engine. */
	private static IReportEngine getEngine() {
		if (engine == null) {
			try {
				EngineConfig config = new EngineConfig( );
				//config.setEngineHome(HasUrls.REPORT_ENGINE_DIRECTORY);
				config.setLogConfig(null, Level.FINEST);
				//config.setLogConfig(Report.REPORT_LOG_DIRECTORY, Level.FINE);
				//config.setLogConfig(null, Level.WARNING);
				Platform.startup( config );
				IReportEngineFactory factory = (IReportEngineFactory) Platform
				.createFactoryObject( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
				engine = factory.createReportEngine( config );
				engine.changeLogLevel(Level.WARNING);
			}
			catch (BirtException x) {
				LOG.error("ReportService getEngine exception " + x.getMessage());
				MonitorService.log(x);
			}
			//finally	{Platform.shutdown();}
		}
		return engine;
	}

	/**
	 * Executes the ReportTable action to read a table of Report instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Report> execute(SqlSession sqlSession, ReportTable action) {
		Table<Report> table = new Table<Report>();
		try {
			table.setDatasize(sqlSession.getMapper(ReportMapper.class).countbydesign(action.getId()));
			table.setValue(sqlSession.getMapper(ReportMapper.class).listbydesign(action));
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the Report action to generate a Report instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the report
	 * @return the response.
	 */
	public static synchronized Report execute(SqlSession sqlSession, Report action) {
		Date timestamp = new Date();
		try {
			LOG.debug("Report " + action);
			sqlSession.getMapper(ReportMapper.class).create(action);
			sqlSession.commit();
			engine = getEngine();
			final IReportRunnable reportDesign = engine.openReportDesign(HasUrls.REPORT_DESIGNS_DIRECTORY + action.getDesign() + ".rptdesign");

			final IRunAndRenderTask task = engine.createRunAndRenderTask(reportDesign);
			task.setMaxRowsPerQuery(50000);
			task.setParameterValue("RP_ReportID", action.getId());
			task.setParameterValue("RP_States", action.getStates());
			task.setParameterValue("RP_Types", action.getTypes());
//			if (RazorServer.isLive()) {task.setParameterValue("RP_Database", "jdbc:mysql://razor-cloud-2012-07-01.cl8byaopxglx.us-east-1.rds.amazonaws.com:3306/razor");}
//			else {task.setParameterValue("RP_Database", "jdbc:mysql://razor-cloud-2012-07-01.cl8byaopxglx.us-east-1.rds.amazonaws.com:3306/demo");}
			if (RazorServer.isLive()) {task.setParameterValue("RP_Database", "jdbc:mysql://54.197.247.82:3306/razor");}
			else {task.setParameterValue("RP_Database", "jdbc:mysql://demodb.mybookingpal.com:3306/razor");}
			task.validateParameters();
			LOG.debug("Report RP_ReportID " + task.getParameterValue("RP_ReportID"));
			LOG.debug("Report RP_Database " + task.getParameterValue("RP_Database"));
			
			if (action.hasFormat(Report.HTML)) {
				final HTMLRenderOption htmlOption = new HTMLRenderOption();
				htmlOption.setOutputFormat("html");
				htmlOption.setOutputFileName(HasUrls.REPORTS_DIRECTORY + action.getId() + "." + Report.HTML);
				task.setRenderOption(htmlOption);
			}
			else if (action.hasFormat(Report.XLS)){
				final EXCELRenderOption xlsOption = new EXCELRenderOption();
				xlsOption.setOutputFormat("xls");
				xlsOption.setOutputFileName(HasUrls.REPORTS_DIRECTORY + action.getId() + "." + Report.XLS);
				task.setRenderOption(xlsOption);
			}
			else {
				final PDFRenderOption pdfOption = new PDFRenderOption();
				pdfOption.setOutputFormat("pdf");
				pdfOption.setOutputFileName(HasUrls.REPORTS_DIRECTORY + action.getId() + "." + Report.PDF);
				LOG.debug("Report pdf " + HasUrls.REPORTS_DIRECTORY + action.getId() + "." + Report.PDF);
				//pdf.setOption(IPDFRenderOption.PAGE_OVERFLOW, IPDFRenderOption.OUTPUT_TO_MULTIPLE_PAGES);
				task.setRenderOption(pdfOption);
			}
			task.run();
			task.close();
		}
		catch (Throwable x) {
			sqlSession.rollback(); MonitorService.log(x);
			LOG.error("ReportService Report " + action + " " + x.getMessage());
		}
		MonitorService.monitor(action.getDesign(), timestamp);
		return action;
	}

	/**
	 * Creates a report with the specified parameters.
	 *
	 * @param pos the report design.
	 * @param format the report output format.
	 * @param notes the report content.
	 * @return the report, or error message.
	 */
	public static final Report getReport(
		String design,
		String format,
		String notes
		) {
		Date timestamp = new Date();

		String message = 
			"?type=" + JSONRequest.REPORT
			+ "&design=" + design 
			+ "&format=" + format 
			+ "&notes=" + notes 
		;
		LOG.debug(message);

		if (design == null || design.isEmpty()) {throw new RuntimeException("Invalid design parameter");}
		if (notes == null || notes.isEmpty()) {throw new RuntimeException("Invalid notes parameter");}

//		ReportButtonItem result = new ReportButtonItem();
		
		Report action = new Report();
		action.setDesign(design);
		action.setFormat(format);
		action.setNotes(notes);
		
		SqlSession sqlSession = RazorServer.openSession();
		try {execute(sqlSession, action);}
		catch (Throwable x) {
			action.setNotes(x.getMessage());
			LOG.error(x.getMessage());
		}
		LOG.debug(action);
		MonitorService.monitor(message, timestamp);
		return action;
	}
}
