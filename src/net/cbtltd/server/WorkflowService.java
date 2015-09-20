/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import java.util.ArrayList;

import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.WorkflowMapper;
import net.cbtltd.shared.DoubleResponse;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdState;
import net.cbtltd.shared.Process;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.Workflow;
import net.cbtltd.shared.workflow.WorkflowProgress;
import net.cbtltd.shared.workflow.WorkflowTable;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/** The Class WorkflowService responds to work flow rule requests. */
public class WorkflowService
implements IsService {

	private static final Logger LOG = Logger.getLogger(WorkflowService.class.getName());
	private static WorkflowService service;

	/**
	 * Gets the single instance of WorkflowService to create lists of Workflow (work flow rule) instances.
	 * @see net.cbtltd.shared.Workflow
	 *
	 * @return single instance of WorkflowService
	 */
	public static synchronized WorkflowService getInstance() {
		if (service == null) {service = new WorkflowService();}
		return service;
	}

	/**
	 * Reads the list of Workflow instances using the ID of the specified actor.
	 *
	 * @param sqlSession the current SQL session.
	 * @param id the ID of the specified actor.
	 * @return the response.
	 */
	public static final ArrayList<Workflow> read(SqlSession sqlSession, String id) {
		ArrayList<Workflow> list = null;
		try {list = sqlSession.getMapper(WorkflowMapper.class).read(id);}
		catch (Exception x) {sqlSession.rollback(); MonitorService.log(x);}
		return list;
	}
	
	/**
	 * Update the specified list of Workflow instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param workflows the list of work flow rule instances.
	 */
	public static final void update(SqlSession sqlSession, ArrayList<Workflow> workflows) {
		try {
			if (workflows == null || workflows.isEmpty()) {return;}
			for (Workflow workflow : workflows) {
				Workflow exists = sqlSession.getMapper(WorkflowMapper.class).exists(workflow);
				if (exists == null) {sqlSession.getMapper(WorkflowMapper.class).create(workflow);}
				else {sqlSession.getMapper(WorkflowMapper.class).update(workflow);}
			}
		}
		catch (Exception x) {sqlSession.rollback(); MonitorService.log(x);}
	}
	
	/**
	 * Executes the WorkflowProgress action to get current progress progress.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the current progress progress.
	 */
	public final DoubleResponse execute(SqlSession sqlSession, WorkflowProgress action) {
		DoubleResponse response = new DoubleResponse();
		Double progressoverduereservation = sqlSession.getMapper(WorkflowMapper.class).progressoverduereservation(action);
		Double progresstotalreservation = sqlSession.getMapper(WorkflowMapper.class).progresstotalreservation(action);
		Double progressoverduetask = sqlSession.getMapper(WorkflowMapper.class).progressoverduetask(action);
		Double progresstotaltask = sqlSession.getMapper(WorkflowMapper.class).progresstotaltask(action);
		
		progressoverduereservation = (progressoverduereservation == null) ? 0.0 : progressoverduereservation;
		progresstotalreservation = (progresstotalreservation == null) ? 0.0 : progresstotalreservation;
		progressoverduetask = (progressoverduetask == null) ? 0.0 : progressoverduetask;
		progresstotaltask = (progresstotaltask == null) ? 0.0 : progresstotaltask;
		Double overdue = progressoverduereservation + progressoverduetask;
		Double total = progresstotalreservation + progresstotaltask;

		response.setValue((total == 0.0) ? 100.0 : (total - overdue) * 100.0 / total);
		LOG.debug("WorkflowProgress " + progressoverduereservation
				+ ", " + progresstotalreservation
				+ ", " + progressoverduetask
				+ ", " + progresstotaltask
				+ ", " + response.getValue());
		return response;
	}
	
	/**
	 * Executes the WorkflowTable action to get a table of current Process (work item) instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Process> execute(SqlSession sqlSession, WorkflowTable action) {
		LOG.debug("WorkflowTable " + action);
		Table<Process> table = new Table<Process>();
		try {
			Integer reservationcount = sqlSession.getMapper(WorkflowMapper.class).reservationcount(action);
			reservationcount = (reservationcount == null ? 0 : reservationcount);
			Integer taskcount = sqlSession.getMapper(WorkflowMapper.class).taskcount(action);
			taskcount = (taskcount == null ? 0 : taskcount);
			table.setDatasize(reservationcount + taskcount);
			table.setValue(sqlSession.getMapper(WorkflowMapper.class).worklist(action));
		}
		catch (Exception x) {sqlSession.rollback(); MonitorService.log(x);}
		LOG.debug("WorkflowTable " + table);
		return table;
	}
	
	/**
	 * Executes the NameIdState action to read a list of work flow state NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, NameIdState action) {
		Table<NameId> table = new Table<NameId>();
		try {table.setValue(sqlSession.getMapper(WorkflowMapper.class).nameidstate(action));}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}
}

