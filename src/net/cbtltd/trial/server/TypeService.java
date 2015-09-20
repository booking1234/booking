/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.trial.server;

import net.cbtltd.server.MonitorService;
import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.TypeMapper;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Table;

import org.apache.ibatis.session.SqlSession;

/** The Class TypeService responds to category related requests. */
public class TypeService
implements IsService {

	private static TypeService service;

	/**
	 * Gets the single instance of TypeService.
	 *
	 * @return single instance of TypeService
	 */
	public static synchronized TypeService getInstance() {
		if (service == null) {service = new TypeService();}
		return service;
	}

	/**
	 * Executes the NameIdAction action to read a list of category NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public static final Table<NameId> execute(SqlSession sqlSession, NameIdAction action) {
		Table<NameId> table = new Table<NameId>();
		try {table.setValue(sqlSession.getMapper(TypeMapper.class).category(action));}
		catch (Throwable x) {MonitorService.log(x);}
		return table;
	}
}
