/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	3.0.10
 */
package net.cbtltd.rest;

import java.util.ArrayList;
import java.util.Collection;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.AccountMapper;
import net.cbtltd.server.api.AssetMapper;
import net.cbtltd.server.api.EventMapper;
import net.cbtltd.server.api.FinanceMapper;
import net.cbtltd.server.api.PartyMapper;
import net.cbtltd.server.api.TaskMapper;
import net.cbtltd.shared.Error;
import net.cbtltd.shared.License;
import net.cbtltd.shared.Model;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.account.AccountAction;
import net.cbtltd.shared.journal.EventJournal;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/** 
 * The Class AccountRest implements REST services for accounts and financial transactions etc. 
 * @see https://ipp.developer.intuit.com/0010_Intuit_Partner_Platform/0050_Data_Services
 * @see https://ipp.developer.intuit.com/0010_Intuit_Partner_Platform/0050_Data_Services/0030_V3_Data_Services_Specification_Draft
 */
public abstract class AbstractAccount {

	private static final Logger LOG = Logger.getLogger(AbstractAccount.class.getName());

	/**
	 * Gets the currency name ids.
	 *
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the currency name ids
	 */
	protected Currencies getCurrencyNameIds(
			String pos,
			String xsl) {

		String message = "/account/currencies?xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		try {
			Party organization = Constants.getParty(sqlSession, pos);
			NameIdAction action = new NameIdAction();
			action.setName(Model.BLANK);
			action.setOrganizationid(organization.getId());
			action.setNumrows(1000);
			Collection<NameId> item = sqlSession.getMapper(AccountMapper.class).nameidcurrency(action);
			if (item == null) {throw new RuntimeException(Error.currency_list.getMessage());}
			return new Currencies(item, null, xsl);
		} 
		catch(Throwable x) {
			//MonitorService.log(x);
			return new Currencies(null, x.getMessage(), xsl);
		}
		finally {sqlSession.close();}
	}

	/**
	 * Gets the account list.
	 *
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the account list
	 */
	protected Items getAccountList(
			String pos,
			String xsl) {

		String message = "/account/list?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		try {
			Party organization = Constants.getParty(sqlSession, pos);
			NameIdAction action = new NameIdAction();
			action.setName(Model.BLANK);
			action.setOrganizationid(organization.getId());
			action.setNumrows(10000);
			Collection<NameId> item = sqlSession.getMapper(AccountMapper.class).nameidbyname(action);
			if (item == null) {throw new RuntimeException(Error.account_list.getMessage());}
			Items result = new Items(null, null, null, null,item, xsl);
			LOG.debug(result);
			return result;
		} 
		catch (Throwable x) {
			//MonitorService.log(x); 
			return new Items(null, null, null, x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
	}

	/**
	 * Gets the account subledger.
	 *
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the account subledger
	 */
	protected Items getAccountSubledger(
			String pos,
			String xsl) {

		String message = "/account/subledger?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		try {
			Party organization = Constants.getParty(sqlSession, pos);
			NameIdAction action = new NameIdAction();
			action.setName(Model.BLANK);
			action.setOrganizationid(organization.getId());
			action.setNumrows(10000);
			LOG.debug("\naction " + action);
			Collection<NameId> item = sqlSession.getMapper(AccountMapper.class).nameidsubledger(action);
			LOG.debug("\nitem " + item);
			if (item == null) {throw new RuntimeException(Error.account_list.getMessage());}
			Items result = new Items(null, null, null, null,item, xsl);
			LOG.debug(result);
			return result;
		} 
		catch (Throwable x) {
			return new Items(null, null, null, x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
	}

	/**
	 * Gets the activity subaccount.
	 *
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the activity subaccount
	 */
	protected Items getActivitySubaccount(
			String pos,
			String xsl) {

		String message = "/account/activity?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		try {
			Party organization = Constants.getParty(sqlSession, pos);
			NameIdAction action = new NameIdAction();
			action.setName(Model.BLANK);
			action.setOrganizationid(organization.getId());
			action.setNumrows(10000);
			LOG.debug("\naction " + action);
			Collection<NameId> item = sqlSession.getMapper(TaskMapper.class).nameidbyname(action);
			LOG.debug("\nitem " + item);
			if (item == null) {throw new RuntimeException(Error.task_list.getMessage());}
			Items result = new Items("Activity", "Subledger", null, null,item, xsl);
			LOG.debug(result);
			return result;
		} 
		catch (Throwable x) {
			return new Items("Activity", "Subledger", null, x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
	}

	/**
	 * Gets the asset subaccount.
	 *
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the asset subaccount
	 */
	protected Items getAssetSubaccount(
			String pos,
			String xsl) {

		String message = "/account/asset?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		try {
			Party organization = Constants.getParty(sqlSession, pos);
			NameIdAction action = new NameIdAction();
			action.setName(Model.BLANK);
			action.setOrganizationid(organization.getId());
			action.setNumrows(10000);
			LOG.debug("\naction " + action);
			Collection<NameId> item = sqlSession.getMapper(AssetMapper.class).nameidbyname(action);
			LOG.debug("\nitem " + item);
			if (item == null) {throw new RuntimeException(Error.asset_list.getMessage());}
			Items result = new Items("Asset", "Subledger", null, null,item, xsl);
			LOG.debug(result);
			return result;
		} 
		catch (Throwable x) {
			return new Items("Asset", "Subledger", null, x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
	}

	/**
	 * Gets the finance subaccount.
	 *
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the finance subaccount
	 */
	protected Items getFinanceSubaccount(
			String pos,
			String xsl) {

		String message = "/account/asset?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		try {
			Party organization = Constants.getParty(sqlSession, pos);
			NameIdAction action = new NameIdAction();
			action.setName(Model.BLANK);
			action.setOrganizationid(organization.getId());
			action.setNumrows(10000);
			LOG.debug("\naction " + action);
			Collection<NameId> item = sqlSession.getMapper(FinanceMapper.class).nameidbyname(action);
			LOG.debug("\nitem " + item);
			if (item == null) {throw new RuntimeException(Error.finance_list.getMessage());}
			Items result = new Items("Finance", "Subledger", null, null,item, xsl);
			LOG.debug(result);
			return result;
		} 
		catch (Throwable x) {
			return new Items("Finance", "Subledger", null, x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
	}

	/**
	 * Gets the party subaccount.
	 *
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the party subaccount
	 */
	protected Items getPartySubaccount(
			String pos,
			String xsl) {

		String message = "/account/party?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		try {
			Party organization = Constants.getParty(sqlSession, pos);
			NameIdAction action = new NameIdAction();
			action.setName(Model.BLANK);
			action.setOrganizationid(organization.getId());
			action.setNumrows(10000);
			LOG.debug("\naction " + action);
			Collection<NameId> item = sqlSession.getMapper(PartyMapper.class).nameidbyname(action);
			LOG.debug("\nitem " + item);
			if (item == null) {throw new RuntimeException(Error.party_list.getMessage());}
			Items result = new Items("Party", "Subledger", null, null,item, xsl);
			LOG.debug(result);
			return result;
		} 
		catch (Throwable x) {
			return new Items("Party", "Subledger", null, x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
	}

	/**
	 * Gets the account transactions.
	 *
	 * @param accountid the accountid
	 * @param fromdate the fromdate
	 * @param todate the todate
	 * @param currency the currency
	 * @param pos the pos
	 * @param xsl the xsl
	 * @return the account transactions
	 */
	protected Accounts getAccountTransactions(
			String accountid,
			String fromdate,
			String todate,
			String currency,
			String pos,
			String xsl) {

		String message = "/account/" + accountid + "/" + fromdate + "/" + todate + "/" + currency + "?pos=" + pos + "&xsl=" + xsl;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		try {
			Party organization = Constants.getParty(sqlSession, pos);

			if (fromdate == null || fromdate.isEmpty() 
					|| todate == null || todate.isEmpty() 
					|| !Constants.parseDate(todate).after(Constants.parseDate(fromdate))) {throw new RuntimeException(Error.date_range.getMessage());}

			AccountAction action = new AccountAction();
			if (!Model.ZERO.equalsIgnoreCase(accountid)) {action.setAccountid(accountid);}
			action.setOrganizationid(organization.getId());
			action.setFromdate(Constants.parseDate(fromdate));
			action.setTodate(Constants.parseDate(todate));
			
			action.setCurrency(currency);
			action.setNumrows(1000);
			
			ArrayList<EventJournal> items = sqlSession.getMapper(EventMapper.class).listbyaccount(action);

			if (items == null || items.isEmpty()) {throw new RuntimeException(Error.account_data.getMessage());}
			LOG.debug("items " + items.size());
			return new Accounts(null, getAccounts(items), xsl);
		}
		catch (Throwable x) {
			//MonitorService.log(x); 
			return new Accounts(x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
	}

	/* Create account journals from item list */
	/**
	 * Gets the accounts.
	 *
	 * @param items the items
	 * @return the accounts
	 */
	private static Collection<Account> getAccounts(ArrayList<EventJournal> items) {
		Collection<Account> account = new ArrayList<Account>();
		Collection<Journal> journal = null;
		String lastAccountid = null;
		int i = 0;
		for (EventJournal item : items) {
			if (!item.hasAccountid(lastAccountid)) {
				lastAccountid = item.getAccountid();
				journal = new ArrayList<Journal>();
				account.add(new Account(item, journal));
			}
			journal.add(new Journal(item));
			if (i++ > 10000){break;}
		}
		return account;
	}

	/**
	 * Gets the event transactions by id.
	 *
	 * @param eventid the eventid
	 * @param fromdate the fromdate
	 * @param todate the todate
	 * @param pos the pos
	 * @param test the test
	 * @param xsl the xsl
	 * @return the event transactions by id
	 */
	protected Events getEventTransactionsById(
			String eventid,
			String fromdate,
			String todate,
			String pos,
			Boolean test,
			String xsl) {

		String message = "/account/event/" + eventid + "/" + fromdate + "/" + todate + "?pos=" + pos + "&xsl=" + xsl + "&test=" + test;
		LOG.debug(message);
		SqlSession sqlSession = RazorServer.openSession();
		try {
			Party organization = Constants.getParty(sqlSession, pos);

			AccountAction action = new AccountAction();
			if (Model.ZERO.equalsIgnoreCase(eventid)) {
				if (fromdate == null || fromdate.isEmpty() 
						|| todate == null || todate.isEmpty() 
						|| !Constants.parseDate(todate).after(Constants.parseDate(fromdate))) {throw new RuntimeException(Error.date_range.getMessage());}
				action.setFromdate(Constants.parseDate(fromdate));
				action.setTodate(Constants.parseDate(todate));	
			}
			else {action.setEventid(eventid);}
			action.setOrganizationid(organization.getId());
			action.setTest(test);
			action.setNumrows(1000);
			ArrayList<EventJournal> items = sqlSession.getMapper(EventMapper.class).listbyevent(action);
			if (items == null || items.isEmpty()) {throw new RuntimeException(Error.event_data.getMessage());}
			return new Events(null, getEvents(sqlSession, test, items), xsl);
		}
		catch (Throwable x) {
			//MonitorService.log(x); 
			sqlSession.rollback();
			return new Events(x.getMessage(), null, xsl);
		}
		finally {sqlSession.close();}
	}

	/* Create event journals from item list */
	/**
	 * Gets the events.
	 *
* @param sqlSession the current SQL session.
	 * @param test the test
	 * @param items the items
	 * @return the events
	 */
	private static Collection<Event> getEvents(SqlSession sqlSession, Boolean test, ArrayList<EventJournal> items) {
		Collection<Event> event = new ArrayList<Event>();
		Collection<Journal> journal = null;
		String lastId = null;
		int i = 0;
		for (EventJournal item : items) {
			if (!item.hasId(lastId)) {
				lastId = item.getId();
				journal = new ArrayList<Journal>();
				event.add(new Event(item, journal));
				if (!test) {sqlSession.getMapper(EventMapper.class).downloaded(lastId);}
			}
			journal.add(new Journal(item));
			if (i++ > 10000){break;}
		}
		sqlSession.commit();
		return event;
	}
}
