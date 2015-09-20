/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 * 
 * @see http://www.nsoftware.com/ibiz/quickbooks/components.aspx
 */
package net.cbtltd.server;

import net.cbtltd.server.api.AccountMapper;
import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.JournalMapper;
import net.cbtltd.shared.Account;
import net.cbtltd.shared.Data;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.NameIdType;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.account.AccountCreate;
import net.cbtltd.shared.account.AccountDelete;
import net.cbtltd.shared.account.AccountEntitytype;
import net.cbtltd.shared.account.AccountRead;
import net.cbtltd.shared.account.AccountUpdate;

import org.apache.ibatis.session.SqlSession;

/** The Class AccountService responds to account requests. */
public class AccountService 
implements IsService {

	private static AccountService service;

	/**
	 * Gets the single instance of AccountService to manage Account instances.
	 * @see net.cbtltd.shared.Account
	 *
	 * @return single instance of AccountService.
	 */
	public static synchronized AccountService getInstance() {
		if (service == null) {service = new AccountService();}
		return service;
	}

	/**
	 * Executes the AccountCreate action to create an Account instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Account execute(SqlSession sqlSession, AccountCreate action) {
		try {sqlSession.getMapper(AccountMapper.class).create(action);}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the AccountRead action to read an Account instance.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Account execute(SqlSession sqlSession, AccountRead action) {
		Account account = null;
		try {account = sqlSession.getMapper(AccountMapper.class).read(action.getId());}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return account;
	}

	/**
	 * Executes the AccountUpdate action to update an Account instance.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Account execute(SqlSession sqlSession, AccountUpdate action) {
		try {
			sqlSession.getMapper(AccountMapper.class).update(action);
			RelationService.create(sqlSession, Relation.ORGANIZATION_ACCOUNT, action.getOrganizationid(), action.getId());
			MonitorService.update(sqlSession, Data.Origin.CONSOLE, NameId.Type.Account, action);
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the AccountDelete action to delete an Account instance.
	 * This deletes the relation between the account and the current organization, not the account instance.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Account execute(SqlSession sqlSession, AccountDelete action) {
		try {RelationService.delete(sqlSession, Relation.ORGANIZATION_ACCOUNT, action.getOrganizationid(), action.getId());}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return null;
	}

	/**
	 * Executes the NameIdAction action to read a list of account NameId instances.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, NameIdAction action) {
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(AccountMapper.class).nameidbyid(action));}
			else {table.setValue(sqlSession.getMapper(AccountMapper.class).nameidbyname(action));}
			table.setDatasize(action.getNumrows());
		} 
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the AccountEntitytype action to read a list of entity (sub-account) NameId instances.
	 *
	 * @param sqlSession the current SQL session
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, AccountEntitytype action) {
		Table<NameId> table = new Table<NameId>();
		try {table.setValue(sqlSession.getMapper(JournalMapper.class).entitytypenameid(action));}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}
	
	/**
	 * Executes the NameIdType action to read a list of account type NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, NameIdType action) {
		Table<NameId> table = new Table<NameId>();
		try {table.setValue(sqlSession.getMapper(AccountMapper.class).nameidtype(action));}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}
}
