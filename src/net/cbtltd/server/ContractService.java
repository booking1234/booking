/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import net.cbtltd.server.api.ContractMapper;
import net.cbtltd.server.api.IsService;
import net.cbtltd.shared.Contract;
import net.cbtltd.shared.Data;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.contract.ContractCreate;
import net.cbtltd.shared.contract.ContractDelete;
import net.cbtltd.shared.contract.ContractRead;
import net.cbtltd.shared.contract.ContractTable;
import net.cbtltd.shared.contract.ContractUpdate;
import net.cbtltd.shared.contract.DiscountTable;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/** The Class ContractService responds to contract requests. */
public class ContractService
implements IsService {

	private static final Logger LOG = Logger.getLogger(ContractService.class.getName());
	private static ContractService service;

	/**
	 * Gets the single instance of ContractService to manage Contract instances.
	 * @see net.cbtltd.shared.Contract
	 *
	 * @return single instance of ContractService.
	 */
	public static synchronized ContractService getInstance() {
		if (service == null){service = new ContractService();}
		return service;
	}

	/**
	 * Executes the ContractCreate action to create a Contract instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the contract
	 */
	public final Contract execute(SqlSession sqlSession, ContractCreate action) {
		try {sqlSession.getMapper(ContractMapper.class).create(action);}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the ContractRead action to read a Contract instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the contract
	 */
	public final Contract execute(SqlSession sqlSession, ContractRead action) {
		Contract contract = null;
		try {contract =  sqlSession.getMapper(ContractMapper.class).read(action.getId());}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return contract;
	}

	/**
	 * Executes the ContractUpdate action to update a Contract instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the contract
	 */
	public final Contract execute(SqlSession sqlSession, ContractUpdate action) {
		try {
			sqlSession.getMapper(ContractMapper.class).update(action);
			MonitorService.update(sqlSession, Data.Origin.CONSOLE, NameId.Type.Contract, action);
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the ContractDelete action to delete a Contract instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the contract
	 */
	public final Contract execute(SqlSession sqlSession, ContractDelete action) {
		try {action.setState(Contract.FINAL);
		sqlSession.getMapper(ContractMapper.class).update(action);
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the ContractTable action to read a list of Contract instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Contract> execute(SqlSession sqlSession, ContractTable action) {
		Table<Contract> table = new Table<Contract>();
		try {
			table.setDatasize(sqlSession.getMapper(ContractMapper.class).contractcount(action));
			table.setValue(sqlSession.getMapper(ContractMapper.class).contractlist(action));
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the DiscountTable action to read a list of Discount instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Contract> execute(SqlSession sqlSession, DiscountTable action) {
		Table<Contract> table = new Table<Contract>();
		try {
			table.setDatasize(sqlSession.getMapper(ContractMapper.class).discountcount(action));
			table.setValue(sqlSession.getMapper(ContractMapper.class).discountlist(action));
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}
}
