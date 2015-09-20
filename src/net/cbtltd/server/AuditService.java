/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import net.cbtltd.server.api.AuditMapper;
import net.cbtltd.server.api.IsService;
import net.cbtltd.shared.Audit;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.audit.AuditCreate;
import net.cbtltd.shared.audit.AuditDelete;
import net.cbtltd.shared.audit.AuditRead;
import net.cbtltd.shared.audit.AuditTable;
import net.cbtltd.shared.audit.AuditUpdate;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/** The Class AuditService responds to contract requests. */
public class AuditService
implements IsService {

	private static final Logger LOG = Logger.getLogger(AuditService.class.getName());
	private static AuditService service;

	/**
	 * Gets the single instance of AuditService to manage guest feedback (rating).
	 *
	 * @return single instance of AuditService
	 */
	public static synchronized AuditService getInstance() {
		if (service == null) {service = new AuditService();}
		return service;
	}

	/**
	 * Executes the AuditCreate action to create an Audit instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Audit execute(SqlSession sqlSession, AuditCreate action) {
		LOG.debug("AuditCreate " + action);
		try {sqlSession.getMapper(AuditMapper.class).create(action);}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the AuditRead action to read an Audit instance.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Audit execute(SqlSession sqlSession, AuditRead action) {
		Audit audit = null;
		try {audit = sqlSession.getMapper(AuditMapper.class).read(action.getId());}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return audit;
	}

	/**
	 * Executes the AuditUpdate action to update an Audit instance.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Audit execute(SqlSession sqlSession, AuditUpdate action) {
		try {sqlSession.getMapper(AuditMapper.class).update(action);}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the AuditDelete action to delete an Audit instance.
	 * This deletes the relation between the account and the current organization, not the account instance.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Audit execute(SqlSession sqlSession, AuditDelete action) {
		try {sqlSession.getMapper(AuditMapper.class).delete(action.getId());}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return null;
	}

	/**
	 * Executes the AuditTable action to read a list of Audit instances.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Audit> execute(SqlSession sqlSession, AuditTable action) {
		Table<Audit> table = new Table<Audit>();
		try {
			table.setDatasize(sqlSession.getMapper(AuditMapper.class).count(action));
			table.setValue(sqlSession.getMapper(AuditMapper.class).list(action));
		} 
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the NameIdAction action to read a list of audit NameId instances.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, NameIdAction action) {
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(AuditMapper.class).nameidbyid(action));}
			else {table.setValue(sqlSession.getMapper(AuditMapper.class).nameidbyname(action));}
			table.setDatasize(action.getNumrows());
		} 
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

}
