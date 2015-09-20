/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import net.cbtltd.server.api.CurrencyMapper;
import net.cbtltd.server.api.FinanceMapper;
import net.cbtltd.server.api.IsService;
import net.cbtltd.shared.Currencyrate;
import net.cbtltd.shared.Data;
import net.cbtltd.shared.Finance;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.finance.CurrencyNameId;
import net.cbtltd.shared.finance.CurrencyrateNameId;
import net.cbtltd.shared.finance.CurrencyrateTable;
import net.cbtltd.shared.finance.FinanceCreate;
import net.cbtltd.shared.finance.FinanceDelete;
import net.cbtltd.shared.finance.FinanceExists;
import net.cbtltd.shared.finance.FinanceRead;
import net.cbtltd.shared.finance.FinanceTable;
import net.cbtltd.shared.finance.FinanceUpdate;
import net.cbtltd.shared.finance.FinanceWrite;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/** The Class FinanceService responds to cash and bank account requests. */
public class FinanceService
implements IsService {

	private static final Logger LOG = Logger.getLogger(FinanceService.class.getName());
	private static FinanceService service;

	/**
	 * Gets the single instance of FinanceService to manage Finance instances.
	 * @see net.cbtltd.shared.Finance
	 *
	 * @return single instance of FinanceService
	 */
	public static synchronized FinanceService getInstance() {
		if (service == null) {service = new FinanceService();}
		return service;
	}

	//CRUD services
	/**
	 * Executes the FinanceCreate action to create a Finance instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action action the action to be executed.
	 * @return the response.
	 */
	public final Finance execute(SqlSession sqlSession, FinanceCreate action) {
		try {sqlSession.getMapper(FinanceMapper.class).create(action);}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the FinanceExists action to check if the Finance instance already exists.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action action the action to be executed.
	 * @return the response.
	 */
	public final Finance execute(SqlSession sqlSession, FinanceExists action) {
		Finance finance = null;
		try {finance = sqlSession.getMapper(FinanceMapper.class).exists(action);}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return finance;
	}

	/**
	 * Executes the FinanceRead action to read a Finance instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action action the action to be executed.
	 * @return the response.
	 */
	public final Finance execute(SqlSession sqlSession, FinanceRead action) {
		Finance finance = null;
		try {
			if (action != null && action.hasType(Finance.CARD_UNENCRYPTED)) {
				action.setAccountnumber(Finance.encrypt(action.getAccountnumber()));
				action.setMonth(Finance.encrypt(action.getMonth()));
				action.setYear(Finance.encrypt(action.getYear()));
				action.setCode(Finance.encrypt(action.getCode()));
				action.setType(Finance.Type.Card.name());
			}
			finance = sqlSession.getMapper(FinanceMapper.class).read(action.getId());
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return finance;
	}

	/**
	 * Executes the FinanceUpdate action to update a Finance instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action action the action to be executed.
	 * @return the response.
	 */
	public final Finance execute(SqlSession sqlSession, FinanceUpdate action) {
		try {
			sqlSession.getMapper(FinanceMapper.class).update(action);
			TextService.update(sqlSession, action.getTexts());
			MonitorService.update(sqlSession, Data.Origin.CONSOLE, NameId.Type.Finance, action);
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the FinanceWrite action to update a Finance instance if it exists, otherwise to create it.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action action the action to be executed.
	 * @return the response.
	 */
	public final Finance execute(SqlSession sqlSession, FinanceWrite action) {
		Finance exists = null;
		try {
			exists = sqlSession.getMapper(FinanceMapper.class).exists(action);
			if (exists == null) {sqlSession.getMapper(FinanceMapper.class).create(action);}
			else {sqlSession.getMapper(FinanceMapper.class).update(action);}
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the FinanceDelete action to delete a Finance instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action action the action to be executed.
	 * @return the response.
	 */
	public final Finance execute(SqlSession sqlSession, FinanceDelete action) {
		try {
			action.setState(Finance.FINAL);
			sqlSession.getMapper(FinanceMapper.class).update(action);
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return null;
	}

	/**
	 * Executes the FinanceTable action to read a list of Finance instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action action the action to be executed.
	 * @return the response.
	 */
	public final Table<Finance> execute(SqlSession sqlSession, FinanceTable action) {
		Table<Finance> table = new Table<Finance>();
		try {
			table.setDatasize(sqlSession.getMapper(FinanceMapper.class).count(action));
			table.setValue(sqlSession.getMapper(FinanceMapper.class).list(action));
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the NameIdAction action to read a list of cash and bank account NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, NameIdAction action) {
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(FinanceMapper.class).nameidbyid(action));}
			else {table.setValue(sqlSession.getMapper(FinanceMapper.class).nameidbyname(action));}
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the CurrencyNameId action to read a list of currency NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, CurrencyNameId action) {
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(CurrencyMapper.class).nameidbyid(action));}
			else {table.setValue(sqlSession.getMapper(CurrencyMapper.class).nameidbyname(action));}
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the CurrencyrateNameId action to read a list of convertible currency NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, CurrencyrateNameId action) {
		Table<NameId> table = new Table<NameId>();
		try {table.setValue(sqlSession.getMapper(CurrencyMapper.class).nameidbyrate(action));}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the Currencyrate action to read a Currencyrate (currency exchange rate) instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action action the action to be executed.
	 * @return the currencyrate
	 */
	public final Currencyrate execute(SqlSession sqlSession, Currencyrate action) {
		LOG.debug("Currencyrate in " + action);
		try {action.setRate(WebService.getRate(sqlSession, action));} 
		catch (Throwable x){sqlSession.rollback(); MonitorService.log(x);}
		LOG.debug("\nCurrencyrate out " + action);
		return action;
	}

	/**
	 * Executes the CurrencyrateTable action to read a list of Currencyrate (currency exchange rate) instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action action the action to be executed.
	 * @return the response.
	 */
	public final Table<Currencyrate> execute(SqlSession sqlSession, CurrencyrateTable action) {
		Table<Currencyrate> response = new Table<Currencyrate>();
		try {
			for (Currencyrate currencyrate : action.getPairs()) {currencyrate.setRate(WebService.getRate(sqlSession, currencyrate));}
			response.setValue(action.getPairs());
		}
		catch (Throwable x){sqlSession.rollback(); MonitorService.log(x);}
		return response;
	}
}
