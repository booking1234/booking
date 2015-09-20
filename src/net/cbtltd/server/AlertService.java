/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import net.cbtltd.server.api.AlertMapper;
import net.cbtltd.server.api.IsService;
import net.cbtltd.shared.Alert;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.alert.AlertCreate;
import net.cbtltd.shared.alert.AlertDelete;
import net.cbtltd.shared.alert.AlertRead;
import net.cbtltd.shared.alert.AlertTable;
import net.cbtltd.shared.alert.AlertUpdate;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/** The Class AlertService responds to alert actions. */
public class AlertService
implements IsService {

	private static final Logger LOG = Logger.getLogger(AlertService.class.getName());
	private static AlertService service;

	/**
	 * Gets the single instance of AlertService.
	 *
	 * @return single instance of AlertService
	 */
	public static synchronized AlertService getInstance() {
		if (service == null) {service = new AlertService();}
		return service;
	}

	/**
	 * Executes the AlertCreate action.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Alert execute(SqlSession sqlSession, AlertCreate action) {
		LOG.debug("AlertCreate in " + action);
		try {sqlSession.getMapper(AlertMapper.class).create(action);}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the AlertRead action.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Alert execute(SqlSession sqlSession, AlertRead action) {
		Alert alert = null;
		try {alert = sqlSession.getMapper(AlertMapper.class).read(action.getId());}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return alert;
	}

	/**
	 * Executes the AlertUpdate action.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Alert execute(SqlSession sqlSession, AlertUpdate action) {
		try {sqlSession.getMapper(AlertMapper.class).update(action);}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the AlertDelete action.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Alert execute(SqlSession sqlSession, AlertDelete action) {
		try {sqlSession.getMapper(AlertMapper.class).delete(action.getId());}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return null;
	}

	/**
	 * Executes the AlertTable action to read an alert table for a property.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Alert> execute(SqlSession sqlSession, AlertTable action) {
		LOG.debug("AlertTable in " + action);
		Table<Alert> table = new Table<Alert>();
		try {
			table.setDatasize(sqlSession.getMapper(AlertMapper.class).count(action));
			table.setValue(sqlSession.getMapper(AlertMapper.class).list(action));
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		LOG.debug("AlertTable out " + table);
		return table;
	}

	/**
	 * Executes the alert NameIdAction action.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, NameIdAction action) {
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(AlertMapper.class).nameidbyid(action));}
			else {table.setValue(sqlSession.getMapper(AlertMapper.class).nameidbyname(action));}
			table.setDatasize(action.getNumrows());
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}
}